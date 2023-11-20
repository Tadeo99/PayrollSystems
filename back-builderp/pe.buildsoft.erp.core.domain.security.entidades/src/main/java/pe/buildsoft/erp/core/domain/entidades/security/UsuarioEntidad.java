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
 * La Class UsuarioEntidad.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Dec 13 22:04:28 COT 2017
 * @since SIAA-CORE 2.1
 */
@Entity
@Table(name = "UsuarioEntidad", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_SECURIDAD)
@Getter
@Setter
public class UsuarioEntidad extends BaseEntidad implements Serializable  {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id usuario entidad. */
    @Id
    @Column(name = "idUsuarioEntidad" , length = 32)
    private String idUsuarioEntidad;
   
    /** El id usuario. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    private Usuario usuario;
   
    /** El entidad. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEntidad", referencedColumnName = "idEntidad")
    private Entidad entidad;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /**
     * Instancia un nuevo usuario entidad.
     */
    public UsuarioEntidad() {
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
                + ((idUsuarioEntidad == null) ? 0 : idUsuarioEntidad.hashCode());
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
        UsuarioEntidad other = (UsuarioEntidad) obj;
        if (idUsuarioEntidad == null) {
            if (other.idUsuarioEntidad != null) {
                return false;
            }
        } else if (!idUsuarioEntidad.equals(other.idUsuarioEntidad)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "UsuarioEntidad [idUsuarioEntidad=" + idUsuarioEntidad + "]";
    }
   
}