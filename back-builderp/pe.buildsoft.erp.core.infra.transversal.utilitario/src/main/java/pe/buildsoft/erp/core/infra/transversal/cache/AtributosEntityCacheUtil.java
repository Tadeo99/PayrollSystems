package pe.buildsoft.erp.core.infra.transversal.cache;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.modules.ModuleClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.AtributoEntityVO;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class AtributosEntityCacheUtil.
 * <ul>
 * <li>Copyright 2014 MAPFRE- mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Tue Apr 29 19:20:13 COT 2014
 * @since Rep v1..0
 */
public class AtributosEntityCacheUtil {

	/** La log. */
	private static Logger log = LoggerFactory.getLogger(AtributosEntityCacheUtil.class);

	/** La configurador cache utl. */
	private static AtributosEntityCacheUtil atributoEntityCacheUtl = null;

	/** El resultado valor variable. */
	private static Map<String, List<AtributoEntityVO>> entityAtributoMap = new HashMap<>();
	private static Map<String, List<AtributoEntityVO>> entityAtributoManyToOneMap = new HashMap<>();

	private static Map<String, Integer> entityAtributoCantidaCamposMap = new HashMap<>();

	/** El flag cargo listado. */
	private boolean flagCargoListado = false;

	private static Map<String, String> paqueteMap = new HashMap<>();

	/**
	 * Instancia un nuevo administracion cache utl.
	 */
	public AtributosEntityCacheUtil() {

	}

	/**
	 * Instanciar.
	 *
	 * @return the configurador cache utl
	 */
	public static AtributosEntityCacheUtil getInstance() {
		if (atributoEntityCacheUtl == null) {
			createInstance();
		} else if (!atributoEntityCacheUtl.isFlagCargoListado()) {
			atributoEntityCacheUtl.sincronizarData();
		}
		return atributoEntityCacheUtl;
	}

	/**
	 * Creates the instance.
	 */
	private static synchronized void createInstance() {
		if (atributoEntityCacheUtl == null) {
			atributoEntityCacheUtl = new AtributosEntityCacheUtil();
			atributoEntityCacheUtl.sincronizarData();
		}
	}

	/**
	 * Sincronizar data.
	 */
	public void sincronizarData() {
		sincronizarAtributos();
	}

	/**
	 * Obtener atributos.
	 *
	 * @param <T>         el tipo generico
	 * @param alias       el alias
	 * @param entityClass el entity class
	 * @param isNative    el is native
	 * @return the string
	 */
	public static String obtenerAtributos(String alias, String entityClass, boolean isNative) {
		List<AtributoEntityVO> listaAtributos = entityAtributoMap.get(entityClass);
		return obtenerAtributos(alias, listaAtributos, isNative);
	}

	/**
	 * Obtener atributos.
	 *
	 * @param <T>            el tipo generico
	 * @param alias          el alias
	 * @param listaAtributos el lista atributos
	 * @return the string
	 */
	public static String obtenerAtributos(String alias, List<AtributoEntityVO> listaAtributos, boolean isNative) {
		var resultado = new StringBuilder();
		var cantidad = listaAtributos.size();
		var contador = 0;
		for (var atributoEntityVO : listaAtributos) {
			if (atributoEntityVO.getNombreColumna() != null || atributoEntityVO.isPKCompuesta()) {
				contador++;
				if (alias != null) {
					resultado.append(alias);
					resultado.append(".");
				}
				if (isNative) {
					if (!atributoEntityVO.isPKCompuesta()) {
						resultado.append(atributoEntityVO.getNombreColumna());
					} else {
						resultado.append(obtenerAtributos(null, atributoEntityVO.getListaAtributoEntityVOPK(), true));
					}
				} else {
					if (!atributoEntityVO.isPKCompuesta()) {
						resultado.append(atributoEntityVO.getNombreAtributo());
					} else {
						resultado.append(obtenerAtributos(null, atributoEntityVO.getListaAtributoEntityVOPK(), true));
					}
				}

				if (contador != cantidad) {
					resultado.append(",");
				}
			}
		}
		return resultado.toString();
	}

	public static String obtenerAtributosValues(List<AtributoEntityVO> listaAtributos) {
		var resultado = new StringBuilder();
		var cantidad = listaAtributos.size();
		var contador = 0;
		for (var atributoEntityVO : listaAtributos) {
			if (!StringUtil.isNullOrEmpty(atributoEntityVO.getNombreColumna()) || atributoEntityVO.isPKCompuesta()) {
				contador++;
				if (atributoEntityVO.isPKCompuesta()) {
					resultado.append(obtenerAtributosValues(atributoEntityVO.getListaAtributoEntityVOPK()));
				} else {
					resultado.append(":");
					resultado.append(atributoEntityVO.getNombreAtributo());
				}
				if (contador != cantidad) {
					resultado.append(",");
				}
			}
		}
		return resultado.toString();
	}

	/**
	 * Obtener lista atributos.
	 *
	 * @param <T>         the generic type
	 * @param entityClass the entity class
	 * @return the list
	 */
	public static List<AtributoEntityVO> obtenerListaAtributos(String entityClass) {
		return entityAtributoMap.get(entityClass);
	}

	public static Integer obtenerListaAtributosCantidad(String entityClass) {
		return entityAtributoCantidaCamposMap.get(entityClass);
	}

	/**
	 * Obtener lista atributos.
	 *
	 * @param <T>         the generic type
	 * @param entityClass the entity class
	 * @return the list
	 */
	public static <T> List<AtributoEntityVO> obtenerListaAtributos(Class<T> entityClass) {
		if (!entityAtributoMap.containsKey(entityClass.getName())) {
			try {
				List<AtributoEntityVO> listaAtrivuto = obtenerAtributosColunm(entityClass, true, 0);
				entityAtributoMap.put(entityClass.getName(), listaAtrivuto);
			} catch (Exception e) {
				log.error("obtenerListaAtributos", e);
			}

		}
		return entityAtributoMap.get(entityClass.getName());
	}

	public static <T> List<AtributoEntityVO> obtenerListaAtributosManyToOne(Class<T> entityClass) {
		if (entityAtributoManyToOneMap.containsKey(entityClass.getName())) {
			try {
				List<AtributoEntityVO> listaAtrivuto = obtenerAtributosColunmManyToOne(entityClass, false, 0);
				entityAtributoManyToOneMap.put(entityClass.getName(), listaAtrivuto);
			} catch (Exception e) {
				log.error("obtenerListaAtributosManyToOne", e);
			}

		}
		return entityAtributoManyToOneMap.get(entityClass.getName());
	}

	/**
	 * Prints the fields.
	 *
	 * @param <T>     el tipo generico
	 * @param dtoTemp el dto temp
	 * @return the list
	 * @throws Exception the exception
	 */
	private static <T> List<AtributoEntityVO> obtenerAtributosColunm(Class<T> dtoTemp, boolean isClaseNormal,
			int posicionTemp) throws Exception {
		log.info("obtenerAtributosColunm PASO 6 inicio");
		T dto = dtoTemp.getDeclaredConstructor().newInstance();

		List<AtributoEntityVO> resultado = new ArrayList<>();
		var tableName = "";
		var schema = "";
		dtoTemp.getDeclaredAnnotations();
		if (dtoTemp.isAnnotationPresent(Table.class)) {
			Table table = dtoTemp.getAnnotation(Table.class);
			tableName = table.name();
			if (table.schema() != null) {
				schema = table.schema();
			}
		}

		var propiedades = dto.getClass().getDeclaredFields();
		var posicion = posicionTemp;
		var cantidadColumna = 0;
		for (Field pd : propiedades) {
			try {
				if (pd.isEnumConstant()) {
					continue;
				}
				if ("id".equals(pd.getName())) {
					System.out.println("id");
				}
				// SE OBTIENE EL CAMPO
				var f = dto.getClass().getDeclaredField(pd.getName());
				// SE VALIDA QUE SEA UNA COLUMNA
				if (f != null && ((f.isAnnotationPresent(Column.class) || f.isAnnotationPresent(Transient.class)
						|| f.isAnnotationPresent(EmbeddedId.class)) || isClaseNormal)) {
					boolean isColum = f.isAnnotationPresent(Column.class);
					boolean esPK = f.isAnnotationPresent(Id.class);
					boolean isTransient = f.isAnnotationPresent(Transient.class)
							&& !(!f.getType().toString().contains("java.lang.Object")
									&& (f.getType().isAssignableFrom(Collection.class)
											|| f.getType().isAssignableFrom(List.class)));
					var objAtri = new AtributoEntityVO();
					objAtri.setTableName(tableName);
					objAtri.setSchema(schema);
					objAtri.setColumn(isColum);
					objAtri.setTransient(isTransient);
					objAtri.setEsPK(esPK);
					if (!f.isAnnotationPresent(EmbeddedId.class)) {
						objAtri.setNombreAtributo(pd.getName());
						objAtri.setClasssAtributo(f.getType());
						objAtri.setClasssAtributoType(f.getType());

						if (f.isAnnotationPresent(Column.class)) {
							cantidadColumna++;
							Column column = f.getAnnotation(Column.class);
							objAtri.setNombreColumna(column.name());
							entityAtributoCantidaCamposMap.put(dtoTemp.getName(), cantidadColumna);
							objAtri.setPosicion(posicion);
							posicion++;
						}
						if (pd.getName().contains("direccion1")) {
							System.out.print("aqui esta aributo que no debe ser considerado..");
						}
						if (isTransient) {
							f.getType().getDeclaredAnnotations();
							if (!f.getType().isAnnotationPresent(Table.class)) {
								resultado.add(objAtri);
							}
						} else {
							resultado.add(objAtri);
						}

					} else {
						objAtri.setListaAtributoEntityVOPK(obtenerAtributosColunm(pd.getType(), isClaseNormal, 0));
						objAtri.setPKCompuesta(true);
						objAtri.setNombreAtributo(pd.getName());
						objAtri.setClasssAtributo(f.getType());
						objAtri.setClasssAtributoType(f.getType());
						objAtri.setPosicion(posicion);
						resultado.add(objAtri);
					}
					// NO APLICA PARA ID O EMBEDDED

				}
			} catch (Exception e) {
				log.error("obtenerAtributosColunm  continue PASO 9 " + e.getMessage());
				continue;
			}
		}
		try {
			var current = dtoTemp.getSuperclass();
			if (current != null) {
				var listaAtributosHerencia = AtributosEntityCacheUtil.getInstance().obtenerListaAtributos(current);
				if (listaAtributosHerencia != null)
					resultado.addAll(listaAtributosHerencia);
			}
		} catch (Exception e) {
			log.error("obtenerAtributosColunm getSuperclass PASO 8 " + e.getMessage());
		}
		return resultado;
	}

	private static <T> List<AtributoEntityVO> obtenerAtributosColunmManyToOne(Class<T> dtoTemp, boolean isClaseNormal,
			int posicionTemp) throws Exception {
		T dto = dtoTemp.getDeclaredConstructor().newInstance();
		List<AtributoEntityVO> resultado = new ArrayList<>();
		var tableName = "";
		var schema = "";
		dtoTemp.getDeclaredAnnotations();
		if (dtoTemp.isAnnotationPresent(Table.class)) {
			var table = dtoTemp.getAnnotation(Table.class);
			tableName = table.name();
			if (table.schema() != null) {
				schema = table.schema();
			}
		}
		var propiedades = dto.getClass().getDeclaredFields();
		for (var pd : propiedades) {
			try {
				if (pd.isEnumConstant()) {
					continue;
				}
				var f = dto.getClass().getDeclaredField(pd.getName());
				// SE VALIDA QUE SEA UNA COLUMNA
				if (f != null && ((f.isAnnotationPresent(ManyToOne.class)) || isClaseNormal)) {
					boolean isColum = f.isAnnotationPresent(ManyToOne.class);
					boolean esPK = f.isAnnotationPresent(Id.class);
					AtributoEntityVO objAtri = new AtributoEntityVO();
					objAtri.setTableName(tableName);
					objAtri.setSchema(schema);
					objAtri.setColumn(isColum);
					objAtri.setEsPK(esPK);
					if (!f.isAnnotationPresent(EmbeddedId.class)) {
						objAtri.setNombreAtributo(pd.getName());
						objAtri.setClasssAtributo(f.getType());
						objAtri.setClasssAtributoType(f.getType());

						if (f.isAnnotationPresent(JoinColumn.class)) {
							JoinColumn joinColumn = f.getAnnotation(JoinColumn.class);
							objAtri.setNombreColumna(joinColumn.name());
						}
						resultado.add(objAtri);

					}

				}
			} catch (Exception e) {
				continue;
			}
		}
		return resultado;
	}

	/**
	 * Sincronizar obtener listados.
	 *
	 * @param <T> el tipo generico
	 */
	private void sincronizarAtributos() {
		try {
			entityAtributoMap = new HashMap<>();
			paqueteMap = new HashMap<>();
			flagCargoListado = true;
		} catch (Exception e) {
			log.error("sincronizarAtributos", e);
			flagCargoListado = false;
		}

	}

	/**
	 * Scans all classes accessible from the context class loader which belong to
	 * the given package and subpackages.
	 *
	 * @param packageName The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static List<Object> getClasses(String... paquete) {
		List<Object> resultado = new ArrayList<>();
		for (var packageName : paquete) {
			if (!paqueteMap.containsKey(packageName)) {
				try {
					var path = packageName.replace('.', '/');
					var classLoader = Thread.currentThread().getContextClassLoader();
					List<File> dirs = new ArrayList<>();
					if (classLoader instanceof ModuleClassLoader moduleClassLoader) {
						log.info("cargar cache de atributos PASO 1xx moduleClassLoader ..."
								+ moduleClassLoader.getName());
						var tmp = moduleClassLoader.iterateResources(path, true);
						if (tmp != null) {
							while (tmp.hasNext()) {
								dirs.add(new File(tmp.next().getURL().getFile()));
							}

						}
					} else {
						var temp = classLoader.getResources(path);
						log.info("cargar cache de atributos PASO 1yy classLoader ..." + classLoader.getName());
						while (temp.hasMoreElements()) {
							dirs.add(new File(temp.nextElement().getFile()));
						}
					}
					for (var directory : dirs) {
						resultado.addAll(findClasses(directory, packageName));
					}
				} catch (Exception e) {
					/* no controlado */
				}
				paqueteMap.put(packageName, "");
			}

		}
		return resultado;
	}

	/**
	 * Recursive method used to find all classes in a given directory and subdirs.
	 *
	 * @param directory   The base directory
	 * @param packageName The package name for classes found inside the base
	 *                    directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private static List<Class<?>> findClasses(File directory, String packageName) {
		List<Class<?>> classes = new ArrayList<>();
		/*
		 * if (!directory.exists()) { return classes; }
		 */
		if (directory.isDirectory()) {
			var files = directory.listFiles();
			for (var file : files) {
				try {
					if (file.isDirectory()) {
						assert !file.getName().contains(".");
						classes.addAll(findClasses(file, packageName + "." + file.getName()));
					} else if (file.getName().endsWith(".class")) {
						classes.add(Class
								.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
					}
				} catch (Exception e) {
					/* no controlado */
				}

			}
		} else {
			try {
				classes.add(Class.forName(
						packageName + '.' + directory.getName().substring(0, directory.getName().length() - 6)));
			} catch (Exception e) {
				/* no controlado */
			}

		}

		return classes;
	}

	private static List<Object> obtenerClasesReflections(String... paquete) {
		// log.error("obtenerClasesReflections...PASO 2.1xx " + paquete);
		return getClasses(paquete);
	}
	/*
	 * private static List<Object> obtenerClasesReflections(String... paquete) {
	 * List<Object> resultado = new ArrayList<>();
	 * log.error("obtenerClasesReflections...PASO 2.1 " + paquete); try {
	 * List<ClassLoader> classLoadersList = new LinkedList<>();
	 * classLoadersList.add(ClasspathHelper.contextClassLoader());
	 * classLoadersList.add(ClasspathHelper.staticClassLoader()); for (String
	 * classLoaderPaquete : paquete) { try { Reflections modules = new
	 * Reflections(classLoaderPaquete); Reflections reflections = new
	 * Reflections(new ConfigurationBuilder()
	 * .setScanners(Scanners.SubTypes.filterResultsBy(s -> true),
	 * Scanners.Resources) .setUrls(modules.getConfiguration().getUrls())
	 * .filterInputsBy(new FilterBuilder().includePackage(classLoaderPaquete)));
	 * 
	 * log.error("obtenerClasesReflections...PASO 2.3.0 " + classLoaderPaquete);
	 * log.error("obtenerClasesReflections...PASO 2.3.0 " +
	 * modules.getConfiguration().getUrls());
	 * 
	 * Set<Class<? extends BasePaginator>> classes =
	 * reflections.getSubTypesOf(BasePaginator.class); Set<Class<?>> classesObject =
	 * reflections.getSubTypesOf(Object.class);
	 * log.error("obtenerClasesReflections...PASO 2.3.1 " + classes.size());
	 * log.error("obtenerClasesReflections...PASO 2.3.2 " + classesObject.size());
	 * if (classes.size() == 0) { classes =
	 * modules.getSubTypesOf(BasePaginator.class);
	 * log.error("obtenerClasesReflections...PASO 2.3.4 " + classes.size());
	 * 
	 * reflections = new Reflections(new ConfigurationBuilder()
	 * .setScanners(Scanners.SubTypes, Scanners.Resources) .filterInputsBy(new
	 * FilterBuilder().includePackage(classLoaderPaquete)));
	 * 
	 * classes = reflections.getSubTypesOf(BasePaginator.class);
	 * log.error("obtenerClasesReflections...PASO 2.3.5 " + classes.size());
	 * 
	 * reflections = new Reflections( classLoaderPaquete, new SubTypesScanner());
	 * classes = reflections.getSubTypesOf(BasePaginator.class);
	 * log.error("obtenerClasesReflections...PASO 2.3.6 " + classes.size() );
	 * 
	 * } for (Class<?> class1 : classes) resultado.add(class1);
	 * 
	 * for (Class<?> class1 : classesObject) resultado.add(class1); } catch
	 * (Exception e) { log.error("No cargo para el paquete PASO 2.2 --> " +
	 * classLoaderPaquete); log.error(e); }
	 * 
	 * }
	 * 
	 * } catch (Exception e) {
	 * log.error("obtenerClasesReflections error general PASO 2.3..." +
	 * e.getMessage()); log.error(e); } return resultado; }
	 */

	public synchronized void sincronizarAtributos(String... paquetes) {
		try {
			List<Object> listEntity = obtenerClasesReflections(paquetes);
			for (var object : listEntity) {
				var classs = (Class<?>) object;
				if (!entityAtributoMap.containsKey(classs.getName())) {
					List<AtributoEntityVO> listaAtrivuto = obtenerAtributosColunm(classs, true, 0);
					entityAtributoMap.put(classs.getName(), listaAtrivuto);
					listaAtrivuto = obtenerAtributosColunmManyToOne(classs, false, 0);
					entityAtributoManyToOneMap.put(classs.getName(), listaAtrivuto);
				}
			}
		} catch (Exception e) {
			log.error("sincronizarAtributos", e);
		}

	}

	/**
	 * Comprueba si es flag cargo listado.
	 *
	 * @return true, si es flag cargo listado
	 */
	public boolean isFlagCargoListado() {
		return flagCargoListado;
	}

	/**
	 * Establece el flag cargo listado.
	 *
	 * @param flagCargoListado el new flag cargo listado
	 */
	public void setFlagCargoListado(boolean flagCargoListado) {
		this.flagCargoListado = flagCargoListado;
	}

	/**
	 * Obtiene entity atributo map.
	 *
	 * @return entity atributo map
	 */
	public static Map<String, List<AtributoEntityVO>> getEntityAtributoMap() {
		return entityAtributoMap;
	}

}
