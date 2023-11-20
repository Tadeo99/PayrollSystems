/**
 * La Class ClasificacionService.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { BaseService } from "@ng-mf/shared/service/core";
import { TipoDocSunatEntidad } from './tipodocsunatentidad.types';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class TipoDocSunatEntidadService extends BaseService<TipoDocSunatEntidad> {

  constructor(http: HttpClient) {
    super(http);
    this.modulo = "pago";
    this.url = "/tipoDocSunatEntidad";
  }

  listarTipoDocByItem(tipodocsunatentidad : any) : Observable<any> {
    return this.post(this.url + "/listarTipoDocByItem",tipodocsunatentidad);
  }

  public findTipoDocSunatEntidad(tipodocsunatentidad: any): Observable<any> {
    return this.post(this.url + "/findTipoDocSunatEntidad",tipodocsunatentidad);
  }
}
