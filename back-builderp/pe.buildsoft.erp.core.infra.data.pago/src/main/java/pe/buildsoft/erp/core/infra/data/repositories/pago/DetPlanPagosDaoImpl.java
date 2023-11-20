package pe.buildsoft.erp.core.infra.data.repositories.pago;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.pago.DetPlanPagos;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.DetPlanPagosDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPagoDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class DetPlanPagosDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:33 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class DetPlanPagosDaoImpl extends GenericPagoDAOImpl<String, DetPlanPagos> implements DetPlanPagosDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.pago.dao.local.DetPlanPagosDaoLocal#
	 * listarDetPlanPagos(pe.com.builderp.core.service.pago.model.jpa.DetPlanPagos)
	 */
	@Override
	public List<DetPlanPagos> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		return query.getResultList();
	}

	/**
	 * Generar query lista DetPlanPagos.
	 *
	 * @param DetPlanPagos el detPlanPagos
	 * @param esContador   el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idDetPlanPagos) from DetPlanPagos o where 1=1 ");
		} else {
			jpaql.append(" select o from DetPlanPagos o where 1=1 ");
		}

		if (!StringUtil.isNullOrEmpty(filtro.getId())) {// comodin
			jpaql.append(" and o.planPagos.idPlanPagos = :idPlanPagos ");
			parametros.put("idPlanPagos", filtro.getId());
		}

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.cuotaConcepto.catalogoCuenta.clasificacion.descripcion) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(
					" ORDER BY o.cuotaConcepto.itemByNivel.nombre,o.cuotaConcepto.catalogoCuenta.cuenta, o.nroCuota   ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.pago.dao.local.DetPlanPagosDaoLocal#contarListar
	 * {entity.getClassName()}(pe.com.builderp.core.service.pago.model.jpa.
	 * DetPlanPagos)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	@Override
	public List<DetPlanPagos> listar(Long idAnho, Long idPeriodo, String idAlumno, boolean flagFaltaMontoResta) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();

		jpaql.append("from DetPlanPagos o left join fetch o.cuotaConcepto cc  ");
		jpaql.append(" left join fetch cc.anhio   left join fetch cc.catalogoCuenta cat  ");
		jpaql.append(" left join fetch cat.clasificacion left join fetch o.planPagos.anhio ");

		if (StringUtil.isNotNullOrBlank(idAlumno)) {
			jpaql.append(" where o.planPagos.alumno.idAlumno =:idAlumno ");
			parametros.put("idAlumno", idAlumno);
		}
		if (StringUtil.isNotNullOrBlank(idAnho)) {
			jpaql.append(" and o.planPagos.anhio.idAnhio = :idSemestre ");
			parametros.put("idSemestre", idAnho);
		}
		if (StringUtil.isNullOrEmptyNumeric(idPeriodo)) {
			jpaql.append(" and o.planPagos.periodo.idPeriodo = :idPeriodo ");
			parametros.put("idPeriodo", idPeriodo);
		}
		if (flagFaltaMontoResta) {
			jpaql.append(" and (montoResta is null or montoResta > 0) ");
		}
		jpaql.append(" order by  o.planPagos.anhio.idAnhio ");
		var query = this.createQuery(jpaql.toString(), parametros);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.pago.dao.local.DetPlanPagosDaoLocal#
	 * generarIdDetPlanPagos()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public DetPlanPagos find(DetPlanPagos filtro) {
		DetPlanPagos resultado = new DetPlanPagos();
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append(
				" from DetPlanPagos o left join fetch o.cuotaConcepto cc left join fetch cc.catalogoCuenta cat  where 1=1 ");
		if (!StringUtil.isNullOrEmpty(filtro.getCuotaConcepto().getIdCuotaConcepto())) {
			ejecutarQuery = true;
			jpaql.append(" and o.cuotaConcepto.idCuotaConcepto =:idCuotaConcepto");
			parametros.put("idCuotaConcepto", filtro.getCuotaConcepto().getIdCuotaConcepto());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getPlanPagos().getIdAnhio())) {
			ejecutarQuery = true;
			jpaql.append(" and o.planPagos.idAnhio =:idAnhio");
			parametros.put("idAnhio", filtro.getPlanPagos().getIdAnhio());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getPlanPagos().getIdPeriodo())) {
			ejecutarQuery = true;
			jpaql.append(" and o.planPagos.idPeriodo =:idPeriodo");
			parametros.put("idPeriodo", filtro.getPlanPagos().getIdPeriodo());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getPlanPagos().getIdAlumno())) {
			ejecutarQuery = true;
			jpaql.append(" and o.planPagos.idAlumno =:idAlumno");
			parametros.put("idAlumno", filtro.getPlanPagos().getIdAlumno());
		}
		if (ejecutarQuery) {
			var query = createQuery(jpaql.toString(), parametros);
			List<DetPlanPagos> listaObj = query.getResultList();
			if (listaObj != null && !listaObj.isEmpty()) {
				resultado = listaObj.get(0);
			}
		}
		return resultado;
	}
}