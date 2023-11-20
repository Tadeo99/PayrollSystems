
/**
 * La Class CentroCosto.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface CentroCosto {

  /** El id centro costo. */
  idCentroCosto: string;

  /** El descripcion. */
  nombre: string;

  /** El codigo. */
  codigo: string;

  /** El descripcion. */
  descripcion: string;

  fechaCreacion: Date;

  /** El estado. */
  estado: string;

  /**Campos agregados */

  descripcionView: string;
}