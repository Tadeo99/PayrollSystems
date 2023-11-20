import {
	AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Component,
	Input,
	OnChanges, OnDestroy, OnInit, SimpleChanges, ViewEncapsulation
} from '@angular/core';
import { UntypedFormBuilder } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { bsAnimations } from '@ng-mf/shared/bs';
import { BasePagination, BsConfirmationService, SelectItemVO, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { merge, takeUntil } from 'rxjs';
import { BaseComponent } from '../base';
import { SelectItemService } from "./selectitem.service";


/**
 * La Class SelectItemComponent.
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
	selector: 'ng-mf-bs-selectitem',
	templateUrl: './selectitem.component.html',
	styleUrls: ['./selectitem.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class SelectItemComponent extends BaseComponent<SelectItemVO> implements OnInit, OnChanges, AfterViewInit, OnDestroy {

	@Input()
	public groupName = '';

	@Input()
	public sortName = '';

	constructor(
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		router: Router,
		route: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: SelectItemService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService,
		public matDialogRef: MatDialogRef<SelectItemComponent>,
	) {
		super(_translocoService, _bsConfirmationService);

		this.debounceTimeProcesar().subscribe(term => { this.search = term; this.buscar() });
		this.SORT_NAME = 'nombre';
		this.params = this.params.set('groupName', 'anhio');
	}

	ngOnChanges(changes: SimpleChanges) {
		if (this.id != null) {
			this.params = this.params.set('codigoExterno', this.id + '');
		}
		if (this.data && this.data.tipo) {
			this.params = this.params.set('tipo', this.data.tipo + '');
		}
		if (this.data && this.data.idPadreView) {
			this.params = this.params.set('idPadreView', this.data.idPadreView + '');
		}
		if (this.data && this.data.idListaItems) {
			this.params = this.params.set('id', this.data.idListaItems + '');
			this.inicializar();
		}
	}

	/**
	 * After view init
	 */
	ngAfterViewInit(): void {
		if (this.sortName.length > 0) {
			this.SORT_NAME = this.sortName;
		} else {
			this.sortName = 'nombre';
		}
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
	 * asociar
	 *
	 */
	public asociar(obj: SelectItemVO) {
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
		this.isLoading = true;
		this.service.paginadorPerByGrupo(
			this.modulo,
			this.groupName,
			this.id,
			this.nvl(this._paginator?.pageIndex, 0),
			this.nvl(this._paginator?.pageSize, 5),
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

	public regresar() {
		this.mostrarPanelForm = false;
		this.changeEmitter.emit({ mostrarPanelForm: this.mostrarPanelForm, isBuscar: false });
	}

	public changeEmitterEvent(event: any) {
		const isNuevo = event.isNuevo;
		if (isNuevo === false) {
			this.regresar();
		}
	}

	/**
	 * Save and close
	 */
	close(): void {
		// Close the dialog
		this.matDialogRef.close();
	}
}