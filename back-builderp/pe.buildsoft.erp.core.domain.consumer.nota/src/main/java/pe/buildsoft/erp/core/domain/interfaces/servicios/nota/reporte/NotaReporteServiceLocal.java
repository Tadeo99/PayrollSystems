package pe.buildsoft.erp.core.domain.interfaces.servicios.nota.reporte;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.nota.vo.ReporteNotaVO;

/**
 * La Class NotaServiceLocal.
 * <ul>
 * <li>Copyright 2017 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 13:43:23 COT 2017
 * @since SIAA-CORE 2.1
 */
@Local
public interface NotaReporteServiceLocal {
	String generarReporteMultiple(ReporteNotaVO filtro);
}