package pe.buildsoft.erp.core.application.entidades.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

 

/**
 * La Class AgrupaEntidadDTO.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Wed Dec 20 10:42:10 COT 2017
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class AgrupaEntidadDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id agrupa entidad. */
    private String idAgrupaEntidad;
   
    /** El entidad. */
    private EntidadDTO entidad;
   
    /** El entidad agrupa. */
    private EntidadDTO entidadAgrupa;
   
    /** El estado. */
    private String estado;
   
    /**
     * Instancia un nuevo agrupa entidadDTO.
     */
    public AgrupaEntidadDTO() {
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
                + ((idAgrupaEntidad == null) ? 0 : idAgrupaEntidad.hashCode());
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
        AgrupaEntidadDTO other = (AgrupaEntidadDTO) obj;
        if (idAgrupaEntidad == null) {
            if (other.idAgrupaEntidad != null) {
                return false;
            }
        } else if (!idAgrupaEntidad.equals(other.idAgrupaEntidad)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AgrupaEntidadDTO [idAgrupaEntidad=" + idAgrupaEntidad + "]";
    }
   
}