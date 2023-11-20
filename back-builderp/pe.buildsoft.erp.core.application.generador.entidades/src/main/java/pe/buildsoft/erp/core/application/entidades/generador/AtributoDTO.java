package pe.buildsoft.erp.core.application.entidades.generador;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class AtributoDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Mon Sep 26 12:14:56 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class AtributoDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id config tabla clasess det. */
	private String idAtributo;

	/** El config tabla class. */
	private ModeloDTO modelo;

	/** El nombre columna. */
	private String columna;

	/** El nombre atributo. */
	private String atributo;

	/** El es null. */
	private String esNull;

	/** El type. */
	private String type;

	/** El length. */
	private Integer length;

	/** El comentario columna. */
	private String comentario;

	/** El tipo l lave. */
	private String tipoLLave;

	/** El nombre tabla referencia. */
	private String idModeloRef;

	/** El mostrar grilla. */
	private String mostrarGrilla;

	/** El requiered filtro. */
	private String requieredFiltro;

	/** El requiered frm. */
	private String requieredFrm;

	/** El tipo componente. */
	private Long tipoComponente;

	/** El indice. */
	private String indice;

	/** El indice grupo. */
	private String indiceGrupo;

	/** El alter add column. */
	private String alterAdd;

	/** El alter mod column. */
	private String alterMod;

	/**
	 * Instancia un nuevo config tabla class det.
	 */
	public AtributoDTO() {
		super();
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
		result = prime * result + ((idAtributo == null) ? 0 : idAtributo.hashCode());
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
		AtributoDTO other = (AtributoDTO) obj;
		if (idAtributo == null) {
			if (other.idAtributo != null) {
				return false;
			}
		} else if (!idAtributo.equals(other.idAtributo)) {
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
		return "AtributoDTO [idAtributo=" + idAtributo + "]";
	}

}