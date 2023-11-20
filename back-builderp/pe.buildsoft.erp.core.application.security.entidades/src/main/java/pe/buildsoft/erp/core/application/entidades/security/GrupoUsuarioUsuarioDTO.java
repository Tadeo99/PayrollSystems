package pe.buildsoft.erp.core.application.entidades.security;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

 

/**
 * La Class GrupoUsuarioUsuarioDTO.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Dec 14 00:27:43 COT 2017
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class GrupoUsuarioUsuarioDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id grupo usuario usuario. */
    private String idGrupoUsuarioUsuario;
   
    /** El grupo usuario. */
    private GrupoUsuarioDTO grupoUsuario;
   
    /** El usuario. */
    private UsuarioDTO usuario;
   
    /** El estado. */
    private String estado;
   
    /** El fecha creacion. */
    private OffsetDateTime fechaCreacion;
   
    /** El usuario creacion. */
    private String usuarioCreacion;
   
    /**
     * Instancia un nuevo grupo usuario usuarioDTO.
     */
    public GrupoUsuarioUsuarioDTO() {
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
                + ((idGrupoUsuarioUsuario == null) ? 0 : idGrupoUsuarioUsuario.hashCode());
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
        GrupoUsuarioUsuarioDTO other = (GrupoUsuarioUsuarioDTO) obj;
        if (idGrupoUsuarioUsuario == null) {
            if (other.idGrupoUsuarioUsuario != null) {
                return false;
            }
        } else if (!idGrupoUsuarioUsuario.equals(other.idGrupoUsuarioUsuario)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GrupoUsuarioUsuarioDTO [idGrupoUsuarioUsuario=" + idGrupoUsuarioUsuario + "]";
    }
   
}