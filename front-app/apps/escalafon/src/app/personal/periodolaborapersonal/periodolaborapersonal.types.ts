import { Personal } from "../personal.types";

/**
 * La Class PeriodoLaboraPersonal.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface PeriodoLaboraPersonal {

  /** El id periodo laboral personal. */
  idPeriodoLaboraPersonal: string;

  /** El personal. */
  personal: Personal;

  /** El situacion. */
  situacion: string;

  /** El fecha ingrreso. */
  fechaIngrreso: Date;

  /** El fecha cese. */
  fechaCese: Date;

  /** El item by motivo cese. */
  idItemByMotivoCese: number;
  itemByMotivoCese: any;

  /** El liquitado. */
  liquitado: string;

  estado: string;

  descripcionView: string;
}