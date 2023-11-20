import {Component, EventEmitter, OnInit,OnChanges,SimpleChanges,AfterViewInit} from '@angular/core';
import {FormControl,FormBuilder,Validators} from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import {TranslateService} from '@ngx-translate/core';

import {CommonServiceImpl} from "../../common/common.impl.service";
import {LoginService} from "../../seguridad/login/login.service";
import {TypeSelectItemService} from "../../../typeselectitemservice/typeselectitem.service";

import {BaseComponent,DialogConfirmContent,DialogContent} from "../../../base/base.component";
import {MatDialog,MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';

import {DetalleContradoService} from "./detallecontrado.service";
import {DetalleContrado} from "../../../models/rrhh_escalafon/detallecontrado.model";
import {Contrato} from "../../../models/rrhh_escalafon/contrato.model";


/**
 * La Class DetalleContradoComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:48 COT 2021
 * @since BUILDERP-CORE 2.1
 */
 
@Component({
  selector: 'app-detallecontrado',
  templateUrl: './detallecontrado.component.html',
  styleUrls: ['./detallecontrado.component.css'],
  providers: [DetalleContradoService]
})
export class DetalleContradoComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto detalle contrado. */
    public detalleContrado : DetalleContrado = new DetalleContrado();
	 
	/** La lista detalle contrado. */
    public listaDetalleContrado : DetalleContrado[] = [];
    
	/** La lista item select. */
    public listaDetalleContradoSelectMap : Map<string,DetalleContrado> = new Map<string,DetalleContrado>();

	
	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private detalleContradoService: DetalleContradoService,
	protected commonServiceImpl : CommonServiceImpl, protected loginDataService : LoginService,protected _typeSelectItemService : TypeSelectItemService,protected _translate: TranslateService) { 
		super(dialog,snackbar,router,route);
		super.setTypeSelectItemService(_typeSelectItemService);
		super.setTraslate(_translate);
		this.debounceTimeProcesar().subscribe(term =>  {this.search = term; this.buscar()});
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
		this.crearFormulario(this.detalleContrado);
	}
	
	private  crearFormulario(obj : DetalleContrado) : void{
		this.frmGroup = this.fb.group({
			contrato: this.fb.group({
				idContrato: [obj.contrato.idContrato ],
				descripcionView: [obj.contrato.descripcionView ,{ updateOn: 'blur' }]
			 }),
			idDetalleContrado: [obj.idDetalleContrado  , [Validators.required] ],
			basico: [obj.basico ],
			horaley: [obj.horaley ],
			montoDia: [obj.montoDia ],
			montoMes: [obj.montoMes ],
			estado: [obj.estado ],
			fecha: [obj.fecha ],
		  });
		  this.onChange();
	}
	
	private onChange():void{
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});
		
		this.frmGroup.get('contrato.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalContratoInputcontrato(value);
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
				this.detalleContradoService.crear(this.frmGroup.value).subscribe(
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
				this.detalleContradoService.modificar(this.frmGroup.value,this.frmGroup.value.idDetalleContrado).subscribe(
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
	 	this.detalleContrado  = new DetalleContrado();
		this.frmGroup.patchValue(this.detalleContrado,{onlySelf: true, emitEvent: false });
		this.mostrarPanelForm = true;
		this.accionNuevo = true;
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
			this.listaDetalleContrado = [];
			this.limpiaDataProvider(this.search);
			this.detalleContrado = new DetalleContrado();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.detalleContrado,{onlySelf: true, emitEvent: false });
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
	private eliminar ( id : any ) {
		try {
			  this.detalleContradoService.eliminar(id).subscribe(
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
	public confirmarEliminar(detalleContradoTemp : DetalleContrado) {
		this.detalleContrado = detalleContradoTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.detalleContrado.idDetalleContrado);
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(detalleContradoTemp : DetalleContrado) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.detalleContrado = Object.assign({}, detalleContradoTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.detalleContrado = Object.assign({}, detalleContradoTemp);
					this.lanzarDetalleContrado();
				}
			}
			this.frmGroup.patchValue(this.detalleContrado,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(detalleContradoTemp : DetalleContrado) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				detalleContradoTemp.checked = true;
				this.agregarCheck(detalleContradoTemp);
				this.dialogRef.close(this.listaDetalleContradoSelectMap);
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
			this.listaDetalleContrado = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.detalleContradoService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaDetalleContrado = data.listaResultado;
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
	
	private asociarData() : void {
		if (this.id != null && this.id != '' && this.listaDetalleContrado.length == 1) {
            this.asociar(this.listaDetalleContrado[0]);
		}
	}
	
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.detalleContradoService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaDetalleContrado = data.listaResultado;
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
	
  private lanzarDetalleContrado(){
    // Usamos el mÃ©todo emit
    this.change.emit({detalleContrado: this.detalleContrado});
  }
  
   /*
   agregar check
   */
  public agregarCheck(detalleContradoTemp : DetalleContrado) {
     if (detalleContradoTemp.checked) {
		 if (!this.listaDetalleContradoSelectMap.has(detalleContradoTemp.idDetalleContrado)) {
			this.listaDetalleContradoSelectMap.set(detalleContradoTemp.idDetalleContrado,detalleContradoTemp);
		 }
	 } else {
		if ((this.listaDetalleContradoSelectMap.has(detalleContradoTemp.idDetalleContrado))) {
			this.listaDetalleContradoSelectMap.delete(detalleContradoTemp.idDetalleContrado);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaDetalleContrado.forEach((data) => {
       if ((this.listaDetalleContradoSelectMap.has(data.idDetalleContrado))) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal Contrato input
  */	
  public abrirModalContratoInputcontrato(pSearch? : string) {
	 this.frmGroup.get('contrato.idContrato').setValue(null);
	 this.abrirModalContratocontrato(pSearch);
  }

  /**
	El abrir modal contrato. 
  */   
  public abrirModalContratocontrato(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Contrato  = new Contrato();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   dialogRef.componentInstance.esModalContrato = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idContrato;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('contrato').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
}