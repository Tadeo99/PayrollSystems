package pe.buildsoft.erp.core.domain.interfaces.repositories.hora;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.hora.Periodo;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class PeriodoDaoLocal.
 * <ul>
 * <li>Copyright 2021 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:07:25 COT 2022
 * @since BuildErp 1.0
 */
@Local
public interface PeriodoDaoLocal  extends GenericDAOLocal<String,Periodo> {
	/**
	 * Listar periodo.
	 *
	 * @param periodo el periodoDTO
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Periodo> listar(BaseSearch obj);
	
	List<Periodo> listarPeriodoMax(Periodo filtro);
	
	/**
	 * contar lista periodo.
	 *
	 * @param periodo el periodo
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch obj);
	/**
	 * Generar id periodo.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}