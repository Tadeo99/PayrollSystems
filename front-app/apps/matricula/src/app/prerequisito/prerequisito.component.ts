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

import {PreRequisitoService} from "./prerequisito.service";
import {PreRequisito} from "../../../models/matricula/prerequisito.model";
import {DetMallaCurricular} from "../../../models/matricula/detmallacurricular.model";


/**
 * La Class PreRequisitoComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:51 COT 2021
 * @since BUILDERP-CORE 2.1
 */
 
@Component({
  selector: 'app-prerequisito',
  templateUrl: './prerequisito.component.html',
  styleUrls: ['./prerequisito.component.css'],
  providers: [PreRequisitoService]
})
export class PreRequisitoComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto pre requisito. */
    public preRequisito : PreRequisito = new PreRequisito();
	 
	/** La lista pre requisito. */
    public listaPreRequisito : PreRequisito[] = [];
    
	/** La lista item select. */
    public listaPreRequisitoSelectMap : Map<string,PreRequisito> = new Map<string,PreRequisito>();

	
	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private preRequisitoService: PreRequisitoService,
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
		this.crearFormulario(this.preRequisito);
	}
	
	private  crearFormulario(obj : PreRequisito) : void{
		this.frmGroup = this.fb.group({
			detMallaCurricular: this.fb.group({
				idDetMallaCurricular: [obj.detMallaCurricular.idDetMallaCurricular ],
				descripcionView: [obj.detMallaCurricular.descripcionView ,{ updateOn: 'blur' }]
			 }),
			detMallaCurricularRequisito: this.fb.group({
				idDetMallaCurricular: [obj.detMallaCurricularRequisito.idDetMallaCurricular ],
				descripcionView: [obj.detMallaCurricularRequisito.descripcionView ,{ updateOn: 'blur' }]
			 }),
			idPreRequisito: [obj.idPreRequisito  , [Validators.required] ],
		  });
		  this.onChange();
	}
	
	private onChange():void{
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});
		
		this.frmGroup.get('detMallaCurricular.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalDetMallaCurricularInputdetMallaCurricular(value);
		});
		this.frmGroup.get('detMallaCurricularRequisito.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalDetMallaCurricularInputdetMallaCurricularRequisito(value);
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
				this.preRequisitoService.crear(this.frmGroup.value).subscribe(
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
				this.preRequisitoService.modificar(this.frmGroup.value,this.frmGroup.value.idPreRequisito).subscribe(
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
	 	this.preRequisito  = new PreRequisito();
		this.frmGroup.patchValue(this.preRequisito,{onlySelf: true, emitEvent: false });
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
			this.listaPreRequisito = [];
			this.limpiaDataProvider(this.search);
			this.preRequisito = new PreRequisito();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.preRequisito,{onlySelf: true, emitEvent: false });
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
			  this.preRequisitoService.eliminar(id).subscribe(
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
	public confirmarEliminar(preRequisitoTemp : PreRequisito) {
		this.preRequisito = preRequisitoTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.preRequisito.idPreRequisito);
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(preRequisitoTemp : PreRequisito) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.preRequisito = Object.assign({}, preRequisitoTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.preRequisito = Object.assign({}, preRequisitoTemp);
					this.lanzarPreRequisito();
				}
			}
			this.frmGroup.patchValue(this.preRequisito,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(preRequisitoTemp : PreRequisito) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				preRequisitoTemp.checked = true;
				this.agregarCheck(preRequisitoTemp);
				this.dialogRef.close(this.listaPreRequisitoSelectMap);
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
			this.listaPreRequisito = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.preRequisitoService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaPreRequisito = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaPreRequisito.length == 1) {
            this.asociar(this.listaPreRequisito[0]);
		}
	}
	
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.preRequisitoService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaPreRequisito = data.listaResultado;
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
	
  private lanzarPreRequisito(){
    // Usamos el mÃ©todo emit
    this.change.emit({preRequisito: this.preRequisito});
  }
  
   /*
   agregar check
   */
  public agregarCheck(preRequisitoTemp : PreRequisito) {
     if (preRequisitoTemp.checked) {
		 if (!this.listaPreRequisitoSelectMap.has(preRequisitoTemp.idPreRequisito)) {
			this.listaPreRequisitoSelectMap.set(preRequisitoTemp.idPreRequisito,preRequisitoTemp);
		 }
	 } else {
		if ((this.listaPreRequisitoSelectMap.has(preRequisitoTemp.idPreRequisito))) {
			this.listaPreRequisitoSelectMap.delete(preRequisitoTemp.idPreRequisito);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaPreRequisito.forEach((data) => {
       if ((this.listaPreRequisitoSelectMap.has(data.idPreRequisito))) {
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
  public abrirModalDetMallaCurricularInputdetMallaCurricularRequisito(pSearch? : string) {
	 this.frmGroup.get('detMallaCurricularRequisito.idDetMallaCurricular').setValue(null);
	 this.abrirModalDetMallaCurriculardetMallaCurricularRequisito(pSearch);
  }

  /**
	El abrir modal det malla curricular. 
  */   
  public abrirModalDetMallaCurriculardetMallaCurricularRequisito(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : DetMallaCurricular  = new DetMallaCurricular();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalDetMallaCurricular = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idDetMallaCurricular;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('detMallaCurricularRequisito').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
}