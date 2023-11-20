package pe.buildsoft.erp.core.domain.interfaces.repositories.planilla;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.planilla.Planilla;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class PlanillaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface PlanillaConsumerDaoLocal extends GenericDAOLocal<String, Planilla> {

	Map<String, Map<String, BigDecimal>> listarPlanillaAntMap(List<String> listaIdPersonal, String idTipoPlanilla,
			Long idAnhio);
	
	Map<String, Map<String, BigDecimal>> listarRenta5taAntMap(List<String> listaIdPersonal, String idTipoPlanilla,
			Long idAnhio);

	/**
	 * Listar planilla.
	 *
	 * @param filtro el planilla
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Planilla> listar(BaseSearch filtro);

	/**
	 * contar lista planilla.
	 *
	 * @param filtro el planilla
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id planilla.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	boolean eliminar(Planilla filtro);
}