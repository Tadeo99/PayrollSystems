package pe.buildsoft.erp.core.infra.data.repositories.pago;

import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.pago.DetControlPago;
import pe.buildsoft.erp.core.domain.interfaces.repositories.pago.DetControlPagoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericPagoBatchDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * La Class DetControlPagoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:29 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class DetControlPagoDaoImpl extends GenericPagoBatchDAOImpl<String, DetControlPago>
		implements DetControlPagoDaoLocal {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.pago.dao.local.DetControlPagoDaoLocal#
	 * listarDetControlPago(pe.com.builderp.core.service.pago.model.jpa.
	 * DetControlPago)
	 */

	@Override
	public List<DetControlPago> listarById(String idControlPago) {
		var parametros = new HashMap<String, Object>();
		var jpaql = new StringBuilder();
		jpaql.append("from DetControlPago o left join fetch o.controlPago cc  ");

		if (StringUtil.isNotNullOrBlank(idControlPago)) {
			jpaql.append(" where o.controlPago.idControlPago =:idControlPago ");
			parametros.put("idControlPago", idControlPago);
		}
		var query = this.createQuery(jpaql.toString(), parametros);
		return query.getResultList();
	}

}