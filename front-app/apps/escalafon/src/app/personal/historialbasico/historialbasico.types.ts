import { Personal } from "../personal.types";

/**
 * La Class HistorialBasico.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface HistorialBasico {

  /** El id historial basico. */
  idHistorialBasico: string;

  /** El personal. */
  personal: Personal;

  /** El descripcion. */
  descripcion: string;

  /** El valor anterior. */
  valorAnterior: number;

  /** El valor actual. */
  valorActual: number;

  /** El personal responsable. */
  personalResponsable: Personal;

  /** El fecha cambio. */
  fechaCambio: Date;

  estado: string;

  descripcionView: string;
}