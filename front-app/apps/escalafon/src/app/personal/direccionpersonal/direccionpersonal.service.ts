/**
 * La Class DireccionPersonalService.
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
import { DireccionPersonal } from '../direccionpersonal.types';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DireccionPersonalService extends BaseService<DireccionPersonal> {
  constructor(http: HttpClient) {
    super(http);
    this.modulo = "rrhh_escalafon";
  }
  override crearPer(idPersonal: string, data: any, moduloPer?: string): Observable<any> {
    if (moduloPer != null) {
      this.modulo = moduloPer;
    }
    this.url = "/personal/" + idPersonal + "/direccion";
    return this.crear(this.modulo + this.url, data);
  }

  override modificarPer(idPersonal: string, data: any, id: any, moduloPer?: string): Observable<any> {
    if (moduloPer != null) {
      this.modulo = moduloPer;
    }
    this.url = "/personal/" + idPersonal + "/direccion";
    return this.put(this.modulo + this.url + "/" + id, data);
  }
}
