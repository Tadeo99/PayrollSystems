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

import { CatalogoCuentaService } from "./catalogocuenta.service";
import { CatalogoCuenta } from "../../../models/pago/catalogocuenta.model";
import { Clasificacion } from "../../../models/pago/clasificacion.model";
import { BaseDialogContent } from '../../../base/base.dialog.content.component';


/**
 * La Class CatalogoCuentaComponent.
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
	selector: 'app-catalogocuenta',
	templateUrl: './catalogocuenta.component.html',
	styleUrls: ['./catalogocuenta.component.css'],
	providers: [CatalogoCuentaService]
})
export class CatalogoCuentaComponent extends BaseComponent implements OnInit, OnChanges, AfterViewInit {

	/** El objeto catalogo cuenta. */
	public catalogoCuenta: CatalogoCuenta = new CatalogoCuenta();

	public catalogoCuentaValidar: CatalogoCuenta = new CatalogoCuenta();

	/** La lista catalogo cuenta. */
	public listaCatalogoCuenta: CatalogoCuenta[] = [];

	/** La lista item select. */
	public listaCatalogoCuentaSelectMap: Map<number, CatalogoCuenta> = new Map<number, CatalogoCuenta>();


	constructor(private fb: FormBuilder, public dialog: MatDialog, public snackbar: MatSnackBar, router: Router, route: ActivatedRoute, private catalogoCuentaService: CatalogoCuentaService,
		protected commonServiceImpl: CommonServiceImpl, protected loginDataService: LoginService, protected _typeSelectItemService: TypeSelectItemService, protected _translate: TranslateService) {
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
		this.crearFormulario(this.catalogoCuenta);
	}

	private crearFormulario(obj: CatalogoCuenta): void {
		this.frmGroup = this.fb.group({
			clasificacion: this.fb.group({
				idClasificacion: [obj.clasificacion.idClasificacion],
				descripcionView: [obj.clasificacion.descripcionView, { updateOn: 'blur' }]
			}),
			idCatalogoCuenta: [obj.idCatalogoCuenta],
			cuenta: [obj.cuenta],
			nroCuenta: [obj.nroCuenta],
			estado: [obj.estado],
			monto: [obj.monto],
			fechaCreacion: [obj.fechaCreacion],
			usuarioCreacion: [obj.usuarioCreacion],
			fechaModificacion: [obj.fechaModificacion],
			usuarioModificacion: [obj.usuarioModificacion],
		});
		this.onChange();
	}

	private onChange(): void {
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});

		this.frmGroup.get('clasificacion.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalClasificacionInputclasificacion(value);
		});

		this.frmGroup.get('cuenta').valueChanges.subscribe(value => {
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
				this.catalogoCuentaService.crear(this.frmGroup.value).subscribe(
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
				this.catalogoCuentaService.modificar(this.frmGroup.value,this.frmGroup.value.idCatalogoCuenta).subscribe(
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
		this.catalogoCuenta = new CatalogoCuenta();
		this.frmGroup.patchValue(this.catalogoCuenta, { onlySelf: true, emitEvent: false });
		this.frmGroup.get('estado').setValue("A");
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
			this.listaCatalogoCuenta = [];
			this.limpiaDataProvider(this.search);
			this.catalogoCuenta = new CatalogoCuenta();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.catalogoCuenta, { onlySelf: true, emitEvent: false });
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
			this.catalogoCuentaService.eliminar(id).subscribe(
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
	public confirmarEliminar(catalogoCuentaTemp: CatalogoCuenta) {
		this.catalogoCuenta = catalogoCuentaTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				this.eliminar(this.catalogoCuenta.idCatalogoCuenta);
			}
		});
	}

	/**
	 * buscar id
	 *
	 */
	public buscarID(catalogoCuentaTemp: CatalogoCuenta) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.catalogoCuenta = Object.assign({}, catalogoCuentaTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
				this.validarCampo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.catalogoCuenta = Object.assign({}, catalogoCuentaTemp);
					this.lanzarCatalogoCuenta();
				}
			}
			this.frmGroup.patchValue(this.catalogoCuenta, { onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(catalogoCuentaTemp: CatalogoCuenta) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				catalogoCuentaTemp.checked = true;
				this.agregarCheck(catalogoCuentaTemp);
				this.dialogRef.close(this.listaCatalogoCuentaSelectMap);
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
			this.listaCatalogoCuenta = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.params = this.params.set('idEntidadSelect',this.idEntidad);
			this.catalogoCuentaService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.setDataProvider(data);
						this.listaCatalogoCuenta = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaCatalogoCuenta.length == 1) {
			this.asociar(this.listaCatalogoCuenta[0]);
		}
	}

	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			this.calcularStartRow();
			this.catalogoCuentaService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaCatalogoCuenta = data.listaResultado;
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

	private lanzarCatalogoCuenta() {
		// Usamos el mÃ©todo emit
		this.change.emit({ catalogoCuenta: this.catalogoCuenta });
	}

	/*
	agregar check
	*/
	public agregarCheck(catalogoCuentaTemp: CatalogoCuenta) {
		if (catalogoCuentaTemp.checked) {
			if (!this.listaCatalogoCuentaSelectMap.has(catalogoCuentaTemp.idCatalogoCuenta)) {
				this.listaCatalogoCuentaSelectMap.set(catalogoCuentaTemp.idCatalogoCuenta, catalogoCuentaTemp);
			}
		} else {
			if ((this.listaCatalogoCuentaSelectMap.has(catalogoCuentaTemp.idCatalogoCuenta))) {
				this.listaCatalogoCuentaSelectMap.delete(catalogoCuentaTemp.idCatalogoCuenta);
			}
		}
	}
	/*
	 verificar check
	*/
	private verificarCheck() {
		this.listaCatalogoCuenta.forEach((data) => {
			if ((this.listaCatalogoCuentaSelectMap.has(data.idCatalogoCuenta))) {
				data.checked = true;
			}
		});
	}

	/**
	Abrir modal Clasificacion input
	*/
	public abrirModalClasificacionInputclasificacion(pSearch?: string) {
		this.frmGroup.get('clasificacion.idClasificacion').setValue(null);
		this.abrirModalClasificacionclasificacion(pSearch);
	}

	/**
	  El abrir modal clasificacion. 
	*/
	public abrirModalClasificacionclasificacion(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContentOverride);
		let data: Clasificacion = new Clasificacion();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalClasificacion = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.idClasificacion + ' ' + entryVal.descripcion;
					this.frmGroup.get('clasificacion.idClasificacion').setValue(entryVal.idClasificacion);
					this.frmGroup.get('clasificacion').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}

	public findCatalogoCuenta() {
		this.catalogoCuentaService.findCatalogoCuenta(this.frmGroup.value).subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					this.catalogoCuentaValidar = data.objetoResultado;
					if (this.validarCampo) {
						if (this.catalogoCuentaValidar.idCatalogoCuenta != null) {
							this.mostrarMensajeAdvertencia(this.catalogoCuentaValidar.cuenta + ' ya Existe');
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
@Component({
	template: `
	<app-clasificacion *ngIf="esModalClasificacion"  [modulo] = "modulo" [id] = "id" [esIncludeComponent] = "esIncludeComponent"  [showModal]="showModal" [showSelectMultiple] = "showSelectMultiple" [dialogRef] = "dialogRef" [data] = "data"></app-clasificacion>
	 `
})
export class DialogContentOverride extends BaseDialogContent {
	public esModalClasificacion: boolean = false;
	constructor(@Optional() public dialogRef: MatDialogRef<DialogContentOverride>) { super() }
}