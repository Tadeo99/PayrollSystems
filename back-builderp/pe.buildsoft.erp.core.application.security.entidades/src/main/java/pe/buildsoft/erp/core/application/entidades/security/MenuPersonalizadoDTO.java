package pe.buildsoft.erp.core.application.entidades.security;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

 

/**
 * La Class MenuPersonalizadoDTO.
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
public class MenuPersonalizadoDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id menu personalizado. */
    private String idMenuPersonalizado;
   
    /** El persona. */
    private UsuarioDTO persona;
   
    /** El menu. */
    private MenuDTO menu;
   
    /** El estado. */
    private String estado;
   
    /** El fecha creacion. */
    private OffsetDateTime fechaCreacion;
   
    /** El usuario creacion. */
    private String usuarioCreacion;
   
    /**
     * Instancia un nuevo menu personalizadoDTO.
     */
    public MenuPersonalizadoDTO() {
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
                + ((idMenuPersonalizado == null) ? 0 : idMenuPersonalizado.hashCode());
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
        MenuPersonalizadoDTO other = (MenuPersonalizadoDTO) obj;
        if (idMenuPersonalizado == null) {
            if (other.idMenuPersonalizado != null) {
                return false;
            }
        } else if (!idMenuPersonalizado.equals(other.idMenuPersonalizado)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MenuPersonalizadoDTO [idMenuPersonalizado=" + idMenuPersonalizado + "]";
    }
   
}