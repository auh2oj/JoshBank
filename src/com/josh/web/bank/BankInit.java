package com.josh.web.bank;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class BankInit
 *
 */
@WebListener
public class BankInit implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public BankInit() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
        BankManager manager = new BankManager();
    		// TODO: Later on, once MYSQL is incorporated:
    		// Load account data from DB and make accounts from them and put them into the BankManager 
    		sce.getServletContext().setAttribute("manager", manager);
    }
	
}
