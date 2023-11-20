package pe.buildsoft.erp.core.domain.entidades.nota;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;

public class test {

	public static void main(String[] args) {
		 LocalDate d1 = LocalDate.parse("2020-04-01", DateTimeFormatter.ISO_LOCAL_DATE);
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek( Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek( 4 );
		int numberWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		System.out.println(numberWeekOfYear);
		// TODO Auto-generated method stub
		LocalDate	DATE = LocalDate.now();
		System.out.println(DATE);
		LocalTime	TIME = LocalTime.now();
		System.out.println(TIME);
		OffsetDateTime	TIMESTAMP = OffsetDateTime.now();
		System.out.println("OffsetDateTime-->TIMESTAMP " + TIMESTAMP);
		OffsetTime	TIME_WITH_TIMEZONE = OffsetTime.now();
		System.out.println(TIME_WITH_TIMEZONE);
		OffsetDateTime	TIMESTAMP_WITH_TIMEZONE = OffsetDateTime.now();
		System.out.println(TIMESTAMP_WITH_TIMEZONE);
		//Duration	BIGINT = new Duration();
		//System.out.println(BIGINT);
		OffsetDateTime	TIMESTAMP_INSTANCE = OffsetDateTime.now();
		System.out.println(TIMESTAMP_INSTANCE);
		ZonedDateTime	TIMESTAMPZonedDateTime = ZonedDateTime.now();
		System.out.println(TIMESTAMPZonedDateTime);
		mainx(args);
	}
	
	public static void mainx(String[] args)  {
        LocalDate d1 = LocalDate.parse("2020-05-06", DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate d2 = LocalDate.parse("2020-05-30", DateTimeFormatter.ISO_LOCAL_DATE);

        LocalDate d3 = LocalDate.parse("2018-05-06", DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate d4 = LocalDate.parse("2020-01-23", DateTimeFormatter.ISO_LOCAL_DATE);

        Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
        Period period = Period.between(d3, d4);

        long diffDays = diff.toDays();
        int years = Math.abs(period.getYears());
        int months = Math.abs(period.getMonths());
        int days = Math.abs(period.getDays());
        System.out.println("Diffrence between dates is : "+diffDays + "days");
        System.out.println("Diffrence is : "+years+" year, "+months+" months, "+days+" days");
    }

}
