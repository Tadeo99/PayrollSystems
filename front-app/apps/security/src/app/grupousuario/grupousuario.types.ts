/**
 * La Class GrupoUsuario.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface GrupoUsuario {

    /** El id grupo usuario. */
    idGrupoUsuario: number;

    /** El descripcion. */
    descripcion: string;

    /** El estado. */
    estado: string;

    descripcionView: string;

    menus: any[];
}