import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { NgForm, UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { bsAnimations } from '@ng-mf/shared/bs';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { AuthService, User, UserService } from '@ng-mf/shared/service/aas';
import { CommonService, EstadoGeneralState, ListaItemType } from '@ng-mf/shared/service/comun';
import { BsConfirmationService, SelectItemVO, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { debounceTime, distinctUntilChanged, Observable, takeUntil } from 'rxjs';
import { Apoderado } from '../../apoderado/apoderado.types';
import { ApoderadoFrmService } from "./apoderadofrm.service";

/**
 * La Class ApoderadoComponent.
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
	selector: 'ng-mf-bs-apoderadofrm',
	templateUrl: './apoderadofrm.component.html',
	styleUrls: ['./apoderadofrm.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class ApoderadoFrmComponent extends BaseComponent<Apoderado> implements OnInit, OnDestroy {

	@ViewChild('signInNgForm') signInNgForm!: NgForm;
	signInForm!: UntypedFormGroup;

	/** La lista nivel. */
	public listaTipoDoc: SelectItemVO[] | undefined = [];
	public listaNacionalidad: SelectItemVO[] | undefined = [];

	public listaDepartamento: SelectItemVO[] | undefined = [];
	public listaProvincia: SelectItemVO[] | undefined = [];
	public listaDistrito: SelectItemVO[] | undefined = [];

	public isRegistrar = true;

	user!: User;
	public isFrmLogin = false;
	public isFrmRegistrar = false;
	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		public _router: Router,
		public _activatedRoute: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: ApoderadoFrmService,
		public commonService: CommonService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService,
		public authService: AuthService,
		public userService: UserService,
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

		// Create the form
		this.signInForm = this.fb.group({
			email: ['', [Validators.required]],
			password: ['', Validators.required],
			rememberMe: ['']
		});

		this.userService.user$
			.pipe(takeUntil(this._unsubscribeAll))
			.subscribe((user: User) => {
				this.user = user;
				this.buscar();
			});
			this.isFrmLogin = !this.authService.authenticated;
			this.isFrmRegistrar = this.authService.authenticated;
	}

	// -----------------------------------------------------------------------------------------------------
	// @ Public methods
	// -----------------------------------------------------------------------------------------------------

	/**
	 * Sign in
	 */
	signIn(): void {
		// Return if the form is invalid
		if (this.signInForm.invalid) {
			return;
		}

		// Disable the form
		this.signInForm.disable();

		// Hide the alert
		this.showAlert = false;

		// Sign in
		this.authService.signIn(this.signInForm.value)
			.subscribe(
				() => {

					// Set the redirect url.
					// The '/signed-in-redirect' is a dummy url to catch the request and redirect the user
					// to the correct page after a successful sign in. This way, that url can be set via
					// routing file and we don't have to touch here.
					const redirectURL = this._activatedRoute.snapshot.queryParamMap.get('redirectURL') || '/admision-in-redirect';
					// Navigate to the redirect url
					this._router.navigateByUrl(redirectURL);

				},
				(response) => {

					// Re-enable the form
					this.signInForm.enable();

					// Reset the form
					this.signInNgForm.resetForm();

					// Set the alert
					this.alert = {
						type: 'error',
						message: 'Wrong email or password'
					};

					// Show the alert
					this.showAlert = true;
				}
			);
	}
	private crearFormulario(): void {
		this.frmGroup = this.fb.group({
			idApoderado: [0],
			codigo: [''],
			idItemByDocIdentidad: [0],
			nroDoc: [''],
			apellidoPaterno: [''],
			apellidoMaterno: [''],
			nombres: [''],
			idItemByEstadoCivil: [0],
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
			firma: [''],
			usuarioCreacion: [''],
			fechaCreacion: [null],
			usuarioModificacion: [''],
			fechaModificacion: [null],
			clave: [''],
			tipo: [2],
			estado: [''],
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
			this.service.paginador(0, 1, '', 'asc', '', this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						if (data.listaResultado != null 
							&& data.listaResultado.length > 0) {
							if (data?.listaResultado[0].idApoderado !== (this.user.codigoExterno??'')) {
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
							this.find(data.objetoResultado);
						}
					},
					error => {
						this.mostrarMensajeError(error);
					}
				);
			} else {
				this.service.modificar(this.frmGroup.value, this.frmGroup.value.idApoderado).subscribe(
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
	public find(obj: Apoderado) {
		try {
			this.frmGroup.patchValue(obj, { onlySelf: true, emitEvent: false });
			this.mostrarPanelForm = true;
			this.accionNuevo = false;

			this.frmGroup.get('idProvincia')?.setValue(obj.lugarNacimiento?.ubigeoByDependencia?.idUbigeo);
			this.frmGroup.get('idDepartamento')?.setValue(this.commonService.ubigeoMap.get(obj.lugarNacimiento?.ubigeoByDependencia?.idUbigeo));

			this.listaProvincia = this.commonService.generarProvincia(this.frmGroup?.get('idDepartamento')?.value);
			this.listaDistrito = this.commonService.generarDistrito(obj.lugarNacimiento?.ubigeoByDependencia?.idUbigeo);

			this.selectedData = obj;
			this._changeDetectorRef.markForCheck();

		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}

	/**
	 * Buscar.
	 *
	 */
	private buscar() {
		this.isLoading = true;
		this.service.buscarID(this.user.codigoExterno)
			.subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						// Mark for check
						this.isLoading = false;
						if (data.objetoResultado) {
							this.find(data.objetoResultado);
						}
						this._changeDetectorRef.markForCheck();
					}
				},
				error => {
					this.isLoading = false;
					this.mostrarMensajeError(error);
				});
	}

	/**
	  * cancelar.
	  *
	*/
	public cancelar() {
		this.isFrmLogin = true;
		this.isFrmRegistrar = false;
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

	public onClickRegistrar() {
		this.isFrmLogin = false;
		this.isFrmRegistrar = true;
		this._changeDetectorRef.markForCheck();
	}

}