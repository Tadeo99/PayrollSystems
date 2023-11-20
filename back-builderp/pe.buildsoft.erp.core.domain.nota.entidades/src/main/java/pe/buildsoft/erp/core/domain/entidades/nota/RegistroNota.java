package pe.buildsoft.erp.core.domain.entidades.nota;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class RegistroNota.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "RegistroNota", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_NOTA)
@Getter
@Setter
public class RegistroNota extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id registro nota. */
	@Id
	@Column(name = "idRegistroNota", length = 32)
	private String idRegistroNota;

	/** El matricula. */
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "idMatricula", referencedColumnName = "idMatricula")
	@Column(name = "idMatricula", length = 32)
	private String matricula;

	/** El nota final. */
	@Column(name = "notaFinal", precision = 18, scale = 2)
	private BigDecimal notaFinal;

	/** El usuario creacion. */
	@Column(name = "usuarioCreacion", length = 50)
	private String usuarioCreacion;

	/** El fecha creacion. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaCreacion")
	private OffsetDateTime fechaCreacion;

	/** El usuario modificacion. */
	@Column(name = "usuarioModificacion", length = 50)
	private String usuarioModificacion;

	/** El fecha modificacion. */
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaModificacion")
	private OffsetDateTime fechaModificacion;

	/** El registro nota det registro nota list. */
	@OneToMany(mappedBy = "registroNota", fetch = FetchType.LAZY)
	private List<DetRegistroNota> registroNotaDetRegistroNotaList = new ArrayList<>();

	/**
	 * Instancia un nuevo registro nota.
	 */
	public RegistroNota() {
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
		result = prime * result + ((idRegistroNota == null) ? 0 : idRegistroNota.hashCode());
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
		RegistroNota other = (RegistroNota) obj;
		if (idRegistroNota == null) {
			if (other.idRegistroNota != null) {
				return false;
			}
		} else if (!idRegistroNota.equals(other.idRegistroNota)) {
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
		return "RegistroNota [idRegistroNota=" + idRegistroNota + "]";
	}

}