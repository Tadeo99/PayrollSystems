package pe.buildsoft.erp.core.domain.entidades.matricula;

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
 * La Class MallaCurricular.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "MallaCurricular", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_MATRICULA)
@Getter
@Setter
public class MallaCurricular extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id malla curricular. */
    @Id
    @Column(name = "idMallaCurricular" , length = 32)
    private String idMallaCurricular;
   
    /** El anhio. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAnhio", referencedColumnName = "idAnhio")
    private Anhio anhio;*/
    @Column(name = "idAnhio" , precision = 18 , scale = 0)
    private Long idAnhio;
   
    /** El grado. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idGrado", referencedColumnName = "idGrado")
    private Grado grado;*/
    @Column(name = "idGrado", precision = 18, scale = 0)
	private Long idGrado;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /** El fecha creacion. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaCreacion")
    private OffsetDateTime fechaCreacion;
   
    /** El malla curricular det malla curricular list. */
    @OneToMany(mappedBy = "mallaCurricular", fetch = FetchType.LAZY)
    private List<DetMallaCurricular> mallaCurricularDetMallaCurricularList = new ArrayList<>();
    
    /**
     * Instancia un nuevo malla curricular.
     */
    public MallaCurricular() {
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
                + ((idMallaCurricular == null) ? 0 : idMallaCurricular.hashCode());
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
        MallaCurricular other = (MallaCurricular) obj;
        if (idMallaCurricular == null) {
            if (other.idMallaCurricular != null) {
                return false;
            }
        } else if (!idMallaCurricular.equals(other.idMallaCurricular)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MallaCurricular [idMallaCurricular=" + idMallaCurricular + "]";
    }
   
}