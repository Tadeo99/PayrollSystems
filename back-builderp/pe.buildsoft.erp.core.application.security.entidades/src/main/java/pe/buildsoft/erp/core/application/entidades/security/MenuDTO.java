package pe.buildsoft.erp.core.application.entidades.security;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

 

/**
 * La Class MenuDTO.
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
public class MenuDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id menu. */
    private Long idMenu;
   
    /** El sistema. */
    private SistemaDTO sistema;
   
    /** El nombre. */
    private String nombre;
   
    /** El descripcion. */
    private String descripcion;
   
    /** El url. */
    private String url;
   
    /** El parametro. */
    private String parametro;
   
    /** El icono. */
    private String icono;
   
    /** El target. */
    private String target;
   
    /** El menu padre. */
    private MenuDTO menuPadre;
   
    /** El fecha creacion. */
    private OffsetDateTime fechaCreacion;
   
    /** El usuario creacion. */
    private String usuarioCreacion;
   
    /** El fecha modificacion. */
    private OffsetDateTime fechaModificacion;
   
    /** El usuario modificacion. */
    private String usuarioModificacion;
   
    /** El estado. */
    private String estado;
   
    /** El menu padre menu list. */
    private List<MenuDTO> menuHijos = new ArrayList<>();
   
   
    /**
     * Instancia un nuevo menuDTO.
     */
    public MenuDTO() {
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
                + ((idMenu == null) ? 0 : idMenu.hashCode());
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
        MenuDTO other = (MenuDTO) obj;
        if (idMenu == null) {
            if (other.idMenu != null) {
                return false;
            }
        } else if (!idMenu.equals(other.idMenu)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MenuDTO [idMenu=" + idMenu + "]";
    }
   
}