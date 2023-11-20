package pe.buildsoft.erp.core.infra.transversal.entidades;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

/**
 * La Class BasePaginator.
 * <ul>
 * <li>Copyright 2014 Mapfre - Mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Wed Sep 30 11:24:54 COT 2014
 * @since Rep 1.0
 */
@Getter
@Setter
public class BaseEntidad  {

	@Transient
	private boolean checked;

	@Transient
	private String descripcionView = "";

	@Transient
	private transient Object id;
	
	/*@Transient
	private String idEntidadSelect;*/

}
