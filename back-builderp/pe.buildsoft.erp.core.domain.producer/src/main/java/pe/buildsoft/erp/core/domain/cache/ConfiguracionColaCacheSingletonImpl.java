package pe.buildsoft.erp.core.domain.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import pe.buildsoft.erp.core.domain.entidades.cola.Cola;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ConfiguracionColaServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ConfiguracionReporteFromCorreoVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ItemVO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;

@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class ConfiguracionColaCacheSingletonImpl implements ConfiguracionColaCacheSingletonLocal {
	private Logger log = LoggerFactory.getLogger(ConfiguracionColaCacheSingletonImpl.class);

	private Map<Long, ItemVO> tipoMonedaAllMap = new HashMap<>();

	/** The cola map. */
	private Map<String, Cola> colaMap = new HashMap<>();

	/** The cola map. */
	private Map<String, Cola> colaNivelMap = new HashMap<>();
	private Long nivelMaximo = 0L;
	
	private Map<String, ConfiguracionReporteFromCorreoVO> configuracionReporteFromCorreoMap = new HashMap<>();


	@Inject
	private ConfiguracionColaServiceLocal configuracionColaServiceLocal;
	
	@PostConstruct
	public void initialize() {
		cargarCache();
	}

	@Override
	public String cargarCache() {
		return cargarConfCola();
	}

	private String cargarConfCola() {
		try {
			BaseSearch colaFiltro = new BaseSearch();
			colaFiltro.setEstado(EstadoGeneralState.ACTIVO.getKey());
			List<Cola> colaList = configuracionColaServiceLocal.listarCola(colaFiltro);
			for (var objCola : colaList) {
				this.colaMap.put(objCola.getIdCola() + "", objCola);
				this.colaNivelMap.put(objCola.getNivelCola() + "", objCola);
			}
			nivelMaximo = obtenerNivelMaximo(colaList);
		} catch (Exception e) {
			log.error("cargarConfCola",e);
			return e.toString();
		}
		return null;
	}

	private Long obtenerNivelMaximo(List<Cola> colaList) {
		Long resultado = 0L;
		for (var objCola : colaList) {
			Long nivel = objCola.getNivelCola();
			if (resultado < nivel)
				resultado = nivel;
		}
		return resultado;

	}

	@Override
	public Map<String, Cola> getColaMap() {
		return colaMap;
	}

	@Override
	public Map<String, Cola> getColaNivelMap() {
		return colaNivelMap;
	}

	@Override
	public Long getNivelMaximo() {
		return nivelMaximo;
	}

	@Override
	public Map<Long, ItemVO> getTipoMonedaAllMap() {
		return tipoMonedaAllMap;
	}
	
	@Override
	public Map<String, ConfiguracionReporteFromCorreoVO> getConfiguracionReporteFromCorreoMap() {
		return configuracionReporteFromCorreoMap;
	}

}
