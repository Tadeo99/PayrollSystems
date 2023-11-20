package pe.buildsoft.erp.core.domain.entidades.security;

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
 * La Class GrupoUsuarioUsuario.
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
@Table(name = "GrupoUsuarioUsuario", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_SECURIDAD)
@Getter
@Setter
public class GrupoUsuarioUsuario extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id grupo usuario usuario. */
    @Id
    @Column(name = "idGrupoUsuarioUsuario" , length = 32)
    private String idGrupoUsuarioUsuario;
   
    /** El grupo usuario. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idGrupoUsuario", referencedColumnName = "idGrupoUsuario")
    private GrupoUsuario grupoUsuario;
   
    /** El usuario. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    private Usuario usuario;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /** El fecha creacion. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaCreacion")
    private OffsetDateTime fechaCreacion;
   
    /** El usuario creacion. */
    @Column(name = "usuarioCreacion" , length = 50)
    private String usuarioCreacion;
   
    /**
     * Instancia un nuevo grupo usuario usuario.
     */
    public GrupoUsuarioUsuario() {
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
                + ((idGrupoUsuarioUsuario == null) ? 0 : idGrupoUsuarioUsuario.hashCode());
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
        GrupoUsuarioUsuario other = (GrupoUsuarioUsuario) obj;
        if (idGrupoUsuarioUsuario == null) {
            if (other.idGrupoUsuarioUsuario != null) {
                return false;
            }
        } else if (!idGrupoUsuarioUsuario.equals(other.idGrupoUsuarioUsuario)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GrupoUsuarioUsuario [idGrupoUsuarioUsuario=" + idGrupoUsuarioUsuario + "]";
    }
   
}