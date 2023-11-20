/*
 * 
 */
package pe.com.builderp.core.service.common.util.cache;

import jakarta.ejb.Local;

/**
 * La Class ParametroCacheUtil.
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
public interface IParametroCacheUtil {

	

	/**
	 * Sincronizar data.
	 *
	 * @return the string
	 */
	 String sincronizarData() ;

	
	
	//get y set
	
	/**
	 * Comprueba si es flag cargo listado.
	 *
	 * @return true, si es flag cargo listado
	 */
	boolean isFlagCargoListado();

	/**
	 * Establece el flag cargo listado.
	 *
	 * @param flagCargoListado el new flag cargo listado
	 */
	void setFlagCargoListado(boolean flagCargoListado) ;

	String get(String key) ;

}
