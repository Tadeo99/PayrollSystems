	package pe.buildsoft.erp.core.application.entidades.hora;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * La Class DetalleRegistroCabeceraDTO.
 * <ul>
 * <li>Copyright 2021 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Wed Feb 23 11:22:45 COT 2022
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class RegistroHoraDetDTO implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id detalle registro cabecera. */
    private String idRegistroHoraDet;
   
    /** El id registro cabecera. */
    private RegistroHoraDTO registroHora = null;
   
    /** El id requerimiento. */
    private String idRequerimiento;
   
    /** El horas imputadas. */
    private BigDecimal horasImputadas;
   
    /** El fecha imputacion. */
    private OffsetDateTime fechaImputacion;
   
    /** El num semana. */
    private Long numSemana;
   
    /**
     * Instancia un nuevo detalle registro cabeceraDTO.
     */
    public RegistroHoraDetDTO() {
		super();
    }
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idRegistroHoraDet == null) ? 0 : idRegistroHoraDet.hashCode());
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
        RegistroHoraDetDTO other = (RegistroHoraDetDTO) obj;
        if (idRegistroHoraDet == null) {
            if (other.idRegistroHoraDet != null) {
                return false;
            }
        } else if (!idRegistroHoraDet.equals(other.idRegistroHoraDet)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "RegistroHoraDetDTO [idRegistroHoraDet=" + idRegistroHoraDet + "]";
    }
   
}