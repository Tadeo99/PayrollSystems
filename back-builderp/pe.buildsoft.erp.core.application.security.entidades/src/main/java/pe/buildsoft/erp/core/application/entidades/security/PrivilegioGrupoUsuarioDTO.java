package pe.buildsoft.erp.core.application.entidades.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

 

/**
 * La Class PrivilegioGrupoUsuarioDTO.
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
public class PrivilegioGrupoUsuarioDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id privilegio grupo usuario. */
    private String idPrivilegioGrupoUsuario;
   
    /** El grupo usuario. */
    private GrupoUsuarioDTO grupoUsuario;
   
    /** El privilegio. */
    private PrivilegioDTO privilegio;
   
    /** El estado. */
    private String estado;
   
    /**
     * Instancia un nuevo privilegio grupo usuarioDTO.
     */
    public PrivilegioGrupoUsuarioDTO() {
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
                + ((idPrivilegioGrupoUsuario == null) ? 0 : idPrivilegioGrupoUsuario.hashCode());
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
        PrivilegioGrupoUsuarioDTO other = (PrivilegioGrupoUsuarioDTO) obj;
        if (idPrivilegioGrupoUsuario == null) {
            if (other.idPrivilegioGrupoUsuario != null) {
                return false;
            }
        } else if (!idPrivilegioGrupoUsuario.equals(other.idPrivilegioGrupoUsuario)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PrivilegioGrupoUsuarioDTO [idPrivilegioGrupoUsuario=" + idPrivilegioGrupoUsuario + "]";
    }
   
}