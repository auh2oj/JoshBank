package com.josh.web.bank;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Withdraw
 */
@WebServlet("/Withdraw")
public class Withdraw extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Withdraw() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession(false);
		
		if (session == null) {
			writer.println("You must login first.");
			System.out.println("User not logged in.");
			request.getRequestDispatcher("index.html").include(request, response);
//			response.sendRedirect("index.html");
		} else {
			synchronized(session) {
				BankManager manager = (BankManager) session.getServletContext().getAttribute("manager");
				
				Account account = (Account) session.getAttribute("account");
				Integer accountID = account.getId();
				double balance = account.getBalance();
				Double withdrawAmount = Double.parseDouble(request.getParameter("withdraw"));
				
				// Account must have a balance of at least $20 to make a withdrawal
				if (balance < 20) {
					writer.println("Insufficient funds. You must have at least $20 to make a withdrawal.");
					System.out.println("Insufficient funds. Withdrawal unsuccessful.");
					request.getRequestDispatcher("account/home.jsp").include(request, response);
				} else if (balance < withdrawAmount) { // withdrawal amount must be less than what's already present in account
					writer.println("Insufficient funds. You do not have enough money in your account to withdraw that amount of money.");
					System.out.println("Insufficient funds. Withdrawal unsuccessful.");
					request.getRequestDispatcher("account/home.jsp").include(request, response);
				} else {
					account = manager.withdraw(accountID, withdrawAmount);
					request.getSession(false).setAttribute("account", account);
					
					System.out.println("Witdrawal recorded. Withdraw amount: " + withdrawAmount);
					response.sendRedirect("account/home.jsp");
				}
			}
		}
		
	}

}
