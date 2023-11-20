package pe.buildsoft.erp.core.domain.interfaces.repositories.common;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.common.Item;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ItemDaoLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 11:25:04 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface ItemDaoLocal  extends GenericDAOLocal<Long,Item> {
	
	/**
	 * Listar item.
	 *
	 * @param listaItemType el lista item type
	 * @return the list
	 * @ the exception
	 */
	List<Item> listar() ;
	
	/**
	 * Listar item.
	 *
	 * @param item el item
	 * @return the list
	 * @ the exception
	 */
	List<Item> listar(BaseSearch filtro) ;
	
	/**
	 * contar lista item.
	 *
	 * @param item el item
	 * @return the list
	 * @ the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id item.
	 *
	 * @return the Long
	 * @ the exception
	 */
	Long generarId() ;
}