package pe.buildsoft.erp.core.domain.servicios.migrador;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import pe.buildsoft.erp.core.domain.entidades.migrador.ConfiguracionReporteTxt;
import pe.buildsoft.erp.core.domain.interfaces.repositories.migrador.ConfiguracionReporteTxtDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.migrador.ConfigReporteTxtServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

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
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class ConfigReporteTxtServiceImpl implements ConfigReporteTxtServiceLocal {
	/** El servicio configuracion reporte txt dao impl. */
	@Inject
	private ConfiguracionReporteTxtDaoLocal configuracionReporteTxtDaoImpl;

	@Override
	public ConfiguracionReporteTxt controladorAccionConfiguracionReporteTxt(ConfiguracionReporteTxt obj,
			AccionType accionType) {
		switch (accionType) {
		case CREAR:
			obj.setIdConfiguracionReporteTxt(this.configuracionReporteTxtDaoImpl.generarId());
			this.configuracionReporteTxtDaoImpl.save(obj);
			break;
		case MODIFICAR:
			this.configuracionReporteTxtDaoImpl.update(obj);
			break;

		case ELIMINAR:
			obj = this.configuracionReporteTxtDaoImpl.find(ConfiguracionReporteTxt.class,
					obj.getIdConfiguracionReporteTxt());
			this.configuracionReporteTxtDaoImpl.delete(obj);
			break;

		case FIND_BY_ID:
			obj = this.configuracionReporteTxtDaoImpl.find(ConfiguracionReporteTxt.class,
					obj.getIdConfiguracionReporteTxt());
			break;

		default:
			break;
		}

		return obj;
	}

	@Override
	public List<ConfiguracionReporteTxt> listarConfiguracionReporteTxt(BaseSearch filtro) {
		return this.configuracionReporteTxtDaoImpl.listar(filtro);
	}

	@Override
	public int contarListarConfiguracionReporteTxt(BaseSearch filtro) {
		return this.configuracionReporteTxtDaoImpl.contar(filtro);
	}
}