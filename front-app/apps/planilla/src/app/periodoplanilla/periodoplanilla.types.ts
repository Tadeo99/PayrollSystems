/**
 * La Class PeriodoPlanilla.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface PeriodoPlanilla {

  /** El id periodo planilla. */
  idPeriodoPlanilla: string;

  /** El descripcion. */
  descripcion: string;

  /** El fecha inico. */
  fechaInico: Date;

  /** El fecha fina. */
  fechaFinal: Date;

  /** El item by periodo mes. */
  idItemByPeriodoMes: number;
  itemByPeriodoMes: any;

  /** El anhio. */
  idAnhio: number;
  anhio: any;

  /** El dias netos. */
  diasNetos: number;

  /** El estado. */
  estado: string;

  descripcionView: string;
}