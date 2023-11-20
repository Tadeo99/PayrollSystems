package pe.buildsoft.erp.core.domain.interfaces.repositories.pago;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.pago.DetPlanPagos;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class DetPlanPagosDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:33 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface DetPlanPagosDaoLocal extends GenericDAOLocal<String, DetPlanPagos> {
	/**
	 * Listar det plan pagos.
	 *
	 * @param filtro el det plan pagos
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DetPlanPagos> listar(BaseSearch filtro);

	/**
	 * contar lista det plan pagos.
	 *
	 * @param filtro el det plan pagos
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id detPlanPagos.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	List<DetPlanPagos> listar(Long idAnho, Long idPeriodo, String idAlumno,
			boolean flagFaltaMontoResta);

	DetPlanPagos find(DetPlanPagos filtro);
}