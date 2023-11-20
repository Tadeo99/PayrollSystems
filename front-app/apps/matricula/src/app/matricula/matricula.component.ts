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

import { MatriculaService } from "./matricula.service";
import { Matricula } from "../../../models/matricula/matricula.model";
import { Anhio } from "../../../models/matricula/anhio.model";
import { Periodo } from "../../../models/matricula/periodo.model";
import { Alumno } from "../../../models/matricula/alumno.model";
import { CargaAcademica } from "../../../models/matricula/cargaacademica.model";
import { HttpParams } from '@angular/common/http';
import { DetalleCargaAcademica } from '../../../models/matricula/detallecargaacademica.model';
import { MatriculaServiceImpl } from '../matricula.impl.service';
import { StringUtils } from '../../../util/stringutils';
import { ConstantesMensajesMatricula } from '../../../constante/constantemensajesmatricula';
import { EstadoGeneralState } from '../../../type/estadogeneral.state';
import { ModuloContextoType } from '../../../type/moduloContexto.type';
import { SelectItemVO } from '../../../vo/selectitem.vo';
import { ListaItemType } from '../../../type/listaitem.type';
import { Seccion } from '../../../models/admision/seccion.model';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { UserStoreService } from '../../../shared/user-store.service';


/**
 * La Class MatriculaComponent.
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
	selector: 'app-matricula',
	templateUrl: './matricula.component.html',
	styleUrls: ['./matricula.component.css'],
	providers: [MatriculaService, MatriculaServiceImpl, UserStoreService],
	animations: [
		trigger('detailExpand', [
			state('collapsed', style({ height: '0px', minHeight: '0' })),
			state('expanded', style({ height: '*' })),
			transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
		]),
		trigger('indicatorRotate', [
			state('collapsed', style({ transform: 'rotate(0deg)' })),
			state('expanded', style({ transform: 'rotate(180deg)' })),
			transition('expanded <=> collapsed',
				animate('225ms cubic-bezier(0.4,0.0,0.2,1)')
			),
		])
	],
})
export class MatriculaComponent extends BaseComponent implements OnInit, OnChanges, AfterViewInit {

	/** El objeto matricula. */
	public matricula: Matricula = new Matricula();

	/** La lista matricula. */
	public listaMatricula: Matricula[] = [];

	/** La lista item select. */
	public listaMatriculaSelectMap: Map<string, Matricula> = new Map<string, Matricula>();

	/** La lista det carga lectiva. */
	public listaDetalleCargaAcademica: DetalleCargaAcademica[] = [];

	public detCargaAcademica: DetalleCargaAcademica = new DetalleCargaAcademica();

	public isMatriculado: boolean = false;

	public idGrado: number = null;

	public listasize2: number = 0;

	public idTurno: number = null;

	public tipoPeriodo: string = null;

	/*** El objeto anhio. */
	public anhioActivo: Anhio = new Anhio();

	public periodo: Periodo = new Periodo();

	public alumno: Alumno = new Alumno();

	/*** El objeto anhio. */
	public seccion: Seccion = new Seccion();

	/** La lista lista Tipo Doc Identidad. */
	public listaTurno: SelectItemVO[] = [];

	constructor(private fb: FormBuilder, public dialog: MatDialog, public snackbar: MatSnackBar, router: Router, route: ActivatedRoute, private matriculaService: MatriculaService,
		protected commonServiceImpl: CommonServiceImpl, protected loginDataService: LoginService, protected _typeSelectItemService: TypeSelectItemService,
		protected _translate: TranslateService, public userStoreService: UserStoreService, protected matriculaServiceImpl: MatriculaServiceImpl) {
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
	}



	public async cargarCombo() {
		let paramsTemp: Map<any, any> = new Map<any, any>();
		paramsTemp = paramsTemp.set(ListaItemType.ITEM_TURNO, 0);
		await this.commonServiceImpl.obtenerListaItemSelectItemMap(paramsTemp);
		this.listaTurno = this.commonServiceImpl.getListaItemSelectItem(ListaItemType.ITEM_TURNO);
	}


	onInit() {
		this.iconAccionItems.push({ disabled:false,text: 'Descargar', icon: 'archive', click: 'generarReporteFichaMatricula' });
		/*var id = this.route.params.subscribe(params => {
		  var id = params['id'];
		
		});*/
	}

	/**
	 * guardar.
	 *
	 */
	public guardar() { 
		try {
			if (this.listaDetalleCargaAcademica) {
				this.matricula.listaDetalleCargaAcademica = this.listaDetalleCargaAcademica;
				this.matricula.cargaAcademica = this.listaDetalleCargaAcademica[0].cargaAcademica;
			}
			this.matricula.alumno = this.alumno;
			this.matricula.periodo = this.periodo;
			this.matricula.anhio = this.anhioActivo;
			this.matriculaService.crear(this.matricula).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.guardoExito();
						this.generarReporteFichaMatricula(data.objetoResultado);
						this.listaDetalleCargaAcademica = [];
						this.alumno = new Alumno();
						this.isMatriculado = true;
						this.buscar();
					}
				},
				error => {
					this.mostrarMensajeError(error +"aaa");
				}
			);
		} catch (e) {
			this.mostrarMensajeError(e +"----");
		}
	}

	/**
	 * Nuevo.
	 *
	 */
	public nuevo() {
		this.matricula = new Matricula();
		this.alumno = new Alumno();
		this.seccion = new Seccion();
		this.periodo =  new Periodo();
		this.matricula.anhio = this.anhioActivo; 
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
		this.limpiar();
		this.cargarCombo();
	}



	/**
   * accion menu.
   *
   */
	public accionMenu(item : any) {
		if (item.click == 'confirmarEliminar') {
			this.confirmarEliminar(this.matricula);
		} else if (item.click == 'generarReporteFichaMatricula') {
			this.generarReporteFichaMatricula();
		}
	}


	public generarReporteFichaMatricula(matricula?: Matricula) {
		try {
			this.startProgres();
			if(matricula!=null){
				this.periodo = matricula.periodo;
				this.alumno = matricula.alumno;
			}
			  
		
			this.matriculaServiceImpl.generarReporteFichaMatricula(this.anhioActivo.idAnhio, this.alumno.idAlumno, this.periodo.idPeriodo).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.descargarReporte(ModuloContextoType.MATRICULA, data.objetoResultado);
					}
				},
				error => {
					this.mostrarMensajeError(error);
				},
				() => {
					this.endProgres();
				}
			);
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/**
	 * Limpiar.
	 *
	 */
	private limpiar() {
		try {
			this.listaMatricula = [];
			this.limpiaDataProvider(this.search);
			this.matricula = new Matricula();
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
			this.matriculaService.eliminar(id).subscribe(
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
	public confirmarEliminar(matriculaTemp: Matricula) {
		this.matricula = matriculaTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				this.eliminar(this.matricula.idMatricula);
			}
		});
	}

	/**
	 * buscar id
	 *
	 */
	public buscarID(matriculaTemp: Matricula) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.matricula = Object.assign({}, matriculaTemp);
				this.matricula.anhio.descripcionView = this.matricula.anhio.idAnhio + ' ' + this.matricula.anhio.nombre;
				this.matricula.periodo.descripcionView = this.matricula.periodo.idPeriodo + ' ' + this.matricula.periodo.descripcion;
				this.matricula.alumno.descripcionView = this.obtenerNombreAlumno(this.matricula.alumno);
				this.matricula.cargaAcademica.descripcionView = this.matricula.cargaAcademica.nombre;
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.matricula = Object.assign({}, matriculaTemp);
					this.lanzarMatricula();
				}
			}
			this.frmGroup.patchValue(this.matricula, { onlySelf: true, emitEvent: false });

		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(matriculaTemp: Matricula) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				matriculaTemp.checked = true;
				this.agregarCheck(matriculaTemp);
				this.dialogRef.close(this.listaMatriculaSelectMap);
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
			this.listaMatricula = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.params = this.params.set('id', this.idTurno + "");
			this.params = this.params.set('idAnhio', this.anhioActivo.idAnhio + "");
			this.params = this.params.set('idEntidadSelect',this.idEntidad);
			this.matriculaService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.setDataProvider(data);
						this.listaMatricula = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaMatricula.length == 1) {
			this.asociar(this.listaMatricula[0]);
		}
	}

	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			this.calcularStartRow();
			this.matriculaService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaMatricula = data.listaResultado;
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
		this.listaDetalleCargaAcademica = [];
		this.mostrarPanelForm = false;
	}

	private lanzarMatricula() {
		// Usamos el mÃ©todo emit
		this.change.emit({ matricula: this.matricula });
	}

	/*
	agregar check
	*/
	public agregarCheck(matriculaTemp: Matricula) {
		if (matriculaTemp.checked) {
			if (!this.listaMatriculaSelectMap.has(matriculaTemp.idMatricula)) {
				this.listaMatriculaSelectMap.set(matriculaTemp.idMatricula, matriculaTemp);
			}
		} else {
			if ((this.listaMatriculaSelectMap.has(matriculaTemp.idMatricula))) {
				this.listaMatriculaSelectMap.delete(matriculaTemp.idMatricula);
			}
		}
	}
	/*
	 verificar check
	*/
	private verificarCheck() {
		this.listaMatricula.forEach((data) => {
			if ((this.listaMatriculaSelectMap.has(data.idMatricula))) {
				data.checked = true;
			}
		});
	}

	openOpcion(opcion: any) {
		if (opcion.esPadre) {
			opcion.checked = !opcion.checked;
			this.userStoreService.destroyTokenKey(opcion.detMallaCurricular.idDetMallaCurricular);
			this.userStoreService.saveTokenKey(opcion.detMallaCurricular.idDetMallaCurricular, opcion.checked);
		}
	}


	public obtenerCursosPosiblesLlevar(): void {
		let paramsTemp = new HttpParams();
		paramsTemp = paramsTemp.set("idAnhio", this.anhioActivo.idAnhio + "");
		paramsTemp = paramsTemp.set("idGrado", this.idGrado + "");
		paramsTemp = paramsTemp.set("idTurno", this.idTurno + "");
		paramsTemp = paramsTemp.set("idEntidad", this.idEntidad + "");
		paramsTemp = paramsTemp.set("idSeccion", this.seccion.idSeccion + "");
		this.listaDetalleCargaAcademica = [];
		this.matriculaServiceImpl.obtenerCursosPosiblesLlevar(paramsTemp).subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					this.listaDetalleCargaAcademica = data.listaResultado;
					if (this.listaDetalleCargaAcademica) {
						this.listasize2 = this.listaDetalleCargaAcademica.length; 1
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

	public cargarDatosAsociadosAlumno(): void {
		this.isMatriculado = false;
		this.startProgres();
		this.matriculaService.obtenerMatricula(this.anhioActivo.idAnhio, this.alumno.idAlumno, this.idTurno).subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					this.matricula = data.objetoResultado;
					if (!this.matricula) {
						this.matricula = new Matricula();
					}
					console.log("matricula::  ",this.matricula)
					if (StringUtils.isNotNullOrBlank(this.matricula.idMatricula)) {
						this.isMatriculado = true;
						let mensajeModalAlert: string = "";
						if (this.esAlumno) {
							mensajeModalAlert = this.cargarMensaje(ConstantesMensajesMatricula.MATRICULA_ALUMNO_MENSAJE_EXISTE_MATRICULA_CONFIRMACION);

						} else {
							let nombreCompleto: string = this.obtenerNombreAlumno(this.frmGroup.get('alumno').value);
							mensajeModalAlert = this.cargarMensaje(ConstantesMensajesMatricula.MATRICULA_MENSAJE_EXISTE_MATRICULA_CONFIRMACION, nombreCompleto);
						}
						this.mostrarMensajeAdvertencia(mensajeModalAlert);
					}
				}
			},
			error => {
				this.mostrarMensajeError(error);
			},
			() => {
				//this.obtenerCursosPosiblesLlevar();
				this.endProgres();
			}
		);
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
					this.anhioActivo = entryVal;
				});
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
					entryVal.descripcionView = entryVal.tipo + ' ' + entryVal.descripcion;
					this.periodo = entryVal;
				});
			}
		});
	}

	public evenTurno() {
		if (!this.mostrarPanelForm) {
			this.buscar();
		}
		if(this.accionNuevo){
			if(this.seccion.idSeccion!=null ){
				this.obtenerCursosPosiblesLlevar();
			}
			
		}

	}
	/**
	Abrir modal Alumno input
	*/
	public abrirModalAlumnoInputalumno(pSearch?: string) {
		this.frmGroup.get('alumno.idAlumno').setValue(null);
		this.abrirModalAlumnoalumno(pSearch);
	}

	/**
	  El abrir modal alumno. 
	*/
	public abrirModalAlumnoalumno(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Alumno = new Alumno();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalAlumnoSelect = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = this.obtenerNombreAlumno(entryVal);
					this.alumno = entryVal;
					this.idGrado = entryVal.grado.idGrado;
					this.seccion = new Seccion();
					this.periodo = new Periodo();
					this.cargarDatosAsociadosAlumno();
				});
			}
		});
	}

	/**
	  El abrir modal seccion. 
	*/
	public abrirModalSeccionseccion(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Seccion = new Seccion();
		data.id = this.idGrado;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalSeccion = true;
		//dialogRef.componentInstance.modulo = 'matricula';
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					this.seccion = entryVal;
					this.obtenerCursosPosiblesLlevar();
				});
			}
		});
	}
}