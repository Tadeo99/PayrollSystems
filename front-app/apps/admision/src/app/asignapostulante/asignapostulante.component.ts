import {Component, EventEmitter, OnInit,OnChanges,SimpleChanges,AfterViewInit} from '@angular/core';
import {FormControl,FormBuilder} from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import {TranslateService} from '@ngx-translate/core';

import {CommonServiceImpl} from "../../common/common.impl.service";
import {LoginService} from "../../seguridad/login/login.service";
import {TypeSelectItemService} from "../../../typeselectitemservice/typeselectitem.service";

import {BaseComponent,DialogConfirmContent,DialogContent} from "../../../base/base.component";
import {MatDialog,MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';

import {AsignaPostulanteService} from "./asignapostulante.service";
import {AsignaPostulante} from "../../../models/admision/asignapostulante.model";
import {Apoderado} from "../../../models/admision/apoderado.model";
import {Postulante} from "../../../models/admision/postulante.model";
import {Sede} from "../../../models/admision/sede.model";
import {Grado} from "../../../models/admision/grado.model";
import { Anhio } from '../../../models/matricula/anhio.model';



/**
 * La Class AsignaPostulanteComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Nov 05 22:14:39 COT 2020
 * @since BUILDERP-CORE 2.1
 */
 
@Component({
  selector: 'app-asignapostulante',
  templateUrl: './asignapostulante.component.html',
  styleUrls: ['./asignapostulante.component.css'],
  providers: [AsignaPostulanteService]
})
export class AsignaPostulanteComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto asigna postulante. */
    public asignaPostulante : AsignaPostulante = new AsignaPostulante();
	 
	/** La lista asigna postulante. */
    public listaAsignaPostulante : AsignaPostulante[] = [];
    
	/** La lista item select. */
    public listaAsignaPostulanteSelectMap : Map<string,AsignaPostulante> = new Map<string,AsignaPostulante>();

	
	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private asignaPostulanteService: AsignaPostulanteService,
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
		this.crearFormulario(this.asignaPostulante);
	}
	
	private  crearFormulario(obj : AsignaPostulante) : void{
		this.frmGroup = this.fb.group({
			apoderado: this.fb.group({
				idApoderado: [obj.apoderado ]
			 }),
			postulante: this.fb.group({				
				idPostulante: [obj.postulante.idPostulante ],
				descripcionView: [obj.postulante.descripcionView ,{ updateOn: 'blur' }]
			 }),
			sede: this.fb.group({
				idSede: [obj.sede ]
			 }),
			grado: this.fb.group({
				idGrado: [obj.grado.idGrado ],
				descripcionView: [obj.grado.descripcionView ,{ updateOn: 'blur' }]
			 }),
			anhio: this.fb.group({
				idAnhio: [obj. anho ]
			 }),
			idAsignaPostulante: [obj.idAsignaPostulante ],
			estado: [obj.estado ],
		  });
		  this.onChange();
	}
	
	private onChange():void{
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});
		
		this.frmGroup.get('apoderado.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalApoderadoInputapoderado(value);
		});
		this.frmGroup.get('postulante.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalPostulanteInputpostulante(value);
		});
		this.frmGroup.get('sede.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalSedeInputsede(value);
		});
		this.frmGroup.get('grado.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalGradoInputgrado(value);
		});
		this.frmGroup.get('anhio.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalAnhioInputanhio(value);
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
				this.asignaPostulanteService.crear(this.frmGroup.value).subscribe(
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
				this.asignaPostulanteService.modificar(this.frmGroup.value,this.frmGroup.value.idAsignaPostulante).subscribe(
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
	 	this.asignaPostulante  = new AsignaPostulante();
		this.frmGroup.patchValue(this.asignaPostulante,{onlySelf: true, emitEvent: false });
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
			this.listaAsignaPostulante= [];
			this.limpiaDataProvider(this.search);
			this.asignaPostulante = new AsignaPostulante();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.asignaPostulante,{onlySelf: true, emitEvent: false });
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
			  this.asignaPostulanteService.eliminar(id).subscribe(
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
	public confirmarEliminar(asignaPostulanteTemp : AsignaPostulante) {
		this.asignaPostulante = asignaPostulanteTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialog(dialogRef,this.cargarMensaje('confirmar.eliminar.menssage'),null,null,this.cargarMensaje('confirmar.mensaje.title'));
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.asignaPostulante.idAsignaPostulante);
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(asignaPostulanteTemp : AsignaPostulante) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.asignaPostulante = Object.assign({}, asignaPostulanteTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.asignaPostulante = Object.assign({}, asignaPostulanteTemp);
					this.lanzarAsignaPostulante();
				}
			}
			this.frmGroup.patchValue(this.asignaPostulante,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(asignaPostulanteTemp : AsignaPostulante) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				asignaPostulanteTemp.checked = true;
				this.agregarCheck(asignaPostulanteTemp);
				this.dialogRef.close(this.listaAsignaPostulanteSelectMap);
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
			this.listaAsignaPostulante = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.asignaPostulanteService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaAsignaPostulante = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaAsignaPostulante.length == 1) {
            this.asociar(this.listaAsignaPostulante[0]);
		}
	}
	
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.asignaPostulanteService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaAsignaPostulante = data.listaResultado;
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
	
  private lanzarAsignaPostulante(){
    // Usamos el mÃ©todo emit
    this.change.emit({asignaPostulante: this.asignaPostulante});
  }
  
   /*
   agregar check
   */
  public agregarCheck(asignaPostulanteTemp : AsignaPostulante) {
     if (asignaPostulanteTemp.checked) {
		 if (!this.listaAsignaPostulanteSelectMap.has(asignaPostulanteTemp.idAsignaPostulante)) {
			this.listaAsignaPostulanteSelectMap.set(asignaPostulanteTemp.idAsignaPostulante,asignaPostulanteTemp);
		 }        
	 } else {
		if ((this.listaAsignaPostulanteSelectMap.has(asignaPostulanteTemp.idAsignaPostulante))) {
			this.listaAsignaPostulanteSelectMap.delete(asignaPostulanteTemp.idAsignaPostulante);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaAsignaPostulante.forEach((data) => {
       if ((this.listaAsignaPostulanteSelectMap.has(data.idAsignaPostulante))) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal Apoderado input
  */	
  public abrirModalApoderadoInputapoderado(pSearch? : string) {
	 this.frmGroup.get('apoderado.idApoderado').setValue(null);
	 this.abrirModalApoderadoapoderado(pSearch);
  }

  /**
	El abrir modal apoderado. 
  */   
  public abrirModalApoderadoapoderado(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Apoderado  = new Apoderado();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
  // dialogRef.componentInstance.esModalApoderado = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idApoderado;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('apoderado').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
  /**
  Abrir modal Postulante input
  */	
  public abrirModalPostulanteInputpostulante(pSearch? : string) {
	 this.frmGroup.get('postulante.idPostulante').setValue(null);
	 this.abrirModalPostulantepostulante(pSearch);
  }

  /**
	El abrir modal postulante. 
  */   
  public abrirModalPostulantepostulante(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Postulante  = new Postulante();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
  // //dialogRef.componentInstance.esModalPostulante = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idPostulante;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('postulante').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
  /**
  Abrir modal Sede input
  */	
  public abrirModalSedeInputsede(pSearch? : string) {
	 this.frmGroup.get('sede.idSede').setValue(null);
	 this.abrirModalSedesede(pSearch);
  }

  /**
	El abrir modal sede. 
  */   
  public abrirModalSedesede(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Sede  = new Sede();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalSede = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idSede;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('sede').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
  /**
  Abrir modal Grado input
  */	
  public abrirModalGradoInputgrado(pSearch? : string) {
	 this.frmGroup.get('grado.idGrado').setValue(null);
	 this.abrirModalGradogrado(pSearch);
  }

  /**
	El abrir modal grado. 
  */   
  public abrirModalGradogrado(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Grado  = new Grado();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   ////dialogRef.componentInstance.esModalGrado = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idGrado;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('grado').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
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
}