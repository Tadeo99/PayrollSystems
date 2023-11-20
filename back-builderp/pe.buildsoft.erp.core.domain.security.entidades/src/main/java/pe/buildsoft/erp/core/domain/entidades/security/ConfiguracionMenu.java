package pe.buildsoft.erp.core.domain.entidades.security;

import java.io.Serializable;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ConfiguracionMenu.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Dec 13 22:04:25 COT 2017
 * @since SIAA-CORE 2.1
 */
@Entity
@Table(name = "ConfiguracionMenu", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_SECURIDAD)
@Getter
@Setter
public class ConfiguracionMenu extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id configuracion menu. */
    @Id
    @Column(name = "idConfiguracionMenu" , length = 32)
    private String idConfiguracionMenu;
   
    /** El menu. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMenu", referencedColumnName = "idMenu")
    private Menu menu;
   
    /** El item by componente. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idComponente", referencedColumnName = "idItem")
    private Item itemByComponente;*/
    @Column(name = "idComponente" , precision = 18 , scale = 0)
    private Long idItemByComponente;
   
    /** El properties. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProperties", referencedColumnName = "idProperties")
    private Properties properties;
   
    /** El required. */
    @Column(name = "required")
    private Boolean required;
   
    /** El readonly. */
    @Column(name = "readonly")
    private Boolean readonly;
   
    /** El rendered. */
    @Column(name = "rendered")
    private Boolean rendered;
   
    /** El disabled. */
    @Column(name = "disabled")
    private Boolean disabled;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /** El usuario modificacion. */
    @Column(name = "usuarioModificacion" , length = 50)
    private String usuarioModificacion;
   
    /** El fecha modificacion. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaModificacion")
    private OffsetDateTime fechaModificacion;
   
    /**
     * Instancia un nuevo configuracion menu.
     */
    public ConfiguracionMenu() {
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
        ConfiguracionMenu other = (ConfiguracionMenu) obj;
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
        return "ConfiguracionMenu [idConfiguracionMenu=" + idConfiguracionMenu + "]";
    }
   
}