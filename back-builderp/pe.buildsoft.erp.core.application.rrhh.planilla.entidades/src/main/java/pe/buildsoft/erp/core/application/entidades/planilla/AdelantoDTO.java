package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class AdelantoDTO.
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
public class AdelantoDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id adelanto. */
    private String idAdelanto;
   
    /** El personal. */
    private String idPersonal;
   // private PersonalDTO personal;
   
    /** El concepto p d t. */
    private ConceptoPdtDTO conceptoPdt;
   
    /** El monto. */
    private BigDecimal monto;
   
    /** El fecha adelanto. */
    private OffsetDateTime fechaAdelanto;
   
    /** El fecha pago. */
    private OffsetDateTime fechaPago;
   
    /** El estado. */
    private String estado;
   
    /**
     * Instancia un nuevo adelantoDTO.
     */
    public AdelantoDTO() {
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
                + ((idAdelanto == null) ? 0 : idAdelanto.hashCode());
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
        AdelantoDTO other = (AdelantoDTO) obj;
        if (idAdelanto == null) {
            if (other.idAdelanto != null) {
                return false;
            }
        } else if (!idAdelanto.equals(other.idAdelanto)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AdelantoDTO [idAdelanto=" + idAdelanto + "]";
    }
   
}