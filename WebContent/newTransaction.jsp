<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="bankapplication.model.Customer" %>
<%
    Customer customer = (Customer) session.getAttribute("customer");
    if (customer == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>New Transaction</title>
</head>
<body>
    <h2>New Transaction</h2>
    <% if (request.getParameter("error") != null) { %>
        <p style="color:red;"><%= request.getParameter("error") %></p>
    <% } %>
    <form action="TransactionServlet" method="post">
        <select name="transactionType">
            <option value="Credit">Credit</option>
            <option value="Debit">Debit</option>
            <option value="Transfer">Transfer</option>
        </select>
        Amount: <input type="number" name="amount" required><br>
        To Account (if Transfer): <input type="text" name="toAccountID"><br>
        <input type="submit" value="Submit">
    </form>
</body>
</html>