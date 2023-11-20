package pe.buildsoft.erp.core.infra.data.webclient.rest.aas;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
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
public class CobranzaVo extends BaseEntidad implements Serializable {
 
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
   
    /** El lugar nacimiento. */
    private String idLugarNacimiento;
   
    /** El item by nacionalidad. */
    private Long idItemByNacionalidad;
   
    /** El foto. */
    private String foto;
   
    /** El firma. */
    private String firma;
   
    /** El item by situacion. */
    private Long idItemBySituacion;
   
    /** El item byafp. */
    private Long idItemByAfp;
   
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
   
    /** El item by regimen pensionario. */
    private Long idItemByRegimenPensionario;
   
    /** El es afiliacion asegura tu pension. */
    private String esAfiliacionAseguraTuPension;
   
    /** El item by convenio evitar doble tributacion. */
    private Long idItemByConvenioEvitarDobleTributacion;
   
    /** El ruc cas. */
    private String rucCas;
   
    /** El auto generado essalud. */
    private String autoGeneradoEssalud;
   
    /** El item by categoria trabajador. */
    private Long idItemByCategoriaTrabajador;
   
    /** El empleador destaca personal tercero. */
    private Long idEmpleadorDestacaPersonalTercero;
   
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
   
    /** El item by regimen aseg salud. */
    private Long idItemByRegimenAsegSalud;
   
    /** El item by sctr pension. */
    private Long idItemBySctrPension;
   
    /** El item by eps. */
    private Long idItemByEps;
   
    /** El item by servico medico. */
    private Long idItemByServicoMedico;
   
    /** El item by periocidad ingreso. */
    private Long idItemByPeriocidadIngreso;
   
    /** El item by tipo pago. */
    private Long idItemByTipoPago;
   
    /** El renta5ta exonerada e inafecta. */
    private String renta5taExoneradaEInafecta;
   
    /** El renta quinta manual. */
    private String rentaQuintaManual;
   
    /** El item by situacion especial. */
    private Long idItemBySituacionEspecial;
   
    /** El item by categoria ocupacional. */
    private Long idItemByCategoriaOcupacional;
   
    /** El item by ocupacion. */
    private Long idItemByOcupacion;
   
    /** El item by ocupacion regimen publico. */
    private Long idItemByOcupacionRegimenPublico;
   
    /** El discapacidad. */
    private String discapacidad;
   
    /** El es pencionista. */
    private String esPencionista;
   
    /** El item by tipo pension. */
    private Long idItemByTipoPension;
   
    /** El item by regimen pensionario pension. */
    private Long idItemByRegimenPensionarioPension;
   
    /** El fecha inscripcion reg pensionario. */
    private OffsetDateTime fechaInscripcionRegPensionario;
   
    /** El cuspp pension. */
    private String cusppPension;
   
    /** El item by tipo pago pension. */
    private Long idItemByTipoPagoPension;
   
    /** El informa otros ingreso5ta categoria. */
    private String informaOtrosIngreso5taCategoria;
   
    /** El es practicante. */
    private String esPracticante;
   
    /** El madre responsa famliar. */
    private String madreResponsaFamliar;
   
    /** El item by tipo centro formacion profesional. */
    private Long idItemByTipoCentroFormacionProfesional;
   
    /** El item by tipo modalidad formativa. */
    private Long idItemByTipoModalidadFormativa;
   
    /** El vencimiento fotocheck. */
    private OffsetDateTime vencimientoFotocheck;
   
    /** El item by situacion educativa. */
    private Long idItemBySituacionEducativa;
   
    /** El es educacion completa ins educativa peru. */
    private String esEducacionCompletaInsEducativaPeru;
   
    /** El carrera. */
   // private CarreraDTO carrera;
   
    /** El anho egreso. */
    private Long anhoEgreso;
   
    /** El entidad. */
    private String idEntidad;
   
    /** El sede. */
    private String sede;
   
    
    //new 
    private String usuarioSession;
    
    /**
     * Instancia un nuevo personalDTO.
     */
    public CobranzaVo() {
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
        CobranzaVo other = (CobranzaVo) obj;
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