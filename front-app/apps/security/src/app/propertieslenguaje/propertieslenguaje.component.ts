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

import {PropertiesLenguajeService} from "./propertieslenguaje.service";
import {PropertiesLenguaje} from "../../../models/seguridad/propertieslenguaje.model";
import {Item} from "../../../models/common/item.model";
import {Properties} from "../../../models/seguridad/properties.model";


/**
 * La Class PropertiesLenguajeComponent.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Mon Oct 16 14:31:03 COT 2017
 * @since SIAA-CORE 2.1
 */
 
@Component({
  selector: 'app-propertieslenguaje',
  templateUrl: './propertieslenguaje.component.html',
  styleUrls: ['./propertieslenguaje.component.css'],
  providers: [PropertiesLenguajeService]
})
export class PropertiesLenguajeComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto properties lenguaje. */
    public propertiesLenguaje : PropertiesLenguaje = new PropertiesLenguaje();
	 
	/** La lista properties lenguaje. */
    public listaPropertiesLenguaje : PropertiesLenguaje[] = [];
    
	/** La lista item select. */
    public listaPropertiesLenguajeSelectMap : Map<string,PropertiesLenguaje> = new Map<string,PropertiesLenguaje>();

	
	constructor(public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private propertiesLenguajeService: PropertiesLenguajeService,
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
				this.propertiesLenguajeService.crear(this.propertiesLenguaje).subscribe(
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
				this.propertiesLenguajeService.modificar(this.propertiesLenguaje,this.propertiesLenguaje.idPropertiesLenguaje).subscribe(
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
	 	this.propertiesLenguaje  = new PropertiesLenguaje();
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
			this.listaPropertiesLenguaje= [];
			this.limpiaDataProvider(this.search);
			this.propertiesLenguaje = new PropertiesLenguaje();
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
			  this.propertiesLenguajeService.eliminar(id).subscribe(
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
	public confirmarEliminar(propertiesLenguajeTemp : PropertiesLenguaje) {
		this.propertiesLenguaje = propertiesLenguajeTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.propertiesLenguaje.idPropertiesLenguaje);
		 }
		});
    }
	  
	/**
	 * accion menu.
	 *
	 */
	 public accionMenu(item : any) {
		 if (item.click == 'confirmarEliminar') {
		     this.confirmarEliminar(this.propertiesLenguaje);
		 }
	}
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(propertiesLenguajeTemp : PropertiesLenguaje) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.propertiesLenguaje = Object.assign({}, propertiesLenguajeTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.propertiesLenguaje = Object.assign({}, propertiesLenguajeTemp);
					this.lanzarPropertiesLenguaje();
				}
			}
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(propertiesLenguajeTemp : PropertiesLenguaje) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				propertiesLenguajeTemp.checked = true;
				this.agregarCheck(propertiesLenguajeTemp);
				this.dialogRef.close(this.listaPropertiesLenguajeSelectMap);
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
			this.listaPropertiesLenguaje = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.propertiesLenguajeService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaPropertiesLenguaje = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaPropertiesLenguaje.length == 1) {
            this.asociar(this.listaPropertiesLenguaje[0]);
		}
	}
	/**
	 * Buscar paginado.
	 */
	public buscarPaginado () {
		this.startProgres();
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.propertiesLenguajeService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaPropertiesLenguaje = data.listaResultado;
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
	
  public lanzarPropertiesLenguaje(){
    // Usamos el mÃ©todo emit
    this.change.emit({propertiesLenguaje: this.propertiesLenguaje});
  }
  
   /*
   agregar check
   */
  public agregarCheck(propertiesLenguajeTemp : PropertiesLenguaje) {
     if (propertiesLenguajeTemp.checked) {
		 if (this.listaPropertiesLenguajeSelectMap.get(propertiesLenguajeTemp.idPropertiesLenguaje) == null) {
			this.listaPropertiesLenguajeSelectMap.set(propertiesLenguajeTemp.idPropertiesLenguaje,propertiesLenguajeTemp);
		 }        
	 } else {
		if ((this.listaPropertiesLenguajeSelectMap.get(propertiesLenguajeTemp.idPropertiesLenguaje) != null)) {
			this.listaPropertiesLenguajeSelectMap.delete(propertiesLenguajeTemp.idPropertiesLenguaje);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaPropertiesLenguaje.forEach((data) => {
       if ((this.listaPropertiesLenguajeSelectMap.get(data.idPropertiesLenguaje) != null)) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal Item input
  */	
  public abrirModalItemInput(pSearch? : string) {
	 this.propertiesLenguaje.itemByLenguaje.idItem = null;
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
			this.propertiesLenguaje.itemByLenguaje = entryVal;
			this.propertiesLenguaje.itemByLenguaje.descripcionView = entryVal.idItem;//   + ' ' +  entryVal.nombre;
		  });
	   }
     }
   });
  }
  /**
  Abrir modal Properties input
  */	
  public abrirModalPropertiesInput(pSearch? : string) {
	 this.propertiesLenguaje.properties.idProperties = null;
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
  // dialogRef.componentInstance.esModalProperties = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
	  let listaPropertiesSelectMap = result;
	  if (listaPropertiesSelectMap) {
		  listaPropertiesSelectMap.forEach((entryVal : any , entryKey : any )  => {
			this.propertiesLenguaje.properties = entryVal;
			this.propertiesLenguaje.properties.descripcionView = entryVal.idProperties;//   + ' ' +  entryVal.nombre;
		  });
	   }
     }
   });
  }
}