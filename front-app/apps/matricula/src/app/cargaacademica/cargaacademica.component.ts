import { Component, EventEmitter, OnInit, OnChanges, SimpleChanges, AfterViewInit, Optional, ViewChild } from '@angular/core';
import { FormControl, FormBuilder, Validators, FormArray } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

import { CommonServiceImpl } from "../../common/common.impl.service";
import { LoginService } from "../../seguridad/login/login.service";
import { TypeSelectItemService } from "../../../typeselectitemservice/typeselectitem.service";

import { BaseComponent, DialogConfirmContent, DialogContent } from "../../../base/base.component";
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { CargaAcademicaService } from "./cargaacademica.service";
import { CargaAcademica } from "../../../models/matricula/cargaacademica.model";
import { Anhio } from "../../../models/matricula/anhio.model";
import { Seccion } from "../../../models/admision/seccion.model";
import { Item } from "../../../models/common/item.model";
import { Aula } from "../../../models/matricula/aula.model";
import { Personal } from "../../../models/rrhh_escalafon/personal.model";
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import { DetalleCargaAcademica } from '../../../models/matricula/detallecargaacademica.model';
import { DetalleCargaAcademicaService } from '../detallecargaacademica';
import { ListaItems } from '../../../models/common/listaitems.model';
import { ListaItemType } from '../../../type/listaitem.type';
import { Grupo } from '../../../models/matricula/grupo.model';
import { BaseDialogContent } from '../../../base/base.dialog.content.component';

import { MatPaginator } from '@angular/material/paginator';
import { MatriculaServiceImpl } from '../matricula.impl.service';
import { EstadoGeneralState } from '../../../type/estadogeneral.state';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { SelectItemVO } from '../../../vo/selectitem.vo';
 
/**
 * La Class CargaAcademicaComponent.
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
	selector: 'app-cargaacademica',
	templateUrl: './cargaacademica.component.html',
	styleUrls: ['./cargaacademica.component.css'],
	providers: [CargaAcademicaService, DetalleCargaAcademicaService, MatriculaServiceImpl],
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
export class CargaAcademicaComponent extends BaseComponent implements OnInit, OnChanges, AfterViewInit {

	/** El objeto carga academica. */
	public cargaAcademica: CargaAcademica = new CargaAcademica();

	public cargaAcademicaTemp: CargaAcademica = new CargaAcademica();

	/** La lista carga academica. */
	public listaCargaAcademica: CargaAcademica[] = [];

	/** La lista item select. */
	public listaCargaAcademicaSelectMap: Map<string, CargaAcademica> = new Map<string, CargaAcademica>();

	public mostrasEditT: boolean = false;s

	
	/** detcargaacademica */
	public detCargaAcademica: DetalleCargaAcademica = new DetalleCargaAcademica();

	public idDetPlanEstudioCurso: string = "";
	public personalBydocente: number = 1;
	public personalBydocentePractica: number = 2;

	public dataSource = new MatTableDataSource<DetalleCargaAcademica>();

	public columnsToDisplay = ['view','c', 'curso', 'donceteP', 'docenteA', 'grupo', 'A'];
	@ViewChild(MatPaginator) paginator: MatPaginator;

	public listaDetalleCargaAcademica: DetalleCargaAcademica[] = [];

	public listaDetalleCargaAcademicaSelectMap: Map<string, DetalleCargaAcademica> = new Map<string, DetalleCargaAcademica>();

	public mostrarBtn: boolean = false;

	public gradoTemp: number = null;

	/*** El objeto anhio. */
	public anhioActivo: Anhio = new Anhio(); 

	public listaTurno: SelectItemVO[] = [];

	constructor(private fb: FormBuilder, public dialog: MatDialog, public snackbar: MatSnackBar, router: Router, route: ActivatedRoute, private cargaAcademicaService: CargaAcademicaService,
		protected commonServiceImpl: CommonServiceImpl, public detalleCargaAcademicaService: DetalleCargaAcademicaService, protected loginDataService: LoginService, protected _typeSelectItemService: TypeSelectItemService,
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
		this.crearFormulario(this.cargaAcademica);
	}

	private crearFormulario(obj: CargaAcademica): void {

		this.frmGroup = this.fb.group({
			anhio: this.fb.group({
				idAnhio: [obj.anhio.idAnhio, { validators: [Validators.required] }],
				descripcionView: [obj.anhio.descripcionView, { validators: [Validators.required] }, { updateOn: 'blur' }]
			}),
			// seccion:[obj.seccion],
			seccion: this.fb.group({
				idSeccion: [obj.seccion.idSeccion],
				grado: [obj.seccion.grado],
				descripcionView: [obj.seccion.descripcionView, { updateOn: 'blur' }],
			}),
			itemByTurno: this.fb.group({
				idItem: [obj.itemByTurno.idItem],
				descripcionView: [obj.itemByTurno.descripcionView, { updateOn: 'blur' }]
			}),
			aula: this.fb.group({
				idAula: [obj.aula.idAula, { validators: [Validators.required] }],
				descripcionView: [obj.aula.descripcionView, { validators: [Validators.required] }, { updateOn: 'blur' }]
			}),
			personalByTutor: this.fb.group({
				idPersonal: [obj.personalByTutor.idPersonal],
				descripcionView: [obj.personalByTutor.descripcionView, { updateOn: 'blur' }]
			}),
			personalByCoTutor: this.fb.group({
				idPersonal: [obj.personalByCoTutor.idPersonal],
				descripcionView: [obj.personalByCoTutor.descripcionView, { updateOn: 'blur' }]
			}),
			personalByCoordinador: this.fb.group({
				idPersonal: [obj.personalByCoordinador.idPersonal],
				descripcionView: [obj.personalByCoordinador.descripcionView, { updateOn: 'blur' }]
			}),
			idCargaAcademica: [obj.idCargaAcademica],
			idEntidad: [obj.idEntidad],
			codigoSalon: [obj.codigoSalon],
			nombre: [obj.nombre],
			tipoPeriodo: [obj.tipoPeriodo],
			nroVacante: [obj.nroVacante],
			usuarioCreacion: [obj.usuarioCreacion],
			fechaCreacion: [obj.fechaCreacion],
			usuarioModificacion: [obj.usuarioModificacion],
			fechaModificacion: [obj.fechaModificacion],
			observacion: [obj.observacion],

		});
		this.onChange();
	}

	private onChange(): void {
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});

		this.frmGroup.get('anhio.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalAnhioInputanhio(value);
		});
		this.frmGroup.get('seccion.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalSeccionInputseccion(value);
		});
		this.frmGroup.get('itemByTurno.idItem').valueChanges.subscribe(value => {
			this.listarDetalleCargaAcademicaVO(value);
		});
		this.frmGroup.get('aula.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalAulaInputaula(value);
		});
		this.frmGroup.get('personalByTutor.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalPersonalInputpersonalByTutor(value);
		});
		this.frmGroup.get('personalByCoTutor.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalPersonalInputpersonalByCoTutor(value);
		});
		this.frmGroup.get('personalByCoordinador.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalPersonalInputpersonalByCoordinador(value);
		});

		
	}
	public async cargarCombo() {
		let paramsTemp: Map<any, any> = new Map<any, any>();
		paramsTemp = paramsTemp.set(ListaItemType.ITEM_TURNO, 0);
		await this.commonServiceImpl.obtenerListaItemSelectItemMap(paramsTemp);
		this.listaTurno = this.commonServiceImpl.getListaItemSelectItem(ListaItemType.ITEM_TURNO);
	}

	onInit() {
		/*var id = this.route.params.subscribe(params => {
		  var id = params['id'];
	
		});*/
	}
	get productControlArray() {
		return this.frmGroup.get('listaDetCargaAcademica') as FormArray;
	}


	public guardar() {
		try {
			this.cargaAcademica = this.frmGroup.value;
			this.cargaAcademica.idEntidad = this.usuarioSession.entidad.idEntidad; 
			this.cargaAcademica.cargaAcademicaDetalleCargaAcademicaList = [];
			if (this.listaDetalleCargaAcademicaSelectMap) {
				this.listaDetalleCargaAcademicaSelectMap.forEach((entryVal : any , entryKey : any )  => {
					this.cargaAcademica.cargaAcademicaDetalleCargaAcademicaList.push(entryVal);
				})
			}
			this.cargaAcademicaService.crear(this.cargaAcademica).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.guardoExito();
						this.buscar();
						this.listaDetalleCargaAcademica = [];
						this.listaDetalleCargaAcademicaSelectMap = new Map<string, DetalleCargaAcademica>();
					}
				},
				error => {
					this.mostrarMensajeError(error);
				}, () => {
					this.endProgres();
				});
		} catch (error) {
			this.mostrarMensajeError(error);
		}
		this.endProgres();
	}





	selection = new SelectionModel<DetalleCargaAcademica>(true, []);

	isAllSelected() {
		const numSelected = this.selection.selected.length;
		const numRows = this.dataSource.data.length;
		return numSelected === numRows;
	}
	masterToggle() {
		this.isAllSelected() ?
			this.dataSource.data.forEach(row => {
				if (!row.idDetalleCargaAcademica) { 
					row.idDetalleCargaAcademica = "";
					row.personalByDocenteAuxiliar = new Personal();
					row.personalByDocentePrincipal = new Personal();
					row.grupo = new Grupo();
					row.checked = false;
					this.selection.clear();
					this.listaDetalleCargaAcademicaSelectMap.delete(row.detMallaCurricular.idDetMallaCurricular);
					this.mostrarBtn = false;
				} else {
					this.selection.select(row);
					row.checked = true;
					this.mostrarBtn = true;
				}

			}) : this.dataSource.data.forEach(row => {
				this.selection.select(row);
				row.checked = true;
				this.mostrarBtn = true;
			});
	}

	togleCheck(row: DetalleCargaAcademica) { 
		if (row.idDetalleCargaAcademica != "") {
			//this.selection.isSelected(row);
		} else {
			if (!row.idDetalleCargaAcademica) { 
				row.idDetalleCargaAcademica = "";
				row.personalByDocenteAuxiliar = new Personal();
				row.personalByDocentePrincipal = new Personal();
				row.grupo = new Grupo(); 
				this.listaDetalleCargaAcademicaSelectMap.delete(row.detMallaCurricular.idDetMallaCurricular); 
			}
			//this.selection.isSelected(row);
			this.mostrarBtn = true;
		}

	}

	togleCheckSub(surow: DetalleCargaAcademica,row: DetalleCargaAcademica) {
		row.checked = surow.checked;
			if (!surow.checked) {
				surow.idDetalleCargaAcademica = "";
				surow.personalByDocenteAuxiliar = new Personal();
				surow.personalByDocentePrincipal = new Personal();
				surow.grupo = new Grupo(); 
				//this.listaDetalleCargaAcademicaSelectMap.delete(row.detMallaCurricular.idDetMallaCurricular);
			}else{
				//this.listaDetalleCargaAcademicaSelectMap.set(row.detMallaCurricular.idDetMallaCurricular, row) 
			}
	}

	/**
	 * Nuevo.
	 *
	 */
	public nuevo() {
		this.cargaAcademica = new CargaAcademica();
		this.gradoTemp = null;
		this.listaDetalleCargaAcademica = [];
		this.listaDetalleCargaAcademicaSelectMap = new Map<string, DetalleCargaAcademica>();
		this.dataSource = new MatTableDataSource<DetalleCargaAcademica>();
		this.frmGroup.patchValue(this.cargaAcademica, { onlySelf: true, emitEvent: false });
		this.frmGroup.get('anhio').patchValue(this.anhioActivo, { onlySelf: true, emitEvent: false });
		this.mostrarPanelForm = true;
		this.accionNuevo = true;
		this.listasize = 0;
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
	 * Limpiar.
	 *
	 */
	private limpiar() {
		try {
			this.listaCargaAcademica = [];
			this.limpiaDataProvider(this.search);
			this.cargaAcademica = new CargaAcademica();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.cargaAcademica, { onlySelf: true, emitEvent: false });
				this.frmGroup.get('anhio').patchValue(this.anhioActivo, { onlySelf: true, emitEvent: false });
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
			this.cargaAcademicaService.eliminar(id).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.eliminoExito();
						this.listaDetalleCargaAcademicaSelectMap = new Map<string, DetalleCargaAcademica>();
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
	public confirmarEliminar(cargaAcademicaTemp: CargaAcademica) {
		this.cargaAcademica = cargaAcademicaTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				this.eliminar(this.cargaAcademica.idCargaAcademica);
			}
		});
	}

	/**
	 * confirmar eliminar.
	 *
	 */
	public confirmarEliminarDetCargaAcademica(detcargaAcademicaTempVO: DetalleCargaAcademica) {
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				detcargaAcademicaTempVO.checked = false;
				if(detcargaAcademicaTempVO.detMallaCurricular.idDetMallaCurricular!=null){
					this.listaDetalleCargaAcademicaSelectMap.set(detcargaAcademicaTempVO.detMallaCurricular.idDetMallaCurricular, detcargaAcademicaTempVO);
				}
			}
		});
	}



	/**
	 * buscar id
	 *
	 */
	public buscarID(cargaAcademicaTemp: CargaAcademica) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.cargaAcademica = Object.assign({}, cargaAcademicaTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
				this.gradoTemp = cargaAcademicaTemp.seccion.grado.idGrado;
				this.listarDetalleCargaAcademicaVO();
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.cargaAcademica = Object.assign({}, cargaAcademicaTemp);
					this.lanzarCargaAcademica();
				}
			}
			this.frmGroup.patchValue(this.cargaAcademica, { onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}

	}
	/* 
	asociar 
	*/
	public asociar(cargaAcademicaTemp: CargaAcademica) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				cargaAcademicaTemp.checked = true;
				this.agregarCheck(cargaAcademicaTemp);
				this.dialogRef.close(this.listaCargaAcademicaSelectMap);
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
			this.listaCargaAcademica = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.params = this.params.set('id', this.anhioActivo.idAnhio + "");
			this.params = this.params.set('idEntidadSelect',this.idEntidad);
			this.cargaAcademicaService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.setDataProvider(data);
						this.listaCargaAcademica = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaCargaAcademica.length == 1) {
			this.asociar(this.listaCargaAcademica[0]);
		}
	}


	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			this.calcularStartRow();
			this.cargaAcademicaService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaCargaAcademica = data.listaResultado;
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
		this.buscar();
	}

	private lanzarCargaAcademica() {
		// Usamos el mÃ©todo emit
		this.change.emit({ cargaAcademica: this.cargaAcademica });
	}

	/*
	agregar check
	*/
	public agregarCheck(cargaAcademicaTemp: CargaAcademica) {
		if (cargaAcademicaTemp.checked) {
			if (!this.listaCargaAcademicaSelectMap.has(cargaAcademicaTemp.idCargaAcademica)) {
				this.listaCargaAcademicaSelectMap.set(cargaAcademicaTemp.idCargaAcademica, cargaAcademicaTemp);
			}
		} else {
			if ((this.listaCargaAcademicaSelectMap.has(cargaAcademicaTemp.idCargaAcademica))) {
				this.listaCargaAcademicaSelectMap.delete(cargaAcademicaTemp.idCargaAcademica);
			}
		}
	}
	/*
	 verificar check
	*/
	private verificarCheck() {
		this.listaCargaAcademica.forEach((data) => {
			if ((this.listaCargaAcademicaSelectMap.has(data.idCargaAcademica))) {
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
					this.cargaAcademica.anhio.idAnhio = entryVal.idAnhio;
					this.anhioActivo = entryVal;
					this.frmGroup.get('anhio.idAnhio').setValue(entryVal.idAnhio);
					this.frmGroup.get('anhio').patchValue(entryVal, { onlySelf: true, emitEvent: false });
					this.buscar();
				});
			}
		});
	}
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
					this.frmGroup.get('seccion.idSeccion').setValue(entryVal.idSeccion);
					this.frmGroup.get('seccion.grado').setValue(entryVal.grado);
					this.cargaAcademica.seccion = entryVal
					this.frmGroup.get('seccion').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}
	/**
	Abrir modal Item input
	*/
	public abrirModalItemInputitemByTurno(pSearch?: string) {
		this.frmGroup.get('itemByTurno.idItem').setValue(null);
		this.abrirModalItemitemByTurno(pSearch);
	}

	/**
	  El abrir modal item. 
	*/
	public abrirModalItemitemByTurno(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: ListaItems = new ListaItems();
		data.idListaItems = ListaItemType.ITEM_TURNO;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalItem = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					this.cargaAcademica.itemByTurno = entryVal
					this.frmGroup.get('itemByTurno.idItem').setValue(entryVal.idItem);
					this.frmGroup.get('itemByTurno').patchValue(entryVal, { onlySelf: true, emitEvent: false });
					this.listarDetalleCargaAcademicaVO();

				});
			}
		});
	}
	/**
	Abrir modal Aula input
	*/
	public abrirModalAulaInputaula(pSearch?: string) {
		this.frmGroup.get('aula.idAula').setValue(null);
		this.abrirModalAulaaula(pSearch);
	}

	/**
	  El abrir modal aula. 
	*/
	public abrirModalAulaaula(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Aula = new Aula();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalAula = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					this.frmGroup.get('aula.idAula').setValue(entryVal.idAula);
					this.frmGroup.get('aula').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}
	/**
	Abrir modal Personal input
	*/
	public abrirModalPersonalInputpersonalByTutor(pSearch?: string) {
		this.frmGroup.get('personalByTutor.idPersonal').setValue(null);
		this.abrirModalPersonalpersonalByTutor(pSearch);
	}

	/**
	  El abrir modal personal. 
	*/
	public abrirModalPersonalpersonalByTutor(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Personal = new Personal();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalPersonalSelect = true;
		//dialogRef.componentInstance.modulo = "matricula";
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					this.frmGroup.get('personalByTutor.idPersonal').setValue(entryVal.idPersonal);
					this.frmGroup.get('personalByTutor').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}
	/**
	Abrir modal Personal input
	*/
	public abrirModalPersonalInputpersonalByCoTutor(pSearch?: string) {
		this.frmGroup.get('personalByCoTutor.idPersonal').setValue(null);
		this.abrirModalPersonalpersonalByCoTutor(pSearch);
	}

	/**
	  El abrir modal personal. 
	*/
	public abrirModalPersonalpersonalByCoTutor(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Personal = new Personal();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalPersonalSelect = true;
		//dialogRef.componentInstance.modulo = "matricula";
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					this.frmGroup.get('personalByCoTutor.idPersonal').setValue(entryVal.idPersonal);
					this.frmGroup.get('personalByCoTutor').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}
	/**
	Abrir modal Personal input
	*/
	public abrirModalPersonalInputpersonalByCoordinador(pSearch?: string) {
		this.frmGroup.get('personalByCoordinador.idPersonal').setValue(null);
		this.abrirModalPersonalpersonalByCoordinador(pSearch);
	}

	/**
	  El abrir modal personal. 
	*/
	public abrirModalPersonalpersonalByCoordinador(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Personal = new Personal();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalPersonalSelect = true;
		//dialogRef.componentInstance.modulo = "matricula";
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					this.frmGroup.get('personalByCoordinador.idPersonal').setValue(entryVal.idPersonal);
					this.frmGroup.get('personalByCoordinador').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}

	/**detalle carga academica */

	/**
		 * Buscar.
		 *
		 */
	public listarDetalleCargaAcademicaVO(idItemTurno ? : any) {
		try {
			this.listaDetalleCargaAcademica = [];
			this.cargaAcademicaTemp = new CargaAcademica();
			this.dataSource = new MatTableDataSource<DetalleCargaAcademica>();
			if (this.accionNuevo) {
				this.cargaAcademicaTemp = this.frmGroup.value;
				this.cargaAcademicaTemp.itemByTurno.idItem = idItemTurno;
				this.cargaAcademicaTemp.idEntidad = this.idEntidad;
			} else {
				this.cargaAcademicaTemp = this.cargaAcademica;
			} 
			this.selection.clear();
			this.detalleCargaAcademicaService.obtenerDetCargaLectiva(this.cargaAcademicaTemp).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.setDataProvider(data);
						this.listaDetalleCargaAcademica = data.listaResultado;
						if (this.listaDetalleCargaAcademica) {
							this.listasize = this.listaDetalleCargaAcademica.length;
						}
						this.dataSource = new MatTableDataSource<DetalleCargaAcademica>(this.listaDetalleCargaAcademica);
						this.dataSource.paginator = this.paginator;
						this.mostrarBtn = false;

						this.dataSource.data.forEach((row, index) => {
							if (row.idDetalleCargaAcademica != null) {
								this.mostrarBtn = true;
								row.checked = true;
								this.selection.select(row);
							}
							if(row.detalleCargaAcademicaDTOList.length>0){
								row.detalleCargaAcademicaDTOList.forEach((obj)=>{
									if (obj.idDetalleCargaAcademica != null) { 
										obj.checked = true; 
									}	
								})
							}
						});
						if (this.mostrarBtn) {
							//this.listarDetCargaLectiva();
						}
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
	  El abrir modal anho semestre. 
	*/
	public abrirModalPersonal(tipoPersona: number, temp: DetalleCargaAcademica, pSearch?: string) {
		if (temp.checked) {
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
							if (tipoPersona == this.personalBydocente) {
								//this.detCargaAcademica.personalByDocentePrincipal = entryVal.idPersonal;
								//	this.detCargaAcademica.personalByDocentePrincipal.descripcionView = this.obtenerNombrePersona(entryVal.persona);
								temp.personalByDocentePrincipal = entryVal;
							} else if (tipoPersona == this.personalBydocentePractica) {
								//this.detCargaAcademica.personalByDocenteAuxiliar = entryVal.idPersonal;
								//	this.detCargaAcademica.personalByDocenteAuxiliar.descripcionView = this.obtenerNombrePersona(entryVal.persona);
								temp.personalByDocenteAuxiliar = entryVal
							}
							// if(temp.detalleCargaAcademicaDTOList.length<=0){
							 	this.detcargaAcademicaVoadd(temp);
							// }
						});
					}
				}
			});
		}
	}

	/**
	 Abrir modal Grupo input
	*/
	public abrirModalGrupoInputgrupo(temP: DetalleCargaAcademica, pSearch?: string) {
		//this.detCargaAcademica.grupo = null;
		this.abrirModalGrupogrupo(temP, pSearch);
	}

	/**
	 El abrir modal grupo. 
	*/
	public abrirModalGrupogrupo(temP: DetalleCargaAcademica, pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContentOverride);
		let data: Grupo = new Grupo();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalGrupo = true;
		dialogRef.componentInstance.modulo = 'matricula';
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					temP.grupo = entryVal;
					this.detcargaAcademicaVoadd(temP);
				});
			}
		});
	}


	detcargaAcademicaVoadd(detTempVO: DetalleCargaAcademica) {
		if (detTempVO.idDetalleCargaAcademica != "") {
			this.listaDetalleCargaAcademicaSelectMap.set(detTempVO.detMallaCurricular.idDetMallaCurricular, detTempVO)
		}
		let key: string = detTempVO.detMallaCurricular.idDetMallaCurricular;
		if (!this.listaDetalleCargaAcademicaSelectMap.has(key)) {
			let detCargaVO: DetalleCargaAcademica = new DetalleCargaAcademica();

			//detCargaVO.detMallaCurricular..grupo = detTempVO.grupo;	
			detCargaVO.personalByDocentePrincipal = detTempVO.personalByDocentePrincipal;
			detCargaVO.personalByDocenteAuxiliar = detTempVO.personalByDocenteAuxiliar;
			detCargaVO.detMallaCurricular = detTempVO.detMallaCurricular;
			detCargaVO.idDetalleCargaAcademica = detTempVO.idDetalleCargaAcademica;
			detCargaVO.checked = detTempVO.checked;
			this.listaDetalleCargaAcademicaSelectMap.set(key, detCargaVO);
		}
		else {
			let detCargaVO: DetalleCargaAcademica = this.listaDetalleCargaAcademicaSelectMap.get(key);

			detCargaVO.grupo = detTempVO.grupo;
			detCargaVO.personalByDocentePrincipal = detTempVO.personalByDocentePrincipal;
			detCargaVO.personalByDocenteAuxiliar = detTempVO.personalByDocenteAuxiliar;
			detCargaVO.detMallaCurricular = detTempVO.detMallaCurricular;
			detCargaVO.idDetalleCargaAcademica = detTempVO.idDetalleCargaAcademica;
			detCargaVO.checked = detTempVO.checked;
		}

	}
}

@Component({
	template: `
	<app-grupo *ngIf="esModalGrupo"  [modulo] = "modulo" [id] = "id" [esIncludeComponent] = "esIncludeComponent"  [showModal]="showModal" [showSelectMultiple] = "showSelectMultiple" [dialogRef] = "dialogRef" [data] = "data"></app-grupo>
	 `
})
export class DialogContentOverride extends BaseDialogContent {
	public esModalGrupo: boolean = false;
	constructor(@Optional() public dialogRef: MatDialogRef<DialogContentOverride>) { super() }
}