package pe.buildsoft.erp.core.application.interfaces.cola;

import java.util.List;
import java.util.TreeMap;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.application.entidades.cola.ColaDTO;
import pe.buildsoft.erp.core.application.entidades.cola.ColaNocturaDTO;
import pe.buildsoft.erp.core.application.entidades.cola.ConfiguracionFiltroColaDTO;
import pe.buildsoft.erp.core.application.entidades.cola.ConfiguracionFiltroReporteDTO;
import pe.buildsoft.erp.core.application.entidades.cola.ValorConfiguracionFiltroReporteDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoSolicitudEjecucionEstate;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class ConfiguracionColaServiceLocal.
 * <ul>
 * <li>Copyright 2015 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 2.1, Thu Dec 21 12:25:45 COT 2017
 * @since BuildErp 1.0
 */
@Local
public interface ConfiguracionColaAppServiceLocal {

	String actualizarEstadoColaNoctura(String idColaNocturna,
			EstadoSolicitudEjecucionEstate estadoSolicitudEjecucionEstate);

	/**
	 * Controlador accion configuracion filtro reporte.
	 *
	 * @param obj        el configuracion filtro reporte
	 * @param accionType el accion type
	 * @return the configuracion filtro reporte
	 * @throws Exception the exception
	 */
	ConfiguracionFiltroReporteDTO controladorAccionConfiguracionFiltroReporte(ConfiguracionFiltroReporteDTO obj,
			AccionType accionType);

	/**
	 * Listar configuracion filtro reporte.
	 *
	 * @param filtro el configuracion filtro reporte
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ConfiguracionFiltroReporteDTO> listarConfiguracionFiltroReporte(BaseSearch filtro);

	/**
	 * contar lista configuracion filtro reporte.
	 *
	 * @param filtro el configuracion filtro reporte
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarConfiguracionFiltroReporte(BaseSearch filtro);

	/**
	 * Controlador accion configuracion filtro cola.
	 *
	 * @param obj        el configuracion filtro cola
	 * @param accionType el accion type
	 * @return the configuracion filtro cola
	 * @throws Exception the exception
	 */
	ConfiguracionFiltroColaDTO controladorAccionConfiguracionFiltroCola(ConfiguracionFiltroColaDTO obj,
			AccionType accionType);

	/**
	 * Listar configuracion filtro cola.
	 *
	 * @param filtro el configuracion filtro cola
	 * @return the list
	 * @throws Exception the exception
	 */
	TreeMap<Long, List<ConfiguracionFiltroColaDTO>> listarConfiguracionFiltroColaMap(BaseSearch filtro);

	/**
	 * contar lista configuracion filtro cola.
	 *
	 * @param filtro el configuracion filtro cola
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarConfiguracionFiltroCola(BaseSearch filtro);

	/**
	 * Controlador accion valor configuracion filtro reporte.
	 *
	 * @param obj        el valor configuracion filtro reporte
	 * @param accionType el accion type
	 * @return the valor configuracion filtro reporte
	 * @throws Exception the exception
	 */
	ValorConfiguracionFiltroReporteDTO controladorAccionValorConfiguracionFiltroReporte(
			ValorConfiguracionFiltroReporteDTO obj, AccionType accionType);

	/**
	 * Listar valor configuracion filtro reporte.
	 *
	 * @param filtro el valor configuracion filtro reporte
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ValorConfiguracionFiltroReporteDTO> listarValorConfiguracionFiltroReporte(BaseSearch filtro);

	/**
	 * contar lista valor configuracion filtro reporte.
	 *
	 * @param filtro el valor configuracion filtro reporte
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarValorConfiguracionFiltroReporte(BaseSearch filtro);

	/**
	 * Controlador accion cola noctura.
	 *
	 * @param obj        el cola noctura
	 * @param accionType el accion type
	 * @return the cola noctura
	 * @throws Exception the exception
	 */
	ColaNocturaDTO controladorAccionColaNoctura(ColaNocturaDTO obj, AccionType accionType);

	/**
	 * Listar cola noctura.
	 *
	 * @param filtro el cola noctura
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ColaNocturaDTO> listarColaNoctura(BaseSearch filtro);

	/**
	 * contar lista cola noctura.
	 *
	 * @param filtro el cola noctura
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarColaNoctura(BaseSearch filtro);

	/**
	 * Controlador accion cola.
	 *
	 * @param obj        el cola
	 * @param accionType el accion type
	 * @return the cola
	 * @throws Exception the exception
	 */
	ColaDTO controladorAccionCola(ColaDTO obj, AccionType accionType);

	/**
	 * Listar cola.
	 *
	 * @param filtro el cola
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ColaDTO> listarCola(BaseSearch filtro);

	/**
	 * contar lista cola.
	 *
	 * @param filtro el cola
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCola(BaseSearch filtro);

}