import { Personal } from "../personal.types";

/**
 * La Class HistorialCargoArea.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface HistorialCargoArea {

  /** El id historial cargo area. */
  idHistorialCargoArea: string;

  /** El personal. */
  personal: Personal;

  /** El item by cargo. */
  idItemByCargo: number;
  itemByCargo: any;

  /** El item by area. */
  idItemByArea: number;
  itemByArea: any;

  /** El fecha desde. */
  fechaDesde: Date;

  /** El observaciones. */
  observaciones: string;

  estado: string;

  descripcionView: string;
}