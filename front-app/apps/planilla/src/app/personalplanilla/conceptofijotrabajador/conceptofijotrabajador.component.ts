import {
	Component, OnInit, ChangeDetectorRef,
	AfterViewInit, OnDestroy, ViewEncapsulation,
	ChangeDetectionStrategy
} from '@angular/core';
import { FormControl, UntypedFormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

import { MatDialog } from '@angular/material/dialog';

import { ConceptoFijoTrabajadorService } from "./conceptofijotrabajador.service";
import { ConceptoFijoTrabajador } from "./conceptofijotrabajador.types";
import { TranslocoService } from '@ngneat/transloco';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { BasePagination, BsConfirmationService, SelectItemVO, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { CommonService, EstadoGeneralState, ListaItemType } from '@ng-mf/shared/service/comun';
import { merge, takeUntil } from 'rxjs';
import { bsAnimations } from '@ng-mf/shared/bs';
import { ConceptoPdtSelectItemComponent } from '../../conceptopdtselect';
/**
 * La Class ConceptoFijoTrabajadorComponent.
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
	selector: 'ng-mf-bs-conceptofijotrabajador',
	templateUrl: './conceptofijotrabajador.component.html',
	styleUrls: ['./conceptofijotrabajador.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class ConceptoFijoTrabajadorComponent extends BaseComponent<ConceptoFijoTrabajador> implements OnInit, AfterViewInit, OnDestroy {
	selected = new FormControl(0);
	/** La lista TipoContrato. */
	//public listaPeriodoMes: SelectItemVO[] | undefined = [];

	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		router: Router,
		route: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: ConceptoFijoTrabajadorService,
		public commonService: CommonService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService
	) {
		super(_translocoService, _bsConfirmationService);

		this.debounceTimeProcesar().subscribe(term => { this.search = term; this.buscar() });
		this.SORT_NAME = 'o.conceptoPdt.descripcion';
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
			idConceptoTrabajador: [null],
			conceptoPdt: this.fb.group({
				idConceptoPdt: [''],
				descripcion: [''],
			}),
			idItemByPeriodoMes: [null],
			descripcion: [null],
			porcentaje: [null],
			monto: [null],
			estado: [''],
		});
		this.onChange();
	}
	public async cargarCombo() {
		//let paramsTemp: Map<any, any> = new Map<any, any>();
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
				this.service.crearPer(this.id, this.frmGroup.value).subscribe(
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
				this.service.modificarPer(this.id, this.frmGroup.value, this.frmGroup.value.idConceptoTrabajador).subscribe(
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
			this.service.eliminarPer(this.id, id).subscribe(
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
	public confirmarEliminar(obj: ConceptoFijoTrabajador) {
		const dialogRef = this.abrirModalConfirDialogEliminar();
		dialogRef.afterClosed().subscribe(result => {
			if (result === 'confirmed') {
				this.eliminar(obj.idConceptoTrabajador);
			}
		});
	}

	/**
	 * find id
	 *
	 */
	public find(obj: ConceptoFijoTrabajador) {
		try {
			this.frmGroup.patchValue(obj, { onlySelf: true, emitEvent: false });
			this.mostrarPanelForm = true;
			this.accionNuevo = false;
			// Mark for check
			this._changeDetectorRef.markForCheck();
		} catch (e) {
			alert(e);
			this.mostrarMensajeError(e);
		}
	}

	/**
	 * Buscar.
	 *
	 */
	public buscar() {
		if (this.typeService.listaTipoPDT != null) {
			this.params = this.params.set("tipo", (this.typeService.listaTipoPDT[this.selected?.value ?? 0]?.id) + "");
		}
		this.isLoading = true;
		this.service.paginadorPer(this.id,
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

	public abrirModalConceptoPdt(pSearch?: string) {
		// Open the dialog
		const dialogRef = this.dialog.open(ConceptoPdtSelectItemComponent);
		dialogRef.componentInstance.titlePage = "Conceptos";
		dialogRef.componentInstance.search = pSearch ?? '';
		dialogRef.componentInstance.selected = this.selected;
		dialogRef.afterClosed()
			.subscribe((result) => {
				if (result !== null && result.idConceptoPdt && result.idConceptoPdt !== null) {
					this.frmGroup.get("conceptoPdt.idConceptoPdt")?.setValue(result.idConceptoPdt);
					this.frmGroup.get("conceptoPdt.descripcion")?.setValue(result.descripcion);
					this.frmGroup.get("descripcion")?.setValue(result.descripcion);
					this._changeDetectorRef.markForCheck();
				}
			});
	}

	public tabChanged = (tabChangeEvent: any): void => {
		this.titlePage = '';
		this.selectedData = null;
		this.buscar();
	}

}