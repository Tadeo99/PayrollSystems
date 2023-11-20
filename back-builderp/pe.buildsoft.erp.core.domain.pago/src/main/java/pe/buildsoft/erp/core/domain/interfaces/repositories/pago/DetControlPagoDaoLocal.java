package pe.buildsoft.erp.core.domain.interfaces.repositories.pago;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.pago.DetControlPago;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

/**
 * La Class DetControlPagoDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface DetControlPagoDaoLocal extends GenericDAOLocal<String, DetControlPago> {

	/**
	 * Generar id detControlPago.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	List<DetControlPago> listarById(String idControlPago);

	List<DetControlPago> listar(String idControlPago);
}