<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bank Application - Login</title>
</head>
<body>
    <h2>Welcome to MyBank</h2>
    <% if (request.getParameter("error") != null) { %>
        <p style="color:red;"><%= request.getParameter("error") %></p>
    <% } %>
    <form action="LoginServlet" method="post">
        <label for="role">Login as:</label>
        <select name="role" id="role">
            <option value="admin">Admin</option>
            <option value="customer">Customer</option>
        </select><br><br>
        
        <div id="adminFields">
            Username: <input type="text" name="adminUsername"><br>
            Password: <input type="password" name="adminPassword"><br>
        </div>
        
        <div id="customerFields" style="display:none;">
            Email: <input type="email" name="email"><br>
            Password: <input type="password" name="password"><br>
        </div>
        
        <input type="submit" value="Login">
    </form>
    
    <script>
        document.getElementById("role").addEventListener("change", function() {
            var role = this.value;
            document.getElementById("adminFields").style.display = role === "admin" ? "block" : "none";
            document.getElementById("customerFields").style.display = role === "customer" ? "block" : "none";
        });
    </script>
</body>
</html>