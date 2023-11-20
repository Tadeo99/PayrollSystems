package pe.buildsoft.erp.core.domain.interfaces.repositories.planilla;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptosTipoPlanilla;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ConceptosTipoPlanillaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface ConceptosTipoPlanillaDaoLocal  extends GenericDAOLocal<String,ConceptosTipoPlanilla> {

	/**
	 * Listar conceptos tipo planilla.
	 *
	 * @param filtro el conceptos tipo planilla
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ConceptosTipoPlanilla> listar(BaseSearch filtro);
	
	/**
	 * contar lista conceptos tipo planilla.
	 *
	 * @param filtro el conceptos tipo planilla
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id conceptosTipoPlanilla.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
	
	List<ConceptosTipoPlanilla> listarByTipoPlanilla(String idTipoPlanilla);
	
}