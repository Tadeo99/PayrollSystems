/**
 * La Class TipoDocSunatEntidad.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface TipoDocSunatEntidad {

    /** El id clasificacion. */
    idTipoDocSunatEntidad: string;
  
    /** El item by tipo doc sunat. */
    idItemByTipoDocSunat: number;
    itemByTipoDocSunat: any;
  
    /** El descripcion. */
    idEntidad: string;
  
    /** El abreviatura. */
    correla: string;
  
    /** El entidad. */
    serie: string;
    descripcionView: string;
}
  