package controller;

import java.util.List;

import model.Transaction;

public interface TransactionFilter {

  public List<Transaction> filter(List<Transaction> transactions);

}