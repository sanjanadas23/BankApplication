package bankapplication.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bankapplication.db.AccountRepository;
import bankapplication.db.CustomerRepository;
import bankapplication.model.Customer;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        CustomerRepository customerRepo = new CustomerRepository();
        AccountRepository accountRepo = new AccountRepository();

        if ("addCustomer".equals(action)) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            int customerID = customerRepo.addCustomer(firstName, lastName, email, password);
            if (customerID > 0) {
                response.sendRedirect("adminHome.jsp?msg=Customer registered successfully! Customer ID: " + customerID);
            } else {
                response.sendRedirect("addCustomer.jsp?error=Failed to register customer.");
            }
        }
        
        if ("addAccount".equals(action)) {
            try {
                int customerID = Integer.parseInt(request.getParameter("customerID"));
                double initialBalance = Double.parseDouble(request.getParameter("initialBalance")); // Get initial balance
                
                if (!customerRepo.customerExists(customerID)) {
                    response.sendRedirect("addAccount.jsp?error=Customer ID does not exist.");
                    return;
                }
                
                if (accountRepo.accountExists(customerID)) {
                    response.sendRedirect("addAccount.jsp?error=Account already exists for this customer.");
                    return;
                }
                
                int accountID = accountRepo.createAccount(customerID, initialBalance); // Pass initial balance
                if (accountID > 0) {
                    response.sendRedirect("adminHome.jsp?msg=Account created successfully! Account Number: " + accountID);
                } else {
                    response.sendRedirect("addAccount.jsp?error=Failed to create account.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("addAccount.jsp?error=Invalid input or server error.");
            }
        }
    }
}