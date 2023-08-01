package test;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import model.ExpenseTrackerModel;
import model.Transaction;


public class TestExample {
  
  ExpenseTrackerModel model;

  @Before
  public void setup() {
    model = new ExpenseTrackerModel();
  }

  @Test
  public void testAddTransaction() {
    Transaction t = new Transaction(10.00, "Food"); 
    model.addTransaction(t);

    assertEquals(1, model.getTransactions().size());
  }

  @Test 
  public void testRemoveTransaction() {
    Transaction t1 = new Transaction(18.25, "Food");
    model.addTransaction(t1);

    Transaction t2 = new Transaction(15.00, "Gas");  
    model.addTransaction(t2);

    model.removeTransaction(t1);

    assertEquals(1, model.getTransactions().size());
  }
}