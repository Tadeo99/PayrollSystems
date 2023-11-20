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
 * La Class AsociarCentroCosto.
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
@Table(name = "AsociarCentroCosto", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_ESCAL)
@Getter
@Setter
public class AsociarCentroCosto extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id asociar centro costo. */
    @Id
    @Column(name = "idAsociarCentroCosto" , length = 32)
    private String idAsociarCentroCosto;
   
    /** El personal. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPersonal", referencedColumnName = "idPersonal")
    private Personal personal;
   
    /** El planilla. */
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "idPlanilla", referencedColumnName = "idPlanilla")
    @Column(name = "idPlanilla" , length = 32)
    private String planilla;
   
    /** El centro costo. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCentroCosto", referencedColumnName = "idCentroCosto")
    private CentroCosto centroCosto;
   
    /** El fecha inicio. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaInicio")
    private OffsetDateTime fechaInicio;
   
    /** El fecha final. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaFinal")
    private OffsetDateTime fechaFinal;
   
    /** El porcentaje. */
    @Column(name = "porcentaje" , precision = 18 , scale = 2)
    private BigDecimal porcentaje;
   
    /**
     * Instancia un nuevo asociar centro costo.
     */
    public AsociarCentroCosto() {
		super();
    }
   
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idAsociarCentroCosto == null) ? 0 : idAsociarCentroCosto.hashCode());
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
        AsociarCentroCosto other = (AsociarCentroCosto) obj;
        if (idAsociarCentroCosto == null) {
            if (other.idAsociarCentroCosto != null) {
                return false;
            }
        } else if (!idAsociarCentroCosto.equals(other.idAsociarCentroCosto)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AsociarCentroCosto [idAsociarCentroCosto=" + idAsociarCentroCosto + "]";
    }
   
}