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

import {DetMallaCurricularService} from "./detmallacurricular.service";
import {DetMallaCurricular} from "../../../models/matricula/detmallacurricular.model";
import {MallaCurricular} from "../../../models/matricula/mallacurricular.model";


/**
 * La Class DetMallaCurricularComponent.
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
  selector: 'app-detmallacurricular',
  templateUrl: './detmallacurricular.component.html',
  styleUrls: ['./detmallacurricular.component.css'],
  providers: [DetMallaCurricularService]
})
export class DetMallaCurricularComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto det malla curricular. */
    public detMallaCurricular : DetMallaCurricular = new DetMallaCurricular();

	public detMallaCurricularDependencia  : DetMallaCurricular = new DetMallaCurricular();
	 
	/** La lista det malla curricular. */
    public listaDetMallaCurricular : DetMallaCurricular[] = [];
    
	/** La lista item select. */
    public listaDetMallaCurricularSelectMap : Map<string,DetMallaCurricular> = new Map<string,DetMallaCurricular>();
	
	public pnlFromCompetencia : boolean = false;

	
	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private detMallaCurricularService: DetMallaCurricularService,
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
			this.params = this.params.set("idPadreView", this.id + '');
			this.buscar();
		}
		this.showAccion();
    }
	
	ngOnInit() {
		this.onInit();
		//this.inicializar();
		this.detMallaCurricular.detMallaCurricularPadre = new DetMallaCurricular();
		this.crearFormulario(this.detMallaCurricular);
	}
	
	private  crearFormulario(obj : DetMallaCurricular) : void{
		this.frmGroup = this.fb.group({
			detMallaCurricularPadre: this.fb.group({
				idDetMallaCurricular: [obj.detMallaCurricularPadre.idDetMallaCurricular ],
				descripcionView: [obj.detMallaCurricularPadre.descripcionView ,{ updateOn: 'blur' }]
			 }),
			 mallaCurricular: this.fb.group({
			 	idMallaCurricular: [obj.mallaCurricular.idMallaCurricular ],
			 	descripcionView: [obj.mallaCurricular.descripcionView ,{ updateOn: 'blur' }]
			 }),
			idDetMallaCurricular: [obj.idDetMallaCurricular ],
			descripcionCurso: [obj.descripcionCurso  , [Validators.required] ],
			codigoAsignatura: [obj.codigoAsignatura  , [Validators.required] ],
			horaTeorica: [obj.horaTeorica  , [Validators.required] ],
			horaPractica: [obj.horaPractica  , [Validators.required] ],
			tipoAsignatura: [obj.tipoAsignatura  , [Validators.required] ],
			peso: [obj.peso ],
			tipoCalculo: [obj.tipoCalculo ],
			tipoPromedio: [obj.tipoPromedio ],
			checked:[obj.checked],
		  });
		  this.onChange();
	}
	
	private onChange():void{
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});
		
		// this.frmGroup.get('detMallaCurricularPadre.descripcionView').valueChanges.subscribe(value => {
		// 	this.abrirModalDetMallaCurricularInputdetMallaCurricularPadre(value);
		// });
		// this.frmGroup.get('mallaCurricular.descripcionView').valueChanges.subscribe(value => {
		// 	this.abrirModalMallaCurricularInputmallaCurricular(value);
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
			this.frmGroup.value.mallaCurricular.idMallaCurricular =this.id;
	 		if (this.accionNuevo) {
	 			this.detMallaCurricularService.crear(this.frmGroup.value).subscribe(
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
	 			this.detMallaCurricularService.modificar(this.frmGroup.value,this.frmGroup.value.idDetMallaCurricular).subscribe(
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
	 	this.detMallaCurricular  = new DetMallaCurricular();	
		 this.detMallaCurricular.detMallaCurricularPadre = new DetMallaCurricular();
		 if (this.detMallaCurricularDependencia != null) {
			this.detMallaCurricular.detMallaCurricularPadre = this.detMallaCurricularDependencia;
		}
	
		this.frmGroup.patchValue(this.detMallaCurricular,{onlySelf: true, emitEvent: false });
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
			this.listaDetMallaCurricular = [];
			this.limpiaDataProvider(this.search);
			this.detMallaCurricular = new DetMallaCurricular();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.detMallaCurricular,{onlySelf: true, emitEvent: false });
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
	private eliminar ( id : any) {
		try {			
			this.detMallaCurricularService.eliminar(id).subscribe(
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
	public confirmarEliminar(detMallaCurricularTemp : DetMallaCurricular) {
		this.detMallaCurricular = detMallaCurricularTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){			
				this.eliminar(this.detMallaCurricular.idDetMallaCurricular);			 
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(detMallaCurricularTemp : DetMallaCurricular) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.detMallaCurricular = Object.assign({}, detMallaCurricularTemp);				
				this.mostrarPanelForm = true;
				this.accionNuevo = false;				
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.detMallaCurricular = Object.assign({}, detMallaCurricularTemp);
					this.lanzarDetMallaCurricular();
				}
			}
			this.frmGroup.patchValue(this.detMallaCurricular,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(detMallaCurricularTemp : DetMallaCurricular) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				detMallaCurricularTemp.checked = true;
				this.agregarCheck(detMallaCurricularTemp);
				this.dialogRef.close(this.listaDetMallaCurricularSelectMap);
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
			this.listaDetMallaCurricular = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.detMallaCurricularService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaDetMallaCurricular = data.listaResultado;					
					this.asociarData();
					this.mostrarPanelForm = false;
					this.noEncontroRegistoAlmanecado(this.dataProvider);
					this.change.emit({ listar: true,listaDetMallaCurricular:this.listaDetMallaCurricular });
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
		if (this.id != null && this.id != '' && this.listaDetMallaCurricular.length == 1) {
            this.asociar(this.listaDetMallaCurricular[0]);
		}
	}
	
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.detMallaCurricularService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaDetMallaCurricular = data.listaResultado;
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
	
  private lanzarDetMallaCurricular(){
    // Usamos el mÃ©todo emit
    this.change.emit({detMallaCurricular: this.detMallaCurricular});
  }
  
   /*
   agregar check
   */
  public agregarCheck(detMallaCurricularTemp : DetMallaCurricular) {
     if (detMallaCurricularTemp.checked) {
		 if (!this.listaDetMallaCurricularSelectMap.has(detMallaCurricularTemp.idDetMallaCurricular)) {
			this.listaDetMallaCurricularSelectMap.set(detMallaCurricularTemp.idDetMallaCurricular,detMallaCurricularTemp);
		 }
	 } else {
		if ((this.listaDetMallaCurricularSelectMap.has(detMallaCurricularTemp.idDetMallaCurricular))) {
			this.listaDetMallaCurricularSelectMap.delete(detMallaCurricularTemp.idDetMallaCurricular);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaDetMallaCurricular.forEach((data) => {
       if ((this.listaDetMallaCurricularSelectMap.has(data.idDetMallaCurricular))) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal DetMallaCurricular input
  */	
  public abrirModalDetMallaCurricularInputdetMallaCurricularPadre(pSearch? : string) {
	 this.frmGroup.get('detMallaCurricularPadre.idDetMallaCurricular').setValue(null);
	 this.abrirModalDetMallaCurriculardetMallaCurricularPadre(pSearch);
  }

  /**
	El abrir modal det malla curricular. 
  */   
  public abrirModalDetMallaCurriculardetMallaCurricularPadre(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : DetMallaCurricular  = new DetMallaCurricular();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalDetMallaCurricular = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idDetMallaCurricular;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('detMallaCurricularPadre').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
  /**
  Abrir modal MallaCurricular input
  */	
  public abrirModalMallaCurricularInputmallaCurricular(pSearch? : string) {
	 this.frmGroup.get('mallaCurricular.idMallaCurricular').setValue(null);
	 this.abrirModalMallaCurricularmallaCurricular(pSearch);
  }

  /**
	El abrir modal malla curricular. 
  */   
  public abrirModalMallaCurricularmallaCurricular(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : MallaCurricular  = new MallaCurricular();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalMallaCurricular = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idMallaCurricular;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('mallaCurricular').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }

  public regresarDependencia() {
	this.search = '';
	if((this.detMallaCurricularDependencia.detMallaCurricularPadre != null) && (this.detMallaCurricularDependencia.detMallaCurricularPadre != null && this.detMallaCurricularDependencia.detMallaCurricularPadre.idDetMallaCurricular)) {
		this.verDependecia(this.detMallaCurricularDependencia.detMallaCurricularPadre);
	} else {
		this.detMallaCurricularDependencia = new DetMallaCurricular();
		this.params = this.params.set("id",'');
		this.descripcion = '';
		this.buscar();
	}
  }

  	public verDependecia (detMallaCurricularDependencia : DetMallaCurricular ) {
		this.verDependeciaView(detMallaCurricularDependencia);
		this.buscar();
	}
	private verDependeciaView (detMallaCurricularDependencia : DetMallaCurricular ) {
		this.params = this.params.set("id",'');
		this.descripcion = '';
		this.detMallaCurricularDependencia = detMallaCurricularDependencia;
		this.descripcion = detMallaCurricularDependencia.codigoAsignatura + ' ' +  detMallaCurricularDependencia.descripcionCurso;
		this.detMallaCurricularDependencia.descripcionView = detMallaCurricularDependencia.codigoAsignatura + ' ' +  detMallaCurricularDependencia.descripcionCurso;	
		this.params = this.params.set("id",detMallaCurricularDependencia.idDetMallaCurricular + '');
	}

	public verCompetencia(detMallaCurricular : DetMallaCurricular ) {
		this.pnlFromCompetencia=true;
		this.detMallaCurricular = Object.assign({}, detMallaCurricular);	
	}

	/**
	 * changeCompetencia
	 */
	public changeCompetencia($event) {	
		if( $event.regresarDetMalla !=null){
			this.pnlFromCompetencia = $event.regresarDetMalla		
		}
		
	}
}