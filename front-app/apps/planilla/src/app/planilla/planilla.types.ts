import { PeriodoPlanilla } from "../periodoplanilla/periodoplanilla.types";
import { TipoPlanilla } from "../tipoplanilla/tipoplanilla.types";
import { DetallePlanilla } from "./detalleplanilla.type";

/**
 * La Class Planilla.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Planilla {

  /** El id planilla. */
  idPlanilla: string;

  /** El periodo planilla. */
  periodoPlanilla: PeriodoPlanilla;

  /** El nombre. */
  nombre: string;

  /** El tipo calculo. */
  tipoCalculo: string;

  /** El item by tipo trabajador. */
  idItemByTipoTrabajador: number;
  itemByTipoTrabajador: any;

  /** El item by periodo mes. */
  idItemByPeriodoMes: number;
  itemByPeriodoMes: any;

  /** El anhio. */
  idAnhio: string;
  anhio: any;

  /** El tipo planilla. */
  tipoPlanilla: TipoPlanilla;

  /** El fecha pago. */
  fechaPago: Date;

  /** El fecha inicio. */
  fechaInicio: Date;

  /** El fcha fin. */
  fchaFin: Date;

  /** El estado. */
  estado: string;

  /** El planilla detalle planilla list. */
  planillaDetallePlanillaList: DetallePlanilla[];
  
  descripcionView: string;
  id: string;
}