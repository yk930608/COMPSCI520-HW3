package view;

import model.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ExpenseTrackerView extends JFrame {

    private final JTable transactionsTable;
    private final JButton addTransactionBtn;
    private JFormattedTextField amountField;
    private JTextField categoryField;
    private final DefaultTableModel model;

    // private JTextField dateFilterField;
    private final JTextField categoryFilterField;
    private final JButton categoryFilterBtn;

    private final JTextField amountFilterField;
    private final JButton amountFilterBtn;
    private final JButton undoBtn;
    private final JOptionPane  invalidInputJOptionPane;
    private final JOptionPane  invalidUndoJOptionPane;
    private final JDialog invalidInputDialog;
    private final JDialog invalidUndoDialog;
    public ExpenseTrackerView() {
        setTitle("Expense Tracker"); // Set title
        setSize(600, 400); // Make GUI larger

        String[] columnNames = {"serial", "Amount", "Category", "Date"};
        this.model = new DefaultTableModel(columnNames, 0);


        // Create table
        transactionsTable = new JTable(model);

        addTransactionBtn = new JButton("Add Transaction");

        // Create UI components
        JLabel amountLabel = new JLabel("Amount:");
        NumberFormat format = NumberFormat.getNumberInstance();

        amountField = new JFormattedTextField(format);
        amountField.setColumns(10);


        JLabel categoryLabel = new JLabel("Category:");
        categoryField = new JTextField(10);


        JLabel categoryFilterLabel = new JLabel("Filter by Category:");
        categoryFilterField = new JTextField(10);
        categoryFilterBtn = new JButton("Filter by Category");

        JLabel amountFilterLabel = new JLabel("Filter by Amount:");
        amountFilterField = new JTextField(10);
        amountFilterBtn = new JButton("Filter by Amount");

        undoBtn = new JButton("Undo");
        invalidInputJOptionPane = new JOptionPane("Invalid amount or category entered");
        invalidInputDialog = invalidInputJOptionPane.createDialog("INVALID INPUT");
        invalidUndoJOptionPane = new JOptionPane("No entry is available!");
        invalidUndoDialog = invalidUndoJOptionPane.createDialog("UNDO ERROR");

        // Layout components
        JPanel inputPanel = new JPanel();
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(categoryLabel);
        inputPanel.add(categoryField);
        inputPanel.add(addTransactionBtn);
        inputPanel.add(undoBtn);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(amountFilterBtn);
        buttonPanel.add(categoryFilterBtn);

        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(transactionsTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set frame properties
        setSize(600, 400); // Increase the size for better visibility
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


    }

    public DefaultTableModel getTableModel() {
        return model;
    }


    public List<Transaction> getTransactionsTable() {
        List<Transaction> result = new ArrayList<>();
        int numberOfRows = transactionsTable.getRowCount();
        for(int i = 0; i < numberOfRows -1; i++){
            double curAmount = Float.parseFloat(transactionsTable.getValueAt(i, 1).toString());
            String curCategory = transactionsTable.getValueAt(i, 2).toString();
            String curDate = transactionsTable.getValueAt(i, 3).toString();
            Transaction curTransition = new Transaction(curAmount, curCategory, curDate);
            result.add(curTransition);
        }
        return result;
    }

    public double getAmountField() {
        if (amountField.getText().isEmpty()) {
            return 0;
        } else {
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

    public void addApplyCategoryFilterListener(ActionListener listener) {
        categoryFilterBtn.addActionListener(listener);
    }

    public String getCategoryFilterInput() {
        return JOptionPane.showInputDialog(this, "Enter Category Filter:");
    }


    public void addApplyAmountFilterListener(ActionListener listener) {
        amountFilterBtn.addActionListener(listener);
    }

    public double getAmountFilterInput() {
        String input = JOptionPane.showInputDialog(this, "Enter Amount Filter:");
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            // Handle parsing error here
            // You can show an error message or return a default value
            return 0.0; // Default value (or any other appropriate value)
        }
    }

    public void refreshTable(List<Transaction> transactions) {
        // Clear existing rows
        model.setRowCount(0);
        // Get row count
        int rowNum = model.getRowCount();
        double totalCost = 0;
        // Calculate total cost
        for (Transaction t : transactions) {
            totalCost += t.getAmount();
        }

        // Add rows from transactions list
        for (Transaction t : transactions) {
            model.addRow(new Object[]{rowNum += 1, t.getAmount(), t.getCategory(), t.getTimestamp()});
        }
        // Add total row
        Object[] totalRow = {"Total", null, null, totalCost};
        model.addRow(totalRow);

        // Fire table update
        transactionsTable.updateUI();

    }


    public JButton getAddTransactionBtn() {
        return addTransactionBtn;
    }

    public JButton getUndoBtn() {
        return undoBtn;
    }

    public void highlightRows(List<Integer> rowIndexes) {
        // The row indices are being used as hashcodes for the transactions.
        // The row index directly maps to the the transaction index in the list.
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

    public int[] getUserSelection() {
        return transactionsTable.getSelectedRows();
    }

    public JTable getJTable() {return this.transactionsTable; }

    public JDialog getInvalidInputDialog() {return this.invalidInputDialog; }

    public JDialog getInvalidUndoDialog() {return this.invalidUndoDialog; }

}
