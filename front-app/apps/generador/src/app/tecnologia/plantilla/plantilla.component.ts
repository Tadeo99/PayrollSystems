import {
  AfterViewInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  OnDestroy,
  OnInit,
  ViewChild,
  ViewEncapsulation,
} from '@angular/core';
import { UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { MatDialog } from '@angular/material/dialog';

import { bsAnimations } from '@ng-mf/shared/bs';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { CommonService, EstadoGeneralState } from '@ng-mf/shared/service/comun';
import {
  BasePagination,
  BsConfirmationService,
  BsNavigationItem,
  TypeSelectItemService,
} from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { merge, takeUntil } from 'rxjs';
import { PlantillaService } from './plantilla.service';
import { Plantilla } from './plantilla.types';

import { javascript, javascriptLanguage } from '@codemirror/lang-javascript';
import { PostgreSQL, sql } from '@codemirror/lang-sql';

import { EditorState, Extension } from '@codemirror/state';
import { oneDark } from '@codemirror/theme-one-dark';
import { EditorView } from '@codemirror/view';
import { basicSetup } from 'codemirror';

import { completeFromList } from '@codemirror/autocomplete';

/**
 * La Class Plantillaomponent.
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
  selector: 'ng-mf-bs-plantilla',
  templateUrl: './plantilla.component.html',
  styleUrls: ['./plantilla.component.scss'],
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: bsAnimations,
})
export class PlantillaComponent
  extends BaseComponent<Plantilla>
  implements OnInit, AfterViewInit, OnDestroy
{
  @ViewChild('myeditorCalc') myEditor: any;
  view!: EditorView;

  menuData!: BsNavigationItem[];

  constructor(
    private fb: UntypedFormBuilder,
    public dialog: MatDialog,
    router: Router,
    route: ActivatedRoute,
    private _changeDetectorRef: ChangeDetectorRef,
    private service: PlantillaService,
    public commonService: CommonService,
    public override _translocoService: TranslocoService,
    public override _bsConfirmationService: BsConfirmationService,
    public typeService: TypeSelectItemService
  ) {
    super(_translocoService, _bsConfirmationService);

    this.debounceTimeProcesar().subscribe((term) => {
      this.search = term;
      this.buscar();
    });
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
        disableClear: true,
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
      merge(this._sort.sortChange, this._paginator.page)
        .pipe(takeUntil(this._unsubscribeAll))
        .subscribe(() => {
          this.buscar();
        });
    }
    this.viewFormulaInit('');
  }
  viewFormula(formula: string) {
    const options: any[] = [];
    this.menuData?.forEach((objData) => {
      objData.children?.forEach((objDet) => {
        options.push({
          label: objDet.title,
          type: 'variable',
          info: objData.title,
        });
      });
    });
    options.push({
      label: 'REDONDEAR',
      type: 'keyword',
      apply: 'REDONDEAR( )',
      detail: 'funcion',
    });

    let state!: EditorState;
    const myExt: Extension = [
      basicSetup,
      javascript(),
      oneDark,
      javascriptLanguage.data.of({
        autocomplete: completeFromList(options),
      }),
    ];

    const myExtSQl: Extension = [basicSetup, sql(), oneDark, PostgreSQL];

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
        doc: formula,
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
      idPlantilla: [null],
      nombre: [null],
      codigoGrupo: [null],
      plantilla: [null],
      nombreArchivoGenerar: [null],
      extension: [null],
      esCollectionData: [''],
      esInclude: [''],
      orden: [null],
      estado: [''],
    });
    this.onChange();
  }
  public async cargarCombo() {
    //
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
      const plantilla = this.view.state.doc;
      this.frmGroup.get('plantilla')?.setValue(plantilla.toString());
      if (this.accionNuevo) {
        this.service.crearPer(this.id, this.frmGroup.value).subscribe(
          (data) => {
            if (this.isProcesoOK(data)) {
              this.guardoExito();
              this.buscar();
            }
          },
          (error) => {
            this.mostrarMensajeError(error);
          }
        );
      } else {
        this.service
          .modificarPer(
            this.id,
            this.frmGroup.value,
            this.frmGroup.value.idPlantilla
          )
          .subscribe(
            (data) => {
              if (this.isProcesoOK(data)) {
                this.actualizadoExito();
                this.buscar();
              }
            },
            (error) => {
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
    this.frmGroup.get('estado')?.setValue(EstadoGeneralState.ACTIVO.toString());
    this.mostrarPanelForm = true;
    this.accionNuevo = true;
    this.viewFormula('');
    this._changeDetectorRef.markForCheck();
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
        (data) => {
          if (this.isProcesoOK(data)) {
            this.eliminoExito();
            this.buscar();
          }
        },
        (error) => {
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
  public confirmarEliminar(obj: Plantilla) {
    const dialogRef = this.abrirModalConfirDialogEliminar();
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 'confirmed') {
        this.eliminar(obj.idPlantilla);
      }
    });
  }

  /**
   * find id
   *
   */
  public find(obj: Plantilla) {
    try {
      this.frmGroup.patchValue(obj, { onlySelf: true, emitEvent: false });
      this.mostrarPanelForm = true;
      this.accionNuevo = false;
      this.viewFormula(obj.plantilla);
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
    this.service
      .paginadorPer(
        this.id,
        this.nvl(this._paginator?.pageIndex, 0),
        this.nvl(this._paginator?.pageSize, 10),
        this.nvl(this._sort?.active, this.SORT_NAME),
        this.nvl(this._sort?.direction, this.SORT_DIR),
        this.search,
        this.params
      )
      .subscribe(
        (data) => {
          if (this.isProcesoOK(data)) {
            // Mark for check
            this.isLoading = false;
            this.mostrarPanelForm = false;
            this._changeDetectorRef.markForCheck();
          }
        },
        (error) => {
          this.isLoading = false;
          this.mostrarMensajeError(error);
        }
      );
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
}
