package pe.buildsoft.erp.core.application.entidades.escalafon;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

 

/**
 * La Class CarreraDTO.
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
public class CarreraDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id carrera. */
    private Long idCarrera;
   
    /** El institucion. */
    private InstitucionDTO institucion;
   
    /** El codigo. */
    private String codigo;
   
    /** El nombre. */
    private String nombre;
   
    /** El estado. */
    private String estado;
   
    /**
     * Instancia un nuevo carreraDTO.
     */
    public CarreraDTO() {
		super();
    }
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idCarrera == null) ? 0 : idCarrera.hashCode());
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
        CarreraDTO other = (CarreraDTO) obj;
        if (idCarrera == null) {
            if (other.idCarrera != null) {
                return false;
            }
        } else if (!idCarrera.equals(other.idCarrera)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CarreraDTO [idCarrera=" + idCarrera + "]";
    }
   
}