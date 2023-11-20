import { BasePaginator } from '../base/basepaginator.model';
import { ConceptosTipoPlanilla } from './conceptostipoplanilla.model';
import { PersonalConcepto } from './personalconcepto.model';

/**
 * La Class TipoPlanilla.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:47 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface DetallePersonalConcepto {

    /** El id tipo planilla. */
    idDetallePersonalConcepto: string

    /** El Personal. */
    personalConcepto: PersonalConcepto;

    /** El conceptos TipoPlanilla. */
    conceptosTipoPlanilla: ConceptosTipoPlanilla;

    /** El detalle Personal ConceptoList list. */
    monto: number

    detallePersonalConceptoList: DetallePersonalConcepto[];
}