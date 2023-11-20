package pe.buildsoft.erp.core.application.entidades.generador;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class ConfigTecnologiaDTO.
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
public class PlantillaDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id config tecnologia. */
    private String idPlantilla;
   
    /** El tecnologia. */
    private TecnologiaDTO tecnologia;
   
    /** El nombre plantilla. */
    private String nombre;
   
    /** El codigo grupo plantilla. */
    private String codigoGrupo;
   
    /** El plantilla. */
    private String plantilla;
   
    /** El nombre archivo generar. */
    private String nombreArchivoGenerar;
   
    /** El extension. */
    private String extension;
   
    /** El es collection data. */
    private String esCollectionData;
   
    /** El alias paquete. */
    private String esInclude;
   
    /** El alias nombre. */
    private Long orden;
   
    /**
     * Instancia un nuevo config tecnologiaDTO.
     */
    public PlantillaDTO() {
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
                + ((idPlantilla == null) ? 0 : idPlantilla.hashCode());
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
        PlantillaDTO other = (PlantillaDTO) obj;
        if (idPlantilla == null) {
            if (other.idPlantilla != null) {
                return false;
            }
        } else if (!idPlantilla.equals(other.idPlantilla)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PlantillaDTO [idPlantilla=" + idPlantilla + "]";
    }
   
}