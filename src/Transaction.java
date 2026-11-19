
public class Transaction{
    long id;
    String type;
    String description;
    String category;
    double amount;

    public Transaction(String type, String description, 
        String category, double amount) {
        this.id = 0;
        this.type = type;
        this.description = description;
        this.category = category;
        this.amount = amount;
        
    };

    public Transaction(long id, String type, String description, String category, double amount)
    {
        this.id = id;
        this.type = type;
        this.description = description;
        this.category = category;
        this.amount = amount;
    }
}


