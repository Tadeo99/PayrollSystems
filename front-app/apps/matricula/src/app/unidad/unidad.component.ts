import { Component, EventEmitter, OnInit, OnChanges, SimpleChanges, AfterViewInit } from '@angular/core';
import { FormControl, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

import { CommonServiceImpl } from "../../common/common.impl.service";
import { LoginService } from "../../seguridad/login/login.service";
import { TypeSelectItemService } from "../../../typeselectitemservice/typeselectitem.service";

import { BaseComponent, DialogConfirmContent, DialogContent } from "../../../base/base.component";
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { UnidadService } from "./unidad.service";
import { Unidad } from "../../../models/matricula/unidad.model";
import { Periodo } from "../../../models/matricula/periodo.model";


/**
 * La Class UnidadComponent.
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
	selector: 'app-unidad',
	templateUrl: './unidad.component.html',
	styleUrls: ['./unidad.component.css'],
	providers: [UnidadService]
})
export class UnidadComponent extends BaseComponent implements OnInit, OnChanges, AfterViewInit {

	/** El objeto unidad. */
	public unidad: Unidad = new Unidad();

	/** La lista unidad. */
	public listaUnidad: Unidad[] = [];

	/** La lista item select. */
	public listaUnidadSelectMap: Map<number, Unidad> = new Map<number, Unidad>();

	public unidadValidar: Unidad = new Unidad();

	constructor(private fb: FormBuilder, public dialog: MatDialog, public snackbar: MatSnackBar, router: Router, route: ActivatedRoute, private unidadService: UnidadService,
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
		if (this.data) {
			this.params = this.params.set('id', this.data.id + '');
			this.buscar();
		}
		this.showAccion();
	}

	ngOnInit() {
		this.onInit();
		this.inicializar();
		this.crearFormulario(this.unidad);
	}

	private crearFormulario(obj: Unidad): void {
		this.frmGroup = this.fb.group({
			periodo: this.fb.group({
				idPeriodo: [obj.periodo.idPeriodo],
				descripcionView: [obj.periodo.descripcionView, { updateOn: 'blur' }]
			}),
			idUnidad: [obj.idUnidad],
			descripcion: [obj.descripcion],
			abreviatura: [obj.abreviatura],
			estado: [obj.estado],
		});
		this.onChange();
	}

	private onChange(): void {
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});

		this.frmGroup.get('periodo.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalPeriodoInputperiodo(value);
		});

		this.frmGroup.get('descripcion').valueChanges.subscribe(value => {
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
				this.unidadService.crear(this.frmGroup.value).subscribe(
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
				this.unidadService.modificar(this.frmGroup.value,this.frmGroup.value.idUnidad).subscribe(
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
		this.unidad = new Unidad();
		this.unidad.estado = "A";
		this.frmGroup.patchValue(this.unidad, { onlySelf: true, emitEvent: false });
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
			this.listaUnidad = [];
			this.limpiaDataProvider(this.search);
			this.unidad = new Unidad();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.unidad, { onlySelf: true, emitEvent: false });
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
			this.unidadService.eliminar(id).subscribe(
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
	public confirmarEliminar(unidadTemp: Unidad) {
		this.unidad = unidadTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				this.eliminar(this.unidad.idUnidad);
			}
		});
	}

	/**
	 * buscar id
	 *
	 */
	public buscarID(unidadTemp: Unidad) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.unidad = Object.assign({}, unidadTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
				this.validarCampo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.unidad = Object.assign({}, unidadTemp);
					this.lanzarUnidad();
				}
			}
			this.frmGroup.patchValue(this.unidad, { onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(unidadTemp: Unidad) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				unidadTemp.checked = true;
				this.agregarCheck(unidadTemp);
				this.dialogRef.close(this.listaUnidadSelectMap);
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
			this.listaUnidad = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.unidadService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.setDataProvider(data);
						this.listaUnidad = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaUnidad.length == 1) {
			this.asociar(this.listaUnidad[0]);
		}
	}

	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			this.calcularStartRow();
			this.unidadService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaUnidad = data.listaResultado;
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

	private lanzarUnidad() {
		// Usamos el mÃ©todo emit
		this.change.emit({ unidad: this.unidad });
	}

	/*
	agregar check
	*/
	public agregarCheck(unidadTemp: Unidad) {
		if (unidadTemp.checked) {
			if (!this.listaUnidadSelectMap.has(unidadTemp.idUnidad)) {
				this.listaUnidadSelectMap.set(unidadTemp.idUnidad, unidadTemp);
			}
		} else {
			if ((this.listaUnidadSelectMap.has(unidadTemp.idUnidad))) {
				this.listaUnidadSelectMap.delete(unidadTemp.idUnidad);
			}
		}
	}
	/*
	 verificar check
	*/
	private verificarCheck() {
		this.listaUnidad.forEach((data) => {
			if ((this.listaUnidadSelectMap.has(data.idUnidad))) {
				data.checked = true;
			}
		});
	}

	/**
	Abrir modal Periodo input
	*/
	public abrirModalPeriodoInputperiodo(pSearch?: string) {
		this.frmGroup.get('periodo.idPeriodo').setValue(null);
		this.abrirModalPeriodoperiodo(pSearch);
	}

	/**
	  El abrir modal periodo. 
	*/
	public abrirModalPeriodoperiodo(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Periodo = new Periodo();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalPeriodo = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView =entryVal.descripcion;
					this.frmGroup.get('periodo.idPeriodo').setValue(entryVal.idPeriodo);
					this.frmGroup.get('periodo').patchValue(entryVal, { onlySelf: true, emitEvent: false });
					this.validarCampo = true;
				});
			}
		});
	}

	public findUnidad() {		
		this.unidadService.findUnidad(this.frmGroup.value).subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					this.unidadValidar = data.objetoResultado;
					if (this.validarCampo) {
						if (this.unidadValidar.idUnidad != null) {
							this.mostrarMensajeAdvertencia(this.unidadValidar.descripcion +  ' ya Existe');
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