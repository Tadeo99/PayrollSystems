import {Component, EventEmitter, OnInit,OnChanges,SimpleChanges,AfterViewInit, Input} from '@angular/core';
import {FormControl,FormGroup,Validators } from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import {TranslateService} from '@ngx-translate/core';

import {CommonServiceImpl} from "../../common/common.impl.service";
import {LoginService} from "../../seguridad/login/login.service";
import {TypeSelectItemService} from "../../../typeselectitemservice/typeselectitem.service";

import {BaseComponent,DialogConfirmContent,DialogContent} from "../../../base/base.component";
import {MatDialog,MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';

import {DinamicFormularioAtributoService} from "./dinamicformularioatributo.service";
import {ConfiguracionAtributo} from "../../../models/common/configuracionatributo.model";
import {Item} from "../../../models/common/item.model";
import {ListaItems} from "../../../models/common/listaitems.model";
import {ListaItemType} from "../../../type/listaitem.type";
import {EstadoGeneralState} from "../../../type/estadogeneral.state";
import { ConfiguracionAtributoValue } from '../../../models/common/configuracionatributovalue.model';

/**
 * La Class DinamicFormularioAtributoComponent.
 * <ul>
 * <li>Copyright 2019 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, 10/04/2019
 * @since BUILDERP-CORE 2.1
 */
 
@Component({
  selector: 'app-dinamicformularioatributo',
  templateUrl: './dinamicformularioatributo.component.html',
  styleUrls: ['./dinamicformularioatributo.component.css'],
  providers: [DinamicFormularioAtributoService]
})
export class DinamicFormularioAtributoComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
		
	form: FormGroup;
	/** La lista configuracion atributo. */
	@Input('listaDataDinamic')
  public listaConfiguracionAtributo : ConfiguracionAtributo[] = [];
    
	
	constructor(public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private dinamicFormularioAtributoService: DinamicFormularioAtributoService,
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
		}
		if (this.idTupla) {
			this.params = this.params.set('idTupla', this.idTupla + '');
		}
		this.showAccion();
		this.inicializar();
  }
	
	ngOnInit() {
		this.onInit();
		
	}
	 
	onInit() {
    /*var id = this.route.params.subscribe(params => {
      var id = params['id'];

    });*/
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
			this.buscar();
		} catch (e) {
			this.mostrarMensajeError(e); 
		}
	}
	
	public async buscar() {
		this.generateFrmDinamic();
		this.completarConfiguracionValue();
		this.mostrarPanelForm = false;
	}
  /**
	 * Buscar.
	 *
	 */
	public async buscarx() {
		try {
			this.startProgres();
			this.mostrarPanelForm = false;
			this.dinamicFormularioAtributoService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
						this.listaConfiguracionAtributo = data.listaResultado;
						this.generateFrmDinamic();
						this.completarConfiguracionValue();
						this.mostrarPanelForm = false;
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
	public async  completarConfiguracionValue() {
		let paramsTemp : Map<any,any> = new Map<any,any>() ;
		this.listaConfiguracionAtributo.forEach((objData) => {
				if (objData.listaItem != null && objData.listaItem.idListaItems > 0){
					paramsTemp = paramsTemp.set(objData.listaItem.idListaItems,0);
				}
		});
		await this.commonServiceImpl.obtenerListaItemSelectItemMap(paramsTemp);
		this.listaConfiguracionAtributo.forEach((objData) => {
				if (objData.listaItem != null && objData.listaItem.idListaItems > 0){
						objData.listaSelectItemVO = this.commonServiceImpl.getListaItemSelectItem(objData.listaItem.idListaItems);
				}
	 });
	}
	
	private generateFrmDinamic(){
		let group: any = {};
		this.listaConfiguracionAtributo.forEach((objData) => {
			if (objData.listaItem != null && objData.listaItem.idListaItems > 0
				&&( objData.itemByIdComponte.nombre =='MODAL'
				|| objData.itemByIdComponte.nombre =='MODAL-MULTIPLE')){
					objData.nombreAtributoID = objData.nombreAtributo + 'ID';
					group[objData.nombreAtributoID] = new FormControl('', Validators.required);
			}
		});
		this.form = new FormGroup(group);
	}
	// convenience getter for easy access to form fields
	get frm() { return this.form.controls; }
	public isValid(name: string) { return this.form.controls[name].valid; }

  public lanzarConfiguracionAtributo(){
    // Usamos el mÃ©todo emit
    //this.change.emit({configuracionAtributo: this.configuracionAtributo});
  }
  
  /**
  Abrir modal Item input
  */	
  public abrirModalItemInput(idListaItem: number,configuracionAtributo : ConfiguracionAtributo,isMultiple:boolean,pSearch? : string) {
		configuracionAtributo.itemAtributoValue.idItem = null;
		this.form.controls[configuracionAtributo.nombreAtributoID].setValue('');
	  this.abrirModalItem(idListaItem,configuracionAtributo,isMultiple,pSearch);
  }

  /**
	El abrir modal item. 
  */   
  public abrirModalItem(idListaItem: number,configuracionAtributo : ConfiguracionAtributo,isMultiple:boolean,pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
	 let listaItem : ListaItems  = new ListaItems();
   listaItem.idListaItems = idListaItem;
   dialogRef = this.abrirModal(dialogRef,true,isMultiple,false,listaItem,pSearch);
   dialogRef.componentInstance.esModalItem = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
	  let listaItemSelectMap = result;
	  if (listaItemSelectMap) {
			if (isMultiple == true) {
					configuracionAtributo.listaSelectItemSelectedVO = [];
					configuracionAtributo.itemAtributoValue.descripcionView = '';
			}
			let index : number= 0;
		  listaItemSelectMap.forEach((entryVal : any , entryKey : any )  => {
				if (isMultiple == false) {
						configuracionAtributo.itemAtributoValue = entryVal;
						this.form.controls[configuracionAtributo.nombreAtributoID].setValue(entryVal.idItem);
				} else {
					index = index + 1;
					configuracionAtributo.listaSelectItemSelectedVO.push(entryVal.idItem);
					if (index == 1) {
							configuracionAtributo.itemAtributoValue.descripcionView =  entryVal.nombre;
					} else {
						configuracionAtributo.itemAtributoValue.descripcionView =  configuracionAtributo.itemAtributoValue.descripcionView + ',' + entryVal.nombre;
					}
					this.form.controls[configuracionAtributo.nombreAtributoID].setValue(entryVal.idItem);
				}
		  });
	   }
     }
   });
  }
}