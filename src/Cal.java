import javax.swing.*;
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

    /*
     * Most of what happens here is deciding what to do. If we are called with no arguments
     * we print the calendar for the current month, with one argument we print the calendar
     * for that year, and with two the calendar for the month/year combination.
     */
    public static void main(String[] args) {
        int month = 0;
        int year = 0;

        System.setErr(System.out);

        switch (args.length) {
            case 0:
                // No command line arguments, print calendar for current month.
                Calendar now = Calendar.getInstance();
                month = now.get(Calendar.MONTH) + 1; // They January is 0, we say 1.
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
                // Anything else is an error.
                System.err.printf("%s: don't know what to do with that much information.", "Cal");
                System.exit(-1);
        }

        if (year < 1587) {  // TODO: make a constant.
            System.err.printf("%s: the year %d is before my time.", "Cal", year);
            System.exit(-2);
        }

        if (month == 0) {
            printYear(year);
        } else {
            printMonth(month, year);
        }
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
     * @param year - year to generate calendar for.
     */
    private static void printYear(int year) {
        for (int i = 1; i <= 12; i++) {
            printMonth(i, year);
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

    /**
     * Return number of days in the year.
     *
     * @param year - Year of interest.
     * @return - Number of days.
     */
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
        System.out.printf("\n");
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

    }
}