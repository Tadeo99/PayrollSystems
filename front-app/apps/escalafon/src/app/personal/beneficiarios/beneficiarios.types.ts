import { Personal } from "../personal.types";

/**
 * La Class Beneficiarios.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Beneficiarios {

  /** El id beneficiario. */
  idBeneficiario: string;

  /** El personal. */
  personal: Personal;

  /** El apellido paterno. */
  apellidoPaterno: string;

  /** El apellido materno. */
  apellidoMaterno: string;

  /** El nombre. */
  nombre: string;

  /** El item by vinculo. */
  idItemByVinculo: number;
  itemByVinculo: any;

  /** El fecha nacimiento. */
  fechaNacimiento: Date;

  /** El situacion. */
  situacion: string;

  /** El fecha activacion. */
  fechaActivacion: Date;

  estado: string;

  descripcionView: string;
}