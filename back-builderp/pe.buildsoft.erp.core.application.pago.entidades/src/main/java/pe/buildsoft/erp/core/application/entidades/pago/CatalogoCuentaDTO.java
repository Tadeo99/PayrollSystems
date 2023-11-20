package pe.buildsoft.erp.core.application.entidades.pago;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class CatalogoCuentaDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 21:45:30 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class CatalogoCuentaDTO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id catalogo cuenta. */
	private Long idCatalogoCuenta;

	/** El cuenta. */
	private String cuenta;

	/** El nro cuenta. */
	private String nroCuenta;

	/** El clasificacion. */
	private ClasificacionDTO clasificacion;

	/** El estado. */
	private String estado;

	/** El monto. */
	private BigDecimal monto;

	/** El fecha creacion. */
	private OffsetDateTime fechaCreacion;

	/** El usuario creacion. */
	private String usuarioCreacion;

	/** El fecha modificacion. */
	private OffsetDateTime fechaModificacion;

	/** El usuario modificacion. */
	private String usuarioModificacion;

	/**
	 * Instancia un nuevo catalogo cuentaDTO.
	 */
	public CatalogoCuentaDTO() {
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
		result = prime * result + ((idCatalogoCuenta == null) ? 0 : idCatalogoCuenta.hashCode());
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
		CatalogoCuentaDTO other = (CatalogoCuentaDTO) obj;
		if (idCatalogoCuenta == null) {
			if (other.idCatalogoCuenta != null) {
				return false;
			}
		} else if (!idCatalogoCuenta.equals(other.idCatalogoCuenta)) {
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
		return "CatalogoCuentaDTO [idCatalogoCuenta=" + idCatalogoCuenta + "]";
	}

}