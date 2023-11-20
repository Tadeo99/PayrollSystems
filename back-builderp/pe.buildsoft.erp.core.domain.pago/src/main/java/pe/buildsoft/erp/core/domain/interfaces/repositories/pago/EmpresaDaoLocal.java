package pe.buildsoft.erp.core.domain.interfaces.repositories.pago;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.pago.Empresa;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class EmpresaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:31 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface EmpresaDaoLocal extends GenericDAOLocal<Long, Empresa> {
	/**
	 * Listar empresa.
	 *
	 * @param filtro el empresa
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Empresa> listar(BaseSearch filtro);

	/**
	 * contar lista empresa.
	 *
	 * @param filtro el empresa
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id empresa.
	 *
	 * @return the Long
	 * @throws Exception the exception
	 */
	Long generarId();

	Empresa find(Empresa filtro);
}