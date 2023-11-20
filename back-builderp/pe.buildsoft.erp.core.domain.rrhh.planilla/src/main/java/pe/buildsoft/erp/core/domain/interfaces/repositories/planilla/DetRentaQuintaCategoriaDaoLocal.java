package pe.buildsoft.erp.core.domain.interfaces.repositories.planilla;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.planilla.DetRentaQuintaCategoria;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class DetRentaQuintaCategoriaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface DetRentaQuintaCategoriaDaoLocal extends GenericDAOLocal<String, DetRentaQuintaCategoria> {
	/**
	 * Listar det renta quinta categoria.
	 *
	 * @param filtro el det renta quinta categoria
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DetRentaQuintaCategoria> listar(BaseSearch filtro);

	/**
	 * contar lista det renta quinta categoria.
	 *
	 * @param filtro el det renta quinta categoria
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id detRentaQuintaCategoria.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}