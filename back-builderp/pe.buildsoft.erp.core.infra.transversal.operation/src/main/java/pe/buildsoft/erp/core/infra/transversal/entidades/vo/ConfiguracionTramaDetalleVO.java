package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ConfiguracionTramaDetalleVO.
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
public class ConfiguracionTramaDetalleVO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id configurador trama detalle. */
    private Long idConfiguradorTramaDetalle;
   
    /** El configuracion trama. */
    private ConfiguracionTramaVO configuracionTrama;
   
    /** El nombre campo. */
    private String nombreCampo = "";
   
    /** El descripcion campo. */
    private String descripcionCampo;
    
    /** El obligatorio. */
    private Character obligatorio;
   
    /** El tipo campo. */
    private String tipoCampo;
   
    /** El posicion campo inicial. */
    private Long posicionCampoInicial;
   
    /** El posicion campo final. */
    private Long posicionCampoFinal;
   
    /** El valor defecto campo. */
    private String valorDefectoCampo;
   
    /** El formato campo. */
    private String formatoCampo;
   
    /** El nombe campo tabla. */
    private String nombeCampoTabla;
    
    /** El orden. */
    private Long orden;
   
    /** El fila data. */
    private Long filaData;
   
    /** El flag campo agrupador. */
    private Character flagCampoAgrupador;
   
    /** El flag campo no leido trama. */
    private Character flagCampoNoLeidoTrama;
   
    /** El longitud. */
    private Long longitud;
   
    /** El campo fijo. */
    private Long campoFijo;
   
    /** El configuracion trama asociada. */
    private ConfiguracionTramaVO configuracionTramaAsociada = new ConfiguracionTramaVO();
   
    /** El campo asociado match inicio. */
    private ConfiguracionTramaDetalleVO campoAsociadoMatchInicio;
    
    /** El campo asociado match fin. */
    private ConfiguracionTramaDetalleVO campoAsociadoMatchFin;
    
    /** El campo asociado. */
    private ConfiguracionTramaDetalleVO campoAsociado;
    
    /** El es persistente. */
    private Character esPersistente;

    /** El es campo negocio. */
    private Character flagCampoNegocio;
    
    /** El tipo homologacion. */
    private Long tipoHomologacion;
   
    /** El campo dependiente. */
    private ConfiguracionTramaDetalleVO campoDependiente;
   
    /** El campo asociado configuracion trama detalle list. */
    private List<ConfiguracionTramaDetalleVO> campoAsociadoConfiguracionTramaDetalleList = new ArrayList<ConfiguracionTramaDetalleVO>();
   
    /** El es campo asociado. */
    private boolean esCampoAsociado;
    
    /** El es campo persistente. */
    private boolean esCampoPersistente;
    
    /** El style class. */
    private String styleClass;
    
    /** El codigo uuid. */
    private String codigoUUID;
       
    /** La regla negocio. */
    private String reglaNegocio; 
    
    /**
     * Instancia un nuevo configuracion trama detalleDTO.
     */
    public ConfiguracionTramaDetalleVO() {
    }
   
   
    /**
     * Instancia un nuevo configuracion trama detalleDTO.
     *
     * @param idConfiguradorTramaDetalle el id configurador trama detalle
     * @param configuracionTrama el configuracion trama
     * @param nombreCampo el nombre campo
     * @param descripcionCampo el descripcion campo
     * @param obligatorio el obligatorio
     * @param tipoCampo el tipo campo
     * @param posicionCampoInicial el posicion campo inicial
     * @param posicionCampoFinal el posicion campo final
     * @param valorDefectoCampo el valor defecto campo
     * @param formatoCampo el formato campo
     * @param nombeCampoTabla el nombe campo tabla
     * @param orden el orden
     * @param filaData el fila data
     * @param flagCampoAgrupador el flag campo agrupador
     * @param flagCampoNoLeidoTrama el flag campo no leido trama
     * @param longitud el longitud
     * @param campoFijo el campo fijo
     * @param configuracionTramaAsociada el configuracion trama asociada
     * @param campoAsociado el campo asociado
     * @param tipoHomologacion the tipo homologacion
     * @param campoDependiente the campo dependiente
     */
    public ConfiguracionTramaDetalleVO(Long idConfiguradorTramaDetalle, ConfiguracionTramaVO configuracionTrama,String nombreCampo,String descripcionCampo, Character obligatorio, String tipoCampo, Long posicionCampoInicial, Long posicionCampoFinal, String valorDefectoCampo, String formatoCampo, String nombeCampoTabla, Long orden, Long filaData, Character flagCampoAgrupador, Character flagCampoNoLeidoTrama, Long longitud, Long campoFijo, ConfiguracionTramaVO configuracionTramaAsociada,ConfiguracionTramaDetalleVO campoAsociado,Long tipoHomologacion, ConfiguracionTramaDetalleVO campoDependiente) {
        super();
        this.idConfiguradorTramaDetalle = idConfiguradorTramaDetalle;
        this.configuracionTrama = configuracionTrama;
        this.nombreCampo = nombreCampo;
        this.descripcionCampo = descripcionCampo;        
        this.obligatorio = obligatorio;
        this.tipoCampo = tipoCampo;
        this.posicionCampoInicial = posicionCampoInicial;
        this.posicionCampoFinal = posicionCampoFinal;
        this.valorDefectoCampo = valorDefectoCampo;
        this.formatoCampo = formatoCampo;
        this.nombeCampoTabla = nombeCampoTabla;
        this.orden = orden;
        this.filaData = filaData;
        this.flagCampoAgrupador = flagCampoAgrupador;
        this.flagCampoNoLeidoTrama = flagCampoNoLeidoTrama;
        this.longitud = longitud;
        this.campoFijo = campoFijo;
        this.configuracionTramaAsociada = configuracionTramaAsociada;
        this.campoAsociado = campoAsociado;
        this.tipoHomologacion = tipoHomologacion;
        this.campoDependiente = campoDependiente;
    }
   
   
	/* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idConfiguradorTramaDetalle == null) ? 0 : idConfiguradorTramaDetalle.hashCode());
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
        ConfiguracionTramaDetalleVO other = (ConfiguracionTramaDetalleVO) obj;
        if (idConfiguradorTramaDetalle == null) {
            if (other.codigoUUID != null) {
                return false;
            }
        } else if (!idConfiguradorTramaDetalle.equals(other.idConfiguradorTramaDetalle)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ConfiguracionTramaDetalleVO [idConfiguradorTramaDetalle=" + idConfiguradorTramaDetalle + "]";
    }

   
}