/**
 * La Class Sistema.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Sistema {

    /** El id sistema. */
    idSistema: number;

    /** El nombre. */
    nombre: string;

    /** El descripcion. */
    descripcion: string;

    /** El abreviatura. */
    abreviatura: string;

    /** El fecha. */
    fecha: Date;

    /** El version. */
    version: string;

    /** El estado. */
    estado: string;

    /** El icono. */
    icono: string;

    descripcionView: string;
}