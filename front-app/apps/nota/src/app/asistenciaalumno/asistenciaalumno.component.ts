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

import { AsistenciaAlumnoService } from "./asistenciaalumno.service";
import { AsistenciaAlumno } from "../../../models/nota/asistenciaalumno.model";
import { Alumno } from "../../../models/matricula/alumno.model";
import { DetalleCargaAcademica } from "../../../models/matricula/detallecargaacademica.model";
import { Item } from "../../../models/common/item.model";
import { Anhio } from '../../../models/matricula/anhio.model';
import { Personal } from '../../../models/rrhh_escalafon/personal.model';
import { DetalleCargaAcademicaService } from '../../matricula/detallecargaacademica/detallecargaacademica.service';
import { EstadoAsistenciaType } from '../../../type/estadoasistencia.type';
import { BaseDialogContent } from '../../../base/base.dialog.content.component';
import { MatriculaServiceImpl } from '../../matricula/matricula.impl.service';
import { EstadoGeneralState } from '../../../type/estadogeneral.state';


/**
 * La Class AsistenciaAlumnoComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */

@Component({
	selector: 'app-asistenciaalumno',
	templateUrl: './asistenciaalumno.component.html',
	styleUrls: ['./asistenciaalumno.component.css'],
	providers: [AsistenciaAlumnoService, DetalleCargaAcademicaService,MatriculaServiceImpl]
})
export class AsistenciaAlumnoComponent extends BaseComponent implements OnInit, OnChanges, AfterViewInit {

	/** El objeto asistencia alumno. */
	public asistenciaAlumno: AsistenciaAlumno = new AsistenciaAlumno();

	/** La lista asistencia alumno. */
	public listaAsistenciaAlumno: AsistenciaAlumno[] = [];

	/** La lista item select. */
	public listaAsistenciaAlumnoSelectMap: Map<string, AsistenciaAlumno> = new Map<string, AsistenciaAlumno>();

	public listaDetalleCargaAcademica: DetalleCargaAcademica[] = [];

	/** detcargaacademica */
	public detCargaAcademica: DetalleCargaAcademica = new DetalleCargaAcademica();

	/*** El objeto anhio. */
	public anhioActivo: Anhio = new Anhio();

	constructor(private fb: FormBuilder, public dialog: MatDialog, public snackbar: MatSnackBar, router: Router, route: ActivatedRoute, private asistenciaAlumnoService: AsistenciaAlumnoService,
		protected commonServiceImpl: CommonServiceImpl, public detalleCargaAcademicaService: DetalleCargaAcademicaService, 
		protected loginDataService: LoginService, protected _typeSelectItemService: TypeSelectItemService, protected _translate: TranslateService,
		protected matriculaServiceImpl: MatriculaServiceImpl) {
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
		this.crearFormulario(this.asistenciaAlumno);
	}

	private crearFormulario(obj: AsistenciaAlumno): void {
		this.frmGroup = this.fb.group({
			anhio: this.fb.group({
				idAnhio: ['',],
				descripcionView: ['', { updateOn: 'blur' }]
			}),
			personalByDocente: this.fb.group({
				idPersonal: [''],
				descripcionView: ['', { updateOn: 'blur' }]
			}),
			alumno: this.fb.group({
				idAlumno: [obj.alumno.idAlumno],
				descripcionView: [obj.alumno.descripcionView, { updateOn: 'blur' }]
			}),
			itemByDia: this.fb.group({
				idItem: [obj.itemByDia.idItem],
				descripcionView: [obj.itemByDia.descripcionView, { updateOn: 'blur' }]
			}),
			detalleCargaAcademica: this.fb.group({
				idDetalleCargaAcademica: [obj.detalleCargaAcademica.idDetalleCargaAcademica],
				descripcionView: ['', { updateOn: 'blur' }]
			}),
			idAsistenciaAlumno: [obj.idAsistenciaAlumno],
			estado: [obj.estado],
			justificacion: [obj.justificacion],
			fechaHorario: [obj.fechaHorario, { validators: [Validators.required] }],
			fechaCreacion: [obj.fechaCreacion],
			usuarioCreacion: [obj.usuarioCreacion],
			fechaModificacion: [obj.fechaModificacion],
			usuarioModificacion: [obj.usuarioModificacion],

		});
		this.onChange();
	}

	private onChange(): void {
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});

		this.frmGroup.get('alumno.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalAlumnoInputalumno(value);
		});
		this.frmGroup.get('itemByDia.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalItemInputitemByDia(value);
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
			this.asistenciaAlumnoService.crear(this.listaAsistenciaAlumno).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.guardoExito();
						this.obtenerAsistencia();
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
	 * Nuevo.
	 *
	 */
	public nuevo() {
		this.asistenciaAlumno = new AsistenciaAlumno();
		this.frmGroup.patchValue(this.asistenciaAlumno, { onlySelf: true, emitEvent: false });
		this.frmGroup.get('anhio').patchValue(this.anhioActivo, { onlySelf: true, emitEvent: false }); 
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
		this.limpiar();
	}

	/**
	 * Limpiar.
	 *
	 */
	private limpiar() {
		try {
			this.listaAsistenciaAlumno = [];
			this.limpiaDataProvider(this.search);
			this.asistenciaAlumno = new AsistenciaAlumno();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.asistenciaAlumno, { onlySelf: true, emitEvent: false });
				this.frmGroup.get('anhio').patchValue(this.anhioActivo, { onlySelf: true, emitEvent: false }); 
			}
			//	this.buscar();
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
			this.asistenciaAlumnoService.eliminar(id).subscribe(
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
	public confirmarEliminar(asistenciaAlumnoTemp: AsistenciaAlumno) {
		this.asistenciaAlumno = asistenciaAlumnoTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				this.eliminar(this.asistenciaAlumno.idAsistenciaAlumno);
			}
		});
	}

	/**
	 * buscar id
	 *
	 */
	public buscarID(detalleCargaAcademicaTemp: DetalleCargaAcademica) {
		try {
			this.mostrarPanelForm = true;
			this.asistenciaAlumno.detalleCargaAcademica = detalleCargaAcademicaTemp;
			this.asistenciaAlumno.detalleCargaAcademica.idDetalleCargaAcademica = detalleCargaAcademicaTemp.idDetalleCargaAcademica;
			this.frmGroup.get('fechaHorario').setValue(new Date());
			this.obtenerAsistencia();
			// if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
			// 	this.asistenciaAlumno = Object.assign({}, asistenciaAlumnoTemp);
			// 	this.mostrarPanelForm = true;
			// 	this.accionNuevo = false;
			// } else {
			// 	if (this.esIncludeComponent && !this.showSelectMultiple) {
			// 		this.asistenciaAlumno = Object.assign({}, asistenciaAlumnoTemp);
			// 		this.lanzarAsistenciaAlumno();
			// 	}
			// }
			// this.frmGroup.patchValue(this.asistenciaAlumno,{onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(asistenciaAlumnoTemp: AsistenciaAlumno) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				asistenciaAlumnoTemp.checked = true;
				this.agregarCheck(asistenciaAlumnoTemp);
				this.dialogRef.close(this.listaAsistenciaAlumnoSelectMap);
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
			this.listaAsistenciaAlumno = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.asistenciaAlumnoService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.setDataProvider(data);
						this.listaAsistenciaAlumno = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaAsistenciaAlumno.length == 1) {
			this.asociar(this.listaAsistenciaAlumno[0]);
		}
	}

	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			this.calcularStartRow();
			this.asistenciaAlumnoService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaAsistenciaAlumno = data.listaResultado;
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

	private lanzarAsistenciaAlumno() {
		// Usamos el mÃ©todo emit
		this.change.emit({ asistenciaAlumno: this.asistenciaAlumno });
	}

	/*
	agregar check
	*/
	public agregarCheck(asistenciaAlumnoTemp: AsistenciaAlumno) {
		if (asistenciaAlumnoTemp.checked) {
			if (!this.listaAsistenciaAlumnoSelectMap.has(asistenciaAlumnoTemp.idAsistenciaAlumno)) {
				this.listaAsistenciaAlumnoSelectMap.set(asistenciaAlumnoTemp.idAsistenciaAlumno, asistenciaAlumnoTemp);
			}
		} else {
			if ((this.listaAsistenciaAlumnoSelectMap.has(asistenciaAlumnoTemp.idAsistenciaAlumno))) {
				this.listaAsistenciaAlumnoSelectMap.delete(asistenciaAlumnoTemp.idAsistenciaAlumno);
			}
		}
	}
	/*
	 verificar check
	*/
	private verificarCheck() {
		this.listaAsistenciaAlumno.forEach((data) => {
			if ((this.listaAsistenciaAlumnoSelectMap.has(data.idAsistenciaAlumno))) {
				data.checked = true;
			}
		});
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
					entryVal.descripcionView = entryVal.idAlumno;//   + ' ' +  entryVal.nombre;		
					this.asistenciaAlumno.alumno = entryVal;
					this.obtenerAsistencia()
					this.frmGroup.get('alumno').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}

	/**
	Abrir modal Item input
	*/
	public abrirModalItemInputitemByDia(pSearch?: string) {
		this.frmGroup.get('itemByDia.idItem').setValue(null);
		this.abrirModalItemitemByDia(pSearch);
	}

	/**
	  El abrir modal item. 
	*/
	public abrirModalItemitemByDia(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Item = new Item();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalItem = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.idItem;//   + ' ' +  entryVal.nombre;			
					this.frmGroup.get('itemByDia').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
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
					this.detCargaAcademica.cargaAcademica.anhio = entryVal;
					this.frmGroup.get('anhio.idAnhio').setValue(entryVal.idAnhio);
					this.frmGroup.get('anhio').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}

	/**
		  El abrir modal anho semestre. 
		*/
	public abrirModalPersonal(pSearch?: string) {
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
						this.listarDetalleCargaAcademicaByDocente();
						this.frmGroup.get('personalByDocente.idPersonal').setValue(entryVal.idPersonal);
						this.frmGroup.get('personalByDocente').patchValue(entryVal, { onlySelf: true, emitEvent: false });

					});
				}
			}
		});

	}

	public obtenerAsistencia(): void {
		this.asistenciaAlumno.fechaHorario = this.frmGroup.value.fechaHorario;
		this.asistenciaAlumnoService.obtenerAsistencia(this.asistenciaAlumno).subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					this.listaAsistenciaAlumno = data.listaResultado;
					this.listaAsistenciaAlumno.forEach((obj) => {
						/*if (obj.estado == (EstadoAsistenciaType.ASISTIO + '')) {
							this.mostrarBDescargar = true;
						} else {
							this.mostrarBDescargar = false;
						}*/
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

	/**
		 * Buscar.
		 *
		 */
	public listarDetalleCargaAcademicaByDocente() {
		try {
			this.listaDetalleCargaAcademica = [];
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

	public selecionarAsistencia(asistenciaAlumno: AsistenciaAlumno): void {
		if (asistenciaAlumno.estado == (EstadoAsistenciaType.DESIDIR + '')) {
			asistenciaAlumno.estado = (EstadoAsistenciaType.ASISTIO + '');
			this.change.emit({ isBuscar: true });
		} else if (asistenciaAlumno.estado == (EstadoAsistenciaType.ASISTIO + '')) {
			asistenciaAlumno.estado = (EstadoAsistenciaType.FALTO + '');
			this.change.emit({ isBuscar: true });
		} else if (asistenciaAlumno.estado == (EstadoAsistenciaType.FALTO + '')) {
			asistenciaAlumno.estado = (EstadoAsistenciaType.TARDANZA + '');
			this.change.emit({ isBuscar: true });
		} else if (asistenciaAlumno.estado == (EstadoAsistenciaType.TARDANZA + '')) {
			asistenciaAlumno.estado = (EstadoAsistenciaType.DESIDIR + '');
			this.change.emit({ isBuscar: false });
		}
	}



	public justificarAsistencia(asistenciaAlumno: AsistenciaAlumno): void {
		let dialogRef = this.dialog.open(DialogContentOverride);
		let data = asistenciaAlumno;
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, '');
		dialogRef.componentInstance.esModalJustificarAsistencia = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result != null) {
				asistenciaAlumno.justificacion = result + '';
			}
		});
	}

}

@Component({
	template: `
	<app-justificarasistencia *ngIf="esModalJustificarAsistencia"  [modulo] = "modulo" [id] = "id" [esIncludeComponent] = "esIncludeComponent"  [showModal]="showModal" [showSelectMultiple] = "showSelectMultiple" [dialogRef] = "dialogRef" [data] = "data"></app-justificarasistencia>
	
	`
})
export class DialogContentOverride extends BaseDialogContent {
	public esModalJustificarAsistencia: boolean = false;
	constructor(@Optional() public dialogRef: MatDialogRef<DialogContentOverride>) { super() }
}