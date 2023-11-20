package pe.buildsoft.erp.core.application.entidades.admision;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class SeccionDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Wed Apr 21 12:29:28 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class SeccionDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id seccion. */
    private Long idSeccion;
   
    /** El grado. */
    private GradoDTO grado;
   
    /** El codigo. */
    private String codigo;
   
    /** El nombre. */
    private String nombre;
   
    /**
     * Instancia un nuevo seccionDTO.
     */
    public SeccionDTO() {
		super();
    }
   
   
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idSeccion == null) ? 0 : idSeccion.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SeccionDTO other = (SeccionDTO) obj;
        if (idSeccion == null) {
            if (other.idSeccion != null) {
                return false;
            }
        } else if (!idSeccion.equals(other.idSeccion)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SeccionDTO [idSeccion=" + idSeccion + "]";
    }
   
}