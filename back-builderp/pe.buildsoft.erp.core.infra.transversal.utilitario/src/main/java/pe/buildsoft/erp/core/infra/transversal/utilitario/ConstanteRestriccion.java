package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class ConstanteRestriccion.
 *
 * @author BuildSoft.
 * @version 1.0 , 07/04/2015
 * @since BuildErp 1.0
 */
public class ConstanteRestriccion {

	/**
	 * Obtener menu opcion restriccion.
	 *
	 * @return the list
	 */
	public static List<Long> obtenerMenuOpcionRestriccion() {
		List<Long> resultado = new ArrayList<Long>();
		resultado.add(1L); //"Gestionar Usuarios"--> Opción
		resultado.add(2L); //"Gestionar Roles"--> Opción
		resultado.add(3L); //"Gestionar Opciones"--> Opción
		resultado.add(4L); //"Configurar menú"--> Opción
		resultado.add(5L); //"Seguridad"--> Menú
		return resultado;
	}
}
