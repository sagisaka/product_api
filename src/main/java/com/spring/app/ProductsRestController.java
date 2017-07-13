package com.spring.app;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController  
@RequestMapping("api/product")  
public class ProductsRestController {
	@Autowired  
	ProductsService service;  

	// 顧客全件取得
	@RequestMapping(method=RequestMethod.GET,value="/all")  
	public List<Product> getproduct() {
		return service.findAll();
	}

	// 顧客一件取得
	@RequestMapping(method=RequestMethod.GET, value="{id}")  
	public Product getproduct(@PathVariable Integer id) {
		return service.findOne(id);
	}

	//　顧客取得
	@RequestMapping(method=RequestMethod.POST, value="/sam")  
	public List<Product> getValueproduct(@RequestBody Product product) {
		return service.find(product.getName());
	}

//	// 顧客一件作成
//	@RequestMapping(method=RequestMethod.POST)  
//	@ResponseStatus(HttpStatus.CREATED)  
//	public product postproduct(@RequestBody product product) {
//		return service.create(product);
//	}

	// 顧客一件更新
	@RequestMapping(method=RequestMethod.POST, value="{id}")  
	public Product putproduct(@PathVariable Integer id,
			HttpServletResponse response, @RequestParam String name, @RequestParam String introduction, @RequestParam String price,@RequestParam MultipartFile file) {
		Product product = new Product();
		product.setName(name);
		product.setIntroduction(introduction);
		product.setPrice(price);
		product.setImageUrl(file.getOriginalFilename());
		product.setId(id);
		return service.update(product);
	}

	// 顧客一件削除
	@RequestMapping(method=RequestMethod.DELETE, value="{id}")  
	@ResponseStatus(HttpStatus.NO_CONTENT)  
	public void deleteproduct(@PathVariable Integer id) {
		service.delete(id);
	}

	@RequestMapping(method=RequestMethod.POST)
	public Product handle(HttpServletResponse response, @RequestParam String name, @RequestParam String introduction, @RequestParam String price,@RequestParam MultipartFile file){
		
		Product product = new Product();
		product.setName(name);
		product.setIntroduction(introduction);
		product.setPrice(price);
		product.setImageUrl(file.getOriginalFilename());
		service.create(product);
		if (file.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		// アップロードされたファイルを保存。
		try {
			BufferedInputStream in =
					new BufferedInputStream(file.getInputStream());
			BufferedOutputStream out =
					new BufferedOutputStream(new FileOutputStream("src/main/resources/static/image/" + file.getOriginalFilename()));
			FileCopyUtils.copy(in, out);
		} catch (IOException e) {
			throw new RuntimeException("Error uploading file.", e);
		}
		return product;
	}

}