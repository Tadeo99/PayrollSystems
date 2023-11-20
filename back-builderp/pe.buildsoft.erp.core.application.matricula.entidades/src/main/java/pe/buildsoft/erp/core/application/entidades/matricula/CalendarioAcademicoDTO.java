package pe.buildsoft.erp.core.application.entidades.matricula;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.AnhioDTO;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class CalendarioAcademicoDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class CalendarioAcademicoDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id calendario academico. */
	private String idCalendarioAcademico;

	/** El anhio. */
	private AnhioDTO anhio;

	/** El periodo. */
	private PeriodoDTO periodo;

	/** El unidad. */
	private UnidadDTO unidad;

	/** El item by nivel. */
	private ItemDTO itemByNivel;

	/** El descripcion. */
	private String descripcion;

	/** El codigo cronograma. */
	private Long codigoCronograma;

	/** El fecha inicio. */
	private OffsetDateTime fechaInicio;

	/** El fecha fin. */
	private OffsetDateTime fechaFin;

	/** El estado. */
	private String estado;

	/** El usuario creacion. */
	private String usuarioCreacion;

	/** El fecha creacion. */
	private OffsetDateTime fechaCreacion;

	/** El usuario modificacion. */
	private String usuarioModificacion;

	/** El fecha modificacion. */
	private OffsetDateTime fechaModificacion;

	/**
	 * Instancia un nuevo calendario academicoDTO.
	 */
	public CalendarioAcademicoDTO() {
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
		result = prime * result + ((idCalendarioAcademico == null) ? 0 : idCalendarioAcademico.hashCode());
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
		CalendarioAcademicoDTO other = (CalendarioAcademicoDTO) obj;
		if (idCalendarioAcademico == null) {
			if (other.idCalendarioAcademico != null) {
				return false;
			}
		} else if (!idCalendarioAcademico.equals(other.idCalendarioAcademico)) {
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
		return "CalendarioAcademicoDTO [idCalendarioAcademico=" + idCalendarioAcademico + "]";
	}

}