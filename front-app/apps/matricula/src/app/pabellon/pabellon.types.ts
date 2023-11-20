/**
 * La Class Pabellon.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Pabellon {

  /** El id grupo. */
  idPabellon: number;

  /** El nombre. */
  descripcion: string;

  abreviatura: string;

  /** El estado. */
  estado: string;
  idEntidad: string;
  idSede: string;

  descripcionView: string;
}