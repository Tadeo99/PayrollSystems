package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class FTPVO.
 *
 * @author BuildSoft.
 * @version 1.0 , 30/06/2016
 * @since BuildErp 1.0
 */
@Getter
@Setter
public class FTPVO implements Serializable {
	
	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** La server. */
	private String server;
	
	/** La port. */
	private int port;
	
	/** La usuario. */
	private String usuario;
	
	/** La clave. */
	private String clave;
	
	/** La ruta local. */
	private String rutaLocal;
	
	/** La ruta ftp. */
	private String rutaFTP;
	
	/** La nombre archivo local. */
	private String nombreArchivoLocal;
	
	/** La nombre archivo ftp. */
	private String nombreArchivoFTP;
	
	/** La id juego trama. */
	private Long idJuegoTrama = 0L;
	
	/** La numero referencia. */
	private String numeroReferencia = "";
	
	/** La nomenclatura simulacion. */
	private String nomenclaturaSimulacion = "";
	
	/** La id trama nomenclatura archivo. */
	private Long idTramaNomenclaturaArchivo = 0L;
	
	private boolean isEliminarArchivoTemp = true;
	
	private String protocolo = "";
	
	/**
	 * Instancia un nuevo ftpvo.
	 */
	public FTPVO() {
		super();
	}
	
	/**
	 * Instancia un nuevo ftpvo.
	 *
	 * @param server el server
	 * @param port el port
	 * @param usuario el usuario
	 * @param clave el clave
	 * @param rutaLocal el ruta local
	 * @param rutaFTP el ruta ftp
	 * @param nombreArchivoLocal el nombre archivo local
	 * @param nombreArchivoFTP el nombre archivo ftp
	 */
	public FTPVO(String server, int port, String usuario, String clave,
			String rutaLocal, String rutaFTP, String nombreArchivoLocal,
			String nombreArchivoFTP) {
		super();
		this.server = server;
		this.port = port;
		this.usuario = usuario;
		this.clave = clave;
		this.rutaLocal = rutaLocal;
		this.rutaFTP = rutaFTP;
		this.nombreArchivoLocal = nombreArchivoLocal;
		this.nombreArchivoFTP = nombreArchivoFTP;
	}
}
