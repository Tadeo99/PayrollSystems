package pe.buildsoft.erp.core.api.controller.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.buildsoft.erp.core.application.interfaces.common.CommonAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.FileVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaWSVO;
import pe.buildsoft.erp.core.infra.transversal.util.GenericServiceRestImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;

/**
 * La Class FileUploadRestController.
 * <ul>
 * <li>Copyright 2018 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, 05/04/2018 03:13 pm
 * @since SIAA-CORE 2.1
 */
@Path("/fileUpload")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FileUploadRestController extends GenericServiceRestImpl implements IFileUploadRestController {

	private Logger log = LoggerFactory.getLogger(FileUploadRestController.class);

	@Inject
	private CommonAppServiceLocal servicioApp;

	@POST
	@Path("/alumno")
	public Response subirImagen(FileVO file) {
		RespuestaWSVO<FileVO> resultado = new RespuestaWSVO<>();
		try {
			file.setRuta(ConstanteConfigUtil.RUTA_RECURSOS_FOTO_ALUMN + ConstanteConfigUtil.SEPARADOR_FILE
					+ file.getName() + "." + file.getExtension());
			servicioApp.subirImagen(file);
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	@POST
	@Path("/personal")
	public Response subirImagenPersonal(FileVO file) {
		RespuestaWSVO<FileVO> resultado = new RespuestaWSVO<>();
		try {
			file.setRuta(ConstanteConfigUtil.RUTA_RECURSOS_FOTO_ALUMN + ConstanteConfigUtil.SEPARADOR_FILE
					+ file.getName() + "." + file.getExtension());
			servicioApp.subirImagen(file);
		} catch (Exception e) {
			log.error("subirImagenPersonal", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	@GET
	@Path("/alumno/{fotoImg}")
	public Response obtenerImagenEncodeBase64(@PathParam("fotoImg") String fotoImg) {
		RespuestaWSVO<String> resultado = new RespuestaWSVO<>();
		try {
			FileVO fileVO = new FileVO();
			fileVO.setRuta(ConstanteConfigUtil.RUTA_RECURSOS_FOTO_ALUMN + ConstanteConfigUtil.SEPARADOR_FILE + "086"
					+ fotoImg);
			resultado.setObjetoResultado(servicioApp.obtenerImagenEncodeBase64(fileVO));
		} catch (Exception e) {
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

	@GET
	@Path("/personal/{fotoImg}")
	public Response obtenerImagenEncodeBase64Personal(@PathParam("fotoImg") String fotoImg) {
		RespuestaWSVO<String> resultado = new RespuestaWSVO<>();
		try {
			FileVO fileVO = new FileVO();
			fileVO.setRuta(ConstanteConfigUtil.RUTA_RECURSOS_FOTO_ALUMN + ConstanteConfigUtil.SEPARADOR_FILE + fotoImg);
			resultado.setObjetoResultado(servicioApp.obtenerImagenEncodeBase64(fileVO));
		} catch (Exception e) {
			log.error("obtenerImagenEncodeBase64Personal", e);
			parsearResultadoError(e, resultado);
		}
		return respuesta(resultado, null);
	}

}