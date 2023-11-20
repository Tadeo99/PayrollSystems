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

import {DetMatriculaService} from "./detmatricula.service";
import {DetMatricula} from "../../../models/matricula/detmatricula.model";
import {Matricula} from "../../../models/matricula/matricula.model";
import {DetMallaCurricular} from "../../../models/matricula/detmallacurricular.model";


/**
 * La Class DetMatriculaComponent.
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
  selector: 'app-detmatricula',
  templateUrl: './detmatricula.component.html',
  styleUrls: ['./detmatricula.component.css'],
  providers: [DetMatriculaService]
})
export class DetMatriculaComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto det matricula. */
    public detMatricula : DetMatricula = new DetMatricula();
	 
	/** La lista det matricula. */
    public listaDetMatricula : DetMatricula[] = [];
    
	/** La lista item select. */
    public listaDetMatriculaSelectMap : Map<string,DetMatricula> = new Map<string,DetMatricula>();

	
	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private detMatriculaService: DetMatriculaService,
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
		this.crearFormulario(this.detMatricula);
	}
	
	private  crearFormulario(obj : DetMatricula) : void{
		this.frmGroup = this.fb.group({
			matricula: this.fb.group({
				idMatricula: [obj.matricula.idMatricula ],
				descripcionView: [obj.matricula.descripcionView ,{ updateOn: 'blur' }]
			 }),
			detMallaCurricular: this.fb.group({
				idDetMallaCurricular: [obj.detMallaCurricular.idDetMallaCurricular  , [Validators.required] ],
				descripcionView: [obj.detMallaCurricular.descripcionView  , [Validators.required] ,{ updateOn: 'blur' }]
			 }),
			idDetMatricula: [obj.idDetMatricula  , [Validators.required] ],
			descripcionCurso: [obj.descripcionCurso ],
			codigoAsignatura: [obj.codigoAsignatura  , [Validators.required] ],
			estado: [obj.estado ],
		  });
		  this.onChange();
	}
	
	private onChange():void{
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});
		
		this.frmGroup.get('matricula.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalMatriculaInputmatricula(value);
		});
		this.frmGroup.get('detMallaCurricular.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalDetMallaCurricularInputdetMallaCurricular(value);
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
				this.detMatriculaService.crear(this.frmGroup.value).subscribe(
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
				this.detMatriculaService.modificar(this.frmGroup.value,this.frmGroup.value.idDetMatricula).subscribe(
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
	 	this.detMatricula  = new DetMatricula();
		this.frmGroup.patchValue(this.detMatricula,{onlySelf: true, emitEvent: false });
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
			this.listaDetMatricula = [];
			this.limpiaDataProvider(this.search);
			this.detMatricula = new DetMatricula();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.detMatricula,{onlySelf: true, emitEvent: false });
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
			  this.detMatriculaService.eliminar(id).subscribe(
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
	public confirmarEliminar(detMatriculaTemp : DetMatricula) {
		this.detMatricula = detMatriculaTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.detMatricula.idDetMatricula);
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(detMatriculaTemp : DetMatricula) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.detMatricula = Object.assign({}, detMatriculaTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.detMatricula = Object.assign({}, detMatriculaTemp);
					this.lanzarDetMatricula();
				}
			}
			this.frmGroup.patchValue(this.detMatricula,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(detMatriculaTemp : DetMatricula) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				detMatriculaTemp.checked = true;
				this.agregarCheck(detMatriculaTemp);
				this.dialogRef.close(this.listaDetMatriculaSelectMap);
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
			this.listaDetMatricula = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.detMatriculaService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaDetMatricula = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaDetMatricula.length == 1) {
            this.asociar(this.listaDetMatricula[0]);
		}
	}
	
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.detMatriculaService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaDetMatricula = data.listaResultado;
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
	
  private lanzarDetMatricula(){
    // Usamos el mÃ©todo emit
    this.change.emit({detMatricula: this.detMatricula});
  }
  
   /*
   agregar check
   */
  public agregarCheck(detMatriculaTemp : DetMatricula) {
     if (detMatriculaTemp.checked) {
		 if (!this.listaDetMatriculaSelectMap.has(detMatriculaTemp.idDetMatricula)) {
			this.listaDetMatriculaSelectMap.set(detMatriculaTemp.idDetMatricula,detMatriculaTemp);
		 }
	 } else {
		if ((this.listaDetMatriculaSelectMap.has(detMatriculaTemp.idDetMatricula))) {
			this.listaDetMatriculaSelectMap.delete(detMatriculaTemp.idDetMatricula);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaDetMatricula.forEach((data) => {
       if ((this.listaDetMatriculaSelectMap.has(data.idDetMatricula))) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal Matricula input
  */	
  public abrirModalMatriculaInputmatricula(pSearch? : string) {
	 this.frmGroup.get('matricula.idMatricula').setValue(null);
	 this.abrirModalMatriculamatricula(pSearch);
  }

  /**
	El abrir modal matricula. 
  */   
  public abrirModalMatriculamatricula(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Matricula  = new Matricula();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalMatricula  = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idMatricula;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('matricula').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
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
}