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

import {MenuPersonalizadoService} from "./menupersonalizado.service";
import {PrivilegioPersonalizadoService} from "../privilegiopersonalizado/privilegiopersonalizado.service";
import {MenuPersonalizado} from "../../../models/seguridad/menupersonalizado.model";
import {Usuario} from "../../../models/seguridad/usuario.model";
import {Menu} from "../../../models/seguridad/menu.model";
import {Sistema} from "../../../models/seguridad/sistema.model";
import {Privilegio} from "../../../models/seguridad/privilegio.model";

import { DialogContentOverride } from '../administrarsistema/administrarsistema.component';
/**
 * La Class MenuPersonalizadoComponent.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Mon Oct 16 14:31:02 COT 2017
 * @since SIAA-CORE 2.1
 */
 
@Component({
  selector: 'app-menupersonalizado',
  templateUrl: './menupersonalizado.component.html',
  styleUrls: ['./menupersonalizado.component.css'],
  providers: [MenuPersonalizadoService,PrivilegioPersonalizadoService]
})
export class MenuPersonalizadoComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto menu personalizado. */
    public usuario : Usuario = new Usuario();
	 
	/** La lista menu personalizado. */
    public listaMenuPersonalizado : Menu[] = [];
    
	/** La lista item select. */
    public listaMenuPersonalizadoSelectMap : Map<number,Menu> = new Map<number,Menu>();

	/** El objeto menu. */
	public menu : Menu = new Menu();
	
	public menuDependencia  : Menu = new Menu();
	
	public sistema  : Sistema = new Sistema();

	//
	public listaPrivilegioPersonalizadoSelectMap : Map<number,Privilegio> = new Map<number,Privilegio>();
	public listaIdMenu : number[] = [];
	public fechaActual : Date = null;
	
	constructor(public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private menuPersonalizadoService: MenuPersonalizadoService,
		private privilegioPersonalizadoService : PrivilegioPersonalizadoService,
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
			let listaMenuTemp : Menu[] = [];
			this.listaMenuPersonalizadoSelectMap.forEach((entryVal : any , entryKey : any )  => {
				entryVal.id = this.usuario.idUsuario;
				listaMenuTemp.push(entryVal);
			 });	

			 let listaPrivilegioTemp : Privilegio[] = [];
			 this.listaPrivilegioPersonalizadoSelectMap.forEach((entryVal : any , entryKey : any )  => {
				 entryVal.id = this.usuario.idUsuario;
				 listaPrivilegioTemp.push(entryVal);
			  });

				this.menuPersonalizadoService.crear(listaMenuTemp).subscribe(
			    data => {
					if (this.isProcesoOK(data)) {
						this.privilegioPersonalizadoService.crear(listaPrivilegioTemp).subscribe(
							dataPrivilegio => {
								if (this.isProcesoOK(dataPrivilegio)) {
									this.guardoExito();
									this.buscar();
								}
							},
							errorPrivilegio => {
								this.mostrarMensajeError(errorPrivilegio);
							},
						   ()=> {
							 this.endProgres();
						   }
						  );
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
	 * Inicializar.
	 *
	 */
	private inicializar() {
		super.getUsuarioSession();
	    this.limpiar();	
	}
	
	/**
	 * Limpiar.
	 *
	 */
	private limpiar () {
		try {
			this.listaMenuPersonalizado= [];
			this.limpiaDataProvider(this.search);
			this.buscar();
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
			this.listaMenuPersonalizado = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.menuPersonalizadoService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaMenuPersonalizado = data.listaResultado;
					this.verificarCheck();
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

	/**
	 * Buscar paginado.
	 */
	public buscarPaginado () {
		this.startProgres();
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.menuPersonalizadoService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaMenuPersonalizado = data.listaResultado;
						this.verificarCheck();
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
	this.verDependeciaView(this.menuDependencia);
   }
	
  public lanzarMenuPersonalizado(){
    // Usamos el mÃ©todo emit
  }
  
   /*
   agregar check
   */
  public agregarCheck(menuTemp : Menu) {
     if (menuTemp.checked) {
		let index = this.listaIdMenu.indexOf(menuTemp.idMenu);
		if (index < 0) {
			this.listaIdMenu.push(menuTemp.idMenu);
			this.fechaActual = new Date();
		}
		this.listaMenuPersonalizadoSelectMap.set(menuTemp.idMenu,menuTemp);
	 } else {
		this.listaMenuPersonalizadoSelectMap.set(menuTemp.idMenu,menuTemp);
		let index = this.listaIdMenu.indexOf(menuTemp.idMenu);
		if (index >= 0) {
			this.listaIdMenu.splice(index,1);
			this.fechaActual = new Date();
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	  if (this.listaMenuPersonalizado != null) {
			this.listaMenuPersonalizado.forEach((data) => {
				if (data.checked) {
					let index = this.listaIdMenu.indexOf(data.idMenu);
					if (index < 0) {
						this.listaIdMenu.push(data.idMenu);
						this.fechaActual = new Date();
					}
				} else {
					let index = this.listaIdMenu.indexOf(data.idMenu);
					if (index >= 0) {
						this.listaIdMenu.splice(index,1);
						this.fechaActual = new Date();
					}
				}
		       if ((this.listaMenuPersonalizadoSelectMap.has(data.idMenu))) {
				   let dataTemp : Menu = this.listaMenuPersonalizadoSelectMap.get(data.idMenu);
				   if (dataTemp.checked == true) {
						data.checked = true;
						let index = this.listaIdMenu.indexOf(data.idMenu);
						if (index < 0) {
							this.listaIdMenu.push(data.idMenu);
							this.fechaActual = new Date();
						}
				   } else {
					let index = this.listaIdMenu.indexOf(data.idMenu);
					if (index >= 0) {
						this.listaIdMenu.splice(index,1);
						this.fechaActual = new Date();
					}
				   }			
			   }
			});
        }
  }
  
  /**
  Abrir modal Persona input
  */	
  public abrirModalPersonaInput(pSearch? : string) {
	 this.usuario.idUsuario = null;
	 this.params = this.params.set('idUsuario', '');
	 this.abrirModalPersona(pSearch);
  }

  /**
	El abrir modal persona. 
  */   
  public abrirModalPersona(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContentOverride);
   let data : Usuario  = new Usuario();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   dialogRef.componentInstance.esModalUsuario = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
	  let listaPersonaSelectMap = result;
	  if (listaPersonaSelectMap) {
		  listaPersonaSelectMap.forEach((entryVal : any , entryKey : any )  => {
			this.usuario = entryVal;
			this.usuario.descripcionView = entryVal.userName + ' ' +  entryVal.nombre + ' ' +  entryVal.apellidoPaterno + ' ' +  entryVal.apellidoMaterno;
			this.params = this.params.set('idUsuario', entryVal.idUsuario + '');
			this.listaMenuPersonalizadoSelectMap = new Map<number,Menu>();

		  });
	   }
     }
   });
  }
  
public verDependecia (menuDependencia : Menu ) {	
	this.verDependeciaView(menuDependencia);
	this.buscar();
}
private verDependeciaView (menuDependencia : Menu ) {	
	this.params = this.params.set("id",''); 
	this.descripcion = '';
	this.menuDependencia = menuDependencia;
	this.descripcion = menuDependencia.idMenu + ' ' +  menuDependencia.nombre;
	this.params = this.params.set("id",menuDependencia.idMenu + ''); 	
}

/**
  Abrir modal Sistema input
  */	
  public abrirModalSistemaInput(pSearch? : string) {
	this.menu.sistema.idSistema = null;
	this.params = this.params.set("idSistema",null);
	this.abrirModalSistema(pSearch);
 }

 /**
   El abrir modal sistema. 
 */   
 public abrirModalSistema(pSearch? : string) {
  let dialogRef = this.dialog.open(DialogContentOverride);
  let data : Sistema  = new Sistema();
 // data.id = id;
  dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
  dialogRef.componentInstance.esModalSistema = true;
  dialogRef.afterClosed().subscribe(result => {
  if (result) {
	 let listaSistemaSelectMap = result;
	 if (listaSistemaSelectMap) {
		 listaSistemaSelectMap.forEach((entryVal : any , entryKey : any )  => {
		   this.params = this.params.set("idSistema",entryVal.idSistema);
		   this.sistema = entryVal;
		   this.sistema.descripcionView = entryVal.idSistema  + ' ' +  entryVal.nombre;
		   this.buscar();
		 });
	  }
	}
  });
 }

 public regresarDependencia() {
   this.search = '';
   if((this.menuDependencia.menuPadre != null) && (this.menuDependencia.menuPadre != null && this.menuDependencia.menuPadre.idMenu)) {
	   this.verDependecia(this.menuDependencia.menuPadre);
   } else {
	   this.params = this.params.set("id",'');
	   this.descripcion = '';
	   this.buscar();
   }
}

}