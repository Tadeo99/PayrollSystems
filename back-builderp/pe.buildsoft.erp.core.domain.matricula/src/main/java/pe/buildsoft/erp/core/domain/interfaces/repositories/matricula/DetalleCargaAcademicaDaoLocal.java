package pe.buildsoft.erp.core.domain.interfaces.repositories.matricula;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.matricula.DetalleCargaAcademica;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class DetalleCargaAcademicaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:54 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface DetalleCargaAcademicaDaoLocal extends GenericDAOLocal<String, DetalleCargaAcademica> {
	/**
	 * Generar id detalleCargaAcademica.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	List<DetalleCargaAcademica> get(BaseSearch filtro);

	List<DetalleCargaAcademica> listar(Map<String, Object> parametrosMap);
}