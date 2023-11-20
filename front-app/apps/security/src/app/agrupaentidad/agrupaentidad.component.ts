import {Component, EventEmitter, OnInit,OnChanges,SimpleChanges,AfterViewInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import {TranslateService} from '@ngx-translate/core';

import {CommonServiceImpl} from "../../common/common.impl.service";
import {LoginService} from "../../seguridad/login/login.service";
import {TypeSelectItemService} from "../../../typeselectitemservice/typeselectitem.service";

import {BaseComponent,DialogConfirmContent,DialogContent} from "../../../base/base.component";
import {MatDialog,MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';

import {AgrupaEntidadService} from "./agrupaentidad.service";
import {AgrupaEntidad} from "../../../models/seguridad/agrupaentidad.model";
import {Entidad} from "../../../models/seguridad/entidad.model";


/**
 * La Class AgrupaEntidadComponent.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sat Dec 23 17:16:36 COT 2017
 * @since SIAA-CORE 2.1
 */
 
@Component({
  selector: 'app-agrupaentidad',
  templateUrl: './agrupaentidad.component.html',
  styleUrls: ['./agrupaentidad.component.css'],
  providers: [AgrupaEntidadService]
})
export class AgrupaEntidadComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto agrupa entidad. */
    public agrupaEntidad : AgrupaEntidad = new AgrupaEntidad();
	 
	/** La lista agrupa entidad. */
    public listaAgrupaEntidad : AgrupaEntidad[] = [];
    
	/** La lista item select. */
    public listaAgrupaEntidadSelectMap : Map<string,AgrupaEntidad> = new Map<string,AgrupaEntidad>();

	
	constructor(public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private agrupaEntidadService: AgrupaEntidadService,
	public commonServiceImpl : CommonServiceImpl, public loginDataService : LoginService,public _typeSelectItemService : TypeSelectItemService,public _translate: TranslateService) { 
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
	}
	 
	onInit() {
    /*var id = this.route.params.subscribe(params => {
      var id = params['id'];

    });*/
    }
  
    /**
	 * listo.
	 *
	 */
	public listo () {
		try {
			this.startProgres();
			if (this.accionNuevo) {			
				this.agrupaEntidadService.crear(this.agrupaEntidad).subscribe(
			    data => {
					if (this.isProcesoOK(data)) {
						this.guardoExito();
						this.buscar();
					}
				},
				error => {
					this.mostrarMensajeError(error);
				},
			   ()=> {
				 this.endProgres();
			   }
			  );
			} else {
				this.agrupaEntidadService.modificar(this.agrupaEntidad,this.agrupaEntidad.idAgrupaEntidad).subscribe(
			    data => {
					if (this.isProcesoOK(data)) {
						this.actualizadoExito();
						this.buscar();
					}
				},
				error => {
					this.mostrarMensajeError(error);
				},
			   ()=> {
				 this.endProgres();
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
	public nuevo () {
	 	this.agrupaEntidad  = new AgrupaEntidad();
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
	private limpiar () {
		try {
			this.listaAgrupaEntidad= [];
			this.limpiaDataProvider(this.search);
			this.agrupaEntidad = new AgrupaEntidad();
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
			  this.startProgres();
			  this.agrupaEntidadService.eliminar(id).subscribe(
			    data => {
					if (this.isProcesoOK(data)) {
						this.eliminoExito();
						this.buscar();
					}
				},
				error => {
					this.mostrarMensajeError(error);
			   },
			   ()=> {
				 this.endProgres();
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
	public confirmarEliminar(agrupaEntidadTemp : AgrupaEntidad) {
		this.agrupaEntidad = agrupaEntidadTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.agrupaEntidad.idAgrupaEntidad);
		 }
		});
    }
	  
	/**
	 * accion menu.
	 *
	 */
	 public accionMenu(item : any) {
		 if (item.click == 'confirmarEliminar') {
		     this.confirmarEliminar(this.agrupaEntidad);
		 }
	}
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(agrupaEntidadTemp : AgrupaEntidad) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.agrupaEntidad = Object.assign({}, agrupaEntidadTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.agrupaEntidad = Object.assign({}, agrupaEntidadTemp);
					this.lanzarAgrupaEntidad();
				}
			}
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(agrupaEntidadTemp : AgrupaEntidad) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				agrupaEntidadTemp.checked = true;
				this.agregarCheck(agrupaEntidadTemp);
				this.dialogRef.close(this.listaAgrupaEntidadSelectMap);
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
			this.startProgres();
			this.listaAgrupaEntidad = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.agrupaEntidadService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaAgrupaEntidad = data.listaResultado;
					this.asociarData();
					this.mostrarPanelForm = false;
					this.noEncontroRegistoAlmanecado(this.dataProvider);
				}
			}, 
			error => {
				this.mostrarMensajeError(error);
			},
			()=> {
				this.endProgres();
			}
			);		 
		} catch (e) {
			this.mostrarMensajeError(e);
		}				
	}
	private asociarData() : void {
		if (this.id != null && this.id != '' && this.listaAgrupaEntidad.length == 1) {
            this.asociar(this.listaAgrupaEntidad[0]);
		}
	}
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado () {
		this.startProgres();
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.agrupaEntidadService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaAgrupaEntidad = data.listaResultado;
						this.asociarData();
						if (this.showSelectMultiple) {
							this.verificarCheck();
						}
					}
				},
				error => {
					this.mostrarMensajeError(error);
				},
				()=> {
					this.endProgres();
				}
			 );  
	   }
	};	
	
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
	
  public lanzarAgrupaEntidad(){
    // Usamos el mÃ©todo emit
    this.change.emit({agrupaEntidad: this.agrupaEntidad});
  }
  
   /*
   agregar check
   */
  public agregarCheck(agrupaEntidadTemp : AgrupaEntidad) {
     if (agrupaEntidadTemp.checked) {
		 if (this.listaAgrupaEntidadSelectMap.get(agrupaEntidadTemp.idAgrupaEntidad) == null) {
			this.listaAgrupaEntidadSelectMap.set(agrupaEntidadTemp.idAgrupaEntidad,agrupaEntidadTemp);
		 }        
	 } else {
		if ((this.listaAgrupaEntidadSelectMap.get(agrupaEntidadTemp.idAgrupaEntidad) != null)) {
			this.listaAgrupaEntidadSelectMap.delete(agrupaEntidadTemp.idAgrupaEntidad);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaAgrupaEntidad.forEach((data) => {
       if ((this.listaAgrupaEntidadSelectMap.get(data.idAgrupaEntidad) != null)) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal Entidad input
  */	
  public abrirModalEntidadInput(pSearch? : string) {
	 this.agrupaEntidad.entidad.idEntidad = null;

	 this.agrupaEntidad.entidadAgrupa.idEntidad = null;
	 
	 this.abrirModalEntidad(pSearch);
  }

  /**
	El abrir modal entidad. 
  */   
  public abrirModalEntidad(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Entidad  = new Entidad();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalEntidad = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
	  let listaEntidadSelectMap = result;
	  if (listaEntidadSelectMap) {
		  listaEntidadSelectMap.forEach((entryVal : any , entryKey : any )  => {
			this.agrupaEntidad.entidad = entryVal;
			this.agrupaEntidad.entidad.descripcionView = entryVal.idEntidad;//   + ' ' +  entryVal.nombre;

			this.agrupaEntidad.entidadAgrupa = entryVal;
			this.agrupaEntidad.entidadAgrupa.descripcionView = entryVal.idEntidad;//   + ' ' +  entryVal.nombre;

		  });
	   }
     }
   });
  }
  
}