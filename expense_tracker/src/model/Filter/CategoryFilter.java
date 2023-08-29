package model.Filter;

import java.util.ArrayList;
import java.util.List;

import model.Transaction;
import controller.InputValidation;

public class CategoryFilter implements TransactionFilter {
    private String categoryFilter;

    public CategoryFilter(String categoryFilter) {
        if(!InputValidation.isValidCategory(categoryFilter)){
            throw new IllegalArgumentException("Invalid category filter");
        }else{
            this.categoryFilter = categoryFilter;
        }
    }

    @Override
    public List<Transaction> filter(List<Transaction> transactions) {

        List<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getCategory().equalsIgnoreCase(categoryFilter)) {
                filteredTransactions.add(transaction);
            }
        }

        return filteredTransactions;
    }
}
