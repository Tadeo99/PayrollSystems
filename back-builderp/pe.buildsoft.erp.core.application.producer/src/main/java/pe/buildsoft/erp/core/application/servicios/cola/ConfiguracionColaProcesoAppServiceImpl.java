package pe.buildsoft.erp.core.application.servicios.cola;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import pe.buildsoft.erp.core.application.interfaces.cola.ConfiguracionColaProcesoAppServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.cola.ConfiguracionColaProcesoServiceLocal;


/**
 * La Class ConfiguracionColaServiceImpl.
 * <ul>
 * <li>Copyright 2015 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 2.1, Thu Dec 21 12:25:45 COT 2017
 * @since BuildErp 1.0
 */
 @Stateless
 @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
 @TransactionManagement(TransactionManagementType.BEAN)
public class ConfiguracionColaProcesoAppServiceImpl implements ConfiguracionColaProcesoAppServiceLocal {
	 
	/** El servicio cola noctura dao impl. */
	@Inject
	private ConfiguracionColaProcesoServiceLocal servicio;

	@Override
	public String ejecucionColaNoctura(String uuid){
		return servicio.ejecucionColaNoctura(uuid);
	}
	
	
}