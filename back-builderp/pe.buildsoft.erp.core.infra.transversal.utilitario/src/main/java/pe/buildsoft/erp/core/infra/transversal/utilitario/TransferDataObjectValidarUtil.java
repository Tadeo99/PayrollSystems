package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.AtributoEntityVO;

/**
 * La Class TransferDataObjectValidarUtil.
 * <ul>
 * <li>Copyright 2014 MAPFRE- mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0,27/10/2016
 * @since BuildErp v1.0
 */
public class TransferDataObjectValidarUtil implements Serializable {

	private static final String SEPARADOR = ";";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** La Constante ARTIFICIO_CLASS. */
	public static final String ARTIFICIO_CLASS = "$class";

	/** La Constante CARACTER_EXTRANHO. */
	public static final String CARACTER_EXTRANHO = "ï»¿";

	public static void defaultLocaleProcess() {
		Locale.setDefault(Locale.US);
	}

	protected static <T> List<AtributoEntityVO> obtenerListaAtributos(Class<T> entityClass) {
		return AtributosEntityCacheUtil.getInstance().obtenerListaAtributos(entityClass);
	}

	protected static <T> List<AtributoEntityVO> obtenerListaAtributosManyToOne(Class<T> entityClass) {
		return AtributosEntityCacheUtil.getInstance().obtenerListaAtributosManyToOne(entityClass);
	}

	protected static List<AtributoEntityVO> obtenerListaAtributos(String entityClass) {
		return AtributosEntityCacheUtil.getInstance().obtenerListaAtributos(entityClass);
	}

	protected static <T> String[] obtenerListaAtributosPK(Class<T> entityClassEntity, String... entityClasess) {
		List<String> resultado = new ArrayList<>();
		List<AtributoEntityVO> listaAtributo = AtributosEntityCacheUtil.getInstance()
				.obtenerListaAtributosManyToOne(entityClassEntity);
		for (var obj : listaAtributo) {
			resultado.add(obj.getNombreAtributo() + "@PK@");
		}
		for (var other : entityClasess) {
			resultado.add(other);
		}
		return resultado.toArray(new String[resultado.size()]);
	}

	public static <T> Field fieldHerenciaSet(T resultado, Map<String, Integer> fieldHerenciaMap,
			AtributoEntityVO objAtr) throws NoSuchFieldException {
		Field f = null;
		if (!fieldHerenciaMap.containsKey(objAtr.getNombreAtributo())) {
			f = resultado.getClass().getDeclaredField(objAtr.getNombreAtributo());
		} else {
			int nivelHerencia = fieldHerenciaMap.get(objAtr.getNombreAtributo());
			int nivel = 0;
			Class<?> currentHerencia = resultado.getClass();
			while (currentHerencia.getSuperclass() != null) {
				nivel++;
				currentHerencia = currentHerencia.getSuperclass();
				if (nivel == nivelHerencia) {
					f = currentHerencia.getDeclaredField(objAtr.getNombreAtributo());
					break;
				}
			}
		}
		if (f != null) {
			f.setAccessible(true);
		}
		return f;
	}

	public static Field getField(Object obj, String atributo) throws NoSuchFieldException, SecurityException {
		Field f = obj.getClass().getDeclaredField(atributo);
		if (f != null) {
			f.setAccessible(true);
		}
		return f;
	}

	public static <T> void setField(Field f, T resultado, Object value)
			throws IllegalArgumentException, IllegalAccessException {
		if (value != null) {
			f.set(resultado, value);
		}
	}

	public static <T> Map<String, Integer> fieldHerenciaMap(T resultado) {
		Map<String, Integer> fieldHerenciaMap = new HashMap<>();
		Class<?> current = resultado.getClass();
		int nivel = 0;
		while (current.getSuperclass() != null) { // we don't want to process Object.class
			// do something with current's fields
			current = current.getSuperclass();
			List<AtributoEntityVO> listaAtributosHerencia = obtenerListaAtributos(current);
			nivel++;
			if (listaAtributosHerencia != null) {
				for (var field : listaAtributosHerencia) {
					fieldHerenciaMap.put(field.getNombreAtributo(), nivel);
				}
			}
		}
		return fieldHerenciaMap;
	}

	public static String obtenerHandlerHibernate(String className) {
		String resultado = "";
		String handlerHibernateKeys = ConfiguracionUtil.getPwrConfUtil("handlerHibernate.impl");
		boolean existeConfiguracion = false;
		if (!StringUtil.isNullOrEmpty(handlerHibernateKeys)) {
			String[] dataKey = handlerHibernateKeys.split(",", -1);
			if (dataKey != null && dataKey.length > 0) {
				for (var handlerHibernate : dataKey) {
					if (className.contains(handlerHibernate)) {
						resultado = handlerHibernate;
						existeConfiguracion = true;
						break;
					}
				}
			}
		}
		if (!existeConfiguracion) {
			resultado = "_$$_javassist";
			if (className.contains("_$$_javassist")) {
				resultado = "_$$_javassist";
			} else if (className.contains("$HibernateProxy")) {
				resultado = "$HibernateProxy";
			} else {
				if (!className.contains("_$$_javassist")) {
					resultado = "_$$_jvst";
				}
			}
		}
		return resultado;

	}

	public static int lengthCaracterExtranho() {
		return CARACTER_EXTRANHO.length() + 1;
	}

	/**
	 * Recortar cadena personalizado.
	 *
	 * @param vIindex el v iindex
	 * @return the string
	 */
	public static StringBuilder recortarCadenaValuePosicion(String vIindex) {
		StringBuilder resultado = new StringBuilder();
		resultado.append(vIindex.substring(0, (vIindex.indexOf(SEPARADOR)) > 0 ? (vIindex.indexOf(SEPARADOR)) : 0));
		return resultado;
	}

	/**
	 * Recortar cadena coordenada valor.
	 *
	 * @param vIindex el v iindex
	 * @return the string builder
	 */
	public static StringBuilder recortarCadenaCoordenadaValor(String vIindex) {
		StringBuilder resultado = new StringBuilder();
		int index1 = vIindex.indexOf(SEPARADOR);
		int index2 = vIindex.indexOf(SEPARADOR, index1 + 1);
		resultado.append(vIindex.substring(index1 + 1, index2));
		return resultado;
	}

	

	/**
	 * Obtener valor.
	 *
	 * @param resultadoValor el resultado valor
	 * @param objAt          el obj at
	 * @param isVO           el is vo
	 * @return the object
	 * @throws Exception the exception
	 */
	public static Object obtenerValor(String resultadoValor, String format, AtributoEntityVO objAt, boolean isVO) {
		Map<String, String> formatoMap = new HashMap<>();
		formatoMap.put(objAt.getNombreAtributo(), format);
		return obtenerValor(resultadoValor, objAt, isVO, formatoMap);
	}

	public static Object obtenerValor(String resultadoValor, AtributoEntityVO objAt, boolean isVO) {
		return obtenerValor(resultadoValor, objAt, isVO, new HashMap<>());
	}

	public static Object obtenerValor(Object resultadoValor, AtributoEntityVO objAt, boolean isVO) {
		return obtenerValorPer(resultadoValor, objAt, isVO, new HashMap<>());
	}

	/**
	 * Obtener valor.
	 *
	 * @param resultadoValor el resultado valor
	 * @param objAt          el obj at
	 * @param isVO           el is vo
	 * @param formatoMap     el formato map
	 * @return the object
	 * @throws Exception the exception
	 */
	public static Object obtenerValor(String resultadoValor, AtributoEntityVO objAt, boolean isVO,
			Map<String, String> formatoMap) {
		return obtenerValorPer(resultadoValor, objAt, isVO, formatoMap);
	}

	public static Object obtenerValorPer(Object resultadoValorPer, AtributoEntityVO objAt, boolean isVO,
			Map<String, String> formatoMap) {
		Object resultado = null;
		if (StringUtil.isNullOrEmpty(resultadoValorPer)) {
			return null;
		}
		String resultadoValor = resultadoValorPer.toString();
		try {
			if (objAt.getClasssAtributoType().toString().equals(Object.class.toString())) {
				resultado = resultadoValor;
			} else if (objAt.getClasssAtributoType().isAssignableFrom(boolean.class)) {
				resultado = Boolean.valueOf(resultadoValor).booleanValue();
			} else if (objAt.getClasssAtributoType().isAssignableFrom(Boolean.class)) {
				resultado = Boolean.valueOf(resultadoValor);
			} else if (objAt.getClasssAtributoType().isAssignableFrom(Integer.class)) {
				resultado = Integer.parseInt(resultadoValor);
			} else if (objAt.getClasssAtributoType().isAssignableFrom(Float.class)) {
				resultado = Float.parseFloat(resultadoValor);
			} else if (objAt.getClasssAtributoType().isAssignableFrom(Double.class)) {
				resultado = Double.parseDouble(resultadoValor);
			} else if (objAt.getClasssAtributoType().isAssignableFrom(Long.class)) {
				resultado = Long.parseLong(resultadoValor);
			} else if (objAt.getClasssAtributoType().isAssignableFrom(BigDecimal.class)) {
				resultado = new BigDecimal(resultadoValor);
			} else if (objAt.getClasssAtributoType().isAssignableFrom(Character.class)) {
				resultado = resultadoValor.charAt(0);
			} else if (objAt.getClasssAtributoType().isAssignableFrom(OffsetDateTime.class)) {
				if (resultadoValorPer instanceof OffsetDateTime) {
					resultado = resultadoValorPer;
				} else {
					if (!isVO) {
						if (formatoMap.containsKey(objAt.getNombreAtributo())) {
							resultado = FechaUtil.obtenerFechaFormatoPersonalizado(resultadoValor,
									formatoMap.get(objAt.getNombreAtributo()));
						} else {
							resultado = FechaUtil.obtenerFecha(resultadoValor);
						}

					} else {
						if (formatoMap.containsKey(objAt.getNombreAtributo())) {
							resultado = FechaUtil.obtenerFechaFormatoPersonalizado(resultadoValor,
									formatoMap.get(objAt.getNombreAtributo()));
						} else {
							resultado = FechaUtil.obtenerFechaFormatoCompleto(resultadoValor);
						}
					}
				}
			} else if (objAt.getClasssAtributoType().isAssignableFrom(Collection.class)) {
				resultado = null;
			} else if (objAt.getClasssAtributoType().isAssignableFrom(int.class)) {
				resultado = Integer.parseInt(resultadoValor);
			} else {
				resultado = resultadoValor;
			}
		} catch (Exception e) {
			resultado = null;

		}

		return resultado;
	}

}
