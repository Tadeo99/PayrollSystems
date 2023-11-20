package pe.buildsoft.erp.core.application.entidades.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

 

/**
 * La Class GrupoUsuarioDTO.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Dec 14 00:27:42 COT 2017
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class GrupoUsuarioDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id grupo usuario. */
    private Long idGrupoUsuario;
   
    /** El descripcion. */
    private String descripcion;
   
    /** El estado. */
    private String estado;
    
    private List<Long> menus = new ArrayList<Long>();
    
	private String usuarioSession;
   
    /**
     * Instancia un nuevo grupo usuarioDTO.
     */
    public GrupoUsuarioDTO() {
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
                + ((idGrupoUsuario == null) ? 0 : idGrupoUsuario.hashCode());
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
        GrupoUsuarioDTO other = (GrupoUsuarioDTO) obj;
        if (idGrupoUsuario == null) {
            if (other.idGrupoUsuario != null) {
                return false;
            }
        } else if (!idGrupoUsuario.equals(other.idGrupoUsuario)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GrupoUsuarioDTO [idGrupoUsuario=" + idGrupoUsuario + "]";
    }
   
}