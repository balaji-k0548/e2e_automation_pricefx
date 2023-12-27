package com.syscolab.qe.core.util.dates;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * This is the Util class for Date related functions
 * @author  Sandeep Perera
 */
public class DateUtils {
    /**
     * Default constructor of the class
     */
    private DateUtils(){}

    /**
     * This will return the current date in MM/dd/yy format
     * @return current date
     */
    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String currentDate = sdf.format(date);
        return currentDate;
    }

    /**
     * This will return a future date in MM/dd/yy format
     * @param numOfDays number of Days for future date
     * @return current date
     */
    public static String getFutureDate(int numOfDays) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, numOfDays);
        date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        String futureDate = sdf.format(date);

        return futureDate;
    }

    /**
     * This will return a future date with the passed date format
     * @param numOfDays number of Days for future date
     * @param strFormat date format
     * @return future date
     */
    public static String getFutureDate(int numOfDays, String strFormat) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, numOfDays);
        date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
        String futureDate = sdf.format(date);

        return futureDate;
    }

    /**
     * This will get Date of the Week of Given Date in the format of MM/dd/yyyy
     * @param date date
     * @return day of Week
     */
    public static String getDayOfWeekOfGivenDate(String date) {
        String dayOfWeek = "";
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
            Date dt1 = format1.parse(date);
            DateFormat format2 = new SimpleDateFormat("EEEE");
            dayOfWeek = format2.format(dt1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayOfWeek;
    }

    // swms REG-103 related

    /**
     * This will return Date from time Zone ID in the format of MM/dd/yyyy
     * @param timeZoneId time zone ID
     * @return date from zone id
     */
    public static String getDateFromZoneId(String timeZoneId) {
        SimpleDateFormat DateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date1 = new Date();
        DateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId));
        return DateFormat.format(date1);
    }

    /**
     * This will return the date in different format
     * @param date date
     * @param inptFormat current format
     * @param outFormat output format
     * @return date in output format
     */
    public static String getDateInDiffFormat(String date, String inptFormat, String outFormat) {
        String date1 = "";
        try {
            SimpleDateFormat format1 = new SimpleDateFormat(inptFormat);
            Date dt1 = format1.parse(date);
            DateFormat format2 = new SimpleDateFormat(outFormat);
            date1 = format2.format(dt1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date1;
    }

    /**
     * This will return the time by subtracting passed time
     * @param time time
     * @param numberOfHoursToSubtract number of hours to subtract
     * @param inputFormat current format
     * @param outFormat output format
     * @return minus time
     */
    public static String getMinusTime(String time, int numberOfHoursToSubtract, String inputFormat, String outFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(inputFormat);
        LocalTime datetime = LocalTime.parse(time,formatter);
        datetime=datetime.minusHours(numberOfHoursToSubtract);
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(outFormat);
        return datetime.format(formatter2);
    }
}
