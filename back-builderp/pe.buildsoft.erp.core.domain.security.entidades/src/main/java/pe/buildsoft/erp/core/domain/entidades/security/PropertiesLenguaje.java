package pe.buildsoft.erp.core.domain.entidades.security;

import java.io.Serializable;

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
 * La Class PropertiesLenguaje.
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
@Table(name = "PropertiesLenguaje", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_SECURIDAD)
@Getter
@Setter
public class PropertiesLenguaje extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id properties lenguaje. */
    @Id
    @Column(name = "idPropertiesLenguaje" , length = 32)
    private String idPropertiesLenguaje;
   
    /** El item by lenguaje. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idLenguaje", referencedColumnName = "idItem")
    private Item itemByLenguaje;*/
    @Column(name = "idLenguaje" , precision = 18 , scale = 0)
    private Long idItemByLenguaje;
   
    /** El properties. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProperties", referencedColumnName = "idProperties")
    private Properties properties;
   
    /** El value. */
    @Column(name = "value" , length = 100)
    private String value;
   
    /**
     * Instancia un nuevo properties lenguaje.
     */
    public PropertiesLenguaje() {
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
                + ((idPropertiesLenguaje == null) ? 0 : idPropertiesLenguaje.hashCode());
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
        PropertiesLenguaje other = (PropertiesLenguaje) obj;
        if (idPropertiesLenguaje == null) {
            if (other.idPropertiesLenguaje != null) {
                return false;
            }
        } else if (!idPropertiesLenguaje.equals(other.idPropertiesLenguaje)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PropertiesLenguaje [idPropertiesLenguaje=" + idPropertiesLenguaje + "]";
    }
   
}