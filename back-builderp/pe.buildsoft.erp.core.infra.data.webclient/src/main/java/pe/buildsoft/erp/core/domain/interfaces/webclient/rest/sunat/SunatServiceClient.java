package pe.buildsoft.erp.core.domain.interfaces.webclient.rest.sunat;

import java.io.IOException;
import java.util.List;

import jakarta.ejb.Local;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.sunat.vo.EmpresaSunatVo;
import pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.sunat.vo.PersonaSunatVo;

@Local
public interface SunatServiceClient {

	List<PersonaSunatVo> consultarReniecDni(String nrodni) throws IOException;

	List<EmpresaSunatVo> consultarRuc(String ruc) throws IOException;

}
