import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { AbstractControl, UntypedFormGroup, ValidatorFn } from '@angular/forms';
import { BaseUtilComponent } from './base.util.component';
import { TranslocoService } from '@ngneat/transloco';
import { BsConfirmationService } from '@ng-mf/shared/service/core';
import { Observable, Subject } from 'rxjs';
import { BasePagination } from '@ng-mf/shared/service/core';

import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { BsAlertType } from '../alert';

@Component({
  selector: 'ng-mf-bs-base',
  templateUrl: './base.component.html',
  styleUrls: ['./base.component.scss']
})
export class BaseComponent<T> extends BaseUtilComponent {

  @Input()
  public id = '';

  @Input()
  public idTupla = '';

  @Input()
  public data: any;

  @Input()
  public procesar = false;

  @Input()
  public showModal = false;

  @Input()
  public esIncludeComponent = false;

  @Input()
  public showSelectMultiple = false;

  //@Input("dialogRef")
  //public dialogRef: MatDialogRef<any>;

  // Usamos el decorador Output
  @Output()
  public changeEmitter = new EventEmitter();

  @Input()
  public showComponentPage = false;

  @Input()
  public titlePage = "";

  @Input()
  public isCrud = false;

  @Input()
  public modulo?: string;

  @Input()
  public frmGroup!: UntypedFormGroup;

  @Input()
  public controlName?: string;

  @Input()
  public viewSource?: string;

  public rutaAccesoMenu = "";

  public viewPage = "";

  public listaData$!: Observable<T[]>;
  public pagination!: BasePagination | null;
  protected _unsubscribeAll: Subject<any> = new Subject<any>();

  @ViewChild(MatPaginator) public _paginator!: MatPaginator;


  @ViewChild(MatSort) protected _sort!: MatSort;

  public selectedData: T | null = null;

  public showAlert = false;
  public alert: { type: BsAlertType; message: string } = {
    type: 'success',
    message: ''
  };

  public SORT_NAME = '';
  public SORT_DIR : any = 'asc';

  drawerMode: 'over' | 'side' = 'side';
  drawerOpened = true;
  panels: any[] = [];
  selectedPanel = 'basico';

  labelColors: any;

  constructor(
    public override _translocoService: TranslocoService,
    public override _bsConfirmationService: BsConfirmationService) {
    super(_translocoService, _bsConfirmationService);
  }

  /**
   * Get the details of the panel
   *
   * @param id
   */
  getPanelInfo(id: string): any {
    return this.panels.find((panel) => panel.id === id);
  }
  /**
    * Close the details
    */
  closeDetails(): void {
    this.selectedData = null;
  }
  /**
     * Track by function for ngFor loops
     *
     * @param index
     * @param item
     */
  trackByFn(index: number, item: any): any {
    return item.id || index;
  }

  public getError(controlName: string): string {
    let error = '';
    const control = this.frmGroup.get(controlName);
    if (control != null && control.touched && control.errors != null) {
      /*if (control.errors?.requiered) {
        error = "Es requerido";
      } else if (control.errors?.existBD) {
        error = "No existe en bd";
      } else {*/
      error = JSON.stringify(control.errors);
      //}
    }
    return error;
  }

  public invalid(controlName: string): boolean | undefined {
    return this.frmGroup?.get(controlName)?.invalid;
  }

  public showAccion() {
    if (this.showModal) {
      if (!this.isCrud) {
        this.showAccionNuevo = false;
        this.showAccionModificar = false;
        this.showAccionEliminar = false;
      }
      if (this.showSelectMultiple) {
        this.showAccionAsociar = true;
        this.showAccionCheck = true;
      }
    }
    if (this.esIncludeComponent && !this.isCrud) {
      this.showAccionNuevo = false;
    }
  }

  // Cuando se lance el evento click en la plantilla llamaremos a este método
  public cancelarPage() {
    this.mostrarPanelForm = false;
    // this.showComponentPage = false;
  }

  public regresarPage() {
    this.lanzar(null);
    this.showComponentPage = false;
  }
  public regresarPageEvent(event: any) {
    this.lanzar(null);
    this.showComponentPage = false;
  }
  public regresarViewPage(event: any) {
    const isRegresar = event.isRegresar;
    if (isRegresar) {
      this.lanzar(event);
      this.showComponentPage = false;
    }
  }
  public lanzar(event: any) {
    // Usamos el método emit
    this.changeEmitter.emit({ data: this.data, isRegresar: true });
  }


  public guardoExito() {
    this.mostrarMensaje('se guardo con exito', 'Mensaje');
  }
  public actualizadoExito() {
    this.mostrarMensaje('se modifico con exito', 'Mensaje');
  }
  public eliminoExito() {
    this.mostrarMensaje('se elimino con exito', 'Mensaje');
  }
  private mostrarMensaje(msg: string, tituloTmp: string) {
    const dialogRef = this._bsConfirmationService.open({
      title: tituloTmp,
      message: msg,
      actions: {
        confirm: {
          label: 'Aceptar'
        },
        cancel: {
          show: false
        }

      }
    });
    dialogRef.afterClosed().subscribe(result => {
      //
    });
  }
  public isProcesoOK(e: any): boolean {
    if (e.error) {
      if (e.codigoError === "MSG") {
        this.mostrarMensajeAdvertencia(e.mensajeError);
      } else {
        this.mostrarMensajeError(e);
      }
      return false;
    }
    return true;
  }
  public mostrarMensajeErrorFrmInvalid() {
    this.mostrarMensajeError("Formulario No valido");
  }
  public mostrarMensajeError(e: any) {
    //console.error('Error:' + e );
    let mensaje = '';
    let codigo = '';
    console.log("e.status == " + e.status);
    console.log("e.code == " + e.code);
    console.log("e.statusCode == " + e.statusCode);
    console.log("e.error == " + e.error);

    if (e != null && e != '' && e.mensajeError) {
      mensaje = '' + e.mensajeError;
      codigo = '' + e.codigoError;
    }
    if (e != null && e != '' && e.error != null && e.error.mensajeError) {
      mensaje = '' + e.error.mensajeError;
      codigo = '' + e.error.codigoError;
    }
    /*if ( e != null && e != '' && e.statusText && (mensaje ==null || mensaje =='')) {
      mensaje = '' + e.statusText;
      codigo = '' + e.status;
    }*/
    else if (e != null && e != '' && e.error && (mensaje == null || mensaje == '')) {
      mensaje = '' + e.error;
      codigo = '' + e.status;
    } else {
      mensaje = '' + e;
    }

    if (e != null && e.status) {
      if (e != null && e.status === 401 || e.status === 403) {
        this.endProgres();
        this.mostrarMensajeAdvertencia("Expiro la sessión");
        return;
      }
      if (e != null && e.code === 401 || e.code === 403) {
        this.endProgres();
        this.mostrarMensajeAdvertencia("Expiro la sessión");
        return;
      }
      if (e != null && e.code === 500 && (mensaje == null || mensaje == '')) {
        this.endProgres();
        mensaje = "Error Interno en el Servidor status = " + e.code;
        this.mostrarMensajeAdvertencia(mensaje);
        // this.navigate("login");
        return;
      }
      if (e != null && e.status === 500 && (mensaje == null || mensaje == '')) {
        this.endProgres();
        mensaje = "Error Interno en el Servidor status = " + e.status;
        this.mostrarMensajeAdvertencia(mensaje);
        // this.navigate("login");
        return;
      }
      if (e != null && e.statusCode === 401 || e.statusCode === 403) {
        console.log("e!= null && e.statusCode ");
        this.endProgres();
        this.mostrarMensajeAdvertencia("Expiro la sessión");
        return;
      }

      if (e != null && e.statusCode === 500 && (mensaje == null || mensaje == '')) {
        console.log("e!= null && e.statusCode ");
        this.endProgres();
        mensaje = "Error Interno en el Servidor status = " + e.statusCode;
        this.mostrarMensajeAdvertencia(mensaje);
        return;
      }

    }
    this.mostrarMensaje(mensaje, 'ERROR');
  }

  public mostrarMensajeExito(msg: any) {
    this.mostrarMensaje(msg, 'Mensaje');
  }

  public mostrarMensajeAdvertencia(msg: any) {
    this.mostrarMensaje(msg, 'Mensaje');
  }



  public procesarWebSockect(msg: any, moduloContexto?: any) {
    console.log("Response from websocket: " + msg);
    console.log("Response from websocket.codigoSolicitud: " + msg.codigoSolicitud);
    console.log("Response from websocket.content.length: " + msg.content.length);
    if (msg.content != null && msg.content.length > 0) {
      this.mostrarMensajeError(msg.content);
      return false;
    } else {
      if (msg.tipo === 'R') {
        this.descargarReporte(moduloContexto, msg.codigoSolicitud);
        return false;
      }
    }
    return true;
  }

}

export class ValidatorsPer {
  static existBD(controlValidar: string): ValidatorFn {
    return (control: AbstractControl) => {
      if (control != null) {

        if (control.root != null && control.root.get(controlValidar) != null
          && control.root.get(controlValidar)?.value == null) {

          return { noExitBD: true }
        }
        if (control.root != null && control.root.get(controlValidar) != null) {
          console.log("2control.root.get(controlValidar).value " + control?.root?.get(controlValidar)?.value);
        }
      }
      return { noExitBD: false }
    };
  }
}
