package pe.buildsoft.erp.core.infra.transversal.utilitario.ftp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.buildsoft.erp.core.infra.transversal.cache.ConfiguracionCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.FTPVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.RespuestaFTPVO;
import pe.buildsoft.erp.core.infra.transversal.type.ProtocoloType;
import pe.buildsoft.erp.core.infra.transversal.type.TipoParametroType;
import pe.buildsoft.erp.core.infra.transversal.type.TipoVariableType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ArchivoUtilidades;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfiguracionTramaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ResourceUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

public class FTPClienteUtil {

	private static final String $_DESCARGAR_ERROR_EN = "${DESCARGAR}Error en ";
	private static final String CONEXION_NO_SE_PUDO_CONECTAR_AL_FTP = "${CONEXION}No se pudo conectar al FTP ==> ";
	private static final String ERROR_OCURRIO_UN_ERROR_INESPERADO_DESCARGAR_ARCHIVO_DEL_FTP = "${ERROR}Ocurrio un error inesperado descargar archivo del FTP ==> ";
	private static final String SUBIR_ERROR_NO_SE_PUDO_SUBIR_ERROR = "${SUBIR}Error ==> No se pudo subir ==> error : ";
	private static final String FI = "{";
	private static final String FF = "}";
	private static final String FLAG_FIN = ConstanteConfiguracionTramaUtil.FLAG_FIN;
	private static final String FLAG_INICIO = ConstanteConfiguracionTramaUtil.FLAG_INICIO;

	/**
	 * Logger para el registro de errores.
	 */
	private static Logger log = LoggerFactory.getLogger(FTPClienteUtil.class);

	public static String obtenerVariableFechaFormateada(String rutaArchivo) {
		if (rutaArchivo != null && (rutaArchivo.contains(FI) && rutaArchivo.contains(FF))) {
			int indexOf = rutaArchivo.indexOf(FI);
			int lastIndexOf = rutaArchivo.lastIndexOf(FF);
			String formato = rutaArchivo.substring(indexOf + 1, lastIndexOf);
			rutaArchivo = rutaArchivo.replace(FI + formato + FF,
					FechaUtil.obtenerFechaFormatoPersonalizado(FechaUtil.obtenerFechaActual(), formato));
		}
		return rutaArchivo;
	}

	public static String obtenerVariableFechaFormateada(String rutaArchivo, OffsetDateTime fecha) {
		if (rutaArchivo != null && (rutaArchivo.contains(FI) && rutaArchivo.contains(FF))) {
			int indexOf = rutaArchivo.indexOf(FI);
			int lastIndexOf = rutaArchivo.lastIndexOf(FF);
			String formato = rutaArchivo.substring(indexOf + 1, lastIndexOf);
			rutaArchivo = rutaArchivo.replace(FI + formato + FF,
					FechaUtil.obtenerFechaFormatoPersonalizado(fecha, formato));
		}
		return rutaArchivo;
	}

	public static String subirArchivo(FTPVO fTPVO) {
		String resultado = "";
		// inicio
		if (ProtocoloType.SFTP.getKey().equals(fTPVO.getProtocolo())) {
			resultado = subirArchivoSFTPClient(fTPVO);
		} else {
			resultado = subirArchivoFTPClient(fTPVO);
		}
		// fin
		return resultado;
	}

	public static String subirArchivoSFTPClient(FTPVO fTPVO) {
		String resultado = "";
		SftpClient sftpClient = new SftpClient(fTPVO.getServer(), fTPVO.getPort(), fTPVO.getUsuario(),
				fTPVO.getClave());
		String rutaArchivoLocal = fTPVO.getRutaLocal();
		boolean success = false;

		try {
			String resultConexion = sftpClient.connect();
			boolean conectoFTP = true;
			// Verificar conexión con el servidor.
			conectoFTP = resultConexion.equals("");
			if (conectoFTP) {
				// Verificar si se cambia de direcotirio de trabajo
				sftpClient.changeWorkingDirectory(fTPVO.getRutaFTP());// Cambiar directorio de trabajo
				// Activar que se envie cualquier tipo de archivo
				String remoteFile1 = fTPVO.getRutaFTP() + fTPVO.getNombreArchivoFTP();
				// Inicio BUIDSOFT 01 Requerimiento telecobranza pendiente
				String successVal = sftpClient.uploadFile(rutaArchivoLocal + fTPVO.getNombreArchivoLocal(),
						remoteFile1);
				// Fin BUIDSOFT 01 Requerimiento telecobranza pendiente
				success = successVal.equals("");

				if (!success) {
					resultado = "${SUBIR}Error en " + remoteFile1 + " ==> No se pudo subir ==> error : " + successVal;
				}

			} else {
				log.error("No se pudo conectar al SFTP --> " + fTPVO.getServer());
				resultado = "${SUBIR}Error  ==> No se pudo subir";
			}
		} catch (Exception e) {
			log.error("subirArchivoSFTPClient", e);
			resultado = SUBIR_ERROR_NO_SE_PUDO_SUBIR_ERROR + e.getMessage();
		} finally {
			try {
				if (sftpClient != null) {
					sftpClient.disconnect();
				}
			} catch (Exception ex) {
				log.error("subirArchivoSFTPClient", ex);
			}
		}
		return resultado;

	}

	public static String subirArchivoFTPClient(FTPVO fTPVO) {
		String resultado = "";
		// FTPHTTPClient
		FTPClient ftpClient = new FTPClient();

		try {
			// Inicio BuildSoft Mejora Orquestador flujo 11/09/2019
			int timeout = 600 * 1000;// 10 min
			if (ConfiguracionCacheUtil.getInstance().containsKeyPwrConfUtil("configuracion.timeout.ftp.milisegundos")) {
				timeout = ConfiguracionCacheUtil.getInstance()
						.getPwrConfUtilInt("configuracion.timeout.ftp.milisegundos");
			}
			ftpClient.setConnectTimeout(timeout);
			ftpClient.setDefaultTimeout(timeout);
			// Fin BuildSoft Mejora Orquestador flujo 11/09/2019
			ftpClient.connect(fTPVO.getServer(), fTPVO.getPort());
			ftpClient.login(fTPVO.getUsuario(), fTPVO.getClave());
			// Verificar conexión con el servidor.
			int reply = ftpClient.getReplyCode();
			if (FTPReply.isPositiveCompletion(reply)) {
				// Verificar si se cambia de direcotirio de trabajo
				ftpClient.changeWorkingDirectory(fTPVO.getRutaFTP());// Cambiar directorio de trabajo
				// Activar que se envie cualquier tipo de archivo
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				BufferedInputStream buffIn = null;
				buffIn = new BufferedInputStream(
						new FileInputStream(fTPVO.getRutaLocal() + fTPVO.getNombreArchivoLocal()));
				ftpClient.enterLocalPassiveMode();
				boolean isTransferencia = ftpClient.storeFile(fTPVO.getRutaFTP() + fTPVO.getNombreArchivoFTP(), buffIn);// Ruta
																														// completa
																														// de
																														// alojamiento
																														// en
																														// el
																														// FTP
				buffIn.close(); // Cerrar envio de archivos al FTP
				if (!isTransferencia) {
					resultado = "${SUBIR}Error en " + fTPVO.getRutaFTP() + fTPVO.getNombreArchivoFTP()
							+ " ==> No se pudo subir ==> reply : " + reply;
				}
			} else {
				log.error("No se pudo conectar al FTP --> " + fTPVO.getServer());
				resultado = "${SUBIR}Error  ==> No se pudo subir ==> reply : " + reply;
			}
		} catch (Exception e) {
			log.error("subirArchivoFTPClient", e);
			resultado = SUBIR_ERROR_NO_SE_PUDO_SUBIR_ERROR + e.getMessage();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				log.error("subirArchivoFTPClient", ex);
			}
		}
		return resultado;

	}

	public static boolean descargarArchivo(FTPVO fTPVO, Map<String, Object> parametrosMap) {
		boolean resultado = false;
		if (ProtocoloType.SFTP.getKey().equals(fTPVO.getProtocolo())) {
			resultado = descargarArchivoSFTP(fTPVO, parametrosMap);
		} else {
			resultado = descargarArchivoFTP(fTPVO, parametrosMap);
		}
		return resultado;
	}

	public static boolean descargarArchivoSFTP(FTPVO fTPVO, Map<String, Object> parametrosMap) {
		boolean esSimulacion = ResourceUtil
				.esSimulacion(parametrosMap.get(ConstanteConfiguracionTramaUtil.ES_SIMULACION));
		boolean success = false;
		String usuario = "manual" + fTPVO.getIdJuegoTrama();
		String rutaArchivo = ConstanteConfigUtil.generarRuta(ArchivoUtilidades.RUTA_RECURSOS,
				ArchivoUtilidades.RUTA_REPORTE_GENERADO, usuario) + fTPVO.getRutaLocal();
		fTPVO.setRutaLocal(rutaArchivo);
		File downloadFile1 = new File(rutaArchivo);
		if (!downloadFile1.exists()) {
			downloadFile1.mkdirs();
		}
		SftpClient sftpClient = new SftpClient(fTPVO.getServer(), fTPVO.getPort(), fTPVO.getUsuario(),
				fTPVO.getClave());
		try {
			String resultConexion = "";
			if (!esSimulacion) {
				resultConexion = sftpClient.connect();
			}
			boolean conectoFTP = true;
			if (!esSimulacion) {
				conectoFTP = resultConexion.equals("");
			}
			if (conectoFTP) {
				if (!esSimulacion) {
					String remoteFile1 = fTPVO.getRutaFTP() + fTPVO.getNombreArchivoFTP();
					String successVal = sftpClient.retrieveFile(remoteFile1,
							rutaArchivo + fTPVO.getNombreArchivoLocal());
					success = successVal.equals("");
					if (Boolean.valueOf(parametrosMap.get(ConstanteConfiguracionTramaUtil.ES_IMPRIMIR).toString())) {
						log.error("descargarArchivo.Inicio ");
						log.error("descargarArchivo.Nombre del archivo " + fTPVO.getNombreArchivoFTP());
						log.error("descargarArchivo.Fin ");
						File sourceFile = new File(rutaArchivo + fTPVO.getNombreArchivoLocal());
						String rutaSession = ConstanteConfigUtil.RUTA_SESSION_TEMP
								+ ConstanteConfigUtil.generarRuta(parametrosMap.get("usuario") + "", "trama");
						File sourceDestino = new File(rutaSession);
						if (!sourceDestino.exists()) {
							sourceDestino.mkdirs();
						}
						sourceDestino = new File(rutaSession + fTPVO.getNombreArchivoFTP());
						ArchivoUtilidades.copyArchivo(sourceFile, sourceDestino);
					}

				} else {
					File sourceFile = new File(ConstanteConfigUtil.RUTA_RECURSOS_BYTE_BUFFER + ConstanteConfigUtil
							.generarRuta(parametrosMap.get(ConstanteConfiguracionTramaUtil.USUARIO) + "",
									fTPVO.getNombreArchivoLocal()));
					if (sourceFile.isFile()) {
						ArchivoUtilidades.copyArchivoBigMemoryTrama(
								parametrosMap.get(ConstanteConfiguracionTramaUtil.USUARIO) + "",
								fTPVO.getNombreArchivoLocal(), fTPVO.getNombreArchivoLocal(), rutaArchivo);
						success = true;
					}
				}
			} else {
				log.error("descargarArchivo-->No se pudo conectar al SFTP --> " + fTPVO.getServer());
			}

		} catch (Exception ex) {
			log.error("descargarArchivoSFTP", ex);
		} finally {
			try {
				if (sftpClient != null) {
					sftpClient.disconnect();
				}
			} catch (Exception ex) {
				log.error("descargarArchivoSFTP", ex);
			}
		}
		return success;
	}

	public static boolean descargarArchivoFTP(FTPVO fTPVO, Map<String, Object> parametrosMap) {
		boolean esSimulacion = ResourceUtil
				.esSimulacion(parametrosMap.get(ConstanteConfiguracionTramaUtil.ES_SIMULACION));
		boolean success = false;
		String usuario = "manual" + fTPVO.getIdJuegoTrama();
		String rutaArchivo = ConstanteConfigUtil.generarRuta(ArchivoUtilidades.RUTA_RECURSOS,
				ArchivoUtilidades.RUTA_REPORTE_GENERADO, usuario) + fTPVO.getRutaLocal();
		fTPVO.setRutaLocal(rutaArchivo);
		File downloadFile1 = new File(rutaArchivo);
		if (!downloadFile1.exists()) {
			downloadFile1.mkdirs();
		}
		FTPClient ftpClient = new FTPClient();

		try {
			if (!esSimulacion) {
				// Inicio BuildSoft Mejora Orquestador flujo 11/09/2019
				int timeout = 600 * 1000;// 10 min
				if (ConfiguracionCacheUtil.getInstance()
						.containsKeyPwrConfUtil("configuracion.timeout.ftp.milisegundos")) {
					timeout = ConfiguracionCacheUtil.getInstance()
							.getPwrConfUtilInt("configuracion.timeout.ftp.milisegundos");
				}
				ftpClient.setConnectTimeout(timeout);
				ftpClient.setDefaultTimeout(timeout);
				ftpClient.connect(fTPVO.getServer(), fTPVO.getPort());
				ftpClient.login(fTPVO.getUsuario(), fTPVO.getClave());
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			}
			boolean conectoFTP = true;
			if (!esSimulacion) {
				// Verificar conexión con el servidor.
				int reply = ftpClient.getReplyCode();
				conectoFTP = FTPReply.isPositiveCompletion(reply);
			}
			if (conectoFTP) {
				// APPROACH #1: using retrieveFile(String, OutputStream)
				if (!esSimulacion) {
					String remoteFile1 = fTPVO.getRutaFTP() + fTPVO.getNombreArchivoFTP();
					OutputStream outputStream1 = new FileOutputStream(rutaArchivo + fTPVO.getNombreArchivoLocal());
					// Escribe en el outputStream local el archivo remoto cuyo nombre esta en
					// remoteFile1
					success = ftpClient.retrieveFile(remoteFile1, outputStream1);
					if (Boolean.valueOf(parametrosMap.get(ConstanteConfiguracionTramaUtil.ES_IMPRIMIR).toString())) {
						log.error("descargarArchivo.Inicio ");
						log.error("descargarArchivo.Nombre del archivo " + fTPVO.getNombreArchivoFTP());
						log.error("descargarArchivo.getCharsetName " + ftpClient.getCharsetName());
						log.error("descargarArchivo.getCharset " + ftpClient.getCharset());
						log.error("descargarArchivo.getControlEncoding " + ftpClient.getControlEncoding());
						log.error("descargarArchivo.Fin ");
						File sourceFile = new File(rutaArchivo + fTPVO.getNombreArchivoLocal());
						String rutaSession = ConstanteConfigUtil.RUTA_SESSION_TEMP
								+ ConstanteConfigUtil.generarRuta(parametrosMap.get("usuario") + "", "trama");
						File sourceDestino = new File(rutaSession);
						if (!sourceDestino.exists()) {
							sourceDestino.mkdirs();
						}
						sourceDestino = new File(rutaSession + fTPVO.getNombreArchivoFTP());
						ArchivoUtilidades.copyArchivo(sourceFile, sourceDestino);
					}
					outputStream1.close();
				} else {
					File sourceFile = new File(ConstanteConfigUtil.RUTA_RECURSOS_BYTE_BUFFER + ConstanteConfigUtil
							.generarRuta(parametrosMap.get(ConstanteConfiguracionTramaUtil.USUARIO) + "",
									fTPVO.getNombreArchivoLocal()));
					if (sourceFile.isFile()) {
						ArchivoUtilidades.copyArchivoBigMemoryTrama(
								parametrosMap.get(ConstanteConfiguracionTramaUtil.USUARIO) + "",
								fTPVO.getNombreArchivoLocal(), fTPVO.getNombreArchivoLocal(), rutaArchivo);
						success = true;
					} else {
						success = false;
					}
				}
			} else {
				log.error("descargarArchivo-->No se pudo conectar al FTP --> " + fTPVO.getServer());
			}

		} catch (Exception ex) {
			log.error("descargarArchivoFTP", ex);
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				log.error("descargarArchivoFTP", ex);
			}
		}
		return success;
	}

	public static RespuestaFTPVO descargarArchivoListFilterPersonalizadoGrupo(FTPVO fTPVO,
			Map<String, Object> parametrosMap) {
		RespuestaFTPVO resultado = new RespuestaFTPVO();
		boolean esSimulacion = ResourceUtil
				.esSimulacion(parametrosMap.get(ConstanteConfiguracionTramaUtil.ES_SIMULACION));
		List<Map<String, Map<String, String>>> listaGrupoArchivoMap = new ArrayList<>();
		Map<String, String> resultadoTempMap = new HashMap<>();
		FTPClient ftpClient = new FTPClient();
		SftpClient sftpClient = null;
		int reply = 0;
		String resultConexion = "";
		String errorArchivo = ConstanteConfiguracionTramaUtil.ARTIFICIO_ERROR_NOMENCLATURA
				+ " El nombre de la trama no coincide con la configuración de la nomenclatura del juego, FTP --> rutaFTP : "
				+ fTPVO.getRutaFTP() + "  server : " + fTPVO.getServer() + " idJuego : " + fTPVO.getIdJuegoTrama()
				+ " ";
		if (esSimulacion) {
			errorArchivo = "La nomenclatura del archivo no coincide con la configuración, un nombre válido sería: "
					+ fTPVO.getNomenclaturaSimulacion();
		}
		try {
			boolean existeNumeroReferencia = !StringUtil.isNullOrEmpty(fTPVO.getNumeroReferencia());
			boolean conectoFTP = true;
			if (!esSimulacion) {
				if (ProtocoloType.SFTP.getKey().equals(fTPVO.getProtocolo())) {
					sftpClient = new SftpClient(fTPVO.getServer(), fTPVO.getPort(), fTPVO.getUsuario(),
							fTPVO.getClave());
					resultConexion = sftpClient.connect();
					sftpClient.changeWorkingDirectory(fTPVO.getRutaFTP());
				} else {
					// Inicio BuildSoft Mejora Orquestador flujo 11/09/2019
					int timeout = 600 * 1000;// 10 min
					if (ConfiguracionCacheUtil.getInstance()
							.containsKeyPwrConfUtil("configuracion.timeout.ftp.milisegundos")) {
						timeout = ConfiguracionCacheUtil.getInstance()
								.getPwrConfUtilInt("configuracion.timeout.ftp.milisegundos");
					}
					ftpClient.setConnectTimeout(timeout);
					ftpClient.setDefaultTimeout(timeout);
					ftpClient.connect(fTPVO.getServer(), fTPVO.getPort());
					ftpClient.login(fTPVO.getUsuario(), fTPVO.getClave());
					ftpClient.enterLocalPassiveMode();
					ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
					ftpClient.changeWorkingDirectory(fTPVO.getRutaFTP());
				}
			}

			if (!esSimulacion) {
				// Verificar conexión con el servidor.
				if (ProtocoloType.SFTP.getKey().equals(fTPVO.getProtocolo())) {
					conectoFTP = resultConexion.equals("");
				} else {
					reply = ftpClient.getReplyCode();
					conectoFTP = FTPReply.isPositiveCompletion(reply);
				}
			}
			String nombreArchivoSimlar = "";
			if (conectoFTP) {
				FTPFile archivosFTP[] = null;
				if (!esSimulacion) {
					if (ProtocoloType.SFTP.getKey().equals(fTPVO.getProtocolo())) {
						archivosFTP = sftpClient.listFiles();
					} else {
						archivosFTP = ftpClient.listFiles();
					}
					if (archivosFTP != null && archivosFTP.length == 0) { // REQUERIMIENTO ESTADOS DE ORQUESTADOR CUANDO
																			// NO SE ENCONTRO TRAMAS
						StringBuilder builder = new StringBuilder();
						builder.append(ConstanteConfiguracionTramaUtil.ARTIFICIO_NO_HAY_TRAMAS);
						builder.append(errorArchivo);
						errorArchivo = builder.toString();
					}
				} else {
					Map<Long, String> nombreArchivoSimlarListMap = (Map<Long, String>) parametrosMap
							.get(ConstanteConfiguracionTramaUtil.ARCHIVO_SIMULAR);
					nombreArchivoSimlar = nombreArchivoSimlarListMap.get(fTPVO.getIdTramaNomenclaturaArchivo());
					archivosFTP = new FTPFile[1];// LISTA x
					int contador = 0;
					FTPFile archivoSimular = new FTPFile();
					archivoSimular.setName(nombreArchivoSimlar);
					archivosFTP[contador] = archivoSimular;
					contador++;

				}
				for (var file : archivosFTP) {
					if (!resultado.getNombreArchivoFTPMap().containsKey(file.getName())) {
						resultado.getNombreArchivoFTPMap().put(file.getName().toUpperCase(), file.getName());
					}
				}
				if (!fTPVO.getNombreArchivoLocal().contains("@@")) {
					if (!esSimulacion) {
						resultado.setListaGrupoArchivoMap(listaGrupoArchivoMap);
						resultado.setError(false);
						resultado.setMensajeError(errorArchivo);
						return resultado;
					} else {
						if (fTPVO.getNombreArchivoLocal().equals(nombreArchivoSimlar)) {
							resultado.setListaGrupoArchivoMap(listaGrupoArchivoMap);
							resultado.setError(false);
							resultado.setMensajeError(errorArchivo);
							return resultado;
						}
					}
				}
				String nomenclaturaValidarTemp[] = fTPVO.getNombreArchivoLocal().split("@@");
				String nomenclaturaValidar = "";

				int index = 0;
				Map<Integer, Integer> indexOfMap = new HashMap<>();
				Map<Integer, Map<String, Integer>> indexOfFinalMap = new HashMap<>();
				Map<String, Map<String, Integer>> indexOfFinalTipoMap = new HashMap<>();
				Map<String, String> indexOfFinalTipoFormatoMap = new HashMap<>();
				for (var objData : nomenclaturaValidarTemp) {
					if (!objData.contains(":")) {
						indexOfMap.put(index, objData.length());
					} else {
						indexOfMap.put(index, 0);
					}
					index++;
				}
				indexOfMap.put(index, 0);
				index = 0;

				Map<String, Integer> indexOfTempMap = new HashMap<>();
				Map<Integer, Integer> indexOfTempNoMap = new HashMap<>();
				for (var objData : nomenclaturaValidarTemp) {
					if (objData.contains(":")) {
						String[] valorDigiro = objData.split(":");
						int numeroDigito = Integer.parseInt(valorDigiro[2]);
						if (valorDigiro.length > 3) {
							if (index == 0) {
								Map<String, Integer> indexOfProcesarMap = new HashMap<>();
								indexOfTempMap.put(FLAG_INICIO, 0);
								indexOfProcesarMap.put(FLAG_INICIO, 0);

								indexOfProcesarMap.put(FLAG_FIN, indexOfMap.get(index));
								indexOfTempMap.put(FLAG_FIN, indexOfMap.get(index));
								indexOfFinalMap.put(index, indexOfProcesarMap);

								Map<String, Integer> indexOfProcesarTipoMap = new HashMap<>();
								indexOfProcesarTipoMap.put(FLAG_INICIO,
										indexOfTempMap.get(FLAG_FIN) + indexOfMap.get(index));
								indexOfProcesarTipoMap.put(FLAG_FIN,
										indexOfTempMap.get(FLAG_FIN) + indexOfMap.get(index) + numeroDigito);

								TipoParametroType tipoParametroType = TipoParametroType.get(valorDigiro[3]);
								if (tipoParametroType != null) {
									indexOfFinalTipoMap.put(tipoParametroType.toString(), indexOfProcesarTipoMap);
								} else {
									TipoVariableType tipoVariableType = TipoVariableType.get(valorDigiro[1]);
									indexOfFinalTipoFormatoMap.put(tipoVariableType.toString(), valorDigiro[3]);
									indexOfFinalTipoMap.put(tipoVariableType.toString(), indexOfProcesarTipoMap);
								}
							} else {
								Map<String, Integer> indexOfProcesarTipoMap = new HashMap<>();
								indexOfProcesarTipoMap.put(FLAG_INICIO,
										indexOfTempMap.get(FLAG_FIN) + indexOfMap.get(index));
								indexOfProcesarTipoMap.put(FLAG_FIN,
										indexOfTempMap.get(FLAG_FIN) + indexOfMap.get(index) + numeroDigito);

								Map<String, Integer> indexOfProcesarMap = new HashMap<>();
								int indexOfCalcular = indexOfTempMap.get(FLAG_FIN) + indexOfMap.get(index)
										+ numeroDigito;
								indexOfTempMap.put(FLAG_INICIO, indexOfCalcular);
								indexOfProcesarMap.put(FLAG_INICIO, indexOfCalcular);

								indexOfCalcular = indexOfTempMap.get(FLAG_INICIO) + indexOfMap.get(index + 1);
								indexOfTempMap.put(FLAG_FIN, indexOfCalcular);
								indexOfProcesarMap.put(FLAG_FIN, indexOfCalcular);
								indexOfFinalMap.put(index, indexOfProcesarMap);
								indexOfTempNoMap.put(index + 1, index + 1);

								TipoParametroType tipoParametroType = TipoParametroType.get(valorDigiro[3]);
								if (tipoParametroType != null) {
									indexOfFinalTipoMap.put(tipoParametroType.toString(), indexOfProcesarTipoMap);
								} else {
									TipoVariableType tipoVariableType = TipoVariableType.get(valorDigiro[1]);
									indexOfFinalTipoFormatoMap.put(tipoVariableType.toString(), valorDigiro[3]);
									indexOfFinalTipoMap.put(tipoVariableType.toString(), indexOfProcesarTipoMap);
								}
							}
						}
					} else {
						nomenclaturaValidar = nomenclaturaValidar + objData;
						if (!indexOfTempNoMap.containsKey(index)) {
							if (index == 0) {
								Map<String, Integer> indexOfProcesarMap = new HashMap<>();
								indexOfTempMap.put(FLAG_INICIO, 0);
								indexOfProcesarMap.put(FLAG_INICIO, 0);

								indexOfProcesarMap.put(FLAG_FIN, indexOfMap.get(index));
								indexOfTempMap.put(FLAG_FIN, indexOfMap.get(index));
								indexOfFinalMap.put(index, indexOfProcesarMap);
							} else {
								Map<String, Integer> indexOfProcesarMap = new HashMap<>();
								int indexOfCalcular = indexOfTempMap.get(FLAG_FIN) + indexOfMap.get(index);
								indexOfTempMap.put(FLAG_INICIO, indexOfCalcular);
								indexOfProcesarMap.put(FLAG_INICIO, indexOfCalcular);

								indexOfCalcular = indexOfTempMap.get(FLAG_INICIO) + indexOfMap.get(index);
								indexOfTempMap.put(FLAG_FIN, indexOfCalcular);
								indexOfProcesarMap.put(FLAG_FIN, indexOfCalcular);
								indexOfFinalMap.put(index, indexOfProcesarMap);
							}
						}
					}
					index++;
				}

				for (var file : archivosFTP) {
					try {
						String nombreArchivo = "";
						for (var rangoIndexMap : indexOfFinalMap.entrySet()) {
							Map<String, Integer> rangoIndexDataMap = rangoIndexMap.getValue();
							try {
								nombreArchivo = nombreArchivo + file.getName()
										.substring(rangoIndexDataMap.get(FLAG_INICIO), rangoIndexDataMap.get(FLAG_FIN));
							} catch (Exception e) {
								continue;
							}
						}
						if (nombreArchivo.intern().toUpperCase().equals(nomenclaturaValidar.intern().toUpperCase())) {
							String numeroPoliza = "";
							if (indexOfFinalTipoMap.containsKey(TipoParametroType.NUMERO_POLIZA.toString())) {
								Map<String, Integer> rangoIndexDataMap = indexOfFinalTipoMap
										.get(TipoParametroType.NUMERO_POLIZA.toString());
								numeroPoliza = file.getName().substring(rangoIndexDataMap.get(FLAG_INICIO),
										rangoIndexDataMap.get(FLAG_FIN));
							}
							String fechaCalculada = "";
							String fechaCaluladaParametro = "";
							if (indexOfFinalTipoMap.containsKey(TipoVariableType.FECHA.toString())) {
								Map<String, Integer> rangoIndexDataMap = indexOfFinalTipoMap
										.get(TipoVariableType.FECHA.toString());
								fechaCalculada = file.getName().substring(rangoIndexDataMap.get(FLAG_INICIO),
										rangoIndexDataMap.get(FLAG_FIN));
								String formatoFecha = obtenerFormatoFecha(
										indexOfFinalTipoFormatoMap.get(TipoVariableType.FECHA.toString()));
								try {
									// Inicio mejora fechaCalculada
									OffsetDateTime dateTemp = FechaUtil.obtenerFechaFormatoPersonalizado(fechaCalculada,
											formatoFecha);
									fechaCaluladaParametro = FechaUtil.obtenerFechaFormatoPersonalizado(dateTemp,
											FechaUtil.DATE_DMY); // vuelve a formatear la fecha
									// Fin mejora fechaCalculada
								} catch (Exception e) {
									if (esSimulacion) {
										resultado.setError(true);
										resultado.setMensajeError(errorArchivo + ". El formato fecha '" + fechaCalculada
												+ "'  es inválido.");
									} else {
										fechaCalculada = "${ERROR} " + fechaCalculada + " FORMATO FECHA NO VALIDO ("
												+ formatoFecha + ")";
									}
								}
							}
							if (indexOfFinalTipoMap.containsKey(TipoParametroType.NUMERO_REFERENCIA.toString())
									&& !existeNumeroReferencia) {
								Map<String, Integer> rangoIndexDataMap = indexOfFinalTipoMap
										.get(TipoParametroType.NUMERO_REFERENCIA.toString());
								String numeroReferencia = file.getName().substring(rangoIndexDataMap.get(FLAG_INICIO),
										rangoIndexDataMap.get(FLAG_FIN));
								if (!resultadoTempMap.containsKey(numeroReferencia)) {
									Map<String, String> dataValueMap = new HashMap<>();
									dataValueMap.put(ConstanteConfiguracionTramaUtil.NOMENCLATURA, file.getName());
									if (!StringUtil.isNullOrEmpty(numeroPoliza)) {
										dataValueMap.put(ConstanteConfiguracionTramaUtil.NUMERO_POLIZA_PARAM,
												numeroPoliza);
									}
									dataValueMap.put(ConstanteConfiguracionTramaUtil.FECHA_CALCULADA_FORMATO_PARAM,
											fechaCalculada);
									dataValueMap.put(ConstanteConfiguracionTramaUtil.FECHA_CALCULADA_PARAMETRO,
											fechaCaluladaParametro);
									Map<String, Map<String, String>> resultadoGrupoMap = new HashMap<>();
									resultadoGrupoMap.put(numeroReferencia, dataValueMap);
									resultadoTempMap.put(numeroReferencia, numeroReferencia);
									listaGrupoArchivoMap.add(resultadoGrupoMap);
								}
							} else {
								String numeroReferencia = fTPVO.getNumeroReferencia();
								if (!resultadoTempMap.containsKey(numeroReferencia)) {
									Map<String, String> dataValueMap = new HashMap<>();
									dataValueMap.put(ConstanteConfiguracionTramaUtil.NOMENCLATURA, file.getName());
									if (!StringUtil.isNullOrEmpty(numeroPoliza)) {
										dataValueMap.put(ConstanteConfiguracionTramaUtil.NUMERO_POLIZA_PARAM,
												numeroPoliza);
									}
									dataValueMap.put(ConstanteConfiguracionTramaUtil.FECHA_CALCULADA_FORMATO_PARAM,
											fechaCalculada);
									dataValueMap.put(ConstanteConfiguracionTramaUtil.FECHA_CALCULADA_PARAMETRO,
											fechaCaluladaParametro);
									Map<String, Map<String, String>> resultadoGrupoMap = new HashMap<>();
									resultadoGrupoMap.put(numeroReferencia, dataValueMap);
									resultadoTempMap.put(numeroReferencia, numeroReferencia);
									listaGrupoArchivoMap.add(resultadoGrupoMap);
								}
								break;
							}
						}
					} catch (Exception e) {
						continue;
					}

				}
			} else {

				if (ProtocoloType.SFTP.getKey().equals(fTPVO.getProtocolo())) {
					log.error(
							"descargarArchivoListFilterPersonalizadoGrupo --> No se pudo conectar al SFTP --> rutaSFTP : "
									+ fTPVO.getRutaFTP() + "  server : " + fTPVO.getServer() + " idJuego : "
									+ fTPVO.getIdJuegoTrama() + " ");
					resultado.setError(true);
					resultado.setMensajeError(ConstanteConfiguracionTramaUtil.ARTIFICIO_ERROR_TECNICO
							+ " No se pudo conectar al SFTP Error " + reply + " --> rutaSFTP : " + fTPVO.getRutaFTP()
							+ "  server : " + fTPVO.getServer() + " idJuego : " + fTPVO.getIdJuegoTrama() + " ");
				} else {
					log.error(
							"descargarArchivoListFilterPersonalizadoGrupo --> No se pudo conectar al FTP --> rutaFTP : "
									+ fTPVO.getRutaFTP() + "  server : " + fTPVO.getServer() + " idJuego : "
									+ fTPVO.getIdJuegoTrama() + " ");
					resultado.setError(true);
					resultado.setMensajeError(ConstanteConfiguracionTramaUtil.ARTIFICIO_ERROR_TECNICO
							+ " No se pudo conectar al FTP Error " + reply + " --> rutaFTP : " + fTPVO.getRutaFTP()
							+ "  server : " + fTPVO.getServer() + " idJuego : " + fTPVO.getIdJuegoTrama() + " ");
				}

			}
		} catch (Exception ex) {
			log.error("descargarArchivoListFilterPersonalizadoGrupo", ex);
			resultado.setError(true);
			resultado.setMensajeError(ConstanteConfiguracionTramaUtil.ARTIFICIO_ERROR_TECNICO
					+ " Ocurrio un error al intentar conectar al FTP --> rutaFTP : " + fTPVO.getRutaFTP()
					+ "  server : " + fTPVO.getServer() + " idJuego : " + fTPVO.getIdJuegoTrama() + " error : "
					+ ex.getMessage());
		} finally {
			try {

				if (ProtocoloType.SFTP.getKey().equals(fTPVO.getProtocolo())) {
					if (sftpClient != null) {
						sftpClient.disconnect();

					}
				} else {
					if (ftpClient.isConnected()) {
						ftpClient.logout();
						ftpClient.disconnect();
					}
				}

			} catch (IOException ex) {
				log.error("descargarArchivoListFilterPersonalizadoGrupo", ex);
			}
		}
		if (listaGrupoArchivoMap.isEmpty()) {
			if (!resultado.isError()) {
				resultado.setError(true);
				resultado.setMensajeError(errorArchivo);
			}
		}
		resultado.setListaGrupoArchivoMap(listaGrupoArchivoMap);
		return resultado;
	}

	public static RespuestaFTPVO descargarArchivoListFilter(FTPVO fTPVO, Map<String, Object> parametrosMap) {
		RespuestaFTPVO resultado = new RespuestaFTPVO();
		FTPClient ftpClient = new FTPClient();
		SftpClient sftpClient = null;
		int reply = 0;
		String errorArchivo = "";
		String resultConexion = "";

		try {
			boolean conectoFTP = true;

			if (ProtocoloType.SFTP.getKey().equals(fTPVO.getProtocolo())) {
				sftpClient = new SftpClient(fTPVO.getServer(), fTPVO.getPort(), fTPVO.getUsuario(), fTPVO.getClave());
				resultConexion = sftpClient.connect();
				sftpClient.changeWorkingDirectory(fTPVO.getRutaFTP());
			} else {
				// Inicio BuildSoft Mejora Orquestador flujo 11/09/2019
				int timeout = 600 * 1000;// 10 min
				if (ConfiguracionCacheUtil.getInstance()
						.containsKeyPwrConfUtil("configuracion.timeout.ftp.milisegundos")) {
					timeout = ConfiguracionCacheUtil.getInstance()
							.getPwrConfUtilInt("configuracion.timeout.ftp.milisegundos");
				}
				ftpClient.setConnectTimeout(timeout);
				ftpClient.setDefaultTimeout(timeout);
				// Fin BuildSoft Mejora Orquestador flujo 11/09/2019
				ftpClient.connect(fTPVO.getServer(), fTPVO.getPort());
				ftpClient.login(fTPVO.getUsuario(), fTPVO.getClave());
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.changeWorkingDirectory(fTPVO.getRutaFTP());
			}

			// Verificar conexión con el servidor.
			if (ProtocoloType.SFTP.getKey().equals(fTPVO.getProtocolo())) {
				conectoFTP = resultConexion.equals("");
			} else {
				reply = ftpClient.getReplyCode();
				conectoFTP = FTPReply.isPositiveCompletion(reply);
			}

			if (conectoFTP) {
				FTPFile archivosFTP[] = null;
				if (ProtocoloType.SFTP.getKey().equals(fTPVO.getProtocolo())) {
					archivosFTP = sftpClient.listFiles();
				} else {
					archivosFTP = ftpClient.listFiles();
				}
				if (archivosFTP != null && archivosFTP.length == 0) {
					StringBuilder builder = new StringBuilder();
					builder.append("No existen archivos");
					errorArchivo = builder.toString();
				}

				for (var file : archivosFTP) {
					if (!resultado.getNombreArchivoFTPMap().containsKey(file.getName().toUpperCase())) {
						resultado.getNombreArchivoFTPMap().put(file.getName().toUpperCase(), file.getName());
						resultado.getNombreArchivoMetaDataFTPMap().put(file.getName().toUpperCase(),
								file.getTimestamp().getTime().toString());
					}
				}
			} else {

				if (ProtocoloType.SFTP.getKey().equals(fTPVO.getProtocolo())) {
					log.error(
							"descargarArchivoListFilterPersonalizadoGrupo --> No se pudo conectar al SFTP --> rutaSFTP : "
									+ fTPVO.getRutaFTP() + "  server : " + fTPVO.getServer() + " idJuego : "
									+ fTPVO.getIdJuegoTrama() + " ");
					resultado.setError(true);
					resultado.setMensajeError(ConstanteConfiguracionTramaUtil.ARTIFICIO_ERROR_TECNICO
							+ " No se pudo conectar al SFTP Error " + reply + " --> rutaSFTP : " + fTPVO.getRutaFTP()
							+ "  server : " + fTPVO.getServer() + " idJuego : " + fTPVO.getIdJuegoTrama() + " ");
				} else {
					log.error(
							"descargarArchivoListFilterPersonalizadoGrupo --> No se pudo conectar al FTP --> rutaFTP : "
									+ fTPVO.getRutaFTP() + "  server : " + fTPVO.getServer() + " idJuego : "
									+ fTPVO.getIdJuegoTrama() + " ");
					resultado.setError(true);
					resultado.setMensajeError(ConstanteConfiguracionTramaUtil.ARTIFICIO_ERROR_TECNICO
							+ " No se pudo conectar al FTP Error " + reply + " --> rutaFTP : " + fTPVO.getRutaFTP()
							+ "  server : " + fTPVO.getServer() + " idJuego : " + fTPVO.getIdJuegoTrama() + " ");
				}

			}
		} catch (Exception ex) {
			log.error("descargarArchivoListFilter", ex);
			resultado.setError(true);
			resultado.setMensajeError(ConstanteConfiguracionTramaUtil.ARTIFICIO_ERROR_TECNICO
					+ " Ocurrio un error al intentar conectar al FTP --> rutaFTP : " + fTPVO.getRutaFTP()
					+ "  server : " + fTPVO.getServer() + " idJuego : " + fTPVO.getIdJuegoTrama() + " error : "
					+ ex.getMessage());
		} finally {
			try {

				if (ProtocoloType.SFTP.getKey().equals(fTPVO.getProtocolo())) {
					if (sftpClient != null) {
						sftpClient.disconnect();

					}
				} else {
					if (ftpClient.isConnected()) {
						ftpClient.logout();
						ftpClient.disconnect();
					}
				}

			} catch (IOException ex) {
				log.error("descargarArchivoListFilter", ex);
			}
		}
		if (!resultado.isError() && errorArchivo.length() > 0) {
			resultado.setError(true);
			resultado.setMensajeError(errorArchivo);
		}
		return resultado;
	}

	public static RespuestaFTPVO testFTP(FTPVO fTPVO) {
		RespuestaFTPVO resultado = new RespuestaFTPVO();
		// Inicio
		if (ProtocoloType.SFTP.getKey().equals(fTPVO.getProtocolo())) {
			resultado = testClientSFTP(fTPVO);
		} else {
			resultado = testClientFTP(fTPVO);
		}
		// Fin
		return resultado;
	}

	public static RespuestaFTPVO testClientFTP(FTPVO fTPVO) {
		String ruta = fTPVO.getRutaFTP();
		RespuestaFTPVO resultado = new RespuestaFTPVO();
		FTPClient ftpClient = new FTPClient();
		try {
			fTPVO.setRutaLocal(obtenerVariableFechaFormateada(ruta));
			fTPVO.setRutaFTP(obtenerVariableFechaFormateada(ruta));
			// Inicio BuildSoft Mejora Orquestador flujo 11/09/2019
			int timeout = 600 * 1000;// 10 min
			if (ConfiguracionCacheUtil.getInstance().containsKeyPwrConfUtil("configuracion.timeout.ftp.milisegundos")) {
				timeout = ConfiguracionCacheUtil.getInstance()
						.getPwrConfUtilInt("configuracion.timeout.ftp.milisegundos");
			}
			ftpClient.setConnectTimeout(timeout);
			ftpClient.setDefaultTimeout(timeout);
			// Fin BuildSoft Mejora Orquestador flujo 11/09/2019
			ftpClient.connect(fTPVO.getServer(), fTPVO.getPort());
			// ftpClient.connect(fTPVO.getServer());
			ftpClient.login(fTPVO.getUsuario(), fTPVO.getClave());
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.changeWorkingDirectory(fTPVO.getRutaFTP());
			// Verificar conexión con el servidor.
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				log.error("testConexion --> No se pudo conectar al FTP --> rutaFTP : " + fTPVO.getRutaFTP() + " ");
				resultado.setError(true);
				resultado.setMensajeError("No se pudo conectar al FTP --> rutaFTP : " + fTPVO.getRutaFTP() + " ");
			}
		} catch (Exception ex) {
			log.error("testClientFTP", ex);
			resultado.setError(true);
			resultado.setMensajeError("Ocurrio un error al intentar conectar al FTP --> rutaFTP : " + fTPVO.getRutaFTP()
					+ "  error : " + ex.getMessage());
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				log.error("testClientFTP", ex);
			}
		}
		return resultado;
	}

	public static RespuestaFTPVO testClientSFTP(FTPVO fTPVO) {
		String ruta = fTPVO.getRutaFTP();
		RespuestaFTPVO resultado = new RespuestaFTPVO();
		SftpClient sftpClient = new SftpClient(fTPVO.getServer(), fTPVO.getPort(), fTPVO.getUsuario(),
				fTPVO.getClave());
		try {
			fTPVO.setRutaLocal(obtenerVariableFechaFormateada(ruta));
			fTPVO.setRutaFTP(obtenerVariableFechaFormateada(ruta));
			String resultConexion = sftpClient.connect();
			String resulRuta = sftpClient.changeWorkingDirectory(fTPVO.getRutaFTP());
			// Verificar conexión con el servidor.
			String reply = resultConexion;
			if (!reply.equals("") || !resulRuta.equals("")) {
				log.error("testConexion --> No se pudo conectar al SFTP --> rutaSFTP : " + fTPVO.getRutaFTP() + " ");
				resultado.setError(true);
				resultado.setMensajeError("No se pudo conectar al SFTP --> rutaSFTP : " + fTPVO.getRutaFTP() + " ");
			}
		} catch (Exception ex) {
			log.error("testClientSFTP", ex);
			resultado.setError(true);
			resultado.setMensajeError("Ocurrio un error al intentar conectar al SFTP --> rutaSFTP : "
					+ fTPVO.getRutaFTP() + "  error : " + ex.getMessage());
		} finally {
			try {
				if (sftpClient != null) {
					sftpClient.disconnect();
				}
			} catch (Exception ex) {
				log.error("testClientSFTP", ex);
			}
		}
		return resultado;
	}

	private static String obtenerFormatoFecha(String formatoTemp) {
		String formato = formatoTemp;
		if (formatoTemp != null && (formatoTemp.contains(FI) && formatoTemp.contains(FF))) {
			int indexOf = formatoTemp.indexOf(FI);
			int lastIndexOf = formatoTemp.lastIndexOf(FF);
			formato = formatoTemp.substring(indexOf + 1, lastIndexOf);

		}
		return formato;
	}

	public static String descargarArchivo(FTPVO fTPVO, String usuario) {
		String resultado = "";
		// inicio
		if (ProtocoloType.SFTP.getKey().equals(fTPVO.getProtocolo())) {
			resultado = descargarArchivoSFTPClient(fTPVO, usuario);
		} else {
			resultado = descargarArchivoFTPClient(fTPVO, usuario);
		}
		// fin
		return resultado;
	}

	public static String eliminarArchivo(FTPVO fTPVO, String usuario) {
		String resultado = "";
		// inicio
		if (ProtocoloType.SFTP.getKey().equals(fTPVO.getProtocolo())) {
			resultado = eliminarArchivoSFTPClient(fTPVO, usuario);
		} else {
			resultado = eliminarArchivoFTPClient(fTPVO, usuario);
		}
		// fin
		return resultado;
	}

	public static String descargarArchivoSFTPClient(FTPVO fTPVO, String usuario) {
		String resultado = "";
		boolean success = false;
		String rutaArchivoLocal = fTPVO.getRutaLocal();
		fTPVO.setRutaLocal(rutaArchivoLocal);
		File downloadFile1 = new File(rutaArchivoLocal);
		if (!downloadFile1.exists()) {
			downloadFile1.mkdirs();
		}
		SftpClient sftpClient = new SftpClient(fTPVO.getServer(), fTPVO.getPort(), fTPVO.getUsuario(),
				fTPVO.getClave());
		int reply = -1000;
		try {
			String resultConexion = sftpClient.connect();
			boolean conectoFTP = true;
			// Verificar conexión con el servidor.
			conectoFTP = resultConexion.equals("");
			if (conectoFTP) {
				// APPROACH #1: using retrieveFile(String, OutputStream)
				String remoteFile1 = fTPVO.getRutaFTP() + fTPVO.getNombreArchivoFTP();
				try {
					log.debug("descargando archivo " + (rutaArchivoLocal + fTPVO.getNombreArchivoLocal()) + " desde "
							+ (fTPVO.getRutaFTP() + fTPVO.getNombreArchivoFTP()));
					String successVal = sftpClient.retrieveFile(remoteFile1,
							rutaArchivoLocal + fTPVO.getNombreArchivoLocal());
					success = successVal.equals("");
					if (!success) {
						resultado = $_DESCARGAR_ERROR_EN + remoteFile1 + " ==> No se pudo descargar ==> error : "
								+ successVal;
					}
					try {
						if (success && fTPVO.isEliminarArchivoTemp()) {
							sftpClient.deleteFile(remoteFile1);
						}
					} catch (Exception e) {
						log.error("descargarArchivo.deleteFile-->No se pudo eliminar --> " + remoteFile1
								+ " ==> reply :" + reply);
					}

				} catch (Exception e) {
					resultado = $_DESCARGAR_ERROR_EN + remoteFile1 + " ==> " + e.getMessage() + "  ==> reply :" + reply;
					log.error("descargarArchivo.descargar-->No se pudo descargar --> " + remoteFile1);
				}
			} else {
				resultado = "${CONEXION}No se pudo conectar al SFTP ==> " + fTPVO.getServer() + " " + reply;
				log.error("descargarArchivo-->No se pudo conectar al SFTP --> " + fTPVO.getServer() + "  ==> reply :"
						+ reply);
			}
		} catch (Exception ex) {
			resultado = "${ERROR}Ocurrio un error inesperado descargar archivo del SFTP ==> " + ex.getMessage()
					+ " ==> reply :" + reply;
			log.error("descargarArchivoSFTPClient", ex);
		} finally {
			try {
				if (sftpClient != null) {
					sftpClient.disconnect();
				}
			} catch (Exception ex) {
				log.error("descargarArchivoSFTPClient", ex);
			}
		}
		return resultado;
	}

	public static String eliminarArchivoSFTPClient(FTPVO fTPVO, String usuario) {
		String resultado = "";
		String rutaArchivoLocal = fTPVO.getRutaLocal();
		fTPVO.setRutaLocal(rutaArchivoLocal);
		File downloadFile1 = new File(rutaArchivoLocal);
		if (!downloadFile1.exists()) {
			downloadFile1.mkdirs();
		}
		SftpClient sftpClient = new SftpClient(fTPVO.getServer(), fTPVO.getPort(), fTPVO.getUsuario(),
				fTPVO.getClave());
		int reply = -1000;
		try {
			String resultConexion = sftpClient.connect();
			boolean conectoFTP = true;
			// Verificar conexión con el servidor.
			conectoFTP = resultConexion.equals("");
			if (conectoFTP) {
				// APPROACH #1: using retrieveFile(String, OutputStream)
				String remoteFile1 = fTPVO.getRutaFTP() + fTPVO.getNombreArchivoFTP();
				try {
					sftpClient.deleteFile(remoteFile1);
				} catch (Exception e) {
					log.error("descargarArchivo.deleteFile-->No se pudo eliminar --> " + remoteFile1 + " ==> reply :"
							+ reply);
					resultado = "${ERROR}No se pudo eliminar " + remoteFile1 + " ==> " + e.getMessage()
							+ "  ==> reply :" + reply;
				}
			} else {
				resultado = "${CONEXION}No se pudo conectar al SFTP ==> " + fTPVO.getServer() + " " + reply;
				log.error("descargarArchivo-->No se pudo conectar al SFTP --> " + fTPVO.getServer() + "  ==> reply :"
						+ reply);
			}
		} catch (Exception ex) {
			resultado = "${ERROR}Ocurrio un error inesperado descargar archivo del SFTP ==> " + ex.getMessage()
					+ " ==> reply :" + reply;
			log.error("eliminarArchivoSFTPClient", ex);
		} finally {
			try {
				if (sftpClient != null) {
					sftpClient.disconnect();
				}
			} catch (Exception ex) {
				log.error("eliminarArchivoSFTPClient", ex);
			}
		}
		return resultado;
	}

	public static String descargarArchivoFTPClient(FTPVO fTPVO, String usuario) {
		String resultado = "";
		boolean success = false;
		String rutaArchivoLocal = fTPVO.getRutaLocal();
		File downloadFile1 = new File(rutaArchivoLocal);
		if (!downloadFile1.exists()) {
			downloadFile1.mkdirs();
		}
		FTPClient ftpClient = new FTPClient();
		int reply = -1000;
		try {
			// Inicio BuildSoft Mejora Orquestador flujo 11/09/2019
			int timeout = 600 * 1000;// 10 min
			if (ConfiguracionCacheUtil.getInstance().containsKeyPwrConfUtil("configuracion.timeout.ftp.milisegundos")) {
				timeout = ConfiguracionCacheUtil.getInstance()
						.getPwrConfUtilInt("configuracion.timeout.ftp.milisegundos");
			}
			ftpClient.setConnectTimeout(timeout);
			ftpClient.setDefaultTimeout(timeout);
			ftpClient.connect(fTPVO.getServer(), fTPVO.getPort());
			ftpClient.login(fTPVO.getUsuario(), fTPVO.getClave());
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			boolean conectoFTP = true;
			// Verificar conexión con el servidor.
			reply = ftpClient.getReplyCode();
			conectoFTP = FTPReply.isPositiveCompletion(reply);
			if (conectoFTP) {
				// APPROACH #1: using retrieveFile(String, OutputStream)
				String remoteFile1 = fTPVO.getRutaFTP() + fTPVO.getNombreArchivoFTP();
				try {
					OutputStream outputStream1 = new FileOutputStream(rutaArchivoLocal + fTPVO.getNombreArchivoLocal());
					success = ftpClient.retrieveFile(remoteFile1, outputStream1);
					outputStream1.close();
					if (!success) {
						resultado = $_DESCARGAR_ERROR_EN + remoteFile1 + " ==> No se pudo descargar ==> reply : "
								+ reply;
					}
					try {
						if (success && fTPVO.isEliminarArchivoTemp()) {
							ftpClient.deleteFile(remoteFile1);
						}
					} catch (Exception e) {
						log.error("descargarArchivo.deleteFile-->No se pudo eliminar --> " + remoteFile1
								+ " ==> reply :" + reply);
					}

				} catch (Exception e) {
					resultado = $_DESCARGAR_ERROR_EN + remoteFile1 + " ==> " + e.getMessage() + "  ==> reply :" + reply;
					log.error("descargarArchivo.descargar-->No se pudo descargar --> " + remoteFile1);
				}
			} else {
				resultado = CONEXION_NO_SE_PUDO_CONECTAR_AL_FTP + fTPVO.getServer() + " " + reply;
				log.error("descargarArchivo-->No se pudo conectar al FTP --> " + fTPVO.getServer() + "  ==> reply :"
						+ reply);
			}
		} catch (Exception ex) {
			resultado = ERROR_OCURRIO_UN_ERROR_INESPERADO_DESCARGAR_ARCHIVO_DEL_FTP + ex.getMessage() + " ==> reply :"
					+ reply;
			log.error("descargarArchivoFTPClient", ex);
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				log.error("descargarArchivoFTPClient", ex);
			}
		}
		return resultado;
	}

	public static String eliminarArchivoFTPClient(FTPVO fTPVO, String usuario) {
		String resultado = "";
		String rutaArchivoLocal = fTPVO.getRutaLocal();
		File downloadFile1 = new File(rutaArchivoLocal);
		if (!downloadFile1.exists()) {
			downloadFile1.mkdirs();
		}
		FTPClient ftpClient = new FTPClient();
		int reply = -1000;
		try {
			// Inicio BuildSoft Mejora Orquestador flujo 11/09/2019
			int timeout = 600 * 1000;// 10 min
			if (ConfiguracionCacheUtil.getInstance().containsKeyPwrConfUtil("configuracion.timeout.ftp.milisegundos")) {
				timeout = ConfiguracionCacheUtil.getInstance()
						.getPwrConfUtilInt("configuracion.timeout.ftp.milisegundos");
			}
			ftpClient.setConnectTimeout(timeout);
			ftpClient.setDefaultTimeout(timeout);
			ftpClient.connect(fTPVO.getServer(), fTPVO.getPort());
			ftpClient.login(fTPVO.getUsuario(), fTPVO.getClave());
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			boolean conectoFTP = true;
			// Verificar conexión con el servidor.
			reply = ftpClient.getReplyCode();
			conectoFTP = FTPReply.isPositiveCompletion(reply);
			if (conectoFTP) {
				// APPROACH #1: using retrieveFile(String, OutputStream)
				String remoteFile1 = fTPVO.getRutaFTP() + fTPVO.getNombreArchivoFTP();
				try {
					ftpClient.deleteFile(remoteFile1);
				} catch (Exception e) {
					log.error("eliminarArchivo.deleteFile-->No se pudo eliminar --> " + remoteFile1 + " ==> reply :"
							+ reply);
					resultado = "${ERROR}No se pudo eliminar  " + remoteFile1 + " ==> " + e.getMessage()
							+ "  ==> reply :" + reply;
				}
			} else {
				resultado = CONEXION_NO_SE_PUDO_CONECTAR_AL_FTP + fTPVO.getServer() + " " + reply;
				log.error("descargarArchivo-->No se pudo conectar al FTP --> " + fTPVO.getServer() + "  ==> reply :"
						+ reply);
			}
		} catch (Exception ex) {
			resultado = ERROR_OCURRIO_UN_ERROR_INESPERADO_DESCARGAR_ARCHIVO_DEL_FTP + ex.getMessage() + " ==> reply :"
					+ reply;
			log.error("eliminarArchivoFTPClient", ex);
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				log.error("eliminarArchivoFTPClient", ex);
			}
		}
		return resultado;
	}

	/**
	 * Remover archivo fp.
	 *
	 * @param fTPVO the f tpvo
	 * @return the string
	 */
	public static String removerArchivoFPT(FTPVO fTPVO) {
		String resultado = "";
		FTPClient ftpClient = new FTPClient();
		int reply = -1000;
		try {
			// Inicio BuildSoft Mejora Orquestador flujo 11/09/2019
			int timeout = 600 * 1000;// 10 min
			if (ConfiguracionCacheUtil.getInstance().containsKeyPwrConfUtil("configuracion.timeout.ftp.milisegundos")) {
				timeout = ConfiguracionCacheUtil.getInstance()
						.getPwrConfUtilInt("configuracion.timeout.ftp.milisegundos");
			}
			ftpClient.setConnectTimeout(timeout);
			ftpClient.setDefaultTimeout(timeout);
			// Fin BuildSoft Mejora Orquestador flujo 11/09/2019
			ftpClient.connect(fTPVO.getServer(), fTPVO.getPort());
			// ftpClient.connect(fTPVO.getServer());
			ftpClient.login(fTPVO.getUsuario(), fTPVO.getClave());
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			boolean conectoFTP = true;
			// Verificar conexión con el servidor.
			reply = ftpClient.getReplyCode();
			conectoFTP = FTPReply.isPositiveCompletion(reply);
			if (conectoFTP) {
				// APPROACH #1: using retrieveFile(String, OutputStream)
				String remoteFile1 = fTPVO.getRutaFTP() + fTPVO.getNombreArchivoFTP();
				try {
					try {
						if (fTPVO.isEliminarArchivoTemp()) {
							ftpClient.deleteFile(remoteFile1);
						}
					} catch (Exception e) {
						log.error("descargarArchivo.deleteFile-->No se pudo eliminar --> " + remoteFile1
								+ " ==> reply :" + reply);
					}

				} catch (Exception e) {
					resultado = $_DESCARGAR_ERROR_EN + remoteFile1 + " ==> " + e.getMessage() + "  ==> reply :" + reply;
					log.error("descargarArchivo.descargar-->No se pudo descargar --> " + remoteFile1);
				}
			} else {
				resultado = CONEXION_NO_SE_PUDO_CONECTAR_AL_FTP + fTPVO.getServer() + " " + reply;
				log.error("descargarArchivo-->No se pudo conectar al FTP --> " + fTPVO.getServer() + "  ==> reply :"
						+ reply);
			}
		} catch (Exception ex) {
			resultado = ERROR_OCURRIO_UN_ERROR_INESPERADO_DESCARGAR_ARCHIVO_DEL_FTP + ex.getMessage() + " ==> reply :"
					+ reply;
			log.error("removerArchivoFPT", ex);
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				log.error("removerArchivoFPT", ex);
			}
		}
		return resultado;
	}

	/**
	 * Creacion carpeta.
	 *
	 * @param fTPVO the f tpvo
	 * @return the string
	 */
	public static String creacionCarpeta(FTPVO fTPVO) {
		String resultado = "";
		if (ProtocoloType.SFTP.getKey().equals(fTPVO.getProtocolo())) {
			resultado = creacionCarpetaSFTPClient(fTPVO);
		} else {
			resultado = creacionCarpetaFTP(fTPVO);
		}
		return resultado;
	}

	public static String creacionCarpetaSFTPClient(FTPVO fTPVO) {
		String resultado = "";
		SftpClient sftpClient = new SftpClient(fTPVO.getServer(), fTPVO.getPort(), fTPVO.getUsuario(),
				fTPVO.getClave());
		boolean success = false;
		try {
			String resultConexion = sftpClient.connect();
			boolean conectoFTP = true;
			// Verificar conexión con el servidor.
			conectoFTP = resultConexion.equals("");
			if (conectoFTP) {
				// Verificar si se cambia de direcotirio de trabajo
				sftpClient.changeWorkingDirectory(fTPVO.getRutaFTP());// Cambiar directorio de trabajo
				// Activar que se envie cualquier tipo de archivo
				sftpClient.makeDirectory(fTPVO.getRutaFTP());
				if (!success) {
					resultado = "${SUBIR}Error ==> No se pudo crear la carpeta " + fTPVO.getRutaFTP() + " ==> error : ";
				}

			} else {
				log.error("No se pudo conectar al SFTP --> " + fTPVO.getServer());
				resultado = "${SUBIR}Error  ==> No se pudo subir";
			}
		} catch (Exception e) {
			log.error("creacionCarpetaSFTPClient", e);
			resultado = SUBIR_ERROR_NO_SE_PUDO_SUBIR_ERROR + e.getMessage();
		} finally {
			try {
				if (sftpClient != null) {
					sftpClient.disconnect();
				}
			} catch (Exception ex) {
				log.error("creacionCarpetaSFTPClient", ex);
			}
		}
		return resultado;

	}

	public static String creacionCarpetaFTP(FTPVO fTPVO) {
		String resultado = "";
		// FTPHTTPClient
		FTPClient ftpClient = new FTPClient();
		try {
			// Inicio BuildSoft Mejora Orquestador flujo 11/09/2019
			int timeout = 600 * 1000;// 10 min
			if (ConfiguracionCacheUtil.getInstance().containsKeyPwrConfUtil("configuracion.timeout.ftp.milisegundos")) {
				timeout = ConfiguracionCacheUtil.getInstance()
						.getPwrConfUtilInt("configuracion.timeout.ftp.milisegundos");
			}
			ftpClient.setConnectTimeout(timeout);
			ftpClient.setDefaultTimeout(timeout);
			// Fin BuildSoft Mejora Orquestador flujo 11/09/2019
			ftpClient.connect(fTPVO.getServer(), fTPVO.getPort());
			ftpClient.login(fTPVO.getUsuario(), fTPVO.getClave());
			// Verificar conexión con el servidor.
			int reply = ftpClient.getReplyCode();
			if (FTPReply.isPositiveCompletion(reply)) {
				// Verificar si se cambia de direcotirio de trabajo
				ftpClient.changeWorkingDirectory(fTPVO.getRutaFTP());// Cambiar directorio de trabajo
				// Activar que se envie cualquier tipo de archivo
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				String dirToCreate = fTPVO.getRutaFTP();
				boolean success = ftpClient.makeDirectory(dirToCreate);
				showServerReply(ftpClient);
				if (!success) {
					resultado = "${SUBIR} Error  ==> No se pudo crear la carpetar ==> reply";
				}
			} else {
				log.error("No se pudo conectar al FTP --> " + fTPVO.getServer());
				resultado = "${SUBIR}Error  ==> No se pudo subir ==> reply : " + reply;
			}
		} catch (Exception e) {
			log.error("creacionCarpetaFTP", e);
			resultado = SUBIR_ERROR_NO_SE_PUDO_SUBIR_ERROR + e.getMessage();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				log.error("creacionCarpetaFTP", ex);
			}
		}
		return resultado;
	}

	/**
	 * Show server reply.
	 *
	 * @param ftpClient the ftp client
	 */
	private static void showServerReply(FTPClient ftpClient) {
		String[] replies = ftpClient.getReplyStrings();
		if (replies != null && replies.length > 0) {
			for (var aReply : replies) {
				System.out.println("SERVER: " + aReply);
			}
		}
	}
}
