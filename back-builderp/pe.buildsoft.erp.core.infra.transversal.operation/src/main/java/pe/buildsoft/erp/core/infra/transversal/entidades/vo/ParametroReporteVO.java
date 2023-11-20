package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.sql.Connection;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.type.RutaReporteType;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class ParametroReporteVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 06/04/2015
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class ParametroReporteVO  extends CabeceraReporteVO implements Serializable {
	
	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** La parametros. */
	private Map<String, Object> parametros;
	
	/** La lista. */
	private List<?> lista;
	
	/** La jasper. */
	private String jasper;
	
	/** La jasper file. */
	private String jasperFile;
	
	/** La subreportes. */
	private String[] subreportes;
	
	/** La ruta reporte type. */
	private RutaReporteType rutaReporteType;
	
	/** La is bean. */
	private boolean isBean;
	
	/** La cn. */
	private Connection cn;
	
	/** La ruta reportes absoluto. */
	private String rutaReportesAbsoluto;
	
	/** La formato. */
	private String formato;
	
	/** La file name. */
	private String fileName;
	
	/** La crear archivo. */
	private boolean crearArchivo = false;
	
	/** La is online. */
	private boolean isOnline = false;
	
	/** La user name. */
	private String userName = "";
	
	/** La fecha generacion. */
	private OffsetDateTime fechaGeneracion;
	
	/** La criterio busqueda. */
	private Object criterioBusqueda;
	
	/** La big memory. */
	private boolean bigMemory = false;
	
	/** La requiere conexion. */
	private String nombreJNDIConexion; 
		
	/**  SWFACTORY INICIO*. */
	private Boolean withParameters;
	
	/** La tipo proceso. */
	private String tipoProceso;
	
	/** La id component map. */
	private Map<String, String>  idComponentMap = new HashMap<>();
	
	private boolean esMBDData = false;
	private String codigoServicio = null;
	
	private boolean esCopiaCorreo;
	
	/**
	 * Instancia un nuevo parametro reporte vo.
	 */
	public ParametroReporteVO() {
		super();
	}


	/**
	 * Instancia un nuevo parametro reporte vo.
	 *
	 * @param parametros el parametros
	 * @param lista el lista
	 * @param jasperFile el jasper file
	 * @param subreportes el subreportes
	 * @param rutaReporteType el ruta reporte type
	 * @param isBean el is bean
	 */
	public ParametroReporteVO(Map<String, Object> parametros, List<?> lista,
			String jasperFile, String[] subreportes,
			RutaReporteType rutaReporteType, boolean isBean) {
		super();
		this.parametros = parametros;
		this.lista = lista;
		this.jasperFile = jasperFile;
		this.subreportes = subreportes;
		this.rutaReporteType = rutaReporteType;
		this.isBean = isBean;
	}
	
	/**
	 * Instancia un nuevo parametro reporte vo.
	 *
	 * @param parametros el parametros
	 * @param lista el lista
	 * @param jasperFile el jasper file
	 * @param subreportes el subreportes
	 * @param rutaReporteType el ruta reporte type
	 * @param isBean el is bean
	 * @param cn el cn
	 */
	public ParametroReporteVO(Map<String, Object> parametros, List<?> lista,
			String jasperFile, String[] subreportes,
			RutaReporteType rutaReporteType, boolean isBean,Connection cn) {
		super();
		this.parametros = parametros;
		this.lista = lista;
		this.jasperFile = jasperFile;
		this.subreportes = subreportes;
		this.rutaReporteType = rutaReporteType;
		this.isBean = isBean;
		this.cn = cn;
	}
	
	
	
	/**
	 * Instancia un nuevo parametro reporte vo.
	 *
	 * @param parametros el parametros
	 * @param lista el lista
	 * @param jasperFile el jasper file
	 * @param subreportes el subreportes
	 * @param rutaReporteType el ruta reporte type
	 * @param isBean el is bean
	 * @param formato el formato
	 * @param fileName el file name
	 */
	public ParametroReporteVO(Map<String, Object> parametros, List<?> lista,String jasper,
			String jasperFile,boolean isBean, String formato,
			String fileName) {
		super();
		this.parametros = parametros;
		this.lista = lista;
		this.jasper = jasper;
		this.jasperFile = jasperFile;
		this.isBean = isBean;
		this.formato = formato;
		this.fileName = fileName;
		if ( this.subreportes == null) {
			this.subreportes = new String[0];
		}
	}
	
}