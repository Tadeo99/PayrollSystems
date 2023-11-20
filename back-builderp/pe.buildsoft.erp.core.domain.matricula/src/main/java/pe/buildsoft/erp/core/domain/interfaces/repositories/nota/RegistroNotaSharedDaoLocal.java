package pe.buildsoft.erp.core.domain.interfaces.repositories.nota;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.nota.RegistroNota;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

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
public interface RegistroNotaSharedDaoLocal extends GenericDAOLocal<String, RegistroNota> {

	/**
	 * Generar id registroNota.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	RegistroNota get(String idMatricula);

}