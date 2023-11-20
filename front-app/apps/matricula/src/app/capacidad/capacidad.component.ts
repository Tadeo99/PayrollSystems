import {Component, EventEmitter, OnInit,OnChanges,SimpleChanges,AfterViewInit, Optional} from '@angular/core';
import {FormControl,FormBuilder,Validators} from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import {TranslateService} from '@ngx-translate/core';

import {CommonServiceImpl} from "../../common/common.impl.service";
import {LoginService} from "../../seguridad/login/login.service";
import {TypeSelectItemService} from "../../../typeselectitemservice/typeselectitem.service";

import {BaseComponent,DialogConfirmContent,DialogContent} from "../../../base/base.component";
import {MatDialog,MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';

import {CapacidadService} from "./capacidad.service";
//import {Capacidad} from "../../../models/matricula/capacidad.model";
//import {Competencia} from "../../../models/matricula/competencia.model";
import { BaseDialogContent } from '../../../base/base.dialog.content.component';
import { UserStoreService } from '../../../shared/user-store.service';
//import { Desempenho } from '../../../models/matricula/desempenho.model';




/**
 * La Class CapacidadComponent.
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
  selector: 'app-capacidad',
  templateUrl: './capacidad.component.html',
  styleUrls: ['./capacidad.component.css'],
  providers: [CapacidadService]
})
export class CapacidadComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto capacidad. */
    public capacidad : any = {}
	 
	/** La lista capacidad. */
    public listaCapacidad : any[] = [];
    
	/** La lista item select. */
    public listaCapacidadSelectMap : Map<string,any> = new Map<string,any>();

	
	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private capacidadService: CapacidadService,
	protected commonServiceImpl : CommonServiceImpl, public userStoreService : UserStoreService, protected loginDataService : LoginService,protected _typeSelectItemService : TypeSelectItemService,protected _translate: TranslateService) { 
		super(dialog,snackbar,router,route);
		super.setTypeSelectItemService(_typeSelectItemService);
		super.setTraslate(_translate);
		this.debounceTimeProcesar().subscribe(term =>  {this.search = term; this.buscar()});
		super.setLoginDataService(loginDataService);
		this.setUserStoreService(userStoreService);
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
		this.crearFormulario(this.capacidad);
	}
	
	private  crearFormulario(obj : any) : void{
		this.frmGroup = this.fb.group({
			competencia: this.fb.group({
				idCompetencia: [obj.competencia.idCompetencia ],
				descripcionView: [obj.competencia.descripcionView ,{ updateOn: 'blur' }]
			 }),
			idCapacidad: [obj.idCapacidad ],
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
		
		this.frmGroup.get('competencia.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalCompetenciaInputcompetencia(value);
		});
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
			if (this.accionNuevo) {
				this.capacidadService.crear(this.frmGroup.value).subscribe(
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
				this.capacidadService.modificar(this.frmGroup.value,this.frmGroup.value.idCapacidad).subscribe(
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
	
	/**
	 * Nuevo.
	 *
	 */
	public nuevo() {
	 	this.capacidad  =  {};
		this.frmGroup.patchValue(this.capacidad,{onlySelf: true, emitEvent: false });
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
			this.listaCapacidad = [];
			this.limpiaDataProvider(this.search);
			this.capacidad =  {};
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.capacidad,{onlySelf: true, emitEvent: false });
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
			  this.capacidadService.eliminar(id).subscribe(
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
	public confirmarEliminar(capacidadTemp : any) {
		this.capacidad = capacidadTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.capacidad.idCapacidad);
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(capacidadTemp : any) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.capacidad = Object.assign({}, capacidadTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.capacidad = Object.assign({}, capacidadTemp);
					this.lanzarCapacidad();
				}
			}
			this.frmGroup.patchValue(this.capacidad,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(capacidadTemp : any) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				capacidadTemp.checked = true;
				this.agregarCheck(capacidadTemp);
				this.dialogRef.close(this.listaCapacidadSelectMap);
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
			this.listaCapacidad = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.capacidadService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaCapacidad = data.listaResultado;
					this.listaCapacidad.forEach(element => {
						element.checked=this.isOpenOpcion(element);
					});
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
		if (this.id != null && this.id != '' && this.listaCapacidad.length == 1) {
            this.asociar(this.listaCapacidad[0]);
		}
	}
	
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.capacidadService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaCapacidad = data.listaResultado;
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
	this.mostrarPanelForm = false;
   }
	
  private lanzarCapacidad(){
    // Usamos el mÃ©todo emit
    this.change.emit({capacidad: this.capacidad});
  }
  
   /*
   agregar check
   */
  public agregarCheck(capacidadTemp : any) {
     if (capacidadTemp.checked) {
		 if (!this.listaCapacidadSelectMap.has(capacidadTemp.idCapacidad)) {
			this.listaCapacidadSelectMap.set(capacidadTemp.idCapacidad,capacidadTemp);
		 }
	 } else {
		if ((this.listaCapacidadSelectMap.has(capacidadTemp.idCapacidad))) {
			this.listaCapacidadSelectMap.delete(capacidadTemp.idCapacidad);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaCapacidad.forEach((data) => {
       if ((this.listaCapacidadSelectMap.has(data.idCapacidad))) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal Competencia input
  */	
  public abrirModalCompetenciaInputcompetencia(pSearch? : string) {
	 this.frmGroup.get('competencia.idCompetencia').setValue(null);
	 this.abrirModalCompetenciacompetencia(pSearch);
  }

  /**
	El abrir modal competencia. 
  */   
  public abrirModalCompetenciacompetencia(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : any  =  {};
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
  // dialogRef.componentInstance.esModalCompetencia = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idCompetencia;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('competencia').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }

  /**
	El abrir modal det malla curricular. 
  */   
	 public abrirModalCapacidadEdit(capacidadTemp? :any) {	
	 	let dialogRef = this.dialog.open(DialogContentOverrideCapacidad);	 	
		let data = capacidadTemp;	
	 	dialogRef = this.abrirModal(dialogRef,true,false,false,data,'');
	 	dialogRef.componentInstance.esModalCapacidadEdit = true;
	 	dialogRef.afterClosed().subscribe(result => {
	 	if (result) {
	 		this.buscar();
	 	  }
	 	});
	}
	
	openOpcion(opcion : any) {
		// console.log("openOpcion.id==> " + opcion.id);
		 //console.log("openOpcion.checked==> " + opcion.checked);
		 opcion.checked = !opcion.checked;	
		 this.userStoreService.destroyTokenKey(opcion.idCapacidad);
		 this.userStoreService.saveTokenKey(opcion.idCapacidad,opcion.checked);
	   }
	
	   isOpenOpcion(opcion : any) : boolean {
		return  "true" == "" + this.userStoreService.getDataKey(opcion.idCapacidad);
	 }

	 /**
	El abrir modal det malla curricular. 
  */   
	public abrirModalDesempenhoEdit(capacidadTemp? :any) {	
		let dialogRef = this.dialog.open(DialogContentOverrideCapacidad);
		let data : any =  {};
	    data.capacidad = capacidadTemp;		
		dialogRef = this.abrirModal(dialogRef,true,false,false,data,'');
		dialogRef.componentInstance.esModalDesempenhoEdit = true;
		dialogRef.afterClosed().subscribe(result => {
		if (result) {
			this.buscar();
		  }
		});
	   }
}

@Component({
	template: `
	<app-capacidadmodaledit *ngIf="esModalCapacidadEdit" [id] = "id" [esIncludeComponent] = "esIncludeComponent"  [showModal]="showModal" [showSelectMultiple] = "showSelectMultiple" [dialogRef] = "dialogRef" [data] = "data"></app-capacidadmodaledit>
	<app-desempenhomodaledit *ngIf="esModalDesempenhoEdit" [id] = "id" [esIncludeComponent] = "esIncludeComponent"  [showModal]="showModal" [showSelectMultiple] = "showSelectMultiple" [dialogRef] = "dialogRef" [data] = "data"></app-desempenhomodaledit>
	 `,
  })
  export class DialogContentOverrideCapacidad extends BaseDialogContent {
	public esModalCapacidadEdit: boolean = false;
	public esModalDesempenhoEdit: boolean = false;
	constructor(@Optional() public dialogRef: MatDialogRef<DialogContentOverrideCapacidad>) {super() }
  }