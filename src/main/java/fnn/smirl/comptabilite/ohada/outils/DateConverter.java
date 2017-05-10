package fnn.smirl.comptabilite.ohada.outils;
import java.text.*;
import java.util.*;

public class DateConverter {
	public static long convertToLong(String date, String sformat)throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		return	(formatter.parse(date)).getTime();
	}

	public static String convertToString(long date, String sformat) {
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		return	formatter.format(new Date(date));
	}
}
