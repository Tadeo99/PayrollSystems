import { Tecnologia } from '../tecnologia.types';

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
export interface ConfigTypeEquivalencia {
  /** El id config type equivalencia. */
  idEquivalenciaType: string;

  /** El tecnologia by base datos. */
  tecnologia: Tecnologia;

  /** El tecnologia equivalente. */
  tecnologiaEquivalente: Tecnologia;

  /** El type. */
  type: string;

  /** El type atribute. */
  typeAtribute: string;

  /** El es decimal. */
  esDecimal: string;

  estado: string;

  descripcionView: string;
}
