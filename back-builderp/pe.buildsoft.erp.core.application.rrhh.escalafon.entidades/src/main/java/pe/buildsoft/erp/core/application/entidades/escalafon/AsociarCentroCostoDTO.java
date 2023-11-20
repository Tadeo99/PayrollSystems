package pe.buildsoft.erp.core.application.entidades.escalafon;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class AsociarCentroCostoDTO.
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
public class AsociarCentroCostoDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id asociar centro costo. */
    private String idAsociarCentroCosto;
   
    /** El personal. */
    private PersonalDTO personal;
   
    /** El planilla. */
    private String planilla;
   
    /** El centro costo. */
    private CentroCostoDTO centroCosto;
   
    /** El fecha inicio. */
    private OffsetDateTime fechaInicio;
   
    /** El fecha final. */
    private OffsetDateTime fechaFinal;
   
    /** El porcentaje. */
    private BigDecimal porcentaje;
   
    /**
     * Instancia un nuevo asociar centro costoDTO.
     */
    public AsociarCentroCostoDTO() {
		super();
    }
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idAsociarCentroCosto == null) ? 0 : idAsociarCentroCosto.hashCode());
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
        AsociarCentroCostoDTO other = (AsociarCentroCostoDTO) obj;
        if (idAsociarCentroCosto == null) {
            if (other.idAsociarCentroCosto != null) {
                return false;
            }
        } else if (!idAsociarCentroCosto.equals(other.idAsociarCentroCosto)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AsociarCentroCostoDTO [idAsociarCentroCosto=" + idAsociarCentroCosto + "]";
    }
   
}