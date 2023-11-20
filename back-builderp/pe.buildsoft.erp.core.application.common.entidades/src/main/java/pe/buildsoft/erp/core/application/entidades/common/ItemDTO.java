package pe.buildsoft.erp.core.application.entidades.common;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * La Class ItemDTO.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Tue Apr 18 09:56:47 COT 2017
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class ItemDTO implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id item. */
    private Long idItem;
   
    /** El lista items. */
    private ListaItemsDTO listaItems;
   
    /** El descripcion. */
    private String descripcion;
   
    /** El nombre. */
    private String nombre;
   
    /** El codigo. */
    private Long codigo;
   
    /** El codigo externo. */
    private String codigoExterno;
   
    /** El estado. */
    private String estado;
    
    private Object planContable;
    
    private String abreviatura;
    private String observacion;
    
    private String tipo = "";
    
    private String idPadreView;
    
    private String descripcionView;
    
    /**
     * Instancia un nuevo itemDTO.
     */
    public ItemDTO() {
    	super();
    }
    
    public ItemDTO(Long idItem) {
    	 super();
         this.idItem = idItem;
    }
   
    
	/* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idItem == null) ? 0 : idItem.hashCode());
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
        ItemDTO other = (ItemDTO) obj;
        if (idItem == null) {
            if (other.idItem != null) {
                return false;
            }
        } else if (!idItem.equals(other.idItem)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ItemDTO [idItem=" + idItem + "]";
    }
   
}