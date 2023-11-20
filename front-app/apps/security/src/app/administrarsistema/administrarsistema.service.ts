/**
 * La Class PropertiesService.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Mon Oct 16 14:31:03 COT 2017
 * @since SIAA-CORE 2.1
 */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import {BaseService} from "../../../base/base.service";

@Injectable()
export class AdministrarSistemaService extends BaseService {

  constructor(http: HttpClient,router : Router) { 
    super(http,router);
    this.url = "security/properties";
  }
}
