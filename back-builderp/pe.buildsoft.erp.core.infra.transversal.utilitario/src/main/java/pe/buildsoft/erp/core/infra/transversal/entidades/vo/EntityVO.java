package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * La Class EntityVO.
 * <ul>
 * <li>Copyright 2014 MAPFRE-
 * mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Mon Aug 11 09:00:30 COT 2014
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class EntityVO {

	/** El alias. */
	private String alias;
	
	/** El classs. */
	private String classs;
	
	/** La posicion. */
	private int posicion;
	
	
	/**
	 * Instancia un nuevo entity vo.
	 */
	public EntityVO() {
		super();
	}

	

	/**
	 * Instancia un nuevo entity vo.
	 *
	 * @param alias el alias
	 * @param classs el classs
	 */
	public EntityVO(String alias, String classs) {
		super();
		this.alias = alias;
		this.classs = classs;
	}



	/**
	 * Instancia un nuevo entity vo.
	 *
	 * @param alias el alias
	 * @param classs el classs
	 * @param posicion el posicion
	 */
	public EntityVO(String alias, String classs, int posicion) {
		super();
		this.alias = alias;
		this.classs = classs;
		this.posicion = posicion;
	}

}
