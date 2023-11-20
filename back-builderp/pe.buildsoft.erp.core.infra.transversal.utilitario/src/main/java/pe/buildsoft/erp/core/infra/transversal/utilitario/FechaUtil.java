package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import pe.buildsoft.erp.core.infra.transversal.type.DiaSemanaType;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class FechaUtil.
 *
 * @author BuildSoft.
 * @version 1.0 , 07/04/2015
 * @since BuildErp 1.0
 */
public final class FechaUtil {

	/** La Constante DATE_SHORT. */
	private static final DateTimeFormatter DATE_SHORT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	/** La Constante DATE_LONG. */
	private static final DateTimeFormatter DATE_LONG = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	/** La Constante HOURS_LONG. */
	private static final DateTimeFormatter HOURS_LONG = DateTimeFormatter.ofPattern("hh:mm:ss");

	/** La Constante EN. */
	private static final Locale EN = new Locale("en", "US");

	/** La Constante ES. */
	private static final Locale ES = new Locale("es", "PE");

	/** La Constante DATE_DMY. */
	public static final String DATE_DMY = "dd/MM/yyyy";

	/** La Constante DATE_DMY_HORA. */
	public static final String DATE_DMY_HORA = "dd-MM-yyyy - hh:mm:ss";

	/** La Constante DATE_DMY_HORA_24_HORAS. */
	public static final String DATE_DMY_HORA_24_HORAS = "dd-MM-yyyy - HH:mm:ss";

	/**
	 * OffsetDateTimeiates a new fecha util.
	 */
	private FechaUtil() {

	}

	/**
	 * Metodo que permite obtener el formato completo de una fecha.
	 *
	 * @param fecha Recibe un Date con la fecha.
	 * @return Retorna un String con la fecha en el formato establecido.
	 */
	public static String obtenerFechaFormatoCompleto(OffsetDateTime fecha) {
		return DATE_LONG.format(fecha);
	}

	/**
	 * Metodo que permite obtener un formato simple para una fecha especifica.
	 *
	 * @param fecha Recibe un Date.
	 * @return Retorna un String con el formato simple.
	 */
	public static String obtenerFechaFormatoSimple(OffsetDateTime fecha) {
		return DATE_SHORT.format(fecha);
	}

	/**
	 * Metodo que permite obtener un formato completo para una fecha especifica.
	 *
	 * @param fecha Recibe un String
	 * @return Retorna un Date @ Excepci&oacute;n en caso de que no se realize el
	 *         parseo.
	 */
	public static OffsetDateTime obtenerFechaFormatoCompleto(String fecha) {
		return OffsetDateTime.parse(fecha, DATE_LONG);
	}

	/**
	 * Metodo para obtener la fecha a partir de un String.
	 *
	 * @param fecha Recibe un String con la fecha.
	 * @return Retorna un Date con la fecha. @ Excepci&oacute;n en caso de que no se
	 *         realize el parseo.
	 */
	public static OffsetDateTime obtenerFecha(String fecha) {
		return OffsetDateTime.parse(fecha, DATE_SHORT);
	}

	/**
	 * Metodo para obtener la fecha a partir de la fecha en formato String y el
	 * formato.
	 *
	 * @param fecha   Recibe un String con la fecha.
	 * @param formato Recibe un String con el formato deseado.
	 * @return Retorna un Date con la fecha convertida. @ Excepci&oacute;n en caso
	 *         de que no se realize el parseo.
	 */
	public static OffsetDateTime obtenerFechaFormatoPersonalizado(String fecha, String formato) {
		DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern(formato, ES);
		return OffsetDateTime.parse(fecha, simpleDateFormat);
	}

	/**
	 * Obtener fecha formato personalizado.
	 *
	 * @param fecha   el fecha
	 * @param formato el formato
	 * @return the string @ the parse exception
	 */
	public static String obtenerFechaFormatoPersonalizado(OffsetDateTime fecha, String formato) {
		return DateTimeFormatter.ofPattern(formato, ES).format(fecha);
	}

	/**
	 * Obtener Fecha Formato Personalizado Default.
	 * 
	 * @param fecha
	 * @return @
	 */
	public static String obtenerFechaFormatoPersonalizadoDefault(OffsetDateTime fecha) {
		return DateTimeFormatter.ofPattern(DATE_DMY, ES).format(fecha);
	}

	/**
	 * Metodo que permite obtener la fecha actual.
	 *
	 * @return Retorna la fecha actual.
	 */
	public static OffsetDateTime obtenerFechaActual() {
		return OffsetDateTime.now();
	}

	public static OffsetDateTime obtenerFechaActualShort() {
		return obtenerFechaFormatoPersonalizado(obtenerFechaFormatoPersonalizadoDefault(OffsetDateTime.now()),
				DATE_DMY);
	}

	public static OffsetDateTime obtenerFechaShort(OffsetDateTime date) {
		return obtenerFechaFormatoPersonalizado(obtenerFechaFormatoPersonalizadoDefault(date), DATE_DMY);
	}

	/**
	 * Obtener hora actual formato completo.
	 *
	 * @return the string
	 */
	public static String obtenerHoraActualFormatoCompleto() {
		return HOURS_LONG.format(OffsetDateTime.now());
	}

	/**
	 * Retorna la diferencia de horas entre una fecha mayor y una fecha menor.
	 * 
	 * @param menor Fecha de tipo java.util.Date fecha menor
	 * @param mayor Fecha de tipo java.util.Date fecha mayor
	 * @return int numero de dias de diferencia
	 */
	public static int restaMinutos(OffsetDateTime menor, OffsetDateTime mayor) {
		int diferencia = 0;
		OffsetDateTime temp = OffsetDateTime.of(mayor.toLocalDate(), mayor.toLocalTime(), mayor.getOffset());
		while (temp.isAfter(menor)) {
			temp = adicionarMinutosCalendario(temp, -1);
			diferencia++;
		}
		return diferencia;
	}

	/**
	 * Adiciona un nï¿½mero de horas a una fecha dada como tipo java.util.Date.
	 *
	 * @param date    Fecha de tipo java.util.Date
	 * @param minutos el minutos
	 * @return fecha con los nuevos valores
	 */
	public static OffsetDateTime adicionarMinutosCalendario(OffsetDateTime date, long minutos) {
		return date.plusMinutes(minutos);
	}

	/**
	 * Retorna la diferencia de dias entre una fecha mayor y una fecha menor.
	 * 
	 * @param menor Fecha de tipo java.util.Date fecha menor
	 * @param mayor Fecha de tipo java.util.Date fecha mayor
	 * @return int numero de dias de diferencia
	 */
	public static int restaFechas(OffsetDateTime menor, OffsetDateTime mayor) {
		int diferencia = 0;
		OffsetDateTime temp = OffsetDateTime.of(mayor.toLocalDate(), mayor.toLocalTime(), mayor.getOffset());
		while (temp.isAfter(menor)) {
			temp = addDays(temp, -1);
			diferencia++;
		}
		return diferencia;
	}

	/**
	 * Adiciona un nï¿½mero de dï¿½as calendario a una fecha dada como tipo
	 * java.util.Date.
	 * 
	 * @param date Fecha de tipo java.util.Date
	 * @param dias Nï¿½mero de dï¿½as calendario
	 * @return fecha con los nuevos valores
	 */
	public static OffsetDateTime addDays(OffsetDateTime date, long dias) {
		return date.plusDays(dias);
	}

	/**
	 * ** Devuelve el n&uacute;mero de d&iacute;as de diferencia entre 2 fechas.
	 *
	 * @param date1 the date1
	 * @param date2 the date2
	 * @return Retorna un int con los d&iacute;as de diferencia
	 */
	public static long restarFechas(OffsetDateTime date1, OffsetDateTime date2) {
		Duration diff = Duration.between(date1, date2);
		return diff.toDays();
	}

	/**
	 * Mes.
	 *
	 * @param date el date
	 * @return the integer
	 */
	public static Integer mes(OffsetDateTime date) {
		if (date == null) {
			return null;
		} else {
			return date.getMonth().getValue() + 1;
		}
	}

	/**
	 * Anio.
	 *
	 * @param date el date
	 * @return the integer
	 */
	public static Integer anio(OffsetDateTime date) {
		if (date == null) {
			return null;
		} else {
			return date.getYear() + 1;
		}
	}

	/**
	 * Dia.
	 *
	 * @param date el date
	 * @return the integer
	 */
	public static Integer dia(OffsetDateTime date) {
		if (date == null) {
			return null;
		} else {
			return date.getDayOfMonth() + 1;
		}
	}

	/**
	 * ** Devuelve una fecha a la que se le suma un n&uacute;mero de entero de
	 * meses.
	 *
	 * @param date  the date
	 * @param sumar the sumar
	 * @return Retorna un Date con el n&uacute;mero de meses que se le ha agregado
	 */
	public static OffsetDateTime sumarMes(OffsetDateTime date, long sumar) {
		return date.plusMonths(sumar);
	}

	/**
	 * Obtiene dia semana.
	 *
	 * @param d el d
	 * @return dia semana
	 */
	public static int getDiaSemana(OffsetDateTime d) {
		return d.getDayOfWeek().getValue();
	}

	/**
	 * Obtiene nombre dia semana.
	 *
	 * @param d el d
	 * @return nombre dia semana
	 */
	public static DiaSemanaType getNombreDiaSemana(OffsetDateTime d) {
		int dia = getDiaSemana(d) + 1;
		DiaSemanaType resultado = null;
		switch (dia) {
		case 1:
			resultado = DiaSemanaType.DOMINGO;
			break;
		case 2:
			resultado = DiaSemanaType.LUNES;
			break;
		case 3:
			resultado = DiaSemanaType.MARTES;
			break;
		case 4:
			resultado = DiaSemanaType.MIERCOLES;
			break;
		case 5:
			resultado = DiaSemanaType.JUEVES;
			break;
		case 6:
			resultado = DiaSemanaType.VIERNES;
			break;
		case 7:
			resultado = DiaSemanaType.SABADO;
			break;
		}
		return resultado;
	}

	/**
	 * Obtener fecha actual concatenada.
	 *
	 * @return the string @ the parse exception
	 */
	public static String obtenerFechaActualConcatenada() {
		return obtenerFechaFormatoPersonalizado(OffsetDateTime.now(), "yyyyMMdd");
	}

	public static boolean estaDentroRango(OffsetDateTime fechaComparar, OffsetDateTime fechaIntervalo1,
			OffsetDateTime fechaIntervalo2) {
		boolean respuesta = false;
		respuesta = (fechaComparar.isEqual(fechaIntervalo1) || fechaComparar.isAfter(fechaIntervalo1))
				&& (fechaComparar.isEqual(fechaIntervalo1) || fechaComparar.isAfter(fechaIntervalo2));
		return respuesta;
	}

	public static long restarFechasHora(OffsetDateTime date1, OffsetDateTime date2) {
		long respuesta = 0L;
		/*
		 * GregorianCalendar gDate1 = new GregorianCalendar(2000,01,01);
		 * gDate1.setTime(date1); GregorianCalendar gDate2 = new
		 * GregorianCalendar(2000,01,01); gDate2.setTime(date2);
		 * 
		 * if (gDate1.get(Calendar.YEAR) == gDate2.get(Calendar.YEAR)) { respuesta =
		 * gDate2.get(Calendar.HOUR_OF_DAY) - gDate1.get(Calendar.HOUR_OF_DAY); } else {
		 * int diasAnyo = gDate1.isLeapYear(gDate1.get(Calendar.YEAR)) ? 366 : 365; int
		 * rangoAnyos = gDate2.get(Calendar.YEAR) - gDate1.get(Calendar.YEAR); int rango
		 * = ((rangoAnyos * diasAnyo) * 24) + (gDate2.get(Calendar.HOUR_OF_DAY) -
		 * gDate1.get(Calendar.HOUR_OF_DAY)); respuesta = rango; }
		 */
		return respuesta;
	}

}
