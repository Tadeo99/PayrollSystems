package pe.buildsoft.erp.core.infra.data.repositories.hora;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.rrhh.hora.vo.RegistroHoraVO;
import pe.buildsoft.erp.core.domain.interfaces.repositories.hora.ReporteRegistroHoraDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericHoraDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@TransactionManagement(TransactionManagementType.BEAN)
public class ReporteRegistroHoraDaoImpl extends GenericHoraDAOImpl<String, RegistroHoraVO>
		implements ReporteRegistroHoraDaoLocal {

	@Override
	public List<RegistroHoraVO> listar(BaseSearch basePaginator, String idPersonal, String idPeriodo) {
		var query = generarQuery(basePaginator, idPersonal, idPeriodo, false);
		query.setFirstResult(basePaginator.getStartRow());
		query.setMaxResults(basePaginator.getOffSet());
		return parsearRegistroHoraVO(query.getResultList());
	}

	private Query generarQuery(BaseSearch basePaginator, String idPersonal, String idPeriodo, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(r.idRegistroHora) from plani.RegistroHora r ");
			jpaql.append(" LEFT JOIN  escal.personal p ON r.idpersonal=p.idpersonal where 1=1 and r.estado='A' ");
		} else {
			jpaql.append(" select r.idpersonal as id, concat(p.nombres,' ',p.apellidoPaterno,' ',p.apellidoMaterno) "
					+ "as nombre, sum(rd.horasimputadas) as total from plani.registrohora r ");
			jpaql.append(
					" LEFT JOIN  escal.personal p ON r.idpersonal=p.idpersonal LEFT JOIN plani.registrohoradet rd ");
			jpaql.append(" ON r.idregistrohora = rd.idregistrohora where 1=1 and r.estado='A' ");
		}
		if (!StringUtil.isNullOrEmpty(idPeriodo)) {
			jpaql.append(" and r.idperiodo = :idPeriodo ");
			parametros.put("idPeriodo", idPeriodo);
		}
		if (!StringUtil.isNullOrEmpty(idPersonal)) {
			jpaql.append(" and r.idpersonal = :idPersonal ");
			parametros.put("idPersonal", idPersonal);
		}
		if (!StringUtil.isNullOrEmpty(basePaginator.getSearch())) {
			jpaql.append(
					" and upper(p.nombres) like :search or upper(p.apellidoPaterno) like :search or upper(p.apellidoMaterno) like :search");
			parametros.put("search", "%" + basePaginator.getSearch().toUpperCase() + "%");
		}

		if (!esContador) {
			jpaql.append(" group by r.idpersonal, p.nombres,p.apellidoPaterno,p.apellidoMaterno ");
		}
		return createNativeQuery(jpaql.toString(), parametros);
	}

	private List<RegistroHoraVO> parsearRegistroHoraVO(List<Object[]> resultadoTemp) {
		List<RegistroHoraVO> resultado = new ArrayList<>();
		for (var objects : resultadoTemp) {
			RegistroHoraVO obj = new RegistroHoraVO();
			obj.setId(objects[0] + "");
			obj.setNombre(objects[1] + "");
			obj.setTotal((BigDecimal) objects[2]);
			resultado.add(obj);
		}
		return resultado;
	}

	@Override
	public int contar(BaseSearch filtro, String idPersonal, String idPeriodo) {
		var query = generarQuery(filtro, idPersonal, idPeriodo, true);
		return getContador(query);
	}

}
