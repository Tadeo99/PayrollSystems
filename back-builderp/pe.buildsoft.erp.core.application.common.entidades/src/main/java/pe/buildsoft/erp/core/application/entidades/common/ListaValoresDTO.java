package pe.buildsoft.erp.core.application.entidades.common;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * La Class ListaValoresDTO.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Tue Apr 18 09:57:00 COT 2017
 * @since SIAA-CORE 2.1
 */
@Getter
@Setter
public class ListaValoresDTO implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id lista valores. */
	private Long idListaValores;

	/** El nombre. */
	private String nombre;

	/** El descripcion. */
	private String descripcion;

	/** El sentencia sql. */
	private String sentenciaSql;

	/** El estado. */
	private String estado;

	/**
	 * Instancia un nuevo lista valoresDTO.
	 */
	public ListaValoresDTO() {
		super();
	}

	/**
	 * Instancia un nuevo lista valoresDTO.
	 *
	 * @param idListaValores el id lista valores
	 * @param nombre         el nombre
	 * @param descripcion    el descripcion
	 * @param sentenciaSql   el sentencia sql
	 * @param estado         el estado
	 */
	public ListaValoresDTO(Long idListaValores, String nombre, String descripcion, String sentenciaSql, String estado) {
		super();
		this.idListaValores = idListaValores;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.sentenciaSql = sentenciaSql;
		this.estado = estado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idListaValores == null) ? 0 : idListaValores.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ListaValoresDTO other = (ListaValoresDTO) obj;
		if (idListaValores == null) {
			if (other.idListaValores != null) {
				return false;
			}
		} else if (!idListaValores.equals(other.idListaValores)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ListaValoresDTO [idListaValores=" + idListaValores + "]";
	}

}