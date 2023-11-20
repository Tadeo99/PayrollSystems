import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
  ViewEncapsulation,
} from '@angular/core';
import { UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { MatDialog } from '@angular/material/dialog';

import { bsAnimations } from '@ng-mf/shared/bs';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { EstadoGeneralState } from '@ng-mf/shared/service/comun';
import {
  BsConfirmationService,
  TypeSelectItemService,
} from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { TecnologiaService } from '../tecnologia.service';
import { Tecnologia } from '../tecnologia.types';
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
  selector: 'ng-mf-bs-tecnologiafrm',
  templateUrl: './tecnologiafrm.component.html',
  styleUrls: ['./tecnologiafrm.component.scss'],
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: bsAnimations,
})
export class TecnologiaFrmComponent
  extends BaseComponent<Tecnologia>
  implements OnInit, OnChanges, OnDestroy
{
  constructor(
    private fb: UntypedFormBuilder,
    public dialog: MatDialog,
    router: Router,
    route: ActivatedRoute,
    private _changeDetectorRef: ChangeDetectorRef,
    private service: TecnologiaService,
    public override _translocoService: TranslocoService,
    public override _bsConfirmationService: BsConfirmationService,
    public typeService: TypeSelectItemService
  ) {
    super(_translocoService, _bsConfirmationService);
  }

  ngOnChanges(changes: SimpleChanges) {
    console.log('ngonchanges');
    this.crearFormulario();
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
    if (this.data) {
      this.find(this.data);
    } else {
      this.nuevo();
    }
  }

  private crearFormulario(): void {
    this.frmGroup = this.fb.group({
      idTecnologia: [''],
      nombre: [''],
      tipo: [''],
      estado: [''],
    });
    this.onChange();
  }

  private onChange(): void {
    //this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});
  }

  onInit() {
    // Get the pagination
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
          (data) => {
            if (this.isProcesoOK(data)) {
              this.guardoExito();
              this.accionNuevo = false;
              this.changeEmitter.emit({
                isNuevoGuardar: true,
                idTecnologia: data.objetoResultado.idTecnologia,
              });
              this.find(data.objetoResultado);
            }
          },
          (error) => {
            this.mostrarMensajeError(error);
          }
        );
      } else {
        this.service
          .modificar(this.frmGroup.value, this.frmGroup.value.idTecnologia)
          .subscribe(
            (data) => {
              if (this.isProcesoOK(data)) {
                this.actualizadoExito();
                this.find(data.objetoResultado);
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
      // this.buscar();
    } catch (e) {
      this.mostrarMensajeError(e);
    }
  }

  /**
   * find id
   *
   */
  public find(obj: Tecnologia) {
    try {
      this.frmGroup.patchValue(obj, { onlySelf: true, emitEvent: false });
      this.mostrarPanelForm = true;
      this.accionNuevo = false;
      this._changeDetectorRef.markForCheck();
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
    this._changeDetectorRef.markForCheck();
  }
  public changeEmitterEvent(event: any) {
    const isNuevo = event.isNuevo;
    if (isNuevo === true) {
      this.nuevo();
    }
  }
}
