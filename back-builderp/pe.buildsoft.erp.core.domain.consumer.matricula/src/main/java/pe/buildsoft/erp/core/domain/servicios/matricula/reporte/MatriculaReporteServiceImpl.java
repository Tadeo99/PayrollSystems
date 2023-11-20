package pe.buildsoft.erp.core.domain.servicios.matricula.reporte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.domain.entidades.matricula.DetMatricula;
import pe.buildsoft.erp.core.domain.entidades.matricula.Matricula;
import pe.buildsoft.erp.core.domain.entidades.matricula.vo.FichaMatriculaVO;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.DetMatriculaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.matricula.MatriculaDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.GenerarReporteServiceLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.matricula.reporte.MatriculaReporteServiceLocal;
import pe.buildsoft.erp.core.domain.type.NombreReporteType;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ParametroReporteVO;
import pe.buildsoft.erp.core.infra.transversal.type.TipoReporteGenerarType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ArchivoUtilidades;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class MatriculaReporteServiceImpl implements MatriculaReporteServiceLocal {

	@Inject
	private ICache cache;

	@Inject
	private GenerarReporteServiceLocal generarReporteServiceImpl;

	/** El servicio matricula dao impl. */
	@Inject
	private MatriculaDaoLocal matriculaDaoImpl;

	/** El servicio det matricula dao impl. */
	@Inject
	private DetMatriculaDaoLocal detMatriculaDaoImpl;

	@Override
	public String generarReporteFichaMatricula(FichaMatriculaVO filtro) {
		String fileName = UUIDUtil.generarElementUUID();
		String userName = filtro.getUsuario();
		var parametros = new HashMap<String, Object>();
		parametros.put("ruta", "");
		parametros.put("ruta_foto", ArchivoUtilidades.RUTA_RECURSOS);
		parametros.put("ruta_logo", "");
		List<Matricula> listaResultado = new ArrayList<>();
		Matricula resultado = obtenerMatricula(filtro);
		if (!StringUtil.isNotNullOrBlank(resultado.getAlumno().getFoto())) {
			resultado.getAlumno().setFoto("carita.jpg");
		}
		List<DetMatricula> listDetMatricula = obtenerListMatricula(resultado.getIdMatricula());
		resultado.setMatriculaDetMatriculaList(listDetMatricula);
		listaResultado.add(resultado);
		NombreReporteType reporte = NombreReporteType.JR_REP_FICHA_MATRICULA_INDIVIDUAL;
		ParametroReporteVO objParam = new ParametroReporteVO(parametros, listaResultado, reporte.getCarpeta(),
				reporte.getKey(), true, TipoReporteGenerarType.PDF.getKey(), fileName);
		objParam.setEsCopiaCorreo(filtro.isCopiaCorreo());
		objParam.setUserName(userName);
		objParam.setOnline(filtro.isOnline());
		return generarReporteServiceImpl.obtenerParametroReporteBigMemory(objParam);
	}

	private Matricula obtenerMatricula(FichaMatriculaVO filtro) {
		return matriculaDaoImpl.get(filtro);
	}

	private List<DetMatricula> obtenerListMatricula(String idMatricula) {
		return this.detMatriculaDaoImpl.listar(idMatricula);
	}

}
