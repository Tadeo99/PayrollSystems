package pe.com.builderp.core.service.common.util.cache;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.common.UbigeoDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.type.ListaItemType;

/**
 * <ul>
 * <li>Copyright 2012 BUILD SOFT S.A.C - BS. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class SelectItemServiceCacheUtil.
 *
 * @author ndavilal.
 * @version 1.0 , 25/03/2012
 * @since SIAA 2.0
 */
@Local
public interface ISelectItemServiceCacheUtil {

	/**
	 * Inicializar.
	 */
	String inicializar();

	/**
	 * Cargar ubigeo.
	 */
	void cargarUbigeo();

	/**
	 * Actualizar item.
	 *
	 * @param item       el item
	 * @param accionType el accion type
	 */
	void actualizarItem(ItemDTO item, AccionType accionType);

	/**
	 * Obtener item.
	 *
	 * @param id el id
	 * @return the item
	 */
	ItemDTO obtenerItem(Long id);

	ItemDTO obtenerItemByCodigo(String codigo);

	/**
	 * Obtener ubigeo by id.
	 *
	 * @param id el id
	 * @return the ubigeo
	 */
	UbigeoDTO obtenerUbigeoById(String id);

	/**
	 * Obtener selec cbo item.
	 *
	 * @param itemType el item type
	 * @return the list
	 */
	List<SelectItemVO> obtenerSelecCboItem(ListaItemType itemType);

	List<SelectItemVO> obtenerSelecCboItem(Long idListaItem);

	List<SelectItemVO> obtenerSeleItemVOByKey(String keySelectItemVO);

	/**
	 * Obtener selec cbo item.
	 *
	 * @param itemType el item type
	 * @param id       el id
	 * @return the list
	 */
	List<SelectItemVO> obtenerSelecCboItem(ListaItemType itemType, Object id);

	/**
	 * Obtener selec rb item.
	 *
	 * @param itemType el item type
	 * @return the list
	 */
	List<SelectItemVO> obtenerSelecRbItem(ListaItemType itemType);

	/**
	 * Obtener selec ubigeo dependencia.
	 *
	 * @param id el id
	 * @return the list
	 */
	List<SelectItemVO> obtenerSelecUbigeoDependencia(String id);

	List<ItemDTO> converItemDTO(List<SelectItemVO> listaSelectItemVO);

}