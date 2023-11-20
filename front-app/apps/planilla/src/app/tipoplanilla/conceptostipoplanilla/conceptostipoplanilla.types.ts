import { ConceptoPdt } from "../../conceptopdt/conceptopdt.types";
import { TipoPlanilla } from "../tipoplanilla.types";

/**
 * La Class ConceptosTipoPlanilla.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface ConceptosTipoPlanilla {

  /** El id conceptos tipo planilla. */
  idConceptosTipoPlanilla: string;

  /** El tipo planilla. */
  tipoPlanilla: TipoPlanilla;

  /** El concepto pdt. */
  conceptoPdt: ConceptoPdt;

  /** El descripcion. */
  descripcion: string;

  codigo: string;
  
  /** El orden. */
  orden: number;

  /** El formula. */
  formula: string;

  /** El persistente. */
  persistente: string;

  showBoleta : string;

  /** El conceptos tipo planilla detalle planilla concepto list. */
  conceptosTipoPlanillaDetallePlanillaConceptoList: any[];

  esEliminado: boolean;

  /** El estado. */
  estado: string;

  descripcionView: string;
}