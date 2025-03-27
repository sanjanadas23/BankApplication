<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="bankapplication.db.CustomerRepository, bankapplication.db.AccountRepository, java.util.List, bankapplication.model.Customer" %>
<%
CustomerRepository customerRepo = new CustomerRepository();
AccountRepository accountRepo = new AccountRepository();
List<Customer> customers = customerRepo.getAllCustomers();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer List</title>
</head>
<body>
    <h2>Customer List</h2>
<table border="1">
    <tr>
        <th>Customer ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Account Number</th>
        <th>Balance</th>
    </tr>
    <% for (Customer c : customers) { %>
    <tr>
        <td><%= c.getCustomerID() %></td>
        <td><%= c.getFirstName() %> <%= c.getLastName() %></td>
        <td><%= c.getEmail() %></td>
        <% if (accountRepo.accountExists(c.getCustomerID())) { %>
            <td><%= accountRepo.getAccountNumber(c.getCustomerID()) %></td>
            <td><%= accountRepo.getBalance(c.getCustomerID()) %></td>
        <% } else { %>
            <td colspan="2">No Account Created</td>
        <% } %>
    </tr>
    <% } %>
</table>
</body>
</html>