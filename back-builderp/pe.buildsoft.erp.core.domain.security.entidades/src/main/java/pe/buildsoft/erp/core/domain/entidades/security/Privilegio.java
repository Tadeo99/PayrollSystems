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
 * La Class Privilegio.
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
@Table(name = "Privilegio", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_SECURIDAD)
@Getter
@Setter
public class Privilegio extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id privilegio. */
    @Id
    @Column(name = "idPrivilegio" , precision = 18 , scale = 0)
    private Long idPrivilegio;
   
    /** El acronimo. */
    @Column(name = "acronimo" , length = 100)
    private String acronimo;
   
    /** El nombre. */
    @Column(name = "nombre" , length = 100)
    private String nombre;
   
    /** El descripcion. */
    @Column(name = "descripcion" , length = 100)
    private String descripcion;
   
    /** El accion. */
    @Column(name = "accion" , length = 100)
    private String accion;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /** El privilegio privilegio grupo usuario list. */
//    @OneToMany(mappedBy = "privilegio", fetch = FetchType.LAZY)
    @Transient
    private List<PrivilegioGrupoUsuario> privilegioPrivilegioGrupoUsuarioList = new ArrayList<>();
    
    /** El privilegio privilegio menu list. */
//    @OneToMany(mappedBy = "privilegio", fetch = FetchType.LAZY)
    @Transient
    private List<PrivilegioMenu> privilegioPrivilegioMenuList = new ArrayList<>();
    
    /** El privilegio privilegio personalizado list. */
//    @OneToMany(mappedBy = "privilegio", fetch = FetchType.LAZY)
    @Transient
    private List<PrivilegioPersonalizado> privilegioPrivilegioPersonalizadoList = new ArrayList<>();
    
    /**
     * Instancia un nuevo privilegio.
     */
    public Privilegio() {
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
                + ((idPrivilegio == null) ? 0 : idPrivilegio.hashCode());
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
        Privilegio other = (Privilegio) obj;
        if (idPrivilegio == null) {
            if (other.idPrivilegio != null) {
                return false;
            }
        } else if (!idPrivilegio.equals(other.idPrivilegio)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Privilegio [idPrivilegio=" + idPrivilegio + "]";
    }
   
}