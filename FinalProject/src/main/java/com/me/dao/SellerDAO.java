package com.me.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.me.exception.SellerException;
import com.me.pojo.Product;
import com.me.pojo.Seller;

public class SellerDAO extends DAO {
	
	public SellerDAO() {
	
	}
	
	public void delete(Seller seller) throws SellerException {
		try {
			begin();
			getSession().delete(seller);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new SellerException("Could not delete seller " + seller.getUsername(), e);
		}
	}
	
	public Seller register(Seller s)
			throws SellerException {
		try {
			begin();
			System.out.println("inside Seller DAO!!");
			String email = s.getEmailAddress();
			String contactnumber = s.getContactnumber();
			Seller seller = new Seller(s.getUsername(), s.getPassword());
			seller.setName(s.getName());
			seller.setContactnumber(contactnumber);
			seller.setEmailAddress(email);
			seller.setStatus("Pending");
			getSession().save(seller);
			commit();
			return seller;

		} catch (HibernateException e) {
			rollback();
			throw new SellerException("Exception while creating seller: " + e.getMessage());
		}
	}
	
	public Seller get(String username, String password) throws SellerException {
		try {
			begin();
			Query q = getSession().createQuery("from Seller where username = :username and password = :password");
			System.out.println("Username:"+username+"\tpassword:"+password);
			q.setString("username", username);
			q.setString("password", password);			
			Seller seller = (Seller) q.uniqueResult();
			commit();
			close();
			if(seller!=null)
			System.out.println("Seller retrieved for U&P:\t"+seller.getName());
			return seller;
		} catch (HibernateException e) {
			rollback();
			throw new SellerException("Could not get seller " + username, e);
		}
	}

	public Seller get(int sellerId) throws SellerException {
		System.out.println("Seller ID in get function:\t"+sellerId);
		try {
			begin();
			Query q = getSession().createQuery("from Seller where sellerID= :sellerID");
			q.setInteger("sellerID", sellerId);		
			Seller seller = (Seller) q.uniqueResult();
			commit();
			return seller;
		} catch (HibernateException e) {
			rollback();
			throw new SellerException("Could not get seller " + sellerId, e);
		}
	}
	
    public void update(Seller seller) throws SellerException {
        try {
            begin();
            getSession().update(seller);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new SellerException("Could not update the saved seller!!", e);
        }
    }	
}
