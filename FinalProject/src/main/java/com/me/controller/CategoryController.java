package com.me.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.me.dao.CategoryDAO;
import com.me.pojo.Category;
import com.me.validator.CategoryValidator;
import com.me.exception.CategoryException;

@Controller
public class CategoryController {
	@Autowired
	@Qualifier("categoryValidator")
	CategoryValidator categoryValidator;

	@Autowired
	@Qualifier("categoryDao")
	CategoryDAO categoryDAO;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(categoryValidator);
	}
	
	@RequestMapping(value = "admin/AddCategories.htm", method = RequestMethod.GET)
	protected ModelAndView AdminAddCategories() throws Exception {
		System.out.println("Admin home Add Categories clicked!");
		return new ModelAndView("category-add-form","category",new Category());
	}
	
	@RequestMapping(value = "admin/add.htm", method = RequestMethod.POST)
	public ModelAndView addCategory(@ModelAttribute("category") Category category, BindingResult result,HttpServletRequest Request) throws Exception {
		System.out.println("Inside Add Category Controller!!!");
		categoryValidator.validate(category, result);
		
		if (result.hasErrors()) {
			return new ModelAndView("category-add-form", "category", category);
		}

		try {				
			category = categoryDAO.create(category.getTitle());
		} catch (CategoryException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
		Request.getSession().setAttribute("success", "Successfully added Category!!");
		return new ModelAndView("category-add-form", "category", new Category());		
	}
}