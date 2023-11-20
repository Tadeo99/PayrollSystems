package pe.buildsoft.erp.core.domain.interfaces.repositories.cola;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.cola.ConfiguracionFiltroCola;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ConfiguracionFiltroColaDaoLocal.
 * <ul>
 * <li>Copyright 2015 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 2.1, Thu Dec 21 12:16:30 COT 2017
 * @since BuildErp 1.0
 */
@Local
public interface ConfiguracionFiltroColaDaoLocal extends GenericDAOLocal<String, ConfiguracionFiltroCola> {
	/**
	 * Listar configuracion filtro cola.
	 *
	 * @param filtro el configuracion filtro cola
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ConfiguracionFiltroCola> listar(BaseSearch filtro);

	/**
	 * contar lista configuracion filtro cola.
	 *
	 * @param filtro el configuracion filtro cola
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id configuracionFiltroCola.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}