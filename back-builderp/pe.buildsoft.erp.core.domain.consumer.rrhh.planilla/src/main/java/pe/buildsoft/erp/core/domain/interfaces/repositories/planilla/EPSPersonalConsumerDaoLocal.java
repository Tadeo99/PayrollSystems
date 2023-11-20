package pe.buildsoft.erp.core.domain.interfaces.repositories.planilla;

import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.planilla.EPSPersonal;
import pe.buildsoft.erp.core.domain.entidades.planilla.PeriodoPlanilla;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class EPSPersonalConsumerDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface EPSPersonalConsumerDaoLocal extends GenericDAOLocal<String, EPSPersonal> {

	boolean eliminar(PeriodoPlanilla periodo);
	
	Map<String, EPSPersonal> listarMap(BaseSearch filtro);

	String generarId();

}