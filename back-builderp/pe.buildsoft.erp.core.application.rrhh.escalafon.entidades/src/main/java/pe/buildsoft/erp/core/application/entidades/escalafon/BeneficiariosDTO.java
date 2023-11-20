package pe.buildsoft.erp.core.application.entidades.escalafon;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class BeneficiariosDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 23:29:58 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class BeneficiariosDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id beneficiario. */
    private String idBeneficiario;
   
    /** El personal. */
    private PersonalDTO personal;
   
    /** El apellido paterno. */
    private String apellidoPaterno;
   
    /** El apellido materno. */
    private String apellidoMaterno;
   
    /** El nombre. */
    private String nombre;
   
    /** El item by vinculo. */
    private Long idItemByVinculo;
    private ItemDTO itemByVinculo;
   
    /** El fecha nacimiento. */
    private OffsetDateTime fechaNacimiento;
   
    /** El situacion. */
    private String situacion;
   
    /** El fecha activacion. */
    private OffsetDateTime fechaActivacion;
   
    /**
     * Instancia un nuevo beneficiariosDTO.
     */
    public BeneficiariosDTO() {
		super();
    }
   
   
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idBeneficiario == null) ? 0 : idBeneficiario.hashCode());
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
        BeneficiariosDTO other = (BeneficiariosDTO) obj;
        if (idBeneficiario == null) {
            if (other.idBeneficiario != null) {
                return false;
            }
        } else if (!idBeneficiario.equals(other.idBeneficiario)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BeneficiariosDTO [idBeneficiario=" + idBeneficiario + "]";
    }
   
}