package pe.buildsoft.erp.core.infra.transversal.util;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Map;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.buildsoft.erp.core.infra.transversal.entidades.BasePagination;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.AppHttpHeaderNames;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConfiguracionActiveDirectoryWSUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.TransferDataUtil;

public class GenericServiceRestImpl {
	public String getUrl(String key) {
		return ConfiguracionActiveDirectoryWSUtil.getWebService(key);
	}

	public String getToken(HttpHeaders headers) {
		return headers.getRequestHeaders().getFirst(AppHttpHeaderNames.AUTH_TOKEN);
	}

	public String getServiceKey(HttpHeaders headers) {
		return headers.getRequestHeaders().getFirst(AppHttpHeaderNames.SERVICE_KEY);
	}

	public String getNodos() {
		return testNodos();
	}

	public static String testNodos() {
		String resultado = "";
		try {
			String nodeName = System.getProperty("jboss.node.name");
			if (nodeName == null) {
				nodeName = InetAddress.getLocalHost().getHostName() + " >>";
			}
			resultado = nodeName;
		} catch (Exception e) {
			resultado = e.getMessage();
		}
		return resultado;
	}

	public Response ping(String recurso) {
		RespuestaWSVO<String> resultado = inicializar(new RespuestaWSVO<String>());
		resultado.setMensajeError(
				"OK...(" + getNodos() + ")..." + recurso + "...ping " + FechaUtil.obtenerFechaActual());
		return ok(resultado);
	}

	public <T> RespuestaWSVO<T> parsearResultadoError(Exception e, RespuestaWSVO<T> resultado) {
		resultado.setError(true);
		resultado.setCodigoError("999");
		resultado.setMensajeError(e.getMessage() + " --> " + e.toString());
		return resultado;
	}

	public <T> RespuestaWSVO<T> inicializar(RespuestaWSVO<T> resultado) {
		resultado.setCodigoError("");
		resultado.setError(false);
		resultado.setMensajeError("");
		resultado.setListaResultado(new ArrayList<>());
		return resultado;
	}

	public <T> RespuestaWSVO<T> parsearResultadoError(RespuestaWSVO<T> resultado, String codigoError,
			String mensajeError) {
		resultado.setError(true);
		resultado.setCodigoError(codigoError);
		resultado.setMensajeError(mensajeError);
		return resultado;
	}

	public Response noContent(Object object) {
		return Response.status(Response.Status.NO_CONTENT).type(MediaType.APPLICATION_JSON).entity(object).build();
	}

	public Response ok(Object object) {
		return Response.status(Response.Status.OK).entity(object).build();
	}

	public Response aceptado(Object object) {
		return Response.status(Response.Status.ACCEPTED).entity(object).build();
	}

	public Response created(Object object) {
		return Response.status(Response.Status.CREATED).entity(object).build();
	}

	public Response internalServerError(Object object) {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(object).build();
	}

	public Response noAutorizado(Object object) {
		return Response.status(Response.Status.UNAUTHORIZED).entity(object).build();
	}

	public <T> Response respuesta(RespuestaWSVO<T> resultado, AccionType accionType) {
		return respuesta(resultado, accionType, null);
	}
	
	public <T> Response respuesta(RespuestaWSVO<T> resultado, AccionType accionType,@Context UriInfo info) {
		resultado.setPagination(toPagination(info));
		resultado.getPagination().setLength(resultado.getContador());
		if (!resultado.isError()) {
			if (resultado.getObjetoResultado() != null || !CollectionUtil.isEmpty(resultado.getListaResultado())) {
				if (accionType != null && AccionType.CREAR.equals(accionType))
					return created(resultado);

				return ok(resultado);
			} else {
				if (accionType == null) {
					return ok(resultado);
				}
				return noContent(resultado);
			}
		} else {
			return internalServerError(resultado);
		}
	}

	public <T> Response respuestaCola(RespuestaWSVO<T> resultado) {
		if (!resultado.isError()) {
			return aceptado(resultado);
		} else {
			return internalServerError(resultado);
		}
	}

	public <T> T toRest(@Context UriInfo info, Class<T> entityClassDTO) {
		return TransferDataUtil.toRestDTO(info, entityClassDTO);
	}

	protected Map<String, Object> toMap(@Context UriInfo info) {
		return TransferDataUtil.toGetRestMap(info);
	}

	protected BaseSearch to(@Context UriInfo info) {
		BaseSearch resultado = toRest(info, BaseSearch.class);
		resultado.setStartRow(resultado.getStartRow()* resultado.getOffSet());
		return resultado;
	}
	
	protected BasePagination toPagination(@Context UriInfo info) {
		return info != null ? toRest(info, BasePagination.class) : new BasePagination();
	}

}