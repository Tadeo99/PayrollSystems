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
import { VariableConfDet } from './variableconfdet.types';

@Injectable({
  providedIn: 'root'
})
export class VariableConfDetService extends BaseService<VariableConfDet> {

  constructor(http: HttpClient) {
    super(http);
    this.modulo = "rrhh_planilla";
    this.url = "/variableConf";
  }
  eliminarPerSub(idTipoPlanilla: string, idVariableConf: string, id: any): Observable<any> {
    this.url = "/tipoPlanilla/" + idTipoPlanilla + "/variableGrupo/" + idVariableConf + "/variable";
    return super.eliminarPer(this.url, id);
  }
  crearPerSub(idTipoPlanilla: string, idVariableConf: string, data: any): Observable<any> {
    this.url = "/tipoPlanilla/" + idTipoPlanilla + "/variableGrupo/" + idVariableConf + "/variable";
    return super.crearPer(this.url, data);
  }

  modificarPerSub(idTipoPlanilla: string, idVariableConf: string, data: any, id: any): Observable<any> {
    this.url = "/tipoPlanilla/" + idTipoPlanilla + "/variableGrupo/" + idVariableConf + "/variable";
    return super.modificarPer(this.url, data, id);
  }
  /**
     * Get paginado
     *
     *
     * @param page
     * @param size
     * @param sort
     * @param order
     * @param search
     */
  paginadorPerSub(idTipoPlanilla: string, idVariableConf: string, page: number = 0, size: number = 10, sort: string = 'nombre', order: 'asc' | 'desc' | '' = 'asc', search: string = '', paramsDinamic?: HttpParams):
    Observable<{ pagination: BasePagination; listaResultado: VariableConfDet[] }> {
    this.url = "/tipoPlanilla/" + idTipoPlanilla + "/variableGrupo/" + idVariableConf + "/variables";
    return super.paginadorPer(this.url, page, size, sort, order, search, paramsDinamic);
  }
}
