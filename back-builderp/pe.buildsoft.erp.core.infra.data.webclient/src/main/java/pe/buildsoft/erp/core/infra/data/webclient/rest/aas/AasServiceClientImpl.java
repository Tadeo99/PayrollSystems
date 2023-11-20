package pe.buildsoft.erp.core.infra.data.webclient.rest.aas;

import java.io.IOException;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import com.fasterxml.jackson.core.type.TypeReference;

import pe.buildsoft.erp.core.domain.interfaces.webclient.rest.aas.AasServiceClient;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AasServiceClientImpl extends AasServiceClientUtil implements AasServiceClient {

	private static final String URL_REST_LOGIN = "aas/login";

	public AasServiceClientImpl() {
		setUrlBaseKey(URL_REST_AAS_BACK);
	}

	public TypeReference<RespuestaWSVO<Boolean>> getValueTypeRefValid() {
		return new TypeReference<RespuestaWSVO<Boolean>>() {
		};
	}

	@Override
	public String actualizarCache(String authToken) {
		return null;
	}

	@Override
	public String validate(String jwt) throws IOException {
		return post(URL_REST_LOGIN + "/validate", jwt, getHeaders(""), getValueTypeRef()).getObjetoResultado();
	}

}
