package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TimeAndDateUtils {

	public static String GetCurrentDate() {

	        LocalDate localDate = LocalDate.now();

	        return DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate);

	    }

	public static int GetDaysLeft(String aDate) {
		
        // Input date
        
        String[] aValues = aDate.split("/");
        int aDay = Integer.parseInt(aValues[2]);
        int aMonth = Integer.parseInt(aValues[1]);
        int aYear = Integer.parseInt(aValues[0]);

        // Current date

        String date = DateTimeFormatter.ofPattern("yyy/MM/dd").format(LocalDate.now());

        String[] values = date.split("/");
        int day = Integer.parseInt(values[2]);
        int month = Integer.parseInt(values[1]);
        int year = Integer.parseInt(values[0]);
        
        // Passed days
        
        int passedDays = (day - aDay) + (month - aMonth)*31 + (year - aYear)*365;
        
        return passedDays;

    }

}
