package pe.buildsoft.erp.core.domain.interfaces.repositories.planilla;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.planilla.InformaOtrosIngreso5ta;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

/**
 * La Class InformaOtrosIngreso5taDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface InformaOtrosIngreso5taConsumerDaoLocal extends GenericDAOLocal<String, InformaOtrosIngreso5ta> {
	/**
	 * Listar map.
	 *
	 * @param informaOtrosIngreso5ta el informa otros ingreso5ta
	 * @return the list
	 * @throws Exception the exception
	 */
	Map<String, BigDecimal> listarMap(Long idAnhio, List<String> listaIdPersonal);
}