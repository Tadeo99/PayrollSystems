
/**
 * La Class InformaOtrosIngreso5ta.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface InformaOtrosIngreso5ta {

  /** El id informa otros ingreso5ta. */
  idInformaOtrosIngreso5ta: string;

  /** El personal. */
  idPersonal: string;
  personal: any;

  /** El empresa. */
  idEmpresa: number;
  empresa: any;

  /** El importe. */
  importe: number;

  estado: string;

  descripcionView: string;
}