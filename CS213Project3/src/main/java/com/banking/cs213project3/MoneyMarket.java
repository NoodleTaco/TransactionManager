package com.banking.cs213project3;
/**
 * MoneyMarket class extends Savings and defines a type of account with monthly interest and fees depending
 * on the loyalty and balance of the customer.
 * @author Michael Kassie Donald Yubeaton
 */
public class MoneyMarket extends Savings{
    private int withdrawal; //number of withdrawals
    private final double MONTHLY_INTEREST_RATE= (0.045)/12;
    private final double MONTHLY_FEE= 25.0;

    private final double LOYAL_MONTHLY_INTEREST_RATE= (0.0475)/12;

    static final int LOYALTY_THRESHOLD = 2000;

    static final int WITHDRAWAL_FEE = 10;

    /**
     * No argument constructor for MoneyMarket.
     * calls on the savings class constructor. sets loyalty to true.
     */
    public MoneyMarket()
    {
        super();
        this.isLoyal = true;
    }

    /**
     * constructor with set properties for a money market account.
     * @param holder specifies the profile of the account.
     * @param balance specifies the balance of the account.
     * @param withdrawal specifies the number of withdrawals on the account.
     * @param isLoyal specifies if the account holder is loyal or not.
     */
    public MoneyMarket(Profile holder, double balance, int withdrawal,boolean isLoyal)
    {
        super(holder, balance, true);
        this.withdrawal = withdrawal;
    }

    /**
     * This method returns the monthly rate based on loyalty and update's
     * loyalty based on the balance on the account.
     * @return MONTHLY_INTEREST_RATE if the balance is lower than 2000 or
     * returns LOYAL_MONTHLY_INTEREST_RATE.
     *
     */
    public double monthlyInterest()
    {
        if( isLoyal)
        {
            return LOYAL_MONTHLY_INTEREST_RATE * balance;
        }
        return MONTHLY_INTEREST_RATE * balance;
    }

    /**
     * This method returns monthly fee based on the balance and the number of withdrawals
     * and updates the loyalty based on baalnce and resets the # of withdrawals to 0.
     * @return (MONTHLY_FEE+10) if withdrawal > 3 and balance<2000 or MONTHLY_FEE if balance<2000
     * but withdrawal<=3 or 10 if withdrawal<=3 but balance >= 2000 or 0.
     */
    public double monthlyFee()
    {
        double fee = 0;
        if(this.balance < LOYALTY_THRESHOLD)
        {
            fee += MONTHLY_FEE;
        }
        if(this.withdrawal > 3)
        {
            fee += WITHDRAWAL_FEE;
        }
        return fee;
    }

    /**
     * this method updates the balance based on the monthly fee and the monthly interest.
     */
    public void updateBalance()
    {
        balance += monthlyInterest();
        balance -= monthlyFee();
        setWithdrawal(0);
        checkLoyalCustomer();
    }

    /**
     * This method overrides the String method of the Account class and the Object class.
     * @return a string representation of the money market account instance.
     */
    public String toString()
    {
        return "Money Market::" + holder.toString() + "::Balance $" + formatBalance() + loyalString() + "::withdrawal: " + getWithdrawal() ;
    }

    /**
     * This method checks if the object passed in the argument and the money market account instance are equal or not.
     * Overrides the equals method of the Account class and The object class.
     * @param o the object instance to be compared to the money market account instance.
     * @return true if o and the money market account instance are equal or false if they are not.
     */
    @Override
    public boolean equals(Object o)
    {
        if(o == this)
        {
            return true;
        }
        if(!(o instanceof MoneyMarket))
        {
            return false;
        }

        MoneyMarket moneyMarket = (MoneyMarket)o;
        return this.getProfile().equals(moneyMarket.getProfile());
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

    /**
     * sets the number of withdrawals to the passed argument for the money market instance.
     * @param numWithdrawals the value that specifies the number of withdrawals for the money market instance.
     */
    public void setWithdrawal(int numWithdrawals)
    {
        withdrawal = numWithdrawals;
    }

    /**
     * Returns the number of withdrawals to the passed argument for the money market instance.
     * @return withdrawal the number of withdrawals.
     */
    public int getWithdrawal()
    {
        return withdrawal;
    }

    /**
     * checks the loyalty of the customer based on the balance that the customer has.
     */
    public void checkLoyalCustomer()
    {
        if(balance >= LOYALTY_THRESHOLD)
        {
            setIsLoyal(true);
        }
        else
        {
            setIsLoyal(false);
        }

    }
}
