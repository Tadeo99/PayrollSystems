package pe.buildsoft.erp.core.domain.entidades.common;

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

/**
 * La Class ConfiguracionAtributoValue.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 09 11:08:27 COT 2019
 * @since SIAA-CORE 2.1
 */
@Entity
@Table(name = "ConfiguracionAtributoValue", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
@Getter
@Setter
public class ConfiguracionAtributoValue implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id configuracion atributo value. */
    @Id
    @Column(name = "idConfiguracionAtributoValue" , length = 32)
    private String idConfiguracionAtributoValue;
   
    /** El configuracion atributo. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idConfiguracionAtributo", referencedColumnName = "idConfiguracionAtributo")
    private ConfiguracionAtributo configuracionAtributo;
   
    /** El id tupla entidad. */
    @Column(name = "idTuplaEntidad" , length = 32)
    private String idTuplaEntidad;
   
    /** El value. */
    @Column(name = "value" , length = 200)
    private String value;
   
    /**
     * Instancia un nuevo configuracion atributo value.
     */
    public ConfiguracionAtributoValue() {
		super();
    }
   
    /**
     * Instancia un nuevo configuracion atributo value.
     *
     * @param idConfiguracionAtributoValue el id configuracion atributo value
     * @param configuracionAtributo el configuracion atributo
     * @param idTuplaEntidad el id tupla entidad
     * @param value el value
     */
    public ConfiguracionAtributoValue(String idConfiguracionAtributoValue, ConfiguracionAtributo configuracionAtributo,String idTuplaEntidad, String value ) {
        super();
        this.idConfiguracionAtributoValue = idConfiguracionAtributoValue;
        this.configuracionAtributo = configuracionAtributo;
        this.idTuplaEntidad = idTuplaEntidad;
        this.value = value;
    }
   
   
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idConfiguracionAtributoValue == null) ? 0 : idConfiguracionAtributoValue.hashCode());
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
        ConfiguracionAtributoValue other = (ConfiguracionAtributoValue) obj;
        if (idConfiguracionAtributoValue == null) {
            if (other.idConfiguracionAtributoValue != null) {
                return false;
            }
        } else if (!idConfiguracionAtributoValue.equals(other.idConfiguracionAtributoValue)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ConfiguracionAtributoValue [idConfiguracionAtributoValue=" + idConfiguracionAtributoValue + "]";
    }
   
}