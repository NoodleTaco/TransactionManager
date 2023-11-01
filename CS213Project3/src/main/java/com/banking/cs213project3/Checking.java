package com.banking.cs213project3;
/**
 * The checking class extends the account class and implements monthly interest rates and
 * fees for this type of account
 * @author Michael Kassie, Donald Yubeaton
 */
public class Checking extends Account {

    private final double MONTHLY_INTEREST_RATE= (0.01)/12;
    private final double MONTHLY_FEE= 12.0;

    static final int C_MONTHLY_FEE_THRESHOLD = 1000;


    /**
     * no arg constructor of Checking calling the constructor of Account to inherit its properties.
     */
    public Checking()
    {
        super();
    }

    /**
     * Checking constructor calling the Account constructor to get the specified profile anf double balance
     * to create a checking account.
     * @param holder the profile of the account holder
     * @param balance the balance of the checking account that is opened.
     */
    public Checking(Profile holder, double balance)
    {
        super(holder, balance);
    }

    /**
     * This method returns the monthly interest rate for a checking account.
     * @return monthly interest(1.0/12) for the checking account
     */
    public double monthlyInterest()
    {
        return MONTHLY_INTEREST_RATE * balance;
    }

    /**
     * This method returns the monthly interest rate for a checking account.
     * @return 0 for the checking account if the balance is greater than or equal to 1000 or else return monthly fee (12.0).
     */
    public double monthlyFee()
    {
        if (this.balance>= C_MONTHLY_FEE_THRESHOLD){
            return 0;
        }
        return MONTHLY_FEE;
    }

    /**
     * This method updates the balance by adding the monthly interest and subtracting the monthly fee.
     */
    public void updateBalance()
    {
        balance += monthlyInterest();
        balance -= monthlyFee();
    }

    /**
     * This method overrides the String method of the Account class and the Object class.
     * @return a string representation of the checking account instance.
     */
    @Override
    public String toString()
    {
        return "Checking::" + holder.toString() + "::Balance $" + formatBalance();
    }

    /**
     * This method checks if the object passed in the argument and the checking account instance are equal or not.
     * Overrides the equals method of the Account class and The object class.
     * @param o the object instance to be compared to the checking account instance.
     * @return true if o and the checking account instance are equal or false if they are not.
     */
    @Override
    public boolean equals(Object o)
    {
        if(o == this)
        {
            return true;
        }
        if(!(o instanceof Checking))
        {
            return false;
        }

        Checking checking = (Checking)o;
        return this.getProfile().equals(checking.getProfile());
    }

    /**
     * compares the account types and profile types if the accounts are the same to return -1,0, 0r 1.
     * @param account the object to be compared.
     * @return -1 if profile or account type return -1, 0 if they are the same, or 1 if profile or account type return 1.
     */
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
}
