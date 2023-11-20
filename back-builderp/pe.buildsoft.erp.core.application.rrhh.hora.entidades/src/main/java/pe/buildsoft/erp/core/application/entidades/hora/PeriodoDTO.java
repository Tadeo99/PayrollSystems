package pe.buildsoft.erp.core.application.entidades.hora;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class PeriodoDTO.
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
public class PeriodoDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id periodo. */
    private String idPeriodo;
   
    /** El nombre. */
    private String nombre;
   
    /** El fecha inicio. */
    private OffsetDateTime fechaInicio;
   
    /** El fecha fin. */
    private OffsetDateTime fechaFin;
   
    /** El estado. */
    private String estado;
   
    /**
     * Instancia un nuevo periodoDTO.
     */
    public PeriodoDTO() {
		super();
    }
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idPeriodo == null) ? 0 : idPeriodo.hashCode());
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
        PeriodoDTO other = (PeriodoDTO) obj;
        if (idPeriodo == null) {
            if (other.idPeriodo != null) {
                return false;
            }
        } else if (!idPeriodo.equals(other.idPeriodo)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PeriodoDTO [idPeriodo=" + idPeriodo + "]";
    }
   
}