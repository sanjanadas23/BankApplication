package bankapplication.model;

public class Account {
    private int accountID, customerID;
    private double balance;

    public Account(int accountID, int customerID, double balance) {
        this.accountID = accountID;
        this.customerID = customerID;
        this.balance = balance;
    }

    public int getAccountID() { 
    	return accountID; 
    	}
    public int getCustomerID() { 
    	return customerID; 
    	}
    public double getBalance() { 
    	return balance; 
    	}
}
