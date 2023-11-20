/**
 * La Class EmpresaService.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:53 COT 2021
 * @since BUILDERP-CORE 2.1
 */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import {BaseService} from "../../../base/base.service";
import { Observable } from 'rxjs/internal/Observable';

@Injectable()
export class EmpresaService extends BaseService {

  constructor(http: HttpClient,router : Router) { 
    super(http,router);
    this.url = "pago/empresa";
  }
  public findEmpresa(empresa: any): Observable<any> {
    return this.post(this.url + "/findEmpresa",empresa);
  }
  consultaDocumentoSunat(nroDoc: string): Observable<any> {     
    return this.get(this.url + "/consultaDocumentoSunat/" + nroDoc + "");
  }
}
