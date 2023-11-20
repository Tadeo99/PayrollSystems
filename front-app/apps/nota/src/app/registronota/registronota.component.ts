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

import { RegistroNotaService } from "./registronota.service";
import { RegistroNota } from "../../../models/nota/registronota.model";
import { Matricula } from "../../../models/matricula/matricula.model";
import { Seccion } from '../../../models/admision/seccion.model';
import { Periodo } from '../../../models/matricula/periodo.model';
import { Anhio } from '../../../models/matricula/anhio.model';
import { Personal } from '../../../models/rrhh_escalafon/personal.model';
import { DetalleCargaAcademica } from '../../../models/matricula/detallecargaacademica.model';
import { DetalleCargaAcademicaService } from '../../matricula/detallecargaacademica';
import { DetRegistroNota } from '../../../models/nota/detregistronota.model';
import { CriterioEvaluacion } from '../../../models/matricula/criterioevaluacion.model';
import { EstadoGeneralState } from '../../../type/estadogeneral.state';

import { RegistroNotaVO } from '../../../vo/registronota.vo';
import { CursoNotaPeriodo } from '../../../models/nota/cursonotaperiodo.model';
import { MatriculaServiceImpl } from '../../matricula/matricula.impl.service';
import { UserStoreService } from '../../../shared/user-store.service';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { Unidad } from '../../../models/matricula/unidad.model';
import { BaseDialogContent } from '../../../base/base.dialog.content.component';
import { CursoNotaUnidad } from '../../../models/nota/cursonotaunidad.model';
import { CursoNotaUnidadProm } from '../../../models/nota/cursonotaunidadprom.model';

/**
 * La Class RegistroNotaComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */

@Component({
	selector: 'app-registronota',
	templateUrl: './registronota.component.html',
	styleUrls: ['./registronota.component.css'],
	providers: [RegistroNotaService, DetalleCargaAcademicaService, MatriculaServiceImpl, UserStoreService],
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
	]
})
export class RegistroNotaComponent extends BaseComponent implements OnInit, OnChanges, AfterViewInit {

	/** El objeto registro nota. */
	public registroNota: RegistroNota = new RegistroNota();

	/** La lista registro nota. */
	public listaRegistroNota: RegistroNota[] = [];

	/** La lista item select. */
	public listaRegistroNotaSelectMap: Map<string, RegistroNota> = new Map<string, RegistroNota>();

	public seccion: Seccion = new Seccion();

	public periodo: Periodo = new Periodo();

	public unidad: Unidad = new Unidad();

	public detCargaAcademica: DetalleCargaAcademica = new DetalleCargaAcademica();

	public listaDetalleCargaAcademica: DetalleCargaAcademica[] = [];

	public mostarListado: boolean = false;

	/** La lista det registro nota. */
	public listaDetRegistroNota: DetRegistroNota[] = [];

	/** La lista det registro nota. */
	public listaRegistroNotaVO: RegistroNotaVO[] = [];

	/** La lista concepto nota disponible. */
	public listaCriterioEvaluacionDisponible: CriterioEvaluacion[] = [];


	public criterioEvaluacionMap: Map<string, CriterioEvaluacion> = new Map<string, CriterioEvaluacion>();

	/** El curso nota map. */
	public cursoNotaUnidadMap: Map<string, CursoNotaUnidad> = new Map<string, CursoNotaUnidad>();
	public cursoNotaUnidadPromMap: Map<string, CursoNotaUnidadProm> = new Map<string, CursoNotaUnidadProm>();

	/*** El objeto anhio. */
	public anhioActivo: Anhio = new Anhio();

	constructor(private fb: FormBuilder, public dialog: MatDialog, public snackbar: MatSnackBar, router: Router, route: ActivatedRoute, private registroNotaService: RegistroNotaService,
		protected commonServiceImpl: CommonServiceImpl, public detalleCargaAcademicaService: DetalleCargaAcademicaService,
		protected loginDataService: LoginService, protected _typeSelectItemService: TypeSelectItemService,
		protected _translate: TranslateService, public userStoreService: UserStoreService, protected matriculaServiceImpl: MatriculaServiceImpl) {
		super(dialog, snackbar, router, route);
		super.setTypeSelectItemService(_typeSelectItemService);
		super.setTraslate(_translate);
		this.debounceTimeProcesar().subscribe(term => { this.search = term; this.listarDetalleCargaAcademicaByDocente() });
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
		//this.crearFormulario(this.registroNota);
	}

	// private crearFormulario(obj: RegistroNota): void {
	// 	this.frmGroup = this.fb.group({
	// 		matricula: this.fb.group({
	// 			idMatricula: [obj.matricula, [Validators.required]]
	// 		}),
	// 		idRegistroNota: [obj.idRegistroNota, [Validators.required]],
	// 		notaFinal: [obj.notaFinal, [Validators.required]],
	// 		usuarioCreacion: [obj.usuarioCreacion],
	// 		fechaCreacion: [obj.fechaCreacion],
	// 		usuarioModificacion: [obj.usuarioModificacion],
	// 		fechaModificacion: [obj.fechaModificacion],
	// 	});
	// 	this.onChange();
	// }

	// private onChange(): void { 
	// 	this.frmGroup.get('matricula.descripcionView').valueChanges.subscribe(value => {
	// 		this.abrirModalMatriculaInputmatricula(value);
	// 	});
	// }


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
				this.registroNotaService.crear(this.listaDetRegistroNota).subscribe(
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
				this.registroNotaService.modificar(this.frmGroup.value,this.frmGroup.value.idRegistroNota).subscribe(
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
		this.registroNota = new RegistroNota();
		this.frmGroup.patchValue(this.registroNota, { onlySelf: true, emitEvent: false });
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
		this.detCargaAcademica.cargaAcademica.anhio =  this.anhioActivo ;
		//this.limpiar();
	}

	/**
	 * Limpiar.
	 *
	 */
	private limpiar() {
		try {
			this.listaRegistroNota = [];
			this.limpiaDataProvider(this.search);
			this.registroNota = new RegistroNota();
			// if (this.frmGroup != null) {
			// 	this.frmGroup.patchValue(this.registroNota, { onlySelf: true, emitEvent: false }); 
			// }
			//this.buscar();
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
			this.registroNotaService.eliminar(id).subscribe(
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
	public confirmarEliminar(registroNotaTemp: RegistroNota) {
		this.registroNota = registroNotaTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				this.eliminar(this.registroNota.idRegistroNota);
			}
		});
	}

	/**
	 * buscar id
	 *
	 */
	public buscarID(registroNotaTemp: RegistroNota) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.registroNota = Object.assign({}, registroNotaTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.registroNota = Object.assign({}, registroNotaTemp);
					this.lanzarRegistroNota();
				}
			}
			this.frmGroup.patchValue(this.registroNota, { onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(registroNotaTemp: RegistroNota) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				registroNotaTemp.checked = true;
				this.agregarCheck(registroNotaTemp);
				this.dialogRef.close(this.listaRegistroNotaSelectMap);
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
			this.listaRegistroNota = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.registroNotaService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.setDataProvider(data);
						this.listaRegistroNota = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaRegistroNota.length == 1) {
			this.asociar(this.listaRegistroNota[0]);
		}
	}

	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			this.calcularStartRow();
			this.registroNotaService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaRegistroNota = data.listaResultado;
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
		this.mostarListado = false;
	}

	private lanzarRegistroNota() {
		// Usamos el mÃ©todo emit
		this.change.emit({ registroNota: this.registroNota });
	}

	/*
	agregar check
	*/
	public agregarCheck(registroNotaTemp: RegistroNota) {
		if (registroNotaTemp.checked) {
			if (!this.listaRegistroNotaSelectMap.has(registroNotaTemp.idRegistroNota)) {
				this.listaRegistroNotaSelectMap.set(registroNotaTemp.idRegistroNota, registroNotaTemp);
			}
		} else {
			if ((this.listaRegistroNotaSelectMap.has(registroNotaTemp.idRegistroNota))) {
				this.listaRegistroNotaSelectMap.delete(registroNotaTemp.idRegistroNota);
			}
		}
	}
	/*
	 verificar check
	*/
	private verificarCheck() {
		this.listaRegistroNota.forEach((data) => {
			if ((this.listaRegistroNotaSelectMap.has(data.idRegistroNota))) {
				data.checked = true;
			}
		});
	}




	/**
	Abrir modal Matricula input
	*/
	public abrirModalMatriculaInputmatricula(pSearch?: string) {
		this.frmGroup.get('matricula.idMatricula').setValue(null);
		this.abrirModalMatriculamatricula(pSearch);
	}

	/**
	  El abrir modal matricula. 
	*/
	public abrirModalMatriculamatricula(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Matricula = new Matricula();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		// dialogRef.componentInstance.esModalMatricula = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.idMatricula;//   + ' ' +  entryVal.nombre;			
					this.frmGroup.get('matricula').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}

	//
	/**
   Abrir modal Seccion input
   */
	public abrirModalSeccionInputseccion(pSearch?: string) {
		this.frmGroup.get('seccion.idSeccion').setValue(null);
		this.abrirModalSeccionseccion(pSearch);
	}

	/**
	  El abrir modal seccion. 
	*/
	public abrirModalSeccionseccion(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Seccion = new Seccion();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalSeccion = true;
		//dialogRef.componentInstance.modulo = 'matricula';
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					this.seccion = entryVal;
					this.seccion.descripcionView = this.seccion.nombre + " " + this.seccion.grado.nombre;

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
					this.periodo = entryVal;
					this.periodo.descripcionView = entryVal.idPeriodo + ' ' + entryVal.descripcion;

				});
			}
		});
	}

	/**
 Abrir modal Unidad input
 */
	public abrirModalUnidadInputunidad(pSearch?: string) {
		this.frmGroup.get('unidad.idUnidad').setValue(null);
		this.abrirModalUnidadunidad(pSearch);
	}

	/**
	  El abrir modal unidad. 
	*/
	public abrirModalUnidadunidad(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Unidad = new Unidad();
		data.id = this.periodo.idPeriodo;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalUnidad = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					this.unidad = entryVal;//   + ' ' +  entryVal.nombre;	
					this.unidad.descripcionView = entryVal.descripcion;
				});
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
					this.anhioActivo = entryVal;
					this.anhioActivo.descripcionView = entryVal.idAnhio;//   + ' ' +  entryVal.nombre;			 
				});
			}
		});
	}

	public actualizaFrm(event) {
		if (event.invalid == true || event.invalid == false) {
			//this.disabledBotonGuardar = event.invalid;
		}
		let isBUscar = event.isBUscar;
		if (isBUscar) {
			this.mostarListado = false;
			this.listaDetRegistroNota = [];
		}

		let isGuardoExito = event.isGuardoExito;
		if (isGuardoExito) {
			this.obtenerNotaAlumnoMariculadoByCurso(event.detCargaAcademica);
		}

		let isCriterioEvaluacion = event.isCriterioEvaluacion;
		if (isCriterioEvaluacion) {
			this.registrarNota(this.detCargaAcademica);
		}
	}

	public abrirModalPersonal(pSearch?: string) {
		this.detCargaAcademica = new DetalleCargaAcademica();
		let dialogRef = this.dialog.open(DialogContent);
		let data: Personal = new Personal();
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalPersonalSelect = true;
		//dialogRef.componentInstance.modulo = 'matricula';
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				let listaPersonalSelectMap = result;
				if (listaPersonalSelectMap) {
					listaPersonalSelectMap.forEach((entryVal : any , entryKey : any )  => {
						this.detCargaAcademica.personalByDocentePrincipal = entryVal;
						this.search = "";
						// if (this.periodo.idPeriodo) {
						// 	this.detCargaAcademica.cargaAcademica.tipoPeriodo = this.periodo.tipo;
						// }
						this.listarDetalleCargaAcademicaByDocente();

					});
				}
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

	public listarDetalleCargaAcademicaByDocente() {
		try {
			this.listaDetalleCargaAcademica = [];
			//this.detCargaAcademica.search = this.search;
			this.detCargaAcademica.cargaAcademica.idEntidad = this.idEntidad;
			this.detalleCargaAcademicaService.listarCursosByDocente(this.detCargaAcademica).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.setDataProvider(data);
						this.listaDetalleCargaAcademica = data.listaResultado;
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

	public obtenerNotaAlumnoMariculadoByCurso(detCargaAcademicaTemp: DetalleCargaAcademica): void {
		this.registroNotaService.obtenerRegistroNota(detCargaAcademicaTemp.detMallaCurricular.idDetMallaCurricular, "", false).subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					this.listaDetRegistroNota = data.listaResultado;
					if (this.listaDetRegistroNota.length > 0) {
						this.actualizarNotaAlumnos();
					}
					else {
						this.mostrarMensajeAdvertencia("No existe alumnos matriculados en este curso");
					}
					//this.listarFraccionamiento();
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


	/**
 * Actualizar nota alumnos.
 */
	public actualizarNotaAlumnos(): void {
		this.listaDetRegistroNota.forEach((detRegistroNota) => {
			detRegistroNota.detRegistroNotaCursoNotaUnidadList.forEach((cursoNotaResul) => {
				let key: string = cursoNotaResul.criterioEvaluacion.idCriterioEvaluacion + detRegistroNota.idDetRegistroNota;
				this.cursoNotaUnidadMap.set(key, cursoNotaResul);
			});
			detRegistroNota.detRegistroNotaCursoNotaUnidadPromList.forEach((cursoNotaResul) => {
				let key: string = cursoNotaResul.unidad + detRegistroNota.idDetRegistroNota;
				this.cursoNotaUnidadPromMap.set(key, cursoNotaResul);
			});
		});
	}


	public obtenerRegistroNotaVO(detCargaAcademicaTemp: DetalleCargaAcademica): void {
		this.registroNotaService.obtenerRegistroNotaDataSourceSettings(detCargaAcademicaTemp.detMallaCurricular.idDetMallaCurricular, "", false).subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					this.data = data.listaResultado;

					console.log("listadoRoww: ", this.data)
					//this.listarFraccionamiento();
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

	public registrarNota(detCargaAcademicaTemp: DetalleCargaAcademica) {
		this.mostarListado = true;
		this.obtenerNotaAlumnoMariculadoByCurso(detCargaAcademicaTemp);
		this.detCargaAcademica = Object.assign({}, detCargaAcademicaTemp);
		//this.obtenerRegistroNotaVO(detCargaAcademicaTemp);
		this.llenarConceptoNota(detCargaAcademicaTemp);
	}

	/**
	 * Llenar concepto nota.
	 *
	 * @throws Exception the exception
	 */
	public llenarConceptoNota(detCargaAcademicaTemp: DetalleCargaAcademica): void {
		this.listaCriterioEvaluacionDisponible = [];
		this.startProgres();
		this.registroNotaService.listaCriterioEvaluacion(EstadoGeneralState.ACTIVO + "", detCargaAcademicaTemp.detMallaCurricular.idDetMallaCurricular).subscribe(
			dataTemp => {
				if (this.isProcesoOK(dataTemp)) {
					this.listaCriterioEvaluacionDisponible = dataTemp.listaResultado;
					this.criterioEvaluacionMap = new Map<string, CriterioEvaluacion>();
					this.listaCriterioEvaluacionDisponible.forEach((objCriterioEvaluacion) => {
						this.criterioEvaluacionMap.set(objCriterioEvaluacion.idCriterioEvaluacion, objCriterioEvaluacion);
					});
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
