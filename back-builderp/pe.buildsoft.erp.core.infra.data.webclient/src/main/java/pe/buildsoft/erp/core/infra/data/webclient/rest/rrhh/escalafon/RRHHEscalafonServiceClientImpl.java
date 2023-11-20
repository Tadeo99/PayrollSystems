package pe.buildsoft.erp.core.infra.data.webclient.rest.rrhh.escalafon;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import pe.buildsoft.erp.core.domain.interfaces.webclient.rest.rrhh.escalafon.RRHHEscalafonServiceClient;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.rrhh.escalafon.vo.PersonalVO;
import pe.buildsoft.erp.core.infra.data.webclient.rest.aas.AasServiceClientUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class RRHHEscalafonServiceClientImpl extends AasServiceClientUtil implements RRHHEscalafonServiceClient {

	private static final String URL_REST_PERSONAL = "personal";

	public RRHHEscalafonServiceClientImpl() {
		setUrlBaseKey(URL_REST_ESCALAFON_BACK);
	}

	public TypeReference<RespuestaWSVO<Boolean>> getValueTypeRefValid() {
		return new TypeReference<RespuestaWSVO<Boolean>>() {
		};
	}

	@Override
	public RespuestaWSVO<String> obtenterPersonalIds(String authToken, Map<String, Object> filtroMap) throws Exception {
		return get(URL_REST_PERSONAL + "/ids", filtroMap, getHeaders(authToken), getValueTypeRef());
	}

	@Override
	public String actualizarCache(String authToken) {
		return null;
	}

	@Override
	public Map<String, BigDecimal> obtenerBasicoPersonalMap(String authToken, Long idCategoriaTrabajador)
			throws Exception {
		var filtroMap = new HashMap<String, Object>();
		filtroMap.put("idCategoriaTrabajador", idCategoriaTrabajador);
		return (get(URL_REST_PERSONAL + "/historialBasicos", filtroMap, getHeaders(authToken),
				getValueTypeRefHistorial())).getObjetoResultado();
	}

	@Override
	public RespuestaWSVO<PersonalVO> paginarPersonal(String authToken, Map<String, Object> filtroMap) throws Exception {
		return get(URL_REST_PERSONAL, filtroMap, getHeaders(authToken), getValueTypeRefPersonal());
	}

	@Override
	public Map<String, PersonalVO> obtenerPersonalMap(String authToken, Map<String, Object> filtroMap)
			throws Exception {
		return (Map<String, PersonalVO>) obtenerPersonalDataMap(authToken, filtroMap).get("personalMap");
	}

	@Override
	public Map<String, Object> obtenerPersonalDataMap(String authToken, Map<String, Object> filtroMap)
			throws Exception {
		var resultado = new HashMap<String, Object>();
		var personalMap = new HashMap<String, PersonalVO>();
		var listPersonal = this.paginarPersonal(authToken, filtroMap).getListaResultado();
		if (listPersonal != null) {
			for (var obj : listPersonal) {
				personalMap.put(obj.getIdPersonal(), obj);
			}
		}
		resultado.put("personalMap", personalMap);
		resultado.put("listPersonal", listPersonal);
		return resultado;
	}

	public TypeReference<RespuestaWSVO<Map<String, BigDecimal>>> getValueTypeRefHistorial() {
		return new TypeReference<RespuestaWSVO<Map<String, BigDecimal>>>() {
		};
	}

	public TypeReference<RespuestaWSVO<PersonalVO>> getValueTypeRefPersonal() {
		return new TypeReference<RespuestaWSVO<PersonalVO>>() {
		};
	}

}
