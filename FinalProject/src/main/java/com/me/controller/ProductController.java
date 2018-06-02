package com.me.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.me.dao.CategoryDAO;
import com.me.dao.ProductDAO;
import com.me.dao.SellerDAO;
import com.me.exception.CategoryException;
import com.me.exception.ProductException;
import com.me.exception.SellerException;
import com.me.pojo.Product;
import com.me.pojo.Seller;
import com.me.validator.ProductValidator;
import com.me.pojo.Category;

@Controller
public class ProductController {

	@Autowired
	@Qualifier("categoryDao")
	CategoryDAO categoryDAO;

	@Autowired
	@Qualifier("productDao")
	ProductDAO productDao;

	@Autowired
	@Qualifier("sellerDao")
	SellerDAO sellerDao;

	@Autowired
	@Qualifier("productValidator")
	ProductValidator productvalidator;

	@RequestMapping(value = "/seller/AddProducts.htm", method = RequestMethod.GET)
	public String addProducts(@ModelAttribute Seller seller, Model model,HttpServletRequest request) throws Exception {
		String hiddensellerid = request.getSession().getAttribute("hiddensellerid").toString();
		System.out.println("Hidden seller ID:\t"+hiddensellerid);
		seller = sellerDao.get(Integer.parseInt(hiddensellerid));
		request.getSession().setAttribute("seller", seller);
		request.getSession().setAttribute("categories", categoryDAO.list());
		request.getSession().setAttribute("postedby",seller.getName());
		return productCreate(seller,model,true);
	}
	
	@RequestMapping(value = "/seller/UpdateProducts.htm", method = RequestMethod.GET)
	public ModelAndView updateProducts(@ModelAttribute Product product,Model model,HttpServletRequest request) throws Exception {
		String hiddensellerid = request.getSession().getAttribute("hiddensellerid").toString();
		System.out.println("Hidden seller ID:\t"+hiddensellerid);
		List<Product> products = productDao.list(Long.parseLong(hiddensellerid));
		request.getSession().setAttribute("products", products);
		request.getSession().setAttribute("noofproducts", products.size());
		return new ModelAndView("UpdateProducts","products",products);
	}

	@RequestMapping(value = "/seller/viewandupdateproduct.htm", method = RequestMethod.GET)
	public ModelAndView viewandupdateproducts(@ModelAttribute Product product,Model model,HttpServletRequest request) throws Exception {
		String productid = request.getParameter("id");
		System.out.println("Product ID for that row:\t"+productid);
		//List<Product> products = productDao.list(Long.parseLong(hiddensellerid));
		Product p = productDao.get(Long.parseLong(productid));
		return new ModelAndView("viewandupdateproduct","product",p);
	}
	
	@RequestMapping(value = "/seller/viewandupdateproduct.htm", method = RequestMethod.POST)
	public ModelAndView viewandupdateproducts1(@ModelAttribute Product product,Model model,HttpServletRequest request) throws Exception {
		String productid = request.getParameter("id");
		System.out.println("Product ID for the update function:\t"+productid);
		//List<Product> products = productDao.list(Long.parseLong(hiddensellerid));
		//Product p = productDao.get(Long.parseLong(productid));
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String price = request.getParameter("price");
		String quantity = request.getParameter("quantity");
		
		//File Operations
		CommonsMultipartFile photoInMemory = product.getPhoto();
		String fileName = photoInMemory.getOriginalFilename();
		System.out.println(fileName);
		
		File localFile = new File("C:\\SpringProjects\\FinalProject\\src\\main\\webapp\\user\\resources\\images\\", fileName);
		photoInMemory.transferTo(localFile);
		System.out.println("File is stored at" + localFile.getPath());
		product.setFilename(fileName);
		product.setName(name);
		product.setDescription(description);
		product.setPrice(price);
		product.setQuantity(quantity);
		product.setId(Long.parseLong(productid));
		
		product = productDao.update(product);
		System.out.println("Updated product!!");
		request.getSession().setAttribute("productupdate", "PRODUCT DETAILS SUCCESSFULLY UPDATED!!");
		return new ModelAndView("viewandupdateproduct","product",product);
	}
	
	private String productCreate(Seller seller,Model model,boolean init)
	{
		System.out.println("productCreate method--1");
		if(init)
		{
			AutoPopulatingList<Product> add= new AutoPopulatingList<Product>(Product.class);
			add.add(new Product());
			System.out.println("productCreate method--2");
			seller.setProducts(add);
		}
		System.out.println("productCreate method--3");
		model.addAttribute("type","create");
		return "/products-add-form";
	}

	@RequestMapping(value = "/seller/AddProducts.htm", method = RequestMethod.POST)
	protected ModelAndView registerNewUser(HttpServletRequest request,@ModelAttribute Seller seller, BindingResult bindingResult, Model model) {
		System.out.println("Product Add Post method--1");
		try{
			System.out.println("Product Add Post method--3");
			
			String hiddensellerid = request.getSession().getAttribute("hiddensellerid").toString();
			System.out.println("NUMBER OF PRODUCTS:\t"+seller.getProducts().size());
			Seller s = sellerDao.get(Integer.parseInt(hiddensellerid));
			System.out.println("******Seller for which products are added:\t"+s.getId());
			String[] categories = request.getParameterValues("categories");
			for(String s1:categories){
				System.out.println(s1);
			}
			for(Product p:seller.getProducts()){
				p.setSeller(s);
				//System.out.println(p.getQuantity());
				System.out.println("Inside every products!!");
				String string = p.getDescription();
				//System.out.println(string);
				String str2 = p.getName();
				String str3 = p.getPrice();
				String str4 = p.getQuantity();
				CommonsMultipartFile str5 = p.getPhoto();
				System.out.println("PHOTO KA PATH:\t"+str5); // filename
				List<String> list = Arrays.asList(string.split(","));
				List<String> list1 = Arrays.asList(str2.split(","));
				List<String> list2 = Arrays.asList(str3.split(","));
				List<String> list3 = Arrays.asList(str4.split(","));
				//List<String> list4 = Arrays.asList(str5.split(","));
				for(int i=0;i<list.size();i++){
					Product p1 = new Product();
					p1.setName(list1.get(i));
					p1.setPrice(list2.get(i));
					p1.setQuantity(list3.get(i));
					p1.setDescription(list.get(i));
					
					
					//FILE OPERATIONS FOR IMAGE UPLOAD ON SERVER
					
					p1.setSeller(s);
					p1 = productDao.create(p1);
					
					for(int i1=0;i1<categories.length;i1++){
		            	Category c = categoryDAO.get(categories[i1]);
		            	c.getProducts().add(p1);
		            	categoryDAO.update(c);
		            }
					System.out.println("Upodated Category-Product bridge table!!");
					System.out.println("Created a product!!");
				}				
			}
			System.out.println("Created products and updated bridge table!!!");
			return new ModelAndView("success");

		}
		catch (ProductException e) 
		{
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
		catch (SellerException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while updating seller on products addition!!");
		} catch (CategoryException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while updating category bridge table on products addition!!");
		}
	}
}
