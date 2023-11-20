import {
	AfterViewInit,
	ChangeDetectionStrategy,
	ChangeDetectorRef,
	Component,
	OnDestroy,
	OnInit,
	ViewEncapsulation
} from '@angular/core';
import { FormControl, UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { MatDialog } from '@angular/material/dialog';

import { bsAnimations } from '@ng-mf/shared/bs';
import { BaseComponent, SelectItemComponent } from '@ng-mf/shared/components/core';
import { CommonService, EstadoGeneralState } from '@ng-mf/shared/service/comun';
import { BasePagination, BsConfirmationService, SelectItemVO, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { merge, takeUntil } from 'rxjs';
import { Vacaciones } from '../personalplanilla/vacaciones/vacaciones.types';
import { VacacionesPersonalService } from "./vacacionespersonal.service";
/**
 * La Class VacacionesPersonalComponent.
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
	selector: 'ng-mf-bs-vacacionespersonal',
	templateUrl: './vacacionespersonal.component.html',
	styleUrls: ['./vacacionespersonal.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class VacacionesPersonalComponent extends BaseComponent<Vacaciones> implements OnInit, AfterViewInit, OnDestroy {

	selected = new FormControl(0);
	disableSelect = new FormControl(true);

	idAnhio = new FormControl("");
	private idAnhioActivo = "";
	public listaAnhio: SelectItemVO[] | undefined = [];


	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		router: Router,
		route: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: VacacionesPersonalService,
		public commonService: CommonService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService
	) {
		super(_translocoService, _bsConfirmationService);

		this.debounceTimeProcesar().subscribe(term => { this.search = term; this.buscar() });
		this.SORT_NAME = 'idPersonal';
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
		this.crearFormulario();
	}

	private crearFormulario(): void {
		this.frmGroup = this.fb.group({
			idVacaciones: [null],
			idPersonal: [null],
			fechaInicio: [null],
			fechaFin: [null],
			idAnhio: [null],
			fechaReg: [null],
			fechaAprobacion: [null],
			idpersonalAprob: [null],
			estado: [''],
			observacion: [''],
			descripcionView: [null]
		});
		this.onChange();
	}
	public async cargarCombo() {
		let paramsTemp: Map<any, any> = new Map<any, any>();
		paramsTemp = paramsTemp.set("anhio", 0);
		await this.commonService.obtenerListaSelectItemVOMap("common", paramsTemp);
		this.listaAnhio = this.commonService.getListaSelectVOItem("anhio");
		this.listaAnhio?.forEach((data) => {
			if (data.descripcion === EstadoGeneralState.ACTIVO.toString()) {
				this.idAnhioActivo = data.id;
			}
		});
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
				this.service.crearPer(this.id, this.frmGroup.value).subscribe(
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
				this.service.modificarPer(this.id, this.frmGroup.value, this.frmGroup.value.idAsistenciaPersonal).subscribe(
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
		this.frmGroup.get("idAnhio")?.setValue(this.idAnhio);

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
			this.service.eliminarPer(this.id, id).subscribe(
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
	public confirmarEliminar(obj: Vacaciones) {
		const dialogRef = this.abrirModalConfirDialogEliminar();
		dialogRef.afterClosed().subscribe(result => {
			if (result === 'confirmed') {
				this.eliminar(obj.idVacaciones);
			}
		});
	}

	/**
	 * find id
	 *
	 */
	public find(obj: Vacaciones) {
		try {
			this.id = obj.idPersonal;
			this.frmGroup.patchValue(obj, { onlySelf: true, emitEvent: false });
			this.frmGroup.get("descripcionView")?.setValue(obj.personal.nombres ?? '');
			this.mostrarPanelForm = true;
			this.accionNuevo = false;
			// Mark for check
			this._changeDetectorRef.markForCheck();
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
		this.params = this.params.set('idAnhio', this.idAnhio.value ?? '');
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

	public async onAnhioChange(ob: any) {
		this.idAnhio.setValue(ob.value);
		this.buscar();
	}

	public abrirModalSelectItemPersonal(pSearch?: string) {
		// Open the dialog
		const dialogRef = this.dialog.open(SelectItemComponent);
		dialogRef.componentInstance.titlePage = "Personal";
		dialogRef.componentInstance.groupName = "personal";
		dialogRef.componentInstance.search = pSearch ?? '';
		dialogRef.componentInstance.sortName = 'nombres';
		dialogRef.componentInstance.modulo = 'rrhh_escalafon';
		dialogRef.afterClosed()
			.subscribe((result) => {
				if (result !== null && result.id && result.id !== null) {
					this.frmGroup.get("idPersonal")?.setValue(result.id);
					this.frmGroup.get("descripcionView")?.setValue(result.nombre);
					this._changeDetectorRef.markForCheck();
				}
			});
	}
}