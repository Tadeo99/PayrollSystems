package pe.buildsoft.erp.core.domain.entidades.planilla.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptoRegimenPensionario;
import pe.buildsoft.erp.core.domain.entidades.planilla.ConceptosTipoPlanilla;
import pe.buildsoft.erp.core.domain.entidades.planilla.DetallePersonalConcepto;
import pe.buildsoft.erp.core.domain.entidades.planilla.EPSConf;
import pe.buildsoft.erp.core.domain.entidades.planilla.EPSPersonal;
import pe.buildsoft.erp.core.domain.entidades.planilla.TareoPersonal;
import pe.buildsoft.erp.core.domain.entidades.planilla.ValoresUIT;

@Getter
@Setter
public class CalculoPlanillaVO {
	private static Logger log = LoggerFactory.getLogger(CalculoPlanillaVO.class);

	protected Future<Map<String, TareoPersonal>> listarTareoFuture;
	protected Future<List<ConceptosTipoPlanilla>> listarConceptoFuture;
	protected Future<Map<String, Map<String, BigDecimal>>> conceptoTrabajorStaticoMapFuture;
	protected Future<Map<String, BigDecimal>> adelantosMapFuture;
	protected Future<Map<Long, ConceptoRegimenPensionario>> conceptoRegimenPensionarioMapFuture;
	protected Future<Map<String, EPSPersonal>> epsPersonalMapFuture;// TODO:REVISAR EN EL NUEVO CALCULO
	protected Future<Map<Long, EPSConf>> epsConfMapFuture;// TODO:REVISAR EN EL NUEVO CALCULO

	protected Future<Map<Long, Integer>> feriadoMapFuture;

	protected Future<List<Object>> listaParametrolMapFuture;
	protected Future<ValoresUIT> valoresUITFurure;
	protected Future<Map<String, Map<String, DetallePersonalConcepto>>> detConceptoMapFuture;

	protected Future<Map<String, Map<String, BigDecimal>>> planillaAntMapFuture;
	protected Future<Map<String, Map<String, BigDecimal>>> renta5taAntMapFuture;

	protected Future<Map<String, Map<String, String>>> variableGrupoFormulaMapFuture;

	protected Future<Map<String, BigDecimal>> rentaEmpresaAntMapFuture;

	private Map<String, TareoPersonal> tareoPersonalMap = new HashMap<>();
	private List<ConceptosTipoPlanilla> listarConcepto = null;
	private Map<String, Map<String, BigDecimal>> conceptoTrabajorStaticoMap = new HashMap<>();
	private Map<String, BigDecimal> adelantosMap = new HashMap<>();
	private Map<Long, ConceptoRegimenPensionario> conceptoRegimenPensionarioMap = new HashMap<>();
	private Map<String, EPSPersonal> epsPersonalMap = new HashMap<>();

	private Map<Long, Integer> feriadoMap = new HashMap<>();

	private List<Object> listaParametro = new ArrayList<>();
	private ValoresUIT uit;
	private Map<String, Map<String, DetallePersonalConcepto>> detConceptoMap = new HashMap<>();

	private Map<Long, EPSConf> epsConfMap = new HashMap<>();

	private Map<String, Map<String, BigDecimal>> planillaAntMap = new HashMap<>();
	private Map<String, Map<String, BigDecimal>> renta5taAntMap = new HashMap<>();

	private Map<String, Map<String, String>> variableGrupoFormulaMap;
	protected Map<String, BigDecimal> rentaEmpresaAntMap;

	public boolean isTerminoFuture() {
		boolean future = true;
		while (future) {
			try {
				if (listarTareoFuture != null && listarTareoFuture.get() != null) {
					tareoPersonalMap = (HashMap<String, TareoPersonal>) listarTareoFuture.get();
				}
				if (listarConceptoFuture != null && listarConceptoFuture.get() != null) {
					listarConcepto = listarConceptoFuture.get();
				}
				if (conceptoTrabajorStaticoMapFuture != null && conceptoTrabajorStaticoMapFuture.get() != null) {
					conceptoTrabajorStaticoMap = (HashMap<String, Map<String, BigDecimal>>) conceptoTrabajorStaticoMapFuture
							.get();
				}
				if (adelantosMapFuture != null && adelantosMapFuture.get() != null) {
					adelantosMap = (HashMap<String, BigDecimal>) adelantosMapFuture.get();
				}
				/*
				 * if (basicoPersonalMapFuture != null && basicoPersonalMapFuture.get() != null)
				 * { basicoPersonalMap = (HashMap<String, BigDecimal>)
				 * basicoPersonalMapFuture.get(); }
				 */
				if (conceptoRegimenPensionarioMapFuture != null && conceptoRegimenPensionarioMapFuture.get() != null) {
					conceptoRegimenPensionarioMap = (HashMap<Long, ConceptoRegimenPensionario>) conceptoRegimenPensionarioMapFuture
							.get();
				}
				if (epsPersonalMapFuture != null && epsPersonalMapFuture.get() != null) {
					epsPersonalMap = (HashMap<String, EPSPersonal>) epsPersonalMapFuture.get();
				}
				if (listaParametrolMapFuture != null && listaParametrolMapFuture.get() != null) {
					listaParametro = listaParametrolMapFuture.get();
				}
				if (valoresUITFurure != null && valoresUITFurure.get() != null) {
					uit = valoresUITFurure.get();
				}
				if (detConceptoMapFuture != null && detConceptoMapFuture.get() != null) {
					detConceptoMap = detConceptoMapFuture.get();
				}
				if (epsConfMapFuture != null && epsConfMapFuture.get() != null) {
					epsConfMap = epsConfMapFuture.get();
				}
				if (feriadoMapFuture != null && feriadoMapFuture.get() != null) {
					feriadoMap = feriadoMapFuture.get();
				}
				if (planillaAntMapFuture != null && planillaAntMapFuture.get() != null) {
					planillaAntMap = planillaAntMapFuture.get();
				}
				if (renta5taAntMapFuture != null && renta5taAntMapFuture.get() != null) {
					renta5taAntMap = renta5taAntMapFuture.get();
				}
				if (variableGrupoFormulaMapFuture != null && variableGrupoFormulaMapFuture.get() != null) {
					variableGrupoFormulaMap = variableGrupoFormulaMapFuture.get();
				}
				if (rentaEmpresaAntMapFuture != null && rentaEmpresaAntMapFuture.get() != null) {
					rentaEmpresaAntMap = rentaEmpresaAntMapFuture.get();
				}
				Thread.sleep(1000);
			} catch (Exception e) {
				future = false;
				log.error("while (!isDone())", e);
			}
			try {
				if ((listarConceptoFuture != null && listarConceptoFuture.get() != null)
						&& (listarTareoFuture != null && listarTareoFuture.get() != null)
						&& (conceptoTrabajorStaticoMapFuture != null && conceptoTrabajorStaticoMapFuture.get() != null)
						&& (adelantosMapFuture != null && adelantosMapFuture.get() != null)
						// && (basicoPersonalMapFuture != null && basicoPersonalMapFuture.get() != null)
						&& (conceptoRegimenPensionarioMapFuture != null
								&& conceptoRegimenPensionarioMapFuture.get() != null)
						&& (epsPersonalMapFuture != null && epsPersonalMapFuture.get() != null)
						&& (listaParametrolMapFuture != null && listaParametrolMapFuture.get() != null)
						&& (valoresUITFurure != null && valoresUITFurure.get() != null)
						&& (detConceptoMapFuture != null && detConceptoMapFuture.get() != null)
						&& (epsConfMapFuture != null && epsConfMapFuture.get() != null)
						&& (feriadoMapFuture != null && feriadoMapFuture.get() != null)
						&& (planillaAntMapFuture != null && planillaAntMapFuture.get() != null)
						&& (renta5taAntMapFuture != null && renta5taAntMapFuture.get() != null)
						&& (variableGrupoFormulaMapFuture != null && variableGrupoFormulaMapFuture.get() != null)
						&& (rentaEmpresaAntMapFuture != null && rentaEmpresaAntMapFuture.get() != null)) {
					future = false;
				}
			} catch (Exception e) {
				future = false;
				log.error("while (!isDone())", e);
			}

		}
		return future;
	}
}
