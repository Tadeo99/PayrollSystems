package pe.buildsoft.erp.core.domain.entidades.hora;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Periodo.
 * <ul>
 * <li>Copyright 2021 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:16:44 COT 2022
 * @since BuildErp 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "periodo", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
public class Periodo extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id periodo. */
    @Id
    @Column(name = "idperiodo" , length = 32)
    private String idPeriodo;
   
    /** El nombre. */
    @Column(name = "nombre" , length = 100)
    private String nombre;
   
    /** El fecha inicio. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechainicio")
    private OffsetDateTime fechaInicio;
   
    /** El fecha fin. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechafin")
    private OffsetDateTime fechaFin;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /** El id periodo registro cabecera list. */
    @OneToMany(mappedBy = "periodo", fetch = FetchType.LAZY)
    private List<RegistroHora> periodoRegistroCabeceraList = new ArrayList<>();
    
    
    /**
     * Instancia un nuevo periodo.
     */
    public Periodo() {
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
                + ((idPeriodo == null) ? 0 : idPeriodo.hashCode());
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
        Periodo other = (Periodo) obj;
        if (idPeriodo == null) {
            if (other.idPeriodo != null) {
                return false;
            }
        } else if (!idPeriodo.equals(other.idPeriodo)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Periodo [idPeriodo=" + idPeriodo + "]";
    }
   
}