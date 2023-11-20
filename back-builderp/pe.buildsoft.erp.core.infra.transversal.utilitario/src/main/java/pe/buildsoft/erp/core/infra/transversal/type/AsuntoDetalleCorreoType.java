package pe.buildsoft.erp.core.infra.transversal.type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
* La Enum TipoDetalleCorreoType.
* <ul>
* <li>Copyright 2014 MAPFRE -
* MAPFRE. Todos los derechos reservados.</li>
* </ul>
*
* @author BuildSoft.
* @version 1.0, Wed Jul 30 17:20:50 COT 2014
* @since BuildErp 1.0
*/
public enum AsuntoDetalleCorreoType {

	 /** El ASUNTO_REPORTE. */
	ASUNTO_PROCESO("R","correoProcesoEjecutado.asunto","correoProcesoTerminado.ftl"),
	 
    /**  El ASUNTO_PROCESO. */
	 ASUNTO_REPORTE("P","correoReporteEjecutado.asunto","correoReporteTerminado.ftl"),
	 
	/** La asunto proceso trama. */
	ASUNTO_PROCESO_TRAMA("PT","correoReporteEjecutado.asunto","correoProcesoTramaTerminado.ftl"),
	
	
	//Inicio BUIDSOFT 01 Requerimiento telecobranza pendiente
	/** La ASUNTO_PROCESO_TELECOBRANZA_PENDIENTE. */
	ASUNTO_PROCESO_TELECOBRANZA_PENDIENTE("RTP","correoReporeteTelecobranzaPendiente.asunto","correoReporteTelecobranzaPendienteTerminado.ftl"),
	
	//Funeraria Video
	ASUNTO_MENSAJE_FUNERARIA_VIDEO("MFV","correoFunerariaVideo.asunto","correoCuerpoFunerariaVideo.ftl"),
	
	//Camote Pagos Diarios 
	ASUNTO_MENSAJE_PAGOS_DIARIOS("MPD","correoPagoDiario.asunto","correoCuerpoCamotePagoDiario.ftl"),
	
	//Camote Cambio Comunidad 
	ASUNTO_MENSAJE_CAMBIO_COMUNIDAD("MCC","cambioComunidad.asunto","correoCuerpoCamoteCambioComunidad.ftl"),
	
	//Camote Alerta Comunidad 
	ASUNTO_MENSAJE_ALERTA_COMUNIDAD("MA","alertaComunidad.asunto","correoCuerpoCamoteAlertaComunidad.ftl"),
	
	ASUNTO_PROCESO_TELECOBRANZA_RESULTADO("RTR","correoReporeteTelecobranzaResultado.asunto","correoReporteTelecobranzaResultadoTerminado.ftl"),
	//Fin BUIDSOFT 01 Requerimiento telecobranza pendiente
	
	ASUNTO_MENSAJE_JENKINS("MJ","correoProcesoJenkins.asunto","correoMensajeJenkins.ftl"),
	
		// Inicio de requerimiento 80 certificacion 
	ASUNTO_MENSAJE_CERTIFICACION_APROBADO("MCA","correoCertificacionAprobado.asunto","correoCerAprobado.ftl"),
	ASUNTO_MENSAJE_CERTIFICACION_RECHAZADO("MCR","correoCertificacionRechazado.asunto","correoCerRechazado.ftl");
	
	/** La Constante LOO_KUP_MAP. */
	private static final Map<String, AsuntoDetalleCorreoType> LOO_KUP_MAP = new HashMap<>();
	
	static {
		for (var s : EnumSet.allOf(AsuntoDetalleCorreoType.class)) {
			LOO_KUP_MAP.put(s.getKey(), s);
		}
	}

	/** El key. */
	private String key;
	
	/** El value. */
	private String value;
	
	/** La template. */
	private String template;


	/**
	 * Instancia un nuevo asunto detalle correo type.
	 *
	 * @param key el key
	 * @param value el value
	 * @param template el template
	 */
	private AsuntoDetalleCorreoType(String key, String value,String template) {
		this.key = key;
		this.value = value;
		this.template = template;
	}
	
	/**
	 * Gets the.
	 *
	 * @param key el key
	 * @return the accion type
	 */
	public static AsuntoDetalleCorreoType get(String key) {
		return LOO_KUP_MAP.get(key);
	}

	/**
	 * Obtiene key.
	 *
	 * @return key
	 */
	public String getKey() {
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
	
	/**
	 * Obtiene template.
	 *
	 * @return template
	 */
	public String getTemplate() {
		return template;
	}
}
