package pe.buildsoft.erp.core.domain.interfaces.repositories.cola;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.cola.ConfiguracionFiltroReporte;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ConfiguracionFiltroReporteDaoLocal.
 * <ul>
 * <li>Copyright 2015 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 2.1, Thu Dec 21 12:16:30 COT 2017
 * @since BuildErp 1.0
 */
@Local
public interface ConfiguracionFiltroReporteDaoLocal extends GenericDAOLocal<Long, ConfiguracionFiltroReporte> {
	/**
	 * Listar configuracion filtro reporte.
	 *
	 * @param filtro el configuracion filtro reporte
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ConfiguracionFiltroReporte> listar(BaseSearch filtro);

	/**
	 * contar lista configuracion filtro reporte.
	 *
	 * @param filtro el configuracion filtro reporte
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id configuracionFiltroReporte.
	 *
	 * @return the Long
	 * @throws Exception the exception
	 */
	Long generarId();

	/**
	 * Listar id componentes.
	 *
	 * @param idOpcion el id opcion
	 * @return the list
	 * @throws Exception the exception
	 */
	Map<String, List<String>> listarIdComponentes(Long idOpcion);
}