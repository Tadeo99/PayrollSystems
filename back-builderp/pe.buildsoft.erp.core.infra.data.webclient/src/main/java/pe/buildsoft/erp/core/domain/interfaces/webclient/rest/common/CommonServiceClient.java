package pe.buildsoft.erp.core.domain.interfaces.webclient.rest.common;

import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.common.vo.ParametroVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;

@Local
public interface CommonServiceClient {

	RespuestaWSVO<ParametroVO> listaParametro(String authToken, Map<String, Object> filtroMap) throws Exception;

}
