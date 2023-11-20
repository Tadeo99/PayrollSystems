package pe.buildsoft.erp.core.domain.interfaces.repositories.admision;

import java.util.List;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.admision.Apoderado;
import pe.buildsoft.erp.core.domain.entidades.admision.vo.BaseSearchVO;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

/**
 * La Class ApoderadoDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Oct 21 20:17:19 COT 2020
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface ApoderadoDaoLocal extends GenericDAOLocal<String, Apoderado> {
	/**
	 * Listar apoderado.
	 *
	 * @param filtro el apoderadoDTO
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Apoderado> listar(BaseSearchVO filtro);

	/**
	 * contar lista apoderado.
	 *
	 * @param filtro el apoderado
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearchVO filtro);

	/**
	 * Generar id apoderado.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}