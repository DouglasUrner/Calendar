import javax.swing.*;
import javax.swing.text.Style;
import java.util.Calendar;

/**
 * Something like Unix cal(1) in Java.
 *
 * Created by dlu on 19.01.2017.
 */
public class Cal {
    private static String[] monthNames = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    private static String[] dayNames = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};

    public static void main(String[] args) {
        int month = 0;
        int year = 0;

        System.setErr(System.out);

        switch (args.length) {
            case 0:
                // No command line arguments, print calendar for current month.
                Calendar now = Calendar.getInstance();
                month = now.get(Calendar.MONTH) + 1; // Result is zero based, we are using 1 for January.
                year = now.get(Calendar.YEAR);
                break;
            case 1:
                // Year - print calendar for the whole year.
                month = 0;
                year = Integer.parseInt(args[0]);
                break;
            case 2:
                // Month and Year - print calendar for that month.
                month = Integer.parseInt(args[0]);
                year = Integer.parseInt(args[1]);
                break;
            default:
                System.err.printf("%s: don't know what to do with that much information.", "Cal");
                System.exit(-1);
        }

        if (year < 1970) {
            // TODO: use baseYear. Make it global?
            System.err.printf("%s: the year %d is before my time.", "Cal", year);
            System.exit(-2);
        }

        if (month == 0) {
            printYear(year);
        } else {
            printMonth(month, year);
        }
        System.exit(0);
    }

    /**
     * Print calendar for a month.
     *
     * @param month - this month, January == 1.
     * @param year - of this year.
     */
    private static void printMonth(int month, int year) {
        printMonthHeader(month, year);
        printDays(firstDay(month, year), daysInMonth(month, year));
    }

    /**
     * Print a calendar for a whole year.
     *
     * TODO: use full screen width
     *
     * @param year - year to generate calendar for.
     */
    private static void printYear(int year) {
        for (int i = 0; i < 12; i++) {
            printMonth(i + 1, year);
            System.out.printf("\n");
        }
    }

    /**
     * Determine the day of the week for the first day of the month, Sunday == 1.
     *
     * @param month - this month.
     * @param year - of this year.
     * @return - index of the first day of the week, Sunday == 1.
     */
    private static int firstDay(int month, int year) {
        return 1;
    }

    /**
     * Return the number of days in a month.
     *
     * @param month - Calculate days for this month, January is 1.
     * @param year - Of this year.
     * @return integer number of days, checking for leap year.
     */
    private static int daysInMonth(int month, int year) {
        return 31;
    }

    private static int daysInYear(int year) {
        return 365;
    }

    /**
     * Determine if this is a leap year.
     *
     * @param year - Year to check.
     * @return true if year is a leap year, else false.
     */
    private static boolean isLeapYear(int year) {
        return false;
    }

    /**
     * Print the header lines for a month.
     *
     *   January 2017
     *   Su   Mo   Tu   We   Th   Fr   Sa
     *
     * @param month - Ordinal of month, January is 1.
     * @param year - In this year.
     */
    private static void printMonthHeader(int month, int year) {
        System.out.printf("  %s %d\n", monthNames[month - 1], year);
        for (String d : dayNames) {
            System.out.printf("  %s ", d);
        }
        System.out.println();
    }

    /**
     * Print the days for a calendar for a month.
     *
     *    1    2    3    4    5    6    7
     *    8    9   10   11   12   13   14
     *   15   16   17   18   19   20   21
     *   22   23   24   25   26   27   28
     *   29   30   31
     *
     * @param start - First day of month, Sunday is 1.
     * @param days - Number of days in the month.
     */
    private static void printDays(int start, int days) {
        for (int day = 1; day <= days; day++) {
            /*
             * TL;DR - One way to keep the columns lined up is with System.out.printf(), another
             * is to use System.out.println() and some if statements.
             *
             * System.out.printf() is the deluxe verison of println(). There is a bit of
             * a learning curve, but once you learn how to harness its power you can do
             * some cool stuff. The way it works is that you create a "format string" that
             * is the template for what you want to print out. In the format string placeholders
             * which begin with the percent sign, '%', are used to mark the location of the
             * data you want to output. After the format string come the variables you want
             * to output. The are listed in the same order that their placeholders appear in
             * the format string.
             *
             * The format string here is "%4d%c". The "%4d" part says print an integer, as a
             * decimal number in a field that is padded on the left so that it is a total of
             * four spaces wide, including the number. The first day provides the value that
             * goes in the field. When day is less than 10 the field is padded with four
             * spaces on the left, when it is 10 or greater the padding is two spaces. This
             * keeps the columns of the calendar lined up.
             *
             * The second part of the format string "%c" prints out a character. The expression
             *
             *   day % 7 == 0 ? '\n' : ' '
             *
             * returns an newline character (\n) if day is evenly divisible by 7, or a space if
             * it is not.
             *
             * Unfortunately this only works correctly as written if the month starts on a Sunday,
             * but it is a start.
             */
            System.out.printf("%4d%c", day, day % 7 == 0 ? '\n' : ' ');
        }
        System.out.printf("\n"); // Final newline even if we don't reach the end of the week.
    }
}
