package pe.buildsoft.erp.core.domain.interfaces.repositories.admision;

import java.util.List;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.admision.Postulante;
import pe.buildsoft.erp.core.domain.entidades.admision.vo.BaseSearchVO;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

/**
 * La Class PostulanteDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Oct 21 20:17:20 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface PostulanteDaoLocal extends GenericDAOLocal<String, Postulante> {
	/**
	 * Listar postulante.
	 *
	 * @param filtro el postulanteDTO
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Postulante> listar(BaseSearchVO filtro);

	/**
	 * contar lista postulante.
	 *
	 * @param filtro el postulante
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearchVO filtro);

	/**
	 * Generar id postulante.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}