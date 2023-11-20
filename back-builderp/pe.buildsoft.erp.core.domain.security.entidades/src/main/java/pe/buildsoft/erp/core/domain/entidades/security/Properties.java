package pe.buildsoft.erp.core.domain.entidades.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Properties.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Dec 13 22:04:27 COT 2017
 * @since SIAA-CORE 2.1
 */
@Entity
@Table(name = "Properties", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_SECURIDAD)
@Getter
@Setter
public class Properties extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id properties. */
    @Id
    @Column(name = "idProperties" , precision = 18 , scale = 0)
    private Long idProperties;
   
    /** El name. */
    @Column(name = "name" , length = 100)
    private String name;
   
    /** El descripcion. */
    @Column(name = "descripcion" , length = 100)
    private String descripcion;
   
    /** El value default. */
    @Column(name = "valueDefault" , length = 100)
    private String valueDefault;
   
    /** El value. */
    @Column(name = "value" , length = 100)
    private String value;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /** El properties configuracion menu list. */
//    @OneToMany(mappedBy = "properties", fetch = FetchType.LAZY)
    @Transient
    private List<ConfiguracionMenu> propertiesConfiguracionMenuList = new ArrayList<>();
    
    /** El properties properties lenguaje list. */
//    @OneToMany(mappedBy = "properties", fetch = FetchType.LAZY)
    @Transient
    private List<PropertiesLenguaje> propertiesPropertiesLenguajeList = new ArrayList<>();
    
    /**
     * Instancia un nuevo properties.
     */
    public Properties() {
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
                + ((idProperties == null) ? 0 : idProperties.hashCode());
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
        Properties other = (Properties) obj;
        if (idProperties == null) {
            if (other.idProperties != null) {
                return false;
            }
        } else if (!idProperties.equals(other.idProperties)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Properties [idProperties=" + idProperties + "]";
    }
   
}