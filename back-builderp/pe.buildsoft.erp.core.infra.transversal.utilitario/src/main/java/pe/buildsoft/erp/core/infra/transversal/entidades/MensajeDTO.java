package pe.buildsoft.erp.core.infra.transversal.entidades;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

 

/**
 * La Class MensajeDTO.
 * <ul>
 * <li>Copyright 2020 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, 22/05/2020
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class MensajeDTO implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    private String urlRestEjecutor = "";
    private String operacion;
    private String data;
    private String metodo;//get|post|put|delete
    private String usuario;
    private String clave;
    private String token;
    private String qcfName;
    private String queueName;
    private String uuid;
    /**
     * Instancia un nuevo m d b j m s dataDTO.
     */
    public MensajeDTO() {
		super();
    }

}