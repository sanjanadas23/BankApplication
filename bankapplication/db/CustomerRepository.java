package bankapplication.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bankapplication.model.Customer;

public class CustomerRepository {
    public int addCustomer(String firstName, String lastName, String email, String password) {
        String query = "INSERT INTO Customers (firstName, lastName, email, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, password);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Return generated Customer ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if insertion fails
    }
    
    public Customer authenticate(String email, String password) {
        String query = "SELECT c.*, a.accountID FROM Customers c " +
                       "LEFT JOIN Accounts a ON c.customerID = a.customerID " +
                       "WHERE c.email = ? AND c.password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(rs.getInt("customerID"), rs.getString("firstName"), 
                                    rs.getString("lastName"), rs.getString("email"), 
                                    rs.getString("password"), rs.getInt("accountID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean updateProfile(int customerID, String firstName, String lastName, String password) {
        String query = "UPDATE Customers SET firstName = ?, lastName = ?, password = ? WHERE customerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, password);
            ps.setInt(4, customerID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT c.*, a.accountID FROM Customers c " +
                       "LEFT JOIN Accounts a ON c.customerID = a.customerID";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                customers.add(new Customer(
                    rs.getInt("customerID"), 
                    rs.getString("firstName"), 
                    rs.getString("lastName"), 
                    rs.getString("email"), 
                    rs.getString("password"),
                    rs.getInt("accountID")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    
    public boolean customerExists(int customerID) {
        String query = "SELECT COUNT(*) FROM Customers WHERE customerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Customer exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
