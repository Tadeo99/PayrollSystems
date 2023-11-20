import {
	Component, OnInit, ChangeDetectorRef,
	AfterViewInit, OnDestroy, ViewEncapsulation,
	ChangeDetectionStrategy
} from '@angular/core';
import { UntypedFormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

import { MatDialog } from '@angular/material/dialog';

import { UsuarioService } from "./usuario.service";
import { Usuario } from "./usuario.types";
import { TranslocoService } from '@ngneat/transloco';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { BasePagination, BsConfirmationService, SelectItemVO, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { CommonService, EstadoGeneralState } from '@ng-mf/shared/service/comun';
import { merge, takeUntil } from 'rxjs';
import { bsAnimations, labelColorDefs } from '@ng-mf/shared/bs';

/**
 * La Class UsuarioComponent.
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
	selector: 'ng-mf-bs-usuario',
	templateUrl: './usuario.component.html',
	styleUrls: ['./usuario.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class UsuarioComponent extends BaseComponent<Usuario> implements OnInit, AfterViewInit, OnDestroy {

	/** La lista tipo usuario. */
	public listaTipoUsuario: SelectItemVO[] | undefined = [];
	public listaGrupoUsuario: SelectItemVO[] | undefined = [];
	public listaEntidad: SelectItemVO[] | undefined = [];

	public grupoUsuarios: any[] = [];
	public entidades: any[] = [];
	

	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		router: Router,
		route: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: UsuarioService,
		public commonService: CommonService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService
	) {
		super(_translocoService, _bsConfirmationService);
		this.debounceTimeProcesar().subscribe(term => { this.search = term; this.buscar() });
		this.SORT_NAME = 'nombre';
	}

	/**
	 * After view init
	 */
	ngAfterViewInit(): void {
		if (this._sort && this._paginator) {
			// Set the initial sort
			this._sort.sort({
				id: this.SORT_NAME,
				start: this.SORT_DIR,
				disableClear: true
			});
			// Mark for check
			this._changeDetectorRef.markForCheck();
			// If the user changes the sort order...
			this._sort.sortChange
				.pipe(takeUntil(this._unsubscribeAll))
				.subscribe(() => {
					// Reset back to the first page
					this._paginator.pageIndex = 0;
				});

			// Get products if sort or page changes
			merge(this._sort.sortChange, this._paginator.page).pipe(takeUntil(this._unsubscribeAll))
				.subscribe(() => {
					this.buscar();
				});
		}
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
		// Get the label colors
		this.labelColors = labelColorDefs;

		this.onInit();
		this.inicializar();
		this.crearFormulario();
	}

	private crearFormulario(): void {
		this.frmGroup = this.fb.group({
			idUsuario: [0],
			nombre: [''],
			apellidoPaterno: [''],
			apellidoMaterno: [''],
			email: [''],
			telefono: [''],
			celular: [''],
			userName: [''],
			userPassword: [''],
			tipoUsuario: this.fb.group({
				idTipoUsuario: [0]
			}),
			codigoExterno: [''],
			estado: [''],
			grupoUsuarios: [[]],
			entidades: [[]]
		});
		this.onChange();
	}

	public async cargarCombo() {
		let paramsTemp: Map<any, any> = new Map<any, any>();
		paramsTemp = paramsTemp.set("tipoUsuario", 0);
		paramsTemp = paramsTemp.set("grupoUsuario", 0);
		paramsTemp = paramsTemp.set("entidad", 0);
		await this.commonService.obtenerListaSelectItemVOMap(this.service.modulo, paramsTemp);
		this.listaTipoUsuario = this.commonService.getListaSelectVOItem("tipoUsuario");
		this.listaGrupoUsuario = this.commonService.getListaSelectVOItem("grupoUsuario");
		this.listaEntidad = this.commonService.getListaSelectVOItem("entidad");
	}

	private onChange(): void {
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});
	}

	onInit() {
		// Get the pagination
		this.service.pagination$
			.pipe(takeUntil(this._unsubscribeAll))
			.subscribe((pagination: BasePagination | null) => {
				// Update the pagination
				this.pagination = pagination;
				// Mark for check
				this._changeDetectorRef.markForCheck();
			});
		// Get the listaData
		this.listaData$ = this.service.listaData$;
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
			this.frmGroup.get("grupoUsuarios")?.setValue(this.grupoUsuarios);
			this.frmGroup.get("entidades")?.setValue(this.entidades);
			if (this.accionNuevo) {
				this.service.crear(this.frmGroup.value).subscribe(
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
				this.service.modificar(this.frmGroup.value, this.frmGroup.value.idUsuario).subscribe(
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
		this.crearFormulario();
		this.grupoUsuarios = [];
		this.entidades = [];
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
			this.service.eliminar(id).subscribe(
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
	public confirmarEliminar(obj: Usuario) {
		const dialogRef = this.abrirModalConfirDialogEliminar();
		dialogRef.afterClosed().subscribe(result => {
			if (result === 'confirmed') {
				this.eliminar(obj.idUsuario);
			}
		});
	}

	/**
	 * find id
	 *
	 */
	public find(obj: Usuario) {
		try {
			this.service.buscarID(obj.idUsuario).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.frmGroup.patchValue(obj, { onlySelf: true, emitEvent: false });
						this.mostrarPanelForm = true;
						this.accionNuevo = false;
						this.grupoUsuarios = data.objetoResultado.grupoUsuarios;
						this.entidades = data.objetoResultado.entidades;
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
	 * Buscar.
	 *
	 */
	public buscar() {
		this.isLoading = true;
		this.service.paginador(
			this.nvl(this._paginator?.pageIndex, 0),
			this.nvl(this._paginator?.pageSize, 10),
			this.nvl(this._sort?.active, this.SORT_NAME),
			this.nvl(this._sort?.direction, this.SORT_DIR),
			this.search, this.params)
			.subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						// Mark for check
						this.isLoading = false;
						this.mostrarPanelForm = false;
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
		this.mostrarPanelForm = false;
	}
	public changeEmitterEvent(event: any) {
		const isNuevo = event.isNuevo;
		if (isNuevo === true) {
			this.nuevo();
		}
	}

	/**
	* Toggle label
	*
	* @param label
	*/
	togglegGrupoUsuarioLabel(label: SelectItemVO): void {
		// Update the mail object
		if (this.grupoUsuarios?.includes(label.id)) {
			// Delete the label
			this.grupoUsuarios?.splice(this.grupoUsuarios?.indexOf(label.id), 1);
		}
		else {
			// Add the label
			this.grupoUsuarios?.push(label.id);
		}
	}

	/**
	* Toggle label
	*
	* @param label
	*/
	togglegEntidadLabel(label: SelectItemVO): void {
		// Update the mail object
		if (this.entidades?.includes(label.id)) {
			// Delete the label
			this.entidades?.splice(this.entidades?.indexOf(label.id), 1);
		}
		else {
			// Add the label
			this.entidades?.push(label.id);
		}
	}


}