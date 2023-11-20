import {
	Component, OnInit, ChangeDetectorRef,
	AfterViewInit, OnDestroy, ViewEncapsulation,
	ChangeDetectionStrategy
} from '@angular/core';
import { FormControl, UntypedFormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

import { MatDialog } from '@angular/material/dialog';

import { ConceptoRegimenPensionarioService } from "./conceptoregimenpensionario.service";
import { ConceptoRegimenPensionario } from "./conceptoregimenpensionario.types";
import { TranslocoService } from '@ngneat/transloco';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { BasePagination, BsConfirmationService, SelectItemVO, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { CommonService, EstadoGeneralState, ListaItemType } from '@ng-mf/shared/service/comun';
import { merge, takeUntil } from 'rxjs';
import { bsAnimations } from '@ng-mf/shared/bs';
/**
 * La Class ConceptoRegimenPensionarioComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Wed Oct 21 21:47:02 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Component({
	selector: 'ng-mf-bs-conceptoregimenpensionario',
	templateUrl: './conceptoregimenpensionario.component.html',
	styleUrls: ['./conceptoregimenpensionario.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class ConceptoRegimenPensionarioComponent extends BaseComponent<ConceptoRegimenPensionario> implements OnInit, AfterViewInit, OnDestroy {

	/** La lista anhio. */
	public listaAnhio: SelectItemVO[] | undefined = [];

	public listaRegimenPensionario: SelectItemVO[] | undefined = [];
	
	//public listaMesByDevengue: SelectItemVO[] | undefined = [];

	idRegimenPensionario = new FormControl(null);
	idMesByDevengue = new FormControl(null);
	idAnhio = new FormControl(null);

	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		router: Router,
		route: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: ConceptoRegimenPensionarioService,
		public commonService: CommonService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService
	) {
		super(_translocoService, _bsConfirmationService);

		this.debounceTimeProcesar().subscribe(term => { this.search = term; this.buscar() });
		this.SORT_NAME = 'idItemByRegimenPensionario';
	}

	/**
	 * After view init
	 */
	ngAfterViewInit(): void {
		if (this._sort && this._paginator) {
			// Set the initial sort
			this._sort.sort({
				id: this.SORT_NAME,
				start: this.SORT_DIR,
				disableClear: true
			});
			// Mark for check
			this._changeDetectorRef.markForCheck();
			// If the user changes the sort order...
			this._sort.sortChange
				.pipe(takeUntil(this._unsubscribeAll))
				.subscribe(() => {
					// Reset back to the first page
					this._paginator.pageIndex = 0;
				});

			// Get products if sort or page changes
			merge(this._sort.sortChange, this._paginator.page).pipe(takeUntil(this._unsubscribeAll))
				.subscribe(() => {
					this.buscar();
				});;
		}
	}

	/**
	 * On destroy
	 */
	override ngOnDestroy(): void {
		// Unsubscribe from all subscriptions
		this._unsubscribeAll.next(null);
		this._unsubscribeAll.complete();
	}

	ngOnInit() {
		this.onInit();
		this.inicializar();
		this.crearFormulario();
	}

	private crearFormulario(): void {
		this.frmGroup = this.fb.group({
			idConceptoRegimenPensionario: [null],
			idItemByRegimenPensionario: [null],
			idItemByMesByDevengue: [null],
			comisionFija: [null],
			comisionSobreFlujo: [null],
			comisionSobreFlujoMixto: [null],
			comisionAnualSobreSaldoMixto: [null],
			primaSeguros: [null],
			aporteObligatorio: [null],
			remuneracionMaximaAsegurable: [null],
			idAnhio: [null],
			estado: [''],
		});
		this.onChange();
	}
	public async cargarCombo() {
		let paramsTemp: Map<any, any> = new Map<any, any>();
		paramsTemp = paramsTemp.set("anhio", 0);
		await this.commonService.obtenerListaSelectItemVOMap("common", paramsTemp);
		this.listaAnhio = this.commonService.getListaSelectVOItem("anhio");

		paramsTemp = new Map<any, any>();
		paramsTemp = paramsTemp.set(ListaItemType.T11_REGIMEN_PENSIONARIO, 0);
		//paramsTemp = paramsTemp.set(ListaItemType.TIPO_MESES, 0);
		await this.commonService.obtenerListaItemSelectItemMap(paramsTemp);
		//this.listaMesByDevengue = this.commonService.getListaItemSelectItem(ListaItemType.TIPO_MESES);
		this.listaRegimenPensionario = this.commonService.getListaItemSelectItem(ListaItemType.T11_REGIMEN_PENSIONARIO);
	}
	private onChange(): void {
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});

	}

	onInit() {
		// Get the pagination
		this.service.pagination$
			.pipe(takeUntil(this._unsubscribeAll))
			.subscribe((pagination: BasePagination | null) => {
				// Update the pagination
				this.pagination = pagination;
				// Mark for check
				this._changeDetectorRef.markForCheck();
			});
		// Get the listaData
		this.listaData$ = this.service.listaData$;
	}

	/**
	 * guardar.
	 *
	 */
	public guardar() {
		if (this.frmGroup.invalid) {
			this.mostrarMensajeErrorFrmInvalid();
			return;
		}
		try {
			if (this.accionNuevo) {
				this.service.crear(this.frmGroup.value).subscribe(
					data => {
						if (this.isProcesoOK(data)) {
							this.guardoExito();
							this.buscar();
						}
					},
					error => {
						this.mostrarMensajeError(error);
					}
				);
			} else {
				this.service.modificar(this.frmGroup.value, this.frmGroup.value.idConceptoRegimenPensionario).subscribe(
					data => {
						if (this.isProcesoOK(data)) {
							this.actualizadoExito();
							this.buscar();
						}
					},
					error => {
						this.mostrarMensajeError(error);
					}
				);
			}
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}

	/**
	 * Nuevo.
	 *
	 */
	public nuevo() {
		this.crearFormulario();
		this.frmGroup.get("estado")?.setValue(EstadoGeneralState.ACTIVO.toString());
		this.frmGroup.get("idAnhio")?.setValue(this.idAnhio.value);
		this.frmGroup.get("idItemByMesByDevengue")?.setValue(this.idMesByDevengue.value);
		this.frmGroup.get("idItemByRegimenPensionario")?.setValue(this.idRegimenPensionario.value);
		this.mostrarPanelForm = true;
		this.accionNuevo = true;
	}

	/**
	 * Inicializar.
	 *
	 */
	private inicializar() {
		try {
			this.cargarCombo();
			this.buscar();
		} catch (e) {
			this.mostrarMensajeError(e);
		}

	}

	/**
	 * eliminar.
	 *
	 */
	private eliminar(id: any) {
		try {
			this.service.eliminar(id).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.eliminoExito();
						this.buscar();
					}
				},
				error => {
					this.mostrarMensajeError(error);
				}
			);
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}

	/**
	 * confirmar eliminar.
	 *
	 */
	public confirmarEliminar(obj: ConceptoRegimenPensionario) {
		const dialogRef = this.abrirModalConfirDialogEliminar();
		dialogRef.afterClosed().subscribe(result => {
			if (result === 'confirmed') {
				this.eliminar(obj.idConceptoRegimenPensionario);
			}
		});
	}

	/**
	 * find id
	 *
	 */
	public find(obj: ConceptoRegimenPensionario) {
		try {
			this.frmGroup.patchValue(obj, { onlySelf: true, emitEvent: false });
			this.mostrarPanelForm = true;
			this.accionNuevo = false;
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}

	/**
	 * Buscar.
	 *
	 */
	public buscar() {
		this.isLoading = true;

		this.params = this.params.set('idAnhio', this.idAnhio.value + "");
		this.params = this.params.set('idItemByMesByDevengue', this.idMesByDevengue.value + "");
		this.params = this.params.set('idItemByRegimenPensionario', this.idRegimenPensionario.value + "");


		this.service.paginador(
			this.nvl(this._paginator?.pageIndex, 0),
			this.nvl(this._paginator?.pageSize, 10),
			this.nvl(this._sort?.active, this.SORT_NAME),
			this.nvl(this._sort?.direction, this.SORT_DIR),
			this.search, this.params)
			.subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						// Mark for check
						this.isLoading = false;
						this.mostrarPanelForm = false;
						this._changeDetectorRef.markForCheck();
					}
				},
				error => {
					this.isLoading = false;
					this.mostrarMensajeError(error);
				});
	}

	/**
	  * cancelar.
	  *
	*/
	public cancelar() {
		this.mostrarPanelForm = false;
	}
	public changeEmitterEvent(event: any) {
		const isNuevo = event.isNuevo;
		if (isNuevo === true) {
			this.nuevo();
		}
	}

	public onAnhioChange(ob: any) {
		this.idAnhio.setValue(ob.value);
		this.buscar();
	}
	public onMesByDevengeChange(ob: any) {
		this.idMesByDevengue.setValue(ob.value);
		this.buscar();
	}
	public onRegimenPensionarioChange(ob: any) {
		this.idRegimenPensionario.setValue(ob.value);
		this.buscar();
	}
}