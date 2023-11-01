package com.banking.cs213project3;
/**
 * Enum representing the possible campuses for the CollegeChecking account
 * @author Michael Kassie, Donald Yubeaton
 */
public enum Campus {
    NEW_BRUNSWICK("NEW_BRUNSWICK" , 0),
    NEWARK("NEWARK" , 1),
    CAMDEN("CAMDEN" , 2);

    private final String name;

    private final int campusCode;


    /**
     * Enum Constructor
     * @param name Campus name
     * @param campusCode Campuse code
     */
    Campus(String name, int campusCode)
    {
        this.name = name;
        this.campusCode = campusCode;
    }

    /**
     * Returns the name of the campus
     * @return the name of the campus
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the code of the campus
     * @return the code of the campus
     */
    public int getCampusCode()
    {
        return campusCode;
    }


}
