/*
 * 
 */
package pe.com.builderp.core.service.common.util.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.application.entidades.common.ParametroDTO;
import pe.buildsoft.erp.core.application.interfaces.common.CommonAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;

/**
 * La Class ParametroCacheUtil.
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
public class ParametroCacheUtil implements IParametroCacheUtil {

	private Logger log = LoggerFactory.getLogger(ParametroCacheUtil.class);

	/** El service common impl. */
	@Inject
	private CommonAppServiceLocal commonServiceImpl;

	@Inject
	private ICache cacheUtil;

	/** El flag cargo listado. */
	private boolean flagCargoListado = false;

	/**
	 * Instancia un nuevo administracion cache utl.
	 */
	public ParametroCacheUtil() {
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
		return sincronizarProperties();
	}

	/**
	 * Sincronizar properties.
	 *
	 * @return the string
	 */
	private String sincronizarProperties() {
		try {
			// commonServiceImpl = Referencia.getReference(CommonServiceLocal.class);
			BaseSearch filter = new BaseSearch();
			filter.setSortDirections("asc");
			filter.setSortFields("descripcion");
			filter.setEstado(EstadoGeneralState.ACTIVO.getKey());
			List<ParametroDTO> listaParametro = commonServiceImpl.listarParametro(filter);
			for (var parametroDTO : listaParametro) {
				String key = parametroDTO.getEntidad() + parametroDTO.getCodigo();
				String value = parametroDTO.getValor();
				cacheUtil.put(key, value);
			}
			flagCargoListado = true;
		} catch (Exception e) {
			log.error("sincronizarProperties", e);
			flagCargoListado = false;
			return e.toString();
		}
		return null;
	}

	@Override
	public String get(String key) {
		return cacheUtil.getString(key);
	}
	// get y set

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

}
