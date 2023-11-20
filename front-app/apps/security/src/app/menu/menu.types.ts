import { Sistema } from "../sistema/sistema.types";

/**
 * La Class Menu.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Menu {

    /** El id menu. */
    idMenu: number;

    /** El sistema. */
    sistema: Sistema;

    /** El nombre. */
    nombre: string;

    /** El descripcion. */
    descripcion: string;

    /** El url. */
    url: string;

    /** El parametro. */
    parametro: string;

    /** El icono. */
    icono: string;

    /** El target. */
    target: string;

    /** El menu padre. */
    menuPadre: Menu;

    /** El fecha creacion. */
    fechaCreacion: Date;

    /** El usuario creacion. */
    usuarioCreacion: string;

    /** El fecha modificacion. */
    fechaModificacion: Date;

    /** El usuario modificacion. */
    usuarioModificacion: string;

    /** El estado. */
    estado: string;

    descripcionView: string;
}