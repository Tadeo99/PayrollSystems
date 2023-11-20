package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class LogConfiguracionTramaVO.
 * <ul>
 * <li>Copyright 2015 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Wed Oct 28 10:12:06 COT 2015
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class LogConfiguracionTramaVO extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id log configuracion trama. */
	private String idLogConfiguracionTrama;

	/** El configuracion trama. */
	private Long configuracionTrama;

	/** El nombre error. */
	private String nombreError;

	/** El tipo error. */
	private String tipoError;

	/** El conponente validado. */
	private String conponenteValidado;

	/** El nombre conponente validado. */
	private String nombreConponenteValidado;

	/** El descripcion error. */
	private String descripcionError;

	/** El fecha error. */
	private OffsetDateTime fechaError;

	/** La fila. */
	private String fila;

	/** La nombre campo. */
	private String nombreCampo;

	/**
	 * Instancia un nuevo log configuracion tramaDTO.
	 */
	public LogConfiguracionTramaVO() {
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
		result = prime * result + ((idLogConfiguracionTrama == null) ? 0 : idLogConfiguracionTrama.hashCode());
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
		LogConfiguracionTramaVO other = (LogConfiguracionTramaVO) obj;
		if (idLogConfiguracionTrama == null) {
			if (other.idLogConfiguracionTrama != null) {
				return false;
			}
		} else if (!idLogConfiguracionTrama.equals(other.idLogConfiguracionTrama)) {
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
		return idLogConfiguracionTrama;
	}

}