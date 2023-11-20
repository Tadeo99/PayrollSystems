package pe.buildsoft.erp.core.domain.interfaces.repositories.generador;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.generador.Atributo;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ConfigTablaClassDetDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 11:45:04 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface AtributoDaoLocal extends GenericDAOLocal<String, Atributo> {
	/**
	 * Listar.
	 *
	 * @param configTablaClassDet el config tabla class detDTO
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Atributo> listar(BaseSearch filtro);

	/**
	 * contar lista config tabla class det.
	 *
	 * @param configTablaClassDet el config tabla class det
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id configTablaClassDet.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}