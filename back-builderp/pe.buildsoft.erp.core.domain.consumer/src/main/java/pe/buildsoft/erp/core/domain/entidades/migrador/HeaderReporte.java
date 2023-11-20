package pe.buildsoft.erp.core.domain.entidades.migrador;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class HeaderReporte.
 * <ul>
 * <li>Copyright 2018 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Mon Oct 22 12:34:48 COT 2018
 * @since BuildErp 1.0
 */
//Inicio BUIDSOFT 02 Requerimiento reporte detalle produccion excel 
@Entity
@Table(name = "HeaderReporte", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
public class HeaderReporte extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id header reporte. */
    @Id
    @Column(name = "idHeaderReporte" , length = 32)
    private String idHeaderReporte;
   
    /** El codigo reporte. */
    @Column(name = "codigoReporte" , length = 150)
    private String codigoReporte;
   
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
                + ((idHeaderReporte == null) ? 0 : idHeaderReporte.hashCode());
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
        HeaderReporte other = (HeaderReporte) obj;
        if (idHeaderReporte == null) {
            if (other.idHeaderReporte != null) {
                return false;
            }
        } else if (!idHeaderReporte.equals(other.idHeaderReporte)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "HeaderReporte [idHeaderReporte=" + idHeaderReporte + "]";
    }
   
}