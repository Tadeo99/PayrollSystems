package pe.buildsoft.erp.core.domain.interfaces.repositories.planilla;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoFijosTrabajador;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ConceptoTrabajadorDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface ConceptoTrabajadorDaoLocal extends GenericDAOLocal<String, ConceptoFijosTrabajador> {

	/**
	 * Listar concepto trabajabador.
	 *
	 * @param filtro el concepto trabajabador
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ConceptoFijosTrabajador> listar(BaseSearch filtro);

	/**
	 * contar lista concepto trabajabador.
	 *
	 * @param filtro el concepto trabajabador
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id conceptoTrabajabador.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	ConceptoFijosTrabajador find(ConceptoFijosTrabajador filtro);

	List<ConceptoFijosTrabajador> listarByTrabajador(String idTrabajador);
}