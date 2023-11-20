package pe.buildsoft.erp.core.infra.data.repositories.hora;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.rrhh.hora.vo.RegistroHoraVO;
import pe.buildsoft.erp.core.domain.interfaces.repositories.hora.ReporteCentroCostoRegistroHoraDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericHoraDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class ReporteCentroCostoRegistroHoraDaoImpl extends GenericHoraDAOImpl<String, RegistroHoraVO>
		implements ReporteCentroCostoRegistroHoraDaoLocal {
	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.ejb.dao.seguridad.local.requerimientoDaoLocal#
	 * listarrequerimiento(pe.com.builderp.core.model.jpa.seguridad.requerimiento)
	 */
	@Override
	public Map<String, List<RegistroHoraVO>> listar(List<String> listaIdPersonal, String idPeriodo) {
		var query = generarQuery(listaIdPersonal, idPeriodo);
		return parsearRegistroHoraVO(query.getResultList());
	}

	/**
	 * Generar query lista requerimiento.
	 *
	 * @param requerimiento el requerimiento
	 * @param esContador    el es contador
	 * @return the query
	 */
	private Query generarQuery(List<String> listaIdPersonal, String idPeriodo) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(
				" select rh.idpersonal,c.idcentrocosto,c.nombre,sum(dt.horasimputadas) as total from plani.registrohoradet dt  ");
		jpaql.append(" LEFT JOIN plani.requerimiento r ON dt.idrequerimiento=r.idrequerimiento ");
		jpaql.append(" LEFT JOIN escal.centrocosto c ON r.idcentrocosto=c.idcentrocosto ");
		jpaql.append(" LEFT JOIN plani.registrohora rh ON rh.idregistrohora=dt.idregistrohora where 1=1 ");
		if (idPeriodo != null) {
			jpaql.append(" and rh.idperiodo = :idPeriodo ");
			parametros.put("idPeriodo", idPeriodo);
		}
		if (!CollectionUtil.isEmpty(listaIdPersonal)) {
			jpaql.append(" and rh.idpersonal IN (:listaIdPersonal) ");
			parametros.put("listaIdPersonal", listaIdPersonal);
		}
		jpaql.append(" group by rh.idpersonal,c.idcentrocosto,c.nombre ");

		return createNativeQuery(jpaql.toString(), parametros);
	}

	private Map<String, List<RegistroHoraVO>> parsearRegistroHoraVO(List<Object[]> resultadoTemp) {
		Map<String, List<RegistroHoraVO>> resultado = new HashMap<>();
		for (var objects : resultadoTemp) {
			String key = objects[0] + "";
			RegistroHoraVO obj = new RegistroHoraVO();
			obj.setId(objects[0] + "");
			obj.setIdCentroCosto(objects[1] + "");
			obj.setNombre(objects[2] + "");
			obj.setTotal((BigDecimal) objects[3]);
			if (!resultado.containsKey(key)) {
				List<RegistroHoraVO> value = new ArrayList<>();
				value.add(obj);
				resultado.put(key, value);
			} else {
				List<RegistroHoraVO> value = resultado.get(key);
				value.add(obj);
			}
		}
		return resultado;
	}
}
