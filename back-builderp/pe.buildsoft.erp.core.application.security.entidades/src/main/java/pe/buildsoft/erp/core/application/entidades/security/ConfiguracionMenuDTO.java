package pe.buildsoft.erp.core.application.entidades.security;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class ConfiguracionMenuDTO.
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
public class ConfiguracionMenuDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id configuracion menu. */
    private String idConfiguracionMenu;
   
    /** El menu. */
    private MenuDTO menu;
   
    /** El item by componente. */
    private ItemDTO itemByComponente;
   
    /** El properties. */
    private PropertiesDTO properties;
   
    /** El required. */
    private Boolean required;
   
    /** El readonly. */
    private Boolean readonly;
   
    /** El rendered. */
    private Boolean rendered;
   
    /** El disabled. */
    private Boolean disabled;
   
    /** El estado. */
    private String estado;
   
    /** El usuario modificacion. */
    private String usuarioModificacion;
   
    /** El fecha modificacion. */
    private OffsetDateTime fechaModificacion;
   
  //trasient	
  	/** El flag show required. */
  	private boolean showRequired;
  	
  	/** El flag show readonly. */
  	private boolean showReadonly;
  	
  	/** El flag show rendered. */
  	private boolean showRendered;
  	
  	/** El flag show disabled. */
  	private boolean showDisabled;
  	
    /**
     * Instancia un nuevo configuracion menuDTO.
     */
    public ConfiguracionMenuDTO() {
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
                + ((idConfiguracionMenu == null) ? 0 : idConfiguracionMenu.hashCode());
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
        ConfiguracionMenuDTO other = (ConfiguracionMenuDTO) obj;
        if (idConfiguracionMenu == null) {
            if (other.idConfiguracionMenu != null) {
                return false;
            }
        } else if (!idConfiguracionMenu.equals(other.idConfiguracionMenu)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ConfiguracionMenuDTO [idConfiguracionMenu=" + idConfiguracionMenu + "]";
    }
   
}