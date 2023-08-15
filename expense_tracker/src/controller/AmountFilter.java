package controller;

import java.util.ArrayList;
import java.util.List;

import model.Transaction;

public class AmountFilter implements TransactionFilter{
    private double amountFilter;

    public AmountFilter(double amountFilter){
        this.amountFilter = amountFilter;
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
