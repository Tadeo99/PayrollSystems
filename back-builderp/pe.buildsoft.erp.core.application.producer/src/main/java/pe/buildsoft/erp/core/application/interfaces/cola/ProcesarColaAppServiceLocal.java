package pe.buildsoft.erp.core.application.interfaces.cola;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.application.entidades.cola.ValorConfiguracionFiltroReporteDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.SolicitudReporteDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RegistroMensajeriaOutputVO;


/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Interface ProcesarColaServiceLocal.
 *
 * @author BuildSoft.
 * @version 1.0 , 07/04/2015
 * @since BuildErp 1.0
 */
@Local
public interface ProcesarColaAppServiceLocal {
	
	/**
	 * Enviar cola.
	 *
	 * @param listaObjectos el lista objectos
	 * @return the registro mensajeria output vo
	 */
	RegistroMensajeriaOutputVO enviarCola(List<Object> listaObjectos);
	
	/**
	 * Enviar cola generar solicitud reporte.
	 *
	 * @param solicitudReporteDTO el solicitud reporte dto
	 * @return the registro mensajeria output vo
	 */
	RegistroMensajeriaOutputVO enviarColaGenerarSolicitudReporte(SolicitudReporteDTO solicitudReporteDTO);
	
	/**
	 * Guardar intentos generar reporte fallidos.
	 *
	 * @param solicitudReporteDTO el solicitud reporte dto
	 * @param descripcionError el descripcion error
	 */
	void guardarIntentosGenerarReporteFallidos(SolicitudReporteDTO solicitudReporteDTO, String descripcionError, boolean isReintentar);
	/**
	 * Listar datos filtro.
	 *
	 * @param keyList el key list
	 * @return the map
	 * @throws Exception the exception
	 */
	Map<Long, List<ValorConfiguracionFiltroReporteDTO>> listarDatosFiltro(List<Long> keyList) throws Exception;
	/**
	 * Listar id componentes.
	 *
	 * @param idOpcion el id opcion
	 * @return the list
	 * @throws Exception the exception
	 */
	Map<String, List<String>> listarIdComponentes(Long idOpcion);
	
}