package pe.buildsoft.erp.core.infra.transversal.entidades.migrador;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;



/**
 * La Class ConfiguracionReporteTxtDTO.
 * <ul>
 * <li>Copyright 2018 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Mar 14 14:44:30 COT 2019
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class ConfiguracionReporteTxtDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id configuracion reporte txt. */
    private String idConfiguracionReporteTxt;
   
    /** El codigo reporte. */
    private String codigoReporte;
   
    /** El tipo reporte. */
    private String tipoReporte;
   
    /** El valor tipo reporte. */
    private String valorTipoReporte;
   
    /** El key header. */
    private String keyHeader;
   
    /** El value header. */
    private String valueHeader;
   
    /** El tipo dato. */
    private String tipoDato;
   
    /** El logitud columna. */
    private Long logitudColumna;
   
    /** El logitud decimal. */
    private Long logitudDecimal;
   
    /** El posicion inicial. */
    private Long posicionInicial;
   
    /** El posicion final. */
    private Long posicionFinal;
   
    /** El codigo columna. */
    private String codigoColumna;
   
    /** El descripcion columna. */
    private String descripcionColumna;
   
    /** El tipo registro. */
    private String tipoRegistro;
   
    /** El formato. */
    private String formato;
   
    /** El orden. */
    private Long orden;
   
    /** El estado. */
    private String estado;
   
    /**
     * Instancia un nuevo configuracion reporte txtDTO.
     */
    public ConfiguracionReporteTxtDTO() {
    }
   
   
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idConfiguracionReporteTxt == null) ? 0 : idConfiguracionReporteTxt.hashCode());
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
        ConfiguracionReporteTxtDTO other = (ConfiguracionReporteTxtDTO) obj;
        if (idConfiguracionReporteTxt == null) {
            if (other.idConfiguracionReporteTxt != null) {
                return false;
            }
        } else if (!idConfiguracionReporteTxt.equals(other.idConfiguracionReporteTxt)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ConfiguracionReporteTxtDTO [idConfiguracionReporteTxt=" + idConfiguracionReporteTxt + "]";
    }
   
}