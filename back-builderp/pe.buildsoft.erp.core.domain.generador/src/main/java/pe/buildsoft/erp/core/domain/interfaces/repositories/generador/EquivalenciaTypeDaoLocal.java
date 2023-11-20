package pe.buildsoft.erp.core.domain.interfaces.repositories.generador;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.generador.EquivalenciaType;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ConfigTypeEquivalenciaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 11:45:04 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface EquivalenciaTypeDaoLocal extends GenericDAOLocal<String, EquivalenciaType> {
	/**
	 * Listar.
	 *
	 * @param configTypeEquivalencia el config type equivalenciaDTO
	 * @return the list
	 * @throws Exception the exception
	 */
	List<EquivalenciaType> listar(BaseSearch filtro);

	/**
	 * contar lista config type equivalencia.
	 *
	 * @param configTypeEquivalencia el config type equivalencia
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id configTypeEquivalencia.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}