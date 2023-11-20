import { Carrera } from "../carrera/carrera.types";

/**
 * La Class Personal.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
export interface Personal {

  /** El id personal. */
  idPersonal: string;

  /** El codigo unico. */
  codigoUnico: string;

  /** El estado. */
  estado: string;

  /** El item by doc identidad. */
  idItemByDocIdentidad: number
  itemByDocIdentidad: any;

  /** El nro doc. */
  nroDoc: string;

  /** El apellido paterno. */
  apellidoPaterno: string;

  /** El apellido materno. */
  apellidoMaterno: string;

  /** El nombres. */
  nombres: string;

  /** El item by estado civil. */
  idItemByEstadoCivil: number;
  itemByEstadoCivil: any;

  /** El fecha nacimiento. */
  fechaNacimiento: Date;

  /** El telefono1. */
  telefono1: string;

  /** El telefono2. */
  telefono2: string;

  /** El celular. */
  celular: string;

  /** El email. */
  email: string;

  /** El codigo control. */
  codigoControl: string;

  /** El nro tarjeta. */
  nroTarjeta: string;

  /** El sexo. */
  sexo: string;

  /** El item by pais emisor. */
  idItemByPaisEmisor: number;
  itemByPaisEmisor: any;

  /** El lugar nacimiento. */
  idLugarNacimiento: string;
  lugarNacimiento: any;

  /** El item by nacionalidad. */
  idItemByNacionalidad: number;
  itemByNacionalidad: any;

  /** El foto. */
  foto: string;

  /** El firma. */
  firma: string;

  /** El item by situacion. */
  idItemBySituacion: number;
  itemBySituacion: any;

  /** El item by afp. */
  idItemByAfp: number;
  itemByAfp: any;

  /** El fecha ingresospp. */
  fechaIngresospp: Date;

  /** El fecha ingresoonp. */
  fechaIngresoonp: Date;

  /** El cuspp. */
  cuspp: string;

  /** El es comision mixta. */
  esComisionMixta: string;

  /** El aporta senati. */
  aportaSenati: string;

  /** El item by regimen laboral. */
  idItemByRegimenLaboral: number;
  itemByRegimenLaboral: any;

  /** El item by regimen pensionario. */
  idItemByRegimenPensionario: number;
  itemByRegimenPensionario: any;

  /** El es afiliacion asegura tu pension. */
  esAfiliacionAseguraTuPension: string;

  /** El item by convenio evitar doble tributacion. */
  idItemByConvenioEvitarDobleTributacion: number;
  itemByConvenioEvitarDobleTributacion: any;

  /** El ruc cas. */
  rucCas: string;

  /** El auto generado essalud. */
  autoGeneradoEssalud: string;

  /** El item by categoria trabajador. */
  idItemByCategoriaTrabajador: number;
  itemByCategoriaTrabajador: any;

  /** El empleador destaca personal tercero. */
  idEmpleadorDestacaPersonalTercero: number;
  empleadorDestacaPersonalTercero: any;

  /** El es salud vida. */
  esSaludVida: string;

  /** El es trabajador sindicalizado. */
  esTrabajadorSindicalizado: string;

  /** El sujeto controlnmediato. */
  sujetoControlnmediato: string;

  /** El regimen alter acumula atipico. */
  regimenAlterAcumulaAtipico: string;

  /** El con asignacion familiar. */
  conAsignacionFamiliar: string;

  /** El sujeto horario nocturno. */
  sujetoHorarioNocturno: string;

  /** El jornada trabajo maxima. */
  jornadaTrabajoMaxima: string;

  /** El item by sctr salud. */
  idItemBySctrSalud: number;
  itemBySctrSalud: any;

  /** El item by regimen aseg salud. */
  idItemByRegimenAsegSalud: number;
  itemByRegimenAsegSalud: any;

  /** El item by sctr pension. */
  idItemBySctrPension: number;
  itemBySctrPension: any;

  /** El item by eps. */
  idItemByEps: number;
  itemByEps: any;

  /** El item by servico medico. */
  idItemByServicoMedico: number;
  itemByServicoMedico: any;

  /** El item by periocidad ingreso. */
  idItemByPeriocidadIngreso: number;
  itemByPeriocidadIngreso: any;

  /** El item by tipo pago. */
  idItemByTipoPago: number;
  itemByTipoPago: any;

  /** El renta5ta exonerada e inafecta. */
  renta5taExoneradaEInafecta: string;

  /** El renta quinta manual. */
  rentaQuintaManual: string;

  /** El item by situacion especial. */
  idItemBySituacionEspecial: number;
  itemBySituacionEspecial: any;

  /** El item by categoria ocupacional. */
  idItemByCategoriaOcupacional: number;
  itemByCategoriaOcupacional: any;

  /** El item by ocupacion. */
  idItemByOcupacion: number;
  itemByOcupacion: any;

  /** El item by ocupacion regimen publico. */
  idItemByOcupacionRegimenPublico: number;
  itemByOcupacionRegimenPublico: any;

  /** El discapacidad. */
  discapacidad: string;

  /** El es pencionista. */
  esPencionista: string;

  /** El item by tipo pension. */
  idItemByTipoPension: number;
  itemByTipoPension: any;

  /** El item by regimen pensionario pension. */
  idItemByRegimenPensionarioPension: number;
  itemByRegimenPensionarioPension: any;

  /** El fecha inscripcion reg pensionario. */
  fechaInscripcionRegPensionario: Date;

  /** El cuspp pension. */
  cusppPension: string;

  /** El item by tipo pago pension. */
  idItemByTipoPagoPension: number;
  itemByTipoPagoPension: any;

  /** El informa otros ingreso5ta categoria. */
  informaOtrosIngreso5taCategoria: string;

  /** El es practicante. */
  esPracticante: string;

  /** El madre responsa famliar. */
  madreResponsaFamliar: string;

  /** El item by tipo centro formacion profesional. */
  idItemByTipoCentroFormacionProfesional: number;
  itemByTipoCentroFormacionProfesional: any;

  /** El item by tipo modalidad formativa. */
  idItemByTipoModalidadFormativa: number;
  itemByTipoModalidadFormativa: any;

  /** El vencimiento fotocheck. */
  vencimientoFotocheck: Date;

  /** El item by situacion educativa. */
  idItemBySituacionEducativa: number;
  itemBySituacionEducativa: any;

  /** El es educacion completa ins educativa peru. */
  esEducacionCompletaInsEducativaPeru: string;

  /** El carrera. */
  carrera: Carrera;

  /** El anho egreso. */
  anhoEgreso: number;

  /** El entidad. */
  idEntidad: string;

  /** El sede. */
  sede: string;

  /** El item by cargo. */
  idItemByCargo: number;
  itemByCargo: any;

  /** El item area. */
  idItemByArea: number;
  itemByArea: any;

  /** El fecha ingreso. */
  fechaIngreso: Date;

  /** El fecha cese. */
  fechaCese: Date;

  remuneracion: number;
  asignacionFamiliar: number;
  presAlimentarias: number;

  direccion1: any;
  direccion2: any;

  cuentaBancariaPago: any;
  cuentaBancariaCts: any;

  /**Campos agregados */

  descripcionView: string;
}