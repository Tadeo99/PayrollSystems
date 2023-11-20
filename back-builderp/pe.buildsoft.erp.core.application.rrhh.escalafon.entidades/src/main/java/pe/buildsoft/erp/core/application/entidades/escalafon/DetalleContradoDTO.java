package pe.buildsoft.erp.core.application.entidades.escalafon;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class DetalleContradoDTO.
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
public class DetalleContradoDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id detalle contrado. */
    private String idDetalleContrado;
   
    /** El contrato. */
    private ContratoDTO contrato;
   
    /** El basico. */
    private BigDecimal basico;
   
    /** El horaley. */
    private Long horaley;
   
    /** El monto dia. */
    private BigDecimal montoDia;
   
    /** El monto mes. */
    private BigDecimal montoMes;
   
    /** El estado. */
    private String estado;
   
    /** El fecha. */
    private OffsetDateTime fecha;
   
    /**
     * Instancia un nuevo detalle contradoDTO.
     */
    public DetalleContradoDTO() {
		super();
    }
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idDetalleContrado == null) ? 0 : idDetalleContrado.hashCode());
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
        DetalleContradoDTO other = (DetalleContradoDTO) obj;
        if (idDetalleContrado == null) {
            if (other.idDetalleContrado != null) {
                return false;
            }
        } else if (!idDetalleContrado.equals(other.idDetalleContrado)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DetalleContradoDTO [idDetalleContrado=" + idDetalleContrado + "]";
    }
   
}