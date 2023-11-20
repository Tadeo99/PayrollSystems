import { Personal } from './personal.types';

/**
 * La Class CuentaBancariaPersonal.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:48 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface CuentaBancariaPersonal {

    /** El id cuenta bancaria personal. */
    idCuentaBancariaPersonal: string;

    /** El personal. */
    personal: Personal;

    /** El item by banco. */
    idItemByBanco: number;
    itemByBanco: any;

    /** El nro cuenta. */
    nroCuenta: string;

    /** El nro c c i. */
    nroCCI: string;

    /** El fecha apertura. */
    fechaApertura: Date;

    /** El item by moneda. */
    idItemByMoneda: number;
    itemByMoneda: any;

    /** El item by tipo cuenta. */
    idItemByTipoCuenta: number;
    itemByTipoCuenta: any;

    /** El modulo. */
    modulo: string;

    /** El sucursal. */
    sucursal: string;

    /** El sub cuenta. */
    subCuenta: string;

    /** El item by tipo deposito cuenta. */
    idItemByTipoDepositoCuenta: number;
    itemByTipoDepositoCuenta: any;

    esCts: string;
}