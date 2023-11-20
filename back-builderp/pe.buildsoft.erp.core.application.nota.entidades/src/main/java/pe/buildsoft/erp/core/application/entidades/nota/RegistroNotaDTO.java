package pe.buildsoft.erp.core.application.entidades.nota;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class RegistroNotaDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class RegistroNotaDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id registro nota. */
	private String idRegistroNota;

	/** El matricula. */
	private String matricula;

	/** El nota final. */
	private BigDecimal notaFinal;

	/** El usuario creacion. */
	private String usuarioCreacion;

	/** El fecha creacion. */
	private OffsetDateTime fechaCreacion;

	/** El usuario modificacion. */
	private String usuarioModificacion;

	/** El fecha modificacion. */
	private OffsetDateTime fechaModificacion;

	/**
	 * Instancia un nuevo registro notaDTO.
	 */
	public RegistroNotaDTO() {
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
		result = prime * result + ((idRegistroNota == null) ? 0 : idRegistroNota.hashCode());
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
		RegistroNotaDTO other = (RegistroNotaDTO) obj;
		if (idRegistroNota == null) {
			if (other.idRegistroNota != null) {
				return false;
			}
		} else if (!idRegistroNota.equals(other.idRegistroNota)) {
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
		return "RegistroNotaDTO [idRegistroNota=" + idRegistroNota + "]";
	}

}