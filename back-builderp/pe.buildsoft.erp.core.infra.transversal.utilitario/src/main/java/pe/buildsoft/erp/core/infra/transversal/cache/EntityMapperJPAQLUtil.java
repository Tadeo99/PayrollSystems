package pe.buildsoft.erp.core.infra.transversal.cache;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.buildsoft.erp.core.infra.transversal.entidades.vo.AtributoEntityVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.EntityVO;
import pe.buildsoft.erp.core.infra.transversal.utilitario.TransferDataObjectValidarUtil;

/**
 * La Class EntityMapperJPAQLUtil.
 * <ul>
 * <li>Copyright 2014 MAPFRE- mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal.
 * @version 1.0, Tue Apr 29 17:13:19 COT 2014
 * @since Rep v1..0
 */
public class EntityMapperJPAQLUtil extends TransferDataObjectValidarUtil implements Serializable {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instancia un nuevo data export excel.
	 */
	public EntityMapperJPAQLUtil() {

	}

	/**
	 * Obtener jpaql atributos.
	 *
	 * @param <T>         the generic type
	 * @param alias       the alias
	 * @param entityClass the entity class
	 * @param isNative    el is native
	 * @return the string
	 */
	public static <T> String obtenerJpaqlAtributos(String alias, String entityClass, boolean isNative) {
		return AtributosEntityCacheUtil.getInstance().obtenerAtributos(alias, entityClass, isNative);
	}

	/**
	 * Obtener jpaql atributos.
	 *
	 * @param <T>                   el tipo generico
	 * @param alias                 el alias
	 * @param listaAtributoEntityVO el lista atributo entity vo
	 * @param isNative              el is native
	 * @return the string
	 */
	public static <T> String obtenerJpaqlAtributos(String alias, List<AtributoEntityVO> listaAtributoEntityVO,
			boolean isNative) {
		return AtributosEntityCacheUtil.getInstance().obtenerAtributos(alias, listaAtributoEntityVO, isNative);
	}

	/**
	 * Obtener jpaql atributos.
	 *
	 * @param <T>         the generic type
	 * @param entityClass the entity class
	 * @param distinct    el distinct
	 * @param isNative    el is native
	 * @return the string
	 */
	public static <T> String obtenerSelectJpaqlAtributos(List<EntityVO> entityClass, boolean distinct,
			boolean isNative) {
		var jpaql = new StringBuilder();
		jpaql.append("select ");
		if (distinct) {
			jpaql.append(" distinct ");
		}
		var contador = 0;
		for (var obj : entityClass) {
			String class1 = obj.getClasss();
			if (contador > 0) {
				jpaql.append(", ");
			}
			jpaql.append(obtenerJpaqlAtributos(obj.getAlias(), class1, isNative));
			contador++;
		}
		jpaql.append(" ");
		return jpaql.toString();
	}

	/**
	 * Obtener values insert native atributos.
	 *
	 * @param <T>         el tipo generico
	 * @param entityClass el entity class
	 * @param isNative    el is native
	 * @return the string
	 */
	public static <T> String generarInsertNative(Class<T> entityClass) {
		var jpaql = new StringBuilder();
		var listaAtributos = obtenerListaAtributos(entityClass);
		var tableName = "";
		entityClass.getDeclaredAnnotations();
		if (listaAtributos != null && !listaAtributos.isEmpty()) {
			tableName = listaAtributos.get(0).getTableName();
			if (listaAtributos.get(0).getSchema() != null) {
				tableName = listaAtributos.get(0).getSchema() + "." + tableName;
			}
		}

		jpaql.append("Insert into " + tableName + " ( ");
		var class1 = entityClass.getName();
		jpaql.append(obtenerJpaqlAtributos(null, class1, true));
		jpaql.append(" )");
		jpaql.append(" values ( ");
		jpaql.append(AtributosEntityCacheUtil.getInstance().obtenerAtributosValues(listaAtributos));
		jpaql.append(" )");
		return jpaql.toString();
	}

	/**
	 * Obtener select jpaql atributos.
	 *
	 * @param <T>         el tipo generico
	 * @param entityClass el entity class
	 * @return the string
	 */
	public static String obtenerSelectJpaqlAtributos(List<EntityVO> entityClass) {
		return obtenerSelectJpaqlAtributos(entityClass, false, false);
	}

	/**
	 * Obtener select native atributos.
	 *
	 * @param <T>         el tipo generico
	 * @param entityClass el entity class
	 * @return the string
	 */
	public static String obtenerSelectNativeAtributos(List<EntityVO> entityClass) {
		return obtenerSelectJpaqlAtributos(entityClass, false, true);
	}

	/**
	 * Obtener lista atributos.
	 *
	 * @param <T>           the generic type
	 * @param entityClass   the entity class
	 * @param listaAtributo el lista atributo
	 * @return the list
	 */
	public static List<AtributoEntityVO> obtenerListaAtributos(String entityClass, List<String> listaAtributo) {
		List<AtributoEntityVO> resultado = new ArrayList<>();
		var resulTemp = obtenerListaAtributos(entityClass);
		int posicion = 0;
		for (var atributoEntityVO : resulTemp) {
			for (var atributo : listaAtributo) {
				if (atributo.equals(atributoEntityVO.getClasssAtributo())) {
					atributoEntityVO.setPosicion(posicion);
					posicion++;
					resultado.add(atributoEntityVO);
				}
			}
		}
		return resultado;
	}

	/**
	 * Paserar objeto entity.
	 *
	 * @param <T>         the generic type
	 * @param ressul      the ressul
	 * @param entityClass the entity class
	 * @return the t
	 */
	public static <T> T parsearObjetoEntity(Object[] ressul, Class<T> entityClass) {
		try {
			var listaAtributos = obtenerListaAtributos(entityClass.getName());
			return parsearObjetoEntity(ressul, entityClass, listaAtributos);
		} catch (Exception e) {

		}
		return null;
	}

	public static <T> T parsearObjetoEntityVO(Object[] ressul, Class<T> entityClass) {
		try {
			var listaAtributos = obtenerListaAtributos(entityClass);
			return parsearObjetoEntityVO(ressul, entityClass, listaAtributos);
		} catch (Exception e) {

		}
		return null;
	}

	public static <T> List<T> parsearObjetoEntityList(List<Object[]> ressul, Class<T> entityClass) {
		List<T> resultado = new ArrayList<>();
		try {
			for (var Object : ressul) {
				resultado.add(parsearObjetoEntity(Object, entityClass));
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return resultado;
	}

	public static <T> List<T> parsearObjetoEntityVOList(List<Object[]> ressul, Class<T> entityClass) {
		List<T> resultado = new ArrayList<>();
		try {
			for (var Object : ressul) {
				resultado.add(parsearObjetoEntityVO(Object, entityClass));
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return resultado;
	}

	/**
	 * Parsear objeto entity.
	 *
	 * @param <T>            el tipo generico
	 * @param ressul         el ressul
	 * @param entityClass    el entity class
	 * @param listaAtributos el lista atributos
	 * @return the t
	 */
	public static <T> T parsearObjetoEntity(Object[] ressul, Class<T> entityClass,
			List<AtributoEntityVO> listaAtributos) throws Exception {
		T resultado = entityClass.getDeclaredConstructor().newInstance();
		try {
			for (var objAtr : listaAtributos) {
				if (objAtr.isColumn()) {
					var f = getField(resultado, objAtr.getNombreAtributo());
					var value = obtenerValor(ressul[objAtr.getPosicion()], objAtr, false);

					setField(f, resultado, value);
				}
			}
		} catch (Exception e) {

		}
		return resultado;
	}

	public static <T> T parsearObjetoEntityVO(Object[] ressul, Class<T> entityClass,
			List<AtributoEntityVO> listaAtributos) throws Exception {
		T resultado = entityClass.getDeclaredConstructor().newInstance();
		try {
			for (var objAtr : listaAtributos) {
				try {
					var f = getField(resultado, objAtr.getNombreAtributo());
					var value = obtenerValor(ressul[objAtr.getPosicion()], objAtr, false);
					setField(f, resultado, value);
				} catch (Exception e) {
					System.err.println(e);
				}

			}
		} catch (Exception e) {

		}
		return resultado;
	}

	/**
	 * Parsear objeto entity.
	 *
	 * @param <T>            el tipo generico
	 * @param ressul         el ressul
	 * @param entityClass    el entity class
	 * @param listaAtributos el lista atributos
	 * @param ordenSiguente  el orden siguente
	 * @return the t
	 */
	public static <T> T parsearObjetoEntity(Object[] ressul, Class<T> entityClass,
			List<AtributoEntityVO> listaAtributos, int ordenSiguente) throws Exception {
		T resultado = entityClass.getDeclaredConstructor().newInstance();
		try {
			for (var objAtr : listaAtributos) {
				if (objAtr.isColumn()) {
					var f = getField(resultado, objAtr.getNombreAtributo());
					var value = obtenerValor(ressul[objAtr.getPosicion() + ordenSiguente], objAtr, false);

					setField(f, resultado, value);
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return resultado;
	}

	/**
	 * Parsear objeto entity.
	 *
	 * @param <T>           el tipo generico
	 * @param ressul        el ressul
	 * @param entityClass   el entity class
	 * @param ordenSiguente el orden siguente
	 * @return the t
	 */
	public static <T> T parsearObjetoEntity(Object[] ressul, Class<T> entityClass, int ordenSiguente) {
		try {
			var listaAtributos = obtenerListaAtributos(entityClass.getName());
			return parsearObjetoEntity(ressul, entityClass, listaAtributos, ordenSiguente);
		} catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}

	/**
	 * Obtener cantidad atributos.
	 *
	 * @param <T>         el tipo generico
	 * @param entityClass el entity class
	 * @return the int
	 */
	public static int obtenerCantidadAtributos(String entityClass) {
		try {
			return AtributosEntityCacheUtil.getInstance().obtenerListaAtributosCantidad(entityClass);
		} catch (Exception e) {

		}
		return 0;
	}

	/**
	 * Obtene parametro objeto entity.
	 *
	 * @param <T>             el tipo generico
	 * @param entityClass     el entity class
	 * @param entityClassName el entity class name
	 * @return the map
	 */
	public static <T> Map<String, Object> obteneParametroObjetoEntity(T entityClass, String entityClassName) {
		Map<String, Object> resultado = new HashMap<>();
		try {
			var listaAtributos = obtenerListaAtributos(entityClassName);
			for (var objAtr : listaAtributos) {
				if (objAtr.isColumn()) {
					var fValue = getField(entityClass, objAtr.getNombreAtributo());
					var value = fValue.get(entityClass);
					resultado.put(objAtr.getNombreAtributo(), value);
				}
			}
			return resultado;
		} catch (Exception e) {

		}
		return null;
	}

}
