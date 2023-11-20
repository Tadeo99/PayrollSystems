package pe.buildsoft.erp.core.domain.interfaces.servicios.cola;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.cola.ColaNoctura;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

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
public interface ConfiguracionColaProcesoServiceLocal{
	
	String ejecucionColaNoctura(String uuid);
	
	List<ColaNoctura> listarColaNoctura(BaseSearch colaNoctura);
	
	int contarListarColaNoctura(BaseSearch colaNoctura);
}