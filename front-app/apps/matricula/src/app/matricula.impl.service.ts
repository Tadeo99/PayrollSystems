/**
 * La Class AnhoSemestreService.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu May 18 12:31:06 COT 2017
 * @since SIAA-CORE 2.1
 */
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

import { BaseService } from "../../base/base.service";

@Injectable()
export class MatriculaServiceImpl extends BaseService {

  constructor(http: HttpClient, public router: Router) {
    super(http, router);
    this.url = "";
  }


  obtenerCursosPosiblesLlevar(params: HttpParams): Observable<any> {
    this.url = "matricula/detalleCargaAcademica";
    return this.get(this.url + "/obtenerCursosPosiblesLlevar/", params);
  }

  obtenerAnhioyEstado(estado: any): Observable<any> {
    this.url = "matricula/anhio";
    let params: HttpParams = new HttpParams();
    params = params.set('estado', estado);
    return this.get(this.url + "/obtenerAnhioyEstado", params);
  }


  generarReporteFichaMatricula(idAnhoSeemstre: number, idAlumno: string, idPeriodo: number): Observable<any> {
    this.url = "matricula/matricula";
    return this.get(this.url + "/generarReporteFichaMatricula/" + idAnhoSeemstre + "/" + idAlumno + "/" + idPeriodo + "");
  }

}

