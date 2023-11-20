import { GrupoNegocio } from '../gruponegocio/gruponegocio.types';
import { Atributo } from './atributo.types';

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
export interface Modelo {
  /** El id config tabla class. */
  idModelo: string;

  /** El config grupo servicio. */
  grupoNegocio: GrupoNegocio;

  /** El sequence tabla. */
  sequence: string;

  /** El nombre tabla. */
  nombreTabla: string;

  /** El nombre clase. */
  nombreClase: string;

  /** El codigo. */
  codigo: string;

  /** El comentario tabla. */
  comentario: string;

  /** El notas. */
  notas: string;

  /** El config tabla class config tabla class det list. */
  listaAtributo: Atributo[];

  estado: string;

  descripcionView: string;
}
