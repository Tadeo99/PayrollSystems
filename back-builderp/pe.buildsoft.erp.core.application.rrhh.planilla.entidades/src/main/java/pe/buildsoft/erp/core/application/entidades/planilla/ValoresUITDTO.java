package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.AnhioDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ValoresUITDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 23:10:02 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class ValoresUITDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id uit. */
    private String idUit;
   
    /** El anhio. */
    private Long idAnhio;
    private AnhioDTO anhio;
   
    /** El valor. */
    private BigDecimal valor;
   
    /** El base legal. */
    private String baseLegal;
   
    /** El observaciones. */
    private String observaciones;
   
    /**
     * Instancia un nuevo valores u i tDTO.
     */
    public ValoresUITDTO() {
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
                + ((idUit == null) ? 0 : idUit.hashCode());
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
        ValoresUITDTO other = (ValoresUITDTO) obj;
        if (idUit == null) {
            if (other.idUit != null) {
                return false;
            }
        } else if (!idUit.equals(other.idUit)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ValoresUITDTO [idUit=" + idUit + "]";
    }
   
}