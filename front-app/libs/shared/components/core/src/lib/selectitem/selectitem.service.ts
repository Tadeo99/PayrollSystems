/**
 * La Class ItemService.
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
import { BasePagination, BaseService, SelectItemVO } from "@ng-mf/shared/service/core";
import { Observable, tap } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class SelectItemService extends BaseService<SelectItemVO> {

  constructor(http: HttpClient) {
    super(http);
    this.modulo = "common";

  }

  paginadorPerByGrupo(module: string = '', groupName: string = '', id: string = '', page: number = 0, size: number = 5, sort: string = 'nombre', order: 'asc' | 'desc' | '' = 'asc', search: string = '', paramsDinamic?: HttpParams):
    Observable<{ pagination: BasePagination; listaResultado: SelectItemVO[] }> {
    if (!paramsDinamic) {
      paramsDinamic = new HttpParams();
    }
    if (module != null && module.trim.length == 0) {
      this.modulo = "common";
    }
    this.modulo = module;
    paramsDinamic = paramsDinamic.set("id", id);
    this.url = "/group/" + groupName + "/items";
    return super.paginadorPer(this.url, page, size, sort, order, search, paramsDinamic);
  }
}
