package br.com.devgrid.percentagebreakd.util;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Utilitário para manipulação de datas e horas
 * 
 * @author Cesar Buzin
 */
public class DateTimeUtil {

	/**
	 * Busca hora de um timestamp
	 * 
	 * @param timestamp
	 * @return
	 */
	public static Integer getHour(Timestamp timestamp) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(timestamp);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Cria Timestamp baseado na hora e minutos informados
	 * 
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static Timestamp createTimestamp(int hour, int minute) {
		
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		
		return new Timestamp(cal.getTimeInMillis());
	}
}
