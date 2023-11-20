package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BigMemoryManager;

/**
 * <ul>
 * <li>Copyright 2019 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class RespuestaLecturaArchivoVO.
 *
 * @author BuildSoft
 * @version 1.0 , 10/10/2019
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class RespuestaCargaDataTramaVO implements Serializable  {
	
	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
    /** La es error. */
    private boolean esError;
    
    /** La valor. */
    private Object valor;
    
    /** La lista data resul map. */
    private List<Map<String,ValueDataVO>> listaDataResulMap = new ArrayList<>();
    
    /** La mensaje error. */
    private String mensajeError;
    
    /** La id configuracion trama. */
    private Long idConfiguracionTrama;
	
    /** La campo mapping formato map. */
    private Map<String,String> campoMappingFormatoMap = new HashMap<>();
    
    /** La propiedad map. */
    private Map<String,String> propiedadMap = new HashMap<>();
    
    /** La key grupo. */
    private String keyGrupo = "";
    
    /** La error tupla procesamiento. */
    private boolean errorTuplaProcesamiento = false;
    
    /** La error personalizado map. */
    private Map<String,String> errorPersonalizadoMap = new HashMap<>();
    
    //separando logica de negocio
    private boolean existeError;
    
    private  Map<String,ValueDataVO> map = new HashMap<>();
    
    /** La lista data resul map. */
    private List<Map<String,ValueDataVO>> listaDataResulErrorMap = new ArrayList<>();
    
    private BigMemoryManager<String,ConfiguracionTramaDataVO> listaConfiguracionTramaDataVO = new BigMemoryManager<>();
    
    private Map<String,ConfiguracionTramaDetalleVO> configuracionTramaDetalleMap = new HashMap<>();
    
    private String[] schemaTableName = null;
    private Map<String,String> campoNoPersistente = new HashMap<>();
	/**
	 * Instancia un nuevo respuesta lectura archivo vo.
	 */
	public RespuestaCargaDataTramaVO() {
		super();
	}

}
