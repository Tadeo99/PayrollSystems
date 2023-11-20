package pe.buildsoft.erp.core.domain.interfaces.repositories.planilla;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoRegimenPensionario;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ConceptoRegimenPensionarioDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface ConceptoRegimenPensionarioDaoLocal extends GenericDAOLocal<String, ConceptoRegimenPensionario> {

	/**
	 * Listar concepto regimen pensionario.
	 *
	 * @param conceptoRegimenPensionario el concepto regimen pensionario
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ConceptoRegimenPensionario> listar(BaseSearch filtro);

	Map<Long, ConceptoRegimenPensionario> listarMap(Long idMesDevengado, Long anhio);

	/**
	 * Generar id conceptoRegimenPensionario.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
	
	int contar(BaseSearch filtro);
}