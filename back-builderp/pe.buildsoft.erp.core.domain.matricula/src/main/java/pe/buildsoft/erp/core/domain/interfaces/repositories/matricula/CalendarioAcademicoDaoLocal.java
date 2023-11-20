package pe.buildsoft.erp.core.domain.interfaces.repositories.matricula;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.matricula.CalendarioAcademico;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class CalendarioAcademicoDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface CalendarioAcademicoDaoLocal extends GenericDAOLocal<String, CalendarioAcademico> {
	/**
	 * Listar calendario academico.
	 *
	 * @param filtro el calendario academico
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CalendarioAcademico> listar(BaseSearch filtro);

	/**
	 * contar lista calendario academico.
	 *
	 * @param filtro el calendario academico
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id calendarioAcademico.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}