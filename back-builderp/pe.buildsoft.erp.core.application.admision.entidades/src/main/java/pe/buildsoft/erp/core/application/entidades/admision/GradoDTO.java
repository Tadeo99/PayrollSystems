package pe.buildsoft.erp.core.application.entidades.admision;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class GradoDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Wed Apr 21 12:29:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class GradoDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id grado. */
    private Long idGrado;
   
    /** El item by nivel. */
    private Long idItemByNivel;
    private ItemDTO itemByNivel;
   
    /** El codigo. */
    private String codigo;
   
    /** El nombre. */
    private String nombre;
   
    /**
     * Instancia un nuevo gradoDTO.
     */
    public GradoDTO() {
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
                + ((idGrado == null) ? 0 : idGrado.hashCode());
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
        GradoDTO other = (GradoDTO) obj;
        if (idGrado == null) {
            if (other.idGrado != null) {
                return false;
            }
        } else if (!idGrado.equals(other.idGrado)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GradoDTO [idGrado=" + idGrado + "]";
    }
   
}