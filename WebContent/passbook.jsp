<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="bankapplication.db.TransactionRepository, java.util.List, bankapplication.model.Transaction" %>    
<%
    // Ensure the user is logged in and has an account ID
    if (session.getAttribute("accountID") == null) {
        response.sendRedirect("login.jsp?error=Please log in first.");
        return;
    }

    int accountID = (int) session.getAttribute("accountID");
    TransactionRepository transactionRepo = new TransactionRepository();
    List<Transaction> transactions = transactionRepo.getTransactions(accountID);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Passbook</title>
</head>
<body>
    <h2>Passbook</h2>
    <% if (transactions.isEmpty()) { %>
        <p>No transactions found.</p>
    <% } else { %>
        <table border="1">
            <tr><th>Date</th><th>Type</th><th>Amount</th><th>To Account</th></tr>
            <% for (Transaction t : transactions) { %>
            <tr>
                <td><%= t.getTransactionDate() %></td>
                <td><%= t.getTransactionType() %></td>
                <td><%= t.getAmount() %></td>
                <td><%= t.getToAccountID() == null ? "-" : t.getToAccountID() %></td>
            </tr>
            <% } %>
        </table>
    <% } %>
</body>
</html>