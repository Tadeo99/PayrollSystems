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

import {GrupoUsuarioUsuarioService} from "./grupousuariousuario.service";
import {GrupoUsuarioUsuario} from "../../../models/seguridad/grupousuariousuario.model";
import {GrupoUsuario} from "../../../models/seguridad/grupousuario.model";
import {Usuario} from "../../../models/seguridad/usuario.model";

import { DialogContentOverride } from '../administrarsistema/administrarsistema.component';

/**
 * La Class GrupoUsuarioUsuarioComponent.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Mon Oct 16 14:31:01 COT 2017
 * @since SIAA-CORE 2.1
 */
 
@Component({
  selector: 'app-grupousuariousuario',
  templateUrl: './grupousuariousuario.component.html',
  styleUrls: ['./grupousuariousuario.component.css'],
  providers: [GrupoUsuarioUsuarioService]
})
export class GrupoUsuarioUsuarioComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto grupo usuario usuario. */
    public grupoUsuarioUsuario : GrupoUsuarioUsuario = new GrupoUsuarioUsuario();
	 
	/** La lista grupo usuario usuario. */
    public listaGrupoUsuarioUsuario : GrupoUsuario[] = [];
    
	/** La lista item select. */
    public listaGrupoUsuarioUsuarioSelectMap : Map<number,GrupoUsuario> = new Map<number,GrupoUsuario>();

	
	constructor(public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private grupoUsuarioUsuarioService: GrupoUsuarioUsuarioService,
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
			let listaGrupoUsuarioTemp : GrupoUsuario[] = [];
			this.listaGrupoUsuarioUsuarioSelectMap.forEach((entryVal : any , entryKey : any )  => {
				entryVal.id = this.grupoUsuarioUsuario.usuario.idUsuario;
				listaGrupoUsuarioTemp.push(entryVal);
			 });		
			this.grupoUsuarioUsuarioService.crear(listaGrupoUsuarioTemp).subscribe(
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
			this.listaGrupoUsuarioUsuario= [];
			this.limpiaDataProvider(this.search);
			this.grupoUsuarioUsuario = new GrupoUsuarioUsuario();
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
			this.listaGrupoUsuarioUsuario = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.grupoUsuarioUsuarioService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaGrupoUsuarioUsuario = data.listaResultado;
					this.mostrarPanelForm = false;
					this.verificarCheck();
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
			 this.grupoUsuarioUsuarioService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaGrupoUsuarioUsuario = data.listaResultado;
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
   }
	
  public lanzarGrupoUsuarioUsuario(){
    // Usamos el mÃ©todo emit
    this.change.emit({grupoUsuarioUsuario: this.grupoUsuarioUsuario});
  }
  
   /*
   agregar check
   */
  public agregarCheck(grupoUsuarioUsuarioTemp : GrupoUsuario) {
     if (grupoUsuarioUsuarioTemp.checked) {
			this.listaGrupoUsuarioUsuarioSelectMap.set(grupoUsuarioUsuarioTemp.idGrupoUsuario,grupoUsuarioUsuarioTemp);
	 } else {
			this.listaGrupoUsuarioUsuarioSelectMap.set(grupoUsuarioUsuarioTemp.idGrupoUsuario,grupoUsuarioUsuarioTemp);
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	  if (this.listaGrupoUsuarioUsuario != null) {
			this.listaGrupoUsuarioUsuario.forEach((data) => {
		       if ((this.listaGrupoUsuarioUsuarioSelectMap.has(data.idGrupoUsuario))) {
				   let objData : GrupoUsuario = this.listaGrupoUsuarioUsuarioSelectMap.get(data.idGrupoUsuario);
				   if (objData.checked == true) {
						data.checked = true;
				   }			
			   }
			});
       }
  }
  
  /**
  Abrir modal Persona input
  */	
  public abrirModalPersonaInput(pSearch? : string) {
	 this.grupoUsuarioUsuario.usuario.idUsuario = null;
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
			this.params = this.params.set('idUsuario', entryVal.idUsuario + '');
			this.grupoUsuarioUsuario.usuario = entryVal;
			this.grupoUsuarioUsuario.usuario.descripcionView = entryVal.userName + ' ' +  entryVal.nombre + ' ' +  entryVal.apellidoPaterno + ' ' +  entryVal.apellidoMaterno;

			this.listaGrupoUsuarioUsuarioSelectMap = new Map<number,GrupoUsuario>();

			this.buscar();
		  });
	   }
     }
   });
  }
}