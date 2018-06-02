package com.me.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestScope;
import org.springframework.web.context.request.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import com.me.dao.SellerDAO;
import com.me.exception.SellerException;
import com.me.pojo.Product;
import com.me.pojo.Seller;
import com.me.validator.SellerValidator;
import com.me.dao.CategoryDAO;

@Controller
@RequestMapping("/seller/*")
public class SellerLinkController {

	@Autowired
	@Qualifier("sellerDao")
	SellerDAO sellerDao;

	@Autowired
	@Qualifier("categoryDao")
	CategoryDAO categoryDAO;
	
	@Autowired
	@Qualifier("sellerValidator")
	SellerValidator sellervalidator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(sellervalidator);
	}
	
	@RequestMapping(value = "/seller/sellerregister.htm", method = RequestMethod.GET)
	protected ModelAndView RegisterPageTransferSeller() throws Exception {
		System.out.println("Register clicked!");
		return new ModelAndView("seller-register","seller",new Seller());
	}
	
	@RequestMapping(value = "/seller/registerseller.htm", method = RequestMethod.POST)
	protected ModelAndView registerNewSeller(HttpServletRequest request,  @ModelAttribute("seller") Seller seller, BindingResult result) throws Exception {
		System.out.println("Inside register controller for Seller!!!");
		sellervalidator.validate(seller, result);

		if (result.hasErrors()) {
			return new ModelAndView("seller-register", "seller", seller);
		}

		System.out.println("registerNewSeller");
		String receiverEmail = seller.getEmailAddress();
		Seller s = null;
		try {
			s = sellerDao.register(seller);
			request.getSession().setAttribute("seller", s);
			Email email = new SimpleEmail();
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("chintankoticha@gmail.com", "dtvc4_{koticha"));
			email.setHostName("smtp.gmail.com");//if a server is capable of sending email.
			email.setSSL(true);//setSSLOnConnect(true);
			email.setFrom("chintankoticha@gmail.com");
			email.setSubject("SUCCESSFUL SIGN UP!!!");
			email.setMsg("This is system generated mail, do not reply to this email.\n\n"+"\nYOUR USERNAME IS:\t"+seller.getUsername()+"\n"
					+ "KINDLY LOGIN TO THE WEBSITE ONCE YOU APPROVED BY THE ADMIN!!");
			email.addTo(receiverEmail);
			email.setTLS(true);//startTLS.enable.true
			email.send();
		} catch (SellerException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		} catch (Exception e) {
			System.out.println("Exception e:" + e.getMessage());
		} 
		System.out.println("EMAIL SENT TO NEW SELLER!!");
		return new ModelAndView("seller-register", "message", "Successfully registered, just wait for our team to approve you before can login!!");
	}
	
	@RequestMapping(value = "/seller/sellerlogin.htm", method = RequestMethod.POST)
	protected ModelAndView loginSeller(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		HttpSession session = (HttpSession) request.getSession();

		try {

			System.out.println("Inside LoginSeller Link Controller!!");
			Seller u = sellerDao.get(request.getParameter("sellerusername"), request.getParameter("sellerpassword"));

			if(u == null){
				System.out.println("UserName/Password does not exist");
				session.setAttribute("errorMessage", "UserName/Password does not exist");
				return new ModelAndView ("error");
			}
			
			if(u.getStatus().equalsIgnoreCase("Pending")){
				return new ModelAndView("login", "messagelogin", "PLEASE WAIT FOR OUR TEAM TO APPROVE YOU BEFORE YOU CAN LOGIN!!");
			}else{
				session.setAttribute("seller", u);
				request.getSession().setAttribute("role", "seller");
				SessionScope ss = new SessionScope();
				RequestScope rs = new RequestScope();
				ss.remove("messagelogin");
				rs.remove("messagelogin");
				mv.addObject("seller",u);
				System.out.println("Adding to hidden field:\t"+u.getId());
				request.getSession().setAttribute("hiddensellerid", u.getId());
				mv.setViewName("seller-home");
				return mv;
			}
		} catch (SellerException e) {
			System.out.println("Exception: " + e.getMessage());
			session.setAttribute("errorMessage", "error while login");
			return new ModelAndView ("error");
		}
	}	
}