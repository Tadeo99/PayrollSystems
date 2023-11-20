/**
 * La Class VentaService.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sat Dec 23 17:16:36 COT 2017
 * @since SIAA-CORE 2.1
 */

import { BaseService } from "../../../base/base.service";
import { Observable } from 'rxjs';
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { SunatDatos } from "../../../vo/sunatdatos.vo";
@Injectable()
export class FacturacionService extends BaseService {

  constructor(http: HttpClient, public router: Router) {
    super(http, router);
    this.url = "pago/controlPago";
  }

   

  public generarExtracionTXT() : Observable<any> {
    return this.post(this.url + "/generarExtracionTXT");
  } 


  generarComprobante(temp: SunatDatos): Observable<any> {
    return this.post(this.url + "/generarComprobante", temp);
  }

  enviarComprobante(temp: SunatDatos): Observable<any> {
    return this.post(this.url + "/enviarComprobante", temp);
  }

  eliminarBandeja(): Observable<any> {
    return this.post(this.url + "/eliminarBandeja");
  }

  actualizarBandeja(): Observable<any> {
    return this.post(this.url + "/actualizarBandeja");
  }




}
