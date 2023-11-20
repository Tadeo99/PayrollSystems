package pe.buildsoft.erp.core.domain.entidades.matricula;

import java.io.Serializable;
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
 * La Class Grupo.
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
@Table(name = "Grupo", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_MATRICULA)
@Getter
@Setter
public class Grupo extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id grupo. */
    @Id
    @Column(name = "idGrupo" , precision = 18 , scale = 0)
    private Long idGrupo;
   
    /** El nombre. */
    @Column(name = "nombre" , length = 100)
    private String nombre;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /** El grupo detalle carga academica list. */
    @OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY)
    private List<DetalleCargaAcademica> grupoDetalleCargaAcademicaList = new ArrayList<>();
    
    /**
     * Instancia un nuevo grupo.
     */
    public Grupo() {
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
                + ((idGrupo == null) ? 0 : idGrupo.hashCode());
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
        Grupo other = (Grupo) obj;
        if (idGrupo == null) {
            if (other.idGrupo != null) {
                return false;
            }
        } else if (!idGrupo.equals(other.idGrupo)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Grupo [idGrupo=" + idGrupo + "]";
    }
   
}