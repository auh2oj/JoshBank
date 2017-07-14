package com.josh.web.bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BankManager
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BankManager manager;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("username");
		String pswd = request.getParameter("password");
		PrintWriter writer = response.getWriter();
		RequestDispatcher view;
		
        manager = (BankManager) getServletContext().getAttribute("manager");
        if (manager == null) {
        		manager = new BankManager();
        		getServletContext().setAttribute("manager", manager);
        }
		
		if (!manager.authenticate(uname, pswd)) {
			response.setContentType("text/html");
			writer.println("Invalid username or password.\n\n");
			view = request.getRequestDispatcher("/index.html");
			view.include(request, response);
		} else {
			Account account = manager.getAccount(uname, pswd);
			request.setAttribute("account", account);
			view = request.getRequestDispatcher("/HomePage");
			view.forward(request, response);
		}
	}
}
