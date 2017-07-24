package com.josh.web.bank;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter("/*")
public class AuthenticationFilter implements Filter {

	private ServletContext c;
	
    /**
     * Default constructor. 
     */
    public AuthenticationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		res.setContentType("text/html");
		String uri = req.getRequestURI();
		c.log("Requested resource::"+uri);
		
		HttpSession session = req.getSession(false);
		
		if (session == null && uri.contains("account")) {
			c.log("Unauthorized resource request::"+uri+"::Invalid session.");
			res.getWriter().println("You do not have access to this resource. Please "
					+ "<a href='/JoshBank/index.html'>login</a> first.");
//			RequestDispatcher view = req.getRequestDispatcher("/index.html");
//			view.include(req, res);
//			res.sendRedirect("/JoshBank/index.html");
		} else {
			c.log("Access OK");
			if (session != null) {
				c.log("Current session::"+session.getId());
			}
		// pass the request along the filter chain
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.c = fConfig.getServletContext();
		c.log("Authenticator initialized.");
	}

}
