/**
 * La Class Clasificacion.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Clasificacion {

  /** El id clasificacion. */
  idClasificacion: number;

  /** El item by tipo clasificacion. */
  idItemByTipoClasificacion: number;
  itemByTipoClasificacion: any;

  /** El descripcion. */
  descripcion: string;

  /** El abreviatura. */
  abreviatura: string;

  /** El entidad. */
  entidad: string;

  /** El sede. */
  sede: string;

  estado: string;

  descripcionView: string;
}
