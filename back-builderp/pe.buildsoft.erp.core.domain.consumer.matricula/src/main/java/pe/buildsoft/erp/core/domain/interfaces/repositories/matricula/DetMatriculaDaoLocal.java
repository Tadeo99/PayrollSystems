package pe.buildsoft.erp.core.domain.interfaces.repositories.matricula;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.matricula.DetMatricula;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

/**
 * La Class DetMatriculaDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface DetMatriculaDaoLocal extends GenericDAOLocal<String, DetMatricula> {

	List<DetMatricula> listar(String idMatricula);
}