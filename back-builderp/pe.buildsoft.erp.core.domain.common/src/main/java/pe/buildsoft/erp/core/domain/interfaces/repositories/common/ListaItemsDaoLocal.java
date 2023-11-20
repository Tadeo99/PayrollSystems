package pe.buildsoft.erp.core.domain.interfaces.repositories.common;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.common.ListaItems;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ListaItemsDaoLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 11:25:03 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface ListaItemsDaoLocal  extends GenericDAOLocal<Long,ListaItems> {
	/**
	 * Listar lista items.
	 *
	 * @param listaItems el lista items
	 * @return the list
	 * @ the exception
	 */
	List<ListaItems> listar(BaseSearch filtro) ;
	
	/**
	 * contar lista lista items.
	 *
	 * @param listaItems el lista items
	 * @return the list
	 * @ the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id listaItems.
	 *
	 * @return the Long
	 * @ the exception
	 */
	Long generarId() ;
}