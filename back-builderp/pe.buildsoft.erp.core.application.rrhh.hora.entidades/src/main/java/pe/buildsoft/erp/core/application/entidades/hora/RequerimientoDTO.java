package pe.buildsoft.erp.core.application.entidades.hora;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.CentroCostoDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.PersonalDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class RequerimientoDTO.
 * <ul>
 * <li>Copyright 2021 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Wed Feb 23 11:22:44 COT 2022
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class RequerimientoDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id requerimiento. */
    private String idRequerimiento;
   
    /** El area. */
    private String area;
    
    /** El codigo. */
    private String codigo; 
   
    /** El nombre. */
    private String nombre;
   
    /** El horas. */
    private BigDecimal horas;
   
    /** El id personal. */
    private PersonalDTO personal;
   
    /** El tipo. */
    private String tipo;
   
    /** El fecha inicio. */
    private OffsetDateTime fechaInicio;
   
    /** El fecha fin. */
    private OffsetDateTime fechaFin;
   
    /** El fecha termino. */
    private OffsetDateTime fechaTermino;
    
    /** El fechaRegistra. */
    private OffsetDateTime fechaRegistra;
   
    /** El estado. */
    private String estado;
   
    /** El observaciones. */
    private String observaciones;
    
    /** El id centrocosto. */
    private CentroCostoDTO centroCosto;
    
    /** El item by tipo Doc. */
    private Long idItemByTipoGobierno;
    private ItemDTO itemByTipoGobierno;
    
    /** El isUpdate. */
    private boolean isUpdate;
   
    /**
     * Instancia un nuevo requerimientoDTO.
     */
    public RequerimientoDTO() {
		super();
    }
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idRequerimiento == null) ? 0 : idRequerimiento.hashCode());
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
        RequerimientoDTO other = (RequerimientoDTO) obj;
        if (idRequerimiento == null) {
            if (other.idRequerimiento != null) {
                return false;
            }
        } else if (!idRequerimiento.equals(other.idRequerimiento)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "RequerimientoDTO [idRequerimiento=" + idRequerimiento + "]";
    }
   
}