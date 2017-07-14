package com.josh.web.bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BankManager
 */
@WebServlet("/BankManager")
public class BankManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, Account> accounts;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BankManager() {
        super();
        accounts = new HashMap<String, Account>();
		//<for test purposes>
		accounts.put("josh,josh", new Account("josh", "josh"));
		//</for test purposes>
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("username");
		String pswd = request.getParameter("password");
		PrintWriter writer = response.getWriter();
		RequestDispatcher view;
		
		if (!authenticate(uname, pswd)) {
			response.setContentType("text/html");
			writer.println("Invalid username or password.\n\n");
			view = request.getRequestDispatcher("/index.html");
			view.include(request, response);
		} else {
			Account account = getAccount(uname, pswd);
			request.setAttribute("account", account);
			view = request.getRequestDispatcher("/HomePage");
			view.forward(request, response);
		}
	}
	
	private boolean authenticate(String username, String password) {
		return accounts.containsKey(username+","+password);
	}

	private Account getAccount(String username, String password) {
		return accounts.get(username+","+password);
	}
}
