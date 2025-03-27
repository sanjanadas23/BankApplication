package bankapplication.model;

public class Customer {
    private int customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int accountID; // Add this field if not present

    // Constructor
    public Customer(int customerID, String firstName, String lastName, String email, String password, int accountID) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.accountID = accountID; // Assign account ID
    }

    // Getters
    public int getCustomerID() {
        return customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAccountID() { 
        return accountID;
    }

    // Setter for account ID (if needed)
    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }
}