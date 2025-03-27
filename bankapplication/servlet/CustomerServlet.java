package bankapplication.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bankapplication.db.CustomerRepository;
import bankapplication.model.Customer;

@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        
        if (customer == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        
        if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
            response.sendRedirect("editProfile.jsp?error=All fields are required");
            return;
        }

        CustomerRepository dao = new CustomerRepository();
        boolean updated = dao.updateProfile(customer.getCustomerID(), firstName, lastName, password);
        
        if (updated) {
            session.setAttribute("customer", new Customer(customer.getCustomerID(), firstName, lastName, customer.getEmail(), password, customer.getAccountID()));
            response.sendRedirect("editProfile.jsp?msg=Profile Updated Successfully");
        } else {
            response.sendRedirect("editProfile.jsp?error=Failed to Update Profile");
        }
    }
}