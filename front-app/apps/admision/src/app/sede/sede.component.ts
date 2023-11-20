import {
	Component, OnInit, ChangeDetectorRef,
	AfterViewInit, OnDestroy, ViewEncapsulation,
	ChangeDetectionStrategy
} from '@angular/core';
import { FormControl, UntypedFormArray, UntypedFormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

import { MatDialog } from '@angular/material/dialog';

import { SedeService } from "./sede.service";
import { Sede } from "./sede.types";
import { TranslocoService } from '@ngneat/transloco';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { BasePagination, BsConfirmationService, SelectItemVO, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { CommonService, EstadoGeneralState, ListaItemType } from '@ng-mf/shared/service/comun';
import { merge, takeUntil } from 'rxjs';
import { bsAnimations } from '@ng-mf/shared/bs';
import { MatButtonToggleChange } from '@angular/material/button-toggle';
import { DetSede } from './detsede.types';
/**
 * La Class SedeComponent.
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
	selector: 'ng-mf-bs-sede',
	templateUrl: './sede.component.html',
	styleUrls: ['./sede.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class SedeComponent extends BaseComponent<Sede> implements OnInit, AfterViewInit, OnDestroy {

	/** La lista nivel. */
	public listaDepartamento: SelectItemVO[] | undefined = [];
	public listaProvincia: SelectItemVO[] | undefined = [];
	public listaDistrito: SelectItemVO[] | undefined = [];

	public listaGradoMap: Map<number, DetSede[]> = new Map<number, DetSede[]>();
	public listaGradoAll: SelectItemVO[] | undefined = [];

	selected = new FormControl(0);

	/** La lista nivel. */
	public listaNivel: SelectItemVO[] | undefined = [];
	public listaVacanetes: DetSede[] = [];
	public listaVacanetesMap: Map<number, DetSede> = new Map<number, DetSede>();

	selectedFilter: SelectItemVO | undefined;

	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		router: Router,
		route: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: SedeService,
		public commonService: CommonService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService
	) {
		super(_translocoService, _bsConfirmationService);
		this.debounceTimeProcesar().subscribe(term => { this.search = term; this.buscar() });
		this.SORT_NAME = 'nombre';
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

	vacanteChanged(objData: any, arg: any) {
		objData.nroVacante = arg.target.value;
		this.listaVacanetesMap.set(objData.grado.idGrado, objData);
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
		//this.params = this.params.set("id", this.selectedFilter?.id + "");
		///this.buscar();
	}

	private crearFormulario(): void {
		this.frmGroup = this.fb.group({
			idSede: [0],
			idDepartamento: [''],
			idProvincia: [''],
			idUbigeo: [''],
			codigo: [''],
			nombre: [''],
			direccion: [''],
			coordenadaDireccion: [''],
			estado: [''],
			usuarioCreacion: [''],
			fechaCreacion: [null],
			usuarioModificacion: [''],
			fechaModificacion: [null],
			sedeDetSedeList: this.fb.array([]),
		});
		this.onChange();
	}

	public async cargarCombo() {
		let paramsTemp: Map<any, any> = new Map<any, any>();
		paramsTemp = paramsTemp.set("departamento", 0);
		paramsTemp = paramsTemp.set("provincia", 0);
		paramsTemp = paramsTemp.set("distrito", 0);
		await this.commonService.obtenerListaSelectItemVOMap("common", paramsTemp);
		this.listaDepartamento = this.commonService.getListaSelectVOItem("departamento");
		this.commonService.generarUbigeoMap();

		paramsTemp = new Map<any, any>();
		paramsTemp = paramsTemp.set(ListaItemType.T38_NIVEL, 0);
		//paramsTemp = paramsTemp.set(ListaItemType.TURNO,0);	
		await this.commonService.obtenerListaItemSelectItemMap(paramsTemp);
		this.listaNivel = this.commonService.getListaItemSelectItem(ListaItemType.T38_NIVEL);


		paramsTemp = new Map<any, any>();
		paramsTemp = paramsTemp.set("grado", 0);
		await this.commonService.obtenerListaSelectItemVOMap(this.service.modulo, paramsTemp);
		this.listaGradoAll = this.commonService.getListaSelectVOItem("grado");


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
			this.generarVacanteFrm();
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
				this.service.modificar(this.frmGroup.value, this.frmGroup.value.idSede).subscribe(
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
		this.listaVacanetes = [];
		this.crearFormulario();
		this.frmGroup.get("estado")?.setValue(EstadoGeneralState.ACTIVO.toString());
		this.mostrarPanelForm = true;
		this.accionNuevo = true;
		this.generarVacante();
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
	public confirmarEliminar(obj: Sede) {
		const dialogRef = this.abrirModalConfirDialogEliminar();
		dialogRef.afterClosed().subscribe(result => {
			if (result === 'confirmed') {
				this.eliminar(obj.idSede);
			}
		});
	}

	/**
	 * find id
	 *
	 */
	public find(obj: Sede) {
		try {
			this.listaVacanetes = [];
			this.service.buscarID(obj.idSede).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.frmGroup.patchValue(obj, { onlySelf: true, emitEvent: false });

						this.frmGroup.get('idProvincia')?.setValue(obj.ubigeo?.ubigeoByDependencia?.idUbigeo);
						this.frmGroup.get('idDepartamento')?.setValue(this.commonService.ubigeoMap.get(obj.ubigeo?.ubigeoByDependencia?.idUbigeo));

						this.listaProvincia = this.commonService.generarProvincia(this.frmGroup?.get('idDepartamento')?.value);
						this.listaDistrito = this.commonService.generarDistrito(obj.ubigeo?.ubigeoByDependencia?.idUbigeo);

						this.mostrarPanelForm = true;
						this.accionNuevo = false;
						this.listaVacanetes = data.objetoResultado.sedeDetSedeList;
						this.generarVacante();
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
	private generarVacanteFrm() {
		this.listaVacanetes = [];
		this.listaVacanetesMap?.forEach((value, key) => {
			if (value.nroVacante > 0) {
				this.listaVacanetes.push(value);
				// Create an empty phone number form group
				const vacanteFrm = this.fb.group({
					idDetSede: [''],
					grado: this.fb.group({
						idGrado: [value.grado.idGrado]
					}),
					nroVacante: [value.nroVacante],
					estado: ['A'],
				});
				// Add the phone number form group to the phoneNumbers form array
				(this.frmGroup.get('sedeDetSedeList') as UntypedFormArray).push(vacanteFrm);
			}

		});

		// Mark for check
		this._changeDetectorRef.markForCheck();
	}
	private generarVacante() {
		this.listaGradoMap = new Map<number, DetSede[]>();
		this.listaVacanetesMap = new Map<number, DetSede>();
		this.listaVacanetes?.forEach((data) => {
			this.listaVacanetesMap.set(data.grado.idGrado, data);
		});
		this.listaGradoAll?.forEach((data) => {
			const key = parseInt(data.descripcion);
			if (!this.listaGradoMap.has(key)) {
				let nroVacanteTmp = 0;
				if (this.listaVacanetesMap.has(data.id)) {
					nroVacanteTmp = this.listaVacanetesMap?.get(data.id)?.nroVacante ?? 0;
				}
				const value: DetSede[] = [];
				const vacante: DetSede = {
					idDetSede: '',
					grado: { idGrado: data.id, descripcionView: data.nombre, idItemByNivel: 0 },
					nroVacante: nroVacanteTmp, estado: 'A'
				}
				value.push(vacante);
				this.listaGradoMap.set(key, value);
			} else {
				const value = this.listaGradoMap.get(key);
				if (value) {
					let nroVacanteTmp = 0;
					if (this.listaVacanetesMap.has(data.id)) {
						if (this.listaVacanetesMap?.get(data.id)?.nroVacante) {
							nroVacanteTmp = this.listaVacanetesMap?.get(data.id)?.nroVacante ?? 0;
						}
					}
					const vacante: DetSede = {
						idDetSede: '',
						grado: { idGrado: data.id, descripcionView: data.nombre, idItemByNivel: 0 },
						nroVacante: nroVacanteTmp, estado: 'A'
					}
					value?.push(vacante);
					this.listaGradoMap.set(key, value);
				}
			}

		});
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
		//
	}

	public onDepartamentoChange(ob: any) {
		const selected = ob.value;
		this.listaProvincia = this.commonService.generarProvincia(selected);
	}

	public onProvinciaChange(ob: any) {
		const selected = ob.value;
		this.listaDistrito = this.commonService.generarDistrito(selected);
	}

}