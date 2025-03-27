package bankapplication.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bankapplication.db.CustomerRepository;
import bankapplication.model.Customer;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role = request.getParameter("role");
        
        if ("admin".equals(role)) {
            String adminUsername = request.getParameter("adminUsername");
            String adminPassword = request.getParameter("adminPassword");
            
            if (adminUsername.equals("sanjana") && adminPassword.equals("123")) {
                HttpSession session = request.getSession();
                session.setAttribute("admin", "true");
                response.sendRedirect("adminHome.jsp");
            } else {
                response.sendRedirect("index.jsp?error=Wrong credentials. Please enter correct credentials.");
            }
        } else if ("customer".equals(role)) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            CustomerRepository dao = new CustomerRepository();
            Customer customer = dao.authenticate(email, password);
            
            if (customer != null) {
                HttpSession session = request.getSession();
                session.setAttribute("customer", customer);
                session.setAttribute("accountID", customer.getAccountID()); // Ensure accountID is saved
                response.sendRedirect("customerHome.jsp");
            } else {
                response.sendRedirect("index.jsp?error=Wrong credentials. Please enter correct credentials.");
            }
        }
    }
}