package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.TransactionFilter;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import model.Transaction;

import java.util.List;

public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;

  // private JTextField dateFilterField;
  private JTextField categoryFilterField;
  private JButton categoryFilterBtn;

  
  

  public List<Transaction> getTransactionsTable() {
    return (List<Transaction>) transactionsTable;
  }

  public double getAmountField() {
    if(amountField.getText().isEmpty()) {
      return 0;
    }else {
    double amount = Double.parseDouble(amountField.getText());
    return amount;
    }
  }

  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  
  public String getCategoryField() {
    return categoryField.getText();
  }

  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }

  public ExpenseTrackerView(DefaultTableModel model) {
    setTitle("Expense Tracker"); // Set title
    setSize(600, 400); // Make GUI larger
    this.model = model;

    addTransactionBtn = new JButton("Add Transaction");

    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();

    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    
    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);
    
    // JLabel dateFilterLabel = new JLabel("Filter by Date:");
    // dateFilterField = new JTextField(10);
    // JButton dateFilterBtn = new JButton("Filter by Date");

    JLabel categoryFilterLabel = new JLabel("Filter by Category:");
    categoryFilterField = new JTextField(10);
    categoryFilterBtn = new JButton("Filter by Category");
    


    // Create table
    transactionsTable = new JTable(model);
  
    // Layout components
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel); 
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);
    // inputPanel.add(categoryFilterLabel);
    // inputPanel.add(categoryFilterField);
    inputPanel.add(categoryFilterBtn);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addTransactionBtn);
  
    // Add panels to frame
    add(inputPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER); 
    add(buttonPanel, BorderLayout.SOUTH);
  
    // Set frame properties
    setSize(800, 300); // Increase the size for better visibility
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  

  
  }

  public void addApplyCategoryFilterListener(ActionListener listener) {
    categoryFilterBtn.addActionListener(listener);
  }

  public String getCategoryFilterInput() {
    return JOptionPane.showInputDialog(this, "Enter Category Filter:");
}
  public void refreshTable(List<Transaction> transactions) {
      // Clear existing rows
      model.setRowCount(0);
  
      // Add rows from transactions list
      for(Transaction t : transactions) {
        model.addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()}); 
      }
  
      // Fire table update
      transactionsTable.updateUI();
  
    }  
  

  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }
  public DefaultTableModel getTableModel() {
    return model;
  }

public void highlightRows(List<Integer> rowIndexes) {
    transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (rowIndexes.contains(row)) {
                c.setBackground(new Color(173, 255, 168)); // Light green
            } else {
                c.setBackground(table.getBackground());
            }
            return c;
        }
    });

    transactionsTable.repaint();
}


}
