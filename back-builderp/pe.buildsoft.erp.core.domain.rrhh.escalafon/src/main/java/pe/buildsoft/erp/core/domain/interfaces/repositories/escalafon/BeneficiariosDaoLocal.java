package pe.buildsoft.erp.core.domain.interfaces.repositories.escalafon;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.escalafon.Beneficiarios;
import pe.buildsoft.erp.core.domain.interfaces.repositories.GenericDAOLocal;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

/**
 * La Class BeneficiariosDaoLocal.
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
public interface BeneficiariosDaoLocal  extends GenericDAOLocal<String,Beneficiarios> {
	/**
	 * Listar beneficiarios.
	 *
	 * @param beneficiarios el beneficiarios
	 * @return the list
	 * @throws Exception the exception
	 */
	List<Beneficiarios> listar(BaseSearch filtro);
	
	/**
	 * contar lista beneficiarios.
	 *
	 * @param beneficiarios el beneficiarios
	 * @return the list
	 * @throws Exception the exception
	 */
	int contar(BaseSearch filtro);
	/**
	 * Generar id beneficiarios.
	 *
	 * @return the String
	 * @throws Exception the exception
	 */
	String generarId();
}