import { ConceptoPdt } from "../../conceptopdt/conceptopdt.types";

/**
 * La Class Vacaciones.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Vacaciones {

  /** El id vacaciones. */
  idVacaciones: string;

  /** El personal. */
  idPersonal: string;
  personal: any;

  fechaInicio: Date;

  fechaFin: Date;

  dias : number;

  /**
   * El anhio.
   * 
   */
  idAnhio: number;

  fechaReg: Date;

  fechaAprobacion: Date;

  idpersonalAprob: string;

  /** El estado. */
  estado: string;

  observacion: string;

  descripcionView: string;
}