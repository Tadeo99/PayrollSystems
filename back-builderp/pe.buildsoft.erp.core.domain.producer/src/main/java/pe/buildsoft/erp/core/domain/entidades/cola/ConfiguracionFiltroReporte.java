package pe.buildsoft.erp.core.domain.entidades.cola;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ConfiguracionFiltroReporte.
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
@Table(name = "ConfigFiltroReporte", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
@Getter
@Setter
public class ConfiguracionFiltroReporte extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** El id codigo filtro. */
    @Id
    @Column(name = "idCodigoFiltro" , precision = 18 , scale = 0)
    private Long idCodigoFiltro;
   
    /** El id opcion menu. */
    @Column(name = "idOpcionMenu" , precision = 18 , scale = 0)
    private Long idOpcionMenu;
   
    /** El nombre filtro. */
    @Column(name = "nombreFiltro" , length = 150)
    private String nombreFiltro;
    
    @Column(name = "idComponenteUI" , length = 400)
    private String idComponenteUI;
    
    /** El tipo valor filtro. */
    @Column(name = "tipoValorFiltro" , precision = 18 , scale = 0)
    private String tipoValorFiltro;
   
    /** El visible filtro. */
    @Column(name = "visibleFiltro" , length = 1)
    private String visibleFiltro;
   
    /** El tipo dato filtro. */
    @Column(name = "tipoDatoFiltro" , length = 1)
    private String tipoDatoFiltro;
   
    /** El fecha actualizacion. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaActualizacion")
    private OffsetDateTime fechaActualizacion;
   
    /** El codigo usuario. */
    @Column(name = "codigoUsuario" , length = 10)
    private String codigoUsuario;
   
    /** El objecto atributo. */
    @Column(name = "objectoAtributo" , length = 200)
    private String objectoAtributo;
    
    /** La objecto atributo descripcion. */
    @Column(name = "objectoAtributoDescripcion" , length = 200) 
    private String objectoAtributoDescripcion;
   
    /** El formato tipo dato. */
    @Column(name = "formatoTipoDato" , length = 20)
    private String formatoTipoDato;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
    
    @Column(name = "ordenFiltro" , length = 1)
    private Long ordenFiltro;
   
    /** El configuracion filtro reporte valor configuracion filtro reporte list. */
    @OneToMany(mappedBy = "configuracionFiltroReporte", fetch = FetchType.LAZY)
    private List<ValorConfiguracionFiltroReporte> configuracionFiltroReporteValorConfiguracionFiltroReporteList = new ArrayList<>();
    
    /** El configuracion filtro reporte configuracion filtro cola list. */
    @OneToMany(mappedBy = "configuracionFiltroReporte", fetch = FetchType.LAZY)
    private List<ConfiguracionFiltroCola> configuracionFiltroReporteConfiguracionFiltroColaList = new ArrayList<>();
    
    /** El configuracion filtro reporte rango configuracion filtro cola list. */
    @OneToMany(mappedBy = "configuracionFiltroReporteRango", fetch = FetchType.LAZY)
    private List<ConfiguracionFiltroCola> configuracionFiltroReporteRangoConfiguracionFiltroColaList = new ArrayList<>();
    
  
	/* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idCodigoFiltro == null) ? 0 : idCodigoFiltro.hashCode());
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
        ConfiguracionFiltroReporte other = (ConfiguracionFiltroReporte) obj;
        if (idCodigoFiltro == null) {
            if (other.idCodigoFiltro != null) {
                return false;
            }
        } else if (!idCodigoFiltro.equals(other.idCodigoFiltro)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ConfiguracionFiltroReporte [idCodigoFiltro=" + idCodigoFiltro + "]";
    }
   
}