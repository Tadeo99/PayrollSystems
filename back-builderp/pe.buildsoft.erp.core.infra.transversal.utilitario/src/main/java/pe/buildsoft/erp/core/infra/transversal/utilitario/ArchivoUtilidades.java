package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.buildsoft.erp.core.infra.transversal.entidades.vo.FileVO;
import pe.buildsoft.erp.core.infra.transversal.type.CarpetaType;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class ArchivoUtilidades.
 *
 * @author BuildSoft.
 * @version 1.0 , 07/04/2015
 * @since BuildErp 1.0
 */
public class ArchivoUtilidades {

	private static final String NO_SE_PUEDO_ELIMINAR_EL_ARCHIVO = "No se puedo eliminar el archivo ";

	public static final String SEPARADOR_FILE = ConstanteConfigUtil.SEPARADOR_FILE;

	/** La Constante RUTA_RECURSOS. */
	public static final String RUTA_RECURSOS = ConstanteConfigUtil.RUTA_RECURSOS;

	/** La Constante RUTA_TEMP. */
	public static final String RUTA_IMG = SEPARADOR_FILE + CarpetaType.IMG.getKey();

	/** La Constante RUTA_TEMP. */
	public static final String RUTA_REPORTE_GENERADO = CarpetaType.REPORTE_GENERADO.getKey();

	/** La Constante RUTA_RECURSOS. */
	public static final String RUTA_RECURSOS_BYTE_BUFFER = ConstanteConfigUtil.RUTA_RECURSOS_BYTE_BUFFER;

	/**
	 * Logger para el registro de errores.
	 */
	private static Logger log = LoggerFactory.getLogger(ArchivoUtilidades.class);

	private ArchivoUtilidades() {
		throw new IllegalStateException("Utility ArchivoUtilidades");
	}

	/**
	 * Genera el Hash de un archivo.
	 *
	 * @param archivo el archivo
	 * @return the string
	 */
	public static String calcularHashArchivo(File archivo) {
		MessageDigest algor;
		FileInputStream fis;
		byte[] hash = null;
		try {
			algor = MessageDigest.getInstance("SHA1");
			fis = new FileInputStream(archivo);
			BufferedInputStream bis = new BufferedInputStream(fis);
			DigestInputStream dis = new DigestInputStream(bis, algor);

			// read the file and update the hash calculation
			while (dis.read() != -1)
				;

			// get the hash value as byte array
			hash = algor.digest();

		} catch (NoSuchAlgorithmException e) {
			log.error("calcularHashArchivo", e);
		} catch (FileNotFoundException e) {
			log.error("calcularHashArchivo", e);
		} catch (IOException e) {
			log.error("calcularHashArchivo", e.getMessage());
		}
		return byteArray2Hex(hash);

	}

	/**
	 * Byte array2 hex.
	 *
	 * @param hash el hash
	 * @return the string
	 */
	private static String byteArray2Hex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (var b : hash) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}

	/**
	 * Write file.
	 *
	 * @param data     el data
	 * @param fileName el file name
	 * @throws IOException Se�ales de que una excepci�n de E / S se ha producido.
	 */
	public static void writeFile(byte[] data, String fileName) throws Exception {
		OutputStream out = new FileOutputStream(fileName);
		out.write(data);
		out.close();
	}

	/**
	 * Convertiras kb.
	 *
	 * @param tamanioOriginal el tamanio original
	 * @return the double
	 */
	public static Double convertirasKB(Long tamanioOriginal) {
		if (tamanioOriginal.doubleValue() < 1024) {
			return new Double("1");
		}
		Double tam = tamanioOriginal.doubleValue();
		Double tamanio = new Double("1024");
		return redondear(tam / tamanio, 2);
	}

	/**
	 * Redondear.
	 *
	 * @param numero  el numero
	 * @param digitos el digitos
	 * @return the double
	 */
	public static double redondear(Double numero, int digitos) {
		int cifras = (int) Math.pow(10, digitos);
		return Math.rint(numero * cifras) / cifras;
	}

	/**
	 * Obtener ruta archivo tem.
	 *
	 * @return the string
	 */
	public static String obtenerRutaArchivoTem() {
		return RUTA_RECURSOS;
	}

	/**
	 * Redimensionar imagem.
	 *
	 * @param img           el img
	 * @param nombreArchivo el nombre archivo
	 * @param width         el width
	 * @param height        el height
	 * @return the file
	 * @throws IOException Se�ales de que una excepci�n de E / S se ha producido.
	 */
	public static File redimensionarImagem(File img, String nombreArchivo, int width, int height) throws IOException {
		int extDot = nombreArchivo.lastIndexOf('.');
		if (extDot > 0) {
			String extension = nombreArchivo.substring(extDot + 1);
			File fileRedimensionado = new File(nombreArchivo);
			BufferedImage imagem = ImageIO.read(img);
			BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = newImg.createGraphics();
			g.drawImage(imagem, 0, 0, width, height, null);
			ImageIO.write(newImg, extension, fileRedimensionado);
			return fileRedimensionado;
		}
		return null;
	}

	/**
	 * Validar tamano imagen.
	 *
	 * @param ruta   el ruta
	 * @param width  el width
	 * @param height el height
	 * @return true, en caso de exito
	 * @throws IOException Señales de que una excepción de E / S se ha producido.
	 */
	public static boolean validarTamanoImagen(String ruta, int width, int height) throws IOException {
		if (ruta == null) {
			return true;
		}
		File fileRedimensionado = new File(RUTA_RECURSOS + SEPARADOR_FILE + ruta);
		BufferedImage imagem = ImageIO.read(fileRedimensionado);
		int widthImg = imagem.getWidth();
		int heightImge = imagem.getHeight();

		return (width == widthImg && height == heightImge);
	}

	/**
	 * Obtener archivo.
	 *
	 * @param rutaArchivo el img
	 * @return the file vo
	 * @throws Exception the exception
	 */
	public static FileVO obtenerArchivo(String rutaArchivo) {
		FileVO resultado = new FileVO();
		if (rutaArchivo != null) {
			String rutaAbsoluta = RUTA_RECURSOS + SEPARADOR_FILE + rutaArchivo;
			resultado = obtenerArchivoPer(rutaAbsoluta);
		}
		return resultado;
	}

	public static FileVO obtenerArchivoPer(String rutaArchivo) {
		FileVO resultado = new FileVO();
		try {
			if (rutaArchivo != null) {
				FileInputStream fis = null;
				try {
					String rutaAbsoluta = rutaArchivo;
					File file = new File(rutaAbsoluta);
					fis = new FileInputStream(file);
					byte[] datosArchivo = new byte[fis.available()];
					fis.read(datosArchivo);
					resultado.setName(file.getName());
					resultado.setData(datosArchivo);
					resultado.setLength(datosArchivo.length);
				} finally {
					if (fis != null) {
						fis.close();
					}
				}
			}
		} catch (FileNotFoundException e) {
			log.error("obtenerArchivoPer", e);
		} catch (IOException e) {
			log.error("obtenerArchivoPer", e);
		} catch (Exception e) {
			log.error("obtenerArchivoPer", e);
		}
		return resultado;
	}

	public static void limpiarArchivoAllDirectory(String ruta) {
		try {
			File archivo = new File(ruta);
			if (archivo != null) {
				String[] ficheros = archivo.list();
				if (ficheros != null) {
					eliminarArhivoDirectory(ruta, ficheros);
				}
			}
			if (archivo.isDirectory() && archivo.delete()) {
				log.error(NO_SE_PUEDO_ELIMINAR_EL_ARCHIVO + archivo.getName());
			}
		} catch (Exception e) {
			log.error("Error.limpiarArchivoAllDirectory " + e.getMessage());
		}

	}

	public static void eliminarArhivoDirectory(String rutaRelativa, String[] ficheros) {
		try {
			for (var fileName : ficheros) {
				File file = new File(rutaRelativa + SEPARADOR_FILE + fileName);
				if (file.list() != null && file.list().length > 0) {
					eliminarArhivoDirectory(rutaRelativa + SEPARADOR_FILE + fileName, file.list());
				} else {
					if (file.delete()) {
						log.error(NO_SE_PUEDO_ELIMINAR_EL_ARCHIVO + file.getName());
					}
				}
				if (file.isDirectory() && file.delete()) {
					log.error(NO_SE_PUEDO_ELIMINAR_EL_ARCHIVO + file.getName());
				}
			}
		} catch (Exception e) {
			log.error("Error.eliminarArhivoDirectory " + e.getMessage());
		}

	}

	public static void limpiarArchivoAll(String ruta) {
		File archivo = new File(ruta);
		if (archivo != null) {
			File[] ficheros = archivo.listFiles();
			if (ficheros != null) {
				eliminarArhivo(ficheros);
			}
		}
	}

	public static void eliminarArhivo(File[] ficheros) {
		try {
			for (var file : ficheros) {
				if (file.exists()) {
					if (file.listFiles() != null && file.listFiles().length > 0) {
						eliminarArhivo(file.listFiles());
					} else {
						if (file.delete()) {
							log.error(NO_SE_PUEDO_ELIMINAR_EL_ARCHIVO + file.getName());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("eliminarArhivo", e);
		}

	}

	/**
	 * Eliminar archivo.
	 *
	 * @param rutaArchivo el ruta archivo
	 * @return true, en caso de exito
	 * @throws Exception the exception
	 */
	public static boolean eliminarArchivo(String rutaArchivo) {
		boolean resultado = false;
		try {
			if (rutaArchivo != null) {
				String rutaAbsoluta = RUTA_RECURSOS + SEPARADOR_FILE + rutaArchivo;
				File file = new File(rutaAbsoluta);
				if (!file.exists()) {
					resultado = true;
					return resultado;
				}
				if (!file.delete()) {
					log.error(NO_SE_PUEDO_ELIMINAR_EL_ARCHIVO + rutaArchivo);
				} else {
					resultado = true;
				}

			}
		} catch (Exception e) {
			log.error("eliminarArchivo", e);
			resultado = false;
		}

		return resultado;

	}

	public static List<File> obtenerArchivoList(String... rutaArchivo) {
		List<File> resultado = new ArrayList<File>();
		try {
			if (rutaArchivo != null) {
				for (var rutaAbsoluta : rutaArchivo) {
					File file = new File(rutaAbsoluta);
					if (!file.exists()) {
						continue;
					} else {
						File[] resulTemp = file.listFiles();
						resultado.addAll(Arrays.asList(resulTemp));
					}
				}
			}
		} catch (Exception e) {
			log.error("obtenerArchivoList", e);
		}

		return resultado;
	}

	public static byte[] obtenerArchivoByte(File file) {
		byte[] datosArchivo = null;
		try {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				datosArchivo = new byte[fis.available()];
				fis.read(datosArchivo);
			} finally {
				if (fis != null) {
					fis.close();
				}
			}
		} catch (IOException e) {
			log.error("obtenerArchivoByte", e);
		} catch (Exception e) {
			log.error("obtenerArchivoByte", e);
		}
		return datosArchivo;
	}

	/**
	 * Subir archivo.
	 *
	 * @param datosArchivo el datos archivo
	 * @param nombre       el nombre
	 * @param ruta         el ruta
	 * @param esTemp       el es temp
	 * @throws Exception the exception
	 */
	public static void subirArchivo(byte[] datosArchivo, String nombre, String ruta, boolean esTemp) throws Exception {
		byte[] temp = datosArchivo;
		byte[] buffer = new byte[temp.length];
		String rutaAbsoluta = RUTA_RECURSOS;
		if (!esTemp) {
			rutaAbsoluta = rutaAbsoluta + RUTA_IMG;
		}
		File file = new File(rutaAbsoluta + SEPARADOR_FILE + ruta);
		file.mkdirs();
		System.arraycopy(temp, 0, buffer, 0, temp.length);
		writeFile(buffer, rutaAbsoluta + SEPARADOR_FILE + ruta + SEPARADOR_FILE + nombre);
	}

	/**
	 * Subir archivo reporte.
	 *
	 * @param datosArchivo el datos archivo
	 * @param nombre       el nombre
	 * @param ruta         el ruta
	 * @param esTemp       el es temp
	 * @throws Exception the exception
	 */
	public static void subirArchivoReporte(byte[] datosArchivo, String nombre, String ruta, boolean esTemp)
			throws Exception {
		byte[] temp = datosArchivo;
		byte[] buffer = new byte[temp.length];
		String rutaAbsoluta = RUTA_RECURSOS;
		if (!esTemp) {
			rutaAbsoluta = rutaAbsoluta + SEPARADOR_FILE + RUTA_REPORTE_GENERADO;
		}
		File file = new File(rutaAbsoluta + SEPARADOR_FILE + ruta);
		file.mkdirs();
		System.arraycopy(temp, 0, buffer, 0, temp.length);
		writeFile(buffer, rutaAbsoluta + SEPARADOR_FILE + ruta + SEPARADOR_FILE + nombre);
	}

	public static void subirArchivoBigMemory(byte[] resultado, String userName, String archivoName) throws Exception {
		if (!StringUtil.isNullOrEmpty(userName)) {
			userName = userName + SEPARADOR_FILE;
		} else {
			userName = "";
		}
		File archivoZip = new File(RUTA_RECURSOS_BYTE_BUFFER + userName);
		if (!archivoZip.isFile()) {
			archivoZip.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(RUTA_RECURSOS_BYTE_BUFFER + userName + "" + archivoName);
		out.write(resultado);
		out.close();
	}

	public static void copyArchivo(String datosArchivo, String nombre, String ruta, boolean esTemp) throws Exception {
		String rutaAbsoluta = RUTA_RECURSOS;
		if (!esTemp) {
			rutaAbsoluta = rutaAbsoluta + SEPARADOR_FILE + RUTA_REPORTE_GENERADO;
		}
		File sourceFile = new File(RUTA_RECURSOS_BYTE_BUFFER + datosArchivo);
		File destFile = new File(rutaAbsoluta + SEPARADOR_FILE + ruta);
		if (!destFile.exists()) {
			destFile.mkdirs();
		}
		destFile = new File(rutaAbsoluta + SEPARADOR_FILE + ruta + SEPARADOR_FILE + nombre);
		copyArchivo(sourceFile, destFile);
		if (sourceFile.delete()) {
			log.error(NO_SE_PUEDO_ELIMINAR_EL_ARCHIVO + sourceFile.getName());
		}
	}

	public static void copyArchivoBigMemory(String datosArchivo, String nombre, String ruta, boolean esTemp)
			throws Exception {
		String rutaAbsoluta = RUTA_RECURSOS;
		if (!esTemp) {
			rutaAbsoluta = rutaAbsoluta + SEPARADOR_FILE + RUTA_REPORTE_GENERADO;
		}
		File sourceFile = new File(rutaAbsoluta + SEPARADOR_FILE + ruta + SEPARADOR_FILE + nombre);
		File destFile = new File(RUTA_RECURSOS_BYTE_BUFFER + datosArchivo);
		copyArchivo(sourceFile, destFile);
		if (sourceFile.delete()) {
			log.error(NO_SE_PUEDO_ELIMINAR_EL_ARCHIVO + sourceFile.getName());
		}
	}

	public static void copyArchivoBigMemoryTrama(String usuario, String datosArchivo, String nombre, String ruta)
			throws Exception {
		File sourceFile = new File(RUTA_RECURSOS_BYTE_BUFFER + usuario + SEPARADOR_FILE + datosArchivo);
		File destFile = new File(ruta + SEPARADOR_FILE + nombre);
		copyArchivo(sourceFile, destFile);
		if (sourceFile.delete()) {
			log.error(NO_SE_PUEDO_ELIMINAR_EL_ARCHIVO + sourceFile.getName());
		}
	}

	public static void copyArchivo(File sourceFile, File destFile) throws Exception {
		try {
			FileChannel source = null;
			FileChannel destination = null;
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			if (destination != null && source != null) {
				destination.transferFrom(source, 0, source.size());
			}
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		} catch (Exception e) {
			log.error("copyArchivo", e);
		}

	}

	/**
	 * Obtiene bytes from file.
	 *
	 * @param file el file
	 * @return bytes from file
	 * @throws IOException Se�ales de que una excepci�n de E / S se ha producido.
	 */
	public static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}

		is.close();
		return bytes;
	}

	public static int contarLineasTexto(String texto) {
		byte[] content = texto.getBytes();
		InputStream is = null;
		BufferedReader bfReader = null;
		int respuesta = 0;
		try {
			is = new ByteArrayInputStream(content);
			bfReader = new BufferedReader(new InputStreamReader(is));
			String temp = null;
			while ((temp = bfReader.readLine()) != null) {
				respuesta++;
			}
		} catch (IOException e) {
			log.error("contarLineasTexto", e);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception ex) {
				log.error("contarLineasTexto", ex);
			}
		}
		return respuesta;
	}

	public static int contarCarateres(String texto, char caracter) {
		int contador = 0;
		if (!StringUtil.isNullOrEmpty(texto)) {
			char[] arregloCaracteres = texto.toCharArray();
			for (int i = 0; i < arregloCaracteres.length; i++) {
				if (caracter == arregloCaracteres[i]) {
					contador++;
				}
			}
		}
		return contador;
	}

	public static List<Integer> buscarLineaPalabra(String texto, String palabra) {
		List<Integer> lineaErroList = new ArrayList<Integer>();
		byte[] content = texto.getBytes();
		InputStream is = null;
		BufferedReader bfReader = null;
		int respuesta = 1;
		try {
			is = new ByteArrayInputStream(content);
			bfReader = new BufferedReader(new InputStreamReader(is));
			String temp = null;
			while ((temp = bfReader.readLine()) != null) {
				if (temp.contentEquals(palabra)) {
					lineaErroList.add(respuesta);
				}
				respuesta++;
			}
		} catch (IOException e) {
			log.error("buscarLineaPalabra", e);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception ex) {
				log.error("buscarLineaPalabra", ex);
			}
		}
		return lineaErroList;
	}

	/**
	 * Eliminar archivo ruta.
	 *
	 * @param rutaArchivo el ruta archivo
	 * @return true, en caso de exito
	 * @throws Exception the exception
	 */
	public static boolean eliminarArchivoRuta(String rutaArchivo) throws Exception {
		boolean resultado = false;
		try {
			if (rutaArchivo != null) {
				File file = new File(rutaArchivo);
				if (!file.delete()) {
					log.error(NO_SE_PUEDO_ELIMINAR_EL_ARCHIVO + rutaArchivo);
				} else {
					resultado = true;
				}

			}
		} catch (Exception e) {
			log.error("eliminarArchivoRuta", e);
			resultado = false;
		}

		return resultado;

	}
}