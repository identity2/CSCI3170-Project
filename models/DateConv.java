package models;

import java.util.Calendar;

public class DateConv {
    public static String calToStr(Calendar cal) {
        String day = "" + cal.get(Calendar.DAY_OF_MONTH);
        String month = "" + (cal.get(Calendar.MONTH) + 1);
        String year = "" + cal.get(Calendar.YEAR);

        if (day.length() < 2)
            day = "0" + day;
        if (month.length() < 2)
            month = "0" + month;

        while (year.length() < 4)
            year = "0" + year;

        return day + "/" + month + "/" + year;
    }

    public static Calendar strToCal(String str) {
        String[] splitted = str.split("/");
        Calendar cal = Calendar.getInstance();
        int day = Integer.parseInt(splitted[0]);
        int month = Integer.parseInt(splitted[1])-1;
        int year = Integer.parseInt(splitted[2]);
        cal.set(year, month, day);
        return cal;
    }
}