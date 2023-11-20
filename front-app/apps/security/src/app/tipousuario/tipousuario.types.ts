/**
 * La Class TipoUsuario.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface TipoUsuario {

    /** El id tipo usuario. */
    idTipoUsuario : number;
   
    /** El descripcion. */
    descripcion : string;
   
    /** El codigo. */
    codigo : string;
   
    /** El codigo externo. */
    codigoExterno : string;
   
    /** El estado. */
    estado : string;
    
    descripcionView: string;
}