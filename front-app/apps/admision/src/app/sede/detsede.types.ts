import { Grado } from "../grado/grado.types";
import { Sede } from "../sede/sede.types";
/**
 * La Class DetSede.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface DetSede {

  /** El id det sede. */
  idDetSede: string;

  /** El sede. */
  sede?: Sede;

  /** El grado. */
  grado: Grado;

  /** El nro vacante. */
  nroVacante: number;

  /** El estado. */
  estado: string;

  descripcionView?: string;
  checked?: boolean;
}