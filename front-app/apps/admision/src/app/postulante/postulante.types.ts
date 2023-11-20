import { AsignaPostulante } from "./asignapostulante.types";

/**
 * La Class Postulante.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Postulante {

  /** El id postulante. */
  idPostulante: string;

  /** El codigo. */
  codigo: string;

  /** El estado. */
  estado: string;

  /** El item by doc identidad. */
  idItemByDocIdentidad: number;
  itemByDocIdentidad: any;

  /** El nro doc. */
  nroDoc: string;

  /** El apellido paterno. */
  apellidoPaterno: string;

  /** El apellido materno. */
  apellidoMaterno: string;

  /** El nombres. */
  nombres: string;

  /** El fecha nacimiento. */
  fechaNacimiento: Date;

  /** El telefono1. */
  telefono1: string;

  /** El telefono2. */
  telefono2: string;

  /** El celular. */
  celular: string;

  /** El email. */
  email: string;

  /** El sexo. */
  sexo: string;

  /** El lugar nacimiento. */
  idLugarNacimiento: string;
  lugarNacimiento: any;

  /** El item by nacionalidad. */
  idItemByNacionalidad: number;
  itemByNacionalidad: any;

  /** El foto. */
  foto: string;

  /** El usuario creacion. */
  usuarioCreacion: string;

  /** El fecha creacion. */
  fechaCreacion: Date;

  /** El usuario modificacion. */
  usuarioModificacion: string;

  /** El fecha modificacion. */
  fechaModificacion: Date;

  tipo: number;

  /**Campos agregados */

  /** El id apoderado. */
  asignaPostulante: AsignaPostulante;

  descripcionView: string;
}