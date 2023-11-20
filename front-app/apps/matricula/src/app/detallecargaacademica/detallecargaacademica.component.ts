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

import {DetalleCargaAcademicaService} from "./detallecargaacademica.service";
import {DetalleCargaAcademica} from "../../../models/matricula/detallecargaacademica.model";
import {CargaAcademica} from "../../../models/matricula/cargaacademica.model";
import {Personal} from "../../../models/rrhh_escalafon/personal.model";
import {DetMallaCurricular} from "../../../models/matricula/detmallacurricular.model";
import {Grupo} from "../../../models/matricula/grupo.model";


/**
 * La Class DetalleCargaAcademicaComponent.
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
  selector: 'app-detallecargaacademica',
  templateUrl: './detallecargaacademica.component.html',
  styleUrls: ['./detallecargaacademica.component.css'],
  providers: [DetalleCargaAcademicaService]
})
export class DetalleCargaAcademicaComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto detalle carga academica. */
    public detalleCargaAcademica : DetalleCargaAcademica = new DetalleCargaAcademica();
	 
	/** La lista detalle carga academica. */
    public listaDetalleCargaAcademica : DetalleCargaAcademica[] = [];
    
	/** La lista item select. */
    public listaDetalleCargaAcademicaSelectMap : Map<string,DetalleCargaAcademica> = new Map<string,DetalleCargaAcademica>();

	
	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private detalleCargaAcademicaService: DetalleCargaAcademicaService,
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
		this.showAccion();
    }
	
	ngOnInit() {
		this.onInit();
		this.inicializar();
		this.crearFormulario(this.detalleCargaAcademica);
	}
	
	private  crearFormulario(obj : DetalleCargaAcademica) : void{
		this.frmGroup = this.fb.group({
			cargaAcademica: this.fb.group({
				idCargaAcademica: [obj.cargaAcademica.idCargaAcademica  , [Validators.required] ],
				descripcionView: [obj.cargaAcademica.descripcionView  , [Validators.required] ,{ updateOn: 'blur' }]
			 }),
			personalByDocentePrincipal: this.fb.group({
				idPersonal: [obj.personalByDocentePrincipal.idPersonal  , [Validators.required] ],
				descripcionView: [obj.personalByDocentePrincipal.descripcionView  , [Validators.required] ,{ updateOn: 'blur' }]
			 }),
			personalByDocenteAuxiliar: this.fb.group({
				idPersonal: [obj.personalByDocenteAuxiliar.idPersonal ],
				descripcionView: [obj.personalByDocenteAuxiliar.descripcionView ,{ updateOn: 'blur' }]
			 }),
			detMallaCurricular: this.fb.group({
				idDetMallaCurricular: [obj.detMallaCurricular.idDetMallaCurricular  , [Validators.required] ],
				descripcionView: [obj.detMallaCurricular.descripcionView  , [Validators.required] ,{ updateOn: 'blur' }]
			 }),
			grupo: this.fb.group({
				idGrupo: [obj.grupo.idGrupo ],
				descripcionView: [obj.grupo.descripcionView ,{ updateOn: 'blur' }]
			 }),
			idDetalleCargaAcademica: [obj.idDetalleCargaAcademica  , [Validators.required] ],
			codigoActa: [obj.codigoActa ],
		  });
		  this.onChange();
	}
	
	private onChange():void{
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});
		
		this.frmGroup.get('cargaAcademica.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalCargaAcademicaInputcargaAcademica(value);
		});
		this.frmGroup.get('personalByDocentePrincipal.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalPersonalInputpersonalByDocentePrincipal(value);
		});
		this.frmGroup.get('personalByDocenteAuxiliar.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalPersonalInputpersonalByDocenteAuxiliar(value);
		});
		this.frmGroup.get('detMallaCurricular.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalDetMallaCurricularInputdetMallaCurricular(value);
		});
		this.frmGroup.get('grupo.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalGrupoInputgrupo(value);
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
				this.detalleCargaAcademicaService.crear(this.frmGroup.value).subscribe(
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
				this.detalleCargaAcademicaService.modificar(this.frmGroup.value,this.frmGroup.value.idDetalleCargaAcademica).subscribe(
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
	 	this.detalleCargaAcademica  = new DetalleCargaAcademica();
		this.frmGroup.patchValue(this.detalleCargaAcademica,{onlySelf: true, emitEvent: false });
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
			this.listaDetalleCargaAcademica = [];
			this.limpiaDataProvider(this.search);
			this.detalleCargaAcademica = new DetalleCargaAcademica();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.detalleCargaAcademica,{onlySelf: true, emitEvent: false });
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
			  this.detalleCargaAcademicaService.eliminar(id).subscribe(
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
	public confirmarEliminar(detalleCargaAcademicaTemp : DetalleCargaAcademica) {
		this.detalleCargaAcademica = detalleCargaAcademicaTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.detalleCargaAcademica.idDetalleCargaAcademica);
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(detalleCargaAcademicaTemp : DetalleCargaAcademica) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.detalleCargaAcademica = Object.assign({}, detalleCargaAcademicaTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.detalleCargaAcademica = Object.assign({}, detalleCargaAcademicaTemp);
					this.lanzarDetalleCargaAcademica();
				}
			}
			this.frmGroup.patchValue(this.detalleCargaAcademica,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(detalleCargaAcademicaTemp : DetalleCargaAcademica) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				detalleCargaAcademicaTemp.checked = true;
				this.agregarCheck(detalleCargaAcademicaTemp);
				this.dialogRef.close(this.listaDetalleCargaAcademicaSelectMap);
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
			this.listaDetalleCargaAcademica = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.detalleCargaAcademicaService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaDetalleCargaAcademica = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaDetalleCargaAcademica.length == 1) {
            this.asociar(this.listaDetalleCargaAcademica[0]);
		}
	}
	
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.detalleCargaAcademicaService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaDetalleCargaAcademica = data.listaResultado;
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
	
  private lanzarDetalleCargaAcademica(){
    // Usamos el mÃ©todo emit
    this.change.emit({detalleCargaAcademica: this.detalleCargaAcademica});
  }
  
   /*
   agregar check
   */
  public agregarCheck(detalleCargaAcademicaTemp : DetalleCargaAcademica) {
     if (detalleCargaAcademicaTemp.checked) {
		 if (!this.listaDetalleCargaAcademicaSelectMap.has(detalleCargaAcademicaTemp.idDetalleCargaAcademica)) {
			this.listaDetalleCargaAcademicaSelectMap.set(detalleCargaAcademicaTemp.idDetalleCargaAcademica,detalleCargaAcademicaTemp);
		 }
	 } else {
		if ((this.listaDetalleCargaAcademicaSelectMap.has(detalleCargaAcademicaTemp.idDetalleCargaAcademica))) {
			this.listaDetalleCargaAcademicaSelectMap.delete(detalleCargaAcademicaTemp.idDetalleCargaAcademica);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaDetalleCargaAcademica.forEach((data) => {
       if ((this.listaDetalleCargaAcademicaSelectMap.has(data.idDetalleCargaAcademica))) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal CargaAcademica input
  */	
  public abrirModalCargaAcademicaInputcargaAcademica(pSearch? : string) {
	 this.frmGroup.get('cargaAcademica.idCargaAcademica').setValue(null);
	 this.abrirModalCargaAcademicacargaAcademica(pSearch);
  }

  /**
	El abrir modal carga academica. 
  */   
  public abrirModalCargaAcademicacargaAcademica(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : CargaAcademica  = new CargaAcademica();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalCargaAcademica = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idCargaAcademica;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('cargaAcademica').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
  /**
  Abrir modal Personal input
  */	
  public abrirModalPersonalInputpersonalByDocentePrincipal(pSearch? : string) {
	 this.frmGroup.get('personalByDocentePrincipal.idPersonal').setValue(null);
	 this.abrirModalPersonalpersonalByDocentePrincipal(pSearch);
  }

  /**
	El abrir modal personal. 
  */   
  public abrirModalPersonalpersonalByDocentePrincipal(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Personal  = new Personal();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalPersonal = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idPersonal;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('personalByDocentePrincipal').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
  /**
  Abrir modal Personal input
  */	
  public abrirModalPersonalInputpersonalByDocenteAuxiliar(pSearch? : string) {
	 this.frmGroup.get('personalByDocenteAuxiliar.idPersonal').setValue(null);
	 this.abrirModalPersonalpersonalByDocenteAuxiliar(pSearch);
  }

  /**
	El abrir modal personal. 
  */   
  public abrirModalPersonalpersonalByDocenteAuxiliar(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Personal  = new Personal();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalPersonal = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idPersonal;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('personalByDocenteAuxiliar').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
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
  Abrir modal Grupo input
  */	
  public abrirModalGrupoInputgrupo(pSearch? : string) {
	 this.frmGroup.get('grupo.idGrupo').setValue(null);
	 this.abrirModalGrupogrupo(pSearch);
  }

  /**
	El abrir modal grupo. 
  */   
  public abrirModalGrupogrupo(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Grupo  = new Grupo();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalGrupo = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idGrupo;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('grupo').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
}