package pe.buildsoft.erp.core.application.entidades.cola;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ValorConfiguracionFiltroReporteDTO.
 * <ul>
 * <li>Copyright 2015 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Dec 21 12:16:30 COT 2017
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class ValorConfiguracionFiltroReporteDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id valor filtro. */
    private String idValorFiltro;
   
    /** El configuracion filtro reporte. */
    private ConfiguracionFiltroReporteDTO configuracionFiltroReporte = new ConfiguracionFiltroReporteDTO();
   
    /** El id solicitud reporte. */
    private Long idSolicitudReporte;
   
    /** El valor filtro text. */
    private String valorFiltroText;
   
    /** El valor filtro. */
    private String valorFiltro;
   
    /** El estado. */
    private String estado;
   
    /**
     * Instancia un nuevo valor configuracion filtro reporteDTO.
     */
    public ValorConfiguracionFiltroReporteDTO() {
    }
   
   
    /**
     * Instancia un nuevo valor configuracion filtro reporteDTO.
     *
     * @param idValorFiltro el id valor filtro
     * @param configuracionFiltroReporte el configuracion filtro reporte
     * @param idSolicitudReporte el id solicitud reporte
     * @param valorFiltroText el valor filtro text
     * @param valorFiltro el valor filtro
     * @param estado el estado
     */
    public ValorConfiguracionFiltroReporteDTO(String idValorFiltro, ConfiguracionFiltroReporteDTO configuracionFiltroReporte,Long idSolicitudReporte, String valorFiltroText, String valorFiltro, String estado ) {
        super();
        this.idValorFiltro = idValorFiltro;
        this.configuracionFiltroReporte = configuracionFiltroReporte;
        this.idSolicitudReporte = idSolicitudReporte;
        this.valorFiltroText = valorFiltroText;
        this.valorFiltro = valorFiltro;
        this.estado = estado;
    }
   
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idValorFiltro == null) ? 0 : idValorFiltro.hashCode());
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
        ValorConfiguracionFiltroReporteDTO other = (ValorConfiguracionFiltroReporteDTO) obj;
        if (idValorFiltro == null) {
            if (other.idValorFiltro != null) {
                return false;
            }
        } else if (!idValorFiltro.equals(other.idValorFiltro)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ValorConfiguracionFiltroReporteDTO [idValorFiltro=" + idValorFiltro + "]";
    }
   
}