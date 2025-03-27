package bankapplication.model;

import java.sql.Timestamp;
import java.util.Date;

public class Transaction {
	private int transactionID;
    private int accountID;
    private String transactionType;
    private double amount;
    private Integer toAccountID;
    private Timestamp transactionDate;


    public Transaction(int transactionID, int accountID, String transactionType, double amount, Integer toAccountID, Timestamp transactionDate) {
        this.transactionID = transactionID;
        this.accountID = accountID;
        this.transactionType = transactionType;
        this.amount = amount;
        this.toAccountID = toAccountID;
        this.transactionDate = transactionDate;
    }


	public int getTransactionID() {
		return transactionID;
	}


	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}


	public int getAccountID() {
		return accountID;
	}


	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}


	public String getTransactionType() {
		return transactionType;
	}


	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public Integer getToAccountID() {
		return toAccountID;
	}


	public void setToAccountID(Integer toAccountID) {
		this.toAccountID = toAccountID;
	}


	public Timestamp getTransactionDate() {
		return transactionDate;
	}


	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}


    
}
