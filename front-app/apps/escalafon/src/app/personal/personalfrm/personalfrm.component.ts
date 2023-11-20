import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnChanges, OnDestroy, OnInit, SimpleChanges, ViewEncapsulation } from '@angular/core';
import { UntypedFormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { bsAnimations } from '@ng-mf/shared/bs';
import { BaseComponent, SelectItemComponent } from '@ng-mf/shared/components/core';
import { AuthService, UserService } from '@ng-mf/shared/service/aas';
import { CommonService, EstadoGeneralState, ListaItemType } from '@ng-mf/shared/service/comun';
import { BsConfirmationService, SelectItemVO, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { debounceTime, distinctUntilChanged, Observable } from 'rxjs';
import { Personal } from '../personal.types';
import { PersonalFrmService } from "./personalfrm.service";

/**
 * La Class PersonalComponent.
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
	selector: 'ng-mf-bs-personalfrm',
	templateUrl: './personalfrm.component.html',
	styleUrls: ['./personalfrm.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class PersonalFrmComponent extends BaseComponent<Personal> implements OnInit, OnChanges, OnDestroy {

	/** La lista nivel. */
	public listaTipoDoc: SelectItemVO[] | undefined = [];
	public listaNacionalidad: SelectItemVO[] | undefined = [];
	public listaEstadoCivil: SelectItemVO[] | undefined = [];
	public listaPaisEmisor: SelectItemVO[] | undefined = [];

	public listaDepartamento: SelectItemVO[] | undefined = [];
	public listaProvincia: SelectItemVO[] | undefined = [];
	public listaDistrito: SelectItemVO[] | undefined = [];

	public isRegistrar = true;

	public listaSituacion: SelectItemVO[] | undefined = [];
	public listaAfp: SelectItemVO[] | undefined = [];
	public listaRegimenLaboral: SelectItemVO[] | undefined = [];
	public listaRegimenPensionario: SelectItemVO[] | undefined = [];
	public listaConvenioEvitarDobleTributacion: SelectItemVO[] | undefined = [];
	public listaCategoriaTrabajador: SelectItemVO[] | undefined = [];
	public listaEmpleadorDestacaPersonalTercero: SelectItemVO[] | undefined = [];

	public empresaMap: Map<any, string> = new Map<any, string>();

	public listaSctrSalud: SelectItemVO[] | undefined = [];
	public listaRegimenAsegSalud: SelectItemVO[] | undefined = [];
	public listaSctrPension: SelectItemVO[] | undefined = [];
	public listaEps: SelectItemVO[] | undefined = [];
	public listaServicoMedico: SelectItemVO[] | undefined = [];

	//
	public listaPeriocidadIngreso: SelectItemVO[] | undefined = [];
	public listaTipoPago: SelectItemVO[] | undefined = [];
	public listaSituacionEspecial: SelectItemVO[] | undefined = [];
	public listaCategoriaOcupacional: SelectItemVO[] | undefined = [];
	public listaTipoPension: SelectItemVO[] | undefined = [];
	public listaRegimenPensionarioPension: SelectItemVO[] | undefined = [];
	public listaTipoPagoPension: SelectItemVO[] | undefined = [];
	public listaTipoCentroFormacionProfesional: SelectItemVO[] | undefined = [];
	public listaTipoModalidadFormativa: SelectItemVO[] | undefined = [];

	//
	public listaSituacionEducativa: SelectItemVO[] | undefined = [];
	public listaCarrera: SelectItemVO[] | undefined = [];

	/** La lista Cargo. */
	public listaCargo: SelectItemVO[] | undefined = [];

	/** La lista Area. */
	public listaArea: SelectItemVO[] | undefined = [];

	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		public _router: Router,
		public _activatedRoute: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: PersonalFrmService,
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
		console.log("ngonchanges");
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

		if (this.data) {
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
			idPersonal: [null],
			codigoUnico: [''],
			estado: [''],
			idItemByDocIdentidad: [null],
			nroDoc: [''],
			apellidoPaterno: [''],
			apellidoMaterno: [''],
			nombres: [''],
			idItemByEstadoCivil: [null],
			fechaNacimiento: [null],
			telefono1: [''],
			telefono2: [''],
			celular: [''],
			email: [''],
			codigoControl: [''],
			nroTarjeta: [''],
			sexo: [''],
			idItemByPaisEmisor: [null],
			idDepartamento: [''],
			idProvincia: [''],
			idLugarNacimiento: [''],
			idItemByNacionalidad: [null],
			foto: [''],
			firma: [''],
			idItemByAfp: [null],
			idItemBySituacion: [null],
			fechaIngresospp: [null],
			fechaIngresoonp: [null],
			cuspp: [''],
			esComisionMixta: [''],
			aportaSenati: [''],
			idItemByRegimenLaboral: [null],
			idItemByRegimenPensionario: [null],
			esAfiliacionAseguraTuPension: [''],
			idItemByConvenioEvitarDobleTributacion: [null],
			rucCas: [''],
			autoGeneradoEssalud: [''],
			idItemByCategoriaTrabajador: [null],
			idEmpleadorDestacaPersonalTercero: [null],
			esSaludVida: [''],
			esTrabajadorSindicalizado: [''],
			sujetoControlnmediato: [''],
			regimenAlterAcumulaAtipico: [''],
			conAsignacionFamiliar: [''],
			sujetoHorarioNocturno: [''],
			jornadaTrabajoMaxima: [''],
			idItemBySctrSalud: [null],
			idItemByRegimenAsegSalud: [null],
			idItemBySctrPension: [null],
			idItemByEps: [null],
			idItemByServicoMedico: [null],
			idItemByPeriocidadIngreso: [null],
			idItemByTipoPago: [null],
			renta5taExoneradaEInafecta: [''],
			rentaQuintaManual: [''],
			idItemBySituacionEspecial: [null],
			idItemByCategoriaOcupacional: [null],
			idItemByOcupacion: [null],
			itemByOcupacionView: [''],
			idItemByOcupacionRegimenPublico: [null],
			itemByOcupacionRegimenPublicoView: [''],
			discapacidad: [''],
			esPencionista: [''],
			idItemByTipoPension: [null],
			idItemByRegimenPensionarioPension: [null],
			fechaInscripcionRegPensionario: [null],
			cusppPension: [''],
			idItemByTipoPagoPension: [null],
			informaOtrosIngreso5taCategoria: [''],
			esPracticante: [''],
			madreResponsaFamliar: [''],
			idItemByTipoCentroFormacionProfesional: [null],
			idItemByTipoModalidadFormativa: [null],
			vencimientoFotocheck: [null],
			idItemBySituacionEducativa: [null],
			esEducacionCompletaInsEducativaPeru: [''],
			carrera: this.fb.group({
				idCarrera: [null]
			}),
			anhoEgreso: [null],
			idEntidad: [''],
			sede: [''],
			idItemByCargo: [''],
			idItemByArea: [null],
			fechaIngreso: [null],
			fechaCese: [null],
			remuneracion: [null],
			asignacionFamiliar: [null],
			cantAfiliados: [null]
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
		paramsTemp = paramsTemp.set(ListaItemType.T3_TIPO_DOCUMENTO, 0);
		paramsTemp = paramsTemp.set(ListaItemType.T4_NACIONALIDAD, 0);
		paramsTemp = paramsTemp.set(ListaItemType.ESTADO_CIVIL, 0);
		paramsTemp = paramsTemp.set(ListaItemType.T26_PAIS_EMISOR_DEL_DOCUMENTO, 0);

		paramsTemp = paramsTemp.set(ListaItemType.T15_SITUACION, 0);
		paramsTemp = paramsTemp.set(ListaItemType.AFPS, 0);
		paramsTemp = paramsTemp.set(ListaItemType.T33_REGIMEN_LABORAL, 0);
		paramsTemp = paramsTemp.set(ListaItemType.T11_REGIMEN_PENSIONARIO, 0);
		paramsTemp = paramsTemp.set(ListaItemType.T25_CONVENIOS, 0);
		paramsTemp = paramsTemp.set(ListaItemType.T24_CATEGORIA_OCUPACIONAL, 0);
		paramsTemp = paramsTemp.set(ListaItemType.SCTR_SALUD, 0);
		paramsTemp = paramsTemp.set(ListaItemType.T32_REGIMEN_ASEGURAMIENTO_SALUD, 0);
		paramsTemp = paramsTemp.set(ListaItemType.SCTR_PENSION, 0);
		paramsTemp = paramsTemp.set(ListaItemType.T14_EPSSERV_PROPIOS, 0);
		paramsTemp = paramsTemp.set(ListaItemType.SERVICIO_MEDICO, 0);

		//
		paramsTemp = paramsTemp.set(ListaItemType.T16_TIPO_DE_PAGO, 0);
		paramsTemp = paramsTemp.set(ListaItemType.T35_SITUACION_ESPECIAL, 0);
		paramsTemp = paramsTemp.set(ListaItemType.T24_CATEGORIA_OCUPACIONAL, 0);
		paramsTemp = paramsTemp.set(ListaItemType.T10_OCUP_SPUB_PERS_Form, 0);
		paramsTemp = paramsTemp.set(ListaItemType.T10_OCUP_SPUB_PERS_Form, 0);
		paramsTemp = paramsTemp.set(ListaItemType.TIPO_PENSION, 0);
		paramsTemp = paramsTemp.set(ListaItemType.T11_REGIMEN_PENSIONARIO, 0);
		//

		paramsTemp = paramsTemp.set(ListaItemType.T13_PERIODICIDAD, 0);
		paramsTemp = paramsTemp.set(ListaItemType.T16_TIPO_DE_PAGO, 0);
		paramsTemp = paramsTemp.set(ListaItemType.TIPO_CENTRO_FORMACACION, 0);
		paramsTemp = paramsTemp.set(ListaItemType.T18_TIPO_DE_MODALIDAD_FORMATIVA, 0);


		//
		paramsTemp = paramsTemp.set(ListaItemType.T9_SITUACION_EDUCATIVA, 0);


		paramsTemp = paramsTemp.set(ListaItemType.CARGO, 0);
		paramsTemp = paramsTemp.set(ListaItemType.AREA, 0);

		await this.commonService.obtenerListaItemSelectItemMap(paramsTemp);
		this.listaTipoDoc = this.commonService.getListaItemSelectItem(ListaItemType.T3_TIPO_DOCUMENTO);
		this.listaNacionalidad = this.commonService.getListaItemSelectItem(ListaItemType.T4_NACIONALIDAD);

		this.listaEstadoCivil = this.commonService.getListaItemSelectItem(ListaItemType.ESTADO_CIVIL);
		this.listaPaisEmisor = this.commonService.getListaItemSelectItem(ListaItemType.T26_PAIS_EMISOR_DEL_DOCUMENTO);
		this.listaSituacion = this.commonService.getListaItemSelectItem(ListaItemType.T15_SITUACION);

		//
		this.listaAfp = this.commonService.getListaItemSelectItem(ListaItemType.AFPS);
		this.listaRegimenLaboral = this.commonService.getListaItemSelectItem(ListaItemType.T33_REGIMEN_LABORAL);
		this.listaRegimenPensionario = this.commonService.getListaItemSelectItem(ListaItemType.T11_REGIMEN_PENSIONARIO);
		this.listaConvenioEvitarDobleTributacion = this.commonService.getListaItemSelectItem(ListaItemType.T25_CONVENIOS);
		this.listaCategoriaTrabajador = this.commonService.getListaItemSelectItem(ListaItemType.T24_CATEGORIA_OCUPACIONAL);

		this.listaSctrSalud = this.commonService.getListaItemSelectItem(ListaItemType.SCTR_SALUD);
		this.listaRegimenAsegSalud = this.commonService.getListaItemSelectItem(ListaItemType.T32_REGIMEN_ASEGURAMIENTO_SALUD);
		this.listaSctrPension = this.commonService.getListaItemSelectItem(ListaItemType.SCTR_PENSION);
		this.listaEps = this.commonService.getListaItemSelectItem(ListaItemType.T14_EPSSERV_PROPIOS);
		this.listaServicoMedico = this.commonService.getListaItemSelectItem(ListaItemType.SERVICIO_MEDICO);

		//
		this.listaPeriocidadIngreso = this.commonService.getListaItemSelectItem(ListaItemType.T13_PERIODICIDAD);
		this.listaTipoPago = this.commonService.getListaItemSelectItem(ListaItemType.T16_TIPO_DE_PAGO);
		this.listaSituacionEspecial = this.commonService.getListaItemSelectItem(ListaItemType.T35_SITUACION_ESPECIAL);
		this.listaCategoriaOcupacional = this.commonService.getListaItemSelectItem(ListaItemType.T24_CATEGORIA_OCUPACIONAL);
		//this.listaOcupacion = this.commonService.getListaItemSelectItem(ListaItemType.T10_OCUP_SPUB_PERS_Form);
		//this.listaOcupacionRegimenPublico = this.commonService.getListaItemSelectItem(ListaItemType.T10_OCUP_SPUB_PERS_Form);
		this.listaTipoPension = this.commonService.getListaItemSelectItem(ListaItemType.TIPO_PENSION);
		this.listaRegimenPensionarioPension = this.commonService.getListaItemSelectItem(ListaItemType.T11_REGIMEN_PENSIONARIO);
		this.listaTipoPagoPension = this.commonService.getListaItemSelectItem(ListaItemType.T16_TIPO_DE_PAGO);
		this.listaTipoCentroFormacionProfesional = this.commonService.getListaItemSelectItem(ListaItemType.TIPO_CENTRO_FORMACACION);
		this.listaTipoModalidadFormativa = this.commonService.getListaItemSelectItem(ListaItemType.T18_TIPO_DE_MODALIDAD_FORMATIVA);

		//
		this.listaSituacionEducativa = this.commonService.getListaItemSelectItem(ListaItemType.T9_SITUACION_EDUCATIVA);

		this.listaCargo = this.commonService.getListaItemSelectItem(ListaItemType.CARGO);
		this.listaArea = this.commonService.getListaItemSelectItem(ListaItemType.AREA);


		paramsTemp = new Map<any, any>();
		paramsTemp = paramsTemp.set("empresa", 0);
		await this.commonService.obtenerListaSelectItemVOMap("pago", paramsTemp);
		this.listaEmpleadorDestacaPersonalTercero = this.commonService.getListaSelectVOItem("empresa");
		this.empresaMap = this.commonService.generarMap(this.listaEmpleadorDestacaPersonalTercero ?? []);
	}

	private onChange(): void {
		this.debounceTimeProcesarNroDoc()?.subscribe(term => { this.valueChangeNroDoc(term) });
	}
	public debounceTimeProcesarNroDoc(): Observable<any> | undefined {
		return this.frmGroup?.get('nroDoc')?.valueChanges?.pipe(debounceTime(500), distinctUntilChanged());
	}
	public valueChangeNroDoc(value: string) {
		this.isRegistrar = true;
		if (value != null && value.length >= 8) {
			this.params = this.params.set('nroDoc', value);
			this.service.paginador(0, 1, '', 'asc', '', this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						if (data.listaResultado != null
							&& data.listaResultado.length > 0) {
							if (data?.listaResultado[0].idPersonal !== (this.selectedData?.idPersonal ?? '')) {
								this.mostrarMensajeAdvertencia("El nro de Documento ya esta registrado " + value);
								this.isRegistrar = false;
							}
							// Mark for check
							this._changeDetectorRef.markForCheck();
						}
					}
				},
				error => {
					this.mostrarMensajeError(error);
				}
			);
		}

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
				this.service.crear(this.frmGroup.value).subscribe(
					data => {
						if (this.isProcesoOK(data)) {
							this.guardoExito();
							this.accionNuevo = false;
							this.changeEmitter.emit({ isNuevoGuardar: true, idPersonal: data.objetoResultado.idPersonal });
							this.find(data.objetoResultado);
						}
					},
					error => {
						this.mostrarMensajeError(error);
					}
				);
			} else {
				this.service.modificar(this.frmGroup.value, this.frmGroup.value.idPersonal).subscribe(
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
	public find(obj: Personal) {
		try {
			this.frmGroup.patchValue(obj, { onlySelf: true, emitEvent: false });
			this.mostrarPanelForm = true;
			this.accionNuevo = false;

			this.frmGroup.get('idProvincia')?.setValue(obj.lugarNacimiento?.ubigeoByDependencia?.idUbigeo);
			this.frmGroup.get('idDepartamento')?.setValue(this.commonService.ubigeoMap.get(obj.lugarNacimiento?.ubigeoByDependencia?.idUbigeo));

			this.listaProvincia = this.commonService.generarProvincia(this.frmGroup?.get('idDepartamento')?.value);
			this.listaDistrito = this.commonService.generarDistrito(obj.lugarNacimiento?.ubigeoByDependencia?.idUbigeo);
			this.selectedData = obj;

			this.frmGroup.get("itemByOcupacionRegimenPublicoView")?.setValue(obj.itemByOcupacionRegimenPublico?.nombre);
			this.frmGroup.get("itemByOcupacionView")?.setValue(obj.itemByOcupacion?.nombre);

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

	public abrirModalSelectItemItemOcupacion(pSearch?: string) {
		// Open the dialog
		const dialogRef = this.dialog.open(SelectItemComponent);
		dialogRef.componentInstance.titlePage = "Ocupacion";
		dialogRef.componentInstance.groupName = "item";
		dialogRef.componentInstance.search = pSearch ?? '';
		dialogRef.componentInstance.id = ListaItemType.T10_OCUP_SPUB_PERS_Form + "";
		dialogRef.afterClosed()
			.subscribe((result) => {
				if (result !== null && result.id && result.id !== null) {
					this.frmGroup.get("idItemByOcupacion")?.setValue(Number(result.id + ""));
					this.frmGroup.get("itemByOcupacionView")?.setValue(result.nombre);
					this._changeDetectorRef.markForCheck();
				}
			});
	}

	public abrirModalSelectItemItemOcupacionRegimenPublico(pSearch?: string) {
		// Open the dialog
		const dialogRef = this.dialog.open(SelectItemComponent);
		dialogRef.componentInstance.titlePage = "Ocupacion Regimen Publico";
		dialogRef.componentInstance.groupName = "item";
		dialogRef.componentInstance.search = pSearch ?? '';
		dialogRef.componentInstance.id = ListaItemType.T10_OCUP_SPUB_PERS_Form + "";
		dialogRef.afterClosed()
			.subscribe((result) => {
				if (result !== null && result.id && result.id !== null) {
					this.frmGroup.get("idItemByOcupacionRegimenPublico")?.setValue(Number(result.id + ""));
					this.frmGroup.get("itemByOcupacionRegimenPublicoView")?.setValue(result.nombre);
					this._changeDetectorRef.markForCheck();
				}
			});
	}
}