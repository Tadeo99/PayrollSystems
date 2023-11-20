/**
 * La Class TipoPlanilla.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface TipoPlanilla {

  /** El id tipo planilla. */
  idTipoPlanilla: string;

  /** El codigo. */
  codigo: string;

  /** El item by tipo moneda. */
  idItemByTipoMoneda: number;
  itemByTipoMoneda: any;

  /** El descripcion. */
  descripcion: string;

  /** El tipo. */
  idItemByCategoriaTrabajador: number;
  itemByCategoriaTrabajador: any;

  /** El estado. */
  estado: string;

  descripcionView: string;
}