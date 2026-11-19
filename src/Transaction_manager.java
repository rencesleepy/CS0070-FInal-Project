import java.sql.SQLException;
import java.util.ArrayList;

// para sa transactions: taga compute kung ilan para sa income at expenses pati na rin ang total nito para mag show sa main gui

public class Transaction_manager {
    private ArrayList<Transaction> transactions = new ArrayList<>(); 
    private TransactionDAO dao = new TransactionDAO();
    public Transaction_manager() {
        try 
        {
            transactions.addAll(dao.getAllTransactions());
            System.out.println("transactions loaded from sqlite");
        }
        catch (SQLException e)
        {
            throw new IllegalStateException ("failed to load transactions from sqlite");
        }
    }

    public void addTransaction(Transaction transaction) {
        try {
            dao.addTransaction(transaction);
            transactions.add(transaction);
            System.out.println("transaction saved to sql");
        }
        catch (SQLException e)
        {
            throw new IllegalStateException (
                "failed to save transaction to sqlite", e
            );
        }
    }

    public double getTotalIncome() {
        double total = 0; 
        for (Transaction transaction : transactions) {
            if (transaction.type.equals("INCOME")) {
                total += transaction.amount;
            }
        }
        return total;
    }

    public double getTotalExpenses() {
        double total = 0;
        for (Transaction transaction : transactions) {
            if (transaction.type.equals("EXPENSE")) {
                total += transaction.amount;
            }
        }
        return total;
    }

    public double getBalance() {
        return getTotalIncome() - getTotalExpenses();
    }

    public static double validateAmount(String amountstr) {
        double money = Double.parseDouble(amountstr.trim());

        if(!Double.isFinite(money) || money <= 0) {
            throw new IllegalArgumentException(
                "Amount must not below zero"
            );
        }
        return money;
    }

    public ArrayList<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public boolean deleteTransaction(Transaction transaction) {
        if (transaction.id <= 0) {
            return false;
        }
        try {
            boolean deleted = dao.deleteTransaction(
                Math.toIntExact(transaction.id)
            );
            if (deleted) {
                transactions.remove(transaction);
                
            }
            return deleted;
        }
        catch (SQLException e)
        {
            throw new IllegalStateException("failed to delete transactioon from sqlite", e);
        }
    }
    
    public boolean editTransaction(Transaction oldTransaction, Transaction updatedTransaction) 
    {
        int index = transactions.indexOf(oldTransaction);
        if (index == -1 || oldTransaction.id <= 0) {
            return false;
        }
        updatedTransaction.id = oldTransaction.id;
        try {
            boolean updated = dao.updateTransaction(updatedTransaction);
            if (updated) {
                transactions.set(index, updatedTransaction);
            }
            return updated;
        }
        catch (SQLException e)
        {
            throw new IllegalStateException("failed to update transaction", e);
        }
    }

    public double getExpenseByCategory(String category) {
        double total = 0;
        for (Transaction transaction : transactions) {
            if (transaction.type.equalsIgnoreCase("EXPENSE") && transaction.category.equalsIgnoreCase(category)) 
            {
                total += transaction.amount;
            }
        }
        return total;
    }

    public double getIncomeByCategory(String category) {
        double total = 0;
        for (Transaction transaction : transactions) {
            if (transaction.type.equalsIgnoreCase("INCOME") && transaction.category.equalsIgnoreCase(category))
            {
                total += transaction.amount;
            }
        }
        return total;
    }

    public double getIncomeByCategoryPercentage(String category) {
        double totalIncome = getTotalIncome();
        if(totalIncome == 0) {
            return 0;
        }

        double categoryIncome = getIncomeByCategory(category);
        return (categoryIncome / totalIncome) * 100;
    }

    public double getCategoryByPercentage(String category) {
        double totalExpenses = getTotalExpenses();
        if (totalExpenses == 0) {
            return 0;
        }
        double categoryExpenses = getExpenseByCategory(category);
        return (categoryExpenses / totalExpenses) * 100;

        /*
        formula: percentage = category expenses * 100 
                              total expenses
        
        */
    }

    public static void main(String[] args) {
        Transaction_manager manager = new Transaction_manager();

        Transaction oldTransaction = new Transaction(
            "EXPENSE" , 
            "Lunch" , 
            "Food" , 
            150.00
        );

        manager.addTransaction(oldTransaction);

        System.out.println("Before editing: " + manager.getTotalExpenses());

        Transaction updatedTransaction = new Transaction(
            "EXPENSE" , 
            "Dinner" , 
            "Food" , 
            250.00
        );

        boolean edited = manager.editTransaction(oldTransaction, updatedTransaction);

        System.out.println("Edited succesfully: " + edited);
        System.out.println("After editing: " + manager.getTotalExpenses());
    }
}
