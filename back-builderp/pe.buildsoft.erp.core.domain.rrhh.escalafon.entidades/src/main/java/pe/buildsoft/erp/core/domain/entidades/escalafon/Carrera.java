package pe.buildsoft.erp.core.domain.entidades.escalafon;

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

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Carrera.
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
@Table(name = "Carrera", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_ESCAL)
@Getter
@Setter
public class Carrera extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id carrera. */
    @Id
    @Column(name = "idCarrera" , precision = 18 , scale = 0)
    private Long idCarrera;
   
    /** El institucion. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idInstitucion", referencedColumnName = "idInstitucion")
    private Institucion institucion;
   
    /** El codigo. */
    @Column(name = "codigo" , length = 32)
    private String codigo;
   
    /** El nombre. */
    @Column(name = "nombre" , length = 200)
    private String nombre;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /** El carrera personal list. */
    @OneToMany(mappedBy = "carrera", fetch = FetchType.LAZY)
    private List<Personal> carreraPersonalList = new ArrayList<>();
    
    /**
     * Instancia un nuevo carrera.
     */
    public Carrera() {
		super();
    }
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idCarrera == null) ? 0 : idCarrera.hashCode());
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
        Carrera other = (Carrera) obj;
        if (idCarrera == null) {
            if (other.idCarrera != null) {
                return false;
            }
        } else if (!idCarrera.equals(other.idCarrera)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Carrera [idCarrera=" + idCarrera + "]";
    }
   
}