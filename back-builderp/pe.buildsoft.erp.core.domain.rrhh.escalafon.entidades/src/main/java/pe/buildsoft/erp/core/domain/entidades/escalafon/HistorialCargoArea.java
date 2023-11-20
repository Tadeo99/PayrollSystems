package pe.buildsoft.erp.core.domain.entidades.escalafon;

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
 * La Class HistorialCargoArea.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *	
 * @author ndavilal
 * @version 2.1, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "HistorialCargoArea", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_ESCAL)
@Getter
@Setter
public class HistorialCargoArea extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id historial cargo area. */
    @Id
    @Column(name = "idHistorialCargoArea" , length = 32)
    private String idHistorialCargoArea;
   
    /** El personal. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPersonal", referencedColumnName = "idPersonal")
    private Personal personal;
   
    /** El item by cargo.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCargo", referencedColumnName = "idItem")
    private Item itemByCargo; */
    @Column(name = "idCargo" , precision = 18 , scale = 0)
    private Long  idItemByCargo;
   
    /** El item by area. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idArea", referencedColumnName = "idItem")
    private Item itemByArea;*/
    @Column(name = "idArea" , precision = 18 , scale = 0)
    private Long  idItemByArea;
   
    /** El fecha desde. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaDesde")
    private OffsetDateTime fechaDesde;
   
    /** El observaciones. */
    @Column(name = "observaciones" , length = 300)
    private String observaciones;
   
    /**
     * Instancia un nuevo historial cargo area.
     */
    public HistorialCargoArea() {
		super();
    }
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idHistorialCargoArea == null) ? 0 : idHistorialCargoArea.hashCode());
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
        HistorialCargoArea other = (HistorialCargoArea) obj;
        if (idHistorialCargoArea == null) {
            if (other.idHistorialCargoArea != null) {
                return false;
            }
        } else if (!idHistorialCargoArea.equals(other.idHistorialCargoArea)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "HistorialCargoArea [idHistorialCargoArea=" + idHistorialCargoArea + "]";
    }
   
}