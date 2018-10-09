package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * @author adjscent
 */
public class Schedule {
    /**
     * Example
     * 0 0 0 0 0 0 0 0 1 1 0 0 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 0 0 1 1 1 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 0 0 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 0 0 1 1 1 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
     * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
     */

    public static final String MESSAGE_SCHEDULE_CONSTRAINTS = "Schedule should be in 0 or 1s";
    private static final int DAY = 7;
    private static final int HOUR = 48;
    private static final int TOTAL = DAY * HOUR;

    // 7 days 24 hours - 48 30mins
    public final int[][] value;

    public Schedule() {
        value = new int[DAY][HOUR];
    }

    public Schedule(String schedule) {
        requireNonNull(schedule);
        assert (schedule.length() == TOTAL);
        value = new int[DAY][HOUR];

        for (int i = 0, counter = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                value[i][j] = Integer.parseInt(schedule.charAt(counter) + "");
                counter += 1;
            }
        }
    }

    public static boolean isValidSchedule(String trimmedSchedule) {
        return trimmedSchedule.length() == TOTAL;
    }

    /**
     * @param day
     * @param time
     * @return
     */
    public boolean getTimeDay(String day, String time) {

        // day Monday
        // time 0800

        int dayNum = -1;
        dayNum = getDayNum(day, dayNum);


        int hourNum = Integer.parseInt(time.substring(0, 2));

        int minNum = Integer.parseInt(time.substring(2, 4));

        int timeNum = hourNum * 2;

        if (minNum >= 30) {
            return value[dayNum][timeNum + 1] == 1 ? true : false;
        } else {
            return value[dayNum][timeNum] == 1 ? true : false;
        }


    }

    /**
     * @param day
     * @param time
     * @param occupied
     */
    public void setTimeDay(String day, String time, boolean occupied) {

        // day Monday
        // time 0800

        int dayNum = -1;
        dayNum = getDayNum(day, dayNum);

        int timeNum = -1;

        timeNum = Integer.parseInt(time.substring(0, 2)) * 2;

        int minNum = Integer.parseInt(time.substring(2, 4));

        if (minNum >= 30) {
            value[dayNum][timeNum + 1] = occupied ? 1 : 0;
        } else {
            value[dayNum][timeNum] = occupied ? 1 : 0;
        }

    }

    private int getDayNum(String day, int dayNum) {
        switch (day.toLowerCase()) {
        case "monday":
            dayNum = 0;
            break;
        case "tuesday":
            dayNum = 1;
            break;
        case "wednesday":
            dayNum = 2;
            break;
        case "thursday":
            dayNum = 3;
            break;
        case "friday":
            dayNum = 4;
            break;
        case "saturday":
            dayNum = 5;
            break;
        case "sunday":
            dayNum = 6;
            break;
        default:
            break;
        }
        return dayNum;
    }


    /**
     * Allow schedule array to be stored as a string
     *
     * @return
     */
    public String valueToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                sb.append(value[i][j]);
            }
        }
        return sb.toString();
    }

    /** Maxs all possible schedules supplied as parameter
     * @param schedules
     * @return
     */
    public static Schedule maxSchedule(Schedule ... schedules) {
        Schedule newSchedule = new Schedule();

        // using for each loop to display contents of a
        for (Schedule s: schedules) {
            newSchedule.union(s);
        }

        return newSchedule;
    }


    /** ORs the Schedules
     * @param schedule
     */
    private void union(Schedule schedule) {
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                this.value[i][j] = (this.value[i][j] | schedule.value[i][j]);
            }
        }
    }


    /** Get the negate of the schedule
     *
     */
    private void negate() {
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                this.value[i][j] = ~(this.value[i][j]);
            }
        }
    }

    /** Use the updateschedule as a bit flipper
     *
     */
    public void xor(Schedule updateSchedule) {
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                this.value[i][j] ^= (updateSchedule.value[i][j]);
            }
        }
    }
}
