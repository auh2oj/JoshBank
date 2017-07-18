<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.josh.web.bank.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String username; Account account; Double balance;%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home Page</title>
</head>
<body>

	<%
		account = (Account) request.getSession().getAttribute("account");
		username = account.getUsername(); 
		balance = account.getBalance(); %>
		<h1>Hello, <%= username %>!</h1>
		Your balance is: <%= balance %>
		<%-- Your session ID is: <%= request.getSession(false).getID() %> --%>

	<br/><br/>
	<a href="deposit.html">Make a deposit</a><br/>
	<a href="withdraw.html">Make a withdrawal</a><br/>
	<a href="/JoshBank/Logout">Logout</a><br/>
	
	<br/><br/>
	<a href="Delete">Delete account</a>
		

</body>
</html>