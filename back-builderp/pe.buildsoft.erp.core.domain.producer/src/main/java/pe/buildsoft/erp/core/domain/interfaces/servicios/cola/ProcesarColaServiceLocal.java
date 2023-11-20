package pe.buildsoft.erp.core.domain.interfaces.servicios.cola;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.cola.IntentoGenerarReporte;
import pe.buildsoft.erp.core.domain.entidades.cola.ValorConfiguracionFiltroReporte;
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
public interface ProcesarColaServiceLocal {
	
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
	 * @param solicitudReporte el solicitud reporte 
	 * @return the registro mensajeria output vo
	 */
	RegistroMensajeriaOutputVO enviarColaGenerarSolicitudReporte(SolicitudReporteDTO solicitudReporte);
	
	/**
	 * Guardar intentos generar reporte fallidos.
	 *
	 * @param solicitudReporte el solicitud reporte 
	 * @param descripcionError el descripcion error
	 */
	void guardarIntentosGenerarReporteFallidos(SolicitudReporteDTO solicitudReporte, String descripcionError, boolean isReintentar);
	
	boolean registrarMasivo(List<ValorConfiguracionFiltroReporte> listaValorConfiguracionFiltroReporte);
	
	boolean eliminarByIdSolicitudReporte(String idSolicitudReporte);
	
	List<ValorConfiguracionFiltroReporte> listarDatosFiltro(List<Long> keyList, List<String> listaLugarAparece);
	
	String generarIdIntento(String codigoUUID,int incremento);
	IntentoGenerarReporte registrarIntento(IntentoGenerarReporte intentoGenerarReporte);
	/**
	 * Listar id componentes.
	 *
	 * @param idOpcion el id opcion
	 * @return the list
	 * @throws Exception the exception
	 */
	Map<String, List<String>> listarIdComponentes(Long idOpcion);
	
}