package pe.buildsoft.erp.core.domain.interfaces.repositories.security;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.security.Properties;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class PropertiesDaoLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 11:25:11 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface PropertiesDaoLocal  extends GenericDAOLocal<Long,Properties> {
	/**
	 * Listar properties.
	 *
	 * @param properties el properties
	 * @return the list
	 * @ the exception
	 */
	List<Properties> listar(BaseSearch filtro) ;
	
	/**
	 * contar lista properties.
	 *
	 * @param properties el properties
	 * @return the list
	 * @ the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id properties.
	 *
	 * @return the Long
	 * @ the exception
	 */
	Long generarId() ;
}