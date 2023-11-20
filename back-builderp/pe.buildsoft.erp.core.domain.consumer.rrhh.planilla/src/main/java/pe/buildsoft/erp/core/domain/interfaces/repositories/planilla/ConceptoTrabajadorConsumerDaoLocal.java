package pe.buildsoft.erp.core.domain.interfaces.repositories.planilla;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoFijosTrabajador;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

/**
 * La Class ConceptoTrabajadorDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface ConceptoTrabajadorConsumerDaoLocal extends GenericDAOLocal<String, ConceptoFijosTrabajador> {

	Map<String, Map<String, BigDecimal>> conceptoTrajadorStaticoMap(Long idCategoriaTrabajador,List<String> listaIdPersonal);

}