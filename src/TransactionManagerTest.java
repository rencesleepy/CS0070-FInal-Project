public class TransactionManagerTest {

    public static void main(String[] args) {

        Transaction_manager manager = new Transaction_manager();

        
        Transaction oldTransaction = new Transaction(
            "EXPENSE",
            "Lunch",
            "Food",
            150.00
        );

        manager.addTransaction(oldTransaction);

        System.out.println("Created ID: " + oldTransaction.id);

        
        Transaction updatedTransaction = new Transaction(
            "EXPENSE",
            "Dinner",
            "Food",
            250.00
        );

    
        boolean updated = manager.editTransaction(
            oldTransaction,
            updatedTransaction
        );

        System.out.println("Updated: " + updated);

        // RELOAD FROM DATABASE
        Transaction_manager reloadedManager = new Transaction_manager();

        for (Transaction transaction : reloadedManager.getTransactions()) {

            if (transaction.id == oldTransaction.id) {

                System.out.println("----------------------");
                System.out.println("ID: " + transaction.id);
                System.out.println("Description: " + transaction.description);
                System.out.println("Amount: " + transaction.amount);
            }
        }
    }
}