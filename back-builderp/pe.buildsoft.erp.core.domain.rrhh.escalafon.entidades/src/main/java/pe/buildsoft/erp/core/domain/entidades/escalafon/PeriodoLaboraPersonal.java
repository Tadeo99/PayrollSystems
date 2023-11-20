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
 * La Class PeriodoLaboraPersonal.
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
@Table(name = "PeriodoLaboraPersonal", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_ESCAL)
@Getter
@Setter
public class PeriodoLaboraPersonal extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id periodo laboral personal. */
    @Id
    @Column(name = "idPeriodoLaboraPersonal" , length = 32)
    private String idPeriodoLaboraPersonal;
   
    /** El personal. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPersonal", referencedColumnName = "idPersonal")
    private Personal personal;
   
    /** El situacion. */
    @Column(name = "situacion" , length = 100)
    private String situacion;
   
    /** El fecha ingrreso. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaIngrreso")
    private OffsetDateTime fechaIngrreso;
   
    /** El fecha cese. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaCese")
    private OffsetDateTime fechaCese;
   
    /** El item by motivo cese. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMotivoCese", referencedColumnName = "idItem")
    private Item itemByMotivoCese;*/
    @Column(name = "idMotivoCese" , precision = 18 , scale = 0)
    private Long  idItemByMotivoCese;
   
    /** El liquitado. */
    @Column(name = "liquitado" , length = 1)
    private String liquitado;
   
    
    /**
     * Instancia un nuevo periodo labora personal.
     */
    public PeriodoLaboraPersonal() {
		super();
    }
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idPeriodoLaboraPersonal == null) ? 0 : idPeriodoLaboraPersonal.hashCode());
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
        PeriodoLaboraPersonal other = (PeriodoLaboraPersonal) obj;
        if (idPeriodoLaboraPersonal == null) {
            if (other.idPeriodoLaboraPersonal != null) {
                return false;
            }
        } else if (!idPeriodoLaboraPersonal.equals(other.idPeriodoLaboraPersonal)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PeriodoLaboraPersonal [idPeriodoLaboraPersonal=" + idPeriodoLaboraPersonal + "]";
    }
   
}