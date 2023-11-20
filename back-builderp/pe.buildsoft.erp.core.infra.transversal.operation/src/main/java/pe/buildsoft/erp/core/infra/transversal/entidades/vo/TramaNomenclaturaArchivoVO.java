package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class TramaNomenclaturaArchivoVO.
 * <ul>
 * <li>Copyright 2015 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Tue Aug 04 17:28:27 COT 2015
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class TramaNomenclaturaArchivoVO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id trama nomenclatura archivo. */
    private Long idTramaNomenclaturaArchivo;
   
    /** El canal. */
    private String canal;
   
    /** El producto. */
    private String producto;
   
    /** El tipo archivo. */
    private String tipoArchivo;
   
    /** El tipo trama. */
    private Long tipoTrama;
   
    /** El nombre. */
    private String nombre;
    
    /** La acronimo tipo trama. */
    private String acronimoTipoTrama;
    
    /** La ischeked. */
    private boolean ischeked = false;
    
    /** La tipos trama nomenclatura. */
    private List<Long> tiposTramaNomenclatura = new ArrayList<>();
    
    /** La numero item. */
    private int numeroItem = 0;

    private List<Long> existeNomenclaturaBDList = new ArrayList<>();
    //
    /** La nombre archivo. */
    private String nombreArchivo;   
    
    private String mensajeError;  
    
    /** La show accion error. */
    private boolean showAccionError = true;
    
    /** El estado. */
    private String estado;
    /**
     * Instancia un nuevo trama nomenclatura archivoDTO.
     */
    public TramaNomenclaturaArchivoVO() {
    }
   
 
	/* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idTramaNomenclaturaArchivo == null) ? 0 : idTramaNomenclaturaArchivo.hashCode());
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
        TramaNomenclaturaArchivoVO other = (TramaNomenclaturaArchivoVO) obj;
        if (idTramaNomenclaturaArchivo == null) {
            if (other.idTramaNomenclaturaArchivo != null) {
                return false;
            }
        } else if (!idTramaNomenclaturaArchivo.equals(other.idTramaNomenclaturaArchivo)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TramaNomenclaturaArchivoVO [idTramaNomenclaturaArchivo=" + idTramaNomenclaturaArchivo + "]";
    }
   
}