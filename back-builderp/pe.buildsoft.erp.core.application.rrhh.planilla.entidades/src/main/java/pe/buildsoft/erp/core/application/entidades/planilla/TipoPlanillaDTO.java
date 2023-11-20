package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class TipoPlanillaDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 23:10:03 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class TipoPlanillaDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id tipo planilla. */
    private String idTipoPlanilla;
   
    /** El codigo. */
    private String codigo;
   
    /** El item by tipo moneda. */
    private Long idItemByTipoMoneda;
    private ItemDTO itemByTipoMoneda;
   
    /** El descripcion. */
    private String descripcion;
   
    /** El item by categoria ocupacional. */
    private Long idItemByCategoriaTrabajador;
    private ItemDTO itemByCategoriaTrabajador;
   
    /** El tipo planilla conceptos tipo planilla list. */
    private List<ConceptosTipoPlanillaDTO> tipoPlanillaConceptosTipoPlanillaList = new ArrayList<>();
   
    /**
     * Instancia un nuevo tipo planillaDTO.
     */
    public TipoPlanillaDTO() {
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
                + ((idTipoPlanilla == null) ? 0 : idTipoPlanilla.hashCode());
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
        TipoPlanillaDTO other = (TipoPlanillaDTO) obj;
        if (idTipoPlanilla == null) {
            if (other.idTipoPlanilla != null) {
                return false;
            }
        } else if (!idTipoPlanilla.equals(other.idTipoPlanilla)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TipoPlanillaDTO [idTipoPlanilla=" + idTipoPlanilla + "]";
    }
   
}