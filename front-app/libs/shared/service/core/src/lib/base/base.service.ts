import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { BasePagination } from './base.types';


@Injectable({
  providedIn: 'root',
})
export class BaseService<T> {
  public url = "";
  public modulo = "";
  public totalResults = 0;
  public _pagination: BehaviorSubject<BasePagination | null> = new BehaviorSubject<BasePagination | null>(null);
  public _listaData: BehaviorSubject<T[]> = new BehaviorSubject<T[]>([]);
  constructor(public http: HttpClient) {
  }

  get(path: string, paramsParam?: HttpParams): Observable<any> {
    return this.http.get(path, { params: paramsParam });
  }

  put(path: string, body: object = {}): Observable<any> {
    return this.http.put(path, JSON.stringify(body));
  }

  post(path: string, body: object = {}): Observable<any> {
    return this.http.post(path, JSON.stringify(body));
  }
  postMultiPart(path: string, body: any = {}, headers: HttpHeaders): Observable<any> {
    return this.http.post(path, body);
  }

  delete(path: string): Observable<any> {
    return this.http.delete(path);
  }

  /* paginador(searchDataPrvovider: any, params?: HttpParams, moduloPer?: string): Observable<any> {
     if (!params) {
       params = new HttpParams();
     }
     params = params.set("search", searchDataPrvovider.search);
     params = params.set("offSet", searchDataPrvovider.offset);
     params = params.set("startRow", searchDataPrvovider.startRow);
     params = params.set("currentPage", searchDataPrvovider.currentPage);
     params = params.set("sortFields", searchDataPrvovider.sortFields);
     params = params.set("sortDirections", searchDataPrvovider.sortDirections);
     if (moduloPer != null) {
       this.modulo = moduloPer;
     }
     return this.get(this.modulo + this.url + "/", params);
   }*/

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
  paginador(page: number = 0, size: number = 10, sort: string = 'nombre', order: 'asc' | 'desc' | '' = 'asc', search: string = '', paramsDinamic?: HttpParams):
    Observable<{ pagination: BasePagination; listaResultado: T[] }> {
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
    return this.http.get<{ pagination: BasePagination; listaResultado: T[] }>(this.modulo + this.url + '/', {
      params: paramsDinamic
    }).pipe(
      tap((response) => {
        this._pagination.next(response.pagination);
        this._listaData.next(response.listaResultado);
      })
    );
  }


  buscarID(id: any): Observable<any> {
    return this.get(this.modulo + this.url + "/" + id);
  }

  crear(data: any, moduloPer?: string): Observable<any> {
    if (moduloPer != null) {
      this.modulo = moduloPer;
    }
    return this.post(this.modulo + this.url, data);
  }

  modificar(data: any, id: any, moduloPer?: string): Observable<any> {
    if (moduloPer != null) {
      this.modulo = moduloPer;
    }
    return this.put(this.modulo + this.url + "/" + id, data);
  }

  eliminar(id: any, moduloPer?: string): Observable<any> {
    return this.delete(this.getUrl(id, moduloPer));
  }

  private getUrl(id: any, moduloPer?: string) {
    if (moduloPer != null) {
      this.modulo = moduloPer;
    }
    return this.modulo + this.url + "/" + id;
  }
  /**
      * Getter for pagination
      */
  get pagination$(): Observable<BasePagination | null> {
    return this._pagination.asObservable();
  }

  /**
    * Getter for products
    */
  get listaData$(): Observable<T[]> {
    return this._listaData.asObservable();
  }


  eliminarPer(url: string, id: any): Observable<any> {
    this.url = url;
    return this.delete(this.modulo + this.url + "/" + id);
  }
  crearPer(url: string, data: any): Observable<any> {
    this.url = url;
    return this.post(this.modulo + this.url, data);
  }

  modificarPer(url: string, data: any, id: any): Observable<any> {
    this.url = url;
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
  paginadorPer(url: string, page: number = 0, size: number = 10, sort: string = 'nombre', order: 'asc' | 'desc' | '' = 'asc', search: string = '', paramsDinamic?: HttpParams):
    Observable<{ pagination: BasePagination; listaResultado: T[] }> {
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
    this.url = url;
    return this.http.get<{ pagination: BasePagination; listaResultado: T[] }>(this.modulo + this.url + '/', {
      params: paramsDinamic
    }).pipe(
      tap((response) => {
        this._pagination.next(response.pagination);
        this._listaData.next(response.listaResultado);
      })
    );
  }

}