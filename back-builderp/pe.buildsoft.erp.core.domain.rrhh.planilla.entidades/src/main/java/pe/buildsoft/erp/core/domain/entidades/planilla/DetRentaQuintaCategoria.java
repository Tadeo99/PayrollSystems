package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class DetRentaQuintaCategoria.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Sun May 02 01:34:10 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "DetRentaQuintaCategoria", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class DetRentaQuintaCategoria extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id det renta quinta categoria. */
	@Id
	@Column(name = "idDetRentaQuintaCategoria", length = 32)
	private String idDetRentaQuintaCategoria;

	/** El id renta quinta categoria. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idRentaQuintaCategoria", referencedColumnName = "idRentaQuintaCategoria")
	private RentaQuintaCategoria idRentaQuintaCategoria;

	/** El personal.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPersonal", referencedColumnName = "idPersonal")
	private Personal personal; */
	@Column(name = "idPersonal", length = 32)
	private String idPersonal;

	/** El remuneracion mensual. */
	@Column(name = "remuneracionMensual", precision = 18, scale = 2)
	private BigDecimal remuneracionMensual;

	/** El falta tardanza. */
	@Column(name = "faltaTardanza", precision = 18, scale = 2)
	private BigDecimal faltaTardanza;

	/** El remuneracion enero. */
	@Column(name = "remuneracionEnero", precision = 18, scale = 2)
	private BigDecimal remuneracionEnero;

	/** El dias lab. */
	@Column(name = "diasLab", precision = 18, scale = 2)
	private BigDecimal diasLab;

	/** El otra remuneracion. */
	@Column(name = "otraRemuneracion", precision = 18, scale = 2)
	private BigDecimal otraRemuneracion;

	/** El total remuneracion. */
	@Column(name = "totalRemuneracion", precision = 18, scale = 2)
	private BigDecimal totalRemuneracion;

	/** El gratificacion julio. */
	@Column(name = "gratificacionJulio", precision = 18, scale = 2)
	private BigDecimal gratificacionJulio;

	/** El bono extra julio. */
	@Column(name = "bonoExtraJulio", precision = 18, scale = 2)
	private BigDecimal bonoExtraJulio;

	/** El gratificacion diciembre. */
	@Column(name = "gratificacionDiciembre", precision = 18, scale = 2)
	private BigDecimal gratificacionDiciembre;

	/** El bono extra diciembre. */
	@Column(name = "bonoExtraDiciembre", precision = 18, scale = 2)
	private BigDecimal bonoExtraDiciembre;

	/** El extra ordinario. */
	@Column(name = "extraOrdinario", precision = 18, scale = 2)
	private BigDecimal extraOrdinario;

	/** El remuneracion acumulada. */
	@Column(name = "remuneracionAcumulada", precision = 18, scale = 2)
	private BigDecimal remuneracionAcumulada;

	/** El remuneracion variable. */
	@Column(name = "remuneracionVariable", precision = 18, scale = 2)
	private BigDecimal remuneracionVariable;

	/** El bonificacion extra. */
	@Column(name = "bonificacionExtra", precision = 18, scale = 2)
	private BigDecimal bonificacionExtra;

	/** El remuneracion bruta. */
	@Column(name = "remuneracionBruta", precision = 18, scale = 2)
	private BigDecimal remuneracionBruta;

	/** El renta neta. */
	@Column(name = "rentaNeta", precision = 18, scale = 2)
	private BigDecimal rentaNeta;

	/** El impuesto renta. */
	@Column(name = "impuestoRenta", precision = 18, scale = 2)
	private BigDecimal impuestoRenta;

	/** El retencion. */
	@Column(name = "retencion", precision = 18, scale = 2)
	private BigDecimal retencion;

	/**
	 * Instancia un nuevo det renta quinta categoria.
	 */
	public DetRentaQuintaCategoria() {
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
		DetRentaQuintaCategoria other = (DetRentaQuintaCategoria) obj;
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
		return "DetRentaQuintaCategoria [idDetRentaQuintaCategoria=" + idDetRentaQuintaCategoria + "]";
	}

}