/**
 * La Class EntidadService.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sat Dec 23 17:16:37 COT 2017
 * @since SIAA-CORE 2.1
 */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import {BaseService} from "../../../base/base.service";

@Injectable()
export class EntidadSelectService extends BaseService {

  constructor(http: HttpClient,router : Router) { 
    super(http,router);
    this.url = this.modulo + "/entidad";
  }
}
