package pe.buildsoft.erp.core.domain.interfaces.repositories.admision;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.admision.AsignaPostulante;
import pe.buildsoft.erp.core.domain.entidades.admision.vo.BaseSearchVO;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

/**
 * La Class AsignaPostulanteDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Oct 21 20:17:19 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface AsignaPostulanteDaoLocal extends GenericDAOLocal<String, AsignaPostulante> {

	Map<String, Long> listarAsignaPostulanteMap(List<String> listaIdSede);

	/**
	 * Listar asigna postulante.
	 *
	 * @param filtro el asigna postulanteDTO
	 * @return the list
	 * @throws Exception the exception
	 */
	List<AsignaPostulante> listar(BaseSearchVO filtro);

	/**
	 * contar lista asigna postulante.
	 *
	 * @param filtro el asigna postulante
	 * @return the list
	 * @throws Exception the exception
	 */
	int contarListar(BaseSearchVO filtro);

	/**
	 * Generar id asignaPostulante.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}