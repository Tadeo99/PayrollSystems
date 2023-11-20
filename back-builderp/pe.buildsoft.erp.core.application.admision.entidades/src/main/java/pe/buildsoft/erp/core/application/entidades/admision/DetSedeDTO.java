package pe.buildsoft.erp.core.application.entidades.admision;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

 

/**
 * La Class DetSedeDTO.
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
public class DetSedeDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id det sede. */
    private String idDetSede;
   
    /** El sede. */
    private SedeDTO sede;
   
    /** El grado. */
    private GradoDTO grado;
   
    /** El nro vacante. */
    private Long nroVacante;
   
    /**
     * Instancia un nuevo det sedeDTO.
     */
    public DetSedeDTO() {
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
                + ((idDetSede == null) ? 0 : idDetSede.hashCode());
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
        DetSedeDTO other = (DetSedeDTO) obj;
        if (idDetSede == null) {
            if (other.idDetSede != null) {
                return false;
            }
        } else if (!idDetSede.equals(other.idDetSede)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DetSedeDTO [idDetSede=" + idDetSede + "]";
    }
   
}