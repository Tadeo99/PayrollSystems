import {
	ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnChanges,
	OnDestroy, OnInit, SimpleChanges, ViewEncapsulation
} from '@angular/core';
import { UntypedFormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { bsAnimations } from '@ng-mf/shared/bs';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { AuthService, UserService } from '@ng-mf/shared/service/aas';
import { CommonService, EstadoGeneralState, ListaItemType } from '@ng-mf/shared/service/comun';
import { BsConfirmationService, SelectItemVO, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { DireccionPersonal } from '../direccionpersonal.types';
import { DireccionPersonalService } from "./direccionpersonal.service";

/**
 * La Class DireccionPersonalComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Wed Oct 21 21:47:02 COT 2020
 * @since BUILDERP-CORE 2.1
 */

@Component({
	selector: 'ng-mf-bs-direccionpersonal',
	templateUrl: './direccionpersonal.component.html',
	styleUrls: ['./direccionpersonal.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class DireccionPersonalComponent extends BaseComponent<DireccionPersonal> implements OnInit, OnChanges, OnDestroy {

	@Input()
	public domiciliado = '';

	/** La lista nivel. */
	public listaDepartamento: SelectItemVO[] | undefined = [];
	public listaProvincia: SelectItemVO[] | undefined = [];
	public listaDistrito: SelectItemVO[] | undefined = [];

	public isRegistrar = true;

	/** La lista tipo via. */
	public listaTipoVia: SelectItemVO[] | undefined = [];

	/** La lista zona. */
	public listaZona: SelectItemVO[] | undefined = [];

	/** La lista zona. */
	public listaProcedencia: SelectItemVO[] | undefined = [];

	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		public _router: Router,
		public _activatedRoute: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: DireccionPersonalService,
		public commonService: CommonService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService,
		public authService: AuthService,
		public userService: UserService,
		private _matDialog: MatDialog
	) {
		super(_translocoService, _bsConfirmationService);
	}

	ngOnChanges(changes: SimpleChanges) {
		this.crearFormulario();
	}


	/**
	 * On destroy
	 */
	override ngOnDestroy(): void {
		// Unsubscribe from all subscriptions
		this._unsubscribeAll.next(null);
		this._unsubscribeAll.complete();
	}

	async ngOnInit() {
		this.onInit();
		await this.inicializar();
		if (this.data != null && this.data.idDireccionPersonal
			&& this.data.idDireccionPersonal !== null) {
			this.find(this.data);
		} else {
			this.nuevo();
		}
	}

	// -----------------------------------------------------------------------------------------------------
	// @ Public methods
	// -----------------------------------------------------------------------------------------------------


	private crearFormulario(): void {
		this.frmGroup = this.fb.group({
			idDireccionPersonal: [null],
			domiciliado: [this.domiciliado],
			direcionCentroAsistencialEssalud: [null],
			idItemByTipoVia: [null],
			nombreTipoVia: [''],
			idItemByZona: [null],
			nombreZona: [''],
			numero: [null],
			interior: [null],
			departamento: [null],
			block: [null],
			mazana: [''],
			lote: [''],
			etapa: [''],
			kilometro: [null],
			referencia: [''],
			idItemByProcedenciaDireccion: [null],
			idDepartamento: [''],
			idProvincia: [''],
			idUbigeo: ['']
		});
		this.onChange();
	}

	public async cargarCombo() {
		let paramsTemp: Map<any, any> = new Map<any, any>();
		paramsTemp = paramsTemp.set("departamento", 0);
		paramsTemp = paramsTemp.set("provincia", 0);
		paramsTemp = paramsTemp.set("distrito", 0);
		await this.commonService.obtenerListaSelectItemVOMap("common", paramsTemp);
		this.listaDepartamento = this.commonService.getListaSelectVOItem("departamento");
		this.commonService.generarUbigeoMap();

		paramsTemp = new Map<any, any>();
		paramsTemp = paramsTemp.set(ListaItemType.T5_VIA, 0);
		paramsTemp = paramsTemp.set(ListaItemType.T6_ZONA, 0);
		paramsTemp = paramsTemp.set(ListaItemType.PROCEDENCIA_DIRECCION, 0);
		await this.commonService.obtenerListaItemSelectItemMap(paramsTemp);

		this.listaTipoVia = this.commonService.getListaItemSelectItem(ListaItemType.T5_VIA);
		this.listaZona = this.commonService.getListaItemSelectItem(ListaItemType.T6_ZONA);
		this.listaProcedencia = this.commonService.getListaItemSelectItem(ListaItemType.PROCEDENCIA_DIRECCION);
	}

	private onChange(): void {
		//this.debounceTimeProcesarNroDoc()?.subscribe(term => { this.valueChangeNroDoc(term) });
	}

	onInit() {
		//
	}

	/**
	 * guardar.
	 *
	 */
	public guardar() {
		if (this.frmGroup.invalid || !this.isRegistrar) {
			this.mostrarMensajeErrorFrmInvalid();
			return;
		}
		try {
			if (this.accionNuevo) {
				this.service.crearPer(this.id, this.frmGroup.value).subscribe(
					data => {
						if (this.isProcesoOK(data)) {
							this.guardoExito();
							this.accionNuevo = false;
							this.find(data.objetoResultado);
						}
					},
					error => {
						this.mostrarMensajeError(error);
					}
				);
			} else {
				this.service.modificarPer(this.id, this.frmGroup.value, this.frmGroup.value.idDireccionPersonal).subscribe(
					data => {
						if (this.isProcesoOK(data)) {
							this.actualizadoExito();
							this.find(data.objetoResultado);
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
		this.crearFormulario();
		this.frmGroup.get("estado")?.setValue(EstadoGeneralState.ACTIVO.toString());
		this.mostrarPanelForm = true;
		this.accionNuevo = true;
		this._changeDetectorRef.markForCheck();
	}

	/**
	 * Inicializar.
	 *
	 */
	private async inicializar() {
		try {
			await this.cargarCombo();
			//this.buscar();
		} catch (e) {
			this.mostrarMensajeError(e);
		}

	}

	/**
	 * find id
	 *
	 */
	public find(obj: DireccionPersonal) {
		try {
			this.frmGroup.patchValue(obj, { onlySelf: true, emitEvent: false });
			this.mostrarPanelForm = true;
			this.accionNuevo = false;

			this.frmGroup.get('idProvincia')?.setValue(obj.ubigeo?.ubigeoByDependencia?.idUbigeo);
			this.frmGroup.get('idDepartamento')?.setValue(this.commonService.ubigeoMap.get(obj.ubigeo?.ubigeoByDependencia?.idUbigeo));

			this.listaProvincia = this.commonService.generarProvincia(this.frmGroup?.get('idDepartamento')?.value);
			this.listaDistrito = this.commonService.generarDistrito(obj.ubigeo?.ubigeoByDependencia?.idUbigeo);
			this.selectedData = obj;


			this._changeDetectorRef.markForCheck();

		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}


	/**
	  * cancelar.
	  *
	*/
	public cancelar() {
		this.mostrarPanelForm = false;
		this._changeDetectorRef.markForCheck();
	}
	public changeEmitterEvent(event: any) {
		const isNuevo = event.isNuevo;
		if (isNuevo === true) {
			this.nuevo();
		}
	}

	public onDepartamentoChange(ob: any) {
		const selected = ob.value;
		this.listaProvincia = this.commonService.generarProvincia(selected);
	}

	public onProvinciaChange(ob: any) {
		const selected = ob.value;
		this.listaDistrito = this.commonService.generarDistrito(selected);
	}
}