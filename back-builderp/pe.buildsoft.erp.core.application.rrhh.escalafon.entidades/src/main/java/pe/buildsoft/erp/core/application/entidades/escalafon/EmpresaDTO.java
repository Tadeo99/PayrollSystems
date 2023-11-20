package pe.buildsoft.erp.core.application.entidades.escalafon;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class EmpresaDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 21:45:31 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class EmpresaDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id empresa. */
    private Long idEmpresa;
   
    /** El item by tipo via. */
    private Long idItemByTipoVia;
    private ItemDTO itemByTipoVia;
   
    /** El item by zona. */
    private Long idItemByZona;
    private ItemDTO itemByZona;
   
    /** El nombre zona. */
    private String nombreZona;
   
    /** El nombre tipo via. */
    private String nombreTipoVia;
   
    /** El ruc. */
    private String ruc;
   
    /** El razon social. */
    private String razonSocial;
   
    /** El direccion. */
    private String direccion;
   
    /** El telefono. */
    private String telefono;
   
    /** El email. */
    private String email;
   
    /** El web. */
    private String web;
   
    /** El fecha creacion. */
    private OffsetDateTime fechaCreacion;
   
    /** El usuario creacion. */
    private String usuarioCreacion;
   
    /** El estado. */
    private String estado;

    /**
     * Instancia un nuevo empresaDTO.
     */
    public EmpresaDTO() {
		super();
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idEmpresa == null) ? 0 : idEmpresa.hashCode());
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
        EmpresaDTO other = (EmpresaDTO) obj;
        if (idEmpresa == null) {
            if (other.idEmpresa != null) {
                return false;
            }
        } else if (!idEmpresa.equals(other.idEmpresa)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "EmpresaDTO [idEmpresa=" + idEmpresa + "]";
    }
   
}