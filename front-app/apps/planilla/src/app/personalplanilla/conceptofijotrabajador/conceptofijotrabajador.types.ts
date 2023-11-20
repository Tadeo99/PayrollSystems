import { ConceptoPdt } from "../../conceptopdt/conceptopdt.types";

/**
 * La Class ConceptoFijoTrabajador.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface ConceptoFijoTrabajador {

  /** El id concepto trabajador. */
  idConceptoTrabajador: string;

  /** El concepto pdt. */
  conceptoPdt: ConceptoPdt;

  /** El personal. */
  idPersonal: string;

  /** El item by periodo mes. */
  idItemByPeriodoMes: number;
  itemByPeriodoMes: any;

  /** El descripcion. */
  descripcion: string;

  /** El porcentaje. */
  porcentaje: number;

  /** El monto. */
  monto: number;
  /** El estado. */
  estado: string;

  descripcionView: string;
}