package controller;

import view.ExpenseTrackerView;

import java.util.ArrayList;
import java.util.List;

import model.ExpenseTrackerModel;
import model.Transaction;

public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;
  }

  public void refresh() {
    List<Transaction> transactions = model.getTransactions();
    view.refreshTable(transactions);
  }

  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }
    
    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
    return true;
  }

  public void applyFilter(TransactionFilter filter) {
    List<Transaction> transactions = model.getTransactions();
    List<Transaction> filteredTransactions = filter.filter(transactions);
    List<Integer> rowIndexes = new ArrayList<>();
    for (Transaction t : filteredTransactions) {
      int rowIndex = transactions.indexOf(t);
      if (rowIndex != -1) {
        rowIndexes.add(rowIndex);
      }
    }
    view.highlightRows(rowIndexes);
  }

  // public void applyCategoryFilter(String categoryFilterInput) {
  //   // Create a CategoryFilter instance based on the user input
  //   CategoryFilter categoryFilter = new CategoryFilter(categoryFilterInput);

  //   // Get transactions from model
  //   List<Transaction> transactions = model.getTransactions(); 

  //   // Apply the filter and pass the filtered transactions to the view
  //   List<Transaction> filteredTransactions = categoryFilter.filter(transactions);
  //     // Get indices of filtered transactions
  //   List<Integer> rowIndexes = new ArrayList<>();
  //   for (Transaction t : filteredTransactions) {
  //       int rowIndex = transactions.indexOf(t);
  //       if (rowIndex != -1) {
  //           rowIndexes.add(rowIndex);
  //       }
  //   }
  //   // view.refreshTable(filteredTransactions);
  //   view.highlightRows(rowIndexes);
  // }

  // public void applyAmountFilter(double amountFilterInput){
  //   AmountFilter amountFilter = new AmountFilter(amountFilterInput);
  //   List<Transaction> transactions = model.getTransactions();
  //   List<Transaction> filteredTransactions = amountFilter.filter(transactions);
  //   List<Integer> rowIndexes = new ArrayList<>();
  //   for(Transaction t : filteredTransactions){
  //     int rowIndex = transactions.indexOf(t);
  //     if(rowIndex != -1){
  //       rowIndexes.add(rowIndex);
  //     }
  //   }
  //   view.highlightRows(rowIndexes);
  // }
  
}
