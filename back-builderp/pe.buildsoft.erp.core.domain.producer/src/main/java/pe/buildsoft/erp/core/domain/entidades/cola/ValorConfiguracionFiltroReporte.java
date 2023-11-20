package pe.buildsoft.erp.core.domain.entidades.cola;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ValorConfiguracionFiltroReporte.
 * <ul>
 * <li>Copyright 2015 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 2.1, Thu Dec 21 12:16:30 COT 2017
 * @since BuildErp 1.0
 */
@Entity
@Table(name = "ValorConfigFiltroReporte", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
@Getter
@Setter
public class ValorConfiguracionFiltroReporte extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id valor filtro. */
    @Id
    @Column(name = "idValorFiltro" , length = 32)
    private String idValorFiltro;
   
    /** El configuracion filtro reporte. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCodigoFiltro", referencedColumnName = "idCodigoFiltro")
    private ConfiguracionFiltroReporte configuracionFiltroReporte;
    
    /** El id solicitud reporte. */
    @Column(name = "idSolicitudReporte" , length = 32)
    private String idSolicitudReporte;
   
    /** El valor filtro text. */
    @Column(name = "valorFiltroText" , length = 200)
    private String valorFiltroText;
   
    /** El valor filtro. */
    @Column(name = "valorFiltro" , length = 200)
    private String valorFiltro;
   
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
        ValorConfiguracionFiltroReporte other = (ValorConfiguracionFiltroReporte) obj;
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
        return "ValorConfiguracionFiltroReporte [idValorFiltro=" + idValorFiltro + "]";
    }
   
}