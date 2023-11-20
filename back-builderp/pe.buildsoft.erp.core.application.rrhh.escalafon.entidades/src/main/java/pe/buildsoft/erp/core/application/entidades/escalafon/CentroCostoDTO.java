package pe.buildsoft.erp.core.application.entidades.escalafon;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

 

/**
 * La Class CentroCostoDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Feb 18 13:56:23 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class CentroCostoDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id centro costo. */
    private String idCentroCosto;
   
    /** El nombre. */
    private String nombre;
   
    /** El codigo. */
    private String codigo;
   
    /** El descripcion. */
    private String descripcion;
   
    /** El fecha creacion. */
    private String fechaCreacion;
   
    /** El estado. */
    private String estado;
   
    /**
     * Instancia un nuevo centro costoDTO.
     */
    public CentroCostoDTO() {
		super();
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idCentroCosto == null) ? 0 : idCentroCosto.hashCode());
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
        CentroCostoDTO other = (CentroCostoDTO) obj;
        if (idCentroCosto == null) {
            if (other.idCentroCosto != null) {
                return false;
            }
        } else if (!idCentroCosto.equals(other.idCentroCosto)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CentroCostoDTO [idCentroCosto=" + idCentroCosto + "]";
    }
   
}