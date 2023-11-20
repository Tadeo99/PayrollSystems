package pe.buildsoft.erp.core.application.entidades.escalafon;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class PeriodoLaboraPersonalDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 23:29:58 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class PeriodoLaboraPersonalDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id periodo labora personal. */
    private String idPeriodoLaboraPersonal;
   
    /** El personal. */
    private PersonalDTO personal;
   
    /** El situacion. */
    private String situacion;
   
    /** El fecha ingrreso. */
    private OffsetDateTime fechaIngrreso;
   
    /** El fecha cese. */
    private OffsetDateTime fechaCese;
   
    /** El item by motivo cese. */
    private Long  idItemByMotivoCese;
    private ItemDTO itemByMotivoCese;
   
    /** El liquitado. */
    private String liquitado;
   
    /**
     * Instancia un nuevo periodo labora personalDTO.
     */
    public PeriodoLaboraPersonalDTO() {
		super();
    }
   
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idPeriodoLaboraPersonal == null) ? 0 : idPeriodoLaboraPersonal.hashCode());
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
        PeriodoLaboraPersonalDTO other = (PeriodoLaboraPersonalDTO) obj;
        if (idPeriodoLaboraPersonal == null) {
            if (other.idPeriodoLaboraPersonal != null) {
                return false;
            }
        } else if (!idPeriodoLaboraPersonal.equals(other.idPeriodoLaboraPersonal)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PeriodoLaboraPersonalDTO [idPeriodoLaboraPersonal=" + idPeriodoLaboraPersonal + "]";
    }
   
}