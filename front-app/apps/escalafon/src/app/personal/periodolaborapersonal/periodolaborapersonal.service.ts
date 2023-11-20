/**
 * La Class PeriodoLaboraPersonalService.
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
import { HttpClient, HttpParams } from '@angular/common/http';

import { BasePagination, BaseService } from "@ng-mf/shared/service/core";
import { PeriodoLaboraPersonal } from './periodolaborapersonal.types';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PeriodoLaboraPersonalService extends BaseService<PeriodoLaboraPersonal> {

  constructor(http: HttpClient) {
    super(http);
    this.modulo = "rrhh_escalafon";
    this.url = "/periodoLaboral";
  }
  override eliminarPer(idPersonal: string, id: any): Observable<any> {
    this.url = "/personal/" + idPersonal + "/periodoLaboral";
    return this.delete(this.modulo + this.url + "/" + id);
  }
  override crearPer(idPersonal: string, data: any): Observable<any> {
    this.url = "/personal/" + idPersonal + "/periodoLaboral";
    return this.post(this.modulo + this.url, data);
  }

  override modificarPer(idPersonal: string, data: any, id: any): Observable<any> {
    this.url = "/personal/" + idPersonal + "/periodoLaboral";
    return this.put(this.modulo + this.url + "/" + id, data);
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
  override paginadorPer(idPersonal: string, page: number = 0, size: number = 10, sort: string = 'nombre', order: 'asc' | 'desc' | '' = 'asc', search: string = '', paramsDinamic?: HttpParams):
    Observable<{ pagination: BasePagination; listaResultado: PeriodoLaboraPersonal[] }> {
    if (!paramsDinamic) {
      paramsDinamic = new HttpParams();
    }
    paramsDinamic = paramsDinamic.set("startRow", page);
    paramsDinamic = paramsDinamic.set("offSet", size);
    paramsDinamic = paramsDinamic.set("page", page);
    paramsDinamic = paramsDinamic.set("size", size);
    paramsDinamic = paramsDinamic.set("sortFields", sort);
    paramsDinamic = paramsDinamic.set("sortDirections", order);
    paramsDinamic = paramsDinamic.set("search", search);
    this.url = "/personal/" + idPersonal + "/periodoLaborales";
    return this.http.get<{ pagination: BasePagination; listaResultado: PeriodoLaboraPersonal[] }>(this.modulo + this.url + '/', {
      params: paramsDinamic
    }).pipe(
      tap((response) => {
        this._pagination.next(response.pagination);
        this._listaData.next(response.listaResultado);
      })
    );
  }
}
