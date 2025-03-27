<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Account</title>
</head>
<body>
    <h2>Add Bank Account</h2>
    <% if (request.getParameter("msg") != null) { %>
        <p style="color:green;"><%= request.getParameter("msg") %></p>
    <% } %>
    <% if (request.getParameter("error") != null) { %>
        <p style="color:red;"><%= request.getParameter("error") %></p>
    <% } %>
    <form action="AdminServlet" method="post">
    <label for="customerID">Customer ID:</label>
    <input type="text" id="customerID" name="customerID" required>
    
    <label for="initialBalance">Initial Balance:</label>
    <input type="number" id="initialBalance" name="initialBalance" required>
    
    <input type="hidden" name="action" value="addAccount">
    <button type="submit">Create Account</button>
</form>
</body>
</html>
