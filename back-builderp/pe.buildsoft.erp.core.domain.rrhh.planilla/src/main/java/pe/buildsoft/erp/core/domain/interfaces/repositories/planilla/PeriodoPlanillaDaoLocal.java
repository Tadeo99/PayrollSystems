package pe.buildsoft.erp.core.domain.interfaces.repositories.planilla;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.planilla.PeriodoPlanilla;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class PeriodoPlanillaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface PeriodoPlanillaDaoLocal extends GenericDAOLocal<String, PeriodoPlanilla> {
	/**
	 * Listar periodo planilla.
	 *
	 * @param filtro el periodo planilla
	 * @return the list
	 * @throws Exception the exception
	 */
	List<PeriodoPlanilla> listar(BaseSearch filtro);

	/**
	 * contar lista periodo planilla.
	 *
	 * @param filtro el periodo planilla
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id periodoPlanilla.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	PeriodoPlanilla find(PeriodoPlanilla filtro);
}