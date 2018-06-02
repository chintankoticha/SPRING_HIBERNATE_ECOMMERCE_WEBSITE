package com.me.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.me.exception.UserException;
import com.me.pojo.Cart;
import com.me.pojo.Order;
import com.me.pojo.Product;
import com.me.pojo.User;
import com.me.validator.UserValidator;
import com.me.dao.CartDAO;
import com.me.dao.ProductDAO;
import com.me.dao.UserDAO;


@Controller
public class LinkController {

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

	@RequestMapping(value = "/home.htm", method = RequestMethod.GET)
	protected ModelAndView backtohome(HttpServletRequest request) throws Exception {
		System.out.println("homepage clicked!");
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
	public ModelAndView redirectLogout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		System.out.println("-----Session INVALIDATED!!-----");
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/userlogin.htm", method = RequestMethod.GET)
	protected ModelAndView LoginPageTranfer(HttpServletRequest request) throws Exception {
		System.out.println("Login/SignUp clicked!");
		List<Product> productlist = productDao.list();
		request.getSession().setAttribute("productlist", productlist);
		System.out.println("NO OF PRODUCTS TO SHOW FOR USER:\t"+productlist.size());
		request.getSession().setAttribute("role", "user");
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/user/userregister.htm", method = RequestMethod.GET)
	protected ModelAndView RegisterPageTransferUser() throws Exception {
		System.out.println("Register clicked!");
		return new ModelAndView("register","user",new User());
	}

	@RequestMapping(value = "/user/cart.htm", method = RequestMethod.GET)
	protected ModelAndView CartPageTransfer(HttpServletRequest request) throws Exception {
		System.out.println("Cart/Buy clicked!");
		User user = (User)request.getSession().getAttribute("user");
		List<Cart> cartlist = cartDao.list(user.getPersonID());
		request.getSession().setAttribute("cartlist", cartlist);
		double totalprice=0;
		for(Cart cart:cartlist){
			int quantity = cart.getQuantity();
			double price = Double.parseDouble(cart.getProduct().getPrice());
			double temp = quantity*price;
			totalprice = totalprice + temp;
		}
		request.getSession().setAttribute("totalprice", totalprice);
		return new ModelAndView("cart");
	}

	@RequestMapping(value = "/user/orderhistory.htm", method = RequestMethod.GET)
	protected ModelAndView OrderHistoryPage(HttpServletRequest request) throws Exception {
		System.out.println("OrderHistory clicked!");
		User user = (User)request.getSession().getAttribute("user");
		Map<Integer,List<Order>> ordermap = cartDao.listordermap(user.getPersonID());
		request.getSession().setAttribute("ordermap", ordermap);
		return new ModelAndView("orderhistory");
	}	

	@RequestMapping(value = "user/updatecart.htm", method = RequestMethod.POST)
	@ResponseBody
	protected String UpdateCartDb(HttpServletRequest request) throws Exception {
		System.out.println("Update in Cart clicked!!!");
		User user = (User)request.getSession().getAttribute("user");
		Long pid = Long.parseLong(request.getParameter("pid"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		cartDao.updatedb(pid, user.getPersonID(), quantity);

		List<Cart> cartlist = cartDao.list(user.getPersonID());
		request.getSession().setAttribute("cartlist", cartlist);
		double totalprice=0;
		for(Cart cart:cartlist){
			int quantity1 = cart.getQuantity();
			double price = Double.parseDouble(cart.getProduct().getPrice());
			double temp = quantity1*price;
			totalprice = totalprice + temp;
		}
		request.getSession().setAttribute("totalprice", totalprice);
		return String.valueOf(totalprice);
	}

	@RequestMapping(value = "user/deletecart.htm", method = RequestMethod.POST)
	@ResponseBody
	protected String DeleteCartValueDb(HttpServletRequest request) throws Exception {
		System.out.println("Delete in Cart clicked!!!");
		User user = (User)request.getSession().getAttribute("user");
		Long pid = Long.parseLong(request.getParameter("pid"));
		cartDao.deleteindb(pid, user.getPersonID());

		List<Cart> cartlist = cartDao.list(user.getPersonID());
		request.getSession().setAttribute("cartlist", cartlist);
		double totalprice=0;
		for(Cart cart:cartlist){
			int quantity1 = cart.getQuantity();
			double price = Double.parseDouble(cart.getProduct().getPrice());
			double temp = quantity1*price;
			totalprice = totalprice + temp;
		}
		request.getSession().setAttribute("totalprice", totalprice);
		return String.valueOf(totalprice);
	}

	@RequestMapping(value = "/user/userlogin.htm", method = RequestMethod.GET)
	protected ModelAndView loginUserGet(HttpServletRequest request) throws Exception {
		return loginUser(request);
	}


	@RequestMapping(value = "/user/userlogin.htm", method = RequestMethod.POST)
	protected ModelAndView loginUser(HttpServletRequest request) throws Exception {

		HttpSession session = (HttpSession) request.getSession();
		try {
			User u = null;
			User u1 = null;
			System.out.println("loginUser -POST METHOD OF LOGIN!!");
			if(request.getSession().getAttribute("user")==null){
				u1 = userDao.get(request.getParameter("username"), request.getParameter("password"));

				if(u1 == null){
					System.out.println("UserName/Password does not exist");
					session.setAttribute("errorMessage", "UserName/Password does not exist");
					return new ModelAndView ("error");
				}
			}
			if(request.getSession().getAttribute("user")!=null){
				u = (User)request.getSession().getAttribute("user");
			}else if(u1!=null){
				u = u1;
			}
			
			System.out.println("USER ---------------------------\t"+u.getFirstName());
			session.setAttribute("user", u);

			//Create userhome.jsp in views
			request.getSession().setAttribute("role", "user");
			System.out.println("USER ROLE SET TO USER!!");
			return new ModelAndView("user-home");

		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			session.setAttribute("errorMessage", "error while login");
			return new ModelAndView ("error");
		}
	}	

	@RequestMapping(value = "/user/register.htm", method = RequestMethod.POST)
	protected ModelAndView registerNewUser(HttpServletRequest request,  @ModelAttribute("user") User user, BindingResult result) throws Exception {
		System.out.println("Inside register controller!!!");
		validator.validate(user, result);

		System.out.println("Checking for user existance");
		boolean flag = userDao.checkIfExists(user);
		System.out.println("Checked for user existance!!");
		if(!flag){
			System.out.println("Same user found!!");
			return new ModelAndView("register", "user", user);
		}
		if (result.hasErrors()) {
			return new ModelAndView("register", "user", user);
		}

		System.out.print("registerNewUser");
		String receiverEmail = user.getEmail().toString();
		User u = null;
		try {
			u = userDao.register(user);
			request.getSession().setAttribute("user", u);
			Email email = new SimpleEmail();
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("chintankoticha@gmail.com", "dtvc4_{koticha"));
			email.setHostName("smtp.gmail.com");//if a server is capable of sending email.
			email.setSSL(true);//setSSLOnConnect(true);
			email.setFrom("chintankoticha@gmail.com");
			email.setSubject("SUCCESSFUL SIGN UP!!!");
			email.setMsg("This is system generated mail, do not reply to this email.\n\n"+"\nYOUR USERNAME IS:\t"+user.getUsername()+"\nKINDLY LOGIN TO THE WEBSITE!!");
			email.addTo(receiverEmail);
			email.setTLS(true);//startTLS.enable.true
			email.send();
		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		} catch (Exception e) {
			System.out.println("Exception e:" + e.getMessage());
		} 
		System.out.println("EMAIL SENT TO NEW USER!!");
		return new ModelAndView("user-home", "user", u);
	}
}
