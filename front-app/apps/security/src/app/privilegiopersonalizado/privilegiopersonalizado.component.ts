import {Component, EventEmitter, Input, OnInit,OnChanges,SimpleChanges,AfterViewInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import {TranslateService} from '@ngx-translate/core';

import {CommonServiceImpl} from "../../common/common.impl.service";
import {LoginService} from "../../seguridad/login/login.service";
import {TypeSelectItemService} from "../../../typeselectitemservice/typeselectitem.service";

import {BaseComponent,DialogConfirmContent,DialogContent} from "../../../base/base.component";
import {MatDialog,MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';

import {PrivilegioPersonalizadoService} from "./privilegiopersonalizado.service";
import {PrivilegioPersonalizado} from "../../../models/seguridad/privilegiopersonalizado.model";
import {Usuario} from "../../../models/seguridad/usuario.model";
import {PrivilegioMenu} from "../../../models/seguridad/privilegiomenu.model";
import {Privilegio} from "../../../models/seguridad/privilegio.model";
import {Menu} from "../../../models/seguridad/menu.model";


/**
 * La Class PrivilegioPersonalizadoComponent.
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
  selector: 'app-privilegiopersonalizado',
  templateUrl: './privilegiopersonalizado.component.html',
  styleUrls: ['./privilegiopersonalizado.component.css'],
  providers: [PrivilegioPersonalizadoService]
})
export class PrivilegioPersonalizadoComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
	
	@Input("usuario") 
	public usuario : Usuario = new Usuario();
	
	@Input("listaIdMenu") 
	public listaIdMenu : number[] = [];

	@Input("fechaActual") 
	public fechaActual : Date = null;

	/** La lista privilegio personalizado. */
    public listaPrivilegioPersonalizado : PrivilegioMenu[] = [];
    
	/** La lista item select. */
	@Input("listaPrivilegioPersonalizadoSelectMap") 
    public listaPrivilegioPersonalizadoSelectMap : Map<number,Privilegio> = new Map<number,Privilegio>();

	
	constructor(public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private privilegioPersonalizadoService: PrivilegioPersonalizadoService,
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
		console.log("this.listaIdMenu.length ==> " + this.listaIdMenu.length);
		this.params = this.params.delete("idMenu");
		this.params = this.params.set('id', this.usuario.idUsuario + '');
		this.listaIdMenu.forEach((dataMenu) => {
			this.params = this.params.append("idMenu",dataMenu + "");
		 });
		this.buscar();
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
			this.listaPrivilegioPersonalizado= [];
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
			this.listaPrivilegioPersonalizado = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.privilegioPersonalizadoService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaPrivilegioPersonalizado = data.listaResultado;
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
			 this.privilegioPersonalizadoService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaPrivilegioPersonalizado = data.listaResultado;
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
	
  public lanzarPrivilegioPersonalizado(){
    // Usamos el mÃ©todo emit
  }
  
   /*
   agregar check
   */
  public agregarCheck(privilegioMenuTemp : PrivilegioMenu) {
	privilegioMenuTemp.privilegio.checked = privilegioMenuTemp.checked;
     if (privilegioMenuTemp.checked) {
			this.listaPrivilegioPersonalizadoSelectMap.set(privilegioMenuTemp.privilegio.idPrivilegio,privilegioMenuTemp.privilegio);
	 } else {
			this.listaPrivilegioPersonalizadoSelectMap.set(privilegioMenuTemp.privilegio.idPrivilegio,privilegioMenuTemp.privilegio);
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	if (this.listaPrivilegioPersonalizado != null) {
		this.listaPrivilegioPersonalizado.forEach((data) => {
	       if ((this.listaPrivilegioPersonalizadoSelectMap.has(data.privilegio.idPrivilegio))) {
			   let objData : Privilegio = this.listaPrivilegioPersonalizadoSelectMap.get(data.privilegio.idPrivilegio);
			   if (objData.checked == true) {
					data.checked = true;
				}
		   }
		});
   }
  }
  
}