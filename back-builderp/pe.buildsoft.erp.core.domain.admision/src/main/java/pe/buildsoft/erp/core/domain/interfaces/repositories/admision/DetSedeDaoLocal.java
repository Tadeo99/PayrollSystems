package pe.buildsoft.erp.core.domain.interfaces.repositories.admision;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.admision.DetSede;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class DetSedeDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Oct 21 20:17:19 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface DetSedeDaoLocal  extends GenericDAOLocal<String,DetSede> {
	
	Map<String,List<DetSede>> listarDetSedeMap(List<String> listaIdSede);
	/**
	 * Listar det sede.
	 *
	 * @param filtro el det sedeDTO
	 * @return the list
	 * @throws Exception the exception
	 */
	List<DetSede> listar(BaseSearch filtro);
	
	/**
	 * contar lista det sede.
	 *
	 * @param filtro el det sede
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id detSede.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */	
	String generarId();
	
	void eliminarBySede(String idSede);
}