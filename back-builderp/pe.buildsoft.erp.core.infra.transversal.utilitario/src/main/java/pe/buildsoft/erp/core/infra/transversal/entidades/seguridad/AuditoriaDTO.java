package pe.buildsoft.erp.core.infra.transversal.entidades.seguridad;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class AuditoriaDTO.
 * <ul>
 * <li>Copyright 2014 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Wed Jul 30 18:01:08 COT 2014
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class AuditoriaDTO extends BaseEntidad implements Serializable {
 
	/** La Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** El id opcion. */
	private String usuarioLogin;
	private String ip;
	
	
	/**
	 * Instancia un nuevo opcion.
	 */
	public AuditoriaDTO() {
	}


	/**
	 * Instancia un nuevo auditoria dto.
	 *
	 * @param usuarioLogin el usuario login
	 * @param ip el ip
	 */
	public AuditoriaDTO(String usuarioLogin, String ip) {
		super();
		this.usuarioLogin = usuarioLogin;
		this.ip = ip;
	}

	
}