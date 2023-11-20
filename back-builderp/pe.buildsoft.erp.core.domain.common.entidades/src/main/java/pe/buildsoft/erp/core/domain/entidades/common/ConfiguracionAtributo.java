package pe.buildsoft.erp.core.domain.entidades.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;

/**
 * La Class ConfiguracionAtributo.
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
@Table(name = "ConfiguracionAtributo", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
@Getter
@Setter
public class ConfiguracionAtributo implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id configuracion atributo. */
    @Id
    @Column(name = "idConfiguracionAtributo" , length = 32)
    private String idConfiguracionAtributo;
   
    /** El item by id nombre entidad. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idNombreEntidad", referencedColumnName = "idItem")
    private Item itemByIdNombreEntidad;
   
    /** El item by id componte. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idComponte", referencedColumnName = "idItem")
    private Item itemByIdComponte;
   
    /** El nombre atributo. */
    @Column(name = "nombreAtributo" , length = 20)
    private String nombreAtributo;
   
    /** El key label. */
    @Column(name = "keyLabel" , length = 150)
    private String keyLabel;
   
    /** El tipo dato. */
    @Column(name = "tipoDato" , length = 1)
    private String tipoDato;
   
    /** El formato. */
    @Column(name = "formato" , length = 20)
    private String formato;
   
    /** El lista item. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idListaItem", referencedColumnName = "idListaItems")
    private ListaItems listaItem;
   
    /** El nivel. */
    @Column(name = "nivel" , length = 18)
    private Long nivel;
   
    /** El es persistente. */
    @Column(name = "esPersistente")
    private Boolean esPersistente;
   
    /** El orden. */
    @Column(name = "orden" , length = 18)
    private Long orden;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
    
    @Transient
    private String idTupla = "";
    
    @Transient
    private OffsetDateTime valueDate= null;
    @Transient
    private BigDecimal valueNumeric= null;
    @Transient
    private Boolean valueBoolean  = false;
    @Transient
    private String valueText  = null;
    
    @Transient
    private Item itemAtributoValue = new Item();
    
    @Transient
    private List<Long> listaSelectItemSelectedVO = new ArrayList<>();
   
    /** El configuracion atributo configuracion atributo value list. */
//    @OneToMany(mappedBy = "configuracionAtributo", fetch = FetchType.LAZY)
    @Transient
    private List<ConfiguracionAtributoValue> configuracionAtributoConfiguracionAtributoValueList = new ArrayList<>();
    
    @Transient
    private Object id  = null;
    /**
     * Instancia un nuevo configuracion atributo.
     */
    public ConfiguracionAtributo() {
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
                + ((idConfiguracionAtributo == null) ? 0 : idConfiguracionAtributo.hashCode());
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
        ConfiguracionAtributo other = (ConfiguracionAtributo) obj;
        if (idConfiguracionAtributo == null) {
            if (other.idConfiguracionAtributo != null) {
                return false;
            }
        } else if (!idConfiguracionAtributo.equals(other.idConfiguracionAtributo)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ConfiguracionAtributo [idConfiguracionAtributo=" + idConfiguracionAtributo + "]";
    }
   
}