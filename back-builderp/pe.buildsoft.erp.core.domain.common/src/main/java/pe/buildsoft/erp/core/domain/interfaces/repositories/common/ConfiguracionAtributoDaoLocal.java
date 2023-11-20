package pe.buildsoft.erp.core.domain.interfaces.repositories.common;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.common.ConfiguracionAtributo;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class ConfiguracionAtributoDaoLocal.
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
public interface ConfiguracionAtributoDaoLocal  extends GenericDAOLocal<String,ConfiguracionAtributo> {
	/**
	 * Listar configuracion atributo.
	 *
	 * @param configuracionAtributo el configuracion atributo
	 * @return the list
	 * @ the exception
	 */
	List<ConfiguracionAtributo> listar(BaseSearch filtro) ;
	
	/**
	 * contar lista configuracion atributo.
	 *
	 * @param configuracionAtributo el configuracion atributo
	 * @return the list
	 * @ the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id configuracionAtributo.
	 *
	 * @return the String
	 * @ the exception
	 */
	String generarId() ;
}