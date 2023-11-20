/**
 * La Class Grado.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Grado {

  /** El id grado. */
  idGrado: number;

  /** El item by nivel. */
  idItemByNivel: number;
  itemByNivel?: any;

  /** El codigo. */
  codigo?: string;

  /** El nombre. */
  nombre?: string;

  estado?: string;

  descripcionView?: string;
}