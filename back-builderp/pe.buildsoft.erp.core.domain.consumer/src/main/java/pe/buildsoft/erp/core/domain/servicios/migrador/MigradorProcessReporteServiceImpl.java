/**
 * 
 */
package pe.buildsoft.erp.core.domain.servicios.migrador;

import java.io.File;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import pe.buildsoft.erp.core.domain.entidades.migrador.HeaderReporte;
import pe.buildsoft.erp.core.domain.interfaces.repositories.migrador.HeaderReporteDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.migrador.MigradorProcessReporteDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.migrador.ConfigReporteTxtServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.migrador.MigradorProcessReporteServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.ConfiguracionCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.migrador.ConfiguracionReporteTxtDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.CabeceraReporteVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ExcelHederTitleVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.FileVO;
import pe.buildsoft.erp.core.infra.transversal.paginator.IDataProvider;
import pe.buildsoft.erp.core.infra.transversal.paginator.LazyLoadingList;
import pe.buildsoft.erp.core.infra.transversal.type.TipoReporteGenerarType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ArchivoUtilidades;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.exel.DataExportExcelPersonalizadoUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.exel.DataExportZipUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.txt.DataExportTXTUtil;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class MigradorProcessReporteServiceImpl.
 * 
 * @author BuildSoft
 * @version 1.0 , 04/10/2019
 * @since BuildErp 1.0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class MigradorProcessReporteServiceImpl extends BaseTransfer implements MigradorProcessReporteServiceLocal {

	private static final String NOM_TABLA_TMP = "nombreTablaTMP";

	private static final int PAGINA_DATA = 4000;

	private static final String CODIGO_REPORTE_MEMORIA = "codigoReporteMemoria";

	private static final String FILTRO = "filtro";

	private static final String NO_ELIMINAR_TMP = "NO_ELIMINAR_TMP";

	private static final String ERROR = "${ERROR}";

	private static final String NOMBRE_TABLA_TMP = ".nombreTablaTMP";

	private static final String ISO_8859_1 = "ISO-8859-1";

	private static final String CHARSET_KEY = "CharsetKey";

	private static final String CHARSET_VAL_KEY = "CharsetValKey";

	private static final String CHARSET_VAL = "CharsetVal";

	private static final String CHARSET = "Charset";

	private static final String CODIGO_REPORTE = "codigoReporte";

	private static final String IS_ONLINE = "isOnline";

	private static final String FLECHA = "  --> ";

	private static final String LISTA_HOJA = "listaHoja";

	private static final String CALCULAR_WITCH_DEMANDA = "calcularWitchDemanda";

	private static final String EXCLUIR_CAMPOS = ".excluirCampos";

	private static final String LISTA_EXCEL_HEDER_TITLE = "listaExcelHederTitle";

	private static final String PARAM_IS_HEADER_CABECERA_UNION_TITLE = "isHeaderCabeceraUnionTitle";

	private static final String PARAM_IS_FREEZE_PANE_NOT = "isFreezePaneNot";

	private static final String PARAM_IS_FREEZE_PANE = "isFreezePane";

	/** La log. */
	private Logger log = LoggerFactory.getLogger(MigradorProcessReporteServiceImpl.class);

	private static final String XLSX = ".xlsx";
	private static final String TXT = ".txt";

	@Inject
	private MigradorProcessReporteDaoLocal reporteDao;

	@Inject
	private HeaderReporteDaoLocal headerReporteDao;

	@Inject
	private ConfigReporteTxtServiceLocal confTxtService;

	@Inject
	private ICache sessionUtil;

	private List<Map<String, Object>> listarHeaderReporteMap(String codigoReporte) {
		return this.headerReporteDao.listarByCodigo(codigoReporte);
	}

	@Override
	public String exportarExcel(String archivoName, String userName, String idSolicitudReporte,
			List<Object> parametroInType, Map<String, Object> propiedadesMap) {
		List<Integer> parametroOutType = new ArrayList<>();
		parametroOutType.add(Types.VARCHAR);
		return exportarExcel(archivoName, userName, idSolicitudReporte, parametroInType, parametroOutType,
				propiedadesMap, null);
	}

	@Override
	public String exportarTxt(String archivoName, String userName, String idSolicitudReporte,
			List<Object> parametroInType, Map<String, Object> propiedadesMap) {
		List<Integer> parametroOutType = new ArrayList<>();
		parametroOutType.add(Types.VARCHAR);
		return exportarTxt(archivoName, userName, idSolicitudReporte, parametroInType, parametroOutType, propiedadesMap,
				null);
	}

	@Override
	public String migrarSPDatosReporte(String userName, String idSolicitudReporte, List<Object> parametroInType,
			Map<String, Object> propiedadesMap) {
		List<Integer> parametroOutType = new ArrayList<>();
		parametroOutType.add(Types.VARCHAR);
		return migrarSPDatosReporte(userName, idSolicitudReporte, parametroInType, parametroOutType, propiedadesMap,
				null);
	}

	@Override
	public String exportarExcel(String archivoName, String userName, String idSolicitudReporte,
			List<Object> parametroInType, Map<String, Object> propiedadesMap, String jndiConexion) {
		List<Integer> parametroOutType = new ArrayList<>();
		parametroOutType.add(Types.VARCHAR);
		return exportarExcel(archivoName, userName, idSolicitudReporte, parametroInType, parametroOutType,
				propiedadesMap, jndiConexion);
	}

	@Override
	public String exportarTxt(String archivoName, String userName, String idSolicitudReporte,
			List<Object> parametroInType, Map<String, Object> propiedadesMap, String jndiConexion) {
		List<Integer> parametroOutType = new ArrayList<>();
		parametroOutType.add(Types.VARCHAR);
		return exportarTxt(archivoName, userName, idSolicitudReporte, parametroInType, parametroOutType, propiedadesMap,
				jndiConexion);
	}

	@Override
	public String migrarSPDatosReporte(String userName, String idSolicitudReporte, List<Object> parametroInType,
			Map<String, Object> propiedadesMap, String jndiConexion) {
		List<Integer> parametroOutType = new ArrayList<>();
		parametroOutType.add(Types.VARCHAR);
		return migrarSPDatosReporte(userName, idSolicitudReporte, parametroInType, parametroOutType, propiedadesMap,
				jndiConexion);
	}

	@Override
	public String migrarSPDatosReporte(String userName, String idSolicitudReporte, List<Object> parametroInType,
			List<Integer> parametroOutType, Map<String, Object> propiedadesMap, String jndiConexion) {
		String nombreSpMigrador = (String) propiedadesMap.get("nombreSpMigrador");
		return reporteDao.migrarSPDatosReporte(nombreSpMigrador, idSolicitudReporte, parametroInType, parametroOutType,
				jndiConexion);
	}

	@Override
	public String exportarExcel(String archivoName, String userName, String idSolicitudReporte,
			List<Object> parametroInType, List<Integer> parametroOutType, Map<String, Object> propiedadesMap,
			String jndiConexion) {
		String resultado = archivoName;
		String nombreTablaTMP = (String) propiedadesMap.get(NOM_TABLA_TMP);
		boolean isOnline = (Boolean) propiedadesMap.get(IS_ONLINE);
		String codigoReporte = (String) propiedadesMap.get(CODIGO_REPORTE);
		String filtro = (String) propiedadesMap.get(FILTRO);
		if ((!propiedadesMap.containsKey(LISTA_HOJA)) && StringUtil.isNullOrEmpty(nombreTablaTMP)) {
			return "${ERROR}Debe especificar la tabla temporal";
		}
		String isMigro = !propiedadesMap.containsKey("IS_SOLO_REPORTE")
				? migrarSPDatosReporte(userName, idSolicitudReporte, parametroInType, parametroOutType, propiedadesMap,
						jndiConexion)
				: "";
		List<String> listaHoja = new ArrayList<>();
		if (isMigro != null && !isMigro.contains(ERROR)) {
			if (propiedadesMap.containsKey(LISTA_HOJA)) {
				listaHoja = (List<String>) propiedadesMap.get(LISTA_HOJA);
			} else {
				listaHoja.add("hojaName");
				String titulo = (String) propiedadesMap.get("titulo");
				propiedadesMap.put("hojaName.codigoReporte", codigoReporte);
				propiedadesMap.put("hojaName.nombreTablaTMP", nombreTablaTMP);
				propiedadesMap.put("hojaName.titulo", titulo);
				propiedadesMap.put("hojaName.name", "DATA");
				propiedadesMap.put("hojaName.filtro", filtro);
				if (propiedadesMap.containsKey(LISTA_EXCEL_HEDER_TITLE)) {
					propiedadesMap.put("hojaName.listaExcelHederTitle", propiedadesMap.get(LISTA_EXCEL_HEDER_TITLE));
					propiedadesMap.put("hojaName.rowInicio", propiedadesMap.get("rowInicio"));
				}
			}
			boolean isTermino = false;
			int cantidadHoja = listaHoja.size();
			int contadorHoja = 0;
			SXSSFWorkbook workbook = new SXSSFWorkbook(100);
			synchronized (workbook) {
				workbook.setCompressTempFiles(true); // temp files will be gzipped
				for (var hoja : listaHoja) {
					contadorHoja++;
					List<String> excluirCampos = new ArrayList<>();
					if (propiedadesMap.containsKey(hoja + EXCLUIR_CAMPOS)) {
						excluirCampos = (List<String>) propiedadesMap.get(hoja + EXCLUIR_CAMPOS);
					}
					List<Map<String, Object>> listaHeader = listarHeaderReporteMap(
							(String) propiedadesMap.get(hoja + ".codigoReporte"));// TMP
					List<Map<String, Object>> listaDataMap = listarReporteTMP(
							(String) propiedadesMap.get(hoja + NOMBRE_TABLA_TMP), idSolicitudReporte, userName,
							(String) propiedadesMap.get(hoja + FILTRO), "ORDEN", jndiConexion);
					listaDataMap.size();// truco para el paginado
					List<String> listaHeaderData = new ArrayList<>();
					String titulo = (String) propiedadesMap.get(hoja + ".titulo");

					if (!propiedadesMap.containsKey(CALCULAR_WITCH_DEMANDA)) {
						propiedadesMap.put(CALCULAR_WITCH_DEMANDA, "true");
					}
					// escribir hoja
					propiedadesMap.put("hojaName", propiedadesMap.get(hoja + ".name"));
					// KEY_HEADER,VALUE_HEADER,TIPO_FORMATO,VALUE_FORMATO
					Map<String, String> valueHeaderMap = new HashMap<>();
					for (var objHeader : listaHeader) {
						String keyHeader = (objHeader.get("keyheader") + "");
						String valueHeader = objHeader.get("valueheader") + "";
						String tipoFormato = objHeader.get("tipoformato") + "";
						String valueFormato = objHeader.get("valueformato") + "";
						listaHeaderData.add(keyHeader);
						valueHeaderMap.put(keyHeader, valueHeader);
						if (!StringUtil.isNullOrEmpty(tipoFormato)) {
							propiedadesMap.put(keyHeader + tipoFormato, valueFormato);
						}
					}
					if (propiedadesMap.containsKey(hoja + EXCLUIR_CAMPOS)) {
						for (var excluirCampo : excluirCampos) {
							listaHeaderData.remove(excluirCampo);
						}
					}
					propiedadesMap.put("overrideHeaderMap", valueHeaderMap);
					String nombreArchivoTemp = archivoName;
					if (!isOnline) {// Es en cola
						nombreArchivoTemp = UUIDUtil.generarElementUUID();
					}
					if (!propiedadesMap.containsKey(PARAM_IS_FREEZE_PANE)
							&& !propiedadesMap.containsKey(PARAM_IS_FREEZE_PANE_NOT)) {
						propiedadesMap.put(PARAM_IS_FREEZE_PANE, PARAM_IS_FREEZE_PANE);
					}
					List<ExcelHederTitleVO> listaExcelHederTitle = (List<ExcelHederTitleVO>) propiedadesMap
							.get(hoja + ".listaExcelHederTitle");
					if (!propiedadesMap.containsKey(PARAM_IS_HEADER_CABECERA_UNION_TITLE)
							&& !propiedadesMap.containsKey("isHeaderCabeceraUnionTitleNot")) {
						propiedadesMap.put(PARAM_IS_HEADER_CABECERA_UNION_TITLE, PARAM_IS_HEADER_CABECERA_UNION_TITLE);
					}
					if (propiedadesMap.containsKey(hoja + ".rowInicio")) {
						propiedadesMap.put("propiedadesMap", propiedadesMap.get(hoja + ".rowInicio"));
					}
					if (cantidadHoja == contadorHoja) {
						propiedadesMap.put("writeExcel", "true");
						isTermino = true;
					}
					escribirArchivoXLSX(titulo, propiedadesMap, listaHeaderData, listaExcelHederTitle, listaDataMap,
							nombreArchivoTemp, archivoName, userName, workbook, isOnline, isTermino);
				}
			}
		} else {
			resultado = isMigro;
		}
		eliminarData(propiedadesMap, listaHoja, idSolicitudReporte, userName, jndiConexion);
		return resultado;
	}

	@Override
	public String exportarTxt(String archivoName, String userName, String idSolicitudReporte,
			List<Object> parametroInType, List<Integer> parametroOutType, Map<String, Object> propiedadesMap,
			String jndiConexion) {
		String resultado = archivoName;
		String nombreTablaTMP = (String) propiedadesMap.get(NOM_TABLA_TMP);
		boolean isOnline = (Boolean) propiedadesMap.get(IS_ONLINE);
		String codigoReporte = (String) propiedadesMap.get(CODIGO_REPORTE);
		if ((!propiedadesMap.containsKey(LISTA_HOJA)) && StringUtil.isNullOrEmpty(nombreTablaTMP)) {
			return "${ERROR}Debe especificar la tabla temporal";
		}
		String isMigro = !propiedadesMap.containsKey("IS_SOLO_REPORTE")
				? migrarSPDatosReporte(userName, idSolicitudReporte, parametroInType, parametroOutType, propiedadesMap,
						jndiConexion)
				: "";
		List<String> listaHoja = new ArrayList<>();
		listaHoja.add("");
		if (isMigro != null && !isMigro.contains(ERROR)) {
			var filtro = new BaseSearch();
			filtro.setCodigoReporte(codigoReporte);
			List<ConfiguracionReporteTxtDTO> listaConfReporteTXT = null;
			if (!propiedadesMap.containsKey(CODIGO_REPORTE_MEMORIA)) {
				listaConfReporteTXT = listarConfiguracionReporteTxt(filtro);
			} else {
				listaConfReporteTXT = (List<ConfiguracionReporteTxtDTO>) propiedadesMap.get(CODIGO_REPORTE_MEMORIA);
			}

			List<Map<String, Object>> listaDataMap = listarReporteTMP(nombreTablaTMP, idSolicitudReporte, userName,
					(String) propiedadesMap.get(FILTRO), "ORDEN", jndiConexion);
			listaDataMap.size();// truco para el paginado
			log.error("Proceso.MigradorProcessReporteServiceImpl.inicio.generarReporteText." + idSolicitudReporte
					+ ".cantiad." + archivoName + " : " + listaDataMap.size() + "" + idSolicitudReporte + FLECHA
					+ FechaUtil.obtenerFechaActual());
			propiedadesMap.put(CHARSET, ISO_8859_1);// ISO-8859-1;Cp1252
			propiedadesMap.put(CHARSET_VAL, ISO_8859_1);// ISO-8859-1;Cp1252

			if (propiedadesMap.containsKey(CHARSET_KEY)
					&& isObtenerValorProperties(propiedadesMap.get(CHARSET_KEY) + "")) {
				propiedadesMap.put(CHARSET, obtenerValorProperties(propiedadesMap.get(CHARSET_KEY) + ""));
			}
			if (propiedadesMap.containsKey(CHARSET_VAL_KEY)
					&& isObtenerValorProperties(propiedadesMap.get(CHARSET_VAL_KEY) + "")) {
				propiedadesMap.put(CHARSET_VAL, obtenerValorProperties(propiedadesMap.get(CHARSET_KEY) + ""));
			}
			generarTxt(isOnline, listaConfReporteTXT, listaDataMap, archivoName, propiedadesMap, userName);
		} else {
			resultado = isMigro;
		}
		if (!propiedadesMap.containsKey(NO_ELIMINAR_TMP)) {
			eliminarDataTemporalReporte((String) propiedadesMap.get(NOM_TABLA_TMP), idSolicitudReporte, userName,
					(String) propiedadesMap.get(FILTRO), jndiConexion);
		}
		return resultado;
	}

	private void generarTxt(boolean isOnline, List<ConfiguracionReporteTxtDTO> listaConfReporteTXT,
			List<Map<String, Object>> listaDataMap, String archivoName, Map<String, Object> propiedadesMap,
			String usuario) {
		DataExportTXTUtil.generarTXTMap(listaConfReporteTXT, listaDataMap, archivoName, propiedadesMap);
		if (!isOnline) {// Es en cola
			try {
				ArchivoUtilidades.copyArchivo(archivoName + TXT, archivoName + TXT, usuario, false);
			} catch (Exception e) {
				log.error("generarTxt", e);
			}
		}
		if (isOnline) {
			generarDescarga(archivoName, usuario, TipoReporteGenerarType.TXT);
		}
	}

	private void eliminarData(Map<String, Object> propiedadesMap, List<String> listaHoja, String idSolicitudReporte,
			String userName, String jndiConexion) {
		if (!propiedadesMap.containsKey(NO_ELIMINAR_TMP)) {
			for (var hoja : listaHoja) {
				eliminarDataTemporalReporte((String) propiedadesMap.get(hoja + NOMBRE_TABLA_TMP), idSolicitudReporte,
						userName, (String) propiedadesMap.get(hoja + FILTRO), jndiConexion);
			}
		}
	}

	@Override
	public String generarReporteExel(Map<String, Object> propiedadesMap, String archivoName,
			List<Map<String, Object>> listaDataMap, String userName) {
		String resultado = archivoName;
		SXSSFWorkbook workbook = null;
		try {
			boolean isOnline = (Boolean) propiedadesMap.get(IS_ONLINE);
			String codigoReporte = (String) propiedadesMap.get(CODIGO_REPORTE);
			List<ExcelHederTitleVO> listaExcelHederTitle = (List<ExcelHederTitleVO>) propiedadesMap
					.get(LISTA_EXCEL_HEDER_TITLE);
			List<Map<String, Object>> listaHeader = listarHeaderReporteMap(codigoReporte);// TMP
			listaDataMap.size();// truco para el paginado
			List<String> listaHeaderData = new ArrayList<>();
			String titulo = (String) propiedadesMap.get("titulo");
			workbook = new SXSSFWorkbook(100);
			synchronized (workbook) {
				workbook.setCompressTempFiles(true); // temp files will be gzipped
				propiedadesMap.put(CALCULAR_WITCH_DEMANDA, "true");
				// escribir hoja
				propiedadesMap.put("writeExcel", "true");
				// KEY_HEADER,VALUE_HEADER,TIPO_FORMATO,VALUE_FORMATO
				Map<String, String> valueHeaderMap = new HashMap<>();
				for (var objHeader : listaHeader) {
					String keyHeader = (objHeader.get("KEY_HEADER") + "").toUpperCase();
					String valueHeader = objHeader.get("VALUE_HEADER") + "";
					String tipoFormato = objHeader.get("TIPO_FORMATO") + "";
					String valueFormato = objHeader.get("VALUE_FORMATO") + "";
					listaHeaderData.add(keyHeader);
					valueHeaderMap.put(keyHeader, valueHeader);
					if (!StringUtil.isNullOrEmpty(tipoFormato)) {
						propiedadesMap.put(keyHeader + tipoFormato, valueFormato);
					}
				}
				propiedadesMap.put("overrideHeaderMap", valueHeaderMap);
				String nombreArchivoTemp = archivoName;
				if (!isOnline) {// Es en cola
					nombreArchivoTemp = UUIDUtil.generarElementUUID();
				}
				propiedadesMap.put(PARAM_IS_HEADER_CABECERA_UNION_TITLE, PARAM_IS_HEADER_CABECERA_UNION_TITLE);
				escribirArchivoXLSX(titulo, propiedadesMap, listaHeaderData, listaExcelHederTitle, listaDataMap,
						nombreArchivoTemp, archivoName, userName, workbook, isOnline, true);
			}
		} catch (Exception e) {
			resultado = ERROR + e.getMessage();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (Exception e) {
					log.error("Error: No se pudo cerrar workbook");
				}
			}
		}

		return resultado;
	}

	@Override
	public String generarReporteTxt(Map<String, Object> propiedadesMap, String archivoName,
			List<Map<String, Object>> listaDataMap, String userName) {
		String resultado = archivoName;
		try {
			boolean isOnline = (Boolean) propiedadesMap.get(IS_ONLINE);
			String codigoReporte = (String) propiedadesMap.get(CODIGO_REPORTE);
			var filtro = new BaseSearch();
			filtro.setCodigoReporte(codigoReporte);
			List<ConfiguracionReporteTxtDTO> listaConfReporteTXT = listarConfiguracionReporteTxt(filtro);
			log.error("Proceso.MigradorProcessReporteServiceImpl.inicio.generarReporteText.cantidad." + archivoName
					+ " : " + listaDataMap.size() + FLECHA + FechaUtil.obtenerFechaActual());
			propiedadesMap.put(CHARSET, ISO_8859_1);// ISO-8859-1;Cp1252
			propiedadesMap.put(CHARSET_VAL, ISO_8859_1);// ISO-8859-1;Cp1252

			if (propiedadesMap.containsKey(CHARSET_KEY)
					&& isObtenerValorProperties(propiedadesMap.get(CHARSET_KEY) + "")) {
				propiedadesMap.put(CHARSET, obtenerValorProperties(propiedadesMap.get(CHARSET_KEY) + ""));
			}
			if (propiedadesMap.containsKey(CHARSET_VAL_KEY)
					&& isObtenerValorProperties(propiedadesMap.get(CHARSET_VAL_KEY) + "")) {
				propiedadesMap.put(CHARSET_VAL, obtenerValorProperties(propiedadesMap.get(CHARSET_KEY) + ""));
			}
			generarTxt(isOnline, listaConfReporteTXT, listaDataMap, archivoName, propiedadesMap, userName);
		} catch (Exception e) {
			resultado = ERROR + e.getMessage();
		}
		return resultado;
	}

	private List<ConfiguracionReporteTxtDTO> listarConfiguracionReporteTxt(BaseSearch filtro) {
		return toList(confTxtService.listarConfiguracionReporteTxt(filtro), ConfiguracionReporteTxtDTO.class);
	}

	@Override
	public String eliminarDataTemporalReporte(String nombreTablaTMP, String idSolicitudReporte, String userName,
			String filtro, String jndiConexion) {
		return reporteDao.eliminarDataTemporalReporte(nombreTablaTMP, idSolicitudReporte, userName, filtro,
				jndiConexion);
	}

	@Override
	public String eliminarDataTemporalReporte(String nombreTablaTMP, String idSolicitudReporte, String userName,
			String jndiConexion) {
		return eliminarDataTemporalReporte(nombreTablaTMP, idSolicitudReporte, userName, "", jndiConexion);
	}

	private List<Map<String, Object>> listarReporteTMP(String nombreTablaTMP, String idSolicitudReporte,
			String userName, String filtro, int offset, int startRow, String orderBy, String jndiConexion) {
		return reporteDao.listarReporteTMPByOrden(nombreTablaTMP, idSolicitudReporte, userName, filtro, offset,
				startRow, orderBy, jndiConexion);
	}

	@Override
	public List<Map<String, Object>> listarReporteTMP(String nombreTablaTMP, String idSolicitudReporte, String userName,
			int offset, int startRow, String jndiConexion) {
		return listarReporteTMP(nombreTablaTMP, idSolicitudReporte, userName, "", offset, startRow, "orden",
				jndiConexion);
	}

	private int contarReporteTMP(String nombreTablaTMP, String idSolicitudReporte, String userName, String filtro,
			String jndiConexion) {
		return reporteDao.contarReporteTMP(nombreTablaTMP, idSolicitudReporte, userName, filtro, jndiConexion);
	}

	@Override
	public int contarReporteTMP(String nombreTablaTMP, String idSolicitudReporte, String userName,
			String jndiConexion) {
		return reporteDao.contarReporteTMP(nombreTablaTMP, idSolicitudReporte, userName, "", jndiConexion);
	}

	@Override
	public List<HeaderReporte> listarHeaderReporte(String codigoReporte) {
		return this.headerReporteDao.listar(codigoReporte);
	}

	private List<Map<String, Object>> listarReporteTMP(String nombreTablaTMP, String idSolicitudReporte,
			String userName, String filtro, String orderBy, String jndiConexion) {
		return buscarPaginadoReporte(nombreTablaTMP, idSolicitudReporte, userName, filtro, orderBy, jndiConexion);
	}

	@Override
	public List<Map<String, Object>> listarReporteTMP(String nombreTablaTMP, String idSolicitudReporte, String userName,
			String jndiConexion) {
		return listarReporteTMP(nombreTablaTMP, idSolicitudReporte, userName, "", "orden", jndiConexion);
	}

	@Override
	public List<Map<String, Object>> listarReporteTMPOrderBy(String nombreTablaTMP, String idSolicitudReporte,
			String userName, String orderBy, String jndiConexion) {
		return listarReporteTMP(nombreTablaTMP, idSolicitudReporte, userName, "", orderBy, jndiConexion);
	}

	@Override
	public List<Map<String, Object>> listarReporteTMPOrderBy(String nombreTablaTMP, String idSolicitudReporte,
			String userName, String filtro, String orderBy, String jndiConexion) {
		return listarReporteTMP(nombreTablaTMP, idSolicitudReporte, userName, filtro, orderBy, jndiConexion);
	}

	private List<Map<String, Object>> buscarPaginadoReporte(final String nombreTablaTMP,
			final String idSolicitudReporte, final String userName, final String filtro, final String orderBy,
			final String jndiConexion) {
		IDataProvider<Map<String, Object>> dataProvider = new IDataProvider<Map<String, Object>>() {
			private int total = 0;
			private int cuenta = 0;

			@Override
			public List<Map<String, Object>> getBufferedData(int startRow, int offset) {
				List<Map<String, Object>> lista = new ArrayList<>();
				try {
					lista = listarReporteTMP(nombreTablaTMP, idSolicitudReporte, userName, filtro, offset, startRow,
							orderBy, jndiConexion);
				} catch (Exception e) {
					log.info(e + "");
					lista = new ArrayList<>();
				}
				return lista;
			}

			@Override
			public int getTotalResultsNumber() {
				if (total == 0 && cuenta == 0) {
					try {
						total = contarReporteTMP(nombreTablaTMP, idSolicitudReporte, userName, filtro, jndiConexion);
					} catch (Exception e) {
						log.info(e + "");
						total = 0;
					}
					cuenta++;
				}
				return total;
			}
		};
		return new LazyLoadingList<>(dataProvider, PAGINA_DATA);
	}

	private void escribirArchivoXLSX(String titulo, Map<String, Object> propiedadesMap, List<String> listaHeaderData,
			List<ExcelHederTitleVO> listaHeaderCabecera, List<Map<String, Object>> listaDataMap,
			String nombreArchivoTemp, String archivoName, String usuario, SXSSFWorkbook workbook, boolean isOnline,
			boolean isTermino) {
		DataExportExcelPersonalizadoUtil.generarExcelXLSXPerMap(listaHeaderData, listaHeaderCabecera, listaDataMap,
				nombreArchivoTemp, titulo, propiedadesMap, workbook, null);
		if (!isOnline && isTermino) {// Es en cola
			try {
				ArchivoUtilidades.copyArchivo(nombreArchivoTemp + XLSX, archivoName + XLSX, usuario, false);
			} catch (Exception e) {
				log.error("escribirArchivoXLSX", e);
			}
		}
		if (isOnline) {
			generarDescarga(archivoName, usuario, TipoReporteGenerarType.XLSX);
		}
	}

	private void generarDescarga(String archivoName, String usuario, TipoReporteGenerarType tipoReporteGenerarType) {
		FileVO objeto = new FileVO();
		String nombre = archivoName + "." + tipoReporteGenerarType.getKey();
		objeto.setName(nombre);
		objeto.setDataBig(nombre);
		objeto.setMime(tipoReporteGenerarType.getContentType());
		sessionUtil.put(archivoName, objeto);
	}

	@Override
	public String obtenerValorProperties(String key) {
		return ConfiguracionCacheUtil.getInstance().getPwrConfUtil(key);
	}

	@Override
	public int obtenerValorPropertiesInt(String key) {
		return ConfiguracionCacheUtil.getInstance().getPwrConfUtilInt(key);
	}

	@Override
	public boolean isObtenerValorProperties(String key) {
		return ConfiguracionCacheUtil.getInstance().containsKeyPwrConfUtil(key);
	}

	@Override
	public String generarZip(String archivoName, String extension, CabeceraReporteVO objFiltro, String... files) {
		return generarZip(archivoName, extension, null, objFiltro, files);
	}

	@Override
	public String generarZip(String archivoName, String extension, Map<String, Object> reporteGeneradoMap,
			CabeceraReporteVO objFiltro, String... files) {
		List<FileVO> listaFileVO = new ArrayList<>();
		Map<String, File> listaFileVOMap = new HashMap<>();
		for (var objFile : files) {
			listaFileVO.add(geFileVO(objFile, reporteGeneradoMap));
			listaFileVOMap.put(objFile, geFile(objFile + extension));
		}
		DataExportZipUtil.generarZip(listaFileVO, listaFileVOMap, archivoName);
		if (!objFiltro.isOnline()) {
			try {
				ArchivoUtilidades.copyArchivo(archivoName + ".zip", archivoName + ".zip", objFiltro.getUsuario(),
						false);
			} catch (Exception e) {
				log.error("generarZip", e);
			}
		}
		if (objFiltro.isOnline()) {
			generarDescarga(archivoName, objFiltro.getUsuario(), TipoReporteGenerarType.ZIP);
		}
		for (var objFile : listaFileVOMap.entrySet()) {
			objFile.getValue().delete();
		}
		return archivoName;
	}

	private File geFile(String ftpDescargar) {
		return new File(ConstanteConfigUtil.RUTA_RECURSOS_BYTE_BUFFER + ftpDescargar);
	}

	private FileVO geFileVO(String archivoName, Map<String, Object> reporteGeneradoMap) {
		FileVO resultado = new FileVO();
		resultado.setName(archivoName);
		if (reporteGeneradoMap != null) {
			resultado.setReporteGeneradoMap(reporteGeneradoMap);
		}
		return resultado;
	}
}
