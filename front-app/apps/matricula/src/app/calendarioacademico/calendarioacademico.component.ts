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

import {CalendarioAcademicoService} from "./calendarioacademico.service";
import {CalendarioAcademico} from "../../../models/matricula/calendarioacademico.model";
import {Anhio} from "../../../models/matricula/anhio.model";
import {Periodo} from "../../../models/matricula/periodo.model";
import {Unidad} from "../../../models/matricula/unidad.model";
import {Item} from "../../../models/common/item.model";


/**
 * La Class CalendarioAcademicoComponent.
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
  selector: 'app-calendarioacademico',
  templateUrl: './calendarioacademico.component.html',
  styleUrls: ['./calendarioacademico.component.css'],
  providers: [CalendarioAcademicoService]
})
export class CalendarioAcademicoComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto calendario academico. */
    public calendarioAcademico : CalendarioAcademico = new CalendarioAcademico();
	 
	/** La lista calendario academico. */
    public listaCalendarioAcademico : CalendarioAcademico[] = [];
    
	/** La lista item select. */
    public listaCalendarioAcademicoSelectMap : Map<string,CalendarioAcademico> = new Map<string,CalendarioAcademico>();

	
	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private calendarioAcademicoService: CalendarioAcademicoService,
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
		this.crearFormulario(this.calendarioAcademico);
	}
	
	private  crearFormulario(obj : CalendarioAcademico) : void{
		this.frmGroup = this.fb.group({
			anhio: this.fb.group({
				idAnhio: [obj.anhio.idAnhio ],
				descripcionView: [obj.anhio.descripcionView ,{ updateOn: 'blur' }]
			 }),
			periodo: this.fb.group({
				idPeriodo: [obj.periodo.idPeriodo ],
				descripcionView: [obj.periodo.descripcionView ,{ updateOn: 'blur' }]
			 }),
			unidad: this.fb.group({
				idUnidad: [obj.unidad.idUnidad ],
				descripcionView: [obj.unidad.descripcionView ,{ updateOn: 'blur' }]
			 }),
			itemByNivel: this.fb.group({
				idItem: [obj.itemByNivel.idItem ],
				descripcionView: [obj.itemByNivel.descripcionView ,{ updateOn: 'blur' }]
			 }),
			idCalendarioAcademico: [obj.idCalendarioAcademico  , [Validators.required] ],
			descripcion: [obj.descripcion ],
			codigoCronograma: [obj.codigoCronograma ],
			fechaInicio: [obj.fechaInicio ],
			fechaFin: [obj.fechaFin ],
			estado: [obj.estado ],
			usuarioCreacion: [obj.usuarioCreacion ],
			fechaCreacion: [obj.fechaCreacion ],
			usuarioModificacion: [obj.usuarioModificacion ],
			fechaModificacion: [obj.fechaModificacion ],
		  });
		  this.onChange();
	}
	
	private onChange():void{
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});
		
		this.frmGroup.get('anhio.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalAnhioInputanhio(value);
		});
		this.frmGroup.get('periodo.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalPeriodoInputperiodo(value);
		});
		this.frmGroup.get('unidad.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalUnidadInputunidad(value);
		});
		this.frmGroup.get('itemByNivel.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalItemInputitemByNivel(value);
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
				this.calendarioAcademicoService.crear(this.frmGroup.value).subscribe(
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
				this.calendarioAcademicoService.modificar(this.frmGroup.value,this.frmGroup.value.idCalendarioAcademico).subscribe(
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
	 	this.calendarioAcademico  = new CalendarioAcademico();
		this.frmGroup.patchValue(this.calendarioAcademico,{onlySelf: true, emitEvent: false });
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
			this.listaCalendarioAcademico = [];
			this.limpiaDataProvider(this.search);
			this.calendarioAcademico = new CalendarioAcademico();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.calendarioAcademico,{onlySelf: true, emitEvent: false });
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
			  this.calendarioAcademicoService.eliminar(id).subscribe(
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
	public confirmarEliminar(calendarioAcademicoTemp : CalendarioAcademico) {
		this.calendarioAcademico = calendarioAcademicoTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.calendarioAcademico.idCalendarioAcademico);
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(calendarioAcademicoTemp : CalendarioAcademico) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.calendarioAcademico = Object.assign({}, calendarioAcademicoTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.calendarioAcademico = Object.assign({}, calendarioAcademicoTemp);
					this.lanzarCalendarioAcademico();
				}
			}
			this.frmGroup.patchValue(this.calendarioAcademico,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(calendarioAcademicoTemp : CalendarioAcademico) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				calendarioAcademicoTemp.checked = true;
				this.agregarCheck(calendarioAcademicoTemp);
				this.dialogRef.close(this.listaCalendarioAcademicoSelectMap);
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
			this.listaCalendarioAcademico = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.calendarioAcademicoService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaCalendarioAcademico = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaCalendarioAcademico.length == 1) {
            this.asociar(this.listaCalendarioAcademico[0]);
		}
	}
	
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.calendarioAcademicoService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaCalendarioAcademico = data.listaResultado;
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
	
  private lanzarCalendarioAcademico(){
    // Usamos el mÃ©todo emit
    this.change.emit({calendarioAcademico: this.calendarioAcademico});
  }
  
   /*
   agregar check
   */
  public agregarCheck(calendarioAcademicoTemp : CalendarioAcademico) {
     if (calendarioAcademicoTemp.checked) {
		 if (!this.listaCalendarioAcademicoSelectMap.has(calendarioAcademicoTemp.idCalendarioAcademico)) {
			this.listaCalendarioAcademicoSelectMap.set(calendarioAcademicoTemp.idCalendarioAcademico,calendarioAcademicoTemp);
		 }
	 } else {
		if ((this.listaCalendarioAcademicoSelectMap.has(calendarioAcademicoTemp.idCalendarioAcademico))) {
			this.listaCalendarioAcademicoSelectMap.delete(calendarioAcademicoTemp.idCalendarioAcademico);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaCalendarioAcademico.forEach((data) => {
       if ((this.listaCalendarioAcademicoSelectMap.has(data.idCalendarioAcademico))) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal Anhio input
  */	
  public abrirModalAnhioInputanhio(pSearch? : string) {
	 this.frmGroup.get('anhio.idAnhio').setValue(null);
	 this.abrirModalAnhioanhio(pSearch);
  }

  /**
	El abrir modal anhio. 
  */   
  public abrirModalAnhioanhio(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Anhio  = new Anhio();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalAnhio = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idAnhio;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('anhio').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
  /**
  Abrir modal Periodo input
  */	
  public abrirModalPeriodoInputperiodo(pSearch? : string) {
	 this.frmGroup.get('periodo.idPeriodo').setValue(null);
	 this.abrirModalPeriodoperiodo(pSearch);
  }

  /**
	El abrir modal periodo. 
  */   
  public abrirModalPeriodoperiodo(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Periodo  = new Periodo();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalPeriodo = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idPeriodo;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('periodo').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
  /**
  Abrir modal Unidad input
  */	
  public abrirModalUnidadInputunidad(pSearch? : string) {
	 this.frmGroup.get('unidad.idUnidad').setValue(null);
	 this.abrirModalUnidadunidad(pSearch);
  }

  /**
	El abrir modal unidad. 
  */   
  public abrirModalUnidadunidad(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Unidad  = new Unidad();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalUnidad = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idUnidad;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('unidad').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
  /**
  Abrir modal Item input
  */	
  public abrirModalItemInputitemByNivel(pSearch? : string) {
	 this.frmGroup.get('itemByNivel.idItem').setValue(null);
	 this.abrirModalItemitemByNivel(pSearch);
  }

  /**
	El abrir modal item. 
  */   
  public abrirModalItemitemByNivel(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Item  = new Item();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   dialogRef.componentInstance.esModalItem = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idItem;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('itemByNivel').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
}