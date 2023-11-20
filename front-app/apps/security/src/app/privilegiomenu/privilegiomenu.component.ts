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

import {PrivilegioMenuService} from "./privilegiomenu.service";
import {PrivilegioMenu} from "../../../models/seguridad/privilegiomenu.model";
import {Menu} from "../../../models/seguridad/menu.model";
import {Privilegio} from "../../../models/seguridad/privilegio.model";


/**
 * La Class PrivilegioMenuComponent.
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
  selector: 'app-privilegiomenu',
  templateUrl: './privilegiomenu.component.html',
  styleUrls: ['./privilegiomenu.component.css'],
  providers: [PrivilegioMenuService]
})
export class PrivilegioMenuComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto privilegio menu. */
    public privilegioMenu : PrivilegioMenu = new PrivilegioMenu();
	 
	/** La lista privilegio menu. */
    public listaPrivilegioMenu : PrivilegioMenu[] = [];
    
	/** La lista item select. */
    public listaPrivilegioMenuSelectMap : Map<string,PrivilegioMenu> = new Map<string,PrivilegioMenu>();

	
	constructor(public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private privilegioMenuService: PrivilegioMenuService,
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
				this.privilegioMenuService.crear(this.privilegioMenu).subscribe(
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
				this.privilegioMenuService.modificar(this.privilegioMenu,this.privilegioMenu.idPrivilegioMenu).subscribe(
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
	 	this.privilegioMenu  = new PrivilegioMenu();
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
			this.listaPrivilegioMenu= [];
			this.limpiaDataProvider(this.search);
			this.privilegioMenu = new PrivilegioMenu();
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
			  this.privilegioMenuService.eliminar(id).subscribe(
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
	public confirmarEliminar(privilegioMenuTemp : PrivilegioMenu) {
		this.privilegioMenu = privilegioMenuTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.privilegioMenu.idPrivilegioMenu);
		 }
		});
    }
	  
	/**
	 * accion menu.
	 *
	 */
	 public accionMenu(item : any) {
		 if (item.click == 'confirmarEliminar') {
		     this.confirmarEliminar(this.privilegioMenu);
		 }
	}
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(privilegioMenuTemp : PrivilegioMenu) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.privilegioMenu = Object.assign({}, privilegioMenuTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.privilegioMenu = Object.assign({}, privilegioMenuTemp);
					this.lanzarPrivilegioMenu();
				}
			}
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(privilegioMenuTemp : PrivilegioMenu) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				privilegioMenuTemp.checked = true;
				this.agregarCheck(privilegioMenuTemp);
				this.dialogRef.close(this.listaPrivilegioMenuSelectMap);
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
			this.listaPrivilegioMenu = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.privilegioMenuService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaPrivilegioMenu = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaPrivilegioMenu.length == 1) {
            this.asociar(this.listaPrivilegioMenu[0]);
		}
	}
	/**
	 * Buscar paginado.
	 */
	public buscarPaginado () {
		this.startProgres();
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.privilegioMenuService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaPrivilegioMenu = data.listaResultado;
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
	
  public lanzarPrivilegioMenu(){
    // Usamos el mÃ©todo emit
    this.change.emit({privilegioMenu: this.privilegioMenu});
  }
  
   /*
   agregar check
   */
  public agregarCheck(privilegioMenuTemp : PrivilegioMenu) {
     if (privilegioMenuTemp.checked) {
		 if (this.listaPrivilegioMenuSelectMap.get(privilegioMenuTemp.idPrivilegioMenu) == null) {
			this.listaPrivilegioMenuSelectMap.set(privilegioMenuTemp.idPrivilegioMenu,privilegioMenuTemp);
		 }        
	 } else {
		if ((this.listaPrivilegioMenuSelectMap.get(privilegioMenuTemp.idPrivilegioMenu) != null)) {
			this.listaPrivilegioMenuSelectMap.delete(privilegioMenuTemp.idPrivilegioMenu);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaPrivilegioMenu.forEach((data) => {
       if ((this.listaPrivilegioMenuSelectMap.get(data.idPrivilegioMenu) != null)) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal Menu input
  */	
  public abrirModalMenuInput(pSearch? : string) {
	 this.privilegioMenu.menu.idMenu = null;
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
  // dialogRef.componentInstance.esModalMenu = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
	  let listaMenuSelectMap = result;
	  if (listaMenuSelectMap) {
		  listaMenuSelectMap.forEach((entryVal : any , entryKey : any )  => {
			this.privilegioMenu.menu = entryVal;
			this.privilegioMenu.menu.descripcionView = entryVal.idMenu;//   + ' ' +  entryVal.nombre;
		  });
	   }
     }
   });
  }
  /**
  Abrir modal Privilegio input
  */	
  public abrirModalPrivilegioInput(pSearch? : string) {
	 this.privilegioMenu.privilegio.idPrivilegio = null;
	 this.abrirModalPrivilegio(pSearch);
  }

  /**
	El abrir modal privilegio. 
  */   
  public abrirModalPrivilegio(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Privilegio  = new Privilegio();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalPrivilegio = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
	  let listaPrivilegioSelectMap = result;
	  if (listaPrivilegioSelectMap) {
		  listaPrivilegioSelectMap.forEach((entryVal : any , entryKey : any )  => {
			this.privilegioMenu.privilegio = entryVal;
			this.privilegioMenu.privilegio.descripcionView = entryVal.idPrivilegio;//   + ' ' +  entryVal.nombre;
		  });
	   }
     }
   });
  }
}