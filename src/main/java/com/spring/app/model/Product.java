package com.spring.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

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
	@NotBlank
	@Size(max=100)
	private String name;

	@Column(nullable=false)
	@NotBlank
	@Size(max=500)
	private String introduction;

	@Column(nullable=false)
	@NotBlank
	@Pattern(regexp="[0-9]+$")
	private String price;

	@Column(nullable=false)
	@NotBlank
	private String imageUrl;
}
