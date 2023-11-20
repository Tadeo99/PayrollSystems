package pe.buildsoft.erp.core.domain.interfaces.repositories.pago;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.pago.CatalogoCuenta;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class CatalogoCuentaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:30 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface CatalogoCuentaDaoLocal extends GenericDAOLocal<Long, CatalogoCuenta> {
	/**
	 * Listar catalogo cuenta.
	 *
	 * @param filtro el catalogo cuenta
	 * @return the list
	 * @throws Exception the exception
	 */
	List<CatalogoCuenta> listar(BaseSearch filtro);

	/**
	 * contar lista catalogo cuenta.
	 *
	 * @param filtro el catalogo cuenta
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id catalogoCuenta.
	 *
	 * @return the Long
	 * @throws Exception the exception
	 */
	Long generarId();

	CatalogoCuenta find(CatalogoCuenta filtro);
}