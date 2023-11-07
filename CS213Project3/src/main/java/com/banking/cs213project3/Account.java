package com.banking.cs213project3;
import java.text.DecimalFormat;

/**
 * This is an abstract class which extends in to subclasses that have account information for the various accounts.
 * It contains instance variables holder of the Profile type and balance of the double type.
 * @author Michael Kassie, Donald Yubeaton
 */
public abstract class Account implements Comparable<Account> {
    protected Profile holder;
    protected double balance;

    /**
     * No argument constructor for Account class.
     */
    public Account()
    {
    }

    /**
     * Constructor with parameters for the Account class.
     * @param holder holds the profile of the account holder.
     * @param balance balance in the account of the account holder.
     */
    public Account(Profile holder, double balance)
    {
        this.holder = holder;
        this.balance = balance;
    }



    /**
     * sets the profile of the account instance to specific value.
     * @param profile The profile the account will be set with
     */
    public void setProfile(Profile profile)
    {
        holder = profile;
    }

    /**
     * sets the balance of the account instance to specific value.
     * @param balance The balance the account will be set with
     */
    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    /**
     * returns the holder profile of the account instance.
     * @return holder the profile of the account instance
     */
    public Profile getProfile()
    {
        return holder;
    }

    /**
     * returns the  balance of the account instance.
     * @return balance the balance in the account instance.
     */
    public double getBalance()
    {
        return balance;
    }


    /**
     * Formats the balance of an account instance in a specific decimal format.
     * @return balance as a string in a certain format.
     */
    public String formatBalance()
    {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(balance);
    }

    /**
     * Formats the fees and the interest in a specified format.
     * @return balance and the fees as a string in a specified format.
     */
    public String formatFeesAndInterest()
    {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return "::fee $"+ decimalFormat.format(monthlyFee()) +    "::monthly interest $" + decimalFormat.format(monthlyInterest());
    }

    /**
     * Abstract method that is going to be implemented by the subclasses.
     *
     */
    public abstract double monthlyInterest();

    /**
     * Abstract method that is going to be implemented by the subclasses.
     *
     */
    public abstract double monthlyFee();

    /**
     * Abstract method that is going to be implemented by the subclasses.
     *
     */
    @Override
    public abstract String toString();

    /**
     * Abstract method that is going to be implemented by the subclasses.
     *
     */
    public abstract void updateBalance();



    /**
     * Abstract method that is going to be implemented by the subclasses.
     *
     */
    @Override
    public abstract boolean equals(Object o);
}