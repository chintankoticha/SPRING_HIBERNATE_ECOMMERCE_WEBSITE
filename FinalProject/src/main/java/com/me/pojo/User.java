package com.me.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="user_table")
@PrimaryKeyJoinColumn(name = "personID")
public class User extends Person {

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@OneToOne(mappedBy = "user",cascade=CascadeType.ALL)
	private Email email;
	
	@OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<Cart> cart = new ArrayList<Cart>();	
	
	@OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<Order> order = new ArrayList<Order>();	

	public List<Cart> getCart() {
		return cart;
	}

	public void setCart(List<Cart> cart) {
		this.cart = cart;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User() {
	
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}
}