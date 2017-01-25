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
        // Unix epoch, gotta start somewhere...
        int month = 1;
        int year = 1970;

        switch (args.length) {
            case 0:
                // No command line arguments, generate calendar for current month.
                Calendar now = Calendar.getInstance();
                year = now.get(Calendar.YEAR);
                month = now.get(Calendar.MONTH) + 1; // Result is zero based, we are using 1 for January.
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
                System.out.printf("%s: don't know what to do with that much information\n", "Cal");
                System.exit(-1);
        }
        System.out.printf("args = %d month = %d year = %d\n", args.length, month, year);

        if (month == 0) {
            printYear(year);
        } else {
            printMonth(month, year);
        }
        System.exit(0);
    }

    private static void printMonth(int month, int year) {
        printMonthHeader(month, year);
        printDays(4, daysInMonth(month, year));
    }

    private static void printYear(int year) {
        for (int i = 0; i < 12; i++) {
            printMonth(i + 1, year);
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
     * Print ASCII calendar for a month.
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
    }
}
