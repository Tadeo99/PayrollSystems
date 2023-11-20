package pe.buildsoft.erp.core.infra.transversal.utilitario;


/**
 * <ul>
 * <li>Copyright 2012 BUILD SOFT S.A.C - BS. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class ConstantesConfiguracion.
 *
 * @author ndavilal.
 * @version 1.0 , 04/09/2013
 * @since SIAA 2.0
 */
public class ConstantesConfiguracion {
	
	public static final boolean ES_PRODUCCION = obtenerSistemaoperativoProduccion();
	
	/** La Constante ES_PE. */
	public static final String ES_PE = "es_PE";
	
	private ConstantesConfiguracion() {
	    throw new IllegalStateException("Utility ConstantesConfiguracion class");
	  }

	
	private static boolean obtenerSistemaoperativoProduccion(){
		String sSistemaOperativo = System.getProperty("os.name");
		if (sSistemaOperativo == null) {
			sSistemaOperativo = "";
		}
		return !sSistemaOperativo.toUpperCase().contains("WINDOWS");
	}
}
