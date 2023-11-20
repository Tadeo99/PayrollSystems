package pe.buildsoft.erp.core.application.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.escalafon.PersonalDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class DetRentaQuintaCategoriaDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 01:34:10 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class DetRentaQuintaCategoriaDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id det renta quinta categoria. */
	private String idDetRentaQuintaCategoria;

	/** El id renta quinta categoria. */
	private RentaQuintaCategoriaDTO idRentaQuintaCategoria;

	/** El personal. */
	private PersonalDTO personal;
	private String idPersonal;

	/** El remuneracion mensual. */
	private BigDecimal remuneracionMensual;

	/** El falta tardanza. */
	private BigDecimal faltaTardanza;

	/** El remuneracion enero. */
	private BigDecimal remuneracionEnero;

	/** El dias lab. */
	private BigDecimal diasLab;

	/** El otra remuneracion. */
	private BigDecimal otraRemuneracion;

	/** El total remuneracion. */
	private BigDecimal totalRemuneracion;

	/** El gratificacion julio. */
	private BigDecimal gratificacionJulio;

	/** El bono extra julio. */
	private BigDecimal bonoExtraJulio;

	/** El gratificacion diciembre. */
	private BigDecimal gratificacionDiciembre;

	/** El bono extra diciembre. */
	private BigDecimal bonoExtraDiciembre;

	/** El extra ordinario. */
	private BigDecimal extraOrdinario;

	/** El remuneracion acumulada. */
	private BigDecimal remuneracionAcumulada;

	/** El remuneracion variable. */
	private BigDecimal remuneracionVariable;

	/** El bonificacion extra. */
	private BigDecimal bonificacionExtra;

	/** El remuneracion bruta. */
	private BigDecimal remuneracionBruta;

	/** El renta neta. */
	private BigDecimal rentaNeta;

	/** El impuesto renta. */
	private BigDecimal impuestoRenta;

	/** El retencion. */
	private BigDecimal retencion;

	/**
	 * Instancia un nuevo det renta quinta categoriaDTO.
	 */
	public DetRentaQuintaCategoriaDTO() {
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
		result = prime * result + ((idDetRentaQuintaCategoria == null) ? 0 : idDetRentaQuintaCategoria.hashCode());
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
		DetRentaQuintaCategoriaDTO other = (DetRentaQuintaCategoriaDTO) obj;
		if (idDetRentaQuintaCategoria == null) {
			if (other.idDetRentaQuintaCategoria != null) {
				return false;
			}
		} else if (!idDetRentaQuintaCategoria.equals(other.idDetRentaQuintaCategoria)) {
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
		return "DetRentaQuintaCategoriaDTO [idDetRentaQuintaCategoria=" + idDetRentaQuintaCategoria + "]";
	}

}