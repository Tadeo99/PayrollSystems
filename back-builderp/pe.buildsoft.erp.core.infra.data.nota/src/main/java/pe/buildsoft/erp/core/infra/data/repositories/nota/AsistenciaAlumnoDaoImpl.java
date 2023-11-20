package pe.buildsoft.erp.core.infra.data.repositories.nota;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import jakarta.ejb.Stateless;
import pe.buildsoft.erp.core.domain.entidades.nota.AsistenciaAlumno;
import pe.buildsoft.erp.core.domain.interfaces.repositories.nota.AsistenciaAlumnoDaoLocal;
import pe.buildsoft.erp.core.infra.data.repositories.GenericNotaDAOImpl;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.UUIDUtil;

/**
 * La Class AsistenciaAlumnoDaoImpl.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Stateless
public class AsistenciaAlumnoDaoImpl extends GenericNotaDAOImpl<String, AsistenciaAlumno>
		implements AsistenciaAlumnoDaoLocal {

	@Override
	public List<AsistenciaAlumno> listar(AsistenciaAlumno filtro) {
		var parametros = new HashMap<String, Object>();
		var ejecutarQuery = false;
		var jpaql = new StringBuilder();
		jpaql.append("from AsistenciaAlumno o left join fetch  o.detalleCargaAcademica dca ");
		jpaql.append(" left join fetch  o.alumno a  ");
		jpaql.append(" left join fetch o.itemByDia where 1 = 1 ");
		if (StringUtil.isNotNullOrBlank(filtro.getDetalleCargaAcademica().getIdDetalleCargaAcademica())) {
			ejecutarQuery = true;
			jpaql.append(" and o.detalleCargaAcademica.idDetalleCargaAcademica = :idDetalleCargaAcademica ");
			parametros.put("idDetalleCargaAcademica", filtro.getDetalleCargaAcademica().getIdDetalleCargaAcademica());
			if (StringUtil.isNotNullOrBlank(filtro.getAlumno().getIdAlumno())) {
				jpaql.append(" and o.alumno.idAlumno = :idAlumno ");
				parametros.put("idAlumno", filtro.getAlumno().getIdAlumno());
			}
			if (filtro.getFechaHorario() != null) {
				jpaql.append(" and to_char(o.fechaHorario,'dd/MM/yyyy') = :fechaHorario");
				parametros.put("fechaHorario",
						FechaUtil.obtenerFechaFormatoPersonalizado(filtro.getFechaHorario(), "dd/MM/yyyy"));
			}
		}
		if (ejecutarQuery) {
			jpaql.append(" order by o.alumno.apellidoPaterno asc ");
			var query = createQuery(jpaql.toString(), parametros);
			return query.getResultList();
		}
		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pe.com.builderp.core.service.nota.dao.local.AsistenciaAlumnoDaoLocal#
	 * generarIdAsistenciaAlumno()
	 */
	@Override
	public String generarId() {
		return UUIDUtil.generarElementUUID();
	}

}