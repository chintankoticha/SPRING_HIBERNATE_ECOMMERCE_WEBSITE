package com.me.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="category_table")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categoryId",unique = true,nullable=false)
    private long categoryId;
	
	@Column(name = "title",unique = true,nullable=false)
    private String title;
    
	@ManyToMany
	@JoinTable(
			name = "category_product_table",
			joinColumns = {@JoinColumn(name="categoryID",nullable=false,updatable=false)},
			inverseJoinColumns = {@JoinColumn(name = "productID")}
			)
	private Set<Product> products = new HashSet<Product>();

    public Category() {
    	
    }
	
	public Category(String title) {
        this.title = title;
    }

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	@Override 
	public String toString(){
		return title;
	}
}