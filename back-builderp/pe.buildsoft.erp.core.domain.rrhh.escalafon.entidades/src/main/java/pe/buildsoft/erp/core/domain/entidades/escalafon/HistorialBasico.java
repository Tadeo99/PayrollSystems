package pe.buildsoft.erp.core.domain.entidades.escalafon;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

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
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class HistorialBasico.
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
@Table(name = "HistorialBasico", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_ESCAL)
@Getter
@Setter
public class HistorialBasico extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id historial basico. */
    @Id
    @Column(name = "idHistorialBasico" , length = 32)
    private String idHistorialBasico;
   
    /** El personal. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPersonal", referencedColumnName = "idPersonal")
    private Personal personal;
   
    /** El descripcion. */
    @Column(name = "descripcion" , length = 300)
    private String descripcion;
   
    /** El valor anterior. */
    @Column(name = "valorAnterior" , precision = 18 , scale = 2)
    private BigDecimal valorAnterior;
   
    /** El valor actual. */
    @Column(name = "valorActual" , precision = 18 , scale = 2)
    private BigDecimal valorActual;
   
    /** El personal responsable. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPersonalResponsable", referencedColumnName = "idPersonal")
    private Personal personalResponsable;
   
    /** El fecha cambio. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaCambio")
    private OffsetDateTime fechaCambio;
    
    /** El descripcion. */
   // @Column(name = "estado" , length = 1)
    @Transient
    private String estado;
   
    /**
     * Instancia un nuevo historial basico.
     */
    public HistorialBasico() {
		super();
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idHistorialBasico == null) ? 0 : idHistorialBasico.hashCode());
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
        HistorialBasico other = (HistorialBasico) obj;
        if (idHistorialBasico == null) {
            if (other.idHistorialBasico != null) {
                return false;
            }
        } else if (!idHistorialBasico.equals(other.idHistorialBasico)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "HistorialBasico [idHistorialBasico=" + idHistorialBasico + "]";
    }
   
}