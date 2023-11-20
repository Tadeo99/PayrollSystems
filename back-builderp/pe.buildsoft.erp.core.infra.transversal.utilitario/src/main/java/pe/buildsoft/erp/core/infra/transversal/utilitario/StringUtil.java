package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class StringUtil.
 *
 * @author BuildSoft.
 * @version 1.0 , 06/04/2015
 * @since BuildErp 1.0
 */
public class StringUtil {

	/**
	 * Instancia un nuevo string util.
	 */
	public StringUtil() {

	}

	public static Object obtenerValorMap(Object valueMap, Object valueDefault) {
		Object resultado = null;
		if (!StringUtil.isNullOrEmpty(valueMap)) {
			resultado = valueMap;
		} else {
			resultado = valueDefault;
		}
		return resultado;
	}

	/**
	 * Comprueba si es not null or blank.
	 *
	 * @param obj el obj
	 * @return true, si es not null or blank
	 */
	public static boolean isNotNullOrBlank(Object obj) {
		boolean resultado = false;
		if (obj != null && obj.toString().trim().length() > 0) {
			resultado = true;
		}
		return resultado;
	}

	/**
	 * Checks if is null or empty.
	 *
	 * @param object the object
	 * @return true, if is null or empty
	 */
	public static boolean isNullOrEmpty(Object object) {
		if (object != null && !object.toString().trim().equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * Comprueba si es null or empty numeric.
	 *
	 * @param object el object
	 * @return true, si es null or empty numeric
	 */
	public static boolean isNullOrEmptyNumeric(Object object) {
		if (object != null && !object.toString().trim().equals("")) {
			try {
				BigDecimal numero = new BigDecimal(object.toString());
				if (numero.compareTo(BigDecimal.ZERO) > 0) {
					return false;
				}
			} catch (Exception e) {
				return true;
			}
			return true;
		}

		return true;
	}

	public static boolean isNullOrEmptyNumericMenosCero(Object object) {
		if (object != null && !object.toString().trim().equals("")) {
			try {
				BigDecimal numero = new BigDecimal(object.toString());
				if (numero.compareTo(BigDecimal.ZERO) != 0) {
					return false;
				}
			} catch (Exception e) {
				return true;
			}
			return true;
		}

		return true;
	}

	public static boolean isNullOrEmptyNumeriCero(Object object) {
		if (object != null && !object.toString().trim().equals("")) {
			try {
				BigDecimal numero = new BigDecimal(object.toString());
				return false;
			} catch (Exception e) {
				return true;
			}
		}

		return true;
	}

	/**
	 * Adiciona caracteres a una cadena por la izquierda.
	 *
	 * @param cadena   Cadena a llenar por la izquierda
	 * @param caracter Caracter de llenado
	 * @param longitud Longitud de la cadena final
	 * @return la cadena llenada con el caracter especificado.
	 */
	public static String completeLeft(Object cadena, char caracter, int longitud) {
		int tamanio = cadena.toString().length();
		StringBuffer valor = new StringBuffer();

		for (int i = 0; i < (longitud - tamanio); i++) {
			valor.append(caracter);
		}
		valor.append(cadena);
		return valor.toString();
	}

	/**
	 * Completar cerosy coma.
	 *
	 * @param numero el numero
	 * @return the string
	 */
	public static String completarCerosyComa(String numero) {
		numero = (numero.toString().substring(0, numero.toString().length() - 1) + "."
				+ numero.toString().substring(numero.toString().length() - 1)).trim();

		String ceros = "";
		int cantidad = 10 - numero.length();
		if (cantidad >= 1) {
			for (int i = 0; i < cantidad; i++) {
				ceros += "0";
			}
			numero = ceros + numero;
		}
		return numero;
	}

	/**
	 * Devuelve posicion cadena.
	 *
	 * @param cadena             el cadena
	 * @param cantidadCaracteres el cantidad caracteres
	 * @return the int
	 */
	public static int devuelvePosicionCadena(String cadena, int cantidadCaracteres) {
		int devuelvePosicion = 0;
		int caracteres = 0;
		for (int i = 0; i < cadena.length(); i++) {
			char x = cadena.charAt(i);
			if (x == ',') {
				caracteres++;
			}
			if (caracteres == cantidadCaracteres) {
				devuelvePosicion = 1 + i++;
				break;
			}
		}
		return devuelvePosicion;
	}

	/**
	 * Generar uuid.
	 *
	 * @return UUID
	 */
	public static String generarUUID() {
		return java.util.UUID.randomUUID().toString();
	}

	/**
	 * Obtener cadena maxima.
	 *
	 * @param error          el error
	 * @param cantidadMaxima el cantidad maxima
	 * @return the string
	 */
	public static String obtenerCadenaMaxima(String error, int cantidadMaxima) {
		String resultado = error;
		if (error != null) {
			if (error.length() > cantidadMaxima) {
				resultado = error.substring(0, cantidadMaxima);
			}
		}
		return resultado;
	}

	/**
	 * Generar key.
	 *
	 * @param argumentos el argumentos
	 * @return the string
	 */
	public static String generarKey(Object... argumentos) {
		StringBuilder respuesta = new StringBuilder();
		for (var object : argumentos) {
			respuesta.append(object);
		}
		return respuesta.toString();
	}

	/**
	 * Generar key.
	 *
	 * @param dataMap el data map
	 * @param key     el key
	 * @return the string
	 */
	public static String generarKey(Map<String, Object> dataMap, String... key) {
		StringBuilder keyGenerada = new StringBuilder();
		for (var valueKey : key) {
			keyGenerada.append(dataMap.get(valueKey));
		}
		return keyGenerada.toString();
	}

	// Inicio Mejora Clon 23/08/2017 001
	/**
	 * Generar key.
	 *
	 * @param dataMap el data map
	 * @param key     el key
	 * @return the string
	 */
	public static String generarKeyExluirKey(Map<String, Object> dataMap, String... excluirKey) {
		StringBuilder keyGenerada = new StringBuilder();
		Map<String, String> excluirMap = new HashMap<String, String>();
		for (var valueKey : excluirKey) {
			excluirMap.put(valueKey, valueKey);
		}
		for (var objData : dataMap.entrySet()) {
			if (!excluirMap.containsKey(objData.getKey())) {
				keyGenerada.append(objData.getValue());
			}
		}
		return keyGenerada.toString();
	}
	// Fin Mejora Clon 23/08/2017 001

	/**
	 * Generar keys.
	 *
	 * @param objects  el objects
	 * @param cantidad el cantidad
	 * @return the string
	 */
	public static String generarKey(Object[] objects, Integer cantidad) {
		StringBuilder resultado = new StringBuilder();
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				Object object = objects[i];
				if (i < cantidad) {
					resultado.append(object + "");
				} else {
					break;
				}
			}
		}
		return resultado.toString();
	}

	public static String generarKey(Object[] objects, Integer posicionInicial, Integer cantidad) {
		StringBuilder resultado = new StringBuilder();
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				Object object = objects[i];
				if (i >= posicionInicial && i < cantidad) {
					resultado.append(object + "");
				}
			}
		}
		return resultado.toString();
	}

	public static String concatenarComillas(String argumento) {
		StringBuilder resultado = new StringBuilder();
		resultado.append("'");
		resultado.append(argumento);
		resultado.append("'");
		return resultado.toString();
	}

	public static String quitarCaracterExtranio(String variable) {
		for (var caracterExtranio : CadenaCacheUtil.getInstance().getCaracterExtranioList()) {
			variable = variable.replaceAll(caracterExtranio, "");
		}
		return variable;
	}

	public static String quitarCaracterExtranio(String variable, int bloque) {
		try {
			List<String> listaBuscar = CadenaCacheUtil.getInstance().getCaracterExtranioList(bloque, "b");
			List<String> listaReemplazar = CadenaCacheUtil.getInstance().getCaracterExtranioList(bloque, "r");
			for (int i = 0; i < listaBuscar.size(); i++) {
				variable = variable.replaceAll(listaBuscar.get(i), listaReemplazar.get(i));
			}
		} catch (Exception e) {
			return variable;
		}

		return variable;
	}

	public static String generarStringSimboloSeparador(List<String> lista, String simbolo) {
		StringBuilder resultado = new StringBuilder();
		int contador = 1;
		int cantidadTotal = lista.size();
		for (var string : lista) {
			if (cantidadTotal == contador) {
				resultado.append(string);
			} else {
				resultado.append(string);
				resultado.append(simbolo);
			}
			contador = contador + 1;
		}
		return resultado.toString();
	}

	public static String generarStringSimboloSeparadorComillas(List<String> lista, String simbolo) {
		StringBuilder resultado = new StringBuilder();
		int contador = 1;
		int cantidadTotal = lista.size();
		for (var string : lista) {
			if (cantidadTotal == contador) {
				resultado.append("'");
				resultado.append(string);
				resultado.append("'");
			} else {
				resultado.append("'");
				resultado.append(string);
				resultado.append("'");
				resultado.append(simbolo);
			}
			contador = contador + 1;
		}
		return resultado.toString();
	}

	public static String padLeft(String str, int length, String padChar) {
		String pad = "";
		for (int i = 0; i < length; i++) {
			pad += padChar;
		}
		return pad.substring(str.length()) + str;
	}
}
