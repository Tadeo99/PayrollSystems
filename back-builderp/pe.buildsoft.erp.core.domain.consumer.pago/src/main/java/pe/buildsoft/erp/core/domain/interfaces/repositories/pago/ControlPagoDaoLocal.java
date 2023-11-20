package pe.buildsoft.erp.core.domain.interfaces.repositories.pago;

import java.util.List;
import java.util.Map;

import pe.buildsoft.erp.core.domain.entidades.pago.ControlPago;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ControlPagoDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
public interface ControlPagoDaoLocal extends GenericDAOLocal<String, ControlPago> {
	/**
	 * Listar control pago.
	 *
	 * @param filtro el control pago
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ControlPago> listar(BaseSearch filtro);

	/**
	 * contar lista control pago.
	 *
	 * @param filtro el control pago
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id controlPago.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	List<Object[]> listaControlPagoGrupByFechas();

	List<ControlPago> listaVentaExtracionF();

	void updateVentaExtracion();

	List<ControlPago> listarById(String idControlPago);

	List<Map<String, Object>> generarArchivosPlanosXML(ControlPago filtro);
}