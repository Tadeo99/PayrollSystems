package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.escalafon.EmpresaDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class InformaOtrosIngreso5taDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 23:29:57 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class InformaOtrosIngreso5taDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id informa otros ingreso5ta. */
    private String idInformaOtrosIngreso5ta;
   
    private String idPersonal;
	private PersonalResponseVo personal;
   
    /** El empresa. */
    private Long idEmpresa;
    private EmpresaDTO empresa;
   
    /** El importe. */
    private BigDecimal importe;
    
    private Long idAnhio;
   
    /**
     * Instancia un nuevo informa otros ingreso5taDTO.
     */
    public InformaOtrosIngreso5taDTO() {
		super();
    }
  
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idInformaOtrosIngreso5ta == null) ? 0 : idInformaOtrosIngreso5ta.hashCode());
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
        InformaOtrosIngreso5taDTO other = (InformaOtrosIngreso5taDTO) obj;
        if (idInformaOtrosIngreso5ta == null) {
            if (other.idInformaOtrosIngreso5ta != null) {
                return false;
            }
        } else if (!idInformaOtrosIngreso5ta.equals(other.idInformaOtrosIngreso5ta)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "InformaOtrosIngreso5taDTO [idInformaOtrosIngreso5ta=" + idInformaOtrosIngreso5ta + "]";
    }
   
}