package pe.buildsoft.erp.core.domain.entidades.matricula;

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
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Horario.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Horario", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_MATRICULA)
@Getter
@Setter
public class Horario extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id horario. */
    @Id
    @Column(name = "idHorario" , length = 32)
    private String idHorario;
   
    /** El detalle carga academica. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDetalleCargaAcademica", referencedColumnName = "idDetalleCargaAcademica")
    private DetalleCargaAcademica detalleCargaAcademica;
   
    /** El personal by docente. */
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "idDocente", referencedColumnName = "idPersonal")
    @Column(name = "idDocente" , length = 32)
    private String personalByDocente;
   
    /** El item by dia. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDia", referencedColumnName = "idItem")
    private Item itemByDia;*/
    @Column(name = "idDia" , precision = 18 , scale = 0)
    private Long idItemByDia;

   
    /** El hora inicio. */
    @Column(name = "horaInicio" , length = 5)
    private String horaInicio;
   
    /** El hora fin. */
    @Column(name = "horaFin" , length = 5)
    private String horaFin;
   
    /**
     * Instancia un nuevo horario.
     */
    public Horario() {
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
                + ((idHorario == null) ? 0 : idHorario.hashCode());
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
        Horario other = (Horario) obj;
        if (idHorario == null) {
            if (other.idHorario != null) {
                return false;
            }
        } else if (!idHorario.equals(other.idHorario)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Horario [idHorario=" + idHorario + "]";
    }
   
}