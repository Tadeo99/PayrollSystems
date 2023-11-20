package pe.buildsoft.erp.core.application.entidades.escalafon;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class InstitucionDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 23:29:57 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class InstitucionDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id institucion. */
    private Long idInstitucion;
   
    /** El item by tipo institucion. */
    private Long  idItemByTipoInstitucion;
    private ItemDTO itemByTipoInstitucion;
   
    /** El item regimen. */
    private Long  idItemByRegimen;
    private ItemDTO itemByRegimen;
   
    /** El estado. */
    private String estado;
   
    /**
     * Instancia un nuevo institucionDTO.
     */
    public InstitucionDTO() {
		super();
    }
   
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
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
        InstitucionDTO other = (InstitucionDTO) obj;
        if (idInstitucion == null) {
            if (other.idInstitucion != null) {
                return false;
            }
        } else if (!idInstitucion.equals(other.idInstitucion)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "InstitucionDTO [idInstitucion=" + idInstitucion + "]";
    }
   
}