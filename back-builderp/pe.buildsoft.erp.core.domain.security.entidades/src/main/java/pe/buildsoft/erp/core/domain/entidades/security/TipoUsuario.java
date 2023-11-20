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
 * La Class TipoUsuario.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Dec 20 00:30:24 COT 2017
 * @since SIAA-CORE 2.1
 */
@Entity
@Table(name = "TipoUsuario", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_SECURIDAD)
@Getter
@Setter
public class TipoUsuario extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id tipo usuario. */
    @Id
    @Column(name = "idTipoUsuario" , precision = 18 , scale = 0)
    private Long idTipoUsuario;
   
    /** El descripcion. */
    @Column(name = "descripcion" , length = 100)
    private String descripcion;
   
    /** El codigo. */
    @Column(name = "codigo" , length = 20)
    private String codigo;
   
    /** El codigo externo. */
    @Column(name = "codigoExterno" , length = 20)
    private String codigoExterno;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /** El tipo usuario usuario list. */
//    @OneToMany(mappedBy = "tipoUsuario", fetch = FetchType.LAZY)
    @Transient
    private List<Usuario> tipoUsuarioUsuarioList = new ArrayList<>();
    
    /**
     * Instancia un nuevo tipo usuario.
     */
    public TipoUsuario() {
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
                + ((idTipoUsuario == null) ? 0 : idTipoUsuario.hashCode());
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
        TipoUsuario other = (TipoUsuario) obj;
        if (idTipoUsuario == null) {
            if (other.idTipoUsuario != null) {
                return false;
            }
        } else if (!idTipoUsuario.equals(other.idTipoUsuario)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TipoUsuario [idTipoUsuario=" + idTipoUsuario + "]";
    }
   
}