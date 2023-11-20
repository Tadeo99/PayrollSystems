package pe.buildsoft.erp.core.application.entidades.generador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ModeloDTO.
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
public class ModeloDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id config tabla class. */
	private String idModelo;

	/** El config grupo servicio. */
	private GrupoNegocioDTO grupoNegocio;

	/** El sequence tabla. */
	private String sequence;

	/** El nombre tabla. */
	private String nombreTabla;

	/** El nombre clase. */
	private String nombreClase;

	/** El codigo. */
	private String codigo;

	/** El comentario tabla. */
	private String comentario;

	/** El notas. */
	private String notas;

	/** El config tabla class config tabla class det list. */
	private List<AtributoDTO> listaAtributo = new ArrayList<>();

	/**
	 * Instancia un nuevo config tabla class.
	 */
	public ModeloDTO() {
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
		result = prime * result + ((idModelo == null) ? 0 : idModelo.hashCode());
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
		ModeloDTO other = (ModeloDTO) obj;
		if (idModelo == null) {
			if (other.idModelo != null) {
				return false;
			}
		} else if (!idModelo.equals(other.idModelo)) {
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
		return "ModeloDTO [idModelo=" + idModelo + "]";
	}

}