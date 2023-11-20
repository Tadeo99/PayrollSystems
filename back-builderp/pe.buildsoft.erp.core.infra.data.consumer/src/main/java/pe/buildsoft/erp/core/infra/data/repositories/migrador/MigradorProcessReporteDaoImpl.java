/**
 * 
 */
package pe.buildsoft.erp.core.infra.data.repositories.migrador;

import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import pe.buildsoft.erp.core.domain.interfaces.repositories.migrador.MigradorProcessReporteDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericBatchJDBC;
import pe.buildsoft.erp.core.infra.data.repositories.GenericCommonBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ConfiguracionTramaDataVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ConfiguracionTramaDetalleVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaCargaDataTramaVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ScriptSqlResulJDBCVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ValueDataVO;
import pe.buildsoft.erp.core.infra.transversal.type.CampoFijoType;
import pe.buildsoft.erp.core.infra.transversal.type.RespuestaNaturalType;
import pe.buildsoft.erp.core.infra.transversal.type.TipoCampoType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BigMemoryManager;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class BienvenidaReboteDaoImpl.
 *
 * @author BuildSoft S.A.C.
 * @version 1.0 , 04/05/2019
 * @since BuildErp 1.0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class MigradorProcessReporteDaoImpl extends GenericCommonBatchDAOImpl<Object, Object>
		implements MigradorProcessReporteDaoLocal {

	private static final String RESULTADO1 = "resultado1";
	private static final String ID_SOLICITUD_REPORTE = "idSolicitudReporte";
	private static final String CODIGO_USUARIO = "codigoUsuario";
	private static final String ERROR = "${ERROR}";
	private static final String REGISTRAR_TRAMA_DATA = "registrarTramaData.";
	private static final String INICIO = ".inicio ";
	private static final String UUID_TRAZA = "uuidTraza";
	private static final String JNDI_CONEXION = "jndiConexion";
	private static final String FUNC = "${FUNC}";
	private static final String FUNC_UUID = "${UUID}";
	private static final String FUNC_CONTADOR = "${CONTADOR}";
	private static final String C_ERROR = ERROR;
	private static final String CAMPO_MENSAJE_ERROR = "${MENSAJE_ERROR}";

	/** La log. */
	private Logger log = LoggerFactory.getLogger(MigradorProcessReporteDaoImpl.class);

	private static final Logger LOG = LoggerFactory.getLogger(MigradorProcessReporteDaoImpl.class);

	@Override
	public String migrarSPDatosReporte(String nombreSpMigrador, String idSolicitudReporte, List<Object> parametroInType,
			List<Integer> parametroOutType, String jndiConexion) {
		String resultado = "";
		String resultadoSP = "";
		log.error("Proceso.migrarSPDatosReporte." + nombreSpMigrador + ".inicio." + idSolicitudReporte + "  --> "
				+ FechaUtil.obtenerFechaActual());
		try {
			ScriptSqlResulJDBCVO sqEjecutado = executeQuerySP(nombreSpMigrador, jndiConexion, parametroOutType,
					parametroInType);
			if (!sqEjecutado.isTieneError() && !CollectionUtil.isEmpty(sqEjecutado.getListaData())) {
				resultadoSP = sqEjecutado.getResulMap().get(RESULTADO1) + "";
				resultado = resultadoSP;
			} else {
				resultadoSP = sqEjecutado.getMensajeError();
				resultado = ERROR + sqEjecutado.getMensajeError();
			}
		} catch (Exception e) {
			log.error("migrarSPDatosReporte", e);
			resultado = ERROR + e.getMessage();
		}
		log.error("Proceso.migrarSPDatosReporte." + nombreSpMigrador + ".fin." + idSolicitudReporte + " ==> "
				+ resultado + ": " + resultadoSP + " --> " + FechaUtil.obtenerFechaActual());
		return resultado;
	}

	@Override
	public String eliminarDataTemporalReporte(String nombreTablaTMP, String idSolicitudReporte, String userName,
			String filtro, String jndiConexion) {
		String resultado = "";
		List<Integer> parametroOutType = new ArrayList<>();
		parametroOutType.add(Types.VARCHAR);
		log.error("Proceso.eliminarDataTemporalReporte.inicio." + nombreTablaTMP + "." + userName + "."
				+ idSolicitudReporte + "  --> " + FechaUtil.obtenerFechaActual());
		try {
			var parametros = new HashMap<String, Object>();
			parametros.put(ID_SOLICITUD_REPORTE, idSolicitudReporte);
			parametros.put(CODIGO_USUARIO, userName);
			ScriptSqlResulJDBCVO sqEjecutado = executeUpdate(new StringBuilder("DELETE FROM " + nombreTablaTMP
					+ " WHERE CODSOLICITUD =:idSolicitudReporte and CODUSUARIO=:codigoUsuario and ORDEN > 0 "
					+ (filtro == null ? "" : filtro)), parametros);
			if (sqEjecutado.isTieneError()) {
				resultado = sqEjecutado.getMensajeError();
			}
		} catch (Exception e) {
			log.error("eliminarDataTemporalReporte", e);
			resultado = e.getMessage();
		}
		log.error("Proceso.eliminarDataTemporalReporte.fin." + nombreTablaTMP + "." + userName + "."
				+ idSolicitudReporte + "  --> " + resultado + " ==>" + FechaUtil.obtenerFechaActual());
		return resultado;
	}

	@Override
	public List<Map<String, Object>> listarReporteTMP(String nombreTablaTMP, String idSolicitudReporte, String userName,
			String filtro, int offset, int startRow, String jndiConexion) {
		return listarReporteTMP(nombreTablaTMP, idSolicitudReporte, userName, filtro, offset, startRow, "orden",
				jndiConexion);
	}

	private List<Map<String, Object>> listarReporteTMP(String nombreTablaTMP, String idSolicitudReporte,
			String userName, String filtro, int offset, int startRow, String ordenBy, String jndiConexion) {
		List<Map<String, Object>> resultado = new ArrayList<>();
		try {
			if (filtro == null) {
				filtro = "";
			}
			ScriptSqlResulJDBCVO resultadoTemp = null;
			StringBuilder sql = new StringBuilder();
			sql.append(" select  /*+ first_rows(:cantidadRegistros) */ a.* ");
			sql.append(" from " + nombreTablaTMP + " a  ");
			sql.append(" where a.codsolicitud =:numSolicitud  and a.codusuario=:codigoUsuario " + filtro + "");
			sql.append(" AND a." + ordenBy + " > :startRow AND a." + ordenBy + "  <=  (:startRow + :offset)  ");
			sql.append(" ORDER BY a. " + ordenBy);
			var parametros = new HashMap<String, Object>();
			parametros.put("cantidadRegistros", offset);
			parametros.put("startRow", startRow);
			parametros.put("offset", offset);
			parametros.put("numSolicitud", idSolicitudReporte);
			parametros.put(CODIGO_USUARIO, userName);
			resultadoTemp = executeQuery(sql, parametros, jndiConexion, true);
			if (resultadoTemp.isTieneData()) {
				resultado = resultadoTemp.getListaData();
			}
		} catch (Exception e) {
			log.error("listarReporteTMP", e);
		}
		return resultado;
	}

	@Override
	public List<Map<String, Object>> listarReporteTMPByOrden(String nombreTablaTMP, String idSolicitudReporte,
			String userName, String filtro, int offset, int startRow, String ordenBy, String jndiConexion) {
		return listarReporteTMP(nombreTablaTMP, idSolicitudReporte, userName, filtro, offset, startRow, ordenBy,
				jndiConexion);
	}

	@Override
	public int contarReporteTMP(String nombreTablaTMP, String idSolicitudReporte, String userName, String filtro,
			String jndiConexion) {
		int resultado = 0;
		try {
			if (filtro == null) {
				filtro = "";
			}
			StringBuilder sql = new StringBuilder();
			sql.append(" select count(1) as CONTADOR");
			sql.append(" from " + nombreTablaTMP + " a");
			sql.append(" where a.codsolicitud=:numSolicitud  and a.codusuario=:codigoUsuario " + filtro
					+ " and  a.orden > 0 ");
			var parametros = new HashMap<String, Object>();
			parametros.put("numSolicitud", idSolicitudReporte);
			parametros.put(CODIGO_USUARIO, userName);
			ScriptSqlResulJDBCVO resultadoTemp = executeQuery(sql, parametros, jndiConexion);
			if (!resultadoTemp.isTieneError()) {
				if (resultadoTemp.isTieneData()) {
					resultado = Integer.parseInt(resultadoTemp.getListaData().get(0).get("contador") + "");
				}
			} else {
				log.error("contarReporteTMP." + nombreTablaTMP + " => " + idSolicitudReporte + ""
						+ resultadoTemp.getMensajeError());
			}
		} catch (Exception e) {
			log.error("contarReporteTMP", e);
			resultado = 0;
		}
		return resultado;
	}

	@Override
	public RespuestaCargaDataTramaVO registrarTramaData(Map<String, Object> parametroMap,
			RespuestaCargaDataTramaVO respuestaCargaDataTramaVO) throws Exception {
		LOG.error(REGISTRAR_TRAMA_DATA + parametroMap.get(UUID_TRAZA) + INICIO + FechaUtil.obtenerFechaActual());
		if (parametroMap.containsKey("ISRECALCULO")) {
			BigMemoryManager<String, ConfiguracionTramaDataVO> resultadoListaConf;
			resultadoListaConf = generarTuplaPersistente(respuestaCargaDataTramaVO.getListaDataResulMap(),
					new HashMap<Integer, Boolean>(), respuestaCargaDataTramaVO.getSchemaTableName(),
					respuestaCargaDataTramaVO.getConfiguracionTramaDetalleMap(),
					respuestaCargaDataTramaVO.getCampoNoPersistente(), parametroMap);
			respuestaCargaDataTramaVO.setListaConfiguracionTramaDataVO(resultadoListaConf);
		}
		LOG.error(REGISTRAR_TRAMA_DATA + parametroMap.get(UUID_TRAZA) + ".inicio.data "
				+ respuestaCargaDataTramaVO.getListaConfiguracionTramaDataVO().getListaKey().size() + " "
				+ FechaUtil.obtenerFechaActual());
		Map<String, StringBuilder> sqlMap = new HashMap<>();
		List<Map<String, Object>> listaParametrosMap = new ArrayList<>();
		int dataPosicion = 1;
		int cotadorOrden = 0;
		for (var configuracionTramaDataKey : respuestaCargaDataTramaVO.getListaConfiguracionTramaDataVO()
				.getListaKey()) {
			cotadorOrden++;
			ConfiguracionTramaDataVO configuracionTramaData = respuestaCargaDataTramaVO
					.getListaConfiguracionTramaDataVO().get(configuracionTramaDataKey);
			Map<String, ValueDataVO> dataResulMapTemp = new HashMap<>();
			var parametros = new HashMap<String, Object>();
			StringBuilder sql = new StringBuilder();
			String tableName = configuracionTramaData.getSchemaName() + "." + configuracionTramaData.getTableName();
			try {
				int cantidad = obtenerCantidad(configuracionTramaData.getListaCampoValue());
				int contador = 0;
				int contadorPametroIndex = 0;
				String shemaName = "";
				if (!StringUtil.isNullOrEmpty(configuracionTramaData.getSchemaName())) {
					shemaName = configuracionTramaData.getSchemaName() + ".";
				}
				StringBuilder sqlHeader = new StringBuilder();
				StringBuilder sqlValue = new StringBuilder();
				sql.append("insert into " + shemaName + "" + configuracionTramaData.getTableName() + " (");
				sqlValue.append(" values(");
				for (var objValue : configuracionTramaData.getListaCampoValue()) {
					dataResulMapTemp.put(objValue.getAtributeName(), objValue.getAtributeValue());
					String separador = ",";
					contador++;
					if (contador >= cantidad) {
						separador = "";
					}
					TipoCampoType tipoCampoType = TipoCampoType.get(objValue.getAtributeType());
					if (tipoCampoType != null) {
						switch (tipoCampoType) {
							case FECHA:
								if (!StringUtil.isNullOrEmpty(objValue.getAtributeValue())) {
									if (objValue.getAtributeValue().getData().toString().contains(FUNC)) {
										sqlHeader.append(objValue.getAtributeName() + separador);
										sqlValue.append(
												objValue.getAtributeValue().getData().toString().replace(FUNC, "")
														+ separador);
									} else {
										sqlHeader.append(objValue.getAtributeName() + separador);
										String formato = "dd/MM/yyyy";
										String keyFormat = objValue.getAtributeName() + "Format";
										sqlValue.append("to_date(?,?)" + separador);
										contadorPametroIndex++;
										parametros.put(contadorPametroIndex + "",
												FechaUtil.obtenerFechaFormatoPersonalizado(
														(OffsetDateTime) objValue.getAtributeValue().getData(),
														formato));
										contadorPametroIndex++;
										parametros.put(contadorPametroIndex + "", formato);
									}

								} else {
									sqlHeader.append(objValue.getAtributeName() + separador);
									sqlValue.append("?" + separador);
									contadorPametroIndex++;
									parametros.put(contadorPametroIndex + "", null);
								}
								break;
							case NUMERICO:
								if (!StringUtil.isNullOrEmpty(objValue.getAtributeValue())) {
									if (objValue.getAtributeValue().getData().toString().contains(FUNC_CONTADOR)) {
										sqlHeader.append(objValue.getAtributeName() + separador);
										sqlValue.append("?" + separador);
										contadorPametroIndex++;
										parametros.put(contadorPametroIndex + "", cotadorOrden);
									} else {
										sqlHeader.append(objValue.getAtributeName() + separador);
										sqlValue.append("?" + separador);
										contadorPametroIndex++;
										parametros.put(contadorPametroIndex + "",
												objValue.getAtributeValue().getData());
									}

								} else {
									sqlHeader.append(objValue.getAtributeName() + separador);
									sqlValue.append("?" + separador);
									contadorPametroIndex++;
									parametros.put(contadorPametroIndex + "", null);
								}

								break;

							case TEXTO:
								if (!StringUtil.isNullOrEmpty(objValue.getAtributeValue())) {
									if (objValue.getAtributeValue().getData().toString().contains(FUNC_UUID)) {
										sqlHeader.append(objValue.getAtributeName() + separador);
										sqlValue.append("?" + separador);
										contadorPametroIndex++;
										parametros.put(contadorPametroIndex + "", UUIDUtil.generarElementUUID());
									} else {
										sqlHeader.append(objValue.getAtributeName() + separador);
										sqlValue.append("?" + separador);
										contadorPametroIndex++;
										parametros.put(contadorPametroIndex + "",
												objValue.getAtributeValue().getData());
									}
								} else {
									sqlHeader.append(objValue.getAtributeName() + separador);
									sqlValue.append("?" + separador);
									contadorPametroIndex++;
									parametros.put(contadorPametroIndex + "", null);
								}
								break;
							default:
								if (!StringUtil.isNullOrEmpty(objValue.getAtributeValue())) {
									sqlHeader.append(objValue.getAtributeName() + separador);
									sqlValue.append("?" + separador);
									contadorPametroIndex++;
									parametros.put(contadorPametroIndex + "", objValue.getAtributeValue().getData());
								} else {
									sqlHeader.append(objValue.getAtributeName() + separador);
									sqlValue.append("?" + separador);
									contadorPametroIndex++;
									parametros.put(contadorPametroIndex + "", null);
								}
								break;
						}
					} else {
						if (!StringUtil.isNullOrEmpty(objValue.getAtributeValue())) {
							sqlHeader.append(objValue.getAtributeName() + separador);
							sqlValue.append("?" + separador);
							contadorPametroIndex++;
							parametros.put(contadorPametroIndex + "", objValue.getAtributeValue().getData());
						} else {
							sqlHeader.append(objValue.getAtributeName() + separador);
							sqlValue.append("?" + separador);
							contadorPametroIndex++;
							parametros.put(contadorPametroIndex + "", null);
						}
					}
				}
				sqlHeader.append(")");
				sqlValue.append(")");
				sql.append(sqlHeader.toString());
				sql.append(sqlValue.toString());
				// Inicio BuildSoft Configuracion de caja raiz
				if (contador > 0) {
					if (!sqlMap.containsKey("SQL")) {
						sqlMap.put("SQL", sql);
					}
					listaParametrosMap.add(parametros);
				}
				// Fin BuildSoft Configuracion de caja raiz
			} catch (Exception e) {
				LOG.error(REGISTRAR_TRAMA_DATA + parametroMap.get(UUID_TRAZA) + ".proceso.error " + e.getMessage() + " "
						+ FechaUtil.obtenerFechaActual());
				// Inicio BuildSoft Mejoras Interconexion
				String mensajeError = e.getMessage() + " --> No se pudo registrar en la tabla " + tableName
						+ ",registro nro " + dataPosicion + "";
				// Fin BuildSoft Mejoras Interconexion
				respuestaCargaDataTramaVO.setMensajeError(mensajeError);
				respuestaCargaDataTramaVO.setEsError(true);
				// imprimir log
			}
			dataPosicion++;
		}
		if (sqlMap.containsKey("SQL")) {
			int batch = 2000;
			Integer cantidadRegitrosActualizados = 0;
			int contadorBatch = 1;
			GenericBatchJDBC genericBatchJDBC = new GenericBatchJDBC();
			if (parametroMap.containsKey(JNDI_CONEXION)) {
				genericBatchJDBC.getConexionDS(parametroMap.get(JNDI_CONEXION).toString());
			} else {
				genericBatchJDBC.getConexionDS();
			}

			genericBatchJDBC.prepareStatement(sqlMap.get("SQL"));
			for (var data : listaParametrosMap) {
				Map<String, Object> parametros = data;
				genericBatchJDBC.addBatch(parametros);
				if (contadorBatch == batch) {
					int[] actulizados = genericBatchJDBC.executeBatch();
					contadorBatch = 1;
					cantidadRegitrosActualizados = cantidadRegitrosActualizados + actulizados.length;
					LOG.error(REGISTRAR_TRAMA_DATA + parametroMap.get(UUID_TRAZA) + ".proceso.registros.actualizados "
							+ cantidadRegitrosActualizados + " " + FechaUtil.obtenerFechaActual());
					genericBatchJDBC = new GenericBatchJDBC();
					if (parametroMap.containsKey(JNDI_CONEXION)) {
						genericBatchJDBC.getConexionDS(parametroMap.get(JNDI_CONEXION).toString());
					} else {
						genericBatchJDBC.getConexionDS();
					}
					genericBatchJDBC.prepareStatement(sqlMap.get("SQL"));
				}
				contadorBatch++;
			}
			int[] actulizados = genericBatchJDBC.executeBatch();
			cantidadRegitrosActualizados = cantidadRegitrosActualizados + actulizados.length;
			LOG.error(REGISTRAR_TRAMA_DATA + parametroMap.get(UUID_TRAZA) + ".proceso.registros.actualizados "
					+ cantidadRegitrosActualizados + " " + FechaUtil.obtenerFechaActual());
		}

		// limpiando memoria
		return respuestaCargaDataTramaVO;
	}

	@Override
	public BigMemoryManager<String, ConfiguracionTramaDataVO> generarTuplaPersistente(
			List<Map<String, ValueDataVO>> listaDataResulGrupoMap, Map<Integer, Boolean> errorTuplaMap,
			String[] schemaTableName, Map<String, ConfiguracionTramaDetalleVO> configuracionTramaDetalleMap,
			Map<String, String> campoNoPersistente, Map<String, Object> parametroMap) {
		LOG.error("generarTupaPersistente." + parametroMap.get(UUID_TRAZA) + INICIO + FechaUtil.obtenerFechaActual());
		String rutaRelativaTemp = parametroMap.get("rutaRelativaTemp") + "";
		String rutaRelativaTempTable = ConstanteConfigUtil.generarRuta(rutaRelativaTemp)
				+ schemaTableName[1].toLowerCase();
		BigMemoryManager<String, ConfiguracionTramaDataVO> resultadoListaConf = new BigMemoryManager<>(
				rutaRelativaTempTable, "data_persist_table");
		int errorTupaIndice = 0;
		for (var map : listaDataResulGrupoMap) {
			ConfiguracionTramaDataVO resultadoTemp = new ConfiguracionTramaDataVO();
			resultadoTemp.setSchemaName(schemaTableName[0]);
			resultadoTemp.setTableName(schemaTableName[1]);
			List<ConfiguracionTramaDataVO> listaCampoValue = new ArrayList<>();
			boolean existeErrorTupla = false;
			for (var mapValue : map.entrySet()) {
				if (!CAMPO_MENSAJE_ERROR.equals(mapValue.getKey()) && !mapValue.getKey().contains(C_ERROR)) {
					errorTupaIndice++;
					ConfiguracionTramaDataVO resultadoCampoValue = new ConfiguracionTramaDataVO();
					resultadoCampoValue.setSchemaName(schemaTableName[0]);
					resultadoCampoValue.setTableName(schemaTableName[1]);
					resultadoCampoValue.setAtributeName(mapValue.getKey());
					// validar
					ConfiguracionTramaDetalleVO configuracionTramaDetalle = configuracionTramaDetalleMap
							.get(mapValue.getKey());
					boolean esCampoNegocio = false;
					if (RespuestaNaturalType.SI.getKey().equals(configuracionTramaDetalle.getFlagCampoNegocio())) {
						esCampoNegocio = true;
					}
					resultadoCampoValue.setEsCampoNegocio(esCampoNegocio);
					TipoCampoType tipoCampoType = obtenerTipoCampo(configuracionTramaDetalle);
					resultadoCampoValue.setAtributeValue(mapValue.getValue());
					resultadoCampoValue.setAtributeType(tipoCampoType.getKey());
					if (!campoNoPersistente.containsKey(mapValue.getKey())) {
						listaCampoValue.add(resultadoCampoValue);
					}
					if ((!existeErrorTupla) && errorTuplaMap.containsKey(errorTupaIndice)) {
						existeErrorTupla = errorTuplaMap.get(errorTupaIndice);
					}
				}
			}

			CollectionUtil.ordenador(false, listaCampoValue, "atributeName");
			resultadoTemp.setListaCampoValue(listaCampoValue);
			if (!existeErrorTupla) {
				resultadoListaConf.add(resultadoTemp);
			}
		}
		LOG.error("generarTupaPersistente." + parametroMap.get(UUID_TRAZA) + ".fin " + FechaUtil.obtenerFechaActual());
		return resultadoListaConf;
	}

	@Override
	public TipoCampoType obtenerTipoCampo(ConfiguracionTramaDetalleVO configuracionTramaDetalle) {
		TipoCampoType tipoCampoType = TipoCampoType.get(configuracionTramaDetalle.getTipoCampo());

		CampoFijoType campoFijoType = CampoFijoType.get(configuracionTramaDetalle.getCampoFijo());
		if (campoFijoType != null) {
			switch (campoFijoType) {
				case CAMPO_CONTADOR:
				case CAMPO_SUMA:
				case CAMPO_PROMEDIO:
					tipoCampoType = TipoCampoType.NUMERICO;
					return tipoCampoType;
				default:
					break;
			}
		}

		if (configuracionTramaDetalle.getCampoAsociado() != null) {
			tipoCampoType = TipoCampoType.get(configuracionTramaDetalle.getCampoAsociado().getTipoCampo());
		}
		if (tipoCampoType == null && configuracionTramaDetalle.getCampoFijo() != null) {
			campoFijoType = CampoFijoType.get(configuracionTramaDetalle.getCampoFijo());
			switch (campoFijoType) {
				case NUMERO_RIESGO:
				case NUMERO_SECUENCIAL:
					tipoCampoType = TipoCampoType.NUMERICO;
					break;

				case NUMERO_LOTE:
					tipoCampoType = TipoCampoType.TEXTO;
					break;

				case FECHA_LOTE:
					tipoCampoType = TipoCampoType.FECHA;
					break;

				default:
					break;
			}
		}
		if (tipoCampoType == null) {
			tipoCampoType = TipoCampoType.TEXTO;
		}
		return tipoCampoType;
	}

	protected Integer obtenerCantidad(List<ConfiguracionTramaDataVO> listaCampoValue) {
		return listaCampoValue.size();
	}
}
