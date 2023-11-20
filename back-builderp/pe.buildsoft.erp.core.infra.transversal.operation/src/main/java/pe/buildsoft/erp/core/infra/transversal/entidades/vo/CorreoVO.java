package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;


/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class CorreoVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 06/04/2015
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class CorreoVO implements Serializable {

	/** El asunto. */
	private String asunto;
	
	/** El contenido. */
	private String contenido;
	
    /** La Constante serialVersionUID. */
    private static final long serialVersionUID = 1L;
     
   
    /** La lista destinatarios. */
    private List<DetalleCorreoVO> detalleCorreo;
    
    /** La uuid. */
    private String UUID = null;
    
    /** La archivos adjuntos. */
    private File[] archivosAdjuntos;
    
    //Solo para el caso de los reportes
    /** La configuracion reporte from correo vo. */
    private ConfiguracionReporteFromCorreoVO configuracionReporteFromCorreoVO = new ConfiguracionReporteFromCorreoVO();
    
    /** La usuario dto. */
    private String usuarioCorreoCorporativo;
    private String usuarioNombreCompleto;
    
    
   private Map<String,String> parametrosMap = new HashMap<>();

    /**
     * OffsetDateTimeiates a new correo vo.
     *
     * @param asunto the asunto
     * @param contenido the contenido
     * @param detalleCorreo the detalle correo
     */
    public CorreoVO(String asunto, String contenido,
			List<DetalleCorreoVO> detalleCorreo) {
		super();
		this.asunto = asunto;
		this.contenido = contenido;
		this.detalleCorreo = detalleCorreo;
	}

	/**
     * Instancia un nuevo mensaje vo.
     */
    public CorreoVO() {

    }
    public void modificarUUID(String UUID) {
    	this.UUID = UUID;
    }

}
