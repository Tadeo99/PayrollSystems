import { Grado } from "../grado/grado.types";

/**
 * La Class Seccion.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Seccion {

  /** El id seccion. */
  idSeccion: number;

  /** El grado. */
  grado: Grado;

  /** El codigo. */
  codigo: string;

  /** El nombre. */
  nombre: string;

  estado: string;

  descripcionView: string;
}