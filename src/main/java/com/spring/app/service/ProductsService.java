package com.spring.app.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.spring.app.model.Product;
import com.spring.app.repository.ProductsRepository;

@Service
@Transactional
public class ProductsService {
	@Autowired
	private ProductsRepository repository;

	// 商品全件取得
	public List<Product> findAll() {
		return repository.findAll();
	}

	// 商品一件取得
	public Product findOne(Integer id) {
		return repository.findOne(id);
	}

	//商品の名前検索
	public List<Product> find(String name) {
		return repository.findByName(name);
	}

	// 商品一件作成
	public Product create(String name,String introduction, String price,MultipartFile file,HttpServletResponse response) throws IOException {
		if (file.isEmpty()) {
			response.sendError(HttpStatus.BAD_REQUEST.value());
		}
		Product product = new Product();
		product.setName(name);
		product.setIntroduction(introduction);
		product.setPrice(price);
		product.setImageUrl(file.getOriginalFilename());
		try(BufferedInputStream in = new BufferedInputStream(file.getInputStream());
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("src/main/resources/static/image/" + file.getOriginalFilename()))) {
			FileCopyUtils.copy(in, out);
		} catch (IOException e) {
			throw new RuntimeException("Error uploading file.", e);
		}
		return repository.save(product);
	}

	// 商品一件更新
	public Product update(Integer id,String name,String introduction, String price,MultipartFile file, HttpServletResponse response) throws IOException {
		Product product = repository.findOne(id);
		if (file.isEmpty() || product == null) {
			response.sendError(HttpStatus.BAD_REQUEST.value());
		}
		product.setName(name);
		product.setIntroduction(introduction);
		product.setPrice(price);
		product.setImageUrl(file.getOriginalFilename());
		try(BufferedInputStream in = new BufferedInputStream(file.getInputStream());
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("src/main/resources/static/image/" + file.getOriginalFilename()))) {
			FileCopyUtils.copy(in, out);
		} catch (IOException e) {
			throw new RuntimeException("Error uploading file.", e);
		}
		return repository.save(product);
	}

	// 商品一件削除
	public void delete(Integer id) {
		repository.delete(id);
	}
}
