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

import {RentaQuintaCategoriaService} from "./rentaquintacategoria.service";
import {RentaQuintaCategoria} from "../../../models/rrhh_planilla/rentaquintacategoria.model";
import {TipoPlanilla} from "../../../models/rrhh_planilla/tipoplanilla.model";
import {PeriodoPlanilla} from "../../../models/rrhh_planilla/periodoplanilla.model";


/**
 * La Class RentaQuintaCategoriaComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:47 COT 2021
 * @since BUILDERP-CORE 2.1
 */
 
@Component({
  selector: 'app-rentaquintacategoria',
  templateUrl: './rentaquintacategoria.component.html',
  styleUrls: ['./rentaquintacategoria.component.css'],
  providers: [RentaQuintaCategoriaService]
})
export class RentaQuintaCategoriaComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto renta quinta categoria. */
    public rentaQuintaCategoria : RentaQuintaCategoria = new RentaQuintaCategoria();
	 
	/** La lista renta quinta categoria. */
    public listaRentaQuintaCategoria : RentaQuintaCategoria[] = [];
    
	/** La lista item select. */
    public listaRentaQuintaCategoriaSelectMap : Map<string,RentaQuintaCategoria> = new Map<string,RentaQuintaCategoria>();

	
	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private rentaQuintaCategoriaService: RentaQuintaCategoriaService,
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
		this.crearFormulario(this.rentaQuintaCategoria);
	}
	
	private  crearFormulario(obj : RentaQuintaCategoria) : void{
		this.frmGroup = this.fb.group({
			tipoPlanilla: this.fb.group({
				idTipoPlanilla: [obj.tipoPlanilla.idTipoPlanilla  , [Validators.required] ],
				descripcionView: [obj.tipoPlanilla.descripcionView  , [Validators.required] ,{ updateOn: 'blur' }]
			 }),
			periodoPlanilla: this.fb.group({
				idPeriodoPlanilla: [obj.periodoPlanilla.idPeriodoPlanilla  , [Validators.required] ],
				descripcionView: [obj.periodoPlanilla.descripcionView  , [Validators.required] ,{ updateOn: 'blur' }]
			 }),
			idRentaQuintaCategoria: [obj.idRentaQuintaCategoria  , [Validators.required] ],
		  });
		  this.onChange();
	}
	
	private onChange():void{
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});
		
		this.frmGroup.get('tipoPlanilla.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalTipoPlanillaInputtipoPlanilla(value);
		});
		this.frmGroup.get('periodoPlanilla.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalPeriodoPlanillaInputperiodoPlanilla(value);
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
				this.rentaQuintaCategoriaService.crear(this.frmGroup.value).subscribe(
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
				this.rentaQuintaCategoriaService.modificar(this.frmGroup.value,this.frmGroup.value.idRentaQuintaCategoria).subscribe(
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
	 	this.rentaQuintaCategoria  = new RentaQuintaCategoria();
		this.frmGroup.patchValue(this.rentaQuintaCategoria,{onlySelf: true, emitEvent: false });
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
			this.listaRentaQuintaCategoria = [];
			this.limpiaDataProvider(this.search);
			this.rentaQuintaCategoria = new RentaQuintaCategoria();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.rentaQuintaCategoria,{onlySelf: true, emitEvent: false });
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
			  this.rentaQuintaCategoriaService.eliminar(id).subscribe(
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
	public confirmarEliminar(rentaQuintaCategoriaTemp : RentaQuintaCategoria) {
		this.rentaQuintaCategoria = rentaQuintaCategoriaTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.rentaQuintaCategoria.idRentaQuintaCategoria);
		 }
		});
    }
	 
	/**
	 * buscar id
	 *
	 */
	public buscarID(rentaQuintaCategoriaTemp : RentaQuintaCategoria) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.rentaQuintaCategoria = Object.assign({}, rentaQuintaCategoriaTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.rentaQuintaCategoria = Object.assign({}, rentaQuintaCategoriaTemp);
					this.lanzarRentaQuintaCategoria();
				}
			}
			this.frmGroup.patchValue(this.rentaQuintaCategoria,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(rentaQuintaCategoriaTemp : RentaQuintaCategoria) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				rentaQuintaCategoriaTemp.checked = true;
				this.agregarCheck(rentaQuintaCategoriaTemp);
				this.dialogRef.close(this.listaRentaQuintaCategoriaSelectMap);
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
			this.listaRentaQuintaCategoria = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.rentaQuintaCategoriaService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaRentaQuintaCategoria = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaRentaQuintaCategoria.length == 1) {
            this.asociar(this.listaRentaQuintaCategoria[0]);
		}
	}
	
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.rentaQuintaCategoriaService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaRentaQuintaCategoria = data.listaResultado;
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
	
  private lanzarRentaQuintaCategoria(){
    // Usamos el mÃ©todo emit
    this.change.emit({rentaQuintaCategoria: this.rentaQuintaCategoria});
  }
  
   /*
   agregar check
   */
  public agregarCheck(rentaQuintaCategoriaTemp : RentaQuintaCategoria) {
     if (rentaQuintaCategoriaTemp.checked) {
		 if (!this.listaRentaQuintaCategoriaSelectMap.has(rentaQuintaCategoriaTemp.idRentaQuintaCategoria)) {
			this.listaRentaQuintaCategoriaSelectMap.set(rentaQuintaCategoriaTemp.idRentaQuintaCategoria,rentaQuintaCategoriaTemp);
		 }
	 } else {
		if ((this.listaRentaQuintaCategoriaSelectMap.has(rentaQuintaCategoriaTemp.idRentaQuintaCategoria))) {
			this.listaRentaQuintaCategoriaSelectMap.delete(rentaQuintaCategoriaTemp.idRentaQuintaCategoria);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaRentaQuintaCategoria.forEach((data) => {
       if ((this.listaRentaQuintaCategoriaSelectMap.has(data.idRentaQuintaCategoria))) {
			data.checked = true;
	   }
    });
  }
  
  /**
  Abrir modal TipoPlanilla input
  */	
  public abrirModalTipoPlanillaInputtipoPlanilla(pSearch? : string) {
	 this.frmGroup.get('tipoPlanilla.idTipoPlanilla').setValue(null);
	 this.abrirModalTipoPlanillatipoPlanilla(pSearch);
  }

  /**
	El abrir modal tipo planilla. 
  */   
  public abrirModalTipoPlanillatipoPlanilla(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : TipoPlanilla  = new TipoPlanilla();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalTipoPlanilla = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idTipoPlanilla;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('tipoPlanilla').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
  /**
  Abrir modal PeriodoPlanilla input
  */	
  public abrirModalPeriodoPlanillaInputperiodoPlanilla(pSearch? : string) {
	 this.frmGroup.get('periodoPlanilla.idPeriodoPlanilla').setValue(null);
	 this.abrirModalPeriodoPlanillaperiodoPlanilla(pSearch);
  }

  /**
	El abrir modal periodo planilla. 
  */   
  public abrirModalPeriodoPlanillaperiodoPlanilla(pSearch? : string) {
   let dialogRef = this.dialog.open(DialogContent);
   let data : PeriodoPlanilla  = new PeriodoPlanilla();
  // data.id = id;
   dialogRef = this.abrirModal(dialogRef,true,false,false,data,pSearch);
   //dialogRef.componentInstance.esModalPeriodoPlanilla = true;
   dialogRef.afterClosed().subscribe(result => {
   if (result) {
		result.forEach((entryVal : any , entryKey : any )  => {
			  entryVal.descripcionView = entryVal.idPeriodoPlanilla;//   + ' ' +  entryVal.nombre;			
			 this.frmGroup.get('periodoPlanilla').patchValue(entryVal,{onlySelf: true, emitEvent: false });
			 
		});
     }
   });
  }
}