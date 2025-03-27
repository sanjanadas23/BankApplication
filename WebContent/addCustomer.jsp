<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%
    String adminSession = (String) session.getAttribute("admin");
    if (adminSession == null) {
        response.sendRedirect("index.jsp?error=Unauthorized access.");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Customer</title>
</head>
<body>
    <h2>Add New Customer</h2>
    <form action="AdminServlet" method="post">
    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" required>
    
    <label for="lastName">Last Name:</label>
    <input type="text" id="lastName" name="lastName" required>
    
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>
    
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required>
    
    <input type="hidden" name="action" value="addCustomer">
    <button type="submit">Register Customer</button>
</form>
</body>
</html>