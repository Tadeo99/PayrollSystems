package pe.buildsoft.erp.core.domain.interfaces.repositories.generador;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.generador.Proyecto;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ProyectoDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 11:45:04 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface ProyectoDaoLocal extends GenericDAOLocal<String, Proyecto> {
	/**
	 * Listar.
	 *
	 * @param proyecto el proyectoDTO
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Proyecto> listar(BaseSearch filtro);

	/**
	 * contar lista proyecto.
	 *
	 * @param proyecto el proyecto
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id proyecto.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}