package pe.buildsoft.erp.core.domain.interfaces.repositories.hora;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.hora.RequerimientoPersonal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class RequerimientoDaoLocal.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:07:25 COT 2022
 * @since BuildErp 1.0
 */
@Local
public interface RequerimientoPersonalDaoLocal extends GenericDAOLocal<String, RequerimientoPersonal> {
	/**
	 * Listar requerimiento.
	 *
	 * @param entidad el requerimiento
	 * @return the list @ the exception
	 */
	List<RequerimientoPersonal> listar(BaseSearch filtro);

	/**
	 * contar lista requerimiento.
	 *
	 * @param entidad el requerimiento
	 * @return the list @ the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id requerimiento.
	 *
	 * @return the String @ the exception
	 */
	String generarId();

}