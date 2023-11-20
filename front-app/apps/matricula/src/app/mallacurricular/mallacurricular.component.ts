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

import { MallaCurricularService } from "./mallacurricular.service";
import { MallaCurricular } from "../../../models/matricula/mallacurricular.model";
import { Anhio } from "../../../models/matricula/anhio.model";
import { Grado } from "../../../models/admision/grado.model";
import { EstadoGeneralState } from '../../../type/estadogeneral.state';
import { MatriculaServiceImpl } from '../matricula.impl.service';


/**
 * La Class MallaCurricularComponent.
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
	selector: 'app-mallacurricular',
	templateUrl: './mallacurricular.component.html',
	styleUrls: ['./mallacurricular.component.css'],
	providers: [MallaCurricularService, MatriculaServiceImpl]
})
export class MallaCurricularComponent extends BaseComponent implements OnInit, OnChanges, AfterViewInit {

	/** El objeto malla curricular. */
	public mallaCurricular: MallaCurricular = new MallaCurricular();

	/** La lista malla curricular. */
	public listaMallaCurricular: MallaCurricular[] = [];

	/** La lista item select. */
	public listaMallaCurricularSelectMap: Map<string, MallaCurricular> = new Map<string, MallaCurricular>();

	public verDetMallaCurricular: boolean = false;

	/*** El objeto anhio. */
	public anhioActivo: Anhio = new Anhio();

	public filtroAnhio = new FormControl(null);

	public filtroEstado = new FormControl(null);

	constructor(private fb: FormBuilder, public dialog: MatDialog, public snackbar: MatSnackBar, router: Router, route: ActivatedRoute, private mallaCurricularService: MallaCurricularService,
		protected commonServiceImpl: CommonServiceImpl, protected loginDataService: LoginService, protected _typeSelectItemService: TypeSelectItemService,
		protected _translate: TranslateService, protected matriculaServiceImpl: MatriculaServiceImpl) {
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
		this.crearFormulario(this.mallaCurricular);
	}

	private crearFormulario(obj: MallaCurricular): void {
		this.frmGroup = this.fb.group({
			anhio: this.fb.group({
				idAnhio: [obj.anhio.idAnhio],
				descripcionView: [obj.anhio.descripcionView]
			}),
			grado: this.fb.group({
				idGrado: [obj.grado.idGrado],
				descripcionView: [obj.grado.descripcionView]
			}),
			idMallaCurricular: [obj.idMallaCurricular],
			estado: [obj.estado],
			fechaCreacion: [obj.fechaCreacion],
		});
		this.onChange();
	}

	private onChange(): void {
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});

		this.frmGroup.get('anhio.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalAnhioInputanhio(value);
		});
		this.frmGroup.get('grado.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalGradoInputgrado(value);
		});
	}

	/**
	 * verDetalleMalla
	 */
	public verDetalleMallaCurri(mallaCurricularTemp: MallaCurricular) {
		this.verDetMallaCurricular = true;
		this.buscarID(mallaCurricularTemp);
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
				this.mallaCurricularService.crear(this.frmGroup.value).subscribe(
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
				this.mallaCurricularService.modificar(this.frmGroup.value,this.frmGroup.value.idMallaCurricular).subscribe(
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
		this.mallaCurricular = new MallaCurricular();
		this.mallaCurricular.anhio = this.anhioActivo;
		this.mallaCurricular.estado = EstadoGeneralState.ACTIVO + "";
		this.frmGroup.patchValue(this.mallaCurricular, { onlySelf: true, emitEvent: false });
		this.mostrarPanelForm = true;
		this.accionNuevo = true;
		this.verDetMallaCurricular = false;
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
		this.filtroEstado.setValue("A");
		this.limpiar();
	}

	/**
	 * Limpiar.
	 *
	 */
	private limpiar() {
		try {
			this.listaMallaCurricular = [];
			this.limpiaDataProvider(this.search);
			this.mallaCurricular = new MallaCurricular();
			if (this.frmGroup != null) {
				this.mallaCurricular.anhio = this.anhioActivo;
				this.filtroAnhio.setValue(this.anhioActivo.descripcionView);
				this.mallaCurricular.estado = EstadoGeneralState.ACTIVO + "";
				this.frmGroup.patchValue(this.mallaCurricular, { onlySelf: true, emitEvent: false }); 
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
			this.mallaCurricularService.eliminar(id).subscribe(
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
	public confirmarEliminar(mallaCurricularTemp: MallaCurricular) {
		this.mallaCurricular = mallaCurricularTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				this.eliminar(this.mallaCurricular.idMallaCurricular);
			}
		});
	}

	/**
	 * buscar id
	 *
	 */
	public buscarID(mallaCurricularTemp: MallaCurricular) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.mallaCurricular = Object.assign({}, mallaCurricularTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.mallaCurricular = Object.assign({}, mallaCurricularTemp);
					this.lanzarMallaCurricular();
				}
			}
			this.frmGroup.patchValue(this.mallaCurricular, { onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(mallaCurricularTemp: MallaCurricular) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				mallaCurricularTemp.checked = true;
				this.agregarCheck(mallaCurricularTemp);
				this.dialogRef.close(this.listaMallaCurricularSelectMap);
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
			this.listaMallaCurricular = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.params = this.params.set('estado', this.filtroEstado.value);
			this.params = this.params.set('id', this.anhioActivo.idAnhio + "");
			this.mallaCurricularService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.setDataProvider(data);
						this.listaMallaCurricular = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaMallaCurricular.length == 1) {
			this.asociar(this.listaMallaCurricular[0]);
		}
	}

	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			this.calcularStartRow();
			this.mallaCurricularService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaMallaCurricular = data.listaResultado;
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
		this.verDetMallaCurricular = false;
	}

	private lanzarMallaCurricular() {
		// Usamos el mÃ©todo emit
		this.change.emit({ mallaCurricular: this.mallaCurricular });
	}

	/*
	agregar check
	*/
	public agregarCheck(mallaCurricularTemp: MallaCurricular) {
		if (mallaCurricularTemp.checked) {
			if (!this.listaMallaCurricularSelectMap.has(mallaCurricularTemp.idMallaCurricular)) {
				this.listaMallaCurricularSelectMap.set(mallaCurricularTemp.idMallaCurricular, mallaCurricularTemp);
			}
		} else {
			if ((this.listaMallaCurricularSelectMap.has(mallaCurricularTemp.idMallaCurricular))) {
				this.listaMallaCurricularSelectMap.delete(mallaCurricularTemp.idMallaCurricular);
			}
		}
	}
	/*
	 verificar check
	*/
	private verificarCheck() {
		this.listaMallaCurricular.forEach((data) => {
			if ((this.listaMallaCurricularSelectMap.has(data.idMallaCurricular))) {
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
					this.frmGroup.get('anhio.idAnhio').patchValue(entryVal.idAnhio);
					this.frmGroup.get('anhio').patchValue(entryVal, { onlySelf: true, emitEvent: false });
				});
			}
		});
	}

	/**
	  El abrir modal anhio. 
	*/
	public abrirModalAnhioFiltro(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Anhio = new Anhio();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalAnhio = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					this.anhioActivo = entryVal;
					this.filtroAnhio.setValue(entryVal.descripcionView);
					this.buscar();
				});
			}
		});
	}

	public eventEstado() {
		this.buscar();
	}

	/**
	Abrir modal Grado input
	*/
	public abrirModalGradoInputgrado(pSearch?: string) {
		this.frmGroup.get('grado.idGrado').setValue(null);
		this.abrirModalGradogrado(pSearch);
	}

	/**
	  El abrir modal grado. 
	*/
	public abrirModalGradogrado(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Grado = new Grado();

		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalGrado = true;
		//dialogRef.componentInstance.modulo = 'matricula';

		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					this.frmGroup.get('grado').patchValue(entryVal, { onlySelf: true, emitEvent: false });
					this.frmGroup.get('grado.idGrado').patchValue(entryVal.idGrado);
				});
			}
		});
	}
}