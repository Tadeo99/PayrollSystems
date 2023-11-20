import { Contrato } from "./contrato.types";

/**
 * La Class DetalleContrado.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface DetalleContrado {

  /** El id detalle contrado. */
  idDetalleContrado: string;

  /** El contrato. */
  contrato: Contrato;

  /** El basico. */
  basico: number;

  /** El horaley. */
  horaley: number;

  /** El monto dia. */
  montoDia: number;

  /** El monto mes. */
  montoMes: number;

  /** El estado. */
  estado: string;

  /** El fecha. */
  fecha: Date;

  descripcionView: string;
}