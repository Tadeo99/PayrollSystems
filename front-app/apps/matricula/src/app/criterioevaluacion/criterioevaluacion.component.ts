import {Component, EventEmitter, OnInit,OnChanges,SimpleChanges,AfterViewInit, Input} from '@angular/core';
import {FormControl,FormBuilder,Validators} from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import {TranslateService} from '@ngx-translate/core';

import {CommonServiceImpl} from "../../common/common.impl.service";
import {LoginService} from "../../seguridad/login/login.service";
import {TypeSelectItemService} from "../../../typeselectitemservice/typeselectitem.service";

import {BaseComponent,DialogConfirmContent,DialogContent} from "../../../base/base.component";
import {MatDialog,MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';

import {CriterioEvaluacionService} from "./criterioevaluacion.service";
import {CriterioEvaluacion} from "../../../models/matricula/criterioevaluacion.model";
import {DetMallaCurricular} from "../../../models/matricula/detmallacurricular.model";


/**
 * La Class CriterioEvaluacionComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
 
@Component({
  selector: 'app-criterioevaluacion',
  templateUrl: './criterioevaluacion.component.html',
  styleUrls: ['./criterioevaluacion.component.css'],
  providers: [CriterioEvaluacionService]
})
export class CriterioEvaluacionComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto criterio evaluacion. */
    public criterioEvaluacion : CriterioEvaluacion = new CriterioEvaluacion();
	 
	/** La lista criterio evaluacion. */
    public listaCriterioEvaluacion : CriterioEvaluacion[] = [];
    
	/** La lista item select. */
    public listaCriterioEvaluacionSelectMap : Map<string,CriterioEvaluacion> = new Map<string,CriterioEvaluacion>();

	@Input("pnlFromCompetencia") 
	public pnlFromCompetencia : boolean = false;

	public criterioEvaluacionDependencia  : CriterioEvaluacion = new CriterioEvaluacion();
	
	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private criterioEvaluacionService: CriterioEvaluacionService,
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
		if (this.data) {
			this.params = this.params.set('idPadreView', this.data.idDetMallaCurricular + '');
			this.buscar();
		}
		this.showAccion();
    }
	
	ngOnInit() {
		this.onInit();
	//	this.inicializar();
		this.criterioEvaluacion.criterioEvaluacionPadre = new CriterioEvaluacion();
		this.crearFormulario(this.criterioEvaluacion);
	}
	
	private  crearFormulario(obj : CriterioEvaluacion) : void{
		this.frmGroup = this.fb.group({
			detMallaCurricular: this.fb.group({
				idDetMallaCurricular: [obj.detMallaCurricular.idDetMallaCurricular ],
				descripcionView: [obj.detMallaCurricular.descripcionView ,{ updateOn: 'blur' }]
			 }),
			 criterioEvaluacionPadre: this.fb.group({
				idCriterioEvaluacion: [obj.criterioEvaluacionPadre.idCriterioEvaluacion ],
				descripcionView: [obj.criterioEvaluacionPadre.descripcionView ,{ updateOn: 'blur' }]
			 }),
			idCriterioEvaluacion: [obj.idCriterioEvaluacion  ],
			codigo: [obj.codigo ],
			nombre: [obj.nombre ],
			abreviatura: [obj.abreviatura ],
			peso: [obj.peso ],
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
		// this.frmGroup.get('criterioEvaluacion.descripcionView').valueChanges.subscribe(value => {
		// 	this.abrirModalDetMallaCurricularInputcriterioEvaluacion(value);
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
				this.criterioEvaluacionService.crear(this.frmGroup.value).subscribe(
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
				this.criterioEvaluacionService.modificar(this.frmGroup.value,this.frmGroup.value.idCriterioEvaluacion).subscribe(
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
	 	this.criterioEvaluacion  = new CriterioEvaluacion();
		 this.criterioEvaluacion.criterioEvaluacionPadre = new CriterioEvaluacion();
		 if (this.criterioEvaluacionDependencia != null) {
			this.criterioEvaluacion.criterioEvaluacionPadre = this.criterioEvaluacionDependencia;
		}
		this.frmGroup.patchValue(this.criterioEvaluacion,{onlySelf: true, emitEvent: false });
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
			this.listaCriterioEvaluacion = [];
			this.limpiaDataProvider(this.search);
			this.criterioEvaluacion = new CriterioEvaluacion();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.criterioEvaluacion,{onlySelf: true, emitEvent: false });
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
			  this.criterioEvaluacionService.eliminar(id).subscribe(
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
	public confirmarEliminar(criterioEvaluacionTemp : CriterioEvaluacion) {
		this.criterioEvaluacion = criterioEvaluacionTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.criterioEvaluacion.idCriterioEvaluacion);
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(criterioEvaluacionTemp : CriterioEvaluacion) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.criterioEvaluacion = Object.assign({}, criterioEvaluacionTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.criterioEvaluacion = Object.assign({}, criterioEvaluacionTemp);
					this.lanzarCriterioEvaluacion();
				}
			}
			this.frmGroup.patchValue(this.criterioEvaluacion,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(criterioEvaluacionTemp : CriterioEvaluacion) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				criterioEvaluacionTemp.checked = true;
				this.agregarCheck(criterioEvaluacionTemp);
				this.dialogRef.close(this.listaCriterioEvaluacionSelectMap);
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
			this.listaCriterioEvaluacion = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.criterioEvaluacionService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaCriterioEvaluacion = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaCriterioEvaluacion.length == 1) {
            this.asociar(this.listaCriterioEvaluacion[0]);
		}
	}
	
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.criterioEvaluacionService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaCriterioEvaluacion = data.listaResultado;
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
	this.pnlFromCompetencia=false;
	this.change.emit({regresarDetMalla: false});	
   }
	
  private lanzarCriterioEvaluacion(){
    // Usamos el mÃ©todo emit
    this.change.emit({criterioEvaluacion: this.criterioEvaluacion});
  }
  
   /*
   agregar check
   */
  public agregarCheck(criterioEvaluacionTemp : CriterioEvaluacion) {
     if (criterioEvaluacionTemp.checked) {
		 if (!this.listaCriterioEvaluacionSelectMap.has(criterioEvaluacionTemp.idCriterioEvaluacion)) {
			this.listaCriterioEvaluacionSelectMap.set(criterioEvaluacionTemp.idCriterioEvaluacion,criterioEvaluacionTemp);
		 }
	 } else {
		if ((this.listaCriterioEvaluacionSelectMap.has(criterioEvaluacionTemp.idCriterioEvaluacion))) {
			this.listaCriterioEvaluacionSelectMap.delete(criterioEvaluacionTemp.idCriterioEvaluacion);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaCriterioEvaluacion.forEach((data) => {
       if ((this.listaCriterioEvaluacionSelectMap.has(data.idCriterioEvaluacion))) {
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
  Abrir modal DetMallaCurricular input
  */	
  public abrirModalDetMallaCurricularInputcriterioEvaluacion(pSearch? : string) {
	 this.frmGroup.get('criterioEvaluacion.idDetMallaCurricular').setValue(null);
	 this.abrirModalDetMallaCurricularcriterioEvaluacion(pSearch);
  }

  /**
	El abrir modal det malla curricular. 
  */   
  public abrirModalDetMallaCurricularcriterioEvaluacion(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : DetMallaCurricular  = new DetMallaCurricular();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
//   dialogRef.componentInstance.esModalDetMallaCurricular = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idDetMallaCurricular;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('criterioEvaluacion').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
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

	public regresarDependencia() {
		this.search = '';
		if((this.criterioEvaluacionDependencia.criterioEvaluacionPadre != null) && (this.criterioEvaluacionDependencia.criterioEvaluacionPadre != null && this.criterioEvaluacionDependencia.criterioEvaluacionPadre.idCriterioEvaluacion)) {
			this.verDependecia(this.criterioEvaluacionDependencia.criterioEvaluacionPadre);
		} else {
			this.criterioEvaluacionDependencia = new CriterioEvaluacion();
			this.params = this.params.set("id",'');
			this.descripcion = '';
			this.buscar();
		}
	  }
	
		  public verDependecia (criterioEvaluacionDependencia : CriterioEvaluacion ) {
			this.verDependeciaView(criterioEvaluacionDependencia);
			this.buscar();
		}
		private verDependeciaView (criterioEvaluacionDependencia : CriterioEvaluacion ) {
			this.params = this.params.set("id",'');
			this.descripcion = '';
			this.criterioEvaluacionDependencia = criterioEvaluacionDependencia;
			this.descripcion = criterioEvaluacionDependencia.idCriterioEvaluacion + ' ' +  criterioEvaluacionDependencia.nombre;
			this.criterioEvaluacionDependencia.descripcionView = criterioEvaluacionDependencia.idCriterioEvaluacion + ' ' +  criterioEvaluacionDependencia.nombre;	
			this.params = this.params.set("id",criterioEvaluacionDependencia.idCriterioEvaluacion + '');
		}
	
		public verCompetencia(detMallaCurricular : CriterioEvaluacion ) {
			this.pnlFromCompetencia=true;
			this.criterioEvaluacion = Object.assign({}, detMallaCurricular);	
		}
	
}