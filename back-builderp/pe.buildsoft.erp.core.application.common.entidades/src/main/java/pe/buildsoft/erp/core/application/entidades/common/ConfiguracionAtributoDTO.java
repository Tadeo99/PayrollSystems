package pe.buildsoft.erp.core.application.entidades.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;

 

/**
 * La Class ConfiguracionAtributoDTO.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Tue Apr 09 11:11:54 COT 2019
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class ConfiguracionAtributoDTO implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id configuracion atributo. */
    private String idConfiguracionAtributo;
   
    /** El item by id nombre entidad. */
    private ItemDTO itemByIdNombreEntidad;
   
    /** El item by id componte. */
    private ItemDTO itemByIdComponte;
   
    /** El nombre atributo. */
    private String nombreAtributo;
   
    /** El key label. */
    private String keyLabel;
   
    /** El tipo dato. */
    private String tipoDato;
   
    /** El formato. */
    private String formato;
   
    /** El lista item. */
    private ListaItemsDTO listaItem;
   
    /** El nivel. */
    private Long nivel;
   
    /** El es persistente. */
    private Boolean esPersistente;
   
    /** El orden. */
    private Long orden;
   
    /** El estado. */
    private String estado;
   
    /** El configuracion atributo configuracion atributo value list. */
    private List<ConfiguracionAtributoValueDTO> configuracionAtributoConfiguracionAtributoValueList = new ArrayList<>();
    
    private List<SelectItemVO> listaSelectItemVO = new ArrayList<>();

    private List<Long> listaSelectItemSelectedVO = new ArrayList<>();

    private ItemDTO itemAtributoValue = new ItemDTO();

    private String nombreAtributoID = null;
    
    private OffsetDateTime valueDate= null;
    private BigDecimal valueNumeric= null;
    private Boolean valueBoolean  = false;
    private String valueText  = null;
    
    private String idTupla = "";
    
    private Object id  = null;
   
    /**
     * Instancia un nuevo configuracion atributoDTO.
     */
    public ConfiguracionAtributoDTO() {
    	super();
    }
   

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
        ConfiguracionAtributoDTO other = (ConfiguracionAtributoDTO) obj;
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
        return "ConfiguracionAtributoDTO [idConfiguracionAtributo=" + idConfiguracionAtributo + "]";
    }
   
}