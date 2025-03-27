package bankapplication.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bankapplication.db.TransactionRepository;

@WebServlet("/TransactionServlet")
public class TransactionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("accountID") == null) {
            response.sendRedirect("login.jsp?error=Session expired. Please log in again.");
            return;
        }

        int accountID = (int) session.getAttribute("accountID");
        String type = request.getParameter("transactionType");
        String amountStr = request.getParameter("amount");
        String toAccountIDStr = request.getParameter("toAccountID");

        if (type == null || amountStr == null || amountStr.isEmpty()) {
            response.sendRedirect("newTransaction.jsp?error=Invalid input. Please fill all fields.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                response.sendRedirect("newTransaction.jsp?error=Amount must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("newTransaction.jsp?error=Invalid amount format.");
            return;
        }

        Integer toAccountID = null;
        if (toAccountIDStr != null && !toAccountIDStr.isEmpty()) {
            try {
                toAccountID = Integer.parseInt(toAccountIDStr);
            } catch (NumberFormatException e) {
                response.sendRedirect("newTransaction.jsp?error=Invalid account ID format.");
                return;
            }
        }

        TransactionRepository dao = new TransactionRepository();
        boolean success = false;

        if ("Transfer".equals(type) && toAccountID != null) {
            if (!dao.accountExists(toAccountID)) {
                response.sendRedirect("newTransaction.jsp?error=This account ID does not exist.");
                return;
            }
            success = dao.transfer(accountID, toAccountID, amount);
        } else {
            success = dao.processTransaction(accountID, type, amount, null);
        }

        if (success) {
            response.sendRedirect("customerHome.jsp?msg=Transaction Completed Successfully");
        } else {
            response.sendRedirect("newTransaction.jsp?error=Transaction failed. Please check your details and try again.");
        }
    }
}