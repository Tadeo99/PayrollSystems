package pe.buildsoft.erp.core.application.entidades.security;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

 

/**
 * La Class TipoUsuarioDTO.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Dec 14 00:27:45 COT 2017
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class TipoUsuarioDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id tipo usuario. */
    private Long idTipoUsuario;
   
    /** El descripcion. */
    private String descripcion;
   
    /** El codigo. */
    private String codigo;
   
    /** El codigo externo. */
    private String codigoExterno;
   
    /** El estado. */
    private String estado;
   
    /**
     * Instancia un nuevo tipo usuarioDTO.
     */
    public TipoUsuarioDTO() {
    }
   
   
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idTipoUsuario == null) ? 0 : idTipoUsuario.hashCode());
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
        TipoUsuarioDTO other = (TipoUsuarioDTO) obj;
        if (idTipoUsuario == null) {
            if (other.idTipoUsuario != null) {
                return false;
            }
        } else if (!idTipoUsuario.equals(other.idTipoUsuario)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TipoUsuarioDTO [idTipoUsuario=" + idTipoUsuario + "]";
    }
   
}