// package test;

import java.util.Date;
import java.util.List;
import java.text.ParseException;

import model.Filter.AmountFilter;
import model.Filter.CategoryFilter;
import org.junit.Before;
import org.junit.Test;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Transaction;
import view.ExpenseTrackerView;

import javax.swing.*;

import static org.junit.Assert.*;


public class TestExample {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  private ExpenseTrackerController controller;

  @Before
  public void setup() {
    model = new ExpenseTrackerModel();
    view = new ExpenseTrackerView();
    controller = new ExpenseTrackerController(model, view);
  }

    public double getTotalCost() {
        double totalCost = 0.0;
        List<Transaction> allTransactions = model.getTransactions(); // Using the model's getTransactions method
        for (Transaction transaction : allTransactions) {
            totalCost += transaction.getAmount();
        }
        return totalCost;
    }


    public void checkTransaction(double amount, String category, Transaction transaction) {
	assertEquals(amount, transaction.getAmount(), 0.01);
        assertEquals(category, transaction.getCategory());
        String transactionDateString = transaction.getTimestamp();
        Date transactionDate = null;
        try {
            transactionDate = Transaction.dateFormatter.parse(transactionDateString);
        }
        catch (ParseException pe) {
            pe.printStackTrace();
            transactionDate = null;
        }
        Date nowDate = new Date();
        assertNotNull(transactionDate);
        assertNotNull(nowDate);
        // They may differ by 60 ms
        assertTrue(nowDate.getTime() - transactionDate.getTime() < 60000);
    }


    @Test
    public void testAddTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add a transaction
	double amount = 50.0;
	String category = "food";
        assertTrue(controller.addTransaction(amount, category));
    
        // Post-condition: List of transactions contains only
	//                 the added transaction	
        assertEquals(1, model.getTransactions().size());
    
        // Check the contents of the list
	Transaction firstTransaction = model.getTransactions().get(0);
	checkTransaction(amount, category, firstTransaction);
	
	// Check the total amount
        assertEquals(amount, getTotalCost(), 0.01);
    }


    @Test
    public void testRemoveTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add and remove a transaction
	double amount = 50.0;
	String category = "food";
        Transaction addedTransaction = new Transaction(amount, category);
        model.addTransaction(addedTransaction);
    
        // Pre-condition: List of transactions contains only
	//                the added transaction
        assertEquals(1, model.getTransactions().size());
	Transaction firstTransaction = model.getTransactions().get(0);
	checkTransaction(amount, category, firstTransaction);

	assertEquals(amount, getTotalCost(), 0.01);
	
	// Perform the action: Remove the transaction
        model.removeTransaction(addedTransaction);
    
        // Post-condition: List of transactions is empty
        List<Transaction> transactions = model.getTransactions();
        assertEquals(0, transactions.size());
    
        // Check the total cost after removing the transaction
        double totalCost = getTotalCost();
        assertEquals(0.00, totalCost, 0.01);
    }

    @Test
    public void testAddTransactionToView(){
        assertEquals(0, model.getTransactions().size());
        double amount = 50.0;
        String category = "food";
        assertTrue(controller.addTransaction(amount, category));
        assertEquals(1, model.getTransactions().size());
        Transaction actualTransactions = model.getTransactions().get(0);
        List<Transaction> expectedTransactions = view.getTransactionsTable();
        compareTransaction(actualTransactions, expectedTransactions.get(0));
        checkTransaction(amount, category, actualTransactions);
        assertEquals(amount, getTotalCost(), 0.01);
    }

    @Test
    public void testInvalidInputHandling(){
      double invalidAmount = 0.0;
      String validCategory = "food";
      assertFalse(controller.addTransaction(invalidAmount, validCategory));
    }

    @Test
    public void testFilterByAmount() {
      Transaction expectedTransaction = new Transaction(1.0, "food");
      Transaction otherFoodTransaction = new Transaction(2.0, "food");
      Transaction otherBillTransaction = new Transaction(2.0, "bills");
      List<Transaction> transactions = List.of(expectedTransaction, otherBillTransaction, otherFoodTransaction);
      AmountFilter filter = new AmountFilter(1.0);
      List<Transaction> filteredTransactions = filter.filter(transactions);
      assertEquals(filteredTransactions.size(), 1);
      compareTransaction(filteredTransactions.get(0), expectedTransaction);
    }

    @Test
    public void testFilterByCategory() {
        Transaction otherFoodTransaction1 = new Transaction(1.0, "food");
        Transaction otherFoodTransaction2 = new Transaction(2.0, "food");
        Transaction expectedTransaction = new Transaction(2.0, "bills");
        List<Transaction> transactions = List.of(expectedTransaction, otherFoodTransaction1, otherFoodTransaction2);
        CategoryFilter filter = new CategoryFilter("bills");
        List<Transaction> filteredTransactions = filter.filter(transactions);
        assertEquals(filteredTransactions.size(), 1);
        compareTransaction(filteredTransactions.get(0), expectedTransaction);
    }
    @Test
    public void testUndoDisallowed() {
      assertFalse(controller.undoRecord());
    }

    @Test
    public void testUndoAllowed() {
      Transaction transaction = new Transaction(1.0, "food");
      model.addTransaction(transaction);
      assertTrue(controller.undoRecord());
      List<Transaction> expectedTransactions = view.getTransactionsTable();
      assertEquals(expectedTransactions.size(),0);
      assertEquals(0.0, getTotalCost(), 0.01);
    }

    private void compareTransaction(Transaction actualTransition, Transaction expectTransition){
        assertEquals(actualTransition.getAmount(), expectTransition.getAmount(), 0.0);
        assertEquals(actualTransition.getCategory(), expectTransition.getCategory());
        assertEquals(actualTransition.getTimestamp(), expectTransition.getTimestamp());
    }
}
