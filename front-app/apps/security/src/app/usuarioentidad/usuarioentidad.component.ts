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

import {UsuarioEntidadService} from "./usuarioentidad.service";
import {UsuarioEntidad} from "../../../models/seguridad/usuarioentidad.model";
import {Usuario} from "../../../models/seguridad/usuario.model";
import {Entidad} from "../../../models/seguridad/entidad.model";

import { DialogContentOverride } from '../administrarsistema/administrarsistema.component';

/**
 * La Class UsuarioEntidadComponent.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sat Dec 23 17:16:38 COT 2017
 * @since SIAA-CORE 2.1
 */
 
@Component({
  selector: 'app-usuarioentidad',
  templateUrl: './usuarioentidad.component.html',
  styleUrls: ['./usuarioentidad.component.css'],
  providers: [UsuarioEntidadService]
})
export class UsuarioEntidadComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto usuario entidad. */
    public usuarioEntidad : UsuarioEntidad = new UsuarioEntidad();
	 
	/** La lista usuario entidad. */
    public listaUsuarioEntidad : UsuarioEntidad[] = [];
    
	/** La lista item select. */
    public listaUsuarioEntidadSelectMap : Map<string,UsuarioEntidad> = new Map<string,UsuarioEntidad>();

	
	constructor(public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private usuarioEntidadService: UsuarioEntidadService,
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
				this.usuarioEntidadService.crear(this.usuarioEntidad).subscribe(
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
				this.usuarioEntidadService.modificar(this.usuarioEntidad,this.usuarioEntidad.idUsuarioEntidad).subscribe(
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
		this.usuarioEntidad.entidad = new Entidad();
		this.usuarioEntidad.estado = '';
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
	    //this.limpiar();	
	}
	
	/**
	 * Limpiar.
	 *
	 */
	private limpiar () {
		try {
			this.listaUsuarioEntidad= [];
			this.limpiaDataProvider(this.search);
			this.usuarioEntidad = new UsuarioEntidad();
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
			  this.usuarioEntidadService.eliminar(id).subscribe(
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
	public confirmarEliminar(usuarioEntidadTemp : UsuarioEntidad) {
		this.usuarioEntidad = usuarioEntidadTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.usuarioEntidad.idUsuarioEntidad);
		 }
		});
    }
	  
	/**
	 * accion menu.
	 *
	 */
	 public accionMenu(item : any) {
		 if (item.click == 'confirmarEliminar') {
		     this.confirmarEliminar(this.usuarioEntidad);
		 }
	}
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(usuarioEntidadTemp : UsuarioEntidad) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				let usuarioentidadSelect : UsuarioEntidad = Object.assign({}, this.usuarioEntidad);
				this.usuarioEntidad = Object.assign({}, usuarioEntidadTemp);
				this.usuarioEntidad.usuario = usuarioentidadSelect.usuario;

				this.usuarioEntidad.entidad.descripcionView = this.usuarioEntidad.entidad.codigo + ' ' +  this.usuarioEntidad.entidad.nombre;

				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.usuarioEntidad = Object.assign({}, usuarioEntidadTemp);
					this.lanzarUsuarioEntidad();
				}
			}
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(usuarioEntidadTemp : UsuarioEntidad) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				usuarioEntidadTemp.checked = true;
				this.agregarCheck(usuarioEntidadTemp);
				this.dialogRef.close(this.listaUsuarioEntidadSelectMap);
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
			this.listaUsuarioEntidad = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.params = this.params.set("id",this.usuarioEntidad.usuario.idUsuario);
			this.usuarioEntidadService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaUsuarioEntidad = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaUsuarioEntidad.length == 1) {
            this.asociar(this.listaUsuarioEntidad[0]);
		}
	}
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado () {
		this.startProgres();
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.usuarioEntidadService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaUsuarioEntidad = data.listaResultado;
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
	
  public lanzarUsuarioEntidad(){
    // Usamos el mÃ©todo emit
    this.change.emit({usuarioEntidad: this.usuarioEntidad});
  }
  
   /*
   agregar check
   */
  public agregarCheck(usuarioEntidadTemp : UsuarioEntidad) {
     if (usuarioEntidadTemp.checked) {
		 if (this.listaUsuarioEntidadSelectMap.get(usuarioEntidadTemp.idUsuarioEntidad) == null) {
			this.listaUsuarioEntidadSelectMap.set(usuarioEntidadTemp.idUsuarioEntidad,usuarioEntidadTemp);
		 }        
	 } else {
		if ((this.listaUsuarioEntidadSelectMap.get(usuarioEntidadTemp.idUsuarioEntidad) != null)) {
			this.listaUsuarioEntidadSelectMap.delete(usuarioEntidadTemp.idUsuarioEntidad);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaUsuarioEntidad.forEach((data) => {
       if ((this.listaUsuarioEntidadSelectMap.get(data.idUsuarioEntidad) != null)) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal Usuario input
  */	
  public abrirModalUsuarioInput(pSearch? : string) {
	 this.usuarioEntidad.usuario.idUsuario = null;
	 this.abrirModalUsuario(pSearch);
  }

  /**
	El abrir modal usuario. 
  */   
  public abrirModalUsuario(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContentOverride);
   let data : Usuario  = new Usuario();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   dialogRef.componentInstance.esModalUsuario = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
	  let listaUsuarioSelectMap = result;
	  if (listaUsuarioSelectMap) {
		  listaUsuarioSelectMap.forEach((entryVal : any , entryKey : any )  => {
			this.usuarioEntidad.usuario = entryVal;
			this.usuarioEntidad.usuario.descripcionView = entryVal.userName + ' ' +  entryVal.nombre + ' ' +  entryVal.apellidoPaterno + ' ' +  entryVal.apellidoMaterno;
            this.buscar();
		  });
	   }
     }
   });
  }
  /**
  Abrir modal Entidad input
  */	
  public abrirModalEntidadInput(pSearch? : string) {
	 this.usuarioEntidad.entidad.idEntidad = null;
	 this.abrirModalEntidad(pSearch);
  }

  /**
	El abrir modal entidad. 
  */   
  public abrirModalEntidad(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : any  = {};
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   dialogRef.componentInstance.esModalEntidadSelect = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
	  let listaEntidadSelectMap = result;
	  if (listaEntidadSelectMap) {
		  listaEntidadSelectMap.forEach((entryVal : any , entryKey : any )  => {
			this.usuarioEntidad.entidad = entryVal;
			this.usuarioEntidad.entidad.descripcionView = entryVal.codigo + ' ' +  entryVal.nombre;
		  });
	   }
     }
   });
  }
}