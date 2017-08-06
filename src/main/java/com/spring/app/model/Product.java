package com.spring.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
	@NotNull
	@Size(max=100)
	private String name;

	@Column(nullable=false)
	@NotNull
	@Size(max=500)
	private String introduction;

	@Column(nullable=false)
	@NotNull
	private String price;

	@Column(nullable=false)
	@NotNull
	private String imageUrl;
}
