import { Institucion } from "../institucion/institucion.types";

/**
 * La Class Carrera.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Carrera {

  /** El id carrera. */
  idCarrera: number;

  /** El institucion. */
  institucion: Institucion;

  /** El codigo. */
  codigo: string;

  /** El nombre. */
  nombre: string;

  /** El estado. */
  estado: string;

  descripcionView: string;
}