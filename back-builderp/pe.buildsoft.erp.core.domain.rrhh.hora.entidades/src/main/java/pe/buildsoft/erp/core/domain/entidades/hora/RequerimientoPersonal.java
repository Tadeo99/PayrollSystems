package pe.buildsoft.erp.core.domain.entidades.hora;

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
import pe.buildsoft.erp.core.domain.entidades.escalafon.Personal;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class RequerimientoPersonal.
 * <ul>
 * <li>Copyright 2021 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:16:43 COT 2022
 * @since BuildErp 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "requerimientopersonal", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
public class RequerimientoPersonal extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id requerimiento empleado. */
    @Id
    @Column(name = "idrequerimientopersonal" , length = 32)
    private String idRequerimientoPersonal;
   
    /** El id requerimiento. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idrequerimiento", referencedColumnName = "idrequerimiento")
    private Requerimiento requerimiento;
   
    /** El id personal. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpersonal", referencedColumnName = "idpersonal")
    private Personal personal;
    
    /** El id periodo. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idperiodo", referencedColumnName = "idperiodo")
    private Periodo periodo;
   
    /**
     * Instancia un nuevo requerimiento personal.
     */
    public RequerimientoPersonal() {
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
                + ((idRequerimientoPersonal == null) ? 0 : idRequerimientoPersonal.hashCode());
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
        RequerimientoPersonal other = (RequerimientoPersonal) obj;
        if (idRequerimientoPersonal == null) {
            if (other.idRequerimientoPersonal != null) {
                return false;
            }
        } else if (!idRequerimientoPersonal.equals(other.idRequerimientoPersonal)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "RequerimientoPersonal [idRequerimientoPersonal=" + idRequerimientoPersonal + "]";
    }
   
}