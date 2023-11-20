package pe.buildsoft.erp.core.domain.interfaces.repositories.generador;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.generador.ConfigParametro;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ConfigArquetipoDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 11:45:04 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface ConfigParametroDaoLocal extends GenericDAOLocal<String, ConfigParametro> {
	/**
	 * Listar.
	 *
	 * @param configArquetipo el config arquetipoDTO
	 * @return the list
	 * @throws Exception the exception
	 */
	List<ConfigParametro> listar(BaseSearch filtro);

	/**
	 * contar lista config arquetipo.
	 *
	 * @param configArquetipo el config arquetipo
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id configArquetipo.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}