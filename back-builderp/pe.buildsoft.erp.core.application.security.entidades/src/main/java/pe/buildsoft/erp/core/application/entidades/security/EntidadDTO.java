package pe.buildsoft.erp.core.application.entidades.security;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class EntidadDTO.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Wed Dec 20 13:16:01 COT 2017
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class EntidadDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id entidad. */
    private String idEntidad;
   
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
   
    /** El codigo. */
    private String codigo;
   
    /** El codigo externo. */
    private String codigoExterno;
   
    /** El codigo referencia. */
    private String codigoReferencia;
   
    /** El nombre. */
    private String nombre;
   
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
   
   
    /**
     * Instancia un nuevo entidadDTO.
     */
    public EntidadDTO() {
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
                + ((idEntidad == null) ? 0 : idEntidad.hashCode());
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
        EntidadDTO other = (EntidadDTO) obj;
        if (idEntidad == null) {
            if (other.idEntidad != null) {
                return false;
            }
        } else if (!idEntidad.equals(other.idEntidad)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "EntidadDTO [idEntidad=" + idEntidad + "]";
    }
   
}