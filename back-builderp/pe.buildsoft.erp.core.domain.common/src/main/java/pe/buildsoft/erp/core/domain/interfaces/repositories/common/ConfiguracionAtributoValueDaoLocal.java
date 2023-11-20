package pe.buildsoft.erp.core.domain.interfaces.repositories.common;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.common.ConfiguracionAtributoValue;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

/**
 * La Class ConfiguracionAtributoValueDaoLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Dec 28 12:02:21 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface ConfiguracionAtributoValueDaoLocal  extends GenericDAOLocal<String,ConfiguracionAtributoValue> {
	/**
	 * Listar configuracion atributo value.
	 *
	 * @param configuracionAtributoValue el configuracion atributo value
	 * @return the list
	 * @ the exception
	 */
	Map<String,List<ConfiguracionAtributoValue>> listar(List<String> listaIdConfiguracionAtributo,String idTupla ) ;
	
	boolean eliminar(List<String> listaIdConfiguracionAtributo,String idTupla);
	/**
	 * Generar id configuracionAtributoValue.
	 *
	 * @return the String
	 * @ the exception
	 */
	String generarId() ;
}