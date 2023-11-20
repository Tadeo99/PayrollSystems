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

import { BasePagination, BaseService, BsNavigationItem } from "@ng-mf/shared/service/core";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConfiguracionService extends BaseService<BsNavigationItem> {

  constructor(http: HttpClient) {
    super(http);
    this.modulo = "rrhh_planilla";
    this.url = "/tipoPlanilla";
  }

  getVariables(idTipoPlanilla: string, page: number = 0, size: number = 10, sort: string = 'nombre', order: 'asc' | 'desc' | '' = 'asc', search: string = '', paramsDinamic?: HttpParams):
    Observable<{ pagination: BasePagination; listaResultado: BsNavigationItem[] }> {
    this.url = "/tipoPlanilla/" + idTipoPlanilla + "/variables/aplicacion";
    return super.paginadorPer(this.url, page, size, sort, order, search, paramsDinamic);
  }
}
