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

import {ConfiguracionAtributoService} from "./configuracionatributo.service";
import {ConfiguracionAtributo} from "../../../models/common/configuracionatributo.model";
import {Item} from "../../../models/common/item.model";
import {ListaItems} from "../../../models/common/listaitems.model";
import {ListaItemType} from "../../../type/listaitem.type";
import {EstadoGeneralState} from "../../../type/estadogeneral.state";

import { DinamicFormularioAtributoComponent } from '../dinamicformularioatributo/dinamicformularioatributo.component';
/**
 * La Class ConfiguracionAtributoComponent.
 * <ul>
 * <li>Copyright 2019 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sat Dec 23 17:16:34 COT 2017
 * @since BUILDERP-CORE 2.1
 */
 
@Component({
  selector: 'app-configuracionatributo',
  templateUrl: './configuracionatributo.component.html',
  styleUrls: ['./configuracionatributo.component.css'],
  providers: [ConfiguracionAtributoService]
})
export class ConfiguracionAtributoComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
		
	public ITEM_ENTIDAD_CONFIGURAR :number = ListaItemType.ITEM_ENTIDAD_CONFIGURAR;
  public ITEM_TIPO_COMPONENTE_CONFIGURAR  :number = ListaItemType.ITEM_TIPO_COMPONENTE_CONFIGURAR;

	/** El objeto configuracion atributo. */
    public configuracionAtributo : ConfiguracionAtributo = new ConfiguracionAtributo();
	 
	/** La lista configuracion atributo. */
    public listaConfiguracionAtributo : ConfiguracionAtributo[] = [];
    
	/** La lista item select. */
    public listaConfiguracionAtributoSelectMap : Map<string,ConfiguracionAtributo> = new Map<string,ConfiguracionAtributo>();
		
	constructor(public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private configuracionAtributoService: ConfiguracionAtributoService,
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
	 * guardar.
	 *
	 */
	public guardar () {
		console.log("this.configuracionAtributo.listaDataDinamic  == > " + this.configuracionAtributo.listaDataDinamic.length);
		try {
			this.limpiarFrmDataDinamic();
			if (this.accionNuevo) {			
				this.configuracionAtributoService.crear(this.configuracionAtributo).subscribe(
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
				this.configuracionAtributoService.modificar(this.configuracionAtributo,this.configuracionAtributo.idConfiguracionAtributo).subscribe(
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
	private limpiarFrmDataDinamic() {
		this.configuracionAtributo.listaDataDinamic.forEach(data => {
			data.listaSelectItemVO = [];
		});
	}
	
	/**
	 * Nuevo.
	 *
	 */
	public async nuevo () {
		this.configuracionAtributo  = new ConfiguracionAtributo();
		this.configuracionAtributo.esPersistente = true;
		this.configuracionAtributo.estado = EstadoGeneralState.ACTIVO.toString();
		this.mostrarPanelForm = true;
		this.accionNuevo = true;
		this.obtenerListaDataDinamic();
	}
	private async obtenerListaDataDinamic(){
		this.configuracionAtributo.listaDataDinamic = <ConfiguracionAtributo[]> await  this.commonServiceImpl.listaDataDinamic('PRODUCTO',this.configuracionAtributo.idConfiguracionAtributo);
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
			this.listaConfiguracionAtributo= [];
			this.limpiaDataProvider(this.search);
			this.configuracionAtributo = new ConfiguracionAtributo();
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
			  this.configuracionAtributoService.eliminar(id).subscribe(
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
	public confirmarEliminar(configuracionAtributoTemp : ConfiguracionAtributo) {
		this.configuracionAtributo = configuracionAtributoTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.configuracionAtributo.idConfiguracionAtributo);
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public async buscarID(configuracionAtributoTemp : ConfiguracionAtributo) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.configuracionAtributo = Object.assign({}, configuracionAtributoTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
				if (this.configuracionAtributo.itemByIdNombreEntidad == null) {
					this.configuracionAtributo.itemByIdNombreEntidad = new Item();
				}
				if (this.configuracionAtributo.itemByIdComponte == null) {
					this.configuracionAtributo.itemByIdComponte = new Item();
				}
				if (this.configuracionAtributo.listaItem == null ) {
					this.configuracionAtributo.listaItem = new ListaItems();
				}
				this.obtenerListaDataDinamic();
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.configuracionAtributo = Object.assign({}, configuracionAtributoTemp);
					this.lanzarConfiguracionAtributo();
				}
			}
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(configuracionAtributoTemp : ConfiguracionAtributo) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				configuracionAtributoTemp.checked = true;
				this.agregarCheck(configuracionAtributoTemp);
				this.dialogRef.close(this.listaConfiguracionAtributoSelectMap);
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
			this.listaConfiguracionAtributo = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.configuracionAtributoService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaConfiguracionAtributo = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaConfiguracionAtributo.length == 1) {
            this.asociar(this.listaConfiguracionAtributo[0]);
		}
	}
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado () {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.configuracionAtributoService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaConfiguracionAtributo = data.listaResultado;
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
	
  public lanzarConfiguracionAtributo(){
    // Usamos el mÃ©todo emit
    this.change.emit({configuracionAtributo: this.configuracionAtributo});
  }
  
   /*
   agregar check
   */
  public agregarCheck(configuracionAtributoTemp : ConfiguracionAtributo) {
    if (configuracionAtributoTemp.checked) {
		 if (!this.listaConfiguracionAtributoSelectMap.has(configuracionAtributoTemp.idConfiguracionAtributo)) {
			this.listaConfiguracionAtributoSelectMap.set(configuracionAtributoTemp.idConfiguracionAtributo,configuracionAtributoTemp);
		 }        
	 } else {
		if ((this.listaConfiguracionAtributoSelectMap.has(configuracionAtributoTemp.idConfiguracionAtributo))) {
			this.listaConfiguracionAtributoSelectMap.delete(configuracionAtributoTemp.idConfiguracionAtributo);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaConfiguracionAtributo.forEach((data) => {
       if ((this.listaConfiguracionAtributoSelectMap.has(data.idConfiguracionAtributo))) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal Item input
  */	
  public abrirModalItemInput(idListaItem: number,pSearch? : string) {
		if (idListaItem == this.ITEM_ENTIDAD_CONFIGURAR) {
	 			this.configuracionAtributo.itemByIdNombreEntidad.idItem = null;
		} else if (idListaItem == this.ITEM_TIPO_COMPONENTE_CONFIGURAR) {
	 			this.configuracionAtributo.itemByIdComponte.idItem = null;
		}
	 this.abrirModalItem(idListaItem,pSearch);
  }

  /**
	El abrir modal item. 
  */   
  public abrirModalItem(idListaItem: number,pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
	 let listaItem : ListaItems  = new ListaItems();
   listaItem.idListaItems = idListaItem;
   dialogRef = this.abrirModal(dialogRef,true,false,false,listaItem,pSearch);
   dialogRef.componentInstance.esModalItem = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
	  let listaItemSelectMap = result;
	  if (listaItemSelectMap) {
		  listaItemSelectMap.forEach((entryVal : any , entryKey : any )  => {
			if (idListaItem == this.ITEM_ENTIDAD_CONFIGURAR) {
					this.configuracionAtributo.itemByIdNombreEntidad = entryVal;
			} else if (idListaItem == this.ITEM_TIPO_COMPONENTE_CONFIGURAR) {
					this.configuracionAtributo.itemByIdComponte = entryVal;
	   	} 
		  });
	   }
     }
   });
  }
 
  /**
  Abrir modal ListaItems input
  */	
  public abrirModalListaItemsInput(pSearch? : string) {
	 this.configuracionAtributo.listaItem.idListaItems = null;
	 this.abrirModalListaItems(pSearch);
  }

  /**
	El abrir modal lista items. 
  */   
  public abrirModalListaItems(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : ListaItems  = new ListaItems();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   dialogRef.componentInstance.esModalListaItem = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
	  let listaListaItemsSelectMap = result;
	  if (listaListaItemsSelectMap) {
		  listaListaItemsSelectMap.forEach((entryVal : any , entryKey : any )  => {
			this.configuracionAtributo.listaItem = entryVal;
			this.configuracionAtributo.listaItem.descripcionView = entryVal.idListaItems   + ' ' +  entryVal.descripcion;
		  });
	   }
     }
   });
  }
}