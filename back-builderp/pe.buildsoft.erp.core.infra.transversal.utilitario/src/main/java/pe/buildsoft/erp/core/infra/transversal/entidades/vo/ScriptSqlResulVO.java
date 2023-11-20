package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class ScriptSqlResulVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 06/04/2015
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class ScriptSqlResulVO implements Serializable {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
    /** La lista header. */
    private List<String> listaHeader = new ArrayList<>();
    
    /** La lista data. */
    private List<Object[]> listaData = new ArrayList<>();
    
    /** La tiene error. */
    private boolean tieneError = false;
    
    /** La mensaje error. */
    private String mensajeError = "";
    
	/**
	 * Instancia un nuevo script sql resul vo.
	 */
	public ScriptSqlResulVO() {
		super();
	}

	/**
	 * Instancia un nuevo script sql resul vo.
	 *
	 * @param listaHeader el lista header
	 * @param listaData el lista data
	 */
	public ScriptSqlResulVO(List<String> listaHeader, List<Object[]> listaData) {
		super();
		this.listaHeader = listaHeader;
		this.listaData = listaData;
	}

}
