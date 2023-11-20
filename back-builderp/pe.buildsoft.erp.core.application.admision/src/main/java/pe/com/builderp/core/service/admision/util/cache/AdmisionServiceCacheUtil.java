package pe.com.builderp.core.service.admision.util.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import pe.buildsoft.erp.core.application.entidades.admision.GradoDTO;
import pe.buildsoft.erp.core.application.entidades.admision.SeccionDTO;
import pe.buildsoft.erp.core.application.interfaces.admision.AdmisionAppServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.cache.ICache;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * <ul>
 * <li>Copyright 2012 BUILD SOFT S.A.C - BS. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class SelectItemServiceCacheUtil.
 *
 * @author ndavilal.
 * @version 1.0 , 25/03/2012
 * @since SIAA 2.0
 */
@Startup
@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class AdmisionServiceCacheUtil implements IAdmisionServiceCacheUtil {

	/**
	 * Logger para el registro de errores.
	 */

	private Logger log = LoggerFactory.getLogger(AdmisionServiceCacheUtil.class);

	/** El service common impl. */
	@Inject
	private AdmisionAppServiceLocal servicioApp;

	@Inject
	private ICache cacheUtil;

	/**
	 * Instancia un nuevo select item service m bean.
	 */
	public AdmisionServiceCacheUtil() {
		super();
	}

	@PostConstruct
	public void initialize() {
		inicializar();
	}

	/**
	 * Inicializar.
	 */
	@Override
	public void inicializar() {
		cargarGrado();
	}

	public String cargarGrado() {
		String resultado = "";
		try {

			List<GradoDTO> listaData = servicioApp.listarGrado(new BaseSearch());
			for (var item : listaData) {
				cacheUtil.put("grado" + item.getIdGrado(), item);
			}
		} catch (Exception e) {
			log.error("ERROR", e);
			resultado = e.getMessage();
		}
		return resultado;
	}
	public String cargarSeccion() {
		String resultado = "";
		try {

			List<SeccionDTO> listaData = servicioApp.listarSeccion(new BaseSearch());
			for (var item : listaData) {
				cacheUtil.put("seccion" + item.getIdSeccion(), item);
			}
		} catch (Exception e) {
			log.error("ERROR", e);
			resultado = e.getMessage();
		}
		return resultado;
	}

}