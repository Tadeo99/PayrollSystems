/**
 * La Class PersonalConceptoService.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:47 COT 2021
 * @since BUILDERP-CORE 2.1
 */
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BasePagination, BaseService } from '@ng-mf/shared/service/core';
import { PersonalConcepto } from './personalconcepto.types';
import { Observable } from 'rxjs';

@Injectable()
export class PersonalConceptoService extends BaseService<PersonalConcepto> {

  constructor(http: HttpClient) {
    super(http);
    this.modulo = "rrhh_planilla"
    this.url = "/personal/concepto";
  }
  override eliminarPer(idPersonal: string, id: any): Observable<any> {
    this.url = "/personal/" + idPersonal + "/concepto";
    return super.eliminarPer(this.url, id);
  }
  override crearPer(idPersonal: string, data: any): Observable<any> {
    this.url = "/personal/" + idPersonal + "/concepto";
    return super.crearPer(this.url, data);
  }

  override modificarPer(idPersonal: string, data: any, id: any): Observable<any> {
    this.url = "/personal/" + idPersonal + "/concepto";
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
    Observable<{ pagination: BasePagination; listaResultado: PersonalConcepto[] }> {
    this.modulo = "rrhh_planilla"
    this.url = "/personal/conceptos";
    return super.paginadorPer(this.url, page, size, sort, order, search, paramsDinamic);
  }

}
