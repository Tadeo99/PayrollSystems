package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BigMemoryManager;

// TODO: Auto-generated Javadoc
/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class RespuestaLecturaAgrupadoVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 24/06/2016
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class RespuestaLecturaAgrupadoVO implements Serializable {
	
	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** La lista agrupado data resul map. */
	private List<Map<String,ValueDataVO>> listaAgrupadoDataResulMap = new ArrayList<>();
    
    /** La lista data resul map. */
    private List<Map<String,ValueDataVO>> listaDataResulMap = new ArrayList<>();
	
    /** La lista log configuracion trama ayuda dto. */
    private BigMemoryManager<String,LogConfiguracionTramaVO> listaLogConfiguracionTramaAyudaDTO = new BigMemoryManager<>();
    
    /** La error numero lote. */
    private boolean errorNumeroLote;
    
	/**
	 * Instancia un nuevo respuesta lectura agrupado vo.
	 */
	public RespuestaLecturaAgrupadoVO() {
		super();
	}

	
}
