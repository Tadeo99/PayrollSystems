import { AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit, ViewEncapsulation } from '@angular/core';
import { FormControl, UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';


import { MatDialog } from '@angular/material/dialog';

import { bsAnimations } from '@ng-mf/shared/bs';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { CommonService, EstadoGeneralState, ModuloContextoType } from '@ng-mf/shared/service/comun';
import {
	BasePagination,
	BsConfirmationService,
	BsLoadingInterceptor,
	SelectItemVO,
	TypeSelectItemService, WebsocketService
} from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { PlanillaService } from './planilla.service';
import { Planilla } from './planilla.types';
import { merge, takeUntil } from 'rxjs';
import { DetallePlanilla } from './detalleplanilla.type';

/**
 * La Class PlanillaComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:47 COT 2021
 * @since BUILDERP-CORE 2.1
 */

@Component({
	selector: 'ng-mf-bs-planilla',
	templateUrl: './planilla.component.html',
	styleUrls: ['./planilla.component.scss'],
	providers: [PlanillaService],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class PlanillaComponent extends BaseComponent<DetallePlanilla> implements OnInit, AfterViewInit, OnDestroy {

	idTipoPlanilla = new FormControl("");
	idPeriodoPlanilla = new FormControl("");
	idAnhio = new FormControl("");
	idCategoriaTrabajador = new FormControl("");
	private codigoSolicitudGenerado = "";
	private idAnhioActivo = "";
	private idItemByPeriodoMes?: number;

	public listaTipoPlanilla: SelectItemVO[] | undefined = [];
	public listaPeriodoPlanilla: SelectItemVO[] | undefined = [];
	public listaAnhio: SelectItemVO[] | undefined = [];
	public tipoPlanilaMap: Map<string, string> = new Map<string, string>();
	public periodoPlanilaMap: Map<string, string> = new Map<string, string>();

	public planilla: Planilla | null = null;
	private idPlanilla?: string;

	public disabled = false;

	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		router: Router,
		route: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: PlanillaService,
		public commonService: CommonService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService,
		private wsService: WebsocketService,
		private interceptor: BsLoadingInterceptor
	) {
		super(_translocoService, _bsConfirmationService);

		this.debounceTimeProcesar().subscribe(term => { this.search = term; this.buscar() });
		this.SORT_NAME = 'nombres';

		wsService.messages.subscribe(msg => {
			console.log("Response from websocket: " + msg);
			console.log("Response from websocket.codigoSolicitud: " + msg.codigoSolicitud);
			console.log("Response from websocket.content.length: " + msg.content.length);
			if (msg.codigoSolicitud === this.codigoSolicitudGenerado) {
				if (this.procesarWebSockect(msg, ModuloContextoType.RRHH_PLANILLA)) {
					this.buscar();
				}
				// Re-enable the form
				this.disabled = false;
				this.reglasFrm();
			}

		});
	}
	reglasFrm() {
		if (this.disabled === false) {
			this.frmGroup.enable();
			this.idTipoPlanilla.enable();
			this.idPeriodoPlanilla.enable();
			this.idAnhio.enable();
			this.idCategoriaTrabajador.enable();
		} else {
			this.frmGroup.disable();
			this.idTipoPlanilla.disable();
			this.idPeriodoPlanilla.disable();
			this.idAnhio.disable();
			this.idCategoriaTrabajador.disable();
		}
		this._changeDetectorRef.markForCheck();
	}
	ngAfterViewInit() {
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
			periodoPlanilla: this.fb.group({
				idPeriodoPlanilla: ['']
			}),
			idItemByTipoTrabajador: [null],
			idItemByPeriodoMes: [null],
			idAnhio: [null],
			tipoPlanilla: this.fb.group({
				idTipoPlanilla: [null]
			}),
			idPlanilla: [null],
			nombre: [null],
			tipoCalculo: [null],
			fechaPago: [null],
			fechaInicio: [null],
			fchaFin: [null],
			estado: [null]
		});
		this.onChange();
	}

	public async cargarCombo() {
		let paramsTemp: Map<any, any> = new Map<any, any>();

		paramsTemp = new Map<any, any>();
		paramsTemp = paramsTemp.set("anhio", 0);
		await this.commonService.obtenerListaSelectItemVOMap("common", paramsTemp);
		this.listaAnhio = this.commonService.getListaSelectVOItem("anhio");
		console.log("ACTIVO.toString() " + EstadoGeneralState.ACTIVO.toString())
		this.listaAnhio?.forEach((data) => {
			if (data.descripcion === EstadoGeneralState.ACTIVO.toString()) {
				this.idAnhioActivo = data.id;
			}
		});
		paramsTemp = new Map<any, any>();
		paramsTemp = paramsTemp.set("tipoPlanilla", 0);
		await this.commonService.obtenerListaSelectItemVOMap("rrhh_planilla", paramsTemp);
		this.listaTipoPlanilla = this.commonService.getListaSelectVOItem("tipoPlanilla");
		this.tipoPlanilaMap = this.commonService.generarMap(this.listaTipoPlanilla ?? [], true);
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


	public generarPlanilla() {
		if (this.frmGroup.invalid) {
			this.mostrarMensajeErrorFrmInvalid();
			return;
		}
		try {
			// Disable the form			
			this.disabled = true;
			this.reglasFrm();
			this.planilla = this.frmGroup.value;
			if (this.planilla != null) {
				this.planilla.idPlanilla = this.idPlanilla ?? '';
				this.planilla.idItemByPeriodoMes = this.idItemByPeriodoMes ?? 0;
				this.planilla.idAnhio = this.idAnhio.value ?? '';
				this.planilla.tipoPlanilla.idTipoPlanilla = this.idTipoPlanilla.value ?? '';
				this.planilla.tipoPlanilla.idItemByCategoriaTrabajador = Number(this.idCategoriaTrabajador.value);
				this.planilla.periodoPlanilla.idPeriodoPlanilla = this.idPeriodoPlanilla.value ?? '';
			}

			this.service.generarPlanilla(this.planilla).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.codigoSolicitudGenerado = data.objetoResultado;
						//dejar bloqueado
						this.disabled = true;
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
	 * Inicializar.
	 *
	 */
	private async inicializar() {
		try {
			this.cargarCombo();
			this.buscar();
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/**
		 * find id
		 *
		 */
	public find(obj: DetallePlanilla) {
		try {
			if (this.selectedData && this.selectedData.idDetallePlanilla === obj.idDetallePlanilla) {
				// Close the details
				this.closeDetails();
				return;
			}
			this.frmGroup.patchValue(obj, { onlySelf: true, emitEvent: false });
			this.selectedData = obj;
			this.accionNuevo = this.selectedData.idDetallePlanilla === null || this.selectedData.idDetallePlanilla === "";
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}

	/**
	 * Buscar.
	 *
	 */
	public buscar() {
		try {
			this.idPlanilla = "";
			this.mostrarPanelForm = false;

			//this.params = this.params.set('idEntidadSelect', this.idEntidad);
			this.params = this.params.set('idAnhio', this.idAnhio.value + "");
			this.params = this.params.set('idTipoPlanilla', this.idTipoPlanilla.value + "");
			this.params = this.params.set('idItemByPeriodoMes', this.idItemByPeriodoMes + "");
			this.params = this.params.set('idItemByCategoriaTrabajador', this.idCategoriaTrabajador.value ?? '');

			this.service.paginador(
				this.nvl(this._paginator?.pageIndex, 0),
				this.nvl(this._paginator?.pageSize, 10),
				this.nvl(this._sort?.active, this.SORT_NAME),
				this.nvl(this._sort?.direction, this.SORT_DIR),
				this.search, this.params)
				.subscribe(
					data => {
						if (this.isProcesoOK(data)) {
							const listaDetallePlanilla = data.listaResultado ?? [];
							if (!(listaDetallePlanilla === null || listaDetallePlanilla.length === 0)) {
								const objDet = listaDetallePlanilla[0];
								this.idPlanilla = objDet.planilla.idPlanilla;
							}
							this.isLoading = false;
							this.mostrarPanelForm = false;
							this._changeDetectorRef.markForCheck();
						}
					},
					error => {
						this.isLoading = false;
						this.mostrarMensajeError(error);
					}
				);
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}

	/**
	  * cancelar.
	  *
	*/
	public cancelar() {
		this.mostrarPanelForm = false;
		this.selectedData = null;
		this.planilla = null;
	}

	public changeEmitterEvent(event: any) {
		const isNuevo = event.isNuevo;
		if (isNuevo === true) {
			this.generarPlanilla();
		}
	}

	public onTipoPlanillaChange(ob: any) {
		this.idTipoPlanilla.setValue(ob.value);
		this.idCategoriaTrabajador.setValue(this.tipoPlanilaMap?.get(ob.value) ?? '');
		this.buscar();
	}

	public onPeriodoPlanillaChange(ob: any) {
		this.idPeriodoPlanilla.setValue(ob.value);
		this.idItemByPeriodoMes = Number(this.periodoPlanilaMap.get(ob.value));
		this.buscar();
	}
	public async onAnhioChange(ob: any) {
		this.idAnhio.setValue(ob.value);
		this.idPeriodoPlanilla.setValue("");
		let paramsTemp: Map<any, any> = new Map<any, any>();
		paramsTemp = paramsTemp.set("periodoPlanilla", 0);

		let paramsAdicional: Map<any, any> = new Map<any, any>();
		paramsAdicional = paramsAdicional.set("id", ob.value);
		this.commonService.listaSelectItemVOMap.delete("periodoPlanilla");
		await this.commonService.obtenerListaSelectItemVOMap("rrhh_planilla", paramsTemp, paramsAdicional);
		this.listaPeriodoPlanilla = this.commonService.getListaSelectVOItem("periodoPlanilla");
		this.periodoPlanilaMap = this.commonService.generarMap(this.listaPeriodoPlanilla ?? [], true);
		this._changeDetectorRef.markForCheck();
		this.buscar();
	}

	public descargarReportePlanillaBoletaIndividual(obj: DetallePlanilla) {
		this.service.generarReportePlanillaBoletaIndividual(obj).subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					this.codigoSolicitudGenerado = data.objetoResultado;
					//dejar bloqueado
					this.interceptor.handleRequestsAutomatically = false;
				}
			},
			error => {
				this.mostrarMensajeError(error);
			}
		);
	}

	public descargarReportePlanillaBoletaMasiva() {
		const objFiltro: any = { planilla: { idItemByPeriodoMes: this.idItemByPeriodoMes, idAnhio: this.idAnhio.value ?? '', tipoPlanilla: { idTipoPlanilla: this.idTipoPlanilla.value ?? '' } } };
		this.service.generarReportePlanillaBoletaMasiva(objFiltro).subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					this.codigoSolicitudGenerado = data.objetoResultado;
					//dejar bloqueado
					this.interceptor.handleRequestsAutomatically = false;
				}
			},
			error => {
				this.mostrarMensajeError(error);
			}
		);
	}
}