/**
 * La Class ListaItemsService.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Wed Oct 25 11:30:30 COT 2017
 * @since SIAA-CORE 2.1
 */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { BaseService } from "@ng-mf/shared/service/core";
import { ListaItems } from './listaitems.types';

@Injectable({
  providedIn: 'root'
})
export class ListaItemsService extends BaseService<ListaItems> {

  constructor(http: HttpClient) {
    super(http);
    this.modulo = "common";
    this.url = "/listaItems";
  }
}
