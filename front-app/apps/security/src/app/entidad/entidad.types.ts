/**
 * La Class Entidad.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Entidad {

    /** El id entidad. */
    idEntidad: string;

    /** El item by tipo via. */
    idItemByTipoVia: number;
    itemByTipoVia: any;

    /** El item by zona. */
    idItemByZona: number;
    itemByZona: any;

    /** El nombre zona. */
    nombreZona: string;

    /** El nombre tipo via. */
    nombreTipoVia: string;

    /** El codigo. */
    codigo: string;

    /** El codigo externo. */
    codigoExterno: string;

    /** El codigo referencia. */
    codigoReferencia: string;

    /** El nombre. */
    nombre: string;

    /** El direccion. */
    direccion: string;

    /** El telefono. */
    telefono: string;

    /** El email. */
    email: string;

    /** El web. */
    web: string;

    /** El fecha creacion. */
    fechaCreacion: Date;

    /** El usuario creacion. */
    usuarioCreacion: string

    /** El estado. */
    estado: string;

    descripcionView: string;
}