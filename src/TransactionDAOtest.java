import java.sql.SQLException;
import java.util.List;

public class TransactionDAOtest {
    public static void main(String[] args) {
        TransactionDAO dao = new TransactionDAO();
        try {
            //pinang testing ito
            /* 
            Transaction newTransaction = new Transaction(
                "INCOME" , 
                "Database test" , 
                "salary" , 
                1000.0
            );
            dao.addTransaction(newTransaction);
            System.out.println("transaction inserted successfully");

              // TEST UPDATE
            Transaction updatedTransaction = new Transaction(
                5,
                "INCOME",
                "Updated Database Test",
                "Salary",
                1500.00
            );

            boolean updated = dao.updateTransaction(updatedTransaction);

            System.out.println("Transaction updated: " + updated);
            */
                                   // TEST DELETE
            // TEST GENERATED DATABASE ID
            Transaction newTransaction = new Transaction(
                "INCOME",
                "ID Test",
                "Salary",
                100.00
            );

            dao.addTransaction(newTransaction);

            System.out.println(
                "Generated ID: " + newTransaction.id
            );

            List<Transaction> savedTransactions = dao.getAllTransactions();

            long testId = savedTransactions.get(savedTransactions.size() - 1).id;

            boolean deleted = dao.deleteTransaction((int) testId);

            System.out.println("Transaction deleted: " + deleted);
            

            

            List<Transaction> transactions = dao.getAllTransactions();
            System.out.println("Transactions found: " + transactions.size());
            
            for (Transaction transaction : transactions)
            {
                System.out.println(" ");
                System.out.println("Type: " + transaction.type);
                System.out.println("Description: " + transaction.description);
                System.out.println("Category: " + transaction.category);
                System.out.println("Amount: " + transaction.amount);
                System.out.println("ID: " + transaction.id );
            }
        }
        catch (SQLException e)
        {
            System.out.println("database error: " + e.getMessage());
        }
    }
}
