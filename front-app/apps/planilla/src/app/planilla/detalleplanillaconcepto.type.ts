import { ConceptosTipoPlanilla } from "../tipoplanilla/conceptostipoplanilla/conceptostipoplanilla.types";
import { DetallePlanilla } from "./detalleplanilla.type";

/**
 * La Class DetallePlanillaConcepto.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:47 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface DetallePlanillaConcepto {

    /** El id detalle planilla concepto. */
    idDetallePlanillaConcepto: string;

    /** El detalle planilla. */
    detallePlanilla: DetallePlanilla;

    /** El concepto trabajador. */
    concepto: ConceptosTipoPlanilla;

    /** El monto. */
    monto: number;

    /** El id detalle planilla concepto detalle planilla concepto list. */
    detallePlanillaDetallePlanillaConceptoList: DetallePlanillaConcepto[];

}