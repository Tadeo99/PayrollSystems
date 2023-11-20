package pe.buildsoft.erp.core.domain.cache;

import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.cola.Cola;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ConfiguracionReporteFromCorreoVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ItemVO;

@Local
public interface ConfiguracionColaCacheSingletonLocal {
	String cargarCache();

	Map<String, Cola> getColaMap();

	Map<String, Cola> getColaNivelMap();

	Long getNivelMaximo();

	Map<Long, ItemVO> getTipoMonedaAllMap();
	
	Map<String, ConfiguracionReporteFromCorreoVO> getConfiguracionReporteFromCorreoMap();
}
