import javax.swing.JOptionPane;
import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import view.ExpenseTrackerView;
import model.Filter.AmountFilter;
import model.Filter.CategoryFilter;

public class ExpenseTrackerApp {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // Create MVC components
        ExpenseTrackerModel model = new ExpenseTrackerModel();
        ExpenseTrackerView view = new ExpenseTrackerView();
        ExpenseTrackerController controller = new ExpenseTrackerController(model, view);


        // Initialize view
        view.setVisible(true);



        // Handle add transaction button clicks
        view.getAddTransactionBtn().addActionListener(e -> controller.performAddTransactionBtnClick());

        // Add action listener to the "Apply Category Filter" button
        view.addApplyCategoryFilterListener(e -> {
            try{
                String categoryFilterInput = view.getCategoryFilterInput();
                CategoryFilter categoryFilter = new CategoryFilter(categoryFilterInput);
                if (categoryFilterInput != null) {
                    // controller.applyCategoryFilter(categoryFilterInput);
                    controller.setFilter(categoryFilter);
                    controller.applyFilter();
                }
            }catch(IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(view, exception.getMessage());
                view.toFront();
            }});

        // Handle undo transaction button clicks
        view.getUndoBtn().addActionListener(e -> controller.performUnDoBtnClick());
        // Add action listener to the "Apply Amount Filter" button
        view.addApplyAmountFilterListener(e -> {
            try{
                double amountFilterInput = view.getAmountFilterInput();
                AmountFilter amountFilter = new AmountFilter(amountFilterInput);
                if (amountFilterInput != 0.0) {
                    controller.setFilter(amountFilter);
                    controller.applyFilter();
                }
            }catch(IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(view,exception.getMessage());
                view.toFront();
            }});

    }
}
