package pe.buildsoft.erp.core.domain.interfaces.repositories.matricula;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.matricula.Alumno;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class AlumnoDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface AlumnoDaoLocal extends GenericDAOLocal<String, Alumno> {
	/**
	 * Listar alumno.
	 *
	 * @param filtro el alumno
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Alumno> listar(BaseSearch filtro);

	/**
	 * contar lista alumno.
	 *
	 * @param filtro el alumno
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id alumno.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}