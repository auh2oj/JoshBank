package com.josh.web.bank;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateAccount
 */
@WebServlet("/CreateAccount")
public class CreateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccount() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirm = request.getParameter("confirmpassword");
		
		RequestDispatcher view;
		BankManager manager = (BankManager) getServletContext().getAttribute("manager");
				
		if (!password.equals(confirm)) { // if passwords don't match
			writer.println("Passwords do not match.</br></br>");
			view = request.getRequestDispatcher("/createAccount.html");
			view.include(request, response);
		} else if (manager.accountExists(username)) { // if account with username already exists in DB
			writer.println("An account with that username already exists. Please use a different username.");
			view = request.getRequestDispatcher("/createAccount.html");
			view.include(request, response);
		} else {
			writer.println("Username: "+username);
			writer.print("Password: "+password);			
		}
	}

}
