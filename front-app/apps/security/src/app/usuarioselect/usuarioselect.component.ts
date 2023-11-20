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

import {UsuarioSelectService} from "./usuarioselect.service";
import {Usuario} from "../../../models/seguridad/usuario.model";
import {Item} from "../../../models/common/item.model";
import {Ubigeo} from "../../../models/common/ubigeo.model";
import {UsuarioEntidad} from "../../../models/seguridad/usuarioentidad.model";

/**
 * La Class PersonaComponent.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Fri Sep 08 19:59:41 COT 2017
 * @since SIAA-CORE 2.1
 */
 
@Component({
  selector: 'app-usuario',
  templateUrl: './usuarioselect.component.html',
  styleUrls: ['./usuarioselect.component.css'],
  providers: [UsuarioSelectService]
})
export class UsuarioSelectComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto usuario. */
    public usuario : Usuario = new Usuario();
	 
	/** La lista usuario. */
    public listaUsuario : Usuario[] = [];
    
	/** La lista item select. */
    public listaUsuarioSelectMap : Map<string,Usuario> = new Map<string,Usuario>();


	/** La lista asociar empresa. */
    private listaUsuarioEntidadNewMap :  Map<number,UsuarioEntidad> = new Map<number,UsuarioEntidad>();

    /** La lista asociar empresa eliminar map. */
	private listaUsuarioEmpresaEliminarMap  : Map<number,UsuarioEntidad> = new Map<number,UsuarioEntidad>();


	
	constructor(public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private usuarioService: UsuarioSelectService,
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
			this.listaUsuario= [];
			this.limpiaDataProvider(this.search);
			this.usuario = new Usuario();
			this.buscar();
		} catch (e) {
			this.mostrarMensajeError(e); 
		}
	}
	
	
	/**
	 * buscar id
	 *
	 */
	public buscarID(usuarioTemp : Usuario) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.usuario = Object.assign({}, usuarioTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.usuario = Object.assign({}, usuarioTemp);
					this.lanzarUsuario();
				}
			}
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}

	
	/* 
	asociar 
	*/
	public asociar(usuarioTemp : Usuario) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				usuarioTemp.checked = true;
				this.agregarCheck(usuarioTemp);
				this.dialogRef.close(this.listaUsuarioSelectMap);
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
			this.listaUsuario = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.usuarioService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaUsuario = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaUsuario.length == 1) {
            this.asociar(this.listaUsuario[0]);
		}
	}
	/**
	 * Buscar paginado.
	 */
	public buscarPaginado () {
		this.startProgres();
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.usuarioService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaUsuario = data.listaResultado;
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
	
  public lanzarUsuario(){
    // Usamos el mÃ©todo emit
    this.change.emit({usuario: this.usuario});
  }
  
   /*
   agregar check
   */
  public agregarCheck(usuarioTemp : Usuario) {
     if (usuarioTemp.checked) {
		 if (this.listaUsuarioSelectMap.get(usuarioTemp.idUsuario) == null) {
			this.listaUsuarioSelectMap.set(usuarioTemp.idUsuario,usuarioTemp);
		 }        
	 } else {
		if ((this.listaUsuarioSelectMap.get(usuarioTemp.idUsuario) != null)) {
			this.listaUsuarioSelectMap.delete(usuarioTemp.idUsuario);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaUsuario.forEach((data) => {
       if ((this.listaUsuarioSelectMap.get(data.idUsuario) != null)) {
			data.checked = true;
	   }
    });
  }
  
  
}