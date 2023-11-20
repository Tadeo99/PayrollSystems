/**
 * La Class CommonService.
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

@Injectable({
  providedIn: 'root'
})
export class CommonService extends BaseService<any> {
  public listaItemSelectItemMap: Map<any, SelectItemVO[]> = new Map<any, SelectItemVO[]>();
  public listaSelectItemVOMap: Map<any, SelectItemVO[]> = new Map<any, SelectItemVO[]>();
  public escuelaNroCicloMap: Map<string, number> = new Map<string, number>();

  public urlCommonService = "common/item";

  public ubigeoMap: Map<string, string> = new Map<string, string>();
  public listaUbigeoMap: Map<string, SelectItemVO[]> = new Map<string, SelectItemVO[]>();

  constructor(http: HttpClient) {
    super(http);
    this.url = "";
  }

  async listaDataDinamic(idEntidad: any, idTuplaEntidad: any): Promise<any[]> {
    let resultado: any[] = [];
    this.url = "common/configuracionAtributo";
    let params: HttpParams = new HttpParams()
    params = params.append("id", idEntidad);
    params = params.append("idTupla", idTuplaEntidad);
    await this.get(this.url + "/listar", params).toPromise().then(
      data => {
        resultado = data.listaResultado;
      }
    );
    return Promise.resolve(resultado);
  }

  async obtenerListaItemSelectItemMap(paramsTemp: Map<any, any>): Promise<boolean> {
    this.url = this.urlCommonService;
    let params: HttpParams = new HttpParams();
    paramsTemp.forEach((entryVal: any, entryKey: any) => {
      if (!this.existListaItemSelectItem(entryKey)) {
        params = params.append("idListaItems", entryKey);
      }
    });

    if (!params.has("idListaItems")) {
      return Promise.resolve(true);
    }
    await this.get(this.url + "/listaItemSelectItemMap", params).toPromise().then(
      data => {
        //if (this.isProcesoOK(data)) {
        this.setListaItemSelectItem(data.objetoResultado);
      }
    );
    return Promise.resolve(true);

  }
  async obtenerListaSelectItemVOMap(keyService: string, paramsTemp: Map<any, any>, paramsAdicional?: Map<any, any>): Promise<boolean> {
    this.url = keyService;
    let params: HttpParams = new HttpParams();
    paramsTemp.forEach((entryVal: any, entryKey: any) => {
      if (!this.existListaSelectItemVO(entryKey)) {
        params = params.append("groupName", entryKey);
      }
    });
    if (paramsAdicional != null) {
      paramsAdicional?.forEach((entryVal: any, entryKey: any) => {
        params = params.append(entryKey, entryVal);
      });
    }

    if (!params.has("groupName")) {
      return Promise.resolve(true);
    }
    await this.get(this.url + "/group/items", params).toPromise().then(
      data => {
        this.setListaSelectItemVO(data.objetoResultado);
      }
    );
    return Promise.resolve(true);
  }
  getListaItemSelectItem(idListaItems: any): SelectItemVO[] | undefined {
    return this.listaItemSelectItemMap.get(idListaItems);
  }

  obtenerItem(itemType: any, id: any): SelectItemVO {
    let resultado = { id: "", nombre: "", descripcion: "0", checked: false };
    const resultadoTemp = this.listaItemSelectItemMap.get(itemType);
    resultadoTemp?.forEach((selectItemVO) => {
      if (selectItemVO.id == id) {
        resultado = selectItemVO;
      }
    });
    return resultado;
  }
  getListaSelectVOItem(keySelectItem: any): SelectItemVO[] | undefined {
    return this.listaSelectItemVOMap.get(keySelectItem);
  }

  removeListaSelectVOItem(keySelectItem: any) {
     this.listaSelectItemVOMap.delete(keySelectItem);
  }

  existListaItemSelectItem(idListaItems: any): boolean {
    return this.listaItemSelectItemMap.has(idListaItems);
  }

  existListaSelectItemVO(keySelectItem: any): boolean {
    return this.listaSelectItemVOMap.has(keySelectItem);
  }

  setListaItemSelectItem(data: any): void {
    Object.keys(data).forEach((entryKey) => {
      this.listaItemSelectItemMap.set(Number.parseInt(entryKey + ""), data[entryKey]);
    });
  }
  setListaSelectItemVO(data: any): void {
    Object.keys(data).forEach((entryKey) => {
      this.listaSelectItemVOMap.set(entryKey + "", data[entryKey]);
    });
  }

  async obtenerAnhioyEstadoAsync(estado: any): Promise<any> {
    let resultado: any = null;
    this.url = "common/anhio";
    let params: HttpParams = new HttpParams();
    params = params.set('estado', estado);
    await this.get(this.url + "/findByEstado", params).toPromise().then(
      data => {
        resultado = data.objetoResultado;
      }
    );
    return Promise.resolve(resultado);
  }

  generarMap(listaTmp: SelectItemVO[], isDescripcion?: boolean): Map<any, string> {
    const resulMap = new Map<any, string>();
    listaTmp?.forEach((data) => {
      resulMap.set(data.id, isDescripcion === true ? data.descripcion : data.nombre);
    });
    return resulMap;
  }

  generarUbigeoMap() {
    this.listaUbigeoMap = new Map<string, SelectItemVO[]>();
    const listaProvinviaTmp = this.getListaSelectVOItem("provincia");
    const listaDistritoTmp = this.getListaSelectVOItem("distrito");
    listaProvinviaTmp?.forEach((data) => {
      const key = data.descripcion;
      this.ubigeoMap.set(data.id, key);
      if (!this.listaUbigeoMap.has(key)) {
        const value: SelectItemVO[] = [];
        value.push(data);
        this.listaUbigeoMap.set(key, value);
      } else {
        const value = this.listaUbigeoMap.get(key);
        if (value) {
          value?.push(data);
          this.listaUbigeoMap.set(key, value);
        }
      }

    });

    listaDistritoTmp?.forEach((data) => {
      const key = data.descripcion;
      this.ubigeoMap.set(data.id, key);
      if (!this.listaUbigeoMap.has(key)) {
        const value: SelectItemVO[] = [];
        value.push(data);
        this.listaUbigeoMap.set(key, value);
      } else {
        const value = this.listaUbigeoMap.get(key);
        if (value) {
          value?.push(data);
          this.listaUbigeoMap.set(key, value);
        }
      }

    });

  }

  public generarProvincia(selected: any): SelectItemVO[] | undefined {
    if (this.listaUbigeoMap.has(selected)) {
      return this.listaUbigeoMap.get(selected);
    }
    return [];
  }
  public generarDistrito(selected: any): SelectItemVO[] | undefined {
    if (this.listaUbigeoMap.has(selected)) {
      return this.listaUbigeoMap.get(selected);
    }
    return [];
  }

}
