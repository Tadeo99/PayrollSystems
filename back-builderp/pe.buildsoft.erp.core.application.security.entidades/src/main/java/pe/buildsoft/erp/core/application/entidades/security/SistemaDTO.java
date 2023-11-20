package pe.buildsoft.erp.core.application.entidades.security;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

 

/**
 * La Class SistemaDTO.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Dec 14 00:27:44 COT 2017
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class SistemaDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id sistema. */
    private Long idSistema;
   
    /** El nombre. */
    private String nombre;
   
    /** El descripcion. */
    private String descripcion;
   
    /** El abreviatura. */
    private String abreviatura;
   
    /** El fecha. */
    private OffsetDateTime fecha;
   
    /** El version. */
    private String version;
   
    /** El estado. */
    private String estado;
   
    /** El icono. */
    private String icono;
   
   
    /**
     * Instancia un nuevo sistemaDTO.
     */
    public SistemaDTO() {
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
                + ((idSistema == null) ? 0 : idSistema.hashCode());
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
        SistemaDTO other = (SistemaDTO) obj;
        if (idSistema == null) {
            if (other.idSistema != null) {
                return false;
            }
        } else if (!idSistema.equals(other.idSistema)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SistemaDTO [idSistema=" + idSistema + "]";
    }
   
}