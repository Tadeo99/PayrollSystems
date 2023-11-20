package pe.buildsoft.erp.core.domain.entidades.escalafon;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Personal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Personal", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_ESCAL)
@Getter
@Setter
public class Personal extends BaseEntidad implements Serializable {

	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El id personal. */
	@Id
	@Column(name = "idPersonal", length = 32)
	private String idPersonal;

	/** El codigo unico. */
	@Column(name = "codigoUnico", length = 150)
	private String codigoUnico;

	/** El estado. */
	@Column(name = "estado", length = 1)
	private String estado;

	/**
	 * El item by doc identidad.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idDocIdentidad", referencedColumnName = "idItem") private
	 *                  Item itemByDocIdentidad;
	 */
	@Column(name = "idDocIdentidad", precision = 18, scale = 0)
	private Long idItemByDocIdentidad;

	/** El nro doc. */
	@Column(name = "nroDoc", length = 20)
	private String nroDoc;

	/** El apellido paterno. */
	@Column(name = "apellidoPaterno", length = 150)
	private String apellidoPaterno;

	/** El apellido materno. */
	@Column(name = "apellidoMaterno", length = 150)
	private String apellidoMaterno;

	/** El nombres. */
	@Column(name = "nombres", length = 150)
	private String nombres;

	/**
	 * El item by estado civil.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idEstadoCivil", referencedColumnName = "idItem") private
	 *                  Item itemByEstadoCivil;
	 */
	@Column(name = "idEstadoCivil", precision = 18, scale = 0)
	private Long idItemByEstadoCivil;

	/** El fecha nacimiento. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaNacimiento")
	private OffsetDateTime fechaNacimiento;

	/** El telefono1. */
	@Column(name = "telefono1", length = 20)
	private String telefono1;

	/** El telefono2. */
	@Column(name = "telefono2", length = 20)
	private String telefono2;

	/** El celular. */
	@Column(name = "celular", length = 20)
	private String celular;

	/** El email. */
	@Column(name = "email", length = 100)
	private String email;

	/** El codigo control. */
	@Column(name = "codigoControl", length = 50)
	private String codigoControl;

	/** El nro tarjeta. */
	@Column(name = "nroTarjeta", length = 50)
	private String nroTarjeta;

	/** El sexo. */
	@Column(name = "sexo", length = 32)
	private String sexo;

	/**
	 * El item by pais emisor.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idPaisEmisor", referencedColumnName = "idItem") private
	 *                  Item itemByPaisEmisor;
	 */
	@Column(name = "idPaisEmisor", precision = 18, scale = 0)
	private Long idItemByPaisEmisor;

	/**
	 * El lugar nacimiento.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idLugarNacimiento", referencedColumnName = "idUbigeo")
	 *                  private Ubigeo lugarNacimiento;
	 */
	@Column(name = "idLugarNacimiento", length = 6)
	private String idLugarNacimiento;

	/**
	 * El item by nacionalidad.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idNacionalidad", referencedColumnName = "idItem") private
	 *                  Item itemByNacionalidad;
	 */
	@Column(name = "idNacionalidad", precision = 18, scale = 0)
	private Long idItemByNacionalidad;

	/** El foto. */
	@Column(name = "foto", length = 150)
	private String foto;

	/** El firma. */
	@Column(name = "firma", length = 150)
	private String firma;

	/**
	 * El item by situacion.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idSituacion", referencedColumnName = "idItem") private
	 *                  Item itemBySituacion;
	 */
	@Column(name = "idSituacion", precision = 18, scale = 0)
	private Long idItemBySituacion;

	/**
	 * El item by afp.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idAfp", referencedColumnName = "idItem") private Item
	 *                  itemByAfp;
	 */
	@Column(name = "idAfp", precision = 18, scale = 0)
	private Long idItemByAfp;

	/** El fecha ingresospp. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaIngresospp")
	private OffsetDateTime fechaIngresospp;

	/** El fecha ingresoonp. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaIngresoonp")
	private OffsetDateTime fechaIngresoonp;

	/** El cuspp. */
	@Column(name = "cuspp", length = 150)
	private String cuspp;

	/** El es comision mixta. */
	@Column(name = "esComisionMixta", length = 1)
	private String esComisionMixta;

	/** El aporta senati. */
	@Column(name = "aportaSenati", length = 1)
	private String aportaSenati;

	/**
	 * El item by regimen laboral.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idRegimenLaboral", referencedColumnName = "idItem")
	 *                  private Item itemByRegimenLaboral;
	 */
	@Column(name = "idRegimenLaboral", precision = 18, scale = 0)
	private Long idItemByRegimenLaboral;

	/**
	 * El item by regimen pensionario.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idRegimenPensionario", referencedColumnName = "idItem")
	 *                  private Item itemByRegimenPensionario;
	 */
	@Column(name = "idRegimenPensionario", precision = 18, scale = 0)
	private Long idItemByRegimenPensionario;

	/** El es afiliacion asegura tu pension. */
	@Column(name = "esAfiliacionAseguraTuPension", length = 1)
	private String esAfiliacionAseguraTuPension;

	/**
	 * El item by convenio evitar doble tributacion.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idConvenioEvitarDobleTributacion", referencedColumnName =
	 *                  "idItem") private Item itemByConvenioEvitarDobleTributacion;
	 */
	@Column(name = "idConvenioEvitarDobleTributacion", precision = 18, scale = 0)
	private Long idItemByConvenioEvitarDobleTributacion;

	/** El ruc cas. */
	@Column(name = "rucCas", length = 11)
	private String rucCas;

	/** El auto generado essalud. */
	@Column(name = "autoGeneradoEssalud", length = 150)
	private String autoGeneradoEssalud;

	/**
	 * El item by categoria trabajador.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idCategoriaTrabajador", referencedColumnName = "idItem")
	 *                  private Item itemByCategoriaTrabajador;
	 */
	@Column(name = "idCategoriaTrabajador", precision = 18, scale = 0)
	private Long idItemByCategoriaTrabajador;

	/**
	 * El empleador destaca personal tercero.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idEmpleadorDestacaPersonalTercero", referencedColumnName
	 *                  = "idEmpresa") private Empresa
	 *                  empleadorDestacaPersonalTercero;
	 */

	@Column(name = "idEmpleadorDestacaPersonalTercero", precision = 18, scale = 0)
	private Long idEmpleadorDestacaPersonalTercero;

	/** El es salud vida. */
	@Column(name = "esSaludVida", length = 1)
	private String esSaludVida;

	/** El es trabajador sindicalizado. */
	@Column(name = "esTrabajadorSindicalizado", length = 1)
	private String esTrabajadorSindicalizado;

	/** El sujeto controlnmediato. */
	@Column(name = "sujetoControlnmediato", length = 1)
	private String sujetoControlnmediato;

	/** El regimen alter acumula atipico. */
	@Column(name = "regimenAlterAcumulaAtipico", length = 1)
	private String regimenAlterAcumulaAtipico;

	/** El con asignacion familiar. */
	@Column(name = "conAsignacionFamiliar", length = 1)
	private String conAsignacionFamiliar;

	/** El sujeto horario nocturno. */
	@Column(name = "sujetoHorarioNocturno", length = 1)
	private String sujetoHorarioNocturno;

	/** El jornada trabajo maxima. */
	@Column(name = "jornadaTrabajoMaxima", length = 1)
	private String jornadaTrabajoMaxima;

	/**
	 * El item by sctr salud.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idSctrSalud", referencedColumnName = "idItem") private
	 *                  Item itemBySctrSalud;
	 */
	@Column(name = "idSctrSalud", precision = 18, scale = 0)
	private Long idItemBySctrSalud;

	/**
	 * El item by regimen aseg salud.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idRegimenAsegSalud", referencedColumnName = "idItem")
	 *                  private Item itemByRegimenAsegSalud;
	 */
	@Column(name = "idRegimenAsegSalud", precision = 18, scale = 0)
	private Long idItemByRegimenAsegSalud;

	/**
	 * El item by sctr pension.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idSctrPension", referencedColumnName = "idItem") private
	 *                  Item itemBySctrPension;
	 */
	@Column(name = "idSctrPension", precision = 18, scale = 0)
	private Long idItemBySctrPension;

	/**
	 * El item by eps.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idEps", referencedColumnName = "idItem") private Item
	 *                  itemByEps;
	 */
	@Column(name = "idEps", precision = 18, scale = 0)
	private Long idItemByEps;

	/**
	 * El item by servico medico.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idServicoMedico", referencedColumnName = "idItem")
	 *                  private Item itemByServicoMedico;
	 */
	@Column(name = "idServicoMedico", precision = 18, scale = 0)
	private Long idItemByServicoMedico;

	/**
	 * El item by periocidad ingreso.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idPeriocidadIngreso", referencedColumnName = "idItem")
	 *                  private Item itemByPeriocidadIngreso;
	 */
	@Column(name = "idPeriocidadIngreso", precision = 18, scale = 0)
	private Long idItemByPeriocidadIngreso;

	/**
	 * El item by tipo pago.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idTipoPago", referencedColumnName = "idItem") private
	 *                  Item itemByTipoPago;
	 */
	@Column(name = "idTipoPago", precision = 18, scale = 0)
	private Long idItemByTipoPago;

	/** El renta5ta exonerada e inafecta. */
	@Column(name = "renta5taExoneradaEInafecta", length = 1)
	private String renta5taExoneradaEInafecta;

	/** El renta quinta manual. */
	@Column(name = "rentaQuintaManual", length = 1)
	private String rentaQuintaManual;

	/**
	 * El item by situacion especial.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idSituacionEspecial", referencedColumnName = "idItem")
	 *                  private Item itemBySituacionEspecial;
	 */
	@Column(name = "idSituacionEspecial", precision = 18, scale = 0)
	private Long idItemBySituacionEspecial;

	/**
	 * El item by categoria ocupacional.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idCategoriaOcupacional", referencedColumnName = "idItem")
	 *                  private Item itemByCategoriaOcupacional;
	 */
	@Column(name = "idCategoriaOcupacional", precision = 18, scale = 0)
	private Long idItemByCategoriaOcupacional;

	/**
	 * El item by ocupacion.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idOcupacion", referencedColumnName = "idItem") private
	 *                  Item itemByOcupacion;
	 */
	@Column(name = "idOcupacion", precision = 18, scale = 0)
	private Long idItemByOcupacion;

	/**
	 * El item by ocupacion regimen publico.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idOcupacionRegPublico", referencedColumnName = "idItem")
	 *                  private Item itemByOcupacionRegimenPublico;
	 */
	@Column(name = "idOcupacionRegPublico", precision = 18, scale = 0)
	private Long idItemByOcupacionRegimenPublico;

	/** El discapacidad. */
	@Column(name = "discapacidad", length = 1)
	private String discapacidad;

	/** El es pencionista. */
	@Column(name = "esPencionista", length = 1)
	private String esPencionista;

	/**
	 * El item by tipo pension.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idTipoPension", referencedColumnName = "idItem") private
	 *                  Item itemByTipoPension;
	 */
	@Column(name = "idTipoPension", precision = 18, scale = 0)
	private Long idItemByTipoPension;

	/**
	 * El item by regimen pensionario pension.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idRegimenPensionarioPension", referencedColumnName =
	 *                  "idItem") private Item itemByRegimenPensionarioPension;
	 */
	@Column(name = "idRegimenPensionarioPension", precision = 18, scale = 0)
	private Long idItemByRegimenPensionarioPension;

	/** El fecha inscripcion reg pensionario. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaInscripcionRegPensionario")
	private OffsetDateTime fechaInscripcionRegPensionario;

	/** El cuspp pension. */
	@Column(name = "cusppPension", length = 150)
	private String cusppPension;

	/**
	 * El item by tipo pago pension.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idTipoPagoPension", referencedColumnName = "idItem")
	 *                  private Item itemByTipoPagoPension;
	 */
	@Column(name = "idTipoPagoPension", precision = 18, scale = 0)
	private Long idItemByTipoPagoPension;

	/** El informa otros ingreso5ta categoria. */
	@Column(name = "informaOtrosIngreso5taCategoria", length = 1)
	private String informaOtrosIngreso5taCategoria;

	/** El es practicante. */
	@Column(name = "esPracticante", length = 1)
	private String esPracticante;

	/** El madre responsa famliar. */
	@Column(name = "madreResponsaFamliar", length = 1)
	private String madreResponsaFamliar;

	/**
	 * El item by tipo centro formacion profesional.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idTipoCentroFormacionProfesional", referencedColumnName =
	 *                  "idItem") private Item itemByTipoCentroFormacionProfesional;
	 */
	@Column(name = "idTipoCentroFormacionProfesional", precision = 18, scale = 0)
	private Long idItemByTipoCentroFormacionProfesional;

	/**
	 * El item by tipo modalidad formativa.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idTipoModalidadFormativa", referencedColumnName =
	 *                  "idItem") private Item itemByTipoModalidadFormativa;
	 */
	@Column(name = "idTipoModalidadFormativa", precision = 18, scale = 0)
	private Long idItemByTipoModalidadFormativa;

	/** El vencimiento fotocheck. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "vencimientoFotocheck")
	private OffsetDateTime vencimientoFotocheck;

	/**
	 * El item by situacion educativa.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idSituacionEducativa", referencedColumnName = "idItem")
	 *                  private Item itemBySituacionEducativa;
	 */
	@Column(name = "idSituacionEducativa", precision = 18, scale = 0)
	private Long idItemBySituacionEducativa;

	/** El es educacion completa ins educativa peru. */
	@Column(name = "esEducacionCompletaInsEducativaPeru", length = 1)
	private String esEducacionCompletaInsEducativaPeru;

	/** El carrera. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCarrera", referencedColumnName = "idCarrera")
	private Carrera carrera;

	/** El anho egreso. */
	@Column(name = "anhoEgreso", precision = 18, scale = 0)
	private Long anhoEgreso;

	/**
	 * El entidad.
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * @JoinColumn(name = "idEntidad", referencedColumnName = "idEntidad")
	 *                  // @Column(name = "idEntidad", length = 32) private Entidad
	 *                  entidad;
	 */
	@Column(name = "idEntidad", length = 32)
	private String idEntidad;

	/** El sede. */
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "idSede", referencedColumnName = "idSede")
	@Column(name = "idSede", length = 32)
	private String sede;

	/** El item by cargo. */
	@Column(name = "idItemByCargo", precision = 18, scale = 0)
	private Long idItemByCargo;

	/** El item area. */
	@Column(name = "idItemByArea", precision = 18, scale = 0)
	private Long idItemByArea;

	/** El fecha ingreso. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaIngreso")
	private OffsetDateTime fechaIngreso;

	/** El fecha cese. */
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaCese")
	private OffsetDateTime fechaCese;

	/** El remineracion. */
	@Column(name = "remuneracion", precision = 18, scale = 2)
	private BigDecimal remuneracion;

	/** El asignacion familiar. */
	@Column(name = "asignacionfamiliar", precision = 18, scale = 2)
	private BigDecimal asignacionFamiliar;

	/** El prestaciones alimentarias. */
	@Column(name = "cantafiliados", precision = 18, scale = 0)
	private Integer cantAfiliados;

	@Transient
	private DireccionPersonal direccion1;
	@Transient
	private DireccionPersonal direccion2;
	@Transient
	private CuentaBancariaPersonal cuentaBancariaCts;
	@Transient
	private CuentaBancariaPersonal cuentaBancariaPago;

	/*
	 * @Transient private List<ConceptoCaluldadosVO> listaIngreso = new
	 * ArrayList<>();
	 * 
	 * @Transient private List<ConceptoCaluldadosVO> listaDescuento = new
	 * ArrayList<>();
	 * 
	 * @Transient private List<ConceptoCaluldadosVO> listaAporteTrabajador = new
	 * ArrayList<>();
	 * 
	 * @Transient private List<ConceptoCaluldadosVO> listaAporteEmpleador = new
	 * ArrayList<>();
	 */
	// new
	@Transient
	private String usuarioSession;

	/**
	 * Instancia un nuevo personal.
	 */
	public Personal() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPersonal == null) ? 0 : idPersonal.hashCode());
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
		Personal other = (Personal) obj;
		if (idPersonal == null) {
			if (other.idPersonal != null) {
				return false;
			}
		} else if (!idPersonal.equals(other.idPersonal)) {
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
		return "Personal [idPersonal=" + idPersonal + "]";
	}

}