package pe.buildsoft.erp.core.application.entidades.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

 

/**
 * La Class PropertiesDTO.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Dec 14 00:27:44 COT 2017
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class PropertiesDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id properties. */
    private Long idProperties;
   
    /** El name. */
    private String name;
   
    /** El descripcion. */
    private String descripcion;
   
    /** El value default. */
    private String valueDefault;
   
    /** El value. */
    private String value;
   
    /** El estado. */
    private String estado;
   
    /**
     * Instancia un nuevo propertiesDTO.
     */
    public PropertiesDTO() {
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
                + ((idProperties == null) ? 0 : idProperties.hashCode());
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
        PropertiesDTO other = (PropertiesDTO) obj;
        if (idProperties == null) {
            if (other.idProperties != null) {
                return false;
            }
        } else if (!idProperties.equals(other.idProperties)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PropertiesDTO [idProperties=" + idProperties + "]";
    }
   
}