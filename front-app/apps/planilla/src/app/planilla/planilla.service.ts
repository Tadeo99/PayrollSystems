/**
 * La Class PlanillaService.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:47 COT 2021
 * @since BUILDERP-CORE 2.1
 */
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BaseService } from '@ng-mf/shared/service/core';
import { Observable } from 'rxjs';
import { DetallePlanilla } from './detalleplanilla.type';

@Injectable()
export class PlanillaService extends BaseService<DetallePlanilla> {

  constructor(http: HttpClient) {
    super(http);
    this.url = "rrhh_planilla/planilla";
  }

  generarReportePlanillaBoletaIndividual(etallePlanilla: any): Observable<any> {
    return this.post("rrhh_planilla/reporte/boleta/individual", etallePlanilla);
  }

  generarReportePlanillaBoletaMasiva(detallePlanilla: any): Observable<any> {
    return this.post("rrhh_planilla/reporte/boleta/masivo", detallePlanilla);
  }

  generarPlanilla(objPlanilla: any): Observable<any> {
    return this.post("rrhh_planilla/proceso/planilla", objPlanilla);
  }

}
