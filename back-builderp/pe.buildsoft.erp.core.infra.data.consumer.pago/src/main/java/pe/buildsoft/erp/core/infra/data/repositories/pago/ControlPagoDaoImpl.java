package pe.buildsoft.erp.core.infra.data.repositories.pago;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.pago.ControlPago;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.ControlPagoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPagoBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ScriptSqlResulJDBCVO;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.SqlMapingUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class ControlPagoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class ControlPagoDaoImpl extends GenericPagoBatchDAOImpl<String, ControlPago> implements ControlPagoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.pago.dao.local.ControlPagoDaoLocal#
	 * listarControlPago(pe.com.builderp.core.service.pago.model.jpa.ControlPago)
	 */
	@Override
	public List<ControlPago> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista ControlPago.
	 *
	 * @param ControlPago el controlPago
	 * @param esContador  el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idControlPago) from ControlPago o where 1=1 ");
		} else {
			jpaql.append(" select o from ControlPago o  where 1=1  ");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdAnhio())) {
			jpaql.append(" and o.anhio.idAnhio =: idAnhio ");
			parametros.put("idAnhio", filtro.getIdAnhio());
		}
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdPeriodo())) {
			jpaql.append(" and o.periodo.idPeriodo =: idPeriodo ");
			parametros.put("idPeriodo", ObjectUtil.objectToLong(filtro.getIdPeriodo()));
		}
		if (!StringUtil.isNullOrEmpty(filtro.getIdAlumno())) {
			jpaql.append(" and o.alumno.idAlumno =: idAlumno ");
			parametros.put("idAlumno", filtro.getIdAlumno());
		}

		if (!StringUtil.isNullOrEmpty(filtro.getSearch())) {
			jpaql.append(" and upper(o.nroDoc) like :search ");
			parametros.put("search", "%" + filtro.getSearch().toUpperCase() + "%");
		}
		if (!esContador) {
			jpaql.append(" ORDER BY o.nroDoc desc");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	@Override
	public List<ControlPago> listarById(String idControlPago) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append("from ControlPago o   ");

		if (StringUtil.isNotNullOrBlank(idControlPago)) {
			jpaql.append(" where o.idControlPago =:idControlPago ");
			parametros.put("idControlPago", idControlPago);
		}
		var query = this.createQuery(jpaql.toString(), parametros);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pe.com.builderp.core.service.pago.dao.local.ControlPagoDaoLocal#contarListar{
	 * entity.getClassName()}(pe.com.builderp.core.service.pago.model.jpa.
	 * ControlPago)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.pago.dao.local.ControlPagoDaoLocal#
	 * generarIdControlPago()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

	@Override
	public List<Object[]> listaControlPagoGrupByFechas() {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append(
				"select to_date(to_char(o.fechaPago,'yyyy-MM-dd'),'yyyy-MM-dd') , count(o.fechaPago), to_char(o.fechaPago,'yyyymmdd') ,o.tipoDocSunat.idItem   from ControlPago o ");
		jpaql.append(
				" where o.envioSunat = '' and o.tipoDocSunat.idItem=3750008 and to_char(o.fechaPago,'dd/MM/yyyy')!=:fecha  ");
		jpaql.append(
				" group by to_date(to_char(o.fechaPago,'yyyy-MM-dd'),'yyyy-MM-dd'),to_char(o.fechaPago,'yyyymmdd'),o.tipoDocSunat.idItem  ");
		parametros.put("fecha",
				FechaUtil.obtenerFechaFormatoPersonalizado(FechaUtil.obtenerFechaActual(), "dd/MM/yyyy"));
		var query = createQuery(jpaql.toString(), parametros);
		return query.getResultList();
	}

	@Override
	public List<ControlPago> listaVentaExtracionF() {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append("from ControlPago o  left join fetch o.tipoDocSunat ");
		jpaql.append(
				"left join fetch o.itemByTipoMoneda left join fetch o.empresa  where o.envioSunat ='' and (o.tipoDocSunat.idItem=3750007) and to_char(o.fechaPago,'dd/MM/yyyy')!=:fecha ");
		parametros.put("fecha",
				FechaUtil.obtenerFechaFormatoPersonalizado(FechaUtil.obtenerFechaActual(), "dd/MM/yyyy"));
		var query = createQuery(jpaql.toString(), parametros);
		return query.getResultList();
	}

	@Override
	public void updateVentaExtracion() {
		var jpaql = new StringBuilder();
		jpaql.append(" UPDATE pago.ControlPago  SET envioSunat='X'  where envioSunat ='' ");
		jpaql.append(" and to_char(fechaPago,'dd/MM/yyyy') !=:fechaAc and idtipodocsunat is not null ");
		var query = createNativeQuery(jpaql.toString(), null);
		query.setParameter("fechaAc",
				FechaUtil.obtenerFechaFormatoPersonalizado(FechaUtil.obtenerFechaActual(), "dd/MM/yyyy"));
		query.executeUpdate();
	}

	@Override
	public List<Map<String, Object>> generarArchivosPlanosXML(ControlPago controlp) {
		ScriptSqlResulJDBCVO resultadoTemp = obtenerScriptSqlTXT(controlp);
		return resultadoTemp.getListaData();
	}

	private ScriptSqlResulJDBCVO obtenerScriptSqlTXT(ControlPago obj) {
		StringBuilder sql = new StringBuilder();
		Long comparacionSerieB = 3750008L;
		Long comparacionSerieF = 3750007L;
		Long comparacionSerieNC = 692L;
		Long comparacionSerieND = 693L;
		String det = "DET";
		String trd = "TRD";
		String ley = "LEY";
		String tri = "TRI";
		var parametros = new HashMap<String, Object>();
		StringBuilder filtroDinamic = new StringBuilder();
		if (!StringUtil.isNullOrEmpty(obj.getAnulado())) {
			if (obj.getIdTipoDocSunat().equals(comparacionSerieB)) {
				if (StringUtil.isNullOrEmpty(obj.getId())) {
					parametros.put("fecha",
							FechaUtil.obtenerFechaFormatoPersonalizado(obj.getFechaPago(), "yyyy-MM-dd"));
					sql.append(
							SqlMapingUtil.obtenerSqlSentenciaBuildERP(ConstanteQueryNameUtil.REPORTE_TXT_GENERAR_DRI));
					sql = new StringBuilder(sql.toString().replace("${filtroDinamic}", filtroDinamic));
				} else if (obj.getId().equals(trd)) {
					parametros.put("fecha",
							FechaUtil.obtenerFechaFormatoPersonalizado(obj.getFechaPago(), "yyyy-MM-dd"));
					sql.append(
							SqlMapingUtil.obtenerSqlSentenciaBuildERP(ConstanteQueryNameUtil.REPORTE_TXT_GENERAR_TRD));
					sql = new StringBuilder(sql.toString().replace("${filtroDinamic}", filtroDinamic));
				}
			}
		} else if (!StringUtil.isNullOrEmpty(obj.getTipo())) {

			if (obj.getIdTipoDocSunat().equals(comparacionSerieF)) {
				parametros.put("idVentaFB", obj.getIdControlPago());
				sql.append(SqlMapingUtil.obtenerSqlSentenciaBuildERP(ConstanteQueryNameUtil.REPORTE_TXT_GENERAR_F_B));
				sql = new StringBuilder(sql.toString().replace("${filtroDinamic}", filtroDinamic));
			} else if (obj.getIdTipoDocSunat().equals(comparacionSerieNC)) {
				parametros.put("idVentaNC_ND", obj.getIdControlPago());
				sql.append(SqlMapingUtil.obtenerSqlSentenciaBuildERP(ConstanteQueryNameUtil.REPORTE_TXT_GENERAR_NOTAS));
				sql = new StringBuilder(sql.toString().replace("${filtroDinamic}", filtroDinamic));
			} else if (obj.getIdTipoDocSunat().equals(comparacionSerieND)) {
				parametros.put("idVentaNC_ND", obj.getIdControlPago());
				sql.append(SqlMapingUtil.obtenerSqlSentenciaBuildERP(ConstanteQueryNameUtil.REPORTE_TXT_GENERAR_NOTAS));
				sql = new StringBuilder(sql.toString().replace("${filtroDinamic}", filtroDinamic));
			}

		} else if (obj.getDet().equals(det)) {
			parametros.put("idVentaDet", obj.getIdControlPago());
			sql.append(SqlMapingUtil.obtenerSqlSentenciaBuildERP(ConstanteQueryNameUtil.REPORTE_TXT_GENERAR_DET));
			sql = new StringBuilder(sql.toString().replace("${filtroDinamic}", filtroDinamic));
		} else if (obj.getTri().equals(tri)) {
			parametros.put("idVentaTri", obj.getIdControlPago());
			sql.append(SqlMapingUtil.obtenerSqlSentenciaBuildERP(ConstanteQueryNameUtil.REPORTE_TXT_GENERAR_TRI));
			sql = new StringBuilder(sql.toString().replace("${filtroDinamic}", filtroDinamic));
		} else if (obj.getLey().equals(ley)) {
			parametros.put("idVentaLey", obj.getIdControlPago());
			parametros.put("montoLetra", obj.getMontoletra());
			sql.append(SqlMapingUtil.obtenerSqlSentenciaBuildERP(ConstanteQueryNameUtil.REPORTE_TXT_GENERAR_LEY));
			sql = new StringBuilder(sql.toString().replace("${filtroDinamic}", filtroDinamic));
		}

		return executeQuery(sql, parametros);
	}
}