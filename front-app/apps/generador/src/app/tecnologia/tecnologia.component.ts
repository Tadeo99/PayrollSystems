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

import { MatDrawer } from '@angular/material/sidenav';
import { bsAnimations } from '@ng-mf/shared/bs';
import { BaseComponent } from '@ng-mf/shared/components/core';
import {
	BasePagination,
	BsConfirmationService,
	BsMediaWatcherService,
	TypeSelectItemService,
} from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { merge, takeUntil } from 'rxjs';
import { TecnologiaService } from './tecnologia.service';
import { Tecnologia } from './tecnologia.types';
/**
 * La Class TecnologiaComponent.
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
  selector: 'ng-mf-bs-tecnologia',
  templateUrl: './tecnologia.component.html',
  styleUrls: ['./tecnologia.component.scss'],
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: bsAnimations,
})
export class TecnologiaComponent
  extends BaseComponent<Tecnologia>
  implements OnInit, AfterViewInit, OnDestroy
{
  @ViewChild('drawer') drawer!: MatDrawer;


  constructor(
    private fb: UntypedFormBuilder,
    public dialog: MatDialog,
    router: Router,
    route: ActivatedRoute,
    private _changeDetectorRef: ChangeDetectorRef,
    private service: TecnologiaService,
    public override _translocoService: TranslocoService,
    public override _bsConfirmationService: BsConfirmationService,
    public typeService: TypeSelectItemService,
    private _bsMediaWatcherService: BsMediaWatcherService
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
        id: 'basico',
        icon: 'heroicons_outline:user-circle',
        title: 'Informacion Basico',
        description: 'Datos de tecnologia',
      },
      {
        id: 'plantilla',
        icon: 'heroicons_outline:user-circle',
        title: 'Plantilla',
        description: 'Crear Plantilla segun tecnologia',
      },
      {
        id: 'equivalenciaType',
        icon: 'heroicons_outline:user-group',
        title: 'Equivalencia Type',
        description: 'Registrar Equivalencia segun tecnologia',
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
        } else {
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
  public confirmarEliminar(obj: Tecnologia) {
    const dialogRef = this.abrirModalConfirDialogEliminar();
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 'confirmed') {
        this.eliminar(obj.idTecnologia);
      }
    });
  }

  /**
   * find id
   *
   */
  public find(obj: Tecnologia) {
    try {
      this.selectedData = obj;
      this.mostrarPanelForm = true;
      this.accionNuevo = false;
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
      .paginador(
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
  public changeEmitterEventCrear(event: any) {
    const isNuevoGuardar = event.isNuevoGuardar;
    const idTecnologia = event.idTecnologia;
    if (isNuevoGuardar === true && idTecnologia) {
      if (this.selectedData && this.selectedData !== null) {
        this.selectedData.idTecnologia = idTecnologia;
        this._changeDetectorRef.markForCheck();
      }
    }
  }
}
