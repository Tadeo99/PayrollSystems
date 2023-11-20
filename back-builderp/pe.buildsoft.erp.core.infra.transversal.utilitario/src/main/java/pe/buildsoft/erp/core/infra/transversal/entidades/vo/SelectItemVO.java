package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class SelectItemVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 06/04/2015
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class SelectItemVO implements Serializable {
 
	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** El value. */
	private Object id;
	
	/** El nombre. */
	private String nombre;
	
	/** La descripcion. */
	private String descripcion;
	
	private List<SelectItemVO> listaData;

	
	/**
	 * Instancia un nuevo select item vo.
	 */
	public SelectItemVO() {
		super();
	}

	/**
	 * Instancia un nuevo select item vo.
	 *
	 * @param id el id
	 * @param nombre el nombre
	 * @param descripcion el descripcion
	 */
	public SelectItemVO(Object id, String nombre,String descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	
	/**
	 * Instancia un nuevo select item vo.
	 *
	 * @param id el id
	 * @param nombre el nombre
	 * @param descripcion el descripcion
	 */
	public SelectItemVO(Object id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = nombre;
	}
	
}