package com.me.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.dao.CartDAO;
import com.me.dao.DAO;
import com.me.dao.ProductDAO;
import com.me.dao.UserDAO;
import com.me.exception.CartException;
import com.me.exception.ProductException;
import com.me.exception.UserException;
import com.me.pojo.Cart;
import com.me.pojo.Product;
import com.me.pojo.Seller;
import com.me.pojo.User;

/**
 * Servlet implementation class AjaxDataProvider
 */
@Controller 
public class AjaxDataProvider extends DAO {

	public AjaxDataProvider() {

	}

	@Autowired
	@Qualifier("userDao")
	UserDAO userDAO;

	@Autowired
	@Qualifier("cartDao")
	CartDAO cartDAO;

	@Autowired
	@Qualifier("productDao")
	ProductDAO productDAO;

	@RequestMapping(value = "admin/sellerlist.htm", method=RequestMethod.POST)
	@ResponseBody
	public String getAttr(HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		Long sellername = Long.parseLong(request.getParameter("seller"));
		System.out.println("****RETRIEVING FROM AJAX: " + sellername);
		ArrayList<Seller> sellerList;
		Seller seller1 = new Seller();
		System.out.println();
		try{
			String sql = "from Seller where id = :someAttr";
			Query query = getSession().createQuery(sql);
			long sellername1 = sellername;
			System.out.println(String.valueOf(sellername1));
			query.setLong("someAttr", sellername1);
			System.out.println("query for selecting seller details from db ran in ajax controller!!");
			sellerList = (ArrayList<Seller>)query.list();
			for(Seller seller:sellerList){
				seller1 = seller;
			}
		}catch (HibernateException e) {
			rollback();
			System.out.println("Error retreiving seller list - AJAX CONTROLLER!!");
		}
		for(Product p : seller1.getProducts()){
			System.out.println("Products list!!");
			System.out.println(p.getName());
		}
		//String jsonInString = gson.toJson(seller1);
		String jsonInString="";
		try {
			jsonInString = mapper.writeValueAsString(seller1);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception converting object to JSON:\t"+e.getMessage());
		}
		System.out.println("JSON OUTPUT:"+jsonInString);
		System.out.println("Seller name retrieved is:\t"+seller1.getName());
		return jsonInString;
	}

	@RequestMapping(value = "admin/sellerlistapprove.htm", method=RequestMethod.POST)
	@ResponseBody
	public String getSellerApproved(HttpServletRequest request) {
		//Gson gson = new Gson();
		ObjectMapper mapper = new ObjectMapper();
		String sellername = request.getParameter("seller");
		System.out.println("*** RETRIEVING FROM AJAX: " + sellername);
		ArrayList<Seller> sellerList;
		Seller seller1 = new Seller();
		try{
			begin();
			String sql = "update Seller set status = :status where id = :id";
			Query query = getSession().createQuery(sql);
			long sellername1 = Long.parseLong(sellername);
			query.setLong("id", sellername1);
			query.setString("status", "Approved");
			query.executeUpdate();
			System.out.println("query for updating seller details in db ran in ajax controller!!");
			commit();

			String sql1 = "from Seller where id = :someAttr";
			Query query1 = getSession().createQuery(sql1);
			//long sellername1 = Long.parseLong(sellername);
			System.out.println(sellername1);
			query1.setLong("someAttr", sellername1);
			System.out.println("query for selecting seller details from db ran in ajax controller!!");
			sellerList = (ArrayList<Seller>)query1.list();
			for(Seller seller:sellerList){
				seller1 = seller;
			}
		}catch (HibernateException e) {
			rollback();
			System.out.println("Error retreiving seller list - AJAX CONTROLLER!!");
		}
		System.out.println("Seller name retrieved is:\t"+seller1.getName());
		//String jsonInString = gson.toJson(seller1);
		String jsonInString="";
		try {
			jsonInString = mapper.writeValueAsString(seller1);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception converting object to JSON:\t"+e.getMessage());
		}
		return jsonInString;
	}

	@RequestMapping(value = "seller/deleteproduct.htm", method=RequestMethod.POST)
	@ResponseBody
	public String deleteProductFromDb(HttpServletRequest request) {
		Long productid = Long.parseLong(request.getParameter("product"));
		System.out.println("**** PRODUCT-ID RETRIEVING FROM AJAX: " + productid);

		try{
			begin();
			String sql1 = "delete from category_product_table where productID =:productid";
			Query query1 = getSession().createSQLQuery(sql1);
			query1.setLong("productid", productid);
			int result = query1.executeUpdate();
			String sql = "delete Product where productID =:productid";
			Query query = getSession().createQuery(sql);
			query.setLong("productid", productid);
			int result1 = query.executeUpdate();
			System.out.println("Resultant Rows:\t"+result);
			System.out.println("Resultant Rows:\t"+result1);
			commit();
			close();
			System.out.println("query for deleting product from db ran in ajax controller!!");
		}catch (HibernateException e) {
			rollback();
			System.out.println("Error deleting products! - AJAX CONTROLLER!!");
		}
		return "success";
	}

	@RequestMapping(value = "user/addToCart.htm", method=RequestMethod.POST)
	@ResponseBody
	public String addtoCart(HttpServletRequest request) {
		System.out.println("INSIDE ADDTOCART CONTROLLER!");
		User u = (User)request.getSession().getAttribute("user");
		System.out.println(u.getFirstName());
		Long userid = u.getPersonID();
		Long pid = Long.parseLong(request.getParameter("productid"));
		System.out.println("****RETRIEVING FROM SESSION: " +userid);
		try{
			begin();
			System.out.println("inside CARTDAO - AJAXDATAPROVIDER!!!");

			Query q2 = getSession().createQuery("from Cart where productID= :pid and userID= :uid");
			q2.setLong("pid", pid);
			q2.setLong("uid", userid);
			Cart cart = (Cart) q2.uniqueResult();
			if(cart!=null){
				begin();
				int quantity = cart.getQuantity();
				quantity = quantity + 1;
				Query q3 = getSession().createQuery("update Cart set quantity=:quantity where productID= :pid and userID= :uid");
				q3.setLong("pid", pid);
				q3.setInteger("quantity", quantity);
				q3.setLong("uid", userid);
				int result = q3.executeUpdate();
				System.out.println("result of update cart:\t"+result);
				commit();
			}else{
				Query q1 = getSession().createQuery("from Product where productID= :pid");
				q1.setLong("pid", pid);		
				Product product = (Product) q1.uniqueResult();

				System.out.println("PRODUCT RETRIEVED IS:\t"+product.getName());
				System.out.println("USER RETRIEVED IS:\t"+u.getFirstName());
				Cart cart1 = new Cart();
				cart1.setUser(u);
				cart1.setProduct(product);
				cart1.setQuantity(1);
				getSession().save(cart1);
				commit();
			}
		}catch (HibernateException e) {
			rollback();
			System.out.println("Error for CART - AJAX CONTROLLER!!");
		}
		return "incart";
	}

	@RequestMapping(value = "user/updateproductlist.htm", method=RequestMethod.POST)
	@ResponseBody
	public String updateproductlist(HttpServletRequest request) {
		String text = request.getParameter("text");
		System.out.println("Searched value is:\t"+text);
		List<Product> productList = new ArrayList<Product>();
		try {
			productList = productDAO.get(text);
		} catch (ProductException e1) {
			System.out.println("+++++++"+e1.getMessage());
		}
		System.out.println("SIZE OF RETREIVED LIST:\t"+productList.size());
		try{
			request.getSession().setAttribute("productlist", productList);
		}catch (HibernateException e) {
			rollback();
			System.out.println("Error retreiving seller list - AJAX CONTROLLER!!");
		}
		System.out.println("STORED IN SESSION:\t"+productList);
		String jsonInString="abc";
		return jsonInString;
	}

	@RequestMapping(value = "user/updateproductlistfilter.htm", method=RequestMethod.POST)
	@ResponseBody
	public String updateproductlistfilter(HttpServletRequest request) {
		String text = request.getParameter("text");
		System.out.println("Searched value is:\t"+text);
		List<Product> productList = new ArrayList<Product>();
		try {
			productList = productDAO.list();
		} catch (ProductException e1) {
			System.out.println("+++++++"+e1.getMessage());
		}
		System.out.println("SIZE OF RETREIVED LIST:\t"+productList.size());
		try{
			request.getSession().setAttribute("productlist", productList);
		}catch (HibernateException e) {
			rollback();
			System.out.println("Error retreiving product list filter - AJAX CONTROLLER!!");
		}
		//System.out.println("STORED IN SESSION:\t"+productList);
		String jsonInString="abc";
		return jsonInString;
	}

	@RequestMapping(value = "user/updatequantityproducts.htm", method=RequestMethod.POST)
	@ResponseBody
	public String updatequantityproducts(HttpServletRequest request) {

		User u = (User)request.getSession().getAttribute("user");
		List<Product> productList = new ArrayList<Product>();
		List<Cart> cartList = new ArrayList<Cart>();
		try {
			try {
				cartList = cartDAO.getlist(u.getPersonID());
				productList = productDAO.list();
			} catch (CartException e) {
				System.out.println("+++++++"+e.getMessage());
			}
		} catch (ProductException e1) {
			System.out.println("+++++++"+e1.getMessage());
		}
		System.out.println("SIZE OF RETREIVED PRODUCT LIST:\t"+productList.size());
		String booleanvalue = "true";
		String pname="";
		System.out.println("..............all true!");
		for(Cart c:cartList){
			int quantity = c.getQuantity();
			System.out.println("CART QUANTITY:\t"+quantity);
			long pid = c.getProduct().getId();
			for(Product p :productList){
				if(p.getId()==pid){
					int newquantity = Integer.parseInt(p.getQuantity()) - quantity;
					System.out.println("??????????????????????????????????????"+newquantity);
					if(newquantity>0){
						productDAO.updatequantity(newquantity,p.getId());
					}
				}
			}
		}

		JSONObject json = new JSONObject();
		json.put("booleanvalue", booleanvalue);
		json.put("pname", pname);

		String jsonString = json.toString();
		return jsonString;
	}
}