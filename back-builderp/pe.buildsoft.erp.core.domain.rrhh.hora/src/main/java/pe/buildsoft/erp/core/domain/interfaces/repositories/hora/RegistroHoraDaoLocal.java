package pe.buildsoft.erp.core.domain.interfaces.repositories.hora;
import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.hora.RegistroHora;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class RegistroCabeceraDaoLocal.
 * <ul>
 * <li>Copyright 2021 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:16:44 COT 2022
 * @since BuildErp 1.0
 */
@Local
public interface RegistroHoraDaoLocal  extends GenericDAOLocal<String,RegistroHora> {
	/**
	 * Listar registro cabecera.
	 *
	 * @param registroCabecera el registro cabeceraDTO
	 * @return the list
	 * @throws Exception the exception
	 */
	List<RegistroHora> listar(BaseSearch filtro);
	
	/**
	 * contar lista registro cabecera.
	 *
	 * @param registroCabecera el registro cabecera
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id registroCabecera.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}