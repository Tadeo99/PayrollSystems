package pe.buildsoft.erp.core.domain.entidades.common;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;

/**
 * La Class Item.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 09:56:47 COT 2017
 * @since SIAA-CORE 2.1
 */
@Entity
@Table(name = "Item", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
@Getter
@Setter
public class Item implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id item. */
    @Id
    @Column(name = "idItem" , precision = 18 , scale = 0)
    private Long idItem;
   
    /** El lista items. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idListaitems", referencedColumnName = "idListaItems")
    private ListaItems listaItems;
   
    /** El descripcion. */
    @Column(name = "descripcion" , length = 250)
    private String descripcion;
   
    /** El nombre. */
    @Column(name = "nombre" , length = 250)
    private String nombre;
   
    /** El codigo. */
    @Column(name = "codigo" , precision = 18 , scale = 0)
    private Long codigo;
   
    /** El codigo externo. */
    @Column(name = "codigoExterno" , length = 20)
    private String codigoExterno;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
    
    @Column(name = "abreviatura" , length = 250)
    private String abreviatura;
    
    @Column(name = "observacion" , length = 1000)
    private String observacion;
    
    @Transient
    private String tipo;
    
    /**
     * Instancia un nuevo item.
     */
    public Item() {
    	super();
    }
    
    public Item(Long idItem) {
    	super();
        this.idItem = idItem;
    }
   
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
        Item other = (Item) obj;
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
        return "Item [idItem=" + idItem + "]";
    }
   
}