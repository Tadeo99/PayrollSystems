package pe.buildsoft.erp.core.domain.interfaces.repositories.nota;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.nota.RegistroNota;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class RegistroNotaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface RegistroNotaDaoLocal extends GenericDAOLocal<String, RegistroNota> {
	/**
	 * Listar registro nota.
	 *
	 * @param filtro el registro nota
	 * @return the list
	 * @throws Exception the exception
	 */
	List<RegistroNota> listar(BaseSearch filtro);

	/**
	 * contar lista registro nota.
	 *
	 * @param registroNota el registro nota
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch registroNota);

	/**
	 * Generar id registroNota.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	RegistroNota find(String idMatricula);
}