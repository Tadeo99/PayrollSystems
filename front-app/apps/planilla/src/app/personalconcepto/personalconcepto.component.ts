import { AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Component, OnDestroy, OnInit, ViewEncapsulation } from '@angular/core';
import { FormControl, UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';


import { MatDialog } from '@angular/material/dialog';

import { bsAnimations } from '@ng-mf/shared/bs';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { CommonService } from '@ng-mf/shared/service/comun';
import { BasePagination, BsConfirmationService, SelectItemVO, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { merge, takeUntil } from 'rxjs';
import { PersonalConceptoService } from './personalconcepto.service';
import { PersonalConcepto } from './personalconcepto.types';


/**
 * La Class PersonalConceptoComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:47 COT 2021
 * @since BUILDERP-CORE 2.1
 */

@Component({
	selector: 'ng-mf-bs-personalconcepto',
	templateUrl: './personalconcepto.component.html',
	styleUrls: ['./personalconcepto.component.scss'],
	providers: [PersonalConceptoService],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class PersonalConceptoComponent extends BaseComponent<PersonalConcepto> implements OnInit, AfterViewInit, OnDestroy {

	idTipoPlanilla = new FormControl("");
	idPeriodoPlanilla = new FormControl("");
	idAnhio = new FormControl("");
	idCategoriaTrabajador = new FormControl("");

	public listaTipoPlanilla: SelectItemVO[] | undefined = [];
	public listaPeriodoPlanilla: SelectItemVO[] | undefined = [];
	public listaAnhio: SelectItemVO[] | undefined = [];
	public tipoPlanilaMap: Map<string, string> = new Map<string, string>();

	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		router: Router,
		route: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: PersonalConceptoService,
		public commonService: CommonService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService
	) {
		super(_translocoService, _bsConfirmationService);

		this.debounceTimeProcesar().subscribe(term => { this.search = term; this.buscar() });
		this.SORT_NAME = 'nombres';
	}

	ngAfterViewInit() {

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
			personal: this.fb.group({
				idPersonal: [null],
				descripcionView: [null]
			}),
			periodoPlanilla: this.fb.group({
				idPeriodoPlanilla: [null,],
				descripcionView: [null]
			}),
			tipoPlanilla: this.fb.group({
				idTipoPlanilla: [null],
				descripcionView: [null],
				itemByCategoriaTrabajador: this.fb.group({
					idItem: [null],
				}),

			}),
			idPersonalConcepto: [null],
			search: this.search,
		});
		this.onChange();
	}

	public async cargarCombo() {
		let paramsTemp: Map<any, any> = new Map<any, any>();

		paramsTemp = new Map<any, any>();
		paramsTemp = paramsTemp.set("anhio", 0);
		await this.commonService.obtenerListaSelectItemVOMap("common", paramsTemp);
		this.listaAnhio = this.commonService.getListaSelectVOItem("anhio");

		paramsTemp = new Map<any, any>();
		paramsTemp = paramsTemp.set("tipoPlanilla", 0);
		await this.commonService.obtenerListaSelectItemVOMap("rrhh_planilla", paramsTemp);
		this.listaTipoPlanilla = this.commonService.getListaSelectVOItem("tipoPlanilla");
		this.tipoPlanilaMap = this.commonService.generarMap(this.listaTipoPlanilla ?? [], true);
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
				this.service.crearPer(this.selectedData?.idPersonal ?? '', this.selectedData).subscribe(
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
				this.service.modificarPer(this.selectedData?.idPersonal ?? '', this.selectedData, this.selectedData?.idPersonalConcepto).subscribe(
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
	 * find id
	 *
	 */
	public find(obj: PersonalConcepto) {
		try {
			if (this.selectedData && this.selectedData.idPersonal === obj.idPersonal) {
				// Close the details
				this.closeDetails();
				return;
			}
			this.frmGroup.patchValue(obj, { onlySelf: true, emitEvent: false });
			this.selectedData = obj;
			this.accionNuevo = this.selectedData.idPersonalConcepto === null || this.selectedData.idPersonalConcepto === "";
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
		this.selectedData = null;
		try {
			/*
			this.params = this.params.set('idEntidadSelect', this.idEntidad);
			this.params = this.params.set('idPeriodoPlanilla', this.personalConcepto.periodoPlanilla.idPeriodoPlanilla + "");
			this.params = this.params.set('idTipoPlanilla', this.personalConcepto.tipoPlanilla.idTipoPlanilla + "");
			this.params = this.params.set('idItemByCategoriaTrabajador', this.personalConcepto.tipoPlanilla.itemByCategoriaTrabajador.idItem + "");
			*/
			this.params = this.params.set('idTipoPlanilla', this.idTipoPlanilla.value ?? '');
			this.params = this.params.set('idAnhio', this.idAnhio.value ?? '');
			this.params = this.params.set('idPeriodoPlanilla', this.idPeriodoPlanilla.value ?? '');
			this.params = this.params.set('idItemByCategoriaTrabajador', this.idCategoriaTrabajador.value ?? '');
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
		this.selectedData = null;
	}

	public changeEmitterEvent(event: any) {
		const isNuevo = event.isNuevo;
		if (isNuevo === true) {
			this.nuevo();
		}
	}

	public onTipoPlanillaChange(ob: any) {
		this.idTipoPlanilla.setValue(ob.value);
		this.idCategoriaTrabajador.setValue(this.tipoPlanilaMap?.get(ob.value) ?? '');
		this.buscar();
	}

	public onPeriodoPlanillaChange(ob: any) {
		this.idPeriodoPlanilla.setValue(ob.value);
		this.buscar();
	}
	public async onAnhioChange(ob: any) {
		this.idAnhio.setValue(ob.value);
		this.idPeriodoPlanilla.setValue("");
		let paramsTemp: Map<any, any> = new Map<any, any>();
		paramsTemp = paramsTemp.set("periodoPlanilla", 0);

		let paramsAdicional: Map<any, any> = new Map<any, any>();
		paramsAdicional = paramsAdicional.set("id", ob.value);
		this.commonService.listaSelectItemVOMap.delete("periodoPlanilla");
		await this.commonService.obtenerListaSelectItemVOMap("rrhh_planilla", paramsTemp, paramsAdicional);
		this.listaPeriodoPlanilla = this.commonService.getListaSelectVOItem("periodoPlanilla");
		this._changeDetectorRef.markForCheck();
		this.buscar();
	}

}