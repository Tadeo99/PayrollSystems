package pe.buildsoft.erp.core.domain.interfaces.repositories.nota;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.nota.DetRegistroNota;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

/**
 * La Class DetRegistroNotaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface DetRegistroNotaSharedDaoLocal extends GenericDAOLocal<String, DetRegistroNota> {

	/**
	 * Generar id detRegistroNota.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	List<DetRegistroNota> listar(String idRegistroNota);

}