package pe.buildsoft.erp.core.application.entidades.hora;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.escalafon.PersonalDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class RegistroCabeceraDTO.
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
public class RegistroHoraDTO extends BaseEntidad implements Serializable {
	
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id registro cabecera. */
    private String idRegistroHora;
   
    /** El id periodo. */
    private PeriodoDTO periodo = new PeriodoDTO();
   
    /** El id personal. */
    private PersonalDTO personal = new PersonalDTO();
   
    /** El estado. */
    private String estado;
    
    private List<String> listaEstado;
    
    /** El observaciones. */
    private String observaciones;
   
    /** El id registro cabecera detalle registro cabecera list. */
    private List<RegistroHoraDetDTO> registroHoraDetRegistroHoraList = new ArrayList<>();
   
    /**
     * Instancia un nuevo registro cabeceraDTO.
     */
    public RegistroHoraDTO() {
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
                + ((idRegistroHora == null) ? 0 : idRegistroHora.hashCode());
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
        RegistroHoraDTO other = (RegistroHoraDTO) obj;
        if (idRegistroHora == null) {
            if (other.idRegistroHora != null) {
                return false;
            }
        } else if (!idRegistroHora.equals(other.idRegistroHora)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "RegistroHoraDTO [idRegistroHora=" + idRegistroHora + "]";
    }
   
}