package pe.buildsoft.erp.core.domain.entidades.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class GrupoUsuario.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Dec 13 22:04:26 COT 2017
 * @since SIAA-CORE 2.1
 */
@Entity
@Table(name = "GrupoUsuario", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_SECURIDAD)
@Getter
@Setter
public class GrupoUsuario extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id grupo usuario. */
    @Id
    @Column(name = "idGrupoUsuario" , precision = 18 , scale = 0)
    private Long idGrupoUsuario;
   
    /** El descripcion. */
    @Column(name = "descripcion" , length = 100)
    private String descripcion;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /** El grupo usuario grupo usuario menu list. */
//    @OneToMany(mappedBy = "grupoUsuario", fetch = FetchType.LAZY)
    @Transient
    private List<GrupoUsuarioMenu> grupoUsuarioGrupoUsuarioMenuList = new ArrayList<>();
    
    /** El grupo usuario grupo usuario usuario list. */
//    @OneToMany(mappedBy = "grupoUsuario", fetch = FetchType.LAZY)
    @Transient
    private List<GrupoUsuarioUsuario> grupoUsuarioGrupoUsuarioUsuarioList = new ArrayList<>();
    
    /** El grupo usuario privilegio grupo usuario list. */
//    @OneToMany(mappedBy = "grupoUsuario", fetch = FetchType.LAZY)
    @Transient
    private List<PrivilegioGrupoUsuario> grupoUsuarioPrivilegioGrupoUsuarioList = new ArrayList<>();
    
    @Transient
    private List<Long> menus = new ArrayList<Long>();
    
    @Transient
	private String usuarioSession;
    /**
     * Instancia un nuevo grupo usuario.
     */
    public GrupoUsuario() {
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
                + ((idGrupoUsuario == null) ? 0 : idGrupoUsuario.hashCode());
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
        GrupoUsuario other = (GrupoUsuario) obj;
        if (idGrupoUsuario == null) {
            if (other.idGrupoUsuario != null) {
                return false;
            }
        } else if (!idGrupoUsuario.equals(other.idGrupoUsuario)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GrupoUsuario [idGrupoUsuario=" + idGrupoUsuario + "]";
    }
   
}