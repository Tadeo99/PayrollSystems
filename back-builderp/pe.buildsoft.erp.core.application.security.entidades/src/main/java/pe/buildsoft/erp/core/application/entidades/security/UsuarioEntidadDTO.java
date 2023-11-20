package pe.buildsoft.erp.core.application.entidades.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

 

/**
 * La Class UsuarioEntidadDTO.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Dec 14 00:27:45 COT 2017
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class UsuarioEntidadDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id usuario entidad. */
    private String idUsuarioEntidad;
   
    /** El id usuario. */
    private UsuarioDTO usuario;
   
    /** El entidad. */
    private EntidadDTO entidad;
   
    /** El estado. */
    private String estado;
   
    /**
     * Instancia un nuevo usuario entidadDTO.
     */
    public UsuarioEntidadDTO() {
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
                + ((idUsuarioEntidad == null) ? 0 : idUsuarioEntidad.hashCode());
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
        UsuarioEntidadDTO other = (UsuarioEntidadDTO) obj;
        if (idUsuarioEntidad == null) {
            if (other.idUsuarioEntidad != null) {
                return false;
            }
        } else if (!idUsuarioEntidad.equals(other.idUsuarioEntidad)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "UsuarioEntidadDTO [idUsuarioEntidad=" + idUsuarioEntidad + "]";
    }
   
}