package pe.buildsoft.erp.core.domain.servicios.migrador;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import pe.buildsoft.erp.core.domain.entidades.migrador.HeaderReadReporte;
import pe.buildsoft.erp.core.domain.interfaces.repositories.migrador.HeaderReadReporteDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.migrador.MigradorProcessReporteDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.migrador.CargadorDataTramaServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.CampoTablaVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ConfiguracionTramaDataVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ConfiguracionTramaDetalleVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ConfiguracionTramaVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.FileVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ReglaVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaCargaDataTramaVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaLecturaAgrupadoVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.TramaNomenclaturaArchivoVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ValueDataVO;
import pe.buildsoft.erp.core.infra.transversal.type.RespuestaNaturalType;
import pe.buildsoft.erp.core.infra.transversal.type.TipoArchivoProcesarType;
import pe.buildsoft.erp.core.infra.transversal.type.TipoCampoType;
import pe.buildsoft.erp.core.infra.transversal.type.TipoProcesoType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BigMemoryManager;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfiguracionTramaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ResourceUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.TransferDataObjectValidarOperUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.Utilitario;
import pe.buildsoft.erp.core.infra.transversal.utilitario.csv.CSVUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.exel.DataExportExcelPersonalizadoUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.exel.ExcelUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.txt.TXTUtil;

/**
 * La Class MantenedorCubosOAServiceImpl.
 * <ul>
 * <li>Copyright 2018 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Mon Jul 29 14:01:07 COT 2019
 * @since BuildErp 1.0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class CargadorDataTramaServiceImpl implements CargadorDataTramaServiceLocal {

	private static final String PARSEAR_CONFIGURACION_TRAMA_DATA = "parsearConfiguracionTramaData.";
	private static final String INICIO = ".inicio ";
	private static final String UUID_TRAZA = "uuidTraza";
	private static final String SALTO_LINEA = "\n";
	private static final String EL_CAMPO = "El Campo '";
	private static final String FUNC = "${FUNC}";
	private static final String FUNC_UUID = "${UUID}";
	private static final String FUNC_CONTADOR = "${CONTADOR}";
	private static final String FUNC_USER = "${USER}";
	private static final String ERROR = "Error";
	private static final String PROCESADO = "{PROCESADO}";
	private static final String ERROR_TYPE = "${type}";
	private static final String C_ERROR = "${ERROR}";
	private static final String CAMPO_MENSAJE_ERROR = "${MENSAJE_ERROR}";
	private static final String CAMPO_MENSAJE_ERROR_VAL = "MENSAJE_ERROR";

	private static Map<String, String> funcionesMap = new HashMap<>();
	/**
	 * Logger para el registro de errores.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CargadorDataTramaServiceImpl.class);

	@Inject
	private HeaderReadReporteDaoLocal headerReadReporteDaoLocal;

	@Inject
	private MigradorProcessReporteDaoLocal migradorProcessReporteDaoLocal;

	@Override
	public List<Map<String, Object>> listarHeaderReadReporte(String codigoProceso) {
		return headerReadReporteDaoLocal.listar(codigoProceso);
	}

	@Override
	public List<HeaderReadReporte> listarHeaderReadReporteByCodigo(String codigoProceso) {
		var filtro = new BaseSearch();
		filtro.setCodigoReporte(codigoProceso);
		return headerReadReporteDaoLocal.listar(filtro);
	}

	@Override
	public String generarArchivoCargaDataTramaError(List<Map<String, Object>> listaHeaderTemp,
			List<Map<String, ValueDataVO>> listaDataMapTemp, String archivoName) throws Exception {
		List<Map<String, Object>> listaHeader = new ArrayList<>();
		listaHeader.addAll(listaHeaderTemp);
		Map<String, Object> map = new HashMap<>();
		map.put("C_KEY_HEADER", CAMPO_MENSAJE_ERROR);
		map.put("C_VALUE_HEADER", CAMPO_MENSAJE_ERROR_VAL);
		map.put("C_TIPO_FORMATO", "");
		map.put("C_VALUE_FORMATO", "");
		map.put("C_CAMPO_LEIDO_TRAMA", RespuestaNaturalType.SI.getKey().toString());
		listaHeader.add(map);
		return generarArchivoCargaDataTrama(listaHeader, listaDataMapTemp, archivoName);
	}

	@Override
	public String generarArchivoCargaDataTrama(List<Map<String, Object>> listaHeader,
			List<Map<String, ValueDataVO>> listaDataMapTemp, String archivoName) throws Exception {
		String resultado = archivoName;
		List<Map<String, Object>> listaDataMap = new ArrayList<>();
		for (var map : listaDataMapTemp) {
			Map<String, Object> mapObject = new HashMap<>();
			for (var objDataMap : map.entrySet()) {
				if (objDataMap.getValue().getData() != null
						&& objDataMap.getValue().getData().toString().contains("${ERROR}${type}")) {
					mapObject.put(objDataMap.getKey(),
							objDataMap.getValue().getData().toString().replace("${ERROR}${type}", ""));
				} else {
					mapObject.put(objDataMap.getKey(), objDataMap.getValue().getData());
				}

			}
			listaDataMap.add(mapObject);
		}
		List<String> listaHeaderData = new ArrayList<>();
		String titulo = "Carga Exel Presupuesto OA";
		SXSSFWorkbook workbook = new SXSSFWorkbook(100);
		workbook.setCompressTempFiles(true); // temp files will be gzipped
		Map<String, Object> propiedadesMap = new HashMap<>();
		propiedadesMap.put("calcularWitchDemanda", "true");
		// escribir hoja
		propiedadesMap.put("hojaName", "DATA");
		propiedadesMap.put("writeExcel", "true");
		propiedadesMap.put("wrapText" + CAMPO_MENSAJE_ERROR, "wrapText");
		propiedadesMap.put("witch" + CAMPO_MENSAJE_ERROR, "80");
		// KEY_HEADER,VALUE_HEADER,TIPO_FORMATO,VALUE_FORMATO
		Map<String, String> valueHeaderMap = new HashMap<>();
		for (var objHeader : listaHeader) {
			if (RespuestaNaturalType.SI.getKey().toString()
					.equalsIgnoreCase(objHeader.get("C_CAMPO_LEIDO_TRAMA") + "")) {
				String keyHeader = (objHeader.get("C_KEY_HEADER") + "").toUpperCase();
				String valueHeader = objHeader.get("C_VALUE_HEADER") + "";
				String tipoFormato = objHeader.get("C_TIPO_FORMATO") + "";
				String valueFormato = objHeader.get("C_VALUE_FORMATO") + "";
				listaHeaderData.add(keyHeader);
				valueHeaderMap.put(keyHeader, valueHeader);
				if (!StringUtil.isNullOrEmpty(tipoFormato)) {
					propiedadesMap.put(keyHeader + tipoFormato, valueFormato);
				}
			}
		}
		propiedadesMap.put("overrideHeaderMap", valueHeaderMap);
		String nombreArchivoTemp = archivoName;
		propiedadesMap.put("isFreezePane", "isFreezePane");
		escribirArchivoXLSX(titulo, propiedadesMap, listaHeaderData, listaDataMap, nombreArchivoTemp, workbook);
		return resultado;
	}

	private void escribirArchivoXLSX(String titulo, Map<String, Object> propiedadesMap, List<String> listaHeaderData,
			List<Map<String, Object>> listaDataMap, String nombreArchivoTemp, SXSSFWorkbook workbook) {
		DataExportExcelPersonalizadoUtil.generarExcelXLSXPerMap(listaHeaderData, null, listaDataMap, nombreArchivoTemp,
				titulo, propiedadesMap, workbook, null);
	}

	private void generarFuncionMap() {
		funcionesMap.put(FUNC, FUNC);
		funcionesMap.put(FUNC_UUID, FUNC_UUID);
		funcionesMap.put(FUNC_CONTADOR, FUNC_CONTADOR);
		funcionesMap.put(FUNC_USER, FUNC_USER);
	}

	private static String obtenerValorDefectoCampo(Map<String, Object> parametroMap, String valorDefectoCampo,
			String nombeCampoTabla) {
		String resultadoValor = valorDefectoCampo;
		if (resultadoValor != null && resultadoValor.contains(FUNC_USER)) {
			resultadoValor = parametroMap.get("userName") + "";
		} else if (resultadoValor != null && resultadoValor.contains("${" + nombeCampoTabla + "}")) {
			resultadoValor = parametroMap.get("${" + nombeCampoTabla + "}") + "";
		}
		return resultadoValor;
	}

	@Override
	public RespuestaCargaDataTramaVO procesarCargaDataTrama(List<HeaderReadReporte> listaHeadeReadReporte,
			Map<String, Object> parametroMap, FileVO fileVO, String codigoProceso) throws Exception {
		generarFuncionMap();
		LOG.error("procesarCargaDataTrama." + parametroMap.get(UUID_TRAZA) + INICIO + FechaUtil.obtenerFechaActual());
		RespuestaCargaDataTramaVO resultado = new RespuestaCargaDataTramaVO();

		ConfiguracionTramaVO configuracionTrama = generarConfiguracionTrama(listaHeadeReadReporte, parametroMap);
		TipoArchivoProcesarType tipoArchivoProcesarType = TipoArchivoProcesarType
				.get(configuracionTrama.getTramaNomenclaturaArchivo().getTipoArchivo());
		HSSFWorkbook hSSFWorkbook = null;
		XSSFWorkbook xSSFWorkbook = null;
		Map<String, Object> campoMappingPosicionMap = obtenerCampoMappingPosicionMap(configuracionTrama);
		Map<String, String> campoMappingTypeMap = obtenerCampoMappingTypeMap(configuracionTrama);

		// seteando valor por defaulft
		Map<String, ValueDataVO> valuePorDefectoMap = new HashMap<>();
		Map<String, String> campoMappingFormatoMap = new HashMap<>();
		Map<String, pe.buildsoft.erp.core.infra.transversal.entidades.vo.ConfiguracionTramaDetalleVO> configuracionTramaDetalleMap = new HashMap<>();
		Map<String, Character> configuracionTramaDetalleCharacterMap = new HashMap<>();
		Map<String, String> campoNoPersistente = new HashMap<>();
		for (var configuracionTramaDetalle : configuracionTrama.getConfiguracionTramaConfiguracionTramaDetalleList()) {
			String nombeCampoTabla = obtenerCampoPersistenteAsociado(configuracionTramaDetalle);
			if ((RespuestaNaturalType.SI.getKey().equals(configuracionTramaDetalle.getFlagCampoNoLeidoTrama()))
					&& !StringUtil.isNullOrEmpty(configuracionTramaDetalle.getValorDefectoCampo())) {
				if (!funcionesMap.containsKey(configuracionTramaDetalle.getValorDefectoCampo())) {
					valuePorDefectoMap.put(nombeCampoTabla,
							obtenerValueParse(nombeCampoTabla, configuracionTramaDetalle.getValorDefectoCampo(),
									campoMappingTypeMap.get(nombeCampoTabla),
									configuracionTramaDetalle.getFormatoCampo(), 0, parametroMap));
				} else {
					String resultadoValor = obtenerValorDefectoCampo(parametroMap,
							configuracionTramaDetalle.getValorDefectoCampo(), nombeCampoTabla);
					valuePorDefectoMap.put(nombeCampoTabla, new ValueDataVO(resultadoValor));
				}

			}
			if (RespuestaNaturalType.NO.getKey().equals(configuracionTramaDetalle.getEsPersistente())) {
				campoNoPersistente.put(nombeCampoTabla, "");
			}
			campoMappingFormatoMap.put(nombeCampoTabla, configuracionTramaDetalle.getFormatoCampo());
			configuracionTramaDetalleMap.put(nombeCampoTabla, configuracionTramaDetalle);
			configuracionTramaDetalleCharacterMap.put(nombeCampoTabla,
					configuracionTramaDetalle.getFlagCampoAgrupador());
		}
		boolean isCabecera = false;
		Integer cantidadDataProcesar = isCabecera ? 1 : null;
		List<Map<String, ValueDataVO>> listaDataResulMap = new ArrayList<>();
		parametroMap.put("CARGA_TRAMA_PER", "");
		switch (tipoArchivoProcesarType) {
			case EXCEL_XLS:
			case EXCEL_XLSX:
				hSSFWorkbook = null;
				xSSFWorkbook = null;
				try {
					xSSFWorkbook = ExcelUtil.leerExcelXLSX(fileVO.getData());
				} catch (Exception e) {
					hSSFWorkbook = ExcelUtil.leerExcel(fileVO.getData());
				}
				if (hSSFWorkbook != null) {
					listaDataResulMap = ExcelUtil.toXlsMap(hSSFWorkbook, configuracionTrama.getNumeroHoja().intValue(),
							configuracionTrama.getFilaData().intValue(), campoMappingPosicionMap, campoMappingTypeMap,
							campoMappingFormatoMap, cantidadDataProcesar, parametroMap,
							configuracionTramaDetalleCharacterMap);
				} else if (xSSFWorkbook != null) {
					listaDataResulMap = ExcelUtil.toXlsxMap(xSSFWorkbook, configuracionTrama.getNumeroHoja().intValue(),
							configuracionTrama.getFilaData().intValue(), campoMappingPosicionMap, campoMappingTypeMap,
							campoMappingFormatoMap, cantidadDataProcesar, parametroMap,
							configuracionTramaDetalleCharacterMap);
				}
				break;
			case CSV:
				BufferedReader br = CSVUtil.leerCVS(fileVO.getData());
				listaDataResulMap = CSVUtil.toCSVMap(br, configuracionTrama.getFilaData().intValue(),
						campoMappingPosicionMap, campoMappingTypeMap, campoMappingFormatoMap,
						configuracionTrama.getSeparador(), cantidadDataProcesar, parametroMap,
						configuracionTramaDetalleCharacterMap);
				break;

			case TXT:
				boolean isByCoordenada = verificarProcesamientoByCoordenada(configuracionTrama);
				BufferedReader brText = TXTUtil.leerTXT(fileVO.getData());
				if (!isByCoordenada) {
					if (RespuestaNaturalType.SI.getKey().equals(configuracionTrama.getTieneSeparador())) {
						listaDataResulMap = TXTUtil.transferObjetoEntityTXTMapDTO(brText,
								configuracionTrama.getFilaData().intValue(), campoMappingPosicionMap,
								campoMappingTypeMap, campoMappingFormatoMap, configuracionTrama.getSeparador(),
								cantidadDataProcesar, parametroMap, configuracionTramaDetalleCharacterMap);
					} else {
						listaDataResulMap = TXTUtil.toTXTMap(brText, configuracionTrama.getFilaData().intValue(),
								campoMappingPosicionMap, campoMappingTypeMap, campoMappingFormatoMap,
								cantidadDataProcesar, parametroMap, configuracionTramaDetalleCharacterMap);
					}
				} else {
					if (isCabecera) {
						cantidadDataProcesar = obtenerCantidadProcesamientoByCoordenada(configuracionTrama);
					}
					listaDataResulMap = TXTUtil.transferObjetoEntityCoordenadaTXTMapDTO(brText,
							configuracionTrama.getFilaData().intValue(), campoMappingPosicionMap, campoMappingTypeMap,
							campoMappingFormatoMap, cantidadDataProcesar, isCabecera,
							configuracionTrama.getDelimitadorData(), parametroMap,
							configuracionTramaDetalleCharacterMap);
				}
				break;

			default:
				break;
		}

		for (var map : listaDataResulMap) {
			for (var mapValue : valuePorDefectoMap.entrySet()) {
				if (map.containsKey(mapValue.getKey())) {
					map.put(mapValue.getKey(),
							(ValueDataVO) BeanUtils.cloneBean(valuePorDefectoMap.get(mapValue.getKey())));
				}
			}
		}
		resultado.setListaDataResulMap(listaDataResulMap);
		resultado.setCampoMappingFormatoMap(campoMappingFormatoMap);
		Map<String, CampoTablaVO> campoDisponibleMap = new HashMap<>();
		campoNoPersistente.put(CAMPO_MENSAJE_ERROR, "");
		resultado = parsearConfiguracionTramaData(resultado, configuracionTrama, configuracionTramaDetalleMap,
				valuePorDefectoMap, campoDisponibleMap, campoNoPersistente, parametroMap);
		resultado.setConfiguracionTramaDetalleMap(configuracionTramaDetalleMap);
		if (resultado.isEsError()) {
			resultado.setListaDataResulErrorMap(resultado.getListaDataResulMap());
		}
		LOG.error("procesarCargaDataTrama." + parametroMap.get(UUID_TRAZA) + ".fin " + FechaUtil.obtenerFechaActual());
		return resultado;
	}

	private ConfiguracionTramaVO generarConfiguracionTrama(List<HeaderReadReporte> listaHeadeReadReporte,
			Map<String, Object> parametroMap) {
		ConfiguracionTramaVO configuracionTrama = new ConfiguracionTramaVO();
		Map<String, ConfiguracionTramaVO> configuracionTramaMap = new HashMap<>();
		for (var objData : listaHeadeReadReporte) {
			String key = objData.getNombreTabla();
			if (!configuracionTramaMap.containsKey(key)) {
				configuracionTrama.setTramaNomenclaturaArchivo(new TramaNomenclaturaArchivoVO());
				String tipoArchivo = "";
				if (TipoArchivoProcesarType.EXCEL_XLSX.getExtension().equalsIgnoreCase(objData.getTipoReporte())) {
					tipoArchivo = TipoArchivoProcesarType.EXCEL_XLSX.getKey();
				} else if (TipoArchivoProcesarType.EXCEL_XLS.getExtension()
						.equalsIgnoreCase(objData.getTipoReporte())) {
					tipoArchivo = TipoArchivoProcesarType.EXCEL_XLS.getKey();
				} else if (TipoArchivoProcesarType.CSV.getExtension().equalsIgnoreCase(objData.getTipoReporte())) {
					tipoArchivo = TipoArchivoProcesarType.CSV.getKey();
				} else if (TipoArchivoProcesarType.TXT.getExtension().equalsIgnoreCase(objData.getTipoReporte())) {
					tipoArchivo = TipoArchivoProcesarType.TXT.getKey();
				}
				configuracionTrama.getTramaNomenclaturaArchivo().setTipoArchivo(tipoArchivo);
				configuracionTrama.setNombreTabla(objData.getNombreTabla());
				configuracionTrama.setFilaData(objData.getFilaData());
				configuracionTrama.setTipoProceso(TipoProcesoType.DETALLE.getKey());
				configuracionTrama.getConfiguracionTramaConfiguracionTramaDetalleList()
						.add(generarConfiguracionTramaDetalle(objData));
				if (parametroMap.containsKey("NUMERO_HOJA")) {
					configuracionTrama.setNumeroHoja(ObjectUtil.objectToLong(parametroMap.get("NUMERO_HOJA")));// por
																												// defecto
				} else {
					configuracionTrama.setNumeroHoja(1L);// por defecto
				}
				configuracionTramaMap.put(key, configuracionTrama);
			} else {
				configuracionTrama.getConfiguracionTramaConfiguracionTramaDetalleList()
						.add(generarConfiguracionTramaDetalle(objData));
			}
		}
		return configuracionTrama;
	}

	private ConfiguracionTramaDetalleVO generarConfiguracionTramaDetalle(HeaderReadReporte objData) {
		ConfiguracionTramaDetalleVO resultado = new ConfiguracionTramaDetalleVO();
		resultado.setCodigoUUID(objData.getIdHeaderReadReporte());
		resultado.setNombreCampo(objData.getKeyHeader());
		resultado.setNombeCampoTabla(objData.getKeyHeader());
		resultado.setDescripcionCampo(objData.getValueHeader());
		resultado.setObligatorio(objData.getObligatorio().charAt(0));
		resultado.setTipoCampo(objData.getTipoCampo());
		resultado.setPosicionCampoInicial(objData.getPosicionCampoInicial());
		resultado.setPosicionCampoFinal(objData.getPosicionCampoFinal());
		resultado.setValorDefectoCampo(objData.getValorDefectoCampo());
		resultado.setFormatoCampo(objData.getValueFormato());
		resultado.setOrden(objData.getOrden());
		// resultado.setFilaData(objData.getFilaData());//cuando es txt y coordenada
		resultado.setFlagCampoAgrupador(RespuestaNaturalType.NO.getKey());
		resultado.setFlagCampoNoLeidoTrama(
				RespuestaNaturalType.NO.getKey().toString().equals(objData.getCampoLeidoTrama())
						? RespuestaNaturalType.SI.getKey()
						: RespuestaNaturalType.NO.getKey());
		resultado.setLongitud(objData.getLongitud());
		resultado.setEsPersistente(objData.getEsPersistente().charAt(0));
		resultado.setEsCampoPersistente(RespuestaNaturalType.SI.getKey().toString().equals(objData.getEsPersistente()));
		resultado.setReglaNegocio(objData.getRegla());

		/*
		 * if ((!StringUtil.isNullOrEmpty(objData.getRegla())) &&
		 * !ReglaCacheUtil.getInstance().getReglaMap().containsKey(ParametroReglaUtil.
		 * ACRONIMO_REGLA_CONF_TRAMA_DETALLE_CARGA_DATA +
		 * objData.getIdHeaderReadReporte())) {
		 * ReglaCacheUtil.getInstance().parsearRegla(objData.getRegla(),
		 * ParametroReglaUtil.ACRONIMO_REGLA_CONF_TRAMA_DETALLE_CARGA_DATA +
		 * objData.getIdHeaderReadReporte()); }
		 */
		return resultado;
	}

	@Override
	public RespuestaCargaDataTramaVO registrarTramaData(Map<String, Object> parametroMap,
			RespuestaCargaDataTramaVO respuestaCargaDataTramaVO) throws Exception {
		return migradorProcessReporteDaoLocal.registrarTramaData(parametroMap, respuestaCargaDataTramaVO);
	}

	public RespuestaCargaDataTramaVO parsearConfiguracionTramaData(RespuestaCargaDataTramaVO resuestaLectura,
			ConfiguracionTramaVO configuracionTrama,
			Map<String, ConfiguracionTramaDetalleVO> configuracionTramaDetalleMap,
			Map<String, ValueDataVO> valuePorDefectoMap, Map<String, CampoTablaVO> campoDisponibleMap,
			Map<String, String> campoNoPersistente, Map<String, Object> parametroMap) throws Exception {
		LOG.error(PARSEAR_CONFIGURACION_TRAMA_DATA + parametroMap.get(UUID_TRAZA) + INICIO
				+ FechaUtil.obtenerFechaActual());
		Long idConfiguracionTrama = configuracionTrama.getIdConfiguradorTrama();
		parametroMap.put(ConstanteConfiguracionTramaUtil.NUMERO_HOJA + "_" + idConfiguracionTrama,
				configuracionTrama.getNumeroHoja()); // setear numero de hoja
		RespuestaCargaDataTramaVO resultado = new RespuestaCargaDataTramaVO();
		BigMemoryManager<String, ConfiguracionTramaDataVO> resultadoListaConf = new BigMemoryManager<>();
		String nombreTabla = configuracionTrama.getNombreTabla().replace(".", ";");
		String[] schemaTableName = nombreTabla.split(";");
		Map<String, String> campoMappingTypeMap = obtenerCampoMappingTypeMap(configuracionTrama);
		List<Map<String, ValueDataVO>> listaDataResulMap = resuestaLectura.getListaDataResulMap();
		LOG.error(PARSEAR_CONFIGURACION_TRAMA_DATA + parametroMap.get(UUID_TRAZA) + ".proceso.size."
				+ listaDataResulMap.size() + " " + FechaUtil.obtenerFechaActual());
		resultado.setCampoMappingFormatoMap(resuestaLectura.getCampoMappingFormatoMap());
		if (campoNoPersistente == null) {
			campoNoPersistente = new HashMap<>();
		}
		RespuestaLecturaAgrupadoVO respuestaLecturaAgrupadoVO = null;
		try {
			// seteando valor por defaulft
			// agrupando
			respuestaLecturaAgrupadoVO = new RespuestaLecturaAgrupadoVO();// tmp natan
			respuestaLecturaAgrupadoVO.getListaAgrupadoDataResulMap().addAll(listaDataResulMap);
			List<Map<String, ValueDataVO>> listaDataResulGrupoMap = respuestaLecturaAgrupadoVO
					.getListaAgrupadoDataResulMap();
			listaDataResulMap = listaDataResulGrupoMap;
			// parseando la data y validar
			Map<Integer, Boolean> errorTuplaMap = new HashMap<>();
			int errorTuplaIndice = 0;
			for (int i = 0; i < listaDataResulGrupoMap.size(); i++) {
				Map<String, ValueDataVO> map = listaDataResulGrupoMap.get(i);
				Map<String, ValueDataVO> mapError = new HashMap<>();
				boolean existeErrorTupla = false;
				StringBuilder errores = new StringBuilder();
				for (var mapValue : map.entrySet()) {
					errorTuplaIndice++;
					ConfiguracionTramaDetalleVO configuracionTramaDetalle = configuracionTramaDetalleMap
							.get(mapValue.getKey());
					TipoCampoType tipoCampoType = obtenerTipoCampo(configuracionTramaDetalle);
					// validar
					boolean existeError = false;
					if (StringUtil.isNullOrEmpty(configuracionTramaDetalle.getReglaNegocio())
							&& !funcionesMap.containsKey(mapValue.getValue().getData())) {
						validarCampoReglaNegocio(resultado, tipoCampoType, map, mapValue, configuracionTramaDetalleMap,
								idConfiguracionTrama, schemaTableName, campoDisponibleMap, configuracionTramaDetalle,
								parametroMap);
						existeError = resultado.isExisteError();
						if (existeError) {
							errores.append(resultado.getValor());
							mapError.put(mapValue.getKey() + C_ERROR, new ValueDataVO());
						}
						map.put(mapValue.getKey(), resultado.getMap().get(mapValue.getKey())); // RESOLUCION DE
																								// INCIDENCIA
					}
					if ((StringUtil.isNullOrEmpty(mapValue.getValue()))
							&& valuePorDefectoMap.containsKey(mapValue.getKey())) {
						map.put(mapValue.getKey(), valuePorDefectoMap.get(mapValue.getKey()));
					}
					if (existeError) {
						existeErrorTupla = true;
					}
				}
				if (mapError.size() > 0) {
					map.putAll(mapError);
				}
				if (!map.containsKey(CAMPO_MENSAJE_ERROR) && errores.length() > 0) {
					map.put(CAMPO_MENSAJE_ERROR, new ValueDataVO(
							errores.toString().substring(0, errores.toString().length() - SALTO_LINEA.length())));
					resultado.setEsError(true);
				} else {
					if (errores.length() > 0) {
						map.put(CAMPO_MENSAJE_ERROR, new ValueDataVO(map.get(CAMPO_MENSAJE_ERROR) + SALTO_LINEA
								+ errores.toString().substring(0, errores.toString().length() - SALTO_LINEA.length())));
					}
				}
				errorTuplaMap.put(errorTuplaIndice, existeErrorTupla);
			}
			// INICIO CAMPOS CALCULADOS.
			// Inicio Procesar Reglas cuando no cumplio con la regla en el for de arriba no
			// se considera el valor de esta para este for.
			errorTuplaIndice = 0;
			for (int i = 0; i < listaDataResulGrupoMap.size(); i++) {
				Map<String, ValueDataVO> map = listaDataResulGrupoMap.get(i);
				boolean existeErrorTupla = false;
				StringBuilder errores = new StringBuilder();
				Map<String, ValueDataVO> mapError = new HashMap<>();
				for (var mapValue : map.entrySet()) {
					if (!CAMPO_MENSAJE_ERROR.equals(mapValue.getKey()) && !mapValue.getKey().contains(C_ERROR)) {
						errorTuplaIndice++;
						ConfiguracionTramaDetalleVO configuracionTramaDetalle = configuracionTramaDetalleMap
								.get(mapValue.getKey());
						TipoCampoType tipoCampoType = obtenerTipoCampo(configuracionTramaDetalle);
						// validar
						boolean existeError = false;
						// INICIO CALCULO DE REGLA
						if (!StringUtil.isNullOrEmpty(configuracionTramaDetalle.getReglaNegocio())) {
							try {
								ValueDataVO memoriaRegla = ejecutarProcesamientoRegla(map, mapValue.getKey(),
										campoMappingTypeMap, tipoCampoType, configuracionTramaDetalle, parametroMap);
								if (memoriaRegla != null && memoriaRegla.getTipoCampoType() != null) { // MODIFICAR EL
																										// TIPO DE CAMPO
																										// EN TIEMPO DE
																										// EJECUCION
									tipoCampoType = memoriaRegla.getTipoCampoType();
									configuracionTramaDetalle.setTipoCampo(memoriaRegla.getTipoCampoType().getKey());
									configuracionTramaDetalleMap.put(mapValue.getKey(), configuracionTramaDetalle);
								}
								map.put(mapValue.getKey(), memoriaRegla);
								validarCampoReglaNegocio(resultado, tipoCampoType, map, mapValue,
										configuracionTramaDetalleMap, idConfiguracionTrama, schemaTableName,
										campoDisponibleMap, configuracionTramaDetalle, parametroMap);
								existeError = resultado.isExisteError();
								if (existeError) {
									errores.append(resultado.getValor());
									mapError.put(mapValue.getKey() + C_ERROR, new ValueDataVO());
								}
								map.put(mapValue.getKey(), resultado.getMap().get(mapValue.getKey())); // RESOLUCION DE
																										// INCIDENCIA
							} catch (Exception e) {
								if (existeError) {
									errores.append("Error Procear Regla" + e.getMessage() + SALTO_LINEA);
								}
							}

						}
						// FIN CALCULO DE REGLAS
						if (existeError) {
							existeErrorTupla = true;
						}
					}
				}
				if (mapError.size() > 0) {
					map.putAll(mapError);
				}
				if (!map.containsKey(CAMPO_MENSAJE_ERROR) && errores.length() > 0) {
					map.put(CAMPO_MENSAJE_ERROR, new ValueDataVO(
							errores.toString().substring(0, errores.toString().length() - SALTO_LINEA.length())));
					resultado.setEsError(true);
				} else {
					if (errores.length() > 0) {
						map.put(CAMPO_MENSAJE_ERROR, new ValueDataVO(map.get(CAMPO_MENSAJE_ERROR) + SALTO_LINEA
								+ errores.toString().substring(0, errores.toString().length() - SALTO_LINEA.length())));
					}
				}

				if (!errorTuplaMap.get(errorTuplaIndice).booleanValue() && existeErrorTupla) {
					errorTuplaMap.put(errorTuplaIndice, existeErrorTupla);
				}
			}
			// Fin Procesar Reglas
			// PASAR AL VO PARA PERSISTIR
			parametroMap.remove(
					ConstanteConfiguracionTramaUtil.NUMERO_HOJA + "_" + configuracionTrama.getIdConfiguradorTrama()); // remover
																														// numero
																														// de
																														// hoja
																														// dinamico
			resultadoListaConf = migradorProcessReporteDaoLocal.generarTuplaPersistente(listaDataResulGrupoMap,
					errorTuplaMap, schemaTableName, configuracionTramaDetalleMap, campoNoPersistente, parametroMap);
		} catch (Exception e) {
			LOG.error("parsearConfiguracionTramaData", e);
			LOG.error(PARSEAR_CONFIGURACION_TRAMA_DATA + parametroMap.get(UUID_TRAZA) + ".proceso.error "
					+ e.getMessage() + "" + FechaUtil.obtenerFechaActual());
			resultado.setMensajeError("parsearConfiguracionTramaData Error General " + e.getMessage());
			resultado.setEsError(true);
		}
		resultado.setSchemaTableName(schemaTableName);
		resultado.setListaDataResulMap(listaDataResulMap);
		resultado.setListaConfiguracionTramaDataVO(resultadoListaConf);
		return resultado;
	}

	private RespuestaCargaDataTramaVO validarCampoReglaNegocio(RespuestaCargaDataTramaVO resultado,
			TipoCampoType tipoCampoType, Map<String, ValueDataVO> map, Map.Entry<String, ValueDataVO> mapValue,
			Map<String, ConfiguracionTramaDetalleVO> configuracionTramaDetalleMap, Long idConfiguracionTrama,
			String[] schemaTableName, Map<String, CampoTablaVO> campoDisponibleMap,
			ConfiguracionTramaDetalleVO configuracionTramaDetalle, Map<String, Object> parametroMap) throws Exception {
		StringBuilder errores = new StringBuilder();
		boolean existeErrorValor = mapValue.getValue() != null && mapValue.getValue().toString().contains(C_ERROR);
		boolean tieneValor = !StringUtil.isNullOrEmpty(mapValue.getValue());
		boolean esObligatorio = RespuestaNaturalType.SI.getKey().equals(configuracionTramaDetalle.getObligatorio());
		boolean isCampoPersistente = !RespuestaNaturalType.NO.getKey()
				.equals(configuracionTramaDetalle.getEsPersistente());
		boolean existeError = false;
		resultado.setExisteError(existeError);
		// Validar Tipo
		boolean typeValido = true;
		if (tieneValor && mapValue.getValue().toString().contains(ERROR_TYPE)) {
			typeValido = false;
		}
		// Validad Obligatoridad
		if (esObligatorio) {
			if (existeErrorValor) {
				tieneValor = false;
				existeErrorValor = false;
			}
			if (!tieneValor) {
				existeError = true;
				String mensajeError = EL_CAMPO + configuracionTramaDetalle.getNombreCampo() + "' es obligatorio "
						+ SALTO_LINEA;
				errores.append(mensajeError);
			}
		}
		// error tipo de dato
		if (!typeValido) {
			existeError = true;
			String valor = mapValue.getValue().toString();
			valor = valor.replace(C_ERROR, "");
			valor = valor.replace(ERROR_TYPE, "");
			String mensajeError = EL_CAMPO + configuracionTramaDetalle.getNombreCampo() + "' tiene valor no válido '"
					+ valor + "' debe ser " + tipoCampoType.toString() + SALTO_LINEA;
			errores.append(mensajeError);
		}
		// Validar Longitud
		if ((TipoCampoType.NUMERICO.equals(tipoCampoType) || TipoCampoType.TEXTO.equals(tipoCampoType))
				&& isCampoPersistente) {
			String valor = mapValue.getValue() + "";
			if (tieneValor && !existeErrorValor) {
				CampoTablaVO campoTablaVO = campoDisponibleMap.get(mapValue.getKey());
				Integer longitudCampo;
				Long longitudCampoUsuario = obtenerLongitud(configuracionTramaDetalle);
				if (!StringUtil.isNullOrEmpty(campoTablaVO) && !StringUtil.isNullOrEmpty(campoTablaVO.getLength())) {
					longitudCampo = Integer.parseInt(campoTablaVO.getLength());
					if (valor.length() > longitudCampo.intValue()) {
						existeError = true;
						String mensajeError = EL_CAMPO + configuracionTramaDetalle.getNombreCampo()
								+ "' excedida la longitud(" + longitudCampo + ") permitidos en el campo de la tabla "
								+ SALTO_LINEA;
						errores.append(mensajeError);
					}
				}
				if (longitudCampoUsuario != null && valor.length() > longitudCampoUsuario.intValue()) {
					existeError = true;
					String mensajeError = EL_CAMPO + configuracionTramaDetalle.getNombreCampo()
							+ "' excedida la longitud(" + longitudCampoUsuario + ") permitidos " + SALTO_LINEA;
					if (errores.length() == 0) {
						errores.append(mensajeError);
					}
				}
			}
		}
		if (existeErrorValor && typeValido && !esObligatorio) {
			String valor = mapValue.getValue() + "";
			if (!valor.contains(PROCESADO)) {
				valor = valor.replace(C_ERROR, ERROR);
				valor = valor.replace(ERROR_TYPE, tipoCampoType.toString());
				String mensajeError = EL_CAMPO + configuracionTramaDetalle.getNombreCampo()
						+ "'  Error obtener al información de '" + valor + "' " + SALTO_LINEA;
				errores.append(mensajeError);
			}
			map.put(mapValue.getKey(), new ValueDataVO(""));
		}
		resultado.setMap(map);
		resultado.setValor(errores.toString());
		resultado.setExisteError(existeError);
		return resultado;
	}

	public static ValueDataVO obtenerValueParse(String nombeCampoTabla, String resultadoValor, String objAt,
			String formatoFecha, int filaData, Map<String, Object> parametroMap) {
		ValueDataVO resultado = new ValueDataVO();
		resultado.setFila("" + (filaData != 0 ? filaData : ""));
		resultadoValor = obtenerValorDefectoCampo(parametroMap, resultadoValor, nombeCampoTabla);
		try {
			if (StringUtil.isNullOrEmpty(resultadoValor)) {
				return null;
			}
			resultadoValor = StringUtil.quitarCaracterExtranio(resultadoValor);
			resultadoValor = resultadoValor.trim();
			if (objAt.equals(Boolean.class.getName())) {
				resultado.setData(Boolean.valueOf(resultadoValor));
			} else if (objAt.equals(Integer.class.getName())) {
				resultado.setData(Integer.parseInt(resultadoValor));
			} else if (objAt.equals(Float.class.getName())) {
				resultado.setData(Float.parseFloat(resultadoValor));
			} else if (objAt.equals(Double.class.getName())) {
				resultado.setData(Double.parseDouble(resultadoValor));
			} else if (objAt.equals(Long.class.getName())) {
				resultado.setData(Long.parseLong(resultadoValor));
			} else if (objAt.equals(BigDecimal.class.getName())) {
				// convirtiendo datos numericos fomateados
				resultadoValor = Utilitario.reingenieriaFormateoNumerico(resultadoValor);
				resultado.setData(new BigDecimal(resultadoValor));
			} else if (objAt.equals(Character.class.getName())) {
				resultado.setData(resultadoValor.charAt(0));
			} else if (objAt.equals(OffsetDateTime.class.getName())) {
				if (resultadoValor.contains(FUNC)) {
					resultado.setData(resultadoValor);
				} else {
					resultado.setData(FechaUtil.obtenerFechaFormatoPersonalizado(resultadoValor, formatoFecha));
				}

			} else if (objAt.equals(Collection.class.getName())) {
				resultado = null;
			} else {
				resultado.setData(resultadoValor);
			}
		} catch (Exception e) {
			resultado.setData("${ERROR} :Tipo de Conversión no valido ${type} --> " + resultadoValor + " en la fila "
					+ (filaData));
			return resultado;
		}
		return resultado;
	}

	protected Integer obtenerCantidadProcesamientoByCoordenada(ConfiguracionTramaVO configuracionTrama) {
		Integer resultado = 0;
		int maxResultado = 0;
		if (!StringUtil.isNullOrEmptyNumeric(
				configuracionTrama.getConfiguracionTramaConfiguracionTramaDetalleList().get(0).getFilaData())) {
			maxResultado = configuracionTrama.getConfiguracionTramaConfiguracionTramaDetalleList().get(0).getFilaData()
					.intValue();
		}
		for (var configuracionTramaDetalleVO : configuracionTrama
				.getConfiguracionTramaConfiguracionTramaDetalleList()) {
			if (!StringUtil.isNullOrEmptyNumeric(configuracionTramaDetalleVO.getFilaData())) {
				int valorMaximo = configuracionTramaDetalleVO.getFilaData().intValue();
				if (maxResultado < valorMaximo) {
					maxResultado = valorMaximo;
				}
			}
		}
		resultado = maxResultado;
		return resultado;
	}

	protected Long obtenerLongitud(ConfiguracionTramaDetalleVO configuracionTramaDetalle) {
		Long longitud = configuracionTramaDetalle.getLongitud();
		if (configuracionTramaDetalle.getCampoAsociado() != null) {
			longitud = configuracionTramaDetalle.getCampoAsociado().getLongitud();
		}
		return longitud;
	}

	private ValueDataVO ejecutarProcesamientoRegla(Map<String, ValueDataVO> map, String nombreCampoTabla,
			Map<String, String> campoMappingTypeMap, TipoCampoType tipoCampoType,
			ConfiguracionTramaDetalleVO configuracionTramaDetalle, Map<String, Object> parametroMap) throws Exception {
		ReglaVO memoria = new ReglaVO(map, new ValueDataVO());
		String fila = "";
		memoria.setEsSimulacion(
				ResourceUtil.esSimulacion(parametroMap.get(ConstanteConfiguracionTramaUtil.ES_SIMULACION)));
		for (var elemento : map.entrySet()) {
			if (!StringUtil.isNullOrEmpty(elemento.getValue().getFila())) {
				fila = elemento.getValue().getFila();
				break;
			}
		}
		/*
		 * ProcesarReglaUtil.procesarRegla(memoria,
		 * ParametroReglaUtil.ACRONIMO_REGLA_CONF_TRAMA_DETALLE_CARGA_DATA +
		 * configuracionTramaDetalle.getCodigoUUID()); if (memoria.getResultado() !=
		 * null && memoria.getResultado().getTipoCampoType() != null) { tipoCampoType =
		 * memoria.getResultado().getTipoCampoType(); }
		 */
		parserarTipoCampoByRegla(tipoCampoType, memoria.getResultado(), nombreCampoTabla, campoMappingTypeMap,
				configuracionTramaDetalle, parametroMap);
		memoria.getResultado().setFila(fila);
		return memoria.getResultado();
	}

	private ValueDataVO parserarTipoCampoByRegla(TipoCampoType tipoCampoType, ValueDataVO data, String nombeCampoTabla,
			Map<String, String> campoMappingTypeMap, ConfiguracionTramaDetalleVO configuracionTramaDetalle,
			Map<String, Object> parametroMap) throws Exception {
		if (data == null || data.getData() == null) {
			return data;
		}
		if (data.getData().toString().contains(C_ERROR)) {
			return data;
		}
		if (tipoCampoType != null) {
			switch (tipoCampoType) {
				case FECHA:
					if (data.getData().toString().contains(";")) {
						String[] dataTemp = data.getData().toString().split(";", -1);
						if (dataTemp.length > 1) {
							data.setData(TransferDataObjectValidarOperUtil.obtenerValueParse(dataTemp[0],
									campoMappingTypeMap.get(nombeCampoTabla), dataTemp[1], 0, parametroMap).getData());
						}
					} else {
						try {
							if (!(data.getData() instanceof OffsetDateTime)) {
								OffsetDateTime date = FechaUtil
										.obtenerFechaFormatoPersonalizado(data.getData().toString(), "dd/MM/yyyy");
								data.setData(date);
							}
						} catch (Exception e) {
							data.setData("${ERROR}El campo no tiene formato ejemplo 01/01/1900;dd/MM/yyyy");
						}
					}
					break;
				case TEXTO:
					if (data.getData().toString().contains(";")) {
						String[] dataTemp = data.getData().toString().split(";", -1);
						if (dataTemp.length > 0) {
							data.setData(dataTemp[0]);
						}
					}
					break;

				case NUMERICO:
					if (data.getData().toString().contains(";")) {
						String[] dataTemp = data.getData().toString().split(";", -1);
						if (dataTemp.length > 0) {
							data.setData(
									TransferDataObjectValidarOperUtil
											.obtenerValueParse(dataTemp[0], campoMappingTypeMap.get(nombeCampoTabla),
													configuracionTramaDetalle.getFormatoCampo(), 0, parametroMap)
											.getData());
						}
					} else {
						data.setData(TransferDataObjectValidarOperUtil
								.obtenerValueParse(data.getData().toString(), campoMappingTypeMap.get(nombeCampoTabla),
										configuracionTramaDetalle.getFormatoCampo(), 0, parametroMap)
								.getData());
					}
					break;
				default:
					break;
			}
		}
		return data;
	}

	protected Map<String, Object> obtenerCampoMappingPosicionMap(ConfiguracionTramaVO configuracionTrama) {
		Map<String, Object> resultado = new HashMap<>();
		TipoArchivoProcesarType tipoArchivoProcesarType = TipoArchivoProcesarType
				.get(configuracionTrama.getTramaNomenclaturaArchivo().getTipoArchivo());
		switch (tipoArchivoProcesarType) {
			case EXCEL_XLS:
			case EXCEL_XLSX:
			case CSV:
				for (var configuracionTramaDetalle : configuracionTrama
						.getConfiguracionTramaConfiguracionTramaDetalleList()) {
					String nombeCampoTabla = obtenerCampoPersistenteAsociado(configuracionTramaDetalle);
					if (!RespuestaNaturalType.SI.getKey()
							.equals(configuracionTramaDetalle.getFlagCampoNoLeidoTrama())) {
						resultado.put(nombeCampoTabla, configuracionTramaDetalle.getPosicionCampoInicial() - 1);// - 1
																												// para
																												// el
																												// usuario
																												// empieza
																												// en 1
																												// para
																												// java
																												// en 0
					}
					if (RespuestaNaturalType.SI.getKey().equals(configuracionTramaDetalle.getFlagCampoNoLeidoTrama())) {
						resultado.put(nombeCampoTabla, -1L);
					}
				}
				break;

			case TXT:
				boolean isByCoordenada = verificarProcesamientoByCoordenada(configuracionTrama);
				if (!isByCoordenada) {
					if (RespuestaNaturalType.SI.getKey().equals(configuracionTrama.getTieneSeparador())) {
						for (var configuracionTramaDetalle : configuracionTrama
								.getConfiguracionTramaConfiguracionTramaDetalleList()) {
							String nombeCampoTabla = obtenerCampoPersistenteAsociado(configuracionTramaDetalle);
							if (!RespuestaNaturalType.SI.getKey()
									.equals(configuracionTramaDetalle.getFlagCampoNoLeidoTrama())) {
								resultado.put(nombeCampoTabla, configuracionTramaDetalle.getPosicionCampoInicial() - 1);// -
																														// 1
																														// para
																														// el
																														// usuario
																														// empieza
																														// en
																														// 1
																														// para
																														// java
																														// en
																														// 0
							}
							if (RespuestaNaturalType.SI.getKey()
									.equals(configuracionTramaDetalle.getFlagCampoNoLeidoTrama())) {
								resultado.put(nombeCampoTabla, -1L);
							}
						}
					} else {
						for (var configuracionTramaDetalle : configuracionTrama
								.getConfiguracionTramaConfiguracionTramaDetalleList()) {
							String nombeCampoTabla = obtenerCampoPersistenteAsociado(configuracionTramaDetalle);
							if (!RespuestaNaturalType.SI.getKey()
									.equals(configuracionTramaDetalle.getFlagCampoNoLeidoTrama())) {
								String posicionTexto = "" + (configuracionTramaDetalle.getPosicionCampoInicial() - 1)
										+ ";" + (configuracionTramaDetalle.getPosicionCampoFinal());// Mejora Lectura
																									// Posicion
																									// Final
								resultado.put(nombeCampoTabla, posicionTexto);
							}
							if (RespuestaNaturalType.SI.getKey()
									.equals(configuracionTramaDetalle.getFlagCampoNoLeidoTrama())) {
								resultado.put(nombeCampoTabla, -1L);
							}
						}
					}
				} else {
					for (var configuracionTramaDetalle : configuracionTrama
							.getConfiguracionTramaConfiguracionTramaDetalleList()) {
						String nombeCampoTabla = obtenerCampoPersistenteAsociado(configuracionTramaDetalle);
						if (!RespuestaNaturalType.SI.getKey()
								.equals(configuracionTramaDetalle.getFlagCampoNoLeidoTrama())) {
							String posicionTexto = (configuracionTramaDetalle.getFilaData() - 1) + ";"
									+ (configuracionTramaDetalle.getPosicionCampoInicial() - 1) + ";"
									+ (configuracionTramaDetalle.getPosicionCampoFinal());// Mejora Lectura Posicion
																							// Final
							resultado.put(nombeCampoTabla, posicionTexto);
						}
						if (RespuestaNaturalType.SI.getKey()
								.equals(configuracionTramaDetalle.getFlagCampoNoLeidoTrama())) {
							resultado.put(nombeCampoTabla, -1L);
						}
					}
				}
				break;

			default:
				break;
		}
		return resultado;
	}

	protected Map<String, String> obtenerCampoMappingTypeMap(ConfiguracionTramaVO configuracionTrama) {
		Map<String, String> resultado = new HashMap<>();
		for (var configuracionTramaDetalle : configuracionTrama.getConfiguracionTramaConfiguracionTramaDetalleList()) {
			String nombeCampoTabla = obtenerCampoPersistenteAsociado(configuracionTramaDetalle);
			TipoCampoType tipoCampoType = obtenerTipoCampo(configuracionTramaDetalle);
			switch (tipoCampoType) {
				case FECHA:
					resultado.put(nombeCampoTabla, "java.util.Date");
					break;
				case NUMERICO:
					resultado.put(nombeCampoTabla, "java.math.BigDecimal");
					break;
				case TEXTO:
					resultado.put(nombeCampoTabla, "java.lang.String");
					break;
				default:
					break;
			}
		}
		return resultado;
	}

	protected TipoCampoType obtenerTipoCampo(ConfiguracionTramaDetalleVO configuracionTramaDetalle) {
		return migradorProcessReporteDaoLocal.obtenerTipoCampo(configuracionTramaDetalle);
	}

	protected String obtenerCampoPersistenteAsociado(ConfiguracionTramaDetalleVO configuracionTramaDetalle) {
		String resultado = configuracionTramaDetalle.getNombeCampoTabla();
		boolean isCampoPersistente = !RespuestaNaturalType.NO.getKey()
				.equals(configuracionTramaDetalle.getEsPersistente());
		if (!isCampoPersistente) {
			resultado = configuracionTramaDetalle.getNombreCampo();
		}
		return resultado;
	}

	protected boolean verificarProcesamientoByCoordenada(ConfiguracionTramaVO configuracionTrama) {
		return RespuestaNaturalType.SI.getKey().equals(configuracionTrama.getEsCoordenada());
	}

}