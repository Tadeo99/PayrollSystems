package pe.buildsoft.erp.core.domain.interfaces.repositories.planilla;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePlanillaConcepto;
import pe.buildsoft.erp.core.domain.entidades.planilla.Planilla;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

/**
 * La Class DetallePlanillaConceptoDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface DetallePlanillaConceptoConsumerDaoLocal extends GenericDAOLocal<String, DetallePlanillaConcepto> {

	List<DetallePlanillaConcepto> get(DetallePlanillaConcepto filtro);

	/**
	 * Generar id detallePlanillaConcepto.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	boolean eliminar(Planilla filtro);
}