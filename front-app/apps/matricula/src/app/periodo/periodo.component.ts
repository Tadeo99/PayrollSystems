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

import {PeriodoService} from "./periodo.service";
import {Periodo} from "../../../models/matricula/periodo.model";


/**
 * La Class PeriodoComponent.
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
  selector: 'app-periodo',
  templateUrl: './periodo.component.html',
  styleUrls: ['./periodo.component.css'],
  providers: [PeriodoService]
})
export class PeriodoComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto periodo. */
    public periodo : Periodo = new Periodo();
	 
	/** La lista periodo. */
    public listaPeriodo : Periodo[] = [];
    
	/** La lista item select. */
    public listaPeriodoSelectMap : Map<number,Periodo> = new Map<number,Periodo>();

	public periodoValidar: Periodo = new Periodo();

	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private periodoService: PeriodoService,
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
		this.crearFormulario(this.periodo);
	}
	
	private  crearFormulario(obj : Periodo) : void{
		this.frmGroup = this.fb.group({
			idPeriodo: [obj.idPeriodo],
			tipo: [obj.tipo ],
			descripcion: [obj.descripcion ],
			abreviatura: [obj.abreviatura ],
			estado: [obj.estado ],
		  });
		  this.onChange();
	}
	
	private onChange():void{
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});
		this.frmGroup.get('descripcion').valueChanges.subscribe(value => {
			this.validarCampo = true;
		});
		this.frmGroup.get('tipo').valueChanges.subscribe(value => {
			this.validarCampo = true;
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
				this.periodoService.crear(this.frmGroup.value).subscribe(
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
				this.periodoService.modificar(this.frmGroup.value,this.frmGroup.value.idPeriodo).subscribe(
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
	 	this.periodo  = new Periodo();
		this.periodo.estado = "A";
		this.frmGroup.patchValue(this.periodo,{onlySelf: true, emitEvent: false });
		this.mostrarPanelForm = true;
		this.accionNuevo = true;
		this.validarCampo = true;
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
			this.listaPeriodo = [];
			this.limpiaDataProvider(this.search);
			this.periodo = new Periodo();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.periodo,{onlySelf: true, emitEvent: false });
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
			  this.periodoService.eliminar(id).subscribe(
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
	public confirmarEliminar(periodoTemp : Periodo) {
		this.periodo = periodoTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.periodo.idPeriodo);
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(periodoTemp : Periodo) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.periodo = Object.assign({}, periodoTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
				this.validarCampo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.periodo = Object.assign({}, periodoTemp);
					this.lanzarPeriodo();
				}
			}
			this.frmGroup.patchValue(this.periodo,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(periodoTemp : Periodo) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				periodoTemp.checked = true;
				this.agregarCheck(periodoTemp);
				this.dialogRef.close(this.listaPeriodoSelectMap);
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
			this.listaPeriodo = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.periodoService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaPeriodo = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaPeriodo.length == 1) {
            this.asociar(this.listaPeriodo[0]);
		}
	}
	
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.periodoService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaPeriodo = data.listaResultado;
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
	
  private lanzarPeriodo(){
    // Usamos el mÃ©todo emit
    this.change.emit({periodo: this.periodo});
  }
  
   /*
   agregar check
   */
  public agregarCheck(periodoTemp : Periodo) {
     if (periodoTemp.checked) {
		 if (!this.listaPeriodoSelectMap.has(periodoTemp.idPeriodo)) {
			this.listaPeriodoSelectMap.set(periodoTemp.idPeriodo,periodoTemp);
		 }
	 } else {
		if ((this.listaPeriodoSelectMap.has(periodoTemp.idPeriodo))) {
			this.listaPeriodoSelectMap.delete(periodoTemp.idPeriodo);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaPeriodo.forEach((data) => {
       if ((this.listaPeriodoSelectMap.has(data.idPeriodo))) {
			data.checked = true;
	   }
    });
  }
  public findPeriodo() {	
	this.periodoService.findPeriodo(this.frmGroup.value).subscribe(
		data => {
			if (this.isProcesoOK(data)) {
				this.periodoValidar = data.objetoResultado;
				if (this.validarCampo) {
					if (this.periodoValidar.idPeriodo != null) {
						this.mostrarMensajeAdvertencia(this.periodoValidar.descripcion +  ' ya Existe');
					} else {
						this.guardar();
					}
				} else {
					this.guardar();
				}
			}
		},
		error => {
			this.mostrarMensajeError(error);
		},
		() => {
			this.endProgres();
		}
	);
}
}