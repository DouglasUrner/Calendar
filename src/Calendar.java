import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

/**
 * Something like Unix cal(1) in Java.
 *
 * Created by dlu on 19.01.2017.
 */
public class Calendar {
    private static String[] monthNames = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    private static String[] dayNames = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};

    public static void main(String[] args) {
        int month = 2;
        int year = 1904;

        printMonthHeader(month, year);
        printMonth(4, daysInMonth(month, year));
    }

    /**
     * Return the number of days in a month.
     *
     * @param month - Check this month, January is 1.
     * @param year - For this year.
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
     * @param year
     * @return true if year is a leap year, else false.
     */
    private static boolean isLeapYear(int year) {
        return year % 400 == 0 || (year % 100 != 0 && year % 4 == 0);
    }

    /**
     * Print the header lines for a month.
     *
     * @param month - Ordinal of month, January is 1.
     * @param year
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
    private static void printMonth(int start, int days) {
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
