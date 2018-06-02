package com.me.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.me.dao.CartDAO;
import com.me.dao.ProductDAO;
import com.me.dao.UserDAO;
import com.me.pojo.Cart;
import com.me.pojo.Order;
import com.me.pojo.Product;
import com.me.pojo.User;
import com.me.validator.UserValidator;

@Controller
public class UserController {

	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	@Autowired
	@Qualifier("cartDao")
	CartDAO cartDao;

	@Autowired
	@Qualifier("productDao")
	ProductDAO productDao;

	@Autowired
	@Qualifier("userValidator")
	UserValidator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value = "/user/buypage.htm", method = RequestMethod.GET)
	protected ModelAndView BuyProducts(HttpServletRequest request) throws Exception {
		System.out.println("BUYPAGE clicked!");
		User user = (User)request.getSession().getAttribute("user");
		@SuppressWarnings("unchecked")
		List<Cart> cartlist = (List<Cart>) request.getSession().getAttribute("cartlist");
		int max = cartDao.getmax();	
		System.out.println("MAX ORDERNUMBER:\t"+max);
		if(max==0){
			max = 1;
		}else{
			max = max+1;
		}
		List<Order> orderlist = new ArrayList<Order>();
		for(Cart c:cartlist){
			Order od = new Order();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			System.out.println(dateFormat.format(date));
			od.setCreationDate(date);
			od.setProduct(c.getProduct());
			int quantity = cartDao.get(c.getProduct(),user.getPersonID());
			od.setQuantity(quantity);
			od.setUser(user);
			od.setId(max);
			cartDao.createOrder(od);
			
			//Storing to show in pdf
			orderlist.add(od);
			cartDao.deleteindb(c.getProduct().getId(), user.getPersonID());
		}
		request.getSession().setAttribute("currentorderlist", orderlist);
		//System.out.println("NO OF PRODUCTS TO SHOW FOR USER:\t"+productlist.size());
		return new ModelAndView("buysuccess");
	}

	@RequestMapping(value="/user/order.htm", method = RequestMethod.GET)
	public ModelAndView pdfview(HttpServletRequest request) throws Exception
	{
		System.out.println("In Order pdf method");

		View view = new MyView();
		System.out.println("In Order pdf method");
	
		@SuppressWarnings("unchecked")
		List<Order> orderlist = (List<Order>)request.getSession().getAttribute("currentorderlist");
		System.out.println("In Order pdf method");
		System.out.println("orderId"+orderlist.size());
		return new ModelAndView(view,"orderlist",orderlist);
	}
	
	@RequestMapping(value="/user/receipt.htm", method = RequestMethod.GET)
	public ModelAndView pdfviewOrderHistoryPage(HttpServletRequest request) throws Exception
	{
		System.out.println("In Order pdf pdfviewOrderHistoryPage method");

		View view = new MyViewOrderHistory();
		System.out.println("In Order pdf method");
		int orderidpdf = Integer.parseInt(request.getParameter("id"));
		@SuppressWarnings("unchecked")
		List<Order> orderlist = cartDao.getOrderList(orderidpdf);
		System.out.println("In Order pdf method");
		System.out.println("orderId"+orderlist.size());
		return new ModelAndView(view,"orderlist",orderlist);
	}
}