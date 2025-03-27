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
<title>Edit Profile</title>
</head>
<body>
    <h2>Edit Profile</h2>
    <% if (request.getParameter("msg") != null) { %>
        <p style="color:green;"><%= request.getParameter("msg") %></p>
    <% } %>
    <% if (request.getParameter("error") != null) { %>
        <p style="color:red;"><%= request.getParameter("error") %></p>
    <% } %>
    <form action="CustomerServlet" method="post">
        First Name: <input type="text" name="firstName" value="<%= customer.getFirstName() %>"><br>
        Last Name: <input type="text" name="lastName" value="<%= customer.getLastName() %>"><br>
        Password: <input type="password" name="password" required><br>
        <input type="submit" value="Update Profile">
    </form>
</body>
</body>
</html>