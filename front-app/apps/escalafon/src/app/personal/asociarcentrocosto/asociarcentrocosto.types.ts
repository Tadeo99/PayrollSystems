import { CentroCosto } from "../../centrocosto/centrocosto.types";
import { Personal } from "../personal.types";

/**
 * La Class AsociarCentroCosto.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface AsociarCentroCosto {

  /** El id asociar centro costo. */
  idAsociarCentroCosto: string;

  /** El personal. */
  personal: Personal;

  /** El planilla. */
  planilla: any;

  /** El centro costo. */
  centroCosto: CentroCosto;

  /** El fecha inicio. */
  fechaInicio: Date;

  /** El fecha final. */
  fechaFinal: Date;

  /** El porcentaje. */
  porcentaje: number;

  /** El estado. */
  estado: string;

  descripcionView: string;
}