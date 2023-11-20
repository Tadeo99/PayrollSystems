package pe.buildsoft.erp.core.application.entidades.escalafon;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class HistorialBasicoDTO.
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
public class HistorialBasicoDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id historial basico. */
    private String idHistorialBasico;
   
    /** El personal. */
    private PersonalDTO personal;
   
    /** El descripcion. */
    private String descripcion;
   
    /** El valor anterior. */
    private BigDecimal valorAnterior;
   
    /** El valor actual. */
    private BigDecimal valorActual;
   
    /** El personal responsable. */
    private PersonalDTO personalResponsable;
   
    /** El fecha cambio. */
    private OffsetDateTime fechaCambio;
    
    private String estado;
   
    /**
     * Instancia un nuevo historial basicoDTO.
     */
    public HistorialBasicoDTO() {
		super();
    }
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idHistorialBasico == null) ? 0 : idHistorialBasico.hashCode());
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
        HistorialBasicoDTO other = (HistorialBasicoDTO) obj;
        if (idHistorialBasico == null) {
            if (other.idHistorialBasico != null) {
                return false;
            }
        } else if (!idHistorialBasico.equals(other.idHistorialBasico)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "HistorialBasicoDTO [idHistorialBasico=" + idHistorialBasico + "]";
    }
   
}