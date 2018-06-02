package com.me.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.me.dao.AdminDAO;
import com.me.dao.SellerDAO;
import com.me.pojo.Seller;
import com.me.validator.AdminValidator;
import com.me.validator.SellerValidator;

@Controller
@RequestMapping("/admin/*")
public class AdminController {
	
	@Autowired
	@Qualifier("adminDao")
	AdminDAO adminDao;
	
	@Autowired
	@Qualifier("adminValidator")
	AdminValidator adminvalidator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(adminvalidator);
	}
	
	@RequestMapping(value = "/admin/validatesellers.htm", method = RequestMethod.GET)
	protected ModelAndView ValidateSellers() throws Exception {
		System.out.println("Validate Sellers by Admin clicked!");
		ArrayList<Seller> sellerList = adminDao.getNewSellerList();
		return new ModelAndView("validate-sellers","sellerlist",sellerList);
	}
	
	@RequestMapping(value = "/admin/admin-home.htm", method = RequestMethod.GET)
	protected ModelAndView AdminHomePage(HttpServletRequest request) throws Exception {
		request.getSession().setAttribute("role", "admin");
		return new ModelAndView("admin-home");
	}	
}