import { DetallePlanillaConcepto } from "./detalleplanillaconcepto.type";
import { Planilla } from "./planilla.types";

/**
 * La Class DetallePlanilla.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:47 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface DetallePlanilla {

    /** El id detalle planilla. */
    idDetallePlanilla: string;

    /** El planilla. */
    planilla: Planilla;

    /** El personal. */
    idPersonal: string;

    personal: any;

    /** El totalIngreso. */
    totalIngreso: number;

    /** El totalDescuento. */
    totalDescuento: number;

    /** El totalAportaciones. */
    totalAportaciones: number;

    /** El neto pagar. */
    netoPagar: number;

    /** El detalle planilla detalle planilla concepto list. */
    detallePlanillaDetallePlanillaConceptoList: DetallePlanillaConcepto[];

    estado: number;
}