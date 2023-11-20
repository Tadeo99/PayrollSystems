package pe.buildsoft.erp.core.application.entidades.generador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class ProyectoDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Mon Sep 26 12:14:56 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class ProyectoDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id proyecto. */
    private String idProyecto;
   
    /** El id usuario. */
    private String idUsuario;
   
    /** El nombre. */
    private String nombre;
   
    /** El paquete base. */
    private String idsTecnologias;
   
    /** El proyecto config grupo servicio list. */
    private List<GrupoNegocioDTO> proyectoConfigGrupoServicioList = new ArrayList<>();
   
    /**
     * Instancia un nuevo proyectoDTO.
     */
    public ProyectoDTO() {
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
        ProyectoDTO other = (ProyectoDTO) obj;
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
        return "ProyectoDTO [idProyecto=" + idProyecto + "]";
    }
   
}