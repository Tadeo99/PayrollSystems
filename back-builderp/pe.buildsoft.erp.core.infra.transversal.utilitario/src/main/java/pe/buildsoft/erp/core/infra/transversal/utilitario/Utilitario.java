package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * La Class Utilitario.
 * <ul>
 * <li>Copyright 2014 MAPFRE - MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft.
 * @version 1.0, Wed Jul 30 17:25:41 COT 2014
 * @since BuildErp 1.0
 */
public class Utilitario implements Serializable {

	/** La Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** La log. */
	private static Logger log = LoggerFactory.getLogger(Utilitario.class);

	/**
	 * Ordenador.
	 *
	 * @param descending    el descending
	 * @param listaGeneral  el lista general
	 * @param nombreColumna el nombre columna
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void ordenador(boolean descending, List listaGeneral, String nombreColumna) {
		CollectionUtil.ordenador(descending, listaGeneral, nombreColumna);
	}

	/**
	 * Validar hora.
	 *
	 * @param hora el hora
	 * @return true, en caso de exito
	 */
	public static boolean validarHora(String hora) {
		if (hora == null || hora.trim().equals("")) {
			return true;
		}
		try {
			StringTokenizer horaToken = new StringTokenizer(hora, ":");
			// validando hora
			String horaValidando = horaToken.nextToken();
			if (Integer.parseInt(horaValidando) < 0 || Integer.parseInt(horaValidando) > 23) {
				return false;
			}

			// validando minuto
			String minutoValidando = horaToken.nextToken();
			if (Integer.parseInt(minutoValidando) < 0 || Integer.parseInt(minutoValidando) > 59) {
				return false;
			}
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}

	}

	/**
	 * Obtener hora.
	 *
	 * @param hora el hora
	 * @return the integer
	 */
	public static Integer obtenerHora(String hora) {
		try {
			StringTokenizer horaToken = new StringTokenizer(hora, ":");

			// validando hora
			String horaValidando = horaToken.nextToken();
			return Integer.parseInt(horaValidando);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Obtener minuto.
	 *
	 * @param hora el hora
	 * @return the integer
	 */
	public static Integer obtenerMinuto(String hora) {
		try {
			StringTokenizer horaToken = new StringTokenizer(hora, ":");

			// validando hora
			String horaValidando = horaToken.nextToken();
			String minutoValidando = horaToken.nextToken();
			return Integer.parseInt(minutoValidando);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Formatear decimal seg�n patr�n ###,###.##.
	 *
	 * @param numero el numero
	 * @return the string
	 */
	public static String formatearDecimal(String numero) {
		DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
		simbolo.setDecimalSeparator('.');
		simbolo.setGroupingSeparator(',');
		DecimalFormat formateador = new DecimalFormat("###,###.##", simbolo);
		return formateador.format(new BigDecimal(numero));
	}

	/**
	 * Reingenieria formateo numerico.
	 *
	 * @param dataResul el data resul
	 * @return the string
	 */
	public static String reingenieriaFormateoNumerico(String dataResul) {
		try {
			String formateoDecimal = ConfiguracionUtil.getPwrConfUtil(ConfiguracionUtil.CARACTER_FORMATEO_NUMERICO);
			if (formateoDecimal != null) {
				for (var chart : formateoDecimal.toCharArray()) {
					dataResul = dataResul.replace(chart + "", "");
				}
			}
		} catch (Exception e) {
			// log.error(e);
		}
		if (dataResul != null) {
			dataResul = dataResul.trim();
		}
		return dataResul;
	}

	/**
	 * Substring.
	 *
	 * @param origen el origen
	 * @param start  el start
	 * @param end    el end
	 * @return the string
	 */
	public static String substring(String origen, int start, int end) {
		if (origen != null) {
			return origen.substring(start, end);
		}
		return "";
	}

	/**
	 * Obtiene description type.
	 *
	 * @param locale el locale
	 * @param value  el value
	 * @return description type
	 */
	public String getDescriptionType(Locale locale, String value) {
		return ResourceUtil.getString(locale, ConstanteTypeUtil.BUNDLE_NAME_TYPE, value);
	}

	/**
	 * Byte to string.
	 *
	 * @param data el data
	 * @return the string
	 */
	public static String byteToString(byte[] data) {
		String resultado = null;
		try {
			resultado = new String(data, "UTF-8");
		} catch (Exception e) {
		}
		return resultado;
	}

	/**
	 * Leer archivo plano.
	 *
	 * @param inputStream el input stream
	 * @return the list
	 */
	public static List<String> leerArchivoPlano(InputStream inputStream) {
		List<String> resultado = new ArrayList<String>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				resultado.add(line);
			}
		} catch (IOException e) {
			log.error("leerArchivoPlano", e);
		}
		return resultado;
	}

	/**
	 * Completar cerosy coma.
	 *
	 * @param numero el numero
	 * @return the string
	 */
	public static String completarCerosyComa(String numero) {
		return StringUtil.completarCerosyComa(numero);
	}

	public static int obtenetDifNum(int a, int b) {
		int resultado = a - b;

		return resultado;
	}

}