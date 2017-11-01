package br.gov.pi.tce.whitedragon.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.function.Predicate;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.swing.text.MaskFormatter;

public class Utils {

	public static HttpSession getSession(){
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static String formatarString(String pattern, String value) {
        MaskFormatter mask;
        try {
            mask = new MaskFormatter(pattern);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

	public static String subString(String string, int n) {
		//return string.concat(StringUtils.repeat(" ",n)).substring(0, n);
		return string.concat(new String(new char[n]).replace("\0"," ")).substring(0, n);
	}

	public static String formatarData(Date data) {
		return formatarData(data, "dd/MM/yyy");	
	}

	public static String formatarDataHora(Date data) {
		return formatarData(data, "dd/MM/yyy hh:mm");	
	}
	
	public static String formatarData(Date data, String formato) {
		if (data == null) return null;
		SimpleDateFormat sdf = new SimpleDateFormat(formato); // Set your date format
		return sdf.format(data); // Get Date String according to date format 
	}

	public static Date agora() {
		return  Calendar.getInstance().getTime();
	}

	public static Date dataHoje() {
		Calendar c = new GregorianCalendar();
	    c.set(Calendar.HOUR_OF_DAY, 23);
	    c.set(Calendar.MINUTE, 59);
	    c.set(Calendar.SECOND, 59);
	    return c.getTime();
	}

	public static int diasCorridosEntreDatas(Date dataMenor, Date dataMaior) {
		int dias = 0;
		if (dataMenor != null && dataMaior != null) {
			LocalDate dataInicial       = new java.sql.Date(dataMenor.getTime()).toLocalDate();
			LocalDate dataFinal   		= new java.sql.Date(dataMaior.getTime()).toLocalDate();
			dias = Long.valueOf(ChronoUnit.DAYS.between(dataInicial,dataFinal)).intValue()+1; 
		}
		return dias;
	}

	public static int diasCorridosEntreDatasExcluindoFimDeSemana(final Date dataMenor, final Date dataMaior) {
		
		LocalDate start     = new java.sql.Date(dataMenor.getTime()).toLocalDate();
		LocalDate end   	= new java.sql.Date(dataMaior.getTime()).toLocalDate();
		
		List<DayOfWeek> ignore = Arrays.asList(DayOfWeek.SATURDAY,DayOfWeek.SUNDAY);
	    int count = 0;
	    LocalDate curr = start.plusDays(0);

	    while (curr.isBefore(end)) {
	        if (!ignore.contains(curr.getDayOfWeek())) {
	            count++;
	        }
	        curr = curr.plusDays(1); // Increment by a day.
	    }
	    return count;
	}

	public static boolean dateBetweenInclusive(Date dt, Date dt1, Date dt2) {
		Date dtStart, dtEnd;
		if (dt1.compareTo(dt2) < 0 ) {
			dtStart = dt1;
			dtEnd = dt2;
		} else {
			dtStart = dt2;
			dtEnd = dt1;			
		}
		return dtStart.compareTo(dt) * dt.compareTo(dtEnd) >= 0;
	}

	public static String humanReadableByteCount(long bytes, boolean si) {
	    int unit = si ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	public static <T> List<T> filter(List<T> list, Predicate<T> p) {
		List<T> result = new ArrayList<>();
		for (T e : list) {
			if (p.test(e)) {
				result.add(e);
			}
		}
		return result;
	}

}
