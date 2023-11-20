package pe.buildsoft.erp.core.domain.interfaces.repositories.pago;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.pago.TipoDocSunatEntidad;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class TipoDocSunatEntidadDaoLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:31 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface TipoDocSunatEntidadDaoLocal extends GenericDAOLocal<String, TipoDocSunatEntidad> {
	/**
	 * Listar tipo doc sunat entidad.
	 *
	 * @param filtro el tipo doc sunat entidad
	 * @return the list
	 * @throws Exception the exception
	 */
	List<TipoDocSunatEntidad> listar(BaseSearch filtro);

	/**
	 * contar lista tipo doc sunat entidad.
	 *
	 * @param filtro el tipo doc sunat entidad
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);

	/**
	 * Generar id tipoDocSunatEntidad.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();

	String update(Long idTipoDocSunat, String idEntidad, String nroDoc, String serie);
	// add serie para actualizar

	TipoDocSunatEntidad get(Long idTipoDocSunat, String idEntidad, String serie);

	List<TipoDocSunatEntidad> listarByItem(TipoDocSunatEntidad filtro);
}