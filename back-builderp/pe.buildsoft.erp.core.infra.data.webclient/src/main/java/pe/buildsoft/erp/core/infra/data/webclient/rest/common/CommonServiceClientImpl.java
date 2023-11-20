package pe.buildsoft.erp.core.infra.data.webclient.rest.common;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import pe.buildsoft.erp.core.domain.interfaces.webclient.rest.common.CommonServiceClient;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.common.vo.ParametroVO;
import pe.buildsoft.erp.core.infra.data.webclient.rest.aas.AasServiceClientUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CommonServiceClientImpl extends AasServiceClientUtil implements CommonServiceClient {

	private static final String URL_REST_PARAMETRO = "parametro";

	public CommonServiceClientImpl() {
		setUrlBaseKey(URL_REST_COMMON_BACK);
	}

	@Override
	public RespuestaWSVO<ParametroVO> listaParametro(String authToken, Map<String, Object> filtroMap) throws Exception {
		return get(URL_REST_PARAMETRO, filtroMap, getHeaders(authToken), getValueTypeRefPer());
	}

	public TypeReference<RespuestaWSVO<ParametroVO>> getValueTypeRefPer() {
		return new TypeReference<RespuestaWSVO<ParametroVO>>() {
		};
	}

}
