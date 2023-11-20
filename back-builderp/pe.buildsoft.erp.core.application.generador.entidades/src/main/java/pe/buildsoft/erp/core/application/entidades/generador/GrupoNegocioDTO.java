package pe.buildsoft.erp.core.application.entidades.generador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class ConfigGrupoServicioDTO.
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
public class GrupoNegocioDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id config grupo servicio. */
    private String idGrupoNegocio;
   
    /** El proyecto. */
    private String idProyecto;
   
    /** El nombre. */
    private String nombre;
    
    private String idsTecnologias;
   
    /** El config grupo servicio config tabla class list. */
    private List<ModeloDTO> configGrupoServicioConfigTablaClassList = new ArrayList<>();
   
    /**
     * Instancia un nuevo config grupo servicioDTO.
     */
    public GrupoNegocioDTO() {
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
        GrupoNegocioDTO other = (GrupoNegocioDTO) obj;
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
        return "ConfigGrupoServicioDTO [idConfigGrupoServicio=" + idGrupoNegocio + "]";
    }
   
}