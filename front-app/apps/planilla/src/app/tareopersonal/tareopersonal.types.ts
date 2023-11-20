
/**
 * La Class TareoPersonal.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface TareoPersonal {

  idTareoPersonal: string;

  /** El personal. 
  Personal personal;*/
  idPersonal: string;
  personal : any;

  /** El item by mes.*/
  idItemByMes: number;
  itemByMes: any;

  /** El anhio. 
  Anhio anhio;*/
  idAnhio: number;

  /** El item by categoria ocupacional. 
  Item itemByCategoriaOcupacional;*/
  idItemByCategoriaOcupacional: number;
  itemByCategoriaOcupacional: any;

  /** El item by periocidad. 
  Item itemByPeriocidad;*/
  idItemByPeriocidad: number;
  itemByPeriocidad: any;

  /** El dias lab. */
  diasLab: number;

  /** El dias tra. */
  diasTra: number;

  /** El dias tra. */
  dominical: number;

  /** El horas extras25. */
  horasNormal: number;

  /** El horas extras25. */
  horasExtras25: number;

  /** El horas extras35. */
  horasExtras35: number;

  /** El horas extras. */
  horasExtras100: number;

  /** El horas nocturna. */
  horasNocturna: number;

  /** El vacaciones. */
  vacaciones: number;

  /** El permisio sin goce de haber. */
  permisoSinGoceHaber: number;

  /** El falta. */
  falta: number;

  /** El subsidio. */
  subsidio: number;

  /** El tardanza. */
  tardanza: number;

  /** El rmv. */
  rmv: number;

  /** El bofinicacion nocturna. */
  bonifiNocturna: number;

  /** El estado. */
  estado: string;

  descripcionView: string;
}