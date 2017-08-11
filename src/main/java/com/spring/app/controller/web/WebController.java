package com.spring.app.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.spring.app.model.Product;
import com.spring.app.service.ProductsService;

@Controller
public class WebController {
	@Autowired
	ProductsService service;

	@GetMapping(value="/")
	public String index() {
		return "index";
	}

	@GetMapping(value="/{id}")
	public String detail(@PathVariable("id") String id, Model model) {
		try {
			Product product = service.findOne(Integer.parseInt(id));
			if(product == null){
				return "nullDetail";
			}
			model.addAttribute("image","/image/"+product.getImageUrl());
			model.addAttribute("introduction",product.getIntroduction());
			model.addAttribute("price",product.getPrice()+"円");
			model.addAttribute("data",service.findOne(Integer.parseInt(id)));
			return "detail";
		} catch (NumberFormatException e) {
			return "nullDetail";
		}				
	}

	@GetMapping(value="/create")
	public String create() {
		return "create";
	}
}
