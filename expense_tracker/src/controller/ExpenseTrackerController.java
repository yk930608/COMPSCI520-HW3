package controller;

import model.ExpenseTrackerModel;
import model.Filter.TransactionFilter;
import model.Transaction;
import view.ExpenseTrackerView;

import javax.swing.*;
import java.util.*;

public class ExpenseTrackerController {

    private final ExpenseTrackerModel model;
    private final ExpenseTrackerView view;
    /**
     * The Controller is applying the Strategy design pattern.
     * This is the has-a relationship with the Strategy class
     * being used in the applyFilter method.
     */
    private TransactionFilter filter;

    public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
        this.model = model;
        this.view = view;
    }

    public void setFilter(TransactionFilter filter) {
        // Sets the Strategy class being used in the applyFilter method.
        this.filter = filter;
    }

    public void refresh() {
        List<Transaction> transactions = model.getTransactions();
        view.refreshTable(transactions);
    }

    public void addTransaction(double amount, String category) {
        if (!InputValidation.isValidAmount(amount)) {
            view.displayMessage("Invalid amount or category entered");
            return;
        }
        if (!InputValidation.isValidCategory(category)) {
            view.displayMessage("Invalid amount or category entered");
            return;
        }

        Transaction t = new Transaction(amount, category);
        model.addTransaction(t);
        view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
        refresh();
    }

    public void applyFilter() {
        //null check for filter
        if (filter != null) {
            // Use the Strategy class to perform the desired filtering
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
        } else {
            JOptionPane.showMessageDialog(view, "No filter applied");
            view.toFront();
        }

    }

    public void undoRecord() {
        List<Transaction> currentTransactions = model.getTransactions();
        List<Transaction> transactionsAfterUndo = new ArrayList<>();
        List<Integer> selectedRow = new ArrayList<>(Arrays.stream(view.getUserSelection()).boxed().toList());
        // If the transaction table is empty return false
        if (currentTransactions.isEmpty()) {
            view.displayMessage("No entry is available!");
        }
        // If no row is been selected, remove the last row
        if (selectedRow.isEmpty()) {
            selectedRow.add(currentTransactions.size()-1);
        }
        Set<Integer> removingIndexes = new HashSet<>(selectedRow);
        // If multiple rows are been selected, remove all these together
        for (int i = 0; i < currentTransactions.size(); i++){
            if(!removingIndexes.contains(i)) {
                transactionsAfterUndo.add(currentTransactions.get(i));
            }
        }
        model.updateTransaction(transactionsAfterUndo);
        view.refreshTable(transactionsAfterUndo);
    }
}
