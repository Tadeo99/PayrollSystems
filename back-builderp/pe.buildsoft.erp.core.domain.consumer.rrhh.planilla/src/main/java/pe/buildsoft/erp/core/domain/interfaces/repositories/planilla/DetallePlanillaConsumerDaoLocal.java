package pe.buildsoft.erp.core.domain.interfaces.repositories.planilla;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.Planilla;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class DetallePlanillaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface DetallePlanillaConsumerDaoLocal extends GenericDAOLocal<String, DetallePlanilla> {

	List<DetallePlanilla> get(BaseSearch filtro);

	/**
	 * Generar id detallePlanilla.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
	
	boolean eliminar(Planilla filtro);
}