package pe.buildsoft.erp.core.application.interfaces.common;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.application.entidades.common.AnhioDTO;
import pe.buildsoft.erp.core.application.entidades.common.ConfiguracionAtributoDTO;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.common.ListaItemsDTO;
import pe.buildsoft.erp.core.application.entidades.common.ListaValoresDTO;
import pe.buildsoft.erp.core.application.entidades.common.ParametroDTO;
import pe.buildsoft.erp.core.application.entidades.common.UbigeoDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.FileVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class SeguridadServiceLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 13:43:23 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface CommonAppServiceLocal {

	List<SelectItemVO> listarSelectItem(String groupName,BaseSearch filtro);
	int contarSelectItem(String groupName,BaseSearch filtro);
	/**
	 * Controlador accion configuracion atributo.
	 *
	 * @param configuracionAtributo el configuracion atributo
	 * @param accionType            el accion type
	 * @return the configuracion atributo @ the exception
	 */
	ConfiguracionAtributoDTO controladorAccionConfiguracionAtributo(ConfiguracionAtributoDTO obj,
			AccionType accionType);

	/**
	 * Listar configuracion atributo.
	 *
	 * @param configuracionAtributo el configuracion atributo
	 * @return the list @ the exception
	 */
	List<ConfiguracionAtributoDTO> listarConfiguracionAtributo(BaseSearch filtro);

	/**
	 * contar lista configuracion atributo.
	 *
	 * @param configuracionAtributo el configuracion atributo
	 * @return the list @ the exception
	 */
	int contarListarConfiguracionAtributo(BaseSearch filtro);

	/**
	 * Controlador accion configuracion atributo value.
	 *
	 * @param configuracionAtributoValue el configuracion atributo value
	 * @param accionType                 el accion type
	 * @return the configuracion atributo value @ the exception
	 */
	void registrarConfiguracionAtributoValue(List<ConfiguracionAtributoDTO> listaDataDinamic);

	void eliminarConfiguracionAtributoValue(List<ConfiguracionAtributoDTO> listaDataDinamic);

	void subirImagen(FileVO fileVO);

	String obtenerImagenEncodeBase64(FileVO fileVO);

	/**
	 * Controlador accion parametro.
	 *
	 * @param parametro  el parametro
	 * @param accionType el accion type
	 * @return the parametro @ the exception
	 */
	ParametroDTO controladorAccionParametro(ParametroDTO obj, AccionType accionType);

	/**
	 * Listar parametro.
	 *
	 * @param parametro el parametro
	 * @return the list @ the exception
	 */
	List<ParametroDTO> listarParametro(BaseSearch filtro);

	/**
	 * contar lista parametro.
	 *
	 * @param parametro el parametro
	 * @return the list @ the exception
	 */
	int contarListarParametro(BaseSearch filtro);

	/**
	 * Listar ubigeo select item.
	 *
	 * @param listaUbigeo el lista ubigeo
	 * @return the list @ the exception
	 */
	List<SelectItemVO> listarUbigeoSelectItem(List<UbigeoDTO> listaUbigeo);

	/**
	 * Convertir lista item select item map.
	 *
	 * @param listaItem el lista item
	 * @return the map @ the exception
	 */
	Map<Long, List<SelectItemVO>> convertirListaItemSelectItemMap(List<ItemDTO> listaItem);

	/**
	 * Listar item.
	 *
	 * @param listaItemType el lista item type
	 * @return the list @ the exception
	 */
	List<ItemDTO> listarItem();

	/**
	 * Controlador accion lista items.
	 *
	 * @param listaItems el lista items
	 * @param accionType el accion type
	 * @return the lista items @ the exception
	 */
	ListaItemsDTO controladorAccionListaItems(ListaItemsDTO listaItems, AccionType accionType);

	/**
	 * Listar lista items.
	 *
	 * @param listaItems el lista items
	 * @return the list @ the exception
	 */
	List<ListaItemsDTO> listarListaItems(BaseSearch filtro);

	/**
	 * contar lista lista items.
	 *
	 * @param listaItems el lista items
	 * @return the list @ the exception
	 */
	int contarListarListaItems(BaseSearch filtro);

	/**
	 * Controlador accion item.
	 *
	 * @param item       el item
	 * @param accionType el accion type
	 * @return the item @ the exception
	 */
	ItemDTO controladorAccionItem(ItemDTO obj, AccionType accionType);

	/**
	 * Listar item.
	 *
	 * @param item el item
	 * @return the list @ the exception
	 */
	List<ItemDTO> listarItem(BaseSearch filtro);

	/**
	 * contar lista item.
	 *
	 * @param item el item
	 * @return the list @ the exception
	 */
	int contarListarItem(BaseSearch filtro);

	/**
	 * Controlador accion ubigeo.
	 *
	 * @param ubigeo     el ubigeo
	 * @param accionType el accion type
	 * @return the ubigeo @ the exception
	 */
	UbigeoDTO controladorAccionUbigeo(UbigeoDTO obj, AccionType accionType);

	/**
	 * Listar ubigeo.
	 *
	 * @param ubigeo el ubigeo
	 * @return the list @ the exception
	 */
	List<UbigeoDTO> listarUbigeo(BaseSearch filtro);

	/**
	 * contar lista ubigeo.
	 *
	 * @param ubigeo el ubigeo
	 * @return the list @ the exception
	 */
	int contarListarUbigeo(BaseSearch filtro);

	/**
	 * Controlador accion lista valores.
	 *
	 * @param listaValores el lista valores
	 * @param accionType   el accion type
	 * @return the lista valores @ the exception
	 */
	ListaValoresDTO controladorAccionListaValores(ListaValoresDTO obj, AccionType accionType);

	/**
	 * Listar lista valores.
	 *
	 * @param listaValores el lista valores
	 * @return the list @ the exception
	 */
	List<ListaValoresDTO> listarListaValores(BaseSearch filtro);

	/**
	 * contar lista lista valores.
	 *
	 * @param listaValores el lista valores
	 * @return the list @ the exception
	 */
	int contarListarListaValores(BaseSearch filtro);

	/**
	 * Controlador accion anhio.
	 *
	 * @param obj        el anhio
	 * @param accionType el accion type
	 * @return the anhio
	 * @throws Exception the exception
	 */
	AnhioDTO controladorAccionAnhio(AnhioDTO obj, AccionType accionType);

	/**
	 * Listar anhio.
	 *
	 * @param filtro el anhio
	 * @return the list
	 * @throws Exception the exception
	 */
	List<AnhioDTO> listarAnhio(BaseSearch filtro);

	/**
	 * contar lista anhio.
	 *
	 * @param filtro el anhio
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListarAnhio(BaseSearch filtro);

	AnhioDTO obtenerAnhioyEstado(EstadoGeneralState estadoAnhoState);

}