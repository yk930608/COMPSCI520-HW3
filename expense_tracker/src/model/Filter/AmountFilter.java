package model.Filter;

import java.util.ArrayList;
import java.util.List;

import model.Transaction;
import controller.InputValidation;

public class AmountFilter implements TransactionFilter{
    private double amountFilter;

    public AmountFilter(double amountFilter){
        if(!InputValidation.isValidAmount(amountFilter)){
            throw new IllegalArgumentException("Invalid amount filter");
        } else {
            this.amountFilter = amountFilter;
        }
    }
    @Override
    public List<Transaction> filter(List<Transaction> transactions){
        List<Transaction> filteredTransactions = new ArrayList<>();
        for(Transaction transaction : transactions){
            if(transaction.getAmount() == amountFilter){
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }
    
}
