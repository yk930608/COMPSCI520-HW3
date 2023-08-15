package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpenseTrackerModel {

  private List<Transaction> transactions;

  public ExpenseTrackerModel() {
    transactions = new ArrayList<>(); 
  }

  public void addTransaction(Transaction t) {
    transactions.add(t);
  }

  public void removeTransaction(Transaction t) {
    transactions.remove(t);
  }

  public List<Transaction> getTransactions() {
    //encapsulation
    //return a copy of the list of transactions instead of actual list
    //Make the copied list unmodifiable
    //this will ensure data integrity
    // return new ArrayList<>(transactions);
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }

}