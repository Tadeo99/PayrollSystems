import { Component, EventEmitter, OnInit, OnChanges, SimpleChanges, AfterViewInit, Optional } from '@angular/core';
import { FormControl, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

import { CommonServiceImpl } from "../../common/common.impl.service";
import { LoginService } from "../../seguridad/login/login.service";
import { TypeSelectItemService } from "../../../typeselectitemservice/typeselectitem.service";

import { BaseComponent, DialogConfirmContent, DialogContent } from "../../../base/base.component";
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { CuotaConceptoService } from "./cuotaconcepto.service";
import { CuotaConcepto } from "../../../models/pago/cuotaconcepto.model";
import { Anhio } from "../../../models/matricula/anhio.model";
import { Item } from "../../../models/common/item.model";
import { CatalogoCuenta } from "../../../models/pago/catalogocuenta.model";
import { BaseDialogContent } from '../../../base/base.dialog.content.component';
import { ListaItems } from '../../../models/common/listaitems.model';
import { ListaItemType } from '../../../type/listaitem.type';
import { MatriculaServiceImpl } from '../../matricula/matricula.impl.service';
import { EstadoGeneralState } from '../../../type/estadogeneral.state';
import { SelectItemVO } from '../../../vo/selectitem.vo';


/**
 * La Class CuotaConceptoComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:52 COT 2021
 * @since BUILDERP-CORE 2.1
 */

@Component({
	selector: 'app-cuotaconcepto',
	templateUrl: './cuotaconcepto.component.html',
	styleUrls: ['./cuotaconcepto.component.css'],
	providers: [CuotaConceptoService, MatriculaServiceImpl]
})
export class CuotaConceptoComponent extends BaseComponent implements OnInit, OnChanges, AfterViewInit {

	/** El objeto cuota concepto. */
	public cuotaConcepto: CuotaConcepto = new CuotaConcepto();

	/** La lista cuota concepto. */
	public listaCuotaConcepto: CuotaConcepto[] = [];

	/** La lista item select. */
	public listaCuotaConceptoSelectMap: Map<string, CuotaConcepto> = new Map<string, CuotaConcepto>();

	/*** El objeto anhio. */
	public anhioActivo: Anhio = new Anhio();


	/** La lista nivel. */
	public listaNivel: SelectItemVO[] = [];

	constructor(private fb: FormBuilder, public dialog: MatDialog, public snackbar: MatSnackBar, router: Router, route: ActivatedRoute, private cuotaConceptoService: CuotaConceptoService,
		protected commonServiceImpl: CommonServiceImpl, protected matriculaServiceImpl: MatriculaServiceImpl, protected loginDataService: LoginService, protected _typeSelectItemService: TypeSelectItemService, protected _translate: TranslateService) {
		super(dialog, snackbar, router, route);
		super.setTypeSelectItemService(_typeSelectItemService);
		super.setTraslate(_translate);
		this.debounceTimeProcesar().subscribe(term => { this.search = term; this.buscar() });
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
		this.crearFormulario(this.cuotaConcepto);
	}

	private crearFormulario(obj: CuotaConcepto): void {
		this.frmGroup = this.fb.group({
			anhio: this.fb.group({
				idAnhio: [obj.anhio.idAnhio],
				descripcionView: [obj.anhio.descripcionView, { updateOn: 'blur' }]
			}),
			itemByNivel: this.fb.group({
				idItem: [obj.itemByNivel.idItem],
				descripcionView: [obj.itemByNivel.descripcionView, { updateOn: 'blur' }]
			}),
			catalogoCuenta: this.fb.group({
				idCatalogoCuenta: [obj.catalogoCuenta.idCatalogoCuenta],
				descripcionView: [obj.catalogoCuenta.descripcionView, { updateOn: 'blur' }]
			}),
			idCuotaConcepto: [obj.idCuotaConcepto],
			nroMinFraccionamiento: [obj.nroMinFraccionamiento],
			nroMaxFraccionamiento: [obj.nroMaxFraccionamiento],
			monto: [obj.monto],
			subTotal: [obj.subTotal],
			igv: [obj.igv],
			permanente: [obj.permanente],
			fechaTentativa: [obj.fechaTentativa],
			fechaCreacion: [obj.fechaCreacion],
			usuarioCreacion: [obj.usuarioCreacion],
			fechaModificacion: [obj.fechaModificacion],
			usuarioModificacion: [obj.usuarioModificacion],
			aplicaIgv: [obj.aplicaIgv],
		});
		this.onChange();
	}

	private onChange(): void {
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});

		this.frmGroup.get('anhio.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalAnhioInputanhio(value);
		});
		this.frmGroup.get('itemByNivel.idItem').valueChanges.subscribe(value => {

		});
		this.frmGroup.get('catalogoCuenta.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalCatalogoCuentaInputcatalogoCuenta(value);
		});
		this.frmGroup.get('aplicaIgv').valueChanges.subscribe(value => {
			if (value == false) {
				this.frmGroup.get('igv').setValue(0);
				this.calcularSubMonto();
			} else {
				this.calcularSubMonto();
			}
		});
	}


	public async cargarCombo() {
		let paramsTemp: Map<any, any> = new Map<any, any>();
		paramsTemp = paramsTemp.set(ListaItemType.T38_NIVEL, 0);
		//paramsTemp = paramsTemp.set(ListaItemType.TURNO,0);	
		await this.commonServiceImpl.obtenerListaItemSelectItemMap(paramsTemp);
		this.listaNivel = this.commonServiceImpl.getListaItemSelectItem(ListaItemType.T38_NIVEL);
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
				this.cuotaConceptoService.crear(this.frmGroup.value).subscribe(
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
				this.cuotaConceptoService.modificar(this.frmGroup.value,this.frmGroup.value.idCuotaConcepto).subscribe(
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
		this.cuotaConcepto = new CuotaConcepto();
		this.cuotaConcepto.fechaTentativa = new Date();
		this.frmGroup.patchValue(this.cuotaConcepto, { onlySelf: true, emitEvent: false });
		this.frmGroup.get('anhio').patchValue(this.anhioActivo, { onlySelf: true, emitEvent: false });
		this.frmGroup.get('permanente').setValue("S");
		this.mostrarPanelForm = true;
		this.accionNuevo = true;
	}

	/**
	 * Inicializar.
	 *
	 */
	private async inicializar() {
		super.validarPaginaView();
		super.getUsuarioSession();
		this.anhioActivo = await this.commonServiceImpl.obtenerAnhioyEstadoAsync(EstadoGeneralState.ACTIVO);
		this.anhioActivo.descripcionView = this.obtenerDescripcionAnhio(this.anhioActivo);
		this.cargarCombo();
		this.limpiar();
	}

	/**
	 * Limpiar.
	 *
	 */
	private limpiar() {
		try {
			this.listaCuotaConcepto = [];
			this.limpiaDataProvider(this.search);
			this.cuotaConcepto = new CuotaConcepto();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.cuotaConcepto, { onlySelf: true, emitEvent: false });
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
	private eliminar(id: any) {
		try {
			this.cuotaConceptoService.eliminar(id).subscribe(
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
	public confirmarEliminar(cuotaConceptoTemp: CuotaConcepto) {
		this.cuotaConcepto = cuotaConceptoTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				this.eliminar(this.cuotaConcepto.idCuotaConcepto);
			}
		});
	}

	/**
	 * buscar id
	 *
	 */
	public buscarID(cuotaConceptoTemp: CuotaConcepto) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.cuotaConcepto = Object.assign({}, cuotaConceptoTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.cuotaConcepto = Object.assign({}, cuotaConceptoTemp);
					this.lanzarCuotaConcepto();
				}
			}
			this.frmGroup.patchValue(this.cuotaConcepto, { onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(cuotaConceptoTemp: CuotaConcepto) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				cuotaConceptoTemp.checked = true;
				this.agregarCheck(cuotaConceptoTemp);
				this.dialogRef.close(this.listaCuotaConceptoSelectMap);
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
			this.listaCuotaConcepto = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.params = this.params.set('idEntidadSelect', this.usuarioSession.entidad.idEntidad + '');
			if(this.data){
				this.params = this.params.set('id', this.data.id + '');
			}
			this.cuotaConceptoService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.setDataProvider(data);
						this.listaCuotaConcepto = data.listaResultado;
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

	private asociarData(): void {
		if (this.id != null && this.id != '' && this.listaCuotaConcepto.length == 1) {
			this.asociar(this.listaCuotaConcepto[0]);
		}
	}

	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			this.calcularStartRow();
			this.cuotaConceptoService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaCuotaConcepto = data.listaResultado;
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

	private lanzarCuotaConcepto() {
		// Usamos el mÃ©todo emit
		this.change.emit({ cuotaConcepto: this.cuotaConcepto });
	}

	/*
	agregar check
	*/
	public agregarCheck(cuotaConceptoTemp: CuotaConcepto) {
		if (cuotaConceptoTemp.checked) {
			if (!this.listaCuotaConceptoSelectMap.has(cuotaConceptoTemp.idCuotaConcepto)) {
				this.listaCuotaConceptoSelectMap.set(cuotaConceptoTemp.idCuotaConcepto, cuotaConceptoTemp);
			}
		} else {
			if ((this.listaCuotaConceptoSelectMap.has(cuotaConceptoTemp.idCuotaConcepto))) {
				this.listaCuotaConceptoSelectMap.delete(cuotaConceptoTemp.idCuotaConcepto);
			}
		}
	}
	/*
	 verificar check
	*/
	private verificarCheck() {
		this.listaCuotaConcepto.forEach((data) => {
			if ((this.listaCuotaConceptoSelectMap.has(data.idCuotaConcepto))) {
				data.checked = true;
			}
		});
	}

	/**
	Abrir modal Anhio input
	*/
	public abrirModalAnhioInputanhio(pSearch?: string) {
		this.frmGroup.get('anhio.idAnhio').setValue(null);
		this.abrirModalAnhioanhio(pSearch);
	}

	/**
	  El abrir modal anhio. 
	*/
	public abrirModalAnhioanhio(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Anhio = new Anhio();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalAnhio = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.idAnhio + ' ' + entryVal.nombre;
					this.frmGroup.get('anhio.idAnhio').setValue(entryVal.idAnhio);
					this.frmGroup.get('anhio').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}

	/**
	Abrir modal CatalogoCuenta input
	*/
	public abrirModalCatalogoCuentaInputcatalogoCuenta(pSearch?: string) {
		this.frmGroup.get('catalogoCuenta.idCatalogoCuenta').setValue(null);
		this.abrirModalCatalogoCuentacatalogoCuenta(pSearch);
	}

	/**
	  El abrir modal catalogo cuenta. 
	*/
	public abrirModalCatalogoCuentacatalogoCuenta(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContentOverride);
		let data: CatalogoCuenta = new CatalogoCuenta();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalCatalogoCuenta = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					//entryVal.descripcionView = entryVal.nroCuenta + ' ' + entryVal.cuenta;
					entryVal.descripcionView = entryVal.cuenta;
					//  this.frmGroup.get('monto').setValue(entryVal.monto);	
					this.frmGroup.get('subTotal').setValue(entryVal.monto);
					this.frmGroup.get('catalogoCuenta.idCatalogoCuenta').setValue(entryVal.idCatalogoCuenta);
					this.frmGroup.get('catalogoCuenta').patchValue(entryVal, { onlySelf: true, emitEvent: false });
					this.frmGroup.get('aplicaIgv').setValue(true);
					this.calcularSubMonto();
				});
			}
		});
	}

	/**
 * calcularSubMonto
 */
	public calcularSubMonto() {
		//	this.frmGroup.get('subTotal').setValue(Number.parseFloat(this.frmGroup.get('monto').value/1.18+"").toFixed(2));	
		//	this.frmGroup.get('subTotal').setValue(this.frmGroup.get('monto').value);	
		if (this.frmGroup.get('aplicaIgv').value == true) {
			this.frmGroup.get('igv').setValue(Number.parseFloat((this.frmGroup.get('subTotal').value * 0.18) + "").toFixed(2));
			this.calcularMotoTotalConIgv()
		} else {
			this.frmGroup.get('monto').setValue(this.frmGroup.get('subTotal').value);
		}
	}

	/**
	 * calcularMotoTotalConIgv
	*/
	public calcularMotoTotalConIgv() {
		this.frmGroup.get('igv').setValue(Number.parseFloat((this.frmGroup.get('subTotal').value * 0.18) + "").toFixed(2));
		this.frmGroup.get('monto').setValue(Number.parseFloat(Number(this.frmGroup.get('igv').value) + Number(this.frmGroup.get('subTotal').value) + "").toFixed(2));
	}

}

@Component({
	template: `
	<app-catalogocuenta *ngIf="esModalCatalogoCuenta"  [modulo] = "modulo" [id] = "id" [esIncludeComponent] = "esIncludeComponent"  [showModal]="showModal" [showSelectMultiple] = "showSelectMultiple" [dialogRef] = "dialogRef" [data] = "data"></app-catalogocuenta>
	 `
})
export class DialogContentOverride extends BaseDialogContent {
	public esModalCatalogoCuenta: boolean = false;
	constructor(@Optional() public dialogRef: MatDialogRef<DialogContentOverride>) { super() }
}