package com.spring.app.controller.rest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.spring.app.model.Product;
import com.spring.app.service.ProductsService;

@RestController
@RequestMapping("api/product")
public class ProductsRestController {
	@Autowired
	private ProductsService service;

	// 商品全件取得
	@GetMapping(value="/all")
	public List<Product> getproduct() {
		return service.findAll();
	}

	// 商品一件取得
	@GetMapping(value="{id}")
	public Product getProduct(@PathVariable String id) {
		try {
			Product product = service.findOne(Integer.parseInt(id));
			if(product == null){
				product = new Product();
				return product;
			}
			return product;
		} catch (NumberFormatException e) {
			return new Product();
		}		
	}

	//　商品取得
	@PostMapping(value="/sam")
	public List<Product> getValueproduct(@RequestBody Product product) {
		return service.find(product.getName());
	}

	// 商品一件更新
	@PostMapping(value="{id:[0-9]+$}")
	public Product putproduct(@PathVariable Integer id, HttpServletResponse response, @RequestParam String name, @RequestParam String introduction, @RequestParam Integer price,@RequestParam MultipartFile file) {
		if (file.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		Product product = new Product();
		product.setName(name);
		product.setIntroduction(introduction);
		product.setPrice(price);
		product.setImageUrl(file.getOriginalFilename());
		product.setId(id);
		try(BufferedInputStream in = new BufferedInputStream(file.getInputStream());
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("src/main/resources/static/image/" + file.getOriginalFilename()))) {
			FileCopyUtils.copy(in, out);
		} catch (IOException e) {
			throw new RuntimeException("Error uploading file.", e);
		}
		return service.update(product);
	}

	// 商品一件削除
	@DeleteMapping(value="{id:[0-9]+$}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteproduct(@PathVariable Integer id) {
		service.delete(id);
	}

	// 商品一件登録
	@PostMapping
	public Product handle(HttpServletResponse response, @RequestParam String name, @RequestParam String introduction, @RequestParam Integer price,@RequestParam MultipartFile file){
		if (file.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		Product product = new Product();
		product.setName(name);
		product.setIntroduction(introduction);
		product.setPrice(price);
		product.setImageUrl(file.getOriginalFilename());
		service.create(product);
		// アップロードされたファイルを保存。
		try(BufferedInputStream in = new BufferedInputStream(file.getInputStream());
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("src/main/resources/static/image/" + file.getOriginalFilename()))) {
			FileCopyUtils.copy(in, out);
		} catch (IOException e) {
			throw new RuntimeException("Error uploading file.", e);
		}
		return product;
	}
}
