import {
	Component, OnInit, ChangeDetectorRef,
	AfterViewInit, OnDestroy, ViewEncapsulation,
	ChangeDetectionStrategy
} from '@angular/core';
import { FormControl, UntypedFormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

import { MatDialog } from '@angular/material/dialog';

import { SeccionService } from "./seccion.service";
import { Seccion } from "./seccion.types";
import { TranslocoService } from '@ngneat/transloco';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { BasePagination, BsConfirmationService, SelectItemVO, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { CommonService, EstadoGeneralState, ListaItemType } from '@ng-mf/shared/service/comun';
import { merge, takeUntil } from 'rxjs';
import { bsAnimations } from '@ng-mf/shared/bs';
import { MatButtonToggleChange } from '@angular/material/button-toggle';
/**
 * La Class SeccionComponent.
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
	selector: 'ng-mf-bs-seccion',
	templateUrl: './seccion.component.html',
	styleUrls: ['./seccion.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class SeccionComponent extends BaseComponent<Seccion> implements OnInit, AfterViewInit, OnDestroy {

	/** La lista nivel. */
	public listaNivel: SelectItemVO[] | undefined = [];
	selected = new FormControl(0);
	public listaGrado: SelectItemVO[] | undefined = [];
	selectedFilter: SelectItemVO | undefined;
	public idNivel = 0;

	public listaGradoMap: Map<number, SelectItemVO[]> = new Map<number, SelectItemVO[]>();
	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		router: Router,
		route: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: SeccionService,
		public commonService: CommonService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService
	) {
		super(_translocoService, _bsConfirmationService);
		this.debounceTimeProcesar().subscribe(term => { this.search = term; this.buscar() });
		this.SORT_NAME = 'grado.idGrado';
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
	/**
		* On filter change
		*
		* @param change
		*/
	onFilterChange(toggleChange: MatButtonToggleChange): void {
		// Set the filter
		this.selectedFilter = toggleChange.value;
		this.params = this.params.set("id", this.selectedFilter?.id + "");
		this.buscar();
	}

	private crearFormulario(): void {
		this.frmGroup = this.fb.group({
			idSeccion: [0],
			grado: this.fb.group({
				idGrado: [0],
			}),
			codigo: [''],
			nombre: [''],
			estado: [''],
		});
		this.onChange();
	}

	public async cargarCombo() {
		let paramsTemp: Map<any, any> = new Map<any, any>();
		paramsTemp = paramsTemp.set(ListaItemType.T38_NIVEL, 0);
		//paramsTemp = paramsTemp.set(ListaItemType.TURNO,0);	
		await this.commonService.obtenerListaItemSelectItemMap(paramsTemp);
		this.listaNivel = this.commonService.getListaItemSelectItem(ListaItemType.T38_NIVEL);


		paramsTemp = new Map<any, any>();
		paramsTemp = paramsTemp.set("grado", 0);
		await this.commonService.obtenerListaSelectItemVOMap(this.service.modulo, paramsTemp);
		const listaGradoAll = this.commonService.getListaSelectVOItem("grado");

		listaGradoAll?.forEach((data) => {
			const key = parseInt(data.descripcion);
			if (!this.listaGradoMap.has(key)) {
				const value: SelectItemVO[] = [];
				value.push({ id: 0, nombre: "All", descripcion: "", checked: false });
				value.push(data);
				this.listaGradoMap.set(key, value);
			} else {
				const value = this.listaGradoMap.get(key);
				if (value) {
					value?.push(data);
					this.listaGradoMap.set(key, value);
				}
			}

		});
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
				this.service.modificar(this.frmGroup.value, this.frmGroup.value.idSeccion).subscribe(
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
		if (this.listaGradoMap != null) {
			this.frmGroup.get('gradoo.idGrado')?.setValue(this.selectedFilter?.id);
		}
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
	public confirmarEliminar(obj: Seccion) {
		const dialogRef = this.abrirModalConfirDialogEliminar();
		dialogRef.afterClosed().subscribe(result => {
			if (result === 'confirmed') {
				this.eliminar(obj.idSeccion);
			}
		});
	}

	/**
	 * find id
	 *
	 */
	public find(obj: Seccion) {
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

	public tabChanged = (tabChangeEvent: any): void => {
		this.params = this.params.set("id", "");
		if (this.listaNivel != null) {
			this.idNivel = (this.listaNivel[this.selected?.value ?? 0]?.id);
			this.params = this.params.set("idNivel", this.idNivel + "");
		}
		this.listaGrado = [];
		if (this.listaGradoMap.has(this.idNivel)) {
			const listaGradoTmp = this.listaGradoMap.get(this.idNivel);
			listaGradoTmp?.forEach((data) => {
				if (data.id > 0) {
					this.listaGrado?.push(data);
				}
			});
		}
		this.buscar();
	}

}