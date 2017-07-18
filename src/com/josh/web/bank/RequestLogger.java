package com.josh.web.bank;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class RequestLogger
 */
@WebFilter("/*")
public class RequestLogger implements Filter {
	
	private ServletContext c;

    /**
     * Default constructor. 
     */
    public RequestLogger() {
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
		//Log request parameters
		Enumeration<String> params = ((HttpServletRequest) request).getParameterNames();
		while (params.hasMoreElements()) {
			String param = params.nextElement();
			String value = request.getParameter(param);
			c.log(request.getRemoteAddr()+"::RequestParameter::{Name="+param+"::Value="+value+"}");
		}
		
		//Log request attribute names
		Enumeration<String> atts = ((HttpServletRequest) request).getAttributeNames();
		while (atts.hasMoreElements()) {
			String att = atts.nextElement();
			String value = request.getAttribute(att).getClass().toString();
			c.log(request.getRemoteAddr()+"::RequestAttribute::{Name="+att+"::Class="+value+"}");
		}
		
		//TODO: Log cookies (for later)

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.c = fConfig.getServletContext();
		c.log("RequestLogger initialized.");
	}

}
