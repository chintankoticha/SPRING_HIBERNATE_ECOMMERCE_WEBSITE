package com.me.dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.me.exception.AdminException;
import com.me.exception.UserException;
import com.me.pojo.Seller;

public class AdminDAO extends DAO{

	public ArrayList<Seller> getNewSellerList() throws AdminException{
		ArrayList<Seller> sellerList;
		try{
			String sql = "from Seller where status = 'Pending'";
			Query query = getSession().createQuery(sql);
			System.out.println("query for seller's with status Pending ran!!");
			//query.setParameter("username1", username);
			sellerList = (ArrayList<Seller>)query.list();
			for(Seller seller:sellerList){
				System.out.println("1 - Printing seller's with status pending!");
				System.out.println(seller.getName());
			}
		}catch (HibernateException e) {
			rollback();
			throw new AdminException("Error retrieving seller list from db!!", e);
		}
		return sellerList;
	}
}
