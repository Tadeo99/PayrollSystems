import {Component, EventEmitter, OnInit,OnChanges,SimpleChanges,AfterViewInit, Optional} from '@angular/core';
import {FormControl,FormBuilder,Validators} from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import {TranslateService} from '@ngx-translate/core';

import {CommonServiceImpl} from "../../common/common.impl.service";
import {LoginService} from "../../seguridad/login/login.service";
import {TypeSelectItemService} from "../../../typeselectitemservice/typeselectitem.service";

import {BaseComponent,DialogConfirmContent,DialogContent} from "../../../base/base.component";
import {MatDialog,MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';

import {AulaService} from "./aula.service";
import {Aula} from "../../../models/matricula/aula.model";
import {Pabellon} from "../../../models/matricula/pabellon.model";
import { BaseDialogContent } from '../../../base/base.dialog.content.component';


/**
 * La Class AulaComponent.
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
  selector: 'app-aula',
  templateUrl: './aula.component.html',
  styleUrls: ['./aula.component.css'],
  providers: [AulaService]
})
export class AulaComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto aula. */
    public aula : Aula = new Aula();
	 
	/** La lista aula. */
    public listaAula : Aula[] = [];
    
	/** La lista item select. */
    public listaAulaSelectMap : Map<number,Aula> = new Map<number,Aula>();

	
	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private aulaService: AulaService,
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
		this.crearFormulario(this.aula);
	}
	
	private  crearFormulario(obj : Aula) : void{
		this.frmGroup = this.fb.group({
			pabellon: this.fb.group({
				idPabellon: [obj.pabellon.idPabellon ],
				descripcionView: [obj.pabellon.descripcionView ,{ updateOn: 'blur' }]
			 }),
			idAula: [obj.idAula],
			descripcion: [obj.descripcion  , [Validators.required] ],
			abreviatura: [obj.abreviatura ],
			estado: [obj.estado  , [Validators.required] ],
		  });
		  this.onChange();
	}
	
	private onChange():void{
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});
		
		this.frmGroup.get('pabellon.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalPabellonInputpabellon(value);
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
				this.aulaService.crear(this.frmGroup.value).subscribe(
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
				this.aulaService.modificar(this.frmGroup.value,this.frmGroup.value.idAula).subscribe(
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
	 	this.aula  = new Aula();
		this.aula.estado = "A";
		this.frmGroup.patchValue(this.aula,{onlySelf: true, emitEvent: false });
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
			this.listaAula = [];
			this.limpiaDataProvider(this.search);
			this.aula = new Aula();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.aula,{onlySelf: true, emitEvent: false });
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
			  this.aulaService.eliminar(id).subscribe(
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
	public confirmarEliminar(aulaTemp : Aula) {
		this.aula = aulaTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.aula.idAula);
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(aulaTemp : Aula) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.aula = Object.assign({}, aulaTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.aula = Object.assign({}, aulaTemp);
					this.lanzarAula();
				}
			}
			this.frmGroup.patchValue(this.aula,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(aulaTemp : Aula) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				aulaTemp.checked = true;
				this.agregarCheck(aulaTemp);
				this.dialogRef.close(this.listaAulaSelectMap);
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
			this.listaAula = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.params = this.params.set('idEntidadSelect',this.idEntidad);
			this.aulaService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaAula = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaAula.length == 1) {
            this.asociar(this.listaAula[0]);
		}
	}
	
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.aulaService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaAula = data.listaResultado;
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
	
  private lanzarAula(){
    // Usamos el mÃ©todo emit
    this.change.emit({aula: this.aula});
  }
  
   /*
   agregar check
   */
  public agregarCheck(aulaTemp : Aula) {
     if (aulaTemp.checked) {
		 if (!this.listaAulaSelectMap.has(aulaTemp.idAula)) {
			this.listaAulaSelectMap.set(aulaTemp.idAula,aulaTemp);
		 }
	 } else {
		if ((this.listaAulaSelectMap.has(aulaTemp.idAula))) {
			this.listaAulaSelectMap.delete(aulaTemp.idAula);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaAula.forEach((data) => {
       if ((this.listaAulaSelectMap.has(data.idAula))) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal Pabellon input
  */	
  public abrirModalPabellonInputpabellon(pSearch? : string) {
	 this.frmGroup.get('pabellon.idPabellon').setValue(null);
	 this.abrirModalPabellonpabellon(pSearch);
  }

  /**
	El abrir modal pabellon. 
  */   
  public abrirModalPabellonpabellon(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContentOverride);
   let data : Pabellon  = new Pabellon();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   dialogRef.componentInstance.esModalPabellon = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			 this.frmGroup.get('pabellon.idPabellon').setValue(entryVal.idPabellon);
			 this.frmGroup.get('pabellon').patchValue(entryVal,{onlySelf: true, emitEvent: false });
		});
     }
   });
  }
}

@Component({
	template: `
	<app-pabellon *ngIf="esModalPabellon" [id] = "id" [esIncludeComponent] = "esIncludeComponent"  [showModal]="showModal" [showSelectMultiple] = "showSelectMultiple" [dialogRef] = "dialogRef" [data] = "data"></app-pabellon>
	 `,
  })
  export class DialogContentOverride extends BaseDialogContent {
	public esModalPabellon: boolean = false;
	constructor(@Optional() public dialogRef: MatDialogRef<DialogContentOverride>) {super() }
  }