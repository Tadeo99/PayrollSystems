package pe.buildsoft.erp.core.domain.interfaces.repositories.cola;

import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ConfiguracionReporteFromCorreoVO;

/**
 * La Class ConfiguracionReporteFromCorreoDaoLocal.
 * <ul>
 * <li>Copyright 2014 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Wed Jul 30 17:22:02 COT 2014
 * @since BuildErp 1.0
 */
@Local
public interface ConfiguracionReporteFromCorreoDaoLocal extends GenericDAOLocal<Object, Object> {

	/**
	 * Obtener configuracion reporte from correo map.
	 *
	 * @return the map
	 * @throws Exception the exception
	 */
	Map<String, ConfiguracionReporteFromCorreoVO> getFromCorreoMap();

}