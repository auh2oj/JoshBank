<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.josh.web.bank.*, java.util.Date" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String username; Account account; Double balance;%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home Page</title>
</head>
<body>

	<%
		account = (Account) request.getSession(false).getAttribute("account");
		username = account.getUsername(); 
		balance = account.getBalance();
	%>
	<h1>Hello, <%= username %>!</h1>
	
<%-- 	<%
	//show date & time
	response.setIntHeader("Refresh", 1);
	Date date = new Date();
	response.getWriter().println(date.toString());
	
	%> --%>
	
	Your balance is: <%= balance %><br>
		<%-- Your session ID is: <%= request.getSession(false).getID() %> --%>
	Account created on: <%= account.getDateCreated() %>

	<br/><br/>
	<a href="deposit.html">Make a deposit</a><br/>
	<a href="withdraw.html">Make a withdrawal</a><br/>
	<a href="/JoshBank/Logout">Logout</a><br/>
	
	<br/><br/>
	<a href="Delete">Delete account</a>
		

</body>
</html>