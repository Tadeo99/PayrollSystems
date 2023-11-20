/**
 * La Class ConceptoPdt.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface ConceptoPdt {

    /** El id concepto pdt. */
    idConceptoPdt: string;

    /** El descripcion. */
    descripcion: string;

    /** El depencia. */
    conceptoPdtPadre: ConceptoPdt;

    /** El tipo. */
    tipo: string;

    /** El formula. */
    formula: string;

    /** El estado. */
    estado: string;

    /** El codigo. */
    codigo: string;

    /** El abreviatura. */
    abreviatura: string;

    /** El visible. */
    visible: string;

    descripcionView: string;
}