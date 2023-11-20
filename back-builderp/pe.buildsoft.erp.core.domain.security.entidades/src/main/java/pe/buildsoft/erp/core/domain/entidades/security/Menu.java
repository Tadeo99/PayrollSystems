package pe.buildsoft.erp.core.domain.entidades.security;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Menu.
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
@Table(name = "Menu", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_SECURIDAD)
@Getter
@Setter
public class Menu extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id menu. */
    @Id
    @Column(name = "idMenu" , precision = 18 , scale = 0)
    private Long idMenu;
   
    /** El sistema. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSistema", referencedColumnName = "idSistema")
    private Sistema sistema;
   
    /** El nombre. */
    @Column(name = "nombre" , length = 100)
    private String nombre;
   
    /** El descripcion. */
    @Column(name = "descripcion" , length = 100)
    private String descripcion;
   
    /** El url. */
    @Column(name = "url" , length = 120)
    private String url;
   
    /** El parametro. */
    @Column(name = "parametro" , length = 50)
    private String parametro;
   
    /** El icono. */
    @Column(name = "icono" , length = 50)
    private String icono;
   
    /** El target. */
    @Column(name = "target" , length = 50)
    private String target;
   
    /** El menu padre. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPadreMenu", referencedColumnName = "idMenu")
    private Menu menuPadre;
   
    /** El fecha creacion. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaCreacion")
    private OffsetDateTime fechaCreacion;
   
    /** El usuario creacion. */
    @Column(name = "usuarioCreacion" , length = 50)
    private String usuarioCreacion;
   
    /** El fecha modificacion. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaModificacion")
    private OffsetDateTime fechaModificacion;
   
    /** El usuario modificacion. */
    @Column(name = "usuarioModificacion" , length = 50)
    private String usuarioModificacion;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /** El menu padre menu list. */
//    @OneToMany(mappedBy = "menuPadre", fetch = FetchType.LAZY)
    @Transient
    private List<Menu> menuPadreMenuList = new ArrayList<>();
    
    /** El menu configuracion menu list. */
//    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    @Transient
    private List<ConfiguracionMenu> menuConfiguracionMenuList = new ArrayList<>();
    
    /** El menu grupo usuario menu list. */
//    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    @Transient
    private List<GrupoUsuarioMenu> menuGrupoUsuarioMenuList = new ArrayList<>();
    
    /** El menu menu personalizado list. */
//    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    @Transient
    private List<MenuPersonalizado> menuMenuPersonalizadoList = new ArrayList<>();
    
    /** El menu privilegio menu list. */
//    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    @Transient
    private List<PrivilegioMenu> menuPrivilegioMenuList = new ArrayList<>();
    
    /**
     * Instancia un nuevo menu.
     */
    public Menu() {
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
                + ((idMenu == null) ? 0 : idMenu.hashCode());
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
        Menu other = (Menu) obj;
        if (idMenu == null) {
            if (other.idMenu != null) {
                return false;
            }
        } else if (!idMenu.equals(other.idMenu)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Menu [idMenu=" + idMenu + "]";
    }
   
}