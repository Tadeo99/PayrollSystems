import {
  AfterViewInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  Input,
  OnDestroy,
  OnInit,
  ViewEncapsulation,
} from '@angular/core';
import { UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { MatDialog } from '@angular/material/dialog';

import { bsAnimations, labelColorDefs } from '@ng-mf/shared/bs';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { CommonService, EstadoGeneralState } from '@ng-mf/shared/service/comun';
import {
  BasePagination,
  BsConfirmationService,
  SelectItemVO,
  TypeSelectItemService,
} from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { merge, takeUntil } from 'rxjs';
import { GrupoNegocioService } from './gruponegocio.service';
import { GrupoNegocio } from './gruponegocio.types';

/**
 * La Class GrupoNegocioComponent.
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
  selector: 'ng-mf-bs-gruponegocio',
  templateUrl: './gruponegocio.component.html',
  styleUrls: ['./gruponegocio.component.scss'],
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: bsAnimations,
})
export class GrupoNegocioComponent
  extends BaseComponent<GrupoNegocio>
  implements OnInit, AfterViewInit, OnDestroy
{
  @Input()
  public listaTecnologia: SelectItemVO[] | undefined = [];
  public tecnologias: any[] = [];

  constructor(
    private fb: UntypedFormBuilder,
    public dialog: MatDialog,
    router: Router,
    route: ActivatedRoute,
    private _changeDetectorRef: ChangeDetectorRef,
    private service: GrupoNegocioService,
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
      idGrupoNegocio: [null],
      nombre: [null],
      idsTecnologias: [''],
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
      const idsTecnologias = this.tecnologias.toString();
      this.frmGroup.get('idsTecnologias')?.setValue(idsTecnologias);
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
            this.frmGroup.value.idGrupoNegocio
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
  public confirmarEliminar(obj: GrupoNegocio) {
    const dialogRef = this.abrirModalConfirDialogEliminar();
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 'confirmed') {
        this.eliminar(obj.idGrupoNegocio);
      }
    });
  }

  /**
   * find id
   *
   */
  public find(obj: GrupoNegocio) {
    try {
      this.frmGroup.patchValue(obj, { onlySelf: true, emitEvent: false });
      this.mostrarPanelForm = true;
      this.accionNuevo = false;
      this.tecnologias = [];
      if (obj.idsTecnologias != null && obj.idsTecnologias != '') {
        this.tecnologias = obj.idsTecnologias.split(',');
      }
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
  /**
   * Toggle label
   *
   * @param label
   */
  togglegTecnologiaLabel(label: SelectItemVO): void {
    // Update the mail object
    if (this.tecnologias?.includes(label.id)) {
      // Delete the label
      this.tecnologias?.splice(this.tecnologias?.indexOf(label.id), 1);
    } else {
      // Add the label
      this.tecnologias?.push(label.id);
    }
  }
}
