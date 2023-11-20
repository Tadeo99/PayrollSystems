import {
	AfterViewInit,
	ChangeDetectionStrategy,
	ChangeDetectorRef,
	Component,
	OnDestroy,
	OnInit,
	ViewEncapsulation
} from '@angular/core';
import { UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { MatDialog } from '@angular/material/dialog';

import { bsAnimations } from '@ng-mf/shared/bs';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { CommonService, EstadoGeneralState, ListaItemType } from '@ng-mf/shared/service/comun';
import { BasePagination, BsConfirmationService, SelectItemVO, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { merge, takeUntil } from 'rxjs';
import { PeriodoPlanillaService } from "./periodoplanilla.service";
import { PeriodoPlanilla } from "./periodoplanilla.types";
/**
 * La Class PeriodoPlanillaComponent.
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
	selector: 'ng-mf-bs-periodoplanilla',
	templateUrl: './periodoplanilla.component.html',
	styleUrls: ['./periodoplanilla.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class PeriodoPlanillaComponent extends BaseComponent<PeriodoPlanilla> implements OnInit, AfterViewInit, OnDestroy {

	/** La lista anhio. */
	public listaAnhio: SelectItemVO[] | undefined = [];
	//public listaPeriodoMes: SelectItemVO[] | undefined = [];
	
	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		router: Router,
		route: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: PeriodoPlanillaService,
		public commonService: CommonService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService
	) {
		super(_translocoService, _bsConfirmationService);
		this.debounceTimeProcesar().subscribe(term => { this.search = term; this.buscar() });
		this.SORT_NAME = 'descripcion';
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
			idPeriodoPlanilla: [null],
			descripcion: [''],
			fechaInico: [null],
			fechaFina: [null],
			idItemByPeriodoMes: [null],
			idAnhio: [null],
			diasNetos: [null],
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
		//paramsTemp = paramsTemp.set(ListaItemType.TIPO_MESES, 0);
		//await this.commonService.obtenerListaItemSelectItemMap(paramsTemp);
		//this.listaPeriodoMes = this.commonService.getListaItemSelectItem(ListaItemType.TIPO_MESES);
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
				this.service.modificar(this.frmGroup.value, this.frmGroup.value.idPeriodoPlanilla).subscribe(
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
	public confirmarEliminar(obj: PeriodoPlanilla) {
		const dialogRef = this.abrirModalConfirDialogEliminar();
		dialogRef.afterClosed().subscribe(result => {
			if (result === 'confirmed') {
				this.eliminar(obj.idPeriodoPlanilla);
			}
		});
	}

	/**
	 * find id
	 *
	 */
	public find(obj: PeriodoPlanilla) {
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

}