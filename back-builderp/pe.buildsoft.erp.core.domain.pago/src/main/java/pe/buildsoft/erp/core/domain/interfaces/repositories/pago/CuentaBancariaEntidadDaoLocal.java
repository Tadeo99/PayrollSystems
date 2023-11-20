package pe.buildsoft.erp.core.domain.interfaces.repositories.pago;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.pago.CuentaBancariaEntidad;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class CuentaBancariaEntidadDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:29 COT 202	1
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface CuentaBancariaEntidadDaoLocal  extends GenericDAOLocal<String,CuentaBancariaEntidad> {
	/**
	 * Listar cuenta bancaria entidad.
	 *
	 * @param filtro el cuenta bancaria entidad
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CuentaBancariaEntidad> listar(BaseSearch filtro);
	
	/**
	 * contar lista cuenta bancaria entidad.
	 *
	 * @param filtro el cuenta bancaria entidad
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id cuentaBancariaEntidad.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}