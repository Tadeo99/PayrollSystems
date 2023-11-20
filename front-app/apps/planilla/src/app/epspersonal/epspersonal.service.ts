/**
 * La Class AsistenciaPersonalService.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { BasePagination, BaseService } from "@ng-mf/shared/service/core";
import { Observable } from 'rxjs';
import { EPSPersonal } from './epspersonal.types';

@Injectable({
  providedIn: 'root'
})
export class EPSPersonalService extends BaseService<EPSPersonal> {

  constructor(http: HttpClient) {
    super(http);
    this.modulo = "rrhh_planilla";
    this.url = "/personal/eps";
  }
  override eliminarPer(idPersonal: string, id: any): Observable<any> {
    this.url = "/personal/" + idPersonal + "/eps";
    return super.eliminarPer(this.url, id);
  }
  override crearPer(idPersonal: string, data: any): Observable<any> {
    this.url = "/personal/" + idPersonal + "/eps";
    return super.crearPer(this.url, data);
  }

  override modificarPer(idPersonal: string, data: any, id: any): Observable<any> {
    this.url = "/personal/" + idPersonal + "/eps";
    return super.modificarPer(this.url, data, id);
  }
  /**
     * Get paginador
     *
     *
     * @param page
     * @param size
     * @param sort
     * @param order
     * @param search
     */
  override paginador(page: number = 0, size: number = 10, sort: string = 'nombre', order: 'asc' | 'desc' | '' = 'asc', search: string = '', paramsDinamic?: HttpParams):
    Observable<{ pagination: BasePagination; listaResultado: EPSPersonal[] }> {
    this.modulo = "rrhh_planilla"
    this.url = "/personal/eps";
    return super.paginadorPer(this.url, page, size, sort, order, search, paramsDinamic);
  }
}
