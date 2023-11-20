package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.type.TipoCampoType;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class ValueDataVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 23/06/2016
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class ValueDataVO implements Serializable {
	
	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** La fila. */
	private String fila;
	
	/** La data. */
	private Object data;
	
	/** La tipo campo type. */
	private TipoCampoType tipoCampoType = null; 
	
	/**
	 * Instancia un nuevo value data vo.
	 */
	public ValueDataVO() {
		super();
	}

	/**
	 * Instancia un nuevo value data vo.
	 *
	 * @param data el data
	 */
	public ValueDataVO(Object data) {
		super();
		this.data = data;
	}

	/**
	 * Instancia un nuevo value data vo.
	 *
	 * @param valueDataVO el value data vo
	 */
	public ValueDataVO(ValueDataVO valueDataVO) {
		super();
		this.data = valueDataVO.getData();
		this.fila = valueDataVO.getFila();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (data != null) {
			return  data + "";
		}
		return  "";
	} 
	
}

