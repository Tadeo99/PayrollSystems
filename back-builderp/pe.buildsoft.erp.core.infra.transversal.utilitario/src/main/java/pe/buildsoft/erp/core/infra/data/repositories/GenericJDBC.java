package pe.buildsoft.erp.core.infra.data.repositories;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ScriptSqlResulJDBCVO;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class GenericJDBC.
 *
 * @author BuildSoft.
 * @version 1.0 , 24/05/2016
 * @param <T> el tipo generico
 * @since BuildErp 1.0
 */
public class GenericJDBC<T> extends BaseTransfer {

	/** La log. */
	private Logger log = LoggerFactory.getLogger(GenericJDBC.class);

	/**
	 * Execute query.
	 *
	 * @param <T>               el tipo generico
	 * @param sql               el sql
	 * @param parametros        el parametros
	 * @param entityClassEntity el entity class entity
	 * @return the t @ the exception
	 */
	public T executeQuery(StringBuilder sql, Map<String, Object> parametros, Class<T> entityClassEntity) {
		return toEntityVO(executeSql(sql, parametros, null, true, false), entityClassEntity);
	}

	public T executeQueryPreparedStatement(StringBuilder sql, Map<String, Object> parametros,
			Class<T> entityClassEntity) {
		return toEntityVO(executeSqlPreparedStatement(sql, parametros, null, true, false), entityClassEntity);
	}

	/**
	 * Execute query.
	 *
	 * @param sp          el sql
	 * @param parametros  el parametros
	 * @param isProcedure el is procedure
	 * @return the script sql resul jdbcvo @ the exception
	 */
	public ScriptSqlResulJDBCVO executeQuerySP(StringBuilder sp) {
		List<Integer> parametroType = new ArrayList<>();
		parametroType.add(java.sql.Types.VARCHAR);
		return ejecutarScriptSqlSP(sp, null, parametroType, new ArrayList<>());
	}

	protected StringBuilder definirParametroCall(String spEjecutar, int cantidad) {
		var resultado = new StringBuilder();
		resultado.append("{call " + spEjecutar + "(");
		var size = cantidad;
		for (int i = 1; i <= size; i++) {
			resultado.append("?");
			if (!(i == size)) {
				resultado.append(",");
			}
		}
		resultado.append(")} ");
		return resultado;
	}

	/**
	 * Execute query.
	 *
	 * @param sql              el sql
	 * @param parametros       el parametros
	 * @param isProcedure      el is procedure
	 * @param parametroOutType el parametro out type
	 * @param parametroInType  el parametro in type
	 * @return the script sql resul jdbcvo @ the exception
	 */
	public ScriptSqlResulJDBCVO executeQuerySP(String sp, String jndiConexion, List<Integer> parametroOutType,
			List<Object> parametroInType) {
		return ejecutarScriptSqlSP(definirParametroCall(sp, parametroOutType.size() + parametroInType.size()),
				jndiConexion, parametroOutType, parametroInType);
	}

	/**
	 * Execute query.
	 *
	 * @param <T>               el tipo generico
	 * @param sql               el sql
	 * @param parametros        el parametros
	 * @param jndiConexion      el JNDI conexion
	 * @param entityClassEntity el entity class entity
	 * @return the t @ the exception
	 */
	public T executeQuery(StringBuilder sql, Map<String, Object> parametros, String jndiConexion,
			Class<T> entityClassEntity) {
		return toEntityVO(executeSql(sql, parametros, jndiConexion, true, false), entityClassEntity);
	}

	public T executeQueryPreparedStatement(StringBuilder sql, Map<String, Object> parametros, String jndiConexion,
			Class<T> entityClassEntity) {
		return toEntityVO(executeSqlPreparedStatement(sql, parametros, jndiConexion, true, false), entityClassEntity);
	}

	/**
	 * Execute query list.
	 *
	 * @param <T>               el tipo generico
	 * @param sql               el sql
	 * @param parametros        el parametros
	 * @param entityClassEntity el entity class entity
	 * @return the list @ the exception
	 */

	public <T> List<T> executeQueryList(StringBuilder sql, Map<String, Object> parametros, Class<T> entityClassEntity) {
		return toEntityListVO(executeSql(sql, parametros, null, true, false), entityClassEntity);
	}

	public List<T> executeQueryListPreparedStatement(StringBuilder sql, Map<String, Object> parametros,
			Class<T> entityClassEntity) {
		return toEntityListVO(executeSqlPreparedStatement(sql, parametros, null, true, false), entityClassEntity);
	}

	/**
	 * Execute query list.
	 *
	 * @param <T>               el tipo generico
	 * @param sql               el sql
	 * @param parametros        el parametros
	 * @param entityClassEntity el entity class entity
	 * @param formatoMap        el formato map
	 * @return the list @ the exception
	 */

	public List<T> executeQueryList(StringBuilder sql, Map<String, Object> parametros, Class<T> entityClassEntity,
			Map<String, String> formatoMap) {
		return toEntityListVO(executeSql(sql, parametros, null, true, false), entityClassEntity, formatoMap);
	}

	public List<T> executeQueryListPreparedStatement(StringBuilder sql, Map<String, Object> parametros,
			Class<T> entityClassEntity, Map<String, String> formatoMap) {
		return toEntityListVO(executeSqlPreparedStatement(sql, parametros, null, true, false), entityClassEntity,
				formatoMap);
	}

	/**
	 * Execute query list.
	 *
	 * @param sql        el sql
	 * @param parametros el parametros
	 * @return the list @ the exception
	 */

	public List<Object[]> executeQueryList(StringBuilder sql, Map<String, Object> parametros) {
		return executeSql(sql, parametros, null, true, true).getListaDataObject();
	}

	public List<Object[]> executeQueryListPreparedStatement(StringBuilder sql, Map<String, Object> parametros) {
		return executeSqlPreparedStatement(sql, parametros, null, true, true).getListaDataObject();
	}

	/**
	 * Execute query list.
	 *
	 * @param <T>               el tipo generico
	 * @param sql               el sql
	 * @param parametros        el parametros
	 * @param jndiConexion      el JNDI conexion
	 * @param entityClassEntity el entity class entity
	 * @return the list @ the exception
	 */
	public List<T> executeQueryList(StringBuilder sql, Map<String, Object> parametros, String jndiConexion,
			Class<T> entityClassEntity) {
		return toEntityListVO(executeSql(sql, parametros, jndiConexion, true, false), entityClassEntity);
	}

	public List<T> executeQueryListPreparedStatement(StringBuilder sql, Map<String, Object> parametros,
			String jndiConexion, Class<T> entityClassEntity) {
		return toEntityListVO(executeSqlPreparedStatement(sql, parametros, jndiConexion, true, false),
				entityClassEntity);
	}

	/**
	 * Execute query.
	 *
	 * @param sql        el sql
	 * @param parametros el parametros
	 * @return the script sql resul jdbcvo @ the exception
	 */

	public ScriptSqlResulJDBCVO executeQuery(StringBuilder sql, Map<String, Object> parametros) {
		return executeSql(sql, parametros, null, true, false);
	}

	public ScriptSqlResulJDBCVO executeQueryPreparedStatement(StringBuilder sql, Map<String, Object> parametros) {
		return executeSqlPreparedStatement(sql, parametros, null, true, false);
	}

	/**
	 * Execute query.
	 *
	 * @param sql          el sql
	 * @param parametros   el parametros
	 * @param jndiConexion el JNDI conexion
	 * @return the script sql resul jdbcvo @ the exception
	 */

	public ScriptSqlResulJDBCVO executeQuery(StringBuilder sql, Map<String, Object> parametros, String jndiConexion) {
		return executeSql(sql, parametros, jndiConexion, true, false);
	}

	public ScriptSqlResulJDBCVO executeQuery(StringBuilder sql, Map<String, Object> parametros, String jndiConexion,
			boolean isNulale) {
		return executeSql(sql, parametros, jndiConexion, true, false, isNulale);
	}

	public ScriptSqlResulJDBCVO executeQueryPreparedStatement(StringBuilder sql, Map<String, Object> parametros,
			String jndiConexion) {
		return executeSqlPreparedStatement(sql, parametros, jndiConexion, true, false);
	}

	/**
	 * Execute update.
	 *
	 * @param sql        el sql
	 * @param parametros el parametros
	 * @return the script sql resul jdbcvo @ the exception
	 */

	public ScriptSqlResulJDBCVO executeUpdate(StringBuilder sql, Map<String, Object> parametros) {
		return executeSql(sql, parametros, null, false, false);
	}

	public ScriptSqlResulJDBCVO executeUpdatePreparedStatement(StringBuilder sql, Map<String, Object> parametros) {
		return executeSqlPreparedStatement(sql, parametros, null, false, false);
	}

	/**
	 * Execute update.
	 *
	 * @param sql          el sql
	 * @param parametros   el parametros
	 * @param jndiConexion el JNDI conexion
	 * @return the script sql resul jdbcvo @ the exception
	 */
	public ScriptSqlResulJDBCVO executeUpdate(StringBuilder sql, Map<String, Object> parametros, String jndiConexion) {
		return executeSql(sql, parametros, jndiConexion, false, false);
	}

	public ScriptSqlResulJDBCVO executeUpdatePreparedStatement(StringBuilder sql, Map<String, Object> parametros,
			String jndiConexion) {
		return executeSqlPreparedStatement(sql, parametros, jndiConexion, false, false);
	}

	/**
	 * Ejecutar script sql.
	 *
	 * @param sql              el sql
	 * @param parametros       el parametros
	 * @param jndiConexion     el JNDI conexion
	 * @param isConsulta       el is consulta
	 * @param devolverOject    el devolver oject
	 * @param isProcedure      el is procedure
	 * @param parametroOutType el parametro out type
	 * @return the script sql resul jdbcvo @ the exception
	 */
	private ScriptSqlResulJDBCVO executeSql(StringBuilder sql, Map<String, Object> parametros, String jndiConexion,
			boolean isConsulta, boolean devolverOject) {
		return executeSql(sql, parametros, jndiConexion, isConsulta, devolverOject, false);
	}

	private ScriptSqlResulJDBCVO executeSql(StringBuilder sql, Map<String, Object> parametros, String jndiConexion,
			boolean isConsulta, boolean devolverOject, boolean isNulale) {
		var resultado = new ScriptSqlResulJDBCVO();
		var jpaql = sql.toString();
		Connection connection = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			if (jndiConexion == null) {
				connection = getConexionDS();
			} else {
				connection = getConexionDS(jndiConexion);
			}
		} catch (Exception e) {
			log.error("executeSql", e);
			resultado.setTieneError(true);
			resultado.setMensajeError(e.getMessage() + " \n " + e.toString());
			return resultado;
		}

		if (parametros != null) {
			for (var objParamMap : parametros.entrySet()) {
				if (objParamMap.getValue() != null) {
					if (objParamMap.getValue().getClass().isAssignableFrom(String.class)) {
						jpaql = jpaql.replaceAll(":" + objParamMap.getKey(),
								"'" + escapeSql((String) objParamMap.getValue()) + "'");
						// jpaql = jpaql.replaceAll(":" + objParamMap.getKey() , "'" +
						// ((String)objParamMap.getValue()) + "'");
					} else if (objParamMap.getValue().getClass().isAssignableFrom(Character.class)) {
						jpaql = jpaql.replaceAll(":" + objParamMap.getKey(), "'" + objParamMap.getValue() + "'");
					} else if (objParamMap.getValue().getClass().isAssignableFrom(OffsetDateTime.class)) {
						String formato = "yyyyMMdd HH:mm:ss";
						String formatoBD = "yyyymmdd HH24:MI:SS";
						String dateValue = "to_date('" + FechaUtil.obtenerFechaFormatoPersonalizado(
								(OffsetDateTime) objParamMap.getValue(), formato) + "','" + formatoBD + "')";
						jpaql = jpaql.replaceAll(":" + objParamMap.getKey(), dateValue);
					} else if (objParamMap.getValue().getClass().isAssignableFrom(ArrayList.class)) {
						jpaql = jpaql.replaceAll(":" + objParamMap.getKey(),
								objParamMap.getValue().toString().replace("[", "").replace("]", "").trim() + "");
					} else {
						jpaql = jpaql.replaceAll(":" + objParamMap.getKey(), objParamMap.getValue() + "");
					}

				} else {
					jpaql = jpaql.replaceAll(":" + objParamMap.getKey(), "''");
				}
			}
		}
		try {
			st = connection.prepareStatement(jpaql);
			if (isConsulta) {
				log.info("jpaql " + jpaql);
				rs = st.executeQuery();
				resultado = generarListMap(rs, devolverOject, isNulale);
			} else {
				log.info("jpaql " + jpaql);
				st.executeUpdate();
			}

		} catch (Exception e) {
			resultado.setTieneError(true);
			resultado.setMensajeError(e.getMessage() + " \n " + e.toString());
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					resultado.setTieneError(true);
					resultado.setMensajeError(e.getMessage() + " \n " + e.toString());
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					resultado.setTieneError(true);
					resultado.setMensajeError(e.getMessage() + " \n " + e.toString());
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					resultado.setTieneError(true);
					resultado.setMensajeError(e.getMessage() + " \n " + e.toString());
				}
			}

		}
		return resultado;
	}

	/*
	 * sql = "select * from mytabla where (fecha=? or fecha=?) and fechaFin<? "
	 * parametros.put("1","01/01/2010"); parametros.put("2","01/01/2011");
	 * parametros.put("3","01/01/2015");
	 */
	private ScriptSqlResulJDBCVO executeSqlPreparedStatement(StringBuilder sql, Map<String, Object> parametros,
			String jndiConexion, boolean isConsulta, boolean devolverOject) {
		return executeSqlPreparedStatement(sql, parametros, jndiConexion, isConsulta, devolverOject, false);
	}

	private ScriptSqlResulJDBCVO executeSqlPreparedStatement(StringBuilder sql, Map<String, Object> parametros,
			String jndiConexion, boolean isConsulta, boolean devolverOject, boolean isNulable) {
		var resultado = new ScriptSqlResulJDBCVO();
		var jpaql = sql.toString();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			if (jndiConexion == null) {
				connection = getConexionDS();
			} else {
				connection = getConexionDS(jndiConexion);
			}
		} catch (Exception e) {
			log.error("executeSqlPreparedStatement", e);
			resultado.setTieneError(true);
			resultado.setMensajeError(e.getMessage() + " \n " + e.toString());
			return resultado;
		}
		try {
			pst = connection.prepareStatement(jpaql);
			if (parametros != null) {
				for (var objParamMap : parametros.entrySet()) {

					if (objParamMap.getValue() != null) {
						if (objParamMap.getValue().getClass().isAssignableFrom(String.class)) {
							pst.setString(Integer.parseInt(objParamMap.getKey()), (String) objParamMap.getValue());
						} else if (objParamMap.getValue().getClass().isAssignableFrom(Character.class)) {
							pst.setString(Integer.parseInt(objParamMap.getKey()), objParamMap.getValue().toString());
						} else if (objParamMap.getValue().getClass().isAssignableFrom(OffsetDateTime.class)) {
							OffsetDateTime ldt = ((OffsetDateTime) objParamMap.getValue());
							java.sql.Timestamp dateTime = Timestamp.valueOf(ldt.toLocalDateTime());
							pst.setTimestamp(Integer.parseInt(objParamMap.getKey()), dateTime);
						} else if (objParamMap.getValue().getClass().isAssignableFrom(java.sql.Timestamp.class)) {
							java.sql.Timestamp dateTime = (java.sql.Timestamp) objParamMap.getValue();
							pst.setTimestamp(Integer.parseInt(objParamMap.getKey()), dateTime);
						} else if (objParamMap.getValue().getClass().isAssignableFrom(ArrayList.class)) {
							pst.setObject(Integer.parseInt(objParamMap.getKey()),
									objParamMap.getValue().toString().replace("[", "").replace("]", "").trim() + "");
						} else if (objParamMap.getValue().getClass().isAssignableFrom(Integer.class)) {
							pst.setInt(Integer.parseInt(objParamMap.getKey()), (Integer) objParamMap.getValue());
						} else if (objParamMap.getValue().getClass().isAssignableFrom(Long.class)) {
							pst.setLong(Integer.parseInt(objParamMap.getKey()), (Long) objParamMap.getValue());
						} else if (objParamMap.getValue().getClass().isAssignableFrom(Double.class)) {
							pst.setDouble(Integer.parseInt(objParamMap.getKey()), (Double) objParamMap.getValue());
						} else if (objParamMap.getValue().getClass().isAssignableFrom(Float.class)) {
							pst.setFloat(Integer.parseInt(objParamMap.getKey()), (Float) objParamMap.getValue());
						} else if (objParamMap.getValue().getClass().isAssignableFrom(BigDecimal.class)) {
							pst.setBigDecimal(Integer.parseInt(objParamMap.getKey()),
									(BigDecimal) objParamMap.getValue());
						} else {
							pst.setObject(Integer.parseInt(objParamMap.getKey()), objParamMap.getValue());
						}
					} else {
						pst.setObject(Integer.parseInt(objParamMap.getKey()), objParamMap.getValue());
					}
				}
			}
			if (isConsulta) {
				rs = pst.executeQuery();
				resultado = generarListMap(rs, devolverOject, isNulable);
			} else {
				var dataProcesado = pst.executeUpdate();
				resultado.setExecuteUpdate(dataProcesado);
			}

		} catch (Exception e) {
			System.err.println("jpaql " + jpaql);
			log.error("executeSqlPreparedStatement", e);
			resultado.setTieneError(true);
			resultado.setMensajeError(e.getMessage() + " \n " + e.toString());
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				log.error("executeSqlPreparedStatement", e2);
			}

		}

		return resultado;
	}

	private ScriptSqlResulJDBCVO generarListMap(ResultSet rs, boolean devolverOject, boolean isNulale)
			throws SQLException {
		var resultado = new ScriptSqlResulJDBCVO();
		List<String> listaHeader = new ArrayList<>();
		List<Map<String, Object>> listaData = new ArrayList<>();
		List<Object[]> listaDataOject = new ArrayList<>();
		var i = 1;
		var metadata = rs.getMetaData();
		for (i = 0; i < metadata.getColumnCount(); i++) {
			listaHeader.add(metadata.getColumnLabel(i + 1));
		}
		while (rs.next()) {
			Map<String, Object> dataMap = new HashMap<>();
			for (i = 0; i < metadata.getColumnCount(); i++) {
				var nombreCampo = metadata.getColumnLabel(i + 1);
				Object value = null;
				if ("rownum".equalsIgnoreCase(nombreCampo)) {
					continue;
				}
				if ("IDU".equals(nombreCampo) || "ROWID".equals(nombreCampo) || nombreCampo.contains("CLOB")) {
					value = rs.getString(i + 1);
				} else {
					value = rs.getObject(i + 1);
				}
				if (!isNulale) {
					value = value == null ? "" : value;
				}

				dataMap.put(metadata.getColumnLabel(i + 1), value);
			}
			listaData.add(dataMap);
			if (devolverOject) {
				var data = new Object[metadata.getColumnCount()];
				for (i = 0; i < metadata.getColumnCount(); i++) {
					Object value = rs.getObject(i + 1);
					data[i] = value == null ? "" : value;
				}
				listaDataOject.add(data);
			}
		}
		resultado.setListaDataObject(listaDataOject);
		resultado.setListaData(listaData);
		resultado.setListaHeader(listaHeader);
		return resultado;
	}

	private ScriptSqlResulJDBCVO generarResulSPMap(ScriptSqlResulJDBCVO resultado, Integer indexOutPut, int indexResul,
			CallableStatement cst, List<Integer> listaIndexOutPut) throws SQLException {
		var listaData = resultado.getListaData();
		var dataMap = resultado.getResulMap();
		var resultadoSp = cst.getObject(indexOutPut);// pasear salidas
		dataMap.put("resultado" + indexResul, resultadoSp);
		listaData.add(dataMap);
		return resultado;
	}

	private ScriptSqlResulJDBCVO ejecutarScriptSqlSP(StringBuilder sql, String jndiConexion,
			List<Integer> parametroOutType, List<Object> parametroInType) {
		return ejecutarScriptSqlSP(sql, jndiConexion, parametroOutType, parametroInType, false);
	}

	private ScriptSqlResulJDBCVO ejecutarScriptSqlSP(StringBuilder sql, String jndiConexion,
			List<Integer> parametroOutType, List<Object> parametroInType, boolean isNulale) {
		var resultado = new ScriptSqlResulJDBCVO();
		var jpaql = sql.toString();
		Connection connection = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		try {
			if (jndiConexion == null) {
				connection = getConexionDS();
			} else {
				connection = getConexionDS(jndiConexion);
			}
		} catch (Exception e) {
			log.error("ejecutarScriptSqlSP", e);
			resultado.setTieneError(true);
			resultado.setMensajeError(e.getMessage() + " \n " + e.toString());
			return resultado;
		}
		try {
			log.info("jpaql " + jpaql);
			cst = connection.prepareCall(sql.toString());
			var index = 1;
			List<Integer> listaIndexOutPut = new ArrayList<>();
			Map<Integer, Integer> indexOutPutMap = new HashMap<>();
			for (var objParamIn : parametroInType) {
				// Inicio BuildSoft Reporte Siniestro 01
				if (objParamIn != null) {
					if (objParamIn instanceof Boolean value) {
						cst.setBoolean(index, value);
					} else if (objParamIn instanceof BigDecimal value) {
						cst.setBigDecimal(index, value);
					} else if (objParamIn instanceof Long value) {
						cst.setLong(index, value);
					} else if (objParamIn instanceof Integer value) {
						cst.setInt(index, value);
					} else if (objParamIn instanceof OffsetDateTime value) {
						java.sql.Date jdbcDate = java.sql.Date.valueOf(value.toLocalDate());
						cst.setDate(index, jdbcDate);
					} else if (objParamIn instanceof java.sql.Date value) {
						java.sql.Date jdbcDate = value;
						cst.setDate(index, jdbcDate);
					} else if (objParamIn instanceof String value) {
						cst.setString(index, escapeSql(value));
					} else if (objParamIn instanceof Character) {
						cst.setString(index, objParamIn + "");
					} else {
						cst.setObject(index, objParamIn);
					}
				} else {
					cst.setObject(index, objParamIn);
				}
				// Fin BuildSoft Reporte Siniestro 01
				index++;
			}
			for (var paramMap : parametroOutType) {
				cst.registerOutParameter(index, paramMap);
				listaIndexOutPut.add(index);
				indexOutPutMap.put(index, paramMap);
				index++;
			}
			cst.execute();
			if (!parametroOutType.isEmpty()) {
				var indexResul = 1;
				var indexResulCursor = 1;
				for (var indexOutPut : listaIndexOutPut) {
					if (Types.REF_CURSOR == indexOutPutMap.get(indexOutPut)) {
						rs = (ResultSet) cst.getObject(indexOutPut);
						var resultadoCursor = generarListMap(rs, false, isNulale);
						resultado.getResulCursorList().add(resultadoCursor);
						resultado.getResulCursorListMap().put("cursor" + indexResulCursor, resultadoCursor);
						indexResulCursor++;
					} else {
						resultado = generarResulSPMap(resultado, indexOutPut, indexResul, cst, listaIndexOutPut);
					}
					indexResul++;
				}
			}

		} catch (Exception e) {
			resultado.setTieneError(true);
			resultado.setMensajeError(e.getMessage() + " \n " + e.toString());
			log.error("ejecutarScriptSqlSP", e);
			// throw e;
		} finally {
			try {
				if (cst != null) {
					cst.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				log.error("ejecutarScriptSqlSP", e2);
			}

		}
		return resultado;
	}

	public static String escapeSql(String str) {
		if (str == null) {
			return null;
		}
		return str.replace("'", "''");
	}

	private boolean existeCursor(List<Integer> listaIndexOutPut, Map<Integer, Integer> indexOutPutMap) {
		var respueta = false;
		for (var indexOutPut : listaIndexOutPut) {
			if (Types.REF_CURSOR == indexOutPutMap.get(indexOutPut)) {
				respueta = true;
				break;
			}
		}
		return respueta;
	}

	/**
	 * Gets the conexion ds.
	 *
	 * @return the conexion ds
	 * @throws NamingException
	 * @throws SQLException    @ the exception
	 */
	public Connection getConexionDS() throws NamingException, SQLException {
		var ctx = new InitialContext();
		var dataSource = (DataSource) ctx.lookup("java:jboss/datasources/commonDS");
		return dataSource.getConnection();
	}

	/**
	 * Gets the conexion ds.
	 *
	 * @param jndiConexion the JNDI conexion
	 * @return the conexion ds
	 * @throws NamingException
	 * @throws SQLException    @ the exception
	 */
	public Connection getConexionDS(String jndiConexion) throws NamingException, SQLException {
		var ctx = new InitialContext();
		var dataSource = (DataSource) ctx.lookup("java:jboss/datasources/" + jndiConexion);
		return dataSource.getConnection();
	}
}
