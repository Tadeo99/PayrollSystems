/**
 * La Class AdmisionService.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu May 18 12:31:06 COT 2017
 * @since SIAA-CORE 2.1
 */
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BaseService, SelectItemVO } from '@ng-mf/shared/service/core';
import { Sede } from '../sede/sede.types';

@Injectable({
  providedIn: 'root'
})
export class RegistrarAdmisionService extends BaseService<any> {
  public listaGradoSelectItemMap: Map<any, SelectItemVO[]> = new Map<any, SelectItemVO[]>();
  public dataMap:Map<any, any> = new Map<any, any>();

  public urlGradoService = "admision/grado";
  public urlSedeService = "admision/sede";

  constructor(http: HttpClient) {
    super(http);
    this.url = "";
  }

  async listarVacantesDisponibles(idUbigeo: string): Promise<Sede[]> {
    let resultado: Sede[] = [];
    this.url = this.urlSedeService;
    const params = new HttpParams()
    //params =  params.append("id",idEntidad);
    //params =  params.append("idTupla",idTuplaEntidad);
    await this.get(this.url + "/vacantes/" + idUbigeo, params).toPromise().then(
      data => {
        resultado = data.listaResultado;
      }
    );
    return Promise.resolve(resultado);
  }


  async obtenerListaGradoSelectItemMap(): Promise<boolean> {
    this.url = this.urlGradoService;
    const params = new HttpParams();
    await this.get(this.url + "/items", params).toPromise().then(
      data => {
        this.setListaItemSelectItem(data.objetoResultado);
      }
    );
    return Promise.resolve(true);
  }

  getListaItemSelectItem(idNivel: any): SelectItemVO[] | undefined {
    return this.listaGradoSelectItemMap.get(idNivel);
  }

  existListaItemSelectItem(idNivel: any): boolean {
    return this.listaGradoSelectItemMap.has(idNivel);
  }

  setListaItemSelectItem(data: any): void {
    Object.keys(data).forEach((entryKey) => {
      this.listaGradoSelectItemMap.set(entryKey, data[entryKey]);
    });
  }

}
