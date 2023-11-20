package pe.buildsoft.erp.core.application.entidades.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

 

/**
 * La Class PrivilegioMenuDTO.
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
public class PrivilegioMenuDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id privilegio menu. */
    private String idPrivilegioMenu;
   
    /** El menu. */
    private MenuDTO menu;
   
    /** El privilegio. */
    private PrivilegioDTO privilegio;
   
    /** El estado. */
    private String estado;
   
    private List<Long> listaIdMenu = new ArrayList<>();
    
    /**
     * Instancia un nuevo privilegio menuDTO.
     */
    public PrivilegioMenuDTO() {
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
                + ((idPrivilegioMenu == null) ? 0 : idPrivilegioMenu.hashCode());
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
        PrivilegioMenuDTO other = (PrivilegioMenuDTO) obj;
        if (idPrivilegioMenu == null) {
            if (other.idPrivilegioMenu != null) {
                return false;
            }
        } else if (!idPrivilegioMenu.equals(other.idPrivilegioMenu)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PrivilegioMenuDTO [idPrivilegioMenu=" + idPrivilegioMenu + "]";
    }
   
}