package pe.buildsoft.erp.core.domain.interfaces.repositories.generador;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.generador.Tecnologia;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class TecnologiaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 11:45:04 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface TecnologiaDaoLocal extends GenericDAOLocal<String, Tecnologia> {
	/**
	 * Listar.
	 *
	 * @param tecnologia el tecnologiaDTO
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Tecnologia> listar(BaseSearch filtro);

	/**
	 * contar lista tecnologia.
	 *
	 * @param tecnologia el tecnologia
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id tecnologia.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}