/**
 * La Class AsignaPostulanteService.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Nov 05 22:14:39 COT 2020
 * @since BUILDERP-CORE 2.1
 */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import {BaseService} from "../../../base/base.service";

@Injectable()
export class AsignaPostulanteService extends BaseService {

  constructor(http: HttpClient,router : Router) { 
    super(http,router);
    this.url = "admision/asignaPostulante";
  }
}
