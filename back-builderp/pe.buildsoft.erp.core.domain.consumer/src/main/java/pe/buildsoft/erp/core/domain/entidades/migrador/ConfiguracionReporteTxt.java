package pe.buildsoft.erp.core.domain.entidades.migrador;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ConfiguracionReporteTxt.
 * <ul>
 * <li>Copyright 2018 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Mar 14 14:44:30 COT 2019
 * @since BuildErp 1.0
 */
@Entity
@Table(name = "ConfiguracionReporteTxt", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
@Getter
@Setter
public class ConfiguracionReporteTxt extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id configuracion reporte txt. */
    @Id
    @Column(name = "idConfiguracionReporteTxt" , length = 32)
    private String idConfiguracionReporteTxt;
   
    /** El codigo reporte. */
    @Column(name = "codigoReporte" , length = 150)
    private String codigoReporte;
   
    /** El tipo reporte. */
    @Column(name = "tipoReporte" , length = 4)
    private String tipoReporte;
   
    /** El valor tipo reporte. */
    @Column(name = "valorTipoReporte" , length = 150)
    private String valorTipoReporte;
   
    /** El key header. */
    @Column(name = "keyHeader" , length = 150)
    private String keyHeader;
   
    /** El value header. */
    @Column(name = "valueHeader" , length = 150)
    private String valueHeader;
   
    /** El tipo dato. */
    @Column(name = "tipoDato" , length = 20)
    private String tipoDato;
   
    /** El logitud columna. */
    @Column(name = "logitudColumna" , length = 18)
    private Long logitudColumna;
   
    /** El logitud decimal. */
    @Column(name = "logitudDecimal" , length = 18)
    private Long logitudDecimal;
   
    /** El posicion inicial. */
    @Column(name = "posicionInicial" , length = 18)
    private Long posicionInicial;
   
    /** El posicion final. */
    @Column(name = "posicionFinal" , length = 18)
    private Long posicionFinal;
   
    /** El codigo columna. */
    @Column(name = "codigoColumna" , length = 150)
    private String codigoColumna;
   
    /** El descripcion columna. */
    @Column(name = "descripcionColumna" , length = 150)
    private String descripcionColumna;
   
    /** El tipo registro. */
    @Column(name = "tipoRegistro" , length = 1)
    private String tipoRegistro;
   
    /** El formato. */
    @Column(name = "formato" , length = 20)
    private String formato;
   
    /** El orden. */
    @Column(name = "orden" , precision = 18 , scale = 0)
    private Long orden;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    
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
        ConfiguracionReporteTxt other = (ConfiguracionReporteTxt) obj;
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
        return "ConfiguracionReporteTxt [idConfiguracionReporteTxt=" + idConfiguracionReporteTxt + "]";
    }
   
}