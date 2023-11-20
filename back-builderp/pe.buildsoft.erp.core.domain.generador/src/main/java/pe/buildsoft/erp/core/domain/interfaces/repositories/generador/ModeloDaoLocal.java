package pe.buildsoft.erp.core.domain.interfaces.repositories.generador;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.generador.Modelo;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ConfigTablaClassDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 11:45:04 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface ModeloDaoLocal extends GenericDAOLocal<String, Modelo> {
	/**
	 * Listar.
	 *
	 * @param configTablaClass el config tabla classDTO
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Modelo> listar(BaseSearch filtro);

	/**
	 * contar lista config tabla class.
	 *
	 * @param configTablaClass el config tabla class
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id configTablaClass.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}