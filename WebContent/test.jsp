<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.josh.web.bank.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JoshBank</title>
</head>
<body>

<h1>Welcome to JoshBank!</h1>

<%

System.out.println("In the jsp");
BankManager manager = (BankManager) getServletContext().getAttribute("manager");
if (manager == null) {
		manager = new BankManager();
		// TODO: Later on, once MYSQL is incorporated:
		// Load account data from DB and make accounts from them and put them into the BankManager 
		getServletContext().setAttribute("manager", manager);
}

%>

<form action="Login" method="post">

Username: <input type="text" name="username" /><br/>
Password: <input type="password" name="password" /><br/>

<input type="submit" value="Login" />

</form>

<a href="createAccount.html">Create New Account</a>

</body>
</html>