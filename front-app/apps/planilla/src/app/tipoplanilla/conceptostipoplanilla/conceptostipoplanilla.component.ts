import {
	AfterViewInit,
	ChangeDetectionStrategy,
	ChangeDetectorRef,
	Component,
	OnDestroy,
	OnInit,
	ViewChild,
	ViewEncapsulation
} from '@angular/core';
import { FormControl, UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { MatDialog } from '@angular/material/dialog';

import { bsAnimations } from '@ng-mf/shared/bs';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { EstadoGeneralState } from '@ng-mf/shared/service/comun';
import { BasePagination, BsConfirmationService, BsNavigationItem, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { merge, takeUntil } from 'rxjs';
import { ConceptoPdtSelectItemComponent } from '../../conceptopdtselect';
import { ConceptosTipoPlanillaService } from "./conceptostipoplanilla.service";
import { ConceptosTipoPlanilla } from "./conceptostipoplanilla.types";
import { ConfiguracionService } from './configuracion.service';


import { DOCUMENT } from '@angular/common';
import { Inject } from '@angular/core';
import { javascript, javascriptLanguage } from '@codemirror/lang-javascript';
import { EditorState, Extension } from '@codemirror/state';
import {
	oneDark
} from '@codemirror/theme-one-dark';
import { EditorView } from '@codemirror/view';
import { basicSetup } from 'codemirror';


import { completeFromList } from "@codemirror/autocomplete";
/**
 * La Class ConceptosTipoPlanillaComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Wed Oct 21 21:47:02 COT 2020
 * @since BUILDERP-CORE 2.1
 */
//https://codemirror.net/examples/lang-package/
//https://github.com/codemirror/lang-example/blob/main/src/syntax.grammar
//https://github.com/UziTech/eslint-linter-browserify/blob/master/example/script.js

@Component({
	selector: 'ng-mf-bs-conceptostipoplanilla',
	templateUrl: './conceptostipoplanilla.component.html',
	styleUrls: ['./conceptostipoplanilla.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: bsAnimations
})
export class ConceptosTipoPlanillaComponent extends BaseComponent<ConceptosTipoPlanilla> implements OnInit, AfterViewInit, OnDestroy {

	selected = new FormControl(0);
	//public listaDataVariable$!: Observable<SelectItemGrupoVO[]>;

	menuData!: BsNavigationItem[];

	@ViewChild('myeditor') myEditor: any;
	view!: EditorView;

	constructor(
		@Inject(DOCUMENT) private document: Document,
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		router: Router,
		route: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		private service: ConceptosTipoPlanillaService,
		private serviceConfig: ConfiguracionService,
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
		this.viewFormulaInit("");
	}

	viewFormula(formula: string) {
		const options: any[] = [];
		this.menuData?.forEach((objData) => {
			objData.children?.forEach((objDet) => {
				options.push({ label: objDet.title, type: 'variable', info: objData.title });
			});
		});
		options.push({ label: 'REDONDEAR', type: 'keyword', apply: 'REDONDEAR( )', detail: 'funcion' });

		let state!: EditorState;
		const myExt: Extension = [basicSetup, javascript(), oneDark, javascriptLanguage.data.of({
			autocomplete: completeFromList(options),
		}),
		];
		try {
			state = EditorState.create({
				doc: formula,
				extensions: myExt,
			});
		} catch (e) {
			//Please make sure install codemirror@6.0.1
			//If your command was npm install codemirror, will installed 6.65.1(whatever)
			//You will be here.
			console.error(e);
		}
		this.view.setState(state);

	}

	viewFormulaInit(formula: string) {
		const myEditorElement = this.myEditor.nativeElement;
		let state!: EditorState;
		try {
			state = EditorState.create({
				doc: formula
			});
		} catch (e) {
			console.error(e);
		}
		//console.log(state);
		this.view = new EditorView({
			state,
			parent: myEditorElement,
		});
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
			idConceptosTipoPlanilla: [null],
			conceptoPdt: this.fb.group({
				idConceptoPdt: [''],
				descripcion: [''],
			}),
			descripcion: [''],
			codigo: [''],
			orden: [null],
			formula: [''],
			persistente: [''],
			showBoleta :[''],
			estado: [''],
		});
		this.onChange();
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
		//this.listaDataVariable$ = this.serviceConfig.listaData$;
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
		if (!this.id) {
			this.mostrarMensajeError("Debe registrar un tipo de Planilla");
			return;
		}
		try {
			const formula = this.view.state.doc;
			this.frmGroup.get("formula")?.setValue(formula.toString());
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
				this.service.modificarPer(this.id, this.frmGroup.value, this.frmGroup.value.idConceptosTipoPlanilla).subscribe(
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
		this.viewFormula("");
		this._changeDetectorRef.markForCheck();
	}

	/**
	 * Inicializar.
	 *
	 */
	private inicializar() {
		try {
			this.buscar();
			this.getVariables();
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
	public confirmarEliminar(obj: ConceptosTipoPlanilla) {
		const dialogRef = this.abrirModalConfirDialogEliminar();
		dialogRef.afterClosed().subscribe(result => {
			if (result === 'confirmed') {
				this.eliminar(obj.idConceptosTipoPlanilla);
			}
		});
	}

	/**
	 * find id
	 *
	 */
	public find(obj: ConceptosTipoPlanilla) {
		try {
			this.frmGroup.patchValue(obj, { onlySelf: true, emitEvent: false });
			this.mostrarPanelForm = true;
			this.accionNuevo = false;
			this.viewFormula(obj.formula);
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
		this.service.paginadorPer(
			this.id,
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

	public getVariables() {
		this.isLoading = true;
		this.serviceConfig.getVariables(this.id,
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
						this.menuData = data.listaResultado;
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

	public tabChanged = (tabChangeEvent: any): void => {
		this.titlePage = '';
		this.selectedData = null;
		this.buscar();
	}
	public abrirModalConceptoPdt(pSearch?: string) {
		// Open the dialog
		const dialogRef = this.dialog.open(ConceptoPdtSelectItemComponent);
		dialogRef.componentInstance.titlePage = "Conceptos";
		dialogRef.componentInstance.search = pSearch ?? '';
		dialogRef.componentInstance.selected = this.selected;
		dialogRef.afterClosed()
			.subscribe((result) => {
				if (result !== null && result.idConceptoPdt && result.idConceptoPdt !== null) {
					this.frmGroup.get("conceptoPdt.idConceptoPdt")?.setValue(result.idConceptoPdt);
					this.frmGroup.get("conceptoPdt.descripcion")?.setValue(result.descripcion);
					this.frmGroup.get("descripcion")?.setValue(result.descripcion);
					this.frmGroup.get("codigo")?.setValue(result.codigo);
					this._changeDetectorRef.markForCheck();
				}
			});
	}
}
