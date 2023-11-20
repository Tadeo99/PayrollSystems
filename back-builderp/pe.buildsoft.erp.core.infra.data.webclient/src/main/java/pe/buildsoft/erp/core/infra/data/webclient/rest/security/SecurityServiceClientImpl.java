package pe.buildsoft.erp.core.infra.data.webclient.rest.security;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import pe.buildsoft.erp.core.domain.interfaces.webclient.rest.security.SecurityServiceClient;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.security.vo.UsuarioVO;
import pe.buildsoft.erp.core.infra.data.webclient.rest.aas.AasServiceClientUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SecurityServiceClientImpl extends AasServiceClientUtil implements SecurityServiceClient {

	private static final String URL_REST_USUARIO = "usuario";

	public SecurityServiceClientImpl() {
		setUrlBaseKey(URL_REST_SECURITY_BACK);
	}

	@Override
	public UsuarioVO integracionUsuario(UsuarioVO obj, AccionType accionType) throws IOException {
		return post(URL_REST_USUARIO, obj, getHeaders(obj.getAuthToken()), getValueTypeRefUser()).getObjetoResultado();
	}

	public TypeReference<RespuestaWSVO<UsuarioVO>> getValueTypeRefUser() {
		return new TypeReference<RespuestaWSVO<UsuarioVO>>() {
		};
	}

}
