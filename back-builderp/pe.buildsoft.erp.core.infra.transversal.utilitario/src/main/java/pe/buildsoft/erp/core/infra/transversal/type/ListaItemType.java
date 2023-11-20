package pe.buildsoft.erp.core.infra.transversal.type;

/**
 * <ul>
 * <li>Copyright 2011 SIATEMA INTEGRAL ACADEMICO Y ADMINISTRATIVO - SIAA. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Enum ListaItemType.
 *
 * @author ndavila and leperiat.
 * @version 1.0 , 18/03/2012
 * @since SIAA 2.0
 */
public enum ListaItemType {
	
	/** El CICLO. */
	CICLO(1L, "Ciclo"),
	
	/** El ESTADO_CIVIL. */
	ESTADO_CIVIL(2L, "Estado Civil"),
	
	/** La CONDICION_LABORAL. */
	CONDICION_LABORAL(3L, "CondicionLaboral") ,
	
	/** El TIPO_DOC_INDENTIDAD. */
	TIPO_DOC_INDENTIDAD(4L,	"Tipo Documento de Indentidad") ,
	
	/** El DIA. */
	DIA(5L, "Dia"),
	
	/** La MODALIDAD_INGRESO. */
	MODALIDAD_INGRESO(6L, "ModalidadIngreso") ,
	
	/** El TURNO. */
	TURNO(7L, "Turno"	) ,
	
	/** El TIPO_DOCUMENTO_REQUERIDO. */
	TIPO_DOCUMENTO_REQUERIDO(8L, "TipoDocumentoRequerido") ,
	
	/** El TIPO_COLEGIO. */
	TIPO_COLEGIO(9L	, "TipoColegio"	) ,
	
	/** El TIPO_PREPARACION. */
	TIPO_PREPARACION(10L, "TipoPreparacion") ,
	
	/** La CLASIFICACION_CONVENIO. */
	CLASIFICACION_CONVENIO(11L,	"ClasificacionConvenio") ,
	
	/** El TIPO_CONTRATO. */
	TIPO_CONTRATO(12L, "TipoContrato") ,
	
	/** La TIP o_ trabajador. */
	TIPO_TRABAJADOR(13L, "TipoTrabajador") ,
	
	/** El GRADO_INSTRUCCION. */
	GRADO_INSTRUCCION(14L, "GradoInstruccion") ,
	
	/** El TIPO_OCUPACION_UNIVERSITARIA. */
	TIPO_OCUPACION_UNIVERSITARIA(15L, "TipoOcupacionUniversitaria") ,
	
	/** La DEDICACION_DOCENTE. */
	DEDICACION_DOCENTE(16L, "DedicacionDocente") ,
	
	/** La NIVEL_EDUCATIVO. */
	NIVEL_EDUCATIVO(17L, "NivelEducativo") ,
	
	/** La CATEGORIA_DOCENTE. */
	CATEGORIA_DOCENTE(18L, "CategoriaDocente") ,
	
	/** El AREA_CONOCIMIENTO. */
	AREA_CONOCIMIENTO(19L, "AreaConocimiento") ,
	
	/** La CATEGORIA_ALUMNO. */
	CATEGORIA_ALUMNO(20L, "CategoriaAlumno") ,
	
	/** El TIPO_ESPECIALIDAD. */
	TIPO_ESPECIALIDAD(21L, "TipoEspecialidad") ,
	
	/** El TIPO_DOCUMENTO. */
	TIPO_DOCUMENTO(22L, "TipoDocumento") ,
	
	/** El TIPO_ENTIDAD_DUNIVERSITARIA. */
	TIPO_ENTIDAD_DUNIVERSITARIA(23L, "TipoEntidadUniversitaria") ,
	
	/** La FACE_INVESTIGACION. */
	FACE_INVESTIGACION(24L,"FaceInvestigacion") ,
	
	/** El ROL_INVESTIGADOR. */
	ROL_INVESTIGADOR(25L, "RolInvestigador") ,
	
	/** El TIPO_INVESTIGACION. */
	TIPO_INVESTIGACION(26L, "TipoInvestigacion") ,
	
	/** El TIPO_INFRAESTRUCTURA. */
	TIPO_INFRAESTRUCTURA(27L, "TipoInfraestructura") ,
	
	/** El TIPO_CLASIFICACION. */
	TIPO_CLASIFICACION(28L, "TipoClasificacion") ,
	
	/** El CLASE_ESPECIALIDAD. */
	CLASE_ESPECIALIDAD(29L, "ClaseEspecialidad") ,
	
	/** El MOTIVO_NO_DICTADO_DOCENTE. */
	MOTIVO_NO_DICTADO_DOCENTE(30L , "MotivoNoDictadoDocente") ,
	
	/** La RELEVANCIA_INVESTIGACION. */
	RELEVANCIA_INVESTIGACION(31L , "RelevanciaInvestigacion") ,
	
	/** El TIPO_DURACION_ACADEMICA. */
	TIPO_DURACION_ACADEMICA(32L , "TipoDuracionAcademica") ,
	
	/** El TIPO_PUBLICACION. */
	TIPO_PUBLICACION(33L, "TipoPublicacion"),
	
	/** El TIPO_VIA. */
	TIPO_VIA(34L, "TipoVia"),
	
	/** LA ZONA. */
	ZONA(35L, "Zona"),
	
	/** El IDIOMA. */
	IDIOMA(36L, "Idioma"),
	
	/** El TIPO_CENTRO_FORMACION_PROFESIONAL. */
	TIPO_CENTRO_FORMACION_PROFESIONAL(37L, "TipoCentroFormacionProfesional"),
	
	/** El TIPO_NOTA. */
	TIPO_NOTA(38L, "TipoNota"),
	
	/** El TIPO_MONEDA. */
	TIPO_MONEDA(39L, "TipoMoneda"),
	
	/** El LENGUAJE. */
	LENGUAJE(40L, "Lenguaje"),
	
	/** El LENGUAJE. */
	NOTA_LETRA(384L, "NOTA LETRA"),
	
	/** El COMPONENTE. */
	COMPONENTE(41L, "Componente");
	
	
	/** El key. */
	private Long key;
	
	/** El value. */
	private String value;
	
	/**
	 * Instancia un nuevo lista item type.
	 *
	 * @param key el key
	 * @param value el value
	 */
	private ListaItemType(Long key, String value) {
		this.key = key;
		this.value = value;
	}
   
	/**
	 * Obtiene key.
	 *
	 * @return key
	 */
	public Long getKey() {
		return key;
	}
	
	/**
	 * Obtiene value.
	 *
	 * @return value
	 */
	public String getValue() {
		return value;
	}
	
}