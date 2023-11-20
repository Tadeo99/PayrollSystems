package pe.buildsoft.erp.core.application.entidades.hora;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.escalafon.PersonalDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class RequerimientoPersonalDTO.
 * <ul>
 * <li>Copyright 2021 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Wed Feb 23 11:22:44 COT 2022
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class RequerimientoPersonalDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id requerimiento empleado. */
    private String idRequerimientoPersonal;
   
    /** El id requerimiento. */
    private RequerimientoDTO requerimiento;
   
    /** El id personal. */
    private PersonalDTO personal= new PersonalDTO();
    
    private PeriodoDTO periodo= new PeriodoDTO();
   
    /**
     * Instancia un nuevo requerimiento personalDTO.
     */
    public RequerimientoPersonalDTO() {
		super();
    }
   
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
        RequerimientoPersonalDTO other = (RequerimientoPersonalDTO) obj;
        if (idRequerimientoPersonal == null) {
            if (other.idRequerimientoPersonal != null) {
                return false;
            }
        } else if (!idRequerimientoPersonal.equals(other.idRequerimientoPersonal)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "RequerimientoPersonalDTO [idRequerimientoPersonal=" + idRequerimientoPersonal + "]";
    }
   
}