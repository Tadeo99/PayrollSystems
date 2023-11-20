package pe.buildsoft.erp.core.domain.interfaces.servicios.cola;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.infra.transversal.entidades.SolicitudReporteDTO;
import pe.buildsoft.erp.core.infra.transversal.state.EstadoSolicitudEjecucionEstate;

/**
 * La Class ConfiguracionColaServiceLocal.
 * <ul>
 * <li>Copyright 2015 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 2.1, Thu Dec 21 12:25:45 COT 2017
 * @since BuildErp 1.0
 */
@Local
public interface IJMSSender {
	void sendMessage(Object obj);

	void sendMessagePreferencial(Object obj);

	void sendMessageRegular(Object obj);

	void sendMessagePesado(Object obj);

	String sendMessageNocturno(Object obj);

	SolicitudReporteDTO actualizarSolicitud(SolicitudReporteDTO obj,
			EstadoSolicitudEjecucionEstate estadoSolicitudEjecucionEstate, String error);

	void actualizarColaNocturna(String idColaNocturna, EstadoSolicitudEjecucionEstate estadoSolicitudEjecucionEstate);

	Integer cantidadMessage(String qcfName, String queueName);

	void sendMessageList(List<Object> listaObject);

	void sendMessageCorreoList(List<Object> listaObject);
}