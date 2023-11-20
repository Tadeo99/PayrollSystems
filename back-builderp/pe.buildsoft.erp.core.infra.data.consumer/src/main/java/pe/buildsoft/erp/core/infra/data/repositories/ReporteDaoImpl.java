package pe.buildsoft.erp.core.infra.data.repositories;

import java.sql.Connection;
import java.util.Locale;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import pe.buildsoft.erp.core.domain.interfaces.repositories.ReporteDaoLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ParametroReporteVO;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.jasper.AdministradorReportes;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class ReporteDaoImpl.
 *
 * @author BuildSoft.
 * @version 1.0 , 06/04/2015
 * @since BuildErp 1.0
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ReporteDaoImpl implements ReporteDaoLocal {

	  /** La Constante EN. */
    private static final Locale EN = new Locale("en", "US");
    
    /** La Constante ES. */
    private static final Locale ES = new Locale("es", "PE");
    
	/**
	 *  La entity manager.
	 *
	 * @return conexion ds
	 * @throws Exception the exception
	 */
	//@PersistenceContext(unitName = ConfiguracionEntityManagerUtil.PWR_MODEL_INTEGRATION)
	//private EntityManager entityManager;

	/**
	 * Obtiene la conexiond el datasource que esta corriendo en Jboss.
	 *
	 * @return la conexion del datasource
	 * @throws Exception del negocio o del sistema
	 */
	private Connection getConexionDS(String conexionName) throws Exception {
		Context ctx;
		ctx = new InitialContext();
		DataSource dataSource = (DataSource) ctx.lookup(conexionName);
		return dataSource.getConnection();

	}

	
	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayPdf(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayPdf(ParametroReporteVO parametroReporteVO) throws Exception {
		Connection conexion = getConexionDS(parametroReporteVO.getNombreJNDIConexion()); //TODO AGREGAR CONEXION BuildErp
		parametroReporteVO.setCn(conexion);
		parametroReporteVO = completarParametros(parametroReporteVO); 
		try {
			return AdministradorReportes.generarReporteArrayPdf(parametroReporteVO);
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayPdfBean(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayPdfBean(ParametroReporteVO parametroReporteVO)
			throws Exception {
		parametroReporteVO = completarParametros(parametroReporteVO);
		return AdministradorReportes.generarReporteArrayPdf(parametroReporteVO);
	}

	
	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayHtml(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayHtml(ParametroReporteVO parametroReporteVO) throws Exception {
		Connection conexion = getConexionDS(parametroReporteVO.getNombreJNDIConexion());
		parametroReporteVO = completarParametros(parametroReporteVO);
		try {
			parametroReporteVO.setCn(conexion);
			return AdministradorReportes.generarReporteArrayHtml(parametroReporteVO);
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayHtmlBean(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayHtmlBean(ParametroReporteVO parametroReporteVO)
			throws Exception {
		parametroReporteVO = completarParametros(parametroReporteVO);
		return AdministradorReportes.generarReporteArrayHtml(parametroReporteVO);
	}
	
	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayXls(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayXls(ParametroReporteVO parametroReporteVO) throws Exception {
		Connection conexion = getConexionDS(parametroReporteVO.getNombreJNDIConexion());
		parametroReporteVO = completarParametros(parametroReporteVO);
		try {
			parametroReporteVO.setCn(conexion);
			return AdministradorReportes.generarReporteArrayXls(parametroReporteVO);
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayXlsBean(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayXlsBean(ParametroReporteVO parametroReporteVO)
			throws Exception {
		parametroReporteVO = completarParametros(parametroReporteVO);
		return AdministradorReportes.generarReporteArrayXls(parametroReporteVO);
	}

	// generarReporteArrayXlsBean

	
	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayRtf(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayRtf(ParametroReporteVO parametroReporteVO) throws Exception {
		Connection conexion = getConexionDS(parametroReporteVO.getNombreJNDIConexion());
		parametroReporteVO = completarParametros(parametroReporteVO);
		try {
			parametroReporteVO.setCn(conexion);
			return AdministradorReportes.generarReporteArrayRtf(parametroReporteVO);
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayRtfBean(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayRtfBean(ParametroReporteVO parametroReporteVO)
			throws Exception {
		parametroReporteVO = completarParametros(parametroReporteVO);
		return AdministradorReportes.generarReporteArrayRtf(parametroReporteVO);
	}
	
	
	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayOdt(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayOdt(ParametroReporteVO parametroReporteVO) throws Exception {
		Connection conexion = getConexionDS(parametroReporteVO.getNombreJNDIConexion());
		parametroReporteVO = completarParametros(parametroReporteVO);
		
		try {
			parametroReporteVO.setCn(conexion);
			return AdministradorReportes.generarReporteArrayOdt(parametroReporteVO);
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}
	}


	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayOdtBean(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayOdtBean(ParametroReporteVO parametroReporteVO)
			throws Exception {
		     parametroReporteVO = completarParametros(parametroReporteVO);
		return AdministradorReportes.generarReporteArrayOdt(parametroReporteVO);
	}
	
	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayOdt(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayOds(ParametroReporteVO parametroReporteVO) throws Exception {
		Connection conexion = getConexionDS(parametroReporteVO.getNombreJNDIConexion());
		parametroReporteVO = completarParametros(parametroReporteVO);
		try {
			parametroReporteVO.setCn(conexion);
			return AdministradorReportes.generarReporteArrayOds(parametroReporteVO);
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayOdsBean(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayOdsBean(ParametroReporteVO parametroReporteVO)
			throws Exception {
			parametroReporteVO = completarParametros(parametroReporteVO);
		return AdministradorReportes.generarReporteArrayOds(parametroReporteVO);
	}
	
	

	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayDocx(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayDocx(ParametroReporteVO parametroReporteVO) throws Exception {
		Connection conexion = getConexionDS(parametroReporteVO.getNombreJNDIConexion());
		parametroReporteVO = completarParametros(parametroReporteVO);
		try {
			parametroReporteVO.setCn(conexion);
			return AdministradorReportes.generarReporteArrayDocx(parametroReporteVO);
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayDocxBean(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayDocxBean(ParametroReporteVO parametroReporteVO)
			throws Exception {
		parametroReporteVO = completarParametros(parametroReporteVO);
		return AdministradorReportes.generarReporteArrayDocx(parametroReporteVO);

	}
	
	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayPptx(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayPptx(ParametroReporteVO parametroReporteVO) throws Exception {
		Connection conexion = getConexionDS(parametroReporteVO.getNombreJNDIConexion());
		parametroReporteVO = completarParametros(parametroReporteVO);
		try {
			parametroReporteVO.setCn(conexion);
			return AdministradorReportes.generarReporteArrayPptx(parametroReporteVO);
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayPptxBean(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayPptxBean(ParametroReporteVO parametroReporteVO)
			throws Exception {
		parametroReporteVO = completarParametros(parametroReporteVO);
		return AdministradorReportes.generarReporteArrayPptx(parametroReporteVO);
	}
	
	
	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayXlsx(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayXlsx(ParametroReporteVO parametroReporteVO) throws Exception {
		Connection conexion = getConexionDS(parametroReporteVO.getNombreJNDIConexion());
		parametroReporteVO = completarParametros(parametroReporteVO);
		try {
			parametroReporteVO.setCn(conexion);
			return AdministradorReportes.generarReporteArrayXlsx(parametroReporteVO);
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see pe.edu.upp.siaa.reporte.dao.ReporteDaoLocal#generarReporteArrayXlsxBean(java.util.Map, java.util.List, java.lang.String, java.lang.String[], pe.edu.upp.siaa.type.parametroReporteVO.getRutaReporteType())
	 */
	@Override
	public Map<String,Object> generarReporteArrayXlsxBean(ParametroReporteVO parametroReporteVO)
			throws Exception {
		 	parametroReporteVO = completarParametros(parametroReporteVO);
		return AdministradorReportes.generarReporteArrayXlsx(parametroReporteVO);
	}
	
	/**
	 * Completar parametros.
	 *
	 * @param parametroReporteVO el parametro reporte vo
	 * @return the parametro reporte vo
	 */
	private ParametroReporteVO completarParametros(ParametroReporteVO parametroReporteVO) {
		 String rutaReportes = "";
		 if (parametroReporteVO.getJasper() == null) {
			 rutaReportes = ConstanteConfigUtil.generarRuta(ConstanteConfigUtil.RUTA_REPORTE, parametroReporteVO.getRutaReporteType().getKey());
		 } else {
			 rutaReportes = ConstanteConfigUtil.generarRuta(ConstanteConfigUtil.RUTA_REPORTE, parametroReporteVO.getJasper() , parametroReporteVO.getRutaReporteType().getKey());
		 }
		 parametroReporteVO.getParametros().put("ruta", rutaReportes);
		 parametroReporteVO.getParametros().put("rutaBase", ConstanteConfigUtil.RUTA_REPORTE);
		 parametroReporteVO.getParametros().put("ruta_logo", ConstanteConfigUtil.RUTA_REPORTE_IMG);
		 parametroReporteVO.getParametros().put("ruta_cabecera", ConstanteConfigUtil.RUTA_REPORTE_CABECERA);
		 parametroReporteVO.getParametros().put("REPORT_LOCALE", EN);//PARA FORMATO DEL DECIMAL #,##.00
		 if (parametroReporteVO.getCn() != null) {
			 parametroReporteVO.getParametros().put("usuario", parametroReporteVO.getUserName());
			 parametroReporteVO.getParametros().put("fechaGeneracion", parametroReporteVO.getFechaGeneracion());
		 }
		// parametroReporteVO.getParametros().put("net.sf.jasperreports.awt.ignore.missing.font", new Boolean(true));	
		 parametroReporteVO.setRutaReportesAbsoluto(rutaReportes);
		return parametroReporteVO;
	}

}