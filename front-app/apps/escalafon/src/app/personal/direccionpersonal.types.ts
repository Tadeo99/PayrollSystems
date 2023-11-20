import { Personal } from './personal.types';


/**
 * La Class DireccionPersonal.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:48 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface DireccionPersonal {

    /** El id direccion personal. */
    idDireccionPersonal: string;

    /** El personal. */
    personal: Personal;

    /** El domiciliado. */
    domiciliado: string;

    /** El direcion centro asistencial essalud. */
    direcionCentroAsistencialEssalud: string;

    /** El item by tipo via. */
    idItemByTipoVia: number;
    itemByTipoVia: any;

    /** El nombre tipo via. */
    nombreTipoVia: string;

    /** El item by zona. */
    idItemByZona: number;
    itemByZona: any;

    /** El nombre zona. */
    nombreZona: string;

    /** El numero. */
    numero: number;

    /** El interior. */
    interior: number;

    /** El departamento. */
    departamento: number;

    /** El block. */
    block: number;

    /** El mazana. */
    mazana: string;

    /** El lote. */
    lote: number;

    /** El etapa. */
    etapa: string;

    /** El kilometro. */
    kilometro: number;

    /** El referencia. */
    referencia: string;

    /** El item by procedencia direccion. */
    idItemByProcedenciaDireccion : number;
    itemByProcedenciaDireccion: any;

    /** El ubigeo. */
    idUbigeo: string;
    ubigeo: any;

}