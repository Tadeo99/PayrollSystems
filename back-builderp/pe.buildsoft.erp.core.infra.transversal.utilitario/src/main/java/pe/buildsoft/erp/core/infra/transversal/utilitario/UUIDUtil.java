package pe.buildsoft.erp.core.infra.transversal.utilitario;


/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class UUIDUtil.
 *
 * @author BuildSoft.
 * @version 1.0 , 11/04/2016
 * @since BuildErp 1.0
 */
public class UUIDUtil {

	/**
	 * Generar uuid.
	 *
	 * @return the string
	 */
	public static String generarUUID() {
		return java.util.UUID.randomUUID().toString();
	}
	
	/**
	 * Generar element uuid.
	 *
	 * @return the string
	 */
	public static String generarElementUUID() {
		return java.util.UUID.randomUUID().toString().replaceAll("-", "");
	}
}
