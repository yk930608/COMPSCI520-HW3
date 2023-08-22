package model.Filter;

import java.util.List;

import model.Transaction;

public interface TransactionFilter {

  public List<Transaction> filter(List<Transaction> transactions);

}