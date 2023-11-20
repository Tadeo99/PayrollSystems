package pe.buildsoft.erp.core.infra.transversal.entidades;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * La Class ModalVO.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Lun Ago 23 10:00:02 COT 2021
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class ModalVO extends BaseEntidad implements Serializable {
	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** id */
	private transient Object id;
	/** La valor 1 */
	private String nombre;
	/** La valor 2 */
	private String descripcion;
	/**
	 * Instancia un nuevo ModalVO.
	 */
	public ModalVO() {
		super();
	}
	/**
	 * Instancia un nuevo ModalVO.
	 */
	public ModalVO(Object id, String nombre) {
		this(id, nombre, null);
	}
	
	/**
	 * Instancia un nuevo ModalVO.
	 */
	public ModalVO(Object id, String nombre, String descripcion) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
}