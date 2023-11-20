import { Grado } from "../grado/grado.types";

/**
 * La Class Postulante.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface AsignaPostulante {

  /** El id asigna postulante. */
  idAsignaPostulante?: string;

  /** El apoderado. */
  apoderado: string;

  /** El postulante. */
  postulante: any;

  /** El sede. */
  sede?: string;

  /** El grado. */
  grado: Grado;

  /** El anho. */
  anho: number;

  /** El periodo. */
  periodo: string;

  /** El estado. */
  estado?: string;

  descripcionView?: string;

  checked?: boolean;
}