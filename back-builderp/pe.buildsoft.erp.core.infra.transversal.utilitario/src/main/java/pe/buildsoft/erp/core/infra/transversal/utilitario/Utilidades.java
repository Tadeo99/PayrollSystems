package pe.buildsoft.erp.core.infra.transversal.utilitario;

/**
 * <ul>
 * <li>Copyright 2012 BUILD SOFT S.A.C - BS. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class Utilidades.
 *
 * @author ndavilal.
 * @version 1.0 , 18/03/2012
 * @since SIAA 2.0
 */
public class Utilidades {

	
	/** La Constante isPosgres. */
	private static final boolean ISPOSGRES = true;

	
	/**
	 * Obtener with recursivo.
	 *
	 * @return the string
	 */
	public static String obtenerWITHRecursivo() {
		if (ISPOSGRES) {
			return "WITH RECURSIVE";
		}
		return "WITH";
	}
	
	/**
	 * Obtener contatenar.
	 *
	 * @return the string
	 */
	public static String obtenerContatenar() {
		if (ISPOSGRES) {
			return "";
		}
		return " + ''";
	}
	
	/**
	 * Obtener separar.
	 *
	 * @return the string
	 */
	public static String obtenerSeparar() {
		if (ISPOSGRES) {
			return " ";
		}
		return " + ' ' + ";
	}
	
	
}