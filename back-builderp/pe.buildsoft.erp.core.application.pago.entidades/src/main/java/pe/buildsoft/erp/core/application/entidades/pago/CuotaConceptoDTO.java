package pe.buildsoft.erp.core.application.entidades.pago;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.AnhioDTO;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class CuotaConceptoDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 21:45:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class CuotaConceptoDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id cuota concepto. */
	private String idCuotaConcepto;

	/** El anhio. */
	private Long idAnhio;
	private AnhioDTO anhio;

	/** El item by nivel. */
	private Long idItemByNivel;
	private ItemDTO itemByNivel;

	/** El catalogo cuenta. */
	private CatalogoCuentaDTO catalogoCuenta;

	/** El nro min fraccionamiento. */
	private Integer nroMinFraccionamiento;

	/** El nro max fraccionamiento. */
	private Integer nroMaxFraccionamiento;

	/** El monto. */
	private BigDecimal monto;

	private BigDecimal subTotal;

	private BigDecimal igv;

	/** El permanente. */
	private String permanente;

	/** El fecha tentativa. */
	private OffsetDateTime fechaTentativa;

	/** El fecha creacion. */
	private OffsetDateTime fechaCreacion;

	/** El usuario creacion. */
	private String usuarioCreacion;

	/** El fecha modificacion. */
	private OffsetDateTime fechaModificacion;

	/** El usuario modificacion. */
	private String usuarioModificacion;

	private Boolean aplicaIgv;


	/**
	 * Instancia un nuevo cuota conceptoDTO.
	 */
	public CuotaConceptoDTO() {
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
		result = prime * result + ((idCuotaConcepto == null) ? 0 : idCuotaConcepto.hashCode());
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
		CuotaConceptoDTO other = (CuotaConceptoDTO) obj;
		if (idCuotaConcepto == null) {
			if (other.idCuotaConcepto != null) {
				return false;
			}
		} else if (!idCuotaConcepto.equals(other.idCuotaConcepto)) {
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
		return "CuotaConceptoDTO [idCuotaConcepto=" + idCuotaConcepto + "]";
	}

}