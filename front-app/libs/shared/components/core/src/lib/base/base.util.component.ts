import { HttpParams } from '@angular/common/http';
import { Component } from "@angular/core";
import { UntypedFormControl } from '@angular/forms';
import { BsConfirmationService } from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { environment } from '../../../../../environments/environment';
import { ControlRecursosWeb } from "../util/controlrecursosweb";




export const TOKEN_NAME = 'jwt_token';

@Component({
  selector: 'ng-mf-bs-baseutil',
  templateUrl: './base.component.html',
  styleUrls: ['./base.component.scss']
})
export class BaseUtilComponent {

  public DEBOUNCE_TIME = 400;
  public timerSubscription: any;
  public showAccionAsociar = false;
  public showAccionCancelar = true;
  public showAccionNuevo = true;
  public showAccionModificar = true;
  public showAccionCheck = false;
  public showAccionEliminar = true;
  public isLinear = false;

  /** El validar pagina. */
  public validarPagina = true;
  public iconAccionItems = [
    { text: 'Eliminar', disabled: false, icon: 'delete', click: 'confirmarEliminar' }
  ];

  /** La accion nuevo. */
  //@Input("accionNuevo") 
  public accionNuevo = false;

  public validarCampo = false;

  /** El flag show frm edit. */
  public mostrarPanelForm = false;
  public touch?: boolean;
  public searchInputControl = new UntypedFormControl();

  public listasize = 0;
  public mensaje = '';
  public search = '';

  public titulo = '';
  public descripcion = '';
  public cantidadPage = 0;
  public isLoading = false;
  public params = new HttpParams();

  constructor(public _translocoService: TranslocoService, public _bsConfirmationService: BsConfirmationService) {

  }
  public debounceTimeProcesar(): Observable<any> {
    return this.searchInputControl.valueChanges.pipe(debounceTime(500), distinctUntilChanged());
  }

  public ngOnDestroy(): void {
    if (this.timerSubscription) {
      this.timerSubscription.unsubscribe();
    }
  }


  public descargarReporte(moduloContexto: any, idCodigoUUID: string) {
    if (idCodigoUUID != '') {
      let url = '?fileName=' + idCodigoUUID;
      const dataTmp: any = environment.apiServicioDescarga;
      const context = '' + dataTmp["API_URL_DESCARGA_" + moduloContexto];
      if (context != '') {
        url = context + url;
      }
      window.open(url, undefined, 'location=no,height=50,width=50,resizable=yes,scrollbars=yes');
    }
  }


  public getToken(): string {
    return window.localStorage[TOKEN_NAME];
  }
  public abrirModalConfirDialogEliminar(aceptar?: string, cancelar?: string) {
    return this.abrirModalConfirDialog(this.cargarMensaje('confirm.mensaje.eliminar'), aceptar, cancelar, this.cargarMensaje('confirmar.menssage.title'));
  }

  public abrirModalConfirDialog(mensajeModalAlert: string, aceptar?: string, cancelar?: string, titulo?: string) {
    const dialogRef = this._bsConfirmationService.open({
      title: titulo == null ? "" : titulo,
      message: mensajeModalAlert,
      actions: {
        confirm: {
          label: aceptar == null ? "ACEPTAR" : aceptar
        },
        cancel: {
          label: cancelar == null ? "CANCELAR" : cancelar
        }
      }
    });

    return dialogRef;
  }
  public abrirModal(dialogRef: any, showModal: boolean, showSelectMultiple: boolean, esIncludeComponent: boolean, data: any, search: any, id?: string) {
    dialogRef.componentInstance.showModal = showModal;
    dialogRef.componentInstance.showSelectMultiple = showSelectMultiple;
    dialogRef.componentInstance.esIncludeComponent = esIncludeComponent;
    dialogRef.componentInstance.data = data;
    dialogRef.componentInstance.id = id;
    dialogRef.componentInstance.search = search;
    return dialogRef;
  }

  public getDataKey(Key: any): any {
    return window.localStorage['jwt' + Key];
  }
  public setDataKey(Key: any, data: any): any {
    return window.localStorage['jwt' + Key] = data;
  }
  public mensajeConfirmacionEliminar(mensaje?: string): string {
    return mensaje ?? '¿Esta seguro que desea eliminar?';
  }

  public startProgres() {
    this.isLoading = true;
  }
  public endProgres() {
    this.isLoading = false;
  }

  /**
     * Método encargado de cargar mensajes del archivo messages.
     * @param clase Clase para ubicar los mensajes
     * @param cadena para traer el mensaje del properties
     * @param parametros Paramtros del mensaje
     * @return mensaje encontrado para la cadena
     */
  public cargarMensaje(key: string, ...parametros: string[]): string {
    try {
      console.log("cargarMensaje.key == > " + this._translocoService.translate(key));
      if (this._translocoService.translate(key) != null) {
        return ControlRecursosWeb.cargarMensajeParametro(this._translocoService.translate(key), parametros);
      }
    } catch (e) {
      //log.info(e.getMessage());
    }
    return '!' + key + '!';
  }

  /**
   * Metodo para ver un mensaje en pantalla.
   *
   * @param llave para buscar el mensaje en el messages.properties del paquete en el que se encuentra
   * @return mensaje del archivo messages.properties
   * @throws Exception al cargar el bundle
   */
  public cargarMensajeLlave(key: string): string {
    console.log("cargarMensajeLlave.key == > " + this._translocoService.translate(key));
    if (this._translocoService.translate(key) != null) {
      return this._translocoService.translate(key);
    }
    return '!' + key + '!';
  }

  /**
   * Metodo para ver un mensaje en pantalla.
   *
   * @param llave para buscar el mensaje en el messages.properties del paquete en el que se encuentra
   * @param parametros el parametros
   * @return mensaje del archivo messages.properties
   * @throws Exception al cargar el bundle
   */
  public cargarMensajeLlaveParams(key: string, ...parametros: string[]): string {
    console.log("cargarMensajeLlaveParams.key == > " + this._translocoService.translate(key));
    if (this._translocoService.translate(key) != null) {
      return ControlRecursosWeb.cargarMensajeParametro(this._translocoService.translate(key), parametros);
    }
    return '!' + key + '!';
  }

  public nvl(valor: any, value: any): any {
    if (valor === undefined) {
      return value;
    } else if (valor === '') {
      return value;
    } else if (valor !== null && valor.length === 0) {
      return value;
    }
    return valor;
  }

}

