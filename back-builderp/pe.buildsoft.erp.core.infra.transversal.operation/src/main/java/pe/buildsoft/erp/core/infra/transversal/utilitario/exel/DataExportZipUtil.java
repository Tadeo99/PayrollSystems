package pe.buildsoft.erp.core.infra.transversal.utilitario.exel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.buildsoft.erp.core.infra.transversal.cache.ConfiguracionCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.FileVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ScriptSqlResulVO;
import pe.buildsoft.erp.core.infra.transversal.type.TipoReporteGenerarType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ArchivoUtilidades;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class DataExportZip.
 *
 * @author BuildSoft.
 * @version 1.0 , 08/04/2015
 * @since BuildErp 1.0
 */
public class DataExportZipUtil implements Serializable {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** La Constante RUTA_RECURSOS. */
	public static final String RUTA_RECURSOS_BYTE_BUFFER = ConstanteConfigUtil.RUTA_RECURSOS_BYTE_BUFFER;

	/** La log. */
	private static Logger log = LoggerFactory.getLogger(DataExportZipUtil.class);

	/**
	 * Generar archivos excel zipeado object view.
	 *
	 * @param listaData      el lista data
	 * @param archivoTitulo  el archivo titulo
	 * @param propiedadesMap el propiedades map
	 * @param nombreZip      el nombre zip
	 * @return the string
	 */
	public static String generarArchivosExcelZipeadoObjectView(List<ScriptSqlResulVO> listaData,
			Map<String, String> archivoTitulo, Map<String, String> propiedadesMap, String nombreZip) {
		int contador = 0;
		List<FileVO> listaObjetoVo = new ArrayList<FileVO>();
		Map<String, File> listaFileMap = new HashMap<String, File>();
		for (var d : listaData) {
			contador++;
			String nombre = archivoTitulo.get(contador + "_archivoName") + ".xls";
			DataExportExcelPersonalizadoUtil.generarExcelObject(d.getListaHeader(), d.getListaData(),
					archivoTitulo.get(contador + "_archivoName"), archivoTitulo.get(contador + "_titulo"),
					propiedadesMap);
			listaFileMap.put(nombre, new File(RUTA_RECURSOS_BYTE_BUFFER + nombre));
			FileVO objeto = new FileVO();
			objeto.setName(nombre);
			objeto.setMime(TipoReporteGenerarType.XLS.getContentType());
			listaObjetoVo.add(objeto);
		}

		return generarZip(listaObjetoVo, listaFileMap, nombreZip);
	}

	public static String generarZip(List<FileVO> listaObjetoVo, Map<String, File> listaFileMap, String nombreZip) {
		generarZipObject(listaObjetoVo, listaFileMap, nombreZip);
		return nombreZip;
	}

	/**
	 * Generar zip object.
	 *
	 * @param listaVo              el lista vo
	 * @param archivoName          el archivo name
	 * @param propiedadesMap       el propiedades map
	 * @param archivosExcelAgregar el archivos excel agregar
	 * @return the byte[]
	 */
	public static void generarZipObject(List<FileVO> listaVo, Map<String, File> listaFileMap, String archivoName) {
		try {
			File archivoZip = new File(RUTA_RECURSOS_BYTE_BUFFER);
			if (!archivoZip.isFile()) {
				archivoZip.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(RUTA_RECURSOS_BYTE_BUFFER + "" + archivoName + ".zip");
			ZipOutputStream zos = new ZipOutputStream(fos);
			// This sets the compression level to STORED, ie, uncompressed
			zos.setLevel(ZipOutputStream.STORED);
			for (var fileVO : listaVo) {
				addToZipFile(fileVO, listaFileMap.get(fileVO.getName()), zos);
			}
			zos.close();
			fos.close();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	public static boolean descomprimirZipObject(String ruta, String rutaDestino) {
		boolean resultado = true;
		ZipInputStream zis = null;
		try {
			File fileDestino = new File(rutaDestino);
			if (!fileDestino.exists()) {
				fileDestino.mkdirs();
			}
			zis = new ZipInputStream(new FileInputStream(ruta));
			ZipEntry entrada;
			while (null != (entrada = zis.getNextEntry())) {
				FileOutputStream fos = new FileOutputStream(
						rutaDestino + ConstanteConfigUtil.SEPARADOR_FILE + entrada.getName());
				int leido;
				byte[] buffer = new byte[2048];
				while (0 < (leido = zis.read(buffer))) {
					fos.write(buffer, 0, leido);
				}
				fos.close();
				zis.closeEntry();
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			resultado = false;
		} finally {
			try {
				if (zis != null) {
					zis.close();
				}
			} catch (Exception ee) {
				log.error(ee.getMessage(), ee);
				resultado = false;
			}
		}
		return resultado;
	}

	public static void addToZipFile(FileVO fileVO, File file, ZipOutputStream zos) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		// Inicio BuildSoft Reporte SBS MU2019020179
		String name = fileVO.getName();
		if (fileVO.getReporteGeneradoMap() != null && fileVO.getReporteGeneradoMap().containsKey(fileVO.getName())) {
			name = fileVO.getReporteGeneradoMap().get(fileVO.getName()).toString();
		}
		// Fin BuildSoft Reporte SBS MU2019020179
		ZipEntry zipEntry = new ZipEntry(name);
		zos.putNextEntry(zipEntry);
		int cantidad = 2048 * 2;

		ConfiguracionCacheUtil.getInstance();

		if (ConfiguracionCacheUtil.containsKeyPwrConfUtil("data.export.zip.bytes")) {
			cantidad = ConfiguracionCacheUtil.getPwrConfUtilInt("data.export.zip.bytes");
		}
		byte[] bytes = new byte[cantidad];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}
		zos.closeEntry();
		fis.close();
	}

	public static void generarZipTxtExel(String archivoName, String usuario, boolean isOnline,
			String... archivoNameComprimir) {
		generarZipTxtExelPer(archivoName, usuario, true, isOnline, archivoNameComprimir);
	}

	public static void generarZipTxtExelPer(String archivoName, String usuario, boolean isTxtIncluido, boolean isOnline,
			String... archivoNameComprimir) {
		generarZipTxtExelPer(getConfig(archivoName, usuario, isTxtIncluido, true, isOnline, null),
				archivoNameComprimir);
	}

	public static void generarZipTxt(String archivoName, String usuario, boolean isOnline,
			String... archivoNameComprimir) {
		generarZipTxtExtension(archivoName, usuario, isOnline, null, archivoNameComprimir);
	}

	public static void generarZipTxtExtension(String archivoName, String usuario, boolean isOnline,
			Map<String, Object> paramsInType, String... archivoNameComprimir) {
		generarZipTxtExelPer(getConfig(archivoName, usuario, true, false, isOnline, paramsInType),
				archivoNameComprimir);
	}

	private static Map<String, Object> getConfig(String archivoName, String usuario, boolean isTxt, boolean isXls,
			boolean isOnline, Map<String, Object> paramsInType) {
		Map<String, Object> config = new HashMap<String, Object>();
		config.put("archivoName", archivoName);
		config.put("usuario", usuario);
		config.put("isIncluyeTxt", isTxt);
		config.put("isIncluyeXls", isXls);
		config.put("isOnline", isOnline);
		config.put("paramsInType", paramsInType);
		return config;
	}

	private static void generarZipTxtExelPer(Map<String, Object> config, String... archivoNameComprimir) {
		// Recuperando parametros
		String archivoName = (String) config.get("archivoName");
		boolean isIncluyeTxt = (Boolean) config.get("isIncluyeTxt");
		boolean isIncluyeXls = (Boolean) config.get("isIncluyeXls");

		// recorrer el nombreArchivoMap para generar el zip
		List<FileVO> listaArchivoDatVO = new ArrayList<FileVO>();
		Map<String, File> listaFileMap = new HashMap<String, File>();

		for (var archiviComprimir : archivoNameComprimir) {
			File fileXLSX = null;
			if (isIncluyeXls)
				fileXLSX = new File(
						DataExportExcelPersonalizadoUtil.RUTA_RECURSOS_BYTE_BUFFER + "" + archiviComprimir + ".xlsx");

			File fileTXT = null;
			if (isIncluyeTxt)
				fileTXT = new File(DataExportExcelPersonalizadoUtil.RUTA_RECURSOS_BYTE_BUFFER + "" + archiviComprimir);

			try {
				if (isIncluyeXls && fileXLSX.isFile()) {
					FileVO fileVO = obtenerFileVo(fileXLSX);
					listaArchivoDatVO.add(fileVO);
					listaFileMap.put(fileXLSX.getName(), fileXLSX);
				}

				if (isIncluyeTxt && fileTXT.isFile()) {
					FileVO fileVO = obtenerFileVo(fileTXT);
					listaArchivoDatVO.add(fileVO);
					listaFileMap.put(fileTXT.getName(), fileTXT);
				}
			} catch (Exception e) {
				log.error("generarZipTxtExelPer", e);
			}
		}

		DataExportZipUtil.generarZip(listaArchivoDatVO, listaFileMap, archivoName);
		eliminarYCopiar(config, archivoName, isIncluyeXls, isIncluyeTxt, archivoNameComprimir);
	}

	private static void eliminarYCopiar(Map<String, Object> config, String archivoName, boolean isIncluyeXls,
			boolean isIncluyeTxt, String... archivoNameComprimir) {
		String usuario = config.get("usuario").toString();
		boolean isOnline = (Boolean) config.get("isOnline");

		// generar zip con el nombre archivoName y elimizar los archivos con los nombre
		// nombreArchivoMap
		for (var archiviComprimir : archivoNameComprimir) {
			File fileXLSX = null;
			if (isIncluyeXls)
				fileXLSX = new File(
						DataExportExcelPersonalizadoUtil.RUTA_RECURSOS_BYTE_BUFFER + "" + archiviComprimir + ".xlsx");

			File fileTXT = null;
			if (isIncluyeTxt)
				fileTXT = new File(DataExportExcelPersonalizadoUtil.RUTA_RECURSOS_BYTE_BUFFER + "" + archiviComprimir);

			try {
				if (isIncluyeXls && fileXLSX.isFile() && !fileXLSX.delete()) {
					// No se pudo eliminar
				}
				if (isIncluyeTxt && fileTXT.isFile() && !fileTXT.delete()) {
					// No se pudo eliminar
				}
			} catch (Exception e) {
				log.error("eliminarYCopiar", e);
			}
		}
		// fin

		copiarSiEsCola(usuario, archivoName, isOnline);
	}

	private static void copiarSiEsCola(String usuario, String archivoName, boolean isOnline) {
		if (!isOnline) {// Es en cola
			try {
				ArchivoUtilidades.copyArchivo(archivoName + ".zip", archivoName + ".zip", usuario, false);
			} catch (Exception e) {
				log.error("copiarSiEsCola", e);
			}
		}
	}

	private static FileVO obtenerFileVo(File file) {
		FileVO fileVO = new FileVO();
		fileVO.setReporteGeneradoMap(new HashMap<String, Object>());
		int lastIndexOf = file.getName().lastIndexOf("_");
		int lastIndexOfTipo = file.getName().lastIndexOf(".");
		String nombre = lastIndexOf > -1 ? file.getName().substring(0, lastIndexOf) : file.getName();
		if (file.getName() != null && file.getName().toUpperCase().contains(".XLS") && lastIndexOf > -1) {
			nombre = file.getName().substring(0, lastIndexOf) + file.getName().substring(lastIndexOfTipo);
		}
		fileVO.getReporteGeneradoMap().put(file.getName(), nombre);
		fileVO.setName(file.getName());
		fileVO.setLength(file.length() / 1024);
		return fileVO;
	}

}
