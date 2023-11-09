# hw1- Manual Review

The homework will be based on this project named "Expense Tracker",where users will be able to add/remove daily transaction. 

## Compile

To compile the code from terminal, use the following command:
```
cd src
javac ExpenseTrackerApp.java
java ExpenseTracker
```

You should be able to view the GUI of the project upon successful compilation. 

## Java Version
This code is compiled with ```openjdk 17.0.7 2023-04-18```. Please update your JDK accordingly if you face any incompatibility issue.

## New Functionality
1. ### Adding New Transaction
    Allow user to enter the transaction amount and category and add it into the table view.
    The total amount will be updated as the user adds more transactions. 
2. ### Filtering by Amount
    Allow user to filter added transactions amount by upper bound and lower bound. 
3. ### Filtering by Category
    Allow user to filter added transactions amount by its category.
4. ### Undo 
    When there is no row selected, the last change made it to the Transaction table will be reverted. If the user selects multiple rows, these transactions will be removed all together and the change will be reflected on the Total Cost. 
    If no entry available when the user clicked on the undo button, there will be a warning
    message prompts up to remind the user.  
