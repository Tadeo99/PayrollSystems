package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class HechoReglaConfiguradorTramaVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 24/06/2016
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class ReglaVO implements Serializable {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** La data tupa map. */
	private Map<String, ValueDataVO> dataTupaMap = new HashMap<>();

	/** La resultado. */
	private ValueDataVO resultado = new ValueDataVO();

	/** La fila. */
	private String fila;

	/** La es simulacion. */
	private boolean esSimulacion;
	
	private boolean error;

	/**
	 * Instancia un nuevo hecho regla configurador trama vo.
	 */
	public ReglaVO() {
		super();
	}

	/**
	 * Instancia un nuevo hecho regla configurador trama vo.
	 *
	 * @param dataTupaMap
	 *            el data tupa map
	 * @param resultado
	 *            el resultado
	 */
	public ReglaVO(Map<String, ValueDataVO> dataTupaMap, ValueDataVO resultado) {
		super();
		this.dataTupaMap = dataTupaMap;
		this.resultado = resultado;
	}

	
}
