package pe.buildsoft.erp.core.domain.entidades.hora;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Personal;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class RegistroCabecera.
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
@Table(name = "registrohora", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
public class RegistroHora extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id registro cabecera. */
    @Id
    @Column(name = "idregistrohora" , length = 32)
    private String idRegistroHora;
   
    /** El id periodo. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idperiodo", referencedColumnName = "idperiodo")
    private Periodo periodo;
   
    /** El id personal. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpersonal", referencedColumnName = "idpersonal")
    private Personal personal;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /** El observaciones. */
    @Column(name = "observaciones" , length = 100)
    private String observaciones;
    
    @Transient
    private List<String> listaEstado;
   
    /** El id registro hora detalle registro hora list. */
    @OneToMany(mappedBy = "registroHora", fetch = FetchType.LAZY)
    private List<RegistroHoraDet> registroHoraDetRegistroHoraList = new ArrayList<>();
    
    /**
     * Instancia un nuevo registro cabecera.
     */
    public RegistroHora() {
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
                + ((idRegistroHora == null) ? 0 : idRegistroHora.hashCode());
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
        RegistroHora other = (RegistroHora) obj;
        if (idRegistroHora == null) {
            if (other.idRegistroHora != null) {
                return false;
            }
        } else if (!idRegistroHora.equals(other.idRegistroHora)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    
    @Override
    public String toString() {
        return "RegistroHora [idRegistroHora=" + idRegistroHora + "]";
    }
   
}