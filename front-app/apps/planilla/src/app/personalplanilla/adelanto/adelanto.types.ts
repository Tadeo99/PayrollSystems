import { ConceptoPdt } from "../../conceptopdt/conceptopdt.types";

/**
 * La Class Adelanto.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Adelanto {

  /** El id adelanto. */
  idAdelanto: string;

  /** El personal. */
  idPersonal: string;
  personal: any;

  /** El concepto p d t. */
  conceptoPdt: ConceptoPdt;

  /** El monto. */
  monto: number;

  /** El fecha adelanto. */
  fechaAdelanto: Date;

  /** El fecha pago. */
  fechaPago: Date;

  /** El estado. */
  estado: string;

  descripcionView: string;
}