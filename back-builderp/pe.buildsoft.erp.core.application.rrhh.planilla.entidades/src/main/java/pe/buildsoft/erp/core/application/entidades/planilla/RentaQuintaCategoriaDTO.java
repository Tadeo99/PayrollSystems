package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class RentaQuintaCategoriaDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 23:29:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class RentaQuintaCategoriaDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id renta quinta categoria. */
	private String idRentaQuintaCategoria;

	/** El tipo planilla. */
	private TipoPlanillaDTO tipoPlanilla;

	/** El periodo planilla. */
	private PeriodoPlanillaDTO periodoPlanilla;

	/**
	 * Instancia un nuevo renta quinta categoriaDTO.
	 */
	public RentaQuintaCategoriaDTO() {
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
		result = prime * result + ((idRentaQuintaCategoria == null) ? 0 : idRentaQuintaCategoria.hashCode());
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
		RentaQuintaCategoriaDTO other = (RentaQuintaCategoriaDTO) obj;
		if (idRentaQuintaCategoria == null) {
			if (other.idRentaQuintaCategoria != null) {
				return false;
			}
		} else if (!idRentaQuintaCategoria.equals(other.idRentaQuintaCategoria)) {
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
		return "RentaQuintaCategoriaDTO [idRentaQuintaCategoria=" + idRentaQuintaCategoria + "]";
	}

}