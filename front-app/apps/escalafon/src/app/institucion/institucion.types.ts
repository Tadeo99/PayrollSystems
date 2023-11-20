/**
 * La Class Institucion.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Institucion {

  /** El id institucion. */
  idInstitucion: number;

  /** El item by tipo institucion. */
  idItemByTipoInstitucion: number;
  itemByTipoInstitucion: any;

  /** El item by regimen. */
  idItemByRegimen: number;
  itemByRegimen: any;

  /** El estado. */
  estado: string;

  descripcionView: string;
}