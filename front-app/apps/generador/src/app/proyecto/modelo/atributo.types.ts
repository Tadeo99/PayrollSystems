import { Modelo } from './modelo.types';

/**
 * La Class Modelo.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Atributo {
  /** El id config tabla clasess det. */
  idAtributo: string;

  /** El config tabla class. */
  modelo: Modelo;

  /** El nombre columna. */
  columna: string;

  /** El nombre atributo. */
  atributo: string;

  /** El es null. */
  esNull: string;

  /** El type. */
  type: string;

  /** El length. */
  length: number;

  /** El comentario columna. */
  comentario: string;

  /** El tipo l lave. */
  tipoLLave: string;

  /** El nombre tabla referencia. */
  idModeloRef: string;

  /** El mostrar grilla. */
  mostrarGrilla: string;

  /** El requiered filtro. */
  requieredFiltro: string;

  /** El requiered frm. */
  requieredFrm: string;

  /** El tipo componente. */
  tipoComponente: number;

  /** El indice. */
  indice: string;

  /** El indice grupo. */
  indiceGrupo: string;

  /** El alter add column. */
  alterAdd: string;

  /** El alter mod column. */
  alterMod: string;

  estado: string;

  descripcionView: string;
}
