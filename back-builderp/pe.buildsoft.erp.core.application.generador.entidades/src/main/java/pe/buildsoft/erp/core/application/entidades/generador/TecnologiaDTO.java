package pe.buildsoft.erp.core.application.entidades.generador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class TecnologiaDTO.
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
public class TecnologiaDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id tecnologia. */
    private String idTecnologia;
   
    /** El nombre. */
    private String nombre;
   
    /** El tipo. */
    private String tipo;
   
    /** El tecnologia config tecnologia list. */
    private List<PlantillaDTO> tecnologiaConfigTecnologiaList = new ArrayList<>();
   
    /** El tecnologia by base datos proyecto list. */
    private List<ProyectoDTO> tecnologiaByBaseDatosProyectoList = new ArrayList<>();
   
    /** El tecnologia by back end proyecto list. */
    private List<ProyectoDTO> tecnologiaByBackEndProyectoList = new ArrayList<>();
   
    /** El tecnologia by front end proyecto list. */
    private List<ProyectoDTO> tecnologiaByFrontEndProyectoList = new ArrayList<>();
   
    /** El tecnologia config arquetipo list. */
    private List<ConfigParametroDTO> tecnologiaConfigArquetipoList = new ArrayList<>();
   
    /** El tecnologia by base datos config type equivalencia list. */
    private List<EquivalenciaTypeDTO> tecnologiaByBaseDatosConfigTypeEquivalenciaList = new ArrayList<>();
   
    /** El tecnologia equivalente config type equivalencia list. */
    private List<EquivalenciaTypeDTO> tecnologiaEquivalenteConfigTypeEquivalenciaList = new ArrayList<>();
   
    /**
     * Instancia un nuevo tecnologiaDTO.
     */
    public TecnologiaDTO() {
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
                + ((idTecnologia == null) ? 0 : idTecnologia.hashCode());
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
        TecnologiaDTO other = (TecnologiaDTO) obj;
        if (idTecnologia == null) {
            if (other.idTecnologia != null) {
                return false;
            }
        } else if (!idTecnologia.equals(other.idTecnologia)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TecnologiaDTO [idTecnologia=" + idTecnologia + "]";
    }
   
}