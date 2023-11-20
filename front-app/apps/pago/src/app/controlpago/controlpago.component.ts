import { Component, EventEmitter, OnInit, OnChanges, SimpleChanges, AfterViewInit, Optional } from '@angular/core';
import { FormControl, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

import { CommonServiceImpl } from "../../common/common.impl.service";
import { LoginService } from "../../seguridad/login/login.service";
import { TypeSelectItemService } from "../../../typeselectitemservice/typeselectitem.service";

import { BaseComponent, DialogConfirmContent, DialogContent } from "../../../base/base.component";
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ControlPagoService } from "./controlpago.service";
import { ControlPago } from "../../../models/pago/controlpago.model";
import { Anhio } from "../../../models/matricula/anhio.model";
import { Periodo } from "../../../models/matricula/periodo.model";
import { Alumno } from "../../../models/matricula/alumno.model";
import { Empresa } from "../../../models/pago/empresa.model";
import { Item } from "../../../models/common/item.model";
import { CuentaBancariaEntidad } from "../../../models/pago/cuentabancariaentidad.model";
import { DetControlPagoService } from '../detcontrolpago/detcontrolpago.service';
import { DetControlPago } from '../../../models/pago/detcontrolpago.model';
import { TipoDocSunatEntidad } from '../../../models/pago/tipodocsunatentidad.model';
import { TipoDocSunatEntidadService } from '../tipodocsunatentidad/tipodocsunatentidad.service';
import { SelectItemVO } from '../../../vo/selectitem.vo';
import { TipoDocSunatType } from '../../../type/tipodocsunat.type';
import { TipoMonedaType } from '../../../type/tipomoneda.type';
import { BaseDialogContent } from '../../../base/base.dialog.content.component';
import { MatriculaServiceImpl } from '../../matricula/matricula.impl.service';
import { EstadoGeneralState } from '../../../type/estadogeneral.state';
import { ModuloContextoType } from '../../../type/moduloContexto.type';
import { MatTableDataSource } from '@angular/material/table';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { indicatorRotate, indicatorRotate2 } from '../../../animations/fade-in.animation';


/**
 * La Class ControlPagoComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:52 COT 2021
 * @since BUILDERP-CORE 2.1
 */

@Component({
	selector: 'app-controlpago',
	templateUrl: './controlpago.component.html',
	styleUrls: ['./controlpago.component.css'],
	providers: [ControlPagoService, DetControlPagoService, TipoDocSunatEntidadService,MatriculaServiceImpl],
	animations: [
		trigger('detailExpand', [
			state('collapsed', style({ height: '0px', minHeight: '0' })),
			state('expanded', style({ height: '*' })),
			transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
		]),
		, indicatorRotate2, indicatorRotate],
})
export class ControlPagoComponent extends BaseComponent implements OnInit, OnChanges, AfterViewInit {

	/** El objeto control pago. */
	public controlPago: ControlPago = new ControlPago();

	/** La lista control pago. */
	public listaControlPago: ControlPago[] = [];

	/** La lista item select. */
	public listaControlPagoSelectMap: Map<string, ControlPago> = new Map<string, ControlPago>();

	public listaDetcontrolPago: DetControlPago[] = [];

	/** El monto pago. */
	public montoPago = new FormControl(0);

	/** El monto resta. */
	public montoResta = new FormControl(0);

	/** pago */
	/** La Constante TIPO_CAMBIO_DEFAULT. */
	public TIPO_CAMBIO_DEFAULT: number = 1;

	//public SERIE_BOLETA: string = "B001";
	//public SERIE_FACTURA: string = "F001";

	public ITEM_TIPO_COMPROBANTE_PAGO: number = 378;

	public ITEM_TIPO_TIPO_MONEDA: number = 380;

	public TIPO_COMPROBANTE_PAG: number = 3750008;

	/** La Constante CIEN_PORCIEENTO. */
	public CIEN_PORCIEENTO: number = 100;

	/** La variable id tipo doc sunat. */
	public idTipoDocSunat = new FormControl(null);

	/** La variable id tipo moneda. */
	public idTipoMoneda = new FormControl(null);

	mostrarCorre: string = "";
	mostrarSerie: string = "";

	public listaTipoMoneda: SelectItemVO[] = [];
	public listaTipoDocSunat: SelectItemVO[] = [];

	/** La lista tipo doc sunat entidad. */
	public listaTipoDocSunatEntidad: TipoDocSunatEntidad[] = [];
	public tipoDocSunatEntidad: TipoDocSunatEntidad = new TipoDocSunatEntidad();
	public mostraSimbolo: boolean;

	/*** El objeto anhio. */
	public anhioActivo: Anhio = new Anhio();

	public mostrar: boolean = false;	
	public mostrarRuc: boolean = false;

	public aplicaIGV: boolean = false;
	public sumMontoTempIGV: number = 0;
	public sumMontoTemp: number = 0;
	public montoCobrado : number = 0;
	public montoDevuelve : number = 0;
	public sumigv : number = 0;

	public sumIGVMonto : number = 0;
	public sumSinIGVMonto : number = 0;

	public idNivel: number;

	public dataSourceBandeja = new MatTableDataSource<ControlPago>();

	columnsToDisplay = ['view', 'fecha', 'comprobante', 'total', 'cant', 'pdf', 'estadosunat'];

	constructor(private fb: FormBuilder, public dialog: MatDialog, public snackbar: MatSnackBar, router: Router, route: ActivatedRoute, private controlPagoService: ControlPagoService,
		protected commonServiceImpl: CommonServiceImpl, public tipoDocSunatEntidadService: TipoDocSunatEntidadService, 
		private detControlPagoService: DetControlPagoService, protected loginDataService: LoginService, 
		protected _typeSelectItemService: TypeSelectItemService, protected _translate: TranslateService,protected matriculaServiceImpl: MatriculaServiceImpl) {
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
		this.crearFormulario(this.controlPago);
	}

	private crearFormulario(obj: ControlPago): void {
		this.frmGroup = this.fb.group({
			anhio: this.fb.group({
				idAnhio: [obj.anhio.idAnhio],
				descripcionView: [obj.anhio.descripcionView, { updateOn: 'blur' }]
			}),
			 periodo: this.fb.group({
			 	idPeriodo: [obj.periodo.idPeriodo ],
			 	descripcionView: [obj.periodo.descripcionView ,{ updateOn: 'blur' }]
			  }),
			 alumno: this.fb.group({
			 	idAlumno: [obj.alumno.idAlumno ],
			 	descripcionView: [obj.alumno.descripcionView ,{ updateOn: 'blur' }]
			  }),			
			empresa: this.fb.group({
				idEmpresa: [obj.empresa.idEmpresa, ],
				descripcionView: [obj.empresa.descripcionView,  { updateOn: 'blur' }]
			}),
			tipoDocSunat: this.fb.group({
				idItem: [obj.tipoDocSunat.idItem],
				descripcionView: [obj.tipoDocSunat.descripcionView, { updateOn: 'blur' }]
			}),
			itemByTipoMoneda: this.fb.group({
				idItem: [obj.itemByTipoMoneda.idItem],
				descripcionView: [obj.itemByTipoMoneda.descripcionView, { updateOn: 'blur' }]
			}),
			cuentaBancariaEntidad: this.fb.group({
				idCuentaBancariaEntidad: [obj.cuentaBancariaEntidad.idCuentaBancariaEntidad ],
				descripcionView: [obj.cuentaBancariaEntidad.descripcionView,  { updateOn: 'blur' }]
			}),
			idControlPago: [obj.idControlPago],
			tipoCambio: [obj.tipoCambio],
			nroDoc: [obj.nroDoc],
			igv: [obj.igv],
			subMontoTotal: [obj.subMontoTotal],
			montoTotal: [obj.montoTotal],
			fechaPago: [obj.fechaPago],
			fechaCreacion: [obj.fechaCreacion],
			usuarioCreacion: [obj.usuarioCreacion],
			fechaModificacion: [obj.fechaModificacion],
			usuarioModificacion: [obj.usuarioModificacion],
			estado: [obj.estado],
			ip: [obj.ip],
			origen: [obj.origen],
			tipoTrajeta: [obj.tipoTrajeta],
			nroOperacion: [obj.nroOperacion],
			fechaOperacion: [obj.fechaOperacion],
			serie: [obj.serie],
			envioSunat: [obj.envioSunat],
			anulado: [obj.anulado],
			tipoDescuento: [obj.tipoDescuento],
			descuentoTotal: [obj.descuentoTotal],
			descuento: [obj.descuento],
			observacion: [obj.observacion],
			idEntidadSelect: [obj.idEntidadSelect],
			deposito: [obj.deposito],
			entidad: [obj.entidad],
		});
		this.onChange();
	}

	private onChange(): void {
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});

		this.frmGroup.get('anhio.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalAnhioInputanhio(value);
		});
		//  this.frmGroup.get('periodo.descripcionView').valueChanges.subscribe(value => {
		//  	this.abrirModalPeriodoInputperiodo(value);
		//  });
		//  this.frmGroup.get('alumno.descripcionView').valueChanges.subscribe(value => {
		//  	this.abrirModalAlumnoInputalumno(value);
		// });	
		this.frmGroup.get('tipoDocSunat.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalItemInputtipoDocSunat(value);
		});
		this.frmGroup.get('itemByTipoMoneda.idItem').valueChanges.subscribe(value => {
			
		});
		
		this.frmGroup.get('deposito').valueChanges.subscribe(value => {
			this.mostrarCard();
		});
		this.frmGroup.get('serie').valueChanges.subscribe(value => {
			this.mostrarCorrelativo();
		});
		
		
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
				this.controlPago = this.frmGroup.value;
				this.controlPago.tipoDocSunat.idItem = this.idTipoDocSunat.value;
				this.controlPago.itemByTipoMoneda.idItem = this.idTipoMoneda.value;
				this.controlPago.idEntidadSelect = this.usuarioSession.entidad.idEntidad;
				this.controlPago.controlPagoDetControlPagoList = this.listaDetcontrolPago;
				this.controlPago.entidad = this.usuarioSession.entidad.idEntidad;
				this.controlPagoService.crear(this.controlPago).subscribe(
					data => {
						if (this.isProcesoOK(data)) {
							this.guardoExito();	
							this.buscar();			
							this.controlPago.idControlPago = 	data.objetoResultado		
							this.generarReportePagoIndividual(this.controlPago);
							this.listarConceptoPagoAlumnoSemestre();
						//	this.buscarTipoDocCorrelativo();							
						}
					},
					error => {
						this.mostrarMensajeError(error);
					}
				);
			} else {
				this.controlPagoService.modificar(this.frmGroup.value,this.frmGroup.value.idControlPago).subscribe(
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
		this.controlPago = new ControlPago();
		this.controlPago.anhio = this.frmGroup.get('anhio').value;
		this.controlPago.alumno = this.frmGroup.get('alumno').value;
		this.controlPago.periodo = this.frmGroup.get('periodo').value;
		this.controlPago.tipoDescuento = '';
		this.controlPago.fechaPago = new Date();
		this.frmGroup.patchValue(this.controlPago, { onlySelf: true, emitEvent: false });
		//this.frmGroup.get('anhio').patchValue(this.anhioActivo, { onlySelf: true, emitEvent: false }); 
		this.mostrarPanelForm = true;
		this.accionNuevo = true;
		this.montoPago.setValue(0);
		this.montoResta.setValue(0);
		this.listarConceptoPagoAlumnoSemestre();
		this.idTipoMoneda.setValue(3750011);
		this.idTipoDocSunat.setValue(this.TIPO_COMPROBANTE_PAG);
		if (this.idTipoDocSunat.value != null && this.idTipoMoneda.value != null) {
			this.valueChangeTipoMoneda();
			this.valueChangeTipoDocSunat();
		}
		this.mostrarCard();
		this.sumMontoTemp = 0;
		this.sumMontoTempIGV= 0;
		this.sumigv =0;
		this.sumIGVMonto=0;
		this.sumSinIGVMonto=0;
		
	}

	/**
	 * Inicializar.
	 *
	 */
	private async inicializar() {
		//this.buscarTipoDocCorrelativo();
		super.validarPaginaView();
		super.getUsuarioSession();
		this.anhioActivo = await this.commonServiceImpl.obtenerAnhioyEstadoAsync(EstadoGeneralState.ACTIVO);
		this.anhioActivo.descripcionView = this.obtenerDescripcionAnhio(this.anhioActivo); 
		this.limpiar();
		this.cargarCombo();
	}



	/**
	 * Value change tipo moneda.
	 *
	 * @param valueChangeEvent el value change event
	 * @throws Exception the exception
	 */
	public valueChangeTipoMoneda(): void {
		let idTipoMonedaType: number = Number.parseInt(this.commonServiceImpl.obtenerItem(this.ITEM_TIPO_TIPO_MONEDA, this.idTipoMoneda.value).descripcion);
		if (!(TipoMonedaType.SOLES == (idTipoMonedaType))) {
			//this.desabilitarCampoTipoCambio = false;
			this.frmGroup.get('tipoCambio').setValue(null);
			this.controlPago.tipoCambio = (null);
		} else {
			//this.desabilitarCampoTipoCambio = true;
			this.frmGroup.get('tipoCambio').setValue(this.TIPO_CAMBIO_DEFAULT);
			//this.controlPago.tipoCambio = (this.TIPO_CAMBIO_DEFAULT);
		}
		//this.desabilitarBotonGuardar = true;
		this.montoResta.setValue(0);
		this.montoResta.setValue(Number.parseFloat(this.montoResta.value + "") + (Number.parseFloat(this.montoPago.value + "")));
		this.frmGroup.get('montoTotal').setValue(null);
		this.frmGroup.get('subMontoTotal').setValue(null);
	}


	public buscarTipoDocCorrelativo(idItemTipoDoc: number) {
		try {
			this.startProgres();
			this.listaTipoDocSunatEntidad = [];	
			this.tipoDocSunatEntidad = new TipoDocSunatEntidad();
			this.tipoDocSunatEntidad.entidad = this.usuarioSession.entidad.idEntidad;	
			this.tipoDocSunatEntidad.itemByTipoDocSunat.idItem = idItemTipoDoc;
			this.tipoDocSunatEntidadService.listarTipoDocByItem(this.tipoDocSunatEntidad).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.setDataProvider(data);
						this.listaTipoDocSunatEntidad = data.listaResultado;						
						this.frmGroup.get('serie').setValue(this.listaTipoDocSunatEntidad[0].serie);
					}
				},
				error => {
					this.mostrarMensajeError(error);
				},
				() => {					
					this.endProgres();
				}
			);
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}

	public async cargarCombo() {
		let paramsTemp: Map<any, any> = new Map<any, any>();
		paramsTemp = paramsTemp.set(this.ITEM_TIPO_TIPO_MONEDA, 0);
		paramsTemp = paramsTemp.set(this.ITEM_TIPO_COMPROBANTE_PAGO, 0);
		await this.commonServiceImpl.obtenerListaItemSelectItemMap(paramsTemp);
		this.listaTipoMoneda = this.commonServiceImpl.getListaItemSelectItem(this.ITEM_TIPO_TIPO_MONEDA);
		this.listaTipoDocSunat = this.commonServiceImpl.getListaItemSelectItem(this.ITEM_TIPO_COMPROBANTE_PAGO);
	}

	/**
 * Value change tipo doc sunat.
 *
 * @param valueChangeEvent el value change event
 * @throws Exception the exception
 */
	public valueChangeTipoDocSunat(): void {		
		//	this.cliente = new Cliente();
		//	this.mostrarRuc = false;	
		//.controlPago.igv = 0;
		//	this.controlPago.descuento = 0;
		this.frmGroup.get('igv').setValue(0);
		this.frmGroup.get('descuentoTotal').setValue(0);
		this.frmGroup.get('subMontoTotal').setValue(0);
		this.frmGroup.get('montoTotal').setValue(0);
		this.frmGroup.get('envioSunat').setValue('');
		this.frmGroup.get('anulado').setValue('');
		//this.controlPago.descuentoTotal = 0;
		//this.controlPago.subMontoTotal = 0;
		// this.controlPago.montoTotal = 0;
		// this.controlPago.envioSunat = "";
		// this.controlPago.anulado = "";
		let idTipoDocSunatType: number = parseInt(this.commonServiceImpl.obtenerItem(this.ITEM_TIPO_COMPROBANTE_PAGO, this.idTipoDocSunat.value).descripcion);
		//this.mostrarCorrelativo();
		if ((TipoDocSunatType.FACTURA == (idTipoDocSunatType))) {
			//	this.desabilitarChekIgv = false;
			//		this.desabilitarCampoIgv = false;
			this.mostrarRuc = true;
		//	this.frmGroup.get('serie').setValue(this.SERIE_FACTURA);
			this.buscarTipoDocCorrelativo((this.idTipoDocSunat.value));	
		} else {
			//	this.desabilitarChekIgv = true;
			//	this.desabilitarCampoIgv = true;
			this.frmGroup.get('empresa.descripcionView').setValue(null);	
			this.frmGroup.get('empresa.idEmpresa').setValue(null);
			this.mostrarRuc = false;
			this.buscarTipoDocCorrelativo((this.idTipoDocSunat.value));	
		//	this.frmGroup.get('serie').setValue(this.SERIE_BOLETA);
		}
		if (idTipoDocSunatType != null) {
			//	this.mostrarNro = true;
		}
	}


	public mostrarCorrelativo() {
		//this.mostrarCorre = "";
		if (this.accionNuevo) {
			this.listaTipoDocSunatEntidad.forEach((obj) => {				
				if (obj.itemByTipoDocSunat.idItem == (this.idTipoDocSunat.value) && obj.serie == (this.frmGroup.get('serie').value)) {
					this.frmGroup.get('nroDoc').setValue(obj.correla);
				}
			})
		} else {
			//console.log("dsfghjknm");
			//this.mostrarCorre = this.controlPago.nroDoc;
		}

	}
	/**
	 * Limpiar.
	 *
	 */
	private limpiar() {
		try {
			this.listaControlPago = [];
			this.limpiaDataProvider(this.search);
			this.controlPago = new ControlPago();
			this.controlPago.anhio = this.anhioActivo;		
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.controlPago, { onlySelf: true, emitEvent: false });				
			}
			//	this.buscar();
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}

	
	public generarReportePagoIndividual(controlPagoTemp: ControlPago) {
		this.controlPagoService.generarReportePagoIndividual(controlPagoTemp).subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					this.descargarReporte(ModuloContextoType.PAGO, data.objetoResultado);
				}
			},
			error => {
				this.mostrarMensajeError(error);
			}
		);
	}

	/**
	 * buscar id
	 *
	 */
	public buscarID(controlPagoTemp: ControlPago) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.controlPago = Object.assign({}, controlPagoTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.controlPago = Object.assign({}, controlPagoTemp);
					this.lanzarControlPago();
				}
			}
			this.frmGroup.patchValue(this.controlPago, { onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(controlPagoTemp: ControlPago) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				controlPagoTemp.checked = true;
				this.agregarCheck(controlPagoTemp);
				this.dialogRef.close(this.listaControlPagoSelectMap);
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
			this.controlPago = this.frmGroup.value;
			this.listaControlPago = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.params = this.params.set('idEntidadSelect',this.idEntidad);
			this.params = this.params.set('idAnhio',this.controlPago.anhio.idAnhio + "");
			this.params = this.params.set('idAlumno',this.controlPago.alumno.idAlumno + "");
			this.params = this.params.set('idPeriodo',this.controlPago.periodo.idPeriodo + "");
			this.controlPagoService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.setDataProvider(data);
						this.listaControlPago = data.listaResultado;
						this.dataSourceBandeja = new MatTableDataSource<ControlPago>(this.listaControlPago);
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
		if (this.id != null && this.id != '' && this.listaControlPago.length == 1) {
			this.asociar(this.listaControlPago[0]);
		}
	}

	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			this.calcularStartRow();
			this.controlPagoService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaControlPago = data.listaResultado;
						this.dataSourceBandeja = new MatTableDataSource<ControlPago>(this.listaControlPago);
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

	private lanzarControlPago() {
		// Usamos el mÃ©todo emit
		this.change.emit({ controlPago: this.controlPago });
	}

	/*
	agregar check
	*/
	public agregarCheck(controlPagoTemp: ControlPago) {
		if (controlPagoTemp.checked) {
			if (!this.listaControlPagoSelectMap.has(controlPagoTemp.idControlPago)) {
				this.listaControlPagoSelectMap.set(controlPagoTemp.idControlPago, controlPagoTemp);
			}
		} else {
			if ((this.listaControlPagoSelectMap.has(controlPagoTemp.idControlPago))) {
				this.listaControlPagoSelectMap.delete(controlPagoTemp.idControlPago);
			}
		}
	}
	/*
	 verificar check
	*/
	private verificarCheck() {
		this.listaControlPago.forEach((data) => {
			if ((this.listaControlPagoSelectMap.has(data.idControlPago))) {
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
					this.frmGroup.get('anhio.idAnhio').setValue(entryVal.idAnhio);
					this.frmGroup.get('anhio').patchValue(entryVal, { onlySelf: true, emitEvent: false });
					if(this.frmGroup.get('alumno.idAlumno').value!=null){
						this.buscar();
					}
				});
			}
		});
	}
	/**
	Abrir modal Periodo input
	*/
	public abrirModalPeriodoInputperiodo(pSearch?: string) {
		this.frmGroup.get('periodo.idPeriodo').setValue(null);
		this.abrirModalPeriodoperiodo(pSearch);
	}

	/**
	  El abrir modal periodo. 
	*/
	public abrirModalPeriodoperiodo(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Periodo = new Periodo();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalPeriodo = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView =  entryVal.descripcion;
					this.frmGroup.get('periodo.idPeriodo').setValue(entryVal.idPeriodo);				
					this.frmGroup.get('periodo').patchValue(entryVal, { onlySelf: false, emitEvent: false });
					if(this.frmGroup.get('alumno.idAlumno').value!=null){
						this.buscar();
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
					this.frmGroup.get('alumno.idAlumno').setValue(entryVal.idAlumno);
					this.frmGroup.get('alumno').patchValue(entryVal, { onlySelf: false, emitEvent: false });				
					this.buscar();

				});
			}
		});
	}
	/**
	Abrir modal Empresa input
	*/
	public abrirModalEmpresaInputempresa(pSearch?: string) {
		this.frmGroup.get('empresa.idEmpresa').setValue(null);
		this.abrirModalEmpresaempresa(pSearch);
	}

	/**
	  El abrir modal empresa. 
	*/
	public abrirModalEmpresaempresa(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContentOverride);
		let data: Empresa = new Empresa();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModaleEmpresa = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.razonSocial;
					this.frmGroup.get('empresa.idEmpresa').setValue(entryVal.idEmpresa);
					this.frmGroup.get('empresa').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}
	/**
	Abrir modal Item input
	*/
	public abrirModalItemInputtipoDocSunat(pSearch?: string) {
		this.frmGroup.get('tipoDocSunat.idItem').setValue(null);
		this.abrirModalItemtipoDocSunat(pSearch);
	}

	/**
	  El abrir modal item. 
	*/
	public abrirModalItemtipoDocSunat(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Item = new Item();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalItem = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.idItem;//   + ' ' +  entryVal.nombre;			
					this.frmGroup.get('tipoDocSunat').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}
	
	/**
	Abrir modal CuentaBancariaEntidad input
	*/
	public abrirModalCuentaBancariaEntidadInputcuentaBancaria(pSearch?: string) {
		this.frmGroup.get('cuentaBancariaEntidad.idCuentaBancariaEntidad').setValue(null);
		this.abrirModalCuentaBancariaEntidadcuentaBancaria(pSearch);
	}

	/**
	  El abrir modal cuenta bancaria entidad. 
	*/
	public abrirModalCuentaBancariaEntidadcuentaBancaria(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContentOverride);
		let data: CuentaBancariaEntidad = new CuentaBancariaEntidad();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModaleCuentaBancariaEntidad = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.itemByBanco.nombre;
					this.frmGroup.get('cuentaBancariaEntidad.idCuentaBancariaEntidad').setValue(entryVal.idCuentaBancariaEntidad);
					this.frmGroup.get('cuentaBancariaEntidad').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}

	/**
	 * Listar concepto pago alumno semestre.
	 *
	 * @throws Exception the exception
	 */
	public listarConceptoPagoAlumnoSemestre(): void {
		this.listaDetcontrolPago = [];
		this.detControlPagoService.listarConceptoPagoAlumnoSemestre(this.controlPago.anhio.idAnhio, this.controlPago.periodo.idPeriodo, this.controlPago.alumno.idAlumno, true).subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					//this.setDataProvider(data);
					this.listaDetcontrolPago = data.listaResultado;
					//this.noEncontroRegistoAlmanecado(this.dataProvider);
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

	/**
	 * Value change monto pago.
	 *
	 * @param valueChangeEvent el value change event
	 * @throws Exception the exception
	 */
	public valueChangeMontoPago(): void {
		this.montoResta.setValue(0);
		this.montoResta.setValue(this.montoResta.value + this.montoPago.value);
		this.frmGroup.get('montoTotal').setValue(0);
		this.frmGroup.get('subMontoTotal').setValue(0);
		this.frmGroup.get('igv').setValue(0);
		//	this.desabilitarBotonGuardar = true;

	}

	public actualizarPadre(event) {//esto se invoca desde el componente hijo de seleccionar cursos	
		let calcularMontos: boolean = event.calcularMontos;
		if (calcularMontos) {	
			this.montoResta.setValue(Number(Number.parseFloat(Number(event.montoResta)+"").toFixed(2)));							
			this.aplicaIGV = event.aplicaIgv;
			this.montoCobrado = event.montoCobrar;
			this.montoDevuelve = event.montoDevuelve;			
			//	this.desabilitarBotonGuardar = event.desabilitarBotonGuardar;
			this.calcularMontos();
			
			
		}
	}

	/**
	 * Calcular montos.
	 */
	public calcularMontos() {
		if(this.aplicaIGV){
			this.sumIGVMonto = Number(Number.parseFloat(this.sumIGVMonto + (this.montoCobrado) - (this.montoDevuelve) +"").toFixed(2));		
			let montoDevuelveTemp =  Number.parseFloat((this.montoDevuelve / 1.18) +"").toFixed(2);	
			let sumigvCobrado =  Number(Number.parseFloat((this.montoCobrado / 1.18)*0.18 +"").toFixed(2));
			let sumigvDevuelve =  Number(Number.parseFloat((this.montoDevuelve / 1.18)*0.18 +"").toFixed(2));			
			this.sumigv  =  this.sumigv  + sumigvCobrado - (sumigvDevuelve);
			this.frmGroup.get('igv').setValue(Number.parseFloat((this.sumigv)+"").toFixed(2));	
			this.sumMontoTempIGV = this.sumMontoTempIGV + Number(Number.parseFloat(this.montoCobrado / 1.18 +"").toFixed(2)) - Number(montoDevuelveTemp);
		}else{
			//this.sumIGVMonto = Number(Number.parseFloat(this.sumIGVMonto + (this.montoCobrado) - (this.montoDevuelve) +"").toFixed(2));	
			this.sumMontoTemp = this.sumMontoTemp  + (this.montoCobrado) - (this.montoDevuelve);
		}	
		this.frmGroup.get('subMontoTotal').setValue(Number(Number.parseFloat(Number(Number.parseFloat(this.sumMontoTemp+"").toFixed(2)) + Number(Number.parseFloat(this.sumMontoTempIGV+"").toFixed(2))+"").toFixed(2)));
		this.frmGroup.get('montoTotal').setValue(Number.parseFloat(Number(this.frmGroup.get('montoTotal').value) + this.montoCobrado - this.montoDevuelve +"").toFixed(2));	
		this.valueChangeCalcularDES();	
		//this.volverCalcularMontos();
	}

	public volverCalcularMontos(){
		this.frmGroup.get('igv').setValue(Number.parseFloat((this.sumigv)+"").toFixed(2));
		this.frmGroup.get('subMontoTotal').setValue(Number(Number.parseFloat(Number(Number.parseFloat(this.sumMontoTemp+"").toFixed(2)) + Number(Number.parseFloat(this.sumMontoTempIGV+"").toFixed(2))+"").toFixed(2)));
		this.frmGroup.get('montoTotal').setValue(Number.parseFloat(Number(this.frmGroup.get('subMontoTotal').value) + Number(this.frmGroup.get('igv').value) +"").toFixed(2));	
		this.montoResta.setValue(Number(Number.parseFloat( Number(this.montoPago.value) - Number(this.frmGroup.get('montoTotal').value) +"").toFixed(2)));
	}


	public valueChangeDESTipo() {
		this.frmGroup.get('descuento').setValue(0);
		if (this.frmGroup.get('tipoDescuento').value == "P") {
			this.mostraSimbolo = false;
		} else if (this.frmGroup.get('tipoDescuento').value == "E") {
			this.mostraSimbolo = true;
		}	
		this.valueChangeCalcularDES();	
	}

	public valueChangeCalcularDES() {	
		let subDescuentoIGv: number  = 0;
		let descuentoTotalConIgv : number  = 0;	
		let descuentoTotalSinIgv : number  = 0;	
		let subTotalConIgv : number  = 0;	
		let subTotalSinIgv : number  = 0;	
		
		if (this.frmGroup.get('tipoDescuento').value == "P") {
			if(this.sumIGVMonto> 0){
				subDescuentoIGv = Number(Number.parseFloat((Number(this.sumIGVMonto) * Number(this.frmGroup.get('descuento').value)) / 100 +"").toFixed(2));
				descuentoTotalConIgv = Number(Number.parseFloat(Number(subDescuentoIGv)  / 1.18 +"").toFixed(2));
				subTotalConIgv =Number(Number.parseFloat((Number(this.sumIGVMonto) - Number(subDescuentoIGv)) / 1.18 +"" ).toFixed(2));
				this.frmGroup.get('igv').setValue(Number.parseFloat(Number(subTotalConIgv) * 0.18 +"").toFixed(2));
			}
			if(this.sumMontoTemp > 0){
				//subDescuentoSinIGv = Number(Number.parseFloat((this.sumMontoTemp) - Number(this.frmGroup.get('descuento').value)+"").toFixed(2));
				descuentoTotalSinIgv = Number(Number.parseFloat((Number(this.sumMontoTemp) * Number(this.frmGroup.get('descuento').value))/100+"").toFixed(2));
				subTotalSinIgv = (this.sumMontoTemp);
			}				
			this.frmGroup.get('descuentoTotal').setValue(Number.parseFloat((descuentoTotalConIgv)+(descuentoTotalSinIgv)+"").toFixed(2));
			this.frmGroup.get('subMontoTotal').setValue(Number.parseFloat((subTotalConIgv)+(subTotalSinIgv)+"").toFixed(2));	
			this.frmGroup.get('montoTotal').setValue(Number.parseFloat((Number(this.sumIGVMonto)  + Number(this.sumMontoTemp))- (subDescuentoIGv +descuentoTotalSinIgv)  +"").toFixed(2));		

		} else if (this.frmGroup.get('tipoDescuento').value == "E") {						
			if(this.sumIGVMonto> 0){
				subDescuentoIGv = Number(Number.parseFloat((Number(this.sumIGVMonto) - Number(this.frmGroup.get('descuento').value)).toFixed(2)));
				descuentoTotalConIgv = Number(Number.parseFloat(this.frmGroup.get('descuento').value /1.18 +"").toFixed(2));
				subTotalConIgv =Number(Number.parseFloat((Number(subDescuentoIGv)) / 1.18 +"" ).toFixed(2));
				this.frmGroup.get('igv').setValue(Number.parseFloat(Number(subTotalConIgv) * 0.18 +"").toFixed(2));
			}
			if(this.sumMontoTemp > 0){
			//	subDescuentoSinIGv = Number(Number.parseFloat((this.sumMontoTemp) - Number(this.frmGroup.get('descuento').value)+"").toFixed(2));
				descuentoTotalSinIgv = Number(this.frmGroup.get('descuento').value);
				subTotalSinIgv = (this.sumMontoTemp);
			}
			this.frmGroup.get('descuentoTotal').setValue(Number.parseFloat((descuentoTotalConIgv)+(descuentoTotalSinIgv)+"").toFixed(2));
			this.frmGroup.get('subMontoTotal').setValue(Number.parseFloat((subTotalConIgv)+(subTotalSinIgv)+"").toFixed(2));	
			this.frmGroup.get('montoTotal').setValue(Number.parseFloat(Number(this.sumIGVMonto) + Number(this.sumMontoTemp) - Number(this.frmGroup.get('descuento').value)+" ").toFixed(2));		
			//	this.frmGroup.get('montoTotal').setValue( Number.parseFloat(( Number(this.frmGroup.get('subMontoTotal').value) + Number(this.frmGroup.get('igv').value)) - Number(this.frmGroup.get('descuentoTotal').value)+"").toFixed(2));	
		}			
		this.montoResta.setValue(Number.parseFloat(Number((this.montoPago.value)- Number(this.frmGroup.get('montoTotal').value)+"").toFixed(2)));	
		
		if(this.frmGroup.get('descuento').value == 0){
			this.frmGroup.get('descuentoTotal').setValue(0);	
			this.volverCalcularMontos();
		}
	}

	public mostrarCard() {
		this.frmGroup.get('tipoTrajeta').setValue(null);
		this.frmGroup.get('nroOperacion').setValue(null);
		this.frmGroup.get('fechaOperacion').setValue(null);	
		this.frmGroup.get('cuentaBancariaEntidad.descripcionView').setValue(null);	
		this.frmGroup.get('cuentaBancariaEntidad.idCuentaBancariaEntidad').setValue(null);
		if (this.frmGroup.get('deposito').value == true) {			
			this.mostrar = true;					
		} else {
			this.mostrar = false;
		}		
	}

}

@Component({
	template: `
	<app-empresa *ngIf="esModaleEmpresa"  [modulo] = "modulo" [id] = "id" [esIncludeComponent] = "esIncludeComponent"  [showModal]="showModal" [showSelectMultiple] = "showSelectMultiple" [dialogRef] = "dialogRef" [data] = "data"></app-empresa>
	<app-cuentabancariaentidad *ngIf="esModaleCuentaBancariaEntidad"  [modulo] = "modulo" [id] = "id" [esIncludeComponent] = "esIncludeComponent"  [showModal]="showModal" [showSelectMultiple] = "showSelectMultiple" [dialogRef] = "dialogRef" [data] = "data"></app-cuentabancariaentidad>
	
	`
})
export class DialogContentOverride extends BaseDialogContent {
	public esModaleEmpresa: boolean = false;
	public esModaleCuentaBancariaEntidad: boolean = false;

	constructor(@Optional() public dialogRef: MatDialogRef<DialogContentOverride>) { super() }
}