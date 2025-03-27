package bankapplication.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import bankapplication.model.Customer;

public class AccountRepository {
    public boolean accountExists(int customerID) {
        String query = "SELECT COUNT(*) FROM Accounts WHERE customerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Account already exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int createAccount(int customerID, double initialBalance) {
        if (accountExists(customerID)) {
            return -2; // Account already exists
        }
        String query = "INSERT INTO Accounts (accountID, customerID, balance) VALUES (?, ?, ?)";
        int accountID = generateAccountNumber();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, accountID);
            ps.setInt(2, customerID);
            ps.setDouble(3, initialBalance);
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                return accountID;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getAccountNumber(int customerID) {
        String query = "SELECT accountID FROM Accounts WHERE customerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("accountID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if no account is found
    }

    public double getBalance(int customerID) {
        String query = "SELECT balance FROM Accounts WHERE customerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0; // Return 0 if balance is not found
    }

    private int generateAccountNumber() {
        Random random = new Random();
        return 10000000 + random.nextInt(90000000); // Ensures an 8-digit number
    }
}