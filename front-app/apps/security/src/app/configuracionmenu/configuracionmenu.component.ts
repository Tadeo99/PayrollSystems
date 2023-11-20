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

import {ConfiguracionMenuService} from "./configuracionmenu.service";
import {ConfiguracionMenu} from "../../../models/seguridad/configuracionmenu.model";
import {Menu} from "../../../models/seguridad/menu.model";
import {Item} from "../../../models/common/item.model";
import {Properties} from "../../../models/seguridad/properties.model";


/**
 * La Class ConfiguracionMenuComponent.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Mon Oct 16 14:31:00 COT 2017
 * @since SIAA-CORE 2.1
 */
 
@Component({
  selector: 'app-configuracionmenu',
  templateUrl: './configuracionmenu.component.html',
  styleUrls: ['./configuracionmenu.component.css'],
  providers: [ConfiguracionMenuService]
})
export class ConfiguracionMenuComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto configuracion menu. */
    public configuracionMenu : ConfiguracionMenu = new ConfiguracionMenu();
	 
	/** La lista configuracion menu. */
    public listaConfiguracionMenu : ConfiguracionMenu[] = [];
    
	/** La lista item select. */
    public listaConfiguracionMenuSelectMap : Map<string,ConfiguracionMenu> = new Map<string,ConfiguracionMenu>();

	
	constructor(public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private configuracionMenuService: ConfiguracionMenuService,
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
				this.configuracionMenuService.crear(this.configuracionMenu).subscribe(
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
				this.configuracionMenuService.modificar(this.configuracionMenu,this.configuracionMenu.idConfiguracionMenu).subscribe(
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
	 	this.configuracionMenu  = new ConfiguracionMenu();
		this.mostrarPanelForm = true;
		this.accionNuevo = true;
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
			this.listaConfiguracionMenu= [];
			this.limpiaDataProvider(this.search);
			this.configuracionMenu = new ConfiguracionMenu();
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
			  this.configuracionMenuService.eliminar(id).subscribe(
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
	public confirmarEliminar(configuracionMenuTemp : ConfiguracionMenu) {
		this.configuracionMenu = configuracionMenuTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.configuracionMenu.idConfiguracionMenu);
		 }
		});
    }
	  
	/**
	 * accion menu.
	 *
	 */
	 public accionMenu(item : any) {
		 if (item.click == 'confirmarEliminar') {
		     this.confirmarEliminar(this.configuracionMenu);
		 }
	}
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(configuracionMenuTemp : ConfiguracionMenu) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.configuracionMenu = Object.assign({}, configuracionMenuTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.configuracionMenu = Object.assign({}, configuracionMenuTemp);
					this.lanzarConfiguracionMenu();
				}
			}
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(configuracionMenuTemp : ConfiguracionMenu) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				configuracionMenuTemp.checked = true;
				this.agregarCheck(configuracionMenuTemp);
				this.dialogRef.close(this.listaConfiguracionMenuSelectMap);
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
			this.listaConfiguracionMenu = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.configuracionMenuService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaConfiguracionMenu = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaConfiguracionMenu.length == 1) {
            this.asociar(this.listaConfiguracionMenu[0]);
		}
	}
	/**
	 * Buscar paginado.
	 */
	public buscarPaginado () {
		this.startProgres();
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.configuracionMenuService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaConfiguracionMenu = data.listaResultado;
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
	
  public lanzarConfiguracionMenu(){
    // Usamos el mÃ©todo emit
    this.change.emit({configuracionMenu: this.configuracionMenu});
  }
  
   /*
   agregar check
   */
  public agregarCheck(configuracionMenuTemp : ConfiguracionMenu) {
     if (configuracionMenuTemp.checked) {
		 if (this.listaConfiguracionMenuSelectMap.get(configuracionMenuTemp.idConfiguracionMenu) == null) {
			this.listaConfiguracionMenuSelectMap.set(configuracionMenuTemp.idConfiguracionMenu,configuracionMenuTemp);
		 }        
	 } else {
		if ((this.listaConfiguracionMenuSelectMap.get(configuracionMenuTemp.idConfiguracionMenu) != null)) {
			this.listaConfiguracionMenuSelectMap.delete(configuracionMenuTemp.idConfiguracionMenu);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaConfiguracionMenu.forEach((data) => {
       if ((this.listaConfiguracionMenuSelectMap.get(data.idConfiguracionMenu) != null)) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal Menu input
  */	
  public abrirModalMenuInput(pSearch? : string) {
	 this.configuracionMenu.menu.idMenu = null;
	 this.abrirModalMenu(pSearch);
  }

  /**
	El abrir modal menu. 
  */   
  public abrirModalMenu(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Menu  = new Menu();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalMenu = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
	  let listaMenuSelectMap = result;
	  if (listaMenuSelectMap) {
		  listaMenuSelectMap.forEach((entryVal : any , entryKey : any )  => {
			this.configuracionMenu.menu = entryVal;
			this.configuracionMenu.menu.descripcionView = entryVal.idMenu;//   + ' ' +  entryVal.nombre;
		  });
	   }
     }
   });
  }
  /**
  Abrir modal Item input
  */	
  public abrirModalItemInput(pSearch? : string) {
	 this.configuracionMenu.itemByComponente.idItem = null;
	 this.abrirModalItem(pSearch);
  }

  /**
	El abrir modal item. 
  */   
  public abrirModalItem(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Item  = new Item();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   dialogRef.componentInstance.esModalItem = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
	  let listaItemSelectMap = result;
	  if (listaItemSelectMap) {
		  listaItemSelectMap.forEach((entryVal : any , entryKey : any )  => {
			this.configuracionMenu.itemByComponente = entryVal;
			this.configuracionMenu.itemByComponente.descripcionView = entryVal.idItem;//   + ' ' +  entryVal.nombre;
		  });
	   }
     }
   });
  }
  /**
  Abrir modal Properties input
  */	
  public abrirModalPropertiesInput(pSearch? : string) {
	 this.configuracionMenu.properties.idProperties = null;
	 this.abrirModalProperties(pSearch);
  }

  /**
	El abrir modal properties. 
  */   
  public abrirModalProperties(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Properties  = new Properties();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalProperties = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
	  let listaPropertiesSelectMap = result;
	  if (listaPropertiesSelectMap) {
		  listaPropertiesSelectMap.forEach((entryVal : any , entryKey : any )  => {
			this.configuracionMenu.properties = entryVal;
			this.configuracionMenu.properties.descripcionView = entryVal.idProperties;//   + ' ' +  entryVal.nombre;
		  });
	   }
     }
   });
  }
}