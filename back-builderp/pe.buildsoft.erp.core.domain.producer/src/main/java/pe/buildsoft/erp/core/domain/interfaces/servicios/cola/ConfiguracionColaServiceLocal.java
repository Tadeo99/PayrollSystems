package pe.buildsoft.erp.core.domain.interfaces.servicios.cola;

import java.util.List;
import java.util.TreeMap;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.cola.Cola;
import pe.buildsoft.erp.core.domain.entidades.cola.ColaNoctura;
import pe.buildsoft.erp.core.domain.entidades.cola.ConfiguracionFiltroCola;
import pe.buildsoft.erp.core.domain.entidades.cola.ConfiguracionFiltroReporte;
import pe.buildsoft.erp.core.domain.entidades.cola.ValorConfiguracionFiltroReporte;
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
public interface ConfiguracionColaServiceLocal {

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
	ConfiguracionFiltroReporte controladorAccionConfiguracionFiltroReporte(ConfiguracionFiltroReporte obj,
			AccionType accionType);

	/**
	 * Listar configuracion filtro reporte.
	 *
	 * @param filtro el configuracion filtro reporte
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ConfiguracionFiltroReporte> listarConfiguracionFiltroReporte(BaseSearch filtro);

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
	ConfiguracionFiltroCola controladorAccionConfiguracionFiltroCola(ConfiguracionFiltroCola obj,
			AccionType accionType);

	/**
	 * Listar configuracion filtro cola.
	 *
	 * @param obj el configuracion filtro cola
	 * @return the list
	 * @throws Exception the exception
	 */
	TreeMap<Long, List<ConfiguracionFiltroCola>> listarConfiguracionFiltroColaMap(BaseSearch obj);

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
	ValorConfiguracionFiltroReporte controladorAccionValorConfiguracionFiltroReporte(
			ValorConfiguracionFiltroReporte obj, AccionType accionType);

	/**
	 * Listar valor configuracion filtro reporte.
	 *
	 * @param filtro el valor configuracion filtro reporte
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ValorConfiguracionFiltroReporte> listarValorConfiguracionFiltroReporte(BaseSearch filtro);

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
	ColaNoctura controladorAccionColaNoctura(ColaNoctura obj, AccionType accionType);

	/**
	 * Listar cola noctura.
	 *
	 * @param filtro el cola noctura
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ColaNoctura> listarColaNoctura(BaseSearch filtro);

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
	Cola controladorAccionCola(Cola obj, AccionType accionType);

	/**
	 * Listar cola.
	 *
	 * @param filtro el cola
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Cola> listarCola(BaseSearch filtro);

	/**
	 * contar lista cola.
	 *
	 * @param filtro el cola
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarCola(BaseSearch filtro);

}