package pe.buildsoft.erp.core.domain.entidades.cola;

import java.io.Serializable;
import java.time.OffsetDateTime;

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
 * La Class ConfiguracionFiltroCola.
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
@Table(name = "ConfigFiltroCola", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
@Getter
@Setter
public class ConfiguracionFiltroCola extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id juego filtro. */
    @Id
    @Column(name = "idJuegoFiltro" , length = 32)
    private String idJuegoFiltro;
   
    /** El cola. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCola", referencedColumnName = "idCola")
    private Cola cola;
   
    /** El configuracion filtro reporte. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCodigoFiltro", referencedColumnName = "idCodigoFiltro")
    private ConfiguracionFiltroReporte configuracionFiltroReporte;
   
    /** El configuracion filtro reporte rango. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCodigoFiltroRango", referencedColumnName = "idCodigoFiltro")
    private ConfiguracionFiltroReporte configuracionFiltroReporteRango;
   
    /** El marca rango. */
    @Column(name = "marcaRango" , length = 1)
    private String marcaRango;
   
    /** El valor juego filtro. */
    @Column(name = "valorJuegoFiltro" , length = 200)
    private String valorJuegoFiltro;
   
    /** El valor rango desde. */
    @Column(name = "valorRangoDesde" , length = 200)
    private String valorRangoDesde;
   
    /** El valor rango hasta. */
    @Column(name = "valorRangoHasta" , length = 200)
    private String valorRangoHasta;
   
    /** El fecha actualizacion. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaActualizacion")
    private OffsetDateTime fechaActualizacion;
   
    /** El codigo usuario. */
    @Column(name = "codigoUsuario" , length = 10)
    private String codigoUsuario;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
    
    @Column(name = "codigoJuego" , precision = 18 , scale = 0)
    private Long codigoJuego;

	/* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idJuegoFiltro == null) ? 0 : idJuegoFiltro.hashCode());
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
        ConfiguracionFiltroCola other = (ConfiguracionFiltroCola) obj;
        if (idJuegoFiltro == null) {
            if (other.idJuegoFiltro != null) {
                return false;
            }
        } else if (!idJuegoFiltro.equals(other.idJuegoFiltro)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ConfiguracionFiltroCola [idJuegoFiltro=" + idJuegoFiltro + "]";
    }
   
}