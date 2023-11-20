import { Tecnologia } from '../tecnologia.types';

/**
 * La Class Plantilla.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Plantilla {
  /** El id config tecnologia. */
  idPlantilla: string;

  /** El tecnologia. */
  tecnologia: Tecnologia;

  /** El nombre plantilla. */
  nombre: string;

  /** El codigo grupo plantilla. */
  codigoGrupo: string;

  /** El plantilla. */
  plantilla: string;

  /** El nombre archivo generar. */
  nombreArchivoGenerar: string;

  /** El extension. */
  extension: string;

  /** El es collection data. */
  esCollectionData: string;

  /** El alias paquete. */
  esInclude: string;

  /** El alias nombre. */
  orden: number;

  estado: string;

  descripcionView: string;
}
