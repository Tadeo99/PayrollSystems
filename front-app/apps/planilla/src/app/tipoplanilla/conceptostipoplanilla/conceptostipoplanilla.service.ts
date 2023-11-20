/**
 * La Class ConceptosTipoPlanillaService.
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
import { ConceptosTipoPlanilla } from './conceptostipoplanilla.types';

@Injectable({
  providedIn: 'root'
})
export class ConceptosTipoPlanillaService extends BaseService<ConceptosTipoPlanilla> {

  constructor(http: HttpClient) {
    super(http);
    this.modulo = "rrhh_planilla";
    this.url = "/concepto";
  }
  override eliminarPer(idTipoPlanilla: string, id: any): Observable<any> {
    this.url = "/tipoPlanilla/" + idTipoPlanilla + "/concepto";
    return super.eliminarPer(this.url, id);
  }
  override crearPer(idTipoPlanilla: string, data: any): Observable<any> {
    this.url = "/tipoPlanilla/" + idTipoPlanilla + "/concepto";
    return super.crearPer(this.url, data);
  }

  override modificarPer(idTipoPlanilla: string, data: any, id: any): Observable<any> {
    this.url = "/tipoPlanilla/" + idTipoPlanilla + "/concepto";
    return super.modificarPer(this.url, data, id);
  }
  /**
     * Get products
     *
     *
     * @param page
     * @param size
     * @param sort
     * @param order
     * @param search
     */
  override paginadorPer(idTipoPlanilla: string, page: number = 0, size: number = 10, sort: string = 'nombre', order: 'asc' | 'desc' | '' = 'asc', search: string = '', paramsDinamic?: HttpParams):
    Observable<{ pagination: BasePagination; listaResultado: ConceptosTipoPlanilla[] }> {
    this.url = "/tipoPlanilla/" + idTipoPlanilla + "/conceptos";
    return super.paginadorPer(this.url, page, size, sort, order, search, paramsDinamic);
  }
}
