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

import { CuentaBancariaEntidadService } from "./cuentabancariaentidad.service";
import { CuentaBancariaEntidad } from "../../../models/pago/cuentabancariaentidad.model";
import { Item } from "../../../models/common/item.model";
import { Entidad } from "../../../models/seguridad/entidad.model";
import { Personal } from "../../../models/rrhh_escalafon/personal.model";
import { ListaItems } from '../../../models/common/listaitems.model';
import { ListaItemType } from '../../../type/listaitem.type';
import { SelectItemVO } from '../../../vo/selectitem.vo';


/**
 * La Class CuentaBancariaEntidadComponent.
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
	selector: 'app-cuentabancariaentidad',
	templateUrl: './cuentabancariaentidad.component.html',
	styleUrls: ['./cuentabancariaentidad.component.css'],
	providers: [CuentaBancariaEntidadService]
})
export class CuentaBancariaEntidadComponent extends BaseComponent implements OnInit, OnChanges, AfterViewInit {

	/** El objeto cuenta bancaria entidad. */
	public cuentaBancariaEntidad: CuentaBancariaEntidad = new CuentaBancariaEntidad();

	/** La lista cuenta bancaria entidad. */
	public listaCuentaBancariaEntidad: CuentaBancariaEntidad[] = [];

	/** La lista item select. */
	public listaCuentaBancariaEntidadSelectMap: Map<string, CuentaBancariaEntidad> = new Map<string, CuentaBancariaEntidad>();

	/** La lista mes. */
	public listaItemMoneda: SelectItemVO[] = [];

	public listaItemTipoCuenta: SelectItemVO[] = [];

	constructor(private fb: FormBuilder, public dialog: MatDialog, public snackbar: MatSnackBar, router: Router, route: ActivatedRoute, private cuentaBancariaEntidadService: CuentaBancariaEntidadService,
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
		this.crearFormulario(this.cuentaBancariaEntidad);
	}

	private crearFormulario(obj: CuentaBancariaEntidad): void {
		this.frmGroup = this.fb.group({
			itemByBanco: this.fb.group({
				idItem: [obj.itemByBanco.idItem,{validators: [Validators.required]} ,{ updateOn: 'blur' }],
				descripcionView: [obj.itemByBanco.descripcionView, {validators: [Validators.required]} ,{ updateOn: 'blur' }]
			}),
			entidad: [obj.entidad],
			itemByMoneda: this.fb.group({
				idItem: [obj.itemByMoneda.idItem,{validators: [Validators.required]} ,{ updateOn: 'blur' }],
				descripcionView: [obj.itemByMoneda.descripcionView, { updateOn: 'blur' }]
			}),
			itemByTipoCuenta: this.fb.group({
				idItem: [obj.itemByTipoCuenta.idItem , {validators: [Validators.required]} ,{ updateOn: 'blur' }],
				descripcionView: [obj.itemByTipoCuenta.descripcionView, { updateOn: 'blur' }]
			}),
			titular: this.fb.group({
				idPersonal: [obj.titular.idPersonal ,{validators: [Validators.required]} ,{ updateOn: 'blur' }],
				descripcionView: [obj.titular.descripcionView, {validators: [Validators.required]} ,{ updateOn: 'blur' }]
			}),
			idCuentaBancariaEntidad: [obj.idCuentaBancariaEntidad],
			nroCuenta: [obj.nroCuenta],
			nroCCI: [obj.nroCCI],
			estado: [obj.estado],
		});
		this.onChange();
	}

	private onChange(): void {
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});

		this.frmGroup.get('itemByBanco.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalItemInputitemByBanco(value);
		});
		/*this.frmGroup.get('entidad.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalEntidadInputentidad(value);
		});*/
		// this.frmGroup.get('itemByMoneda.descripcionView').valueChanges.subscribe(value => {

		// });
		// this.frmGroup.get('itemByTipoCuenta.descripcionView').valueChanges.subscribe(value => {
		// 	this.abrirModalItemInputitemByTipoCuenta(value);
		// });
		this.frmGroup.get('titular.descripcionView').valueChanges.subscribe(value => {
			this.abrirModalPersonalInputtitular(value);
		});
	}

	public async cargarComboItem() {
		let paramsTemp: Map<any, any> = new Map<any, any>();
		paramsTemp = paramsTemp.set(ListaItemType.TIPO_MONEDA, 0);
		paramsTemp = paramsTemp.set(ListaItemType.TIPO_CUENTA_BANCARIA, 0);
		await this.commonServiceImpl.obtenerListaItemSelectItemMap(paramsTemp);
		this.listaItemMoneda = this.commonServiceImpl.getListaItemSelectItem(ListaItemType.TIPO_MONEDA);
		this.listaItemTipoCuenta = this.commonServiceImpl.getListaItemSelectItem(ListaItemType.TIPO_CUENTA_BANCARIA);

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
				this.frmGroup.get('entidad').setValue(this.usuarioSession.entidad.idEntidad);
				this.cuentaBancariaEntidadService.crear(this.frmGroup.value).subscribe(
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
				this.cuentaBancariaEntidadService.modificar(this.frmGroup.value,this.frmGroup.value.idCuentaBancariaEntidad).subscribe(
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
		this.cuentaBancariaEntidad = new CuentaBancariaEntidad();
		this.frmGroup.patchValue(this.cuentaBancariaEntidad, { onlySelf: true, emitEvent: false });
		this.frmGroup.get('estado').setValue("A");
		this.frmGroup.get('itemByMoneda.idItem').setValue(3750011); 
		this.mostrarPanelForm = true;
		this.accionNuevo = true;
	}

	/**
	 * Inicializar.
	 *
	 */
	private inicializar() {
		super.validarPaginaView();
		super.getUsuarioSession();
		this.cargarComboItem();
		this.limpiar();
	}

	/**
	 * Limpiar.
	 *
	 */
	private limpiar() {
		try {
			this.listaCuentaBancariaEntidad = [];
			this.limpiaDataProvider(this.search);
			this.cuentaBancariaEntidad = new CuentaBancariaEntidad();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.cuentaBancariaEntidad, { onlySelf: true, emitEvent: false });
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
			this.cuentaBancariaEntidadService.eliminar(id).subscribe(
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
	public confirmarEliminar(cuentaBancariaEntidadTemp: CuentaBancariaEntidad) {
		this.cuentaBancariaEntidad = cuentaBancariaEntidadTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				this.eliminar(this.cuentaBancariaEntidad.idCuentaBancariaEntidad);
			}
		});
	}

	/**
	 * buscar id
	 *
	 */
	public buscarID(cuentaBancariaEntidadTemp: CuentaBancariaEntidad) {
		try {
			if ((this.showAccionModificar && !this.esIncludeComponent) || this.showComponentPage) {
				this.cuentaBancariaEntidad = Object.assign({}, cuentaBancariaEntidadTemp);
				this.mostrarPanelForm = true;
				this.accionNuevo = false;
			} else {
				if (this.esIncludeComponent && !this.showSelectMultiple) {
					this.cuentaBancariaEntidad = Object.assign({}, cuentaBancariaEntidadTemp);
					this.lanzarCuentaBancariaEntidad();
				}
			}
			this.frmGroup.patchValue(this.cuentaBancariaEntidad, { onlySelf: true, emitEvent: false });
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	/* 
	asociar 
	*/
	public asociar(cuentaBancariaEntidadTemp: CuentaBancariaEntidad) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				cuentaBancariaEntidadTemp.checked = true;
				this.agregarCheck(cuentaBancariaEntidadTemp);
				this.dialogRef.close(this.listaCuentaBancariaEntidadSelectMap);
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
			this.listaCuentaBancariaEntidad = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.params = this.params.set('idEntidadSelect', this.usuarioSession.entidad.idEntidad + '');
			this.cuentaBancariaEntidadService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.setDataProvider(data);
						this.listaCuentaBancariaEntidad = data.listaResultado;
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
		if (this.id != null && this.id != '' && this.listaCuentaBancariaEntidad.length == 1) {
			this.asociar(this.listaCuentaBancariaEntidad[0]);
		}
	}

	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			this.calcularStartRow();
			this.cuentaBancariaEntidadService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaCuentaBancariaEntidad = data.listaResultado;
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

	private lanzarCuentaBancariaEntidad() {
		// Usamos el mÃ©todo emit
		this.change.emit({ cuentaBancariaEntidad: this.cuentaBancariaEntidad });
	}

	/*
	agregar check
	*/
	public agregarCheck(cuentaBancariaEntidadTemp: CuentaBancariaEntidad) {
		if (cuentaBancariaEntidadTemp.checked) {
			if (!this.listaCuentaBancariaEntidadSelectMap.has(cuentaBancariaEntidadTemp.idCuentaBancariaEntidad)) {
				this.listaCuentaBancariaEntidadSelectMap.set(cuentaBancariaEntidadTemp.idCuentaBancariaEntidad, cuentaBancariaEntidadTemp);
			}
		} else {
			if ((this.listaCuentaBancariaEntidadSelectMap.has(cuentaBancariaEntidadTemp.idCuentaBancariaEntidad))) {
				this.listaCuentaBancariaEntidadSelectMap.delete(cuentaBancariaEntidadTemp.idCuentaBancariaEntidad);
			}
		}
	}
	/*
	 verificar check
	*/
	private verificarCheck() {
		this.listaCuentaBancariaEntidad.forEach((data) => {
			if ((this.listaCuentaBancariaEntidadSelectMap.has(data.idCuentaBancariaEntidad))) {
				data.checked = true;
			}
		});
	}

	/**
	Abrir modal Item input
	*/
	public abrirModalItemInputitemByBanco(pSearch?: string) {
		this.frmGroup.get('itemByBanco.idItem').setValue(null);
		this.abrirModalItemitemByBanco(pSearch);
	}

	/**
	  El abrir modal item. 
	*/
	public abrirModalItemitemByBanco(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: ListaItems = new ListaItems();
		data.idListaItems = ListaItemType.TIPO_BANCO;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalItem = true;
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.codigo + ' ' + entryVal.nombre;
					this.frmGroup.get('itemByBanco').patchValue(entryVal, { onlySelf: true, emitEvent: false });
					this.frmGroup.get('itemByBanco.idItem').setValue(entryVal.idItem);

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
	Abrir modal Personal input
	*/
	public abrirModalPersonalInputtitular(pSearch?: string) {
		this.frmGroup.get('titular.idPersonal').setValue(null);
		this.abrirModalPersonaltitular(pSearch);
	}

	/**
	  El abrir modal personal. 
	*/
	public abrirModalPersonaltitular(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContent);
		let data: Personal = new Personal();
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalPersonalSelect = true;
		//dialogRef.componentInstance.modulo = 'matricula';
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				result.forEach((entryVal : any , entryKey : any )  => {
					entryVal.descripcionView = entryVal.nombres + ' ' + entryVal.apellidoPaterno + ' ' + entryVal.apellidoMaterno;
					this.frmGroup.get('titular').patchValue(entryVal, { onlySelf: true, emitEvent: false });
					this.frmGroup.get('titular.idPersonal').setValue(entryVal.idPersonal);

				});
			}
		});
	}
}