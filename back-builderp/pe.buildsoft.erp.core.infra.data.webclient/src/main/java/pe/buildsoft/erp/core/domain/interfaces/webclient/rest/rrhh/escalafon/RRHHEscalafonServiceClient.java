package pe.buildsoft.erp.core.domain.interfaces.webclient.rest.rrhh.escalafon;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.rrhh.escalafon.vo.PersonalVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;

@Local
public interface RRHHEscalafonServiceClient {

	RespuestaWSVO<String> obtenterPersonalIds(String authToken, Map<String, Object> filtroMap) throws Exception;
	
	String actualizarCache(String authToken) throws IOException;

	Map<String, BigDecimal> obtenerBasicoPersonalMap(String authToken, Long idCategoriaTrabajador) throws Exception;

	RespuestaWSVO<PersonalVO> paginarPersonal(String authToken, Map<String, Object> filtroMap) throws Exception;

	Map<String, PersonalVO> obtenerPersonalMap(String authToken, Map<String, Object> filtroMap) throws Exception;

	Map<String, Object> obtenerPersonalDataMap(String authToken, Map<String, Object> filtroMap) throws Exception;
}
