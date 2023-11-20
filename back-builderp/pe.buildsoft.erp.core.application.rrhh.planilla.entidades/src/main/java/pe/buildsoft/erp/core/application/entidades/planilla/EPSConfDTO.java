package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class EPSConfDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:10:02 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class EPSConfDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id eps. */
	private String idEPSConf;

	/** El item by eps. */
	private Long idItemByEps;
	
	private ItemDTO itemByEps;

	private BigDecimal planBase;
	
	private BigDecimal planAdicional1;
	
	private BigDecimal planAdicional2;
	
	private BigDecimal planAdicional3;
	
	private BigDecimal planAdicional4;

	/**
	 * Instancia un nuevo concepto regimen pensionario.
	 */
	public EPSConfDTO() {
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
		result = prime * result + ((idEPSConf == null) ? 0 : idEPSConf.hashCode());
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
		EPSConfDTO other = (EPSConfDTO) obj;
		if (idEPSConf == null) {
			if (other.idEPSConf != null) {
				return false;
			}
		} else if (!idEPSConf.equals(other.idEPSConf)) {
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
		return "EPS [idEPSConf=" + idEPSConf + "]";
	}

}