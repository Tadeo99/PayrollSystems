package pe.buildsoft.erp.core.domain.interfaces.repositories.matricula;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.matricula.Matricula;
import pe.buildsoft.erp.core.domain.entidades.matricula.vo.FichaMatriculaVO;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

/**
 * La Class MatriculaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface MatriculaDaoLocal extends GenericDAOLocal<String, Matricula> {

	Matricula get(FichaMatriculaVO filtro);

}