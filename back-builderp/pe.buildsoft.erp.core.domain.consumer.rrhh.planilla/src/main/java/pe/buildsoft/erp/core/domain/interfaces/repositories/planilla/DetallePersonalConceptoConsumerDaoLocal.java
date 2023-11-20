package pe.buildsoft.erp.core.domain.interfaces.repositories.planilla;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePersonalConcepto;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

/**
 * La Class DetallePersonalConceptoDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface DetallePersonalConceptoConsumerDaoLocal extends GenericDAOLocal<String, DetallePersonalConcepto> {

	Map<String,Map<String, DetallePersonalConcepto>> listarMap(List<String> listaIdPersonal, String idTipoPlanilla, String idPeriodo);
}