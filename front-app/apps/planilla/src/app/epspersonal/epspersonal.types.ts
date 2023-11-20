
/**
 * La Class EPSPersonal.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface EPSPersonal {

  /** El id eps. */
  idEPSPersonal: string;

  /**
   * El personal.
   */
  idPersonal: string;
  personal: any;

  /** El item by eps. */
  idItemByEps: number;

  /**
   * El item by mes.
   */
  idItemByMes: number;

  /**
   * El anhio.
   * 
   */
  idAnhio: number;

  planMonto: number;

  creditoPct: number;

  sueldoPct: number;

  essaludPct: number;

  afiliados: number;

  descontar: number;

  descontarTrabajador: number;

  /** El estado. */
  estado: string;

  descripcionView: string;
}