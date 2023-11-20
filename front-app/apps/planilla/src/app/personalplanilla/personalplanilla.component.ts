import {
	AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild, ViewEncapsulation
} from '@angular/core';
import { UntypedFormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatDrawer } from '@angular/material/sidenav';
import { ActivatedRoute, Router } from '@angular/router';
import { bsAnimations } from '@ng-mf/shared/bs';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { CommonService } from '@ng-mf/shared/service/comun';
import { BasePagination, BsConfirmationService, BsMediaWatcherService, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { merge, takeUntil } from 'rxjs';
import { PersonalPlanillaService } from "./personalplanilla.service";
import { Personal } from "./personalplanilla.types";


/**
 * La Class PersonalPlanillaComponent.
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
	selector: 'ng-mf-bs-personalplanilla',
	templateUrl: './personalplanilla.component.html',
	styleUrls: ['./personalplanilla.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class PersonalPlanillaComponent extends BaseComponent<Personal> implements OnInit, AfterViewInit, OnDestroy {



	@ViewChild('drawer') drawer!: MatDrawer;

	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		router: Router,
		route: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: PersonalPlanillaService,
		public commonService: CommonService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService,
		private _bsMediaWatcherService: BsMediaWatcherService
	) {
		super(_translocoService, _bsConfirmationService);
		this.debounceTimeProcesar().subscribe(term => { this.search = term; this.buscar() });
		this.SORT_NAME = 'nombres';
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
		this.onInit();
		this.inicializar();
		this.onInitPanels();
	}

	/**
	 * On init
	 */
	onInitPanels(): void {
		// Setup available panels
		//personal.frm.tab.direccion.trabajador.pnl.title
		this.panels = [
			{
				id: 'conceptotrabajador',
				icon: 'heroicons_outline:user-circle',
				title: 'Conceptos Fijos',
				description: 'Registrar Conceptos fijos al Personal'
			},
			{
				id: 'adelanto',
				icon: 'heroicons_outline:user-group',
				title: 'Adelanto',
				description: 'Registrar Adelanto al Personal'
			},
			{
				id: 'vacaciones',
				icon: 'heroicons_outline:user-group',
				title: 'Vacaciones',
				description: 'Registrar Vacaciones al Personal'
			},
			{
				id: 'informaotrosingreso5ta',
				icon: 'heroicons_outline:user-group',
				title: 'Informa Otros Ingreso 5ta',
				description: 'Informa otros ingresos 5ta al Personal'
			}

		];

		// Subscribe to media changes
		this._bsMediaWatcherService.onMediaChange$
			.pipe(takeUntil(this._unsubscribeAll))
			.subscribe(({ matchingAliases }) => {

				// Set the drawerMode and drawerOpened
				if (matchingAliases.includes('lg')) {
					this.drawerMode = 'side';
					this.drawerOpened = true;
				}
				else {
					this.drawerMode = 'over';
					this.drawerOpened = false;
				}

				// Mark for check
				this._changeDetectorRef.markForCheck();
			});
	}
	/**
		* Navigate to the panel
		*
		* @param panel
		*/
	goToPanel(panel: string): void {
		this.selectedPanel = panel;

		// Close the drawer on 'over' mode
		if (this.drawerMode === 'over') {
			this.drawer.close();
		}
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
				this.service.modificar(this.frmGroup.value, this.frmGroup.value.idPersonal).subscribe(
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
		this.mostrarPanelForm = true;
		this.accionNuevo = true;
		this.selectedData = null;
	}

	/**
	 * Inicializar.
	 *
	 */
	private inicializar() {
		try {
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
	public confirmarEliminar(obj: Personal) {
		const dialogRef = this.abrirModalConfirDialogEliminar();
		dialogRef.afterClosed().subscribe(result => {
			if (result === 'confirmed') {
				this.eliminar(obj.idPersonal);
			}
		});
	}

	/**
	 * find id
	 *
	 */
	public find(obj: Personal) {
		try {
			this.selectedData = obj;
			this.mostrarPanelForm = true;
			this.accionNuevo = false;
			this._changeDetectorRef.markForCheck();
		} catch (e) {
			this.mostrarMensajeError(e);
			this._changeDetectorRef.markForCheck();
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

	public changeEmitterEventCrear(event: any) {
		const isNuevoGuardar = event.isNuevoGuardar;
		const idPersonal = event.idPersonal;
		if (isNuevoGuardar === true && idPersonal) {
			if (this.selectedData && this.selectedData !== null) {
				this.selectedData.idPersonal = idPersonal;
				this._changeDetectorRef.markForCheck();
			}
		}
	}

}