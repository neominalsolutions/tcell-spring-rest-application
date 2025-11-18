package com.mertalptekin.springrestapplication.domain.service;

import com.mertalptekin.springrestapplication.domain.entity.Product;

import java.util.List;

// Service Layer her zaman entity ile çalışmalıdır.
// Req entity Application katmanında validationdan sonra maplenmelidir.
public interface IProductService {

    Integer addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Integer id);
    Product getProductById(Integer id);
    List<Product> getAllProducts(Integer pageSize, Integer pageNumber, String search, String sort);

    // Product Yeni bir Feedback ekleme işlemini daha sonra yapalım.

}
