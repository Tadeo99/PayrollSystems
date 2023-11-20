package pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.escalafon.Institucion;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class InstitucionDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Fri Apr 23 00:16:12 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface InstitucionDaoLocal  extends GenericDAOLocal<Long,Institucion> {
	/**
	 * Listar institucion.
	 *
	 * @param institucion el institucion
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Institucion> listar(BaseSearch filtro);
	
	/**
	 * contar lista institucion.
	 *
	 * @param institucion el institucion
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id institucion.
	 *
	 * @return the Long
	 * @throws Exception the exception
	 */
	Long generarId();
}