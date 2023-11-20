package pe.buildsoft.erp.core.application.entidades.escalafon;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class ContratoDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:48 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class ContratoDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id contrato. */
    private String idContrato;
   
    /** El personal. */
    private PersonalDTO personal;
   
    /** El item by tipo contrato. */
    private Long idItemByTipoContrato;
    private ItemDTO itemByTipoContrato;
   
    /** El fecha inicio. */
    private OffsetDateTime fechaInicio;
   
    /** El fecha final. */
    private OffsetDateTime fechaFinal;
   
    /** El nrodoc. */
    private String nrodoc;
   
    /** El estado. */
    private String estado;
   
    /**
     * Instancia un nuevo contratoDTO.
     */
    public ContratoDTO() {
		super();
    }
   
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idContrato == null) ? 0 : idContrato.hashCode());
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
        ContratoDTO other = (ContratoDTO) obj;
        if (idContrato == null) {
            if (other.idContrato != null) {
                return false;
            }
        } else if (!idContrato.equals(other.idContrato)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ContratoDTO [idContrato=" + idContrato + "]";
    }
   
}