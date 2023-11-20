import { ListaItems } from "../listaitems/listaitems.types";

/**
 * La Class Item.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Item {

    /** El id item. */
    idItem: number;

    /** El lista items. */
    listaItems: ListaItems;

    /** El descripcion. */
    descripcion: string;

    /** El nombre. */
    nombre: string;

    /** El codigo. */
    codigo: number;

    /** El codigo externo. */
    codigoExterno: string;

    /** El estado. */
    estado: string;

    abreviatura : string;

    descripcionView: string;
}