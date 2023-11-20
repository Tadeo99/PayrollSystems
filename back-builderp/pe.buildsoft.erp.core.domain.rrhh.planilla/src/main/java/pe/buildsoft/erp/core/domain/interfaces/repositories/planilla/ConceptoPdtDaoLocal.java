package pe.buildsoft.erp.core.domain.interfaces.repositories.planilla;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoPdt;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ConceptoPdtDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface ConceptoPdtDaoLocal extends GenericDAOLocal<String, ConceptoPdt> {
	/**
	 * Listar concepto pdt.
	 *
	 * @param filtro el concepto pdt
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ConceptoPdt> listar(BaseSearch filtro);

	List<ConceptoPdt> obtenerFormulaConceptoPdt();

	/**
	 * contar lista concepto pdt.
	 *
	 * @param filtro el concepto pdt
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id conceptoPdt.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	ConceptoPdt find(ConceptoPdt conceptoPdt);
}