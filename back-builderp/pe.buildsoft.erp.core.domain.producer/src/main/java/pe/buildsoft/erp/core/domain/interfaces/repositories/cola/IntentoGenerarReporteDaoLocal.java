package pe.buildsoft.erp.core.domain.interfaces.repositories.cola;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.cola.IntentoGenerarReporte;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;

/**
 * La Class IntentoGenerarReporteDaoLocal.
 * <ul>
 * <li>Copyright 2014 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Thu Aug 21 17:34:21 COT 2014
 * @since BuildErp 1.0
 */
@Local
public interface IntentoGenerarReporteDaoLocal extends GenericDAOLocal<String, IntentoGenerarReporte> {

	/**
	 * Listar intento generar reporte.
	 *
	 * @param filtro el intento generar reporte
	 * @return the list
	 * @throws Exception the exception
	 */
	List<IntentoGenerarReporte> listar(IntentoGenerarReporte filtro);

	void eliminarIntentoGenerarReporte(String idSolicitudReporte);

	/**
	 * Generar id intento.
	 *
	 * @param codigoUuid el codigo uuid
	 * @param incremento el incremento
	 * @return the string
	 * @throws Exception the exception
	 */
	String generarId(String codigoUuid, int incremento);
}