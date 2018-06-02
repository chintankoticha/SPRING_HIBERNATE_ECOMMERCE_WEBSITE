package com.me.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;

import com.me.exception.CartException;
import com.me.exception.ProductException;
import com.me.pojo.Cart;
import com.me.pojo.Category;
import com.me.pojo.Order;
import com.me.pojo.Product;

public class CartDAO extends DAO {

	public List<Cart> list(long uid)
			throws CartException {
		try {
			begin();            
			Query query = getSession().createQuery("from Cart where userid=:uid");
			query.setLong("uid", uid);
			List<Cart> list = query.list();
			commit();
			return list;
		} catch (HibernateException e) {
			rollback();
			throw new CartException("Exception while retrieving cart in CART DAO: " + e.getMessage());
		}
	}

	public void updatedb(long pid,long uid,int quantity)throws CartException{
		try {
			begin();            
			Query query = getSession().createQuery("update Cart set quantity=:quantity where userid=:uid and productid=:pid");
			query.setLong("uid", uid);
			query.setLong("pid", pid);
			query.setInteger("quantity", quantity);
			int result = query.executeUpdate();
			System.out.println("Result -update CARTDAO:\t"+result);
			commit();
			close();
		} catch (HibernateException e) {
			rollback();
			throw new CartException("Exception while updating cart in CART DAO: " + e.getMessage());
		}
	}

	public void deleteindb(Long pid, long personID) throws CartException{
		try {
			begin();            
			Query query = getSession().createQuery("delete Cart where userid=:uid and productid=:pid");
			query.setLong("uid", personID);
			query.setLong("pid", pid);
			int result = query.executeUpdate();
			System.out.println("Result -delete CARTDAO:\t"+result);
			commit();
			close();
		} catch (HibernateException e) {
			rollback();
			throw new CartException("Exception while deleting cart in CART DAO: " + e.getMessage());
		}		
	}

	public int get(Product p,long uid) throws CartException{
		try {
			begin();            
			Query query = getSession().createQuery("from Cart where userid=:uid and productid=:pid");
			query.setLong("pid", p.getId());
			query.setLong("uid", uid);
			Cart cart = (Cart)query.uniqueResult();
			//System.out.println("Result -delete CARTDAO:\t"+result);
			commit();
			close();
			return cart.getQuantity();
		} catch (HibernateException e) {
			rollback();
			throw new CartException("Exception while deleting cart in CART DAO: " + e.getMessage());
		}			
	}

	public int getmax() {
		begin();
		Criteria crit = getSession().createCriteria(Order.class);
		crit.setProjection(Projections.max("id"));
		int maxid=0;
		if(crit.uniqueResult()==null){
			maxid=0;
		}else{
			maxid = (Integer)crit.uniqueResult();
		}
		commit();
		close();
		return maxid;
	}

	public void createOrder(Order order) throws CartException{
		try {
			System.out.println(""+order.getId()+""+order.getQuantity()+""+order.getCreationDate()+""+order.getProduct().getName());
			begin();            
			getSession().save(order);
			commit();
			close();
		} catch (HibernateException e) {
			rollback();
			throw new CartException("Exception while creating order in CART DAO: " + e.getMessage());
		}			
	}

	public Map<Integer,List<Order>> listordermap(long personID) throws CartException{
		Map<Integer,List<Order>> ordermap = new HashMap<Integer,List<Order>>();
		try {
			List<Order> orderlist = listorder(personID);
			int orderid;
			for(Order order:orderlist){
				orderid = order.getId();
				System.out.println("------ORDER ID:\t"+orderid);
				begin();            
				Query query = getSession().createQuery("from Order where userid=:uid and id=:id");
				query.setLong("uid", personID);
				query.setInteger("id", orderid);
				@SuppressWarnings("unchecked")
				List<Order> orderlistOnId = (List<Order>)query.list();
				System.out.println("LIST SIZE FOR ORDERID:\t"+orderlistOnId.size());
				commit();
				close();
				ordermap.put(orderid, orderlistOnId);
			}
			return ordermap;
		} catch (HibernateException e) {
			rollback();
			throw new CartException("Exception while retrieving orderlist in CART DAO: " + e.getMessage());
		}			
	}

	public List<Order> listorder(long personID)throws CartException {
		try {
			begin();            
			Query query = getSession().createQuery("from Order where userid=:uid");
			query.setLong("uid", personID);
			@SuppressWarnings("unchecked")
			List<Order> orderlist = (List<Order>)query.list();
			commit();
			close();
			return orderlist;
		} catch (HibernateException e) {
			rollback();
			throw new CartException("Exception while retrieving orderlist in CART DAO: " + e.getMessage());
		}	
	}

	public List<Order> getOrderList(int orderidpdf) throws CartException{
		try {
			begin();            
			Query query = getSession().createQuery("from Order where id=:id");
			query.setInteger("id", orderidpdf);
			@SuppressWarnings("unchecked")
			List<Order> orderlist = (List<Order>)query.list();
			System.out.println("LIST SIZE FOR ORDERID:\t"+orderlist.size());
			commit();
			close();
			return orderlist;
		}
		catch (HibernateException e) {
			rollback();
			throw new CartException("Exception while retrieving orderlist 2 in CART DAO: " + e.getMessage());
		}	
	}

	public List<Cart> getlist(long personID) throws CartException{
		List<Cart> cartlist = new ArrayList<Cart>();
		try {
			begin();            
			Query query = getSession().createQuery("from Cart where userid=:id");
			query.setLong("id", personID);
			cartlist = (List<Cart>)query.list();
			System.out.println("LIST SIZE OF CART FOR USERID:\t"+cartlist.size());
			commit();
			close();
			return cartlist;
		}
		catch (HibernateException e) {
			rollback();
			throw new CartException("Exception while retrieving cartlist for userid in CART DAO: " + e.getMessage());
		}
	}
}