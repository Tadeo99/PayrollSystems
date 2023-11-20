package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

/**
 * La Class EjecutorRestDimanicUtil.
 * <ul>
 * <li>Copyright 2014 MAPFRE - mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, 23/05/2021
 * @since PWR v1.0
 */
//https://www.baeldung.com/httpclient-post-http-request
//https://mkyong.com/java/apache-httpclient-examples/
//https://howtodoinjava.com/java/library/jaxrs-client-httpclient-get-post/
//https://www.javaguides.net/2018/10/apache-httpclient-post-http-request-example.html
//revisar:https://zetcode.com/java/getpostrequest/
public class EjecutorRestDimanicUtil extends ProxyWSUtil {

	private static final String STATUS_CODE = " statusCode = ";
	private static final String ERROR = "Error ";
	public static final String URL_REST_AAS_BACK = "rest.aas.back.base.url";
	public static final String URL_REST_ESCALAFON_BACK = "rest.rrhh.escalafon.back.base.url";
	public static final String URL_REST_COMMON_BACK = "rest.common.back.base.url";
	public static final String URL_REST_SECURITY_BACK = "rest.security.back.base.url";
	public static final String URL_REST_SUNAT_BACK = "rest.sunat.back.base.url";
	public static final String TRANSACCTION_TIMEOUT = "86400";

	private String urlBaseKey;

	/**
	 * Instancia un nuevo proceso seleccion ficha service impl.
	 */
	public EjecutorRestDimanicUtil() {
		// Constructor
	}

	public String toString(List<String> listaParametro) {
		return (listaParametro.toString().replace("[", "").replace("]", "")).trim();
	}

	public TypeReference<RespuestaWSVO<String>> getValueTypeRef() {
		return new TypeReference<RespuestaWSVO<String>>() {
		};
	}

	public ObjectMapper objectMapper() {
		var objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		return objectMapper;
	}

	private String getUrl(String key) {
		if (ConfiguracionActiveDirectoryWSUtil.isWebServiceKey(key)) {
			return ConfiguracionActiveDirectoryWSUtil.getWebService(key);
		} else if (URL_REST_AAS_BACK.equalsIgnoreCase(key)) {
			return "http://localhost:8080/aas/api/";
		} else if (URL_REST_SUNAT_BACK.equalsIgnoreCase(key)) {
			return "https://dniruc.apisperu.com/api/v1/";
		}
		return "";
	}

	public Map<String, String> generarHeaderMap(boolean isOnline, String archivoName) {
		Map<String, String> resultado = new HashMap<>();
		resultado.put("isBatch", (!isOnline) + "");
		resultado.put("ARCHIVO_NAME", archivoName);
		return resultado;
	}

	private Header[] obtenerHttpHeaders(String usuario, String password, String token,
			Map<String, String> paramHeader) {
		var headers = new ArrayList<>();
		headers.add(new BasicHeader("usuario", usuario));
		headers.add(new BasicHeader("password", password));

		if (token != null && !token.equalsIgnoreCase("")) {
			headers.add(new BasicHeader("Authorization", "Bearer " + token));
		}
		if (paramHeader != null) {
			for (var objMap : paramHeader.entrySet()) {
				headers.add(new BasicHeader(objMap.getKey(), objMap.getValue()));
			}
		}
		headers.add(new BasicHeader("Content-Type", "application/json;charset=UTF-8"));

		headers.add(new BasicHeader("Accept", "application/json"));
		headers.add(new BasicHeader("Content-type", "application/json"));

		return headers.toArray(new Header[headers.size()]);
	}

	private Header[] obtenerHttpHeaders(Map<String, String> paramHeader) {
		return obtenerHttpHeaders(paramHeader.get("usuario"), paramHeader.get("password"), paramHeader.get("token"),
				paramHeader);
	}

	public CloseableHttpClient restTemplate() {
		return HttpClients.createDefault();
	}

	public <T> T post(String url, Object objData, Map<String, String> paramHeader, TypeReference<T> valueTypeRef)
			throws IOException {
		var objectMapper = objectMapper();
		var objBody = getWriteValueAsString(objData);
		var restTemplate = restTemplate();
		var headers = obtenerHttpHeaders(paramHeader);
		var urlFinal = getUrl(getUrlBaseKey()) + url;
		var httpPost = new HttpPost(urlFinal);
		httpPost.setHeaders(headers);
		var stringEntity = new StringEntity(objBody, Charset.defaultCharset());
		httpPost.setEntity(stringEntity);
		var res = restTemplate.execute(httpPost);
		var respJson = EntityUtils.toString(res.getEntity());
		generarExepcion(res, urlFinal, valueTypeRef);
		return objectMapper.readValue(respJson, valueTypeRef);
	}

	private String getWriteValueAsString(Object objData) throws JsonProcessingException {
		if (objData instanceof String parametros) {
			return parametros;
		} else {
			var mapper = new ObjectMapper();
			return mapper.writeValueAsString(objData);
		}
	}

	public <T, K> T put(String url, Object objData, Map<String, String> paramHeader, TypeReference<T> valueTypeRef)
			throws IOException {
		return put(url, objData, null, paramHeader, valueTypeRef);
	}

	public <T, K> T put(String url, Object objData, K pk, Map<String, String> paramHeader,
			TypeReference<T> valueTypeRef) throws IOException {
		var objectMapper = objectMapper();
		var objBody = getWriteValueAsString(objData);
		var restTemplate = restTemplate();
		var headers = obtenerHttpHeaders(paramHeader);
		var urlFinal = url;
		if (pk != null) {
			urlFinal = url + "/" + pk;
		}
		var urlFinalCompleto = getUrl(getUrlBaseKey()) + urlFinal;
		var httpPut = new HttpPut(urlFinalCompleto);
		httpPut.setHeaders(headers);
		var stringEntity = new StringEntity(objBody);
		httpPut.setEntity(stringEntity);
		var res = restTemplate.execute(httpPut);
		generarExepcion(res, urlFinalCompleto, valueTypeRef);
		var respJson = EntityUtils.toString(res.getEntity());
		return objectMapper.readValue(respJson, valueTypeRef);

	}

	private String generarUrl(String url, String recurso, Map<String, Object> parametros) {
		var uriBuilder = new StringBuilder(url + recurso);
		if (parametros != null && parametros.size() > 0) {
			uriBuilder.append("?");
			var contador = 0;
			for (var param : parametros.entrySet()) {
				if (param.getValue() != null && param.getValue().getClass().isAssignableFrom(ArrayList.class)) {
					contador++;
					var listaParam = (List<String>) param.getValue();
					for (var valueParam : listaParam) {
						uriBuilder.append(param.getKey());
						uriBuilder.append("=");
						uriBuilder.append(valueParam);
						if (contador != parametros.size())
							uriBuilder.append("&");
					}

				} else {
					contador++;
					uriBuilder.append(param.getKey());
					uriBuilder.append("=");
					uriBuilder.append(param.getValue());
					if (contador != parametros.size())
						uriBuilder.append("&");
				}
			}
		}
		return uriBuilder.toString();
	}

	public <T> T get(String url, Map<String, Object> parametros, Map<String, String> paramHeader,
			TypeReference<T> valueTypeRef) throws IOException {
		var objectMapper = objectMapper();
		var restTemplate = restTemplate();
		var headers = obtenerHttpHeaders(paramHeader);
		var urlFinal = getUrl(getUrlBaseKey()) + url;
		var httpGet = new HttpGet(generarUrl(urlFinal, "", parametros));
		httpGet.setHeaders(headers);
		var res = restTemplate.execute(httpGet);
		generarExepcion(res, urlFinal, valueTypeRef);
		var respJson = EntityUtils.toString(res.getEntity());
		return objectMapper.readValue(respJson, valueTypeRef);
	}

	public <T, K> T delete(String url, K pk, Map<String, String> paramHeader, TypeReference<T> valueTypeRef,
			boolean isUrriInfo) throws IOException {
		var objectMapper = objectMapper();
		var restTemplate = restTemplate();
		var headers = obtenerHttpHeaders(paramHeader);
		String urlFinal = url + "/" + pk;
		if (isUrriInfo) {
			urlFinal = generarUrl(url, "", putParametros(pk));
		}
		var urlFinalCompleta = getUrl(getUrlBaseKey()) + urlFinal;

		var httpPut = new HttpPut(urlFinalCompleta);
		httpPut.setHeaders(headers);
		var res = restTemplate.execute(httpPut);
		generarExepcion(res, urlFinalCompleta, valueTypeRef);
		var respJson = EntityUtils.toString(res.getEntity());
		return objectMapper.readValue(respJson, valueTypeRef);
	}

	private <T> void generarExepcion(CloseableHttpResponse res, String url, TypeReference<T> valueTypeRef)
			throws IOException {
		int statusCode = res.getStatusLine().getStatusCode();
		if (HttpStatus.SC_NO_CONTENT == statusCode) {
			throw new IOException(ERROR + url + STATUS_CODE + statusCode + "  NOT_FOUND");
		}
		if (HttpStatus.SC_BAD_REQUEST == statusCode || HttpStatus.SC_NOT_FOUND == statusCode) {
			throw new IOException(ERROR + url + STATUS_CODE + statusCode + "");
		} else if (HttpStatus.SC_INTERNAL_SERVER_ERROR == statusCode) {
			ObjectMapper objectMapper = objectMapper();
			String respJson = EntityUtils.toString(res.getEntity());
			Object dataResTemp = objectMapper.readValue(respJson, valueTypeRef);
			if (dataResTemp instanceof RespuestaWSVO<?> dataRes) {
				throw new IOException(ERROR + url + STATUS_CODE + statusCode + " codigoError = "
						+ dataRes.getCodigoError() + " mensajeError = " + dataRes.getMensajeError());
			}
			throw new IOException(ERROR + url + STATUS_CODE + statusCode);

		}

	}

	public <T, E, K> T controladorAccionRest(String url, E obj, K pk, AccionType accionType) throws IOException {
		return (T) controladorAccionRest(url, obj, pk, accionType, true, getValueTypeRef());
	}

	public <T, E, K> T controladorAccionRest(String url, E obj, K pk, AccionType accionType,
			TypeReference<T> valueTypeRef) throws IOException {
		return controladorAccionRest(url, obj, pk, accionType, true, valueTypeRef);
	}

	public <T, E, K> T controladorAccionRest(String url, E obj, K pk, AccionType accionType, boolean isUrriInfo)
			throws IOException {
		return (T) controladorAccionRest(url, obj, pk, accionType, isUrriInfo, getValueTypeRef());
	}

	public <T, E, K> T controladorAccionRest(String url, E obj, K pk, AccionType accionType, boolean isUrriInfo,
			TypeReference<T> valueTypeRef) throws IOException {
		switch (accionType) {
			case CREAR:
				return post(url, obj, putParametrosHeader(), valueTypeRef);
			case MODIFICAR:
				return put(url, obj, pk, putParametrosHeader(), valueTypeRef);
			case ELIMINAR:
				return delete(url, pk, putParametrosHeader(), valueTypeRef, isUrriInfo);

			default:
				return null;
		}
	}

	public Map<String, Object> putParametro(String key, Object value) {
		Map<String, Object> map = new HashMap<>();
		map.put(key, value);
		return map;
	}

	public Map<String, String> putParametrosHeader() {
		return new HashMap<>();
	}

	public <T> Map<String, Object> putParametros(T obj) {
		Map<String, Object> parametros = new HashMap<>();
		if (obj != null) {
			parametros = toVOMap(obj);
			if (obj instanceof BaseSearch) { // Algunos no reconocen BasePaginator
				parametros = toVOMap(obj);
				parametros.put("startRow", ((BaseSearch) obj).getStartRow());
				parametros.put("offSet", ((BaseSearch) obj).getOffSet());
			}
		}
		return parametros;
	}

	public String getUrlBaseKey() {
		return urlBaseKey;
	}

	public void setUrlBaseKey(String urlBaseKey) {
		this.urlBaseKey = urlBaseKey;
	}

}