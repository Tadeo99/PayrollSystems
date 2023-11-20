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

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class DetalleContrado.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Sun May 02 02:32:48 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity

@Table(name = "DetalleContrado", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_ESCAL)
@Getter
@Setter
public class DetalleContrado extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id detalle contrado. */
    @Id
    @Column(name = "idDetalleContrado" , length = 32)
    private String idDetalleContrado;
   
    /** El contrato. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idContrato", referencedColumnName = "idContrato")
    private Contrato contrato;
   
    /** El basico. */
    @Column(name = "basico" , precision = 18 , scale = 2)
    private BigDecimal basico;
   
    /** El horaley. */
    @Column(name = "horaley" , precision = 18 , scale = 0)
    private Long horaley;
   
    /** El monto dia. */
    @Column(name = "montoDia" , precision = 18 , scale = 2)
    private BigDecimal montoDia;
   
    /** El monto mes. */
    @Column(name = "montoMes" , precision = 18 , scale = 2)
    private BigDecimal montoMes;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /** El fecha. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fecha")
    private OffsetDateTime fecha;
   
    /**
     * Instancia un nuevo detalle contrado.
     */
    public DetalleContrado() {
		super();
    }
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idDetalleContrado == null) ? 0 : idDetalleContrado.hashCode());
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
        DetalleContrado other = (DetalleContrado) obj;
        if (idDetalleContrado == null) {
            if (other.idDetalleContrado != null) {
                return false;
            }
        } else if (!idDetalleContrado.equals(other.idDetalleContrado)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DetalleContrado [idDetalleContrado=" + idDetalleContrado + "]";
    }
   
}