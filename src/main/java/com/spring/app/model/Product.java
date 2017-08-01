package com.spring.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity  
@Table(name="product")  
@Data  
@AllArgsConstructor  
@NoArgsConstructor 
public class Product {
	@Id  
	@GeneratedValue  
	private Integer id;

	@Column(nullable=false) 
	private String name;

	@Column(nullable=false)  
	private String introduction;

	@Column(nullable=false)  
	private String price;

	@Column(nullable=false)
	private String imageUrl;

}