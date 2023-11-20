import { TipoUsuario } from "../tipousuario/tipousuario.types";

/**
 * La Class Usuario.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Usuario {

    /** El id usuario. */
    idUsuario: string;

    /** El nombre. */
    nombre: string;

    /** El apellido paterno. */
    apellidoPaterno: string;

    /** El apellido materno. */
    apellidoMaterno: string;

    /** El email. */
    email: string;

    /** El telefono. */
    telefono: string;

    /** El celular. */
    celular: string;

    /** El user name. */
    userName: string;

    /** El user password. */
    userPassword: string;

    /** El tipo usuario. */
    tipoUsuario: TipoUsuario;

    /** El codigo externo. */
    codigoExterno: string;

    /** El estado. */
    estado: string;

    /** El user password. */
    userPasswordEncriptado: string;

    descripcionView: string;

    grupoUsuarios: any[];
    entidades: any[];
}