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

import { AlumnoService } from "./alumno.service";
import { Alumno } from "../../../models/matricula/alumno.model";
import { Postulante } from "../../../models/admision/postulante.model";
import { Grado } from "../../../models/admision/grado.model";
import { Item } from "../../../models/common/item.model";
import { Ubigeo } from "../../../models/common/ubigeo.model";
import { Entidad } from "../../../models/seguridad/entidad.model";
import { Sede } from "../../../models/admision/sede.model";
import { ListaItemType } from '../../../type/listaitem.type';
import { ListaItems } from '../../../models/common/listaitems.model';
import { BaseDialogContent } from '../../../base/base.dialog.content.component';

import { FileVO } from '../../../vo/file.vo';
import { FileUploaderService } from '../../../upload/file-uploader.service';


/**
 * La Class AlumnoComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */

@Component({
	selector: 'app-alumno',
	templateUrl: './alumno.component.html',
	styleUrls: ['./alumno.component.css'],
	providers: [AlumnoService, FileUploaderService]
})
export class AlumnoComponent extends BaseComponent implements OnInit, OnChanges, AfterViewInit {

	/** El objeto alumno. */
	public alumno: Alumno = new Alumno();

	/** La lista alumno. */
	public listaAlumno: Alumno[] = [];

	/** La lista item select. */
	public listaAlumnoSelectMap: Map<string, Alumno> = new Map<string, Alumno>();

	public uploader: FileVO = new FileVO();

	public estadoTemp: string = "";

	public filtroGrado = new FormControl(null);

	public filtroEstado = new FormControl(null);

	constructor(private fb: FormBuilder, public dialog: MatDialog, public snackbar: MatSnackBar, router: Router, route: ActivatedRoute, private alumnoService: AlumnoService,
		protected commonServiceImpl: CommonServiceImpl, protected loginDataService: LoginService,
		protected _typeSelectItemService: TypeSelectItemService, protected _translate: TranslateService,
		protected fileUploaderService: FileUploaderService) {
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
		this.crearFormulario(this.alumno);

	}

	private crearFormulario(obj: Alumno): void {
		this.frmGroup = this.fb.group({
			// postulante: this.fb.group({
			// 	idPostulante: [obj.postulante.idPostulante],
			// 	descripcionView: [obj.postulante.descripcionView, { updateOn: 'blur' }]
			// }),
			grado: this.fb.group({
				idGrado: [obj.grado.idGrado, { Validators: [Validators.required] }],
				descripcionView: [obj.grado.descripcionView, { Validators: [Validators.required] }, { updateOn: 'blur' }]
			}),
			itemByDocIdentidad: this.fb.group({
				idItem: [obj.itemByDocIdentidad.idItem],
				descripcionView: [obj.itemByDocIdentidad.descripcionView, { updateOn: 'blur' }]
			}),
			lugarNacimiento: this.fb.group({
				idUbigeo: [obj.lugarNacimiento.idUbigeo],
				descripcionView: [obj.lugarNacimiento.descripcionView, { updateOn: 'blur' }]
			}),
			itemByNacionalidad: this.fb.group({
				idItem: [obj.itemByNacionalidad.idItem],
				descripcionView: [obj.itemByNacionalidad.descripcionView, { updateOn: 'blur' }]
			}),
			entidad: [obj.entidad],
			sede: this.fb.group({
				idSede: [obj.sede.idSede],
				descripcionView: [obj.sede.descripcionView, { updateOn: 'blur' }]
			}),
			idAlumno: [obj.idAlumno],
			codigo: [obj.codigo],
			estado: [obj.estado],
			nroDoc: [obj.nroDoc],
			apellidoPaterno: [obj.apellidoPaterno],
			apellidoMaterno: [obj.apellidoMaterno],
			nombres: [obj.nombres],
			fechaNacimiento: [obj.fechaNacimiento],
			telefono1: [obj.telefono1],
			telefono2: [obj.telefono2],
			celular: [obj.celular],
			email: [obj.email],
			sexo: [obj.sexo],
			foto: [obj.foto],
			usuarioCreacion: [obj.usuarioCreacion],
			fechaCreacion: [obj.fechaCreacion],
			usuarioModificacion: [obj.usuarioModificacion],
			fechaModificacion: [obj.fechaModificacion],
		});
		this.onChange();
	}

	private onChange(): void {
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});

		this.frmGroup.get('postulante.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalPostulanteInputpostulante(value);
		});
		this.frmGroup.get('grado.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalGradoInputgrado(value);
		});
		this.frmGroup.get('itemByDocIdentidad.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalItemInputitemByDocIdentidad(value);
		});
		this.frmGroup.get('lugarNacimiento.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalUbigeoInputlugarNacimiento(value);
		});
		this.frmGroup.get('itemByNacionalidad.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalItemInputitemByNacionalidad(value);
		});
		/*this.frmGroup.get('entidad.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalEntidadInputentidad(value);
		});*/
		this.frmGroup.get('sede.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalSedeInputsede(value);
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
			this.frmGroup.get('entidad').setValue(this.idEntidad);
			console.log(this.uploader);
			if (this.uploader != null && this.uploader.dataBase64 != "") {
				this.frmGroup.get('foto').setValue(this.frmGroup.get('codigo').value + this.frmGroup.get('nroDoc').value + ".jpg");
			}else{
				this.frmGroup.get('foto').setValue("carita.jpg");
			}
			if (this.accionNuevo) {
				this.alumnoService.crear(this.frmGroup.value).subscribe(
					data => {
						if (this.isProcesoOK(data)) {							
							this.subirImagen(data.objetoResultado);							
							this.guardoExito();
							this.buscar();
						}
					},
					error => {
						this.mostrarMensajeError(error);
					}
				);
			} else {
				this.alumnoService.modificar(this.frmGroup.value,this.frmGroup.value.idAlumno).subscribe(
					data => {
						if (this.isProcesoOK(data)) {							
							this.subirImagen(data.objetoResultado);
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


	private subirImagen(dataAlumno: Alumno) {
		if (this.uploader != null && this.uploader.dataBase64 != null) {
			this.uploader.name = "086" + dataAlumno.codigo + dataAlumno.nroDoc;
			this.uploader.extension = "jpg";
			this.fileUploaderService.subirArchivoAlumno(this.uploader).subscribe(
				data => {
					this.uploader = new FileVO();
					//this.mostrarMensajeExito("Archivo tipo" +" - "+ this.rutaAccesoMenu);
				},
				error => {
					this.mostrarMensajeError(error);
				},
				() => {
				}
			);
		}
	}


	private obtenerFoto() {
		this.uploader.dataBase64Convert = 'data:image/jpg;base64,';
		this.fileUploaderService.obtenerArchivoAlumno(this.alumno.foto).subscribe(
			data => {
				this.uploader.dataBase64 = data.objetoResultado;
			},
			error => {
				this.mostrarMensajeError(error);
			},
			() => { }
		);
	}
	/**
	 * Nuevo.
	 *
	 */
	public nuevo() {
		this.alumno = new Alumno();
		this.alumno.estado = "A";
		this.frmGroup.patchValue(this.alumno, { onlySelf: true, emitEvent: false });
		this.mostrarPanelForm = true;
		this.uploader = new FileVO();
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
			this.listaAlumno = [];
			this.limpiaDataProvider(this.search);
			this.alumno = new Alumno();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.alumno, { onlySelf: true, emitEvent: false });
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
			this.alumnoService.eliminar(id).subscribe(
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
	public confirmarEliminar(alumnoTemp: Alumno) {
		this.alumno = alumnoTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				this.eliminar(this.alumno.idAlumno);
			}
		});
	}

	/**
	 * buscar id
	 *
	 */
	public buscarID(alumnoTemp: Alumno) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.alumno = Object.assign({}, alumnoTemp);
				this.uploader = new FileVO();
				this.obtenerFoto();
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.alumno = Object.assign({}, alumnoTemp);
					this.lanzarAlumno();
				}
			}
			this.frmGroup.patchValue(this.alumno, { onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(alumnoTemp: Alumno) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				alumnoTemp.checked = true;
				this.agregarCheck(alumnoTemp);
				this.dialogRef.close(this.listaAlumnoSelectMap);
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
			this.listaAlumno = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.params = this.params.set('idEntidadSelect', this.idEntidad);
			this.alumnoService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.setDataProvider(data);
						this.listaAlumno = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaAlumno.length == 1) {
			this.asociar(this.listaAlumno[0]);
		}
	}

	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			this.calcularStartRow();
			this.alumnoService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaAlumno = data.listaResultado;
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

	public changeEstado() {		
		this.params = this.params.set('id', this.filtroEstado.value);
		this.buscar();
	}

	/**
	  * cancelar.
	  *
	*/
	public cancelar() {
		this.alumno = new Alumno(); 
		this.frmGroup.patchValue(this.alumno, { onlySelf: true, emitEvent: false });
		this.mostrarPanelForm = false;
	}

	private lanzarAlumno() {
		// Usamos el mÃ©todo emit
		this.change.emit({ alumno: this.alumno });
	}

	clearModal() {	
		this.filtroGrado.setValue(null);
		this.params = this.params.set('idPadreView', "");
		this.buscar();
	}

	/*
	agregar check
	*/
	public agregarCheck(alumnoTemp: Alumno) {
		if (alumnoTemp.checked) {
			if (!this.listaAlumnoSelectMap.has(alumnoTemp.idAlumno)) {
				this.listaAlumnoSelectMap.set(alumnoTemp.idAlumno, alumnoTemp);
			}
		} else {
			if ((this.listaAlumnoSelectMap.has(alumnoTemp.idAlumno))) {
				this.listaAlumnoSelectMap.delete(alumnoTemp.idAlumno);
			}
		}
	}
	/*
	 verificar check
	*/
	private verificarCheck() {
		this.listaAlumno.forEach((data) => {
			if ((this.listaAlumnoSelectMap.has(data.idAlumno))) {
				data.checked = true;
			}
		});
	}

	/**
	Abrir modal Postulante input
	*/
	public abrirModalPostulanteInputpostulante(pSearch?: string) {
		this.frmGroup.get('postulante.idPostulante').setValue(null);
		this.abrirModalPostulantepostulante(pSearch);
	}

	/**
	  El abrir modal postulante. 
	*/
	public abrirModalPostulantepostulante(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Postulante = new Postulante();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		//dialogRef.componentInstance.esModalPostulante = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.idPostulante;//   + ' ' +  entryVal.nombre;			
					this.frmGroup.get('postulante').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
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
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalGrado = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.itemByNivel.nombre + ' ' + entryVal.nombre; 
					this.frmGroup.get('grado.idGrado').setValue(entryVal.idGrado);
					this.frmGroup.get('grado').patchValue(entryVal, { onlySelf: true, emitEvent: false });
				});
			}
		});
	}

	/**
	  El abrir modal grado. 
	*/
	public abrirModalGradogrado2(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Grado = new Grado();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalGrado = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.itemByNivel.nombre + ' ' + entryVal.nombre;
					this.filtroGrado.setValue(entryVal.descripcionView);				
					this.params = this.params.set('idPadreView', entryVal.idGrado + "");
					this.buscar();
				});
			}
		});
	}
	/**
	Abrir modal Item input
	*/
	public abrirModalItemInputitemByDocIdentidad(pSearch?: string) {
		this.frmGroup.get('itemByDocIdentidad.idItem').setValue(null);
		this.abrirModalItemitemByDocIdentidad(pSearch);
	}

	/**
	  El abrir modal item. 
	*/
	public abrirModalItemitemByDocIdentidad(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: ListaItems = new ListaItems();
		data.idListaItems = ListaItemType.T3_TIPO_DOCUMENTO;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalItem = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.codigoExterno + ' ' + entryVal.nombre;
					this.frmGroup.get('itemByDocIdentidad').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}
	/**
	Abrir modal Ubigeo input
	*/
	public abrirModalUbigeoInputlugarNacimiento(pSearch?: string) {
		this.frmGroup.get('lugarNacimiento.idUbigeo').setValue(null);
		this.abrirModalUbigeolugarNacimiento(pSearch);
	}

	/**
	  El abrir modal ubigeo. 
	*/
	public abrirModalUbigeolugarNacimiento(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Ubigeo = new Ubigeo();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalUbigeo = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.idUbigeo + ' ' + entryVal.descripcion;
					this.frmGroup.get('lugarNacimiento').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}
	/**
	Abrir modal Item input
	*/
	public abrirModalItemInputitemByNacionalidad(pSearch?: string) {
		this.frmGroup.get('itemByNacionalidad.idItem').setValue(null);
		this.abrirModalItemitemByNacionalidad(pSearch);
	}

	/**
	  El abrir modal item. 
	*/
	public abrirModalItemitemByNacionalidad(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: ListaItems = new ListaItems();
		data.idListaItems = ListaItemType.T4_NACIONALIDAD;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalItem = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.idItem + ' ' + entryVal.nombre;
					this.frmGroup.get('itemByNacionalidad').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}
	/**
	Abrir modal Entidad input
	*/
	public abrirModalEntidadInputentidad(pSearch?: string) {
		this.frmGroup.get('entidad.idEntidad').setValue(null);
		this.abrirModalEntidadentidad(pSearch);
	}

	/**
	  El abrir modal entidad. 
	*/
	public abrirModalEntidadentidad(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Entidad = new Entidad();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		//dialogRef.componentInstance.esModalEntidad = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.idEntidad;//   + ' ' +  entryVal.nombre;			
					this.frmGroup.get('entidad').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}
	/**
	Abrir modal Sede input
	*/
	public abrirModalSedeInputsede(pSearch?: string) {
		this.frmGroup.get('sede.idSede').setValue(null);
		this.abrirModalSedesede(pSearch);
	}

	/**
	  El abrir modal sede. 
	*/
	public abrirModalSedesede(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContentOverride);
		let data: Sede = new Sede();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalSede = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.idSede + ' ' + entryVal.nombre;
					this.frmGroup.get('sede.idSede').setValue(entryVal.idSede);
					this.frmGroup.get('sede').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}
}

@Component({
	template: `
	<app-sede *ngIf="esModalSede"  [modulo] = "modulo" [id] = "id" [esIncludeComponent] = "esIncludeComponent"  [showModal]="showModal" [showSelectMultiple] = "showSelectMultiple" [dialogRef] = "dialogRef" [data] = "data"></app-sede>
	 `
})
export class DialogContentOverride extends BaseDialogContent {
	public esModalSede: boolean = false;
	constructor(@Optional() public dialogRef: MatDialogRef<DialogContentOverride>) { super() }
}