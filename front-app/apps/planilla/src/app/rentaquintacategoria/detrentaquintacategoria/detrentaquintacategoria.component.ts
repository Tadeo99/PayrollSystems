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

import {DetRentaQuintaCategoriaService} from "./detrentaquintacategoria.service";
import {DetRentaQuintaCategoria} from "../../../models/rrhh_planilla/detrentaquintacategoria.model";
import {RentaQuintaCategoria} from "../../../models/rrhh_planilla/rentaquintacategoria.model";
import {Personal} from "../../../models/rrhh_escalafon/personal.model";


/**
 * La Class DetRentaQuintaCategoriaComponent.
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
  selector: 'app-detrentaquintacategoria',
  templateUrl: './detrentaquintacategoria.component.html',
  styleUrls: ['./detrentaquintacategoria.component.css'],
  providers: [DetRentaQuintaCategoriaService]
})
export class DetRentaQuintaCategoriaComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto det renta quinta categoria. */
    public detRentaQuintaCategoria : DetRentaQuintaCategoria = new DetRentaQuintaCategoria();
	 
	/** La lista det renta quinta categoria. */
    public listaDetRentaQuintaCategoria : DetRentaQuintaCategoria[] = [];
    
	/** La lista item select. */
    public listaDetRentaQuintaCategoriaSelectMap : Map<string,DetRentaQuintaCategoria> = new Map<string,DetRentaQuintaCategoria>();

	
	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private detRentaQuintaCategoriaService: DetRentaQuintaCategoriaService,
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
		this.crearFormulario(this.detRentaQuintaCategoria);
	}
	
	private  crearFormulario(obj : DetRentaQuintaCategoria) : void{
		this.frmGroup = this.fb.group({
			idRentaQuintaCategoria: this.fb.group({
				idRentaQuintaCategoria: [obj.idRentaQuintaCategoria.idRentaQuintaCategoria  , [Validators.required] ],
				descripcionView: [obj.idRentaQuintaCategoria.descripcionView  , [Validators.required] ,{ updateOn: 'blur' }]
			 }),
			personal: this.fb.group({
				idPersonal: [obj.personal.idPersonal  , [Validators.required] ],
				descripcionView: [obj.personal.descripcionView  , [Validators.required] ,{ updateOn: 'blur' }]
			 }),
			idDetRentaQuintaCategoria: [obj.idDetRentaQuintaCategoria  , [Validators.required] ],
			remuneracionMensual: [obj.remuneracionMensual ],
			faltaTardanza: [obj.faltaTardanza ],
			remuneracionEnero: [obj.remuneracionEnero ],
			diasLab: [obj.diasLab ],
			otraRemuneracion: [obj.otraRemuneracion ],
			totalRemuneracion: [obj.totalRemuneracion ],
			gratificacionJulio: [obj.gratificacionJulio ],
			bonoExtraJulio: [obj.bonoExtraJulio ],
			gratificacionDiciembre: [obj.gratificacionDiciembre ],
			bonoExtraDiciembre: [obj.bonoExtraDiciembre ],
			extraOrdinario: [obj.extraOrdinario ],
			remuneracionAcumulada: [obj.remuneracionAcumulada ],
			remuneracionVariable: [obj.remuneracionVariable ],
			bonificacionExtra: [obj.bonificacionExtra ],
			remuneracionBruta: [obj.remuneracionBruta ],
			rentaNeta: [obj.rentaNeta ],
			impuestoRenta: [obj.impuestoRenta ],
			retencion: [obj.retencion ],
		  });
		  this.onChange();
	}
	
	private onChange():void{
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});
		
		this.frmGroup.get('idRentaQuintaCategoria.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalRentaQuintaCategoriaInputidRentaQuintaCategoria(value);
		});
		this.frmGroup.get('personal.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalPersonalInputpersonal(value);
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
				this.detRentaQuintaCategoriaService.crear(this.frmGroup.value).subscribe(
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
				this.detRentaQuintaCategoriaService.modificar(this.frmGroup.value,this.frmGroup.value.idDetRentaQuintaCategoria).subscribe(
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
	 	this.detRentaQuintaCategoria  = new DetRentaQuintaCategoria();
		this.frmGroup.patchValue(this.detRentaQuintaCategoria,{onlySelf: true, emitEvent: false });
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
			this.listaDetRentaQuintaCategoria = [];
			this.limpiaDataProvider(this.search);
			this.detRentaQuintaCategoria = new DetRentaQuintaCategoria();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.detRentaQuintaCategoria,{onlySelf: true, emitEvent: false });
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
			  this.detRentaQuintaCategoriaService.eliminar(id).subscribe(
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
	public confirmarEliminar(detRentaQuintaCategoriaTemp : DetRentaQuintaCategoria) {
		this.detRentaQuintaCategoria = detRentaQuintaCategoriaTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.detRentaQuintaCategoria.idDetRentaQuintaCategoria);
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(detRentaQuintaCategoriaTemp : DetRentaQuintaCategoria) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.detRentaQuintaCategoria = Object.assign({}, detRentaQuintaCategoriaTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.detRentaQuintaCategoria = Object.assign({}, detRentaQuintaCategoriaTemp);
					this.lanzarDetRentaQuintaCategoria();
				}
			}
			this.frmGroup.patchValue(this.detRentaQuintaCategoria,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(detRentaQuintaCategoriaTemp : DetRentaQuintaCategoria) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				detRentaQuintaCategoriaTemp.checked = true;
				this.agregarCheck(detRentaQuintaCategoriaTemp);
				this.dialogRef.close(this.listaDetRentaQuintaCategoriaSelectMap);
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
			this.listaDetRentaQuintaCategoria = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.detRentaQuintaCategoriaService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaDetRentaQuintaCategoria = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaDetRentaQuintaCategoria.length == 1) {
            this.asociar(this.listaDetRentaQuintaCategoria[0]);
		}
	}
	
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.detRentaQuintaCategoriaService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaDetRentaQuintaCategoria = data.listaResultado;
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
	
  private lanzarDetRentaQuintaCategoria(){
    // Usamos el mÃ©todo emit
    this.change.emit({detRentaQuintaCategoria: this.detRentaQuintaCategoria});
  }
  
   /*
   agregar check
   */
  public agregarCheck(detRentaQuintaCategoriaTemp : DetRentaQuintaCategoria) {
     if (detRentaQuintaCategoriaTemp.checked) {
		 if (!this.listaDetRentaQuintaCategoriaSelectMap.has(detRentaQuintaCategoriaTemp.idDetRentaQuintaCategoria)) {
			this.listaDetRentaQuintaCategoriaSelectMap.set(detRentaQuintaCategoriaTemp.idDetRentaQuintaCategoria,detRentaQuintaCategoriaTemp);
		 }
	 } else {
		if ((this.listaDetRentaQuintaCategoriaSelectMap.has(detRentaQuintaCategoriaTemp.idDetRentaQuintaCategoria))) {
			this.listaDetRentaQuintaCategoriaSelectMap.delete(detRentaQuintaCategoriaTemp.idDetRentaQuintaCategoria);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaDetRentaQuintaCategoria.forEach((data) => {
       if ((this.listaDetRentaQuintaCategoriaSelectMap.has(data.idDetRentaQuintaCategoria))) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal RentaQuintaCategoria input
  */	
  public abrirModalRentaQuintaCategoriaInputidRentaQuintaCategoria(pSearch? : string) {
	 this.frmGroup.get('idRentaQuintaCategoria.idRentaQuintaCategoria').setValue(null);
	 this.abrirModalRentaQuintaCategoriaidRentaQuintaCategoria(pSearch);
  }

  /**
	El abrir modal renta quinta categoria. 
  */   
  public abrirModalRentaQuintaCategoriaidRentaQuintaCategoria(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : RentaQuintaCategoria  = new RentaQuintaCategoria();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalRentaQuintaCategoria = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idRentaQuintaCategoria;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('idRentaQuintaCategoria').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
  /**
  Abrir modal Personal input
  */	
  public abrirModalPersonalInputpersonal(pSearch? : string) {
	 this.frmGroup.get('personal.idPersonal').setValue(null);
	 this.abrirModalPersonalpersonal(pSearch);
  }

  /**
	El abrir modal personal. 
  */   
  public abrirModalPersonalpersonal(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Personal  = new Personal();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalPersonal = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idPersonal;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('personal').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
}