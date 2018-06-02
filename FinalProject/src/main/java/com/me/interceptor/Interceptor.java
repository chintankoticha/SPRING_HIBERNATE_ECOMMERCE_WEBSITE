package com.me.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Interceptor extends HandlerInterceptorAdapter{

	String errorPage;

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("----------------------");

		if(request.getParameter("seller")!=null){
			if(request.getParameter("seller").equals("Login As a seller")){
				System.out.println("------>SETTING ROLE AS SELLER!!");
				request.getSession().setAttribute("role", "seller");
			}
		}
		
		if(request.getParameter("user")!=null){
			if(request.getParameter("user").equals("Login as a customer")){
				System.out.println("------>SETTING ROLE AS USER!!");
				request.getSession().setAttribute("role", "user");
			}
		}
		
		HttpSession session = (HttpSession) request.getSession();
		System.out.println("--"+session.getAttribute("role"));
		if(session.getAttribute("role") == null){
			if((request.getRequestURI().contains("seller/"))||
					(request.getRequestURI().contains("user/")))
			{
				System.out.println("in interceptor 1");
				System.out.println("1 -false");
				response.sendRedirect("http://localhost:8080/controller/home.htm");
				return false;
			}
			System.out.println("in interceptor 2");
			return true;
		}

		if(session.getAttribute("role") != null){
			System.out.println("in interceptor 3");
			if((request.getRequestURI().contains("admin") && session.getAttribute("role").equals("admin")) ||
					(request.getRequestURI().contains("seller") && session.getAttribute("role").equals("seller"))||
					(request.getRequestURI().contains("user") && session.getAttribute("role").equals("user")))
			{
				System.out.println("in interceptor 4");
				return true;
			}
			else if((!request.getRequestURI().contains("admin")) &&
					(!request.getRequestURI().contains("seller"))&&
					(!request.getRequestURI().contains("user")))
			{
				System.out.println("in interceptor 5");
				return true;
			}
			else if(request.getRequestURI().contains("seller/")||request.getRequestURI().contains("user")){
				return true;
			}
		}

		System.out.println("NOT VALID!!");
		response.sendRedirect("http://localhost:8080/controller/home.htm");
		System.out.println("1 -false");
		return false;
	}
}