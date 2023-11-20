package pe.buildsoft.erp.core.application.servicios.migrador;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;

import pe.buildsoft.erp.core.application.interfaces.migrador.ConfigReporteTxtAppServiceLocal;
import pe.buildsoft.erp.core.domain.entidades.migrador.ConfiguracionReporteTxt;
import pe.buildsoft.erp.core.domain.interfaces.servicios.migrador.ConfigReporteTxtServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.migrador.ConfiguracionReporteTxtDTO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.BaseTransfer;

/**
 * La Class ConfigReporteTxtServiceImpl.
 * <ul>
 * <li>Copyright 2018 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Mar 14 10:56:05 COT 2019
 * @since BuildErp 1.0
 */
@Stateless
public class ConfigReporteTxtAppServiceImpl extends BaseTransfer implements ConfigReporteTxtAppServiceLocal {

	@Inject
	private ConfigReporteTxtServiceLocal servicio;

	@Override
	public ConfiguracionReporteTxtDTO controladorAccionConfiguracionReporteTxt(ConfiguracionReporteTxtDTO obj,
			AccionType accionType) {
		return toDTO(
				servicio.controladorAccionConfiguracionReporteTxt(to(obj, ConfiguracionReporteTxt.class), accionType),
				ConfiguracionReporteTxtDTO.class);
	}

	@Override
	public List<ConfiguracionReporteTxtDTO> listarConfiguracionReporteTxt(BaseSearch filtro) {
		return toList(this.servicio.listarConfiguracionReporteTxt(filtro), ConfiguracionReporteTxtDTO.class);
	}

	@Override
	public int contarListarConfiguracionReporteTxt(BaseSearch filtro) {
		return this.servicio.contarListarConfiguracionReporteTxt(filtro);
	}

}