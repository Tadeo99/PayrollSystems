package pe.buildsoft.erp.core.domain.entidades.security;

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
 * La Class PrivilegioGrupoUsuario.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Dec 13 22:04:27 COT 2017
 * @since SIAA-CORE 2.1
 */
@Entity
@Table(name = "PrivilegioGrupoUsuario", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_SECURIDAD)
@Getter
@Setter
public class PrivilegioGrupoUsuario extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id privilegio grupo usuario. */
    @Id
    @Column(name = "idPrivilegioGrupoUsuario" , length = 32)
    private String idPrivilegioGrupoUsuario;
   
    /** El grupo usuario. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idGrupoUsuario", referencedColumnName = "idGrupoUsuario")
    private GrupoUsuario grupoUsuario;
   
    /** El privilegio. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPrivilegio", referencedColumnName = "idPrivilegio")
    private Privilegio privilegio;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /**
     * Instancia un nuevo privilegio grupo usuario.
     */
    public PrivilegioGrupoUsuario() {
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
                + ((idPrivilegioGrupoUsuario == null) ? 0 : idPrivilegioGrupoUsuario.hashCode());
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
        PrivilegioGrupoUsuario other = (PrivilegioGrupoUsuario) obj;
        if (idPrivilegioGrupoUsuario == null) {
            if (other.idPrivilegioGrupoUsuario != null) {
                return false;
            }
        } else if (!idPrivilegioGrupoUsuario.equals(other.idPrivilegioGrupoUsuario)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PrivilegioGrupoUsuario [idPrivilegioGrupoUsuario=" + idPrivilegioGrupoUsuario + "]";
    }
   
}