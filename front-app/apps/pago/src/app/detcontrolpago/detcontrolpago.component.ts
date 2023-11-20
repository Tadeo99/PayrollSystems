import {Component, EventEmitter, OnInit,OnChanges,SimpleChanges,AfterViewInit, Input, ViewChild} from '@angular/core';
import {FormControl,FormBuilder,Validators} from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import {TranslateService} from '@ngx-translate/core';

import {CommonServiceImpl} from "../../common/common.impl.service";
import {LoginService} from "../../seguridad/login/login.service";
import {TypeSelectItemService} from "../../../typeselectitemservice/typeselectitem.service";

import {BaseComponent,DialogConfirmContent,DialogContent} from "../../../base/base.component";
import {MatDialog,MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';

import {DetControlPagoService} from "./detcontrolpago.service";
import {DetControlPago} from "../../../models/pago/detcontrolpago.model";
import {ControlPago} from "../../../models/pago/controlpago.model";
import {CuotaConcepto} from "../../../models/pago/cuotaconcepto.model";
import { MatTableDataSource } from '@angular/material/table';
import { interval } from 'rxjs';
import { ConstantesMensajesPago } from '../../../constante/constantemensajespago';
import { DialogContentOverride } from '../planpagos/planpagos.component';
import { MatPaginator } from '@angular/material/paginator';


/**
 * La Class DetControlPagoComponent.
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
  selector: 'app-detcontrolpago',
  templateUrl: './detcontrolpago.component.html',
  styleUrls: ['./detcontrolpago.component.css'],
  providers: [DetControlPagoService]
})
export class DetControlPagoComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto det control pago. */
    public detControlPago : DetControlPago = new DetControlPago();
	 
	/** La lista det control pago. */
	@Input("listaDetControlPago")
	public listaDetControlPagoTemp: DetControlPago[] = [];
    public listaDetControlPago : DetControlPago[] = [];
    
	/** La lista item select. */
    public listaDetControlPagoSelectMap : Map<string,DetControlPago> = new Map<string,DetControlPago>();

	public dataSource = new MatTableDataSource<DetControlPago>();
	displayedColumns = ['concepto', 'monto',  'montoR',  'check'];

	public checkAll: boolean = false;

	/** El monto pago. */
	@Input("montoPago")
	public montoPago = new FormControl(0);


	/** El monto resta. */
	@Input("montoResta")
	public montoResta = new FormControl(0);

	@Input("controlPago")
	public controlPago: ControlPago = new ControlPago();

	@Input("idNivel")
	public idNivel: number;

	/** El flag desabilitar boton guardar. */
	public desabilitarBotonGuardar: boolean = true;
	
	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private detControlPagoService: DetControlPagoService,
	protected commonServiceImpl : CommonServiceImpl, protected loginDataService : LoginService,protected _typeSelectItemService : TypeSelectItemService,protected _translate: TranslateService) { 
		super(dialog,snackbar,router,route);
		super.setTypeSelectItemService(_typeSelectItemService);
		super.setTraslate(_translate);
		//this.debounceTimeProcesar().subscribe(term =>  {this.search = term; this.buscar()});
		super.setLoginDataService(loginDataService);		
	}
	
	ngAfterViewInit() {
    // viewChild is set after the view has been initialized

	}

    ngOnChanges(changes: SimpleChanges) { 	

		if(this.montoPago.value <= 0){
			this.limpiaDataProvider("", 4);	
			this.checkAll = false;
			this.listaDetControlPago = [];		
			this.listasize = this.listaDetControlPagoTemp.length;		
			this.dataProvider.pageSize;
			let data: any = { offset: this.dataProvider.pageSize, contador: this.listasize };
			this.setDataProvider(data);			
			this.noEncontroRegistoAlmanecado(this.dataProvider);
			this.buscarPaginado();
			this.endProgres();
		}
    }
	
	ngOnInit() {
		this.onInit();
	//	this.inicializar();
	//	this.crearFormulario(this.detControlPago);
	}
	
	private  crearFormulario(obj : DetControlPago) : void{
		this.frmGroup = this.fb.group({
			controlPago: this.fb.group({
				idControlPago: [obj.controlPago.idControlPago ],
				descripcionView: [obj.controlPago.descripcionView ,{ updateOn: 'blur' }]
			 }),
			cuotaConcepto: this.fb.group({
				idCuotaConcepto: [obj.cuotaConcepto.idCuotaConcepto ],
				descripcionView: [obj.cuotaConcepto.descripcionView ,{ updateOn: 'blur' }]
			 }),
			idDetControlPago: [obj.idDetControlPago  , [Validators.required] ],
			nroCuota: [obj.nroCuota ],
			descripcionConcepto: [obj.descripcionConcepto ],
			monto: [obj.monto ],
			mora: [obj.mora ],
			fechaReversion: [obj.fechaReversion ],
			fechaCreacion: [obj.fechaCreacion ],
			usuarioCreacion: [obj.usuarioCreacion ],
			fechaModificacion: [obj.fechaModificacion ],
			usuarioModificacion: [obj.usuarioModificacion ],
			estado: [obj.estado ],
			ip: [obj.ip ],
		  });		
	}
	
	
  
	 
	onInit() {
    /*var id = this.route.params.subscribe(params => {
      var id = params['id'];

    });*/
    }
  

	
	/**
	 * Buscar paginado.
	 */
	 public buscarPaginado() {
		this.startProgres();
		if (this.listasize > 0) {
			this.calcularStartRow();
			let begin = this.dataProvider.startRow;
			let end = this.dataProvider.offset + begin;
			this.listaDetControlPago = this.listaDetControlPagoTemp.slice(begin, end);			
			this.verificarCheckAll();
		}
		this.endProgres();
	}
	
  /**
	 * getBufferedData.
	 *
   */
   public getBufferedData(event : any) {
	this.dataProvider = event.dataProvider;
    this.buscarPaginado();
   }
	

  private lanzarDetControlPago(){
    // Usamos el mÃ©todo emit
    this.change.emit({detControlPago: this.detControlPago});
  }
  
  	/**
	 * Verificar check all.
	 */
	public verificarCheckAll(): void {
		this.checkAll = true;
		//this.desabilitarBotonGuardar = true;
		this.listaDetControlPago.forEach((objDetControlpago) => {
			if (objDetControlpago.checked) {
				//this.desabilitarBotonGuardar = false;				
			} else {
				this.checkAll = false;
			}
		});	
		
		this.dataSource = new MatTableDataSource<DetControlPago>(this.listaDetControlPago);
		
		//this.change.emit({ calcularMontos: true, montoResta: this.montoResta.value, desabilitarBotonGuardar: this.desabilitarBotonGuardar });
	}

  /**
	 * Value change concepto pago all.
	 *
	 * @param valueChangeEvent el value change event
	 * @throws Exception the exception
	 */
	public valueChangeConceptoPagoAll(): void {
	//	this.desabilitarBotonGuardar = true;
		let montoCobrado : number = 0 ;
		let montoDevuelve : number = 0 ;
		if (this.checkAll) {
		//	this.desabilitarBotonGuardar = false;		
			this.listaDetControlPago.forEach((objDetControlpago) => {	
				montoCobrado = 0;		
				objDetControlpago.checked = (true);
				this.montoResta.setValue(this.montoResta.value * (this.controlPago.tipoCambio));
				if (this.montoResta.value > (0)) {
					let montoDebe: number = objDetControlpago.monto;
					let montoRestaTemp: number = Number(this.montoResta.value)  - (montoDebe);
					montoCobrado = Number(this.montoResta.value)  - montoRestaTemp;			
					console.log(montoCobrado);									
					if (montoRestaTemp > (0)) {						
						this.montoResta.setValue(montoRestaTemp);
						objDetControlpago.montoResta = Number(Number.parseFloat(objDetControlpago.monto - (montoDebe) +"").toFixed(2));
					} else {						
						objDetControlpago.montoResta = Number(Number.parseFloat(objDetControlpago.monto - (this.montoResta.value)+"").toFixed(2));
						montoCobrado =  objDetControlpago.monto - objDetControlpago.montoResta;
						this.montoResta.setValue(0);
					}
				} else {
					objDetControlpago.checked = (false);
				}
				
				this.change.emit({ calcularMontos: true, montoResta:  Number(this.montoResta.value), montoDevuelve : montoDevuelve, montoCobrar:montoCobrado,aplicaIgv: objDetControlpago.aplicaIgv ,desabilitarBotonGuardar: this.desabilitarBotonGuardar });
			});
		} else {			
			 this.listaDetControlPago.forEach((objDetControlpago) => {	
				montoDevuelve = 0;			
			 	objDetControlpago.checked = (false);
			 	if (objDetControlpago.montoResta != null) {
					if (objDetControlpago.montoResta > (0)) {						
						let montoRetorno: number = Number.parseFloat(objDetControlpago.monto + "") - (Number.parseFloat(objDetControlpago.montoResta + ""));
						montoDevuelve = montoRetorno;
			 			this.montoResta.setValue(Number(Number(this.montoResta.value) + (montoRetorno)));						 
			 		} else {
			 			this.montoResta.setValue(Number(Number(this.montoResta.value) + (objDetControlpago.monto)));
						 let montoRetorno: number = objDetControlpago.monto - (objDetControlpago.montoResta);
						 montoDevuelve = montoRetorno;
			 		}
			 	}	
				 objDetControlpago.montoResta = (null);	
				 this.change.emit({ calcularMontos: true, montoResta: this.montoResta.value, montoDevuelve : montoDevuelve, montoCobrar:montoCobrado,aplicaIgv: objDetControlpago.aplicaIgv ,desabilitarBotonGuardar: this.desabilitarBotonGuardar });		
			});
		}
	
		this.dataSource = new MatTableDataSource<DetControlPago>(this.listaDetControlPago);		
	}

	/**
	 * Value change concepto pago.
	 *
	 * @param valueChangeEvent el value change event
	 * @throws Exception the exception
	 */
	 public valueChangeConceptoPago(conceptoPagoView: DetControlPago): void {
		if (conceptoPagoView.checked) {
			conceptoPagoView.checked = false;
		} else {
			conceptoPagoView.checked = true;
		}
		let ejecutar: boolean = true;
		let actualizarCheckView: boolean = false;
		let montoCobrado : number = 0 ;
		let montoDevuelve : number = 0 ;
		this.checkAll = false;
		let seleccionar: boolean = conceptoPagoView.checked; //(Boolean)valueChangeEvent.getNewValue();
		this.detControlPago = conceptoPagoView;
		if (this.controlPago.tipoCambio == null) {
			this.mostrarMensajeAdvertencia(this.cargarMensaje(ConstantesMensajesPago.DEBE_INGRESAR_TIPO_CAMBIO));
			seleccionar = false;
			actualizarCheckView = true;
			ejecutar = false;//return ;
		}
		if (seleccionar && ejecutar) {
			this.montoResta.setValue(this.montoResta.value * (this.controlPago.tipoCambio));//traduciendo a soles
			//this.montoResta = montoResta.divide(BigDecimal.ONE,0, RoundingMode.HALF_EVEN);
			if (this.montoResta.value > (0)) {
				let montoDebe: number = this.detControlPago.monto;
				let montoRestaTemp: number = Number(this.montoResta.value)  - (montoDebe);	
				montoCobrado = Number(this.montoResta.value)  - montoRestaTemp;			
				if (montoRestaTemp > (0)) {
					this.montoResta.setValue(montoRestaTemp);	
								
					this.detControlPago.montoResta = Number(Number.parseFloat(this.detControlPago.monto - (montoDebe) +"").toFixed(2));
				} else {					
					this.detControlPago.montoResta = Number(Number.parseFloat(this.detControlPago.monto - (this.montoResta.value)+"").toFixed(2));
					montoCobrado =  this.detControlPago.monto -this.detControlPago.montoResta;
					this.montoResta.setValue(0);
				}
			} else {
				this.mostrarMensajeAdvertencia(this.cargarMensaje(ConstantesMensajesPago.NO_TIENE_SALDO));
				seleccionar = false;
				ejecutar = false;
				actualizarCheckView = true;//	this.refreshData(conceptoPagoView.id + '',seleccionar);
				conceptoPagoView.checked = false;
			}			
			//this.montoResta = this.montoResta.divide(controlPago.getTipoCambio(),2, RoundingMode.HALF_EVEN);//traduciendo a tipo moneda origen
			this.montoResta.setValue(Number(this.montoResta.value)  / (this.controlPago.tipoCambio));//traduciendo a tipo moneda origen
				
		} else {
			if (ejecutar) {
				if (this.detControlPago.montoResta != null) {
					montoDevuelve = 0;
					if (this.detControlPago.montoResta > (0)) {
						let montoRetorno: number = this.detControlPago.monto - (this.detControlPago.montoResta);
						montoDevuelve = montoRetorno;
						//montoRetorno = montoRetorno.divide(controlPago.getTipoCambio(),2, RoundingMode.HALF_EVEN);//traduciendo a tipo moneda origen
						montoRetorno = montoRetorno / (this.controlPago.tipoCambio);//traduciendo a tipo moneda origen
						this.montoResta.setValue(Number(this.montoResta.value) + (montoRetorno));						
					} else {
						let montoRetorno: number = this.detControlPago.monto - (this.detControlPago.montoResta);
						montoDevuelve = montoRetorno;
						montoRetorno = montoRetorno / (this.controlPago.tipoCambio);//traduciendo a tipo moneda origen
						this.montoResta.setValue(Number(this.montoResta.value) + (montoRetorno));
					}
				}
				this.detControlPago.montoResta = (null);
			}
		}
		this.change.emit({ calcularMontos: true, montoResta: this.montoResta.value,montoDevuelve : montoDevuelve, montoCobrar:montoCobrado,aplicaIgv: this.detControlPago.aplicaIgv ,desabilitarBotonGuardar: this.desabilitarBotonGuardar });
		//this.calcularMontos();
		if (actualizarCheckView) {
			this.refreshData(conceptoPagoView.cuotaConcepto.idCuotaConcepto + '', seleccionar);
		}
		//return seleccionar;
	}

	public refreshData(idSelect: string, checked: boolean): void {
		this.subscribeToData(idSelect, checked);
	}

	public subscribeToData(idSelect: string, checked: boolean): void {
		this.timerSubscription = interval(200).subscribe(() => {
			this.actualizarCheck(idSelect, checked);			
			this.verificarCheckAll();
			//this.refreshData(idSelect,checked);	
			this.ngOnDestroy();
		}
		);
	}

	public actualizarCheck(idSelect: string, checked: boolean): void {
		this.listaDetControlPago.forEach((data) => {
			if (data.id == idSelect) {
				data.checked = checked;
				return;
			}
		});
		this.listaDetControlPagoTemp.forEach((data) => {
			if (data.id == idSelect) {
				data.checked = checked;
				return;
			}
		});
	}

	 /**
  Abrir modal CuotaConcepto input
  */	
  public abrirModalCuotaConceptoInputcuotaConcepto(pSearch? : string) {
	this.frmGroup.get('cuotaConcepto.idCuotaConcepto').setValue(null);
	this.abrirModalCuotaConceptocuotaConcepto(pSearch);
 }

 /**
   El abrir modal cuota concepto. 
 */   
 public abrirModalCuotaConceptocuotaConcepto(pSearch? : string) {
  let dialogRef = this.dialog.open(DialogContentOverride);
  let data : CuotaConcepto  = new CuotaConcepto();
  data.id = this.idNivel;
  dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
  dialogRef.componentInstance.esModalCuotaConcepto = true;
  dialogRef.afterClosed().subscribe(result => {
  if (result) {
	  if(this.montoResta.value > 0){
		result.forEach((entryVal : any , entryKey : any )  => {
			let obj : DetControlPago = new DetControlPago();
			obj.controlPago = this.controlPago;
			obj.cuotaConcepto = entryVal;
			obj.descripcionConcepto = entryVal.catalogoCuenta.cuenta;
			obj.monto = entryVal.monto;	
			obj.checked = false;
			obj.aplicaIgv = entryVal.aplicaIgv;
			this.listaDetControlPagoTemp.push(obj);
			this.listasize = this.listaDetControlPagoTemp.length;
			this.listaDetControlPagoTemp.reverse();
			let data: any = { offset: this.dataProvider.pageSize, contador: this.listasize }
			this.setDataProvider(data);
			this.noEncontroRegistoAlmanecado(this.dataProvider);
			this.buscarPaginado();
			this.valueChangeConceptoPago(obj);
				
		   });
	  }else{this.mostrarMensajeAdvertencia(this.cargarMensaje(ConstantesMensajesPago.NO_TIENE_SALDO));}

	}
  });
 }



}