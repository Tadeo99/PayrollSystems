package pe.buildsoft.erp.core.domain.interfaces.repositories.pago;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.pago.CuotaConcepto;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class CuotaConceptoDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface CuotaConceptoDaoLocal extends GenericDAOLocal<String, CuotaConcepto> {
	/**
	 * Listar cuota concepto.
	 *
	 * @param filtro el cuota concepto
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CuotaConcepto> listar(BaseSearch filtro);

	/**
	 * contar lista cuota concepto.
	 *
	 * @param filtro el cuota concepto
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id cuotaConcepto.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}