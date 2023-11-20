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
 * La Class PrivilegioMenu.
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
@Table(name = "PrivilegioMenu", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_SECURIDAD)
@Getter
@Setter
public class PrivilegioMenu extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id privilegio menu. */
    @Id
    @Column(name = "idPrivilegioMenu" , length = 32)
    private String idPrivilegioMenu;
   
    /** El menu. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMenu", referencedColumnName = "idMenu")
    private Menu menu;
   
    /** El privilegio. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPrivilegio", referencedColumnName = "idPrivilegio")
    private Privilegio privilegio;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /**
     * Instancia un nuevo privilegio menu.
     */
    public PrivilegioMenu() {
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
                + ((idPrivilegioMenu == null) ? 0 : idPrivilegioMenu.hashCode());
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
        PrivilegioMenu other = (PrivilegioMenu) obj;
        if (idPrivilegioMenu == null) {
            if (other.idPrivilegioMenu != null) {
                return false;
            }
        } else if (!idPrivilegioMenu.equals(other.idPrivilegioMenu)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PrivilegioMenu [idPrivilegioMenu=" + idPrivilegioMenu + "]";
    }
   
}