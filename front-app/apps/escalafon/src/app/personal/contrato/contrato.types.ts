import { Personal } from "../personal.types";
import { DetalleContrado } from "./detallecontrado.types";

/**
 * La Class Contrato.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Contrato {

  /** El id contrato. */
  idContrato: string;

  /** El personal. */
  personal: Personal;

  /** El item by tipo contrato. */
  idItemByTipoContrato: number;
  itemByTipoContrato: any;

  /** El fecha inicio. */
  fechaInicio: Date;

  /** El fecha final. */
  fechaFinal: Date;

  /** El nrodoc. */
  nrodoc: string;

  /** El estado. */
  estado: string;

  /** El contrato detalle contrado list. */
  contratoDetalleContradoList: DetalleContrado[];

  descripcionView: string;
}