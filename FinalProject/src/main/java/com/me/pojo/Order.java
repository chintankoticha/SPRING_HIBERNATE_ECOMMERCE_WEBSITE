package com.me.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="order_table")
public class Order implements Serializable{

	@Id
    @ManyToOne
    @JoinColumn(name="userid")
    private User user;
    
    @Id
    @ManyToOne
    @JoinColumn(name="productid")
    private Product product;
    
    @Id
    @Column(name="orderid")
    private int id;
    
    @Temporal(TemporalType.DATE)
    @Column(name="order_date")
    private java.util.Date creationDate;
    
    @Column(name="quantity")
    private int quantity;
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.util.Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}

	public Order() {
        
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}