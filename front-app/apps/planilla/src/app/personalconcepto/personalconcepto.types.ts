import { PeriodoPlanilla } from "../periodoplanilla/periodoplanilla.types";
import { TipoPlanilla } from "../tipoplanilla/tipoplanilla.types";
import { DetallePersonalConcepto } from "./detallepersonalconcepto.types";

/**
 * La Class PersonalConcepto.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface PersonalConcepto {

  /** El id tipo planilla. */
  idPersonalConcepto: string;

  /** El Personal. */
  idPersonal: string;
  personal: any;

  /** El tipo. */
  tipoPlanilla: TipoPlanilla;

  periodoPlanilla: PeriodoPlanilla;

  /** El detalle Personal ConceptoList list. */
  detallePersonalConceptoList: DetallePersonalConcepto[];

  /** El estado. */
  estado: string;

  descripcionView: string;
  id: string;
}