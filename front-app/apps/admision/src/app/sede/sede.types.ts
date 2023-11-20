import { DetSede } from "./detsede.types";

/**
 * La Class Sede.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Sede {

  /** El id sede. */
  idSede: string;

  /** El codigo. */
  codigo: string;

  /** El nombre. */
  nombre: string;

  /** El direccion. */
  direccion: string;

  /** El coordenada direccion. */
  coordenadaDireccion: string;

  /** El ubigeo. */
  idUbigeo: string;
  ubigeo: any;

  /** El estado. */
  estado: string;

  /** El usuario creacion. */
  usuarioCreacion: string;

  /** El fecha creacion. */
  fechaCreacion: Date;

  /** El usuario modificacion. */
  usuarioModificacion: string;

  /** El fecha modificacion. */
  fechaModificacion: Date;

  /** El sede det sede list. */
  sedeDetSedeList: DetSede[];

  sedeAsignaPostulanteList: any[];

  descripcionView: string;

  checked: boolean;
}