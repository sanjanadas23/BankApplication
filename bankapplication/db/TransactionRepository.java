package bankapplication.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import bankapplication.model.Customer;
import bankapplication.model.Transaction;

public class TransactionRepository {
	public boolean transfer(int fromAccountID, int toAccountID, double amount) {
	    String balanceQuery = "SELECT balance FROM Accounts WHERE accountID = ?";
	    String debitQuery = "UPDATE Accounts SET balance = balance - ? WHERE accountID = ?";
	    String creditQuery = "UPDATE Accounts SET balance = balance + ? WHERE accountID = ?";
	    String insertTransaction = "INSERT INTO Transactions (accountID, transactionType, amount, toAccountID) VALUES (?, ?, ?, ?)";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement balanceCheck = conn.prepareStatement(balanceQuery);
	         PreparedStatement debit = conn.prepareStatement(debitQuery);
	         PreparedStatement credit = conn.prepareStatement(creditQuery);
	         PreparedStatement insert = conn.prepareStatement(insertTransaction)) {
	        
	        balanceCheck.setInt(1, fromAccountID);
	        ResultSet rs = balanceCheck.executeQuery();
	        if (rs.next() && rs.getDouble("balance") >= amount) {
	            debit.setDouble(1, amount);
	            debit.setInt(2, fromAccountID);
	            credit.setDouble(1, amount);
	            credit.setInt(2, toAccountID);
	            
	            if (debit.executeUpdate() > 0 && credit.executeUpdate() > 0) {
	                insert.setInt(1, fromAccountID);
	                insert.setString(2, "Transfer");
	                insert.setDouble(3, amount);
	                insert.setInt(4, toAccountID);
	                insert.executeUpdate();
	                return true;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public boolean processTransaction(int accountID, String type, double amount, Integer toAccountID) {
	    String updateQuery = "UPDATE Accounts SET balance = balance + ? WHERE accountID = ?";
	    String insertTransaction = "INSERT INTO Transactions (accountID, transactionType, amount, toAccountID) VALUES (?, ?, ?, ?)";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement update = conn.prepareStatement(updateQuery);
	         PreparedStatement insert = conn.prepareStatement(insertTransaction)) {
	        
	        update.setDouble(1, "Credit".equals(type) ? amount : -amount);
	        update.setInt(2, accountID);
	        
	        if (update.executeUpdate() > 0) {
	            insert.setInt(1, accountID);
	            insert.setString(2, type);
	            insert.setDouble(3, amount);
	            insert.setObject(4, toAccountID);
	            insert.executeUpdate();
	            return true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public boolean accountExists(int accountID) {
	    String query = "SELECT COUNT(*) FROM Accounts WHERE accountID = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setInt(1, accountID);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next() && rs.getInt(1) > 0) {
	            return true; // Account exists
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public double getInitialBalance(int accountID) {
	    String query = "SELECT balance FROM Accounts WHERE accountID = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setInt(1, accountID);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return rs.getDouble("balance");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0.0;
	}
	
	
    
	public List<Transaction> getTransactions(int accountID) {
	    List<Transaction> transactions = new ArrayList<>();
	    String query = "SELECT * FROM Transactions WHERE accountID = ? ORDER BY transactionDate DESC";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setInt(1, accountID);
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            transactions.add(new Transaction(
	                rs.getInt("transactionID"),
	                rs.getInt("accountID"),
	                rs.getString("transactionType"),
	                rs.getDouble("amount"),
	                rs.getInt("toAccountID"),
	                rs.getTimestamp("transactionDate")
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return transactions;
	}

	public List<Transaction> getAllTransactions() {
	    List<Transaction> transactions = new ArrayList<>();
	    String query = "SELECT * FROM Transactions ORDER BY transactionDate DESC";
	    
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {
	        
	        while (rs.next()) {
	            transactions.add(new Transaction(
	                rs.getInt("transactionID"),
	                rs.getInt("accountID"),
	                rs.getString("transactionType"),
	                rs.getDouble("amount"),
	                rs.getInt("toAccountID"),
	                rs.getTimestamp("transactionDate")
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return transactions;
	}
}
