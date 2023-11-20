/**
 * La Class EPSConf.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface EPSConf {

  /** El id eps. */
  idEPSConf: string;

  /** El item by eps. */
  idItemByEps: number;
  itemByEps: any;

  planBase: number;

  planAdicional1: number;

  planAdicional2: number;

  planAdicional3: number;

  planAdicional4: number;

  /** El estado. */
  estado: string;

  descripcionView: string;
}