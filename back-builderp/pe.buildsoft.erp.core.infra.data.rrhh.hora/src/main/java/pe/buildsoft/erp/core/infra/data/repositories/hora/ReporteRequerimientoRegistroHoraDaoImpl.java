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
import pe.buildsoft.erp.core.domain.interfaces.repositories.hora.ReporteRequerimientoRegistroHoraDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericHoraDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.CollectionUtil;

/**
 * La Class PeriodoDaoImpl.
 * <ul>
 * <li>Copyright 2021 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:07:25 COT 2022
 * @since BuildErp 1.0
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class ReporteRequerimientoRegistroHoraDaoImpl extends GenericHoraDAOImpl<String, RegistroHoraVO>
		implements ReporteRequerimientoRegistroHoraDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.ejb.dao.seguridad.local.requerimientoDaoLocal#
	 * listarrequerimiento(pe.com.builderp.core.model.jpa.seguridad.requerimiento)
	 */
	@Override
	public Map<String, List<RegistroHoraVO>> listar(List<String> listaIdPersonal, List<String> listaIdCentroCosto,
			String idPeriodo) {
		var query = generarQuery(listaIdPersonal, listaIdCentroCosto, idPeriodo);
		return parsearRegistroHoraVO(query.getResultList());
	}

	/**
	 * Generar query lista requerimiento.
	 *
	 * @param requerimiento el requerimiento
	 * @param esContador    el es contador
	 * @return the query
	 */
	private Query generarQuery(List<String> listaIdPersonal, List<String> listaIdCentroCosto, String idPeriodo) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(
				" select rh.idpersonal,r.idcentrocosto, r.idrequerimiento,r.nombre,sum(dt.horasimputadas) as total from plani.registrohoradet dt ");
		jpaql.append(" LEFT JOIN plani.requerimiento r ON dt.idrequerimiento=r.idrequerimiento ");
		jpaql.append(" LEFT JOIN plani.registrohora rh ON rh.idregistrohora=dt.idregistrohora where 1=1");
		if (idPeriodo != null) {
			jpaql.append(" and rh.idperiodo = :idPeriodo ");
			parametros.put("idPeriodo", idPeriodo);
		}
		if (!CollectionUtil.isEmpty(listaIdPersonal)) {
			jpaql.append(" and rh.idpersonal IN (:listaPersonal) ");
			parametros.put("listaPersonal", listaIdPersonal);
		}
		if (!CollectionUtil.isEmpty(listaIdCentroCosto)) {
			jpaql.append(" and r.idcentrocosto IN (:listaCentroCosto) ");
			parametros.put("listaCentroCosto", listaIdCentroCosto);
		}
		jpaql.append(" group by rh.idpersonal,r.idcentrocosto,r.idrequerimiento,r.nombre ");

		return createNativeQuery(jpaql.toString(), parametros);

	}

	private Map<String, List<RegistroHoraVO>> parsearRegistroHoraVO(List<Object[]> resultadoTemp) {
		Map<String, List<RegistroHoraVO>> resultado = new HashMap<>();
		for (var objects : resultadoTemp) {
			String key = objects[0] + "" + objects[1];
			RegistroHoraVO obj = new RegistroHoraVO();
			obj.setId(objects[0] + "");
			obj.setNombre(objects[3] + "");
			obj.setTotal((BigDecimal) objects[4]);
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