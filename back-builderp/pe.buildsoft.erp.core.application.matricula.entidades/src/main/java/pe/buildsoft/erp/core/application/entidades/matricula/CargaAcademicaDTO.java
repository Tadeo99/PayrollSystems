package pe.buildsoft.erp.core.application.entidades.matricula;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.admision.SeccionDTO;
import pe.buildsoft.erp.core.application.entidades.common.AnhioDTO;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.escalafon.PersonalDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class CargaAcademicaDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class CargaAcademicaDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id carga academica. */
	private String idCargaAcademica;

	/** El id entidad. */
	private String idEntidad;

	/** El codigo salon. */
	private String codigoSalon;

	/** El nombre. */
	private String nombre;

	/** El anhio. */
	private AnhioDTO anhio;

	/** El seccion. */
	private SeccionDTO seccion;

	/** El item by turno. */
	private ItemDTO itemByTurno;

	/** El aula. */
	private AulaDTO aula;

	/** El personal by tutor. */
	private PersonalDTO personalByTutor;

	/** El personal by co tutor. */
	private PersonalDTO personalByCoTutor;

	/** El personal by coordinador. */
	private PersonalDTO personalByCoordinador;

	/** El tipo periodo. */
	private String tipoPeriodo;

	/** El nro vacante. */
	private Long nroVacante;

	/** El usuario creacion. */
	private String usuarioCreacion;

	/** El fecha creacion. */
	private OffsetDateTime fechaCreacion;

	/** El usuario modificacion. */
	private String usuarioModificacion;

	/** El fecha modificacion. */
	private OffsetDateTime fechaModificacion;

	/** El observacion. */
	private String observacion;

	/** El carga academica detalle carga academica list. */
	private List<DetalleCargaAcademicaDTO> cargaAcademicaDetalleCargaAcademicaList = new ArrayList<>();

	/**
	 * Instancia un nuevo carga academicaDTO.
	 */
	public CargaAcademicaDTO() {
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
		result = prime * result + ((idCargaAcademica == null) ? 0 : idCargaAcademica.hashCode());
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
		CargaAcademicaDTO other = (CargaAcademicaDTO) obj;
		if (idCargaAcademica == null) {
			if (other.idCargaAcademica != null) {
				return false;
			}
		} else if (!idCargaAcademica.equals(other.idCargaAcademica)) {
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
		return "CargaAcademicaDTO [idCargaAcademica=" + idCargaAcademica + "]";
	}

}