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
 * Servlet implementation class Deposit
 */
@WebServlet("/Deposit")
public class Deposit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deposit() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		HttpSession session = request.getSession(false);
		
		if (session == null) {
			writer.println("You must login first.");
//			request.getRequestDispatcher("index.html").include(request, response);
			response.sendRedirect("index.html");
		} else {
			synchronized(session) {
				Account account = (Account) session.getAttribute("account");
				Double depositAmount = Double.parseDouble(request.getParameter("deposit"));
				account.deposit(depositAmount);
				
				System.out.println("Deposit recorded. Deposit amount: " + depositAmount);
				response.sendRedirect("account/home.jsp");
			}
		}
		
	}

}
