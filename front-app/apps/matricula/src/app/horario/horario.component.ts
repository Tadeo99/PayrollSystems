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

import {HorarioService} from "./horario.service";
import {Horario} from "../../../models/matricula/horario.model";
import {DetalleCargaAcademica} from "../../../models/matricula/detallecargaacademica.model";
import {Personal} from "../../../models/rrhh_escalafon/personal.model";
import {Item} from "../../../models/common/item.model";


/**
 * La Class HorarioComponent.
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
  selector: 'app-horario',
  templateUrl: './horario.component.html',
  styleUrls: ['./horario.component.css'],
  providers: [HorarioService]
})
export class HorarioComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto horario. */
    public horario : Horario = new Horario();
	 
	/** La lista horario. */
    public listaHorario : Horario[] = [];
    
	/** La lista item select. */
    public listaHorarioSelectMap : Map<string,Horario> = new Map<string,Horario>();

	
	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private horarioService: HorarioService,
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
		this.crearFormulario(this.horario);
	}
	
	private  crearFormulario(obj : Horario) : void{
		this.frmGroup = this.fb.group({
			detalleCargaAcademica: this.fb.group({
				idDetalleCargaAcademica: [obj.detalleCargaAcademica.idDetalleCargaAcademica  , [Validators.required] ],
				descripcionView: [obj.detalleCargaAcademica.codigoActa  , [Validators.required] ,{ updateOn: 'blur' }]
			 }),
			personalByDocente: this.fb.group({
				idPersonal: [obj.personalByDocente.idPersonal ],
				descripcionView: [obj.personalByDocente.descripcionView ,{ updateOn: 'blur' }]
			 }),
			itemByDia: this.fb.group({
				idItem: [obj.itemByDia.idItem ],
				descripcionView: [obj.itemByDia.descripcionView ,{ updateOn: 'blur' }]
			 }),
			idHorario: [obj.idHorario  , [Validators.required] ],
			horaInicio: [obj.horaInicio  , [Validators.required] ],
			horaFin: [obj.horaFin ],
		  });
		  this.onChange();
	}
	
	private onChange():void{
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});
		
		this.frmGroup.get('detalleCargaAcademica.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalDetalleCargaAcademicaInputdetalleCargaAcademica(value);
		});
		this.frmGroup.get('personalByDocente.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalPersonalInputpersonalByDocente(value);
		});
		this.frmGroup.get('itemByDia.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalItemInputitemByDia(value);
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
				this.horarioService.crear(this.frmGroup.value).subscribe(
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
				this.horarioService.modificar(this.frmGroup.value,this.frmGroup.value.idHorario).subscribe(
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
	 	this.horario  = new Horario();
		this.frmGroup.patchValue(this.horario,{onlySelf: true, emitEvent: false });
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
			this.listaHorario = [];
			this.limpiaDataProvider(this.search);
			this.horario = new Horario();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.horario,{onlySelf: true, emitEvent: false });
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
			  this.horarioService.eliminar(id).subscribe(
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
	public confirmarEliminar(horarioTemp : Horario) {
		this.horario = horarioTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.horario.idHorario);
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(horarioTemp : Horario) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.horario = Object.assign({}, horarioTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.horario = Object.assign({}, horarioTemp);
					this.lanzarHorario();
				}
			}
			this.frmGroup.patchValue(this.horario,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(horarioTemp : Horario) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				horarioTemp.checked = true;
				this.agregarCheck(horarioTemp);
				this.dialogRef.close(this.listaHorarioSelectMap);
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
			this.listaHorario = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.horarioService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaHorario = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaHorario.length == 1) {
            this.asociar(this.listaHorario[0]);
		}
	}
	
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.horarioService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaHorario = data.listaResultado;
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
	
  private lanzarHorario(){
    // Usamos el mÃ©todo emit
    this.change.emit({horario: this.horario});
  }
  
   /*
   agregar check
   */
  public agregarCheck(horarioTemp : Horario) {
     if (horarioTemp.checked) {
		 if (!this.listaHorarioSelectMap.has(horarioTemp.idHorario)) {
			this.listaHorarioSelectMap.set(horarioTemp.idHorario,horarioTemp);
		 }
	 } else {
		if ((this.listaHorarioSelectMap.has(horarioTemp.idHorario))) {
			this.listaHorarioSelectMap.delete(horarioTemp.idHorario);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaHorario.forEach((data) => {
       if ((this.listaHorarioSelectMap.has(data.idHorario))) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal DetalleCargaAcademica input
  */	
  public abrirModalDetalleCargaAcademicaInputdetalleCargaAcademica(pSearch? : string) {
	 this.frmGroup.get('detalleCargaAcademica.idDetalleCargaAcademica').setValue(null);
	 this.abrirModalDetalleCargaAcademicadetalleCargaAcademica(pSearch);
  }

  /**
	El abrir modal detalle carga academica. 
  */   
  public abrirModalDetalleCargaAcademicadetalleCargaAcademica(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : DetalleCargaAcademica  = new DetalleCargaAcademica();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalDetalleCargaAcademica = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idDetalleCargaAcademica;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('detalleCargaAcademica').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
  /**
  Abrir modal Personal input
  */	
  public abrirModalPersonalInputpersonalByDocente(pSearch? : string) {
	 this.frmGroup.get('personalByDocente.idPersonal').setValue(null);
	 this.abrirModalPersonalpersonalByDocente(pSearch);
  }

  /**
	El abrir modal personal. 
  */   
  public abrirModalPersonalpersonalByDocente(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Personal  = new Personal();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalPersonal = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idPersonal;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('personalByDocente').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
  /**
  Abrir modal Item input
  */	
  public abrirModalItemInputitemByDia(pSearch? : string) {
	 this.frmGroup.get('itemByDia.idItem').setValue(null);
	 this.abrirModalItemitemByDia(pSearch);
  }

  /**
	El abrir modal item. 
  */   
  public abrirModalItemitemByDia(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Item  = new Item();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   dialogRef.componentInstance.esModalItem = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idItem;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('itemByDia').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
}