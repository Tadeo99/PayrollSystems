package pe.buildsoft.erp.core.domain.interfaces.repositories.pago;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.pago.PlanPagos;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

/**
 * La Class PlanPagosDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:31 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface PlanPagosDaoLocal extends GenericDAOLocal<String, PlanPagos> {
	/**
	 * Listar plan pagos.
	 *
	 * @param filtro el plan pagos
	 * @return the list
	 * @throws Exception the exception
	 */
	PlanPagos get(PlanPagos filtro);

	/**
	 * Generar id planPagos.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}