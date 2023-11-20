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
 * La Class ConfiguracionTramaDataVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 24/06/2016
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class ConfiguracionTramaDataVO implements Serializable  {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** La schema name. */
	private String schemaName;
	
	/** La table name. */
	private String tableName;
	
	/** La atribute name. */
	private String atributeName;
	
	/** La atribute value. */
	private ValueDataVO atributeValue;
	
	/** La atribute type. */
	private String atributeType;
	
	/** La es campo negocio. */
	private boolean esCampoNegocio;
	
	/** La lista campo value. */
	private List<ConfiguracionTramaDataVO> listaCampoValue = new ArrayList<ConfiguracionTramaDataVO>();
	
	/**
	 * Instancia un nuevo configuracion trama data vo.
	 */
	public ConfiguracionTramaDataVO() {
		super();
	}

   /**
    * Instancia un nuevo configuracion trama data vo.
    *
    * @param schemaName el schema name
    * @param tableName el table name
    * @param atributeName el atribute name
    * @param atributeValue el atribute value
    * @param atributeType el atribute type
    */
   public ConfiguracionTramaDataVO(String schemaName, String tableName,
			String atributeName, ValueDataVO atributeValue,String atributeType) {
		super();
		this.schemaName = schemaName;
		this.tableName = tableName;
		this.atributeName = atributeName;
		this.atributeValue = atributeValue;
		this.atributeType = atributeType;
	}

}
