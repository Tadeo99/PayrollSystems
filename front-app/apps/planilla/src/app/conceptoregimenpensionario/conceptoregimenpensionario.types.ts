/**
 * La Class ConceptoRegimenPensionario.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface ConceptoRegimenPensionario {

  /** El id concepto regimen pensionario. */
  idConceptoRegimenPensionario: string;

  /** El item by regimen pensionario. */
  idItemByRegimenPensionario: number;
  itemByRegimenPensionario: any;

  /** El periodo laboral personal. */
  idPeriodoLaboraPersonal: string;
  periodoLaboraPersonal: any;

  /** El item by mes by devengue. */
  idItemByMesByDevengue: number;
  itemByMesByDevengue: any;

  /** El comision fija. */
  comisionFija: number;

  /** El comision sobre flujo. */
  comisionSobreFlujo: number;

  /** El comision sobre flujo mixto. */
  comisionSobreFlujoMixto: number;

  /** El comision anual sobre saldo mixto. */
  comisionAnualSobreSaldoMixto: number;

  /** El prima seguros. */
  primaSeguros: number;

  /** El aporte obligatorio. */
  aporteObligatorio: number;

  /** El remuneracion maxima asegurable. */
  remuneracionMaximaAsegurable: number;

  /** El anhio. */
  idAnhio: number;
  anhio: any;

  /** El estado. */
  estado: string;

  descripcionView: string;
}