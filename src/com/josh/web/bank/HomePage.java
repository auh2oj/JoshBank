package com.josh.web.bank;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HomePage
 */
@WebServlet("/HomePage")
public class HomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomePage() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Account account = (Account) request.getSession(false).getAttribute("account");
		PrintWriter writer = response.getWriter();
		
//		response.sendRedirect("homePage.html");
		
		response.setContentType("text/html");
		
		writer.println("<h1>Hello, " + account.getUsername() + "!</h1>");
		writer.println("Your balance is: " + account.getBalance());
		writer.println("<br/>Your session ID is: " + request.getSession(false).getId());
	}

}
