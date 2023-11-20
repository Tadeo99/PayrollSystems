package pe.buildsoft.erp.core.application.interfaces.cola;

import jakarta.ejb.Local;

/**
 * La Class ConfiguracionColaServiceLocal.
 * <ul>
 * <li>Copyright 2015 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 2.1, Thu Dec 21 12:25:45 COT 2017
 * @since BuildErp 1.0
 */
@Local
public interface ConfiguracionColaProcesoAppServiceLocal{
	
	String ejecucionColaNoctura(String uuid);
	
	
}