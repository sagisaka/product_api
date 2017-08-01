package com.spring.app.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.app.model.Product;
import com.spring.app.service.ProductsService;

@Controller
public class WebController {
	@Autowired  
	ProductsService service;  

	@RequestMapping(value="/",method=RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value="/{id}")
	public String detail(@PathVariable("id") String id, Model model) {
		Product p = new Product();
		p = service.findOne(Integer.parseInt(id));
		model.addAttribute("image","/image/"+p.getImageUrl());
		model.addAttribute("introduction",p.getIntroduction());
		model.addAttribute("data",service.findOne(Integer.parseInt(id)));
		return "detail";
	}

	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String create() {
		return "create";
	}
}

