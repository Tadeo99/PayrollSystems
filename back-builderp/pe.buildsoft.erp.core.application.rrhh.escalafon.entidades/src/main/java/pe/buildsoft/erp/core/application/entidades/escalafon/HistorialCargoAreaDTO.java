package pe.buildsoft.erp.core.application.entidades.escalafon;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class HistorialCargoAreaDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 23:29:56 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class HistorialCargoAreaDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id historial cargo area. */
    private String idHistorialCargoArea;
   
    /** El personal. */
    private PersonalDTO personal;
   
    /** El item by cargo. */
    private Long  idItemByCargo;
    private ItemDTO itemByCargo;
   
    /** El item area. */
    private Long  idItemByArea;
    private ItemDTO itemByArea;
   
    /** El fecha desde. */
    private OffsetDateTime fechaDesde;
   
    /** El observaciones. */
    private String observaciones;
   
    /**
     * Instancia un nuevo historial cargo areaDTO.
     */
    public HistorialCargoAreaDTO() {
		super();
    }
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idHistorialCargoArea == null) ? 0 : idHistorialCargoArea.hashCode());
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
        HistorialCargoAreaDTO other = (HistorialCargoAreaDTO) obj;
        if (idHistorialCargoArea == null) {
            if (other.idHistorialCargoArea != null) {
                return false;
            }
        } else if (!idHistorialCargoArea.equals(other.idHistorialCargoArea)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "HistorialCargoAreaDTO [idHistorialCargoArea=" + idHistorialCargoArea + "]";
    }
   
}