package pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.escalafon.Carrera;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class CarreraDaoLocal.
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
public interface CarreraDaoLocal  extends GenericDAOLocal<Long,Carrera> {
	/**
	 * Listar carrera.
	 *
	 * @param carrera el carrera
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Carrera> listar(BaseSearch filtro);
	
	/**
	 * contar lista carrera.
	 *
	 * @param carrera el carrera
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id carrera.
	 *
	 * @return the Long
	 * @throws Exception the exception
	 */
	Long generarId();
	
	Carrera findCarrera(Carrera filtro);
}