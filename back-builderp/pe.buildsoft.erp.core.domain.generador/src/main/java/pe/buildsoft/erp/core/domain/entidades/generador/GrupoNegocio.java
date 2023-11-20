package pe.buildsoft.erp.core.domain.entidades.generador;

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
 * La Class ConfigGrupoServicio.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 11:35:26 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "GrupoNegocio", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_GENERADOR)
@Getter
@Setter
public class GrupoNegocio extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id config grupo servicio. */
    @Id
    @Column(name = "idGrupoNegocio" , length = 32)
    private String idGrupoNegocio;
   
    /** El proyecto. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProyecto", referencedColumnName = "idProyecto")
    private Proyecto proyecto;*/
    
    @Column(name = "idProyecto" , length = 32)
    private String idProyecto;
   
    /** El nombre. */
    @Column(name = "nombre" , length = 200)
    private String nombre;
   
    /** El ids tecnologias. */
    @Column(name = "idsTecnologias" , length = 500)
    private String idsTecnologias;
    
    /** El config grupo servicio config tabla class list. */
//    @OneToMany(mappedBy = "configGrupoServicio", fetch = FetchType.LAZY)
    @Transient
    private List<Modelo> configGrupoServicioConfigTablaClassList = new ArrayList<>();
    
    /**
     * Instancia un nuevo config grupo servicio.
     */
    public GrupoNegocio() {
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
                + ((idGrupoNegocio == null) ? 0 : idGrupoNegocio.hashCode());
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
        GrupoNegocio other = (GrupoNegocio) obj;
        if (idGrupoNegocio == null) {
            if (other.idGrupoNegocio != null) {
                return false;
            }
        } else if (!idGrupoNegocio.equals(other.idGrupoNegocio)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ConfigGrupoServicio [idConfigGrupoServicio=" + idGrupoNegocio + "]";
    }
   
}