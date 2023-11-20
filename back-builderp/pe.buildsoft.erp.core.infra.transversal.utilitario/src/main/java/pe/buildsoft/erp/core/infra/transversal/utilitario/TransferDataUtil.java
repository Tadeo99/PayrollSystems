package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.AtributoEntityVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ScriptSqlResulJDBCVO;

/**
 * La Class TransferDataUtil.
 * <ul>
 * <li>Copyright 2014 MAPFRE- mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Tue Apr 29 17:13:19 COT 2014
 * @since BuildErp v1.0
 */
public class TransferDataUtil extends TransferDataObjectValidarUtil implements Serializable {

	private static final String SERIAL_VERSION_UID = "serialVersionUID";

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** El log. */
	private static Logger log = LoggerFactory.getLogger(TransferDataUtil.class);

	/**
	 * Instancia un nuevo data export excel.
	 */
	public TransferDataUtil() {
		//
	}

	public static <T> T toPojo(Object ressul, Class<T> entityClassVO) {
		String className = "";
		if (ressul == null) {
			return null;
		}
		try {
			T resultado = entityClassVO.getDeclaredConstructor().newInstance();
			className = ressul.getClass().getName();
			String handlerHibernate = obtenerHandlerHibernate(className);
			if (className.contains(handlerHibernate)) {
				int indexOf = className.indexOf(handlerHibernate);
				className = className.substring(0, indexOf);
				Hibernate.initialize(ressul);
				if (ressul instanceof HibernateProxy hibernateProxy) {
					ressul = hibernateProxy.getHibernateLazyInitializer().getImplementation();
				}
			}
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(ressul.getClass());
			Map<String, Integer> fieldHerenciaMap = fieldHerenciaMap(ressul);
			Map<String, Integer> fieldHerenciaResultadoMap = fieldHerenciaMap(resultado);
			for (var objAtr : listaAtributos) {
				if (!SERIAL_VERSION_UID.equalsIgnoreCase(objAtr.getNombreAtributo())) {
					try {
						Field f = fieldHerenciaSet(resultado, fieldHerenciaResultadoMap, objAtr);
						Field fValue = fieldHerenciaSet(ressul, fieldHerenciaMap, objAtr);
						setField(f, resultado, fValue.get(ressul));
					} catch (Exception e) {
						//
					}

				}
			}
			return resultado;
		} catch (Exception e) {
			log.error("Error TransferDataUtil.toPojo() al parsear class name = " + className + ", "
					+ entityClassVO.getName() + "  " + e.getMessage());
		}
		return null;
	}

	/**
	 * Transfer objeto entity get rest dto.
	 *
	 * @param <T>            el tipo generico
	 * @param info           el info
	 * @param entityClassDTO el entity class dto
	 * @return the t
	 */
	public static <T> T toRestDTO(@Context UriInfo info, Class<T> entityClassDTO) {
		T resultado = null;
		try {
			if (info == null) {
				return null;
			}
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(entityClassDTO);
			resultado = entityClassDTO.getDeclaredConstructor().newInstance();
			Map<String, Integer> fieldHerenciaResultadoMap = fieldHerenciaMap(resultado);
			for (var objAtr : listaAtributos) {
				Field f = fieldHerenciaSet(resultado, fieldHerenciaResultadoMap, objAtr);
				Object value = obtenerValor(info.getQueryParameters().getFirst(objAtr.getNombreAtributo()), objAtr,
						false);
				setField(f, resultado, value);
			}

		} catch (Exception e) {
			log.error("Error TransferDataUtil.toRestDTO() al parsear " + entityClassDTO.getName() + "  "
					+ e.getMessage());
		}
		return resultado;
	}

	public static Map<String, Object> toGetRestMap(@Context UriInfo info) {
		Map<String, Object> resultado = new HashMap<>();
		try {
			if (info == null) {
				return resultado;
			}
			for (var key : info.getQueryParameters().keySet()) {
				resultado.put(key.toUpperCase(), info.getQueryParameters().get(key));
			}

		} catch (Exception e) {
			log.error("Error TransferDataObjectUtil.transferObjetoEntityGetRestMap " + e.getMessage());
		}
		return resultado;
	}

	/**
	 * Transfer objeto entity historial.
	 *
	 * @param <T>            el tipo generico
	 * @param ressul         el ressul
	 * @param entityClassDTO el entity class dto
	 * @return the t
	 */
	public static <T> T toHistorial(Object ressul, Class<T> entityClassDTO) {
		try {
			if (ressul == null) {
				return null;
			}
			T resultado = entityClassDTO.getDeclaredConstructor().newInstance();
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(ressul.getClass().getName());
			for (var objAtr : listaAtributos) {
				if (objAtr.isColumn()) {
					try {
						Field f = getField(resultado, objAtr.getNombreAtributo());
						Field fValue = getField(ressul, objAtr.getNombreAtributo());
						setField(f, resultado, fValue.get(ressul));
					} catch (Exception e) {
						//
					}
				}
			}
			return resultado;
		} catch (Exception e) {
			log.error("Error TransferDataUtil.toHistorial() al parsear " + entityClassDTO.getName() + "  "
					+ e.getMessage());
		}
		return null;
	}

	/**
	 * Transfer objeto entity dto list.
	 *
	 * @param <T>            el tipo generico
	 * @param <E>            el tipo de elemento
	 * @param ressulList     el ressul list
	 * @param entityClassDTO el entity class dto
	 * @return the list
	 */
	public static <T, E> List<T> toList(List<E> ressulList, Class<T> entityClassDTO) {
		List<T> resultado = new ArrayList<>();
		if (ressulList == null) {
			return resultado;
		}
		for (var ressul : ressulList) {
			T resultadoTemp = toDTO(ressul, entityClassDTO);
			resultado.add(resultadoTemp);
		}
		return resultado;
	}

	/**
	 * Transfer objeto entity dto list.
	 *
	 * @param <T>            el tipo generico
	 * @param <E>            el tipo de elemento
	 * @param ressulList     el ressul list
	 * @param entityClassDTO el entity class dto
	 * @return the list
	 */
	public static <T, E> List<T> toList(List<E> ressulList, Class<T> entityClassDTO, String... entityClasess) {
		List<T> resultado = new ArrayList<>();
		if (ressulList == null) {
			return resultado;
		}
		for (var ressul : ressulList) {
			T resultadoTemp = toDTO(ressul, entityClassDTO, entityClasess);
			resultado.add(resultadoTemp);
		}
		return resultado;
	}

	private static <T> T transferObjetoEntityDTOPK(Object ressul, Class<T> entityClassDTO, String... entityClasess) {
		return toEntityDTO(ressul, entityClassDTO, true, entityClasess);
	}

	public static <T> T toDTO(Object ressul, Class<T> entityClassDTO, String... entityClasess) {
		return toEntityDTO(ressul, entityClassDTO, false, entityClasess);
	}

	/**
	 * Transfer objeto entity dto.
	 *
	 * @param <T>            el tipo generico
	 * @param ressul         el ressul
	 * @param entityClassDTO el entity class dto
	 * @return the t
	 */
	private static <T> T toEntityDTO(Object ressul, Class<T> entityClassDTO, boolean esPK, String... entityClasess) {
		String className = "";
		if (ressul == null) {
			return null;
		}
		try {

			T resultado = entityClassDTO.getDeclaredConstructor().newInstance();
			className = ressul.getClass().getName();
			String handlerHibernate = obtenerHandlerHibernate(className);
			if (className.contains(handlerHibernate)) {
				int indexOf = className.indexOf(handlerHibernate);
				className = className.substring(0, indexOf);
				Hibernate.initialize(ressul);
				if (ressul instanceof HibernateProxy hibernateProxy) {
					ressul = hibernateProxy.getHibernateLazyInitializer().getImplementation();
				}
			}
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(className);
			Map<String, Integer> fieldHerenciaMap = fieldHerenciaMap(ressul);
			Map<String, Integer> fieldHerenciaResultadoMap = fieldHerenciaMap(resultado);
			for (var objAtr : listaAtributos) {
				if (esPK) {
					if (objAtr.isColumn() && objAtr.isEsPK()) {
						Field f = fieldHerenciaSet(resultado, fieldHerenciaResultadoMap, objAtr);
						Field fValue = fieldHerenciaSet(ressul, fieldHerenciaMap, objAtr);
						setField(f, resultado, fValue.get(ressul));
						break;
					}
				} else {
					if (objAtr.isColumn() || objAtr.isTransient()) {
						Field f = fieldHerenciaSet(resultado, fieldHerenciaResultadoMap, objAtr);
						Field fValue = fieldHerenciaSet(ressul, fieldHerenciaMap, objAtr);
						setField(f, resultado, fValue.get(ressul));
					}
				}

			}
			if (entityClasess != null && entityClasess.length > 0) {
				for (var clasesPojoTemp : entityClasess) {
					boolean isSubClase = clasesPojoTemp.contains(":");
					String[] entitySubClasess = null;
					if (isSubClase) {
						String[] dataTempClase = clasesPojoTemp.split(":");
						clasesPojoTemp = dataTempClase[0];
						String dataTempArray = dataTempClase[1];
						if (dataTempArray.contains("{")) {
							int indexOf = dataTempArray.indexOf("{");
							int lastIndexOf = dataTempArray.lastIndexOf("}");
							dataTempArray = dataTempArray.substring(indexOf + 1, lastIndexOf);
						}

						String[] entitySubClasessTemp = dataTempArray.split(";", -1);
						if (entitySubClasessTemp != null && entitySubClasessTemp.length > 0) {
							entitySubClasess = new String[entitySubClasessTemp.length];
							int index = 0;
							for (var dataTemp : entitySubClasessTemp) {
								if (!dataTemp.contains("{") && !dataTemp.contains("}")) {
									entitySubClasess[index] = dataTemp;
									index++;
								}
							}
						} else {
							if (entitySubClasessTemp != null) {
								entitySubClasess = new String[1];
								String dataTemp = entitySubClasessTemp[0];
								int indexOf = dataTemp.indexOf("{");
								int lastIndexOf = dataTemp.lastIndexOf("}");
								dataTemp = dataTemp.substring(indexOf + 1, lastIndexOf);
								entitySubClasess[0] = dataTemp;
							}
						}

					}
					String clasesPojo = clasesPojoTemp;
					boolean esTansferSoloPK = false;
					if (clasesPojoTemp.contains("@PK@")) {
						esTansferSoloPK = true;
						clasesPojo = clasesPojoTemp.substring(0, clasesPojoTemp.indexOf("@PK@"));
					}

					try {
						Field f = getField(resultado, clasesPojo);
						if (f != null) {
							Field fValue = getField(ressul, clasesPojo);
							Object valueTransfer = fValue.get(ressul);
							if (valueTransfer == null) {
								valueTransfer = fValue.getType().getDeclaredConstructor().newInstance();
							}
							Object value = null;
							if (esTansferSoloPK) {
								value = transferObjetoEntityDTOPK(valueTransfer, f.getType());
							} else {
								if (entitySubClasess != null) {
									value = toDTO(valueTransfer, f.getType(), entitySubClasess);
								} else {
									value = toDTO(valueTransfer, f.getType());
								}

							}
							setField(f, resultado, value);
						}
					} catch (Exception e) {
						//
					}

				}
			}
			return resultado;
		} catch (Exception e) {
			log.error("Error TransferDataUtil.toEntityDTO() al parsear class name = " + className + ", "
					+ entityClassDTO.getName() + "  " + e.getMessage());
		}
		return null;
	}

	public static Field getField(Object ressul, String nombreAtributo) throws NoSuchFieldException {
		AtributoEntityVO objAtr = new AtributoEntityVO();
		objAtr.setNombreAtributo(nombreAtributo);
		Map<String, Integer> fieldHerenciaMap = fieldHerenciaMap(ressul);
		Field fValue = fieldHerenciaSet(ressul, fieldHerenciaMap, objAtr);
		return fValue;
	}

	public static Object getFieldValue(Object ressul, String nombreAtributo)
			throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field fValue = getField(ressul, nombreAtributo);
		return fValue.get(ressul);
	}

	public static void setField(Object ressul, Object object, String nombreAtributo)
			throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field f = getField(ressul, nombreAtributo);
		setField(f, ressul, object);
	}

	public static <T> T to(Object ressul, Class<T> entityClassEntity, boolean isFiltro, String... entityClasess) {
		return toEntity(ressul, entityClassEntity, false, isFiltro, entityClasess);
	}

	private static <T> T transferObjetoEntityPK(Object ressul, Class<T> entityClassEntity, boolean isFiltro,
			String... entityClasess) {
		return toEntity(ressul, entityClassEntity, true, isFiltro, entityClasess);
	}

	/**
	 * Transfer objeto entity.
	 *
	 * @param <T>               el tipo generico
	 * @param ressul            el ressul
	 * @param entityClassEntity el entity class entity
	 * @return the t
	 */
	public static <T> T toEntity(Object ressul, Class<T> entityClassEntity, boolean esPK, boolean isFiltro,
			String... entityClasess) {
		if (ressul == null) {
			return null;
		}
		try {

			T resultado = entityClassEntity.getDeclaredConstructor().newInstance();
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(entityClassEntity.getName());
			if (!esPK) {
				entityClasess = obtenerListaAtributosPK(entityClassEntity, entityClasess);
			}
			Map<String, Integer> fieldHerenciaMap = fieldHerenciaMap(ressul);
			Map<String, Integer> fieldHerenciaResultadoMap = fieldHerenciaMap(resultado);

			for (var objAtr : listaAtributos) {
				if (esPK) {
					if ((objAtr.isColumn() || objAtr.isTransient()) && objAtr.isEsPK()) {
						Field f = fieldHerenciaSet(resultado, fieldHerenciaResultadoMap, objAtr);
						Field fValue = fieldHerenciaSet(ressul, fieldHerenciaMap, objAtr);
						Object value = fValue.get(ressul);
						if (!StringUtil.isNullOrEmpty(value)) {
							setField(f, resultado, value);
						} else {
							if (!isFiltro) {
								resultado = null;
							}
						}
						break;
					}

				} else {
					if (objAtr.isColumn() || objAtr.isTransient()) {
						Field f = fieldHerenciaSet(resultado, fieldHerenciaResultadoMap, objAtr);
						Field fValue = fieldHerenciaSet(ressul, fieldHerenciaMap, objAtr);
						setField(f, resultado, fValue.get(ressul));
					}
				}
			}
			if (entityClasess != null && entityClasess.length > 0) {
				for (var clasesPojoTemp : entityClasess) {
					boolean isSubClase = clasesPojoTemp.contains(":");
					String[] entitySubClasess = null;
					if (isSubClase) {
						String[] dataTempClase = clasesPojoTemp.split(":");
						clasesPojoTemp = dataTempClase[0];
						String dataTempArray = dataTempClase[1];
						if (dataTempArray.contains("{")) {
							int indexOf = dataTempArray.indexOf("{");
							int lastIndexOf = dataTempArray.lastIndexOf("}");
							dataTempArray = dataTempArray.substring(indexOf + 1, lastIndexOf);
						}

						String[] entitySubClasessTemp = dataTempArray.split(";", -1);
						if (entitySubClasessTemp != null && entitySubClasessTemp.length > 0) {
							entitySubClasess = new String[entitySubClasessTemp.length];
							int index = 0;
							for (var dataTemp : entitySubClasessTemp) {
								if (!dataTemp.contains("{") && !dataTemp.contains("}")) {
									entitySubClasess[index] = dataTemp;
									index++;
								}
							}
						} else {
							if (entitySubClasessTemp != null) {
								entitySubClasess = new String[1];
								String dataTemp = entitySubClasessTemp[0];
								int indexOf = dataTemp.indexOf("{");
								int lastIndexOf = dataTemp.lastIndexOf("}");
								dataTemp = dataTemp.substring(indexOf + 1, lastIndexOf);
								entitySubClasess[0] = dataTemp;
							}
						}

					}
					String clasesPojo = clasesPojoTemp;
					boolean esTansferSoloPK = false;
					if (clasesPojoTemp.contains("@PK@")) {
						esTansferSoloPK = true;
						clasesPojo = clasesPojoTemp.substring(0, clasesPojoTemp.indexOf("@PK@"));
					}

					try {
						Field f = getField(resultado, clasesPojo);
						if (f != null) {
							Field fValue = getField(ressul, clasesPojo);
							Object valueTransfer = fValue.get(ressul);
							if (valueTransfer == null) {
								valueTransfer = fValue.getType().getDeclaredConstructor().newInstance();
							}
							Object value = null;
							if (esTansferSoloPK) {
								value = transferObjetoEntityPK(valueTransfer, f.getType(), isFiltro);
							} else {
								if (entitySubClasess != null) {
									value = to(valueTransfer, f.getType(), isFiltro, entitySubClasess);
								} else {
									value = to(valueTransfer, f.getType(), isFiltro);
								}

							}
							setField(f, resultado, value);
						}
					} catch (Exception e) {
						//
					}

				}
			}
			return resultado;
		} catch (Exception e) {
			log.error("Error TransferDataUtil.toEntity() al parsear " + entityClassEntity.getName() + "  "
					+ e.getMessage());
		}
		return null;
	}

	public static <T> T toEntityVO(Object ressul, Class<T> entityClassEntity) {
		if (ressul == null) {
			return null;
		}
		try {
			T resultado = entityClassEntity.getDeclaredConstructor().newInstance();
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(entityClassEntity.getName());
			for (var objAtr : listaAtributos) {
				try {
					if (objAtr.isColumn()) {
						Field f = getField(resultado, objAtr.getNombreAtributo());
						Field fValue = getField(ressul, objAtr.getNombreAtributo());
						setField(f, resultado, fValue.get(ressul));
					}
				} catch (Exception e) {
					//
				}
			}
			return resultado;
		} catch (Exception e) {
			log.error("Error TransferDataUtil.transferObjetoEntity() al parsear " + entityClassEntity.getName() + "  "
					+ e.getMessage());
		}
		return null;
	}

	public static Map<String, Object> toEntityAtributeMap(Object ressul) {
		return toMap(ressul, false, false);

	}

	/**
	 * Transfer objeto entity atribute map.
	 *
	 * @param <T>    el tipo generico
	 * @param ressul el ressul
	 * @return the map
	 */
	public static Map<String, Object> toMap(Object ressul, boolean isNative, boolean isCargarManyToOne) {
		if (ressul == null) {
			return Collections.emptyMap();
		}
		try {
			Map<String, Object> resultado = new HashMap<>();
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(ressul.getClass());
			resultado.putAll(obtenerEntityAtributeMap(listaAtributos, ressul, isNative, false, false));
			if (isCargarManyToOne) {
				listaAtributos = obtenerListaAtributosManyToOne(ressul.getClass());
				resultado.putAll(obtenerEntityAtributeMap(listaAtributos, ressul, isNative, true, false));
			}
			return resultado;
		} catch (Exception e) {
			log.error(
					"Error TransferDataUtil.toMap() al parsear " + ressul.getClass().getName() + "  " + e.getMessage());
		}
		return Collections.emptyMap();
	}

	private static Map<String, Object> obtenerEntityAtributeMap(List<AtributoEntityVO> listaAtributos, Object ressul,
			boolean isNative, boolean isManyToOne, boolean isPK) throws Exception {
		Map<String, Object> resultado = new HashMap<>();
		for (var objAtr : listaAtributos) {
			if (!StringUtil.isNullOrEmpty(objAtr.getNombreColumna()) || objAtr.isPKCompuesta()) {
				if (!isManyToOne || isPK) {
					if (isPK) {
						if (objAtr.isEsPK() && !objAtr.isPKCompuesta()) {
							Field fValue = getField(ressul, objAtr.getNombreAtributo());
							Object value = fValue.get(ressul);
							String key = isNative ? objAtr.getNombreColumna() : objAtr.getNombreAtributo();
							if (value == null) {
								resultado.put(key, "");
							} else {
								resultado.put(key, value);
							}
							break;
						} else {
							if (objAtr.isEsPK() && !objAtr.isPKCompuesta()) {
								Field fValue = getField(ressul, objAtr.getNombreAtributo());
								Object value = fValue.get(ressul);
								if (value == null) {
									value = fValue.getType().getDeclaredConstructor().newInstance();
								}
								resultado.putAll(toMap(value, isNative, false));
								break;
							}

						}
					} else {
						if (!objAtr.isPKCompuesta()) {
							Field fValue = getField(ressul, objAtr.getNombreAtributo());
							Object value = fValue.get(ressul);
							String key = isNative ? objAtr.getNombreColumna() : objAtr.getNombreAtributo();
							if (value == null) {
								resultado.put(key, "");
							} else {
								resultado.put(key, value);
							}
						} else {
							Field fValue = getField(ressul, objAtr.getNombreAtributo());
							Object value = fValue.get(ressul);
							if (value == null) {
								value = fValue.getType().getDeclaredConstructor().newInstance();
							}
							resultado.putAll(toMap(value, isNative, false));
						}
					}

				} else {
					Field fValue = getField(ressul, objAtr.getNombreAtributo());
					Object valueTransfer = fValue.get(ressul);
					List<AtributoEntityVO> listaAtributosManyToOne = obtenerListaAtributos(valueTransfer.getClass());
					Map<String, Object> valueMap = obtenerEntityAtributeMap(listaAtributosManyToOne, valueTransfer,
							isNative, false, true);
					Object value = valueMap.get(isNative ? listaAtributosManyToOne.get(0).getNombreColumna()
							: listaAtributosManyToOne.get(0).getNombreAtributo());
					String key = isNative ? objAtr.getNombreColumna() : objAtr.getNombreAtributo();
					if (value == null) {
						resultado.put(key, "");
					} else {
						resultado.put(key, value);
					}
				}

			}
		}
		return resultado;
	}

	/**
	 * Transfer objeto entity campos map.
	 *
	 * @param <T>    el tipo generico
	 * @param ressul el ressul
	 * @return the map
	 */
	public static Map<String, Object> toEntityMap(Object ressul) {
		if (ressul == null) {
			return Collections.emptyMap();
		}
		try {
			Map<String, Object> resultado = new HashMap<>();
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(ressul.getClass());
			for (var objAtr : listaAtributos) {
				if (!StringUtil.isNullOrEmpty(objAtr.getNombreColumna()) || objAtr.isPKCompuesta()) {
					if (!objAtr.isPKCompuesta()) {
						Field fValue = getField(ressul, objAtr.getNombreAtributo());
						Object value = fValue.get(ressul);
						if (value == null) {
							resultado.put(objAtr.getNombreColumna(), "");
						} else {
							resultado.put(objAtr.getNombreColumna(), value);
						}
					} else {
						Field fValue = getField(ressul, objAtr.getNombreAtributo());
						Object value = fValue.get(ressul);
						if (value == null) {
							value = fValue.getType().getDeclaredConstructor().newInstance();
						}
						resultado.putAll(toEntityMap(value));

					}
				}
			}
			return resultado;
		} catch (Exception e) {
			log.error("Error TransferDataUtil.toEntityMap() al parsear " + ressul.getClass().getName() + "  "
					+ e.getMessage());
		}
		return Collections.emptyMap();
	}

	/**
	 * Transfer objeto vo atribute map.
	 *
	 * @param <T>           el tipo generico
	 * @param ressul        el ressul
	 * @param listaObjeto   el lista objeto
	 * @param listaAtributo el lista atributo
	 * @param isExcluir     el is excluir
	 * @return the map
	 */
	public static Map<String, Map<String, Object>> toVOMap(Object ressul, Map<String, List<String>> listaObjeto,
			List<String> listaAtributo, boolean isExcluir) {
		if (ressul == null) {
			return Collections.emptyMap();
		}
		try {
			if (listaAtributo == null) {
				listaAtributo = new ArrayList<>();
			}
			if (isExcluir && !listaAtributo.contains(SERIAL_VERSION_UID)) {
				listaAtributo.add(SERIAL_VERSION_UID);
			}
			if (listaObjeto == null) {
				listaObjeto = new HashMap<>();
			}
			Map<String, Map<String, Object>> resultado = new HashMap<>();
			Map<String, Object> resultadoValue = new HashMap<>();
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(ressul.getClass());
			for (var objAtr : listaAtributos) {
				boolean isObtenerAtributo = false;
				if (isExcluir) {
					isObtenerAtributo = !listaAtributo.contains(objAtr.getNombreAtributo());
				} else {
					isObtenerAtributo = listaAtributo.contains(objAtr.getNombreAtributo());
				}
				if (isObtenerAtributo) {
					try {
						Field fValue = getField(ressul, objAtr.getNombreAtributo());
						Object value = fValue.get(ressul);
						if (value != null) {
							if (!listaObjeto.containsKey(objAtr.getNombreAtributo())) {
								if (!StringUtil.isNullOrEmpty(value)) {
									if (!objAtr.getClasssAtributoType().isAssignableFrom(OffsetDateTime.class)) {
										resultadoValue.put(objAtr.getNombreAtributo(), value);
									} else {
										resultadoValue.put(objAtr.getNombreAtributo(),
												FechaUtil.obtenerFechaFormatoCompleto((OffsetDateTime) value));
									}
								}
							} else {
								resultadoValue.put(objAtr.getNombreAtributo(), ARTIFICIO_CLASS);
								resultado.putAll(
										toVOMap(value, null, listaObjeto.get(objAtr.getNombreAtributo()), false));
							}
						}
					} catch (Exception e) {
						//
					}
				}
			}
			if (resultadoValue.size() > 0) {
				resultado.put(ressul.getClass().getName(), resultadoValue);
			}
			return resultado;
		} catch (Exception e) {
			log.error("Error TransferDataUtil.toVOMap() al parsear " + ressul.getClass().getName() + "  "
					+ e.getMessage());
		}
		return Collections.emptyMap();
	}

	public static Map<String, Map<String, Object>> toFiltroMap(Object ressul, Map<String, List<String>> listaObjeto,
			List<String> listaAtributo, boolean isExcluir, String nombreClase, Map<String, String> fechaFormatoMap) {
		if (ressul == null) {
			return Collections.emptyMap();
		}
		try {

			if (listaAtributo == null) {
				listaAtributo = new ArrayList<>();
			}
			if (isExcluir && !listaAtributo.contains(SERIAL_VERSION_UID)) {
				listaAtributo.add(SERIAL_VERSION_UID);
			}
			if (listaObjeto == null) {
				listaObjeto = new HashMap<>();
			}
			Map<String, Map<String, Object>> resultado = new HashMap<>();
			Map<String, Object> resultadoValue = new HashMap<>();
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(ressul.getClass());
			for (var objAtr : listaAtributos) {
				boolean isObtenerAtributo = false;
				if (isExcluir) {
					isObtenerAtributo = !listaAtributo.contains(objAtr.getNombreAtributo());
				} else {
					isObtenerAtributo = listaAtributo.contains(objAtr.getNombreAtributo());
				}
				if (isObtenerAtributo) {
					try {
						Field fValue = getField(ressul, objAtr.getNombreAtributo());
						Object value = fValue.get(ressul);
						StringBuilder nombreAtributoFinal = new StringBuilder();
						if (!StringUtil.isNullOrEmpty(nombreClase)) {
							nombreAtributoFinal.append(nombreClase);
							nombreAtributoFinal.append(".");
							nombreAtributoFinal.append(objAtr.getNombreAtributo());
						} else {
							nombreAtributoFinal.append(objAtr.getNombreAtributo());
						}
						if (value != null) {
							if (value instanceof Number) {
								BigDecimal numberTemp = new BigDecimal(value.toString());
								if (numberTemp.compareTo(BigDecimal.ZERO) <= 0) {
									value = null; // nulear valores por defecto 0
								}
							}
							if (!listaObjeto.containsKey(objAtr.getNombreAtributo())) {
								if (value != null) {
									if (!objAtr.getClasssAtributoType().isAssignableFrom(OffsetDateTime.class)) {
										resultadoValue.put(nombreAtributoFinal.toString(), value);
									} else {
										resultadoValue.put(nombreAtributoFinal.toString(),
												FechaUtil.obtenerFechaFormatoPersonalizado((OffsetDateTime) value,
														fechaFormatoMap.get(nombreAtributoFinal.toString())));
									}
								} else {
									resultadoValue.put(nombreAtributoFinal.toString(), null);
								}
							} else {
								resultado.putAll(toFiltroMap(value, null, listaObjeto.get(objAtr.getNombreAtributo()),
										false, objAtr.getNombreAtributo(), fechaFormatoMap));
							}
						} else {
							if (!listaObjeto.containsKey(objAtr.getNombreAtributo())) {
								resultadoValue.put(nombreAtributoFinal.toString(), null);
							}
						}
					} catch (Exception e) {
						//
					}
				}
			}
			if (resultadoValue.size() > 0) {
				resultado.put(ressul.getClass().getName(), resultadoValue);
			}
			return resultado;
		} catch (Exception e) {
			log.error("Error TransferDataUtil.toFiltroMap() al parsear " + ressul.getClass().getName() + "  "
					+ e.getMessage());
		}
		return Collections.emptyMap();
	}

	/**
	 * Transfer objeto vo atribute map.
	 *
	 * @param <T>    el tipo generico
	 * @param ressul el ressul
	 * @return the map
	 */
	public static Map<String, Object> toVOMap(Object ressul) {
		if (ressul == null) {
			return Collections.emptyMap();
		}
		try {
			Map<String, Object> resultado = new HashMap<>();
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(ressul.getClass());
			for (var objAtr : listaAtributos) {
				try {
					Field fValue = getField(ressul, objAtr.getNombreAtributo());
					Object value = fValue.get(ressul);
					if (value != null) {
						if (!StringUtil.isNullOrEmpty(value)) {
							if (!objAtr.getClasssAtributoType().isAssignableFrom(OffsetDateTime.class)) {
								resultado.put(objAtr.getNombreAtributo(), value);
							} else {
								resultado.put(objAtr.getNombreAtributo(),
										FechaUtil.obtenerFechaFormatoCompleto((OffsetDateTime) value));
							}
						}
					}
				} catch (Exception e) {
					//
				}
			}
			return resultado;
		} catch (Exception e) {
			log.error("Error TransferDataUtil.toVOMap() al parsear " + ressul.getClass().getName() + "  "
					+ e.getMessage());
		}
		return Collections.emptyMap();
	}

	/**
	 * Transfer objeto entity vo.
	 *
	 * @param <T>                  el tipo generico
	 * @param scriptSqlResulJDBCVO el script sql resul jdbcvo
	 * @param entityClassEntity    el entity class entity
	 * @return the t
	 */
	public static <T> T toEntityVO(ScriptSqlResulJDBCVO scriptSqlResulJDBCVO, Class<T> entityClassEntity) {
		try {
			List<Map<String, Object>> ressul = scriptSqlResulJDBCVO.getListaData();
			if (ressul == null || scriptSqlResulJDBCVO.isTieneError()) {
				return null;
			}
			Map<String, Object> valueMap = scriptSqlResulJDBCVO.getListaData().get(0);
			T resultado = entityClassEntity.getDeclaredConstructor().newInstance();
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(entityClassEntity);
			for (var objAtr : listaAtributos) {
				if (scriptSqlResulJDBCVO.getListaHeader().contains(objAtr.getNombreAtributo())) {
					Field f = getField(resultado, objAtr.getNombreAtributo());
					Object value = obtenerValor(valueMap.get(objAtr.getNombreAtributo()) + "", objAtr, false);
					setField(f, resultado, value);
				}
			}
			return resultado;
		} catch (Exception e) {
			log.error("Error TransferDataUtil.toEntityVO() al parsear " + entityClassEntity.getName() + "  "
					+ e.getMessage());
		}
		return null;
	}

	public static <T> T toRestVO(Map<String, Object> valueMap, Class<T> entityClassEntity) {
		return toRestVO(valueMap, null, entityClassEntity);
	}

	public static <T> T toRestVO(Map<String, Object> valueMap, Map<String, String> equivalenciaAtributeMap,
			Class<T> entityClassEntity) {
		try {
			T resultado = entityClassEntity.getDeclaredConstructor().newInstance();
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(entityClassEntity);
			for (var objAtr : listaAtributos) {
				String keyAtributo = objAtr.getNombreAtributo();
				if (equivalenciaAtributeMap != null && equivalenciaAtributeMap.containsKey(keyAtributo)) {
					keyAtributo = equivalenciaAtributeMap.get(keyAtributo);
				}
				if (valueMap.containsKey(keyAtributo)) {
					Field f = getField(resultado, objAtr.getNombreAtributo());
					Object value = obtenerValor(valueMap.get(keyAtributo), objAtr, false);
					setField(f, resultado, value);
				}
			}
			return resultado;
		} catch (Exception e) {
			log.error("Error TransferDataUtil.toRestVO() al parsear " + entityClassEntity.getName() + "  "
					+ e.getMessage());
		}
		return null;
	}

	public static <T> List<T> toRestVOList(List<Map<String, Object>> listaValueMap, Class<T> entityClassEntity) {
		return toRestVOList(listaValueMap, null, entityClassEntity);
	}

	public static <T> List<T> toRestVOList(List<Map<String, Object>> listaValueMap,
			Map<String, String> equivalenciaAtributeMap, Class<T> entityClassEntity) {
		List<T> resultado = new ArrayList<>();
		if (listaValueMap == null) {
			return resultado;
		}
		for (var ressul : listaValueMap) {
			T resultadoTemp = toRestVO(ressul, equivalenciaAtributeMap, entityClassEntity);
			resultado.add(resultadoTemp);
		}
		return resultado;
	}

	/**
	 * Transfer objeto entity list vo.
	 *
	 * @param <T>                  el tipo generico
	 * @param scriptSqlResulJDBCVO el script sql resul jdbcvo
	 * @param entityClassEntity    el entity class entity
	 * @return the list
	 */
	public static <T> List<T> toEntityListVO(ScriptSqlResulJDBCVO scriptSqlResulJDBCVO, Class<T> entityClassEntity) {
		return toEntityListVO(scriptSqlResulJDBCVO, entityClassEntity, new HashMap<String, String>());
	}

	/**
	 * Transfer objeto entity list vo.
	 *
	 * @param <T>                  el tipo generico
	 * @param scriptSqlResulJDBCVO el script sql resul jdbcvo
	 * @param entityClassEntity    el entity class entity
	 * @param formatoFechaMap      el formato fecha map
	 * @return the list
	 */
	public static <T> List<T> toEntityListVO(ScriptSqlResulJDBCVO scriptSqlResulJDBCVO, Class<T> entityClassEntity,
			Map<String, String> formatoFechaMap) {
		try {
			List<Map<String, Object>> ressul = scriptSqlResulJDBCVO.getListaData();
			if (ressul == null || scriptSqlResulJDBCVO.isTieneError()) {
				return Collections.emptyList();
			}
			List<T> resultado = new ArrayList<>();
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(entityClassEntity);
			for (var valueMap : scriptSqlResulJDBCVO.getListaData()) {
				T resultadoTemp = entityClassEntity.getDeclaredConstructor().newInstance();
				for (var objAtr : listaAtributos) {
					if (scriptSqlResulJDBCVO.getListaHeader().contains(objAtr.getNombreAtributo())) {
						Object value = null;
						try {
							Field f = getField(resultadoTemp, objAtr.getNombreAtributo());
							value = obtenerValor(valueMap.get(objAtr.getNombreAtributo()) + "", objAtr, false,
									formatoFechaMap);
							setField(f, resultadoTemp, value);
						} catch (Exception e) {
							//
						}
					}
				}
				resultado.add(resultadoTemp);
			}

			return resultado;
		} catch (Exception e) {
			log.error("Error TransferDataUtil.toEntityListVO() al parsear " + entityClassEntity.getName() + "  "
					+ e.getMessage());
		}
		return Collections.emptyList();
	}

	public static <T> List<T> toEntityListVO(Map<String, String> campoMapping,
			ScriptSqlResulJDBCVO scriptSqlResulJDBCVO, Class<T> entityClassEntity,
			Map<String, String> formatoFechaMap) {
		try {
			List<Map<String, Object>> ressul = scriptSqlResulJDBCVO.getListaData();
			if (ressul == null || scriptSqlResulJDBCVO.isTieneError()) {
				return Collections.emptyList();
			}
			List<T> resultado = new ArrayList<>();
			List<AtributoEntityVO> listaAtributos = obtenerListaAtributos(entityClassEntity);
			for (var valueMap : scriptSqlResulJDBCVO.getListaData()) {
				T resultadoTemp = entityClassEntity.getDeclaredConstructor().newInstance();
				for (var objAtr : listaAtributos) {
					String campoKeyBD = campoMapping.get(objAtr.getNombreAtributo()) + "";
					if (scriptSqlResulJDBCVO.getListaHeader().contains(campoKeyBD)) {
						Object value = null;
						try {
							Field f = getField(resultadoTemp, objAtr.getNombreAtributo());
							value = obtenerValor(valueMap.get(campoKeyBD) + "", objAtr, false, formatoFechaMap);
							setField(f, resultadoTemp, value);
						} catch (Exception e) {
							//
						}

					}
				}
				resultado.add(resultadoTemp);
			}

			return resultado;
		} catch (Exception e) {
			log.error("Error TransferDataUtil.toEntityListVO() al parsear " + entityClassEntity.getName() + "  "
					+ e.getMessage());
		}
		return Collections.emptyList();
	}

	/**
	 * Transfer objeto entity list.
	 *
	 * @param <T>               el tipo generico
	 * @param <E>               el tipo de elemento
	 * @param ressulList        el ressul list
	 * @param entityClassEntity el entity class entity
	 * @return the list
	 */
	public static <T, E> List<T> toListEntity(List<E> ressulList, Class<T> entityClassEntity, boolean isFiltro,
			String... entityClasess) {
		List<T> resultado = new ArrayList<>();
		if (ressulList == null) {
			return resultado;
		}
		for (var ressul : ressulList) {
			T resultadoTemp = to(ressul, entityClassEntity, isFiltro, entityClasess);
			resultado.add(resultadoTemp);
		}
		return resultado;
	}

}
