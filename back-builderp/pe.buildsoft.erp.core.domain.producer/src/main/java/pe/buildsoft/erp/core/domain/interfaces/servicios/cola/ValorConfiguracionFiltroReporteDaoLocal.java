package pe.buildsoft.erp.core.domain.interfaces.servicios.cola;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.cola.ValorConfiguracionFiltroReporte;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ValorConfiguracionFiltroReporteDaoLocal.
 * <ul>
 * <li>Copyright 2015 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 2.1, Thu Dec 21 12:16:30 COT 2017
 * @since BuildErp 1.0
 */
@Local
public interface ValorConfiguracionFiltroReporteDaoLocal  extends GenericDAOLocal<String,ValorConfiguracionFiltroReporte> {
	
	boolean registrarMasivo(List<ValorConfiguracionFiltroReporte> listaValorConfiguracionFiltroReporte);
	/**
	 * Listar valor configuracion filtro reporte.
	 *
	 * @param filtro el valor configuracion filtro reporte
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ValorConfiguracionFiltroReporte> listar(BaseSearch filtro);
	
	/**
	 * contar lista valor configuracion filtro reporte.
	 *
	 * @param filtro el valor configuracion filtro reporte
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id valorConfiguracionFiltroReporte.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	/**
	 * Listar datos filtro.
	 *
	 * @param keyList el key list
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ValorConfiguracionFiltroReporte> listarDatosFiltro(List<Long> keyList, List<String> listaLugarAparece);
	
	/**
	 * Eliminar by id solicitud reporte.
	 *
	 * @param idSolicitudReporte el id solicitud reporte
	 * @return true, en caso de exito
	 * @throws Exception the exception
	 */
	boolean eliminarByIdSolicitudReporte(String idSolicitudReporte);
}