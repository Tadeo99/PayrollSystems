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

import {EntidadSelectService} from "./entidadselect.service";
import {Entidad} from "../../../models/seguridad/entidad.model";
import {Item} from "../../../models/common/item.model";


/**
 * La Class EntidadSelectComponent.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sat Dec 23 17:16:37 COT 2017
 * @since SIAA-CORE 2.1
 */
 
@Component({
  selector: 'app-entidadselect',
  templateUrl: './entidadselect.component.html',
  styleUrls: ['./entidadselect.component.css'],
  providers: [EntidadSelectService]
})
export class EntidadSelectComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto entidad. */
    public entidad : Entidad = new Entidad();
	 
	/** La lista entidad. */
    public listaEntidad : Entidad[] = [];
    
	/** La lista item select. */
    public listaEntidadSelectMap : Map<string,Entidad> = new Map<string,Entidad>();

	
	constructor(public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private entidadService: EntidadSelectService,
	public commonServiceImpl : CommonServiceImpl, public loginDataService : LoginService,public _typeSelectItemService : TypeSelectItemService,public _translate: TranslateService) { 
		super(dialog,snackbar,router,route);
		super.setTypeSelectItemService(_typeSelectItemService);
		super.setTraslate(_translate);
		this.debounceTimeProcesar().subscribe(term =>  {this.search = term; this.buscar()});
		super.setLoginDataService(loginDataService);
		this.modulo="security";
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
	 * Inicializar.
	 *
	 */
	private inicializar() {
		//super.validarPaginaView();
		super.getUsuarioSession();
	    this.limpiar();	
	}
	
	/**
	 * Limpiar.
	 *
	 */
	private limpiar () {
		try {
			this.listaEntidad= [];
			this.limpiaDataProvider(this.search);
			this.entidad = new Entidad();
			this.buscar();
		} catch (e) {
			this.mostrarMensajeError(e); 
		}
	}
	
	
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(entidadTemp : Entidad) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.entidad = Object.assign({}, entidadTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.entidad = Object.assign({}, entidadTemp);
					this.lanzarEntidad();
				}
			}
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(entidadTemp : Entidad) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				entidadTemp.checked = true;
				this.agregarCheck(entidadTemp);
				this.dialogRef.close(this.listaEntidadSelectMap);
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
			this.listaEntidad = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.entidadService.paginador(this.dataProvider, this.params,this.modulo).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaEntidad = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaEntidad.length == 1) {
            this.asociar(this.listaEntidad[0]);
		}
	}
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado () {
		this.startProgres();
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.entidadService.paginador(this.dataProvider, this.params,this.modulo).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaEntidad = data.listaResultado;
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
	
  public lanzarEntidad(){
    // Usamos el mÃ©todo emit
    this.change.emit({entidad: this.entidad});
  }
  
   /*
   agregar check
   */
  public agregarCheck(entidadTemp : Entidad) {
     if (entidadTemp.checked) {
		 if (this.listaEntidadSelectMap.get(entidadTemp.idEntidad) == null) {
			this.listaEntidadSelectMap.set(entidadTemp.idEntidad,entidadTemp);
		 }        
	 } else {
		if ((this.listaEntidadSelectMap.get(entidadTemp.idEntidad) != null)) {
			this.listaEntidadSelectMap.delete(entidadTemp.idEntidad);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaEntidad.forEach((data) => {
       if ((this.listaEntidadSelectMap.get(data.idEntidad) != null)) {
			data.checked = true;
	   }
    });
  }
 
}