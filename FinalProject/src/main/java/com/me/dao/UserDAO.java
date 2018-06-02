package com.me.dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import com.me.exception.UserException;
import com.me.pojo.Email;
import com.me.pojo.User;

public class UserDAO extends DAO {

	public UserDAO() {
	}

	public boolean checkIfExists(User user) throws UserException{
		try{
			String sql = "select username from User";
			Query query = getSession().createQuery(sql);
			System.out.println("query for same username ran!!");
			ArrayList<String> userList = (ArrayList<String>)query.list();
			for(String user1:userList){
				System.out.println("1");
				System.out.println(user1);
				if(user1.equalsIgnoreCase(user.getUsername())){
					return false;
				}
				System.out.println("2");
			}
		}catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " +user.getUsername(), e);
		}
		return true;
	}

	public User get(String username, String password) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User where username = :username and password = :password");
			q.setString("username", username);
			q.setString("password", password);			
			User user = (User) q.uniqueResult();
			commit();
			close();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + username, e);
		}
	}

	public void update(User user) throws UserException {
		try {
			begin();
			getSession().update(user);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not save the user", e);
		}
	}

	public User get(int userId) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User where personID= :personID");
			q.setInteger("personID", userId);		
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + userId, e);
		}
	}

	public User register(User u)
			throws UserException {
		try {
			begin();
			System.out.println("inside DAO");

			Email email = new Email(u.getEmail().getEmailAddress());
			User user = new User(u.getUsername(), u.getPassword());

			user.setFirstName(u.getFirstName());
			user.setLastName(u.getLastName());
			user.setEmail(email);
			email.setUser(user);
			getSession().save(user);
			commit();
			return user;

		} catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while creating user: " + e.getMessage());
		}
	}

	public void delete(User user) throws UserException {
		try {
			begin();
			getSession().delete(user);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not delete user " + user.getUsername(), e);
		}
	}
}