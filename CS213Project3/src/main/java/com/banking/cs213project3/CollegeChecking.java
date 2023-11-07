package com.banking.cs213project3;
/**
 * CollegeChecking class specifies the monthly interest and fees associated with this type of account.
 * @author Michael Kassie, Donald Yubeaton
 */
public class CollegeChecking extends Account{

    private Campus campus;
    private final double MONTHLY_INTEREST_RATE= (0.01)/12;
    private final double MONTHLY_FEE= 0.0;

    /**
     * no arg constructor of CollegeChecking calling the constructor of Account to inherit its properties.
     */
    public CollegeChecking()
    {
        super();
    }

    /**
     * CollegeChecking constructor calling the Account constructor to set the specified profile, balance and campus
     * @param holder the profile of the account holder
     * @param balance the balance of the checking account that is opened.
     * @param campus the campus of the account.
     */
    public CollegeChecking(Profile holder, double balance, Campus campus)
    {
        super(holder, balance);
        this.campus = campus;
    }
    /**
     * Clone Constructor
     */
    public CollegeChecking(CollegeChecking collegeChecking)
    {
        super(collegeChecking.getProfile(), collegeChecking.getBalance());
        this.campus = collegeChecking.getCampus();
    }



    /**
     * Returns the monthly interest for the college checking account instance
     * @return MONTHLY_INTEREST_RATE * balance the monthly interst for the account.
     */
    public double monthlyInterest()
    {
        return MONTHLY_INTEREST_RATE * balance;
    }

    /**
     * Sets the campus to the specific one for the college checking account instance.
     * @param campus the specific campus value as a string.
     */
    public void setCampus(Campus campus)
    {
        this.campus = campus;
    }

    /**
     * Returns the specific campus for the  college checking account instance.
     * @return the campus value as a String of the account instance.
     */
    public Campus getCampus() {
        return campus;
    }

    /**
     * This method returns the monthly fee of the college checking account.
     * @return MONTHLY_FEE of the college checking account.
     */
    public double monthlyFee()
    {
        return MONTHLY_FEE;
    }

    /**
     * Updates the balance of the college checking account instance by adding the monthly interest and
     * subtracting the monthly fee.
     */
    public void updateBalance()
    {
        balance += monthlyInterest();
        balance -= monthlyFee();
    }

    /**
     * compares the account types and profile types if the accounts are the same to return -1,0, 0r 1.
     * @param account the object to be compared.
     * @return -1 if profile or account type return -1, 0 if they are the same, or 1 if profile or account type return 1.
     */
    @Override
    public int compareTo(Account account)
    {
        if(this.getClass() == account.getClass())
        {
            return this.getProfile().compareTo(account.getProfile());
        }
        else
        {
            return this.getClass().getName().compareTo(account.getClass().getName());
        }
    }
    /**
     * This method overrides the String method of the Account class and the Object class.
     * @return a string representation of the college checking account instance.
     */
    @Override
    public String toString()
    {
        return "College Checking::" + holder.toString() + "::Balance $" + formatBalance() + "::" + getCampus();
    }

    /**
     * This method checks if the object passed in the argument and the college checking account instance are equal or not.
     * Overrides the equals method of the Account class and The object class.
     * @param o the object instance to be compared to the college checking account instance.
     * @return true if o and the college checking account instance are equal or false if they are not.
     */
    @Override
    public boolean equals(Object o)
    {
        if(o == this)
        {
            return true;
        }
        if(!(o instanceof CollegeChecking))
        {
            return false;
        }

        CollegeChecking collegeChecking = (CollegeChecking)o;
        return this.getProfile().equals(collegeChecking.getProfile());
    }

}
