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
        // Unix epoch, gotta start somewhere...
        int baseYear = 1970;
        int baseMonth = 1;
        int baseFirstDay = 5;   // Thursday
        int daysSinceFirst = 0;

        if (month == baseMonth && year == baseYear) {
            return baseFirstDay;
        } else {
            for (int i = baseYear; i < year; i++) {
                daysSinceFirst += daysInYear(i);
            }
            for (int i = 1; i < month; i++) {
                daysSinceFirst += daysInMonth(i, year);
            }
            return (daysSinceFirst + baseFirstDay) % 7;
        }
    }

    /**
     * Return the number of days in a month.
     *
     * @param month - Calculate days for this month, January is 1.
     * @param year - Of this year.
     * @return integer number of days, checking for leap year.
     */
    private static int daysInMonth(int month, int year) {
        switch (month) {
            case 2:
                if (isLeapYear(year)) {
                    return 29;
                } else {
                    return 28;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    private static int daysInYear(int year) {
        if (isLeapYear(year)) {
            return 366;
        } else {
            return 365;
        }
    }

    /**
     * Determine if this is a leap year.
     *
     * @param year - Year to check.
     * @return true if year is a leap year, else false.
     */
    private static boolean isLeapYear(int year) {
        return year % 400 == 0 || (year % 100 != 0 && year % 4 == 0);
    }

    /**
     * Print the header lines for a month.
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
     * @param start - First day of month, Sunday is 1.
     * @param days - Number of days in the month.
     */
    private static void printDays(int start, int days) {
        for (int day = -(start - 2); day <= days; day++) {
            if (day < 1) {
                // Empty space before start of month.
                System.out.printf("     ");
            } else if (day <= (7 - (start - 1))) {
                // Possibly short first week.
                System.out.printf("%4d%c", day, day == (7 - (start - 1)) ? '\n' : ' ');
            } else {
                // Full and last weeks.
                System.out.printf("%4d%c", day, (day - (7 - (start - 1))) % 7 == 0 ? '\n' : ' ');
            }
        }
        System.out.printf("\n");
    }
}
