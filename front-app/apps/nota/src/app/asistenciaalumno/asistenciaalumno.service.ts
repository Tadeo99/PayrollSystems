/**
 * La Class AsistenciaAlumnoService.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import {BaseService} from "../../../base/base.service";
import { Observable } from 'rxjs';

@Injectable()
export class AsistenciaAlumnoService extends BaseService {

  constructor(http: HttpClient,router : Router) { 
    super(http,router);
    this.url = "nota/asistenciaAlumno";
  }

  obtenerAsistencia(asistenciaAlumno : any) : Observable<any> {   
    return this.post(this.url + "/obtenerAsistenciaAlumno",asistenciaAlumno);
  }
}
