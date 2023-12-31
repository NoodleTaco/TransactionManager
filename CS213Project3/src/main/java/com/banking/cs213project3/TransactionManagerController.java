package com.banking.cs213project3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;

/**
 * Controller for the JavaFX GUI
 * @author Donald Yubeaton, Michael Kassie
 */
public class TransactionManagerController {

    private AccountDatabase accountDatabase;

    static final int MINIMUM_MM_BALANCE = 2000;

    static final int O_STRING_TOKENIZER_MIN_SIZE = 5;
    static final int O_STRING_TOKENIZER_MAX_SIZE = 6;

    static final int C_STRING_TOKENIZER_SIZE = 4;

    static final int ACCOUNT_HOLDER_MIN_AGE = 16;

    static final int CC_ACCOUNT_HOLDER_MAX_AGE = 24;


    public TransactionManagerController(){
        accountDatabase = new AccountDatabase();
    }

    @FXML
    private TextField firstNameInput, lastNameInput, balanceInput, interactionFirstNameInput, interactionLastNameInput,
            interactionAmount, dateInput, interactionDateInput;



    @FXML
    private Button openAccountButton, closeAccountButton, depositButton, withdrawButton, printAccountButton,
            printAccountsFeesAndInterestButton, updateInterestsAndFees, loadAccountsButton, clearButton,
            clearInteractionButton;

    @FXML
    private RadioButton checkingButton, collegeCheckingButton, savingsButton, moneyMarketButton, newBrunswickButton,
            newarkButton, camdenButton, isLoyalButton,checkingInteractionButton, collegeCheckingInteractionButton,
            savingsInteractionButton, moneyMarketInteractionButton;

    @FXML
    private TextArea openCloseTextArea;


    /**
     * Handles the event where the load accounts button is clicked
     * Creates a file chooser prompt for the user to select an appropriate file to be processed
     */
    public void loadAccountsButtonClick(){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        try {
            Scanner scanner = new Scanner(selectedFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(!line.isBlank())
                {
                    StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
                    openAccount(stringTokenizer);
                }

            }
        }
        catch (Exception e){
            openCloseTextArea.appendText("Invalid File Input"+ "\n");
        }

    }

    /**
     * Enables buttons related to specific account types and disables them if that account type is not selected
     */
    public void accountSpecificOptions(){

        newBrunswickButton.setSelected(false);
        newarkButton.setSelected(false);
        camdenButton.setSelected(false);
        isLoyalButton.setSelected(false);

        if(checkingButton.isSelected() || moneyMarketButton.isSelected()){
            newBrunswickButton.setDisable(true);
            newarkButton.setDisable(true);
            camdenButton.setDisable(true);
            isLoyalButton.setDisable(true);
        }
        else if(collegeCheckingButton.isSelected()){
            newBrunswickButton.setDisable(false);
            newarkButton.setDisable(false);
            camdenButton.setDisable(false);
            isLoyalButton.setDisable(true);
        }
        else if(savingsButton.isSelected()){
            newBrunswickButton.setDisable(true);
            newarkButton.setDisable(true);
            camdenButton.setDisable(true);
            isLoyalButton.setDisable(false);
        }
    }

    /**
     * Processes the clear button on the Open/Close Account tab
     * Clears all unprocessed inputs on the GUI
     */
    public void clearButtonClick(){
        firstNameInput.clear();
        lastNameInput.clear();
        dateInput.clear();
        checkingButton.setSelected(false);
        collegeCheckingButton.setSelected(false);
        savingsButton.setSelected(false);
        moneyMarketButton.setSelected(false);
        newBrunswickButton.setSelected(false);
        newarkButton.setSelected(false);
        camdenButton.setSelected(false);
        isLoyalButton.setSelected(false);
        balanceInput.clear();
    }

    /**
     * Processes the clear button on the Deposit/Withdrawal tab
     * Clears all unprocessed inputs on the GUI
     */
    public void interactionClearButtonClick(){
        interactionFirstNameInput.clear();
        interactionLastNameInput.clear();
        interactionDateInput.clear();
        checkingInteractionButton.setSelected(false);
        collegeCheckingInteractionButton.setSelected(false);
        savingsInteractionButton.setSelected(false);
        moneyMarketInteractionButton.setSelected(false);
        interactionAmount.clear();
    }

    /**
     * Returns the account type depending on which button is chosen
     * @return String representation of the account type
     */
    private String getAccountType() {
        if(checkingButton.isSelected()){
            return "C";
        }
        else if(collegeCheckingButton.isSelected())
        {
            return "CC";
        }
        else if(savingsButton.isSelected())
        {
            return "S";
        }
        else if(moneyMarketButton.isSelected())
        {
            return "MM";
        }
        else
        {
            return null;
        }
    }

    /**
     * This method returns the type of account that is chosen by the user.
     * @return a String representations of the account type
     */
    private String getInteractionAccountType(){

        if(checkingInteractionButton.isSelected()){
            return "C";
        }
        else if(collegeCheckingInteractionButton.isSelected())
        {
            return "CC";
        }
        else if(savingsInteractionButton.isSelected())
        {
            return "S";
        }
        else if(moneyMarketInteractionButton.isSelected())
        {
            return "MM";
        }
        else
        {
            return null;
        }
    }

    /**
     * Deposits into an account based on the information in the Deposit/Withdrawal tab
     */
    public void depositClick(){
        String accountType = getInteractionAccountType();
        String firstName = interactionFirstNameInput.getText();
        String lastName = interactionLastNameInput.getText();
        String dob = interactionDateInput.getText();
        String balance = interactionAmount.getText();

        String toBeTokenized = accountType + " " + firstName + " " + lastName + " " + dob + " " + balance;

        StringTokenizer stringTokenizer = new StringTokenizer(toBeTokenized);

        depositAccount(stringTokenizer);
    }


    /**
     * Withdraws from an account based on the information in the Deposit/Withdrawal tab
     */
    public void withdrawClick(){
        String accountType = getInteractionAccountType();
        String firstName = interactionFirstNameInput.getText();
        String lastName = interactionLastNameInput.getText();
        String dob = interactionDateInput.getText();
        String balance = interactionAmount.getText();

        String toBeTokenized = accountType + " " + firstName + " " + lastName + " " + dob + " " + balance;

        StringTokenizer stringTokenizer = new StringTokenizer(toBeTokenized);

        withdrawAccount(stringTokenizer);
    }

    /**
     * Returns the campus code based on which campus Radio Button is selected
     * @return the String value of the campus for the College Student holding the account.
     */
    private String getCampusCode(){
        if(newBrunswickButton.isSelected()){
            return "0";
        }
        else if(newarkButton.isSelected())
        {
            return "1";
        }
        else if(camdenButton.isSelected())
        {
            return "2";
        }
        else
        {
            return null;
        }
    }


    /**
     * Opens an account based on the information in the Open/Close Account tab
     */
    public void openButtonClick(){

        String accountType = getAccountType();
        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        String dob = dateInput.getText();
        String balance = balanceInput.getText();

        String toBeTokenized = accountType + " " + firstName + " " + lastName + " " + dob + " " + balance;

        if(collegeCheckingButton.isSelected())
        {
            toBeTokenized += " " + getCampusCode();
        }

        if(savingsButton.isSelected())
        {
            if(isLoyalButton.isSelected())
            {
                toBeTokenized += " 1";
            }
            else
            {
                toBeTokenized += " 0";
            }
        }

        StringTokenizer stringTokenizer = new StringTokenizer(toBeTokenized);
        openAccount(stringTokenizer);
    }

    /**
     * This method adds the account to the database and appends the openCloseTextArea object with a String representation of it.
     * @param stringTokenizer the String representation of the account
     */
    private void openAccount(StringTokenizer stringTokenizer)
    {
        if(stringTokenizer.countTokens() < O_STRING_TOKENIZER_MIN_SIZE && stringTokenizer.countTokens() != O_STRING_TOKENIZER_MAX_SIZE) {
            openCloseTextArea.appendText("Missing data for opening an account."+ "\n");
            return;
        }
        String accountInput = stringTokenizer.nextToken();
        Account account;
        if(isValidAccountInput(accountInput)) {
            account = processAccountTypeInput(accountInput);
        }
        else {
            openCloseTextArea.appendText("Invalid Account Type"+ "\n");
            return;
        }
        if((account.getClass() == CollegeChecking.class|| account.getClass() == Savings.class) && stringTokenizer.countTokens() < O_STRING_TOKENIZER_MIN_SIZE){
            openCloseTextArea.appendText("Missing data for opening an account."+ "\n");
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
            openCloseTextArea.appendText(accountString(account) + " opened."+ "\n");
        }
        else {
            openCloseTextArea.appendText(accountString(account) + " is already in the database."+ "\n");
        }
    }

    /**
     * Closes an Account based on information in the Open/Close Account tab
     */
    public void closeButtonClick(){
        String accountType = getAccountType();
        String firstName = firstNameInput.getText();
        String lastName = lastNameInput.getText();
        String dob = dateInput.getText();

        String toBeTokenized = accountType + " " + firstName + " " + lastName + " " + dob;

        StringTokenizer stringTokenizer = new StringTokenizer(toBeTokenized);
        closeAccount(stringTokenizer);
    }

    /**
     * This method closes the account based on information given by a stringTokenizer
     * @param stringTokenizer Holds the information to close the account
     */
    private void closeAccount(StringTokenizer stringTokenizer){
        if(stringTokenizer.countTokens() != C_STRING_TOKENIZER_SIZE)
        {
            openCloseTextArea.appendText("Missing data for closing an account."+ "\n");
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
            openCloseTextArea.appendText("Invalid Account Type"+ "\n");
            return;
        }
        if(!processProfileInput(stringTokenizer.nextToken(),stringTokenizer.nextToken(),stringTokenizer.nextToken(),account) )
        {
            return;
        }

        if(accountDatabase.close(account))
        {
            openCloseTextArea.appendText(accountString(account) + " has been closed."+ "\n");
        }
        else
        {
            openCloseTextArea.appendText(accountString(account) + " is not in the database."+ "\n");
        }
    }

    /**
     * It deposits the account with using the information in the String Tokenizer object.
     * @param stringTokenizer Holds the information to deposit into the account
     */
    private void depositAccount(StringTokenizer stringTokenizer){
        if(stringTokenizer.countTokens() != O_STRING_TOKENIZER_MIN_SIZE)
        {
            openCloseTextArea.appendText("Missing data for a deposit."+ "\n");
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
            openCloseTextArea.appendText("Invalid Account Type"+ "\n");
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
            openCloseTextArea.appendText(accountString(account) + " Deposit - balance updated."+ "\n");
        }
        else
        {
            openCloseTextArea.appendText(accountString(account) + " account is not in database."+ "\n");
        }
    }

    /**
     * It withdraws from the account with the information contained in the String Tokenizer object.
     * @param stringTokenizer Holds the information to withdraw from an account
     */
    private void withdrawAccount(StringTokenizer stringTokenizer){
        if(stringTokenizer.countTokens() != O_STRING_TOKENIZER_MIN_SIZE)
        {
            openCloseTextArea.appendText("Missing data for a withdrawal."+ "\n");
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
            openCloseTextArea.appendText("Invalid Account Type"+ "\n");
            return;
        }
        if(!blindProcessProfileInput(stringTokenizer.nextToken(),stringTokenizer.nextToken(),stringTokenizer.nextToken(),account) ) {
            return;
        }
        if(!processBalanceInput(stringTokenizer.nextToken(), account)) {
            return;
        }

        if(!accountDatabase.contains(account)) {
            openCloseTextArea.appendText(accountString(account) + "Withdraw - Account is not in database."+ "\n");
            return;
        }

        if(accountDatabase.withdraw(account)) {
            openCloseTextArea.appendText(accountString(account) + "Withdraw - balance updated."+ "\n");
        }
        else {
            openCloseTextArea.appendText(accountString(account) + "Withdraw - insufficient fund."+ "\n");
            return;
        }
    }

    /**
     * The event handler for when the user clicks the print account option which calls the print accounts function.
     */
    public void printAccountsClick(){
        printAccounts();
    }

    /**
     * The event handler for when the user clicks the print account with fees and interests option which calls the print accounts and with fees and
     * interests function.
     */
    public void printAccountsFeesAndInterestsClick(){
        printAccountsWithFeesAndInterests();
    }

    /**
     * The event handler for when the user clicks the update the account with fees and interests option which calls the print
     * updated accounts function.
     * function.
     */
    public void updateFeesAndInterestsClick(){
        printUpdatedAccounts();
    }


    /**
     * Prints accounts based on the String representation of it sorted by type of profile.
     */
    private void printAccounts(){
        if(accountDatabase.getNumAcct() == 0)
        {
            openCloseTextArea.appendText("Account Database is empty!"+ "\n");
            return;
        }
        openCloseTextArea.appendText("\n");
        openCloseTextArea.appendText("*Accounts sorted by account type and profile."+ "\n");
        StringTokenizer stringTokenizer = new StringTokenizer(accountDatabase.printSorted(), "#@");
        while(stringTokenizer.hasMoreTokens())
        {
            openCloseTextArea.appendText(stringTokenizer.nextToken() + "\n");
        }
        openCloseTextArea.appendText("*end of list."+ "\n");
        openCloseTextArea.appendText("\n");
    }
    /**
     * Prints accounts based on the String representation of it sorted by type of profile with all the fees and interests.
     */
    private void printAccountsWithFeesAndInterests(){
        if(accountDatabase.getNumAcct() == 0)
        {
            openCloseTextArea.appendText("Account Database is empty!"+ "\n");
            return;
        }
        openCloseTextArea.appendText("\n");
        openCloseTextArea.appendText("*list of accounts with fee and monthly interest"+ "\n");

        StringTokenizer stringTokenizer = new StringTokenizer(accountDatabase.printFeesAndInterests(), "#@");
        while(stringTokenizer.hasMoreTokens())
        {
            openCloseTextArea.appendText(stringTokenizer.nextToken() + "\n");
        }
        openCloseTextArea.appendText("*end of list."+ "\n");
        openCloseTextArea.appendText("\n");
    }
    /**
     * Prints accounts based on the String representation of it sorted by type of profile with the updated interests and fees.
     */
    private void printUpdatedAccounts(){
        if(accountDatabase.getNumAcct() == 0)
        {
            openCloseTextArea.appendText("Account Database is empty!"+ "\n");
            return;
        }
        openCloseTextArea.appendText("\n");
        openCloseTextArea.appendText("*list of accounts with fees and interests applied."+ "\n");

        StringTokenizer stringTokenizer = new StringTokenizer(accountDatabase.printUpdatedBalances(), "#@");
        while(stringTokenizer.hasMoreTokens())
        {
            openCloseTextArea.appendText(stringTokenizer.nextToken() + "\n");
        }
        openCloseTextArea.appendText("*end of list."+ "\n");
        openCloseTextArea.appendText("\n");
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
            openCloseTextArea.appendText("Invalid Date Input"+ "\n"+ "\n");
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
        StringTokenizer backSlashSeparator = new StringTokenizer(dateInput, "/");
        int month = Integer.parseInt(backSlashSeparator.nextToken());
        int day = Integer.parseInt(backSlashSeparator.nextToken());
        int year = Integer.parseInt(backSlashSeparator.nextToken());

        Date date = new Date(month, day, year);

        if(!date.isValid()) {
            openCloseTextArea.appendText("DOB invalid: " + date.toString() + " not a valid calendar date!"+ "\n");
            return false;
        }
        if(!date.isPast()) {
            openCloseTextArea.appendText("DOB invalid: " + date.toString() + ": cannot be today or a future day."+ "\n");
            return false;
        }
        if(account instanceof CollegeChecking) {
            if(date.getYearsOld() >= CC_ACCOUNT_HOLDER_MAX_AGE) {
                openCloseTextArea.appendText("DOB invalid: " + date.toString() + " over 24."+ "\n");
                return false;
            }

            if(date.getYearsOld() < ACCOUNT_HOLDER_MIN_AGE) {
                openCloseTextArea.appendText("DOB invalid: " + date.toString() + " under 16."+ "\n");
                return false;
            }
        }
        else
        {
            if(date.getYearsOld() < ACCOUNT_HOLDER_MIN_AGE)
            {
                openCloseTextArea.appendText("DOB invalid: " + date.toString() + " under 16."+ "\n");
                return false;
            }
        }
        profile.setDob(date);
        return true;
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
            openCloseTextArea.appendText("Not a Valid Amount"+ "\n");
            return false;
        }

        if(balance <= 0)
        {
            openCloseTextArea.appendText("Initial deposit, regular deposit, or withdrawal cannot be 0 or negative."+ "\n");
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
                openCloseTextArea.appendText("Missing Campus code."+ "\n");
                return false;
            }
            int campusCode = -1;
            try
            {
                campusCode = Integer.parseInt(stringTokenizer.nextToken());
            }
            catch(Exception e)
            {
                openCloseTextArea.appendText("Invalid campus code."+ "\n");
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
                openCloseTextArea.appendText("Invalid campus code."+ "\n");
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
                openCloseTextArea.appendText("Missing loyal customer status."+ "\n");
                return false;
            }
            int isLoyal = -1;

            try
            {
                isLoyal = Integer.parseInt(stringTokenizer.nextToken());
            }
            catch(Exception e)
            {
                openCloseTextArea.appendText("Not a Valid Loyalty Status"+ "\n");
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
                openCloseTextArea.appendText("Not a Valid Loyalty Status"+ "\n");
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
            openCloseTextArea.appendText("Minimum of $2000 to open a Money Market account."+ "\n");
            return false;
        }
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
            openCloseTextArea.appendText("Invalid Date Input"+ "\n");
            return false;
        }
        account.setProfile(profile);
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
        StringTokenizer backSlashSeparator = new StringTokenizer(dateInput, "/");
        int month = Integer.parseInt(backSlashSeparator.nextToken());
        int day = Integer.parseInt(backSlashSeparator.nextToken());
        int year = Integer.parseInt(backSlashSeparator.nextToken());

        Date date = new Date(month, day, year);

        profile.setDob(date);
        return;
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