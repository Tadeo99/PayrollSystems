package pe.buildsoft.erp.core.domain.servicios.pago.proceso;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import pe.buildsoft.erp.core.domain.entidades.pago.ControlPago;
import pe.buildsoft.erp.core.domain.entidades.pago.vo.SunatDatosVO;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.ControlPagoDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.pago.proceso.PagoProcesoServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.NumerosUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.txt.TXTUtil;

/**
 * La Class PagoServiceImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:13 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class PagoProcesoServiceImpl implements PagoProcesoServiceLocal {

	/** El servicio control pago dao impl. */
	@Inject
	private ControlPagoDaoLocal controlPagoDaoImpl;

	@Inject
	private ICache cacheUtil;

	private String getFileExtension(File file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return ""; // empty extension
		}
		return name.substring(lastIndexOf);
	}

	@Override
	public List<SunatDatosVO> generarExtracionTXTControlPago() {
		String resultado = "";
		String userName = "sfs";

		String archiName = ConstanteConfigUtil.RUTA_DATOS_EXTRACION + ConstanteConfigUtil.generarRuta(userName, "DATA");

		File carpeta = new File(archiName);
		File[] listado = carpeta.listFiles();
		String extencion = "";
		List<String> listadoDoc = new ArrayList<>();
		for (int i = 0; i < listado.length; i++) {
			File archivo = listado[i];
			extencion = this.getFileExtension(archivo);
			if (extencion.equals(".cab")) {
				listadoDoc.add(archivo.getName().substring(0, 28));
			} else if (extencion.equals(".rdi")) {
				listadoDoc.add(archivo.getName().substring(0, 27));
			}
		}

		List<String> exten = new ArrayList<>();
		exten.add(".cab");
		exten.add(".det");
		exten.add(".tri");
		exten.add(".ley");

		List<String> extenB = new ArrayList<>();
		extenB.add(".rdi");
		extenB.add(".trd");

		for (var obj : listadoDoc) {
			String ori = ConstanteConfigUtil.RUTA_DATOS_EXTRACION + ConstanteConfigUtil.generarRuta(userName, "PARSE")
					+ obj + ".xml";
			File archivo = new File(ori);
			if (archivo.exists()) {
				if (obj.length() == 28) {
					for (var ex : exten) {
						String origen = ConstanteConfigUtil.RUTA_DATOS_EXTRACION
								+ ConstanteConfigUtil.generarRuta(userName, "DATA") + obj + ex;
						String destino = ConstanteConfigUtil.RUTA_DATOS_DATA_ENVIADO + obj + ex;
						Path origenPath = FileSystems.getDefault().getPath(origen);
						Path destinoPath = FileSystems.getDefault().getPath(destino);
						try {
							Files.move(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
						} catch (IOException e) {
							System.err.println(e);
						}
					}
				} else {
					for (var exB : extenB) {
						String origen = ConstanteConfigUtil.RUTA_DATOS_EXTRACION
								+ ConstanteConfigUtil.generarRuta(userName, "DATA") + obj + exB;
						String destino = ConstanteConfigUtil.RUTA_DATOS_DATA_ENVIADO + obj + exB;
						Path origenPath = FileSystems.getDefault().getPath(origen);
						Path destinoPath = FileSystems.getDefault().getPath(destino);
						try {
							Files.move(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
						} catch (IOException e) {
							System.err.println(e);
						}
					}
				}
			}
		}

		// if(!isAnulado) {
		List<Object[]> listaControlPagoGrupByFechasAnuladas = controlPagoDaoImpl.listaControlPagoGrupByFechas();
		if (!listaControlPagoGrupByFechasAnuladas.isEmpty()) {
			for (var objects : listaControlPagoGrupByFechasAnuladas) {
				ControlPago control = new ControlPago();
				// control.setTipoDocSunat(new Item());
				control.setFechaPago((OffsetDateTime) objects[0]);
				control.setIdTipoDocSunat((Long) objects[3]);
				control.setAnulado("S");

				String cant = StringUtil.padLeft(Long.toString((Long) objects[1]), 3, "0");
				resultado = "20515531107" + "-" + "RC" + "-" + (String) objects[2] + "-" + cant;
				writeXML(this.generarArchivosPlanosXML(control), userName, resultado, "rdi", "|");
				if (!StringUtil.isNullOrEmpty(control.getFechaPago())) {
					control.setId("TRD");
					writeXML(this.generarArchivosPlanosXML(control), userName, resultado, "trd", "|");
				}
			}
		}

		List<ControlPago> listaF = listaControlPagoExtracionF();
		for (var obj : listaF) {
			Long comparacionSerieB = 3750008L;
			Long comparacionSerieF = 3750007L;
			Long comparacionSerieNC = 692L;
			Long comparacionSerieND = 693L;

			resultado = null;

			if (obj.getIdTipoDocSunat().equals(comparacionSerieB)) {
				resultado = "20515531107" + "-" + "03" + "-" + obj.getSerie() + "-" + obj.getNroDoc();
			} else if (obj.getIdTipoDocSunat().equals(comparacionSerieF)) {
				resultado = "20515531107" + "-" + "01" + "-" + obj.getSerie() + "-" + obj.getNroDoc();
			} else if (obj.getIdTipoDocSunat().equals(comparacionSerieNC)) {
				resultado = "20515531107" + "-" + "07" + "-" + obj.getSerie() + "-" + obj.getNroDoc();
			} else if (obj.getIdTipoDocSunat().equals(comparacionSerieND)) {
				resultado = "20515531107" + "-" + "08" + "-" + obj.getSerie() + "-" + obj.getNroDoc();
			}

			obj.setTipo("F");
			List<Map<String, Object>> listaItemF = this.generarArchivosPlanosXML(obj);

			if (obj.getIdTipoDocSunat().equals(comparacionSerieB)) {
				writeXML(listaItemF, userName, resultado, "cab", "|");
			} else if (obj.getIdTipoDocSunat().equals(comparacionSerieF)) {
				writeXML(listaItemF, userName, resultado, "cab", "|");
			} else if (obj.getIdTipoDocSunat().equals(comparacionSerieNC)) {
				writeXML(listaItemF, userName, resultado, "not", "|");
			} else if (obj.getIdTipoDocSunat().equals(comparacionSerieND)) {
				writeXML(listaItemF, userName, resultado, "not", "|");
			}

			if (!StringUtil.isNullOrEmpty(obj.getIdControlPago())) {
				obj.setTipo("");
				obj.setTri("");
				obj.setLey("");
				obj.setDet("DET");
				writeXML(this.generarArchivosPlanosXML(obj), userName, resultado, "det", "|");

			}
			if (!StringUtil.isNullOrEmpty(obj.getIdControlPago())) {
				obj.setTipo("");
				obj.setDet("");
				obj.setLey("");
				obj.setTri("TRI");
				writeXML(this.generarArchivosPlanosXML(obj), userName, resultado, "tri", "|");

			}
			if (!StringUtil.isNullOrEmpty(obj.getIdControlPago())) {
				obj.setTipo("");
				obj.setTri("");
				obj.setDet("");
				obj.setLey("LEY");
				obj.setMontoletra(NumerosUtil.Convertir(obj.getMontoTotal().toString(), true));
				writeXML(this.generarArchivosPlanosXML(obj), userName, resultado, "ley", "|");
			}

		}

		this.actualizarBandeja();
		controlPagoDaoImpl.updateVentaExtracion();
		return this.actualizarBandeja();
	}

	public void writeXML(List<Map<String, Object>> listaData, String userName, String nombreArchivo, String extension,
			String separador) {
		TXTUtil.escribirArchivoXML(listaData, userName, nombreArchivo, extension, separador);
	}

	private List<Map<String, Object>> generarArchivosPlanosXML(ControlPago control) {
		return this.controlPagoDaoImpl.generarArchivosPlanosXML(control);
	}

	@Override
	public List<ControlPago> listaControlPagoExtracionF() {
		return controlPagoDaoImpl.listaVentaExtracionF();
	}

	@Override
	public List<SunatDatosVO> generarComprobante(SunatDatosVO sfs) {
		String targetURL = "http://localhost:9000/api/GenerarComprobante.htm";// here is my local server url
		return Utilidades.generaraccionComprobante(sfs, targetURL);
	}

	@Override
	public List<SunatDatosVO> enviarComprobante(SunatDatosVO sfs) {
		String targetURL = "http://localhost:9000/api/enviarXML.htm";// here is my local server url
		return Utilidades.generaraccionComprobante(sfs, targetURL);
	}

	@Override
	public List<SunatDatosVO> eliminarBandeja() {
		return Utilidades.eliminarBandeja();
	}

	@Override
	public List<SunatDatosVO> actualizarBandeja() {
		return null;// Utilidades.actualizarPantalla(sfs);
	}
}