package com.josh.web.bank;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
    		SessionFactory factory;
    		//TODO: Include EntityManager
//    		EntityManager em;
//    		
//    		EntityManagerFactory emf = Persistence.createEntityManagerFactory("BankManager");
//    		em = emf.createEntityManager();
    		
    		try {
    			factory = new Configuration().configure().buildSessionFactory();
    		} catch (Throwable e) {
    			System.err.println("Failed to create Session Factory." + e);
    			throw new ExceptionInInitializerError(e);
    		}
    	
        BankManager manager = new BankManager(factory);
    		// TODO: Later on, once MYSQL is incorporated:
        // Set DB connection as context attribute
    		// Load account data from DB and make accounts from them and put them into the BankManager 
    		sce.getServletContext().setAttribute("manager", manager);
    }
	
}
