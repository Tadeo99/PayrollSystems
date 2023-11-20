
/**
 * La Class proyecto.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Proyecto {

    /** El id proyecto. */
    idProyecto : string;
   
    /** El id usuario. */
    idUsuario : string;

    /** El nombre. */
    nombre : string ;
   
    /** El paquete base. */
    idsTecnologias : string;

    estado : string;

    descripcionView : string;
}