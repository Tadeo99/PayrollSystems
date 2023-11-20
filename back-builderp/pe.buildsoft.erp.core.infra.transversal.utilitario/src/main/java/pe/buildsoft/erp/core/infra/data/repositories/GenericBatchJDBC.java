package pe.buildsoft.erp.core.infra.data.repositories;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.buildsoft.erp.core.infra.transversal.cache.ConfiguracionCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class GenericBatchJDBC.
 *
 * @author BuildSoft.
 * @version 1.0 , 17/10/2017
 * @since BuildErp 1.0
 */
public class GenericBatchJDBC implements Serializable {

	private static final String FLECHA = " ==> ";

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** La log. */
	private static Logger log = LoggerFactory.getLogger(GenericBatchJDBC.class);

	/** La connection. */
	private Connection connection = null;

	/** La pst. */
	private PreparedStatement pst = null;

	/** La execute. */
	private boolean execute = false;

	/** La cantidad. */
	private int cantidad = 0;

	/** La nombre. */
	private String nombre = "";

	/**
	 * Prepare statement.
	 *
	 * @param sql    el sql
	 * @param nombre el nombre
	 * @throws Exception the exception
	 */
	public void prepareStatement(StringBuilder sql, String nombre) throws SQLException {
		execute = false;
		this.nombre = nombre;
		pst = connection.prepareStatement(sql.toString());
	}

	/**
	 * Prepare statement.
	 *
	 * @param sql el sql
	 * @throws Exception the exception
	 */
	public void prepareStatement(StringBuilder sql) throws SQLException {
		execute = false;
		pst = connection.prepareStatement(sql.toString());
	}

	/**
	 * Add batch.
	 *
	 * @param parametros el parametros
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @throws Exception             the exception
	 */
	public void addBatch(Map<String, Object> parametros) throws NumberFormatException, SQLException {
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
					pst.setBigDecimal(Integer.parseInt(objParamMap.getKey()), (BigDecimal) objParamMap.getValue());
				} else {
					pst.setObject(Integer.parseInt(objParamMap.getKey()), objParamMap.getValue());
				}
			} else {
				pst.setObject(Integer.parseInt(objParamMap.getKey()), objParamMap.getValue());
			}
		}
		if (parametros.size() > 0) {
			execute = true;
			cantidad++;
			pst.addBatch();
		}
	}

	/**
	 * Execute batch.
	 *
	 * @return the int[]
	 * @throws NamingException
	 * @throws SQLException
	 * @throws Exception       the exception
	 */
	public synchronized int[] executeBatch() throws NamingException, SQLException {
		if (execute) {
			if (isPrintLog()) {
				log.error("inicio " + nombre + " executeBatch " + FechaUtil.obtenerFechaActual() + " " + cantidad
						+ FLECHA + execute);
			}
			if (connection.isClosed()) {
				getConexionDS();
				if (isPrintLog()) {
					log.error("inicio " + nombre + " executeBatch.openConexion " + FechaUtil.obtenerFechaActual() + " "
							+ cantidad + FLECHA + execute);
				}
			}
		}
		var resultado = new int[0];
		try {
			if (execute) {
				resultado = pst.executeBatch();
			}
		} finally {
			pst.clearParameters();
			pst.clearBatch();
			pst.close();
			connection.close();
		}
		if (execute) {
			if (isPrintLog()) {
				log.error("fin " + nombre + "  executeBatch " + FechaUtil.obtenerFechaActual() + "  " + resultado.length
						+ FLECHA + execute);
			}
		}
		return resultado;
	}

	private boolean isPrintLog() {
		return ConfiguracionCacheUtil.getInstance().isPrintLogProcesos("execute_batch");
	}

	/**
	 * Comprueba si es empty.
	 *
	 * @param list el list
	 * @return true, si es empty
	 */
	public boolean isEmpty(List<?> list) {
		var respuesta = false;
		if (list == null || list.isEmpty()) {
			respuesta = true;
		}
		return respuesta;
	}

	/**
	 * Obtiene conexion ds.
	 *
	 * @return conexion ds
	 * @throws SQLException
	 * @throws Exception    the exception
	 */
	public void getConexionDS() throws NamingException, SQLException {
		var ctx = new InitialContext();
		var dataSource = (DataSource) ctx.lookup("java:jboss/datasources/commonDS");
		connection = dataSource.getConnection();
	}

	/**
	 * Obtiene conexion ds.
	 *
	 * @param jndiConexion el JNDI conexion
	 * @return conexion ds
	 * @throws SQLException
	 * @throws Exception    the exception
	 */
	public void getConexionDS(String jndiConexion) throws NamingException, SQLException {
		var ctx = new InitialContext();
		var dataSource = (DataSource) ctx.lookup("java:jboss/datasources/" + jndiConexion);
		connection = dataSource.getConnection();
	}

	/**
	 * Obtiene cantidad.
	 *
	 * @return cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Comprueba si es execute.
	 *
	 * @return true, si es execute
	 */
	public boolean isExecute() {
		return execute;
	}

	/**
	 * Obtiene nombre.
	 *
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre.
	 *
	 * @param nombre el new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
