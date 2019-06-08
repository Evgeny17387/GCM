package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TimeAndDateUtils {

	public static String GetCurrentDate() {

	        LocalDate localDate = LocalDate.now();

	        return DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate);

	    }

}
