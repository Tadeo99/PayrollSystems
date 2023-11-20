package pe.buildsoft.erp.core.domain.drools;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptosTipoPlanilla;
import pe.buildsoft.erp.core.domain.interfaces.servicios.rrhh.planilla.proceso.PlanillaProcesoServiceLocal;
import pe.buildsoft.erp.core.domain.util.ParametroReglaUtil;

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
public class ReglaCachePlanillaUtil implements IReglaCachePlanillaUtil {

	/** La log. */
	private static Logger log = LoggerFactory.getLogger(ReglaCachePlanillaUtil.class);

	/** La configurador trama service local. */
	@Inject
	private PlanillaProcesoServiceLocal servicio;
	
	@Inject
	private IReglaCacheUtil reglaCache;

	@PostConstruct
	public void initialize() {
		init();
	}

	/**
	 * Inits the.
	 *
	 * @return the string
	 */
	public synchronized String init() {
		return cargarCache();
	}
	
	@Override
	public String cargarCache() {
		String resultado = null;
		try {
			if (servicio != null) {
				/*
				 * List<ConceptoPdtDTO> listaFormulaConceptoPdt =
				 * servicio.obtenerFormulaConceptoPdt(); if (listaFormulaConceptoPdt == null) {
				 * listaFormulaConceptoPdt = new ArrayList<>(); } for (ConceptoPdtDTO obj :
				 * listaFormulaConceptoPdt) { parsearRegla(obj.getFormula(),
				 * ParametroReglaUtil.ACRONIMO_REGLA_CONF_FORMULA_CONCPETO +
				 * obj.getIdConceptoPdt()); }
				 */
				List<ConceptosTipoPlanilla> listaFormulaConceptosTipoPlanilla = servicio
						.obtenerFormulaConceptosTipoPlanilla();
				if (listaFormulaConceptosTipoPlanilla == null) {
					listaFormulaConceptosTipoPlanilla = new ArrayList<>();
				}
				for (var obj : listaFormulaConceptosTipoPlanilla) {
					reglaCache.parsearRegla(obj.getFormula().trim(),
							ParametroReglaUtil.ACRONIMO_REGLA_CONF_FORMULA_CONCPETO_TIPO_PLANILLA
									+ obj.getIdConceptosTipoPlanilla());
				}
			}
		} catch (Exception e) {
			log.error("init", e);
			resultado = e.getMessage();
		}
		return resultado;
	}

}