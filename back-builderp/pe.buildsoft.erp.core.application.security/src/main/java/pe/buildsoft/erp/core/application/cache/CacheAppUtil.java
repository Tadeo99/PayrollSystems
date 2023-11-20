package pe.buildsoft.erp.core.application.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import pe.buildsoft.erp.core.application.entidades.aas.vo.ConfiguracionFormularioVO;
import pe.buildsoft.erp.core.application.entidades.aas.vo.ConfiguracionMenuVO;
import pe.buildsoft.erp.core.application.entidades.security.PropertiesDTO;
import pe.buildsoft.erp.core.application.interfaces.aas.AasAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.type.TipoComponenteType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstantesConfiguracion;

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
@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class CacheAppUtil implements ICacheAppUtil {

	private static final String GRILLA = "grilla";

	@Inject
	private AasAppServiceLocal servicioApp;

	/** El properties ideoma map. */
	private Map<String, Map<String, String>> propertiesIdeomaMap = new HashMap<>();

	private ConfiguracionFormularioVO configuracionFormularioVO = new ConfiguracionFormularioVO();

	/** El flag cargo cache. */
	private boolean cargoCache = false;

	/**
	 * Instancia un nuevo cache util.
	 */
	public CacheAppUtil() {
		super();
	}

	@PostConstruct
	public void initialize() {
		sincronizarData();
	}

	/**
	 * Sincronizar data.
	 *
	 * @return the string
	 */
	public String sincronizarData() {
		return iniciarCache();
	}

	public ConfiguracionFormularioVO getConfiguracionFormularioVO() {
		return configuracionFormularioVO;
	}

	private void inicializarMap() {
		configuracionFormularioVO.setRequiredInputMap(new HashMap<>());
		configuracionFormularioVO.setRequiredGrillaMap(new HashMap<>());
		configuracionFormularioVO.setReadonlyInputMap(new HashMap<>());
		configuracionFormularioVO.setReadonlyGrillaMap(new HashMap<>());
		configuracionFormularioVO.setRenderedLabelMap(new HashMap<>());
		configuracionFormularioVO.setRenderedInputMap(new HashMap<>());
		configuracionFormularioVO.setRenderedButtonMap(new HashMap<>());
		configuracionFormularioVO.setRenderedGrillaMap(new HashMap<>());
		configuracionFormularioVO.setRenderedButtonGrillaMap(new HashMap<>());
		configuracionFormularioVO.setDisabledInputMap(new HashMap<>());
		configuracionFormularioVO.setDisabledInputGrillaMap(new HashMap<>());
		configuracionFormularioVO.setDisabledButtonMap(new HashMap<>());
		configuracionFormularioVO.setDisabledButtonGrillaMap(new HashMap<>());
	}

	/**
	 * Actualizar properties cache.
	 *
	 * @param listaConfiguracionMenu el lista configuracion menu
	 */
	public void actualizarPropertiesCache(List<ConfiguracionMenuVO> listaConfiguracionMenu) {
		inicializarMap();
		actualizarConfiguracionFormularioLabelCache(listaConfiguracionMenu);
		actualizarConfiguracionFormularioButtonCache(listaConfiguracionMenu);
		actualizarConfiguracionFormularioInputCache(listaConfiguracionMenu);
	}

	private void actualizarConfiguracionFormularioLabelCache(List<ConfiguracionMenuVO> listaConfiguracionMenu) {
		for (var obj : listaConfiguracionMenu) {
			for (var objConf : obj.getListaConfiguracionMenus()) {
				String key = objConf.getProperties().getName();
				if (TipoComponenteType.get(obj.getCodigo()).equals(TipoComponenteType.LABEL)) {
					if (objConf.getProperties().getName().contains(GRILLA)) {
						configuracionFormularioVO.getRenderedGrillaMap().put(key, objConf.getRendered());
					} else {
						configuracionFormularioVO.getRenderedLabelMap().put(key, objConf.getRendered());
					}
				}
			}
		}
	}

	private void actualizarConfiguracionFormularioButtonCache(List<ConfiguracionMenuVO> listaConfiguracionMenu) {
		for (var obj : listaConfiguracionMenu) {
			for (var objConf : obj.getListaConfiguracionMenus()) {
				String key = objConf.getProperties().getName();
				if (TipoComponenteType.get(obj.getCodigo()).equals(TipoComponenteType.BUTTON)) {
					if (objConf.getProperties().getName().contains(GRILLA)) {
						configuracionFormularioVO.getRenderedButtonGrillaMap().put(key, objConf.getRendered());
						configuracionFormularioVO.getDisabledButtonGrillaMap().put(key, objConf.getDisabled());
					} else {
						configuracionFormularioVO.getRenderedButtonMap().put(key, objConf.getRendered());
						configuracionFormularioVO.getDisabledButtonMap().put(key, objConf.getDisabled());
					}
				}
			}
		}
	}

	private void actualizarConfiguracionFormularioInputCache(List<ConfiguracionMenuVO> listaConfiguracionMenu) {
		for (var obj : listaConfiguracionMenu) {
			for (var objConf : obj.getListaConfiguracionMenus()) {
				String key = objConf.getProperties().getName();
				if (TipoComponenteType.get(obj.getCodigo()).equals(TipoComponenteType.INPUT)) {
					if (objConf.getProperties().getName().contains(GRILLA)) {
						configuracionFormularioVO.getRequiredGrillaMap().put(key, objConf.getRequired());
						configuracionFormularioVO.getReadonlyGrillaMap().put(key, objConf.getReadonly());
						configuracionFormularioVO.getDisabledInputGrillaMap().put(key, objConf.getDisabled());
						configuracionFormularioVO.getRenderedGrillaMap().put(key, objConf.getRendered());
					} else {
						configuracionFormularioVO.getRequiredInputMap().put(key, objConf.getRequired());
						configuracionFormularioVO.getReadonlyInputMap().put(key, objConf.getReadonly());
						configuracionFormularioVO.getRenderedInputMap().put(key, objConf.getRendered());
						configuracionFormularioVO.getDisabledInputMap().put(key, objConf.getDisabled());
					}

				}
			}
		}
	}

	/**
	 * Iniciar cache.
	 */
	public synchronized String iniciarCache() {
		String resultado = "";
		try {
			if (!cargoCache) {
				// Se obtienen los parametros del web.xml
				List<ConfiguracionMenuVO> listaComponente = null;
				listaComponente = servicioApp.obtenerConfiguracionMenu(null);
				actualizarPropertiesCache(listaComponente);

				Map<String, Map<String, String>> propertiesLenguajeMap = servicioApp
						.obtenerPropertiesLenguajeAllMap();
				propertiesIdeomaMap.putAll(propertiesLenguajeMap);
				List<PropertiesDTO> listaPropertiesTemp = servicioApp
						.obtenerPropertiesLenguaje(new BaseSearch());
				actualizarPropertiesCacheIdeomaAll(listaPropertiesTemp);
				cargoCache = true;
			}

		} catch (Exception e) {
			cargoCache = false;
			resultado = e.getMessage();
		}
		return resultado;
	}

	/**
	 * Actualizar properties cache ideoma all.
	 *
	 * @param listaPropertiesTemp   el lista properties temp
	 * @param propertiesLenguajeMap el properties lenguaje map
	 */
	public void actualizarPropertiesCacheIdeomaAll(List<PropertiesDTO> listaPropertiesTemp) {
		String keyIdeoma = ConstantesConfiguracion.ES_PE;
		for (var obj : listaPropertiesTemp) {
			if (!propertiesIdeomaMap.containsKey(keyIdeoma)) {
				Map<String, String> ideomaBDMap = new HashMap<>();
				ideomaBDMap.put(obj.getName(), obj.getValue());
				propertiesIdeomaMap.put(keyIdeoma, ideomaBDMap);
			} else {
				Map<String, String> ideomaBDMap = propertiesIdeomaMap.get(keyIdeoma);
				if (!ideomaBDMap.containsKey(obj.getName())) {
					ideomaBDMap.put(obj.getName(), obj.getValue());
				}
				propertiesIdeomaMap.put(keyIdeoma, ideomaBDMap);
			}
		}
	}

// get y set
	public Map<String, Map<String, String>> getPropertiesIdeomaMap() {
		return propertiesIdeomaMap;
	}

	public void setPropertiesIdeomaMap(Map<String, Map<String, String>> propertiesIdeomaMap) {
		this.propertiesIdeomaMap = propertiesIdeomaMap;
	}

	/**
	 * Comprueba si es cargo cache.
	 *
	 * @return true, si es cargo cache
	 */
	public boolean isCargoCache() {
		return cargoCache;
	}

	/**
	 * Establece el cargo cache.
	 *
	 * @param cargoCache el new cargo cache
	 */
	public void setCargoCache(boolean cargoCache) {
		this.cargoCache = cargoCache;
	}

}
