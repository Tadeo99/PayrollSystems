import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
  ViewEncapsulation,
} from '@angular/core';
import { UntypedFormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { MatDialog } from '@angular/material/dialog';

import { bsAnimations, labelColorDefs } from '@ng-mf/shared/bs';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { CommonService, EstadoGeneralState } from '@ng-mf/shared/service/comun';
import {
  BsConfirmationService,
  SelectItemVO,
  TypeSelectItemService,
} from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { ProyectoService } from '../proyecto.service';
import { Proyecto } from '../proyecto.types';
/**
 * ProyectoComponent
 *.
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
  selector: 'ng-mf-bs-proyectofrm',
  templateUrl: './proyectofrm.component.html',
  styleUrls: ['./proyectofrm.component.scss'],
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: bsAnimations,
})
export class ProyectoFrmComponent
  extends BaseComponent<Proyecto>
  implements OnInit, OnChanges, OnDestroy
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
    private service: ProyectoService,
    public commonService: CommonService,
    public override _translocoService: TranslocoService,
    public override _bsConfirmationService: BsConfirmationService,
    public typeService: TypeSelectItemService
  ) {
    super(_translocoService, _bsConfirmationService);
  }

  ngOnChanges(changes: SimpleChanges) {
    console.log('ngonchanges 2...');
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

  async ngOnInit() {
    console.log('ngOnInit 1...');
    // Get the label colors
    this.labelColors = labelColorDefs;

    this.onInit();
    if (this.data) {
      this.find(this.data);
    } else {
      this.nuevo();
    }

    this._changeDetectorRef.markForCheck();
  }

  private crearFormulario(): void {
    this.frmGroup = this.fb.group({
      idProyecto: [''],
      idUsuario: [''],
      nombre: [''],
      idsTecnologias: [''],
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
      const idsTecnologias = this.tecnologias.toString();
      this.frmGroup.get('idsTecnologias')?.setValue(idsTecnologias);
      if (this.accionNuevo) {
        this.service.crear(this.frmGroup.value).subscribe(
          (data) => {
            if (this.isProcesoOK(data)) {
              this.guardoExito();
              this.changeEmitter.emit({
                isNuevoGuardar: true,
                idProyecto: data.objetoResultado.idProyecto,
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
          .modificar(this.frmGroup.value, this.frmGroup.value.idProyecto)
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
   * find id
   *
   */
  public find(obj: Proyecto) {
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
