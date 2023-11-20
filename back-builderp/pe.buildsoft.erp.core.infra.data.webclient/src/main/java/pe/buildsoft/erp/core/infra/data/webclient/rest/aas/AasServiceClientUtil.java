package pe.buildsoft.erp.core.infra.data.webclient.rest.aas;

import java.util.HashMap;
import java.util.Map;

import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.utilitario.AppHttpHeaderNames;
import pe.buildsoft.erp.core.infra.transversal.utilitario.EjecutorRestDimanicUtil;

//@FeignClient(name = "service-aas",fallback = AasServiceClientImpl.class)
public class AasServiceClientUtil extends EjecutorRestDimanicUtil {

	protected Map<String, String> getHeaders(String authToken) {
		Map<String, String> headers = new HashMap<>();
		headers.put(AppHttpHeaderNames.SERVICE_KEY, AppHttpHeaderNames.SERVICE_KEY_VALUE);
		headers.put(AppHttpHeaderNames.AUTH_TOKEN, authToken);
		headers.put(AppHttpHeaderNames.AUTHORIZATION, "");
		headers.put(AppHttpHeaderNames.VERSION, AppHttpHeaderNames.VERSION_VALUE);
		headers.put(AppHttpHeaderNames.KEY_WEBSOCKECT, "");
		return headers;
	}

	protected <T> RespuestaWSVO<T> parsearResultadoError(Exception e, RespuestaWSVO<T> resultado) {
		resultado.setError(true);
		resultado.setCodigoError(e.getLocalizedMessage());
		resultado.setMensajeError(e.getMessage() + " --> " + e.toString());
		return resultado;
	}
}
