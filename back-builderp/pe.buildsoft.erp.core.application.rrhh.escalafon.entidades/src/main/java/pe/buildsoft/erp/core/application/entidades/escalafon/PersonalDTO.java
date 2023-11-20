package pe.buildsoft.erp.core.application.entidades.escalafon;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.common.UbigeoDTO;
import pe.buildsoft.erp.core.application.entidades.security.EntidadDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class PersonalDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 23:29:56 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class PersonalDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id personal. */
    private String idPersonal;
   
    /** El codigo unico. */
    private String codigoUnico;
   
    /** El estado. */
    private String estado;
   
    /** El item by doc identidad. */
    private Long idItemByDocIdentidad;
    private ItemDTO itemByDocIdentidad;
   
    /** El nro doc. */
    private String nroDoc;
   
    /** El apellido paterno. */
    private String apellidoPaterno;
   
    /** El apellido materno. */
    private String apellidoMaterno;
   
    /** El nombres. */
    private String nombres;
   
    /** El item by estado civil. */
    private Long idItemByEstadoCivil;
    private ItemDTO itemByEstadoCivil;
   
    /** El fecha nacimiento. */
    private OffsetDateTime fechaNacimiento;
   
    /** El telefono1. */
    private String telefono1;
   
    /** El telefono2. */
    private String telefono2;
   
    /** El celular. */
    private String celular;
   
    /** El email. */
    private String email;
   
    /** El codigo control. */
    private String codigoControl;
   
    /** El nro tarjeta. */
    private String nroTarjeta;
   
    /** El sexo. */
    private String sexo;
   
    /** El item bypais emisor. */
    private Long idItemByPaisEmisor;
    private ItemDTO itemByPaisEmisor;
   
    /** El lugar nacimiento. */
    private String idLugarNacimiento;
    private UbigeoDTO lugarNacimiento;
   
    /** El item by nacionalidad. */
    private Long idItemByNacionalidad;
    private ItemDTO itemByNacionalidad;
   
    /** El foto. */
    private String foto;
   
    /** El firma. */
    private String firma;
   
    /** El item by situacion. */
    private Long idItemBySituacion;
    private ItemDTO itemBySituacion;
   
    /** El item byafp. */
    private Long idItemByAfp;
    private ItemDTO itemByAfp;
   
    /** El fecha ingresospp. */
    private OffsetDateTime fechaIngresospp;
   
    /** El fecha ingresoonp. */
    private OffsetDateTime fechaIngresoonp;
   
    /** El cuspp. */
    private String cuspp;
   
    /** El es comision mixta. */
    private String esComisionMixta;
   
    /** El aporta senati. */
    private String aportaSenati;
   
    /** El item by regimen laboral. */
    private Long idItemByRegimenLaboral;
    private ItemDTO itemByRegimenLaboral;
   
    /** El item by regimen pensionario. */
    private Long idItemByRegimenPensionario;
    private ItemDTO itemByRegimenPensionario;
   
    /** El es afiliacion asegura tu pension. */
    private String esAfiliacionAseguraTuPension;
   
    /** El item by convenio evitar doble tributacion. */
    private Long idItemByConvenioEvitarDobleTributacion;
    private ItemDTO itemByConvenioEvitarDobleTributacion;
   
    /** El ruc cas. */
    private String rucCas;
   
    /** El auto generado essalud. */
    private String autoGeneradoEssalud;
   
    /** El item by categoria trabajador. */
    private Long idItemByCategoriaTrabajador;
    private ItemDTO itemByCategoriaTrabajador;
   
    /** El empleador destaca personal tercero. */
    private Long idEmpleadorDestacaPersonalTercero;
    private EmpresaDTO empleadorDestacaPersonalTercero;
   
    /** El es salud vida. */
    private String esSaludVida;
   
    /** El es trabajador sindicalizado. */
    private String esTrabajadorSindicalizado;
   
    /** El sujeto controlnmediato. */
    private String sujetoControlnmediato;
   
    /** El regimen alter acumula atipico. */
    private String regimenAlterAcumulaAtipico;
   
    /** El con asignacion familiar. */
    private String conAsignacionFamiliar;
   
    /** El sujeto horario nocturno. */
    private String sujetoHorarioNocturno;
   
    /** El jornada trabajo maxima. */
    private String jornadaTrabajoMaxima;
   
    /** El item by sctr salud. */
    private Long idItemBySctrSalud;
    private ItemDTO itemBySctrSalud;
   
    /** El item by regimen aseg salud. */
    private Long idItemByRegimenAsegSalud;
    private ItemDTO itemByRegimenAsegSalud;
   
    /** El item by sctr pension. */
    private Long idItemBySctrPension;
    private ItemDTO itemBySctrPension;
   
    /** El item by eps. */
    private Long idItemByEps;
    private ItemDTO itemByEps;
   
    /** El item by servico medico. */
    private Long idItemByServicoMedico;
    private ItemDTO itemByServicoMedico;
   
    /** El item by periocidad ingreso. */
    private Long idItemByPeriocidadIngreso;
    private ItemDTO itemByPeriocidadIngreso;
   
    /** El item by tipo pago. */
    private Long idItemByTipoPago;
    private ItemDTO itemByTipoPago;
   
    /** El renta5ta exonerada e inafecta. */
    private String renta5taExoneradaEInafecta;
   
    /** El renta quinta manual. */
    private String rentaQuintaManual;
   
    /** El item by situacion especial. */
    private Long idItemBySituacionEspecial;
    private ItemDTO itemBySituacionEspecial;
   
    /** El item by categoria ocupacional. */
    private Long idItemByCategoriaOcupacional;
    private ItemDTO itemByCategoriaOcupacional;
   
    /** El item by ocupacion. */
    private Long idItemByOcupacion;
    private ItemDTO itemByOcupacion;
   
    /** El item by ocupacion regimen publico. */
    private Long idItemByOcupacionRegimenPublico;
    private ItemDTO itemByOcupacionRegimenPublico;
   
    /** El discapacidad. */
    private String discapacidad;
   
    /** El es pencionista. */
    private String esPencionista;
   
    /** El item by tipo pension. */
    private Long idItemByTipoPension;
    private ItemDTO itemByTipoPension;
   
    /** El item by regimen pensionario pension. */
    private Long idItemByRegimenPensionarioPension;
    private ItemDTO itemByRegimenPensionarioPension;
   
    /** El fecha inscripcion reg pensionario. */
    private OffsetDateTime fechaInscripcionRegPensionario;
   
    /** El cuspp pension. */
    private String cusppPension;
   
    /** El item by tipo pago pension. */
    private Long idItemByTipoPagoPension;
    private ItemDTO itemByTipoPagoPension;
   
    /** El informa otros ingreso5ta categoria. */
    private String informaOtrosIngreso5taCategoria;
   
    /** El es practicante. */
    private String esPracticante;
   
    /** El madre responsa famliar. */
    private String madreResponsaFamliar;
   
    private ItemDTO itemByTipoCentroFormacionProfesional;
   
    /** El item by tipo modalidad formativa. */
    private Long idItemByTipoModalidadFormativa;
    private ItemDTO itemByTipoModalidadFormativa;
   
    /** El vencimiento fotocheck. */
    private OffsetDateTime vencimientoFotocheck;
   
    /** El item by situacion educativa. */
    private Long idItemBySituacionEducativa;
    private ItemDTO itemBySituacionEducativa;
   
    /** El es educacion completa ins educativa peru. */
    private String esEducacionCompletaInsEducativaPeru;
   
    /** El carrera. */
    private CarreraDTO carrera;
   
    /** El anho egreso. */
    private Long anhoEgreso;
   
    /** El entidad. */
    private String idEntidad;
    private EntidadDTO entidad;
   
    /** El sede. */
    private String sede;
    
    /** El item by cargo. */
    private Long  idItemByCargo;
    private ItemDTO itemByCargo;
   
    /** El item area. */
    private Long  idItemByArea;
    private ItemDTO itemByArea;
   
    /** El fecha ingreso. */
	private OffsetDateTime fechaIngreso;

	/** El fecha cese. */
	private OffsetDateTime fechaCese;
	
	 /** El remineracion. */
    private BigDecimal remuneracion;
    
    /** El asignacion familiar. */
    private BigDecimal asignacionFamiliar;
    private Integer cantAfiliados;

    
    private DireccionPersonalDTO direccion1;
    private DireccionPersonalDTO direccion2;
    private CuentaBancariaPersonalDTO cuentaBancariaCts;
    /** El item by tipo centro formacion profesional. */
	private Long idItemByTipoCentroFormacionProfesional;

	private CuentaBancariaPersonalDTO cuentaBancariaPago;
    
   // private List<ConceptoCaluldadosVO> listaIngreso = new ArrayList<>();
   // private List<ConceptoCaluldadosVO> listaDescuento = new ArrayList<>();
   // private List<ConceptoCaluldadosVO> listaAporteTrabajador = new ArrayList<>();
   // private List<ConceptoCaluldadosVO> listaAporteEmpleador = new ArrayList<>();
    
    //new 
    private String usuarioSession;
    
    /**
     * Instancia un nuevo personalDTO.
     */
    public PersonalDTO() {
		super();
    }

	/* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idPersonal == null) ? 0 : idPersonal.hashCode());
        return result;
    }

    /* (non-Javadoc)
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
        PersonalDTO other = (PersonalDTO) obj;
        if (idPersonal == null) {
            if (other.idPersonal != null) {
                return false;
            }
        } else if (!idPersonal.equals(other.idPersonal)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PersonalDTO [idPersonal=" + idPersonal + "]";
    }
   
}