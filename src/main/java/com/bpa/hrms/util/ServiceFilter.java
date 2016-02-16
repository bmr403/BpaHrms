package com.bpa.hrms.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Component
public class ServiceFilter implements Filter {
	
	//FilterConfig filterConfig;
	
	
    
	// Over-riding the doFilter method to process our logic for Session Filter. 
//	 Over-riding the doFilter method to process our logic for Session Filter. 
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain)
            throws IOException, ServletException {
        
        // Get an HttpServletRequest and Response.
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        
        // Set up the context relative request path.
        String contextRelativeUri = httpRequest.getRequestURI();
        contextRelativeUri = contextRelativeUri.replaceFirst(httpRequest.getContextPath(), "");
       // String userTokenValue = servletRequest.getParameter("userToken");
        
        
        boolean isContextUriMatches = true;
        if(contextRelativeUri.equalsIgnoreCase("/timesheet/exportTimeSheetTOExcel.action") ){
        	isContextUriMatches = false;
        }
        
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        
		if (isContextUriMatches) {
			System.out.println("Authorization denied");
			httpResponse.setStatus(403);
			return;
		} 
        // Continuing with the doFilter Method.
		//filterChain.doFilter(new AtocXssFilter((HttpServletRequest) servletRequest), servletResponse);
		filterChain.doFilter(servletRequest, servletResponse);
       
        
    }
	
	public void init(FilterConfig filterConfig) throws ServletException {
    	//this.filterConfig = filterConfig;
    }
    
    public void destroy() {
    }
}