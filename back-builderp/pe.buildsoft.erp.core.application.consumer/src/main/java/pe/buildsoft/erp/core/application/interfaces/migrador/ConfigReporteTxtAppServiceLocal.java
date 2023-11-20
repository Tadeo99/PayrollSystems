package pe.buildsoft.erp.core.application.interfaces.migrador;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.migrador.ConfiguracionReporteTxtDTO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class ConfigReporteTxtServiceLocal.
 * <ul>
 * <li>Copyright 2018 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Mar 14 10:56:05 COT 2019
 * @since BuildErp 1.0
 */

@Local
public interface ConfigReporteTxtAppServiceLocal {
	/**
	 * Controlador accion configuracion reporte txt.
	 *
	 * @param obj        el configuracion reporte txt
	 * @param accionType el accion type
	 * @return the configuracion reporte txt
	 * @throws Exception the exception
	 */
	ConfiguracionReporteTxtDTO controladorAccionConfiguracionReporteTxt(ConfiguracionReporteTxtDTO obj,
			AccionType accionType);

	/**
	 * Listar configuracion reporte txt.
	 *
	 * @param filtro el configuracion reporte txt
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ConfiguracionReporteTxtDTO> listarConfiguracionReporteTxt(BaseSearch filtro);

	/**
	 * contar lista configuracion reporte txt.
	 *
	 * @param filtro el configuracion reporte txt
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarConfiguracionReporteTxt(BaseSearch filtro);

}