package pe.buildsoft.erp.core.api.servlets;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.FileVO;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ArchivoUtilidades;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * <ul>
 * <li>Copyright 2017 ndavilal. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class DescargarReporte.
 *
 * @author ndavilal
 * @version 1.0 , 08/04/2015
 * @since SIAA-CORE 2.1
 */

@WebServlet(name = "DescargarReporte", description = "DescargarReporte", urlPatterns = "/DescargarReporte")
public class DescargarReporte extends HttpServlet {

	private static final String NO_EXISTE_EL_ARCHIVO_EN = "No existe el archivo en ";

	private static final String ARCHIVO_TEMP = "archivo";

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** La log. */
	private final Logger log = LoggerFactory.getLogger(DescargarReporte.class);

	/** La Constante DEFAULT_BUFFER_SIZE. @see HttpServlet#HttpServlet() */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	private static final int DEFAULT_BUFFER_SIZE = 1024;

	private static final int TAMANO_BUFFER = DEFAULT_BUFFER_SIZE;

	/** La Constante RUTA_RECURSOS. */
	public static final String RUTA_RECURSOS_BYTE_BUFFER = ConstanteConfigUtil.RUTA_RECURSOS_BYTE_BUFFER;

	@Inject
	private ICache cacheUtil;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * jakarta.servlet.http.HttpServlet#doGet(jakarta.servlet.http.HttpServletRequest,
	 * jakarta.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Process request.
	 *
	 * @param request  el request
	 * @param response el response
	 * @throws IOException Seï¿½ales de que una excepciï¿½n de E / S se ha
	 *                     producido.
	 */
	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean eliminarFileSession = false;
		String nombreArchivo = request.getParameter("fileName");
		String userName = request.getParameter("userName");
		boolean isError = false;
		String mensajeError = "";
		try {
			String cola = request.getParameter("cola");
			FileVO documento = null;

			if (cola == null) {
				documento = (FileVO) cacheUtil.get(nombreArchivo);
				if (!StringUtil.isNotNullOrBlank(userName)) {
					userName = documento.getUserName();
				}
				eliminarFileSession = true;

			} else {
				documento = new FileVO();
			}
			response.setBufferSize(DEFAULT_BUFFER_SIZE);
			if (StringUtil.isNullOrEmpty(documento.getDataBig())) {
				if (cola == null) {
					response.setHeader("Content-Length", String.valueOf(documento.getLength()));
					response.setHeader("Content-Type", documento.getMime());
					response.setHeader("Content-Disposition", "attachment; filename=\"" + documento.getName() + "\"");
				}
			}
			if (StringUtil.isNullOrEmpty(documento.getDataBig())) {
				if (cola == null) {
					String rutaArchivo = ConstanteConfigUtil.RUTA_SESSION_TEMP
							+ ConstanteConfigUtil.generarRuta(userName, ARCHIVO_TEMP) + nombreArchivo + "."
							+ documento.getExtension();
					File file = new File(rutaArchivo);
					documento.setName(documento.getName());
					if (file.exists()) {
						descargarReporteFile(request, response, file, documento);
						file.delete();
					} else {
						isError = true;
						mensajeError = NO_EXISTE_EL_ARCHIVO_EN + rutaArchivo;
					}
					file = null;

				} else {
					String rutaArchivo = ConstanteConfigUtil.generarRuta(ArchivoUtilidades.RUTA_RECURSOS,
							ArchivoUtilidades.RUTA_REPORTE_GENERADO, userName) + nombreArchivo;
					File file = new File(rutaArchivo);
					documento.setName(file.getName());
					if (file.exists()) {
						descargarReporteFile(request, response, file, documento);
					} else {
						isError = true;
						mensajeError = NO_EXISTE_EL_ARCHIVO_EN + rutaArchivo;
					}
					file = null;
				}
			} else {
				if (cola == null && !documento.getDataBig().contains(".")) {
					String rutaArchivo = ConstanteConfigUtil.RUTA_SESSION_TEMP
							+ ConstanteConfigUtil.generarRuta(userName, ARCHIVO_TEMP) + nombreArchivo + "."
							+ documento.getExtension();
					File file = new File(rutaArchivo);
					documento.setName(documento.getName());
					if (file.exists()) {
						descargarReporteFile(request, response, file, documento);
						file.delete();
					} else {
						isError = true;
						mensajeError = NO_EXISTE_EL_ARCHIVO_EN + rutaArchivo;
					}
					file = null;
				} else {
					userName = documento.getUserName();
					if (!StringUtil.isNullOrEmpty(userName)) {
						userName = userName + ConstanteConfigUtil.SEPARADOR_FILE;
					} else {
						userName = "";
					}
					File file = new File(RUTA_RECURSOS_BYTE_BUFFER + userName + "" + documento.getName());
					if (file.exists()) {
						descargarReporteFile(request, response, file, documento);
						if (!documento.isEsCopiaCorreo()) {
							file.delete();
						}

					} else {
						isError = true;
						mensajeError = NO_EXISTE_EL_ARCHIVO_EN + file.getAbsolutePath();
					}
				}
			}

			if (!isError) {
				response.flushBuffer();
			}
		} catch (Exception e) {
			mensajeError = mensajeError + " Exception==> " + e.getMessage();
			isError = true;
		} finally {
			if (!isError) {
				response.getOutputStream().flush();
				response.getOutputStream().close();
				if (eliminarFileSession) {
					request.getSession().removeAttribute(nombreArchivo);
				}
			}
		}
		if (isError) {
			log.error("DescargarReporte.error==> " + mensajeError);
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>BuildErp</title>");
			out.println("</head>");
			out.println("<body ><center>");
			out.print(mensajeError);
			out.println("</center><body >");
			out.println("</body>");
			out.println("</html>");
		}
	}

	private void descargarReporteFile(HttpServletRequest request, HttpServletResponse response, File file,
			FileVO documento) throws IOException {
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Type", documento.getMime());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + documento.getName() + "\"");
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		byte[] bbuf = new byte[TAMANO_BUFFER];
		int leido = 0;
		while ((in != null) && ((leido = in.read(bbuf)) != -1)) {
			response.getOutputStream().write(bbuf, 0, leido);
		}
		in.close();
	}

}