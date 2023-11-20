package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class TipoPlanilla.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:10:03 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "TipoPlanilla", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class TipoPlanilla extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id tipo planilla. */
	@Id
	@Column(name = "idTipoPlanilla", length = 32)
	private String idTipoPlanilla;

	/** El codigo. */
	@Column(name = "codigo", length = 10)
	private String codigo;

	/** El item by tipo moneda. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipomoneda", referencedColumnName = "idItem")
	private Item itemByTipoMoneda;*/
	@Column(name = "idTipomoneda", precision = 18, scale = 0)
	private Long idItemByTipoMoneda;
	
	/** El descripcion. */
	@Column(name = "descripcion", length = 100)
	private String descripcion;

	/** El item by categoria trabajador. 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCategoriaTrabajador", referencedColumnName = "idItem")
	private Item itemByCategoriaTrabajador;*/
	@Column(name = "idCategoriaTrabajador", precision = 18, scale = 0)
	private Long idItemByCategoriaTrabajador;

	/** El tipo planilla planilla list. */
//	@OneToMany(mappedBy = "tipoPlanilla", fetch = FetchType.LAZY)
	@Transient
	private List<Planilla> tipoPlanillaPlanillaList = new ArrayList<>();

	/** El tipo planilla conceptos tipo planilla list. */
	@OneToMany(mappedBy = "tipoPlanilla", fetch = FetchType.LAZY)
	@Transient
	private List<ConceptosTipoPlanilla> tipoPlanillaConceptosTipoPlanillaList = new ArrayList<>();

	/**
	 * Instancia un nuevo tipo planilla.
	 */
	public TipoPlanilla() {
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
		result = prime * result + ((idTipoPlanilla == null) ? 0 : idTipoPlanilla.hashCode());
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
		TipoPlanilla other = (TipoPlanilla) obj;
		if (idTipoPlanilla == null) {
			if (other.idTipoPlanilla != null) {
				return false;
			}
		} else if (!idTipoPlanilla.equals(other.idTipoPlanilla)) {
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
		return "TipoPlanilla [idTipoPlanilla=" + idTipoPlanilla + "]";
	}

}