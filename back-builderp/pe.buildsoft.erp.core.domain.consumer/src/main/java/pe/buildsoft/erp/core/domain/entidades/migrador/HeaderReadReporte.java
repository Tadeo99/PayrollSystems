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
 * La Class HeaderReadReporte.
 * <ul>
 * <li>Copyright 2018 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Wed Aug 07 14:48:19 COT 2019
 * @since BuildErp 1.0
 */
@Entity
@Table(name = "HeaderReadReporte", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
@Getter
@Setter
public class HeaderReadReporte extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id header read reporte. */
    @Id
    @Column(name = "idHeaderReadReporte" , length = 36)
    private String idHeaderReadReporte;
   
    /** El codigo reporte. */
    @Column(name = "codigoReporte" , length = 150)
    private String codigoReporte;
   
    /** El nombre tabla. */
    @Column(name = "nombreTabla" , length = 50)
    private String nombreTabla;
   
    /** El tipo reporte. */
    @Column(name = "tipoReporte" , length = 4)
    private String tipoReporte;
   
    /** El key header. */
    @Column(name = "keyHeader" , length = 150)
    private String keyHeader;
   
    /** El value header. */
    @Column(name = "valueHeader" , length = 150)
    private String valueHeader;
   
    /** El tipo formato. */
    @Column(name = "tipoFormato" , length = 20)
    private String tipoFormato;
   
    /** El value formato. */
    @Column(name = "valueFormato" , length = 30)
    private String valueFormato;
   
    /** El tipo campo. */
    @Column(name = "tipoCampo" , length = 1)
    private String tipoCampo;
   
    /** El obligatorio. */
    @Column(name = "obligatorio" , length = 1)
    private String obligatorio;
   
    /** El longitud. */
    @Column(name = "longitud" , precision = 18 , scale = 0)
    private Long longitud;
   
    /** El fila data. */
    @Column(name = "filaData" , precision = 18 , scale = 0)
    private Long filaData;
   
    /** El posicion campo inicial. */
    @Column(name = "posicionCampoInicial" , precision = 18 , scale = 0)
    private Long posicionCampoInicial;
   
    /** El posicion campo final. */
    @Column(name = "posicionCampoFinal" , precision = 18 , scale = 0)
    private Long posicionCampoFinal;
   
    /** El valor defecto campo. */
    @Column(name = "valorDefectoCampo" , length = 150)
    private String valorDefectoCampo;
    
    /** El orden. */
    @Column(name = "orden" , precision = 18 , scale = 0)
    private Long orden;
   
    /** El es persistente. */
    @Column(name = "esPersistente" , length = 1)
    private String esPersistente;
    
    /** El es campoLeidoTrama. */
    @Column(name = "campoLeidoTrama" , length = 1)
    private String campoLeidoTrama;
   
    /** El regla. */
    @Column(name = "regla")
    private String regla;
   
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
                + ((idHeaderReadReporte == null) ? 0 : idHeaderReadReporte.hashCode());
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
        HeaderReadReporte other = (HeaderReadReporte) obj;
        if (idHeaderReadReporte == null) {
            if (other.idHeaderReadReporte != null) {
                return false;
            }
        } else if (!idHeaderReadReporte.equals(other.idHeaderReadReporte)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "HeaderReadReporte [idHeaderReadReporte=" + idHeaderReadReporte + "]";
    }
   
}