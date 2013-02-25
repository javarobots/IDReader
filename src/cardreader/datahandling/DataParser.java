
package cardreader.datahandling;

import java.text.DecimalFormat;
import org.joda.time.DateTime;

/**
 *
 * @author javarobots
 */
public class DataParser {

    public int parseID(String capturedString) {
        if (capturedString.length() < 6){
            return 0;
        }
        StringBuilder builder = new StringBuilder(capturedString);
        builder.delete(0, 4);
        builder.delete(builder.length()-3, builder.length());
        try {
            return Integer.parseInt(builder.toString());
        } catch (NumberFormatException ex){
            return 0;
        }
    }

    public String properGreeting(int hour) {
        String greeting = null;
        if (hour < 12 ){
            greeting = "Good Morning";
        } else if (hour >=12 && hour <17){
            greeting = "Good Afternoon";
        } else {
            greeting = "Good Evening";
        }
        return greeting;
    }

    /**
     * Creates a date string in yyyymmdd format
     * @return
     */
    public String gregorianDateTime() {
        DateTime dt = new DateTime();
        StringBuilder dateString = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#0");
        df.setMinimumIntegerDigits(2);
        String year = Integer.toString(dt.getYear());
        String month = df.format(dt.getMonthOfYear());
        String day = df.format(dt.getDayOfMonth());
        String hour = df.format(dt.getHourOfDay());
        String minute = df.format(dt.getMinuteOfHour());
        String second = df.format(dt.getSecondOfMinute());
        dateString.append(year).append(month).append(day).append(hour).append(minute).append(second);
        return dateString.toString();
    }

    public String recordDateTime() {
        DateTime dt = new DateTime();
        StringBuilder dateString = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#0");
        df.setMinimumIntegerDigits(2);
        String year = Integer.toString(dt.getYear());
        String month = df.format(dt.getMonthOfYear());
        String day = df.format(dt.getDayOfMonth());
        String hour = df.format(dt.getHourOfDay());
        String minute = df.format(dt.getMinuteOfHour());
        String second = df.format(dt.getSecondOfMinute()) + "0";
        dateString.append(year).append("/").append(month).append("/").append(day);
        dateString.append("  ");
        dateString.append(hour).append(":").append(minute).append(":").append(second);
        return dateString.toString();
    }

}
