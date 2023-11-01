package com.banking.cs213project3;
import java.util.Scanner;
import java.util.StringTokenizer;
/**
 * This class interprets user commands to interact with AccountDatabase
 * An instance of this class can process a single or multiple command lines.
 * Processes command line inputs until Q is inputted.
 * @author Donald Yubeaton, Micheal Kassie
 */
public class TransactionManager
{
    private AccountDatabase accountDatabase;

    static final int MINIMUM_MM_BALANCE = 2000;

    static final int O_STRING_TOKENIZER_MIN_SIZE = 5;
    static final int O_STRING_TOKENIZER_MAX_SIZE = 6;

    static final int C_STRING_TOKENIZER_SIZE = 4;

    static final int ACCOUNT_HOLDER_MIN_AGE = 16;

    static final int CC_ACCOUNT_HOLDER_MAX_AGE = 24;

    /**
     * Default constructor
     * Creates a default AccountDatabase
     */
    public TransactionManager()
    {
        accountDatabase = new AccountDatabase();
    }

    /**
     * Starts the command line reader
     * Stops when 'Q' is entered
     */
    public void run()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Transaction Manager is running.");
        while(true)
        {
            String line = scanner.nextLine();
            if (line.equals("Q"))
            {
                System.out.println("Transaction Manager is terminated.");
                break; // Exit the loop
            }
            StringTokenizer stringTokenizer = new StringTokenizer(line);
            processCommand(stringTokenizer);
        }
    }

    /**
     * Processes a single Command
     * Calls upon other input processing methods based on the command
     * @param stringTokenizer The StringTokenizer instance holding the command line input
     */
    private void processCommand(StringTokenizer stringTokenizer){
        if(stringTokenizer.hasMoreTokens()){
            String command = stringTokenizer.nextToken();
            switch (command) {
                case "O" -> processOCommand(stringTokenizer); //Open
                case "C" -> processCCommand(stringTokenizer); //Close
                case "D" -> processDCommand(stringTokenizer); //Deposit
                case "W" -> processWCommand(stringTokenizer); //Withdraw
                case "P" -> processPCommand(); //Print
                case "PI" -> processPICommand(); //Print and extra
                case "UB" -> processUBCommand(); //Update
                default -> System.out.println("Invalid Command!");
            }
        }
    }

    /**
     * Processes one O command
     * Creates a new account based on the input
     * @param stringTokenizer the StringTokenizer instance that holds the appropriate arguments for the 'O' command
     */
    private void processOCommand(StringTokenizer stringTokenizer)
    {
        if(stringTokenizer.countTokens() < O_STRING_TOKENIZER_MIN_SIZE && stringTokenizer.countTokens() != O_STRING_TOKENIZER_MAX_SIZE)
        {
            System.out.println("Missing data for opening an account.");
            return;
        }
        String accountInput = stringTokenizer.nextToken();

        Account account;

        if(isValidAccountInput(accountInput)) {
            account = processAccountTypeInput(accountInput);
        }
        else {
            System.out.println("Invalid Account Type");
            return;
        }
        if(!processProfileInput(stringTokenizer.nextToken(),stringTokenizer.nextToken(),stringTokenizer.nextToken(),account) ) {
            return;
        }
        if(!processBalanceInput(stringTokenizer.nextToken(), account)) {
            return;
        }
        if(!checkCC(stringTokenizer, account)) {
            return;
        }
        if(!checkS(stringTokenizer, account)) {
            return;
        }
        if(!checkMM(account)) {
            return;
        }
        if(accountDatabase.open(account)) {
            System.out.println(accountString(account) + " opened.");
        }
        else {
            System.out.println(accountString(account) + " is already in the database.");
        }
    }

    /**
     * Processes one C command
     * Creates a new account to remove from accountDatabase.
     * If the account does not exist in accountDatabase, an error message is printed.
     * @param stringTokenizer the StringTokenizer instance that holds the appropriate arguments for the 'C' command
     */
    private void processCCommand(StringTokenizer stringTokenizer)
    {
        if(stringTokenizer.countTokens() != C_STRING_TOKENIZER_SIZE)
        {
            System.out.println("Missing data for closing an account.");
            return;
        }

        String accountInput = stringTokenizer.nextToken();

        Account account;

        if(isValidAccountInput(accountInput))
        {
            account = processAccountTypeInput(accountInput);
        }
        else
        {
            System.out.println("Invalid Account Type");
            return;
        }
        if(!processProfileInput(stringTokenizer.nextToken(),stringTokenizer.nextToken(),stringTokenizer.nextToken(),account) )
        {
            return;
        }

        if(accountDatabase.close(account))
        {
            System.out.println(accountString(account) + " has been closed.");
        }
        else
        {
            System.out.println(accountString(account) + " is not in the database.");
        }
    }

    /**
     * Processes one D command
     * Deposits money into an existing account's balance
     * @param stringTokenizer the StringTokenizer instance that holds the appropriate arguments for the 'D' command
     */
    private void processDCommand(StringTokenizer stringTokenizer)
    {
        if(stringTokenizer.countTokens() != O_STRING_TOKENIZER_MIN_SIZE)
        {
            System.out.println("Missing data for a deposit.");
            return;
        }

        String accountInput = stringTokenizer.nextToken();

        Account account;

        if(isValidAccountInput(accountInput))
        {
            account = processAccountTypeInput(accountInput);
        }
        else
        {
            System.out.println("Invalid Account Type");
            return;
        }
        if(!blindProcessProfileInput(stringTokenizer.nextToken(),stringTokenizer.nextToken(),stringTokenizer.nextToken(),account) )
        {
            return;
        }
        if(!processBalanceInput(stringTokenizer.nextToken(), account))
        {
            return;
        }

        if(accountDatabase.contains(account))
        {
            accountDatabase.deposit(account);
            System.out.println(accountString(account) + " Deposit - balance updated.");
        }
        else
        {
            System.out.println(accountString(account) + " account is not in database.");
        }

    }

    /**
     * Processes one W command
     * Withdraws money from an existing account's balance
     * @param stringTokenizer the StringTokenizer instance that holds the appropriate arguments for the 'W' command
     */
    private void processWCommand(StringTokenizer stringTokenizer)
    {
        if(stringTokenizer.countTokens() != O_STRING_TOKENIZER_MIN_SIZE)
        {
            System.out.println("Missing data for a withdrawal.");
            return;
        }
        String accountInput = stringTokenizer.nextToken();
        Account account;

        if(isValidAccountInput(accountInput))
        {
            account = processAccountTypeInput(accountInput);
        }
        else
        {
            System.out.println("Invalid Account Type");
            return;
        }
        if(!blindProcessProfileInput(stringTokenizer.nextToken(),stringTokenizer.nextToken(),stringTokenizer.nextToken(),account) ) {
            return;
        }
        if(!processBalanceInput(stringTokenizer.nextToken(), account)) {
            return;
        }

        if(!accountDatabase.contains(account)) {
            System.out.println(accountString(account) + "Withdraw - Account is not in database.");
            return;
        }

        if(accountDatabase.withdraw(account)) {
            System.out.println(accountString(account) + "Withdraw - balance updated.");
        }
        else {
            System.out.println(accountString(account) + "Withdraw - insufficient fund.");
            return;
        }
    }

    /**
     * Processes one P command
     * Sorts the accountDatabase then prints out all accounts
     */
    private void processPCommand()
    {

        if(accountDatabase.getNumAcct() == 0)
        {
            System.out.println("Account Database is empty!");
            return;
        }
        System.out.println();
        System.out.println("*Accounts sorted by account type and profile.");
        accountDatabase.printSorted();
        System.out.println("*end of list.");
        System.out.println();
    }

    /**
     * Processes one PI command
     * Sorts the accountDatabase then prints out all accounts with their interests and fees
     */
    private void processPICommand()
    {

        if(accountDatabase.getNumAcct() == 0)
        {
            System.out.println("Account Database is empty!");
            return;
        }
        System.out.println();
        System.out.println("*list of accounts with fee and monthly interest");
        accountDatabase.printFeesAndInterests();
        System.out.println("*end of list.");
        System.out.println();
    }

    /**
     * Processes one UB command
     * Updates the balance of all accounts in accountDatabase, then sorts and prints them
     */
    private void processUBCommand()
    {

        if(accountDatabase.getNumAcct() == 0)
        {
            System.out.println("Account Database is empty!");
            return;
        }
        System.out.println();
        System.out.println("*list of accounts with fees and interests applied.");
        accountDatabase.printUpdatedBalances();
        System.out.println("*end of list.");
        System.out.println();
    }

    /**
     * Creates a new Account subclass based on a String input
     * @param input The string input dictating which kind of account will be created
     * @return The reference to the account created
     */
    private Account processAccountTypeInput(String input)
    {
        return switch (input) {
            case "C" -> new Checking();
            case "CC" -> new CollegeChecking();
            case "S" -> new Savings();
            default -> new MoneyMarket();
        };
    }

    /**
     * Checks if an input meant for the processAccountTypeInput function is valid
     * @param input The input that is being checked
     * @return true if the input represents an account type, false otherwise
     */
    private boolean isValidAccountInput(String input)
    {
        return switch (input) {
            case "C" -> true;
            case "CC" -> true;
            case "S" -> true;
            case "MM" -> true;
            default -> false;
        };
    }

    /**
     * Creates a Profile object based on inputs
     * @param fName The first name of the profile
     * @param lName The last name of the profile
     * @param dob The date of birth of the profile
     * @param account The account object where the profile will be added
     * @return false if there are error in the inputs, true if a valid profile is created and assigned
     */
    private boolean processProfileInput(String fName, String lName, String dob,Account account)
    {
        Profile profile = new Profile();
        profile.setFname(fName);
        profile.setLname(lName);

        try
        {
            if(!processDateInput(profile, dob, account))
            {
                return false;
            }
        }
        catch (Exception e)
        {
            System.out.println("Invalid Date Input");
            return false;
        }
        account.setProfile(profile);
        return true;
    }

    /**
     * Creates a profile for the deposit and withdrawal classes to check
     * Does not check for a valid profile since deposit and withdrawal check if the profile is in the database
     * @param fName The first name of the profile
     * @param lName The last name of the profile
     * @param dob The date of birth of the profile
     * @param account The account object where the profile will be added
     * @return false if there are error in the inputs, true if a working profile is created and assigned
     */
    private boolean blindProcessProfileInput(String fName, String lName, String dob,Account account)
    {
        Profile profile = new Profile();
        profile.setFname(fName);
        profile.setLname(lName);

        try
        {
            blindProcessDateInput(profile, dob);
        }
        catch (Exception e)
        {
            System.out.println("Invalid Date Input");
            return false;
        }
        account.setProfile(profile);
        return true;
    }

    /**
     * Creates a Date object and assigns it to a profile
     * @param profile The profile for the date to be assigned to
     * @param dateInput The string input that holds the date's information
     * @param account The account of the profile, used for checking age requirements
     * @return false if there are error in the inputs, true if a valid date is created and assigned
     */
    private boolean processDateInput(Profile profile, String dateInput, Account account)
    {

        StringTokenizer slashSeparator = new StringTokenizer(dateInput, "/");

        Date date = new Date(Integer.parseInt( slashSeparator.nextToken()),Integer.parseInt(slashSeparator.nextToken()),
                Integer.parseInt(slashSeparator.nextToken()));
        if(!date.isValid())
        {
            System.out.println("DOB invalid: " + date.toString() + " not a valid calendar date!");
            return false;
        }
        if(!date.isPast())
        {
            System.out.println("DOB invalid: " + date.toString() + ": cannot be today or a future day.");
            return false;
        }
        if(account instanceof CollegeChecking)
        {

            if(date.getYearsOld() >= CC_ACCOUNT_HOLDER_MAX_AGE)
            {
                System.out.println("DOB invalid: " + date.toString() + " over 24.");
                return false;
            }

        }
        else
        {
            if(date.getYearsOld() < ACCOUNT_HOLDER_MIN_AGE)
            {
                System.out.println("DOB invalid: " + date.toString() + " under 16.");
                return false;
            }
        }
        profile.setDob(date);
        return true;
    }

    /**
     * Creates a date for the blindProcessProfileInput function
     * Does not check for invalid dates
     * @param profile The profile for the date to be assigned to
     * @param dateInput The string input that holds the date's information
     */
    private void blindProcessDateInput(Profile profile, String dateInput)
    {
        StringTokenizer slashSeparator = new StringTokenizer(dateInput, "/");

        Date date = new Date(Integer.parseInt( slashSeparator.nextToken()),Integer.parseInt(slashSeparator.nextToken()),
                Integer.parseInt(slashSeparator.nextToken()));

        profile.setDob(date);
        return;
    }

    /**
     * Creates a balance and assigns it to an account
     * @param balanceInput The string representing the balance
     * @param account The account where the balance will be assigned
     * @return false if there are error in the inputs, true if a valid balance is assigned
     */
    private boolean processBalanceInput(String balanceInput, Account account)
    {
        double balance;
        try
        {
            balance = Double.parseDouble(balanceInput);
        }
        catch(Exception e)
        {
            System.out.println("Not a Valid Amount");
            return false;
        }

        if(balance <= 0)
        {
            System.out.println("Initial deposit, regular deposit, or withdrawal cannot be 0 or negative.");
            return false;
        }

        account.setBalance(balance);
        return true;
    }

    /**
     * Checks if an account is a CollegeChecking account, and if so checks its validity
     * @param stringTokenizer The StringTokenizer that could hold the campus code
     * @param account The account that is being checked
     * @return false if there are errors in the CollegeChecking account, true otherwise
     */
    private boolean checkCC(StringTokenizer stringTokenizer, Account account)
    {
        if(account instanceof CollegeChecking)
        {
            if(!stringTokenizer.hasMoreTokens())
            {
                System.out.println("Missing Campus code.");
                return false;
            }
            int campusCode = -1;
            try
            {
                campusCode = Integer.parseInt(stringTokenizer.nextToken());
            }
            catch(Exception e)
            {
                System.out.println("Invalid campus code.");
                return false;
            }
            CollegeChecking collegeChecking = (CollegeChecking) account;

            if(campusCode == Campus.NEW_BRUNSWICK.getCampusCode()) {
                collegeChecking.setCampus(Campus.NEW_BRUNSWICK);
            }
            else if(campusCode == Campus.NEWARK.getCampusCode()) {
                collegeChecking.setCampus(Campus.NEWARK);
            }
            else if(campusCode == Campus.CAMDEN.getCampusCode()) {
                collegeChecking.setCampus(Campus.CAMDEN);
            }
            else {
                System.out.println("Invalid campus code.");
                return false;
            }
            account = collegeChecking;
        }
        return true;
    }

    /**
     * Checks if an account is a Savings account, and if so checks its validity
     * @param stringTokenizer The StringTokenizer that could hold the loyalty status
     * @param account The account that is being checked
     * @return false if there are errors in the Savings account, true otherwise
     */
    private boolean checkS(StringTokenizer stringTokenizer, Account account)
    {
        if(account instanceof Savings && !(account instanceof MoneyMarket))
        {
            if(!stringTokenizer.hasMoreTokens())
            {
                System.out.println("Missing loyal customer status.");
                return false;
            }
            int isLoyal = -1;

            try
            {
                isLoyal = Integer.parseInt(stringTokenizer.nextToken());
            }
            catch(Exception e)
            {
                System.out.println("Not a Valid Loyalty Status");
                return false;
            }

            Savings savings = (Savings) account;

            if(isLoyal == 0)
            {
                savings.setIsLoyal(false);
            }
            else if(isLoyal == 1)
            {
                savings.setIsLoyal(true);
            }

            else
            {
                System.out.println("Not a Valid Loyalty Status");
                return false;
            }
            account = savings;
        }
        return true;
    }

    /**
     * Checks if an account is a MoneyMarket account, and if so checks its validity
     * @param account The account that is being checked
     * @return false if the minimum balance of the MoneyMarket account is < 2000, true otherwise
     */
    private boolean checkMM(Account account)
    {
        if(account instanceof MoneyMarket && account.balance < MINIMUM_MM_BALANCE)
        {
            System.out.println("Minimum of $2000 to open a Money Market account.");
            return false;
        }
        return true;
    }

    /**
     * Returns the simplified string version of an account
     * @param account The account being considered
     * @return The simplified string version of the account
     */
    private String accountString(Account account)
    {
        String accountType;
        if(account instanceof Checking)
        {
            accountType = "(C)";
        }
        else if(account instanceof CollegeChecking)
        {
            accountType = "(CC)";
        }
        else if(account instanceof MoneyMarket)
        {
            accountType = "(MM)";
        }
        else //Savings account
        {
            accountType = "(S)";
        }

        return account.getProfile().toString() + accountType;
    }



}
