import { Component, EventEmitter, OnInit, OnChanges, SimpleChanges, AfterViewInit, Input } from '@angular/core';
import { FormControl, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

import { CommonServiceImpl } from "../../common/common.impl.service";
import { LoginService } from "../../seguridad/login/login.service";
import { TypeSelectItemService } from "../../../typeselectitemservice/typeselectitem.service";

import { BaseComponent, DialogConfirmContent, DialogContent } from "../../../base/base.component";
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { DetPlanPagosService } from "./detplanpagos.service";
import { DetPlanPagos } from "../../../models/pago/detplanpagos.model";
import { PlanPagos } from "../../../models/pago/planpagos.model";
import { CuotaConcepto } from "../../../models/pago/cuotaconcepto.model";
import { DialogContentOverride, PlanPagosService } from '../planpagos';


/**
 * La Class DetPlanPagosComponent.
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
	selector: 'app-detplanpagos',
	templateUrl: './detplanpagos.component.html',
	styleUrls: ['./detplanpagos.component.css'],
	providers: [DetPlanPagosService, PlanPagosService]
})
export class DetPlanPagosComponent extends BaseComponent implements OnInit, OnChanges, AfterViewInit {

	/** El objeto det plan pagos. */
	public detPlanPagos: DetPlanPagos = new DetPlanPagos();

	public detPlanPagosValidar: DetPlanPagos = new DetPlanPagos();

	public planPagos: PlanPagos = new PlanPagos();

	/** La lista det plan pagos. */
	@Input("listaDetPlanPagosTemp")
	public listaDetPlanPagosTemp: DetPlanPagos[] = [];

	public listaDetPlanPagos: DetPlanPagos[] = [];

	/** La lista item select. */
	public listaDetPlanPagosSelectMap: Map<string, DetPlanPagos> = new Map<string, DetPlanPagos>();

	@Input("btonAdd")
	public btonAdd: boolean;

	@Input("idNivel")
	public idNivel: number;

	public autoTicks: boolean = false;
	public showTicks: boolean = true;
	public step: number = 1;
	public thumbLabel: boolean = true
	//public nroFraccionamiento : number = 0;
	public nroFraccionamiento = new FormControl(0);

	public minFraccionamiento: number = 0;

	public maxFraccionamiento: number = 0;

	constructor(private fb: FormBuilder, public dialog: MatDialog, public snackbar: MatSnackBar, router: Router, route: ActivatedRoute, private detPlanPagosService: DetPlanPagosService,
		protected commonServiceImpl: CommonServiceImpl, private planPagosService: PlanPagosService, protected loginDataService: LoginService, protected _typeSelectItemService: TypeSelectItemService, protected _translate: TranslateService) {
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
		if (this.data) {
			console.log(this.data)
			this.buscar();
		}
		this.showAccion();
	}

	ngOnInit() {
		this.onInit();
		//this.inicializar();			
		this.crearFormulario(this.detPlanPagos);
	}

	private crearFormulario(obj: DetPlanPagos): void {
		this.frmGroup = this.fb.group({
			planPagos: this.fb.group({
				idPlanPagos: [obj.planPagos.idPlanPagos],
				descripcionView: [obj.planPagos.descripcionView, { updateOn: 'blur' }]
			}),
			cuotaConcepto: this.fb.group({
				idCuotaConcepto: [obj.cuotaConcepto.idCuotaConcepto, { validators: [Validators.required] }],
				descripcionView: [obj.cuotaConcepto.descripcionView, { validators: [Validators.required] }, { updateOn: 'blur' }]
			}),
			idDetPlanPagos: [obj.idDetPlanPagos],
			cuota: [obj.cuota],
			subTotal: [obj.subTotal],
			igv: [obj.igv],
			aplicaIgv: [obj.aplicaIgv],
			montoResta: [obj.montoResta],
			fechaVencimiento: [obj.fechaVencimiento],
			flagFraccionado: [obj.flagFraccionado],
		});
		this.onChange();
	}

	private onChange(): void {
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});

		this.frmGroup.get('planPagos.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalPlanPagosInputplanPagos(value);
		});
		this.frmGroup.get('cuotaConcepto.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalCuotaConceptoInputcuotaConcepto(value);
		});
	}


	onInit() {
		/*var id = this.route.params.subscribe(params => {
		  var id = params['id'];
	
		});*/
	}
	get tickInterval(): number | 'auto' {
		return this.showTicks ? (this.autoTicks ? 'auto' : this._tickInterval) : 0;
	}
	set tickInterval(v) {
		this._tickInterval = Number(v);
	}
	public _tickInterval = 1;

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
				this.planPagos = this.data;
				this.planPagos.planPagosDetPlanPagosList = this.listaDetPlanPagosTemp;
				this.planPagosService.crear(this.planPagos).subscribe(
					data => {
						if (this.isProcesoOK(data)) {
							this.guardoExito();
							this.change.emit({ isBuscar: true });
						}
					},
					error => {
						this.mostrarMensajeError(error);
					}
				);
			} else {
				this.planPagosService.modificar(this.frmGroup.value,this.frmGroup.value.idDetPlanPagos).subscribe(
					data => {
						if (this.isProcesoOK(data)) {
							this.actualizadoExito();
							this.change.emit({ isBuscar: true });
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
		this.detPlanPagos = new DetPlanPagos();
		this.frmGroup.patchValue(this.detPlanPagos, { onlySelf: true, emitEvent: false });
		this.mostrarPanelForm = true;
		this.accionNuevo = true;
		this.listaDetPlanPagosTemp = [];
		this.minFraccionamiento = 0;
		this.maxFraccionamiento = 0;
		this.nroFraccionamiento.setValue(0);
		this.planPagos = new PlanPagos();
		this.validarCampo = true;
	}

	/**
	 * Inicializar.
	 *
	 */
	private inicializar() {
		super.validarPaginaView();
		super.getUsuarioSession();
		this.limpiar();
	}

	/**
	 * Limpiar.
	 *
	 */
	private limpiar() {
		try {
			this.listaDetPlanPagos = [];
			this.limpiaDataProvider(this.search);
			this.detPlanPagos = new DetPlanPagos();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.detPlanPagos, { onlySelf: true, emitEvent: false });
			}
			this.buscar();
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}

	/**
	 * eliminar.
	 *
	 */
	private eliminar(id: DetPlanPagos) {
		try {
			this.detPlanPagosService.eliminar(id.idDetPlanPagos).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						if(id.esLista !='S'){
							this.eliminoExito();
							this.buscar(); 
						}
					}
				},
				error => {
					if(id.esLista !='S'){
						this.mostrarMensajeError(error);
					}
				}
			);
		} catch (e) {
			if(id.esLista !='S'){
				this.mostrarMensajeError(e);
			}
		}
	}

	/**
	 * confirmar eliminar.
	 *
	 */
	public confirmarEliminar(detPlanPagosTemp: DetPlanPagos) {
		this.detPlanPagos = detPlanPagosTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				try {
					if(detPlanPagosTemp.esLista == "S"){
						detPlanPagosTemp.detPlanPagosDTOList.forEach((temp)=>{
							temp.esLista = detPlanPagosTemp.esLista;
							this.eliminar(temp);
						})
						this.eliminoExito();
						this.buscar(); 
					}else{
						this.eliminar(this.detPlanPagos);
					}
				} catch (e) {
					this.mostrarMensajeError(e);
				}

			}
		});
	}

	/**
	 * buscar id
	 *
	 */
	public buscarID(detPlanPagosTemp: DetPlanPagos) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.detPlanPagos = Object.assign({}, detPlanPagosTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
				this.validarCampo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.detPlanPagos = Object.assign({}, detPlanPagosTemp);
					this.lanzarDetPlanPagos();
				}
			}
			this.frmGroup.patchValue(this.detPlanPagos, { onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}


	openOpcion(opcion: any) {
		// console.log("openOpcion.id==> " + opcion.id);
		//console.log("openOpcion.checked==> " + opcion.checked);
		opcion.checked = !opcion.checked;
		//console.log("openOpcion.checked.new==> " + opcion.checked); 
	}
	/* 
	asociar 
	*/
	public asociar(detPlanPagosTemp: DetPlanPagos) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				detPlanPagosTemp.checked = true;
				this.agregarCheck(detPlanPagosTemp);
				this.dialogRef.close(this.listaDetPlanPagosSelectMap);
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
			this.listaDetPlanPagos = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.params = this.params.set('id', this.data.idPlanPagos + '');
			// this.params = this.params.set('idPadreView', this.data.alumno.grado.itemByNivel.idItem + '');
			this.detPlanPagosService.paginador(this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.setDataProvider(data);
						this.listaDetPlanPagos = data.listaResultado;
						this.asociarData();
						this.mostrarPanelForm = false;
						this.noEncontroRegistoAlmanecado(this.dataProvider);
						if (this.listaDetPlanPagos) {
							this.listasize = this.listaDetPlanPagos.length;
						}
					}
				},
				error => {
					this.mostrarMensajeError(error + " -- ");
				}
			);
		} catch (e) {
			this.mostrarMensajeError(e + " -- 3");
		}
	}

	private asociarData(): void {
		if (this.id != null && this.id != '' && this.listaDetPlanPagos.length == 1) {
			this.asociar(this.listaDetPlanPagos[0]);
		}
	}

	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			this.calcularStartRow();
			this.detPlanPagosService.paginador(this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaDetPlanPagos = data.listaResultado;
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

	private lanzarDetPlanPagos() {
		// Usamos el mÃ©todo emit
		this.change.emit({ detPlanPagos: this.detPlanPagos });
	}

	/*
	agregar check
	*/
	public agregarCheck(detPlanPagosTemp: DetPlanPagos) {
		if (detPlanPagosTemp.checked) {
			if (!this.listaDetPlanPagosSelectMap.has(detPlanPagosTemp.idDetPlanPagos)) {
				this.listaDetPlanPagosSelectMap.set(detPlanPagosTemp.idDetPlanPagos, detPlanPagosTemp);
			}
		} else {
			if ((this.listaDetPlanPagosSelectMap.has(detPlanPagosTemp.idDetPlanPagos))) {
				this.listaDetPlanPagosSelectMap.delete(detPlanPagosTemp.idDetPlanPagos);
			}
		}
	}
	/*
	 verificar check
	*/
	private verificarCheck() {
		this.listaDetPlanPagos.forEach((data) => {
			if ((this.listaDetPlanPagosSelectMap.has(data.idDetPlanPagos))) {
				data.checked = true;
			}
		});
	}

	/**
	Abrir modal PlanPagos input
	*/
	public abrirModalPlanPagosInputplanPagos(pSearch?: string) {
		this.frmGroup.get('planPagos.idPlanPagos').setValue(null);
		this.abrirModalPlanPagosplanPagos(pSearch);
	}

	/**
	  El abrir modal plan pagos. 
	*/
	public abrirModalPlanPagosplanPagos(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: PlanPagos = new PlanPagos();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		//dialogRef.componentInstance.esModalPlanPagos = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.idPlanPagos;//   + ' ' +  entryVal.nombre;			
					this.frmGroup.get('planPagos').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}
	/**
	Abrir modal CuotaConcepto input
	*/
	public abrirModalCuotaConceptoInputcuotaConcepto(pSearch?: string) {
		this.frmGroup.get('cuotaConcepto.idCuotaConcepto').setValue(null);
		this.abrirModalCuotaConceptocuotaConcepto(pSearch);
	}

	/**
	  El abrir modal cuota concepto. 
	*/
	public abrirModalCuotaConceptocuotaConcepto(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContentOverride);
		let data: CuotaConcepto = new CuotaConcepto();
		data.id = this.idNivel;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalCuotaConcepto = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.catalogoCuenta.cuenta + ' ' + entryVal.monto;
					this.minFraccionamiento = entryVal.nroMinFraccionamiento;
					this.maxFraccionamiento = entryVal.nroMaxFraccionamiento;
					this.frmGroup.get('cuota').setValue(entryVal.monto);
					this.frmGroup.get('fechaVencimiento').setValue(entryVal.fechaTentativa);
					this.frmGroup.get('aplicaIgv').setValue(entryVal.aplicaIgv);
					this.frmGroup.get('cuotaConcepto').patchValue(entryVal, { onlySelf: false, emitEvent: false });
					this.listaDetPlanPagosTemp = [];
					this.validarCampo = true;

				});
			}
		});
	}
	public adicionarDiasCalendario(fechaVencimiento: Date, dias: number): Date {
		let resultado: Date = new Date(fechaVencimiento);
		//resultado = fechaVencimiento.ad
		resultado.setDate(resultado.getDate() + dias);
		return resultado;
	}
	/**
	   * Generar fraccionamiento new.
	   */
	public generarFraccionamientoNew(): void {
		if (this.frmGroup.get('cuota').value > 0) {
			let cuotaFraccionada = this.frmGroup.get('cuota').value / this.nroFraccionamiento.value;
			this.listaDetPlanPagosTemp = [];
			let dias: number = 0;
			for (let i = 0; i < this.nroFraccionamiento.value; i++) {
				let fechaVencimiento: Date = this.adicionarDiasCalendario(this.frmGroup.get('fechaVencimiento').value, dias);
				dias = dias + 30;
				let detPlanPagos = new DetPlanPagos();
				// if(this.CATALOGO_CUENTA_ID == CatalgoCuentaType.MATRICULA_ORDINARIA){
				// 	fraccionamiento.nro = ("M" + (i + 1));
				// }else if(this.CATALOGO_CUENTA_ID == CatalgoCuentaType.MATRICULA_EXTRAORDINAMIA){
				// 	fraccionamiento.nro = ("M_E" + (i + 1));
				// }else{
				detPlanPagos.nroCuota = ("" + (i + 1));
				//	}
				detPlanPagos.flagFraccionado = "S";
				detPlanPagos.cuota = (cuotaFraccionada);
				detPlanPagos.fechaVencimiento = (fechaVencimiento);
				detPlanPagos.cuotaConcepto = this.frmGroup.get('cuotaConcepto').value;
				if (this.frmGroup.get('aplicaIgv').value == true) {
					detPlanPagos.aplicaIgv = true;
					detPlanPagos.subTotal = Number(Number.parseFloat((cuotaFraccionada / 1.18) + "").toFixed(2));
					detPlanPagos.igv = Number(Number.parseFloat(detPlanPagos.subTotal * 0.18 + "").toFixed(2));
				} else {
					detPlanPagos.aplicaIgv = false;
					detPlanPagos.subTotal = (cuotaFraccionada);
					detPlanPagos.igv = 0;
				}
				this.listaDetPlanPagosTemp.push(detPlanPagos);
			}

		}
	}

	public findDetPlanPagos() {
		this.planPagos = this.data;
		this.planPagos.planPagosDetPlanPagosList = this.listaDetPlanPagosTemp;
		this.detPlanPagosService.find(this.planPagos).subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					this.detPlanPagosValidar = data.objetoResultado;
					if (this.validarCampo) {
						if (this.detPlanPagosValidar.idDetPlanPagos != null) {
							this.mostrarMensajeAdvertencia(this.detPlanPagosValidar.cuotaConcepto.catalogoCuenta.cuenta + ' ya Existe');
						} else {
							this.guardar();
						}
					} else {
						this.guardar();
					}
				}
			},
			error => {
				this.mostrarMensajeError(error);
			},
			() => {
				this.endProgres();
			}
		);
	}
}
