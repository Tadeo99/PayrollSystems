package pe.buildsoft.erp.core.domain.servicios.hora.reporte;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import pe.buildsoft.erp.core.domain.entidades.rrhh.hora.vo.RegistroHoraVO;
import pe.buildsoft.erp.core.domain.interfaces.repositories.hora.ReporteCentroCostoRegistroHoraDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.hora.ReporteRegistroHoraDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.repositories.hora.ReporteRequerimientoRegistroHoraDaoLocal;
import pe.buildsoft.erp.core.domain.interfaces.servicios.hora.reporte.ReporteRegistroHoraServiceLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class ReporteRegistroHoraServiceImpl implements ReporteRegistroHoraServiceLocal {

	/** El servicio requerimiento personal dao impl. */
	@Inject
	private ReporteRegistroHoraDaoLocal daoRegistroHora;

	@Inject
	private ReporteCentroCostoRegistroHoraDaoLocal daoCentroCosto;

	@Inject
	private ReporteRequerimientoRegistroHoraDaoLocal daoRequerimiento;

	@Override
	public List<RegistroHoraVO> obtenerRegistroHora(BaseSearch filtro, String idPersonal, String idPeriodo) {
		// paso 1
		List<RegistroHoraVO> resultado = daoRegistroHora.listar(filtro, idPersonal, idPeriodo);
		List<String> listaIdPersonal = new ArrayList<>();
		List<String> listaIdCentroCosto = new ArrayList<>();
		for (var obj : resultado) {
			if (!listaIdPersonal.contains(obj.getId())) {
				listaIdPersonal.add(obj.getId());
			}
		}

		// paso 2
		Map<String, List<RegistroHoraVO>> listaCentroCostoMap = daoCentroCosto.listar(listaIdPersonal, idPeriodo);
		for (var map : listaCentroCostoMap.entrySet()) {
			for (var obj : map.getValue()) {
				if (!listaIdCentroCosto.contains(obj.getIdCentroCosto())) {
					listaIdCentroCosto.add(obj.getIdCentroCosto());
				}
			}
		}

		// paso 4 obtenerReporteRequerimientoRegistroHora
		Map<String, List<RegistroHoraVO>> listaRequerimientoMap = daoRequerimiento.listar(listaIdPersonal,
				listaIdCentroCosto, idPeriodo);

		for (var obj : resultado) {
			String key = obj.getId();
			// paso 3 completarResultado
			if (listaCentroCostoMap.containsKey(key)) {
				obj.setListaDetalle(listaCentroCostoMap.get(key));
				for (var objCentroCosto : obj.getListaDetalle()) {
					String keyCentroCosto = key + objCentroCosto.getIdCentroCosto();
					if (listaRequerimientoMap.containsKey(keyCentroCosto)) {
						objCentroCosto.setListaDetalle(listaRequerimientoMap.get(keyCentroCosto));
					}
				}
			}
		}
		return resultado;
	}

	@Override
	public int contarReporteRegistroHora(BaseSearch filtro, String idPersonal, String idPeriodo) {
		return daoRegistroHora.contar(filtro, idPersonal, idPeriodo);
	}

}
