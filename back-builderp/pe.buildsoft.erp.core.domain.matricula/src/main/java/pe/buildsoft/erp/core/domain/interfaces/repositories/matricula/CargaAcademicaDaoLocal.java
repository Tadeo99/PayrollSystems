package pe.buildsoft.erp.core.domain.interfaces.repositories.matricula;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.matricula.CargaAcademica;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class CargaAcademicaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface CargaAcademicaDaoLocal extends GenericDAOLocal<String, CargaAcademica> {
	/**
	 * Listar carga academica.
	 *
	 * @param filtro el carga academica
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CargaAcademica> listar(BaseSearch filtro);

	/**
	 * contar lista carga academica.
	 *
	 * @param filtro el carga academica
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id cargaAcademica.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}