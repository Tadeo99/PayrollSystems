package pe.buildsoft.erp.core.infra.data.repositories.cola;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import pe.buildsoft.erp.core.domain.entidades.cola.ConfiguracionFiltroReporte;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.ConfiguracionFiltroReporteDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericColaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;
import pe.buildsoft.erp.core.infra.transversal.utilitario.ObjectUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class ConfiguracionFiltroReporteDaoImpl.
 * <ul>
 * <li>Copyright 2015 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 2.1, Thu Dec 21 12:25:45 COT 2017
 * @since BuildErp 1.0
 */
@Stateless
public class ConfiguracionFiltroReporteDaoImpl extends GenericColaDAOImpl<Long, ConfiguracionFiltroReporte>
		implements ConfiguracionFiltroReporteDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.dao.configuracion.cola.local.
	 * ConfiguracionFiltroReporteDaoLocal#listarConfiguracionFiltroReporte(pe.gob.
	 * mapfre.pwr.rep.model.configuracion.cola.jpa.ConfiguracionFiltroReporte)
	 */
	@Override
	public List<ConfiguracionFiltroReporte> listar(BaseSearch filtro) {
		var query = generarQuery(filtro, false);
		query.setFirstResult(filtro.getStartRow());
		query.setMaxResults(filtro.getOffSet());
		return query.getResultList();
	}

	/**
	 * Generar query lista ConfiguracionFiltroReporte.
	 *
	 * @param ConfiguracionFiltroReporte el configuracionFiltroReporte
	 * @param esContador                 el es contador
	 * @return the query
	 */
	private Query generarQuery(BaseSearch filtro, boolean esContador) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		if (esContador) {
			jpaql.append(" select count(o.idCodigoFiltro) from ConfiguracionFiltroReporte o where 1=1 ");
		} else {
			jpaql.append(" select o from ConfiguracionFiltroReporte o where 1=1 ");
		}
		/*if (!StringUtil.isNullOrEmptyNumeric(filtro.getIdOpcionMenu())) {
			jpaql.append(" and o.idOpcionMenu = :idOpcionMenu ");
			parametros.put("idOpcionMenu", filtro.getIdOpcionMenu());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getNombreFiltro())) {
			jpaql.append(" and upper(o.nombreFiltro) like :nombreFiltro ");
			parametros.put("nombreFiltro", "%" + filtro.getNombreFiltro().toUpperCase() + "%");
		}
		if (!StringUtil.isNullOrEmptyNumeric(filtro.getTipoValorFiltro())) {
			jpaql.append(" and o.tipoValorFiltro = :tipoValorFiltro ");
			parametros.put("tipoValorFiltro", filtro.getTipoValorFiltro());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getVisibleFiltro())) {
			jpaql.append(" and upper(o.visibleFiltro) like :visibleFiltro ");
			parametros.put("visibleFiltro", "%" + filtro.getVisibleFiltro().toUpperCase() + "%");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getTipoDatoFiltro())) {
			jpaql.append(" and upper(o.tipoDatoFiltro) like :tipoDatoFiltro ");
			parametros.put("tipoDatoFiltro", "%" + filtro.getTipoDatoFiltro().toUpperCase() + "%");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getFechaActualizacion())) {
			jpaql.append(" and o.fechaActualizacion = :fechaActualizacion ");
			parametros.put("fechaActualizacion", filtro.getFechaActualizacion());
		}
		if (!StringUtil.isNullOrEmpty(filtro.getCodigoUsuario())) {
			jpaql.append(" and upper(o.codigoUsuario) like :codigoUsuario ");
			parametros.put("codigoUsuario", "%" + filtro.getCodigoUsuario().toUpperCase() + "%");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getObjectoAtributo())) {
			jpaql.append(" and upper(o.objectoAtributo) like :objectoAtributo ");
			parametros.put("objectoAtributo", "%" + filtro.getObjectoAtributo().toUpperCase() + "%");
		}
		if (!StringUtil.isNullOrEmpty(filtro.getFormatoTipoDato())) {
			jpaql.append(" and upper(o.formatoTipoDato) like :formatoTipoDato ");
			parametros.put("formatoTipoDato", "%" + filtro.getFormatoTipoDato().toUpperCase() + "%");
		}*/
		if (!StringUtil.isNullOrEmpty(filtro.getEstado())) {
			jpaql.append(" and upper(o.estado) like :estado ");
			parametros.put("estado", "%" + filtro.getEstado().toUpperCase() + "%");
		}
		if (!esContador) {
			// jpaql.append(" ORDER BY 1 ");
		}
		return createQuery(jpaql.toString(), parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.dao.configuracion.cola.local.
	 * ConfiguracionFiltroReporteDaoLocal#contarListar{entity.getClassName()}(pe.gob
	 * .mapfre.pwr.rep.model.configuracion.cola.jpa.ConfiguracionFiltroReporte)
	 */
	@Override
	public int contar(BaseSearch filtro) {
		var query = generarQuery(filtro, true);
		return ((Long) query.getSingleResult()).intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.gob.mapfre.pwr.rep.ejb.dao.configuracion.cola.local.
	 * ConfiguracionFiltroReporteDaoLocal#generarIdConfiguracionFiltroReporte()
	 */
	@Override
	public Long generarId() {
		var resultado = 1L;
		var query = createQuery("select max(o.idCodigoFiltro) from ConfiguracionFiltroReporte o", null);
		var listLong = query.getResultList();
		if (listLong != null && !listLong.isEmpty() && listLong.get(0) != null) {
			Long ultimoIdGenerado = Long.valueOf(listLong.get(0).toString());
			if (!StringUtil.isNullOrEmpty(ultimoIdGenerado)) {
				resultado = resultado + ultimoIdGenerado;
			}
		}
		return resultado;
	}

	@Override
	public Map<String, List<String>> listarIdComponentes(Long idOpcion) {
		var parametros = new HashMap<String, Object>();
		Map<String, List<String>> resultado = new HashMap<>();
		var jpaql = new StringBuilder();
		jpaql.append(" select o.idComponenteUI, o.objectoAtributo from ConfiguracionFiltroReporte o where 1 = 1 ");
		jpaql.append(" and o.idOpcionMenu = :idOpcion ");
		parametros.put("idOpcion", idOpcion);
		var query = createQuery(jpaql.toString(), parametros);
		List<Object[]> respuesta = query.getResultList();
		if (respuesta != null && !respuesta.isEmpty()) {
			for (Object[] objects : respuesta) {
				String key = ObjectUtil.objectToString(objects[0]);
				if (!StringUtil.isNullOrEmpty(key)) {
					if (!resultado.containsKey(key)) {
						List<String> listaAtributos = new ArrayList<>();
						listaAtributos.add(ObjectUtil.objectToString(objects[1]));
						resultado.put(ObjectUtil.objectToString(objects[0]), listaAtributos);
					} else {
						List<String> listaAtributos = resultado.get(key);
						listaAtributos.add(ObjectUtil.objectToString(objects[1]));
					}
				}
			}
		}
		return resultado;
	}
}