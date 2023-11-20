/**
 * La Class Parametro.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Parametro {

    /** El id parametro. */
    idParametro: string;

    /** El id entidad. */
    entidad: string;

    /** El descripcion. */
    descripcion: string;

    /** El codigo. */
    codigo: string;

    /** El valor. */
    valor: string;

    /** El estado. */
    estado: string;

    descripcionView: string;
}