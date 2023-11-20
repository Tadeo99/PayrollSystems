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
 * La Class Proyecto.
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
@Table(name = "Proyecto", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_GENERADOR)
@Getter
@Setter
public class Proyecto extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id proyecto. */
    @Id
    @Column(name = "idProyecto" , length = 32)
    private String idProyecto;
   
    /** El id usuario. */
    @Column(name = "idUsuario" , length = 32)
    private String idUsuario;
   
    /** El nombre. */
    @Column(name = "nombre" , length = 150)
    private String nombre;
   
    /** El paquete base. */
    @Column(name = "idsTecnologias" , length = 500)
    private String idsTecnologias;
   
    /** El proyecto config grupo servicio list. */
//    @OneToMany(mappedBy = "proyecto", fetch = FetchType.LAZY)
    @Transient
    private List<GrupoNegocio> proyectoConfigGrupoServicioList = new ArrayList<>();
    
    /**
     * Instancia un nuevo proyecto.
     */
    public Proyecto() {
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
                + ((idProyecto == null) ? 0 : idProyecto.hashCode());
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
        Proyecto other = (Proyecto) obj;
        if (idProyecto == null) {
            if (other.idProyecto != null) {
                return false;
            }
        } else if (!idProyecto.equals(other.idProyecto)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Proyecto [idProyecto=" + idProyecto + "]";
    }
   
}