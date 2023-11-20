package pe.buildsoft.erp.core.application.entidades.admision;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class AsignaPostulanteDTO.
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
public class AsignaPostulanteDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id asigna postulante. */
    private String idAsignaPostulante;
   
    /** El apoderado. */
    private String apoderado;
   
    /** El postulante. */
    private PostulanteDTO postulante;
   
    /** El sede. */
    private String sede;
   
    /** El grado. */
    private GradoDTO grado;
   
    /** El anho. */
    private Long anho;
   
    /** El periodo. */
    private Long periodo;
   
    /** El estado. */
    private String estado;
    
    /** El nro doc. */
    private String nroDoc;//comodin
   
    /**
     * Instancia un nuevo asigna postulanteDTO.
     */
    public AsignaPostulanteDTO() {
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
                + ((idAsignaPostulante == null) ? 0 : idAsignaPostulante.hashCode());
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
        AsignaPostulanteDTO other = (AsignaPostulanteDTO) obj;
        if (idAsignaPostulante == null) {
            if (other.idAsignaPostulante != null) {
                return false;
            }
        } else if (!idAsignaPostulante.equals(other.idAsignaPostulante)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AsignaPostulanteDTO [idAsignaPostulante=" + idAsignaPostulante + "]";
    }
   
}