/**
 * La Class RegistroNotaService.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import {BaseService} from "../../../base/base.service";
import { Observable } from 'rxjs';

@Injectable()
export class RegistroNotaService extends BaseService {

  constructor(http: HttpClient,router : Router) { 
    super(http,router);
    this.url = "nota/registroNota";
  }

  obtenerRegistroNota(idDetMallaCurricular : string , idAlumno : string ,esActaEvaluacionFinalAplazado : boolean) : Observable<any> {
    this.url = "nota/detRegistroNota";
    if (idAlumno == null || idAlumno == "") {
      idAlumno = "%20";
    }
    return this.get(this.url + "/obtenerRegistroNota/" + idDetMallaCurricular + "/" + idAlumno + "/" + esActaEvaluacionFinalAplazado + "");
  }

  obtenerRegistroNotaDataSourceSettings(idDetMallaCurricular : string , idAlumno : string ,esActaEvaluacionFinalAplazado : boolean) : Observable<any> {
    this.url = "nota/detRegistroNota";
    if (idAlumno == null || idAlumno == "") {
      idAlumno = "%20";
    }
    return this.get(this.url + "/obtenerRegistroNotaDataSourceSettings/" + idDetMallaCurricular + "/" + idAlumno + "/" + esActaEvaluacionFinalAplazado + "");
  }

  listaCriterioEvaluacion( estado : string, idDetMallaCurricular? : string) : Observable<any> {
    this.url = "matricula/criterioEvaluacion";
    return this.get(this.url + "/listaCriterioEvaluacion/"  + estado +"/"+ idDetMallaCurricular +"");
  }  

}
