package pe.buildsoft.erp.core.infra.data.webclient.rest.sunat;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import com.fasterxml.jackson.core.type.TypeReference;

import pe.buildsoft.erp.core.domain.interfaces.webclient.rest.sunat.SunatServiceClient;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.sunat.vo.EmpresaSunatVo;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.sunat.vo.PersonaSunatVo;
import pe.buildsoft.erp.core.infra.data.webclient.rest.aas.AasServiceClientUtil;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SunatServiceClientImpl extends AasServiceClientUtil implements SunatServiceClient {

	private static final String URL_DNI = "dni/";
	private static final String URL_RUC = "ruc/";

	private static final String TOKEN_SUNAT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImtyaXNzLnJlYmlsQGdtYWlsLmNvbSJ9.uq92gVngaARZDf0rG6Ju82WBZcvd0fec32R_h2Va0iE";

	public SunatServiceClientImpl() {
		setUrlBaseKey(URL_REST_SUNAT_BACK);
	}

	@Override
	public List<PersonaSunatVo> consultarReniecDni(String nrodni) throws IOException {
		return get(URL_DNI + nrodni, putParametro("token", TOKEN_SUNAT), new HashMap<String, String>(),
				getValueListTypeRef());
	}

	@Override
	public List<EmpresaSunatVo> consultarRuc(String ruc) throws IOException {
		return get(URL_RUC + ruc, putParametro("token", TOKEN_SUNAT), new HashMap<String, String>(),
				getValueRucListTypeRef());
	}

	public TypeReference<List<PersonaSunatVo>> getValueListTypeRef() {
		return new TypeReference<List<PersonaSunatVo>>() {
		};
	}

	public TypeReference<List<EmpresaSunatVo>> getValueRucListTypeRef() {
		return new TypeReference<List<EmpresaSunatVo>>() {
		};
	}

}
