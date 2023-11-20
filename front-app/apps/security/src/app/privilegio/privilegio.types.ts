/**
 * La Class Privilegio.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Privilegio {

     /** El id privilegio. */
     idPrivilegio : number;
   
     /** El acronimo. */
     acronimo : string;
    
     /** El nombre. */
     nombre : string;
    
     /** El descripcion. */
     descripcion : string;
    
     /** El accion. */
     accion : string;
    
     /** El estado. */
     estado : string;

    descripcionView: string;
}