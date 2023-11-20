/**
 * La Class VariableConfService.
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
import { VariableConf } from './variableconf.types';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VariableConfService extends BaseService<VariableConf> {

  constructor(http: HttpClient) {
    super(http);
    this.modulo = "rrhh_planilla";
    this.url = "/variableGrupo";
  }
  override eliminarPer(idTipoPlanilla: string, id: any): Observable<any> {
    this.url = "/tipoPlanilla/" + idTipoPlanilla + "/variableGrupo";
    return super.eliminarPer(this.url, id);
  }
  override crearPer(idTipoPlanilla: string, data: any): Observable<any> {
    this.url = "/tipoPlanilla/" + idTipoPlanilla + "/variableGrupo";
    return super.crearPer(this.url, data);
  }

  override modificarPer(idTipoPlanilla: string, data: any, id: any): Observable<any> {
    this.url = "/tipoPlanilla/" + idTipoPlanilla + "/variableGrupo";
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
    Observable<{ pagination: BasePagination; listaResultado: VariableConf[] }> {
    this.url = "/tipoPlanilla/" + idTipoPlanilla + "/variableGrupos";
    return super.paginadorPer(this.url, page, size, sort, order, search, paramsDinamic);
  }
}
