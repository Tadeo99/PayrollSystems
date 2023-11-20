package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class ItemVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 06/04/2015
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class ItemVO  implements Serializable {
 
	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** La id. */
	private Object id;
	
	/** La nombre. */
	private String nombre;
	
	/** La object. */
	private Object object;
	
	/** La object2. */
	private Object object2;
	
	/** La object3. */
	private Object object3;
	
	/**
	 * Instancia un nuevo item vo.
	 *
	 * @param id el id
	 * @param nombre el nombre
	 * @param object el object
	 * @param object2 el object2
	 * @param object3 el object3
	 */
	public ItemVO(Object id, String nombre, Object object, Object object2, Object object3) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.object = object;
		this.object2 = object2;
		this.object3 = object3;
	}
}