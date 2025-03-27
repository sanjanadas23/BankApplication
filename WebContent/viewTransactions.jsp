<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="bankapplication.db.TransactionRepository, java.util.List, bankapplication.model.Transaction" %>
<%
    List<Transaction> transactions = new TransactionRepository().getAllTransactions();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Transaction History</title>
</head>
<body>
    <h2>All Transactions</h2>
<% if (transactions.isEmpty()) { %>
    <p>No transactions found.</p>
<% } else { %>
    <table border="1">
        <tr><th>Account ID</th><th>Date</th><th>Type</th><th>Amount</th><th>To Account</th></tr>
        <% for (Transaction t : transactions) { %>
        <tr>
            <td><%= t.getAccountID() %></td>
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