package pe.buildsoft.erp.core.domain.interfaces.servicios.common;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.common.Anhio;
import pe.buildsoft.erp.core.domain.entidades.common.ConfiguracionAtributo;
import pe.buildsoft.erp.core.domain.entidades.common.ConfiguracionAtributoValue;
import pe.buildsoft.erp.core.domain.entidades.common.Item;
import pe.buildsoft.erp.core.domain.entidades.common.ListaItems;
import pe.buildsoft.erp.core.domain.entidades.common.ListaValores;
import pe.buildsoft.erp.core.domain.entidades.common.Parametro;
import pe.buildsoft.erp.core.domain.entidades.common.Ubigeo;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.FileVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.SelectItemVO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoGeneralState;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class SeguridadServiceLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 13:43:23 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface CommonServiceLocal {
	
	/**
	 * Controlador accion configuracion atributo.
	 *
	 * @param configuracionAtributo el configuracion atributo
	 * @param accionType el accion type
	 * @return the configuracion atributo
	 * @ the exception
	 */
	ConfiguracionAtributo controladorAccionConfiguracionAtributo(ConfiguracionAtributo obj,AccionType accionType) ; 
	
	/**
	 * Listar configuracion atributo.
	 *
	 * @param configuracionAtributo el configuracion atributo
	 * @return the list
	 * @ the exception
	 */
	List<ConfiguracionAtributo> listarConfiguracionAtributo(BaseSearch filtro) ;
	
	Map<String,List<ConfiguracionAtributoValue>> listarConfiguracionAtributoValueMap(List<String> listaIdConfiguracionAtributo,String idTupla);
	
	/**
	 * contar lista configuracion atributo.
	 *
	 * @param configuracionAtributo el configuracion atributo
	 * @return the list
	 * @ the exception
	 */
	int contarListarConfiguracionAtributo(BaseSearch filtro);
	
	/**
	 * Controlador accion configuracion atributo value.
	 *
	 * @param configuracionAtributoValue el configuracion atributo value
	 * @param accionType el accion type
	 * @return the configuracion atributo value
	 * @ the exception
	 */
	void registrarConfiguracionAtributoValue(List<ConfiguracionAtributo> listaDataDinamic);
	
	void eliminarConfiguracionAtributoValue(List<ConfiguracionAtributo> listaDataDinamic) ;
	
	void subirImagen(FileVO fileVO)  ;
	
	String obtenerImagenEncodeBase64(FileVO fileVO)  ;
	/**
	 * Controlador accion parametro.
	 *
	 * @param parametro el parametro
	 * @param accionType el accion type
	 * @return the parametro
	 * @ the exception
	 */
	Parametro controladorAccionParametro(Parametro obj,AccionType accionType) ; 
	
	/**
	 * Listar parametro.
	 *
	 * @param parametro el parametro
	 * @return the list
	 * @ the exception
	 */
	List<Parametro> listarParametro(BaseSearch filtro) ;
	
	/**
	 * contar lista parametro.
	 *
	 * @param parametro el parametro
	 * @return the list
	 * @ the exception
	 */
	int contarListarParametro(BaseSearch filtro);
	
	/**
     * Listar ubigeo select item.
     *
     * @param listaUbigeo el lista ubigeo
     * @return the list
     * @ the exception
     */
    List<SelectItemVO> listarUbigeoSelectItem(List<Ubigeo> listaUbigeo) ;
    
	/**
	 * Convertir lista item select item map.
	 *
	 * @param listaItem el lista item
	 * @return the map
	 * @ the exception
	 */
	Map<Long,List<SelectItemVO>> convertirListaItemSelectItemMap(List<Item> listaItem) ;
	
	/**
	 * Listar item.
	 *
	 * @param listaItemType el lista item type
	 * @return the list
	 * @ the exception
	 */
	List<Item> listarItem() ;
	
	
	/**
	 * Controlador accion lista items.
	 *
	 * @param listaItems el lista items
	 * @param accionType el accion type
	 * @return the lista items
	 * @ the exception
	 */
	ListaItems controladorAccionListaItems(ListaItems obj,AccionType accionType) ; 
	
	/**
	 * Listar lista items.
	 *
	 * @param listaItems el lista items
	 * @return the list
	 * @ the exception
	 */
	List<ListaItems> listarListaItems(BaseSearch filtro) ;
	
	/**
	 * contar lista lista items.
	 *
	 * @param listaItems el lista items
	 * @return the list
	 * @ the exception
	 */
	int contarListarListaItems(BaseSearch filtro);
	
	/**
	 * Controlador accion item.
	 *
	 * @param item el item
	 * @param accionType el accion type
	 * @return the item
	 * @ the exception
	 */
	Item controladorAccionItem(Item obj,AccionType accionType) ; 
	
	/**
	 * Listar item.
	 *
	 * @param item el item
	 * @return the list
	 * @ the exception
	 */
	List<Item> listarItem(BaseSearch filtro) ;
	
	/**
	 * contar lista item.
	 *
	 * @param item el item
	 * @return the list
	 * @ the exception
	 */
	int contarListarItem(BaseSearch filtro);
	

	/**
	 * Controlador accion ubigeo.
	 *
	 * @param ubigeo el ubigeo
	 * @param accionType el accion type
	 * @return the ubigeo
	 * @ the exception
	 */
	Ubigeo controladorAccionUbigeo(Ubigeo obj,AccionType accionType) ; 
	
	/**
	 * Listar ubigeo.
	 *
	 * @param ubigeo el ubigeo
	 * @return the list
	 * @ the exception
	 */
	List<Ubigeo> listarUbigeo(BaseSearch filtro) ;
	
	/**
	 * contar lista ubigeo.
	 *
	 * @param ubigeo el ubigeo
	 * @return the list
	 * @ the exception
	 */
	int contarListarUbigeo(BaseSearch filtro);
	
	/**
	 * Controlador accion lista valores.
	 *
	 * @param listaValores el lista valores
	 * @param accionType el accion type
	 * @return the lista valores
	 * @ the exception
	 */
	ListaValores controladorAccionListaValores(ListaValores obj,AccionType accionType) ; 
	
	/**
	 * Listar lista valores.
	 *
	 * @param listaValores el lista valores
	 * @return the list
	 * @ the exception
	 */
	List<ListaValores> listarListaValores(BaseSearch filtro) ;
	
	/**
	 * contar lista lista valores.
	 *
	 * @param listaValores el lista valores
	 * @return the list
	 * @ the exception
	 */
	int contarListarListaValores(BaseSearch filtro);

	/**
	 * Controlador accion anhio.
	 *
	 * @param obj        el anhio
	 * @param accionType el accion type
	 * @return the anhio @ the exception
	 */
	Anhio controladorAccionAnhio(Anhio obj, AccionType accionType);

	/**
	 * Listar anhio.
	 *
	 * @param filtro el anhio
	 * @return the list @ the exception
	 */
	List<Anhio> listarAnhio(BaseSearch filtro);

	/**
	 * contar lista anhio.
	 *
	 * @param filtro el anhio
	 * @return the list @ the exception
	 */
	int contarListarAnhio(BaseSearch filtro);
	
	Anhio obtenerAnhioyEstado(EstadoGeneralState estadoAnhoState);
}