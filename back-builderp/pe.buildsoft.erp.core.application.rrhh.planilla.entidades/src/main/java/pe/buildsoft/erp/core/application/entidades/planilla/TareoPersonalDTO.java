package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class TareoPersonalDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 23:10:02 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class TareoPersonalDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id tareo personal. */
	private String idTareoPersonal;

	/** El personal. 
	private Personal personal;*/
	private String idPersonal;
	private PersonalResponseVo personal;

	/** El item by mes.*/
	private Long idItemByMes;
	private ItemDTO itemByMes;

	/** El anhio. 
	private Anhio anhio;*/
	private Long idAnhio;

	/** El item by categoria ocupacional. 
	private Item itemByCategoriaOcupacional;*/
	private Long idItemByCategoriaOcupacional;
	private ItemDTO itemByCategoriaOcupacional;

	/** El item by periocidad. 
	private Item itemByPeriocidad;*/
	private Long idItemByPeriocidad;
	private ItemDTO itemByPeriocidad;

	/** El dias lab. */
	private Long diasLab;

	/** El dias tra. */
	private Long diasTra;
	
	/** El dias tra. */
	private Long dominical;

	/** El horas extras25. */
	private Long horasNormal;
	
	/** El horas extras25. */
	private Long horasExtras25;
	
	/** El horas extras35. */
	private Long horasExtras35;

	/** El horas extras. */
	private Long horasExtras100;
	
	/** El horas nocturna. */
	private Long horasNocturna;

	/** El vacaciones. */
	private Long vacaciones;
	
	/** El permisio sin goce de haber. */
	private Long permisoSinGoceHaber;
	
	/** El falta. */
	private Long falta;
	
	/** El subsidio. */
	private Long subsidio;
	
	/** El tardanza. */
	private Long tardanza;
	
	/** El rmv. */
	private BigDecimal rmv;
	
	/** El bofinicacion nocturna. */
	private BigDecimal bonifiNocturna;
	

	/**
	 * Instancia un nuevo asistencia personalDTO.
	 */
	public TareoPersonalDTO() {
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
		result = prime * result + ((idTareoPersonal == null) ? 0 : idTareoPersonal.hashCode());
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
		TareoPersonalDTO other = (TareoPersonalDTO) obj;
		if (idTareoPersonal == null) {
			if (other.idTareoPersonal != null) {
				return false;
			}
		} else if (!idTareoPersonal.equals(other.idTareoPersonal)) {
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
		return "AsistenciaPersonalDTO [idAsistenciaPersonal=" + idTareoPersonal + "]";
	}

}