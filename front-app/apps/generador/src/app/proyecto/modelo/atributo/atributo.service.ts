/**
 * La Class plantillaService.
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

import { BasePagination, BaseService } from '@ng-mf/shared/service/core';
import { Observable, tap } from 'rxjs';
import { Atributo } from '../atributo.types';

@Injectable({
  providedIn: 'root',
})
export class PlantillaService extends BaseService<Atributo> {
  constructor(http: HttpClient) {
    super(http);
    this.modulo = 'generador';
    this.url = '/atributo';
  }
  eliminarPers(idProyecto: string, idModelo: string, id: any): Observable<any> {
    this.url = '/proyecto/' + idProyecto + '/modelo/' + idModelo + '/atributo';
    return this.delete(this.modulo + this.url + '/' + id);
  }
  crearPers(idProyecto: string, idModelo: string, data: any): Observable<any> {
    this.url = '/proyecto/' + idProyecto + '/modelo/' + idModelo + '/atributo';
    return this.post(this.modulo + this.url, data);
  }

  modificarPers(
    idProyecto: string,
    idModelo: string,
    data: any,
    id: any
  ): Observable<any> {
    this.url = '/proyecto/' + idProyecto + '/modelo/' + idModelo + '/atributo';
    return this.put(this.modulo + this.url + '/' + id, data);
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
  paginadorPers(
    idProyecto: string,
    idModelo: string,
    page: number = 0,
    size: number = 10,
    sort: string = 'nombre',
    order: 'asc' | 'desc' | '' = 'asc',
    search: string = '',
    paramsDinamic?: HttpParams
  ): Observable<{ pagination: BasePagination; listaResultado: Atributo[] }> {
    if (!paramsDinamic) {
      paramsDinamic = new HttpParams();
    }
    paramsDinamic = paramsDinamic.set('startRow', page);
    paramsDinamic = paramsDinamic.set('offSet', size);
    paramsDinamic = paramsDinamic.set('page', page);
    paramsDinamic = paramsDinamic.set('size', size);
    paramsDinamic = paramsDinamic.set('sortFields', sort);
    paramsDinamic = paramsDinamic.set('sortDirections', order);
    paramsDinamic = paramsDinamic.set('search', search);
    this.url = '/proyecto/' + idProyecto + '/modelo/' + idModelo + '/atributos';
    return this.http
      .get<{ pagination: BasePagination; listaResultado: Atributo[] }>(
        this.modulo + this.url + '/',
        {
          params: paramsDinamic,
        }
      )
      .pipe(
        tap((response) => {
          this._pagination.next(response.pagination);
          this._listaData.next(response.listaResultado);
        })
      );
  }
}
