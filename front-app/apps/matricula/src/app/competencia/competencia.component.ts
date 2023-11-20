import {Component, EventEmitter, OnInit,OnChanges,SimpleChanges,AfterViewInit, Input, Optional} from '@angular/core';
import {FormControl,FormBuilder,Validators} from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import {TranslateService} from '@ngx-translate/core';

import {CommonServiceImpl} from "../../common/common.impl.service";
import {LoginService} from "../../seguridad/login/login.service";
import {TypeSelectItemService} from "../../../typeselectitemservice/typeselectitem.service";

import {BaseComponent,DialogConfirmContent,DialogContent} from "../../../base/base.component";
import {MatDialog,MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';

import {CompetenciaService} from "./competencia.service";
//import {Competencia} from "../../../models/matricula/competencia.model";
import {DetMallaCurricular} from "../../../models/matricula/detmallacurricular.model";
import { BaseDialogContent } from '../../../base/base.dialog.content.component';
//import { Capacidad } from '../../../models/matricula/capacidad.model';
import { UserStoreService } from '../../../shared/user-store.service';


/**
 * La Class CompetenciaComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:50 COT 2021
 * @since BUILDERP-CORE 2.1
 */
 
@Component({
  selector: 'app-competencia',
  templateUrl: './competencia.component.html',
  styleUrls: ['./competencia.component.css'],
  providers: [CompetenciaService]
})
export class CompetenciaComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto competencia. */
    public competencia : any ;
	 
	/** La lista competencia. */
    public listaCompetencia : any[] = [];
    
	/** La lista item select. */
    public listaCompetenciaSelectMap : Map<string,any> = new Map<string,any>();
	
	@Input("pnlFromCompetencia") 
	public pnlFromCompetencia : boolean = false;
	
	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private competenciaService: CompetenciaService,
	protected commonServiceImpl : CommonServiceImpl,public userStoreService : UserStoreService, protected loginDataService : LoginService,protected _typeSelectItemService : TypeSelectItemService,protected _translate: TranslateService) { 
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
		if (this.data) {
			this.params = this.params.set('id',this.data.idDetMallaCurricular + '');
			this.buscar();
		}
		this.showAccion();
    }
	
	ngOnInit() {
		this.onInit();
	//	this.inicializar();
		this.crearFormulario(this.competencia);
	}
	
	private  crearFormulario(obj : any) : void{
		this.frmGroup = this.fb.group({
			detMallaCurricular: this.fb.group({
				idDetMallaCurricular: [obj.detMallaCurricular.idDetMallaCurricular  ],
				descripcionView: [obj.detMallaCurricular.descripcionView  ,{ updateOn: 'blur' }]
			 }),
			idCompetencia: [obj.idCompetencia],
			codigo: [obj.codigo ],
			nombre: [obj.nombre ],
			abreviatura: [obj.abreviatura ],
			nroOrden: [obj.nroOrden ],
			estado: [obj.estado ],
		  });
		  this.onChange();
	}
	
	private onChange():void{
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});
		
		// this.frmGroup.get('detMallaCurricular.descripcionView').valueChanges.subscribe(value => {
		// 	this.abrirModalDetMallaCurricularInputdetMallaCurricular(value);
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
			this.frmGroup.value.detMallaCurricular.idDetMallaCurricular =this.data.idDetMallaCurricular;
			if (this.accionNuevo) {
				this.competenciaService.crear(this.frmGroup.value).subscribe(
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
				this.competenciaService.modificar(this.frmGroup.value,this.frmGroup.value.idCompetencia).subscribe(
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
	 	this.competencia  = {};
		this.frmGroup.patchValue(this.competencia,{onlySelf: true, emitEvent: false });
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
			this.listaCompetencia = [];
			this.limpiaDataProvider(this.search);
			this.competencia =  {};
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.competencia,{onlySelf: true, emitEvent: false });
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
			  this.competenciaService.eliminar(id).subscribe(
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
	public confirmarEliminar(competenciaTemp : any) {
		this.competencia = competenciaTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.competencia.idCompetencia);
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(competenciaTemp : any) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.competencia = Object.assign({}, competenciaTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.competencia = Object.assign({}, competenciaTemp);
					this.lanzarCompetencia();
				}
			}
			this.frmGroup.patchValue(this.competencia,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(competenciaTemp : any) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				competenciaTemp.checked = true;
				this.agregarCheck(competenciaTemp);
				this.dialogRef.close(this.listaCompetenciaSelectMap);
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
			this.listaCompetencia = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.competenciaService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaCompetencia = data.listaResultado;
					this.listaCompetencia.forEach(element => {
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
		if (this.id != null && this.id != '' && this.listaCompetencia.length == 1) {
            this.asociar(this.listaCompetencia[0]);
		}
	}
	
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.competenciaService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaCompetencia = data.listaResultado;
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
	
  private lanzarCompetencia(){
    // Usamos el mÃ©todo emit
    this.change.emit({competencia: this.competencia});
  }
  
   /*
   agregar check
   */
  public agregarCheck(competenciaTemp : any) {
     if (competenciaTemp.checked) {
		 if (!this.listaCompetenciaSelectMap.has(competenciaTemp.idCompetencia)) {
			this.listaCompetenciaSelectMap.set(competenciaTemp.idCompetencia,competenciaTemp);
		 }
	 } else {
		if ((this.listaCompetenciaSelectMap.has(competenciaTemp.idCompetencia))) {
			this.listaCompetenciaSelectMap.delete(competenciaTemp.idCompetencia);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaCompetencia.forEach((data) => {
       if ((this.listaCompetenciaSelectMap.has(data.idCompetencia))) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal DetMallaCurricular input
  */	
  public abrirModalDetMallaCurricularInputdetMallaCurricular(pSearch? : string) {
	 this.frmGroup.get('detMallaCurricular.idDetMallaCurricular').setValue(null);
	 this.abrirModalDetMallaCurriculardetMallaCurricular(pSearch);
  }

  /**
	El abrir modal det malla curricular. 
  */   
  public abrirModalDetMallaCurriculardetMallaCurricular(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : DetMallaCurricular  = new DetMallaCurricular();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalDetMallaCurricular = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idDetMallaCurricular;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('detMallaCurricular').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }

  /**
   * regresarDetMalla
   */
  public regresarDetMalla() {
	  this.pnlFromCompetencia=false;
	  this.change.emit({regresarDetMalla: false});	 
  }

  openOpcion(opcion : any) {
	// console.log("openOpcion.id==> " + opcion.id);
	 //console.log("openOpcion.checked==> " + opcion.checked);
	 opcion.checked = !opcion.checked;	
	 this.userStoreService.destroyTokenKey(opcion.idCompetencia);
	 this.userStoreService.saveTokenKey(opcion.idCompetencia,opcion.checked);
   }

   isOpenOpcion(opcion : any) : boolean {
	return  "true" == "" + this.userStoreService.getDataKey(opcion.idCompetencia);
 }
 

/**
	El abrir modal det malla curricular. 
  */   
	public abrirModalCapacidadEdit(competenciaTemp? :any) {	
		let dialogRef = this.dialog.open(DialogContentOverride);
		let data : any =  {};
	    data.competencia = competenciaTemp;		
		dialogRef = this.abrirModal(dialogRef,true,false,false,data,'');
		dialogRef.componentInstance.esModalCapacidadEdit = true;
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
	 `,
  })
  export class DialogContentOverride extends BaseDialogContent {
	public esModalCapacidadEdit: boolean = false;
	constructor(@Optional() public dialogRef: MatDialogRef<DialogContentOverride>) {super() }
  }