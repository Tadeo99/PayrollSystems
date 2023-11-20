/**
 * La Class PersonalService.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
 import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BaseService } from "@ng-mf/shared/service/core";
import { Personal } from './personal.types';
 
 
 @Injectable({
   providedIn: 'root'
 })
 export class PersonalService extends BaseService<Personal> {
 
   constructor(http: HttpClient) {
     super(http);
     this.modulo = "rrhh_escalafon";
     this.url = "/personal";
   }
 }
 