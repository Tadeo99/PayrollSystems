package pe.buildsoft.erp.core.infra.data.repositories.planilla;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.planilla.Adelanto;
import pe.buildsoft.erp.core.domain.interfaces.repositories.planilla.AdelantoConsumerDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPlanillaBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class AdelantoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:11 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class AdelantoConsumerDaoImpl extends GenericPlanillaBatchDAOImpl<String, Adelanto>
		implements AdelantoConsumerDaoLocal {

	// TODO:FALTA PERIODO DE PLANILLA
	@Override
	public Map<String, BigDecimal> obtnerAdelantosMap(Long idCategoriaTrabajador, List<String> listaIdPersonal) {
		var resultado = new HashMap<String, BigDecimal>();
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append("select o from Adelanto o  where 1 = 1 ");
		if (!StringUtil.isNullOrEmpty(idCategoriaTrabajador)) {
			jpaql.append(" and o.idPersonal in (:listaIdPersonal)");
			parametros.put("listaIdPersonal", listaIdPersonal);
		}
		var query = createQuery(jpaql.toString(), parametros);
		List<Adelanto> resul = query.getResultList();
		for (var objData : resul) {
			String key = objData.getIdPersonal();
			if (!resultado.containsKey(key)) {
				resultado.put(key, objData.getMonto());
			} else {
				resultado.put(key, objData.getMonto());
			}
		}

		return resultado;
	}

}