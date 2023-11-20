package pe.buildsoft.erp.core.domain.util;

/**
 * La Class ConfiguracionJMSUtil.
 * <ul>
 * <li>Copyright 2014 MAPFRE - mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Fri Apr 25 18:56:23 COT 2014
 * @since Rep v1.0
 */
public final class ConfiguracionJMSUtil {

	// Fabrica de conexion

	/** La Constante QCF_NAME. */
	public static final String QCF_NAME = "bsConnectionFactory";

	/** La Constante QCF_TRAMA_CONTROL_NAME. */
	public static final String QCF_CORREO_NAME = "bsCorreoConnectionFactory";

	/** La Constante QCF_TRAMA_CONTROL_NAME. */
	public static final String QCF_TRAMA_CONTROL_NAME = "bsTramaConnectionFactory";

	/** La Constante QCF_TRAMA_FTP_CONTROL_NAME. */
	public static final String QCF_TRAMA_FTP_CONTROL_NAME = "bsTramaFTPConnectionFactory";

	/** La Constante QCF_REPORTE_PREFERENCIAL_NAME. */
	public static final String QCF_REPORTE_PREFERENCIAL_NAME = "bsPreferencialConnectionFactory";

	/** La Constante QCF_REPORTE_REGULAR_NAME. */
	public static final String QCF_REPORTE_REGULAR_NAME = "bsRegularConnectionFactory";

	/** La Constante QCF_REPORTE_PESADO_NAME. */
	public static final String QCF_REPORTE_PESADO_NAME = "bsPesadoConnectionFactory";

	/** La Constante QCF_REPORTE_NOCTURNO_NAME. */
	public static final String QCF_REPORTE_NOCTURNO_NAME = "bsNocturnoConnectionFactory";

	/** La Constante QCF_REPORTE_PESADO_NAME. */
	public static final String QCF_REPORTE_HEAVY_NAME = "bsHeavyConnectionFactory";


	// Inicio Gestor de colas

	/** La Constante QUEUE_NAME. */
	public static final String QUEUE_NAME = "jms/queue/bsQueue";

	/** La Constante QUEUE_TRAMA_CONTROL_NAME. */
	public static final String QUEUE_CORREO_NAME = "queue/bsCorreoQueue";

	/** La Constante QUEUE_TRAMA_CONTROL_NAME. */
	public static final String QUEUE_TRAMA_CONTROL_NAME = "queue/bsTramaQueue";

	/** La Constante QUEUE_TRAMA_FTP_CONTROL_NAME. */
	public static final String QUEUE_TRAMA_FTP_CONTROL_NAME = "queue/bsTramaFTPQueue";

	/** La Constante QUEUE_REPORTE_PREFERENCIAL_NAME. */
	public static final String QUEUE_REPORTE_PREFERENCIAL_NAME = "queue/bsPreferencialQueue";

	/** La Constante QUEUE_REPORTE_REGULAR_NAME. */
	public static final String QUEUE_REPORTE_REGULAR_NAME = "queue/bsRegularQueue";

	/** La Constante QUEUE_REPORTE_HEAVY_NAME. */
	public static final String QUEUE_REPORTE_PESADO_NAME = "queue/bsPesadoQueue";

	/** La Constante QUEUE_REPORTE_NOCTURNO_NAME. */
	public static final String QUEUE_REPORTE_NOCTURNO_NAME = "queue/bsNocturnoQueue";

	/** La Constante QUEUE_REPORTE_HEAVY_NAME. */
	public static final String QUEUE_REPORTE_HEAVY_NAME = "queue/bsHeavyQueue";

	/** La Constante QUEUE_NAME. */
	public static final String TRANSACCTION_TIMEOUT = "86400";

	
	public static final String MAXIMA_INSTANCIA_COLA = "100";// 5 = para pruebas 15 = normal

	// Fin Gestor de colas

	/**
	 * Instancia un nuevo configuracion jms util.
	 */
	private ConfiguracionJMSUtil() {

	}
}
