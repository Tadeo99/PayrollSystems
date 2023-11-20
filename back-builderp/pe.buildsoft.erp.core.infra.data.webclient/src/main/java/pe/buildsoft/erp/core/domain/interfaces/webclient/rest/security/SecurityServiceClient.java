package pe.buildsoft.erp.core.domain.interfaces.webclient.rest.security;

import java.io.IOException;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.security.vo.UsuarioVO;
import pe.buildsoft.erp.core.infra.transversal.type.AccionType;

@Local
public interface SecurityServiceClient {

	UsuarioVO integracionUsuario(UsuarioVO obj, AccionType accionType) throws IOException;

}
