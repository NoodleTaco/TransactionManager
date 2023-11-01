package com.banking.cs213project3;
/**
 * Savings class specifies the monthly interest and fees associated with this type of account.
 * @author Michael Kassie, Donald Yubeaton
 */
public class Savings extends Account {
    protected boolean isLoyal; //loyal customer status

    private final double MONTHLY_INTEREST_RATE= (0.04)/12;
    private final double MONTHLY_FEE= 25.0;

    private final double LOYAL_MONTHLY_INTEREST_RATE= (0.0425)/12;

    static final int MONTHLY_FEE_THRESHOLD = 500;



    /**
     * no arg constructor of Savings calling the constructor of Account to inherit its properties.
     */
    public Savings()
    {
        super();
    }

    /**
     * Savings constructor calling the Account constructor to set the specified profile, balance and loyalty status
     * @param holder the profile of the account holder
     * @param balance the balance of the checking account that is opened.
     * @param isLoyal the loyalty status of the account
     */
    public Savings(Profile holder, double balance, boolean isLoyal)
    {
        super(holder, balance);
        this.isLoyal = isLoyal;
    }


    /**
     * Clone constructor
     * @param savings The savings account that will be copied
     */
    public Savings(Savings savings)
    {
        super(savings.getProfile(), savings.getBalance());
        this.isLoyal = savings.isLoyal;

    }

    /**
     * Returns the monthly interest of the savings account
     * @return the monthly interest
     */
    public double monthlyInterest()
    {
        if(isLoyal){
            return LOYAL_MONTHLY_INTEREST_RATE * balance;

        }

        return MONTHLY_INTEREST_RATE * balance;
    }

    /**
     * Returns the monthly fee of the savings account
     * @return the monthly fee
     */
    public double monthlyFee()
    {
        if(this.balance>=MONTHLY_FEE_THRESHOLD){
            return 0;

    }
        return MONTHLY_FEE;

    }

    /**
     * Overrides the toString method
     * @return the String representation of the savings account
     */
    @Override
    public String toString()
    {
        return "Savings::" + holder.toString() + "::Balance $" + formatBalance() + loyalString();
    }

    /**
     * Overrides the equals method
     * @param o the object being compared
     * @return true if the accounts are equal, false otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if(o == this)
        {
            return true;
        }
        if(!(o instanceof Savings)|| getClass() != o.getClass())
        {
            return false;
        }

        Savings savings = (Savings)o;
        return this.getProfile().equals(savings.getProfile());
    }

    /**
     * Sets if an account is loyal
     * @param isLoyal The loyalty status of the account
     */
    public void setIsLoyal(boolean isLoyal)
    {
        this.isLoyal = isLoyal;
    }

    /**
     * Returns the loyalty status of the account
     * @return the loyalty status of the account
     */
    public boolean getIsLoyal()
    {
        return isLoyal;
    }

    /**
     * Returns the String representation of the loyalty of an account for printing
     * @return the String representation of the loyalty of an account
     */
    protected String loyalString()
    {
        if(isLoyal)
        {
            return "::is loyal";
        }
        return "";
    }

    /**
     * Updates the balance of the savings account
     */
    public void updateBalance()
    {
        balance += monthlyInterest();
        balance -= monthlyFee();
    }

    /**
     * Overrides the compareTo method
     * @param account the account to be compared.
     * @return -1, 0, or 1, based on the compareTo of the class names or profiles if they are the same
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
}
