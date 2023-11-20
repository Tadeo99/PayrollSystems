/**
 * La Class MatriculaService.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:50 COT 2021
 * @since BUILDERP-CORE 2.1
 */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import { BaseService } from "../../../base/base.service";
import { Observable } from 'rxjs';

@Injectable()
export class MatriculaService extends BaseService {

  constructor(http: HttpClient, public router: Router) {
    super(http, router);
    this.url = "matricula/matricula";
  }

  obtenerMatricula(idAnhio: number, idAlumno: string, idTurno: number): Observable<any> {
    return this.get(this.url + "/obtenerMatricula/" + idAnhio + "/" + idAlumno + "/" + idTurno);
  }
}
