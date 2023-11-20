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

import { MatDialog, MatDialogRef } from '@angular/material/dialog';

import { bsAnimations } from '@ng-mf/shared/bs';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { BasePagination, BsConfirmationService, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { merge, takeUntil } from 'rxjs';
import { ConceptoPdtService } from "./conceptopdtselect.service";
import { ConceptoPdt } from '../conceptopdt/conceptopdt.types';
/**
 * La Class ConceptoPdtComponent.
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
	selector: 'ng-mf-bs-conceptopdtselect',
	templateUrl: './conceptopdtselect.component.html',
	styleUrls: ['./conceptopdtselect.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class ConceptoPdtSelectItemComponent extends BaseComponent<ConceptoPdt> implements OnInit, AfterViewInit, OnDestroy {

	public selected = new FormControl(0);

	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		router: Router,
		route: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: ConceptoPdtService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService,
		public matDialogRef: MatDialogRef<ConceptoPdtSelectItemComponent>
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
		this.onInit();
		this.inicializar();
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
	 * find id
	 *
	 */
	public asociar(obj: ConceptoPdt) {
		try {
			this.selectedData = obj;
			this.matDialogRef.close(obj);
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}

	/**
	 * Buscar.
	 *
	 */
	public buscar() {
		if (this.typeService.listaTipoPDT != null) {
			this.params = this.params.set("tipo", (this.typeService.listaTipoPDT[this.selected?.value ?? 0]?.id) + "");
		}
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


	public changeEmitterEvent(event: any) {
		const isNuevo = event.isNuevo;
		if (isNuevo === false) {
			this.regresarDependencia();
		}
	}

	public verDependecia(obj: ConceptoPdt) {
		this.verDependeciaView(obj);
		this.buscar();
	}
	public verDependeciaView(obj: ConceptoPdt) {
		this.searchInputControl.setValue("");
		this.params = this.params.set("id", '');
		this.titlePage = '';
		this._paginator.pageIndex = 0;
		this._paginator.pageSize = 10;
		if (obj.idConceptoPdt != '') {
			this.params = this.params.set("id", obj.idConceptoPdt + '');
			this.titlePage = this.typeService?.tipoPDTMap.get(obj?.tipo) + ' : ' + obj.descripcion;
			this.selectedData = obj;
		}
	}

	public regresarDependencia() {
		this.search = '';
		if ((this.selectedData?.conceptoPdtPadre != null) && (this.selectedData?.conceptoPdtPadre != null && this.selectedData?.conceptoPdtPadre?.idConceptoPdt)) {
			this.verDependecia(this.selectedData?.conceptoPdtPadre);
		} else {
			this.params = this.params.set("id", '');
			this.titlePage = '';
			this.buscar();
			this.selectedData = null;
		}
	}

	public tabChanged = (tabChangeEvent: any): void => {
		this.params = this.params.set("id", '');
		this.titlePage = '';
		this.selectedData = null;
		this.buscar();
	}

	/**
	 * Save and close
	 */
	close(): void {
		// Close the dialog
		this.matDialogRef.close();
	}
}