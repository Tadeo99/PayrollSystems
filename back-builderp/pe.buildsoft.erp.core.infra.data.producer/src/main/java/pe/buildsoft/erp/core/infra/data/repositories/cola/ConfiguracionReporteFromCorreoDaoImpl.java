package pe.buildsoft.erp.core.infra.data.repositories.cola;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import pe.buildsoft.erp.core.domain.interfaces.repositories.cola.ConfiguracionReporteFromCorreoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericColaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ConfiguracionReporteFromCorreoVO;

/**
 * La Class ConfiguracionReporteFromCorreoDaoImpl.
 * <ul>
 * <li>Copyright 2014 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Wed Jul 30 17:22:43 COT 2014
 * @since BuildErp 1.0
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ConfiguracionReporteFromCorreoDaoImpl extends GenericColaDAOImpl<Object, Object>
		implements ConfiguracionReporteFromCorreoDaoLocal {
	@Override
	public Map<String, ConfiguracionReporteFromCorreoVO> getFromCorreoMap() {
		var parametros = new HashMap<String, Object>();
		StringBuilder jpaql = new StringBuilder(obtenerConfiguracionReporteFromCorreoSQL());
		var query = createNativeQuery(jpaql.toString(), parametros);
		return parsearConf(query.getResultList());
	}

	/**
	 * Obtener configuracion reporte from correo sql.
	 *
	 * @return the string
	 */
	private String obtenerConfiguracionReporteFromCorreoSQL() {
		var jpaql = new StringBuilder();
		jpaql.append("  SELECT c.COD_FORM,C.FLAG_LDAP,c.NOM_FORM,C.EMAIL ");
		jpaql.append("  FROM TRON2000.MP_FORM_CORREO C ");
		jpaql.append(" WHERE C.COD_EST = 'A'");
		return jpaql.toString();
	}

	/**
	 * Parsear configuracion reporte from correo map.
	 *
	 * @param resultadoTemp el resultado temp
	 * @return the map
	 */
	private Map<String, ConfiguracionReporteFromCorreoVO> parsearConf(List<Object[]> resultadoTemp) {
		Map<String, ConfiguracionReporteFromCorreoVO> resultado = new HashMap<>();
		for (Object[] objects : resultadoTemp) {
			String key = objects[0].toString();
			String flagLDAP = objects[1].toString();
			String nombreForm = objects[2].toString();
			String email = (String) objects[3];
			ConfiguracionReporteFromCorreoVO value = new ConfiguracionReporteFromCorreoVO(key, flagLDAP, nombreForm,
					email);
			resultado.put(key, value);
		}
		return resultado;
	}

}