package pe.buildsoft.erp.core.domain.interfaces.repositories.matricula;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.matricula.DetMatricula;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class DetMatriculaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface DetMatriculaDaoLocal extends GenericDAOLocal<String, DetMatricula> {

	/**
	 * Listar det matricula.
	 *
	 * @param filtro el det matricula
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DetMatricula> listar(BaseSearch filtro);

	/**
	 * contar lista det matricula.
	 *
	 * @param filtro el det matricula
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id detMatricula.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	List<DetMatricula> listar(String idMatricula);
}