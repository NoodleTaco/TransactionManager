package com.banking.cs213project3;
/**
 * The profile class specifies the name and date of birth for a given account.
 * @author Michael Kassie, Donald Yubeaton
 */
public class Profile implements Comparable<Profile>{
    private String fname;
    private String lname;
    private Date dob;

    /**
     * no argument constructor for the Profile class.
     */
    public Profile()
    {}

    /**
     * constructor for the profile class specifying all the values for the instance variables.
     * @param fname the first name of the account holder.
     * @param lname the last name of the account holder.
     * @param dob the date of birth of the account holder.
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname=lname;
        this.dob=dob;
    }

    /**
     * Returns that last name of the profile holder instance.
     * @return lname as a string, the last name of the profile instance.
     */
    public String getLname() {
        return lname;
    }

    /**
     * sets the last name of the profile holder to a specified string value.
     * @param lname the last name value as a string.
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * Returns that first name of the profile holder instance.
     * @return fname as a string, the last name of the profile instance.
     */
    public String getFname() {
        return fname;
    }

    /**
     * sets the first name of the profile holder to a specified string value.
     * @param fname the last name value as a string.
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * Returns the dob for the profile instance.
     * @return dob the of the profile.
     */
    public Date getDob() {
        return dob;
    }

    /**
     * sets the dob to a certain value of the profile instance.
     * @param dob the value that dob has to be set to for the profile.
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Returns the String value for the profile instance.
     * @return string representation of the profile instance.
     */
    @Override
    public String toString()
    {
        return fname + " " + lname + " " + dob.toString();
    }

    /**
     * compares the object passed and the profile instance to see if they are equal.
     * @param o the object that is passed as an argument.
     * @return true if the objects are equal or false it they are not.
     */
    @Override
    public boolean equals(Object o)
    {
        if(o == this)
        {
            return true;
        }
        if(!(o instanceof Profile))
        {
            return false;
        }

        Profile profile = (Profile)o;
        return this.fname.equalsIgnoreCase(profile.getFname()) && this.lname.equalsIgnoreCase(profile.getLname()) && this.dob.equals(profile.getDob());

    }

    /**
     * Compares the profile passed and the profile instance based on the names and the dob, and returns an integer.
     * @param profile the object to be compared.
     * @return -1 if the names come first alphabetically or the dob comes first for the profile that calls it,
     * 0 if they are equal, or 1.
     */
    @Override
    public int compareTo(Profile profile)
    {
        if(this.lname.compareTo(profile.getLname()) == 0)
        {
            if(this.fname.compareTo(profile.getFname()) == 0)
            {
                return this.dob.compareTo(profile.getDob());
            }
            else
            {
                return this.fname.compareTo(profile.getFname());
            }
        }
        else
        {
            return this.lname.compareTo(profile.getLname());
        }
    }
}