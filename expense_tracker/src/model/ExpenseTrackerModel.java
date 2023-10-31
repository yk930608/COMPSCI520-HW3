package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpenseTrackerModel {

    //encapsulation - data integrity
    private List<Transaction> transactions;

    public ExpenseTrackerModel() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction t) {
        // Perform input validation to guarantee that all transactions added are non-null.
        if (t == null) {
            throw new IllegalArgumentException("The new transaction must be non-null.");
        }
        transactions.add(t);
    }

    public void removeTransaction(Transaction t) {
        transactions.remove(t);
    }

    public List<Transaction> getTransactions() {
        //encapsulation - data integrity
        return Collections.unmodifiableList(new ArrayList<>(transactions));
    }

    public void updateTransaction(List<Transaction> t) {
        transactions = t;
    }

}
