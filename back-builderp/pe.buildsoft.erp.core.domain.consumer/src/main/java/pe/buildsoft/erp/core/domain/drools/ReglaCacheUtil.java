package pe.buildsoft.erp.core.domain.drools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import org.drools.compiler.compiler.io.File;
import org.drools.compiler.compiler.io.memory.MemoryFileSystem;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.compiler.kie.builder.impl.KieBuilderImpl;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.buildsoft.erp.core.domain.util.ParametroReglaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ConstanteConfigUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * <ul>
 * <li>Copyright 2014 planilla. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class ReglaCacheUtil.
 *
 * @author ndavilal
 * @version 1.0, Thu Jul 31 10:21:30 COT 2017
 * @since SIAA-CORE 2.1
 */
@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class ReglaCacheUtil implements IReglaCacheUtil {

	/** La log. */
	private static Logger log = LoggerFactory.getLogger(ReglaCacheUtil.class);

	/** La Constante SEPARADOR_FILE. */
	public static final String SEPARADOR_FILE = ConstanteConfigUtil.SEPARADOR_FILE;

	/** La Constante RUTA_RECURSOS_REGLA. */
	public static final String RUTA_RECURSOS_REGLA = ConstanteConfigUtil.RUTA_RECURSOS_REGLA;

	/** El required map. */
	private Map<String, KieContainer> reglaMap = new HashMap<>();

	/** La regla dls map. */
	private Map<String, String> reglaDlsMap = new HashMap<>();

	/** La regla dls cantidad linea map. */
	private Map<String, Integer> reglaDlsCantidadLineaMap = new HashMap<>();


	@PostConstruct
	public void initialize() {
		init();
	}

	/**
	 * Actualizar regla cache.
	 *
	 * @param nombreRegla el nombre regla
	 * @param ruleBase    el rule base
	 */
	public void actualizarReglaCache(String nombreRegla, KieBase ruleBase) {

	}

	/**
	 * Inits the.
	 *
	 * @return the string
	 */
	public synchronized String init() {
		String resultado = null;
		// aqui cargar la regla
		leerRegla(ParametroReglaUtil.NOMBRE_ARCHIVO_IMPORTACIONES,
				RUTA_RECURSOS_REGLA + generarRuta("rule_base") + ParametroReglaUtil.NOMBRE_ARCHIVO_IMPORTACIONES);
		try {
			reglaMap.clear();
			reglaMap = new HashMap<>();
		} catch (Exception e) {
			log.error("init", e);
			resultado = e.getMessage();
		}
		return resultado;
	}

	/**
	 * Generar ruta.
	 *
	 * @param ruta el ruta
	 * @return the string
	 */
	public String generarRuta(String... ruta) {
		StringBuilder resultado = new StringBuilder();
		for (var carpeta : ruta) {
			resultado.append(carpeta);
			resultado.append("/");
		}
		return resultado.toString();
	}

	/**
	 * Parsear regla.
	 *
	 * @param reglaPersonalizado el regla personalizado
	 * @param nombreRegla        el nombre regla
	 */
	@Override
	public synchronized void parsearRegla(String reglaPersonalizado, String nombreRegla) {
		try {
			reglaPersonalizado = StringUtil.quitarCaracterExtranio(reglaPersonalizado, 0);
			String dslrImport = reglaDlsMap.get(ParametroReglaUtil.NOMBRE_ARCHIVO_IMPORTACIONES);
			reglaPersonalizado = dslrImport + "\n" + reglaPersonalizado;

			KieServices ks = KieServices.Factory.get();
			ReleaseId releaseId = ks.newReleaseId("org.kie.planilla.pwr", "rule-planilla", "1.0-SNAPSHOT");
			KieModuleModel kieModuleModel = obtenerBaseKieModuleModel(ks);
			kieModuleModel = obtenerBaseKieModuleModelPersonalizado(kieModuleModel, nombreRegla);
			KieFileSystem kfs = ks.newKieFileSystem().generateAndWritePomXML(releaseId)
					.write(generarRuta("src", "main", "resources", "KBase", nombreRegla, "org", "planilla", "pkg")
							+ nombreRegla + ".drl", reglaPersonalizado)
					.writeKModuleXML(kieModuleModel.toXML());
			ks.newKieBuilder(kfs).buildAll();
			InternalKieModule kieModule = (InternalKieModule) ks.getRepository().getKieModule(releaseId);
			byte[] jar = kieModule.getBytes();
			MemoryFileSystem mfs = MemoryFileSystem.readFromJar(jar);
			File file = mfs.getFile(KieBuilderImpl.getCompilationCachePath(releaseId, "kbase1"));
			file = mfs.getFile(KieBuilderImpl.getCompilationCachePath(releaseId, "kbase" + nombreRegla));
			Resource jarRes = ks.getResources().newByteArrayResource(jar);
			KieModule KieModule = ks.getRepository().addKieModule(jarRes);
			KieContainer kieContainer = ks.newKieContainer(KieModule.getReleaseId());
			// KieSession ksession = ks.newKieContainer(
			// KieModule.getReleaseId() ).newKieSession("KSession" +
			// nombreRegla);
			reglaMap.put("KSession" + nombreRegla, kieContainer);
		} catch (Exception e) {
			log.error("parsearRegla", e);
		}

	}

	/**
	 * Obtener base kie module model.
	 *
	 * @param ks el ks
	 * @return the kie module model
	 */
	private KieModuleModel obtenerBaseKieModuleModel(KieServices ks) {
		KieModuleModel kproj = ks.newKieModuleModel();
		kproj.newKieBaseModel("kbase1").setEqualsBehavior(EqualityBehaviorOption.EQUALITY)
				.setEventProcessingMode(EventProcessingOption.STREAM).addPackage("org.planilla.pkg")
				.newKieSessionModel("KSessionBase");
		return kproj;
	}

	/**
	 * Obtener base kie module model personalizado.
	 *
	 * @param kproj       el kproj
	 * @param nombreRegla el nombre regla
	 * @return the kie module model
	 */
	private static KieModuleModel obtenerBaseKieModuleModelPersonalizado(KieModuleModel kproj, String nombreRegla) {
		kproj.removeKieBaseModel("kbase" + nombreRegla);
		kproj.newKieBaseModel("kbase" + nombreRegla).setEqualsBehavior(EqualityBehaviorOption.EQUALITY)
				.setEventProcessingMode(EventProcessingOption.STREAM).addPackage("org.planilla.pkg")
				.addInclude("kbase1").newKieSessionModel("KSession" + nombreRegla);
		return kproj;
	}

	/**
	 * Leer regla concatenar.
	 *
	 * @param pathFile el path file
	 * @return the string
	 */
	public String leerReglaConcatenar(String... pathFile) {
		StringBuilder resultado = new StringBuilder();
		for (var key : pathFile) {
			if (reglaDlsMap.containsKey(key)) {
				resultado.append(reglaDlsMap.get(key) + "\n");
			}
		}
		return resultado.toString();
	}

	/**
	 * Leer regla cantidad linea.
	 *
	 * @param pathFile el path file
	 * @return the integer
	 */
	public Integer leerReglaCantidadLinea(String... pathFile) {
		Integer resultado = 0;
		for (var key : pathFile) {
			if (reglaDlsCantidadLineaMap.containsKey(key)) {
				resultado = resultado + reglaDlsCantidadLineaMap.get(key);
			}
		}
		return resultado;
	}

	/**
	 * Leer regla.
	 *
	 * @param key      el key
	 * @param pathFile el path file
	 */
	public void leerRegla(String key, String pathFile) {
		FileInputStream fis = null;
		BufferedReader bufferedReader = null;
		try {
			fis = new FileInputStream(pathFile);
			bufferedReader = new BufferedReader(new InputStreamReader(fis));
			Integer cantidadLinea = obenerCantidadLiena(bufferedReader);
			fis = new FileInputStream(pathFile);
			byte[] datosArchivo = datosArchivo = new byte[fis.available()];
			fis.read(datosArchivo);
			String regla = new String(datosArchivo);
			reglaDlsMap.put(key, regla);
			reglaDlsCantidadLineaMap.put(key, cantidadLinea);
		} catch (Exception e) {
			log.error("leerRegla", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					log.error("leerRegla.fis", e);
				}
			}
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					log.error("leerRegla.bufferedReader", e);
				}
			}
		}
	}

	/**
	 * Obener cantidad liena.
	 *
	 * @param br el br
	 * @return the integer
	 * @throws Exception the exception
	 */
	public Integer obenerCantidadLiena(BufferedReader br) {
		int resultado = 0;
		try {
			String line = "";
			while ((line = br.readLine()) != null) {
				resultado++;
			}
		} catch (Exception e) {
			log.error("obenerCantidadLiena", e);
		}
		return resultado;
	}

	/**
	 * Obtiene regla map.
	 *
	 * @return regla map
	 */
	@Override
	public KieContainer get(String key) {
		return reglaMap.get(key);
	}

	/**
	 * Obtiene regla dls map.
	 *
	 * @return regla dls map
	 */
	@Override
	public String getDls(String key) {
		return reglaDlsMap.get(key);
	}

	@Override
	public boolean containsKeyDls(String key) {
		return reglaDlsMap.containsKey(key);
	}

	/**
	 * Obtiene regla dls cantidad linea map.
	 *
	 * @return regla dls cantidad linea map
	 */
	@Override
	public Integer getDlsCantLinea(String key) {
		return reglaDlsCantidadLineaMap.get(key);
	}

	@Override
	public boolean containsKeyDlsCantLinea(String key) {
		return reglaDlsCantidadLineaMap.containsKey(key);
	}

}