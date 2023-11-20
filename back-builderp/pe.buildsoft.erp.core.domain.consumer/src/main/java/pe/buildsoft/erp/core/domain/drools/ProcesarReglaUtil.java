package pe.buildsoft.erp.core.domain.drools;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import jakarta.inject.Inject;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import org.drools.compiler.compiler.DescrBuildError;
import org.drools.compiler.compiler.RuleBuildError;
import org.drools.drl.parser.ParserError;
import org.drools.drl.parser.lang.ExpanderException;
import org.drools.ecj.EclipseCompilationProblem;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.buildsoft.erp.core.domain.entidades.vo.ErrorValidacionVO;
import pe.buildsoft.erp.core.domain.entidades.vo.RespuestaReglaNegocioVO;
import pe.buildsoft.erp.core.domain.type.CodigoAreaDroolsType;
import pe.buildsoft.erp.core.domain.type.TipoErrorDroolsType;
import pe.buildsoft.erp.core.domain.util.ParametroReglaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ArchivoUtilidades;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class ProcesarReglaUtil implements IProcesarReglaUtil {

	private static final long LINEA_INICIAL = 1L;

	private static final String UTF_8 = "UTF-8";

	/** La Constante SALTO_LINEA. */
	private static final String SALTO_LINEA = "\n";

	/** La Constante ESTRUCTURA_BASE. */
	private static final String ESTRUCTURA_BASE = "rule \"[Ingrese el nombre de la regla]\"\r\n when\r\n    cnf : ReglaVO()\r\n    [Ingrese las condiciones de la regla]\r\n then\r\n    [Ingrese aqui las consecuencias]\r\nend \r\n";

	/** La Constante END. */
	private static final String END = "end";

	/** La Constante RULE. */
	private static final String RULE = "rule";

	/** La Constante LOG. */
	private static final Logger log = LoggerFactory.getLogger(ProcesarReglaUtil.class);

	@Inject
	private IReglaCacheUtil reglaCacheUtil;

	private List<String> palabraReservadaList = new ArrayList<>();

	public ProcesarReglaUtil() {
		llenarPalabrasReservadas();
	}

	/**
	 * Procesar regla.
	 *
	 * @param listaMemoria el lista memoria
	 * @param regla        el regla
	 * @throws Exception the exception
	 */
	@Override
	public <E> void procesarRegla(List<E> listaMemoria, String regla) {
		// Cargamos la base de reglas
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = reglaCacheUtil.get("KSession" + regla);
		KieSession kieSession = kContainer.newKieSession("KSession" + regla);
		// insertamos un objeto al motor
		for (var e : listaMemoria) {
			kieSession.insert(e);
		}
		// Disparamos las reglas de Drools
		kieSession.fireAllRules();
		kieSession.dispose();

	}

	/**
	 * Procesar regla.
	 *
	 * @param memoria el memoria
	 * @param regla   el regla
	 * @throws Exception the exception
	 */
	@Override
	public <E> void procesarRegla(E memoria, String regla) {
		// Cargamos la base de reglas
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = reglaCacheUtil.get("KSession" + regla);
		// KieSession kieSession = kContainer.newKieSession(regla);
		KieSession kieSession = kContainer.newKieSession("KSession" + regla);
		// insertamos un objeto al motor
		kieSession.insert(memoria);

		// Disparamos las reglas de Drools
		kieSession.fireAllRules();

		kieSession.dispose();
	}

	public KieContainer leerRegla() {
		try {
			// Leemos el archivo de reglas (DRL)
			KieServices ks = KieServices.Factory.get();
			KieContainer kContainer = ks.getKieClasspathContainer();
			return kContainer;
		} catch (Exception e) {
			log.error("leerRegla", e);
		}
		return null;
	}

	public String leerRegla(String reglaNombreArchivo) {
		if (reglaCacheUtil.containsKeyDls(reglaNombreArchivo)) {
			return reglaCacheUtil.getDls(reglaNombreArchivo);
		}
		return "";
	}

	public Integer leerReglaCantidadLinea(String reglaNombreArchivo) {
		if (reglaCacheUtil.containsKeyDlsCantLinea(reglaNombreArchivo)) {
			return reglaCacheUtil.getDlsCantLinea(reglaNombreArchivo);
		}
		return 0;
	}

	private List<ErrorValidacionVO> procesarErrorRegla(KnowledgeBuilder kbuilder, Integer numeroLineasRegla) {
		List<ErrorValidacionVO> listaErrorReglaVO = new ArrayList<>();
		for (var error : kbuilder.getErrors()) {
			boolean esMetodoDuplicado = false;
			StringBuilder errorTecnico = new StringBuilder();
			errorTecnico.append(error.getMessage());
			ErrorValidacionVO errorReglaVO = new ErrorValidacionVO();
			StringBuilder mensajeError = new StringBuilder();
			String codigoError = error.getMessage().substring(1, 8);
			if (error instanceof DescrBuildError) {
				codigoError = CodigoAreaDroolsType.ERR_001.getValue();
			}
			if (error instanceof ExpanderException) {
				codigoError = CodigoAreaDroolsType.ERR_EXP_001.getValue();
				errorTecnico.append("");
			}
			if (listaErrorReglaVO.isEmpty() && error instanceof RuleBuildError ruleBuildError) {
				codigoError = CodigoAreaDroolsType.ERR_103.getValue();
				errorTecnico.append("\n");
				errorTecnico.append(ruleBuildError.getMessage());
				Object[] object = (Object[]) ruleBuildError.getObject();
				for (var data : object) {
					if (data instanceof EclipseCompilationProblem problema) {
						errorTecnico.append("\n");
						errorTecnico.append(problema.getMessage());
					}
				}
			}

			if (error instanceof ParserError) {
				codigoError = CodigoAreaDroolsType.ERR_102.getValue();
				if (error.getMessage().contains("Duplicate rule name")) {
					esMetodoDuplicado = true;
				}
			}

			log.error(error.getMessage()); // ESCRIBIR ERROR EN EL LOG.

			if (!StringUtil.isNullOrEmpty(codigoError)) {
				codigoError = codigoError.trim();
				if (!CodigoAreaDroolsType.getLooKupMap().containsKey(codigoError)) {
					continue;
				}
			}
			CodigoAreaDroolsType areaDroolsType = CodigoAreaDroolsType.get(codigoError);
			errorReglaVO.setTipoError(TipoErrorDroolsType.SINTAXIS.getValue());

			Integer cantidadImportacion = leerReglaCantidadLinea(ParametroReglaUtil.NOMBRE_ARCHIVO_IMPORTACIONES);
			Long lineaErrorOcurrido = Long.valueOf(error.getLines()[0]);
			Long lineaError = LINEA_INICIAL;

			if (lineaErrorOcurrido > 0) {
				lineaError = lineaErrorOcurrido - cantidadImportacion;
			} else {
				continue;
			}
			errorReglaVO.setLineaError(lineaError);
			errorReglaVO.setErrorTecnico(errorTecnico.toString());

			switch (areaDroolsType) {
			case ERR_101:
			case ERR_EXP_001:
				mensajeError.append("Error de Sintaxis no existe la palabra reservada ");
				errorReglaVO.setDescripcionError(mensajeError.toString());
				break;

			case ERR_102:
				if (esMetodoDuplicado) {
					mensajeError.append("Error ya existe un regla con ese nombre ");
				} else {
					mensajeError.append("La estructura de la regla contiene errores en la linea ");
				}
				errorReglaVO.setDescripcionError(mensajeError.toString());
				break;

			case ERR_103:
				mensajeError.append("Error de Sintaxis la regla contiene caracteres extraños ");
				errorReglaVO.setDescripcionError(mensajeError.toString());
				break;

			case ERR_104:
				mensajeError.append("Error de Sintaxis punto y coma no permitido ");
				errorReglaVO.setDescripcionError(mensajeError.toString());
				break;

			case ERR_105:
				mensajeError.append("Error de Sintaxis no se ha encontrado la entrada para ");
				errorReglaVO.setDescripcionError(mensajeError.toString());
				break;

			case ERR_107:
				mensajeError.append("La estructura de la regla contiene errores en la linea ");
				errorReglaVO.setDescripcionError(mensajeError.toString());
				break;

			case ERR_001:
				mensajeError.append("No se encontro el objeto o clase ");
				errorReglaVO.setDescripcionError(mensajeError.toString());
				break;

			default:
				mensajeError.append(
						"Ha sucesido un error desconocido ponganse en contacto con el administrador del sistema ");
				errorReglaVO.setTipoError(TipoErrorDroolsType.SISTEMA.getValue());
				errorReglaVO.setDescripcionError(mensajeError.toString());
				break;
			}
			listaErrorReglaVO.add(errorReglaVO);
		}
		return listaErrorReglaVO;
	}

	private RespuestaReglaNegocioVO leerReglaDrlCompiler(String nombreReglaDrl, Integer numeroLineasRegla) {
		RespuestaReglaNegocioVO resultado = new RespuestaReglaNegocioVO();
		List<ErrorValidacionVO> listaErrores = new ArrayList<>();
		try {
			KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
			String dlrImport = leerRegla(ParametroReglaUtil.NOMBRE_ARCHIVO_IMPORTACIONES);
			nombreReglaDrl = dlrImport + "\n" + nombreReglaDrl;
			kbuilder.add(ResourceFactory.newByteArrayResource(nombreReglaDrl.getBytes(UTF_8)), ResourceType.DRL);
			if (kbuilder.hasErrors()) {
				listaErrores.addAll(procesarErrorRegla(kbuilder, numeroLineasRegla));
			}
			if (CollectionUtil.isEmpty(listaErrores)) {
				resultado.setRespuestaValidacion(false);
			} else {
				resultado.getErrorReglaNegocioList().addAll(listaErrores);
				resultado.setRespuestaValidacion(true);
			}
		} catch (Exception e) {
			ErrorValidacionVO errorReglaNegocioVO = new ErrorValidacionVO();
			Long idError = Long.valueOf(resultado.getErrorReglaNegocioList().size() + 1);
			errorReglaNegocioVO.setIdCorrelativoError(idError);
			errorReglaNegocioVO.setLineaError(0L);
			errorReglaNegocioVO
					.setDescripcionError("Error No Controlado pongase en contacto con el administrador del sistema");
			errorReglaNegocioVO.setErrorTecnico(e.getMessage());
			resultado.getErrorReglaNegocioList().add(errorReglaNegocioVO);
			log.error("leerReglaDrlCompiler", e);
			return resultado;
		}
		CollectionUtil.ordenador(false, resultado.getErrorReglaNegocioList(), "lineaError");
		Long idError = 1L;
		for (var objError : resultado.getErrorReglaNegocioList()) {
			objError.setIdCorrelativoError(idError);
			idError++;
		}
		return resultado;
	}

	/**
	 * Validar reglas.
	 *
	 * @throws Exception the exception
	 */
	public List<ErrorValidacionVO> validarReglas(String regla) {
		List<ErrorValidacionVO> errorReglaNegocioList = new ArrayList<>();
		if (!StringUtil.isNullOrEmpty(regla)) {
			int numeroLineasRegla = ArchivoUtilidades.contarLineasTexto(regla);
			errorReglaNegocioList.addAll(contienePalabrasFueraLugar(regla));
			Long idCorrelativoActual = Long.valueOf(errorReglaNegocioList.size() + "");
			errorReglaNegocioList.addAll(contieneComillasSinCerrar(regla, idCorrelativoActual));
			idCorrelativoActual = Long.valueOf(errorReglaNegocioList.size() + "");
			errorReglaNegocioList.addAll(verificarRuleEnd(regla, idCorrelativoActual));
			if (CollectionUtil.isEmpty(errorReglaNegocioList)) {
				RespuestaReglaNegocioVO respuestaReglaNegocio = leerReglaDrlCompiler(regla, numeroLineasRegla);
				if (respuestaReglaNegocio.isRespuestaValidacion()) {
					errorReglaNegocioList.addAll(respuestaReglaNegocio.getErrorReglaNegocioList());
				}
			}
		}
		return errorReglaNegocioList;
	}

	/**
	 * Contiene palabras fuera lugar.
	 *
	 * @param regla el regla
	 * @return the list
	 */
	private List<ErrorValidacionVO> contienePalabrasFueraLugar(String regla) {
		List<ErrorValidacionVO> respuesta = new ArrayList<>();
		String tempReglaTemp = regla.toLowerCase();
		StringTokenizer reglaTemp = new StringTokenizer(tempReglaTemp);
		List<String> palabrasFuera = new ArrayList<>();
		Long idCorrelativo = 1L;
		try {

			boolean grabarCadenaRestante = true;

			while (reglaTemp.hasMoreTokens()) {
				String tempCadena = reglaTemp.nextToken();
				if (RULE.equals(tempCadena)) {
					grabarCadenaRestante = false;
				}
				if (grabarCadenaRestante && !palabrasFuera.contains(tempCadena)) {
					palabrasFuera.add(tempCadena);
				}
				if (END.equals(tempCadena)) {
					grabarCadenaRestante = true;
				}
			}

			if (!CollectionUtil.isEmpty(palabrasFuera)) {
				for (var cadena : palabrasFuera) {
					if (!palabraReservadaList.contains(cadena)) {
						List<Integer> listaTemp = ArchivoUtilidades.buscarLineaPalabra(tempReglaTemp, cadena);
						if (!CollectionUtil.isEmpty(listaTemp)) {
							for (var numeroLinea : listaTemp) {
								ErrorValidacionVO errorValidacionVO = new ErrorValidacionVO();
								errorValidacionVO.setLineaError(Long.valueOf(numeroLinea + ""));
								errorValidacionVO.setIdCorrelativoError(idCorrelativo);
								errorValidacionVO.setDescripcionError(new StringBuilder().append("La palabra ")
										.append(cadena).append(" no se encuentra dentro de una regla").toString());
								errorValidacionVO.setTipoError(TipoErrorDroolsType.SISTEMA.getValue());
								errorValidacionVO.setErrorTecnico("Error de sintaxis");
								idCorrelativo++;
								respuesta.add(errorValidacionVO);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("contienePalabrasFueraLugar", e);
		}
		return respuesta;
	}

	/**
	 * Contiene comillas sin cerrar.
	 *
	 * @param regla               el regla
	 * @param idCorrelativoActual el id correlativo actual
	 * @return the list
	 */
	private List<ErrorValidacionVO> contieneComillasSinCerrar(String regla, Long idCorrelativoActual) {
		List<ErrorValidacionVO> respuesta = new ArrayList<>();
		String tempRegla = regla.toLowerCase();
		byte[] content = tempRegla.getBytes();
		InputStream is = null;
		BufferedReader bfReader = null;
		int contador = 1;
		Long idCorrelativo = idCorrelativoActual;
		try {
			is = new ByteArrayInputStream(content);
			bfReader = new BufferedReader(new InputStreamReader(is));
			String temp = null;
			while ((temp = bfReader.readLine()) != null) {
				if (temp.contains("")) {
					int contarComillas = ArchivoUtilidades.contarCarateres(temp, '"');
					if (contarComillas % 2 > 0) {
						idCorrelativo++;
						ErrorValidacionVO errorValidacionVO = new ErrorValidacionVO();
						errorValidacionVO.setLineaError(Long.valueOf(contador + ""));
						errorValidacionVO.setIdCorrelativoError(idCorrelativo);
						errorValidacionVO.setDescripcionError("Falta cerrar comillas");
						errorValidacionVO.setTipoError(TipoErrorDroolsType.SISTEMA.getValue());
						errorValidacionVO.setErrorTecnico("Error de sintaxis");
						respuesta.add(errorValidacionVO);
					}
				}
				contador++;
			}
		} catch (IOException e) {
			log.error("contieneComillasSinCerrar.IOException", e);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception ex) {
				log.error("contieneComillasSinCerrar.Exception", ex);
			}
		}
		return respuesta;
	}

	/**
	 * Contiene comillas sin cerrar.
	 *
	 * @param regla               el regla
	 * @param idCorrelativoActual el id correlativo actual
	 * @return the list
	 */
	private List<ErrorValidacionVO> verificarRuleEnd(String regla, Long idCorrelativoActual) {
		List<ErrorValidacionVO> respuesta = new ArrayList<>();
		String tempRegla = regla.toLowerCase();
		byte[] content = tempRegla.getBytes();
		InputStream is = null;
		BufferedReader bfReader = null;
		int linea = 0;
		int lineaTemporalRule = 1;
		Long idCorrelativo = idCorrelativoActual;
		boolean buscarEnd = false;
		try {
			is = new ByteArrayInputStream(content);
			bfReader = new BufferedReader(new InputStreamReader(is));
			String temp = null;
			while ((temp = bfReader.readLine()) != null) {
				linea++;
				if (!buscarEnd) {
					StringTokenizer reglaTemp = new StringTokenizer(temp);
					while (reglaTemp.hasMoreTokens()) {
						String tempCadena = reglaTemp.nextToken();
						if (RULE.equals(tempCadena)) {
							buscarEnd = true;
							lineaTemporalRule = linea;
							continue;
						} else {
							if (END.equals(tempCadena)) {
								idCorrelativo++;
								ErrorValidacionVO errorValidacionVO = new ErrorValidacionVO();
								errorValidacionVO.setLineaError(Long.valueOf(linea + ""));
								errorValidacionVO.setIdCorrelativoError(idCorrelativo);
								errorValidacionVO.setDescripcionError("Completar la regla");
								errorValidacionVO.setTipoError(TipoErrorDroolsType.SISTEMA.getValue());
								errorValidacionVO.setErrorTecnico("Error de sintaxis");
								respuesta.add(errorValidacionVO);
								buscarEnd = false;
								break;
							}
						}
					}
					if (buscarEnd) {
						continue;
					}
				}

				if (buscarEnd) {
					StringTokenizer reglaTemp = new StringTokenizer(temp);
					while (reglaTemp.hasMoreTokens()) {
						String tempCadena = reglaTemp.nextToken();
						if (RULE.equals(tempCadena)) {
							idCorrelativo++;
							ErrorValidacionVO errorValidacionVO = new ErrorValidacionVO();
							errorValidacionVO.setLineaError(Long.valueOf(lineaTemporalRule + ""));
							errorValidacionVO.setIdCorrelativoError(idCorrelativo);
							errorValidacionVO.setDescripcionError("Completar la regla");
							errorValidacionVO.setTipoError(TipoErrorDroolsType.SISTEMA.getValue());
							errorValidacionVO.setErrorTecnico("Error de sintaxis");
							respuesta.add(errorValidacionVO);
							lineaTemporalRule = linea;
							break;
						}
						if (END.equals(tempCadena)) {
							buscarEnd = false;
						}
					}
				}
			}
		} catch (IOException e) {
			log.error("verificarRuleEnd.IOException", e);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception ex) {
				log.error("verificarRuleEnd.Exception", ex);
			}
		}

		return respuesta;
	}

	private void llenarPalabrasReservadas() {
		palabraReservadaList.add("\"");
		palabraReservadaList.add("rule");
		palabraReservadaList.add("end");
	}
}
