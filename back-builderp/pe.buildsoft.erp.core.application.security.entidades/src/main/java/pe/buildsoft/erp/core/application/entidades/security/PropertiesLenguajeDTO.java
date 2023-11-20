package pe.buildsoft.erp.core.application.entidades.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class PropertiesLenguajeDTO.
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
public class PropertiesLenguajeDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id properties lenguaje. */
    private String idPropertiesLenguaje;
   
    /** El item by lenguaje. */
    private ItemDTO itemByLenguaje;
   
    /** El properties. */
    private PropertiesDTO properties;
   
    /** El value. */
    private String value;
   
    /**
     * Instancia un nuevo properties lenguajeDTO.
     */
    public PropertiesLenguajeDTO() {
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
                + ((idPropertiesLenguaje == null) ? 0 : idPropertiesLenguaje.hashCode());
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
        PropertiesLenguajeDTO other = (PropertiesLenguajeDTO) obj;
        if (idPropertiesLenguaje == null) {
            if (other.idPropertiesLenguaje != null) {
                return false;
            }
        } else if (!idPropertiesLenguaje.equals(other.idPropertiesLenguaje)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PropertiesLenguajeDTO [idPropertiesLenguaje=" + idPropertiesLenguaje + "]";
    }
   
}