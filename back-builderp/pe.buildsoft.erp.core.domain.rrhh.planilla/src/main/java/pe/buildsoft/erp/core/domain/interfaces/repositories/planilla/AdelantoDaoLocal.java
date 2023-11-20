package pe.buildsoft.erp.core.domain.interfaces.repositories.planilla;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.planilla.Adelanto;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class AdelantoDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface AdelantoDaoLocal extends GenericDAOLocal<String, Adelanto> {
	/**
	 * Listar adelanto.
	 *
	 * @param filtro el adelanto
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Adelanto> listar(BaseSearch filtro);

	/**
	 * contar lista adelanto.
	 *
	 * @param filtro el adelanto
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id adelanto.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	Map<String, BigDecimal> obtnerAdelantosMap(Long idCategoriaTrabajador);
}