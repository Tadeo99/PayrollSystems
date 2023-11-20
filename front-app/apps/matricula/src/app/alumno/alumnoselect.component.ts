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

import {AlumnoService} from "./alumno.service";
import {Alumno} from "../../../models/matricula/alumno.model";
import {Postulante} from "../../../models/admision/postulante.model";
import {Grado} from "../../../models/admision/grado.model";
import {Item} from "../../../models/common/item.model";
import {Ubigeo} from "../../../models/common/ubigeo.model";
import {Entidad} from "../../../models/seguridad/entidad.model";
import {Sede} from "../../../models/admision/sede.model";


/**
 * La Class AlumnoComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
 
@Component({
  selector: 'app-alumnoselect',
  templateUrl: './alumnoselect.component.html',
  styleUrls: ['./alumno.component.css'],
  providers: [AlumnoService]
})
export class AlumnoSelectComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto alumno. */
    public alumno : Alumno = new Alumno();
	 
	/** La lista alumno. */
    public listaAlumno : Alumno[] = [];
    
	/** La lista item select. */
    public listaAlumnoSelectMap : Map<string,Alumno> = new Map<string,Alumno>();

	
	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private alumnoService: AlumnoService,
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
		this.crearFormulario(this.alumno);
	}
	
		
	private  crearFormulario(obj : Alumno) : void{
		this.frmGroup = this.fb.group({
			search: ['']
		  });
		  this.onChange();
	}
	
	private onChange():void{
		 
	}
  
	 
	onInit() {
    /*var id = this.route.params.subscribe(params => {
      var id = params['id'];

    });*/
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
			this.listaAlumno = [];
			this.limpiaDataProvider(this.search);
			this.alumno = new Alumno();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.alumno,{onlySelf: true, emitEvent: false });
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
			  this.alumnoService.eliminar(id).subscribe(
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
	public confirmarEliminar(alumnoTemp : Alumno) {
		this.alumno = alumnoTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.alumno.idAlumno);
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(alumnoTemp : Alumno) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.alumno = Object.assign({}, alumnoTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.alumno = Object.assign({}, alumnoTemp);
					this.lanzarAlumno();
				}
			}
			this.frmGroup.patchValue(this.alumno,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(alumnoTemp : Alumno) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				alumnoTemp.checked = true;
				this.agregarCheck(alumnoTemp);
				this.dialogRef.close(this.listaAlumnoSelectMap);
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
			this.listaAlumno = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.params = this.params.set('idEntidadSelect',this.idEntidad);
			this.alumnoService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaAlumno = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaAlumno.length == 1) {
            this.asociar(this.listaAlumno[0]);
		}
	}
	
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.alumnoService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaAlumno = data.listaResultado;
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
	
  private lanzarAlumno(){
    // Usamos el mÃ©todo emit
    this.change.emit({alumno: this.alumno});
  }
  
   /*
   agregar check
   */
  public agregarCheck(alumnoTemp : Alumno) {
     if (alumnoTemp.checked) {
		 if (!this.listaAlumnoSelectMap.has(alumnoTemp.idAlumno)) {
			this.listaAlumnoSelectMap.set(alumnoTemp.idAlumno,alumnoTemp);
		 }
	 } else {
		if ((this.listaAlumnoSelectMap.has(alumnoTemp.idAlumno))) {
			this.listaAlumnoSelectMap.delete(alumnoTemp.idAlumno);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaAlumno.forEach((data) => {
       if ((this.listaAlumnoSelectMap.has(data.idAlumno))) {
			data.checked = true;
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
   //dialogRef.componentInstance.esModalPostulante = true;
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
   //dialogRef.componentInstance.esModalGrado = true;
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
  Abrir modal Item input
  */	
  public abrirModalItemInputitemByDocIdentidad(pSearch? : string) {
	 this.frmGroup.get('itemByDocIdentidad.idItem').setValue(null);
	 this.abrirModalItemitemByDocIdentidad(pSearch);
  }

  /**
	El abrir modal item. 
  */   
  public abrirModalItemitemByDocIdentidad(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Item  = new Item();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   dialogRef.componentInstance.esModalItem = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idItem;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('itemByDocIdentidad').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
  /**
  Abrir modal Ubigeo input
  */	
  public abrirModalUbigeoInputlugarNacimiento(pSearch? : string) {
	 this.frmGroup.get('lugarNacimiento.idUbigeo').setValue(null);
	 this.abrirModalUbigeolugarNacimiento(pSearch);
  }

  /**
	El abrir modal ubigeo. 
  */   
  public abrirModalUbigeolugarNacimiento(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Ubigeo  = new Ubigeo();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   dialogRef.componentInstance.esModalUbigeo = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idUbigeo;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('lugarNacimiento').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
  /**
  Abrir modal Item input
  */	
  public abrirModalItemInputitemByNacionalidad(pSearch? : string) {
	 this.frmGroup.get('itemByNacionalidad.idItem').setValue(null);
	 this.abrirModalItemitemByNacionalidad(pSearch);
  }

  /**
	El abrir modal item. 
  */   
  public abrirModalItemitemByNacionalidad(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Item  = new Item();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   dialogRef.componentInstance.esModalItem = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idItem;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('itemByNacionalidad').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
  /**
  Abrir modal Entidad input
  */	
  public abrirModalEntidadInputentidad(pSearch? : string) {
	 this.frmGroup.get('entidad.idEntidad').setValue(null);
	 this.abrirModalEntidadentidad(pSearch);
  }

  /**
	El abrir modal entidad. 
  */   
  public abrirModalEntidadentidad(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : Entidad  = new Entidad();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalEntidad = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idEntidad;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('entidad').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
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
}