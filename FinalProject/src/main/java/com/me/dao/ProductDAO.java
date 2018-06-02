package com.me.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.me.exception.ProductException;
import com.me.pojo.Product;

public class ProductDAO extends DAO {

	public Product create(Product product)
			throws ProductException {
		try {
			begin();            
			getSession().save(product);     
			commit();
			return product;
		} catch (HibernateException e) {
			rollback();
			//throw new AdException("Could not create advert", e);
			throw new ProductException("Exception while creating product: " + e.getMessage());
		}
	}

	public void delete(Product product)
			throws ProductException {
		try {
			begin();
			getSession().delete(product);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new ProductException("Could not delete product", e);
		}
	}

	public Product update(Product product)
			throws ProductException {
		try {
			begin();
			Query q = getSession().createQuery("update Product set name=:name,price=:price,quantity=:quantity,filename=:filename,description=:description where id=:id");
			q.setLong("id",product.getId());
			q.setString("price", product.getPrice());
			q.setString("name", product.getName());
			q.setString("description", product.getDescription());
			q.setString("filename", product.getFilename());
			q.setString("quantity", product.getQuantity());
			int result = q.executeUpdate();
			commit();

			begin();
			Query q1 = getSession().createQuery("from Product where id=:id");
			q1.setLong("id",product.getId());
			Product product1 = (Product)q1.uniqueResult();
			return product1;
		} catch (HibernateException e) {
			rollback();
			throw new ProductException("Could not update product!!", e);
		}
	}

	public List<Product> list() throws ProductException{
		try {
			begin();
			Query q = getSession().createQuery("from Product");
			List<Product> products = q.list();
			commit();
			return products;
		} catch (HibernateException e) {
			rollback();
			throw new ProductException("Could not delete product", e);
		}
	}

	public List<Product> list(long sellerid) throws ProductException{
		try {
			begin();
			Query q = getSession().createQuery("from Product where seller=:sellerid");
			q.setLong("sellerid", sellerid);
			List<Product> products = q.list();
			commit();
			return products;
		} catch (HibernateException e) {
			rollback();
			throw new ProductException("Could not retrive product list for that seller!!", e);
		}
	}

	public Product get(long productid) throws ProductException{
		try {
			begin();
			Query q = getSession().createQuery("from Product where id=:productid");
			q.setLong("productid", productid);
			Product product = (Product)q.uniqueResult();
			commit();
			return product;
		} catch (HibernateException e) {
			rollback();
			throw new ProductException("Could not retrive product for that productid!!", e);
		}
	}

	public List<Product> get(String searchtext) throws ProductException{
		try {
			begin();
			Criteria crit = getSession().createCriteria(Product.class)
					.add(Restrictions.ilike("name",searchtext,MatchMode.ANYWHERE));
			@SuppressWarnings("unchecked")
			List<Product> productids = (List<Product>)crit.list();
			commit();
			System.out.println(productids.size());
			return productids;
		} catch (HibernateException e) {
			rollback();
			throw new ProductException("Could not retrive product for that productid!!", e);
		}
	}

	public List<Product> listPaginatedProductsUsingCriteria(int firstResult, int maxResults) {

		try {
			begin();
			Criteria criteria = getSession().createCriteria(Product.class);
			criteria.setFirstResult(firstResult);
			criteria.setMaxResults(maxResults);
			List<Product> products = (List<Product>) criteria.list();
			commit();
			close();
			return products;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getTotalCount() {
		int count =0;
		try {
			begin();
			Query q = getSession().createQuery("from Product");
			List<Product> pl = (List<Product>)q.list();
			count = pl.size();
			System.out.println(count);
			commit();
			close();
			return count;
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void updatequantity(int newquantity, long pid) {
		try {
			begin();
			Query q = getSession().createQuery("update Product set quantity=:quantity where id=:pid");
			System.out.println("1");
			q.setString("quantity", String.valueOf(newquantity));
			System.out.println("2");
			q.setLong("pid", pid);
			System.out.println("B4 EU!!");
			int result = q.executeUpdate();
			System.out.println("Result of update product due to cart:\t"+result);
			commit();
			close();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}
}