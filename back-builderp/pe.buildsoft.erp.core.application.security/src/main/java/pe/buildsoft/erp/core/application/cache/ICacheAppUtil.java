package pe.buildsoft.erp.core.application.cache;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.application.entidades.aas.vo.ConfiguracionFormularioVO;
import pe.buildsoft.erp.core.application.entidades.aas.vo.ConfiguracionMenuVO;
import pe.buildsoft.erp.core.application.entidades.security.PropertiesDTO;

/**
 * La Class CacheUtil.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 13:43:23 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface ICacheAppUtil {

	/**
	 * Sincronizar data.
	 *
	 * @return the string
	 */
	String sincronizarData();

	ConfiguracionFormularioVO getConfiguracionFormularioVO();

	/**
	 * Actualizar properties cache.
	 *
	 * @param listaConfiguracionMenu el lista configuracion menu
	 */
	void actualizarPropertiesCache(List<ConfiguracionMenuVO> listaConfiguracionMenu);

	/**
	 * Iniciar cache.
	 */
	String iniciarCache();

	/**
	 * Actualizar properties cache ideoma all.
	 *
	 * @param listaPropertiesTemp   el lista properties temp
	 * @param propertiesLenguajeMap el properties lenguaje map
	 */
	void actualizarPropertiesCacheIdeomaAll(List<PropertiesDTO> listaPropertiesTemp);

	Map<String, Map<String, String>> getPropertiesIdeomaMap();

	void setPropertiesIdeomaMap(Map<String, Map<String, String>> propertiesIdeomaMap);

	/**
	 * Comprueba si es cargo cache.
	 *
	 * @return true, si es cargo cache
	 */
	boolean isCargoCache();

	/**
	 * Establece el cargo cache.
	 *
	 * @param cargoCache el new cargo cache
	 */
	void setCargoCache(boolean cargoCache);

}
