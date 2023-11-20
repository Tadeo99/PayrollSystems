import {Component, EventEmitter, OnInit,OnChanges,SimpleChanges,AfterViewInit} from '@angular/core';
import {FormControl,FormBuilder,Validators} from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import {TranslateService} from '@ngx-translate/core';

import {CommonServiceImpl} from "../../common/common.impl.service";
import {LoginService} from "../../seguridad/login/login.service";
import {TypeSelectItemService} from "../../../typeselectitemservice/typeselectitem.service";

import {BaseComponent,DialogConfirmContent,DialogContent} from "../../../base/base.component";
import {MatDialog,MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';

import {DesempenhoService} from "./desempenho.service";
//import {Desempenho} from "../../../models/matricula/desempenho.model";
//import {Capacidad} from "../../../models/matricula/capacidad.model";


/**
 * La Class DesempenhoComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Fri May 28 00:58:18 COT 2021
 * @since BUILDERP-CORE 2.1
 */
 
@Component({
  selector: 'app-desempenhomodaledit',
  templateUrl: './desempenhomodaledit.component.html',
  styleUrls: ['./desempenho.component.css'],
  providers: [DesempenhoService]
})
export class DesempenhoModalEditComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto desempenho. */
    public desempenho : any ;
	 
	/** La lista desempenho. */
    public listaDesempenho : any[] = [];
    
	/** La lista item select. */
    public listaDesempenhoSelectMap : Map<string,any> = new Map<string,any>();

	
	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private desempenhoService: DesempenhoService,
	protected commonServiceImpl : CommonServiceImpl, protected loginDataService : LoginService,protected _typeSelectItemService : TypeSelectItemService,protected _translate: TranslateService) { 
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
		if(this.data){					
			this.desempenho = this.data;		
			this.crearFormulario(this.desempenho);
			if(this.desempenho.idDesempenho){
				this.buscarID(this.desempenho);
			}else{				
				this.nuevo();
			}
			
		}
		this.showAccion();
    }
	
	ngOnInit() {
		this.onInit();
		//this.inicializar();
		
	}
	
	private  crearFormulario(obj : any) : void{
		this.frmGroup = this.fb.group({
			capacidad: this.fb.group({
				idCapacidad: [obj.capacidad.idCapacidad ],
				descripcionView: [obj.capacidad.descripcionView ,{ updateOn: 'blur' }]
			 }),
			idDesempenho: [obj.idDesempenho ],
			codigo: [obj.codigo ],
			descripcion: [obj.descripcion ],
			abreviatura: [obj.abreviatura ],
			peso: [obj.peso ],
			nroOrden: [obj.nroOrden ],
			estado: [obj.estado ],
		  });
		  this.onChange();
	}
	
	private onChange():void{
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});
		
		// this.frmGroup.get('capacidad.descripcionView').valueChanges.subscribe(value => {
		// 	this.abrirModalCapacidadInputcapacidad(value);
		// });
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
	public guardar() {
		if (this.frmGroup.invalid) {
			this.mostrarMensajeErrorFrmInvalid();
			return;
		 }
		try {
			this.frmGroup.value.capacidad.idCapacidad = this.data.capacidad.idCapacidad;
			if (this.accionNuevo) {
				this.desempenhoService.crear(this.frmGroup.value).subscribe(
			    data => {
					if (this.isProcesoOK(data)) {
						this.guardoExito();
						this.dialogRef.close(true);
					}
				},
				error => {
					this.mostrarMensajeError(error);
				}
			  );
			} else {
				this.desempenhoService.modificar(this.frmGroup.value,this.frmGroup.value.idDesempenho).subscribe(
			    data => {
					if (this.isProcesoOK(data)) {
						this.actualizadoExito();
						this.dialogRef.close(true);
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
	
	/**
	 * Nuevo.
	 *
	 */
	public nuevo() {
	 	this.desempenho  = {}
		this.frmGroup.patchValue(this.desempenho,{onlySelf: true, emitEvent: false });
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
	private limpiar() {
		try {
			this.listaDesempenho = [];
			this.limpiaDataProvider(this.search);
			this.desempenho = {}
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.desempenho,{onlySelf: true, emitEvent: false });
			}
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
			  this.desempenhoService.eliminar(id).subscribe(
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
	public confirmarEliminar(desempenhoTemp : any) {
		this.desempenho = desempenhoTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.desempenho.idDesempenho);
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(desempenhoTemp : any) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.desempenho = Object.assign({}, desempenhoTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.desempenho = Object.assign({}, desempenhoTemp);
					this.lanzarDesempenho();
				}
			}
			this.frmGroup.patchValue(this.desempenho,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(desempenhoTemp : any) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				desempenhoTemp.checked = true;
				this.agregarCheck(desempenhoTemp);
				this.dialogRef.close(this.listaDesempenhoSelectMap);
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
			this.listaDesempenho = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.desempenhoService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaDesempenho = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaDesempenho.length == 1) {
            this.asociar(this.listaDesempenho[0]);
		}
	}
	
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.desempenhoService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaDesempenho = data.listaResultado;
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
	}
	
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
	this.dialogRef.close();
   }
	
  private lanzarDesempenho(){
    // Usamos el mÃ©todo emit
    this.change.emit({desempenho: this.desempenho});
  }
  
   /*
   agregar check
   */
  public agregarCheck(desempenhoTemp : any) {
     if (desempenhoTemp.checked) {
		 if (!this.listaDesempenhoSelectMap.has(desempenhoTemp.idDesempenho)) {
			this.listaDesempenhoSelectMap.set(desempenhoTemp.idDesempenho,desempenhoTemp);
		 }
	 } else {
		if ((this.listaDesempenhoSelectMap.has(desempenhoTemp.idDesempenho))) {
			this.listaDesempenhoSelectMap.delete(desempenhoTemp.idDesempenho);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaDesempenho.forEach((data) => {
       if ((this.listaDesempenhoSelectMap.has(data.idDesempenho))) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal Capacidad input
  */	
  public abrirModalCapacidadInputcapacidad(pSearch? : string) {
	 this.frmGroup.get('capacidad.idCapacidad').setValue(null);
	 this.abrirModalCapacidadcapacidad(pSearch);
  }

  /**
	El abrir modal capacidad. 
  */   
  public abrirModalCapacidadcapacidad(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : any  = {};
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalCapacidad = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idCapacidad;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('capacidad').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
}