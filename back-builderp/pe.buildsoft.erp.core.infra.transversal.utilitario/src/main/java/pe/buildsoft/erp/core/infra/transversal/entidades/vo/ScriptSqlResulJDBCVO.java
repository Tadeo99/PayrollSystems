package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class ScriptSqlResulJDBCVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 21/09/2015
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class ScriptSqlResulJDBCVO implements Serializable {

	private static final String CURSOR1 = "cursor1";

	private static final String RESULTADO1 = "resultado1";

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
    /** La lista header. */
    private List<String> listaHeader = new ArrayList<>();
    
    /** La lista data. */
    private List<Map<String,Object>> listaData = new ArrayList<>();
    
    /** La lista listaDataObject */
    private List<Object[]> listaDataObject = new ArrayList<>();
    
    /** La tiene error. */
    private boolean tieneError = false;
    
    /** La mensaje error. */
    private String mensajeError = "";
    
    private int executeUpdate = 0;
    
    private List<ScriptSqlResulJDBCVO> resulCursorList = new ArrayList<>();
    private Map<String,ScriptSqlResulJDBCVO> resulCursorListMap = new HashMap<>();
    private Map<String,Object> resulMap = new HashMap<>();
    
	/**
	 * Instancia un nuevo script sql resul vo.
	 */
	public ScriptSqlResulJDBCVO() {
		super();
	}

	public boolean isTieneData() {
		boolean resultado = false;
		if (!tieneError) {
			if ((listaData != null && listaData.size() > 0) || !resulMap.isEmpty()) {
				resultado = true;
			}
		}
		return resultado;
	}
	
	public boolean isTieneDataCursor() {
		boolean resultado = false;
		if (!tieneError) {
			if ((resulCursorList != null && resulCursorList.size() > 0) || !resulCursorListMap.isEmpty()) {
				resultado = true;
			}
		}
		return resultado;
	}

	
	/**
	 * Instancia un nuevo script sql resul vo.
	 *
	 * @param listaHeader el lista header
	 * @param listaData el lista data
	 */
	public ScriptSqlResulJDBCVO(List<String> listaHeader, List<Map<String,Object>> listaData) {
		super();
		this.listaHeader = listaHeader;
		this.listaData = listaData;
	}

	

	public ScriptSqlResulJDBCVO getResulCursor() {
		if (resulCursorList.size() > 0) {
			return resulCursorList.get(0);	
		} else if (resulCursorListMap.containsKey(CURSOR1)) {
			return resulCursorListMap.get(CURSOR1);	
		}
		return new ScriptSqlResulJDBCVO();
	}
	public String getResul() {
		if (listaData.size() > 0 && listaData.get(0).containsKey(RESULTADO1)) {
			return listaData.get(0).get(RESULTADO1) + "";	
		} else if (resulMap.containsKey(RESULTADO1)) {
			return resulMap.get(RESULTADO1) + "";	
		}
		return null;
	}
	public Object getResulObject() {
		if (listaData.size() > 0 && listaData.get(0).containsKey(RESULTADO1)) {
			return listaData.get(0).get(RESULTADO1);	
		} else if (resulMap.containsKey(RESULTADO1)) {
			return resulMap.get(RESULTADO1);	
		}
		return null;
	}
}
