package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ConfiguracionTramaVO.
 * <ul>
 * <li>Copyright 2015 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Wed Aug 26 16:11:24 COT 2015
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class ConfiguracionTramaVO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id configurador trama. */
    private Long idConfiguradorTrama;
   
    /** El juego trama. */
    private Long juegoTrama;
   
    /** El trama nomenclatura archivo. */
    private TramaNomenclaturaArchivoVO tramaNomenclaturaArchivo;
   
    /** El fila data. */
    private Long filaData;
   
    /** El tiene separador. */
    private Character tieneSeparador;
   
    /** El numero orden. */
    private Long numeroOrden;
   
    /** El numero hoja. */
    private Long numeroHoja;
   
    /** El separador. */
    private String separador;
   
    /** El obligatorio. */
    private Character obligatorio;
   
    /** El estado. */
    private String estado;
   
    /** El tipo proceso. */
    private String tipoProceso;
   
    /** El nombre tabla. */
    private String nombreTabla;
    
    /** La descripcion tabla. */
    private String descripcionTabla;
   
    /** El delimitador data. */
    private String delimitadorData;
   
    /** La es coordenada. */
    private Character esCoordenada;
   
    /** La show accion error. */
    private boolean showAccionError = true;
    
	/** La show accion eliminar. */
	private boolean showAccionEliminar = false;
	
	/** La show accion activar. */
	private boolean showAccionActivar = false;
    
	   /** El configuracion trama configuracion trama detalle list. */
    private List<ConfiguracionTramaDetalleVO> configuracionTramaConfiguracionTramaDetalleList = new ArrayList<ConfiguracionTramaDetalleVO>();
 
    /**
     * Instancia un nuevo configuracion tramaDTO.
     */
    public ConfiguracionTramaVO() {
    }
   
   


	/* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idConfiguradorTrama == null) ? 0 : idConfiguradorTrama.hashCode());
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
        ConfiguracionTramaVO other = (ConfiguracionTramaVO) obj;
        if (idConfiguradorTrama == null) {
            if (other.idConfiguradorTrama != null) {
                return false;
            }
        } else if (!idConfiguradorTrama.equals(other.idConfiguradorTrama)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ConfiguracionTramaVO [idConfiguradorTrama=" + idConfiguradorTrama + "]";
    }
   
}