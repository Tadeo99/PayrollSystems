import {
	ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnDestroy, OnInit, ViewEncapsulation
} from '@angular/core';
import { UntypedFormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { bsAnimations } from '@ng-mf/shared/bs';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { User, UserService } from '@ng-mf/shared/service/aas';
import { CommonService, EstadoGeneralState, ListaItemType } from '@ng-mf/shared/service/comun';
import { BsConfirmationService, SelectItemVO, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { debounceTime, distinctUntilChanged, Observable, Subject, takeUntil } from 'rxjs';
import { AsignaPostulante } from '../../postulante/asignapostulante.types';
import { Postulante } from '../../postulante/postulante.types';
import { PostulanteFrmService } from "./postulantefrm.service";


/**
 * La Class PostulanteFrmComponent.
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
	selector: 'ng-mf-bs-postulantefrm',
	templateUrl: './postulantefrm.component.html',
	styleUrls: ['./postulantefrm.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class PostulanteFrmComponent extends BaseComponent<Postulante> implements OnInit, OnDestroy {

	/** La lista nivel. */
	public listaTipoDoc: SelectItemVO[] | undefined = [];
	public listaNacionalidad: SelectItemVO[] | undefined = [];

	public listaDepartamento: SelectItemVO[] | undefined = [];
	public listaProvincia: SelectItemVO[] | undefined = [];
	public listaDistrito: SelectItemVO[] | undefined = [];

	public atribute = 'foto';

	public name?: string;

	public avatar?: string | undefined | null;
	public image = '';

	avatarChanged: Subject<string> = new Subject<string>();

	public isRegistrar = true;

	@Input()
	public asignaPostulante!: AsignaPostulante;

	user!: User;

	public listaPostulanteMap: Map<string, string> = new Map<string, string>();

	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		router: Router,
		route: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: PostulanteFrmService,
		public commonService: CommonService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService,
		public userService: UserService
	) {
		super(_translocoService, _bsConfirmationService);
	}



	/**
	 * On destroy
	 */
	override ngOnDestroy(): void {
		// Unsubscribe from all subscriptions
		this._unsubscribeAll.next(null);
		this._unsubscribeAll.complete();
	}

	ngOnInit() {
		this.onInit();
		this.inicializar();
		this.crearFormulario();
		this.accionNuevo = true;

		this.userService.user$
			.pipe(takeUntil(this._unsubscribeAll))
			.subscribe((user: User) => {
				this.user = user;
			});
	}

	private crearFormulario(): void {
		this.frmGroup = this.fb.group({
			idPostulante: [0],
			codigo: [''],
			idItemByDocIdentidad: [0],
			nroDoc: [''],
			apellidoPaterno: [''],
			apellidoMaterno: [''],
			nombres: [''],
			fechaNacimiento: [null],
			telefono1: [''],
			telefono2: [''],
			celular: [''],
			email: [''],
			sexo: [''],
			idDepartamento: [''],
			idProvincia: [''],
			idLugarNacimiento: [''],
			idItemByNacionalidad: [0],
			foto: [''],
			usuarioCreacion: [''],
			fechaCreacion: [null],
			usuarioModificacion: [''],
			fechaModificacion: [null],
			tipo: [2],
			estado: [''],
			asignaPostulante: this.fb.group({
				grado: this.fb.group({
					idItemByNivel: [0],
					idGrado: [0],
					itemByNivel: this.fb.group({
						nombre: ['']
					}),
				}),
				postulante: this.fb.group({
					nombres: ['']
				}),
				apoderado: [''],
				anho: [0],
				periodo: ['']
			}),
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
		await this.commonService.obtenerListaItemSelectItemMap(paramsTemp);
		this.listaTipoDoc = this.commonService.getListaItemSelectItem(ListaItemType.T3_TIPO_DOCUMENTO);
		this.listaNacionalidad = this.commonService.getListaItemSelectItem(ListaItemType.T4_NACIONALIDAD);

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
			this.service.get('admision/postulante/asignacion', this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						if (data.listaResultado != null
							&& data.listaResultado.length > 0) {
							const obj: any = data.listaResultado[0];
							if (obj.apoderado !== (this.user.codigoExterno ?? '')) {
								this.mostrarMensajeAdvertencia("El nroDoc " + value + ", ya esta registrado con otro apoderado...");
								this.isRegistrar = false;
							} else {
								this.find(obj.postulante);
							}
							// Mark for check
							this._changeDetectorRef.markForCheck();
						} else {
							if (this.listaPostulanteMap.has(value)) {
								//
							}
						}
					}
				},
				error => {
					this.mostrarMensajeError(error);
				},
				() => {
					this.listaPostulanteMap.set(this.frmGroup.get("nroDoc")?.value, this.frmGroup.get("idPostulante")?.value);
					this._changeDetectorRef.markForCheck();
				},
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
		if (this.frmGroup.invalid) {
			this.mostrarMensajeErrorFrmInvalid();
			return;
		}
		try {
			if (this.accionNuevo) {
				if (this.asignaPostulante) {
					this.asignaPostulante.apoderado = this.user.codigoExterno ?? '';
				}
				this.frmGroup.get("asignaPostulante.grado.idGrado")?.setValue(this.asignaPostulante.grado.idGrado);
				this.frmGroup.get("asignaPostulante.periodo")?.setValue(this.asignaPostulante.periodo);
				this.frmGroup.get("asignaPostulante.sede")?.setValue(this.asignaPostulante.sede);
				this.frmGroup.get("asignaPostulante.anho")?.setValue(this.asignaPostulante.anho);
				this.frmGroup.get("asignaPostulante.apoderado")?.setValue(this.asignaPostulante.apoderado);
				this.service.crear(this.frmGroup.value).subscribe(
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
				this.service.modificar(this.frmGroup.value, this.frmGroup.value.idPostulante).subscribe(
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
	}

	/**
	 * Inicializar.
	 *
	 */
	private inicializar() {
		try {
			this.cargarCombo();
			//this.buscar();
		} catch (e) {
			this.mostrarMensajeError(e);
		}

	}


	/**
	 * find id
	 *
	 */
	public find(obj: Postulante) {
		try {
			this.frmGroup.patchValue(obj, { onlySelf: true, emitEvent: false });
			this.mostrarPanelForm = true;
			this.accionNuevo = false;

			this.frmGroup.get('idProvincia')?.setValue(obj.lugarNacimiento?.ubigeoByDependencia?.idUbigeo);
			this.frmGroup.get('idDepartamento')?.setValue(this.commonService.ubigeoMap.get(obj.lugarNacimiento?.ubigeoByDependencia?.idUbigeo));

			this.listaProvincia = this.commonService.generarProvincia(this.frmGroup?.get('idDepartamento')?.value);
			this.listaDistrito = this.commonService.generarDistrito(obj.lugarNacimiento?.ubigeoByDependencia?.idUbigeo);
			this.selectedData = obj;
			this.name = obj.nombres;
			this.listaPostulanteMap.set(this.frmGroup.get("nroDoc")?.value, this.frmGroup.get("idPostulante")?.value);
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