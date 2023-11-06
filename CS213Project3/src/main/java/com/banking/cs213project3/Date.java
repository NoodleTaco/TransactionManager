package com.banking.cs213project3;
import java.util.Calendar;
/**
 * An instance of this class holds a date
 * Provides multiple methods that check if a date follows criteria to be considered valid
 * @author Donald Yubeaton, Michael Kassie
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUARTERCENTENNIAL = 400;

    // Add 7 instead of 6 to account for Calendar using 0-based months
    public static final int WITHINSIXMONTHS = 7;

    public static final int MONTH_JANUARY = 1;

    public static final int MONTH_FEBRUARY = 2;

    public static final int MONTH_APRIL = 4;

    public static final int MONTH_JUNE = 6;

    public static final int MONTH_SEPTEMBER = 9;

    public static final int MONTH_NOVEMBER = 11;

    public static final int MONTH_DECEMBER = 12;

    public static final int FEBRUARY_DAYS = 28;

    public static final int FEBRUARY_LEAP_DAYS = 29;

    public static final int THIRTY_DAYS = 30;

    public static final int THIRTY_ONE_DAYS = 31;

    /**
     * Parameterized Constructor
     *
     * @param month the Date's month
     * @param day   the Date's day
     * @param year  the Date's year
     */
    public Date(int month, int day, int year) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Checks if a Date is a valid calendar date
     *
     * @return true if the date is a valid calendar date, false otherwise
     */
    public boolean isValid() {

        boolean leap = isLeap(this);
        if (this.month == MONTH_FEBRUARY) {
            if (leap) {
                return this.day >= 1 && this.day <= FEBRUARY_LEAP_DAYS;
            } else {
                return this.day >= 1 && this.day <= FEBRUARY_DAYS;

            }
        }
        if (this.month < MONTH_JANUARY || this.month > MONTH_DECEMBER) {
            return false;
        } else if (month == MONTH_APRIL || month == MONTH_JUNE || month == MONTH_SEPTEMBER || month == MONTH_NOVEMBER) {
            return this.day <= THIRTY_DAYS && this.day >= 1;
        } else {
            return this.day <= THIRTY_ONE_DAYS && this.day >= 1;
        }
    }

    /**
     * Checks if a date occurs during a leap year
     *
     * @param date the date being checked
     * @return true if the date occurs during a leap year, false otherwise
     */
    public static boolean isLeap(Date date) {
        if (date.year % QUADRENNIAL != 0) {
            return false;

        }
        if (date.year % CENTENNIAL == 0) {
            if (date.year % QUARTERCENTENNIAL != 0) {
                return false;
            }
        } else {
            return true;
        }
        return false;

    }


    /**
     * Returns the Date's year
     *
     * @return the Date's year
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the Date's month
     *
     * @return the Date's month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Returns the Date's day
     *
     * @return the Date's day
     */
    public int getDay() {
        return day;
    }

    /**
     * Sets the Date's day
     *
     * @param day the new day value for the Date
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Sets the Date's month
     *
     * @param month the new month value for the Date
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Sets the Date's year
     *
     * @param year the new year value for the Date
     */
    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int compareTo(Date o) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.set(Calendar.MONTH, o.getMonth());
        c1.set(Calendar.YEAR, o.getYear());
        c1.set(Calendar.DAY_OF_MONTH, o.getDay());

        c2.set(Calendar.MONTH, this.getMonth());
        c2.set(Calendar.YEAR, this.getYear());
        c2.set(Calendar.DAY_OF_MONTH, this.getDay());


        return c2.compareTo(c1);
    }


    /**
     * Checks if the date is a future date
     *
     * @return true if the date is a future date, false otherwise
     */
    public boolean isPast() {
        Calendar b = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        b.set(this.getYear(), this.getMonth() - 1, this.getDay());
        return c.compareTo(b) > 0;


    }

    /**
     * Returns the number of years old someone with the date as their date of birth would be
     * @return The date's age
     */
    public int getYearsOld()
    {
        Calendar dob = Calendar.getInstance();

        dob.set(this.getYear(), this.getMonth() - 1, this.getDay());

        Calendar currentDate = Calendar.getInstance();

        int age = currentDate.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        // Check if the birthdate hasn't occurred this year yet
        if (currentDate.get(Calendar.MONTH) < dob.get(Calendar.MONTH) ||
                (currentDate.get(Calendar.MONTH) == dob.get(Calendar.MONTH) &&
                        currentDate.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }
        return age;
    }

    /**
     * Checks if the date occurs within 6 months
     *
     * @return true if the date occurs within 6 months, false otherwise
     */
    public boolean notLate() {
        Calendar date = Calendar.getInstance();
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.MONTH, WITHINSIXMONTHS);
        date.set(Calendar.MONTH, this.getMonth());
        date.set(Calendar.YEAR, this.getYear());
        date.set(Calendar.DAY_OF_MONTH, this.getDay());


        return date.before(currentDate);
    }

    /**
     * Returns a string representation of a date in form month/day/year
     *
     * @return a string representation of a date
     */
    @Override
    public String toString() {

        String s;
        s = month + "/" + day + "/" + year;
        return s;
    }

    /**
     * Compares if a date object equals another date object
     *
     * @param obj the date argument to be compared to
     * @return true if the dates are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            Date date = (Date) obj; //type casting from Object to Student
            return this.year == date.year && this.month == date.month && this.day == date.day;
        }
        return false;
    }
}

