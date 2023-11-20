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
import { CuentaBancariaPersonal } from '../cuentabancariapersonal.types';
import { CuentaBancariaPersonalService } from "./cuentabancariapersonal.service";

/**
 * La Class CuentaBancariaPersonalComponent.
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
	selector: 'ng-mf-bs-cuentabancariapersonal',
	templateUrl: './cuentabancariapersonal.component.html',
	styleUrls: ['./cuentabancariapersonal.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class CuentaBancariaPersonalComponent extends BaseComponent<CuentaBancariaPersonal> implements OnInit, OnChanges, OnDestroy {

	@Input()
	public esCts = '';

	public isRegistrar = true;

	/** La lista banco. */
	public listaBanco: SelectItemVO[] | undefined = [];

	/** La lista moneda. */
	public listaMoneda: SelectItemVO[] | undefined = [];

	/** La lista tipo cuenta. */
	public listaTipoCuenta: SelectItemVO[] | undefined = [];

	/** La lista tipo deposito cuenta. */
	public listaTipoDepositoCuenta: SelectItemVO[] | undefined = [];

	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		public _router: Router,
		public _activatedRoute: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: CuentaBancariaPersonalService,
		public commonService: CommonService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService,
		public authService: AuthService,
		public userService: UserService
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
		if (this.data != null && this.data.idCuentaBancariaPersonal
			&& this.data.idCuentaBancariaPersonal !== null) {
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
			idCuentaBancariaPersonal: [null],
			idItemByBanco: [null],
			nroCuenta: [null],
			nroCCI: [null],
			fechaApertura: [null],
			idItemByMoneda: [null],
			idItemByTipoCuenta: [null],
			modulo: [null],
			sucursal: [null],
			subCuenta: [null],
			idItemByTipoDepositoCuenta: [null],
			esCts: [this.esCts]
		});
		this.onChange();
	}

	public async cargarCombo() {
		let paramsTemp: Map<any, any> = new Map<any, any>();
		paramsTemp = paramsTemp.set(ListaItemType.TIPO_BANCO, 0);
		paramsTemp = paramsTemp.set(ListaItemType.TIPO_MONEDA, 0);
		paramsTemp = paramsTemp.set(ListaItemType.TIPO_CUENTA_BANCARIA, 0);
		paramsTemp = paramsTemp.set(ListaItemType.TIPO_DEPOSITO, 0);

		await this.commonService.obtenerListaItemSelectItemMap(paramsTemp);

		this.listaBanco = this.commonService.getListaItemSelectItem(ListaItemType.TIPO_BANCO);
		this.listaMoneda = this.commonService.getListaItemSelectItem(ListaItemType.TIPO_MONEDA);
		this.listaTipoCuenta = this.commonService.getListaItemSelectItem(ListaItemType.TIPO_CUENTA_BANCARIA);
		this.listaTipoDepositoCuenta = this.commonService.getListaItemSelectItem(ListaItemType.TIPO_DEPOSITO);
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
				this.service.modificarPer(this.id, this.frmGroup.value, this.frmGroup.value.idCuentaBancariaPersonal).subscribe(
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
	public find(obj: CuentaBancariaPersonal) {
		try {
			this.frmGroup.patchValue(obj, { onlySelf: true, emitEvent: false });
			this.mostrarPanelForm = true;
			this.accionNuevo = false;
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

}