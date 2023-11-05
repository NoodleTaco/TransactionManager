package com.banking.cs213project3;
/**
 * AccountDatabase holds different accounts in an array and implements operations to grow,
 * find, close, open, withdraw, deposit and print information for the accounts.
 * @author Michael Kassie, Donald Yubeaton
 */
public class AccountDatabase {
    private Account [] accounts; //list of various types of accounts
    private int numAcct; //number of accounts in the array

    static final int ACCOUNT_DATABASE_STARTING_SIZE = 4;

    static final int NOT_FOUND = -1;

    /**
     * Constructor for AccountDatabase class
     * Initializes the accounts array capacity to 4 and the number of accounts to 0.
     */
    public AccountDatabase()
    {
        accounts = new Account[ACCOUNT_DATABASE_STARTING_SIZE];
        numAcct = 0;
    }

    /**
     *The method finds an account in the accounts that exist in the account database array.
     * @param account the account that is to be found in the array of accounts.
     * @return the index of the array account where the parameter account that was passed is located.
     */
    private int find(Account account)
    {
        for(int i = 0; i < numAcct; i++)
        {
            if(accounts[i].equals(account))
            {
                return i;
            }
        }
        return NOT_FOUND;
    } //search for an account in the array

    /**
     * This method increases the capacity of the account array by 4
     */
    private void grow() //increase the capacity by 4
    {
        Account[] tempArray = new Account[numAcct + ACCOUNT_DATABASE_STARTING_SIZE];
        for(int i = 0 ; i < numAcct; i++)
        {
            tempArray[i] = accounts[i];
        }
        accounts = tempArray;
    }

    /**
     * This method determines if a given account exists in the account array or not.
     * @param account the account for which it is to be determined if it exists or not in the account array.
     * @return true if the account exists or false if it doesn't in the account array.
     */
    public boolean contains(Account account)
    {
        for(int i = 0; i < numAcct; i++)
        {
            if(accounts[i].equals(account))
            {
                return true;
            }
        }
        return false;
    } //overload if necessary

    /**
     * It adds a new account in the accounts array if it doesn't exist.
     * @param account this is the account that is to be added.
     * @return true if the account is added or false if it's not.
     */
    public boolean open(Account account)
    {
        if (this.contains(account))
        {
            return false;
        }

        if(account instanceof Checking)
        {
            if(this.containsProfileCC(account.getProfile()))
            {
                return false;
            }
        }
        if(account instanceof CollegeChecking)
        {
            if(this.containsProfileC(account.getProfile()))
            {
                return false;
            }
        }


        accounts[numAcct] = account;
        numAcct ++;

        if(numAcct == accounts.length)
        {
            grow();
        }
        return true;
    } //add a new account

    /**
     * It removes the account that is given from the account array if its found.
     * @param account the account that is to be removed from the accounts array.
     * @return true if the account given is found or false if it's not found.
     */
    public boolean close(Account account)
    {
        if(this.contains(account))
        {
            for(int i = 0; i < numAcct; i++)
            {
                if(accounts[i].equals(account))
                {
                    for(int j = i; j < numAcct-1; j++)
                    {
                        accounts[j] = accounts[j+1];
                    }
                    accounts[accounts.length -1] = null;
                }
            }
            numAcct --;
            return true;
        }
        return false;
    } //remove the given account

    /**
     * Withdraw money from the balance of a given account.
     * @param account the account from which money is to be withdrawn.
     * @return true if the given account is found  or false if it's not found.
     */
    public boolean withdraw(Account account)
    {
        int index = find(account);

        if(accounts[index].getBalance() < account.getBalance())
        {
            return false;
        }

        accounts[index].setBalance(accounts[index].getBalance() - account.getBalance());

        if(account instanceof MoneyMarket)
        {
            MoneyMarket moneyMarket = (MoneyMarket)accounts[index];
            moneyMarket.setWithdrawal(moneyMarket.getWithdrawal() + 1);
            moneyMarket.checkLoyalCustomer();


        }
        return true;
    } //false if insufficient fund



    /**
     * Deposit money to the balance of a given account in the accounts array.
     * @param account the account in which the money is to be deposited.
     */
    public void deposit(Account account)
    {
        int index = find(account);
        accounts[index].setBalance(accounts[index].getBalance() + account.getBalance());

        if(account instanceof MoneyMarket)
        {
            MoneyMarket moneyMarket = ((MoneyMarket) accounts[index]);
            moneyMarket.checkLoyalCustomer();
        }

    }

    /**
     * prints the accounts array in a sorted manner based on their account types and profiles.
     */
    public String printSorted()
    {
        sort();
        String toBeReturned = "";
        for(int i = 0; i < numAcct; i++)
        {
            toBeReturned += accounts[i].toString() + "#@";
        }
        return toBeReturned;
    } //sort by account type and profile

    /**
     * Prints the fees and interests of the accounts array.
     */
    public String printFeesAndInterests()
    {
        sort();
        String toBeReturned = "";
        for(int i = 0; i < numAcct; i++)
        {
            toBeReturned += accounts[i].toString() + accounts[i].formatFeesAndInterest() + "#@";
        }
        return toBeReturned;
    } //calculate interests/fees

    /**
     * Print the updated balances of the accounts array.
     */
    public String printUpdatedBalances()
    {
        updateBalances();
        sort();

        String toBeReturned = "";
        for(int i = 0; i < numAcct; i++)
        {
            toBeReturned += accounts[i].toString() + "#@";
        }
        return toBeReturned;

    } //apply the interests/fees

    /**
     * Checks if accounts has a CollegeChecking account with a specific profile
     * @param profile The profile that's being looked for
     * @return true if there is a CC account with the profile, false otherwise
     */
    private boolean containsProfileCC(Profile profile)
    {
        for(int i = 0; i < numAcct; i++)
        {
            if(accounts[i].getProfile().equals(profile))
            {
                if(accounts[i] instanceof CollegeChecking)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if accounts has a Checking account with a specific profile
     * @param profile The profile that's being looked for
     * @return true if there is a C account with the profile, false otherwise
     */
    private boolean containsProfileC(Profile profile)
    {
        for(int i = 0; i < numAcct; i++)
        {
            if(accounts[i].getProfile().equals(profile))
            {
                if(accounts[i] instanceof Checking)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Sorts the contents of accounts by account type
     * Matching account types are then sorted by profile
     */
    private void sort()
    {
        for (int i = 1; i < numAcct; i++)
        {
            Account pointer = accounts[i];
            int j = i - 1;
            while (j >= 0 && accounts[j].compareTo(pointer) > 0)
            {
                accounts[j + 1] = accounts[j];
                j = j - 1;
            }

            accounts[j + 1] = pointer;
        }
    }

    /**
     * Applies monthly interest and fees to the contents of accounts
     */
    private void updateBalances()
    {
        for(int i = 0; i < numAcct; i++)
        {
            accounts[i].updateBalance();
        }
    }

    /**
     * Returns the number of Accounts in the account array.
     * @return numAcct the number of accounts in the account array.
     */
    public int getNumAcct()
    {
        return numAcct;
    }


}
