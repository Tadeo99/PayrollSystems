import {
	Component, OnInit, ChangeDetectorRef,
	AfterViewInit, OnDestroy, ViewEncapsulation,
	ChangeDetectionStrategy
} from '@angular/core';
import { UntypedFormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';

import { MatDialog } from '@angular/material/dialog';

import { GrupoUsuarioService } from "./grupousuario.service";
import { GrupoUsuario } from "./grupousuario.types";
import { TranslocoService } from '@ngneat/transloco';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { BasePagination, BsConfirmationService, SelectItemVO, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { CommonService, EstadoGeneralState } from '@ng-mf/shared/service/comun';
import { merge, takeUntil } from 'rxjs';
import { bsAnimations, labelColorDefs } from '@ng-mf/shared/bs';
import { MatButtonToggleChange } from '@angular/material/button-toggle';
/**
 * La Class GrupoUsuarioComponent.
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
	selector: 'ng-mf-bs-grupousuario',
	templateUrl: './grupousuario.component.html',
	styleUrls: ['./grupousuario.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class GrupoUsuarioComponent extends BaseComponent<GrupoUsuario> implements OnInit, AfterViewInit, OnDestroy {

	public listaMenu?: SelectItemVO[] = [];
	public listaMenuSelect?: SelectItemVO[] = [];
	public listaSistema: SelectItemVO[] | undefined = [];
	public menus: any[] = [];
	numberOfMenus: any = {};
	selectedFilter: SelectItemVO | undefined;

	public listaMenuMap: Map<number, SelectItemVO[]> = new Map<number, SelectItemVO[]>();

	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		router: Router,
		route: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: GrupoUsuarioService,
		public commonService: CommonService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService
	) {
		super(_translocoService, _bsConfirmationService);

		this.debounceTimeProcesar().subscribe(term => { this.search = term; this.buscar() });
		this.SORT_NAME = 'descripcion';
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
				});;
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

	/**
	* On filter change
	*
	* @param change
	*/
	onFilterChange(toggleChange: MatButtonToggleChange): void {
		// Set the filter
		this.selectedFilter = toggleChange.value;
		this.generarListaMenuSelect();
	}
	private generarListaMenuSelect() {
		this.listaMenu = [];
		this.listaMenuSelect = [];
		if (this.listaMenuMap !== null && this.listaMenuMap.has(this.selectedFilter?.id)) {
			this.listaMenu = this.listaMenuMap?.get(this.selectedFilter?.id);
		}
		this.listaMenu?.forEach((data) => {
			if (this.menus.includes(data.id)) {
				this.listaMenuSelect?.push(data);
			}
		});
	}
	private crearFormulario(): void {
		this.frmGroup = this.fb.group({
			idGrupoUsuario: [0],
			descripcion: [''],
			estado: [''],
			menus: [[]]
		});
		this.onChange();
	}

	public async cargarCombo() {
		let paramsTemp: Map<any, any> = new Map<any, any>();
		paramsTemp = paramsTemp.set("menu", 0);
		paramsTemp = paramsTemp.set("sistema", 0);
		await this.commonService.obtenerListaSelectItemVOMap(this.service.modulo, paramsTemp);
		const listaMenuAll = this.commonService.getListaSelectVOItem("menu");
		this.listaSistema = this.commonService.getListaSelectVOItem("sistema");

		const countSistemaMap: Map<number, number> = new Map<number, number>();

		listaMenuAll?.forEach((data) => {
			const key = parseInt(data.descripcion);
			if (!countSistemaMap.has(key)) {
				this.numberOfMenus[key] = 1;
				countSistemaMap.set(key, 1);
				const value: SelectItemVO[] = [];
				value.push(data);
				this.listaMenuMap.set(key, value);
			} else {
				let cantidad = countSistemaMap.get(key);
				if (cantidad) {
					cantidad = cantidad + 1;
					countSistemaMap.set(key, cantidad);
					this.numberOfMenus[key] = cantidad;
					const value = this.listaMenuMap.get(key);
					if (value) {
						value?.push(data);
						this.listaMenuMap.set(key, value);
					}

				}
			}

		});
		//console.log("numberOfCards " + this.numberOfCards);

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
			this.frmGroup.get("menus")?.setValue(this.menus);
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
				this.service.modificar(this.frmGroup.value, this.frmGroup.value.idGrupoUsuario).subscribe(
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
		this.frmGroup.get("estado")?.setValue(EstadoGeneralState.ACTIVO.toString());
		this.mostrarPanelForm = true;
		this.accionNuevo = true;
		this.listaMenu = [];
		this.listaMenuSelect = [];
		this.menus = [];
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
	public confirmarEliminar(obj: GrupoUsuario) {
		const dialogRef = this.abrirModalConfirDialogEliminar();
		dialogRef.afterClosed().subscribe(result => {
			if (result === 'confirmed') {
				this.eliminar(obj.idGrupoUsuario);
			}
		});
	}

	/**
	 * find id
	 *
	 */
	public find(obj: GrupoUsuario) {
		try {
			this.listaMenu = [];
			this.listaMenuSelect = [];
			this.menus = [];
			this.service.buscarID(obj.idGrupoUsuario).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.frmGroup.patchValue(obj, { onlySelf: true, emitEvent: false });
						this.mostrarPanelForm = true;
						this.accionNuevo = false;
						this.menus = data.objetoResultado.menus;
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
	togglegMenuLabel(label: SelectItemVO): void {
		// Update the mail object
		if (this.menus?.includes(label.id)) {
			// Delete the label
			this.menus?.splice(this.menus?.indexOf(label.id), 1);
		}
		else {
			// Add the label
			this.menus?.push(label.id);
		}
		this.generarListaMenuSelect();
	}

}