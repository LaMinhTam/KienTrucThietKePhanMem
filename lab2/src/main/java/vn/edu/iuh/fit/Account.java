package vn.edu.iuh.fit;

public class Account {
    interface Constants {
        String defaultColor = "black"; // Incorrect: Not in uppercase
        int MAX_SPEED = 200; // Incorrect: Not in uppercase
    }

    private String id;
    private String name;
    private int balance;

    public Account() {
    }

    public String GetId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
