/**
 * La Class Ubigeo.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Ubigeo {

    /** El id ubigeo. */
    idUbigeo : string;
   
    /** El descripcion. */
    descripcion : string;
   
    /** El ubigeo by dependencia. */
     ubigeoByDependencia : Ubigeo
   
    /** El tipo. */
    tipo : string;

    descripcionView: string;
}