import { Component, EventEmitter, OnInit, OnChanges, SimpleChanges, AfterViewInit, Optional } from '@angular/core';
import { FormControl, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

import { CommonServiceImpl } from "../../common/common.impl.service";
import { LoginService } from "../../seguridad/login/login.service";
import { TypeSelectItemService } from "../../../typeselectitemservice/typeselectitem.service";

import { BaseComponent, DialogConfirmContent, DialogContent } from "../../../base/base.component";
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { PlanPagosService } from "./planpagos.service";
import { PlanPagos } from "../../../models/pago/planpagos.model";
import { Anhio } from "../../../models/matricula/anhio.model";
import { Periodo } from "../../../models/matricula/periodo.model";
import { Alumno } from "../../../models/matricula/alumno.model";
import { BaseDialogContent } from '../../../base/base.dialog.content.component';
import { DetPlanPagos } from '../../../models/pago/detplanpagos.model';
import { MatriculaServiceImpl } from '../../matricula/matricula.impl.service';
import { EstadoGeneralState } from '../../../type/estadogeneral.state';


/**
 * La Class PlanPagosComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:53 COT 2021
 * @since BUILDERP-CORE 2.1
 */

@Component({
	selector: 'app-planpagos',
	templateUrl: './planpagos.component.html',
	styleUrls: ['./planpagos.component.css'],
	providers: [PlanPagosService, MatriculaServiceImpl]
})
export class PlanPagosComponent extends BaseComponent implements OnInit, OnChanges, AfterViewInit {

	/** El objeto plan pagos. */
	public planPagos: PlanPagos = new PlanPagos();

	/** La lista plan pagos. */
	public listaPlanPagos: PlanPagos[] = [];

	/** La lista item select. */
	public listaPlanPagosSelectMap: Map<string, PlanPagos> = new Map<string, PlanPagos>();

	public detPlanPagosList: DetPlanPagos[] = [];

	/*** El objeto anhio. */
	public anhioActivo: Anhio = new Anhio();

	public idNivel: number;

	constructor(private fb: FormBuilder, public dialog: MatDialog, public snackbar: MatSnackBar, router: Router, route: ActivatedRoute, private planPagosService: PlanPagosService,
		protected commonServiceImpl: CommonServiceImpl, protected loginDataService: LoginService, protected _typeSelectItemService: TypeSelectItemService,
		protected _translate: TranslateService, protected matriculaServiceImpl: MatriculaServiceImpl) {
		super(dialog, snackbar, router, route);
		super.setTypeSelectItemService(_typeSelectItemService);
		super.setTraslate(_translate);
		this.debounceTimeProcesar().subscribe(term => { this.search = term; this.buscar() });
		super.setLoginDataService(loginDataService);
	}

	ngAfterViewInit() {
		// viewChild is set after the view has been initialized
	}

	ngOnChanges(changes: SimpleChanges) {
		if (this.id) {
			this.params = this.params.set('id', this.id + '');
			this.buscar();
		}
		this.showAccion();
	}

	ngOnInit() {
		this.onInit();
		this.inicializar();
		this.crearFormulario(this.planPagos);
	}

	private crearFormulario(obj: PlanPagos): void {
		this.frmGroup = this.fb.group({
			anhio: this.fb.group({
				idAnhio: [obj.anhio.idAnhio],
				descripcionView: [obj.anhio.descripcionView, { updateOn: 'blur' }]
			}),
			periodo: this.fb.group({
				idPeriodo: [obj.periodo.idPeriodo],
				descripcionView: [obj.periodo.descripcionView, { updateOn: 'blur' }]
			}),
			alumno: this.fb.group({
				idAlumno: [obj.alumno.idAlumno, { validators: [Validators.required] }, { updateOn: 'blur' }],
				descripcionView: [obj.alumno.descripcionView, { validators: [Validators.required] }, { updateOn: 'blur' }]
			}),
			idPlanPagos: [obj.idPlanPagos],
			fechaCreacion: [obj.fechaCreacion],
			usuarioCreacion: [obj.usuarioCreacion],
			fechaModificacion: [obj.fechaModificacion],
			usuarioModificacion: [obj.usuarioModificacion],
		});
		this.onChange();
	}

	private onChange(): void {
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});

		this.frmGroup.get('anhio.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalAnhioInputanhio(value);
		});
		// this.frmGroup.get('periodo.descripcionView').valueChanges.subscribe(value => {
		// 	this.abrirModalPeriodoInputidPeriodo(value);
		// });
		// this.frmGroup.get('alumno.descripcionView').valueChanges.subscribe(value => {
		// 	this.abrirModalAlumnoInputalumno(value);
		// });
	}


	onInit() {
		/*var id = this.route.params.subscribe(params => {
		  var id = params['id'];
	
		});*/
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
				this.planPagosService.crear(this.frmGroup.value).subscribe(
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
				this.planPagosService.modificar(this.frmGroup.value,this.frmGroup.value.idPlanPagos).subscribe(
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
		this.planPagos = new PlanPagos();
		this.frmGroup.patchValue(this.planPagos, { onlySelf: true, emitEvent: false });
		this.frmGroup.get('anhio').patchValue(this.anhioActivo, { onlySelf: true, emitEvent: false });
		this.mostrarPanelForm = true;
		this.accionNuevo = true;
	}

	/**
	 * Inicializar.
	 *
	 */
	private async inicializar() {
		super.validarPaginaView();
		super.getUsuarioSession();
		this.anhioActivo = await this.commonServiceImpl.obtenerAnhioyEstadoAsync(EstadoGeneralState.ACTIVO);
		this.anhioActivo.descripcionView = this.obtenerDescripcionAnhio(this.anhioActivo);
		this.limpiar();
	}

	/**
	 * Limpiar.
	 *
	 */
	private limpiar() {
		try {
			this.listaPlanPagos = [];
			this.limpiaDataProvider(this.search);
			this.planPagos = new PlanPagos();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.planPagos, { onlySelf: true, emitEvent: false });
				this.frmGroup.get('anhio').patchValue(this.anhioActivo, { onlySelf: true, emitEvent: false });
			}
			//this.buscar();
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
			this.planPagosService.eliminar(id).subscribe(
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
	public confirmarEliminar(planPagosTemp: PlanPagos) {
		this.planPagos = planPagosTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				this.eliminar(this.planPagos.idPlanPagos);
			}
		});
	}

	/**
	 * buscar id
	 *
	 */
	public buscarID(planPagosTemp: PlanPagos) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.planPagos = Object.assign({}, planPagosTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.planPagos = Object.assign({}, planPagosTemp);
					this.lanzarPlanPagos();
				}
			}
			this.frmGroup.patchValue(this.planPagos, { onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(planPagosTemp: PlanPagos) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				planPagosTemp.checked = true;
				this.agregarCheck(planPagosTemp);
				this.dialogRef.close(this.listaPlanPagosSelectMap);
			}
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
			this.listaPlanPagos = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.planPagosService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.setDataProvider(data);
						this.listaPlanPagos = data.listaResultado;
						this.asociarData();
						this.mostrarPanelForm = false;
						this.noEncontroRegistoAlmanecado(this.dataProvider);
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

	private asociarData(): void {
		if (this.id != null && this.id != '' && this.listaPlanPagos.length == 1) {
			this.asociar(this.listaPlanPagos[0]);
		}
	}

	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			this.calcularStartRow();
			this.planPagosService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaPlanPagos = data.listaResultado;
						this.asociarData();
						if (this.showSelectMultiple) {
							this.verificarCheck();
						}
					}
				},
				error => {
					this.mostrarMensajeError(error);
				}
			);
		}
	}

	private obtenerPlanaPagos() {
		this.startProgres();
		this.planPagosService.obtenerPlanPagosByIdAlumno(this.frmGroup.value).subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					this.planPagos = data.objetoResultado;
					if (this.planPagos == null) {
						this.planPagos = new PlanPagos();
					}
				}
			},
			error => {
				this.mostrarMensajeError(error);
			},
			() => {
				this.endProgres();
				if (this.planPagos.idPlanPagos == null) {
					this.planPagos.anhio = this.frmGroup.get('anhio').value;
					this.planPagos.alumno = this.frmGroup.get('alumno').value;
					this.planPagos.periodo = this.frmGroup.get('periodo').value;
				}


			}
		);

	}

	/**
	   * getBufferedData.
	   *
	 */
	public getBufferedData(event : any) {
		this.dataProvider = event.dataProvider;
		this.buscarPaginado();
	}

	/**
	  * cancelar.
	  *
	*/
	public cancelar() {
		this.mostrarPanelForm = false;
	}

	private lanzarPlanPagos() {
		// Usamos el mÃ©todo emit
		this.change.emit({ planPagos: this.planPagos });
	}

	/*
	agregar check
	*/
	public agregarCheck(planPagosTemp: PlanPagos) {
		if (planPagosTemp.checked) {
			if (!this.listaPlanPagosSelectMap.has(planPagosTemp.idPlanPagos)) {
				this.listaPlanPagosSelectMap.set(planPagosTemp.idPlanPagos, planPagosTemp);
			}
		} else {
			if ((this.listaPlanPagosSelectMap.has(planPagosTemp.idPlanPagos))) {
				this.listaPlanPagosSelectMap.delete(planPagosTemp.idPlanPagos);
			}
		}
	}
	/*
	 verificar check
	*/
	private verificarCheck() {
		this.listaPlanPagos.forEach((data) => {
			if ((this.listaPlanPagosSelectMap.has(data.idPlanPagos))) {
				data.checked = true;
			}
		});
	}

	/**
	Abrir modal Anhio input
	*/
	public abrirModalAnhioInputanhio(pSearch?: string) {
		this.frmGroup.get('anhio.idAnhio').setValue(null);
		this.abrirModalAnhioanhio(pSearch);
	}

	/**
	  El abrir modal anhio. 
	*/
	public abrirModalAnhioanhio(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Anhio = new Anhio();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalAnhio = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.idAnhio + ' ' + entryVal.nombre;
					this.frmGroup.get('anhio.idAnhio').patchValue(entryVal.idAnhio);
					this.frmGroup.get('anhio').patchValue(entryVal, { onlySelf: true, emitEvent: false });
					if (this.frmGroup.get('alumno.idAlumno').value != null) {
						this.obtenerPlanaPagos();
					}
				});
			}
		});
	}
	/**
	Abrir modal Periodo input
	*/
	public abrirModalPeriodoInputidPeriodo(pSearch?: string) {
		this.frmGroup.get('periodo.idPeriodo').setValue(null);
		this.abrirModalPeriodoidPeriodo(pSearch);
	}

	/**
	  El abrir modal periodo. 
	*/
	public abrirModalPeriodoidPeriodo(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Periodo = new Periodo();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalPeriodo = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.idPeriodo + ' ' + entryVal.descripcion;
					this.frmGroup.get('periodo.idPeriodo').patchValue(entryVal.idPeriodo);
					this.frmGroup.get('periodo').patchValue(entryVal, { onlySelf: true, emitEvent: false });
					if (this.frmGroup.get('alumno.idAlumno').value != null) {
						this.obtenerPlanaPagos();
					}
				});
			}
		});
	}
	/**
	Abrir modal Alumno input
	*/
	public abrirModalAlumnoInputalumno(pSearch?: string) {
		this.frmGroup.get('alumno.idAlumno').setValue(null);
		this.abrirModalAlumnoalumno(pSearch);
	}

	/**
	  El abrir modal alumno. 
	*/
	public abrirModalAlumnoalumno(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Alumno = new Alumno();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalAlumnoSelect = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.nombres + ' ' + entryVal.apellidoPaterno + ' ' + entryVal.apellidoMaterno + " - " + entryVal.grado.itemByNivel.nombre + " " + entryVal.grado.nombre;
					this.idNivel = entryVal.grado.itemByNivel.idItem;
					this.frmGroup.get('alumno.idAlumno').patchValue(entryVal.idAlumno);
					this.frmGroup.get('alumno').patchValue(entryVal, { onlySelf: false, emitEvent: false });
					this.obtenerPlanaPagos();
				});
			}
		});
	}

	/**
	 * listarDetPago
	 */
	public listarDetPago(event) {
		let isBuscar = event.isBuscar;
		if (isBuscar) {
			this.obtenerPlanaPagos();
		}
	}

}
@Component({
	template: `
	<app-cuotaconcepto *ngIf="esModalCuotaConcepto"  [modulo] = "modulo" [id] = "id" [esIncludeComponent] = "esIncludeComponent"  [showModal]="showModal" [showSelectMultiple] = "showSelectMultiple" [dialogRef] = "dialogRef" [data] = "data"></app-cuotaconcepto>
	 `
})
export class DialogContentOverride extends BaseDialogContent {
	public esModalCuotaConcepto: boolean = false;
	constructor(@Optional() public dialogRef: MatDialogRef<DialogContentOverride>) { super() }
}