package pe.buildsoft.erp.core.domain.interfaces.repositories.security;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.security.Properties;
import pe.buildsoft.erp.core.domain.entidades.security.PropertiesLenguaje;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class PropertiesLenguajeDaoLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 11:25:07 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface PropertiesLenguajeDaoLocal extends GenericDAOLocal<String, PropertiesLenguaje> {

	int actualizarPropertiesLenguaje(Properties obj);

	/**
	 * Obtener properties lenguaje map.
	 *
	 * @param listaIdProperties el lista id properties
	 * @return the map @ the exception
	 */
	List<PropertiesLenguaje> obtenerPropertiesLenguajeMap(List<Long> listaIdProperties);

	/**
	 * Obtener properties lenguaje map.
	 *
	 * @return the map
	 */
	List<PropertiesLenguaje> obtenerPropertiesLenguajeAllMap();

	/**
	 * Listar properties lenguaje.
	 *
	 * @param propertiesLenguaje el properties lenguaje
	 * @return the list @ the exception
	 */
	List<PropertiesLenguaje> listar(BaseSearch filtro);

	/**
	 * contar lista properties lenguaje.
	 *
	 * @param propertiesLenguaje el properties lenguaje
	 * @return the list @ the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id propertiesLenguaje.
	 *
	 * @return the Long @ the exception
	 */
	String generarId();
}