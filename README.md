## Transaction Manager
Utilizes JavaFX to simulate a banking application an employee would use to store and iteract with accounts. 
Start the project by running TransactionManagerMain.java

### Add/Close Account
Provides the functionality to open or close an account. 
There are 4 different account types that each have their own criteria to be succesfully opened. Each account has its own monthly fees and interests.
Succesfully opened accounts added to the account database and closed accounts are removed from it. 

### Deposit/Withdraw
Provides the functionality to deposit or withdrawy money from an existing account. 
The account must exist to be interacted with and must have sufficient funds for a withdrawal. 

### Account Database 
Provides functionality to view different lists of accounts.  
The user can view a default list of accounts, accounts with their current fees and interest, or the accounts with interests and fees applied. 
Implements opening accounts from a file. 

## Demo
In the Account Database tab, use the open accounts from file button and select the provided bankAccounts.txt file. 
This will populate the account database with a list of pre made accounts which can then be interacted with. 
