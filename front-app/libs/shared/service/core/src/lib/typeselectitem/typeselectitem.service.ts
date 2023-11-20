import { Injectable } from '@angular/core';
import { SelectItemVO } from "../vo/selectitem.vo";


/**
 * La Class TypeSelectItemService.
 * <ul>
 * <li>Copyright 2016 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Feb 21 12:42:17 COT 2017
 * @since BuildErp 1.0
 */
@Injectable({
	providedIn: 'root',
})
export class TypeSelectItemService {

	public listaEstado: SelectItemVO[] = [];
	public estadoMap: Map<string, string> = new Map<string, string>();

	public listaGenero: SelectItemVO[] = [];
	public generoMap: Map<string, string> = new Map<string, string>();

	public listaTipoPDT: SelectItemVO[] = [];
	public tipoPDTMap: Map<string, string> = new Map<string, string>();

	public listaTipoUbigeo: SelectItemVO[] = [];
	public listaTipoUbigeoMap: Map<string, string> = new Map<string, string>();

	public listaRespuestaNatural: SelectItemVO[] = [];
	public respuestaNaturalMap: Map<string, string> = new Map<string, string>();

	public listaTipoTecnologia: SelectItemVO[] = [];
	public tipoTecnologiaMap: Map<string, string> = new Map<string, string>();

	public listaEstadoVacaciones: SelectItemVO[] = [];
	public estadoVacacionesMap: Map<string, string> = new Map<string, string>();

	public listaMes: SelectItemVO[] = [];
	public mesesMap: Map<number, string> = new Map<number, string>();

	public listaComponente: SelectItemVO[] = [];
	public componenteMap: Map<number, string> = new Map<number, string>();

	public listaTipoLlave: SelectItemVO[] = [];
	public tipoLlaveMap: Map<string, string> = new Map<string, string>();

	constructor() {
		console.log("TypeSelectItemService==> ");

		this.listaEstado.push({ id: "", nombre: "[Seleccionar]", descripcion: "[Seleccionar]", checked: false });
		this.listaEstado.push({ id: "A", nombre: "Activo", descripcion: "Activo", checked: false });
		this.listaEstado.push({ id: "I", nombre: "Inactivo", descripcion: "Inactivo", checked: false });
		this.estadoMap = this.generarMap(this.listaEstado);

		this.listaGenero.push({ id: "", nombre: "[Seleccionar]", descripcion: "[Seleccionar]", checked: false });
		this.listaGenero.push({ id: "M", nombre: "Masculino", descripcion: "Masculino", checked: false });
		this.listaGenero.push({ id: "F", nombre: "Femenino", descripcion: "Femenino", checked: false });
		this.generoMap = this.generarMap(this.listaGenero);

		this.listaTipoUbigeo.push({ id: "DE", nombre: "Departamento", descripcion: "Departamento", checked: false });
		this.listaTipoUbigeo.push({ id: "PR", nombre: "Provincia", descripcion: "Provincia", checked: false });
		this.listaTipoUbigeo.push({ id: "DI", nombre: "Distrito", descripcion: "Distrito", checked: false });
		this.listaTipoUbigeoMap = this.generarMap(this.listaTipoUbigeo);

		this.listaRespuestaNatural.push({ id: "S", nombre: "SI", descripcion: "SI", checked: false });
		this.listaRespuestaNatural.push({ id: "N", nombre: "NO", descripcion: "NO", checked: false });
		this.respuestaNaturalMap = this.generarMap(this.listaRespuestaNatural);

		this.listaTipoTecnologia.push({ id: "MDB", nombre: "Motor de Base de Datos", descripcion: "MDB", checked: false });
		this.listaTipoTecnologia.push({ id: "BACKEND", nombre: "Back end", descripcion: "BACKEND", checked: false });
		this.listaTipoTecnologia.push({ id: "FRONTEND", nombre: "Front end", descripcion: "FRONTEND", checked: false });
		this.tipoTecnologiaMap = this.generarMap(this.listaTipoTecnologia);

		this.listaTipoPDT.push({ id: "I", nombre: "Ingreso", descripcion: "Ingreso", checked: false });
		this.listaTipoPDT.push({ id: "D", nombre: "Descuento", descripcion: "Descuento", checked: false });
		this.listaTipoPDT.push({ id: "T", nombre: "Aportaciones Trabajador", descripcion: "Aportaciones Trabajador", checked: false });
		this.listaTipoPDT.push({ id: "A", nombre: "Aportaciones Empleador", descripcion: "Aportaciones Empleador", checked: false });
		this.tipoPDTMap = this.generarMap(this.listaTipoPDT);


		this.listaEstadoVacaciones.push({ id: "", nombre: "[Seleccionar]", descripcion: "[Seleccionar]", checked: false });
		this.listaEstadoVacaciones.push({ id: "E", nombre: "En solicitud", descripcion: "En solicitud", checked: false });
		this.listaEstadoVacaciones.push({ id: "A", nombre: "Aprobada", descripcion: "Aprobada", checked: false });
		this.listaEstadoVacaciones.push({ id: "R", nombre: "Rechazada", descripcion: "Rechazada", checked: false });
		this.estadoVacacionesMap = this.generarMap(this.listaEstado);


		this.listaMes.push({ id: null, nombre: "[Seleccionar]", descripcion: "[Seleccionar]", checked: false });
		this.listaMes.push({ id: 1, nombre: "Enero", descripcion: "Enero", checked: false });
		this.listaMes.push({ id: 2, nombre: "Febrero", descripcion: "Febrero", checked: false });
		this.listaMes.push({ id: 3, nombre: "Marzo", descripcion: "Marzo", checked: false });
		this.listaMes.push({ id: 4, nombre: "Abril", descripcion: "Abril", checked: false });
		this.listaMes.push({ id: 5, nombre: "Mayo", descripcion: "Mayo", checked: false });
		this.listaMes.push({ id: 6, nombre: "Junio", descripcion: "Junio", checked: false });
		this.listaMes.push({ id: 7, nombre: "Julio", descripcion: "Julio", checked: false });
		this.listaMes.push({ id: 8, nombre: "Agosto", descripcion: "Agosto", checked: false });
		this.listaMes.push({ id: 9, nombre: "Septiembre", descripcion: "Septiembre", checked: false });
		this.listaMes.push({ id: 10, nombre: "Osctubre", descripcion: "Osctubre", checked: false });
		this.listaMes.push({ id: 11, nombre: "Noviembre", descripcion: "Noviembre", checked: false });
		this.listaMes.push({ id: 12, nombre: "Diciembre", descripcion: "Diciembre", checked: false });
		this.mesesMap = this.generarMap(this.listaMes);


		this.listaComponente.push({ id: null, nombre: "[Seleccionar]", descripcion: "[Seleccionar]", checked: false });
		this.listaComponente.push({ id: 1, nombre: "Combo", descripcion: "Combo", checked: false });
		this.listaComponente.push({ id: 2, nombre: "Radio Button", descripcion: "Radio Buttton", checked: false });
		this.listaComponente.push({ id: 3, nombre: "Lupa", descripcion: "Lupa", checked: false });
		this.componenteMap = this.generarMap(this.listaComponente);


		this.listaTipoLlave.push({ id: "", nombre: "[Seleccionar]", descripcion: "[Seleccionar]", checked: false });
		this.listaTipoLlave.push({ id: "PK", nombre: "Primary Key", descripcion: "PK", checked: false });
		this.listaTipoLlave.push({ id: "FK", nombre: "Foren Key", descripcion: "FK", checked: false });
		this.tipoLlaveMap = this.generarMap(this.listaTipoLlave);


	}

	public generarMap(listaTemp: any): Map<any, string> {
		const resultado: Map<any, string> = new Map<any, string>();
		for (const objData of listaTemp) {
			resultado.set(objData.id, objData.nombre);
		}
		return resultado;
	}

	private getDescription(locale: string, value: string): string {
		return value;//return ResourceUtil.getString(locale, ConstanteTypeUtil.BUNDLE_NAME_TYPE, value);
	}
	//get y set
}