package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class ObjectUtil.
 *
 * @author BuildSoft.
 * @version 1.0 , 06/05/2016
 * @since BuildErp 1.0
 */
public final class ObjectUtil {

	private static final Locale ES = new Locale("es", "PE");

	/**
	 * Instancia un nuevo object util.
	 */
	private ObjectUtil() {
	}

	/**
	 * Object to long.
	 *
	 * @param object el object
	 * @return the long
	 */
	public static Long objectToLong(Object object) {
		if (!StringUtil.isNullOrEmptyNumeric(object)) {
			return Long.valueOf(object + "");
		} else {
			return 0L;
		}
	}

	/**
	 * Object to integer.
	 *
	 * @param object el object
	 * @return the integer
	 */
	public static Integer objectToInteger(Object object) {
		if (!StringUtil.isNullOrEmptyNumeric(object)) {
			return Integer.valueOf(object + "");
		} else {
			return 0;
		}
	}

	/**
	 * Object to string.
	 *
	 * @param object el object
	 * @return the string
	 */
	public static String objectToString(Object object) {
		if (object != null) {
			return object + "";
		}
		return "";
	}

	/**
	 * Object to big decimal.
	 *
	 * @param object el object
	 * @return the big decimal
	 */
	public static BigDecimal objectToBigDecimal(Object object) {
		if (!StringUtil.isNullOrEmptyNumeriCero(object)) {
			return new BigDecimal(object + "");
		} else {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * Object to big decimal presicion.
	 *
	 * @param object el object
	 * @param escala el escala
	 * @return the big decimal
	 */
	public static BigDecimal objectToBigDecimalPresicion(Object object, Integer escala) {
		if (!StringUtil.isNullOrEmptyNumeriCero(object)) {
			return new BigDecimal(object + "").setScale(escala, RoundingMode.HALF_UP);
		} else {
			return BigDecimal.ZERO.setScale(escala, RoundingMode.HALF_UP);
		}
	}

}
