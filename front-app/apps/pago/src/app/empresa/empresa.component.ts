import { Component, EventEmitter, OnInit, OnChanges, SimpleChanges, AfterViewInit, Inject } from '@angular/core';
import { FormControl, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

import { CommonServiceImpl } from "../../common/common.impl.service";
import { LoginService } from "../../seguridad/login/login.service";
import { TypeSelectItemService } from "../../../typeselectitemservice/typeselectitem.service";

import { BaseComponent, DialogConfirmContent, DialogContent } from "../../../base/base.component";
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { EmpresaService } from "./empresa.service";
import { Empresa } from "../../../models/pago/empresa.model";
import { Item } from "../../../models/common/item.model";
import { ListaItems } from '../../../models/common/listaitems.model';
import { ListaItemType } from '../../../type/listaitem.type';


/**
 * La Class EmpresaComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:53 COT 2021
 * @since BUILDERP-CORE 2.1
 */

@Component({
	selector: 'app-empresa',
	templateUrl: './empresa.component.html',
	styleUrls: ['./empresa.component.css'],
	providers: [EmpresaService]
})
export class EmpresaComponent extends BaseComponent implements OnInit, OnChanges, AfterViewInit {

	/** El objeto empresa. */
	public empresa: Empresa = new Empresa();

	public empresaValidar: Empresa = new Empresa();

	/** La lista empresa. */
	public listaEmpresa: Empresa[] = [];

	/** La lista item select. */
	public listaEmpresaSelectMap: Map<number, Empresa> = new Map<number, Empresa>();

	public ListadoSevicios: any[];

	constructor(private fb: FormBuilder, public dialog: MatDialog, public snackbar: MatSnackBar, router: Router, route: ActivatedRoute, private empresaService: EmpresaService,
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
		this.crearFormulario(this.empresa);
	}

	private crearFormulario(obj: Empresa): void {
		this.frmGroup = this.fb.group({
			itemByTipoVia: this.fb.group({
				idItem: [obj.itemByTipoVia.idItem],
				descripcionView: [obj.itemByTipoVia.descripcionView, { updateOn: 'blur' }]
			}),
			itemByZona: this.fb.group({
				idItem: [obj.itemByZona.idItem],
				descripcionView: [obj.itemByZona.descripcionView, { updateOn: 'blur' }]
			}),
			idEmpresa: [obj.idEmpresa],
			nombreZona: [obj.nombreZona],
			nombreTipoVia: [obj.nombreTipoVia],
			ruc: [obj.ruc],
			razonSocial: [obj.razonSocial],
			direccion: [obj.direccion],
			telefono: [obj.telefono],
			email: [obj.email],
			web: [obj.web],
			fechaCreacion: [obj.fechaCreacion],
			usuarioCreacion: [obj.usuarioCreacion],
			estado: [obj.estado],
		});
		this.onChange();
	}

	private onChange(): void {
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});

		this.frmGroup.get('itemByTipoVia.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalItemInputitemByTipoVia(value);
		});
		this.frmGroup.get('itemByZona.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalItemInputitemByZona(value);
		});
		this.frmGroup.get('ruc').valueChanges.subscribe(value => {
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
				this.empresaService.crear(this.frmGroup.value).subscribe(
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
				this.empresaService.modificar(this.frmGroup.value,this.frmGroup.value.idEmpresa).subscribe(
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
		this.empresa = new Empresa();
		this.empresa.estado = "A";
		this.frmGroup.patchValue(this.empresa, { onlySelf: true, emitEvent: false });
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
			this.listaEmpresa = [];
			this.limpiaDataProvider(this.search);
			this.empresa = new Empresa();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.empresa, { onlySelf: true, emitEvent: false });
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
			this.empresaService.eliminar(id).subscribe(
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
	public confirmarEliminar(empresaTemp: Empresa) {
		this.empresa = empresaTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				this.eliminar(this.empresa.idEmpresa);
			}
		});
	}

	/**
	 * buscar id
	 *
	 */
	public buscarID(empresaTemp: Empresa) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.empresa = Object.assign({}, empresaTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
				this.validarCampo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.empresa = Object.assign({}, empresaTemp);
					this.lanzarEmpresa();
				}
			}
			this.frmGroup.patchValue(this.empresa, { onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(empresaTemp: Empresa) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				empresaTemp.checked = true;
				this.agregarCheck(empresaTemp);
				this.dialogRef.close(this.listaEmpresaSelectMap);
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
			this.listaEmpresa = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.empresaService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.setDataProvider(data);
						this.listaEmpresa = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaEmpresa.length == 1) {
			this.asociar(this.listaEmpresa[0]);
		}
	}

	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			this.calcularStartRow();
			this.empresaService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaEmpresa = data.listaResultado;
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

	private lanzarEmpresa() {
		// Usamos el mÃ©todo emit
		this.change.emit({ empresa: this.empresa });
	}

	/*
	agregar check
	*/
	public agregarCheck(empresaTemp: Empresa) {
		if (empresaTemp.checked) {
			if (!this.listaEmpresaSelectMap.has(empresaTemp.idEmpresa)) {
				this.listaEmpresaSelectMap.set(empresaTemp.idEmpresa, empresaTemp);
			}
		} else {
			if ((this.listaEmpresaSelectMap.has(empresaTemp.idEmpresa))) {
				this.listaEmpresaSelectMap.delete(empresaTemp.idEmpresa);
			}
		}
	}
	/*
	 verificar check
	*/
	private verificarCheck() {
		this.listaEmpresa.forEach((data) => {
			if ((this.listaEmpresaSelectMap.has(data.idEmpresa))) {
				data.checked = true;
			}
		});
	}

	/**
	Abrir modal Item input
	*/
	public abrirModalItemInputitemByTipoVia(pSearch?: string) {
		this.frmGroup.get('itemByTipoVia.idItem').setValue(null);
		this.abrirModalItemitemByTipoVia(pSearch);
	}

	/**
	  El abrir modal item. 
	*/
	public abrirModalItemitemByTipoVia(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: ListaItems = new ListaItems();
		data.idListaItems = ListaItemType.T5_VIA;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalItem = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.codigo + ' ' + entryVal.nombre;
					this.frmGroup.get('itemByTipoVia.idItem').setValue(entryVal.idItem);
					this.frmGroup.get('itemByTipoVia').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}
	/**
	Abrir modal Item input
	*/
	public abrirModalItemInputitemByZona(pSearch?: string) {
		this.frmGroup.get('itemByZona.idItem').setValue(null);
		this.abrirModalItemitemByZona(pSearch);
	}

	/**
	  El abrir modal item. 
	*/
	public abrirModalItemitemByZona(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: ListaItems = new ListaItems();
		data.idListaItems = ListaItemType.T6_ZONA;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalItem = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.codigo + ' ' + entryVal.nombre;
					this.frmGroup.get('itemByZona.idItem').setValue(entryVal.idItem);
					this.frmGroup.get('itemByZona').patchValue(entryVal, { onlySelf: true, emitEvent: false });

				});
			}
		});
	}

	public findEmpresa() {
		this.empresaService.findEmpresa(this.frmGroup.value).subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					this.empresaValidar = data.objetoResultado;
					if (this.validarCampo) {
						if (this.empresaValidar.idEmpresa != null) {
							this.mostrarMensajeAdvertencia('El ruc ' + this.empresaValidar.ruc + ' ' + this.empresaValidar.razonSocial + ' ya Existe');
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


	seleccionConsultaNroDoc(nrodco: string) {
		if (nrodco.length == 11) {
			this.consultaNroDocRuc(nrodco);
		}
	}

	consultaNroDocRuc(nrodco: string): void {
		let dialogRef = this.dialog.open(DialogOverviewProveedorConsultaNroDocRUC, {
			width: '280px',
		});
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				this.consultarDOC(nrodco);
			}
		})

	}

	public consultarDOC(nroDoc: string) {
		try {
			this.startProgres();
			this.empresaService.consultaDocumentoSunat(nroDoc).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.ListadoSevicios = data.listaResultado;
						this.frmGroup.get("razonSocial").setValue(this.ListadoSevicios[1]);
						this.frmGroup.get("direccion").setValue(this.ListadoSevicios[2]);
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

}


@Component({
	selector: 'dialog-overview-example-dialog',
	template: `
	<mat-card>
		<mat-toolbar  color="primary">
			<span class="app-fill-remaining text-toolbar">
				Extraer Datos
			</span>		
		</mat-toolbar>		
		<mat-dialog-content>
			<p style="margin-left: 10px;margin-right: 10px;">Ingresaste un Ruc, deseas importar los datos desde la SUNAT?</p>
		</mat-dialog-content>	
		<mat-dialog-actions >
			<span class="app-fill-remaining">
				<div class="example-button-row">
					<button mat-raised-button (click)="dialogRef.close(false)" color="accent">NO</button>
					<button mat-raised-button (click)="dialogRef.close(true)" color="primary" >SI</button>
				</div> 
			</span>		
		</mat-dialog-actions>	
	</mat-card>
  `,
})
export class DialogOverviewProveedorConsultaNroDocRUC {

	constructor(
		public dialogRef: MatDialogRef<DialogOverviewProveedorConsultaNroDocRUC>,
		@Inject(MAT_DIALOG_DATA) public data: any) { }
}

