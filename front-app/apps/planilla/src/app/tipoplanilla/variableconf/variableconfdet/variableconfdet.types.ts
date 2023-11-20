import { VariableConf } from "../variableconf.types";

/**
 * La Class ConceptosVariableConf.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface VariableConfDet {

  /** El id conceptos tipo planilla. */
  idVariableConfDet: string;

  /** El variable conf. */
  variableConf: VariableConf;

  /** El variable pdt. */
  variable: string;

  /** El formula. */
  formula: string;

  descripcion: string;

  /** El orden. */
  orden: number;

  descripcionView: string;
}